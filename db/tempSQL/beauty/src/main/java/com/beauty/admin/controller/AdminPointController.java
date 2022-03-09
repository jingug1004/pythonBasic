package com.beauty.admin.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.beauty.admin.service.AdminPointService;


@Controller
@RequestMapping("/admin/point")
public class AdminPointController {


	@Autowired
	AdminPointService adminPointService;
	
	@RequestMapping(value = "/user", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> user(Model model, @RequestParam(value="user_id") String user_id) {
		return adminPointService.findByUserPoint(user_id);
	}
}
