package com.beauty.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.beauty.response.EventCouponResponse;
import com.beauty.service.CouponService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping(value="/api/coupon")
public class CouponController {
	public final String TAG = "쿠폰";
	
	@Autowired
	private CouponService couponService;
	
    /**
     * 쿠폰 목록
     * @return
     */
    @ApiOperation(value = "목록", notes = "이벤트 쿠폰 목록", response = EventCouponResponse.class, tags={TAG, })
    @RequestMapping(value="/list", method=RequestMethod.GET)
    public @ResponseBody EventCouponResponse list(@ApiParam(name="e_id", value="이벤트 ID", required = true) @RequestParam Long e_id) {
    	return couponService.getList(e_id);
    }

}
