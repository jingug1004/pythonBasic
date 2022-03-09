/**
  * Hana Project
  * Copyright 2014 iRush Co.,
  *
  */
package com.hanaph.gw.ea.implement.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.hanaph.gw.co.code.service.CodeService;
import com.hanaph.gw.co.code.vo.CodeVO;
import com.hanaph.gw.co.common.utils.MenuUtil;
import com.hanaph.gw.co.common.utils.PageUtil;
import com.hanaph.gw.co.common.utils.StringUtil;
import com.hanaph.gw.co.common.utils.WebUtil;
import com.hanaph.gw.co.menu.service.MenuService;
import com.hanaph.gw.co.menu.vo.MenuVO;
import com.hanaph.gw.ea.implement.service.ImplementReportService;
import com.hanaph.gw.ea.implement.vo.ImplementReportVO;
import com.hanaph.gw.pe.member.vo.MemberVO;

/**
 * <pre>
 * Class Name : ImplementReportController.java
 * 설명 : 시행문서조회 Controller
 * 
 * Modification Information
 *    수정일      수정자              수정 내용
 *  ------------ -------------- --------------------------------
 *  2015. 1. 16.      CHOIILJI         
 * </pre>
 * 
 * @version : 1.0
 * @author  : CHOIILJI(choiilji@irush.co.kr)
 * @since   : 2015. 1. 16.
 */
@Controller
public class ImplementReportController {
	@Autowired
	private ImplementReportService implementReportService; 
	
	@Autowired
	private MenuService menuService;
	
	@Autowired
	private CodeService codeService;
	
	/**
	 * 
	 * <pre>
	 * 1. 개요     : 시행문서조회 결재중/결재완료 리스트
	 * 2. 처리내용 : 시행문서조회 결재중/결재완료 리스트
	 * </pre>
	 * @Method Name : getImplementList
	 * @param request
	 * @return
	 */
	@RequestMapping("/ea/implement/implementList.do")
	public ModelAndView getImplementList(HttpServletRequest request){
		
		String MENU_CHILD = "020701";	
		String approval_type = StringUtil.nvl((String)request.getParameter("approval_type"), "E03001");
		if(approval_type.equals("E03002")){
			MENU_CHILD = "020702";	
		}
		
		ModelAndView mav = new ModelAndView("ea/implement/implementList");
		Map<String, String> paramMap = new HashMap<String, String>();  
		
		String searchDate[] = WebUtil.dateCal(-2);
		
		String search_start_dt = StringUtil.nvl((String)request.getParameter("search_start_dt"), searchDate[0]);//기안시작일
		String search_end_dt = StringUtil.nvl((String)request.getParameter("search_end_dt"), searchDate[1]);//기안종료일
		String search_docu_cd = StringUtil.nvl((String)request.getParameter("search_docu_cd"));//문서종류
		String search_condition = StringUtil.nvl((String)request.getParameter("search_condition"));//검색조건
		String search_text = StringUtil.nvl((String)request.getParameter("search_text"));//검색어
		String search_read_yn = StringUtil.nvl((String)request.getParameter("search_read_yn"));//열람여부
		String search_interest_yn = StringUtil.nvl((String)request.getParameter("search_interest_yn"));//관심여부

		String checkMustOpinion = StringUtil.nvl(request.getParameter("checkMustOpinion"),"");				//시행부서 의견 필수 상태

		int currentPage = Integer.parseInt(StringUtil.nvl(request.getParameter("currentPage"),"1"));	// 현재 페이지 번호
		int plRowRange = 10;	//한페이지당 보여줄 게시물 수 	
		int plPageRange = 10;	//페이지 번호 수
		
		//세션에서 회원정보 가져 온다.
		HttpSession session = request.getSession(false);
		MemberVO memberSessionVO = (MemberVO) session.getAttribute("gwUser");
		String emp_no = memberSessionVO.getEmp_no();
		
		paramMap.put("emp_no", emp_no);
		paramMap.put("approval_type", approval_type);
		paramMap.put("search_start_dt", search_start_dt.replaceAll("-", ""));
		paramMap.put("search_end_dt", search_end_dt.replaceAll("-", ""));
		paramMap.put("search_docu_cd", search_docu_cd);
		paramMap.put("search_condition", search_condition);
		paramMap.put("search_text", search_text);
		paramMap.put("search_read_yn", search_read_yn);
		paramMap.put("search_interest_yn", search_interest_yn);
		paramMap.put("page",String.valueOf(currentPage));		
		paramMap.put("perPageRow", String.valueOf(plRowRange));

		paramMap.put("checkMustOpinion", checkMustOpinion);

		paramMap.put("dept_cd", memberSessionVO.getDept_cd());
		//시행문서 목록 리스트
		List<ImplementReportVO> implementList = implementReportService.getImplementList(paramMap);
		int implementTotalCount = implementReportService.getImplementCount(paramMap);
		
		//문서정보 검색 리스트
		paramMap.put("m_cd", "E01");
		List<CodeVO> sCodeList = codeService.getScodeList(paramMap);
		mav.addObject("sCodeList", sCodeList);
		
		//페이징
		PageUtil pu = new PageUtil();
		String pagingStr = pu.autoPaging(implementTotalCount, plRowRange, plPageRange, currentPage);
		
		//LNB 메뉴 생성 START
		String menu_parents = StringUtil.substring(MENU_CHILD, 2);
		MenuUtil mu = new MenuUtil();
		List<MenuVO> lnbMenuList = mu.makeLnb(request, menu_parents , menuService);
		mav.addObject("lnbMenuList", lnbMenuList);
		mav.addObject("MENU_CHILD", MENU_CHILD);
		//LNB 메뉴 생성 END
				
		mav.addObject("implementList", implementList);
		mav.addObject("approval_type", approval_type);
		mav.addObject("search_start_dt", search_start_dt);
		mav.addObject("search_end_dt", search_end_dt);
		mav.addObject("search_docu_cd", search_docu_cd);
		mav.addObject("search_condition", search_condition);
		mav.addObject("search_text", search_text);
		mav.addObject("search_read_yn", search_read_yn);
		mav.addObject("search_interest_yn", search_interest_yn);
		mav.addObject("currentPage", currentPage);
		mav.addObject("plRowRange", plRowRange);
		mav.addObject("plPageRange", plPageRange);
		mav.addObject("pagingStr", pagingStr);
		mav.addObject("implementTotalCount", implementTotalCount);
		mav.addObject("sCodeList", sCodeList);
		mav.addObject("menu", MENU_CHILD);

		/*시행부서 의견 필수 유무 */
		mav.addObject("checkMustOpinion", checkMustOpinion);
		
		return mav;
	}
}
