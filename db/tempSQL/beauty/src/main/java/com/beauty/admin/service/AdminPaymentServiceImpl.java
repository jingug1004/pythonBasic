package com.beauty.admin.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.beauty.entity.DataTables;
import com.beauty.entity.PaymentItem;
import com.beauty.entity.Payments;
import com.beauty.repository.PaymentItemRepository;
import com.beauty.repository.PaymentRepository;
import com.beauty.response.DataTablesResult;

@Service
public class AdminPaymentServiceImpl implements AdminPaymentService {
	
	@Autowired
	private PaymentRepository paymentRepository;

	@Autowired
	private PaymentItemRepository paymentItemRepository;

	@Override
	public DataTablesResult list(DataTables input, String type ) {
		String status = "ready";
		 //ready:미결제, paid:결제완료, cancelled:결제취소, failed:결제실패
		if(type.equals("P")) {
			status = "paid";
		} else if(type.equals("C")) {
			status = "cancelled";
		} else if(type.equals("F")) {
			status = "failed";
		}
		PageRequest pageRequest = new PageRequest(input.getStart(), input.getLength(), input.getSort("regDate")); //현재페이지, 조회할 페이지수, 정렬정보
		Page<Payments> payments = paymentRepository.findByStatus(status, pageRequest);
		
		DataTablesResult result = new DataTablesResult();
		result.setDraw(input.getDraw());
		result.setRecordsTotal(payments.getContent().size());
		result.setRecordsFiltered(payments.getTotalElements());
		result.setData(payments.getContent());
		
		return result;
	}
	
	@Override
	public DataTablesResult itemList(DataTables input, String paymentId) {
		Payments payment = paymentRepository.findOne(paymentId);
		List<PaymentItem> paymentItem = paymentItemRepository.findByPayments(payment);

		DataTablesResult result = new DataTablesResult();
		result.setDraw(input.getDraw());
		result.setRecordsTotal(paymentItem.size());
		result.setRecordsFiltered(Long.valueOf(((Integer)paymentItem.size()).longValue()));
		result.setData(paymentItem);
//		
		
		return result;
	}
}
