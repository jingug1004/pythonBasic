package com.beauty.admin.service;

import java.util.List;

import org.springframework.web.bind.annotation.RequestParam;

import com.beauty.entity.DataTables;
import com.beauty.entity.Product;
import com.beauty.entity.ProductItem;
import com.beauty.response.CommonResponse;
import com.beauty.response.DataTablesResult;

public interface AdminItemService {

	public DataTablesResult list(DataTables input, Long productId);
	
	public ProductItem getItem(String itemId);
	public List<ProductItem> getProductItem(Product product);
	
	public CommonResponse seller(List<ProductItem> items, @RequestParam int seller);
	
	public CommonResponse save(ProductItem productItem, String productId);
	public CommonResponse delete(String itemId, int stop);
	
	
}
