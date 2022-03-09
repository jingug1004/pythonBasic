package com.beauty.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel
public class OrderValid {
	
	@ApiModelProperty(value = "결제 금액")
	@Getter @Setter
	private int totalPayment;
	
	@ApiModelProperty(value = "주문 ID")
	@Getter @Setter
	private String orderId;
	
	@ApiModelProperty(value = "사용 쿠폰 ID")
	@Getter @Setter
	private Long orderCpId;
	
	@ApiModelProperty(value = "사용 중복쿠폰 ID")
	@Getter @Setter
	private String overlapCpId;
	
	@ApiModelProperty(value = "사용 중복쿠폰 할인금액")
	@Getter @Setter
	private int overlapCpDis;
	
	@ApiModelProperty(value = "사용 포인트")
	@Getter @Setter
	private int usePoint; 
	
	@ApiModelProperty(value = "이름")
	@Getter @Setter
	private String name;
	
	@ApiModelProperty(value = "전화번호")
	@Getter @Setter
	private String phone;
	
	@ApiModelProperty(value = "우편번호")
	@Getter @Setter
	private String zipcode;
	
	@ApiModelProperty(value = "주소1")
	@Getter @Setter
	private String address1;
	
	@ApiModelProperty(value = "주소2")
	@Getter @Setter
	private String address2;
	
	@ApiModelProperty(value = "배송요청사항")
	@Getter @Setter
	private String memo;
	
	@ApiModelProperty(value = "환불계좌 예금주")
	@Getter @Setter
	private String refund_holder;
	
	@ApiModelProperty(value = "환불계좌 은행코드")
	@Getter @Setter
	private String refund_bank;	
	
	@ApiModelProperty(value = "환불계좌 계좌번호")
	@Getter @Setter
	private String refund_account;
	
}
