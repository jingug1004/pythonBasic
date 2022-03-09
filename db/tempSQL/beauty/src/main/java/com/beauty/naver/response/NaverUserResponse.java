package com.beauty.naver.response;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;

public class NaverUserResponse {
	
	@SerializedName("resultcode")
	@Getter
	String resultcode;
	
	@SerializedName("message")
	@Getter
	String message;
	
	
	@SerializedName("response")
	@Getter
	NaverUserData response;


	
}