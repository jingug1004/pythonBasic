package com.beauty.kakao.request;

import com.google.gson.annotations.SerializedName;

public class KakaoAuthData {

	@SerializedName("grant_type")
	private String grant_type;
	
	@SerializedName("client_id")
	private String client_id;
	
	@SerializedName("redirect_uri")
	private String redirect_uri;
	
	@SerializedName("code")
	private String code;
	
	public KakaoAuthData(String grant_type, String client_id, String redirect_uri, String code) {
		this.grant_type = grant_type;
		this.client_id = client_id;
		this.redirect_uri = redirect_uri;
		this.code = code;
	}
	
}
