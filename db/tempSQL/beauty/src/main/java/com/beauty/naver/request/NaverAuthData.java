package com.beauty.naver.request;

import com.google.gson.annotations.SerializedName;

public class NaverAuthData {
	@SerializedName("client_id")
	private String client_id;
	
	@SerializedName("client_secret")
	private String client_secret;
	
	@SerializedName("grant_type")
	private String grant_type;
	
	@SerializedName("state")
	private String state;
	
	@SerializedName("code")
	private String code;
	
	public NaverAuthData(String client_id, String client_secret, String grant_type, String state, String code) {
		
		this.client_id = client_id;
		this.client_secret = client_secret;
		this.grant_type = grant_type;
		this.state = state;
		this.code = code;
	}
}
