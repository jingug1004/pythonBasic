package com.beauty.admin.service;

import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;
import org.springframework.web.multipart.MultipartFile;

import com.beauty.entity.DataTables;
import com.beauty.entity.Product;
import com.beauty.response.CommonResponse;
import com.beauty.response.DataTablesResult;

public interface AdminProductService {

	public DataTablesResult list(DataTables input, Long box, Long brand, Long category);
	public Map<String, Object> getProduct(String productId);
	
	public List<Product> getProductAll();
	
	public CommonResponse seller(List<Product> products, int seller);
	public CommonResponse score(List<Product> products, int score);
	
	public CommonResponse save(JSONObject jsonObj);
	public CommonResponse delete(String itemId, int stop);
	
	public CommonResponse uploadImage(MultipartFile image, String thumb);
	
	public DataTablesResult topList(DataTables input);
	
	public CommonResponse topSave(JSONObject jsonObj);
}
