package com.beauty.admin.service.advertiser;

import java.util.List;
import java.util.Map;

import com.beauty.entity.DataTables;
import com.beauty.entity.Product;
import com.beauty.entity.ProductItem;
import com.beauty.response.CommonResponse;
import com.beauty.response.DataTablesResult;

public interface AdvertiserProductService {

	public DataTablesResult list(DataTables input);
	public Map<String, Object> getProduct(String productId);
	
	public List<Product> getProductAll();
	
	public CommonResponse seller(List<Product> products, int seller);
	
	public CommonResponse addCount(List<ProductItem> items, int count);
}
