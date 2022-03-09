/**
  * Hana Project
  * Copyright 2014 iRush Co.,
  *
  */
package com.hanaph.gw.ea.report.controller;

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
import com.hanaph.gw.ea.report.service.ReportService;
import com.hanaph.gw.ea.report.vo.ReportVO;
import com.hanaph.gw.pe.member.vo.MemberVO;

/**
 * <pre>
 * Class Name : ReportController.java
 * 설명 : 
 * 
 * Modification Information
 *    수정일      수정자              수정 내용
 *  ------------ -------------- --------------------------------
 *  2015. 1. 19.      Jung.Jin.Muk          
 * </pre>
 * 
 * @version : 
 * @author  : Jung.Jin.Muk(pc123pc@irush.co.kr)
 * @since   : 2015. 1. 19.
 */
@Controller
public class ReportController {
	
	@Autowired 
	private MenuService menuService;
	
	@Autowired
	private CodeService codeService;
	
	@Autowired
	private ReportService reportService;
	
	/**
	 * <pre>
	 * 1. 개요     : 내가올린문서 리스트
	 * 2. 처리내용 : 내가올린문서 리스트를 가져온다.
	 * </pre>
	 * @Method Name : reportList
	 * @param request
	 * @return
	 */
	@RequestMapping("/ea/report/reportList.do")
	public ModelAndView reportList(HttpServletRequest request){
		
		ModelAndView mav = new ModelAndView("ea/report/reportList");
		
		/*LNB 메뉴 생성 START*/
		final String MENU_CHILD = "0202";
		String menu_parents = StringUtil.substring(MENU_CHILD, 2);
		MenuUtil mu = new MenuUtil();
		List<MenuVO> lnbMenuList = mu.makeLnb(request, menu_parents , menuService);
		mav.addObject("lnbMenuList", lnbMenuList);
		mav.addObject("MENU_CHILD", MENU_CHILD);
		/*LNB 메뉴 생성 END*/
		
		int currentPage = Integer.parseInt(StringUtil.nvl(request.getParameter("currentPage"),"1"));	// 현재 페이지 번호
		int plRowRange = 10;	
		int plPageRange = 10;
		
		String searchDate[] = WebUtil.dateCal(-2);
		String search_docu_state = StringUtil.nvl(request.getParameter("search_docu_state"),"all");				//문서상태
		String search_docu_kind = StringUtil.nvl(request.getParameter("search_docu_kind"),"all");				//문서종류
		String searchType = StringUtil.nvl(request.getParameter("searchType"),"approval_seq");					//문서번호,제목 검색
		String searchKeyword = StringUtil.nvl(request.getParameter("searchKeyword"));							//검색어
		String search_start_ymd = StringUtil.nvl(request.getParameter("search_start_ymd"),searchDate[0]);		//기안 시작 날짜
		String search_end_ymd = StringUtil.nvl(request.getParameter("search_end_ymd"),searchDate[1]);			//기안 마지 막날짜
		
		HttpSession session = request.getSession();
		MemberVO memberSessionVO = (MemberVO) session.getAttribute("gwUser");
		
		/*문서종류,상태 코드 리스트*/
		Map<String, String> codeParmaMap = new HashMap<String, String>();
		codeParmaMap.put("m_cd", "E01");
		List<CodeVO> docuKindList = codeService.getCodeList(codeParmaMap);
		codeParmaMap.put("m_cd", "E02");
		List<CodeVO> docuStateList = codeService.getCodeList(codeParmaMap);
		
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("search_docu_state", search_docu_state);
		paramMap.put("search_docu_kind", search_docu_kind);
		paramMap.put("searchType", searchType);
		paramMap.put("searchKeyword", searchKeyword);
		paramMap.put("search_start_ymd", search_start_ymd);
		paramMap.put("search_end_ymd", search_end_ymd);
		paramMap.put("page",String.valueOf(currentPage));		
		paramMap.put("perPageRow", String.valueOf(plRowRange));
		paramMap.put("emp_no", memberSessionVO.getEmp_no());
		
		List<ReportVO> reportList = reportService.getReportList(paramMap);	//내가올린문서 리스트
		int cnt = reportService.getReportCount(paramMap);	//내가올린문서 카운트
		
		PageUtil pu = new PageUtil();
		String pagingStr = pu.autoPaging(cnt, plRowRange, plPageRange, currentPage);
		
		/*검색 파라미터*/
		mav.addObject("search_docu_state", search_docu_state);
		mav.addObject("search_docu_kind", search_docu_kind);
		mav.addObject("searchType", searchType);
		mav.addObject("searchKeyword", searchKeyword);
		mav.addObject("search_start_ymd", search_start_ymd);
		mav.addObject("search_end_ymd", search_end_ymd);
		
		/*페이징 파라미터*/
		mav.addObject("pagingStr", pagingStr);
		mav.addObject("cnt", cnt);
		
		/*내가올린문서*/
		mav.addObject("reportList", reportList);
		
		/*문서종류 리스트*/
		mav.addObject("docuKindList", docuKindList);
		
		/*문서상태 리스트*/
		mav.addObject("docuStateList", docuStateList);
		
		return mav;
	}

}
