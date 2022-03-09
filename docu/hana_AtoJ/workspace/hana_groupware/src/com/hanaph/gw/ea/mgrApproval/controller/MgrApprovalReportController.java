/**
  * Hana Project
  * Copyright 2014 iRush Co.,
  *
  */
package com.hanaph.gw.ea.mgrApproval.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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
import com.hanaph.gw.ea.mgrApproval.service.MgrApprovalReportService;
import com.hanaph.gw.ea.mgrApproval.vo.MgrApprovalReportVO;

/**
 * <pre>
 * Class Name : MgrReportController.java
 * 설명 : 관리자 문서 목록 Controller 
 * 
 * Modification Information
 *    수정일      수정자              수정 내용
 *  ------------ -------------- --------------------------------
 *  2015. 1. 22.      CHOIILJI         
 * </pre>
 * 
 * @version : 1.0
 * @author  : CHOIILJI(choiilji@irush.co.kr)
 * @since   : 2015. 1. 22.
 */
@Controller
public class MgrApprovalReportController {
	@Autowired
	private MgrApprovalReportService mgrReportApprovalService;
	
	@Autowired
	private MenuService menuService;
	
	@Autowired
	private CodeService codeService;
	
	/**
	 * 
	 * <pre>
	 * 1. 개요     : 관리자 문서 목록 리스트
	 * 2. 처리내용 : 관리자 문서 목록 리스트
	 * </pre>
	 * @Method Name : getMgrApprovalReportList
	 * @param request
	 * @return
	 */
	@RequestMapping("/ea/mgrApproval/mgrApprovalReportList.do")
	public ModelAndView getMgrReportList(HttpServletRequest request){
		
		final String MENU_CHILD = "020501";	
		
		ModelAndView mav = new ModelAndView("ea/mgrApproval/mgrApprovalReportList");
		Map<String, String> paramMap = new HashMap<String, String>();  
		
		String searchDate[] = WebUtil.dateCal(-2);
		
		String search_start_dt = StringUtil.nvl((String)request.getParameter("search_start_dt"), searchDate[0]);//기안시작일
		String search_end_dt = StringUtil.nvl((String)request.getParameter("search_end_dt"), searchDate[1]);//기안종료일
		String search_docu_cd = StringUtil.nvl((String)request.getParameter("search_docu_cd"));//문서종류
		String search_condition = StringUtil.nvl((String)request.getParameter("search_condition"));//검색조건
		String search_text = StringUtil.nvl((String)request.getParameter("search_text"));//검색어
		String search_state = StringUtil.nvl((String)request.getParameter("search_state"));//결재상태

		String checkMustOpinion = StringUtil.nvl(request.getParameter("checkMustOpinion"),"");				//시행부서 의견 필수 상태

		int currentPage = Integer.parseInt(StringUtil.nvl(request.getParameter("currentPage"),"1"));	// 현재 페이지 번호
		int plRowRange = 10;	//한페이지당 보여줄 게시물 수 	
		int plPageRange = 10;	//페이지 번호 수
		
		paramMap.put("search_start_dt", search_start_dt.replaceAll("-", ""));
		paramMap.put("search_end_dt", search_end_dt.replaceAll("-", ""));
		paramMap.put("search_docu_cd", search_docu_cd);
		paramMap.put("search_condition", search_condition);
		paramMap.put("search_text", search_text);
		paramMap.put("search_state", search_state);
		paramMap.put("page",String.valueOf(currentPage));		
		paramMap.put("perPageRow", String.valueOf(plRowRange));

		paramMap.put("checkMustOpinion", checkMustOpinion); //시행부서 의견 필수 상태

		//문서 목록 리스트
		List<MgrApprovalReportVO> mgrApprovalReportList = mgrReportApprovalService.getMgrApprovalReportList(paramMap);
		int mgrApprovalReportTotalCount = mgrReportApprovalService.getMgrApprovalReportCount(paramMap);
		
		//문서정보 코드리스트
		paramMap.put("m_cd", "E01");
		List<CodeVO> docuCodeList = codeService.getScodeList(paramMap);
		mav.addObject("docuCodeList", docuCodeList);
		
		//문서상태 코드리스트
		paramMap.put("m_cd", "E02");
		List<CodeVO> docuStateCodeList = codeService.getScodeList(paramMap);
		mav.addObject("docuStateCodeList", docuStateCodeList);
		
		//페이징
		PageUtil pu = new PageUtil();
		String pagingStr = pu.autoPaging(mgrApprovalReportTotalCount, plRowRange, plPageRange, currentPage);
		
		//LNB 메뉴 생성 START
		String menu_parents = StringUtil.substring(MENU_CHILD, 2);
		MenuUtil mu = new MenuUtil();
		List<MenuVO> lnbMenuList = mu.makeLnb(request, menu_parents , menuService);
		mav.addObject("lnbMenuList", lnbMenuList);
		mav.addObject("MENU_CHILD", MENU_CHILD);
		//LNB 메뉴 생성 END
				
		mav.addObject("mgrApprovalReportList", mgrApprovalReportList);
		mav.addObject("search_start_dt", search_start_dt);
		mav.addObject("search_end_dt", search_end_dt);
		mav.addObject("search_docu_cd", search_docu_cd);
		mav.addObject("search_condition", search_condition);
		mav.addObject("search_text", search_text);
		mav.addObject("search_state", search_state);
		mav.addObject("currentPage", currentPage);
		mav.addObject("plRowRange", plRowRange);
		mav.addObject("plPageRange", plPageRange);
		mav.addObject("pagingStr", pagingStr);
		mav.addObject("mgrApprovalReportTotalCount", mgrApprovalReportTotalCount);
		mav.addObject("docuCodeList", docuCodeList);
		mav.addObject("docuStateCodeList", docuStateCodeList);
		mav.addObject("menu", MENU_CHILD);

		/*시행부서 의견 필수 유무 */
		mav.addObject("checkMustOpinion", checkMustOpinion);
		
		return mav;
	}
}
