/**
  * Hana Project
  * Copyright 2014 iRush Co.,
  *
  */
package com.hanaph.gw.ea.receive.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.hanaph.gw.co.authority.service.AuthorityService;
import com.hanaph.gw.co.code.service.CodeService;
import com.hanaph.gw.co.code.vo.CodeVO;
import com.hanaph.gw.co.common.utils.Environment;
import com.hanaph.gw.co.common.utils.MarshallerUtil;
import com.hanaph.gw.co.common.utils.MenuUtil;
import com.hanaph.gw.co.common.utils.PageUtil;
import com.hanaph.gw.co.common.utils.StringUtil;
import com.hanaph.gw.co.common.utils.WebUtil;
import com.hanaph.gw.co.menu.service.MenuService;
import com.hanaph.gw.co.menu.vo.MenuVO;
import com.hanaph.gw.ea.newReport.service.ApprovalService;
import com.hanaph.gw.ea.newReport.service.NewReportE01001Service;
import com.hanaph.gw.ea.newReport.service.NewReportService;
import com.hanaph.gw.ea.newReport.vo.ApprovalMasterVO;
import com.hanaph.gw.ea.newReport.vo.ApprovalVO;
import com.hanaph.gw.ea.newReport.vo.ImplDeptEmpVO;
import com.hanaph.gw.ea.newReport.vo.VacationVO;
import com.hanaph.gw.ea.receive.service.ReceiveService;
import com.hanaph.gw.ea.receive.vo.ReceiveVO;
import com.hanaph.gw.pe.member.vo.AnnualVO;
import com.hanaph.gw.pe.member.vo.MemberVO;

/**
 * <pre>
 * Class Name : ReceiveController.java
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
public class ReceiveController {
	
	@Autowired 
	private MenuService menuService;
	
	@Autowired
	private CodeService codeService;
	
	@Autowired
	private ReceiveService receiveService;
	
	@Autowired
	private NewReportService newReportService;
	
	@Autowired
	private ApprovalService approvalService;
	
	@Autowired
	private NewReportE01001Service newReportE01001Service;
	
	@Autowired
	private AuthorityService authorityService;
	
	private Environment env = new Environment();
	
	/**
	 * <pre>
	 * 1. 개요     : 내가받은문서 리스트
	 * 2. 처리내용 : 내가받은문서 리스트를 가져온다.
	 * </pre>
	 * @Method Name : receiveList
	 * @param request
	 * @return
	 */
	@RequestMapping("/ea/receive/receiveList.do")
	public ModelAndView receiveList(HttpServletRequest request){
		
		ModelAndView mav = new ModelAndView("ea/receive/receiveList");
		
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
		String search_appr_state = StringUtil.nvl(request.getParameter("search_appr_state"),"all");				//결재상태
		String searchType = StringUtil.nvl(request.getParameter("searchType"),"approval_seq");					//문서번호,제목 검색
		String searchKeyword = StringUtil.nvl(request.getParameter("searchKeyword"));							//검색어
		String search_start_ymd = StringUtil.nvl(request.getParameter("search_start_ymd"),searchDate[0]);		//기안 시작 날짜
		String search_end_ymd = StringUtil.nvl(request.getParameter("search_end_ymd"),searchDate[1]);			//기안 마지 막날짜
		
		HttpSession session = request.getSession();
		MemberVO memberSessionVO = (MemberVO) session.getAttribute("gwUser");
		
		Map<String, String> codeParmaMap = new HashMap<String, String>();
		/*문서종류*/
		codeParmaMap.put("m_cd", "E01");
		List<CodeVO> docuKindList = codeService.getCodeList(codeParmaMap);
		/*문서상태*/
		codeParmaMap.put("m_cd", "E02");
		List<CodeVO> docuStateList = codeService.getCodeList(codeParmaMap);
		/*결재상태*/
		codeParmaMap.put("m_cd", "E03");
		List<CodeVO> apprState = codeService.getCodeList(codeParmaMap);
		
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("search_docu_state", search_docu_state);
		paramMap.put("search_docu_kind", search_docu_kind);
		paramMap.put("search_appr_state", search_appr_state);
		
		paramMap.put("searchType", searchType);
		paramMap.put("searchKeyword", searchKeyword);
		paramMap.put("search_start_ymd", search_start_ymd);
		paramMap.put("search_end_ymd", search_end_ymd);
		paramMap.put("page",String.valueOf(currentPage));		
		paramMap.put("perPageRow", String.valueOf(plRowRange));
		paramMap.put("emp_no", memberSessionVO.getEmp_no());
		
		List<ReceiveVO> receiveList = receiveService.getReceiveList(paramMap);	//내가받은문서 리스트
		int cnt = receiveService.getReceiveCount(paramMap);	//내가받은문서 카운트
		
		/*관리자 권한 리스트 가져오기*/
		int [] authIdxArray = {1};
		Map<String, Object> paramMap1 = new HashMap<String, Object>();
		paramMap1.put("authIdxArray", authIdxArray);
		List<MemberVO> authorityMemberList = authorityService.getAuthorityMemberList(paramMap1);
		
		PageUtil pu = new PageUtil();
		String pagingStr = pu.autoPaging(cnt, plRowRange, plPageRange, currentPage);
		
		/*검색 파라미터*/
		mav.addObject("search_appr_state", search_appr_state);
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
		mav.addObject("receiveList", receiveList);
		
		/*문서종류 리스트*/
		mav.addObject("docuKindList", docuKindList);
		
		/*문서상태 리스트*/
		mav.addObject("docuStateList", docuStateList);
		
		/*결재상태 리스트*/
		mav.addObject("apprState", apprState);
		
		/*관리자 리스트*/
		mav.addObject("authorityMemberList", authorityMemberList);
		
		
		/*세션정보*/
		mav.addObject("emp_ko_nm", memberSessionVO.getEmp_ko_nm());
		mav.addObject("emp_no", memberSessionVO.getEmp_no());
		
		return mav;
	}
	
	/**
	 * <pre>
	 * 1. 개요     : 결재승인,반려 레이어 팝업
	 * 2. 처리내용 : 결재승인,반려 레이어 팝업
	 * </pre>
	 * @Method Name : approvalPopup
	 * @param request
	 * @return
	 */
	@RequestMapping("/ea/receive/approvalPopup.do")
	public ModelAndView approvalPopup(HttpServletRequest request){
		
		ModelAndView mav = new ModelAndView("ea/receive/approvalPopup");
		
		HttpSession session = request.getSession();
		MemberVO memberSessionVO = (MemberVO) session.getAttribute("gwUser");
		
		String gubun = StringUtil.nvl(request.getParameter("gubun"));
		String approval_seq = StringUtil.nvl(request.getParameter("approval_seq"));
		
		/*세션정보*/
		mav.addObject("emp_ko_nm", memberSessionVO.getEmp_ko_nm());
		
		/*결재 상태*/
		mav.addObject("gubun", gubun);
		
		/*결재 문서번호*/
		mav.addObject("approval_seq", approval_seq);
		
		return mav;
	}
	
	/**
	 * <pre>
	 * 1. 개요     : 결재승인,결재반려
	 * 2. 처리내용 : 결재승인,결재반려
	 * </pre>
	 * @Method Name : updateAgreeApproval
	 * @param request
	 * @param response
	 * @param map
	 * @throws Exception 
	 */
	@RequestMapping("/ea/receive/updateApproval.do")
	public void updateApproval(HttpServletRequest request, HttpServletResponse response, @RequestParam Map<String,String> map)
			throws Exception{
		
		HttpSession session = request.getSession();
		MemberVO memberSessionVO = (MemberVO) session.getAttribute("gwUser");
		
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
		
		String approval_seq = StringUtil.nvl(request.getParameter("approval_seq"));	//문서번호
		String gubun = StringUtil.nvl(request.getParameter("gubun"));	//결재구분
		String state = StringUtil.nvl(request.getParameter("state"));	//결재상태
		String masterState = StringUtil.nvl(request.getParameter("masterState"));	//결재마스터상태
		String rejection_reason = StringUtil.nvl(request.getParameter("rejection_reason"));	//반려사유
		String docu_cd = StringUtil.nvl(request.getParameter("docu_cd"));	//문서종류
		//System.out.println("docu_cd:: " + docu_cd);
		AnnualVO annualVO = new AnnualVO();
		/*휴가신청서 연차 정보*/
		if("E01001".equals(docu_cd)){
			VacationVO vacationVO = newReportE01001Service.vacationDetail(approval_seq);
			ApprovalMasterVO approvalMasterVO = newReportService.approvalDetail(approval_seq);
			
			float rq_wk_day = 0;
			/*반차는 0.5일을 셋팅한다*/
			if("E06220".equals(vacationVO.getCd())){
				rq_wk_day = (float) 0.5;
				annualVO.setRq_to_dt(vacationVO.getStart_ymd()); 
			}else{
				annualVO.setRq_to_dt(vacationVO.getEnd_ymd());
				//rq_wk_day  = (int) WebUtil.diffOfDate(vacationVO.getStart_ymd(), vacationVO.getEnd_ymd())+1;
				//CHOE 20160418 휴일이 포함된 경우 제거 한다.
				rq_wk_day  = (int) WebUtil.diffOfDate(vacationVO.getStart_ymd(), vacationVO.getEnd_ymd())+1 - vacationVO.getHoliday_cnt();
			}
			String rq_vacat_cd = vacationVO.getCd().replace("E06", "42");
			annualVO.setRq_emp_no(approvalMasterVO.getCreate_no());
			annualVO.setApprv_seq(approval_seq);
			annualVO.setRq_vacat_cd(rq_vacat_cd);
			annualVO.setRq_fr_dt(vacationVO.getStart_ymd());
			annualVO.setRq_emp_nm(approvalMasterVO.getEmp_ko_nm());
			annualVO.setRq_wk_day(rq_wk_day);
			
			annualVO.setRq_remark(vacationVO.getReason());
			annualVO.setFirst_emp_no(memberSessionVO.getEmp_no());
			annualVO.setLast_emp_no(memberSessionVO.getEmp_no());
			annualVO.setLast_ip(ip);
			
		}
		
		/*결재마스터 데이터 정보*/
		ApprovalMasterVO approvalMasterVO = new ApprovalMasterVO();
		approvalMasterVO.setApproval_seq(approval_seq); //결재번호
		approvalMasterVO.setState(masterState); //결재상태
		approvalMasterVO.setModify_no(memberSessionVO.getEmp_no());//수정자
		approvalMasterVO.setRejection_reason(rejection_reason);//반려내용
		approvalMasterVO.setDocu_cd(docu_cd);//문서종류
		
		/*결재라인 데이터 정보*/
		ReceiveVO receiveVO = new ReceiveVO();
		receiveVO.setState(state);//결재상태
		receiveVO.setApproval_seq(approval_seq);//문서번호
		receiveVO.setCreate_no(memberSessionVO.getEmp_no());//결재자
		receiveVO.setGubun(gubun); //결재 구분
		
		/*시행완료삭제 정보*/
		ImplDeptEmpVO implDeptEmpVO = new ImplDeptEmpVO();
		implDeptEmpVO.setApproval_seq(approval_seq);

		receiveService.updateApproval(approvalMasterVO, receiveVO, implDeptEmpVO, annualVO);	//결재(결재마스터 상태 업데이트)
		
		String script = "";
		
		/*결재 승인*/
		if("1".equals(gubun)){
			script = "<script>alert('결재 되었습니다.'); window.opener.document.location.reload();window.self.close();</script>";
		/*결재 반려*/
		}else if("2".equals(gubun)){
			script = "<script>alert('반려 되었습니다.'); window.opener.document.location.reload();window.self.close();</script>";
		/*전결*/
		}else if("3".equals(gubun)){
			script = "<script>alert('전결 되었습니다.'); window.opener.document.location.reload();window.self.close();</script>";
		/*반려취소*/	
		}else if("4".equals(gubun)){
			script = "<script>alert('반려취소 되었습니다.'); window.opener.document.location.reload();window.self.close();</script>";
		/*승인취소*/
		}else if("5".equals(gubun)){
			script = "<script>alert('승인취소 되었습니다.'); window.opener.document.location.reload();window.self.close();</script>";
		/*전결 취소*/
		}else if("6".equals(gubun)){
			script = "<script>alert('전결취소 되었습니다.'); window.opener.document.location.reload();window.self.close();</script>";
		/*일괄 결재*/
		}else if("8".equals(gubun)){
			script = "<script>alert('일괄 결재 되었습니다.'); location.href='"+env.getValue("root_dir.url")+"/ea/receive/receiveList.do';"+"</script>";
		}
		MarshallerUtil.marshalling("txt", response, script);
	}
	
	
	/**
	 * <pre>
	 * 1. 개요     : 결재 체크 
	 * 2. 처리내용 : 내 결재 차례인지 체크한다.
	 * </pre>
	 * @Method Name : approvalNextCheckEmpNo
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/ea/receive/approvalNextCheckEmpNo.do")				     
	public void approvalNextCheckEmpNo(HttpServletRequest request, HttpServletResponse response) throws IOException{
		HttpSession session = request.getSession();
		MemberVO memberSessionVO = (MemberVO) session.getAttribute("gwUser");
		
		/*결재 체크*/
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("approval_seq", request.getParameter("approval_seq")); //문서번호
		ApprovalVO approvalNowEmpNo = approvalService.getApprovalNowEmpNo(paramMap);
		
		ReceiveVO resultVO = new ReceiveVO();
		
		if(null != approvalNowEmpNo){
			if(approvalNowEmpNo.getEmp_no().equals(memberSessionVO.getEmp_no())){
				resultVO.setResult(1);
			}
		}
		
		MarshallerUtil.marshalling("json", response, resultVO);
	}
	
	/**
	 * <pre>
	 * 1. 개요     : 결재 체크
	 * 2. 처리내용 : 이전 결재자 체크
	 * </pre>
	 * @Method Name : approvalPrevCheckEmpNo
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/ea/receive/approvalPrevCheckEmpNo.do")				     
	public void approvalPrevCheckEmpNo(HttpServletRequest request, HttpServletResponse response) throws IOException{
		HttpSession session = request.getSession();
		MemberVO memberSessionVO = (MemberVO) session.getAttribute("gwUser");
		
		/*결재 체크*/
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("approval_seq", request.getParameter("approval_seq")); //문서번호
		ApprovalVO approvalNowEmpNo = approvalService.getApprovalPrevCheckEmpNo(paramMap);
		
		ReceiveVO resultVO = new ReceiveVO();
		
		if(approvalNowEmpNo.getEmp_no().equals(memberSessionVO.getEmp_no())){
			resultVO.setResult(1);
		}
		
		MarshallerUtil.marshalling("json", response, resultVO);
	}

}
