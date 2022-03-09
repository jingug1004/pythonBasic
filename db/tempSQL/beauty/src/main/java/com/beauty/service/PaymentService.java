package com.beauty.service;

import java.util.List;

import com.beauty.entity.OrderTotal;
import com.beauty.entity.OrderValid;
import com.beauty.entity.PaymentItem;
import com.beauty.entity.PaymentOrder;
import com.beauty.entity.Payments;
import com.beauty.response.OrderResponse;
import com.beauty.response.PaymentResponse;
import com.beauty.security.auth.JwtAuthenticationToken;

public interface PaymentService {
	
	public OrderTotal paymentOrder(List<PaymentOrder> orderList, JwtAuthenticationToken token);
	public OrderResponse getOrderTotal(String orderId, JwtAuthenticationToken token);
	public PaymentResponse orderValid(OrderValid valid, JwtAuthenticationToken token);
	public PaymentResponse complete(String payment_id, String imp_uid, String merchant_uid, JwtAuthenticationToken token);
	
	public PaymentResponse verifyPayment(String data, JwtAuthenticationToken token);
	public PaymentResponse verifyPaymentV2(String data, JwtAuthenticationToken token);
	public PaymentResponse complete(String payment_id, String imp_uid, String merchant_uid, String name, String phone, String zipcode, String address1, String address2, JwtAuthenticationToken token);
	public PaymentResponse complete(String payment_id, String imp_uid, String merchant_uid);
	public PaymentResponse cancel(String payment_id, JwtAuthenticationToken token);
	
	public List<Payments> payment(JwtAuthenticationToken token);
	public List<PaymentItem> paymentItem(String payment_id, JwtAuthenticationToken token);
	
	public String iamportComplete(String imp_uid, String merchant_uid, String status);
	
	public Payments getPayments(String payment_id);
	public void save(Payments payment, String name, String phone, String zipcode, String address1, String address2);
}
