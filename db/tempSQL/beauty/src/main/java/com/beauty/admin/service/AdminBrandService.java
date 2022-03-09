package com.beauty.admin.service;

import java.util.List;

import org.json.simple.JSONObject;
import org.springframework.web.multipart.MultipartFile;

import com.beauty.entity.Brand;
import com.beauty.entity.DataTables;
import com.beauty.response.CommonResponse;
import com.beauty.response.DataTablesResult;

public interface AdminBrandService {

	public List<Brand> brandList();
	public Brand getBrand(Long brandId);
	public DataTablesResult list(DataTables input );
	public CommonResponse save(JSONObject data);
	public CommonResponse add(String brandName);
	public CommonResponse delete(List<Brand> brands, String flag);
	public CommonResponse update(Brand brand);
	
	public CommonResponse uploadThumb(MultipartFile image);
}
