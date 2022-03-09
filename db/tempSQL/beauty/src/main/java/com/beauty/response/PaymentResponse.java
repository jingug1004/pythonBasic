package com.beauty.response;

import java.util.Date;

import com.beauty.entity.Payments;
import com.beauty.response.result.PaymentResultCode;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

@ApiModel
public class PaymentResponse {

	@ApiModelProperty(value = "200 성공 99 실패 201 포인트부족 ")
	@Getter 
	private final PaymentResultCode resultCode;

	@ApiModelProperty(value = "성공시 success 실패시 실패 내용 확인")
	@Getter 
    private final String message;

	@Getter 
    private final Date timestamp;
	
	@ApiModelProperty(value = "결제 정보")
	@Getter 
	private final Payments payment;
	
	protected PaymentResponse(final String message, final PaymentResultCode resultCode, final Payments payment) {
        this.message = message;
        this.resultCode = resultCode;
        this.payment = payment;
        this.timestamp = new java.util.Date();
    }
	
    public static PaymentResponse of(final String message, final PaymentResultCode resultCode, final Payments payment) {
        return new PaymentResponse(message, resultCode, payment);
    }
}
