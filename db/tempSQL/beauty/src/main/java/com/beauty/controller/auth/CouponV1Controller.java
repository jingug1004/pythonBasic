package com.beauty.controller.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.beauty.response.CouponUserResponse;
import com.beauty.security.auth.JwtAuthenticationToken;
import com.beauty.service.CouponService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping(value="/api/v1/coupon")
public class CouponV1Controller {
	public final String TAG = "쿠폰 - 로그인 필요";
	
	@Autowired
	private CouponService couponService;
	
    /**
     * 내 쿠폰 목록
     */
    @ApiOperation(value = "내 쿠폰 목록", notes = "내 쿠폰 목록", response = CouponUserResponse.class, tags={TAG, })
    @RequestMapping(value="/my", method=RequestMethod.POST)
    public @ResponseBody CouponUserResponse my(JwtAuthenticationToken token) {
        return couponService.myCoupon(token);
    }
   
    /**
     * 사용가능 쿠폰 목록
     */
    @ApiOperation(value = "사용가능 쿠폰 목록", notes = "내 쿠폰 목록", response = CouponUserResponse.class, tags={TAG, })
    @RequestMapping(value="/my_coupon", method=RequestMethod.POST)
    public @ResponseBody CouponUserResponse coupon(JwtAuthenticationToken token) {
        return couponService.coupon(token);
    }
    
    
    /**
     * 쿠폰 받기
     */
    @ApiOperation(value = "쿠폰 받기", notes = "쿠폰 받기", response = CouponUserResponse.class, tags={TAG, })
    @RequestMapping(value="/use", method=RequestMethod.PUT)
    public @ResponseBody CouponUserResponse couponDown(JwtAuthenticationToken token, 
    		@ApiParam(name="c_id", value="쿠폰 ID", required = true) @RequestParam String c_id) {
        return couponService.couponDown(c_id, token);
    }
    
    
}
