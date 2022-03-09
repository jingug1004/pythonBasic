package com.beauty.admin.service;

import java.util.List;

import com.beauty.entity.DataTables;
import com.beauty.entity.DeliveryCompany;
import com.beauty.response.CommonResponse;
import com.beauty.response.DataTablesResult;

public interface AdminDeliveryCompanyService {

	public List<DeliveryCompany> companyList();
	public DataTablesResult list(DataTables input );
	public CommonResponse add(String name, String code);
	public CommonResponse delete(List<DeliveryCompany> deliveryCompanys);
	public CommonResponse update(DeliveryCompany deliveryCompany);
}
