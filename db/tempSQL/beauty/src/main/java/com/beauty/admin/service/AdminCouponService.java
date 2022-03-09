package com.beauty.admin.service;

import java.util.List;

import org.json.simple.JSONObject;

import com.beauty.entity.Coupon;
import com.beauty.entity.DataTables;
import com.beauty.response.CommonResponse;
import com.beauty.response.DataTablesResult;

public interface AdminCouponService {

	public Coupon getCoupon(String coupon);
	public DataTablesResult list(DataTables input );
	public DataTablesResult dataList(DataTables input, String cpId);
	
	public CommonResponse save(JSONObject data);
	public CommonResponse download(List<Coupon> coupons, int flag);
	
	public CommonResponse auto(List<Coupon> coupons, int flag);
}
