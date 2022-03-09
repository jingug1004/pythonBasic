/**
  * Hana Project
  * Copyright 2014 iRush Co.,
  *
  */
package com.hanaph.gw.ea.newReport.controller;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.hanaph.gw.of.message.service.MessageService;
import com.hanaph.gw.of.message.vo.MessageVO;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.ModelAndViewDefiningException;

import com.hanaph.gw.co.authority.service.AuthorityService;
import com.hanaph.gw.co.code.service.CodeService;
import com.hanaph.gw.co.code.vo.CodeVO;
import com.hanaph.gw.co.common.utils.CommonUtil;
import com.hanaph.gw.co.common.utils.Environment;
import com.hanaph.gw.co.common.utils.FileUtil;
import com.hanaph.gw.co.common.utils.MarshallerUtil;
import com.hanaph.gw.co.common.utils.MenuUtil;
import com.hanaph.gw.co.common.utils.PageUtil;
import com.hanaph.gw.co.common.utils.StringUtil;
import com.hanaph.gw.co.common.utils.WebUtil;
import com.hanaph.gw.co.fileAttach.vo.FileAttachVO;
import com.hanaph.gw.co.menu.service.MenuService;
import com.hanaph.gw.co.menu.vo.MenuVO;
import com.hanaph.gw.ea.mgrDocuInfo.service.DocumentInfoService;
import com.hanaph.gw.ea.mgrDocuInfo.vo.DocumentInfoVO;
import com.hanaph.gw.ea.newReport.service.ApprovalService;
import com.hanaph.gw.ea.newReport.service.ImplDeptEmpService;
import com.hanaph.gw.ea.newReport.service.ImplDeptService;
import com.hanaph.gw.ea.newReport.service.NewReportE01001Service;
import com.hanaph.gw.ea.newReport.service.NewReportE01002Service;
import com.hanaph.gw.ea.newReport.service.NewReportE01003Service;
import com.hanaph.gw.ea.newReport.service.NewReportE01004Service;
import com.hanaph.gw.ea.newReport.service.NewReportE01005Service;
import com.hanaph.gw.ea.newReport.service.NewReportE01006Service;
import com.hanaph.gw.ea.newReport.service.NewReportE01007Service;
import com.hanaph.gw.ea.newReport.service.NewReportE01008Service;
import com.hanaph.gw.ea.newReport.service.NewReportE01009Service;
import com.hanaph.gw.ea.newReport.service.NewReportE01010Service;
import com.hanaph.gw.ea.newReport.service.NewReportE01011Service;
import com.hanaph.gw.ea.newReport.service.NewReportE01012Service;
import com.hanaph.gw.ea.newReport.service.NewReportE01013Service;
import com.hanaph.gw.ea.newReport.service.NewReportE01014Service;
import com.hanaph.gw.ea.newReport.service.NewReportE01015Service;
import com.hanaph.gw.ea.newReport.service.NewReportE01016Service;
import com.hanaph.gw.ea.newReport.service.NewReportService;
import com.hanaph.gw.ea.newReport.service.OpinionService;
import com.hanaph.gw.ea.newReport.vo.AccidentVO;
import com.hanaph.gw.ea.newReport.vo.ApprovalMasterVO;
import com.hanaph.gw.ea.newReport.vo.ApprovalVO;
import com.hanaph.gw.ea.newReport.vo.CertificateVO;
import com.hanaph.gw.ea.newReport.vo.CommuteVO;
import com.hanaph.gw.ea.newReport.vo.DraftVO;
import com.hanaph.gw.ea.newReport.vo.ImplDeptEmpVO;
import com.hanaph.gw.ea.newReport.vo.ImplDeptVO;
import com.hanaph.gw.ea.newReport.vo.IncompanyVO;
import com.hanaph.gw.ea.newReport.vo.OpinionVO;
import com.hanaph.gw.ea.newReport.vo.OvertimeVO;
import com.hanaph.gw.ea.newReport.vo.ParticipationVO;
import com.hanaph.gw.ea.newReport.vo.PurchaseVO;
import com.hanaph.gw.ea.newReport.vo.SampleVO;
import com.hanaph.gw.ea.newReport.vo.VacationVO;
import com.hanaph.gw.ea.newReport.vo.VaporizeVO;
import com.hanaph.gw.ea.newReport.vo.ReviewVO;
import com.hanaph.gw.ea.newReport.vo.RequisitionVO;
import com.hanaph.gw.ea.newReport.vo.DeliveryVO;
import com.hanaph.gw.ea.receive.service.ReceiveService;
import com.hanaph.gw.ea.report.vo.ReportVO;
import com.hanaph.gw.ea.share.service.ShareReportService;
import com.hanaph.gw.ea.share.vo.ShareReportVO;
import com.hanaph.gw.pe.member.service.AnnualService;
import com.hanaph.gw.pe.member.vo.AnnualMgrVO;
import com.hanaph.gw.pe.member.vo.MemberVO;
import com.hanaph.gw.ea.newReport.vo.NewMadicineVO;

/**
 * <pre>
 * Class Name : newReportController.java
 * 설명 : 신규문서작성
 * 
 * Modification Information
 *    수정일      수정자              수정 내용
 *  ------------ -------------- --------------------------------
 *  2014. 12. 23.      Jung.Jin.Muk          
 * </pre>
 * 
 * @version : 
 * @author  : Jung.Jin.Muk(pc123pc@irush.co.kr)
 * @since   : 2014. 12. 23.
 */
@Controller
public class NewReportController {
	
	@Autowired
	private NewReportService newReportService;
	
	@Autowired
	private NewReportE01001Service newReportE01001Service;  
	
	@Autowired
	private NewReportE01002Service newReportE01002Service;
	
	@Autowired
	private NewReportE01003Service newReportE01003Service;
	
	@Autowired
	private NewReportE01004Service newReportE01004Service;
	
	@Autowired
	private NewReportE01005Service newReportE01005Service;
	
	@Autowired
	private NewReportE01006Service newReportE01006Service;
	
	@Autowired
	private NewReportE01007Service newReportE01007Service;
	
	@Autowired
	private NewReportE01008Service newReportE01008Service;
	
	@Autowired
	private NewReportE01009Service newReportE01009Service;
	
	@Autowired
	private NewReportE01010Service newReportE01010Service;
	
	@Autowired
	private NewReportE01011Service newReportE01011Service;
	
	@Autowired
	private NewReportE01012Service newReportE01012Service;
	
	@Autowired
	private NewReportE01013Service newReportE01013Service;
	
	@Autowired
	private NewReportE01014Service newReportE01014Service;
	
	@Autowired
	private NewReportE01015Service newReportE01015Service;
	
	@Autowired
	private NewReportE01016Service newReportE01016Service;
		
	@Autowired 
	private DocumentInfoService documentInfoService;
	
	@Autowired 
	private MenuService menuService;
	
	@Autowired
	private AnnualService annualService;
	
	@Autowired
	private CodeService codeService;
	
	@Autowired
	ApprovalService approvalService;
	
	@Autowired
	ImplDeptService implDeptService;
	
	@Autowired
	ImplDeptEmpService implDeptEmpService;
	
	@Autowired
	ReceiveService receiveService;
	
	@Autowired
	private ShareReportService shareReportService;
	
	@Autowired
	private AuthorityService authorityService;
	
	@Autowired
	private OpinionService opinionService;

	@Autowired
	private MessageService messageService;
	
	private static final Logger logger = Logger.getLogger(NewReportController.class);
	
	private Environment env = new Environment();
	
	/**
	 * <pre>
	 * 1. 개요     : 신규 문서 작성 리스트
	 * 2. 처리내용 : 신규 문서 작성 리스트를 가져온다.
	 * </pre>
	 * @Method Name : newReportWriteList
	 * @param request
	 * @return
	 * @throws ModelAndViewDefiningException
	 */
	@RequestMapping("/ea/newReport/newReportWriteList.do")
	public ModelAndView newReportWriteList(HttpServletRequest request) {
		
		ModelAndView mav = new ModelAndView("ea/newReport/newReportWriteList");
		
		final String MENU_CHILD = "0201";
		
		HttpSession session = request.getSession();
		MemberVO memberSessionVO = (MemberVO) session.getAttribute("gwUser");
		
		Map<String, String> paramMap = new HashMap<String, String>();  
		
		String dept_cd = StringUtil.nvl(memberSessionVO.getDept_cd(), "0000");	//부서코드
		
		paramMap.put("search_dept_cd", dept_cd);
		paramMap.put("use_yn", "Y");
		
		/*양식정보 리스트 시간외근무내역서는 안 보여준다. */
		paramMap.put("menu", MENU_CHILD);
		List<DocumentInfoVO> documentInfoList = documentInfoService.getDocumentInfoList(paramMap);
		
		/*전체 카운트*/
		int documentInfoTotalCount = documentInfoService.getDocumentInfoCount(paramMap);
		
		/*LNB 메뉴 생성 START*/
		String menu_parents = StringUtil.substring(MENU_CHILD, 2);
		MenuUtil mu = new MenuUtil();
		List<MenuVO> lnbMenuList = mu.makeLnb(request, menu_parents , menuService);
		mav.addObject("lnbMenuList", lnbMenuList);
		mav.addObject("MENU_CHILD", MENU_CHILD);
		/*LNB 메뉴 생성 END*/
				
		mav.addObject("documentInfoList", documentInfoList);			 //신규문서리스트
		mav.addObject("documentInfoTotalCount", documentInfoTotalCount); //신규문서 총카운트
				
		return mav;
	}
	
	/**
	 * <pre>
	 * 1. 개요     : 전자결재 신규문서 작성 
	 * 2. 처리내용 : 신규문서 작성 팝업창이 열린다.
	 * </pre>
	 * @Method Name : step1approvalPopup
	 * @param request
	 * @return
	 */
	@RequestMapping("/ea/newReport/step1approvalPopup.do")
	public ModelAndView step1approvalPopup(HttpServletRequest request) {
		
		ModelAndView mav = new ModelAndView("ea/newReport/step1approvalPopup");
		java.util.Calendar cal = java.util.Calendar.getInstance();
		int year = cal.get(cal.YEAR);
			
		HttpSession session = request.getSession();
		MemberVO memberSessionVO = (MemberVO) session.getAttribute("gwUser");
		String emp_ko_nm = memberSessionVO.getEmp_ko_nm();
		String dept_ko_nm = memberSessionVO.getDept_ko_nm();
		Map<String, String> paramMap = new HashMap<String, String>();  
		
		String docu_seq = StringUtil.nvl(request.getParameter("docu_seq"));			//문서시퀀스
		String approval_seq = StringUtil.nvl(request.getParameter("approval_seq"));	//결재마스터시퀀스
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		
		/*문서 기본정보 가져온다.*/
		paramMap.put("docu_seq", docu_seq);
		DocumentInfoVO documentInfoDetail =  documentInfoService.getDocumentInfoDetail(paramMap);
		String docu_cd = documentInfoDetail.getDocu_cd();		
		
		if(!"".equals(approval_seq)){
			/*문서기본 상세정보(문서번호,작성일자,작성부서,작성자,제목)*/
			ApprovalMasterVO approvalMasterVO = newReportService.approvalDetail(approval_seq);
			mav.addObject("approvalMasterVO", approvalMasterVO);
			/*파일리스트 정보*/
			paramMap.put("approval_seq", approval_seq);
			List<FileAttachVO> attachList = newReportService.getAttachList(paramMap);
			mav.addObject("attachList", attachList);
			mav.addObject("approval_seq", approval_seq); //문서번호
		}
		
		/*CHOE 20160113 물품구입청구확인서는 main 상단 화면을 다르게 사용한다: 가로 결재 - IRUSH 제공화면으로*/
		if(docu_cd.equals("E01015")) {			
			mav.setViewName("ea/newReport/step1approvalPopup1015");	
		} else if(docu_cd.equals("E01016")) {			
			mav.setViewName("ea/newReport/step1approvalPopup1015");		
		} else {
			mav.setViewName("ea/newReport/step1approvalPopup");
		}
		//System.out.println("--- NewReportController step1approvalPopup 4 ");
		if(docu_cd.equals("E01001")){
			if(!"".equals(approval_seq)){
				/*휴가신청서 상세정보*/
				VacationVO vacationVO = newReportE01001Service.vacationDetail(approval_seq);
				mav.addObject("vacationVO", vacationVO);
			}
			
			/*개인 연차 정보 가져온다.*/
			paramMap.put("emp_no", memberSessionVO.getEmp_no());
			paramMap.put("yr_year", Integer.toString(calendar.get(Calendar.YEAR)));
			/*CHOE 2017.01.16 HANAHR.F_GET_WORK_DAYS_YMD 사용*/
			DecimalFormat df  = new DecimalFormat("00");
			
			paramMap.put("yr_mmdd", df.format(calendar.get(Calendar.MONTH) + 1) + df.format(calendar.get(Calendar.DATE)));  
			//System.out.println("--- NewReportController step1approvalPopup: 날짜 " + df.format(calendar.get(Calendar.MONTH) + 1));
			//System.out.println("--- NewReportController step1approvalPopup: 날짜 " + df.format(calendar.get(Calendar.DATE)));
			AnnualMgrVO annualPlan = annualService.getAnnualPlan(paramMap);
			if(annualPlan == null){
				annualPlan = new AnnualMgrVO();
			}
			
			/*휴가종류 parameter*/
			Map<String, String> codeParmaMap = new HashMap<String, String>();
			codeParmaMap.put("m_cd", "E06");
			
			List<CodeVO> codeList = codeService.getCodeList(codeParmaMap);
			
			paramMap.put("emp_no", memberSessionVO.getEmp_no());
			paramMap.put("rq_year", Integer.toString(year));
			
			/* 공동연차 카운트를 가져온다.*/
			mav.addObject("annualcommonDtCnt", annualService.getAnnualCommonDTCount(paramMap));
			mav.addObject("codeList", codeList);
			mav.addObject("annualPlan", annualPlan);
			
			
		}else if(docu_cd.equals("E01002")){
			if(!"".equals(approval_seq)){
				/*기안서 상세정보*/
				DraftVO draftVO = newReportE01002Service.draftDetail(approval_seq);
				mav.addObject("draftVO", draftVO);
			}
		}else if(docu_cd.equals("E01003")){
			if(!"".equals(approval_seq)){
				/*사내통신 상세정보*/
				IncompanyVO incompanyVO = newReportE01003Service.incompanyDetail(approval_seq);
				mav.addObject("incompanyVO", incompanyVO);
			}
			
		}else if(docu_cd.equals("E01004")){
			if(!"".equals(approval_seq)){
				/*기화기기안서 상세정보*/
				VaporizeVO vaporizeVO = newReportE01004Service.vaporizeDetail(approval_seq);
				mav.addObject("vaporizeVO", vaporizeVO);
			}

			/*기화기 종류*/
			paramMap.put("m_cd", "E05");
			List<CodeVO> sCodeList = codeService.getScodeList(paramMap);
			mav.addObject("sCodeList", sCodeList);
			
		}else if(docu_cd.equals("E01005")){
			if(!"".equals(approval_seq)){
				/*근태계 상세정보*/
				CommuteVO commuteVO = newReportE01005Service.commuteDetail(approval_seq);
				mav.addObject("commuteVO", commuteVO);
			}
			
			/*CHOE 2016118 개인 지각 가져온다.*/
			paramMap.put("emp_no", memberSessionVO.getEmp_no());
			paramMap.put("year", Integer.toString(calendar.get(Calendar.YEAR)));
			
			//System.out.println("--- NewReportController step1approvalPopup: 근태계 ");
			
			CommuteVO commuteTardy = newReportE01005Service.commuteTardy(paramMap);
			if(commuteTardy == null){
				commuteTardy = new CommuteVO();
			}
			mav.addObject("commuteTardy", commuteTardy);
			
		}else if(docu_cd.equals("E01006")){
			if(!"".equals(approval_seq)){
				/*참가신청서 상세정보*/
				ParticipationVO participationVO = newReportE01006Service.participationDetail(approval_seq);
				mav.addObject("participationVO", participationVO);
			}

		}else if(docu_cd.equals("E01007")){
			List<SampleVO> sampleDetailList;
			if(!"".equals(approval_seq)){
				/*샘플신청서 상세정보*/
				sampleDetailList = newReportE01007Service.sampleDetailList(approval_seq);
			}else{
				sampleDetailList = new ArrayList<SampleVO>();
			}	
			mav.addObject("sampleDetailList", sampleDetailList);
			
		}else if(docu_cd.equals("E01008")){
			List<PurchaseVO> purchaseDetailList;
			if(!"".equals(approval_seq)){
				/*구매신청서 상세정보*/
				purchaseDetailList = newReportE01008Service.purchaseDetailList(approval_seq);
			}else{
				purchaseDetailList = new ArrayList<PurchaseVO>();
			}
			mav.addObject("purchaseDetailList", purchaseDetailList);
			
		}else if(docu_cd.equals("E01009")){
			List<OvertimeVO> overtimeDetailList;
			if(!"".equals(approval_seq)){
				/*시간외근무신청서 상세정보*/
				overtimeDetailList = newReportE01009Service.overtimeDetailList(approval_seq);
			}else{
				overtimeDetailList = new ArrayList<OvertimeVO>();
			}
			
			//System.out.println("--- NewReportController overtimeDetailList Check start");
			if(overtimeDetailList.size()!=0){
				//System.out.println("--- NewReportController overtimeDetailList overtimeDetailList.size");
				for(int i=0; overtimeDetailList.size()>i;i++){
					OvertimeVO overtimeVO = new OvertimeVO();
					overtimeVO = overtimeDetailList.get(i);		
					//System.out.println("--- NewReportController req_no: "+ overtimeVO.getBiz_content());
				}
			}			
			//System.out.println("--- NewReportController overtimeDetailList Check end");			
			
			mav.addObject("overtimeDetailList", overtimeDetailList);
			/*근무구분 code*/
			paramMap.put("m_cd", "E07");
			List<CodeVO> sCodeList = codeService.getScodeList(paramMap);
			mav.addObject("sCodeList", sCodeList);
			
		}else if(docu_cd.equals("E01010")){
			List<CertificateVO> certificateDetailList;
			if(!"".equals(approval_seq)){
				/*증명서발급신청서 상세정보*/
				certificateDetailList = newReportE01010Service.certificateDetailList(approval_seq);
			}else{
				certificateDetailList = new ArrayList<CertificateVO>();
			}
			mav.addObject("certificateDetailList", certificateDetailList);
			/*증명서 종류 code*/
			paramMap.put("m_cd", "E04");
			List<CodeVO> sCodeList = codeService.getScodeList(paramMap);
			mav.addObject("sCodeList", sCodeList);
			
		}else if(docu_cd.equals("E01011")){
			if(!"".equals(approval_seq)){
				/*사고보고서 상세정보*/
				AccidentVO accidentVO = newReportE01011Service.accidentDetail(approval_seq);
				mav.addObject("accidentVO", accidentVO);
			}
			
		}else if(docu_cd.equals("E01012")){
			List<OvertimeVO> overtimeDetailList;
			if(!"".equals(approval_seq)){
				/*시간외근무내역서 상세정보*/
				overtimeDetailList = newReportE01012Service.overtimeDetailDetailList(approval_seq);
				OvertimeVO overtimeVO = overtimeDetailList.get(0);
				mav.addObject("approval_seq_old", overtimeVO.getApproval_seq_old()); //시간외근무신청서 문서번호
			}else{
				/*시간외근무신청서 상세정보가저온다.*/
				String approval_seq_old = StringUtil.nvl(request.getParameter("approval_seq_old"));
				overtimeDetailList = newReportE01009Service.overtimeDetailList(approval_seq_old);
				mav.addObject("approval_seq_old", approval_seq_old); //시간외근무신청서 문서번호
				dept_ko_nm = documentInfoDetail.getDept_ko_nm();//내역서 신규 생성시에는 회원의 부서 정보가 아닌 시간외근무신청서의 부서 정보를 가져 온다.
			}
			mav.addObject("overtimeDetailList", overtimeDetailList);
			/*근무구분 code*/
			paramMap.put("m_cd", "E07");
			List<CodeVO> sCodeList = codeService.getScodeList(paramMap);
			mav.addObject("sCodeList", sCodeList);
			
		}else if(docu_cd.equals("E01013")){
			if(!"".equals(approval_seq)){
				/*신약신청서 상세정보*/
				NewMadicineVO newmadicineVO = newReportE01013Service.NewMadicineDetail(approval_seq);
				mav.addObject("newmadicineVO", newmadicineVO);
			}

			/*신약신청서 종류*/
			paramMap.put("m_cd", "E08");
			List<CodeVO> sCodeList = codeService.getScodeList(paramMap);
			mav.addObject("sCodeList", sCodeList);
			
		}else if(docu_cd.equals("E01014")){
			if(!"".equals(approval_seq)){
				/*개발검토서 상세정보*/
				ReviewVO reviewVO = newReportE01014Service.reviewDetail(approval_seq);
				mav.addObject("reviewVO", reviewVO);
			}
			
		}else if(docu_cd.equals("E01015")){			
			//CHOE 20151218 변경사항 반영 			
			//1. 문서번호가 있으면 리스트를 조회해 온다 : GW_EA_REQUISITION 정보 조회
			List<RequisitionVO> requisitionDetailList;
			if(!"".equals(approval_seq)){				
				/*물품 구입 청구서 상세정보*/
				requisitionDetailList = newReportE01015Service.requisitionDetail(approval_seq);
				
				if(requisitionDetailList.size()!=0){						
					for(int i=0; requisitionDetailList.size()>i;i++){
						RequisitionVO reqVO = new RequisitionVO();
						reqVO = requisitionDetailList.get(i);		
						//System.out.println("--- NewReportController getMaterial_id: "+ reqVO.getMaterial_id());
					}
				}		
				
			}else{
				requisitionDetailList = new ArrayList<RequisitionVO>();
				
				//2-1. 청구일 확인
				String search_req_ymd = StringUtil.nvl(request.getParameter("search_req_ymd"));
				//System.out.println("---------- NewReportController search_ymd : "+ search_req_ymd);
				//2-2. 청구번호 확인
				String search_req_no = StringUtil.nvl(request.getParameter("search_req_no"));
				//System.out.println("---------- NewReportController search_req_data : "+ search_req_no);
				
				//2-3. 청구 구분
				String search_req_div = StringUtil.nvl(request.getParameter("search_req_div"));
				//System.out.println("---------- NewReportController search_req_div : "+ search_req_div);
					
				//2-확인-2. 청구일은 있고 청구 번호만 없는 경우	
				//if (!"".equals(search_ymd) && "".equals(search_req_no)){
				if (!"".equals(search_req_ymd) && "".equals(search_req_no)){
					requisitionDetailList = newReportE01015Service.searchReqNo(search_req_ymd);	
					
					if(requisitionDetailList.size()!=0){						
						for(int i=0; requisitionDetailList.size()>i;i++){
							RequisitionVO reqVO = new RequisitionVO();
							reqVO = requisitionDetailList.get(i);		
							//System.out.println("--- NewReportController getReq_no: "+ reqVO.getReq_no());
						}
					}	
					
				//2-확인-3. 청구일이 있고 청구 번호도 있는 경우	: 문서번호가 없는데 2값이 있다는 것은 사용자가 입력한 것으로 본다.
				}else if (!"".equals(search_req_ymd) && !"".equals(search_req_no)){				
					requisitionDetailList = newReportE01015Service.searchReqData(search_req_no);
					
					if(requisitionDetailList.size()!=0){						
						for(int i=0; requisitionDetailList.size()>i;i++){
							RequisitionVO reqVO = new RequisitionVO();
							reqVO = requisitionDetailList.get(i);		
							//System.out.println("--- NewReportController getMaterial_id: "+ reqVO.getMaterial_id());
						}
					}		
				}				
				
			}	
			mav.addObject("requisitionDetail", requisitionDetailList);
			
		}else if(docu_cd.equals("E01016")){			
			//CHOE 20160126  			
			//1. 문서번호가 있으면 리스트를 조회해 온다 : GW_EA_DELIVAERY 정보 조회
			List<DeliveryVO> deliveryDetailList;
			if(!"".equals(approval_seq)){				
				/*원부자재 납풉확인서 상세정보*/
				deliveryDetailList = newReportE01016Service.deliveryDetail(approval_seq);
				
				if(deliveryDetailList.size()!=0){						
					for(int i=0; deliveryDetailList.size()>i;i++){
						DeliveryVO deliVO = new DeliveryVO();
						deliVO = deliveryDetailList.get(i);		
						//System.out.println("--- NewReportController getMaterial_id: "+ deliVO.getMaterial_id());
					}
				}		
				
			}else{
				deliveryDetailList = new ArrayList<DeliveryVO>();
				
				//2-1. 입고일 확인
				String search_ymd = StringUtil.nvl(request.getParameter("search_ymd"));
				//System.out.println("---------- NewReportController search_ymd : "+ search_ymd);
				//2-2. 전표번호 확인
				String search_slip_no = StringUtil.nvl(request.getParameter("search_slip_no"));
				//System.out.println("---------- NewReportController search_slip_no : "+ search_slip_no);				
			
				//2-확인-2. 입고일은 있고 전표번호만 없는 경우				
				if (!"".equals(search_ymd) && "".equals(search_slip_no)){
					deliveryDetailList = newReportE01016Service.searchSlipNo(search_ymd);	
					
					if(deliveryDetailList.size()!=0){						
						for(int i=0; deliveryDetailList.size()>i;i++){
							DeliveryVO deliVO = new DeliveryVO();
							deliVO = deliveryDetailList.get(i);		
							//System.out.println("--- NewReportController getSlip_no: "+ deliVO.getSlip_no());
						}
					}	
					
				//2-확인-3. 입고일이 있고 전표 번호도 있는 경우	: 문서번호가 없는데 2값이 있다는 것은 사용자가 입력한 것으로 본다.
				}else if (!"".equals(search_ymd) && !"".equals(search_slip_no)){
					//CHOE 20160126 전달 되는 정보는 search_ymd + search_slip_no ex)20160126001 형태로 전달한다. 
					//String search_no = search_ymd + search_slip_no;
					String search_no = search_slip_no;
					//System.out.println("---------- NewReportController search_no : "+ search_no);
					deliveryDetailList = newReportE01016Service.searchSlipData(search_no);
					
					if(deliveryDetailList.size()!=0){						
						for(int i=0; deliveryDetailList.size()>i;i++){
							DeliveryVO deliVO = new DeliveryVO();
							deliVO = deliveryDetailList.get(i);		
							//System.out.println("--- NewReportController getMaterial_id: "+ deliVO.getMaterial_id());
						}
					}		
				}				
				
			}	
			mav.addObject("deliveryDetail", deliveryDetailList);
			
		}
		
		mav.addObject("documentInfoDetail", documentInfoDetail);	//문서기본정보
		mav.addObject("docu_cd", docu_cd);	//문서코드
		mav.addObject("dept_ko_nm", dept_ko_nm); //작성부서
		mav.addObject("emp_ko_nm", emp_ko_nm); //작성자
		mav.addObject("make_dt", dateFormat.format(calendar.getTime())); //작성일시
		mav.addObject("docu_seq", docu_seq); //문서번호
					     
		return mav;
	}
	
	/**
	 * <pre>
	 * 1. 개요     : 신규 문서 등록 
	 * 2. 처리내용 : 신규 문서 등록을 한다.
	 * </pre>
	 * @Method Name : insertDocument
	 * @param request
	 * @param map
	 * @throws ModelAndViewDefiningException
	 * @throws ParseException 
	 * @throws IOException 
	 */
	@RequestMapping("/ea/newReport/insertDocument.do")
	public void insertDocument(HttpServletRequest request, HttpServletResponse response, @RequestParam Map<String,String> map) 
			throws ModelAndViewDefiningException, IOException, ParseException {
		
		HttpSession session = request.getSession();
		MemberVO memberSessionVO = (MemberVO) session.getAttribute("gwUser");
		
		if("on".equals(map.get("security_yn"))){
			map.put("security_yn","Y");
		}
		Map<String, String> paramMap = new HashMap<String, String>();
		String approval_seq = StringUtil.nvl(map.get("approval_seq")); //결재문서 시퀀스
		//System.out.println("--- NewReportController insertDocument.do approval_seq: "+approval_seq);
		/*결재마스터*/
		ApprovalMasterVO paramAmVO = new ApprovalMasterVO();
		paramAmVO.setApproval_seq(approval_seq); //결재문서 시퀀스
		paramAmVO.setDocu_seq(Integer.parseInt(StringUtil.nvl(map.get("docu_seq")))); //문서번호
		paramAmVO.setSubject(StringUtil.nvl(map.get("subject"))); //제목
		//System.out.println("--- NewReportController insertDocument.do"+StringUtil.nvl(map.get("subject")));
		/*make_dt 포맷변경*/
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = dateFormat.parse(map.get("make_dt"));
		SimpleDateFormat dateFormat1 = new java.text.SimpleDateFormat("yyyyMMddHHmmss");
		String make_dt = dateFormat1.format(date);
		
		paramAmVO.setMake_dt(make_dt); //작성일시
		paramAmVO.setState("E02001"); //문서상태코드(작성중)
		paramAmVO.setSecurity_yn(StringUtil.nvl(map.get("security_yn"),"N")); //대외비
		paramAmVO.setDecide_yn(StringUtil.nvl(map.get("decide_yn"))); //전결
		paramAmVO.setStep_state("1"); //스텝상태
		paramAmVO.setCreate_no(StringUtil.nvl(memberSessionVO.getEmp_no())); //작성자
		paramAmVO.setModify_no(StringUtil.nvl(memberSessionVO.getEmp_no())); //수정자
		paramAmVO.setDocu_cd(map.get("docu_cd")); //문서코드
		
		String alertMessage = "";
		if("".equals(approval_seq)){
			alertMessage = "저장 되었습니다.";
		}else{
			alertMessage = "수정 되었습니다.";
		}
		
		/*휴가신청서*/
		if("E01001".equals(map.get("docu_cd"))){
			VacationVO paramVcVO = new VacationVO();
			paramVcVO.setVacation_kind(StringUtil.nvl(map.get("vacation_kind"))); //휴가종류
			paramVcVO.setStart_ymd(StringUtil.nvl(map.get("start_ymd"))); //휴가시작일자
			
			//CHOE 2017.03.13 반차인 경우 종료일자를 조정할 수 없다. 시작일을 넣어 준다.
			if ("E06220".equals(StringUtil.nvl(map.get("vacation_kind")))) {
				paramVcVO.setEnd_ymd(StringUtil.nvl(map.get("start_ymd"))); //휴가종료일자
			}else{
				paramVcVO.setEnd_ymd(StringUtil.nvl(map.get("end_ymd"))); //휴가종료일자
			}
			//System.out.println("--- NewReportController insertDocument : end_ymd " + StringUtil.nvl(map.get("end_ymd")));
			paramVcVO.setReason(StringUtil.nvl(map.get("reason"))); //휴가사유
			paramVcVO.setContact_number(StringUtil.nvl(map.get("contact_number"))); //비상연락처
			if("".equals(approval_seq)){
				/*휴가신청서 등록*/
				approval_seq = newReportE01001Service.insertVacation(paramAmVO, paramVcVO);
			}else{
				paramVcVO.setApproval_seq(approval_seq);
				/*휴가신청서 수정*/
				newReportE01001Service.updateVacation(paramAmVO, paramVcVO);
			}
			
		/*기안서*/
		}else if("E01002".equals(map.get("docu_cd"))){
			DraftVO paramDfVO = new DraftVO();
			paramDfVO.setContent(StringUtil.nvl(map.get("content"))); //기안내용
			paramDfVO.setControll(StringUtil.nvl(map.get("controll"))); //통제
			paramDfVO.setCooperation(StringUtil.nvl(map.get("cooperation"))); //협조
			paramDfVO.setEtc(StringUtil.nvl(map.get("etc"))); //기타
			paramDfVO.setImposition_ymd(StringUtil.nvl(map.get("imposition_ymd")));	//시행일자
			paramDfVO.setPurpose(StringUtil.nvl(map.get("purpose"))); //기안목적
			
			if("".equals(approval_seq)){
				/*기안서 등록*/
				approval_seq = newReportE01002Service.insertDraft(paramAmVO, paramDfVO);
			}else{
				paramDfVO.setApproval_seq(approval_seq);
				/*기안서 수정*/
				newReportE01002Service.updateDraft(paramAmVO, paramDfVO);
			}
			
		
		/*사내통신*/
		}else if("E01003".equals(map.get("docu_cd"))){
			IncompanyVO paramIcVO = new IncompanyVO();
			paramIcVO.setContent(StringUtil.nvl(map.get("content"))); //내용
			paramIcVO.setEtc(StringUtil.nvl(map.get("etc"))); //기타
			paramIcVO.setPurpose(StringUtil.nvl(map.get("purpose"))); //목적
			paramIcVO.setReception(StringUtil.nvl(map.get("reception"))); //참조
			paramIcVO.setReference(StringUtil.nvl(map.get("reference"))); //참조
			paramIcVO.setSend(StringUtil.nvl(map.get("send"))); //발신
			
			if("".equals(approval_seq)){
				/*사내통신 등록*/
				approval_seq = newReportE01003Service.insertIncompany(paramAmVO, paramIcVO);
			}else{
				paramIcVO.setApproval_seq(approval_seq);
				/*사내통신 수정*/
				newReportE01003Service.updateIncompany(paramAmVO, paramIcVO);
			}
			
		/*기화기기안서*/
		}else if("E01004".equals(map.get("docu_cd"))){
			VaporizeVO paramVrVO = new VaporizeVO();
			paramVrVO.setCeo_nm(StringUtil.nvl(map.get("ceo_nm"))); //대표자명
			paramVrVO.setContent(StringUtil.nvl(map.get("content"))); //내용
			paramVrVO.setCust_cd(StringUtil.nvl(map.get("cust_cd"))); //거래처 코드
			paramVrVO.setCust_nm(StringUtil.nvl(map.get("cust_nm"))); //거래처명
			paramVrVO.setImposition_ymd(StringUtil.nvl(map.get("imposition_ymd"))); //시행일자
			paramVrVO.setKind_cd(StringUtil.nvl(map.get("kind_cd"))); //종류코드
			paramVrVO.setModel_qty(StringUtil.nvl(map.get("model_qty"))); //기종 및 수량
			paramVrVO.setMonth_use_qty(StringUtil.nvl(map.get("month_use_qty"))); //월 사용 수량
			paramVrVO.setUnusual(StringUtil.nvl(map.get("unusual"))); //특이사항
			
			if("".equals(approval_seq)){
				/*기화기기안서 등록*/
				approval_seq = newReportE01004Service.insertVaporize(paramAmVO, paramVrVO);
			}else{
				paramVrVO.setApproval_seq(approval_seq);
				/*기화기기안서 수정*/
				newReportE01004Service.updateVaporize(paramAmVO, paramVrVO);
			}
			
		/*근태계*/
		}else if("E01005".equals(map.get("docu_cd"))){
			CommuteVO paramCmVO = new CommuteVO();
			paramCmVO.setBogo_receiver(StringUtil.nvl(map.get("bogo_receiver"))); //사전보고수령자
			paramCmVO.setBogo_yn(StringUtil.nvl(map.get("bogo_yn"))); //사전보고유무
			paramCmVO.setImposition_ymd(StringUtil.nvl(map.get("imposition_ymd"))); //근태계날짜
			paramCmVO.setKind(StringUtil.nvl(map.get("kind"))); //내용
			paramCmVO.setLate_tm(StringUtil.nvl(map.get("late_tm"))); //지각출근시각
			paramCmVO.setLeave_tm(StringUtil.nvl(map.get("leave_tm"))); //조퇴발생시간
			paramCmVO.setMibogo_reason(StringUtil.nvl(map.get("mibogo_reason"))); //미보고사유
			paramCmVO.setReason(StringUtil.nvl(map.get("reason"))); //사유내용
			paramCmVO.setStart_absence_ymd(StringUtil.nvl(map.get("start_absence_ymd"))); //결근시작일자
			paramCmVO.setEnd_absence_ymd(StringUtil.nvl(map.get("end_absence_ymd"))); //결근종료일자
			
			if("".equals(approval_seq)){
				/*근태계 등록*/
				approval_seq = newReportE01005Service.insertCommute(paramAmVO, paramCmVO);
			}else{
				paramCmVO.setApproval_seq(approval_seq);
				/*근태계 수정*/
				newReportE01005Service.updateCommute(paramAmVO, paramCmVO);
			}
		
		/*참가신청서*/
		}else if("E01006".equals(map.get("docu_cd"))){
			ParticipationVO paramPpVO = new ParticipationVO();
			paramPpVO.setContact_number(StringUtil.nvl(map.get("contact_number"))); //문서번호
			paramPpVO.setContent(StringUtil.nvl(map.get("content"))); //내용
			paramPpVO.setEnd_ymd(StringUtil.nvl(map.get("end_ymd"))); //종료일자
			paramPpVO.setKind(StringUtil.nvl(map.get("kind"))); //종류
			paramPpVO.setStart_ymd(StringUtil.nvl(map.get("start_ymd"))); //시작일자
			
			
			if("".equals(approval_seq)){
				/*참가신청서 등록*/
				approval_seq = newReportE01006Service.insertParticipation(paramAmVO, paramPpVO);
			}else{
				paramPpVO.setApproval_seq(approval_seq);
				/*참가신청서 수정*/
				newReportE01006Service.updateParticipation(paramAmVO, paramPpVO);
			}
		
		/*sample신청서*/
		}else if("E01007".equals(map.get("docu_cd"))){
			
			String[] responsible = request.getParameterValues("responsible"); //거래처명
			String[] cust_nm = request.getParameterValues("cust_nm"); //거래처명
			String[] dr_nm = request.getParameterValues("dr_nm"); //dr명
			String[] product_nm = request.getParameterValues("product_nm"); //품명
			String[] packing_unit = request.getParameterValues("packing_unit"); //포장단위
			String[] qty = request.getParameterValues("qty"); //수량
			String[] yongdo = request.getParameterValues("yongdo"); //용도
			String[] address = request.getParameterValues("address"); //배송지
			String[] call_number = request.getParameterValues("call_number"); //전화번호
						
			
			List<SampleVO> sampleVOList = new ArrayList<SampleVO>();
	        for(int i=0 ; i<address.length ; i++){
				SampleVO sampleVO = new SampleVO();
				sampleVO.setResponsible(responsible[i]);
				sampleVO.setCust_nm(cust_nm[i]);
				sampleVO.setCust_cd("");
				sampleVO.setDr_nm(dr_nm[i]);
				sampleVO.setProduct_nm(product_nm[i]);
				sampleVO.setPacking_unit(packing_unit[i]);
				sampleVO.setQty(qty[i]);
				sampleVO.setYongdo(yongdo[i]);
				sampleVO.setAddress(address[i]);
				sampleVO.setCall_number(call_number[i]);
				sampleVOList.add(sampleVO);				
	        }
	        
	        SampleVO paramSpVO = new SampleVO();
	        paramSpVO.setSampleVO(sampleVOList);
			
			if("".equals(approval_seq)){
				/*sample신청서 등록*/
				approval_seq = newReportE01007Service.insertSample(paramAmVO, paramSpVO);
			}else{
				/*sample신청서 수정*/				
				newReportE01007Service.updateSample(paramAmVO, paramSpVO);
			}
			
			
		/*구매신청서*/
		}else if("E01008".equals(map.get("docu_cd"))){
			
			String[] product_nm = request.getParameterValues("product_nm"); //품명
			String[] standard = request.getParameterValues("standard"); //규격
			String[] qty = request.getParameterValues("qty"); //수량
			String[] deliver_req_ymd = request.getParameterValues("deliver_req_ymd"); //납품요구일
			String[] purpose = request.getParameterValues("purpose"); //사무 및 목적
			String[] bigo = request.getParameterValues("bigo"); //비고
			
			List<PurchaseVO> purchaseVOList = new ArrayList<PurchaseVO>();
	        for(int i=0 ; i<product_nm.length ; i++){
	        	PurchaseVO purchaseVO = new PurchaseVO();
	        	purchaseVO.setProduct_nm(product_nm[i]);
	        	purchaseVO.setStandard(standard[i]);
	        	purchaseVO.setQty(qty[i]);
	        	purchaseVO.setDeliver_req_ymd(deliver_req_ymd[i].replaceAll("-", ""));
	        	purchaseVO.setPurpose(purpose[i]);
	        	purchaseVO.setBigo(bigo[i]);
	        	purchaseVOList.add(purchaseVO);				
	        }
	        
	        PurchaseVO paramPcVO = new PurchaseVO();
	        paramPcVO.setPurchaseVO(purchaseVOList);
			
			if("".equals(approval_seq)){
				/*구매신청서 등록*/
				approval_seq = newReportE01008Service.insertPurchase(paramAmVO, paramPcVO);
			}else{
				/*구매신청서 수정*/
				newReportE01008Service.updatePurchase(paramAmVO, paramPcVO);
			}
			
		/*시간외근무신청서*/
		}else if("E01009".equals(map.get("docu_cd"))){
			String[] dept_cd = request.getParameterValues("dept_cd"); //부서코드
			String[] dept_nm = request.getParameterValues("dept_nm"); //부서명
			String[] emp_no = request.getParameterValues("emp_no"); //사원번호
			String[] emp_nm = request.getParameterValues("emp_nm"); //사원명
			String[] biz_content = request.getParameterValues("biz_content"); //업무내용
			String[] work_due_ymd = request.getParameterValues("work_due_ymd"); //근무예정일
			String[] work_start_hh = request.getParameterValues("work_start_hh"); //근무시작시
			String[] work_start_mi = request.getParameterValues("work_start_mi"); //근무시작분
			String[] work_end_hh = request.getParameterValues("work_end_hh"); //근무종료시
			String[] work_end_mi = request.getParameterValues("work_end_mi"); //근무종료분
			String[] work_due_hh = request.getParameterValues("work_due_hh");  //근무예정시
			String[] work_due_mi = request.getParameterValues("work_due_mi"); //근무예정분
			String[] bigo = request.getParameterValues("bigo"); //비고
			
			
			List<OvertimeVO> overtimeVOList = new ArrayList<OvertimeVO>();
	        for(int i=0 ; i<emp_nm.length ; i++){
				OvertimeVO overtimeVO = new OvertimeVO();
				overtimeVO.setDept_cd(dept_cd[i]);
				overtimeVO.setDept_nm(dept_nm[i]);
				overtimeVO.setEmp_no(emp_no[i]);
				overtimeVO.setEmp_nm(emp_nm[i]);
				overtimeVO.setBiz_content(biz_content[i]);
				overtimeVO.setWork_due_ymd(work_due_ymd[i].replaceAll("-", ""));
				overtimeVO.setWork_start_hh(Integer.toString(Integer.parseInt(work_start_hh[i])));
				overtimeVO.setWork_start_mi(Integer.toString(Integer.parseInt(work_start_mi[i])));
				overtimeVO.setWork_end_hh(Integer.toString(Integer.parseInt(work_end_hh[i])));
				overtimeVO.setWork_end_mi(Integer.toString(Integer.parseInt(work_end_mi[i])));
				overtimeVO.setWork_due_hh(Integer.toString(Integer.parseInt(work_due_hh[i])));
				overtimeVO.setWork_due_mi(Integer.toString(Integer.parseInt(work_due_mi[i])));
				overtimeVO.setBigo(bigo[i]);
				overtimeVOList.add(overtimeVO);
	        }
	        
	        OvertimeVO paramOtVO = new OvertimeVO();
	        paramOtVO.setOvertimeVO(overtimeVOList);
	        
			if("".equals(approval_seq)){
				/*시간외 근무신청서 등록*/
				approval_seq = newReportE01009Service.insertOvertime(paramAmVO, paramOtVO);
			}else{
				/*시간외 근무신청서 수정*/
				newReportE01009Service.updateOvertime(paramAmVO, paramOtVO);
			}
		
		/*증명서발급신청서*/
		}else if("E01010".equals(map.get("docu_cd"))){
			String[] certi_cd = request.getParameterValues("certi_cd"); //증명서 코드
			String[] qty = request.getParameterValues("qty"); //수량
			String[] yongdo = request.getParameterValues("yongdo"); //용도
			
			List<CertificateVO> certificateVOList = new ArrayList<CertificateVO>();
	        for(int i=0 ; i<certi_cd.length ; i++){
				CertificateVO certificateVO = new CertificateVO();
				String certiArrValue[] = certi_cd[i].split("\\|");
				certificateVO.setCerti_nm(certiArrValue[1]);
				certificateVO.setCerti_cd(certiArrValue[0]);
				certificateVO.setQty(qty[i]);
				certificateVO.setYongdo(yongdo[i]);
				certificateVOList.add(certificateVO);
	        }
	        
	        CertificateVO paramCfcVO = new CertificateVO();
	        paramCfcVO.setCertificateVO(certificateVOList);
			
			if("".equals(approval_seq)){
				/*증명서발급신청서 등록*/
				approval_seq = newReportE01010Service.insertCertificate(paramAmVO, paramCfcVO);
			}else{
				/*증명서발급신청서 수정*/
				newReportE01010Service.updateCertificate(paramAmVO, paramCfcVO);
			}
		
		/*사고보고서*/
		}else if("E01011".equals(map.get("docu_cd"))){
			AccidentVO paramAdVO = new AccidentVO();
			paramAdVO.setCust_nm(StringUtil.nvl(map.get("cust_nm"))); //거래처명
			paramAdVO.setCust_cd(StringUtil.nvl(map.get("cust_cd"))); //거래처코드
			paramAdVO.setBranch_office(StringUtil.nvl(map.get("branch_office"))); //지점
			paramAdVO.setIncharge(StringUtil.nvl(map.get("incharge"))); //담당자
			paramAdVO.setAccident_kind(StringUtil.nvl(map.get("accident_kind"))); //사고구분
			paramAdVO.setTotal_sale(StringUtil.nvl(map.get("total_sale"))); //총매출
			paramAdVO.setTotal_collect(StringUtil.nvl(map.get("total_collect"))); //총수금
			paramAdVO.setOccurrence_ymd(StringUtil.nvl(map.get("occurrence_ymd"))); //사고발생일
			paramAdVO.setLast_trade_ymd(StringUtil.nvl(map.get("last_trade_ymd"))); //최종거래일
			paramAdVO.setSupyo1(StringUtil.nvl(map.get("supyo1"))); //어음수표 금액
			paramAdVO.setSupyo2(StringUtil.nvl(map.get("supyo2"))); //어음수표 만기일
			paramAdVO.setSupyo3(StringUtil.nvl(map.get("supyo3"))); //어음수표 부동산 소재지/발행인
			paramAdVO.setSupyo4(StringUtil.nvl(map.get("supyo4"))); //어음수표 배서인
			paramAdVO.setBoheom1(StringUtil.nvl(map.get("boheom1"))); //보증보험 금액
			paramAdVO.setBoheom2(StringUtil.nvl(map.get("boheom2"))); //보증보험만기일
			paramAdVO.setBoheom3(StringUtil.nvl(map.get("boheom3"))); //보증보험 부동산소재지/발행인
			paramAdVO.setBoheom4(StringUtil.nvl(map.get("boheom4"))); //보증보험 배서인
			paramAdVO.setBojeung1(StringUtil.nvl(map.get("bojeung1"))); //지급보증 금액
			paramAdVO.setBojeung2(StringUtil.nvl(map.get("bojeung2"))); //지급보증 만기일
			paramAdVO.setBojeung3(StringUtil.nvl(map.get("bojeung3"))); //지급보증 부동산소재지/발행인
			paramAdVO.setBojeung4(StringUtil.nvl(map.get("bojeung4"))); //지급보증 배서인
			paramAdVO.setEtc1(StringUtil.nvl(map.get("etc1"))); //기타 금액
			paramAdVO.setEtc2(StringUtil.nvl(map.get("etc2"))); //기타 만기일
			paramAdVO.setEtc3(StringUtil.nvl(map.get("etc3"))); //기타 부동산 소재지/발행인
			paramAdVO.setEtc4(StringUtil.nvl(map.get("etc4"))); //기타 배서인
			paramAdVO.setReturn_collection_amt(StringUtil.nvl(map.get("return_collection_amt"))); //반품회수액
			paramAdVO.setMoney_collection_amt(StringUtil.nvl(map.get("money_collection_amt"))); //금전회수액
			paramAdVO.setBogo_content(StringUtil.nvl(map.get("bogo_content"))); //보고내용
			paramAdVO.setBogo_year(StringUtil.nvl(map.get("bogo_year"))); //보고년도
			paramAdVO.setBogo_month(StringUtil.nvl(map.get("bogo_month"))); //보고월
			paramAdVO.setBogo_day(StringUtil.nvl(map.get("bogo_day"))); //보고일
			paramAdVO.setCust1(StringUtil.nvl(map.get("cust1"))); //거래처 거래처이름/상호
			paramAdVO.setCust2(StringUtil.nvl(map.get("cust2"))); //거래처 주민/사업자번호
			paramAdVO.setCust3(StringUtil.nvl(map.get("cust3"))); //거래처 주소/소재지
			paramAdVO.setCeo1(StringUtil.nvl(map.get("ceo1"))); //대표자 거래처이름/상호
			paramAdVO.setCeo2(StringUtil.nvl(map.get("ceo2"))); //대표자 주민/사업자번호
			paramAdVO.setCeo3(StringUtil.nvl(map.get("ceo3"))); //대표자 주소/소재지
			paramAdVO.setSurety11(StringUtil.nvl(map.get("surety11"))); //연대보증인1 거래처이름/상호
			paramAdVO.setSurety12(StringUtil.nvl(map.get("surety12"))); //연대보증인1 주민/사업자번호
			paramAdVO.setSurety13(StringUtil.nvl(map.get("surety13"))); //연대보증인1 주소/소재지
			paramAdVO.setSurety21(StringUtil.nvl(map.get("surety21"))); //연대보증인2 거래처이름/상호
			paramAdVO.setSurety22(StringUtil.nvl(map.get("surety22"))); //연대보증인 주민/사업자번호
			paramAdVO.setSurety23(StringUtil.nvl(map.get("surety23"))); //연대보증인2 주소/소재지
			paramAdVO.setCredit_amt(StringUtil.nvl(map.get("credit_amt"))); //외상물품대금
			paramAdVO.setNotcome_bill(StringUtil.nvl(map.get("notcome_bill"))); //미도래어음
			paramAdVO.setBankrupt_amt(StringUtil.nvl(map.get("bankrupt_amt"))); //부도채권총액
			paramAdVO.setProperty1(StringUtil.nvl(map.get("property1"))); //부동산 금액
			paramAdVO.setProperty2(StringUtil.nvl(map.get("property2"))); //부동산 만기일
			paramAdVO.setProperty3(StringUtil.nvl(map.get("property3"))); //부동산 부동산소재지/발행인
			paramAdVO.setProperty4(StringUtil.nvl(map.get("property4"))); //부동산 배서인

			if("".equals(approval_seq)){
				/*사고보고서 등록*/
				approval_seq = newReportE01011Service.insertAccident(paramAmVO, paramAdVO);
			}else{
				paramAdVO.setApproval_seq(approval_seq);
				/*사고보고서 수정*/
				newReportE01011Service.updateAccident(paramAmVO, paramAdVO);
			}
		/*시간외 근무내역서*/	
		}else if("E01012".equals(map.get("docu_cd"))){
			String[] dept_cd = request.getParameterValues("dept_cd"); //부서코드
			String[] dept_nm = request.getParameterValues("dept_nm"); //부서명
			String[] emp_no = request.getParameterValues("emp_no"); //사원번호
			String[] emp_nm = request.getParameterValues("emp_nm"); //사원명
			String[] biz_content = request.getParameterValues("biz_content"); //업무내용
			String[] work_due_ymd = request.getParameterValues("work_due_ymd"); //근무예정일
			String[] work_start_hh = request.getParameterValues("work_start_hh"); //근무시작시
			String[] work_start_mi = request.getParameterValues("work_start_mi"); //근무시작분
			String[] work_end_hh = request.getParameterValues("work_end_hh"); //근무종료시
			String[] work_end_mi = request.getParameterValues("work_end_mi"); //근무종료분
			String[] work_real_hh = request.getParameterValues("work_real_hh");  //실근무시
			String[] work_real_mi = request.getParameterValues("work_real_mi"); //실근무분
			String[] bigo = request.getParameterValues("bigo"); //비고
			String approval_seq_old = request.getParameter("approval_seq_old");//시간외근무신청서 approval_seq
			
			List<OvertimeVO> overtimeVOList = new ArrayList<OvertimeVO>();
	        for(int i=0 ; i<emp_nm.length ; i++){
				OvertimeVO overtimeVO = new OvertimeVO();
				overtimeVO.setDept_cd(dept_cd[i]);
				overtimeVO.setDept_nm(dept_nm[i]);
				overtimeVO.setEmp_no(emp_no[i]);
				overtimeVO.setEmp_nm(emp_nm[i]);
				overtimeVO.setBiz_content(biz_content[i]);
				overtimeVO.setWork_due_ymd(work_due_ymd[i].replaceAll("-", ""));
				overtimeVO.setWork_start_hh(Integer.toString(Integer.parseInt(work_start_hh[i])));
				overtimeVO.setWork_start_mi(Integer.toString(Integer.parseInt(work_start_mi[i])));
				overtimeVO.setWork_end_hh(Integer.toString(Integer.parseInt(work_end_hh[i])));
				overtimeVO.setWork_end_mi(Integer.toString(Integer.parseInt(work_end_mi[i])));
				overtimeVO.setWork_real_hh(Integer.toString(Integer.parseInt(work_real_hh[i])));
				overtimeVO.setWork_real_mi(Integer.toString(Integer.parseInt(work_real_mi[i])));
				overtimeVO.setBigo(bigo[i]);
				overtimeVO.setApproval_seq_old(approval_seq_old);
				overtimeVOList.add(overtimeVO);
				//System.out.println("--- NewReportController insertDocument.do DEPT_CD: "+overtimeVO.getDept_cd());
	        }
	        
	        OvertimeVO paramOtVO = new OvertimeVO();
	        paramOtVO.setOvertimeVO(overtimeVOList);
	        paramOtVO.setApproval_seq_old(approval_seq_old);
	        paramOtVO.setEmp_no(memberSessionVO.getEmp_no());
	        //System.out.println("--- NewReportController insertDocument.do approval_seq: "+approval_seq);
			if("".equals(approval_seq)){
				/*시간외 근무내역서 등록*/
				approval_seq = newReportE01012Service.insertOvertimeDetail(paramAmVO, paramOtVO);
				if("".equals(approval_seq)){					
					String script = "<script>alert('다른사용자에 의해 작성 되어졌습니다.'); window.self.close();</script>";
					MarshallerUtil.marshalling("txt", response, script);
				}
			}else{
				/*시간외 근무내역서 수정*/
				newReportE01012Service.updateOvertimeDetail(paramAmVO, paramOtVO);
			}
			paramMap.put("approval_seq_old", approval_seq_old);	//시간외근무신청서 approval_seq
			
		/*신약신청서*/
		}else if("E01013".equals(map.get("docu_cd"))){
			NewMadicineVO paramVrVO = new NewMadicineVO();
			paramVrVO.setImposition_ymd(StringUtil.nvl(map.get("imposition_ymd"))); //시행일자
			paramVrVO.setHospital_nm(StringUtil.nvl(map.get("hospital_nm"))); //1.병원명
			paramVrVO.setMedical_nm(StringUtil.nvl(map.get("medical_nm"))); //2.진료과명
			paramVrVO.setDoctor_nm(StringUtil.nvl(map.get("doctor_nm"))); //3.의사명
			paramVrVO.setItem_nm(StringUtil.nvl(map.get("item_nm"))); //4.제품명
			paramVrVO.setSide_div(StringUtil.nvl(map.get("side_div"))); //5.원내외구분
			
			paramVrVO.setChange_item(StringUtil.nvl(map.get("change_item"))); //6.교체품목
			paramVrVO.setChange_object(StringUtil.nvl(map.get("change_object"))); //6.교체대상
			paramVrVO.setAdd_item(StringUtil.nvl(map.get("add_item"))); //6.추가품목
			paramVrVO.setTime_limit_dt(StringUtil.nvl(map.get("time_limit_dt"))); //7.요청기한
			paramVrVO.setDischarge_dt(StringUtil.nvl(map.get("discharge_dt"))); //8.D/C일자
			paramVrVO.setBegin_dt(StringUtil.nvl(map.get("begin_dt"))); //9.처방개시일			
			paramVrVO.setChange_item(StringUtil.nvl(map.get("change_item"))); //6.교체품목
			
			paramVrVO.setEstimated_sales_item_1(StringUtil.nvl(map.get("estimated_sales_item_1"))); //10.예상월매출 품명 1번
			paramVrVO.setEstimated_sales_amt_1(StringUtil.nvl(map.get("estimated_sales_amt_1"))); //10.예상월매출 금액 1번
			paramVrVO.setEstimated_sales_item_2(StringUtil.nvl(map.get("estimated_sales_item_2"))); //10.예상월매출 품명 2번
			paramVrVO.setEstimated_sales_amt_2(StringUtil.nvl(map.get("estimated_sales_amt_2"))); //10.예상월매출 금액 2번
			paramVrVO.setEstimated_sales_item_3(StringUtil.nvl(map.get("setEstimated_sales_item_3"))); //10.예상월매출 품명 3번
			paramVrVO.setEstimated_sales_amt_3(StringUtil.nvl(map.get("estimated_sales_amt_3"))); //10.예상월매출 금액 3번
			paramVrVO.setEstimated_sales_item_4(StringUtil.nvl(map.get("setEstimated_sales_item_4"))); //10.예상월매출 품명 4번
			paramVrVO.setEstimated_sales_amt_4(StringUtil.nvl(map.get("estimated_sales_amt_4"))); //10.예상월매출 금액 4번
			paramVrVO.setEstimated_sales_item_5(StringUtil.nvl(map.get("setEstimated_sales_item_5"))); //10.예상월매출 품명 5번
			paramVrVO.setEstimated_sales_amt_5(StringUtil.nvl(map.get("estimated_sales_amt_5"))); //10.예상월매출 금액 5번
			
			paramVrVO.setPaper_form(StringUtil.nvl(map.get("paper_form"))); //11.서류양식
			paramVrVO.setOther_paper(StringUtil.nvl(map.get("other_paper"))); //12.기타 필요자료						
			paramVrVO.setUnusual(StringUtil.nvl(map.get("unusual"))); //13.특이사항
			
			if("".equals(approval_seq)){
				/*신약신청서 등록*/
				approval_seq = newReportE01013Service.insertNewMadicine(paramAmVO, paramVrVO);
			}else{
				paramVrVO.setApproval_seq(approval_seq);
				/*신약신청서 수정*/
				newReportE01013Service.updateNewMadicine(paramAmVO, paramVrVO);
			}
			
		/*개발검토서*/
		}else if("E01014".equals(map.get("docu_cd"))){
			ReviewVO paramDfVO = new ReviewVO();
			paramDfVO.setContent(StringUtil.nvl(map.get("content"))); //기안내용
			paramDfVO.setControll(StringUtil.nvl(map.get("controll"))); //통제
			paramDfVO.setCooperation(StringUtil.nvl(map.get("cooperation"))); //협조
			paramDfVO.setEtc(StringUtil.nvl(map.get("etc"))); //기타
			paramDfVO.setImposition_ymd(StringUtil.nvl(map.get("imposition_ymd")));	//시행일자
			paramDfVO.setPurpose(StringUtil.nvl(map.get("purpose"))); //기안목적
			
			if("".equals(approval_seq)){
				/*개발검토서 등록*/
				approval_seq = newReportE01014Service.insertReview(paramAmVO, paramDfVO);
			}else{
				paramDfVO.setApproval_seq(approval_seq);
				/*개발검토서 수정*/
				newReportE01014Service.updateReview(paramAmVO, paramDfVO);
			}
			
		/*물품 구입 청구서*/
		}else if("E01015".equals(map.get("docu_cd"))){
			
			String req_ymd = StringUtil.nvl(map.get("search_req_ymd")); //청구년월일
			//System.out.println("--- NewReportController insertDocument.do req_ymd : "+req_ymd);
			String req_no = StringUtil.nvl(map.get("search_req_no")); //청구번호(년도(4)+일련번호(6))
			//System.out.println("--- NewReportController insertDocument.do req_no : "+req_no);
			String[] material_id = request.getParameterValues("material_id"); //원부자재코드			
			String[] material_nm = request.getParameterValues("material_nm"); //원부자재명
			String[] standard = request.getParameterValues("standard"); //규격
			String[] unit = request.getParameterValues("unit"); //단위
			/* CHOE 20160121 입력 단계에서 거래처를 선태하지 않기 때문에 SETP1에서 내용을 제거하고 단가를 변경할때 값을 넣도록 수정한다.
			String[] cust_id = request.getParameterValues("cust_id"); //거래처코드
			String[] cust_nm = request.getParameterValues("cust_nm"); //거래처명 */
			String[] qty = request.getParameterValues("qty"); //수량
			String[] danga = request.getParameterValues("danga"); //단가
			String[] amt = request.getParameterValues("amt"); //금액
			String[] import_yn = request.getParameterValues("import_yn"); //수입여부
			String[] hyunjaego_qty = request.getParameterValues("hyunjaego_qty"); //현재고수량
			String[] usage = request.getParameterValues("usage"); //사용목적
			String[] ipgo_ymd = request.getParameterValues("ipgo_ymd"); //납기요구일
			String[] bigo = request.getParameterValues("bigo"); //비고	
			String[] requestno = request.getParameterValues("requestno"); //비고
									
			List<RequisitionVO> requisitionVOList = new ArrayList<RequisitionVO>();
	        for(int i=0 ; i<material_id.length ; i++){
	        	RequisitionVO requisitionVO = new RequisitionVO();	
	        	requisitionVO.setReq_ymd(req_ymd);	        	
	        	requisitionVO.setReq_no(req_no);	        	
	        	requisitionVO.setMaterial_id(material_id[i]);	        	
	        	requisitionVO.setMaterial_nm(material_nm[i]);
	        	requisitionVO.setStandard(standard[i]);
	        	requisitionVO.setUnit(unit[i]);
	        	/* CHOE 20160121 입력 단계에서 거래처를 선태하지 않기 때문에 SETP1에서 내용을 제거하고 단가를 변경할때 값을 넣도록 수정한다.
	        	requisitionVO.setCust_id(cust_id[i]);
	        	requisitionVO.setCust_nm(cust_nm[i]);*/
	        	requisitionVO.setQty(qty[i].replaceAll(",", ""));
	        	requisitionVO.setDanga(danga[i].replaceAll(",", ""));
	        	requisitionVO.setAmt(amt[i].replaceAll(",", ""));
	        	requisitionVO.setImport_yn(import_yn[i]);
	        	requisitionVO.setHyunjaego_qty(hyunjaego_qty[i].replaceAll(",", ""));
	        	requisitionVO.setUsage(usage[i]);
	        	requisitionVO.setIpgo_ymd(ipgo_ymd[i]);
	        	requisitionVO.setBigo(bigo[i]);
	        	requisitionVO.setRequestno(requestno[i]);
	        	
				requisitionVOList.add(requisitionVO);				
	        }
	        
	        
	        RequisitionVO paramRqVO = new RequisitionVO();
	        paramRqVO.setRequisitionVO(requisitionVOList);
			
			if("".equals(approval_seq)){
				/*물품 구입 청구서 등록*/
				//System.out.println("--- NewReportController insertRequisition");
				approval_seq = newReportE01015Service.insertRequisition(paramAmVO, paramRqVO);
			}else{
				/*물품 구입 청구서 수정*/	
				/*System.out.println("--- NewReportController updateRequisition");*/
				newReportE01015Service.updateRequisition(paramAmVO, paramRqVO);
			}	
		/*원부자재 납품확인서*/
		}else if("E01016".equals(map.get("docu_cd"))){			
			String ymd = StringUtil.nvl(map.get("search_ymd")); //입고일			
			String slip_no = StringUtil.nvl(map.get("search_slip_no")); //전표번호			
			
			String cust_id = StringUtil.nvl(map.get("cust_id")); //거래처코드
			//System.out.println("--- NewReportController insertDelivery : "+cust_id);			
			String cust_nm = StringUtil.nvl(map.get("cust_nm")); //거래처명 
			//System.out.println("--- NewReportController insertDelivery : "+cust_nm);
			
			String[] material_id = request.getParameterValues("material_id"); //원부자재코드			
			String[] material_nm = request.getParameterValues("material_nm"); //원부자재명
			String[] standard = request.getParameterValues("standard"); //규격
			String[] unit = request.getParameterValues("unit"); //단위			
			String[] qty = request.getParameterValues("qty"); //수량
			String[] danga = request.getParameterValues("danga"); //단가
			String[] amt = request.getParameterValues("amt"); //금액
			
			String[] balju_no = request.getParameterValues("balju_no"); //발주번호
			String[] vat_sum = request.getParameterValues("vat_sum"); //부가세합계
			//System.out.println("--- NewReportController insertDelivery : "+vat_sum[1]);
			String[] amt_sum = request.getParameterValues("amt_sum"); //공급가액					
			//System.out.println("--- NewReportController insertDelivery : "+amt_sum[1]);			
			String[] bigo = request.getParameterValues("bigo"); //비고
			String[] warehousingno = request.getParameterValues("warehousingno"); //비고
			
			
			List<DeliveryVO> deliveryVOList = new ArrayList<DeliveryVO>();
	        for(int i=0 ; i<material_id.length ; i++){
	        	DeliveryVO deliveryVO = new DeliveryVO();	
	        	deliveryVO.setYmd(ymd);	  
	        	//System.out.println("--- NewReportController insertDelivery deliveryVO.getSlip_no() : "+deliveryVO.getYmd());
	        	deliveryVO.setSlip_no(slip_no);
	        	//System.out.println("--- NewReportController insertDelivery deliveryVO.getSlip_no() : "+deliveryVO.getSlip_no());
	        	deliveryVO.setCust_id(cust_id);
	        	//System.out.println("--- NewReportController insertDelivery deliveryVO.getSlip_no() : "+deliveryVO.getCust_id());
	        	deliveryVO.setCust_nm(cust_nm);
	        	//System.out.println("--- NewReportController insertDelivery deliveryVO.getSlip_no() : "+deliveryVO.getCust_nm());
	        	
	        	deliveryVO.setMaterial_id(material_id[i]);
	        	//System.out.println("--- NewReportController insertDelivery : "+material_id[i]);
	        	deliveryVO.setMaterial_nm(material_nm[i]);
	        	deliveryVO.setStandard(standard[i]);
	        	deliveryVO.setUnit(unit[i]);	        
	        	deliveryVO.setQty(qty[i].replaceAll(",", ""));
	        	deliveryVO.setDanga(danga[i].replaceAll(",", ""));
	        	deliveryVO.setAmt(amt[i].replaceAll(",", ""));
	        	
	        	deliveryVO.setBalju_no(balju_no[i]);
	        	deliveryVO.setVat_sum(vat_sum[i].replaceAll(",", ""));	        	
	        	deliveryVO.setAmt_sum(amt_sum[i].replaceAll(",", ""));	        	
	        	deliveryVO.setBigo(bigo[i]);
	        	deliveryVO.setWarehousingno(warehousingno[i]);
	        	
	        	deliveryVOList.add(deliveryVO);				
	        }
	        
	        
	        DeliveryVO paramDlVO = new DeliveryVO();
	        paramDlVO.setDeliveryVO(deliveryVOList);
			
			if("".equals(approval_seq)){
				/*원부자재 납품확인서 등록*/
				//System.out.println("--- NewReportController insertDelivery");
				approval_seq = newReportE01016Service.insertDelivery(paramAmVO, paramDlVO);
			}else{
				/*원부자재 납품확인서 수정*/	
				//System.out.println("--- NewReportController updateDelivery");
				newReportE01016Service.updateDelivery(paramAmVO, paramDlVO);
			}
		}
		
		paramMap.put("approval_seq", approval_seq);		//결재번호
		paramMap.put("docu_seq", map.get("docu_seq"));	//문서번호
		paramMap.put("make_dt", map.get("make_dt"));	//작성일자
		
		paramMap.put("returnURL", env.getValue("root_dir.url")+"/ea/newReport/step2approvalPopup.do");	//스텝2 결재라인지정 url
		
		if(!"".equals(approval_seq)){
			if(!"".equals(StringUtil.nvl(request.getParameter("fileNum")))){
				paramMap.put("fileNum", StringUtil.nvl(request.getParameter("fileNum")));	//파일첨부번호
				paramMap.put("approval_seq", approval_seq);	//결재시퀀스
				newReportService.updateFileAttach(paramMap);;
			}
			
			String delFileSeq = StringUtil.nvl(request.getParameter("delFileSeq"));	//파일첨부삭제시퀀스
			
			if(!"".equals(delFileSeq)){
				newReportService.deleteAttach(delFileSeq);
			}
			MarshallerUtil.marshalling("txt", response, CommonUtil.postMessageView(alertMessage, paramMap));
		}
		
	}
	
	/**
	 * 
	 * <pre>
	 * 1. 개요     : 전자결재 첨부파일을 업로드한다. 
	 * 2. 처리내용 : 업로드한 파일은 비동기식으로 서버에 파일은 만든다.
	 * </pre>
	 * 
	 * @Method Name : approvalMultiFileUploadAction
	 * @param req
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/approvalMultiFileUploadAction.do", method = RequestMethod.POST)
	public void approvalMultiFileUploadAction(MultipartHttpServletRequest req,HttpServletResponse response)
			throws Exception {

		String gubun = StringUtil.nvl(req.getParameter("filePathKind")); //전자결재문서 경로
		
		String fileParamName  = StringUtil.nvl(req.getParameter("fileParamName"), "image");	// file객체를 담은 파라메터명
		String callbackFuncName  = StringUtil.nvl(req.getParameter("callbackFuncName"));	//upload 후에 호출할 callback함수명. 없으면 json형태로
		
		HttpSession memberSession = req.getSession();
		MemberVO memberSessionVO = (MemberVO) memberSession.getAttribute("gwUser");
		String emp_no = StringUtil.nvl(memberSessionVO.getEmp_no());
		String create_no = emp_no; // 등록자 id
		
		String descr = StringUtil.nvl(req.getParameter("descr")); // 첨부파일 설명

		/*첨부파일 vo 객체 생성*/
		FileAttachVO uploadVO = new FileAttachVO();

		uploadVO.setApproval_seq("0");
		uploadVO.setCreate_no(create_no);
		uploadVO.setDelete_yn("N");

		List files = req.getFiles(fileParamName);
		
		logger.debug("files.size() : "+files.size());

		int fileSeq = 0;
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar currentDate = Calendar.getInstance();

		String today = dateFormat.format(currentDate.getTime()); 
		
		String path = env.getValue("file_dir.url")+"/"+gubun+"/"+today;
		String relativePath = "/"+gubun+"/"+today;
		FileUtil.makeDirectory(path);

		logger.debug("------------------imgMulti---------------------");
		logger.debug("path : "+path);
		logger.debug("create_no : "+create_no);
		logger.debug("uploadVO.getCreate_no() : "+uploadVO.getCreate_no());
		logger.debug("files.size()=" + files.size());
		logger.debug("------------------imgMulti---------------------");

		if(files.size() > 0){
			for (int i = 0; i < files.size(); i++) {
	
				MultipartFile file = (MultipartFile) files.get(i);
	
				/*업로드한 파일 정보 변수 setting*/
				String fileOriginNm = file.getOriginalFilename();
				String fileServerNm = FileUtil.renameFile(fileOriginNm);
				String fileExt = FileUtil.getFileExt(fileOriginNm);
				String pathName = path + File.separator + fileServerNm;
	
				/*서버에 파일 저장*/
				file.transferTo(new File(pathName));
	
				/*DB 저장*/
				uploadVO.setOrigin_file_nm(fileOriginNm);
				uploadVO.setFile_nm(fileServerNm);
				uploadVO.setFile_size(file.getSize());
				uploadVO.setFile_ext(fileExt);
				uploadVO.setDescr(descr);
				uploadVO.setFile_path(relativePath);
	
				//System.out.println("----------new getFile_seq : "+uploadVO.getFile_seq());
				//System.out.println("----------new getApproval_seq : "+uploadVO.getApproval_seq());
				//System.out.println("----------new getOpinion_seq : "+uploadVO.getOpinion_seq());
				//System.out.println("----------new fileOriginNm : "+fileOriginNm);
				fileSeq = newReportService.insertFileAttach(uploadVO);
	
			}
		}
		
		uploadVO.setFile_seq(fileSeq);
		
		/*callback함수가 있을 경우 fileSeq를 인수로 해서 호출한다.*/
		if("".equals(callbackFuncName)){
			MarshallerUtil.marshalling("json", response, uploadVO);
		} else {
			/*현재 document는 response에 write되는 script문자열이 전부이므로 부모창의 함수를 호출하는 것이므로.*/
			String script = "<script>try{parent."+callbackFuncName+"('"+fileSeq+"');}catch(e){opener."+callbackFuncName+"('"+fileSeq+"');}</script>";
			MarshallerUtil.marshalling("txt", response, script);
		}
		
	}
	
	/**
	 * <pre>
	 * 1. 개요     : 스텝3 상세정보
	 * 2. 처리내용 : 스텝3 상세정보
	 * </pre>
	 * @Method Name : step3approvalPopup
	 * @param request
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/ea/newReport/step3approvalPopup.do")
	public ModelAndView step3approvalPopup(HttpServletRequest request) throws Exception {
		
		ModelAndView mav = new ModelAndView("ea/newReport/step3approvalPopup");

		Map<String, String> paramMap = new HashMap<String, String>();
		
		HttpSession session = request.getSession();
		MemberVO memberSessionVO = (MemberVO) session.getAttribute("gwUser");
		
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		
		String docu_seq = StringUtil.nvl(request.getParameter("docu_seq"));
		String approval_seq = StringUtil.nvl(request.getParameter("approval_seq"));

		// ml180126.ml05_T85 김진국 - NewReportDAO, newReport.xml, NewReprtService, NewReportController에 isYMustOpinion 추가 - 결재 등록시 '시의필' 실행했다가 취소하려고 Start
		//		String isYMustOpinion = newReportService.isYMustOpinion(approval_seq);
		//		System.out.println("lllll~~~~~ isYMustOpinion 01 : " + isYMustOpinion);
		//		mav.addObject("isYMustOpinion", isYMustOpinion);
		// ml180126.ml05_T85 김진국 - NewReportDAO, newReport.xml, NewReprtService, NewReportController에 isYMustOpinion 추가 - 결재 등록시 '시의필' 실행했다가 취소하려고 End
		
		/*문서 기본정보 가져온다.*/
		paramMap.put("docu_seq", docu_seq);
		DocumentInfoVO documentInfoDetail =  documentInfoService.getDocumentInfoDetail(paramMap);
		String docu_cd = documentInfoDetail.getDocu_cd();
		
		/*CHOE 20160113 물품구입청구확인서는 main 상단 화면을 다르게 사용한다: 가로 결재 - IRUSH 제공화면으로*/
		if(docu_cd.equals("E01015")) {			
			mav.setViewName("ea/newReport/step3approvalPopup1015");	
		}else if(docu_cd.equals("E01016")) {			
				mav.setViewName("ea/newReport/step3approvalPopup1015");
		} else {
			mav.setViewName("ea/newReport/step3approvalPopup");
		}

		if(docu_cd.equals("E01001")){
			
			/*휴가신청서 상세정보*/
			VacationVO vacationVO = newReportE01001Service.vacationDetail(approval_seq);
			mav.addObject("vacationVO", vacationVO);
			
			/*개인 연차 정보 가져온다.*/
			paramMap.put("emp_no", memberSessionVO.getEmp_no());
			paramMap.put("yr_year", Integer.toString(calendar.get(Calendar.YEAR)));
			/*CHOE 2017.01.16 HANAHR.F_GET_WORK_DAYS_YMD 사용*/
			DecimalFormat df  = new DecimalFormat("00");
			
			paramMap.put("yr_mmdd", df.format(calendar.get(Calendar.MONTH) + 1) + df.format(calendar.get(Calendar.DATE)));  
			//System.out.println("--- NewReportController step1approvalPopup: 날짜 " + df.format(calendar.get(Calendar.MONTH) + 1));
			//System.out.println("--- NewReportController step1approvalPopup: 날짜 " + df.format(calendar.get(Calendar.DATE)));
			AnnualMgrVO annualPlan = annualService.getAnnualPlan(paramMap);
			if(annualPlan == null){
				annualPlan = new AnnualMgrVO();
			}
			
			paramMap.put("emp_no", memberSessionVO.getEmp_no());
			paramMap.put("rq_year", Integer.toString(year));
			/* 공동연차 카운트를 가져온다.*/
			mav.addObject("annualcommonDtCnt", annualService.getAnnualCommonDTCount(paramMap));;
			mav.addObject("annualPlan", annualPlan);
			
		}else if(docu_cd.equals("E01002")){
			/*기안서 상세정보*/
			DraftVO draftVO = newReportE01002Service.draftDetail(approval_seq);
			mav.addObject("draftVO", draftVO);
				
		}else if(docu_cd.equals("E01003")){
			/*사내통신 상세정보*/
			IncompanyVO incompanyVO = newReportE01003Service.incompanyDetail(approval_seq);
			mav.addObject("incompanyVO", incompanyVO);
				
		}else if(docu_cd.equals("E01004")){
			/*기화기기안서 상세정보*/
			VaporizeVO vaporizeVO = newReportE01004Service.vaporizeDetail(approval_seq);
			mav.addObject("vaporizeVO", vaporizeVO);

			/*기화기 종류*/
			paramMap.put("m_cd", "E05");
			List<CodeVO> sCodeList = codeService.getScodeList(paramMap);
			mav.addObject("sCodeList", sCodeList);
			
		}else if(docu_cd.equals("E01005")){
			/*근태계 상세정보*/
			CommuteVO commuteVO = newReportE01005Service.commuteDetail(approval_seq);
			mav.addObject("commuteVO", commuteVO);
			
			/*CHOE 20161118 개인 지각 가져온다.*/
			paramMap.put("emp_no", memberSessionVO.getEmp_no());
			paramMap.put("year", Integer.toString(calendar.get(Calendar.YEAR)));
			
			//System.out.println("--- NewReportController step1approvalPopup: 근태계 ");
			
			CommuteVO commuteTardy = newReportE01005Service.commuteTardy(paramMap);
			if(commuteTardy == null){
				commuteTardy = new CommuteVO();
			}
			mav.addObject("commuteTardy", commuteTardy);

		}else if(docu_cd.equals("E01006")){
			/*참가신청서 상세정보*/
			ParticipationVO participationVO = newReportE01006Service.participationDetail(approval_seq);
			mav.addObject("participationVO", participationVO);

		}else if(docu_cd.equals("E01007")){
			/*샘플신청서 상세정보*/
			List<SampleVO> sampleDetailList;
			sampleDetailList = newReportE01007Service.sampleDetailList(approval_seq);
				
			mav.addObject("sampleDetailList", sampleDetailList);
			
		}else if(docu_cd.equals("E01008")){
			/*구매신청서 상세정보*/
			List<PurchaseVO> purchaseDetailList;
			purchaseDetailList = newReportE01008Service.purchaseDetailList(approval_seq);
			mav.addObject("purchaseDetailList", purchaseDetailList);
			
		}else if(docu_cd.equals("E01009")){
			/*시간외근무신청서 상세정보*/
			List<OvertimeVO> overtimeDetailList;
			overtimeDetailList = newReportE01009Service.overtimeDetailList(approval_seq);
			mav.addObject("overtimeDetailList", overtimeDetailList);
			
		}else if(docu_cd.equals("E01010")){
			/*증명서발급신청서 상세정보*/
			List<CertificateVO> certificateDetailList;
			certificateDetailList = newReportE01010Service.certificateDetailList(approval_seq);
			mav.addObject("certificateDetailList", certificateDetailList);
			
			/*증명서 종류 code*/
			paramMap.put("m_cd", "E04");
			List<CodeVO> sCodeList = codeService.getScodeList(paramMap);
			mav.addObject("sCodeList", sCodeList);
			
		}else if(docu_cd.equals("E01011")){
			
			/*사고보고서 상세정보*/
			AccidentVO accidentVO = newReportE01011Service.accidentDetail(approval_seq);
			mav.addObject("accidentVO", accidentVO);
				
		}else if(docu_cd.equals("E01012")){
			/*시간외근무내역서.*/			
			List<OvertimeVO> overtimeDetailDetailList;
			overtimeDetailDetailList = newReportE01012Service.overtimeDetailDetailList(approval_seq);
			mav.addObject("overtimeDetailDetailList", overtimeDetailDetailList);
			String approval_seq_old = StringUtil.nvl(request.getParameter("approval_seq_old"));
			mav.addObject("approval_seq_old", approval_seq_old);
			
		}else if(docu_cd.equals("E01013")){
			/*신약신청서 상세정보*/
			NewMadicineVO newmadicineVO = newReportE01013Service.NewMadicineDetail(approval_seq);
			mav.addObject("newmadicineVO", newmadicineVO);

			/*신약신청서 서류양식*/
			paramMap.put("m_cd", "E08");
			List<CodeVO> sCodeList = codeService.getScodeList(paramMap);
			mav.addObject("sCodeList", sCodeList);
			
		}else if(docu_cd.equals("E01014")){
			/*개발검토서 상세정보*/
			ReviewVO reviewVO = newReportE01014Service.reviewDetail(approval_seq);
			mav.addObject("reviewVO", reviewVO);
			
		}else if(docu_cd.equals("E01015")){
			/*물품 구입 청구서 상세정보*/
			List<RequisitionVO> requisitionDetail;
			requisitionDetail = newReportE01015Service.requisitionDetail(approval_seq);				
			mav.addObject("requisitionDetail", requisitionDetail);
			
		}else if(docu_cd.equals("E01016")){
			/*원부자재 납품확인서 상세정보*/
			List<DeliveryVO> deliveryDetail;
			deliveryDetail = newReportE01016Service.deliveryDetail(approval_seq);				
			mav.addObject("deliveryDetail", deliveryDetail);
		
		}
		

		/*문서기본 상세정보(문서번호,작성일자,작성부서,작성자,제목)*/
		ApprovalMasterVO approvalMasterVO = newReportService.approvalDetail(approval_seq);
		mav.addObject("approvalMasterVO", approvalMasterVO);
		
		paramMap.put("approval_seq", approval_seq);
		
		/*첨부파일*/
		List<FileAttachVO> attachList = newReportService.getAttachList(paramMap);
		
		/*결재라인을 가져온다*/
		List<ApprovalVO> approvalDetailList = approvalService.getApprovalDetailList(paramMap);
		
		/*시행부서를 가져온다*/
		List<ImplDeptVO> implDeptDetailList = implDeptService.getImplDeptDetailList(paramMap);
		
		mav.addObject("implDeptDetailList", implDeptDetailList);
		mav.addObject("approvalDetailList", approvalDetailList);
		mav.addObject("attachList", attachList);
		
		mav.addObject("menu", StringUtil.nvl(request.getParameter("menu")));
		mav.addObject("docu_cd", docu_cd);
		mav.addObject("docu_seq", docu_seq);
		mav.addObject("approval_seq", approval_seq); //문서번호
		
		return mav;
		
	}
	
	/**
	 * <pre>
	 * 1. 개요     : 전자결재 문서 삭제 
	 * 2. 처리내용 : 작성중인 결재문서 삭제 한다.
	 * </pre>
	 * @Method Name : deleteApproval
	 * @param request
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("/ea/newReport/deleteApprovalDocument.do")
	public void deleteApproval(HttpServletRequest request, HttpServletResponse response )
			throws IOException{
		
		boolean bResult = false;
		
		HttpSession session = request.getSession();
		MemberVO memberSessionVO = (MemberVO) session.getAttribute("gwUser");
		
		String approval_seq = StringUtil.nvl(request.getParameter("approval_seq"));	//결재마스터시퀀스
		String docu_seq = StringUtil.nvl(request.getParameter("docu_seq"));
		String docu_cd = StringUtil.nvl(request.getParameter("docu_cd"));
		
		String menu = StringUtil.nvl(request.getParameter("menu"));

		/*전자결재 문서 삭제 플래그 처리*/
		ApprovalMasterVO paramAmVO = new ApprovalMasterVO();
		paramAmVO.setApproval_seq(approval_seq);
		paramAmVO.setDelete_yn("Y");
		paramAmVO.setModify_no(memberSessionVO.getEmp_no());
		bResult = newReportService.updateApprovalMasterDelete(paramAmVO);
		
		//메뉴별 이동할 페이지
		String returnUrl = "";
		if(bResult){
			if(menu.equals("020701")){
				//결재중
				returnUrl = "window.opener.location.href='"+env.getValue("root_dir.url")+"/ea/implement/implementList.do?approval_type=E03001';";
			}else if(menu.equals("020702")){
				//결재완료
				returnUrl = "window.opener.location.href='"+env.getValue("root_dir.url")+"/ea/implement/implementList.do?approval_type=E03002';";
			}
			
			String script = "<script>alert('삭제되었습니다.'); "+returnUrl+"window.opener.document.location.reload();window.self.close();</script>";
			MarshallerUtil.marshalling("txt", response, script);
		}else{
			Map<String, String> paramMap = new HashMap<String, String>();
			paramMap.put("approval_seq", approval_seq);
			paramMap.put("docu_seq", docu_seq);
			paramMap.put("docu_cd", docu_cd);
			if(menu.equals("020701") || menu.equals("020702")){
				//결재중, 결재완료
				paramMap.put("returnURL", env.getValue("root_dir.url")+"/ea/newReport/approvalPopup.do");
			}else{
				paramMap.put("returnURL", env.getValue("root_dir.url")+"/ea/newReport/step3approvalPopup.do");//스텝3 최종확인
			}
			MarshallerUtil.marshalling("txt", response, CommonUtil.postMessageView("요청 처리 과정에서 에러가 발생하였습니다. 잠시 후 다시 시도하시거나 전산팀에 문의 바랍니다.", paramMap));
			logger.debug("*********** 삭제 실패!! ***********");
		}
	}
	
	/**
	 * <pre>
	 * 1. 개요     : 신규 문서 등록 
	 * 2. 처리내용 : 신규 문서 마지막 저장한다.
	 * </pre>
	 * @Method Name : insertDocumentComplete
	 * @param request
	 * @param map
	 * @throws IOException  
	 */
	@RequestMapping("/ea/newReport/insertDocumentComplete.do")
	public void insertDocumentComplete(HttpServletRequest request, HttpServletResponse response) 
			throws IOException {
		
		boolean bResult = false;
		
		HttpSession session = request.getSession();
		MemberVO memberSessionVO = (MemberVO) session.getAttribute("gwUser");
		
		String approval_seq = StringUtil.nvl(request.getParameter("approval_seq")); //결재문서 시퀀스
		String docu_seq = StringUtil.nvl(request.getParameter("docu_seq"));
		String docu_cd = StringUtil.nvl(request.getParameter("docu_cd"));
		
		/*결재마스터 스텝상태 업데이트*/
		ApprovalMasterVO paramAmVO = new ApprovalMasterVO();
		paramAmVO.setApproval_seq(approval_seq); //결재문서 시퀀스
		paramAmVO.setStep_state("3"); //스텝상태
		paramAmVO.setModify_no(memberSessionVO.getEmp_no()); //수정자
		bResult = newReportService.updateApprovalMasterStepState(paramAmVO);
		
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("approval_seq", approval_seq);
		paramMap.put("docu_seq", docu_seq);
		paramMap.put("docu_cd", docu_cd);
		paramMap.put("returnURL", env.getValue("root_dir.url")+"/ea/newReport/step3approvalPopup.do");	//스텝3 최종확인
		
		if(bResult){
			MarshallerUtil.marshalling("txt", response, CommonUtil.postMessageView("작성완료되었습니다.", paramMap));
		}else{
			MarshallerUtil.marshalling("txt", response, CommonUtil.postMessageView("요청 처리 과정에서 에러가 발생하였습니다. 잠시 후 다시 시도하시거나 전산팀에 문의 바랍니다.", paramMap));
		}
	}
	
	/**
	 * <pre>
	 * 1. 개요     : 신규 문서 등록 
	 * 2. 처리내용 : 신규 문서 마지막 저장과 요청한다.
	 * </pre>
	 * @Method Name : approvalComplete
	 * @param request
	 * @param map
	 * @throws IOException  
	 */
	@RequestMapping("/ea/newReport/approvalComplete.do")
	public void approvalComplete(HttpServletRequest request, HttpServletResponse response) 
			throws IOException {
		
		boolean bResult = false;
		Map<String, String> paramMap = new HashMap<String, String>();
		
		HttpSession session = request.getSession();
		MemberVO memberSessionVO = (MemberVO) session.getAttribute("gwUser");
		
		String approval_seq = StringUtil.nvl(request.getParameter("approval_seq")); //결재문서 시퀀스
		String docu_seq = StringUtil.nvl(request.getParameter("docu_seq"));
		String docu_cd = StringUtil.nvl(request.getParameter("docu_cd"));
		
		/*결재마스터 스텝상태 업데이트, 시행부서사원 테이블에 해당 사원 이관한다*/
		ApprovalMasterVO paramAmVO = new ApprovalMasterVO();
		paramAmVO.setApproval_seq(approval_seq);				 //결재문서 시퀀스
		paramAmVO.setStep_state("3");							 //스텝상태
		paramAmVO.setState("E02002"); 							 //상태
		paramAmVO.setModify_no(memberSessionVO.getEmp_no()); 	 //수정자
		
		ImplDeptEmpVO implDeptEmpVO = new ImplDeptEmpVO();
		implDeptEmpVO.setApproval_seq(approval_seq);			 //결재문서 시퀀스
		implDeptEmpVO.setCreate_no(memberSessionVO.getEmp_no());//등록자
		
		bResult = newReportService.updateApprovalMasterComplete(paramAmVO, implDeptEmpVO);

		if(bResult){
			String script = "<script>alert('요청되었습니다. 내가올린문서 목록으로 이동합니다.'); window.opener.location.href='"+env.getValue("root_dir.url")+"/ea/report/reportList.do';window.self.close();</script>";
			MarshallerUtil.marshalling("txt", response, script);
		}else{
			paramMap.put("approval_seq", approval_seq);
			paramMap.put("docu_seq", docu_seq);
			paramMap.put("docu_cd", docu_cd);
			paramMap.put("returnURL", env.getValue("root_dir.url")+"/ea/newReport/step3approvalPopup.do");	//스텝3 최종확인
			MarshallerUtil.marshalling("txt", response, CommonUtil.postMessageView("요청 처리 과정에서 에러가 발생하였습니다. 잠시 후 다시 시도하시거나 전산팀에 문의 바랍니다.", paramMap));
		}
		
	}
	
	/**
	 * <pre>
	 * 1. 개요     : 문서상세 요청 취소 
	 * 2. 처리내용 : 문서상세 요청 취소한다.
	 * </pre>
	 * @Method Name : approvalCompleteReject
	 * @param request
	 * @param map
	 * @throws IOException  
	 */
	@RequestMapping("/ea/newReport/approvalCompleteReject.do")
	public void approvalCompleteReject(HttpServletRequest request, HttpServletResponse response) 
			throws IOException {
		
		boolean bResult = false;
		Map<String, String> paramMap = new HashMap<String, String>();
		
		HttpSession session = request.getSession();
		MemberVO memberSessionVO = (MemberVO) session.getAttribute("gwUser");
		
		String approval_seq = StringUtil.nvl(request.getParameter("approval_seq")); //결재문서 시퀀스
		String menu = StringUtil.nvl(request.getParameter("menu"));
		
		/*결재마스터 상태 업데이트 요청 취소로 작성중인 상태로 변경한다.*/
		ApprovalMasterVO paramAmVO = new ApprovalMasterVO();
		
		paramAmVO.setProcess_state("REQUEST");
		paramAmVO.setApproval_seq(approval_seq); //결재문서 시퀀스
		paramAmVO.setState("E02001"); //상태
		paramAmVO.setModify_no(memberSessionVO.getEmp_no()); //수정자
		
		bResult = newReportService.updateApprovalMasterState(paramAmVO);

		if(bResult){
			String script = "<script>alert('요청 취소 되었습니다.'); window.opener.location.href='"+env.getValue("root_dir.url")+"/ea/report/reportList.do';window.self.close();</script>";
			MarshallerUtil.marshalling("txt", response, script);
		}else{
			paramMap.put("approval_seq", approval_seq);
			paramMap.put("menu", menu);
			paramMap.put("returnURL", env.getValue("root_dir.url")+"/ea/newReport/approvalPopup.do");	//스텝3 최종확인
			MarshallerUtil.marshalling("txt", response, CommonUtil.postMessageView("요청 처리 과정에서 에러가 발생하였습니다. 잠시 후 다시 시도하시거나 전산팀에 문의 바랍니다.", paramMap));
		}
		
	}
	
	/**
	 * <pre>
	 * 1. 개요     : 결재 상세정보
	 * 2. 처리내용 : 결재 상세정보
	 * </pre>
	 * @Method Name : approvalPopup
	 * @param request
	 * @return
	 */
	@RequestMapping("/ea/newReport/approvalPopup.do")
	public ModelAndView approvalPopup(HttpServletRequest request) {
		
		ModelAndView mav = new ModelAndView("ea/newReport/approvalPopup");

		Map<String, String> paramMap = new HashMap<String, String>();
		
		HttpSession session = request.getSession();
		MemberVO memberSessionVO = (MemberVO) session.getAttribute("gwUser");
		
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		
		String approval_seq = StringUtil.nvl(request.getParameter("approval_seq"));
		String menu = StringUtil.nvl(request.getParameter("menu"));
		String docu_state = StringUtil.nvl(request.getParameter("docu_state"));
		String readYnCnt = StringUtil.nvl(request.getParameter("readYnCnt"),"0");
		
		paramMap.put("approval_seq", approval_seq);
		paramMap.put("emp_no", memberSessionVO.getEmp_no());
		
		/*문서기본 상세정보(문서번호,작성일자,작성부서,작성자,제목)*/
		ApprovalMasterVO approvalMasterVO = newReportService.approvalDetail(approval_seq);
		mav.addObject("approvalMasterVO", approvalMasterVO);
		
		String docu_seq = String.valueOf(approvalMasterVO.getDocu_seq());
		
		/*문서 기본정보 가져온다.*/
		paramMap.put("docu_seq", docu_seq);
		DocumentInfoVO documentInfoDetail =  documentInfoService.getDocumentInfoDetail(paramMap);
		String docu_cd = documentInfoDetail.getDocu_cd();
		
		/*CHOE 20160113 물품구입청구확인서는 main 상단 화면을 다르게 사용한다: 가로 결재 - IRUSH 제공화면으로*/
		if(docu_cd.equals("E01015")) {			
			mav.setViewName("ea/newReport/approvalPopup1015");	
		}else if(docu_cd.equals("E01016")) {			
				mav.setViewName("ea/newReport/approvalPopup1015");
		} else {
			mav.setViewName("ea/newReport/approvalPopup");
		}
		
		/*시행문서 조회의 경우만 업데이트*/ 
		if("020701".equals(menu) || "020702".equals(menu)){
			/*조회여부 변경한다.*/ 
			ImplDeptEmpVO implDeptEmpDetail = implDeptEmpService.getImplDeptEmpDetail(paramMap);
			/*조회 안한 상태시에만 업데이트*/ 
			if(implDeptEmpDetail.getRead_yn().equals("N")){
				ImplDeptEmpVO implDeptEmpVO = new ImplDeptEmpVO();
				implDeptEmpVO.setEmp_no(memberSessionVO.getEmp_no());
				implDeptEmpVO.setApproval_seq(approval_seq);
				implDeptEmpVO.setRead_yn("Y");				
				implDeptEmpService.updateImplDeptEmpReadYN(implDeptEmpVO);
			}
		}
		
		/*공유문서 조회의 경우만 업데이트*/ 
		if("0206".equals(menu)){
			/*조회여부 변경한다.*/ 
			ShareReportVO shareReportDetail = shareReportService.getShareTargetDetail(paramMap);
			/*조회 안한 상태시에만 업데이트*/ 
			if(shareReportDetail.getRead_yn().equals("N")){
				ShareReportVO shareReportVO = new ShareReportVO();
				shareReportVO.setEmp_no(memberSessionVO.getEmp_no());
				shareReportVO.setApproval_seq(approval_seq);
				shareReportVO.setRead_yn("Y");
				shareReportService.updateShareTargetReadYn(shareReportVO);
			}
		}
		
		/*내가받은문서 조희의 경우만 업데이트*/
		if("0203".equals(menu)){
			ApprovalVO receiveReadYnDetail = receiveService.getReceiveReadYnDetail(paramMap);
			/*조회 안한 상태시에만 업데이트*/
			if(receiveReadYnDetail == null){
				ApprovalVO approvalVO = new ApprovalVO();
				approvalVO.setCreate_no(memberSessionVO.getEmp_no());
				approvalVO.setApproval_seq(approval_seq);
				receiveService.updateReceiveReadYn(approvalVO);
			}
		}
		
		/*시행문서 이고 결재 완료 이고 시간외 근무 내역서일 경우 START*/
		boolean overTimeChk = false;
		String overTimeDetailDecu_seq = "";
		if("020702".equals(menu) || "020701".equals(menu)){
			/*시행문서 이고 결재 완료 이고 시간외 근무 내역서일 경우 시간외 근무 신청서 자동 생성을 위해 데이터 있는지 유무 확인 한다.*/ 
			if(!newReportE01012Service.overtimeExist(approval_seq)){
				overTimeChk = true;
			}
			/*시행문서 이고 결재 완료 이고 시간외 근무 내역서일 경우 시간외 근무 신청서 자동 생성을 위해 docu_seq 가져온다.*/
			paramMap.put("dept_cd", documentInfoDetail.getDept_cd());
			paramMap.put("docu_cd", "E01012");
			overTimeDetailDecu_seq = StringUtil.nvl(documentInfoService.selectDocuSeq(paramMap));
		}
		mav.addObject("overTimeDetailDecu_seq", overTimeDetailDecu_seq);
		mav.addObject("overTimeChk", overTimeChk);
		/*시행문서 이고 결재 완료 이고 시간외 근무 내역서일 경우 END*/
		
		/*시행문서 결재완료 시행완료 등록START*/
		boolean implementtCompleteChk = false;
		if("020702".equals(menu)){
			/*시행문서결재 완료 이고 시행부서중 시행완료 데이터 있는지 유무 확인 한다.*/ 
			paramMap.put("dept_cd", memberSessionVO.getDept_cd());
			paramMap.put("complete_yn", "Y");
			List<ImplDeptEmpVO> implDeptEmpList = implDeptEmpService.getImplDeptEmpList(paramMap);
			if(implDeptEmpList.size() > 0){
				implementtCompleteChk = true;
			}
		}
		mav.addObject("implementtCompleteChk", implementtCompleteChk);
		/*시행문서 결재 완료 시행완료 등록START*/
		
		if(docu_cd.equals("E01001")){
			
			/*휴가신청서 상세정보*/
			VacationVO vacationVO = newReportE01001Service.vacationDetail(approval_seq);
			mav.addObject("vacationVO", vacationVO);
			
			/*개인 연차 정보 가져온다.*/
			paramMap.put("emp_no", approvalMasterVO.getCreate_no());
			paramMap.put("yr_year", Integer.toString(calendar.get(Calendar.YEAR)));
			/*CHOE 2017.01.16 HANAHR.F_GET_WORK_DAYS_YMD 사용*/
			DecimalFormat df  = new DecimalFormat("00");
			
			paramMap.put("yr_mmdd", df.format(calendar.get(Calendar.MONTH) + 1) + df.format(calendar.get(Calendar.DATE)));  
			//System.out.println("--- NewReportController step1approvalPopup: 날짜 " + df.format(calendar.get(Calendar.MONTH) + 1));
			//System.out.println("--- NewReportController step1approvalPopup: 날짜 " + df.format(calendar.get(Calendar.DATE)));
			AnnualMgrVO annualPlan = annualService.getAnnualPlan(paramMap);
			if(annualPlan == null){
				annualPlan = new AnnualMgrVO();
			}
			
			paramMap.put("rq_year", Integer.toString(year));
			/* 공동연차 카운트를 가져온다.*/
			mav.addObject("annualcommonDtCnt", annualService.getAnnualCommonDTCount(paramMap));
			mav.addObject("annualPlan", annualPlan);
			
		}else if(docu_cd.equals("E01002")){
			/*기안서 상세정보*/
			DraftVO draftVO = newReportE01002Service.draftDetail(approval_seq);
			mav.addObject("draftVO", draftVO);
				
		}else if(docu_cd.equals("E01003")){
			/*사내통신 상세정보*/
			IncompanyVO incompanyVO = newReportE01003Service.incompanyDetail(approval_seq);
			mav.addObject("incompanyVO", incompanyVO);
				
		}else if(docu_cd.equals("E01004")){
			/*기화기기안서 상세정보*/
			VaporizeVO vaporizeVO = newReportE01004Service.vaporizeDetail(approval_seq);
			mav.addObject("vaporizeVO", vaporizeVO);

			/*기화기 종류*/
			paramMap.put("m_cd", "E05");
			List<CodeVO> sCodeList = codeService.getScodeList(paramMap);
			mav.addObject("sCodeList", sCodeList);
			
		}else if(docu_cd.equals("E01005")){
			/*근태계 상세정보*/
			CommuteVO commuteVO = newReportE01005Service.commuteDetail(approval_seq);
			mav.addObject("commuteVO", commuteVO);
			
			/*CHOE 20161118 개인 지각 가져온다.*/
			paramMap.put("emp_no", approvalMasterVO.getCreate_no());
			paramMap.put("year", Integer.toString(calendar.get(Calendar.YEAR)));
			
			//System.out.println("--- NewReportController step1approvalPopup: 근태계 ");
			
			CommuteVO commuteTardy = newReportE01005Service.commuteTardy(paramMap);
			if(commuteTardy == null){
				commuteTardy = new CommuteVO();
			}
			mav.addObject("commuteTardy", commuteTardy);

		}else if(docu_cd.equals("E01006")){
			/*참가신청서 상세정보*/
			ParticipationVO participationVO = newReportE01006Service.participationDetail(approval_seq);
			mav.addObject("participationVO", participationVO);

		}else if(docu_cd.equals("E01007")){
			/*샘플신청서 상세정보*/
			List<SampleVO> sampleDetailList;
			sampleDetailList = newReportE01007Service.sampleDetailList(approval_seq);
				
			mav.addObject("sampleDetailList", sampleDetailList);
			
		}else if(docu_cd.equals("E01008")){
			/*구매신청서 상세정보*/
			List<PurchaseVO> purchaseDetailList;
			purchaseDetailList = newReportE01008Service.purchaseDetailList(approval_seq);
			mav.addObject("purchaseDetailList", purchaseDetailList);
			
		}else if(docu_cd.equals("E01009")){
			/*시간외근무신청서 상세정보*/
			List<OvertimeVO> overtimeDetailList;
			overtimeDetailList = newReportE01009Service.overtimeDetailList(approval_seq);
			mav.addObject("overtimeDetailList", overtimeDetailList);
			
		}else if(docu_cd.equals("E01010")){
			/*증명서발급신청서 상세정보*/
			List<CertificateVO> certificateDetailList;
			certificateDetailList = newReportE01010Service.certificateDetailList(approval_seq);
			mav.addObject("certificateDetailList", certificateDetailList);
			
			/*증명서 종류 code*/
			paramMap.put("m_cd", "E04");
			List<CodeVO> sCodeList = codeService.getScodeList(paramMap);
			mav.addObject("sCodeList", sCodeList);
			
		}else if(docu_cd.equals("E01011")){
			
			/*사고보고서 상세정보*/
			AccidentVO accidentVO = newReportE01011Service.accidentDetail(approval_seq);
			mav.addObject("accidentVO", accidentVO);
				
		}else if(docu_cd.equals("E01012")){
			/*시간외근무내역서.*/			
			List<OvertimeVO> overtimeDetailDetailList;
			overtimeDetailDetailList = newReportE01012Service.overtimeDetailDetailList(approval_seq);
			mav.addObject("overtimeDetailDetailList", overtimeDetailDetailList);
			
		}else if(docu_cd.equals("E01013")){
			/*신약신청서 상세정보*/
			NewMadicineVO newmadicineVO = newReportE01013Service.NewMadicineDetail(approval_seq);
			mav.addObject("newmadicineVO", newmadicineVO);

			/*신약신청서 서류양식 종류*/
			paramMap.put("m_cd", "E08");
			List<CodeVO> sCodeList = codeService.getScodeList(paramMap);
			mav.addObject("sCodeList", sCodeList);
			
		}else if(docu_cd.equals("E01014")){
			/*개발검토서 상세정보*/
			ReviewVO reviewVO = newReportE01014Service.reviewDetail(approval_seq);
			mav.addObject("reviewVO", reviewVO);
			
		}else if(docu_cd.equals("E01015")){
			/*물품 구입 청구서 상세정보*/
			List<RequisitionVO> requisitionDetail;
			requisitionDetail = newReportE01015Service.requisitionDetail(approval_seq);				
			mav.addObject("requisitionDetail", requisitionDetail);
			
		}else if(docu_cd.equals("E01016")){
			/*원부자재 납품확인서 상세정보*/
			List<DeliveryVO> deliveryDetail;
			deliveryDetail = newReportE01016Service.deliveryDetail(approval_seq);				
			mav.addObject("deliveryDetail", deliveryDetail);
		
		}
		
		/*첨부파일*/
		List<FileAttachVO> attachList = newReportService.getAttachList(paramMap);
		
		/*결재라인을 가져온다*/
		List<ApprovalVO> approvalDetailList = approvalService.getApprovalDetailList(paramMap);
		
		/*시행부서를 가져온다*/
		List<ImplDeptVO> implDeptDetailList = implDeptService.getImplDeptDetailList(paramMap);
		
		/*내 결재차례를 가져온다.*/
		ApprovalVO approvalNowEmpNo = approvalService.getApprovalNowEmpNo(paramMap);
		
		/*의견을 가져온다*/
		List<OpinionVO> opinionList = opinionService.getOpinionList(paramMap);
		
		/*관리자 권한 리스트 가져오기*/
		int [] authIdxArray = {1,2};
		Map<String, Object> paramMap1 = new HashMap<String, Object>();
		paramMap1.put("authIdxArray", authIdxArray);
		List<MemberVO> authorityMemberList = authorityService.getAuthorityMemberList(paramMap1);
		
		mav.addObject("approvalDetailList", approvalDetailList); //결재라인
		mav.addObject("implDeptDetailList", implDeptDetailList); //시행부서
		mav.addObject("attachList", attachList);//첨부파일
		mav.addObject("documentInfoDetail", documentInfoDetail);//문서 기본정보
		mav.addObject("docu_cd", docu_cd);	//문서코드
		mav.addObject("docu_seq", docu_seq);	//문서번호
		mav.addObject("approval_seq", approval_seq); //문서번호
		mav.addObject("menu", menu);	// 메뉴ID
		mav.addObject("authorityMemberList", authorityMemberList);	// 메뉴ID
		mav.addObject("docu_state", docu_state);	//결재문서상태(기결,미결,반려)
		mav.addObject("approvalNowEmpNo", approvalNowEmpNo);	//내 결재차례
		mav.addObject("opinionList", opinionList);	//의견
		mav.addObject("readYnCnt", readYnCnt);	//의견등록,삭제 후 성공값
		
		
		return mav;
		
	}
	
	/**
	 * <pre>
	 * 1. 개요     : 결재 상세정보 인쇄
	 * 2. 처리내용 : 결재 상세정보 인쇄
	 * </pre>
	 * @Method Name : approvalPreviewPopup
	 * @param request
	 * @return
	 */
	@RequestMapping("/ea/newReport/approvalPreviewPopup.do")
	public ModelAndView approvalPreviewPopup(HttpServletRequest request) {
		
		ModelAndView mav;
		
		Map<String, String> paramMap = new HashMap<String, String>();
		
		HttpSession session = request.getSession();
		MemberVO memberSessionVO = (MemberVO) session.getAttribute("gwUser");
		
		Calendar calendar = Calendar.getInstance();
		
		
		String approval_seq = StringUtil.nvl(request.getParameter("approval_seq"));
		
		/*문서기본 상세정보(문서번호,작성일자,작성부서,작성자,제목)*/
		ApprovalMasterVO approvalMasterVO = newReportService.approvalDetail(approval_seq);
		
		paramMap.put("approval_seq", approval_seq);
		
		String docu_seq = String.valueOf(approvalMasterVO.getDocu_seq());
		
		/*문서 기본정보 가져온다.*/
		paramMap.put("docu_seq", docu_seq);
		DocumentInfoVO documentInfoDetail =  documentInfoService.getDocumentInfoDetail(paramMap);
		String docu_cd = documentInfoDetail.getDocu_cd();
		

		/*CHOE 20160113 물품구입청구확인서는 main 상단 화면을 다르게 사용한다: 가로 결재 - IRUSH 제공화면으로*/
		if(docu_cd.equals("E01015")){
			mav = new ModelAndView("ea/newReport/approvalPreviewPopup1015");
		}else if(docu_cd.equals("E01016")){
				mav = new ModelAndView("ea/newReport/approvalPreviewPopup1015");
		}else{
			mav = new ModelAndView("ea/newReport/approvalPreviewPopup");
		}
		mav.addObject("approvalMasterVO", approvalMasterVO);
		
		if(docu_cd.equals("E01001")){
			
			/*휴가신청서 상세정보*/
			VacationVO vacationVO = newReportE01001Service.vacationDetail(approval_seq);
			mav.addObject("vacationVO", vacationVO);
			
			/*개인 연차 정보 가져온다.*/
			paramMap.put("emp_no", memberSessionVO.getEmp_no());
			paramMap.put("yr_year", Integer.toString(calendar.get(Calendar.YEAR)));
			/*CHOE 2017.01.16 HANAHR.F_GET_WORK_DAYS_YMD 사용*/
			DecimalFormat df  = new DecimalFormat("00");
			
			paramMap.put("yr_mmdd", df.format(calendar.get(Calendar.MONTH) + 1) + df.format(calendar.get(Calendar.DATE)));  
			//System.out.println("--- NewReportController step1approvalPopup: 날짜 " + df.format(calendar.get(Calendar.MONTH) + 1));
			//System.out.println("--- NewReportController step1approvalPopup: 날짜 " + df.format(calendar.get(Calendar.DATE)));
			AnnualMgrVO annualPlan = annualService.getAnnualPlan(paramMap);
			if(annualPlan == null){
				annualPlan = new AnnualMgrVO();
			}
			
			mav.addObject("annualPlan", annualPlan);
			
		}else if(docu_cd.equals("E01002")){
			/*기안서 상세정보*/
			DraftVO draftVO = newReportE01002Service.draftDetail(approval_seq);
			mav.addObject("draftVO", draftVO);
				
		}else if(docu_cd.equals("E01003")){
			/*사내통신 상세정보*/
			IncompanyVO incompanyVO = newReportE01003Service.incompanyDetail(approval_seq);
			mav.addObject("incompanyVO", incompanyVO);
				
		}else if(docu_cd.equals("E01004")){
			/*기화기기안서 상세정보*/
			VaporizeVO vaporizeVO = newReportE01004Service.vaporizeDetail(approval_seq);
			mav.addObject("vaporizeVO", vaporizeVO);

			/*기화기 종류*/
			paramMap.put("m_cd", "E05");
			List<CodeVO> sCodeList = codeService.getScodeList(paramMap);
			mav.addObject("sCodeList", sCodeList);
			
		}else if(docu_cd.equals("E01005")){
			/*근태계 상세정보*/
			CommuteVO commuteVO = newReportE01005Service.commuteDetail(approval_seq);
			mav.addObject("commuteVO", commuteVO);
			
			/*CHOE 20161118 개인 지각 가져온다.*/
			paramMap.put("emp_no", memberSessionVO.getEmp_no());
			paramMap.put("year", Integer.toString(calendar.get(Calendar.YEAR)));
			
			//System.out.println("--- NewReportController step1approvalPopup: 근태계 ");
			
			CommuteVO commuteTardy = newReportE01005Service.commuteTardy(paramMap);
			if(commuteTardy == null){
				commuteTardy = new CommuteVO();
			}
			mav.addObject("commuteTardy", commuteTardy);

		}else if(docu_cd.equals("E01006")){
			/*참가신청서 상세정보*/
			ParticipationVO participationVO = newReportE01006Service.participationDetail(approval_seq);
			mav.addObject("participationVO", participationVO);

		}else if(docu_cd.equals("E01007")){
			/*샘플신청서 상세정보*/
			List<SampleVO> sampleDetailList;
			sampleDetailList = newReportE01007Service.sampleDetailList(approval_seq);
				
			mav.addObject("sampleDetailList", sampleDetailList);
			
		}else if(docu_cd.equals("E01008")){
			/*구매신청서 상세정보*/
			List<PurchaseVO> purchaseDetailList;
			purchaseDetailList = newReportE01008Service.purchaseDetailList(approval_seq);
			mav.addObject("purchaseDetailList", purchaseDetailList);
			
		}else if(docu_cd.equals("E01009")){
			/*시간외근무신청서 상세정보*/
			List<OvertimeVO> overtimeDetailList;
			overtimeDetailList = newReportE01009Service.overtimeDetailList(approval_seq);
			mav.addObject("overtimeDetailList", overtimeDetailList);
			
		}else if(docu_cd.equals("E01010")){
			/*증명서발급신청서 상세정보*/
			List<CertificateVO> certificateDetailList;
			certificateDetailList = newReportE01010Service.certificateDetailList(approval_seq);
			mav.addObject("certificateDetailList", certificateDetailList);
			
			/*증명서 종류 code*/
			paramMap.put("m_cd", "E04");
			List<CodeVO> sCodeList = codeService.getScodeList(paramMap);
			mav.addObject("sCodeList", sCodeList);
			
		}else if(docu_cd.equals("E01011")){
			
			/*사고보고서 상세정보*/
			AccidentVO accidentVO = newReportE01011Service.accidentDetail(approval_seq);
			mav.addObject("accidentVO", accidentVO);
				
		}else if(docu_cd.equals("E01012")){
			/*시간외근무내역서.*/			
			List<OvertimeVO> overtimeDetailDetailList;
			overtimeDetailDetailList = newReportE01012Service.overtimeDetailDetailList(approval_seq);
			mav.addObject("overtimeDetailDetailList", overtimeDetailDetailList);
		
		}else if(docu_cd.equals("E01013")){
			/*신약신청서 상세정보*/
			NewMadicineVO newmadicineVO = newReportE01013Service.NewMadicineDetail(approval_seq);
			mav.addObject("newmadicineVO", newmadicineVO);

			/*신약신청서 서류양식*/
			paramMap.put("m_cd", "E08");
			List<CodeVO> sCodeList = codeService.getScodeList(paramMap);
			mav.addObject("sCodeList", sCodeList);
			
		}else if(docu_cd.equals("E01014")){
			/*개발검토서 상세정보*/
			ReviewVO reviewVO = newReportE01014Service.reviewDetail(approval_seq);
			mav.addObject("reviewVO", reviewVO);
			
		}else if(docu_cd.equals("E01015")){
			/*물품 구입 청구서 상세정보*/
			List<RequisitionVO> requisitionDetail;
			requisitionDetail = newReportE01015Service.requisitionDetail(approval_seq);				
			mav.addObject("requisitionDetail", requisitionDetail);
		
		}else if(docu_cd.equals("E01016")){
			/*원부자재 납품확인서 상세정보*/
			List<DeliveryVO> deliveryDetail;
			deliveryDetail = newReportE01016Service.deliveryDetail(approval_seq);				
			mav.addObject("deliveryDetail", deliveryDetail);
		}
		
		/*결재라인을 가져온다*/
		List<ApprovalVO> approvalDetailList = approvalService.getApprovalDetailList(paramMap);
		
		mav.addObject("approvalDetailList", approvalDetailList);
		
		mav.addObject("docu_cd", docu_cd);
		mav.addObject("docu_seq", docu_seq);
		mav.addObject("approval_seq", approval_seq); //문서번호
		
		return mav;
		
	}


	/**
	 * <pre>
	 * 1. 개요     : 시행부서 의견 필수 유무
	 * 2. 처리내용 : 시행부서 의견 필수 유무로 결재 마스터를 변경하다
	 * 3. ml180116.ml04_T06 김진국 - NewReportController.java에 addMustOpinion 메서드 추가 - 웹단 JSP에서 자바스크립트 펑션 호출 되었을 때 호출되어지는 서버단의 컨트롤러
	 * </pre>
	 * @Method Name : addMustOpinion
	 * @param
	 * @return
	 */
	@RequestMapping("/ea/newReport/addMustOpinion.do")
	public void addMustOpinion(HttpServletRequest request) throws Exception {

		HttpSession memberSession = request.getSession();
		MemberVO memberSessionVO = (MemberVO) memberSession.getAttribute("gwUser");

		String emp_no = StringUtil.nvl(memberSessionVO.getEmp_no());

		String approval_seq = StringUtil.nvl(request.getParameter("approval_seq"));	//결재마스터시퀀스

		/* 시행부서 의견 필수 시 시행부서의 구성원에게 쪽지 보냄 Start */
		newReportService.addMustOpinion(approval_seq);					// mustopinion을 'Y'로 바꾼다

		List<String> getEmpcode = implDeptService.getImplDeptDetailListMustOpinion(approval_seq); // 시행부서의 개별 사원 리스트 가져옴 - 시의필 실행하면 시행부서와 시행부서의 소속 사원 결과값을 가져와서 쪽지 보내서 알림 기능

		String subjectFromApproval = newReportService.getApprovalSubject(approval_seq);

		MessageVO messageVO = new MessageVO();
		String message_seq = StringUtil.nvl(request.getParameter("message_seq"));		//쪽지번호(시퀀스)

		String contents = "안녕하세요. 시행부서 담당자분께 안내드립니다. <br />\n <br />\n" +
				"'" + subjectFromApproval + "'" + " 전자결재 문서는 '시행부서 의견 필수' 문서입니다. <br />\n <br />\n" +
				"전자결재에 추가된 각 시행부서의 의견이 모두 등록되어야 결재가 재진행되오니<br />\n <br />\n" +
				"'" + subjectFromApproval + "'" + " 전자결재 건에 대하여 부서 구성원의 종합된 의견 또는 부서 실무자의 고견을 <br />\n <br />\n" +
				"'전자결재->시행문서조회->결재중->해당 전자결재 문서->의견'에 등록하여 주시기 바랍니다. <br />\n <br />\n" +
				"감사합니다."
				;

		// String subject = "'" + approval_seq + " 시행부서 의견 필수' 결재 알림";
		String subject = approval_seq + " '" + subjectFromApproval  + "'"  + " 시행부서 의견 필수";

		messageVO.setMessage_seq(message_seq);
		messageVO.setContents(contents);
		messageVO.setCreate_no(emp_no);
		messageVO.setSubject(subject);

		messageService.insertMessageMustOpinion(messageVO);
		int getMessageSeq = messageService.getMessageSeq();

		// ml180122.ml17_T56 김진국 - NewReportController.java에 getEmpCode 반복문 추가 - 시행부서 의견 필수 시 쪽지 보낼 리스트 사원 추가
		for (int i = 0; i < getEmpcode.size(); i++) {

			messageVO.setEmp_no(getEmpcode.get(i));
			messageVO.setMessage_seq(String.valueOf(getMessageSeq));

			messageService.insertMessageTargetMustOpinion(messageVO);
		}
		/* 시행부서 의견 필수 시 시행부서의 구성원에게 쪽지 보냄 End */

		/* ml180124.ml07_T76 김진국 - newReportController.java getApprovalerMustOpinion 로직 추가 - 시의필 실행시 결재 작성자와 결재자에게도 쪽지 보내짐 */
		/* 시행부서 의견 필수 시 결재작성자와 결재라인의 결재자에게 쪽지 보냄 Start */
		String contentsApprovalers = "안녕하세요. 결재자분께 안내드립니다. <br />\n <br />\n" +
				"'" + subjectFromApproval + "'" + " 전자결재 문서는 '시행부서 의견 필수' 문서입니다. <br />\n <br />\n" +
				"전자결재에 추가된 시행부서 구성원의 종합된 의견 또는 부서 실무자의 고견이 각 부서별로 등록된 이후 결재가 진행될 예정이오니<br />\n <br />\n" +
				"해당 전자결재 문서에 대해서 다시 한번 인지해 주시고 결재를 진행해주시기 바랍니다. <br />\n <br />\n" +
				"'" + subjectFromApproval+ "'" + "의 기 결재승인(기결)은 '시행부서 의견 필수' 실행 시점에서 한단계 승인 취소가 이뤄집니다. <br />\n <br />\n" +
				"의견이 모두 등록되면 한단계 전 승인 취소된 시점에서 다시 결재승인(기결)을 해야함으로  <br />\n <br />\n" +
				"(본인이 이미 결재를 했더라도)해당 전자결재 건을 다시 열람하여 확인/결재하시기 바랍니다. <br />\n <br />\n" +
				"감사합니다."
				;

		// String subjectApprovalers = "'" + approval_seq + " 시행부서 의견 필수' 결재 알림";
		String subjectApprovalers = approval_seq + " '" + subjectFromApproval  + "'"  + " 시행부서 의견 필수";

		messageVO.setContents(contentsApprovalers);
		messageVO.setSubject(subjectApprovalers);


		messageService.insertMessageMustOpinion(messageVO);
		int getMessageSeqMustOpinion = messageService.getMessageSeq();

		List<String> getEmpcodeApprovalerMustOpinion = newReportService.getApprovalerMustOpinion(approval_seq); // 시행부서의 개별 사원 리스트 가져옴 - 시의필 실행하면 시행부서와 시행부서의 소속 사원 결과값을 가져와서 쪽지 보내서 알림 기능

		for (int i = 0; i < getEmpcodeApprovalerMustOpinion.size(); i++) {
			messageVO.setEmp_no(getEmpcodeApprovalerMustOpinion.get(i));
			messageVO.setMessage_seq(String.valueOf(getMessageSeqMustOpinion));

			messageService.insertMessageTargetMustOpinion(messageVO);

		}
		/* 시행부서 의견 필수 시 결재작성자와 결재라인의 결재자에게 쪽지 보냄 End */

		// ml180118.ml07_T29 김진국 - NewReportController.java에 addMustOpinionApproval 서비스 콜 메서드 추가 - '시의필' 버튼 누르면 결재 마스터(GW_EA_APPROVAL_MASTER) 뿐만 아니라 결재 테이블(GW_EA_APPROVAL)도 변경하기 위해서
		// newReportService.addMustOpinionApproval(approval_seq);			// approval_seq를 '9'로 바꾼다

	}

//	@RequestMapping("/ea/newReport/isYMustOpinion.do")
//	public void isYMustOpinion(HttpServletRequest request) throws Exception {
//
//		// ml180126.ml05_T85 김진국 - NewReportDAO, newReport.xml, NewReprtService, NewReportController에 isYMustOpinion 추가 - 결재 등록시 '시의필' 실행했다가 취소하려고 Start
//		String approval_seq = StringUtil.nvl(request.getParameter("approval_seq"));	//결재마스터시퀀스
//		String isYMustOpinion = newReportService.isYMustOpinion(approval_seq);
//		// ml180126.ml05_T85 김진국 - NewReportDAO, newReport.xml, NewReprtService, NewReportController에 isYMustOpinion 추가 - 결재 등록시 '시의필' 실행했다가 취소하려고 End
//
//	}

	@RequestMapping("/ea/newReport/addMustOpinionYtoN.do")
	public void addMustOpinionYtoN(HttpServletRequest request) throws Exception {

		String approval_seq = StringUtil.nvl(request.getParameter("approval_seq"));	//결재마스터시퀀스

		newReportService.addMustOpinionYtoN(approval_seq);

	}




}
