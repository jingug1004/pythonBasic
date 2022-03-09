package com.beauty.service;

import com.beauty.response.CommonResponse;
import com.beauty.response.QnaListResponse;
import com.beauty.security.auth.JwtAuthenticationToken;

public interface QnaService {

	public QnaListResponse qnaList(JwtAuthenticationToken token);
	public CommonResponse saveQna(Long orderId, String subject, String content,String qnaClass1, String qnaClass2,String[] files, JwtAuthenticationToken token);
	public QnaListResponse delete(Long qnaId, JwtAuthenticationToken token);
}
