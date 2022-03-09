/**
  * Hana Project
  * Copyright 2014 iRush Co.,
  *
  */
package com.hanaph.gw.pe.member.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.hanaph.gw.co.common.utils.MenuUtil;
import com.hanaph.gw.co.common.utils.PageUtil;
import com.hanaph.gw.co.common.utils.StringUtil;
import com.hanaph.gw.co.common.utils.WebUtil;
import com.hanaph.gw.co.menu.service.MenuService;
import com.hanaph.gw.co.menu.vo.MenuVO;
import com.hanaph.gw.pe.member.service.SalaryService;
import com.hanaph.gw.pe.member.vo.MemberVO;
import com.hanaph.gw.pe.member.vo.SalaryVO;

/**
 * <pre>
 * Class Name : SalaryController.java
 * 설명 : 급여 정보 Controller
 * 
 * Modification Information
 *    수정일      수정자              수정 내용
 *  ------------ -------------- --------------------------------
 *  2014. 10. 29.      CHOIILJI         
 * </pre>
 * 
 * @version : 
 * @author  : CHOIILJI(choiilji@irush.co.kr)
 * @since   : 2014. 10. 29.
 */
@Controller
public class SalaryController {

	@Autowired
	private SalaryService salaryService;
	
	@Autowired
	private MenuService menuService;
	/**
	 * 
	 * <pre>
	 * 1. 개요     : 급여 리스트
	 * 2. 처리내용 : 급여 리스트 가져온다.
	 * </pre>
	 * @Method Name : salaryList
	 * @param request
	 * @return
	 */
	@RequestMapping("/pe/member/salaryList.do")
	public ModelAndView salaryList(HttpServletRequest request){
		
		final String MENU_CHILD= "0405";
		
		ModelAndView mav = new ModelAndView("pe/member/salaryList");
		Map<String, String> paramMap = new HashMap<String, String>();  
		
		String searchDate[] = WebUtil.dateCal(-12);
		
		String search_start_dt = StringUtil.nvl((String)request.getParameter("search_start_dt"), searchDate[0]);
		String search_end_dt = StringUtil.nvl((String)request.getParameter("search_end_dt"), searchDate[1]);
		
		int currentPage = Integer.parseInt(StringUtil.nvl(request.getParameter("currentPage"),"1"));	// 현재 페이지 번호
		int plRowRange = 10;	//한페이지당 보여줄 게시물 수 	
		int plPageRange = 10;	//페이지 번호 수
		
		//세션에서 회원정보 가져 온다.
		HttpSession session = request.getSession(false);
		MemberVO memberSessionVO = (MemberVO) session.getAttribute("gwUser");
		String emp_no = memberSessionVO.getEmp_no();
		
		paramMap.put("emp_no", emp_no);
		paramMap.put("search_start_dt", search_start_dt.replaceAll("-", ""));
		paramMap.put("search_end_dt", search_end_dt.replaceAll("-", ""));
		paramMap.put("page",String.valueOf(currentPage));		
		paramMap.put("perPageRow", String.valueOf(plRowRange));
		
		List<SalaryVO> salaryList = salaryService.getSalaryList(paramMap);
		int salaryTotalCount = salaryService.getSalaryCount(paramMap);
		
		//페이징
		PageUtil pu = new PageUtil();
		String pagingStr = pu.autoPaging(salaryTotalCount, plRowRange, plPageRange, currentPage);
		
		//LNB 메뉴 생성 START
		String menu_parents = StringUtil.substring(MENU_CHILD, 2);
		MenuUtil mu = new MenuUtil();
		List<MenuVO> lnbMenuList = mu.makeLnb(request, menu_parents , menuService);
		mav.addObject("lnbMenuList", lnbMenuList);
		mav.addObject("MENU_CHILD", MENU_CHILD);
		//LNB 메뉴 생성 END
				
		mav.addObject("salaryList", salaryList);
		mav.addObject("search_start_dt", search_start_dt);
		mav.addObject("search_end_dt", search_end_dt);
		mav.addObject("currentPage", currentPage);
		mav.addObject("plRowRange", plRowRange);
		mav.addObject("plPageRange", plPageRange);
		mav.addObject("pagingStr", pagingStr);
		mav.addObject("salaryTotalCount", salaryTotalCount);
		
		return mav;
	}
	
	/**
	 * 
	 * <pre>
	 * 1. 개요     : 급여상세 정보
	 * 2. 처리내용 : 급여상세 정보 가져온다.
	 * </pre>
	 * @Method Name : salaryDetail
	 * @param request
	 * @return
	 */
	@RequestMapping("/pe/member/salaryDetail.do")
	public ModelAndView salaryDetail(HttpServletRequest request){
		
		final String MENU_CHILD= "0405";
		
		ModelAndView mav = new ModelAndView("pe/member/salaryDetail");
		Map<String, String> paramMap = new HashMap<String, String>();
		
		//세션에서 회원정보 가져 온다.
		HttpSession session = request.getSession(false);
		MemberVO memberSessionVO = (MemberVO) session.getAttribute("gwUser");
		String emp_no = memberSessionVO.getEmp_no();
		String pay_date = StringUtil.nvl(request.getParameter("pay_date"),"00000").replaceAll("-", "");
		String search_start_dt = StringUtil.nvl((String)request.getParameter("search_start_dt"), "");
		String search_end_dt = StringUtil.nvl((String)request.getParameter("search_end_dt"), "");
		String currentPage = StringUtil.nvl(request.getParameter("currentPage"), "1");

		paramMap.put("emp_no", emp_no);
		paramMap.put("pay_dt", pay_date);
		
		SalaryVO salaryDetail = salaryService.getSalaryDetail(paramMap);
		if(salaryDetail == null){
			salaryDetail = new SalaryVO();
		}
		
		//LNB 메뉴 생성 START
		String menu_parents = StringUtil.substring(MENU_CHILD, 2);
		MenuUtil mu = new MenuUtil();
		List<MenuVO> lnbMenuList = mu.makeLnb(request, menu_parents , menuService);
		mav.addObject("lnbMenuList", lnbMenuList);
		mav.addObject("MENU_CHILD", MENU_CHILD);
		//LNB 메뉴 생성 END
				
		mav.addObject("currentPage", currentPage);
		mav.addObject("search_start_dt", search_start_dt);
		mav.addObject("search_end_dt", search_end_dt);
		mav.addObject("salaryDetail", salaryDetail);
        return mav;
	}
}

