package com.beauty.response;

import java.util.Date;
import java.util.List;

import com.beauty.entity.Coupon;
import com.beauty.response.result.ResultCode;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

@ApiModel
public class CouponResponse {
	
	@ApiModelProperty(value = "200 성공 99 실패")
	@Getter 
	private final ResultCode resultCode;

	@ApiModelProperty(value = "성공시 success 실패시 실패 내용 확인")
	@Getter 
    private final String message;

	@Getter 
    private final Date timestamp;
    
	@ApiModelProperty(value = "쿠폰 목록")
	@Getter 
	private List<Coupon> couponList;
	
    protected CouponResponse(final String message, final ResultCode resultCode, final List<Coupon> couponList) {
        this.message = message;
        this.resultCode = resultCode;
        this.couponList = couponList;
        this.timestamp = new java.util.Date();
    }

    public static CouponResponse of(final String message, final ResultCode resultCode,  final List<Coupon> couponList) {
        return new CouponResponse(message, resultCode, couponList);
    }
}
