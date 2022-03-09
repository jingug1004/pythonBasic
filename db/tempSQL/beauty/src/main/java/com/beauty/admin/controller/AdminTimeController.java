package com.beauty.admin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.beauty.admin.service.AdminTimeService;
import com.beauty.entity.DataTables;
import com.beauty.entity.Product;
import com.beauty.entity.ProductTimeSale;
import com.beauty.response.CommonResponse;
import com.beauty.response.DataTablesResult;

import lombok.Getter;
import lombok.Setter;

@Controller
@RequestMapping("/admin/time")
public class AdminTimeController {
	
	@Value("${server.image.url}")
	@Getter @Setter
	String image_url;
	
	@Autowired
	private AdminTimeService adminTimeService;

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public void list(Model model) {
		model.addAttribute("image_url", image_url);
	}
	
	@RequestMapping(value = "/list_processing")
	@ResponseBody
	public DataTablesResult getList(Model model, @ModelAttribute DataTables input) {
		return adminTimeService.list(input);
	}
	
	@RequestMapping(value = "/product_processing")
	@ResponseBody
	public DataTablesResult getProductList(Model model, @ModelAttribute DataTables input) {
		return adminTimeService.getProductList(input);
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse add(Model model,  @RequestBody List<Product> timeSale,
			@RequestParam int type,
			@RequestParam int prodCount,
			@RequestParam String endDate) {
		return adminTimeService.add(timeSale, type, prodCount, endDate);
	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse delete(Model model, @RequestBody List<ProductTimeSale> timeSale) {
	
		return adminTimeService.delete(timeSale);
	}
	
}
