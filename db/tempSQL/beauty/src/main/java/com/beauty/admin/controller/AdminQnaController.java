package com.beauty.admin.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.beauty.admin.service.AdminQnaService;

import lombok.Getter;
import lombok.Setter;

@Controller
@RequestMapping("/admin/qna")
public class AdminQnaController {
	
	@Value("${server.image.url}")
	@Getter @Setter
	String image_url;
	
	@Autowired
	AdminQnaService adminQnaService;
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public void list(HttpSession session, Model model) {
		model.addAttribute("qnaList", adminQnaService.findByQnaList());
	}
	
	@RequestMapping(value = "/reply", method = RequestMethod.GET)
	public void list(HttpSession session, Model model, @RequestParam(value="id", required=true) Long id) {
		model.addAttribute("image_url", image_url);
		model.addAttribute("qna", adminQnaService.findByQna(id));
	}
	
	@RequestMapping(value = "/reply_proc", method = RequestMethod.POST)
	@ResponseBody
	public String list(Model model, 
					@RequestParam(value="id", required=true) Long id,
					@RequestParam(value="reply", required=true) String reply) {
		return adminQnaService.replyQna(id, reply);
	}
}
