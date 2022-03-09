package com.beauty.service;

import com.beauty.response.PointResponse;
import com.beauty.security.auth.JwtAuthenticationToken;

public interface PointService {
	
	public PointResponse pointList(int page, JwtAuthenticationToken token);
	
}
