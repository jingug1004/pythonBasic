package com.beauty.service;

import com.beauty.entity.Coupon;
import com.beauty.entity.User;
import com.beauty.response.CouponUserResponse;
import com.beauty.response.EventCouponResponse;
import com.beauty.security.auth.JwtAuthenticationToken;

public interface CouponService {

	public EventCouponResponse getList(Long eId);
	public CouponUserResponse coupon(JwtAuthenticationToken token);
	public CouponUserResponse myCoupon(JwtAuthenticationToken token);
	public CouponUserResponse couponDown(String cId, JwtAuthenticationToken token);
	
	
	public void autoCoupon(User user, int auto);
	
	public boolean downCoupon(Coupon coupon, User user);
}
