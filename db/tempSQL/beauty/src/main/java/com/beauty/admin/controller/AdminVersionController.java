package com.beauty.admin.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.beauty.admin.service.AdminVersionService;
import com.beauty.entity.Version;
import com.beauty.response.CommonResponse;

@Controller
@RequestMapping("/admin/version")
public class AdminVersionController {

	@Autowired
	AdminVersionService adminVersionService;
	
	@RequestMapping(value = "/getVersion", method = RequestMethod.GET)
	@ResponseBody
	public Version version(HttpSession session, Model model) throws Exception {

		return adminVersionService.findByVersion("A");
	}
	
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse versionUpd(HttpSession session,
			@RequestBody Version version) throws Exception {
		return adminVersionService.versionSave(version);
	}
}
