package com.beauty.admin.service.advertiser;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.beauty.entity.DataTables;
import com.beauty.entity.DeliveryCompany;
import com.beauty.entity.PaymentItem;
import com.beauty.entity.ReviewWait;
import com.beauty.entity.ReviewWaitItem;
import com.beauty.entity.User;
import com.beauty.repository.DeliveryCompanyRepository;
import com.beauty.repository.PaymentItemRepository;
import com.beauty.repository.ReviewWaitItemRepository;
import com.beauty.repository.ReviewWaitRepository;
import com.beauty.repository.UserRepository;
import com.beauty.response.CommonResponse;
import com.beauty.response.DataTablesResult;
import com.beauty.response.result.ResultCode;
import com.beauty.security.model.UserContext;

@Service
public class AdvertiserPaymentServiceImpl implements AdvertiserPaymentService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PaymentItemRepository paymentItemRepository;
	
	@Autowired
	private DeliveryCompanyRepository deliveryCompanyRepository;
	
	@Autowired
	private ReviewWaitRepository reviewWaitRepository;
	
	@Autowired
	private ReviewWaitItemRepository reviewWaitItemRepository;
	
	@Override
	public DataTablesResult list(DataTables input, String type) {
		
//		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserContext userContext = (UserContext) auth.getPrincipal();
		User user = userRepository.findOne(userContext.getUserId());
		
		int confirm = 0;
		String status = "paid";
		if(type.equals("P")) {
			status = "paid";
		} else if(type.equals("C")) {
			status = "cancelled";
		} else if(type.equals("R")) {
			status = "ready";
		} else if(type.equals("D")) {
			confirm = 1;
		}
		PageRequest pageRequest = new PageRequest(input.getStart(), input.getLength(), input.getSort("regDate")); //현재페이지, 조회할 페이지수, 정렬정보
		Page<PaymentItem> paymentItem = null;
		if(type.equals("E")) {
			paymentItem = paymentItemRepository.findByUserAndStatusAndDeliveryNumberNotNull(user, status, pageRequest);
		} else {
			paymentItem = paymentItemRepository.findByUserAndStatusAndProdConfirmAndDeliveryNumberNull(user, status, confirm, pageRequest);
		}
		
		
		DataTablesResult result = new DataTablesResult();
		result.setDraw(input.getDraw());
		result.setRecordsTotal(paymentItem.getContent().size());
		result.setRecordsFiltered(paymentItem.getTotalElements());
		result.setData(paymentItem.getContent());
//		
		
		return result;
	}

//	@Override
//	public CommonResponse saveDelevery(PaymentItem item, String code, String number) {
//		DeliveryCompany dc =  deliveryCompanyRepository.findOne(code);
//		item = paymentItemRepository.findOne(item.getPiId());
//		item.setDeliveryName(dc.getName());
//		item.setDeliveryCode(dc.getCode());
//		item.setDeliveryNumber(number);
//		
//		paymentItemRepository.save(item);
//		return CommonResponse.of("저장되었습니다.", ResultCode.SUCCESS);
//	}

	@Override
	public CommonResponse saveDelivery(List<PaymentItem> item, String code, String number) {
		DeliveryCompany dc =  deliveryCompanyRepository.findOne(code);
		List<PaymentItem> items = new ArrayList<>();
		for(PaymentItem pi:item) {
			pi = paymentItemRepository.findOne(pi.getPiId());
			pi.setDeliveryName(dc.getName());
			pi.setDeliveryCode(dc.getCode());
			pi.setDeliveryNumber(number);
			
			items.add(pi);
		}
//		item = paymentItemRepository.findOne(item.getPiId());
//		item.setDeliveryName(dc.getName());
//		item.setDeliveryCode(dc.getCode());
//		item.setDeliveryNumber(number);
		
		paymentItemRepository.save(items);
		return CommonResponse.of("저장되었습니다.", ResultCode.SUCCESS);
	}

	@Override
	@Transactional
	public CommonResponse saveConfirm(List<PaymentItem> item, int confirm) {
		List<PaymentItem> items = new ArrayList<>();
		ReviewWait rw = null;
		ReviewWaitItem rwi = null;
		for(PaymentItem pi:item) {
			pi = paymentItemRepository.findOne(pi.getPiId());
			pi.setProdConfirm(confirm);
			rw = reviewWaitRepository.findByUserAndProductAndPaymentId(pi.getPayments().getUser(), pi.getItemId().getProduct(), pi.getPaymentId());
			if(rw == null) {
				rw = new ReviewWait();
				rw.setPaymentId(pi.getPaymentId());
				rw.setProduct(pi.getItemId().getProduct());
				rw.setUser(pi.getPayments().getUser());
				rw.setPayDate(pi.getPayments().getRegDate());
				
				rw = reviewWaitRepository.save(rw);
			}
			
			rwi = new ReviewWaitItem();
			rwi.setPaymentItem(pi);
			rwi.setReviewWait(rw);
			reviewWaitItemRepository.save(rwi);
			
			items.add(pi);
		}
		
		paymentItemRepository.save(items);
		
		
		return CommonResponse.of("저장되었습니다.", ResultCode.SUCCESS);
	}
}
