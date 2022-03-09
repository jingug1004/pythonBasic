package com.beauty.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;

import com.beauty.BeautyConstants;
import com.beauty.common.DateUtil;
import com.beauty.entity.Brand;
import com.beauty.entity.Category;
import com.beauty.entity.DetailTop;
import com.beauty.entity.DetailTopContent;
import com.beauty.entity.MainFirstBanner;
import com.beauty.entity.Product;
import com.beauty.entity.ProductCategory;
import com.beauty.entity.ProductItem;
import com.beauty.entity.ProductTimeSale;
import com.beauty.entity.Review;
import com.beauty.repository.BrandRepository;
import com.beauty.repository.CategoryRepository;
import com.beauty.repository.DetailTopContentRepository;
import com.beauty.repository.DetailTopRepository;
import com.beauty.repository.MainFirstBannerRepository;
import com.beauty.repository.ProductCategoryRepository;
import com.beauty.repository.ProductItemRepository;
import com.beauty.repository.ProductRepository;
import com.beauty.repository.ProductTimeSaleRepository;
import com.beauty.repository.specification.DetailTopSpecification;
import com.beauty.repository.specification.ProductCategorySpecification;
import com.beauty.repository.specification.ProductSpecification;
import com.beauty.response.BestResponse;
import com.beauty.response.CategoryResponse;
import com.beauty.response.CategoryResponse2;
import com.beauty.response.ProductResponse;
import com.beauty.response.result.CategoryLvResult;
import com.beauty.response.result.ResultCode;

import lombok.Getter;
import lombok.Setter;

@Service
public class ProductServiceImpl implements ProductService {

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
	private CategoryRepository categoryRepository;

	@Autowired
	private BrandRepository brandRepository;

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private ProductCategoryRepository productCategoryRepository;

	@Autowired
	private ProductItemRepository productItemRepository;

	@Autowired
	private ProductTimeSaleRepository productTimeSaleRepository;

	@Autowired
	private MainFirstBannerRepository mainFirstBannerRepository;

	@Autowired
	private DetailTopRepository detailTopRepository;

	@Autowired
	private DetailTopContentRepository detailTopContentRepository;

	@Override
	public CategoryResponse getCategory() {

		List<Object[]> categoryList = categoryRepository.findByProductCategory();
		CategoryLvResult category = null;
		List<CategoryLvResult> ctgList = new ArrayList<>();
		for(Object[] obj:categoryList) {
			category = new CategoryLvResult();
			category.setLv1_id(Long.parseLong(obj[0].toString()));
			category.setLv1(obj[1].toString());

			if(obj[2] != null) {
				category.setLv2_id(Long.parseLong(obj[2].toString()));
				category.setLv2(obj[3].toString());
			}

			if(obj[4] != null) {
				category.setLv3_id(Long.parseLong(obj[4].toString()));
				category.setLv3(obj[5].toString());
			}
			ctgList.add(category);
		}


		List<Brand> brandList = brandRepository.findByBrandVisibleOrderByBrandNameAsc("Y");

		return CategoryResponse.of("success", ResultCode.SUCCESS, ctgList, brandList);
	}

	@Override
	public CategoryResponse2 getCategory2() {

		List<Object[]> categoryList = categoryRepository.findByProductCategory();
		CategoryLvResult category = null;
		List<CategoryLvResult> ctgList = new ArrayList<>();
		for(Object[] obj:categoryList) {
			category = new CategoryLvResult();
			category.setLv1_id(Long.parseLong(obj[0].toString()));
			category.setLv1(obj[1].toString());

			if(obj[2] != null) {
				category.setLv2_id(Long.parseLong(obj[2].toString()));
				category.setLv2(obj[3].toString());
			}

			if(obj[4] != null) {
				category.setLv3_id(Long.parseLong(obj[4].toString()));
				category.setLv3(obj[5].toString());
			}
			ctgList.add(category);
		}

		List<Brand> brandList = brandRepository.findByBrandVisibleOrderByBrandNameAsc("Y");
		HashMap<String, List<Brand>> choList = new HashMap<>();
		List<Brand> tmpBrandList = null;
		for(Brand brand:brandList) {
			char comVal = (char) (brand.getBrandName().charAt(0)-0xAC00);
			if (comVal >= 0 && comVal <= 11172){ // 한글일경우 
				// 초성만 입력 했을 시엔 초성은 무시해서 List에 추가합니다. 
				char uniVal = (char)comVal; // 유니코드 표에 맞추어 초성 중성 종성을 분리합니다.. 
				char cho = (char) ((((uniVal - (uniVal % 28)) / 28) / 21) + 0x1100); 
				if(cho!=4519){ 
					tmpBrandList = choList.get(cho+" ");
					if(tmpBrandList == null) {
						tmpBrandList = new ArrayList<>();
					}
					tmpBrandList.add(brand);
					choList.put(cho+" ", tmpBrandList);
				} 
			} else {
				comVal = (char) (comVal+0xAC00); 
				tmpBrandList = choList.get(comVal+" ");
				if(tmpBrandList == null) {
					tmpBrandList = new ArrayList<>();
				}
				tmpBrandList.add(brand);
				choList.put(comVal+" ", tmpBrandList);
			}
		}
		TreeMap treeMap = new TreeMap( choList );


		return CategoryResponse2.of("success", ResultCode.SUCCESS, ctgList, treeMap);
	}

	public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map) {
		return map.entrySet()
				.stream()
				.sorted(Map.Entry.comparingByValue(/*Collections.reverseOrder()*/))
				.collect(Collectors.toMap(
						Map.Entry::getKey, 
						Map.Entry::getValue, 
						(e1, e2) -> e1, 
						LinkedHashMap::new
						));
	}

	@Override
	public List<Category> getSubCategory(Long menu_id) {
		List<Category> categorys = categoryRepository.findByMenuParent(menu_id);
		if(categorys == null || categorys.size() == 0) {
			Category category = categoryRepository.findOne(menu_id);
			categorys = categoryRepository.findByMenuParent(category.getMenuParent());
		} 
		return categorys;
	}

	@Override
	public Page<ProductCategory> getProuctList(Long parent, Long menu_id, int page, int sort) {
		List<Category> categorys = null;

		Category category = categoryRepository.findOne(menu_id);  

		int depth = category.getMenuDepth();

		if(depth == 2) {  // 최상단
			categorys = categoryRepository.findByMenuParent(menu_id); // Depth 3

			List<Long> ctgList = new ArrayList<>();
			for(Category ctg:categorys) {
				ctgList.add(ctg.getMenuId());
			}
			categorys.addAll( categoryRepository.findByMenuParentIn(ctgList));  // Depth 4
		} else if(depth == 3) { 
			categorys = categoryRepository.findByMenuParent(menu_id);  // Depth 4 List
			if(categorys == null || categorys.size() == 0) {
				categorys = new ArrayList<>();
				categorys.add(category);
			}
		} else {
			categorys = new ArrayList<>();
			categorys.add(category);
		}
		//		if(menu_id == null) {
		//			categorys = categoryRepository.findByMenuParent(parent);
		//		} else {
		//			categorys = categoryRepository.findByMenuParentAndMenuId(parent, menu_id);
		//		}


		Sort mSort  = null;
		switch (sort) {
		case 1:	//인기순
			mSort = new Sort(Direction.DESC, "product.viewCount");
			break;
		case 2: //리뷰순
			mSort = new Sort(Direction.DESC, "product.reviewCount");
			break;
		case 3: //낮은가격
			mSort = new Sort(Direction.ASC, "product.salePrice");
			break;
		case 4: //높은가격
			mSort = new Sort(Direction.DESC, "product.salePrice");
			break;
		case 5: //신규
			mSort = new Sort(Direction.DESC, "product.regDate");
			break;
		default:
			mSort = new Sort(Direction.DESC, "regDate");
			break;
		}
		//1:인기순 2:리뷰순 3:낮은가격 4:높은가격, 5:신규
		PageRequest pageRequest = new PageRequest(page, BeautyConstants.PAGE_SIZE_PRODUCT, mSort); //현재페이지, 조회할 페이지수, 정렬정보
		Page<ProductCategory> productList = productCategoryRepository.findAll(ProductCategorySpecification.categoryIn(categorys), pageRequest);
		return productList;
	}

	@Override
	public Page<Product> getProuctBrandList(Long brand_id, int page, int sort) {
		Sort mSort  = null;
		switch (sort) {
		case 1:	//인기순
			mSort = new Sort(Direction.DESC, "viewCount");
			break;
		case 2: //리뷰순
			mSort = new Sort(Direction.DESC, "reviewCount");
			break;
		case 3: //낮은가격
			mSort = new Sort(Direction.ASC, "salePrice");
			break;
		case 4: //높은가격
			mSort = new Sort(Direction.DESC, "salePrice");
			break;
		case 5: //신규
			mSort = new Sort(Direction.DESC, "regDate");
			break;
		default:
			mSort = new Sort(Direction.DESC, "regDate");
			break;
		}
		PageRequest pageRequest = new PageRequest(page, BeautyConstants.PAGE_SIZE_PRODUCT, mSort); //현재페이지, 조회할 페이지수, 정렬정보

		Brand brand = brandRepository.findOne(brand_id);
		Page<Product> productList = productRepository.findByBrandAndStopSelling(brand, 0, pageRequest);
		return productList;
	}

	@Override
	public ProductResponse getDetail(Long product_id) {
		Product product = productRepository.findOne(product_id);
		if(product == null) {
			return ProductResponse.of("상품이 존재하지 않습니다.", ResultCode.FAIL, null, null, null, null);
		}
		List<ProductItem> productItem = productItemRepository.findByProductAndStopSelling(product ,0);

		ProductTimeSale timeSale = productTimeSaleRepository.findByProduct(product);
		int star5 = 0;
		int star4 = 0;
		int star3 = 0;
		int star2 = 0;
		int star1 = 0;

		for(Review review:product.getReview()) {
			if(review.getStar() == 5) {
				star5 += 1;
			} else if(review.getStar() == 4) {
				star4 += 1;
			} else if(review.getStar() == 3) {
				star3 += 1;
			} else if(review.getStar() == 2) {
				star2 += 1;
			} else if(review.getStar() == 1) {
				star1 += 1;
			}
		}

		product.setStar5(star5);
		product.setStar4(star4);
		product.setStar3(star3);
		product.setStar2(star2);
		product.setStar1(star1);

		product.setViewCount(product.getViewCount()+1);

		productRepository.save(product);

		List<ProductCategory> pcList = productCategoryRepository.findByProduct(product);

		List<Long> category = new ArrayList<>();
		for(ProductCategory pc:pcList) {
			category.add(pc.getCategory().getMenuId());
		}

		Specifications<DetailTopContent> specs = Specifications.where(DetailTopSpecification.detailTop(product.getBrand(), product, category));

		List<DetailTopContent> dtcList = detailTopContentRepository.findAll(specs);

		List<DetailTop> dcList = new ArrayList<>();
		for(DetailTopContent dtc: dtcList) {
			dcList.add(dtc.getDetailTop());
		}
		List<DetailTop> newArrList = null;
		if(dcList.size() > 0) {
			// HashSet 데이터 형태로 생성되면서 중복 제거됨
			HashSet hs = new HashSet(dcList);

			//ArrayList 형태로 다시 생성
			newArrList = new ArrayList<>(hs);
		}
		//		List<Product> prod = productRepository.findTop6ByOrderByScoreDesc();

		return ProductResponse.of("success", ResultCode.SUCCESS, product, productItem, timeSale, newArrList);
	}


	@Override
	public BestResponse getProuctNewList() {
		List<MainFirstBanner> firstList = mainFirstBannerRepository.findByShowTypeAndShowYn(3, "Y");
		Date ago7Day =  DateUtil.getDayAgoDate(-30);
		List<Product> list = productRepository.findByRegDateAfterAndBoxAndStopSellingOrderByRegDateDesc(ago7Day, 0, 0);
		return BestResponse.of("success", ResultCode.SUCCESS, firstList, list);
	}

	@Override
	public BestResponse getProductBest() {
		List<MainFirstBanner> firstList = mainFirstBannerRepository.findByShowTypeAndShowYn(2, "Y");
		List<Product> list = productRepository.findTop100ByBoxAndStopSellingOrderByScoreDesc(0, 0);
		return BestResponse.of("success", ResultCode.SUCCESS, firstList, list);
	}

	@Override
	public List<ProductTimeSale> getProductTime() {
		return productTimeSaleRepository.findAll(new Sort(Direction.DESC, "regDate"));
	}

	@Override
	public List<Product> getProductBox() {
		return productRepository.findByBoxOrderByRegDateDesc(1);
	}

	@Override
	public List<Product> getProductSearch(String search) {
		List<Brand> brand = brandRepository.findByBrandNameContainingAndBrandVisible(search, "Y");
		Specifications<Product> specs = Specifications.where(ProductSpecification.likeSearch(search, brand));
		return productRepository.findAll(specs);
	}


}
