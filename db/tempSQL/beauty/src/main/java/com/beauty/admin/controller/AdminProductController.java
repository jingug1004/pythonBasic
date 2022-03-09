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
import com.beauty.admin.service.AdminCategoryService;
import com.beauty.admin.service.AdminDeliveryCompanyService;
import com.beauty.admin.service.AdminEventService;
import com.beauty.admin.service.AdminItemService;
import com.beauty.admin.service.AdminProductService;
import com.beauty.admin.service.AdminUserService;
import com.beauty.entity.DataTables;
import com.beauty.entity.Product;
import com.beauty.entity.Role;
import com.beauty.response.CommonResponse;
import com.beauty.response.DataTablesResult;
import com.beauty.response.result.ResultCode;

import lombok.Getter;
import lombok.Setter;


@Controller
@RequestMapping("/admin/product")
public class AdminProductController {

	@Value("${server.image.url}")
	@Getter @Setter
	String image_url;

	@Autowired
	private AdminProductService adminProductService;

	@Autowired
	private AdminCategoryService adminCategoryService;
	
	@Autowired
	private AdminBrandService adminBrandService;
	
	@Autowired
	private AdminEventService adminEventService;
	
	@Autowired
	private AdminDeliveryCompanyService adminDeliveryCompanyService;

	@Autowired
	private AdminItemService adminItemService;
	
	@Autowired
	private AdminUserService adminUserService;
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public void list(Model model, @RequestParam(required=true) int box, @RequestParam(required=false) Long brand, @RequestParam(required=false) Long category) {
		model.addAttribute("brand", brand);
		model.addAttribute("category", category);
		model.addAttribute("box", box);
		model.addAttribute("image_url", image_url);
	}
	
	
	@RequestMapping(value = "/list_processing")
	@ResponseBody
	public DataTablesResult getProductList(Model model, @ModelAttribute DataTables input, @RequestParam(required=true) Long box, @RequestParam(required=false) Long brand, @RequestParam(required=false) Long category) {
		return adminProductService.list(input, box, brand, category);
	}
	
	@RequestMapping(value = "/item_processing")
	@ResponseBody
	public DataTablesResult getItemList(Model model, @ModelAttribute DataTables input, @RequestParam Long productId) {
		return adminItemService.list(input, productId);
	}
	
	@RequestMapping(value = "/seller", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse seller(Model model, @RequestBody List<Product> products, @RequestParam int seller) {
		return adminProductService.seller(products, seller);
	}
	
	@RequestMapping(value = "/score", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse score(Model model, @RequestBody List<Product> products, @RequestParam int score) {
		return adminProductService.score(products, score);
	}
	
	@RequestMapping(value = "/write", method = RequestMethod.GET)
	public void write(Model model, HttpSession session,
			@RequestParam(required=true) Long box, 
			@RequestParam(required=false) String productId) {
		
		// 카테고리 레벨3
		model.addAttribute("category", adminCategoryService.getCategoryLv3());
		// 브랜드
		model.addAttribute("brand", adminBrandService.brandList());
		// 택배사
		model.addAttribute("company", adminDeliveryCompanyService.companyList());
		// 판매자
		model.addAttribute("advertiser", adminUserService.getRoleUser(Role.ADVERTISER));
		
		model.addAttribute("box", box);
		
		if(productId != null) {
			model.addAttribute("product", adminProductService.getProduct(productId));
			model.addAttribute("action", "update");
		} else {
			model.addAttribute("action", "write");
		}
	}
	
	@RequestMapping(value = "/imageUpload", method = RequestMethod.POST)
	@ResponseBody
	public String uploadFile( HttpSession session, @RequestParam(value="uploadFile", required=false) MultipartFile uploadFile) {
		
		CommonResponse filePath = adminProductService.uploadImage(uploadFile, "0");
		
		return image_url + filePath.getMessage();
	}
	

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse save(Model model,  @RequestBody String data) {
		JSONParser parser = new JSONParser();
		try {
			Object obj = parser.parse(data);
			JSONObject jsonObj = (JSONObject) obj;
			
			return adminProductService.save(jsonObj);
		}catch ( ParseException e ){
			return CommonResponse.of("실패하였습니다. 잠시 후 다시 시도해 주세요.", ResultCode.FAIL);
		}

	}
	
	
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse uploadImage(Model model, MultipartHttpServletRequest request,
			HttpServletResponse response) throws IOException {

		// Getting uploaded files from the request object
		Map<String, MultipartFile> fileMap = request.getFileMap();
		String thumb = request.getParameter("thumb");
		// Maintain a list to send back the files info. to the client side
		// List<UploadedFile> uploadedFiles = new ArrayList<UploadedFile>();

		// Iterate through the map
		for (MultipartFile multipartFile : fileMap.values()) {

			//UploadedFile fileInfo = getUploadedFileInfo(multipartFile);

			return adminProductService.uploadImage(multipartFile, thumb);
		}

		return CommonResponse.of("fail", ResultCode.FAIL);
	}


	//	private UploadedFile getUploadedFileInfo(MultipartFile multipartFile)
	//			throws IOException {
	//
	//		UploadedFile fileInfo = new UploadedFile();
	//		fileInfo.setName(multipartFile.getOriginalFilename());
	//		fileInfo.setSize(multipartFile.getSize());
	//		fileInfo.setType(multipartFile.getContentType());
	//
	//		return fileInfo;
	//	}
	
	@RequestMapping(value = "/getProduct", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getItem(Model model, @RequestParam String productId) {
		
		return adminProductService.getProduct(productId);
	}

	


	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse delete(Model model, @RequestParam String item_id, @RequestParam int stop) {
		return adminProductService.delete(item_id, stop);
	}

	
	@RequestMapping(value = "/top/list", method = RequestMethod.GET)
	public void topList(Model model) {
		
		model.addAttribute("brand", adminBrandService.brandList());
		model.addAttribute("category", adminCategoryService.getCategoryLv3());
		model.addAttribute("product", adminProductService.getProductAll());
		model.addAttribute("event", adminEventService.eventList());
		
		model.addAttribute("image_url", image_url);
	}
	
	
	@RequestMapping(value = "/top/list_processing")
	@ResponseBody
	public DataTablesResult getProductTopList(Model model, @ModelAttribute DataTables input) {
		return adminProductService.topList(input);
	}
	
	@RequestMapping(value = "/top/upload", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse topUploadImage(Model model, MultipartHttpServletRequest request,
			HttpServletResponse response) throws IOException {

		Map<String, MultipartFile> fileMap = request.getFileMap();
		
		for (MultipartFile multipartFile : fileMap.values()) {
			return adminProductService.uploadImage(multipartFile, "3");
		}

		return CommonResponse.of("fail", ResultCode.FAIL);
	}
	
	@RequestMapping(value = "/top/save", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse topSave(Model model,  @RequestBody String data) {
		JSONParser parser = new JSONParser();
		try {
			Object obj = parser.parse(data);
			JSONObject jsonObj = (JSONObject) obj;
			System.out.println(jsonObj);
			return adminProductService.topSave(jsonObj);
		}catch ( ParseException e ){
			return CommonResponse.of("실패하였습니다. 잠시 후 다시 시도해 주세요.", ResultCode.FAIL);
		}
	}
}
