package com.beauty.admin.controller;


import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.beauty.admin.service.AdminUserService;

@Controller
@RequestMapping("/admin")
public class LoginController {
	
	@Autowired
	private AdminUserService adminUserService;

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
     
//    /**
//     * Simply selects the home view to render by returning its name.
//     */
//    @RequestMapping(value = "/login", method = RequestMethod.GET)
//    public void login(HttpSession session) {
//        logger.info("Welcome login! {}", session.getId());
//    }
//     
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(HttpSession session, HttpServletRequest request, HttpServletResponse response) {
    	
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null){    
			new SecurityContextLogoutHandler().logout(request, response, auth);
		}
		
		session.invalidate();
		return "redirect:login?logout";//You can redirect wherever you want, but generally it's a good practice to show login screen again.
       
    }
     
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String login_success(Model model, HttpSession session) {
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
		session.setAttribute("auth", auth.getPrincipal());
		boolean authorized = authorities.contains(new SimpleGrantedAuthority("ROLE_ADMIN"));
		if(authorized) {
			return "/admin/index";
		} else {
			return "redirect:/advertiser/product/list";
		}
    }
     
    @RequestMapping(value = "/login_duplicate", method = RequestMethod.GET)
    public void login_duplicate() {    
        logger.info("Welcome login_duplicate!");
    }
    
    @RequestMapping(value = "/reg_user", method = RequestMethod.POST)
    public String regUser(@RequestParam(name="username") String username, 
    					@RequestParam(name="email") String email, 
    					@RequestParam(name="name") String name,
    					@RequestParam(name="phone") String phone,
    					@RequestParam(name="password") String password) {
    	adminUserService.regUser(username, email, name, phone,  password);
    	return "redirect:login";
    }
}