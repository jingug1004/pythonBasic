package com.beauty.naver;

import java.io.IOException;

import com.beauty.BeautyConstants;
import com.beauty.naver.response.NaverAccessToken;
import com.beauty.naver.response.NaverUserResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NaverClient {

	
	private static final String API_URL = "https://nid.naver.com";
//	private static final String API_URL = "http://localhost";
	private Naver naver = null;
	
	public NaverClient(String api_key) {
		this.naver = this.create();
	}

	public NaverAccessToken getAuth(String grant_type, String state, String code) {
		Call<NaverAccessToken> call = this.naver.token(BeautyConstants.NAVER_CLIENT_ID, BeautyConstants.NAVER_SECRET_ID, grant_type, state, code);
		
		try {
			Response<NaverAccessToken> response = call.execute();
			if ( response.isSuccessful() ) {
				System.out.println(response.body().getAccess_token());
				System.out.println(response.body().getToken_type());
				System.out.println(response.body().getAccess_token());
				
				return response.body();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	
	public NaverUserResponse getUserData(String access_token) {
		Call<NaverUserResponse> call = this.naver.userData("Bearer "+access_token);
		
		try {
			Response<NaverUserResponse> response = call.execute();
			System.out.println(response.code());
			if ( response.isSuccessful() ) {
				System.out.println(response.body().getResponse().getId());
				System.out.println(response.body().getResponse().getGender());
				System.out.println(response.body().getResponse().getAge());
				
				return response.body();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	protected Naver create() {
		Retrofit retrofit = new Retrofit.Builder()
								.baseUrl(API_URL)
								.addConverterFactory(buildGsonConverter())
								.build();
		
		return retrofit.create(Naver.class);
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
