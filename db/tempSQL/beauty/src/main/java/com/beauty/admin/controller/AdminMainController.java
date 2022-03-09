package com.beauty.admin.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.beauty.admin.service.AdminMainService;
import com.beauty.admin.service.AdminProductService;
import com.beauty.entity.DataTables;
import com.beauty.entity.MainFirstBanner;
import com.beauty.entity.MainMenu;
import com.beauty.entity.MainSecondBanner;
import com.beauty.entity.MainTenMenu;
import com.beauty.entity.Product;
import com.beauty.response.CommonResponse;
import com.beauty.response.DataTablesResult;
import com.beauty.response.result.ResultCode;

import lombok.Getter;
import lombok.Setter;

@Controller
@RequestMapping("/admin/main")
public class AdminMainController {

	@Value("${server.image.url}")
	@Getter @Setter
	String image_url;
	
	@Autowired
	private AdminMainService adminMainService;

	@Autowired
	private AdminProductService adminProductService;
	
	@RequestMapping(value = "/first_processing")
	@ResponseBody
	public DataTablesResult getFirstList(Model model, @ModelAttribute DataTables input, @RequestParam(required=false, defaultValue="1") String showType) {
		return adminMainService.first(input, Integer.parseInt(showType));
	}
	
	@RequestMapping(value = "/second_processing")
	@ResponseBody
	public DataTablesResult getSecondList(Model model, @ModelAttribute DataTables input) {
		model.addAttribute("image_url", image_url);
		return adminMainService.second(input);
	}
	
	@RequestMapping(value = "/menu_processing")
	@ResponseBody
	public DataTablesResult getMenuList(Model model, @ModelAttribute DataTables input) {
		return adminMainService.menu(input);
	}
	
	@RequestMapping(value = "/list_processing")
	@ResponseBody
	public DataTablesResult getListList(Model model, @ModelAttribute DataTables input) {
		return adminMainService.list(input);
	}
	
	@RequestMapping(value = "/getData", method = RequestMethod.GET)
	@ResponseBody
	public Object getData(Model model, @RequestParam(required=true) int sbType) {
		String data = adminMainService.getData(sbType);

		JSONParser parser = new JSONParser();
		JSONObject jsonObj = null;
		try {
			Object obj = parser.parse(data);
			jsonObj = (JSONObject) obj;
		}catch ( ParseException e ){

		}

		return jsonObj.get("data");
	}
	
	@RequestMapping(value = "/{menu}", method = RequestMethod.GET)
	public void list(Model model, @PathVariable String menu, @RequestParam(required=false, defaultValue="1") String showType) {
		if(menu.equals("first")) {
			List<Product> product = adminProductService.getProductAll();
			model.addAttribute("product", product);
			model.addAttribute("showType", showType);
		} else if(menu.equals("list")) {
			List<Product> product = adminProductService.getProductAll();
			model.addAttribute("product", product);
		}
		
		model.addAttribute("image_url", image_url);
		
	}

	@RequestMapping(value = "/firstDelete", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse firstDelete(Model model, @RequestBody List<MainFirstBanner> datas) {
		return adminMainService.firstDelete(datas);
	}
	
	@RequestMapping(value = "/secondDelete", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse secondDelete(Model model, @RequestBody List<MainSecondBanner> datas) {
		return adminMainService.secondDelete(datas);
	}
	
	@RequestMapping(value = "/menuDelete", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse menuDelete(Model model, @RequestBody List<MainTenMenu> datas) {
		return adminMainService.menuDelete(datas);
	}
	
	@RequestMapping(value = "/listDelete", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse listDelete(Model model, @RequestBody List<MainMenu> datas) {
		return adminMainService.listDelete(datas);
	}
	
	@RequestMapping(value = "/firstSave", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse firstSave(Model model,  @RequestBody String data) {
		JSONParser parser = new JSONParser();
		try {
			Object obj = parser.parse(data);
			JSONObject jsonObj = (JSONObject) obj;
			
			return adminMainService.firstSave(jsonObj);
		}catch ( ParseException e ){
			return CommonResponse.of("실패하였습니다. 잠시 후 다시 시도해 주세요.", ResultCode.FAIL);
		}
	}
	
	@RequestMapping(value = "/secondSave", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse secondSave(Model model,  @RequestBody String data) {
		JSONParser parser = new JSONParser();
		try {
			Object obj = parser.parse(data);
			JSONObject jsonObj = (JSONObject) obj;
			
			return adminMainService.secondSave(jsonObj);
		}catch ( ParseException e ){
			return CommonResponse.of("실패하였습니다. 잠시 후 다시 시도해 주세요.", ResultCode.FAIL);
		}
	}
	
	@RequestMapping(value = "/menuSave", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse menuSave(Model model,  @RequestBody String data) {
		JSONParser parser = new JSONParser();
		try {
			Object obj = parser.parse(data);
			JSONObject jsonObj = (JSONObject) obj;
			
			return adminMainService.menuSave(jsonObj);
		}catch ( ParseException e ){
			return CommonResponse.of("실패하였습니다. 잠시 후 다시 시도해 주세요.", ResultCode.FAIL);
		}
	}
	
	@RequestMapping(value = "/listSave", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse listSave(Model model,  @RequestBody String data) {
		JSONParser parser = new JSONParser();
		try {
			Object obj = parser.parse(data);
			JSONObject jsonObj = (JSONObject) obj;
			
			return adminMainService.listSave(jsonObj);
		}catch ( ParseException e ){
			return CommonResponse.of("실패하였습니다. 잠시 후 다시 시도해 주세요.", ResultCode.FAIL);
		}
	}
	
	
	@RequestMapping(value = "/reorder", method = RequestMethod.POST)
	@ResponseBody
	public String reorder(
			@RequestParam(value="sbid") Long sbid,
			@RequestParam(value="oldorder") int oldorder,
			@RequestParam(value="neworder") int neworder) throws Exception {
		adminMainService.reorder(sbid, oldorder, neworder);
		return "success";
	}
	
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse uploadImage(Model model, MultipartHttpServletRequest request,
			HttpServletResponse response) throws IOException {

		Map<String, MultipartFile> fileMap = request.getFileMap();
		
		for (MultipartFile multipartFile : fileMap.values()) {
			return adminMainService.uploadThumb(multipartFile);
		}

		return CommonResponse.of("fail", ResultCode.FAIL);
	}
}
