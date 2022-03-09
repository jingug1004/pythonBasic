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
import com.hanaph.gw.co.menu.service.MenuService;
import com.hanaph.gw.co.menu.vo.MenuVO;
import com.hanaph.gw.pe.member.service.InsuranceService;
import com.hanaph.gw.pe.member.vo.InsuranceVO;
import com.hanaph.gw.pe.member.vo.MemberVO;

/**
 * <pre>
 * Class Name : InsuranceController.java
 * 설명 : 건강보험 연말정산 환급/징수 정보 Controller
 * 
 * Modification Information
 *    수정일      수정자              수정 내용
 *  ------------ -------------- --------------------------------
 *  2014. 10. 30.      CHOIILJI         
 * </pre>
 * 
 * @version : 
 * @author  : CHOIILJI(choiilji@irush.co.kr)
 * @since   : 2014. 10. 30.
 */
@Controller
public class InsuranceController {
	@Autowired
	private InsuranceService insuranceService;
	
	@Autowired
	private MenuService menuService;
	/**
	 * 
	 * <pre>
	 * 1. 개요     : 건강보험 리스트
	 * 2. 처리내용 : 건강보험 리스트 가져온다.
	 * </pre>
	 * @Method Name : salaryList
	 * @param request
	 * @return
	 */
	@RequestMapping("/pe/member/insuranceList.do")
	public ModelAndView insuranceList(HttpServletRequest request){
		
		final String MENU_CHILD= "0406";
		
		ModelAndView mav = new ModelAndView("pe/member/insuranceList");
		Map<String, String> paramMap = new HashMap<String, String>();  
		
		int currentPage = Integer.parseInt(StringUtil.nvl(request.getParameter("currentPage"),"1"));	// 현재 페이지 번호
		int plRowRange = 10;	//한페이지당 보여줄 게시물 수 	
		int plPageRange = 10;	//페이지 번호 수
		
		//세션에서 회원정보 가져 온다.
		HttpSession session = request.getSession(false);
		MemberVO memberSessionVO = (MemberVO) session.getAttribute("gwUser");
		String emp_no = memberSessionVO.getEmp_no();
		System.out.println("---------- insuranceList 2");
		paramMap.put("emp_no", emp_no);
		paramMap.put("page",String.valueOf(currentPage));		
		paramMap.put("perPageRow", String.valueOf(plRowRange));
		System.out.println("---------- insuranceList 2-1");
		List<InsuranceVO> insuranceList = insuranceService.getInsuranceList(paramMap);
		System.out.println("---------- insuranceList 2-2");
		int insuranceTotalCount = insuranceService.getInsuranceCount(paramMap);
		System.out.println("---------- insuranceList 3");
		//페이징
		PageUtil pu = new PageUtil();
		String pagingStr = pu.autoPaging(insuranceTotalCount, plRowRange, plPageRange, currentPage);
		
		//LNB 메뉴 생성 START
		String menu_parents = StringUtil.substring(MENU_CHILD, 2);
		MenuUtil mu = new MenuUtil();
		List<MenuVO> lnbMenuList = mu.makeLnb(request, menu_parents , menuService);
		//LNB 메뉴 생성 END
			
		mav.addObject("insuranceList", insuranceList);
		mav.addObject("currentPage", currentPage);
		mav.addObject("pagingStr", pagingStr);
		mav.addObject("insuranceTotalCount", insuranceTotalCount);
		mav.addObject("lnbMenuList", lnbMenuList);
		mav.addObject("MENU_CHILD", MENU_CHILD);
		
		return mav;
	}
	
	/**
	 * 
	 * <pre>
	 * 1. 개요     : 건강보험상세 정보
	 * 2. 처리내용 : 건강보험상세 정보 가져온다.
	 * </pre>
	 * @Method Name : salaryDetail
	 * @param request
	 * @return
	 */
	@RequestMapping("/pe/member/insuranceDetail.do")
	public ModelAndView insuranceDetail(HttpServletRequest request){
		
		final String MENU_CHILD= "0406";
		
		ModelAndView mav = new ModelAndView("pe/member/insuranceDetail");
		Map<String, String> paramMap = new HashMap<String, String>();
		
		//세션에서 회원정보 가져 온다.
		HttpSession session = request.getSession(false);
		MemberVO memberSessionVO = (MemberVO) session.getAttribute("gwUser");
		String emp_no = memberSessionVO.getEmp_no();
		String import_year = StringUtil.nvl(request.getParameter("import_year"), "2013");
		String currentPage = StringUtil.nvl(request.getParameter("currentPage"), "1");

		paramMap.put("emp_no", emp_no);
		paramMap.put("import_year", import_year);
		
		InsuranceVO insuranceDetail = insuranceService.getInsuranceDetail(paramMap);
		if(insuranceDetail == null){
			insuranceDetail = new InsuranceVO();
		}
		
		//LNB 메뉴 생성 START
		String menu_parents = StringUtil.substring(MENU_CHILD, 2);
		MenuUtil mu = new MenuUtil();
		List<MenuVO> lnbMenuList = mu.makeLnb(request, menu_parents , menuService);
		mav.addObject("lnbMenuList", lnbMenuList);
		mav.addObject("MENU_CHILD", MENU_CHILD);
		//LNB 메뉴 생성 END

		mav.addObject("currentPage", currentPage);
		mav.addObject("insuranceDetail", insuranceDetail);
		mav.addObject("import_year", import_year);
        return mav;
	}
}
