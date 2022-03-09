package com.beauty.service;

import com.beauty.response.WishListResponse;
import com.beauty.security.auth.JwtAuthenticationToken;

public interface WishListService {
	
	public WishListResponse list(JwtAuthenticationToken token);
	public WishListResponse add(Long productId, JwtAuthenticationToken token);
	public WishListResponse delete(Long wishId, JwtAuthenticationToken token);
	public WishListResponse check(Long productId, JwtAuthenticationToken token);
}
