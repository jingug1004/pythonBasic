package com.beauty.service;

import org.springframework.data.domain.Page;

import com.beauty.entity.Review;
import com.beauty.response.CommonResponse;
import com.beauty.response.MyReviewResponse;
import com.beauty.security.auth.JwtAuthenticationToken;

public interface ReviewService {
	
	public Page<Review> list(Long product_id, int photo, int page, int sort, JwtAuthenticationToken token);
	public CommonResponse add(Long product_id, String[] files,String content,int star, int skin_type, JwtAuthenticationToken token);
	public CommonResponse delete(Long reviewId, JwtAuthenticationToken token);
	
	public CommonResponse like(Long reviewId, JwtAuthenticationToken token);
	
	public MyReviewResponse myReview(JwtAuthenticationToken token);
}
