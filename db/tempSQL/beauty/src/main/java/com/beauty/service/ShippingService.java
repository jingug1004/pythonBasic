package com.beauty.service;

import java.util.List;

import com.beauty.entity.Shipping;
import com.beauty.entity.User;
import com.beauty.response.ShippingResponse;
import com.beauty.security.auth.JwtAuthenticationToken;

public interface ShippingService {
	
	public List<Shipping> list(JwtAuthenticationToken token);
	public ShippingResponse info(JwtAuthenticationToken token);
	public ShippingResponse save(String name, String phone,String zipcode, String address1, String address2, String def_addr, JwtAuthenticationToken token);
	public ShippingResponse save(String name, String phone,String zipcode, String address1, String address2, String def_addr, User user);
	
}
