package com.beauty.admin.controller.advertiser;

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

import com.beauty.admin.service.AdminItemService;
import com.beauty.admin.service.advertiser.AdvertiserProductService;
import com.beauty.entity.DataTables;
import com.beauty.entity.Product;
import com.beauty.entity.ProductItem;
import com.beauty.response.CommonResponse;
import com.beauty.response.DataTablesResult;

import lombok.Getter;
import lombok.Setter;


@Controller
@RequestMapping("/advertiser/product")
public class AdvertiserProductController {

	@Value("${server.image.url}")
	@Getter @Setter
	String image_url;

	@Autowired
	private AdvertiserProductService advertiserProductService;

	@Autowired
	private AdminItemService adminItemService;
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public void list(Model model) {
		model.addAttribute("image_url", image_url);
	}
	
	
	@RequestMapping(value = "/list_processing")
	@ResponseBody
	public DataTablesResult getProductList(Model model, @ModelAttribute DataTables input) {
		return advertiserProductService.list(input);
	}
	
	@RequestMapping(value = "/item_processing")
	@ResponseBody
	public DataTablesResult getItemList(Model model, @ModelAttribute DataTables input, @RequestParam Long productId) {
		return adminItemService.list(input, productId);
	}
	
	@RequestMapping(value = "/seller", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse seller(Model model, @RequestBody List<Product> products, @RequestParam int seller) {
		return advertiserProductService.seller(products, seller);
	}

	@RequestMapping(value = "/addCount", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse addCount(Model model, @RequestBody List<ProductItem> items, @RequestParam int count) {
		return advertiserProductService.addCount(items, count);
	}

		
}
