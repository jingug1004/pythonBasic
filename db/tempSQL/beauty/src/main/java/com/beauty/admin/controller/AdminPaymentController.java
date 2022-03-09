package com.beauty.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.beauty.admin.service.AdminPaymentService;
import com.beauty.entity.DataTables;
import com.beauty.response.DataTablesResult;


@Controller
@RequestMapping("/admin/payment")
public class AdminPaymentController {

	@Autowired
	private AdminPaymentService adminPaymentService;
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public void list(Model model, @RequestParam String type) {
		model.addAttribute("type", type);
	}
	
	
	@RequestMapping(value = "/list_processing")
	@ResponseBody
	public DataTablesResult getPaymentList(Model model, @ModelAttribute DataTables input, @RequestParam String type) {
		return adminPaymentService.list(input, type);
	}
	
	@RequestMapping(value = "/item_processing")
	@ResponseBody
	public DataTablesResult getItemList(Model model, @ModelAttribute DataTables input, @RequestParam String paymentId) {
		return adminPaymentService.itemList(input, paymentId);
	}


	
}
