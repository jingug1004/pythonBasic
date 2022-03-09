package com.beauty.kakao;

import java.io.IOException;

import com.beauty.kakao.response.AccessToken;
import com.beauty.kakao.response.UserData;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class KakaoClient {

	
	private static final String API_URL = "https://kauth.kakao.com";
//	private static final String API_URL = "http://localhost";
	private String api_key = null;
	private Kakao kakao = null;
	
	public KakaoClient(String api_key) {
		this.api_key = api_key;
		this.kakao = this.create();
	}
	
	public AccessToken getAuth(String grant_type, String redirect_uri, String code) {
		Call<AccessToken> call = this.kakao.token("application/json;charset=UTF-8", grant_type, api_key, redirect_uri, code );
		
		try {
			Response<AccessToken> response = call.execute();
			if ( response.isSuccessful() ) {
//				System.out.println(response.body().getAccess_token());
//				System.out.println(response.body().getToken_type());
//				System.out.println(response.body().getAccess_token());
//				
				return response.body();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public UserData getUserData(String access_token) {
		Call<UserData> call = this.kakao.userData("Bearer "+access_token, "application/x-www-form-urlencoded;charset=utf-8");
		
		try {
			Response<UserData> response = call.execute();
			System.out.println(response.code());
			if ( response.isSuccessful() ) {
				System.out.println(response.body().getId());
				return response.body();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	protected Kakao create() {
		Retrofit retrofit = new Retrofit.Builder()
								.baseUrl(API_URL)
								.addConverterFactory(buildGsonConverter())
								.build();
		
		return retrofit.create(Kakao.class);
	}
	
	protected GsonConverterFactory buildGsonConverter() {
        GsonBuilder gsonBuilder = new GsonBuilder();

        // Adding custom deserializers
//        gsonBuilder.registerTypeAdapter(ScheduleEntry.class, new ScheduleEntrySerializer());
//        gsonBuilder.registerTypeAdapter(Schedule.class, new ScheduleEntrySerializer());
        Gson myGson = gsonBuilder.create();

        return GsonConverterFactory.create(myGson);
    }
	
}
