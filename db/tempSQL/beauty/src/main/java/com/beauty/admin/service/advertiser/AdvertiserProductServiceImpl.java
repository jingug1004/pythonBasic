package com.beauty.admin.service.advertiser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.beauty.entity.DataTables;
import com.beauty.entity.Product;
import com.beauty.entity.ProductItem;
import com.beauty.entity.User;
import com.beauty.repository.ProductCategoryRepository;
import com.beauty.repository.ProductItemRepository;
import com.beauty.repository.ProductRepository;
import com.beauty.repository.UserRepository;
import com.beauty.response.CommonResponse;
import com.beauty.response.DataTablesResult;
import com.beauty.response.result.ResultCode;
import com.beauty.security.model.UserContext;

import lombok.Getter;
import lombok.Setter;

@Service
public class AdvertiserProductServiceImpl implements AdvertiserProductService {
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
	private ProductCategoryRepository productCategoryRepository;

	@Override
	public DataTablesResult list(DataTables input) {
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserContext userContext = (UserContext) auth.getPrincipal();
		User user = userRepository.findOne(userContext.getUserId());
		
		PageRequest pageRequest = new PageRequest(input.getStart(), input.getLength(), input.getSort("regDate")); //현재페이지, 조회할 페이지수, 정렬정보
		Page<Product> data = productRepository.findBySeller(user, pageRequest);

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

	@Override
	public List<Product> getProductAll() {
		return productRepository.findByStopSelling(0);
	}

	@Override
	public CommonResponse addCount(List<ProductItem> items, int count) {
		for(ProductItem item:items) {
			item.setItemCnt(item.getItemCnt() + count);
			item.setTotalItemCnt(item.getTotalItemCnt() + count);
		}
		productItemRepository.save(items);
		return CommonResponse.of("정상 처리 되었습니다.", ResultCode.SUCCESS);	
	}

}
