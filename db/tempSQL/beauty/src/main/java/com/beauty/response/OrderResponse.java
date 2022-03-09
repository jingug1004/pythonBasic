package com.beauty.response;

import java.util.Date;
import java.util.List;

import com.beauty.entity.CouponUser;
import com.beauty.entity.OrderTotal;
import com.beauty.entity.Shipping;
import com.beauty.response.result.ResultCode;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

@ApiModel
public class OrderResponse {

	@ApiModelProperty(value = "200 성공 99 실패 ")
	@Getter 
	private final ResultCode resultCode;

	@ApiModelProperty(value = "성공시 success 실패시 실패 내용 확인")
	@Getter 
    private final String message;

	@Getter 
    private final Date timestamp;
	
	@ApiModelProperty(value = "결제이름")
	@Getter 
	private final String username;
	
	@ApiModelProperty(value = "주문 결제")
	@Getter 
	private final OrderTotal orderTotal;
	
	@ApiModelProperty(value = "내포인트")
	@Getter 
	private final int myPoint;
	
	@ApiModelProperty(value = "최대 사용 가능 포인트")
	@Getter 
	private final int maxPoint;
	
	@ApiModelProperty(value = "중복 쿠폰")
	@Getter 
	private final  List<CouponUser> overlap_cp;
	
	@ApiModelProperty(value = "주소 목록")
	@Getter 
	private final  List<Shipping> shipping;
	
	protected OrderResponse(final String message, final ResultCode resultCode, final String username, 
			final OrderTotal orderTotal, final int myPoint, final int maxPoint, 
			final List<CouponUser> overlap_cp, final List<Shipping> shipping) {
        this.message = message;
        this.resultCode = resultCode;
        this.username = username;
        this.orderTotal = orderTotal;
        this.myPoint = myPoint;
        this.maxPoint = maxPoint;
        this.overlap_cp = overlap_cp;
        this.shipping = shipping;
        this.timestamp = new java.util.Date();
    }
	
    public static OrderResponse of(final String message, final ResultCode resultCode, final String username,  
    		final OrderTotal orderTotal, final int myPoint, final int maxPoint, 
			final List<CouponUser> overlap_cp, final List<Shipping> shipping) {
        return new OrderResponse(message, resultCode, username, orderTotal, myPoint, maxPoint, overlap_cp, shipping);
    }
}
