package com.beauty.admin.controller.advertiser;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.beauty.admin.service.AdminDeliveryCompanyService;
import com.beauty.admin.service.advertiser.AdvertiserPaymentService;
import com.beauty.entity.DataTables;
import com.beauty.entity.PaymentItem;
import com.beauty.response.CommonResponse;
import com.beauty.response.DataTablesResult;


@Controller
@RequestMapping("/advertiser/payment")
public class AdvertiserPaymentController {

	@Autowired
	private AdvertiserPaymentService advertiserPaymentService;
	
	@Autowired
	private AdminDeliveryCompanyService adminDeliveryCompanyService;
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public void list(Model model, @RequestParam String type) {
		model.addAttribute("type", type);
		model.addAttribute("delivery", adminDeliveryCompanyService.companyList());
	}
	
	
	@RequestMapping(value = "/list_processing")
	@ResponseBody
	public DataTablesResult getPaymentList(Model model, @ModelAttribute DataTables input, @RequestParam String type) {
		return advertiserPaymentService.list(input, type);
	}
		
	
	@RequestMapping(value = "/saveDelivery")
	@ResponseBody
	public CommonResponse saveDelivery(@RequestBody List<PaymentItem> paymentItem, @RequestParam String code, @RequestParam String number) {
		return advertiserPaymentService.saveDelivery(paymentItem, code, number);
	}
	
	@RequestMapping(value = "/prodConfirm")
	@ResponseBody
	public CommonResponse saveDelivery(@RequestBody List<PaymentItem> paymentItem, @RequestParam int status) {
		return advertiserPaymentService.saveConfirm(paymentItem, status);
	}
}
