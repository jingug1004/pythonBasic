package com.beauty.admin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.beauty.admin.service.AdminItemService;
import com.beauty.entity.Product;
import com.beauty.entity.ProductItem;
import com.beauty.response.CommonResponse;

@Controller
@RequestMapping("/admin/item")
public class AdminItemController {

	@Autowired
	private AdminItemService adminItemService;

	@RequestMapping(value = "/getItem", method = RequestMethod.GET)
	@ResponseBody
	public ProductItem getItem(Model model, @RequestParam String itemId) {
		return adminItemService.getItem(itemId);
	}
	
	@RequestMapping(value = "/getProductItem", method = RequestMethod.GET)
	@ResponseBody
	public List<ProductItem> getProductItem(Model model, @RequestBody Product product) {
		return adminItemService.getProductItem(product);
	}
	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse save(Model model,  @RequestBody ProductItem productItem, @RequestParam String productId) {
		return adminItemService.save(productItem, productId);
	}
	
	
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse delete(Model model, @RequestParam String item_id, @RequestParam int stop) {
		return adminItemService.delete(item_id, stop);
	}
	
	@RequestMapping(value = "/seller", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse seller(Model model, @RequestBody List<ProductItem> items, @RequestParam int seller) {
		return adminItemService.seller(items, seller);
	}
}
