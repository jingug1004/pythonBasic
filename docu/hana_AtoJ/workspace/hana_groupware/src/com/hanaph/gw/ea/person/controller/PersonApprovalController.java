/**
  * Hana Project
  * Copyright 2014 iRush Co.,
  *
  */
package com.hanaph.gw.ea.person.controller;

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
import org.springframework.web.servlet.ModelAndViewDefiningException;

import com.hanaph.gw.co.common.utils.CommonUtil;
import com.hanaph.gw.co.common.utils.Environment;
import com.hanaph.gw.co.common.utils.MarshallerUtil;
import com.hanaph.gw.co.common.utils.MenuUtil;
import com.hanaph.gw.co.common.utils.StringUtil;
import com.hanaph.gw.co.menu.service.MenuService;
import com.hanaph.gw.co.menu.vo.MenuVO;
import com.hanaph.gw.co.personnel.service.DepartmentService;
import com.hanaph.gw.co.personnel.vo.DepartmentVO;
import com.hanaph.gw.ea.person.service.PersonApprovalService;
import com.hanaph.gw.ea.person.service.PersonImplDeptService;
import com.hanaph.gw.ea.person.service.PersonLineService;
import com.hanaph.gw.ea.person.vo.PersonApprovalVO;
import com.hanaph.gw.ea.person.vo.PersonImplDeptVO;
import com.hanaph.gw.ea.person.vo.PersonLineVO;
import com.hanaph.gw.pe.member.service.MemberService;
import com.hanaph.gw.pe.member.vo.MemberVO;

/**
 * <pre>
 * Class Name : PersonApprovalController.java
 * 설명 : 개인결재라인 Controller
 * 
 * Modification Information
 *    수정일      수정자              수정 내용
 *  ------------ -------------- --------------------------------
 *  2015. 1. 9.      CHOIILJI         
 * </pre>
 * 
 * @version : 1.0
 * @author  : CHOIILJI(choiilji@irush.co.kr)
 * @since   : 2015. 1. 9.
 */
@Controller
public class PersonApprovalController {
	private Environment env = new Environment();
	
	@Autowired
	private DepartmentService departmentService;
	
	@Autowired
	private MemberService memberService;
	
	@Autowired
	private MenuService menuService;
	
	@Autowired
	private PersonLineService personLineService;
	
	@Autowired
	private PersonApprovalService personApprovalService;
	
	@Autowired
	private PersonImplDeptService personImplDeptService;
	
	private static final Logger logger = Logger.getLogger(PersonApprovalController.class);
	/**
	 * 
	 * <pre>
	 * 1. 개요     : 개인결재라인
	 * 2. 처리내용 : 개인결재라인 가져온다.
	 * </pre>
	 * @Method Name : getPersonApproval
	 * @param request
	 * @return
	 */
	@RequestMapping("/ea/person/personApproval.do")
	public ModelAndView getPersonApprovalPopup(HttpServletRequest request){
		
		final String MENU_CHILD= "0204";
		
		ModelAndView mav = new ModelAndView("ea/person/personApproval");
		Map<String, String> paramMap = new HashMap<String, String>();  
		
		HttpSession session = request.getSession();
		MemberVO memberSessionVO = (MemberVO) session.getAttribute("gwUser");
		String person_seq = StringUtil.nvl((String)request.getParameter("person_seq"));
		
		//부서 리스트		
		List<DepartmentVO> departmentList = departmentService.getDepartmentList(paramMap);
				
		//결재라인 리스트
		paramMap.put("emp_no", memberSessionVO.getEmp_no());
		List<PersonLineVO> personLineList = personLineService.getPersonLineList(paramMap);
			
		//LNB 메뉴 생성
		String menu_parents = StringUtil.substring(MENU_CHILD, 2);
		MenuUtil mu = new MenuUtil();
		List<MenuVO> lnbMenuList = mu.makeLnb(request, menu_parents , menuService);
		mav.addObject("lnbMenuList", lnbMenuList);
		mav.addObject("MENU_CHILD", MENU_CHILD);
				
		mav.addObject("person_seq", person_seq);
		mav.addObject("departmentList", departmentList);
		mav.addObject("personLineList", personLineList);
		
		return mav;
	}
	
	/**
	 * 
	 * <pre>
	 * 1. 개요     : 개인결재라인
	 * 2. 처리내용 : 개인결재라인 회원정보 가져온다.
	 * </pre>
	 * @Method Name : getPersonApprovalMember
	 * @param request
	 * @return
	 */
	@RequestMapping("/ea/person/personApprovalMemberIframe.do")
	public ModelAndView getPersonApprovalMember(HttpServletRequest request){
		
		ModelAndView mav = new ModelAndView("ea/person/personApprovalMemberIframe");
		Map<String, String> paramMap = new HashMap<String, String>();
		
		//세션정보 가져온다.
		HttpSession session = request.getSession();
		MemberVO memberSessionVO = (MemberVO) session.getAttribute("gwUser");
		
		String dept_cd = StringUtil.nvl(request.getParameter("dept_cd"), memberSessionVO.getDept_cd());
		String up_dept_gbn = StringUtil.nvl(request.getParameter("up_dept_gbn"));
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
	 * 1. 개요     : 개인결재라인
	 * 2. 처리내용 : 개인결재라인 상세 목록 가져온다.
	 * </pre>
	 * @Method Name : getPersonApprovalDetail
	 * @param request
	 * @return
	 */
	@RequestMapping("/ea/person/personApprovalMemberTargetIframe.do")
	public ModelAndView getPersonApprovalDetail(HttpServletRequest request){
		
		ModelAndView mav = new ModelAndView("ea/person/personApprovalMemberTargetIframe");
		Map<String, String> paramMap = new HashMap<String, String>();
		String person_seq = StringUtil.nvl((String)request.getParameter("person_seq"));

		paramMap.put("person_seq", person_seq);
		//결재
		List<PersonApprovalVO> personApprovalDetailList = personApprovalService.getPersonApprovalDetailList(paramMap);

		mav.addObject("personApprovalDetailList", personApprovalDetailList);
		mav.addObject("person_seq", person_seq);
		
        return mav;
	}
	
	/**
	 * 
	 * <pre>
	 * 1. 개요     : 개인결재라인
	 * 2. 처리내용 : 개인결재라인 시행 부서 목록 가져온다.
	 * </pre>
	 * @Method Name : getPersonApprovalDept
	 * @param request
	 * @return
	 */
	@RequestMapping("/ea/person/personApprovalDeptIframe.do")
	public ModelAndView getPersonApprovalDept(HttpServletRequest request){
		
		ModelAndView mav = new ModelAndView("ea/person/personApprovalDeptIframe");
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
	 * 1. 개요     : 개인결재라인
	 * 2. 처리내용 : 개인시행라인 상세 목록 가져온다.
	 * </pre>
	 * @Method Name : getPersonApprovalDeptTarget
	 * @param request
	 * @return
	 */
	@RequestMapping("/ea/person/personApprovalDeptTargetIframe.do")
	public ModelAndView getPersonApprovalDeptTarget(HttpServletRequest request){
		
		ModelAndView mav = new ModelAndView("ea/person/personApprovalDeptTargetIframe");
		Map<String, String> paramMap = new HashMap<String, String>();
		String person_seq = StringUtil.nvl((String)request.getParameter("person_seq"));
		paramMap.put("person_seq", person_seq);
		
		//시행
		List<PersonImplDeptVO> personImplDeptDetailList = personImplDeptService.getPersonImplDeptDetailList(paramMap);

		mav.addObject("personImplDeptDetailList", personImplDeptDetailList);
		mav.addObject("person_seq", person_seq);
		
        return mav;
	}
	
	/**
	 * 
	 * <pre>
	 * 1. 개요     : 개인 결재라인 저장
	 * 2. 처리내용 : 개인 결재라인 저장한다.
	 * </pre>
	 * @Method Name : approvalInsert
	 * @param request
	 * @return
	 * @throws ModelAndViewDefiningException 
	 * @throws IOException 
	 */
	@RequestMapping("/ea/person/personApprovalInsert.do")
	public void approvalInsert(HttpServletRequest request, HttpServletResponse response) throws ModelAndViewDefiningException, IOException{
		
		Map<String, String> paramMap = new HashMap<String, String>();
		
		HttpSession session = request.getSession();
		MemberVO memberSessionVO = (MemberVO) session.getAttribute("gwUser");
		
		String create_no = memberSessionVO.getEmp_no();
		String docu_seq = StringUtil.nvl((String)request.getParameter("docu_seq"));
		String approval_seq = StringUtil.nvl((String)request.getParameter("approval_seq"));
		String subject = StringUtil.nvl((String)request.getParameter("subject"));
		String approval = StringUtil.nvl((String)request.getParameter("approval"));
		String implDept = StringUtil.nvl((String)request.getParameter("implDept"));
		
		//개인라인
		PersonLineVO personLineVO = new PersonLineVO();
		personLineVO.setEmp_no(create_no);
		personLineVO.setSubject(subject);
		personLineVO.setCreate_no(create_no);
		
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

		List<PersonApprovalVO> personApprovalVOList = new ArrayList<PersonApprovalVO>();
		List<PersonImplDeptVO> personImplDeptVOList = new ArrayList<PersonImplDeptVO>();
		
		logger.debug(">>>>>>>>>>>>>>>>>>>>>개인 결재");
		//결재
		PersonApprovalVO personApprovalVO = new PersonApprovalVO();
		if(approvalArr.length > 0){
			String approval_emp_no = "";
			String approval_ordering = "";
			for (int i = 0; i < approvalArr.length; i++) {
				String approvalArrValue[] = approvalArr[i].split("\\|");
				
				approval_emp_no = approvalArrValue[0];
				approval_ordering = approvalArrValue[1];
				
				PersonApprovalVO personApproval = new PersonApprovalVO();
				personApproval.setEmp_no(approval_emp_no);
				personApproval.setOrdering(Integer.parseInt(approval_ordering));
				personApproval.setCreate_no(create_no);
				personApprovalVOList.add(personApproval);
			}
			personApprovalVO.setPersonApprovalVO(personApprovalVOList);
		}
		
		logger.debug(">>>>>>>>>>>>>>>>>>>>>개인 시행");
		//협조
		PersonImplDeptVO personImplDeptVO = new PersonImplDeptVO();
		if(implDeptArr.length > 0){
			String implDept_dept_cd = "";
			String implDept_ordering = "";
			for (int i = 0; i < implDeptArr.length; i++) {
				String implDeptArrValue[] = implDeptArr[i].split("\\|");
				implDept_dept_cd = implDeptArrValue[0];
				implDept_ordering = implDeptArrValue[1];
				
				PersonImplDeptVO personImplDept = new PersonImplDeptVO();
				personImplDept.setDept_cd(implDept_dept_cd);
				personImplDept.setOrdering(Integer.parseInt(implDept_ordering));
				personImplDept.setCreate_no(create_no);
				personImplDeptVOList.add(personImplDept);
			}
			
			personImplDeptVO.setPersonImplDeptVO(personImplDeptVOList);
		}
		
		int person_seq = personApprovalService.insertPersonApprovalAll(paramMap, personLineVO, personApprovalVO, personImplDeptVO);
		
		if(!approval_seq.equals("") && approval_seq != null){
			paramMap.put("docu_seq", docu_seq);
			paramMap.put("approval_seq", approval_seq);
			paramMap.put("person_seq", String.valueOf(person_seq));
			paramMap.put("approval_gbn", "PERSON");
			paramMap.put("returnURL", env.getValue("root_dir.url")+"/ea/newReport/step2approvalPopup.do");
		}else{
			paramMap.put("person_seq", String.valueOf(person_seq));
			paramMap.put("returnURL", env.getValue("root_dir.url")+"/ea/person/personApproval.do");
		}
		
		if(person_seq != 0 ){
			MarshallerUtil.marshalling("txt", response, CommonUtil.postMessageView("결재라인이 저장 되었습니다.", paramMap));
		}else{
			MarshallerUtil.marshalling("txt", response, CommonUtil.postMessageView("요청 처리 과정에서 에러가 발생하였습니다. 잠시 후 다시 시도하시거나 전산팀에 문의 바랍니다.", paramMap));
		}
	}
	
	/**
	 * 
	 * <pre>
	 * 1. 개요     : 개인 결재라인 삭제
	 * 2. 처리내용 : 개인 결재라인 삭제한다.
	 * </pre>
	 * @Method Name : approvalDelete
	 * @param request
	 * @return
	 * @throws ModelAndViewDefiningException 
	 * @throws IOException 
	 */
	@RequestMapping("/ea/person/approvalDelete.do")
	public void approvalDelete(HttpServletRequest request, HttpServletResponse response) throws ModelAndViewDefiningException, IOException{
		
		Map<String, String> paramMap = new HashMap<String, String>();
		
		String person_seq = StringUtil.nvl((String)request.getParameter("person_seq"));
		
		//라인, 결재, 시행 전체 삭제
		paramMap.put("person_seq", person_seq);
		int iResult = personApprovalService.deletePersonApprovalAll(paramMap);

		paramMap.put("returnURL", env.getValue("root_dir.url")+"/ea/person/personApproval.do");
		
		if(iResult != 0 ){
			MarshallerUtil.marshalling("txt", response, CommonUtil.postMessageView("결재라인이 삭제 되었습니다.", paramMap));
		}else{
			MarshallerUtil.marshalling("txt", response, CommonUtil.postMessageView("요청 처리 과정에서 에러가 발생하였습니다. 잠시 후 다시 시도하시거나 전산팀에 문의 바랍니다.", paramMap));
		}
	}
}
