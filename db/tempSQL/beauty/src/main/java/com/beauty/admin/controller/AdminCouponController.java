package com.beauty.admin.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
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

import com.beauty.admin.service.AdminBrandService;
import com.beauty.admin.service.AdminCategoryService;
import com.beauty.admin.service.AdminCouponService;
import com.beauty.admin.service.AdminProductService;
import com.beauty.admin.service.AdminUserService;
import com.beauty.entity.Coupon;
import com.beauty.entity.DataTables;
import com.beauty.entity.Role;
import com.beauty.response.CommonResponse;
import com.beauty.response.DataTablesResult;
import com.beauty.response.result.ResultCode;

import lombok.Getter;
import lombok.Setter;

@Controller
@RequestMapping("/admin/coupon")
public class AdminCouponController {

	@Value("${server.image.url}")
	@Getter @Setter
	String image_url;

	@Autowired
	private AdminCouponService adminCouponService;

	@Autowired
	private AdminCategoryService adminCategoryService;

	@Autowired
	private AdminBrandService adminBrandService;

	@Autowired
	private AdminProductService adminProductService;

	@Autowired
	private AdminUserService adminUserService;

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public void list(Model model) {
		model.addAttribute("image_url", image_url);
		//return adminUserService.getUser(user_id);
	}

	@RequestMapping(value = "/list_processing")
	@ResponseBody
	public DataTablesResult getCouponList(Model model, @ModelAttribute DataTables input) {
		return adminCouponService.list(input);
	}

	@RequestMapping(value = "/write", method = RequestMethod.GET)
	public void write(Model model, HttpSession session, @RequestParam(required=false) String couponId) {
		model.addAttribute("brand", adminBrandService.brandList());
		model.addAttribute("category", adminCategoryService.getCategoryLv3());
		model.addAttribute("product", adminProductService.getProductAll());
		model.addAttribute("user", adminUserService.getRoleUser(Role.MEMBER));

		if(couponId != null) {
			model.addAttribute("image_url", image_url);
			model.addAttribute("coupon", adminCouponService.getCoupon(couponId));
			model.addAttribute("action", "update");
		} else {
			model.addAttribute("action", "write");
		}
	}


	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse save(Model model,  @RequestBody String data) {
		JSONParser parser = new JSONParser();
		try {
			Object obj = parser.parse(data);
			JSONObject jsonObj = (JSONObject) obj;
			return adminCouponService.save(jsonObj);
		}catch ( ParseException e ){
			return CommonResponse.of("실패하였습니다. 잠시 후 다시 시도해 주세요.", ResultCode.FAIL);
		}
	}

	@RequestMapping(value = "/download", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse download(Model model,  @RequestBody List<Coupon> coupons, @RequestParam int flag) {
		return adminCouponService.download(coupons, flag);
	}

	@RequestMapping(value = "/data_processing")
	@ResponseBody
	public DataTablesResult getDataList(Model model, @ModelAttribute DataTables input, @RequestParam String cpId) {
		return adminCouponService.dataList(input, cpId);
	}
	
	@RequestMapping(value = "/auto", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse versionUpd(@RequestBody List<Coupon> coupons, @RequestParam int flag) throws Exception {
		return adminCouponService.auto(coupons, flag);
	}
}
