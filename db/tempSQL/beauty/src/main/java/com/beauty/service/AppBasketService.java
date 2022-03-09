package com.beauty.service;

import java.util.List;

import com.beauty.response.AppBasketResponse;
import com.beauty.security.auth.JwtAuthenticationToken;

public interface AppBasketService {
//	
	public AppBasketResponse getBasketList(JwtAuthenticationToken token);
	public AppBasketResponse basketAdd(Long itemId, int itemCount, JwtAuthenticationToken token);
	public AppBasketResponse basketUpd(Long basketId, int itemCount, JwtAuthenticationToken token);
	public AppBasketResponse basketDelete(Long[] bpi_id, JwtAuthenticationToken token);
}
