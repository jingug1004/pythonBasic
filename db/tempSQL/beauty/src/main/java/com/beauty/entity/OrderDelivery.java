package com.beauty.entity;

import lombok.Data;

@Data
public class OrderDelivery {

	private Long productId;
	private Long itemId;
	//@ApiModelProperty(value = "xxx원 이상 무료배송")
	private int freeDelivery;
	//@ApiModelProperty(value = "배송비")
	private int deliveryPrice = 0;

	// 브랜드별 금액
	private int price;
}
