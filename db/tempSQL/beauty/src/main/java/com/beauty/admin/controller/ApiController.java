package com.beauty.admin.controller;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.text.ParseException;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.beauty.BeautyConstants;
import com.beauty.common.DateUtil;
import com.beauty.entity.Payments;
import com.beauty.kakao.KakaoClient;
import com.beauty.kakao.response.AccessToken;
import com.beauty.naver.NaverClient;
import com.beauty.naver.response.NaverAccessToken;
import com.beauty.naver.response.NaverUserResponse;
import com.beauty.response.PaymentResponse;
import com.beauty.service.PaymentService;

import io.swagger.annotations.ApiOperation;

/**
 * End-point for retrieving logged-in user details.
 * 
 * @author vladimir.stankovic
 *
 * Aug 4, 2016
 */
@Controller
@RequestMapping(value="/inapp")
public class ApiController {
	public final String TAG = "회원";
	
	@Autowired
	private PaymentService paymentService;
	
    @RequestMapping(value = "/kakao/oauth", method = RequestMethod.GET)
	public String oauth() {
		String url = "https://kauth.kakao.com/oauth/authorize?client_id="+BeautyConstants.KAKO_API_KEY+"&redirect_uri="+BeautyConstants.KAKAO_REDIRECT_URL+"&response_type=code";
		return "redirect:"+url;
	}
	
	@RequestMapping(value = "/kakao/auth", method = RequestMethod.GET)
	public String auth(@RequestParam("code") String code) {
		KakaoClient client = new KakaoClient(BeautyConstants.KAKO_API_KEY);
		AccessToken token = client.getAuth("authorization_code", BeautyConstants.KAKAO_REDIRECT_URL, code);
		return "redirect:/kakao/oauth?access_token="+token.getAccess_token();
	}

	
	@CrossOrigin
	@RequestMapping(value="/daum/post", method=RequestMethod.GET)
	public String post() {
		return "/inapp/daum_post";
	}
	
	@RequestMapping(value = "/daum/result", method = RequestMethod.GET)
	public String daum(@RequestParam("zipcode") String zipcode, @RequestParam("address") String address) {
		return "redirect:http://digiserver.cafe24.com:10000/inapp/daum/zipcode?zipcode="+zipcode+"&address=" + address;
	}

	@RequestMapping(value = "/daum/zipcode", method = RequestMethod.GET)
	@ResponseBody
	public String daumResult() {
		return "success";
	} 
	
	@CrossOrigin
	@RequestMapping(value="/naver/login", method=RequestMethod.GET)
	public String naverLogin(HttpServletRequest request) {
		System.out.println("login");
		String redirectUrl = java.net.URLEncoder.encode(BeautyConstants.NAVER_REDIRECT_URL);
		String state = generateState();
		
		String url = "https://nid.naver.com/oauth2.0/authorize?client_id=" + BeautyConstants.NAVER_CLIENT_ID + "&response_type=code&redirect_uri=" + redirectUrl + "&state=" + state;
		request.getSession().setAttribute("state", state);
		return "redirect:"+url;
	}
	
	@RequestMapping(value = "/naver/callback", method = RequestMethod.GET)
	public String naverCallback(HttpServletRequest request) {
		NaverClient client = new NaverClient(BeautyConstants.NAVER_CLIENT_ID);
		NaverAccessToken accessToken = client.getAuth("authorization_code", request.getParameter("state"), request.getParameter("code"));
		NaverUserResponse userData = client.getUserData(accessToken.getAccess_token());
		return "redirect:http://digiserver.cafe24.com:10000/inapp/naver/result?id="+userData.getResponse().getId();
	}
	
	@CrossOrigin
	@RequestMapping(value = "/naver/result", method = RequestMethod.GET)
	@ResponseBody
	public String naver(Model model) {
		
		return "success";
	}
	
//	"https://nid.naver.com/oauth2.0/token?grant_type=delete&client_id=jyvqXeaVOVmV&client_secret=527300A0_COq1_XV33cf&access_token=c8ceMEJisO4Se7uGCEYKK1p52L93bHXLnaoETis9YzjfnorlQwEisqemfpKHUq2gY&service_provider=NAVER"
//	
//	ayment_id=" + paymentId + "&pay_method="+pay_method + "&buyer_email="+buyer_email
//            +"&buyer_name="+buyer_name+"&buyer_tel="+buyer_tel+"&buyer_addr1="
//            +buyer_addr1+"&buyer_addr2=" + buyer_addr2 + "&buyer_postcode=" + buyer_postcode;
	@CrossOrigin
	@RequestMapping(value = "/iamport", method = RequestMethod.GET)
	public String iamport(Model model, @RequestParam("payment_id") String paymendId, @RequestParam("pay_method") String pay_method,
			@RequestParam("buyer_email") String buyer_email,@RequestParam("buyer_name") String buyer_name,@RequestParam("buyer_tel") String buyer_tel,
			@RequestParam("buyer_addr1") String buyer_addr1,@RequestParam("buyer_addr2") String buyer_addr2,@RequestParam("buyer_postcode") String buyer_postcode) {
		Payments payment = paymentService.getPayments(paymendId);
		payment.setBuyer_addr(buyer_addr1 + " " + buyer_addr2);
		payment.setPay_method(pay_method);
		payment.setBuyer_email(buyer_email);
		payment.setBuyer_name(buyer_name);
		payment.setBuyer_tel(buyer_tel);
		payment.setBuyer_postcode(buyer_postcode);

		String vbankDate = DateUtil.getAfterDays(1);
		if(pay_method.equals("vbank")) {
			try {
				payment.setVbank_date(DateUtil.stringToDate(vbankDate, "yyyyMMddHHmm"));
			} catch (ParseException e) {
			}
		}
		paymentService.save(payment, buyer_name, buyer_tel, buyer_postcode, buyer_addr1, buyer_addr2 );
		model.addAttribute("payment", payment);
		model.addAttribute("vbankDate", vbankDate);
		return "/inapp/iamport";
	}
	
	@CrossOrigin
	@RequestMapping(value = "/iamport/result", method = RequestMethod.GET)
	@ResponseBody
	public String iamport(Model model) {
		
		return "success";
	}
	
	@CrossOrigin
	@ApiOperation(value = "아임포트 결제", notes = "결제 직후 Notification URL", response = PaymentResponse.class, tags={TAG, })
	@RequestMapping(value="/complete", method=RequestMethod.GET)
	public String complat2e(HttpServletRequest request) {
		String impSuccess = request.getParameter("imp_success");
		if(impSuccess.equals("true")) {
			String paymentId = request.getParameter("paymentId");
			String impUid = request.getParameter("imp_uid");
			String merchantUid = request.getParameter("merchant_uid");
			paymentService.complete(paymentId, impUid, merchantUid);
			return "redirect:http://digiserver.cafe24.com:10000/inapp/imaport/result?status=success";
		} else {
			return "redirect:http://digiserver.cafe24.com:10000/inapp/imaport/result?status=failed";
		}
	}    
	public String generateState()
	{
	    SecureRandom random = new SecureRandom();
	    return new BigInteger(130, random).toString(32);
	}

}
