package com.beauty.admin.controller;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.beauty.admin.service.AdminUserService;
import com.beauty.entity.DataTables;
import com.beauty.entity.DeviceInfo;
import com.beauty.entity.User;
import com.beauty.response.DataTablesResult;

@Controller
@RequestMapping("/admin/user")
public class AdminUserController {

	@Autowired
	private AdminUserService adminUserService;
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public void list(Model model, @RequestParam(value="type") String type, @RequestParam(value="role", required=false) String role) {
		model.addAttribute("type", type);
		model.addAttribute("role", role);
	}
	
	@RequestMapping(value = "/list_processing", method = RequestMethod.GET)
	@ResponseBody
	public DataTablesResult list(Model model, @ModelAttribute DataTables input, @RequestParam(value="type") String type, @RequestParam(value="role", required=false) String role) {
		return adminUserService.list(input, type, role);
	}

	@RequestMapping(value = "/getUser", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getUser(Model model, @RequestParam(value="user_id") String user_id) {
		try {
			//Field[] fields = obj.getClass().getFields(); //private field는 나오지 않음.
			User user = adminUserService.getUser(user_id);
			Field[] fields = user.getClass().getDeclaredFields();
			Map<String, Object> resultMap = new HashMap<String, Object>();
			for(int i=0; i<=fields.length-1;i++){
				fields[i].setAccessible(true);
				resultMap.put(fields[i].getName(), fields[i].get(user));
			}
			return resultMap;
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;

		//return adminUserService.getUser(user_id);
	}

	
	/**
	 * @param model
	 * @param type 
	 * @return
	 */
	@RequestMapping(value = "/auth", method = RequestMethod.POST)
	@ResponseBody
	public String update(Model model, @RequestParam(value="user_id") String user_id,
			@RequestParam(value="role", required=false) String role) {
			return adminUserService.updateAuth(user_id, role);
	}
	
	/**
	 * @param model
	 * @param type 
	 * @return
	 */
	@RequestMapping(value = "/leave_cancel", method = RequestMethod.POST)
	@ResponseBody
	public String leave_cancel(Model model, @RequestParam(value="user_id") String user_id) {
			return adminUserService.leaveCancel(user_id);
	}
	
	@RequestMapping(value = "/getDevice", method = RequestMethod.GET)
	@ResponseBody
	public List<DeviceInfo> getDevice(Model model, @RequestParam(value="user_id") String user_id) {
		return adminUserService.getDevice(user_id);
	}
}
