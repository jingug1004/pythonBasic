package com.beauty.kakao.response;

import com.google.gson.annotations.SerializedName;

public class UserData {
//	  "id": 389499473,
//	  "properties": {
//	    "profile_image": "",
//	    "nickname": "숨크리에티브",
//	    "thumbnail_image": ""
//	  }
	
	
	@SerializedName("id")
	String id;

	public String getId() {
		return id;
	}
	
	
}
