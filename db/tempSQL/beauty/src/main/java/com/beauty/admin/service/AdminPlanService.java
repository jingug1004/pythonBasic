package com.beauty.admin.service;

import java.util.List;

import org.json.simple.JSONObject;

import com.beauty.entity.DataTables;
import com.beauty.entity.Plan;
import com.beauty.entity.PlanGroup;
import com.beauty.entity.PlanProduct;
import com.beauty.entity.Product;
import com.beauty.response.CommonResponse;
import com.beauty.response.DataTablesResult;

public interface AdminPlanService {

	public DataTablesResult list(DataTables input );
	public DataTablesResult groupList(DataTables input, Long pid);
	public DataTablesResult itemList(DataTables input, Long pgid);
	public List<Product> getProduct(Long pgid);
	
	public Plan getPlan(Long pid);
	
	public CommonResponse planStat(List<Plan> plans,  int stat);
	public CommonResponse groupStat(List<PlanGroup> groups,  int stat, Long pid);
	
	public CommonResponse save(JSONObject jsonObj);
	public CommonResponse groupSave(JSONObject jsonObj);
	public CommonResponse itemSave(JSONObject jsonObj);

	public CommonResponse deleteProd(List<PlanProduct> products);
	public CommonResponse deleteItem(List<PlanGroup> products);
	
	public String reorder(Long pid, int oldorder, int neworder);
}
