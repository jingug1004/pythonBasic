/**
  * Hana Project
  * Copyright 2014 iRush Co.,
  *
  */
package com.hanaph.gw.main.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.hanaph.gw.co.common.utils.WebUtil;
import com.hanaph.gw.ea.newReport.vo.ApprovalMasterVO;
import com.hanaph.gw.ea.receive.service.ReceiveService;
import com.hanaph.gw.ea.receive.vo.ReceiveVO;
import com.hanaph.gw.ea.report.service.ReportService;
import com.hanaph.gw.ea.report.vo.ReportVO;
import com.hanaph.gw.of.message.service.MessageService;
import com.hanaph.gw.of.message.vo.MessageVO;
import com.hanaph.gw.ea.share.service.ShareReportService;
import com.hanaph.gw.of.notice.service.NoticeService;
import com.hanaph.gw.of.notice.vo.NoticeVO;
import com.hanaph.gw.pe.member.vo.MemberVO;

/**
 * <pre>
 * Class Name : MainController.java
 * 설명 : 메인페이지 Controller
 * 
 * Modification Information
 *    수정일      수정자              수정 내용
 *  ------------ -------------- --------------------------------
 *  2014. 10. 16.      CHOIILJI         
 * </pre>
 * 
 * @version : 
 * @author  : CHOIILJI(choiilji@irush.co.kr)
 * @since   : 2014. 10. 16.
 */
@Controller
public class MainController {
	
	@Autowired
	private NoticeService noticeService;
	
	@Autowired
	private MessageService messageService;
	
	@Autowired
	private ReportService reportService;
	
	@Autowired
	private ReceiveService receiveService;
	
	@Autowired
	private ShareReportService shareReportService;
	
	/**
	 * 
	 * <pre>
	 * 1. 개요     : 메인페이지  
	 * 2. 처리내용 : 메인페이지 사내공지 리스트 가져온다.
	 * </pre>
	 * @Method Name : main
	 * @param request
	 * @return
	 */
	@RequestMapping("/main/main.do")
	public ModelAndView main(HttpServletRequest request){
		
		ModelAndView mav = new ModelAndView("main/main");
		
		Map<String, String> paramMap = new HashMap<String, String>();  
		
		/*세션에서 회원정보 가져 온다.*/
		HttpSession session = request.getSession(false);
		MemberVO memberSessionVO = (MemberVO) session.getAttribute("gwUser");
		
		String emp_no = memberSessionVO.getEmp_no();
		
		String searchDate[] = WebUtil.dateCal(0);
		String searchDateMustOpinion[] = WebUtil.dateCal(-2);

		int currentPage = 1;
		
		paramMap.put("page", String.valueOf(currentPage));		
		paramMap.put("emp_no", emp_no);	
		
		paramMap.put("perPageRow", String.valueOf(4));
		/*쪽지함*/
		paramMap.put("search_read_yn", "all");
		paramMap.put("type", "1");	//type 1.받은쪽지 2.보낸쪽지
		List<MessageVO> messageList = messageService.getMessageList(paramMap);
		
		/*공지사항*/
		paramMap.put("search_noti_kind", "all");
		paramMap.put("search_start_ymd", searchDate[0]);
		paramMap.put("search_end_ymd", searchDate[0]);
		paramMap.put("search_read_yn", "all");
		List<NoticeVO> noticeList = noticeService.getNoticeList(paramMap);
		
		/*전자결재 검색 조건*/
		paramMap.put("search_docu_state", "all");
		paramMap.put("search_docu_kind", "all");
		paramMap.put("search_appr_state", "all");
		
		/*장기미결재 카운트*/
		paramMap.put("gubun", "long");
		int longTermReceiveCount = receiveService.getLongTermReceiveCount(paramMap);
		
		paramMap.put("perPageRow", String.valueOf(3));
		/*내가올린문서*/
		List<ReportVO> reportList = reportService.getReportList(paramMap);
		List<ApprovalMasterVO> mainReportCnt = reportService.getMainReportCnt(paramMap);
		
		/*내가받은문서*/
		List<ReceiveVO> receiveList = receiveService.getReceiveList(paramMap);
		List<ApprovalMasterVO> mainReceiveCnt = receiveService.getMainReceiveCnt(paramMap);

		/*비빌번호 변경대상 여부 (true/false)*/
		boolean pass_change_chk = (Boolean)session.getAttribute("pass_change_chk");
		
		/*공유문서 카운트*/
		paramMap.put("search_read_yn", "N");
		int shareTotalCount = shareReportService.getShareCount(paramMap);

		/*메인에 내가올린문서, 내가받은문서-시행부서 의견 필수 카운트*/
		paramMap.put("checkMustOpinion", "Y"); //시행부서 의견 필수 상태
		int mainMustOpinionReportCnt = reportService.getReportCount(paramMap);	//내가받은문서 카운트

		paramMap.put("search_start_ymd", searchDateMustOpinion[0]);
		int mainMustOpinionReceiveCnt = receiveService.getReceiveCount(paramMap);	//내가받은문서 카운트

		/*내가올린문서 카운트*/
		mav.addObject("mainReportCnt", mainReportCnt);
		
		/*내가받은문서 카운트*/
		mav.addObject("mainReceiveCnt", mainReceiveCnt);
		
		/*공유문서 카운트*/
		mav.addObject("shareTotalCount", shareTotalCount);
		
		/*쪽지함 리스트*/
		mav.addObject("messageList", messageList);
		
		/*공지사항 리스트*/
		mav.addObject("noticeList", noticeList);
		
		/*내가받은문서 리스트*/
		mav.addObject("reportList", reportList);
		
		/*내가올린문서 리스트*/
		mav.addObject("receiveList", receiveList);
		
		/*패스워드변경여부체크*/
		mav.addObject("pass_change_chk", pass_change_chk);
		
		/*장기미결재 카운트*/
		mav.addObject("longTermReceiveCount", longTermReceiveCount);

		/*시행부서 의견 필수-올린 카운트*/
		mav.addObject("mainMustOpinionReportCnt", mainMustOpinionReportCnt);

		/*시행부서 의견 필수-받은 카운트*/
		mav.addObject("mainMustOpinionReceiveCnt", mainMustOpinionReceiveCnt);

        return mav;
	}
}
