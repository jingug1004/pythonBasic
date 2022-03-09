package com.beauty.entity;

import lombok.Data;

@Data
public class PaymentOrder {

	private String itemId;
	private int itemPrice;
	private int itemCnt;
}
