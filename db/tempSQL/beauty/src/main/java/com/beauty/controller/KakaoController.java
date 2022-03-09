package com.beauty.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.beauty.BeautyConstants;
import com.beauty.kakao.KakaoClient;
import com.beauty.kakao.response.AccessToken;

@Controller
public class KakaoController {
	
	@RequestMapping(value = "/kakao/oauth", method = RequestMethod.GET)
	public String oauth() {
		String url = "https://kauth.kakao.com/oauth/authorize?client_id="+BeautyConstants.KAKO_API_KEY+"&redirect_uri="+BeautyConstants.KAKAO_REDIRECT_URL+"&response_type=code";
		return "redirect:"+url;
	}
	
	@RequestMapping(value = "/kakao/auth", method = RequestMethod.GET)
	public String auth(@RequestParam("code") String code) {
		KakaoClient client = new KakaoClient(BeautyConstants.KAKO_API_KEY);
		AccessToken token = client.getAuth("authorization_code", BeautyConstants.KAKAO_REDIRECT_URL, code);
		return "redirect:http://localhost:8000/kakao/oauth?access_token="+token.getAccess_token();
	}
	 
}
