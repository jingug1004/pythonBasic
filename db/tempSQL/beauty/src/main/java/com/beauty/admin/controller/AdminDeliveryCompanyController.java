package com.beauty.admin.controller;

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
import com.beauty.entity.DataTables;
import com.beauty.entity.DeliveryCompany;
import com.beauty.response.CommonResponse;
import com.beauty.response.DataTablesResult;

@Controller
@RequestMapping("/admin/dcompany")
public class AdminDeliveryCompanyController {

	@Autowired
	private AdminDeliveryCompanyService adminDeliveryCompanyService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public void list(Model model) {
		//return adminUserService.getUser(user_id);
	}
	
	@RequestMapping(value = "/list_processing")
	@ResponseBody
	public DataTablesResult list(Model model, @ModelAttribute DataTables input) {
		return adminDeliveryCompanyService.list(input);
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse add(Model model, @RequestParam(required=true) String name, 
											@RequestParam(required=true) String code) {
		return adminDeliveryCompanyService.add(name, code);
	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse delete(Model model, @RequestBody List<DeliveryCompany> deliveryCompanys) {
		return adminDeliveryCompanyService.delete(deliveryCompanys);
	}
	
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse update(Model model,  @RequestBody DeliveryCompany deliveryCompany) {
		return adminDeliveryCompanyService.update(deliveryCompany);
	}
}
