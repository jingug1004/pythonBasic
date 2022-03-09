package com.beauty.service;

import org.springframework.web.multipart.MultipartFile;

import com.beauty.response.CommonResponse;
import com.beauty.response.MainResponse;

public interface MainService {
	
	public MainResponse getMain();
	public CommonResponse uploadFile(MultipartFile uploadFile, String type);
}
