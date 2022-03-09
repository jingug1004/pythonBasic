package com.beauty.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.beauty.entity.Category;
import com.beauty.entity.Product;
import com.beauty.entity.ProductCategory;
import com.beauty.entity.ProductTimeSale;
import com.beauty.response.BestResponse;
import com.beauty.response.CategoryResponse;
import com.beauty.response.CategoryResponse2;
import com.beauty.response.ProductResponse;

public interface ProductService {
	
	public CategoryResponse getCategory();
	public CategoryResponse2 getCategory2();
	public List<Category> getSubCategory(Long menu_id);
	public Page<ProductCategory> getProuctList(Long parent, Long menu_id, int page, int sort);
	public Page<Product> getProuctBrandList(Long brand_id, int page, int sort);
	
	public ProductResponse getDetail(Long product_id);
	
	public BestResponse getProuctNewList();
	public BestResponse getProductBest();
	public List<ProductTimeSale> getProductTime();
	public List<Product> getProductBox();
	
	public List<Product> getProductSearch(String search);
}
