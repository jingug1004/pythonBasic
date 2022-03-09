package com.beauty.controller;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.beauty.response.PaymentResponse;
import com.beauty.security.auth.JwtAuthenticationToken;
import com.beauty.service.PaymentService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value="/api/payment")
public class PaymentController {
	public final String TAG = "결제 검증 및 등록 - 로그인시 해당 API 사용";
	
	@Autowired
	private PaymentService paymentService;
	
	/**
	 * 상품 구매완료후 호출
	 * imp_uid : "",
		merchant_uid :"",
	 * @return
	 * 
	 */
	@ApiOperation(value = "아임포트 결제", notes = "결제 직후 Notification URL", response = PaymentResponse.class, tags={TAG, })
	@RequestMapping(value="/complete", method=RequestMethod.POST)
	public @ResponseBody String complate(
			@RequestBody String data,
			JwtAuthenticationToken token) {
		//"imp_uid":"imp_1234567890","merchant_uid":"merchant_1234567890","status":"ready"
//		System.out.println("COMPLETE : " + data);
		JSONParser parser = new JSONParser();
		try {
			Object obj = parser.parse(data);
			JSONObject jsonObj = (JSONObject) obj;

			String impUid = jsonObj.get("imp_uid").toString();
			String merId = jsonObj.get("merchant_uid").toString();
			String stauts = jsonObj.get("status").toString();
			
			return paymentService.iamportComplete(impUid, merId, stauts);
		}catch ( ParseException e ){
			return "failed";
		}

	}    
	
}
