package com.beauty.response;

import java.util.Date;
import java.util.List;

import com.beauty.entity.Payments;
import com.beauty.response.result.ResultCode;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

@ApiModel
public class PaymentsResponse {

	@ApiModelProperty(value = "200 성공 99 실패 ")
	@Getter 
	private final ResultCode resultCode;

	@ApiModelProperty(value = "성공시 success 실패시 실패 내용 확인")
	@Getter 
    private final String message;

	@Getter 
    private final Date timestamp;
	
	@ApiModelProperty(value = "결제 정보")
	@Getter 
	private final List<Payments> payment;
	
	@ApiModelProperty(value = "결제 정보")
	@Getter 
	private final  IamportResponse<Payment> importPayment;
	
	
	protected PaymentsResponse(final String message, final ResultCode resultCode, final List<Payments> payment, final IamportResponse<Payment> importPayment) {
        this.message = message;
        this.resultCode = resultCode;
        this.payment = payment;
        this.importPayment = importPayment;
        this.timestamp = new java.util.Date();
    }
	
    public static PaymentsResponse of(final String message, final ResultCode resultCode, final List<Payments> payment, final IamportResponse<Payment> importPayment) {
        return new PaymentsResponse(message, resultCode, payment, importPayment);
    }
}
