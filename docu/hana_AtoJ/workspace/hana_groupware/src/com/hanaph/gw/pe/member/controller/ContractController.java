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

import com.hanaph.gw.co.authority.service.AuthorityService;
import com.hanaph.gw.co.common.utils.MenuUtil;
import com.hanaph.gw.co.common.utils.PageUtil;
import com.hanaph.gw.co.common.utils.StringUtil;
import com.hanaph.gw.co.menu.service.MenuService;
import com.hanaph.gw.co.menu.vo.MenuVO;
import com.hanaph.gw.pe.member.service.ContractService;
import com.hanaph.gw.pe.member.vo.ContractVO;
import com.hanaph.gw.pe.member.vo.MemberVO;

/**
 * <pre>
 * Class Name : ContractController.java
 * 설명 : 연봉계약서 정보 Controller
 * 
 * Modification Information
 *    수정일      수정자              수정 내용
 *  ------------ -------------- --------------------------------
 *  2014. 11. 4.      CHOIILJI         
 * </pre>
 * 
 * @version : 
 * @author  : CHOIILJI(choiilji@irush.co.kr)
 * @since   : 2014. 11. 4.
 */
@Controller
public class ContractController {

	@Autowired 
	private ContractService contractService;
	
	@Autowired
	private MenuService menuService;
	
	@Autowired
	private AuthorityService authorityService;
	/**
	 * 
	 * <pre>
	 * 1. 개요     : 연봉계약서 리스트
	 * 2. 처리내용 : 연봉계약서 리스트 가져온다.
	 * </pre>
	 * @Method Name : contractList
	 * @param request
	 * @return
	 */
	@RequestMapping("/pe/member/contractList.do")
	public ModelAndView contractList(HttpServletRequest request){
		
		final String MENU_CHILD= "0407";
		
		ModelAndView mav = new ModelAndView("pe/member/contractList");
		Map<String, String> paramMap = new HashMap<String, String>();  
		
		int currentPage = Integer.parseInt(StringUtil.nvl(request.getParameter("currentPage"),"1"));	// 현재 페이지 번호
		int plRowRange = 10;	//한페이지당 보여줄 게시물 수 	
		int plPageRange = 10;	//페이지 번호 수
		
		//세션에서 회원정보 가져 온다.
		HttpSession session = request.getSession(false);
		MemberVO memberSessionVO = (MemberVO) session.getAttribute("gwUser");
		String emp_no = memberSessionVO.getEmp_no();
		
		paramMap.put("emp_no", emp_no);
		paramMap.put("page",String.valueOf(currentPage));		
		paramMap.put("perPageRow", String.valueOf(plRowRange));
		
		List<ContractVO> contractList = contractService.getContractList(paramMap);
		int contractTotalCount = contractService.getContractCount(paramMap);
		
		//페이징
		PageUtil pu = new PageUtil();
		String pagingStr = pu.autoPaging(contractTotalCount, plRowRange, plPageRange, currentPage);
		
		//LNB 메뉴 생성 START
		String menu_parents = StringUtil.substring(MENU_CHILD, 2);
		MenuUtil mu = new MenuUtil();
		List<MenuVO> lnbMenuList = mu.makeLnb(request, menu_parents , menuService);
		mav.addObject("lnbMenuList", lnbMenuList);
		mav.addObject("MENU_CHILD", MENU_CHILD);
		//LNB 메뉴 생성 END
				
		mav.addObject("contractList", contractList);
		mav.addObject("currentPage", currentPage);
		mav.addObject("plRowRange", plRowRange);
		mav.addObject("plPageRange", plPageRange);
		mav.addObject("pagingStr", pagingStr);
		mav.addObject("contractTotalCount", contractTotalCount);
		
		return mav;
	}
	
	/**
	 * 
	 * <pre>
	 * 1. 개요     : 연봉계약서상세 정보
	 * 2. 처리내용 : 연봉계약서상세 정보 가져온다.
	 * </pre>
	 * @Method Name : contractDetail
	 * @param request
	 * @return
	 */
	@RequestMapping("/pe/member/contractDetail.do")
	public ModelAndView contractDetail(HttpServletRequest request){
		
		final String MENU_CHILD= "0407";
		
		ModelAndView mav = new ModelAndView("pe/member/contractDetail");
		Map<String, String> paramMap = new HashMap<String, String>();
		
		String ymd_start = StringUtil.nvl((String)request.getParameter("ymd_start"), "");
		String ymd_end = StringUtil.nvl((String)request.getParameter("ymd_end"), "");
		String currentPage = StringUtil.nvl(request.getParameter("currentPage"), "1");
		
		//세션에서 회원정보 가져 온다.
		HttpSession session = request.getSession(false);
		MemberVO memberSessionVO = (MemberVO) session.getAttribute("gwUser");
		String emp_no = memberSessionVO.getEmp_no();
		
		paramMap.put("emp_no", emp_no);
		paramMap.put("ymd_start", ymd_start);
		paramMap.put("ymd_end", ymd_end);
		
		List<ContractVO> contractList = contractService.getContractList(paramMap);
		
		//관리자 권한 리스트 가져오기
		int [] authIdxArray = {1,2};
		Map<String, Object> paramMap1 = new HashMap<String, Object>();
		paramMap1.put("authIdxArray", authIdxArray);
		List<MemberVO> authorityMemberList = authorityService.getAuthorityMemberList(paramMap1);
		
		//LNB 메뉴 생성 START
		String menu_parents = StringUtil.substring(MENU_CHILD, 2);
		MenuUtil mu = new MenuUtil();
		List<MenuVO> lnbMenuList = mu.makeLnb(request, menu_parents , menuService);
		mav.addObject("lnbMenuList", lnbMenuList);
		mav.addObject("MENU_CHILD", MENU_CHILD);
		//LNB 메뉴 생성 END
		
		mav.addObject("currentPage", currentPage);
		mav.addObject("contractList", contractList);
		mav.addObject("authorityMemberList", authorityMemberList);
		
        return mav;
	}
}
