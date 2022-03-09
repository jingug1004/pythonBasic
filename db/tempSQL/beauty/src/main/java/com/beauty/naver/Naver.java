package com.beauty.naver;


import com.beauty.naver.response.NaverAccessToken;
import com.beauty.naver.response.NaverUserResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Naver {
//	@SerializedName("grant_type")
//	private String grant_type;
//	
//	@SerializedName("client_id")
//	private String client_id;
//	
//	@SerializedName("redirect_uri")
//	private String redirect_uri;
//	
//	@SerializedName("code")
//	private String code;
	//headers.append('Content-Type', 'application/x-www-form-urlencoded');
	@POST("/oauth2.0/token")
	Call<NaverAccessToken> token(
		@Query("client_id") String client_id,
		@Query("client_secret") String client_secret,
		@Query("grant_type") String grant_type,
		@Query("state") String state,
		@Query("code") String code);
	
	@GET("https://openapi.naver.com/v1/nid/me")
	Call<NaverUserResponse> userData(
			@Header("Authorization") String auth
			);
}
