/**
  * Hana Project
  * Copyright 2014 iRush Co.,
  *
  */
package com.hanaph.gw.ea.newReport.controller;

import com.hanaph.gw.co.authority.service.AuthorityService;
import com.hanaph.gw.co.common.utils.CommonUtil;
import com.hanaph.gw.co.common.utils.Environment;
import com.hanaph.gw.co.common.utils.MarshallerUtil;
import com.hanaph.gw.co.common.utils.StringUtil;
import com.hanaph.gw.co.personnel.service.DepartmentService;
import com.hanaph.gw.co.personnel.vo.DepartmentVO;
import com.hanaph.gw.ea.mgrDocuInfo.service.BasicApprovalService;
import com.hanaph.gw.ea.mgrDocuInfo.service.BasicImplDeptService;
import com.hanaph.gw.ea.mgrDocuInfo.service.DocumentInfoService;
import com.hanaph.gw.ea.mgrDocuInfo.vo.BasicApprovalVO;
import com.hanaph.gw.ea.mgrDocuInfo.vo.BasicImplDeptVO;
import com.hanaph.gw.ea.mgrDocuInfo.vo.DocumentInfoVO;
import com.hanaph.gw.ea.newReport.service.ApprovalService;
import com.hanaph.gw.ea.newReport.service.ImplDeptEmpService;
import com.hanaph.gw.ea.newReport.service.ImplDeptService;
import com.hanaph.gw.ea.newReport.service.NewReportService;
import com.hanaph.gw.ea.newReport.vo.ApprovalMasterVO;
import com.hanaph.gw.ea.newReport.vo.ApprovalVO;
import com.hanaph.gw.ea.newReport.vo.ImplDeptEmpVO;
import com.hanaph.gw.ea.newReport.vo.ImplDeptVO;
import com.hanaph.gw.ea.person.service.PersonApprovalService;
import com.hanaph.gw.ea.person.service.PersonImplDeptService;
import com.hanaph.gw.ea.person.service.PersonLineService;
import com.hanaph.gw.ea.person.vo.PersonApprovalVO;
import com.hanaph.gw.ea.person.vo.PersonImplDeptVO;
import com.hanaph.gw.ea.person.vo.PersonLineVO;
import com.hanaph.gw.of.message.service.MessageService;
import com.hanaph.gw.of.message.vo.MessageVO;
import com.hanaph.gw.pe.member.service.MemberService;
import com.hanaph.gw.pe.member.vo.MemberVO;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.ModelAndViewDefiningException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <pre>
 * Class Name : ApprovalController.java
 * 설명 : step2.결재라인지정 Controller
 * 
 * Modification Information
 *    수정일      수정자              수정 내용
 *  ------------ -------------- --------------------------------
 *  2014. 12. 30.      CHOIILJI         
 * </pre>
 * 
 * @version : 
 * @author  : CHOIILJI(choiilji@irush.co.kr)
 * @since   : 2014. 12. 30.
 */
@Controller
public class ApprovalController {
	private Environment env = new Environment();
	
	@Autowired
	private DepartmentService departmentService;
	
	@Autowired
	private MemberService memberService;
	
	@Autowired
	private ApprovalService approvalService;
	
	@Autowired
	private ImplDeptService implDeptService;
	
	@Autowired
	private BasicApprovalService basicApprovalService;
	
	@Autowired
	private BasicImplDeptService basicImplDeptService;
	
	@Autowired
	private PersonLineService personLineService;
	
	@Autowired
	private PersonApprovalService personApprovalService;
	
	@Autowired
	private PersonImplDeptService personImplDeptService;
	
	@Autowired 
	private DocumentInfoService documentInfoService;
	
	@Autowired
	private NewReportService newReportService;
	
	@Autowired
	private AuthorityService authorityService;
	
	@Autowired
	private ImplDeptEmpService implDeptEmpService;

	@Autowired
	private MessageService messageService;
	
	private static final Logger logger = Logger.getLogger(ApprovalController.class);
	/**
	 * 
	 * <pre>
	 * 1. 개요     : 결재라인
	 * 2. 처리내용 : 결재라인 가져온다.
	 * </pre>
	 * @Method Name : getStep2ApprovalPopup
	 * @param request
	 * @return
	 * @throws IOException 
	 * @throws ModelAndViewDefiningException 
	 */
	@RequestMapping("/ea/newReport/step2approvalPopup.do")
	public ModelAndView getStep2ApprovalPopup(HttpServletRequest request, HttpServletResponse response) throws ModelAndViewDefiningException{
		
		ModelAndView mav = new ModelAndView("ea/newReport/step2approvalPopup");
		Map<String, String> paramMap = new HashMap<String, String>();  
		
		HttpSession session = request.getSession();
		MemberVO memberSessionVO = (MemberVO) session.getAttribute("gwUser");
		
		String approval_seq = StringUtil.nvl((String)request.getParameter("approval_seq"));
		String docu_seq = StringUtil.nvl((String)request.getParameter("docu_seq"));
		String person_seq = StringUtil.nvl((String)request.getParameter("person_seq"));
		String approval_gbn = StringUtil.nvl((String)request.getParameter("approval_gbn"));
		String approval_seq_old = StringUtil.nvl((String)request.getParameter("approval_seq_old"));
		String menu = StringUtil.nvl((String)request.getParameter("menu"));
		
		paramMap.put("approval_seq", approval_seq);
		paramMap.put("docu_seq", docu_seq);
		
		//관리자가 수정할경우 결재 상태 다시 한번 체크한다.
		if("020501".equals(menu)){
			ApprovalMasterVO approvalMasterVO = newReportService.approvalDetail(approval_seq);
			if("E02004".equals(approvalMasterVO.getState())){
				mav = CommonUtil.getMessageView("결재가 완료되었습니다. 수정이 불가능합니다.", "window.opener.location.reload();window.close();","");
				throw new ModelAndViewDefiningException(mav);
			}
		}
		
		//문서상세정보
		paramMap.put("docu_seq", docu_seq);
		DocumentInfoVO documentInfoDetail =  documentInfoService.getDocumentInfoDetail(paramMap);
		String docu_cd = documentInfoDetail.getDocu_cd();
		
				
		//부서 리스트
		List<DepartmentVO> departmentList = departmentService.getDepartmentList(paramMap);
		
		//결재라인 리스트
		paramMap.put("emp_no", memberSessionVO.getEmp_no());
		List<PersonLineVO> personLineList = personLineService.getPersonLineList(paramMap);
		
		//저장되 있는 결재 라인이 있으면 저장 된 라인을 보여 준다.		
		List<ApprovalVO> approvalDetailList = approvalService.getApprovalDetailList(paramMap);
		if(approvalDetailList.size() <= 0 && (approval_gbn.equals("") || approval_gbn == null)){
			approval_gbn = "BASIC";
		}
		
		mav.addObject("approvalDetailList", approvalDetailList);
		mav.addObject("departmentList", departmentList);
		mav.addObject("personLineList", personLineList);
		mav.addObject("approval_seq", approval_seq);
		mav.addObject("docu_seq", docu_seq);
		mav.addObject("person_seq", person_seq);
		mav.addObject("approval_gbn", approval_gbn);
		mav.addObject("docu_cd", docu_cd);
		mav.addObject("approval_seq_old", approval_seq_old);
		mav.addObject("menu", menu);
		
		return mav;
	}
	
	/**
	 * 
	 * <pre>
	 * 1. 개요     : 결재라인
	 * 2. 처리내용 : 결재라인 회원정보 가져온다.
	 * </pre>
	 * @Method Name : getStep2ApprovalMember
	 * @param request
	 * @return
	 */
	@RequestMapping("/ea/newReport/step2approvalMemberIframe.do")
	public ModelAndView getStep2ApprovalMember(HttpServletRequest request){
		
		ModelAndView mav = new ModelAndView("ea/newReport/step2approvalMemberIframe");
		Map<String, String> paramMap = new HashMap<String, String>();

		//세션정보 가져온다.
		HttpSession session = request.getSession();
		MemberVO memberSessionVO = (MemberVO) session.getAttribute("gwUser");
		
		String dept_cd = StringUtil.nvl(request.getParameter("dept_cd"), memberSessionVO.getDept_cd());
		String up_dept_gbn = StringUtil.nvl(request.getParameter("up_dept_gbn"), "1");
		String emp_ko_nm = StringUtil.nvl(request.getParameter("emp_ko_nm"));

		if(emp_ko_nm.equals("") || emp_ko_nm == null){
			//하위 조직이 없는 조직은 넘어온 dept_cd 로만 사용자를 검색 하고 하위 조직이 있는 조직은 하위의 조직까지 검색 한다. 
			if(up_dept_gbn.equals("1")){
				paramMap.put("up_dept_cd", "");
				paramMap.put("dept_cd", dept_cd);
			}else{
				paramMap.put("up_dept_cd", dept_cd);
				paramMap.put("dept_cd", "");
			}
		}else{
			paramMap.put("emp_ko_nm", emp_ko_nm);
		}
		
		//소속사용자
		List<MemberVO> memberList = memberService.getMemberList(paramMap);
		
		mav.addObject("memberList", memberList);
		mav.addObject("emp_ko_nm", emp_ko_nm);
		
        return mav;
	}
	
	/**
	 * 
	 * <pre>
	 * 1. 개요     : 결재라인
	 * 2. 처리내용 : 결재라인 상세 목록 가져온다.
	 * </pre>
	 * @Method Name : getStep2ApprovalMemberTarget
	 * @param request
	 * @return
	 */
	@RequestMapping("/ea/newReport/step2approvalMemberTargetIframe.do")
	public ModelAndView getStep2ApprovalMemberTarget(HttpServletRequest request){
		
		ModelAndView mav = new ModelAndView("ea/newReport/step2approvalMemberTargetIframe");
		Map<String, String> paramMap = new HashMap<String, String>();

		String docu_seq = StringUtil.nvl((String)request.getParameter("docu_seq"));
		String person_seq = StringUtil.nvl((String)request.getParameter("person_seq"));
		String approval_seq = StringUtil.nvl((String)request.getParameter("approval_seq"));
		String approval_gbn = StringUtil.nvl((String)request.getParameter("approval_gbn"));
		String approval_seq_old = StringUtil.nvl((String)request.getParameter("approval_seq_old"));
		
		paramMap.put("approval_seq", approval_seq);
		paramMap.put("docu_seq", docu_seq);
		paramMap.put("person_seq", person_seq);
		
		List<ApprovalVO> approvalDetailList = approvalService.getApprovalDetailList(paramMap);
		
		//저장되 있는 결재 라인이 있으면 저장 된 라인을 보여 준다.
		if(approvalDetailList.size() > 0 && (approval_gbn.equals("") || approval_gbn == null)){
			mav.addObject("approvalDetailList", approvalDetailList);
		}else{
			//저장되 있는 결재 라인이 없으면 approval_gbn 에따라 결재라인을 보여준다.
			if(approval_gbn.equals("BASIC")){
				//결재
				List<BasicApprovalVO> basicApprovalDetailList = basicApprovalService.getBasicApprovalDetailList(paramMap);
				mav.addObject("approvalDetailList", basicApprovalDetailList);
			}else if(approval_gbn.equals("PERSON")){
				//결재
				List<PersonApprovalVO> personApprovalDetailList = personApprovalService.getPersonApprovalDetailList(paramMap);
				mav.addObject("approvalDetailList", personApprovalDetailList);
			}
		}
		
		/*관리자 권한 리스트 가져오기*/
		int [] authIdxArray = {1,2};
		Map<String, Object> paramMap1 = new HashMap<String, Object>();
		paramMap1.put("authIdxArray", authIdxArray);
		List<MemberVO> authorityMemberList = authorityService.getAuthorityMemberList(paramMap1);
		
		/*문서기본 상세정보(문서번호,작성일자,작성부서,작성자,제목)*/
		ApprovalMasterVO approvalMasterVO = newReportService.approvalDetail(approval_seq);
		
		
		mav.addObject("docu_seq", docu_seq);
		mav.addObject("person_seq", person_seq);
		mav.addObject("approval_seq", approval_seq);
		mav.addObject("approval_seq_old", approval_seq_old);
		mav.addObject("approval_gbn", approval_gbn);
		mav.addObject("authorityMemberList", authorityMemberList);
		mav.addObject("approvalMasterVO", approvalMasterVO);
		
        return mav;
	}
	
	/**
	 * 
	 * <pre>
	 * 1. 개요     : 결재라인
	 * 2. 처리내용 : 결재라인 협조 부서 목록 가져온다.
	 * </pre>
	 * @Method Name : getStep2ApprovalDept
	 * @param request
	 * @return
	 */
	@RequestMapping("/ea/newReport/step2approvalDeptIframe.do")
	public ModelAndView getStep2ApprovalDept(HttpServletRequest request){
		
		ModelAndView mav = new ModelAndView("ea/newReport/step2approvalDeptIframe");
		Map<String, String> paramMap = new HashMap<String, String>();
		String dept_cd = StringUtil.nvl(request.getParameter("dept_cd"), "00000");
		//하위조직 리스트
		paramMap.put("up_dept_cd", dept_cd);
		List<DepartmentVO> deptTerminalList = departmentService.getDepartmentTerminalList(paramMap);
		
		mav.addObject("deptTerminalList", deptTerminalList);
		
        return mav;
	}
	
	/**
	 * 
	 * <pre>
	 * 1. 개요     : 결재라인
	 * 2. 처리내용 : 시행라인 상세 목록 가져온다.
	 * </pre>
	 * @Method Name : getStep2ApprovalDeptTarget
	 * @param request
	 * @return
	 */
	@RequestMapping("/ea/newReport/step2approvalDeptTargetIframe.do")
	public ModelAndView getStep2ApprovalDeptTarget(HttpServletRequest request){
		
		ModelAndView mav = new ModelAndView("ea/newReport/step2approvalDeptTargetIframe");
		Map<String, String> paramMap = new HashMap<String, String>();
		String docu_seq = StringUtil.nvl((String)request.getParameter("docu_seq"));
		String person_seq = StringUtil.nvl((String)request.getParameter("person_seq"));
		String approval_seq = StringUtil.nvl((String)request.getParameter("approval_seq"));
		String approval_gbn = StringUtil.nvl((String)request.getParameter("approval_gbn"));
		String approval_seq_old = StringUtil.nvl((String)request.getParameter("approval_seq_old"));
		
		paramMap.put("docu_seq", docu_seq);
		paramMap.put("person_seq", person_seq);
		paramMap.put("approval_seq", approval_seq);
		
		List<ApprovalVO> approvalDetailList = approvalService.getApprovalDetailList(paramMap);
		//저장되 있는 결재 라인이 있으면 저장 된 라인을 보여 준다.
		if(approvalDetailList.size() > 0 && (approval_gbn.equals("") || approval_gbn == null)){
			List<ImplDeptVO> implDeptDetailList = implDeptService.getImplDeptDetailList(paramMap);
			mav.addObject("implDeptDetailList", implDeptDetailList);
		}else{
		//저장되 있는 결재 라인이 없으면 approval_gbn 에따라 결재라인을 보여준다.
			if(approval_gbn.equals("BASIC")){
				//시행
				List<BasicImplDeptVO> basicImplDeptDetailList = basicImplDeptService.getBasicImplDeptDetailList(paramMap);
				mav.addObject("implDeptDetailList", basicImplDeptDetailList);
			}else if(approval_gbn.equals("PERSON")){
				//시행
				List<PersonImplDeptVO> personImplDeptDetailList = personImplDeptService.getPersonImplDeptDetailList(paramMap);
				mav.addObject("implDeptDetailList", personImplDeptDetailList);
			}
		}
		
		mav.addObject("docu_seq", docu_seq);
		mav.addObject("person_seq", person_seq);
		mav.addObject("approval_seq", approval_seq);
		mav.addObject("approval_seq_old", approval_seq_old);
		mav.addObject("approval_gbn", approval_gbn);
		
        return mav;
	}
	
	
	/**
	 * 
	 * <pre>
	 * 1. 개요     : 결재라인 저장
	 * 2. 처리내용 : 결재라인 저장한다.
	 * </pre>
	 * @Method Name : step2approvalInsert
	 * @param request
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("/ea/newReport/step2approvalInsert.do")
	public void step2approvalInsert(HttpServletRequest request, HttpServletResponse response) throws IOException{
		
		Map<String, String> paramMap = new HashMap<String, String>();
		
		HttpSession session = request.getSession();
		MemberVO memberSessionVO = (MemberVO) session.getAttribute("gwUser");
		
		String create_no = memberSessionVO.getEmp_no();
		String docu_seq = StringUtil.nvl((String)request.getParameter("docu_seq"));
		String person_seq = StringUtil.nvl((String)request.getParameter("person_seq"));
		String approval_seq = StringUtil.nvl((String)request.getParameter("approval_seq"));
		String approval_gbn = StringUtil.nvl((String)request.getParameter("approval_gbn"));
		String approval = StringUtil.nvl((String)request.getParameter("approval"));
		String implDept = StringUtil.nvl((String)request.getParameter("implDept"));
		String approval_seq_old = StringUtil.nvl((String)request.getParameter("approval_seq_old"));
		String menu = StringUtil.nvl((String)request.getParameter("menu"));		
		String group_div = "1"; //CHOE 201230 물품 구입 청구서 - 1 결재(청구결재) 2확인결재
		
		String state = "E03001";	//작성중
		
		String[] approvalArr;
		String[] implDeptArr;
		
		if(!approval.equals("") && approval != null){
			approvalArr = approval.split(",");
		}else{
			approvalArr = new String[0];
		}
		
		if(!implDept.equals("") && implDept != null){
			implDeptArr = implDept.split(",");
		}else{
			implDeptArr = new String[0];
		}

		//결재마스터 상태 업데이트
		logger.debug(">>>>>>>>>>>>>>>>>>>>>결재마스터");
		ApprovalMasterVO approvalMasterVO = new ApprovalMasterVO(); 
		approvalMasterVO.setApproval_seq(approval_seq);
		approvalMasterVO.setModify_no(create_no);
		//어드민만 진행중인 상태에서결재 라인을 수정 할 수 있기 때문에 스텝 상태는 최종 상태 그대로 업데이트 한다. 그 이외는 STEP2 로 저장한다. 
		if("020501".equals(menu)){
			approvalMasterVO.setStep_state("3");
		}else{
			approvalMasterVO.setStep_state("2");
		}
		
		paramMap.put("approval_seq", approval_seq);
		List<ApprovalVO> approvalVOList = new ArrayList<ApprovalVO>();
		List<ImplDeptVO> implDeptVOList = new ArrayList<ImplDeptVO>();
		logger.debug(">>>>>>>>>>>>>>>>>>>>>결재");
		//결재
		if(approvalArr.length > 0){
			String approval_emp_no = "";
			String approval_ordering = "";
			for (int i = 0; i < approvalArr.length; i++) {
				String approvalArrValue[] = approvalArr[i].split("\\|");
				
				approval_emp_no = approvalArrValue[0];
				approval_ordering = approvalArrValue[1];				

				//----------------------------------------------------------------------------------------------
				//CHOE 20151229 조원형2011020 안상윤2004074 윤홍주2002252 조동훈2006178 최동재2015190 5명 추가				
				//System.out.println("--- ApprovalController step2approvalInsert approval_emp_no : "+ approval_emp_no);
				if ("E010152011020".equals(approval_emp_no)){
					approval_emp_no = "2011020";
					group_div = "2";
					//System.out.println("--- ApprovalController step2approvalInsert approval_emp_no 변경 : "+ approval_emp_no);
				//}else if ("E010152004074".equals(approval_emp_no)){
					//approval_emp_no = "2004074";
					//group_div = "2";
					//System.out.println("--- ApprovalController step2approvalInsert approval_emp_no 변경 : "+ approval_emp_no);
				}else if ("E010152002252".equals(approval_emp_no)){
					approval_emp_no = "2002252";
					group_div = "2";
					//System.out.println("--- ApprovalController step2approvalInsert approval_emp_no 변경 : "+ approval_emp_no);
				//}else if ("E010152006178".equals(approval_emp_no)){
					//approval_emp_no = "2006178";
					//group_div = "2";
					//System.out.println("--- ApprovalController step2approvalInsert approval_emp_no 변경 : "+ approval_emp_no);
				}else if ("E010152015190".equals(approval_emp_no)){
					approval_emp_no = "2015190";
					group_div = "2";
					//System.out.println("--- ApprovalController step2approvalInsert approval_emp_no 변경 : "+ approval_emp_no);
				}
				//----------------------------------------------------------------------------------------------			
				
				ApprovalVO approvalVO = new ApprovalVO();
				approvalVO.setEmp_no(approval_emp_no);				
				approvalVO.setApproval_seq(approval_seq);
				approvalVO.setState(state);
				approvalVO.setOrdering(Integer.parseInt(approval_ordering));
				approvalVO.setCreate_no(create_no);
				approvalVO.setGroup_div(group_div);
				
				approvalVOList.add(approvalVO);
			}
		}
		
		logger.debug(">>>>>>>>>>>>>>>>>>>>>시행");
		//시행
		if(implDeptArr.length > 0){
			String cooperation_dept_cd = "";
			String cooperation_ordering = "";
			for (int i = 0; i < implDeptArr.length; i++) {
				String implDeptArrValue[] = implDeptArr[i].split("\\|");
				cooperation_dept_cd = implDeptArrValue[0];
				cooperation_ordering = implDeptArrValue[1];
				
				ImplDeptVO implDeptVO = new ImplDeptVO();
				implDeptVO.setApproval_seq(approval_seq);
				implDeptVO.setDept_cd(cooperation_dept_cd);
				implDeptVO.setOrdering(Integer.parseInt(cooperation_ordering));
				implDeptVO.setCreate_no(create_no);
				implDeptVOList.add(implDeptVO);
			}
		}
		
		int iResult = approvalService.insertApprovalAll(paramMap, approvalVOList, implDeptVOList, approvalMasterVO);
		
		paramMap.put("docu_seq", docu_seq);
		paramMap.put("person_seq", person_seq);
		paramMap.put("approval_seq", approval_seq);
		paramMap.put("approval_gbn", approval_gbn);
		paramMap.put("approval_seq_old", approval_seq_old);
		
		
		if(iResult > 0){
			if("020501".equals(menu)){
				String script = "<script>alert('결재라인이 수정 되었습니다.'); window.self.close();</script>";
				MarshallerUtil.marshalling("txt", response, script);
			}else{
				paramMap.put("returnURL", env.getValue("root_dir.url")+"/ea/newReport/step3approvalPopup.do");
				MarshallerUtil.marshalling("txt", response, CommonUtil.postMessageView("결재라인이 저장 되었습니다.", paramMap));
			}	
		}else{
			paramMap.put("returnURL", env.getValue("root_dir.url")+"/ea/newReport/step2approvalPopup.do");
			MarshallerUtil.marshalling("txt", response, CommonUtil.postMessageView("요청 처리 과정에서 에러가 발생하였습니다. 잠시 후 다시 시도하시거나 전산팀에 문의 바랍니다.", paramMap));
		}
	}
	
	/**
	 * 
	 * <pre>
	 * 1. 개요     : 시행부서 추가
	 * 2. 처리내용 : 시행부서 추가
	 * </pre>
	 * @Method Name : addApprovalImplDept
	 * @param request
	 * @return
	 */
	@RequestMapping("/ea/newReport/addApprovalImplDept.do")
	public ModelAndView addApprovalImplDept(HttpServletRequest request){
		
		ModelAndView mav = new ModelAndView("ea/newReport/addApprovalImplDept");
		
		String approval_seq = StringUtil.nvl((String)request.getParameter("approval_seq"));
		
		mav.addObject("approval_seq", approval_seq);
		
		return mav;
	}
	
	/**
	 * 
	 * <pre>
	 * 1. 개요     : 시행부서 추가
	 * 2. 처리내용 : 시행부서 추가 부서 리스트
	 * </pre>
	 * @Method Name : addApprovalDept
	 * @param request
	 * @return
	 */
	@RequestMapping("/ea/newReport/addApprovalImplDeptIframe.do")
	public ModelAndView addApprovalImplDeptIframe(HttpServletRequest request){
		
		ModelAndView mav = new ModelAndView("ea/newReport/addApprovalImplDeptIframe");
		Map<String, String> paramMap = new HashMap<String, String>();
		String dept_cd = StringUtil.nvl(request.getParameter("dept_cd"), "0000");
		String dept_ko_nm = StringUtil.nvl(request.getParameter("dept_ko_nm"));
		//하위조직 리스트
		paramMap.put("up_dept_cd", dept_cd);
		paramMap.put("dept_ko_nm", dept_ko_nm);
		List<DepartmentVO> deptTerminalList = departmentService.getDepartmentTerminalList(paramMap);
		
		mav.addObject("deptTerminalList", deptTerminalList);
		mav.addObject("dept_ko_nm", dept_ko_nm);
		
        return mav;
	}
	
	/**
	 * 
	 * <pre>
	 * 1. 개요     : 시행부서 추가
	 * 2. 처리내용 : 시행부서 상세 목록 가져온다.
	 * </pre>
	 * @Method Name : addApprovalImplDeptTarget
	 * @param request
	 * @return
	 */
	@RequestMapping("/ea/newReport/addApprovalImplDeptTargetIframe.do")
	public ModelAndView addApprovalImplDeptTarget(HttpServletRequest request){
		
		ModelAndView mav = new ModelAndView("ea/newReport/addApprovalImplDeptTargetIframe");
		Map<String, String> paramMap = new HashMap<String, String>();
		String approval_seq = StringUtil.nvl((String)request.getParameter("approval_seq"));
		
		paramMap.put("approval_seq", approval_seq);
		
		//저장되 있는 시행부서 라인이 있으면 저장 된 라인을 보여 준다.
		List<ImplDeptVO> implDeptDetailList = implDeptService.getImplDeptDetailList(paramMap);
		mav.addObject("implDeptDetailList", implDeptDetailList);
		
		mav.addObject("approval_seq", approval_seq);
		
        return mav;
	}
	
	/**
	 * 
	 * <pre>
	 * 1. 개요     : 시행부서 저장
	 * 2. 처리내용 : 시행부서 저장한다.
	 * </pre>
	 * @Method Name : addApprovalImplDeptInsert
	 * @param request
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/ea/newReport/addApprovalImplDeptInsert.do")
	public void addApprovalImplDeptInsert(HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		Map<String, String> paramMap = new HashMap<String, String>();
		
		HttpSession session = request.getSession();
		MemberVO memberSessionVO = (MemberVO) session.getAttribute("gwUser");
		
		String create_no = memberSessionVO.getEmp_no();
		String approval_seq = StringUtil.nvl((String)request.getParameter("approval_seq"));
		String implDept = StringUtil.nvl((String)request.getParameter("implDept"));

		/*ml180129.ml03_T89 김진국 - ApprovalController.java의 addApprovalImplDeptInsert 메서드 수정 - '시행부서 추가' 실행시 쪽지 전송될 수 있도록! */
		String emp_no = StringUtil.nvl(memberSessionVO.getEmp_no());

		String[] implDeptArr;
		
		if(!implDept.equals("") && implDept != null){
			implDeptArr = implDept.split(",");
		}else{
			implDeptArr = new String[0];
		}

		//결재마스터 상태 업데이트
		paramMap.put("approval_seq", approval_seq);
		List<ImplDeptVO> implDeptList = new ArrayList<ImplDeptVO>();
		ImplDeptVO implDeptVOList = new ImplDeptVO();
		
		logger.debug(">>>>>>>>>>>>>>>>>>>>>시행");
		//시행
		if(implDeptArr.length > 0){
			String cooperation_dept_cd = "";
			String cooperation_ordering = "";
			for (int i = 0; i < implDeptArr.length; i++) {
				String implDeptArrValue[] = implDeptArr[i].split("\\|");
				cooperation_dept_cd = implDeptArrValue[0];
				cooperation_ordering = implDeptArrValue[1];
				
				ImplDeptVO implDeptVO = new ImplDeptVO();
				implDeptVO.setApproval_seq(approval_seq);
				implDeptVO.setDept_cd(cooperation_dept_cd);
				implDeptVO.setOrdering(Integer.parseInt(cooperation_ordering));
				implDeptVO.setCreate_no(create_no);
				implDeptList.add(implDeptVO);
			}
			implDeptVOList.setImplDeptVO(implDeptList);
		}
		
		ImplDeptEmpVO implDeptEmpVO = new ImplDeptEmpVO();
		implDeptEmpVO.setApproval_seq(approval_seq);
		implDeptEmpVO.setCreate_no(create_no);
		
		int iResult = implDeptService.insertImplDeptAndEmp(implDeptVOList, implDeptEmpVO);
		
		paramMap.put("approval_seq", approval_seq);
		paramMap.put("returnURL", env.getValue("root_dir.url")+"/ea/newReport/addApprovalImplDept.do");
		
		if(iResult > 0){

			/*ml180129.ml03_T89 김진국 - ApprovalController.java의 addApprovalImplDeptInsert 메서드 수정 - '시행부서 추가' 실행시 쪽지 전송될 수 있도록! Start*/
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
			/*ml180129.ml03_T89 김진국 - ApprovalController.java의 addApprovalImplDeptInsert 메서드 수정 - '시행부서 추가' 실행시 쪽지 전송될 수 있도록! End */

			String script = "<script>alert('시행부서가 추가 되었습니다.'); parent.location.reload(); parent.layerClose();</script>";
			MarshallerUtil.marshalling("txt", response, script);
		}else{
			MarshallerUtil.marshalling("txt", response, CommonUtil.postMessageView("요청 처리 과정에서 에러가 발생하였습니다. 잠시 후 다시 시도하시거나 전산팀에 문의 바랍니다.", paramMap));
		}
	}
	
	/**
	 * <pre>
	 * 1. 개요     : 결재상태이력 레이어팝업
	 * 2. 처리내용 : 결재상태이력 레이어팝업
	 * </pre>
	 * @Method Name : approvalHistoryList
	 * @param request
	 * @return
	 */
	@RequestMapping("/ea/newReport/approvalHistoryList.do")
	public ModelAndView approvalHistoryList(HttpServletRequest request) {
		
		ModelAndView mav = new ModelAndView("ea/newReport/approvalHistoryList");
		
		String approval_seq = StringUtil.nvl(request.getParameter("approval_seq"));

		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("approval_seq", approval_seq);
		
		/*결재라인을 가져온다*/
		List<ApprovalVO> approvalDetailList = approvalService.getApprovalDetailList(paramMap);
		
		/*문서기본 상세정보(문서번호,작성일자,작성부서,작성자,제목)*/
		ApprovalMasterVO approvalMasterVO = newReportService.approvalDetail(approval_seq);
		
		mav.addObject("approvalMasterVO", approvalMasterVO);
		mav.addObject("approvalDetailList", approvalDetailList);

		return mav;
		
	}
	
	/**
	 * 
	 * <pre>
	 * 1. 개요     : 시행완료한 대상자
	 * 2. 처리내용 : 시행완료한 대상자 목록 가져온다.
	 * </pre>
	 * @Method Name : implCompletePopup
	 * @param request
	 * @return
	 */
	@RequestMapping("/ea/newReport/implCompletePopup.do")
	public ModelAndView implCompletePopup(HttpServletRequest request){
		
		ModelAndView mav = new ModelAndView("ea/newReport/implCompletePopup");
		
		Map<String, String> paramMap = new HashMap<String, String>();  
		
		String approval_seq = StringUtil.nvl(request.getParameter("approval_seq"));
		
		paramMap.put("approval_seq", approval_seq);
		paramMap.put("complete_yn", "Y");
		List<ImplDeptEmpVO> implCompleteList = implDeptEmpService.getImplDeptEmpList(paramMap);
		mav.addObject("implCompleteList", implCompleteList);
		
        return mav;
		
	}
	
	/**
	 * 
	 * <pre>
	 * 1. 개요     : 시행완료 등록 폼.
	 * 2. 처리내용 : 시행완료 등록 폼.
	 * </pre>
	 * @Method Name : annualPlanFormPopup
	 * @param request
	 * @return
	 */
	@RequestMapping("/ea/newReport/addImplCompleteFormPopup.do")
	public ModelAndView implCompleteFormPopup(HttpServletRequest request){
		
		String approval_seq = StringUtil.nvl(request.getParameter("approval_seq"));
		ModelAndView mav = new ModelAndView("ea/newReport/addImpleCompleteFormPopup");
		
		mav.addObject("approval_seq", approval_seq);
		return mav;
	}
	
	/**
	 * 
	 * <pre>
	 * 1. 개요     : 시행완료 저장
	 * 2. 처리내용 : 시행완료 저장한다.
	 * </pre>
	 * @Method Name : implCompleteInsert
	 * @param request
	 * @return
	 * @throws IOException 
	 * @throws ModelAndViewDefiningException 
	 */
	@RequestMapping("/ea/newReport/implCompleteInsert.do")
	public void implCompleteInsert(HttpServletRequest request, HttpServletResponse response) throws IOException, ModelAndViewDefiningException{
		
		Map<String, String> paramMap = new HashMap<String, String>();
		
		HttpSession session = request.getSession();
		MemberVO memberSessionVO = (MemberVO) session.getAttribute("gwUser");
		
		String emp_no = memberSessionVO.getEmp_no();
		String approval_seq = StringUtil.nvl(request.getParameter("approval_seq"));
		String complete_note = StringUtil.nvl((String)request.getParameter("complete_note"));
		
		ImplDeptEmpVO implDeptEmpVO = new ImplDeptEmpVO();
		implDeptEmpVO.setApproval_seq(approval_seq);
		implDeptEmpVO.setComplete_yn("Y");
		implDeptEmpVO.setComplete_note(complete_note);
		implDeptEmpVO.setEmp_no(emp_no);
		
		//저장전에 다시 한번 체크 한다.
		boolean bResult = false;
		paramMap.put("dept_cd", memberSessionVO.getDept_cd());
		paramMap.put("approval_seq", approval_seq);
		paramMap.put("complete_yn", "Y");
		List<ImplDeptEmpVO> implCompleteList = implDeptEmpService.getImplDeptEmpList(paramMap);
		ModelAndView mav = new ModelAndView("ea/newReport/step2approvalPopup");
		if(implCompleteList.size() > 0){
			mav = CommonUtil.getMessageView("다른 사용자에 의해 시행완료가 등록되었습니다.", "parent.location.reload(); parent.layerClose();","");
			throw new ModelAndViewDefiningException(mav);
		}else{
			bResult = implDeptEmpService.updateImplDeptEmpComplete(implDeptEmpVO);
		}
				
		if(bResult){
			String script = "<script>alert('저장 되었습니다.'); parent.location.reload(); parent.layerClose();</script>";
			MarshallerUtil.marshalling("txt", response, script);
		}else{
			MarshallerUtil.marshalling("txt", response, CommonUtil.postMessageView("요청 처리 과정에서 에러가 발생하였습니다. 잠시 후 다시 시도하시거나 전산팀에 문의 바랍니다.", paramMap));
		}
	}
	
	/**
	 * <pre>
	 * 1. 개요     : 관심 문서 등록 
	 * 2. 처리내용 : 관심 문서 등록 한다.
	 * </pre>
	 * @Method Name : insertInterestDocument
	 * @param request
	 * @param map
	 * @throws IOException  
	 */
	@RequestMapping("/ea/newReport/insertInterestDocument.do")
	public void interestDocumentInsert(HttpServletRequest request, HttpServletResponse response) 
			throws IOException {
		
		HttpSession session = request.getSession();
		MemberVO memberSessionVO = (MemberVO) session.getAttribute("gwUser");
		
		String emp_no = memberSessionVO.getEmp_no();
		
		String implDeptEmpInterest = StringUtil.nvl((String)request.getParameter("implDeptEmpInterest")); 
		String implDeptEmp = StringUtil.nvl((String)request.getParameter("implDeptEmp"));
		
		String approval_type = StringUtil.nvl((String)request.getParameter("approval_type"));
		String search_start_dt = StringUtil.nvl((String)request.getParameter("search_start_dt"));//기안시작일
		String search_end_dt = StringUtil.nvl((String)request.getParameter("search_end_dt"));//기안종료일
		String search_docu_cd = StringUtil.nvl((String)request.getParameter("search_docu_cd"));//문서종류
		String search_condition = StringUtil.nvl((String)request.getParameter("search_condition"));//검색조건
		String search_text = StringUtil.nvl((String)request.getParameter("search_text"));//검색어
		String search_read_yn = StringUtil.nvl((String)request.getParameter("search_read_yn"));//열람여부
		String search_interest_yn = StringUtil.nvl((String)request.getParameter("search_interest_yn"));//관심여부
		String currentPage = StringUtil.nvl(request.getParameter("currentPage"),"1");	//
		
        String[] implDeptEmpInterestArr;
        String[] implDeptEmpArr;
        
        if(!implDeptEmpInterest.equals("") && implDeptEmpInterest != null){
        	implDeptEmpInterestArr = implDeptEmpInterest.split(",");
		}else{
			implDeptEmpInterestArr = new String[0];
		}
        
        if(!implDeptEmp.equals("") && implDeptEmp != null){
        	implDeptEmpArr = implDeptEmp.split(",");
		}else{
			implDeptEmpArr = new String[0];
		}
        
        List<ImplDeptEmpVO> implDeptEmpList = new ArrayList<ImplDeptEmpVO>();
        ImplDeptEmpVO implDeptEmpVOList = new ImplDeptEmpVO();
        
        //관심문서
        if(implDeptEmpInterestArr.length > 0){
			for (int i = 0; i < implDeptEmpInterestArr.length; i++) {
				ImplDeptEmpVO implDeptEmpVO = new ImplDeptEmpVO();
				implDeptEmpVO.setApproval_seq(implDeptEmpInterestArr[i]);
				implDeptEmpVO.setInterest_yn("Y");
				implDeptEmpVO.setEmp_no(emp_no);
				implDeptEmpList.add(implDeptEmpVO);
			}
			implDeptEmpVOList.setImplDeptEmpVO(implDeptEmpList);
		}
       // none 관심문서
        if(implDeptEmpArr.length > 0){
			for (int i = 0; i < implDeptEmpArr.length; i++) {
				ImplDeptEmpVO implDeptEmpVO = new ImplDeptEmpVO();
				implDeptEmpVO.setApproval_seq(implDeptEmpArr[i]);
				implDeptEmpVO.setInterest_yn("N");
				implDeptEmpVO.setEmp_no(emp_no);
				implDeptEmpList.add(implDeptEmpVO);
			}
			implDeptEmpVOList.setImplDeptEmpVO(implDeptEmpList);
		}
        
        boolean bResult = implDeptEmpService.insertImplDeptEmpInterestYN(implDeptEmpVOList);
        
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("approval_type", approval_type);
		paramMap.put("search_start_dt", search_start_dt);
		paramMap.put("search_end_dt", search_end_dt);
		paramMap.put("search_docu_cd", search_docu_cd);
		paramMap.put("search_condition", search_condition);
		paramMap.put("search_text", search_text);
		paramMap.put("search_read_yn", search_read_yn);
		paramMap.put("search_interest_yn", search_interest_yn);
		paramMap.put("currentPage", currentPage);
		paramMap.put("returnURL", env.getValue("root_dir.url")+"/ea/implement/implementList.do");
		
		if(bResult){
			MarshallerUtil.marshalling("txt", response, CommonUtil.postMessageView("관심문서로 등록되었습니다.", paramMap));
		}else{
			MarshallerUtil.marshalling("txt", response, CommonUtil.postMessageView("요청 처리 과정에서 에러가 발생하였습니다. 잠시 후 다시 시도하시거나 전산팀에 문의 바랍니다.", paramMap));
		}
	}
}
