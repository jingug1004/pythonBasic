package com.beauty.controller.auth;

import java.util.List;

import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.beauty.entity.OrderTotal;
import com.beauty.entity.OrderValid;
import com.beauty.entity.PaymentItem;
import com.beauty.entity.PaymentOrder;
import com.beauty.entity.Payments;
import com.beauty.response.OrderResponse;
import com.beauty.response.PaymentResponse;
import com.beauty.security.auth.JwtAuthenticationToken;
import com.beauty.service.PaymentService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping(value="/api/v1/payment")
public class PaymentV1Controller {
	public final String TAG = "결제 검증 및 등록 - 로그인시 해당 API 사용";

	public final String PAYMENT_PARAM = "<pre>{<br/>"
		//+ "		\"imp_uid\" : \"string\",			// 아임포트ID<br/>"
		//+ "		\"merchant_uid\" : \"string\",		// 가맹점ID<br/>"
		+ "		\"total_payment\" : 0,					// 결제 예정 금액<br/>"	
		+ "		\"coupon_dis\" : 0,					// 중복쿠폰 할인금액 (기본값 0)<br/>"
		+ "		\"point_dis\" : 0,					// 포인트 사용금액 (기본값 0)<br/>"
		+ "		\"coupon_id\" : \"string\",					// 중복쿠폰ID<br/>"
		+ "		\"normal_coupon_id\" : \"string\",					// 일반쿠폰ID<br/>"
		+ "		\"normal_coupon_dis\" : 0,					// 일반쿠폰 할인금액<br/>"
		+ "		\"name\" : \"string\",					// 주문명<br/>"
		+ "		\"note\" : \"string\",					// 배송요청사항<br/>"
		+ "		\"refund_holder\" : \"string\",					// 예금주<br/>"
		+ "		\"refund_bank\" : \"string\",					// 은행코드<br/>"
		+ "		\"refund_account\" : \"string\",					// 계좌번호<br/>"
		+ "		\"item_list\" :[				// 구매목록<br/>"
		+ "			{<br/>"
		+ "				\"item_id\" : 0,		// 상품 ITEM ID<br/>"
		+ "				\"item_count\" : 0,		// 상품 구매 수<br/>"	
		+ "				\"delivery_pay\":0			// 배송비<br/>"
		+ "				\"delivery_type\":0			// 0:묶음배송  1:무료배송  2:배송지불<br/>"
		+ "			}, ....<br/>"
		+ "		]<br/>"
		+ "}</pre>";
	
	
	@Autowired
	private PaymentService paymentService;
	
	
	@ApiOperation(value = "결제 준비", notes = "주문결제", response = OrderTotal.class,  tags={TAG, })
	@RequestMapping(value="/order", method=RequestMethod.POST)
	public @ResponseBody OrderTotal order(
			@RequestBody List<PaymentOrder> data, JwtAuthenticationToken token) throws ParseException {
		return paymentService.paymentOrder(data, token);

	}    
	
	@ApiOperation(value = "주문결제 정보", notes = "주문결제", response = OrderResponse.class,  tags={TAG, })
	@RequestMapping(value="/order", method=RequestMethod.GET)
	public @ResponseBody OrderResponse getOrder(
			@RequestParam String orderId, JwtAuthenticationToken token) throws ParseException {
		OrderResponse orderTotal =  paymentService.getOrderTotal(orderId, token);
		return orderTotal;

	}    
	
	@ApiOperation(value = "결제 정보 확인", notes = "결제정보 확인", response = PaymentResponse.class, tags={TAG, })
	@RequestMapping(value="/order_valid", method=RequestMethod.POST)
	public @ResponseBody PaymentResponse orderValid(
			@RequestBody OrderValid orderValid,
			JwtAuthenticationToken token) {

		return paymentService.orderValid(orderValid, token);

	}  
	
	/**
	 * 상품 구매완료후 호출
	 * imp_uid : "",
		merchant_uid :"",
	 * @return
	 * 
	 */
	@ApiOperation(value = "결제 완료", notes = "결제 완료", response = PaymentResponse.class, tags={TAG, })
	@RequestMapping(value="/order_complete", method=RequestMethod.POST)
	public @ResponseBody PaymentResponse complate(
			@ApiParam(name="payment_id", value="결제 ID", required = true) @RequestParam String payment_id,
			@ApiParam(name="imp_uid", value="아임포트DI", required = true) @RequestParam String imp_uid, 
			@ApiParam(name="merchant_uid", value="가맹점ID", required = true) @RequestParam String merchant_uid,
			JwtAuthenticationToken token) {

		return paymentService.complete(payment_id, imp_uid, merchant_uid, token);

	}   
	
	/**
	 * 결제 정보 확인 호출
	 * @return
	 * 
	 */
	@ApiOperation(value = "결제 정보 확인", notes = PAYMENT_PARAM, response = PaymentResponse.class, tags={TAG, })
	@RequestMapping(value="/vailidation", method=RequestMethod.POST)
	public @ResponseBody PaymentResponse payment(
			@RequestBody String data, JwtAuthenticationToken token) {

		return paymentService.verifyPayment(data, token);

	}    
	
	@ApiOperation(value = "결제 정보 확인", notes = PAYMENT_PARAM, response = PaymentResponse.class, tags={TAG, })
	@RequestMapping(value="/vailidation_v2", method=RequestMethod.POST)
	public @ResponseBody PaymentResponse paymentV2(
			@RequestBody String data, JwtAuthenticationToken token) {

		return paymentService.verifyPaymentV2(data, token);

	}  

	
	/**
	 * 상품 구매완료후 호출
	 * imp_uid : "",
		merchant_uid :"",
	 * @return
	 * 
	 */
	@ApiOperation(value = "결제 완료", notes = "결제 완료", response = PaymentResponse.class, tags={TAG, })
	@RequestMapping(value="/complete", method=RequestMethod.POST)
	public @ResponseBody PaymentResponse complate(
			@ApiParam(name="payment_id", value="결제 ID", required = true) @RequestParam String payment_id,
			@ApiParam(name="imp_uid", value="아임포트DI", required = true) @RequestParam String imp_uid, 
			@ApiParam(name="merchant_uid", value="가맹점ID", required = true) @RequestParam String merchant_uid,
			@ApiParam(value="이름") @RequestParam(name="name", required=true) String name,
    		@ApiParam(value="전화번호") @RequestParam(name="phone", required=true) String phone,
    		@ApiParam(value="우편번호") @RequestParam(name="zipcode", required=true) String zipcode,
    		@ApiParam(value="주소1") @RequestParam(name="address1", required=true) String address1,
    		@ApiParam(value="주소2") @RequestParam(name="address2", required=true) String address2,
			JwtAuthenticationToken token) {

		return paymentService.complete(payment_id, imp_uid, merchant_uid, name, phone, zipcode, address1, address2, token);

	}    
	
	/**
	 * 결제 내역
	 */
	@ApiOperation(value = "결제 내역", notes = "결제 내역.", response = PaymentResponse.class, tags={TAG, })
	@RequestMapping(value="/payment", method=RequestMethod.POST)
	public @ResponseBody Payments getPayment(
			@ApiParam(name="payment_id", value="결제 ID", required = true) @RequestParam String payment_id,
			JwtAuthenticationToken token) {

		return paymentService.getPayments(payment_id);
	}    
	
	/**
	 * 결제 취소
	 */
	@ApiOperation(value = "결제 취소", notes = "결제 취소를 한다.", response = PaymentResponse.class, tags={TAG, })
	@RequestMapping(value="/cancel", method=RequestMethod.POST)
	public @ResponseBody PaymentResponse cancel(
			@ApiParam(name="payment_id", value="결제 ID", required = true) @RequestParam String payment_id,
			JwtAuthenticationToken token) {

		return paymentService.cancel(payment_id, token);

	}    
	
	/**
	 * 구매 내역
	 */
	@ApiOperation(value = "구매내역", notes = "구매내역을 가져온다.", response = Payments.class, responseContainer="List", tags={TAG, })
	@RequestMapping(value="/list", method=RequestMethod.POST)
	public @ResponseBody List<Payments> payment(JwtAuthenticationToken token) {

		return paymentService.payment(token);

	}
	
	/**
	 * 구매 내역 상세
	 */
	
	@ApiOperation(value = "구매내역", notes = "구매 상세내역을 가져온다.", response = PaymentItem.class, responseContainer="List", tags={TAG, })
	@RequestMapping(value="/item_list", method=RequestMethod.POST)
	public @ResponseBody List<PaymentItem> paymentItem(
			@ApiParam(name="payment_id", value="결제 ID", required = true) @RequestParam String payment_id,
			JwtAuthenticationToken token) {

		return paymentService.paymentItem(payment_id, token);

	}    
}
