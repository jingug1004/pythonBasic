package com.beauty.response;

import java.util.Date;

import com.beauty.entity.Shipping;
import com.beauty.response.result.ResultCode;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

@ApiModel
public class ShippingResponse {
	
	@ApiModelProperty(value = "200 성공 99 실패")
	@Getter 
	private final ResultCode resultCode;

	@ApiModelProperty(value = "성공시 success 실패시 실패 내용 확인")
	@Getter 
    private final String message;

	@Getter 
    private final Date timestamp;
    
	@ApiModelProperty(value = "기본배송지")
	@Getter 
	private Shipping shipping;
	
	
    protected ShippingResponse(final String message, final ResultCode resultCode, final Shipping shipping) {
        this.message = message;
        this.resultCode = resultCode;
        this.shipping = shipping;
        this.timestamp = new java.util.Date();
    }

    public static ShippingResponse of(final String message, final ResultCode resultCode,  final Shipping shipping) {
        return new ShippingResponse(message, resultCode, shipping);
    }
}
