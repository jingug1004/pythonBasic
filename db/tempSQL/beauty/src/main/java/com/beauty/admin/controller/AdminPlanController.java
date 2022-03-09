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

import com.beauty.admin.service.AdminPlanService;
import com.beauty.entity.DataTables;
import com.beauty.entity.Plan;
import com.beauty.entity.PlanGroup;
import com.beauty.entity.PlanProduct;
import com.beauty.entity.Product;
import com.beauty.response.CommonResponse;
import com.beauty.response.DataTablesResult;
import com.beauty.response.result.ResultCode;

import lombok.Getter;
import lombok.Setter;

@Controller
@RequestMapping("/admin/plan")
public class AdminPlanController {
	
	@Value("${server.image.url}")
	@Getter @Setter
	String image_url;
	
	@Autowired
	private AdminPlanService adminPlanService;

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public void list(Model model) {
		model.addAttribute("image_url", image_url);
		//return adminUserService.getUser(user_id);
	}
	
	@RequestMapping(value = "/list_processing")
	@ResponseBody
	public DataTablesResult getList(Model model, @ModelAttribute DataTables input) {
		return adminPlanService.list(input);
	}
	
	@RequestMapping(value = "/write", method = RequestMethod.GET)
	public void write(Model model, HttpSession session, @RequestParam(required=false) Long pid) {
		if(pid != null) {
			model.addAttribute("image_url", image_url);
			model.addAttribute("plan", adminPlanService.getPlan(pid));
			model.addAttribute("action", "update");
		} else {
			model.addAttribute("action", "write");
		}
	}

	@RequestMapping(value = "/planStat", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse stat(Model model, @RequestBody List<Plan> plans, @RequestParam int stat) {
		return adminPlanService.planStat(plans, stat);
	}
	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse save(Model model,  @RequestBody String data) {
		JSONParser parser = new JSONParser();
		try {
			Object obj = parser.parse(data);
			JSONObject jsonObj = (JSONObject) obj;
			
			return adminPlanService.save(jsonObj);
		}catch ( ParseException e ){
			return CommonResponse.of("실패하였습니다. 잠시 후 다시 시도해 주세요.", ResultCode.FAIL);
		}
	}
	
	@RequestMapping(value = "/group_processing")
	@ResponseBody
	public DataTablesResult getGroupList(Model model, @ModelAttribute DataTables input, @RequestParam Long pid) {
		return adminPlanService.groupList(input, pid);
	}
	
	@RequestMapping(value = "/group_save", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse groupSave(Model model,  @RequestBody String data) {
		JSONParser parser = new JSONParser();
		try {
			Object obj = parser.parse(data);
			JSONObject jsonObj = (JSONObject) obj;
			
			return adminPlanService.groupSave(jsonObj);
		}catch ( ParseException e ){
			return CommonResponse.of("실패하였습니다. 잠시 후 다시 시도해 주세요.", ResultCode.FAIL);
		}
	}
	
	@RequestMapping(value = "/group_stat", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse groupStat(Model model, @RequestBody List<PlanGroup> groups, @RequestParam int stat, @RequestParam Long pid) {
		return adminPlanService.groupStat(groups, stat, pid);
	}
	
	
	@RequestMapping(value = "/item_processing")
	@ResponseBody
	public DataTablesResult getProdList(Model model, @ModelAttribute DataTables input, @RequestParam Long pgid) {
		return adminPlanService.itemList(input, pgid);
	}
	
	@RequestMapping(value = "/getProduct")
	@ResponseBody
	public List<Product> getProduct(Model model, @RequestParam Long pgid) {
		return adminPlanService.getProduct(pgid);
	}
	
	@RequestMapping(value = "/item_save", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse itemSave(Model model,  @RequestBody String data) {
		JSONParser parser = new JSONParser();
		try {
			Object obj = parser.parse(data);
			JSONObject jsonObj = (JSONObject) obj;
			
			return adminPlanService.itemSave(jsonObj);
		}catch ( ParseException e ){
			return CommonResponse.of("실패하였습니다. 잠시 후 다시 시도해 주세요.", ResultCode.FAIL);
		}
	}
	
	@RequestMapping(value = "/deleteProd", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse groupStat(Model model, @RequestBody List<PlanProduct> products) {
		return adminPlanService.deleteProd(products);
	}
	
	@RequestMapping(value = "/deleteItem", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse deleteItem(Model model, @RequestBody List<PlanGroup> products) {
		return adminPlanService.deleteItem(products);
	}
	
	@RequestMapping(value = "/reorder", method = RequestMethod.POST)
	@ResponseBody
	public String reorder(
			@RequestParam(value="pid") Long pid,
			@RequestParam(value="oldorder") int oldorder,
			@RequestParam(value="neworder") int neworder) throws Exception {
		System.out.println(oldorder + " | " + neworder);
		//adminService.ctgReorder(parent, code, neworder, oldorder);
		adminPlanService.reorder(pid, oldorder, neworder);
		return "success";
	}
}
