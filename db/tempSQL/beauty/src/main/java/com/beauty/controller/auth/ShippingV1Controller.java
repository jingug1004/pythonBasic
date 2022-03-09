package com.beauty.controller.auth;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.beauty.entity.Shipping;
import com.beauty.response.ShippingResponse;
import com.beauty.security.auth.JwtAuthenticationToken;
import com.beauty.service.ShippingService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping(value="/api/v1/shipping")
public class ShippingV1Controller {
	public final String TAG = "배송지 - 로그인 필요";
	
	@Autowired
	private ShippingService shippingService;
    
    /**
     * 배송지 등록&수정
     * 
     * @return
     */
    @ApiOperation(value = "배송지 등록&수정", notes = "배송지 등록 및 수정", response = ShippingResponse.class, tags={TAG, })
    @RequestMapping(value="/shipping", produces = { "application/json; charset=utf-8" }, method=RequestMethod.PUT)
    public @ResponseBody ShippingResponse shipping(
    		@ApiParam(value="이름") @RequestParam(name="name", required=true) String name,
    		@ApiParam(value="전화번호") @RequestParam(name="phone", required=true) String phone,
    		@ApiParam(value="우편번호") @RequestParam(name="zipcode", required=true) String zipcode,
    		@ApiParam(value="주소1") @RequestParam(name="address1", required=true) String address1,
    		@ApiParam(value="주소2") @RequestParam(name="address2", required=true) String address2,
    		JwtAuthenticationToken token) {
    	
    	return shippingService.save(name, phone, zipcode, address1, address2, "Y", token);
    }
    
    /**
     * 배송지 정보
     * 
     * @return
     */
    @ApiOperation(value = "배송지 정보", notes = "기본 배송지 정보", response = ShippingResponse.class,tags={TAG, })
    @RequestMapping(value="/shipping/info", produces = { "application/json; charset=utf-8" }, method=RequestMethod.GET)
    public @ResponseBody ShippingResponse shippingInfo(JwtAuthenticationToken token) {
    	
    	return shippingService.info(token);
    }
    
    /**
     * 배송지 정보
     * 
     * @return
     */
    @ApiOperation(value = "배송지 목록", notes = "배송지 목록", response = Shipping.class,  responseContainer="List", tags={TAG, })
    @RequestMapping(value="/shipping/list", produces = { "application/json; charset=utf-8" }, method=RequestMethod.GET)
    public @ResponseBody List<Shipping> shippingList(JwtAuthenticationToken token) {
    	return shippingService.list(token);
    } 
}
