package com.beauty.admin.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.beauty.admin.service.AdminBrandService;
import com.beauty.entity.Brand;
import com.beauty.entity.DataTables;
import com.beauty.response.CommonResponse;
import com.beauty.response.DataTablesResult;
import com.beauty.response.result.ResultCode;

import lombok.Getter;
import lombok.Setter;

@Controller
@RequestMapping("/admin/brand")
public class AdminBrandController {

	@Value("${server.image.url}")
	@Getter @Setter
	String image_url;
	
	@Autowired
	private AdminBrandService adminBrandService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public void list(Model model) {
		model.addAttribute("image_url", image_url);
		//return adminUserService.getUser(user_id);
	}
	
	@RequestMapping(value = "/list_processing")
	@ResponseBody
	public DataTablesResult getBrandList(Model model, @ModelAttribute DataTables input) {
		return adminBrandService.list(input);
	}
	
	@RequestMapping(value = "/write", method = RequestMethod.GET)
	public void write(Model model, HttpSession session, @RequestParam(required=false) Long brandId) {
		if(brandId != null) {
			model.addAttribute("image_url", image_url);
			model.addAttribute("brand", adminBrandService.getBrand(brandId));
			model.addAttribute("action", "update");
		} else {
			model.addAttribute("action", "write");
		}
	}

	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse uploadImage(Model model, MultipartHttpServletRequest request,
			HttpServletResponse response) throws IOException {

		Map<String, MultipartFile> fileMap = request.getFileMap();
		
		for (MultipartFile multipartFile : fileMap.values()) {
			return adminBrandService.uploadThumb(multipartFile);
		}

		return CommonResponse.of("fail", ResultCode.FAIL);
	}
	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse save(Model model,  @RequestBody String data) {
		JSONParser parser = new JSONParser();
		try {
			Object obj = parser.parse(data);
			JSONObject jsonObj = (JSONObject) obj;
			
			return adminBrandService.save(jsonObj);
		}catch ( ParseException e ){
			return CommonResponse.of("실패하였습니다. 잠시 후 다시 시도해 주세요.", ResultCode.FAIL);
		}
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse add(Model model, @RequestParam String brandName) {
		return adminBrandService.add(brandName);
	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse delete(Model model, @RequestBody List<Brand> brands, @RequestParam String flag) {
		return adminBrandService.delete(brands, flag);
	}
	
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse update(Model model,  @RequestBody Brand brand) {
		return adminBrandService.update(brand);
	}
}
