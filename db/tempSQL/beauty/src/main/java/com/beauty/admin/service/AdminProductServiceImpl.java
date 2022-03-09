package com.beauty.admin.service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.transaction.Transactional;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.beauty.BeautyConstants;
import com.beauty.common.DateUtil;
import com.beauty.common.FtpClientUtil;
import com.beauty.entity.Brand;
import com.beauty.entity.Category;
import com.beauty.entity.DataTables;
import com.beauty.entity.DeliveryCompany;
import com.beauty.entity.DetailTop;
import com.beauty.entity.DetailTopContent;
import com.beauty.entity.Product;
import com.beauty.entity.ProductCategory;
import com.beauty.entity.ProductDelivery;
import com.beauty.entity.ProductItem;
import com.beauty.entity.User;
import com.beauty.repository.BrandRepository;
import com.beauty.repository.CategoryRepository;
import com.beauty.repository.DeliveryCompanyRepository;
import com.beauty.repository.DetailTopContentRepository;
import com.beauty.repository.DetailTopRepository;
import com.beauty.repository.ProductCategoryRepository;
import com.beauty.repository.ProductDeliveryRepository;
import com.beauty.repository.ProductItemRepository;
import com.beauty.repository.ProductRepository;
import com.beauty.repository.UserRepository;
import com.beauty.repository.specification.ProductSpecification;
import com.beauty.response.CommonResponse;
import com.beauty.response.DataTablesResult;
import com.beauty.response.result.ResultCode;

import lombok.Getter;
import lombok.Setter;

@Service
public class AdminProductServiceImpl implements AdminProductService {
	@Value("${server.image.url}")
	@Getter @Setter
	String image_url;

	@Value("${ftp.host}")
	@Getter @Setter
	String ftpHost;

	@Value("${ftp.username}")
	@Getter @Setter
	String ftpUsername;

	@Value("${ftp.userpassword}")
	@Getter @Setter
	String ftpPassword;

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private ProductItemRepository productItemRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BrandRepository brandRepository;

	@Autowired
	private ProductDeliveryRepository productDeliveryRepository;

	@Autowired
	private DeliveryCompanyRepository deliveryCompanyRepository;

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private ProductCategoryRepository productCategoryRepository;

	@Autowired
	private DetailTopRepository detailTopRepository;
	
	@Autowired
	private DetailTopContentRepository detailTopContentRepository;
	
	@Override
	public DataTablesResult list(DataTables input, Long box, Long brand, Long category) {
		Specifications<Product> specs = Specifications.where(ProductSpecification.likeSearch(input.getSearch().get("value"), box, null));
		
//		if(brand != null) {
//			specs.and(ProductSpecification.equal("brand.brandId", brand));
//		}
//
//		if(category != null) {
//			specs.and(ProductSpecification.equal("category.categoryId", brand));
//		}

		PageRequest pageRequest = new PageRequest(input.getStart(), input.getLength(), input.getSort("regDate")); //현재페이지, 조회할 페이지수, 정렬정보
		Page<Product> data = productRepository.findAll(specs, pageRequest);

		DataTablesResult result = new DataTablesResult();
		result.setDraw(input.getDraw());
		result.setRecordsTotal(data.getContent().size());
		result.setRecordsFiltered(data.getTotalElements());
		result.setData(data.getContent());

		return result;
	}

	@Override
	public CommonResponse seller(List<Product> products, int seller) {
		for(Product product:products) {
			product.setStopSelling(seller);
		}
		productRepository.save(products);
		if(seller == 0) {
			return CommonResponse.of("선택한 상품이 판매를 시작하였습니다.", ResultCode.SUCCESS);
		} else {
			return CommonResponse.of("선택한 상품의 판매가 중지되었습니다.", ResultCode.SUCCESS);	
		}

	}


	@Override
	public CommonResponse score(List<Product> products, int score) {
		for(Product product:products) {
			product.setScore(product.getScore() + score);
		}
		productRepository.save(products);
		return CommonResponse.of("선택한 상품이 점수가 반영 되었습니다.", ResultCode.SUCCESS);
	}

	@Override
	public Map<String, Object> getProduct(String productId) {
		if(productId.indexOf("p_") > -1) {
			productId = productId.replaceAll("p_", "");
		}
		Product product = productRepository.findOne(Long.parseLong(productId));

		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("product", product);
		resultMap.put("category", productCategoryRepository.findByProduct(product));
		resultMap.put("imageUrl", image_url);
		return resultMap;
	}


	@Transactional
	@Override
	public CommonResponse save(JSONObject jsonObj) {

		//{"seller":"test22@naver.com","thumb_path":"\/beauty\/product\/20170119\/thumb_111913708.jpg","delivery":"0","salePrice":"1000","dcompany":"1","freeDelivery":"0","productName":"ㅇㄻㄹ",
		//"productDesc":"ㅁㄹㅈㅁㄹㅈ","sale":"90","uniqueness":"999","price":"10000","category":["15"],"deliveryDate":"0","brand":"1","detail_path":""}
		//seller//
		// thumb_path//
		//delivery//
		//salePrice
		//dcompany  //
		//freeDelivery//
		//productName
		//productDesc
		//sale
		//uniqueness//
		//price
		//category
		//deliveryDate//
		//brand//
		//detail_path//
		String dcId = jsonObj.get("dcompany").toString();
		DeliveryCompany dc = deliveryCompanyRepository.findOne(dcId);
		ProductDelivery pd = null;
		// 배송정보
		if(jsonObj.get("deliveryId").equals("")) {
			pd = new ProductDelivery();
		} else {
			pd = productDeliveryRepository.findOne(Long.parseLong(jsonObj.get("deliveryId").toString()));
		}

		pd.setDeliveryCompany(dc);
		pd.setDeliveryDate(Integer.parseInt(jsonObj.get("deliveryDate").toString()));
		pd.setFreeDelivery(Integer.parseInt(jsonObj.get("freeDelivery").toString()));
		pd.setShippingType(Integer.parseInt(jsonObj.get("delivery").toString()));
		pd.setDeliveryPrice(Integer.parseInt(jsonObj.get("deliveryPrice").toString()));
		
		pd.setUniqueness(jsonObj.get("uniqueness").toString());

		pd = productDeliveryRepository.save(pd);

		//브랜드
		Long brandId = Long.parseLong(jsonObj.get("brand").toString());
		Brand brand = brandRepository.findOne(brandId);
		//판매자
		String seller = jsonObj.get("seller").toString();
		User user = userRepository.findOne(seller);

		Product product = null;
		if(jsonObj.get("productId").equals("")) {
			product = new Product();
		} else {
			product = productRepository.findOne(Long.parseLong(jsonObj.get("productId").toString()));
		}
		product.setBrand(brand);

//		if(jsonObj.get("thumbUrl") != null && !jsonObj.get("thumbUrl").equals("")) {
//			Optional<InputStream> inputStream;
//			try {
//				inputStream = Base64.Base64InputStream(jsonObj.get("thumbUrl").toString());
//				String ftpDirectory = BeautyConstants.FTP_PRODUCT + "/" + DateUtil.getDateString("yyyyMMdd");
//				String uploadFile = uploadFile(inputStream, ftpDirectory, "thumb");
//
//				product.setThumbUrl(uploadFile);
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}

		product.setBanner(jsonObj.get("detail_path").toString());
		product.setThumbUrl(jsonObj.get("thumb_path").toString());
		product.setPrice(Integer.parseInt(jsonObj.get("price").toString()));
		product.setProdDesc(jsonObj.get("productDesc").toString());
		product.setProdName(jsonObj.get("productName").toString());
		product.setProductDelivery(pd);
		product.setSale(Integer.parseInt(jsonObj.get("sale").toString()));
		product.setSalePrice(Integer.parseInt(jsonObj.get("salePrice").toString()));
		product.setSeller(user);
		product.setContent(jsonObj.get("content").toString());
		product.setRegDate(new Date());
		int box = Integer.parseInt(jsonObj.get("box").toString());
		product.setBox(box);
		
		product = productRepository.save(product);
		
		if(box == 0) {
			JSONArray ctg =  (JSONArray) jsonObj.get("category");

			ProductCategory productCategory = null;
			List<ProductCategory> pcList = new ArrayList<>();

			for(Object ctgId : ctg) {
				productCategory = new ProductCategory();
				productCategory.setProduct(product);

				Category category = categoryRepository.findOne(Long.parseLong(ctgId.toString()));
				productCategory.setCategory(category);

				pcList.add(productCategory);
			}
			productCategoryRepository.deleteByProduct(product);
			productCategoryRepository.save(pcList);
		}
		return CommonResponse.of("저장 되었습니다.", ResultCode.SUCCESS);
	}


	@Override
	public CommonResponse delete(String itemId, int stop) {
		if(itemId.indexOf("i_") > -1) {
			itemId = itemId.replaceAll("i_", "");
		}
		ProductItem item = productItemRepository.findOne(Long.parseLong(itemId));
		item.setStopSelling(stop);
		productItemRepository.save(item);

		return CommonResponse.of("상태가 변경 되었습니다.", ResultCode.SUCCESS);

	}

	@Override
	public CommonResponse uploadImage(MultipartFile image, String thumb) {

		String subname = "thumb";
		String ftpDirectory = BeautyConstants.FTP_PRODUCT + "/" + DateUtil.getDateString("yyyyMMdd");
		if(thumb.equals("0")) {
			subname = "detail";
		} else if(thumb.equals("1")) {
			subname = "banner";
		} else if(thumb.equals("2")) {
			subname = "top";
		}
		return uploadFile(image, ftpDirectory, subname);
	}

	public CommonResponse uploadFile( MultipartFile localDirectoryAndFileName, String ftpDirectory, String sub_name) {
		FtpClientUtil f = new FtpClientUtil(ftpHost, 21, ftpUsername, ftpPassword);
		String result = null;
		try {
			if (f.open()) {
				result = f.put(localDirectoryAndFileName, ftpDirectory, sub_name);
				f.close();
				f = null;
			}
		} catch (Exception e) {
			return CommonResponse.of("fail", ResultCode.FAIL);
		}

		return CommonResponse.of(result, ResultCode.SUCCESS);
	}

	public String uploadFile( Optional<InputStream> localDirectoryAndFileName, String ftpDirectory, String sub_name) {
		FtpClientUtil f = new FtpClientUtil(ftpHost, 21, ftpUsername, ftpPassword);
		String result = null;
		try {
			if (f.open()) {
				result = f.put(localDirectoryAndFileName, ftpDirectory, sub_name);
				f.close();
				f = null;
			}
		} catch (Exception e) {

			e.printStackTrace();
		}

		return result;
	}

	@Override
	public List<Product> getProductAll() {
		return productRepository.findByStopSelling(0);
	}

	@Override
	public DataTablesResult topList(DataTables input) {
		PageRequest pageRequest = new PageRequest(input.getStart(), input.getLength(), input.getSort("regDate")); //현재페이지, 조회할 페이지수, 정렬정보
		Page<DetailTop> data = detailTopRepository.findAll(pageRequest);

		DataTablesResult result = new DataTablesResult();
		result.setDraw(input.getDraw());
		result.setRecordsTotal(data.getContent().size());
		result.setRecordsFiltered(data.getTotalElements());
		result.setData(data.getContent());

		return result;
	}

	@Override
	public CommonResponse topSave(JSONObject jsonObj) {
		DetailTop detailTop = null;
		String action = jsonObj.get("action").toString();
		if(action.equals("write")) {
			detailTop = new DetailTop();
		} else {
			Long id = Long.parseLong(jsonObj.get("dtId").toString());
			detailTop = detailTopRepository.findOne(id);
		}
//
		int topType = Integer.parseInt(jsonObj.get("topType").toString());
		Long eid = Long.parseLong(jsonObj.get("eid").toString());
		detailTop.setTopType(topType);
		detailTop.setThumbUrl(jsonObj.get("thumb_path").toString());
		detailTop.setEid(eid);
		
		detailTop = detailTopRepository.save(detailTop);
		JSONArray prod =  (JSONArray) jsonObj.get("prod");
		DetailTopContent topContent = null;
		List<DetailTopContent> list = new ArrayList<>();
		for (int i=0; i<prod.size(); i++) {
			topContent = new DetailTopContent();
			topContent.setContentId(Long.parseLong(prod.get(i).toString()));
			topContent.setDetailTop(detailTop);
			topContent.setTopType(topType);
			list.add( topContent );
		}
		
		detailTopContentRepository.save(list);
		return CommonResponse.of("저장 되었습니다.", ResultCode.SUCCESS);
	}

}
