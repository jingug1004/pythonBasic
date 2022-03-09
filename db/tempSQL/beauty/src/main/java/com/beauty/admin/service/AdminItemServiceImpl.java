package com.beauty.admin.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.beauty.entity.DataTables;
import com.beauty.entity.Product;
import com.beauty.entity.ProductItem;
import com.beauty.repository.ProductItemRepository;
import com.beauty.repository.ProductRepository;
import com.beauty.response.CommonResponse;
import com.beauty.response.DataTablesResult;
import com.beauty.response.result.ResultCode;

@Service
public class AdminItemServiceImpl implements AdminItemService {

	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private ProductItemRepository productItemRepository;

	@Override
	public DataTablesResult list(DataTables input, Long productId) {
		Product product = productRepository.findOne(productId);
		List<ProductItem> data = productItemRepository.findByProduct(product, input.getSort("regDate"));
		
		DataTablesResult result = new DataTablesResult();
		result.setDraw(input.getDraw());
		result.setRecordsTotal(data.size());
		result.setRecordsFiltered(Long.valueOf(((Integer)data.size()).longValue()));
		result.setData(data);
		
		return result;
	}
	
	@Override
	public CommonResponse seller(List<ProductItem> items, int seller) {
		for(ProductItem item:items) {
			item.setStopSelling(seller);
		}
		productItemRepository.save(items);
		if(seller == 0) {
			return CommonResponse.of("선택한 상품이 판매를 시작하였습니다.", ResultCode.SUCCESS);
		} else {
			return CommonResponse.of("선택한 상품의 판매가 중지되었습니다.", ResultCode.SUCCESS);	
		}
		
	}
	
	@Override
	public ProductItem getItem(String itemId) {
		if(itemId.indexOf("i_") > -1) {
			itemId = itemId.replaceAll("i_", "");
		}
		return productItemRepository.findOne(Long.parseLong(itemId));
	}

	@Override
	public List<ProductItem> getProductItem(Product product) {
		return productItemRepository.findByProduct(product);
	}
	
	@Override
	public CommonResponse save(ProductItem productItem, String productId) {
		if(productId.indexOf("p_") > -1) {
			productId = productId.replaceAll("p_", "");
		}
		
		Product product = productRepository.findOne(Long.parseLong(productId));
		product.setRegDate(new Date());
		productItem.setProduct(product);
		productItemRepository.save(productItem);
		productRepository.save(product);
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


}
