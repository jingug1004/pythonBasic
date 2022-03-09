package com.beauty.service;

import com.beauty.response.BasketResponse;
import com.beauty.security.auth.JwtAuthenticationToken;

public interface BasketService {
	
	public BasketResponse getBasketList(JwtAuthenticationToken token);
	public BasketResponse basketAdd(Long itemId, int itemCount, JwtAuthenticationToken token);
	public BasketResponse basketUpd(Long basketId, int itemCount, JwtAuthenticationToken token);
	public BasketResponse basketDelete(Long basketId, JwtAuthenticationToken token);
}
