/**
 * Hana Project
 * Copyright 2014 iRush Co.,
 *
 */
package com.hanaph.gw.co.login.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.ModelAndViewDefiningException;

import com.hanaph.gw.co.common.interceptor.NoLoginCheck;
import com.hanaph.gw.co.common.utils.CommonUtil;
import com.hanaph.gw.co.common.utils.Environment;
import com.hanaph.gw.co.common.utils.StringUtil;
import com.hanaph.gw.co.login.service.LoginService;
import com.hanaph.gw.co.login.vo.LoginVO;
import com.hanaph.gw.co.menu.service.MenuService;
import com.hanaph.gw.co.menu.vo.MenuVO;
import com.hanaph.gw.pe.member.service.MemberService;
import com.hanaph.gw.pe.member.vo.MemberVO;
import com.hanaph.gw.pe.member.vo.PasswordHisVO;

/**
 * <pre>
 * Class Name : LoginController.java
 * 설명 : 그룹웨어는 로그인관리 하는 테이블과 회원 정보의 테이블이 분리 되어 있어 두개의 class 로 관리 한다. 로그인 정보 Controller
 * 
 * Modification Information
 *    수정일      수정자              수정 내용
 *  ------------ -------------- --------------------------------
 *  2014. 10. 17.      CHOIILJI
 * </pre>
 * 
 * @version : 1.0
 * @author : CHOIILJI(choiilji@irush.co.kr)
 * @since : 2014. 10. 17.
 */
@Controller
public class LoginController {

	private Environment env = new Environment();
	private static final Logger logger = Logger.getLogger(LoginController.class);
	
	@Autowired
	private LoginService loginService;

	@Autowired
	private MemberService memberService;

	@Autowired
	private MenuService menuService; 
	
	/**
	 * 
	 * <pre>
	 * 1. 개요     : 로그인 폼 
	 * 2. 처리내용 : 
	 * </pre>
	 * @Method Name : loginForm
	 * @param request
	 * @return
	 */
	@RequestMapping("/co/login/login.do")
	@NoLoginCheck
	public ModelAndView loginForm(HttpServletRequest request) {
		
		ModelAndView mav = new ModelAndView("co/login/loginForm");
		
		HttpSession session = request.getSession();
		MemberVO memberVO = (MemberVO) session.getAttribute("gwUser");
		if (session != null && memberVO != null && memberVO.getEmp_no() != null) {
			mav = new ModelAndView("redirect:/main/main.do");
		}else{
	
			Cookie[] cookies = request.getCookies();
			String user_id = "";
			String save_chk = "N";
			if (cookies != null) {
				for (int i = 0; i < cookies.length; i++) {
					if (cookies[i].getName().equals("GW_SAVE_ID")) {
						user_id =  cookies[i].getValue();
						save_chk = "Y";
					}
				}
			}
			mav.addObject("user_id", user_id);
			mav.addObject("save_chk", save_chk);
		}
		return mav;
	}

	/**
	 * 
	 * <pre>
	 * 1. 개요     : 로그인 처리
	 * 2. 처리내용 : 입력받은 사원번호와 비밀번호로 데이터를 조회.
	 * </pre>
	 * 
	 * @Method Name : login
	 * @param request
	 * @return
	 * @throws IOException
	 * @throws ModelAndViewDefiningException 
	 */
	@RequestMapping("/co/login/getLogin.do")
	@NoLoginCheck
	public ModelAndView login(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ModelAndViewDefiningException {

		ModelAndView mav = new ModelAndView("redirect:/main/main.do");
		
		Map<String, String> paramMap = new HashMap<String, String>();
		
		String user_id = StringUtil.nvl(request.getParameter("user_id"));
		String user_pw = StringUtil.nvl(request.getParameter("user_pw"));
		
		paramMap.put("emp_no", user_id);
		paramMap.put("pass_word", user_pw);

		LoginVO loginVO= loginService.getLogin(paramMap);
		
		if(loginVO !=null){
			logger.debug("login sucsses>>>>>>>>>>>>>>>>>>>>>>>>>>");
			MemberVO memberVO = memberService.getMemberDetail(paramMap);
			if (memberVO != null) {
				//gnb 메뉴 가져온다.
				List<MenuVO> gnbMenuList = menuService.getGnbMenuList(paramMap);
				// 입력받은 비밀번호화 DB비밀번호 일치 할 경우 로그인 세션 생성
				HttpSession session = request.getSession(false);
				
				if(session != null){  
					session.invalidate(); 
				}
				
				session = request.getSession(true);
				session.setMaxInactiveInterval(120*60); 	// 세션 시간 설정 120분
				session.setAttribute("gwUser", memberVO);
				session.setAttribute("gwGNB", gnbMenuList);

				// 아이디 저장 체크 했을 경우 쿠키 생성
				String save_chk = request.getParameter("save_chk");
				if (save_chk != null && save_chk.equals("Y")) {
					Cookie cookies = new Cookie("GW_SAVE_ID", request.getParameter("user_id"));
					cookies.setMaxAge(60 * 60 * 24 * 365);
					cookies.setPath("/");
					response.addCookie(cookies);
				} else {
					Cookie cookies = new Cookie("GW_SAVE_ID", "");
					cookies.setMaxAge(0);
					cookies.setPath("/");
					response.addCookie(cookies);
				}
				
				// 비밀번호의 유효성 체크, 보안기준에 적합한지 확인함
				Map<String, String> validateParamMap = new HashMap<String, String>();
				validateParamMap.put("in_EMP_NO", user_id);
				validateParamMap.put("in_PASSWORD", user_pw);
				
				LoginVO validateResultVO = loginService.callPasswordValidateProcedure(validateParamMap);
				
				if (!"0".equals(validateResultVO.getOut_CODE())) { // 현재 패스워드가 보안 기준에 적합하지 않을 경우 수정페이지로 이동 체크값을 세션에 저장한다.
					session.setAttribute("pass_change_chk", true);
					session.setAttribute("chgPasswordReason", validateResultVO.getOut_MSG());
				}else{
					session.setAttribute("pass_change_chk", false);
					session.setAttribute("chgPasswordReason", "");
				}
			}
		} else {
			logger.debug("login fail>>>>>>>>>>>>>>>>>>>>>>>>>>>");
			mav = CommonUtil.getMessageView("ID 또는 Password를 잘못 입력하셨습니다.", "window.location.href='"+env.getValue("root_dir.url")+"/co/login/login.do'","");
			throw new ModelAndViewDefiningException(mav);
		}

		return mav;
	}

	
	/**
	 * 
	 * <pre>
	 * 1. 개요     : 로그아웃 
	 * 2. 처리내용 : 세션을 무효화 시킨다.
	 * </pre>
	 * 
	 * @Method Name : logOut
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ModelAndViewDefiningException 
	 */
	@RequestMapping("/co/login/logOut.do")
	@NoLoginCheck
	public ModelAndView logOut(HttpServletRequest request)
			throws IOException, ModelAndViewDefiningException {
		ModelAndView mav = new ModelAndView();
		
		// 세션 종료
		request.getSession().invalidate();
		mav = CommonUtil.getMessageView("로그아웃 되었습니다.", "window.location.href='"+env.getValue("root_dir.url")+"/co/login/login.do'","");
		throw new ModelAndViewDefiningException(mav);
	}
	
	/**
	 * <pre>
	 * 1. 개요     : 비밀번호 변경 form
	 * 2. 처리내용 : 
	 * </pre>
	 * @Method Name : passwordChangeForm
	 * @param request
	 * @param response
	 * @return ModelAndView
	 */		
	@RequestMapping("/co/login/passwordChangeForm.do")
	public ModelAndView passwordChangeForm(HttpServletRequest request){

		ModelAndView mav = new ModelAndView("/co/login/passwordChangeForm");
		
		//세션에서 회원정보 가져 온다.
		HttpSession session = request.getSession(false);
		boolean pass_change_chk = (Boolean)session.getAttribute("pass_change_chk");
		
		mav.addObject("pass_change_chk", pass_change_chk);
		return mav;
	}
	
	/**
	 * <pre>
	 * 1. 개요     : 비밀번호 변경 
	 * 2. 처리내용 : 현재 비밀번호를 업데이트 후 히스토리 테이블에 저장한다.
	 * </pre>
	 * 
	 * @Method Name : updatePassword
	 * @param request : empCode,newPassword 
	 * @param response
	 * @throws Exception 
	 */		
	@RequestMapping("/co/login/updatePassword.do")
	public void updatePassword(HttpServletRequest request) throws Exception{
		
		ModelAndView mav = new ModelAndView("redirect:/main/index.do");
		Map<String, String> paramMap = new HashMap<String, String>();

		String old_password = StringUtil.nvl(request.getParameter("old_password"));
		String new_password = StringUtil.nvl(request.getParameter("new_password"));
		
		//세션생성 세션에서 emp_no 가져온다.
		HttpSession session = request.getSession();
		MemberVO memberSessionVO = (MemberVO) session.getAttribute("gwUser");
		boolean pass_change_chk = (Boolean)session.getAttribute("pass_change_chk");
		
		String emp_no = StringUtil.nvl(memberSessionVO.getEmp_no());
		
		String ip = request.getHeader("X-FORWARDED-FOR"); 
	     
		if (ip == null || ip.length() == 0) {
		    ip = request.getHeader("Proxy-Client-IP");
		}
		
		if (ip == null || ip.length() == 0) {
		    ip = request.getHeader("WL-Proxy-Client-IP");  // 웹로직
		}
		
		if (ip == null || ip.length() == 0) {
		    ip = request.getRemoteAddr() ;
		}
		
		Map<String, String> validateParamMap = new HashMap<String, String>();
		validateParamMap.put("in_EMP_NO", emp_no);
		validateParamMap.put("in_PASSWORD", new_password);
		
		LoginVO validateResultVO = loginService.callPasswordValidateProcedure(validateParamMap);
		
		if ("0".equals(validateResultVO.getOut_CODE())) { // 결과 코드가 0일 경우 비밀번호 변경 수행
			
			paramMap.put("emp_no", emp_no);
			paramMap.put("pass_word", old_password);
			
			LoginVO loginVO = loginService.getLogin(paramMap);
			
			if(loginVO !=null){

				//패스워드 수정
				LoginVO paramLoginVO = new LoginVO();
				paramLoginVO.setEmp_no(emp_no);
				paramLoginVO.setNew_password(new_password);
				
				//패스워드 히스토리 등록
				PasswordHisVO paramPasswordHisVO = new PasswordHisVO();
				paramPasswordHisVO.setEmp_no(emp_no);
				paramPasswordHisVO.setPassword(new_password);
				paramPasswordHisVO.setFirst_emp_no(emp_no);
				paramPasswordHisVO.setLast_emp_no("");
				paramPasswordHisVO.setLast_ip(ip);
				
				LoginVO resultVO = loginService.updatePassword(paramLoginVO, paramPasswordHisVO);

				if("0".equals(resultVO.getOut_CODE())){
					//패스워드 변경후 회원정보 다시 세션에 담는다.
					MemberVO memberDetail = memberService.getMemberDetail(paramMap);
					if (memberDetail != null) {
						session.setAttribute("gwUser", memberDetail);
					}
					
					if(pass_change_chk){ // 레이어에서 수정 되었을 때 결과
						mav = CommonUtil.getMessageView("수정되었습니다.", "window.parent.location.reload(true);window.parent.layerClose();", null);
						session.setAttribute("pass_change_chk", false);
						session.setAttribute("chgPasswordReason", "");
					}else{ // 팝업에서 수정 되었을 때 결과
						mav = CommonUtil.getMessageView("수정되었습니다.", "window.opener.location.reload(true);", "Y");
						session.setAttribute("chgPasswordReason", "");
					}
					throw new ModelAndViewDefiningException(mav);
				}else{
					mav = CommonUtil.getMessageView(resultVO.getOut_MSG(), "window.location.href='"+env.getValue("root_dir.url")+"/co/login/passwordChangeForm.do'","");
					throw new ModelAndViewDefiningException(mav);
				}
			}else{
				mav = CommonUtil.getMessageView("Password를 잘못 입력하셨습니다.", "window.location.href='"+env.getValue("root_dir.url")+"/co/login/passwordChangeForm.do'","");
				throw new ModelAndViewDefiningException(mav);
			}
		} else { // 비밀번호 유효성 체크 실패 시 메세지 반환
			mav = CommonUtil.getMessageView(validateResultVO.getOut_MSG(), "window.location.href='"+env.getValue("root_dir.url")+"/co/login/passwordChangeForm.do'","");
			throw new ModelAndViewDefiningException(mav);
		}
	}

}
