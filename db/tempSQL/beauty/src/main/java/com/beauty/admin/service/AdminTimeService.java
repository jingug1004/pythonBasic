package com.beauty.admin.service;

import java.util.List;

import com.beauty.entity.DataTables;
import com.beauty.entity.Product;
import com.beauty.entity.ProductTimeSale;
import com.beauty.response.CommonResponse;
import com.beauty.response.DataTablesResult;

public interface AdminTimeService {

	public DataTablesResult list(DataTables input );
	public DataTablesResult getProductList(DataTables input );
	public CommonResponse add(List<Product> timeSale, int type, int prodCount, String endDate);
	public CommonResponse delete(List<ProductTimeSale> timeSale);
}
