package com.beauty.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.beauty.response.CommonResponse;
import com.beauty.response.MainResponse;
import com.beauty.service.MainService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping(value="/api/main")
public class MainController {
	public final String TAG = "메인";

	@Autowired
	private MainService mainService;

	/**
	 * 메인 목록
	 * @return
	 */
	@ApiOperation(value = "메인 목록", notes = "메인 목록", response = MainResponse.class, tags={TAG, })
	@RequestMapping(method=RequestMethod.GET)
	public @ResponseBody MainResponse getMain() {
		return mainService.getMain();
	}
	
	@CrossOrigin
	@ApiOperation(value = "이미지업로드", notes = "이미지 업로드", response = String.class, tags={TAG, })
	@RequestMapping(value = "/imageUpload", produces = { "application/json; charset=utf-8" }, method = RequestMethod.POST)
	public @ResponseBody CommonResponse fileUpload(
			@ApiParam(value="파일") @RequestParam(value="uploadFile", required=true) MultipartFile uploadFile,
			@ApiParam(value="R:리뷰, Q:QNA, P:프로필") @RequestParam(value="type", required=true) String type) {
		
		return mainService.uploadFile(uploadFile, type);
	}

	
	
}
