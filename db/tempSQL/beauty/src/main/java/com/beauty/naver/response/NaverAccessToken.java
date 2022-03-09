package com.beauty.naver.response;

import com.google.gson.annotations.SerializedName;

public class NaverAccessToken {

	@SerializedName("access_token")
	String access_token;
	
	@SerializedName("refresh_token")
	String refresh_token;
	
	@SerializedName("token_type")
	String token_type;
	
	@SerializedName("expires_in")
	String expires_in;

	public String getAccess_token() {
		return access_token;
	}

	public String getRefresh_token() {
		return refresh_token;
	}

	public String getToken_type() {
		return token_type;
	}

	public String getExpires_in() {
		return expires_in;
	}
	
	
}
