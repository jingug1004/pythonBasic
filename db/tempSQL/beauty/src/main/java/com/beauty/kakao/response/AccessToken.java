package com.beauty.kakao.response;

import com.google.gson.annotations.SerializedName;

public class AccessToken {

	@SerializedName("access_token")
	String access_token;
	
	@SerializedName("token_type")
	String token_type;
	
	@SerializedName("refresh_token")
	String refresh_token;
	
	@SerializedName("expires_in")
	int expires_in;
	
	@SerializedName("scope")
	String scope;


	public String getAccess_token() {
		return access_token;
	}

	public String getToken_type() {
		return token_type;
	}

	public String getRefresh_token() {
		return refresh_token;
	}

	public int getExpires_in() {
		return expires_in;
	}

	public String getScope() {
		return scope;
	}
	
	
}
