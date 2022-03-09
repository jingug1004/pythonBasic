package com.beauty.admin.service.advertiser;

import java.util.List;

import com.beauty.entity.DataTables;
import com.beauty.entity.PaymentItem;
import com.beauty.response.CommonResponse;
import com.beauty.response.DataTablesResult;

public interface AdvertiserPaymentService {

	public DataTablesResult list(DataTables input, String type);
	public CommonResponse saveDelivery(List<PaymentItem> item, String code, String number);
	public CommonResponse saveConfirm(List<PaymentItem> item, int confirm);
	
}
