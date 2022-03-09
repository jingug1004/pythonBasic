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

import com.beauty.admin.service.AdminEventService;
import com.beauty.entity.DataTables;
import com.beauty.entity.Event;
import com.beauty.entity.ProductTimeSale;
import com.beauty.response.CommonResponse;
import com.beauty.response.DataTablesResult;
import com.beauty.response.result.ResultCode;

import lombok.Getter;
import lombok.Setter;

@Controller
@RequestMapping("/admin/event")
public class AdminEventController {
	
	@Value("${server.image.url}")
	@Getter @Setter
	String image_url;
	
	@Autowired
	private AdminEventService adminEventService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public void list(Model model) {
		model.addAttribute("image_url", image_url);
	}
	
	@RequestMapping(value = "/list_processing")
	@ResponseBody
	public DataTablesResult getList(Model model, @ModelAttribute DataTables input) {
		return adminEventService.list(input);
	}
	
	@RequestMapping(value = "/write", method = RequestMethod.GET)
	public void write(Model model, HttpSession session,
			@RequestParam(required=false) Long eId) {
	}
	
	@RequestMapping(value = "/update", method = RequestMethod.GET)
	public void update(Model model, @RequestParam(required=true) Long eid) {
		model.addAttribute("image_url", image_url);
		model.addAttribute("event", adminEventService.getEvent(eid));
	}
	
	@RequestMapping(value = "/getData", method = RequestMethod.GET)
	@ResponseBody
	public Object getData(Model model, @RequestParam(required=true) int eType) {
		String data = adminEventService.getData(eType);

		JSONParser parser = new JSONParser();
		JSONObject jsonObj = null;
		try {
			Object obj = parser.parse(data);
			jsonObj = (JSONObject) obj;
		}catch ( ParseException e ){

		}

		return jsonObj.get("data");
	}
	
	@RequestMapping(value = "/eventStat", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse stat(Model model, @RequestBody List<Event> events, @RequestParam int stat) {
		return adminEventService.eventStat(events, stat);
	}
	
	@RequestMapping(value = "/imageUpload", method = RequestMethod.POST)
	@ResponseBody
	public String uploadFile( HttpSession session, @RequestParam(value="uploadFile", required=false) MultipartFile uploadFile) {
		return adminEventService.uploadImage(uploadFile);
	}
	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse save(Model model,  @RequestBody String data) {
		JSONParser parser = new JSONParser();
		try {
			Object obj = parser.parse(data);
			JSONObject jsonObj = (JSONObject) obj;
			
			return adminEventService.save(jsonObj);
		}catch ( ParseException e ){
			return CommonResponse.of("실패하였습니다. 잠시 후 다시 시도해 주세요.", ResultCode.FAIL);
		}

	}
	
	@RequestMapping(value = "/update_proc", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse upd(Model model,  @RequestBody String data) {
		JSONParser parser = new JSONParser();
		try {
			Object obj = parser.parse(data);
			JSONObject jsonObj = (JSONObject) obj;
			
			return adminEventService.update(jsonObj);
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
		// Maintain a list to send back the files info. to the client side
		// List<UploadedFile> uploadedFiles = new ArrayList<UploadedFile>();

		// Iterate through the map
		for (MultipartFile multipartFile : fileMap.values()) {

			//UploadedFile fileInfo = getUploadedFileInfo(multipartFile);

			return adminEventService.uploadThumb(multipartFile);
		}

		return CommonResponse.of("fail", ResultCode.FAIL);
	}
	
	@RequestMapping(value = "/reorder", method = RequestMethod.POST)
	@ResponseBody
	public String reorder(
			@RequestParam(value="eid") Long eid,
			@RequestParam(value="oldorder") int oldorder,
			@RequestParam(value="neworder") int neworder) throws Exception {
		System.out.println(oldorder + " | " + neworder);
		//adminService.ctgReorder(parent, code, neworder, oldorder);
		adminEventService.reorder(eid, oldorder, neworder);
		return "success";
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse delete(Model model, @RequestBody List<Event> events) {
	
		return adminEventService.delete(events);
	}
	
}
