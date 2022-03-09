package com.beauty.admin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.beauty.admin.response.JsTreeNode;
import com.beauty.admin.service.AdminCategoryService;
import com.beauty.response.CommonResponse;

@Controller
@RequestMapping("/admin/category")
public class AdminCategoryController {

	@Autowired
	private AdminCategoryService adminCategoryService;


	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public void view(Model model) {
	}


	@RequestMapping(value = "/getCategory", method = RequestMethod.GET)
	@ResponseBody
	public List<JsTreeNode> getCategory(Model model, @RequestParam String id, @RequestParam int level) {
		return adminCategoryService.getCategory(id, level);
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse add(Model model, @RequestParam String categoryName, @RequestParam Long categoryId) {
		return adminCategoryService.add(categoryName, categoryId);
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse delete(Model model,@RequestParam Long categoryId, @RequestParam int visible) {
		return adminCategoryService.delete(categoryId, visible);
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse update(Model model,@RequestParam Long categoryId, @RequestParam String categoryName) {
		return adminCategoryService.update(categoryId, categoryName);
	}

	
}
