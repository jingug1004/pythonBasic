package com.beauty.response;

import java.util.Date;
import java.util.List;

import com.beauty.entity.CouponUser;
import com.beauty.response.result.CouponResultCode;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

@ApiModel
public class CouponUserResponse {
	
	@ApiModelProperty(value = "200 성공, 99 실패, 10 종료, 20 수량없음, 30 이미받은쿠폰")
	@Getter 
	private final CouponResultCode resultCode;

	@ApiModelProperty(value = "성공시 success 실패시 실패 내용 확인")
	@Getter 
    private final String message;

	@Getter 
    private final Date timestamp;
    
	@ApiModelProperty(value = "내 쿠폰 목록")
	@Getter 
	private List<CouponUser> couponUserList;
	
    protected CouponUserResponse(final String message, final CouponResultCode resultCode, final List<CouponUser> couponUserList) {
        this.message = message;
        this.resultCode = resultCode;
        this.couponUserList = couponUserList;
        this.timestamp = new java.util.Date();
    }

    public static CouponUserResponse of(final String message, final CouponResultCode resultCode,  final List<CouponUser> couponUserList) {
        return new CouponUserResponse(message, resultCode, couponUserList);
    }
}
