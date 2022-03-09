/**
  * Hana Project
  * Copyright 2014 iRush Co.,
  *
  */
package com.hanaph.gw.ea.share.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.hanaph.gw.co.code.service.CodeService;
import com.hanaph.gw.co.code.vo.CodeVO;
import com.hanaph.gw.co.common.utils.CommonUtil;
import com.hanaph.gw.co.common.utils.Environment;
import com.hanaph.gw.co.common.utils.MarshallerUtil;
import com.hanaph.gw.co.common.utils.MenuUtil;
import com.hanaph.gw.co.common.utils.PageUtil;
import com.hanaph.gw.co.common.utils.StringUtil;
import com.hanaph.gw.co.common.utils.WebUtil;
import com.hanaph.gw.co.menu.service.MenuService;
import com.hanaph.gw.co.menu.vo.MenuVO;
import com.hanaph.gw.ea.share.service.ShareReportService;
import com.hanaph.gw.ea.share.vo.ShareReportVO;
import com.hanaph.gw.pe.member.vo.MemberVO;

/**
 * <pre>
 * Class Name : ShareReportController.java
 * 설명 : 공유문서 조회 Controller
 * 
 * Modification Information
 *    수정일      수정자              수정 내용
 *  ------------ -------------- --------------------------------
 *  2015. 1. 21.      CHOIILJI         
 * </pre>
 * 
 * @version : 1.0
 * @author  : CHOIILJI(choiilji@irush.co.kr)
 * @since   : 2015. 1. 21.
 */
@Controller
public class ShareReportController {
	private Environment env = new Environment();
	
	@Autowired
	private ShareReportService shareReportService;
	
	@Autowired
	private MenuService menuService;
	
	@Autowired
	private CodeService codeService;
	
	private static final Logger logger = Logger.getLogger(ShareReportController.class);
	/**
	 * 
	 * <pre>
	 * 1. 개요     : 공유문서조회 결재중 리스트
	 * 2. 처리내용 : 공유문서조회 결재중 리스트
	 * </pre>
	 * @Method Name : getShareList
	 * @param request
	 * @return
	 */
	@RequestMapping("/ea/share/shareList.do")
	public ModelAndView getShareList(HttpServletRequest request){
		
		final String MENU_CHILD = "0206";	
		
		ModelAndView mav = new ModelAndView("ea/share/shareList");
		Map<String, String> paramMap = new HashMap<String, String>();  
		
		String searchDate[] = WebUtil.dateCal(-2);
		
		String search_start_dt = StringUtil.nvl((String)request.getParameter("search_start_dt"), searchDate[0]);//기안시작일
		String search_end_dt = StringUtil.nvl((String)request.getParameter("search_end_dt"), searchDate[1]);//기안종료일
		String search_docu_cd = StringUtil.nvl((String)request.getParameter("search_docu_cd"));//문서종류
		String search_condition = StringUtil.nvl((String)request.getParameter("search_condition"));//검색조건
		String search_text = StringUtil.nvl((String)request.getParameter("search_text"));//검색어
		String search_read_yn = StringUtil.nvl((String)request.getParameter("search_read_yn"));//열람여부

		String checkMustOpinion = StringUtil.nvl(request.getParameter("checkMustOpinion"),"");//시행부서 의견 필수 상태

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
		paramMap.put("search_docu_cd", search_docu_cd);
		paramMap.put("search_condition", search_condition);
		paramMap.put("search_text", search_text);
		paramMap.put("search_read_yn", search_read_yn);
		paramMap.put("page",String.valueOf(currentPage));		
		paramMap.put("perPageRow", String.valueOf(plRowRange));

		paramMap.put("checkMustOpinion", checkMustOpinion); //시행부서 의견 필수 상태

		//시행문서 목록 리스트
		List<ShareReportVO> shareList = shareReportService.getShareList(paramMap);
		int shareTotalCount = shareReportService.getShareCount(paramMap);
		
		//문서정보 검색 리스트
		paramMap.put("m_cd", "E01");
		List<CodeVO> sCodeList = codeService.getScodeList(paramMap);
		mav.addObject("sCodeList", sCodeList);
		
		//페이징
		PageUtil pu = new PageUtil();
		String pagingStr = pu.autoPaging(shareTotalCount, plRowRange, plPageRange, currentPage);
		
		//LNB 메뉴 생성 START
		String menu_parents = StringUtil.substring(MENU_CHILD, 2);
		MenuUtil mu = new MenuUtil();
		List<MenuVO> lnbMenuList = mu.makeLnb(request, menu_parents , menuService);
		mav.addObject("lnbMenuList", lnbMenuList);
		mav.addObject("MENU_CHILD", MENU_CHILD);
		//LNB 메뉴 생성 END
				
		mav.addObject("shareList", shareList);
		mav.addObject("search_start_dt", search_start_dt);
		mav.addObject("search_end_dt", search_end_dt);
		mav.addObject("search_docu_cd", search_docu_cd);
		mav.addObject("search_condition", search_condition);
		mav.addObject("search_text", search_text);
		mav.addObject("search_read_yn", search_read_yn);
		mav.addObject("currentPage", currentPage);
		mav.addObject("plRowRange", plRowRange);
		mav.addObject("plPageRange", plPageRange);
		mav.addObject("pagingStr", pagingStr);
		mav.addObject("shareTotalCount", shareTotalCount);
		mav.addObject("sCodeList", sCodeList);
		mav.addObject("menu", MENU_CHILD);

		/*시행부서 의견 필수 유무 */
		mav.addObject("checkMustOpinion", checkMustOpinion);
		
		return mav;
	}
	
	/**
	 * 
	 * <pre>
	 * 1. 개요     : 공유문서 대상자
	 * 2. 처리내용 : 공유문서 대상자 저장한다.
	 * </pre>
	 * @Method Name : insertShareTarget
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/ea/share/insertShareTarget.do")
	public void insertShareTarget(HttpServletRequest request, HttpServletResponse response) throws IOException{
		
		Map<String, String> paramMap = new HashMap<String, String>();
		
		HttpSession session = request.getSession();
		MemberVO memberSessionVO = (MemberVO) session.getAttribute("gwUser");
		
		String create_no = memberSessionVO.getEmp_no();
		String approval_seq = StringUtil.nvl((String)request.getParameter("approval_seq"));
		String emp_no = StringUtil.nvl((String)request.getParameter("emp_no"));
		String menu = StringUtil.nvl((String)request.getParameter("menu"));
		
		String[] emp_noArr;
		
		if(!emp_no.equals("") && emp_no != null){
			emp_noArr = emp_no.split(",");
		}else{
			emp_noArr = new String[0];
		}

		paramMap.put("approval_seq", approval_seq);
		List<ShareReportVO> shareVOList = new ArrayList<ShareReportVO>();
		ShareReportVO shareVO = new ShareReportVO();
		logger.debug(">>>>>>>>>>>>>>>>>>>>>공유타켓");
		if(emp_noArr.length > 0){
			String share_emp_no = "";
			for (int i = 0; i < emp_noArr.length; i++) {
				share_emp_no = emp_noArr[i];
				
				ShareReportVO share = new ShareReportVO();
				share.setApproval_seq(approval_seq);
				share.setEmp_no(share_emp_no);
				share.setCreate_no(create_no);
				shareVOList.add(share);
			}
			shareVO.setShareVO(shareVOList);
		}
		
		int iResult = shareReportService.insertShareTarget(shareVO);
		
		if(iResult > 0){
			paramMap.put("approval_seq", approval_seq);
			paramMap.put("type", "SHARE");
			paramMap.put("menu", menu);
			paramMap.put("returnURL", env.getValue("root_dir.url")+"/ea/newReport/approvalPopup.do");
			MarshallerUtil.marshalling("txt", response, CommonUtil.postMessageView("공유대상 임직원이 저장 되었습니다.", paramMap));
		}else{
			String script = "<script>alert('요청 처리 과정에서 에러가 발생하였습니다. 잠시 후 다시 시도하시거나 전산팀에 문의 바랍니다.'); parent.addShare();</script>";
			MarshallerUtil.marshalling("txt", response, script);
		}
	}
	
	/**
	 * 
	 * <pre>
	 * 1. 개요     : 공유문서 대상자
	 * 2. 처리내용 : 공유문서 대상자 목록 가져온다.
	 * </pre>
	 * @Method Name : shareTargetListPopup
	 * @param request
	 * @return
	 */
	@RequestMapping("/ea/share/shareTargetListPopup.do")
	public ModelAndView shareTargetListPopup(HttpServletRequest request){
		
		ModelAndView mav = new ModelAndView("ea/share/shareTargetListPopup");
		
		Map<String, String> paramMap = new HashMap<String, String>();  
		
		String approval_seq = StringUtil.nvl(request.getParameter("approval_seq"));
		
		paramMap.put("approval_seq", approval_seq);
		List<MemberVO> shareTargetList = shareReportService.getShareTargetList(paramMap);
		mav.addObject("shareTargetList", shareTargetList);
		
        return mav;
		
	}
}
