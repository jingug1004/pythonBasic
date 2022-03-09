/**
  * Hana Project
  * Copyright 2014 iRush Co.,
  *
  */
package com.hanaph.gw.ea.mgrDocuInfo.controller;

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
import com.hanaph.gw.ea.mgrDocuInfo.service.BasicApprovalService;
import com.hanaph.gw.ea.mgrDocuInfo.service.BasicImplDeptService;
import com.hanaph.gw.ea.mgrDocuInfo.service.DocumentInfoService;
import com.hanaph.gw.ea.mgrDocuInfo.vo.BasicApprovalVO;
import com.hanaph.gw.ea.mgrDocuInfo.vo.BasicImplDeptVO;
import com.hanaph.gw.ea.mgrDocuInfo.vo.DocumentInfoVO;
import com.hanaph.gw.pe.member.service.MemberService;
import com.hanaph.gw.pe.member.vo.MemberVO;

/**
 * <pre>
 * Class Name : BasicApprovalController.java
 * 설명 : 문서별 기본결재라인 Controller 
 * 
 * Modification Information
 *    수정일      수정자              수정 내용
 *  ------------ -------------- --------------------------------
 *  2014. 12. 18.      CHOIILJI         
 * </pre>
 * 
 * @version : 1.0
 * @author  : CHOIILJI(choiilji@irush.co.kr)
 * @since   : 2014. 12. 18.
 */
@Controller
public class BasicApprovalController {
	
	private Environment env = new Environment();
	
	@Autowired
	private DepartmentService departmentService;
	
	@Autowired
	private MemberService memberService;
	
	@Autowired
	private MenuService menuService;
	
	@Autowired
	private DocumentInfoService documentInfoService;

	@Autowired
	private BasicApprovalService basicApprovalService;
	
	@Autowired
	private BasicImplDeptService basicImplDeptService;
	
	private static final Logger logger = Logger.getLogger(BasicApprovalController.class);
	/**
	 * 
	 * <pre>
	 * 1. 개요     : 기본결재라인
	 * 2. 처리내용 : 기본결재라인 가져온다.
	 * </pre>
	 * @Method Name : getBasicApprovalPopup
	 * @param request
	 * @return
	 */
	@RequestMapping("/ea/mgrDocuInfo/basicApproval.do")
	public ModelAndView getBasicApprovalPopup(HttpServletRequest request){
		
		final String MENU_CHILD= "020502";
		
		ModelAndView mav = new ModelAndView("ea/mgrDocuInfo/basicApproval");
		Map<String, String> paramMap = new HashMap<String, String>();  
		
		String docu_seq = StringUtil.nvl((String)request.getParameter("docu_seq"));		
		String search_dept_cd = StringUtil.nvl((String)request.getParameter("search_dept_cd"));
		String search_docu_nm = StringUtil.nvl((String)request.getParameter("search_docu_nm"));
		
		//부서 리스트		
		List<DepartmentVO> departmentList = departmentService.getDepartmentList(paramMap);
				
		//양식정보 리스트
		paramMap.put("docu_seq", docu_seq);
		DocumentInfoVO documentInfoDetail = documentInfoService.getDocumentInfoDetail(paramMap);
			
		//LNB 메뉴 생성
		String menu_parents = StringUtil.substring(MENU_CHILD, 2);
		MenuUtil mu = new MenuUtil();
		List<MenuVO> lnbMenuList = mu.makeLnb(request, menu_parents , menuService);
		mav.addObject("lnbMenuList", lnbMenuList);
		mav.addObject("MENU_CHILD", MENU_CHILD);
				
		mav.addObject("departmentList", departmentList);
		mav.addObject("documentInfoDetail", documentInfoDetail);
		mav.addObject("search_dept_cd", search_dept_cd);
		mav.addObject("search_docu_nm", search_docu_nm);
		
		return mav;
	}
	
	/**
	 * 
	 * <pre>
	 * 1. 개요     : 기본결재라인
	 * 2. 처리내용 : 기본결재라인 회원정보 가져온다.
	 * </pre>
	 * @Method Name : getBasicApprovalMember
	 * @param request
	 * @return
	 */
	@RequestMapping("/ea/mgrDocuInfo/basicApprovalMemberIframe.do")
	public ModelAndView getBasicApprovalMember(HttpServletRequest request){
		
		ModelAndView mav = new ModelAndView("ea/mgrDocuInfo/basicApprovalMemberIframe");
		Map<String, String> paramMap = new HashMap<String, String>();
		
		String dept_cd = StringUtil.nvl(request.getParameter("dept_cd"), "0000");
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
	 * 1. 개요     : 기본결재라인
	 * 2. 처리내용 : 기본결재라인 상세 목록 가져온다.
	 * </pre>
	 * @Method Name : getBasicApprovalMemberTarget
	 * @param request
	 * @return
	 */
	@RequestMapping("/ea/mgrDocuInfo/basicApprovalMemberTargetIframe.do")
	public ModelAndView getBasicApprovalMemberTarget(HttpServletRequest request){
		
		ModelAndView mav = new ModelAndView("ea/mgrDocuInfo/basicApprovalMemberTargetIframe");
		Map<String, String> paramMap = new HashMap<String, String>();
		String docu_seq = StringUtil.nvl((String)request.getParameter("docu_seq"));

		paramMap.put("docu_seq", docu_seq);
		//결재
		List<BasicApprovalVO> basicApprovalDetailList = basicApprovalService.getBasicApprovalDetailList(paramMap);

		mav.addObject("basicApprovalDetailList", basicApprovalDetailList);
		mav.addObject("docu_seq", docu_seq);
		
        return mav;
	}
	
	/**
	 * 
	 * <pre>
	 * 1. 개요     : 기본결재라인
	 * 2. 처리내용 : 기본결재라인 시행 부서 목록 가져온다.
	 * </pre>
	 * @Method Name : getBasicApprovalDept
	 * @param request
	 * @return
	 */
	@RequestMapping("/ea/mgrDocuInfo/basicApprovalDeptIframe.do")
	public ModelAndView getBasicApprovalDept(HttpServletRequest request){

		ModelAndView mav = new ModelAndView("ea/mgrDocuInfo/basicApprovalDeptIframe");
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
	 * 1. 개요     : 기본결재라인
	 * 2. 처리내용 : 기본시행라인 상세 목록 가져온다.
	 * </pre>
	 * @Method Name : getBasicApprovalDeptTarget
	 * @param request
	 * @return
	 */
	@RequestMapping("/ea/mgrDocuInfo/basicApprovalDeptTargetIframe.do")
	public ModelAndView getBasicApprovalDeptTarget(HttpServletRequest request){
		
		ModelAndView mav = new ModelAndView("ea/mgrDocuInfo/basicApprovalDeptTargetIframe");
		Map<String, String> paramMap = new HashMap<String, String>();
		String docu_seq = StringUtil.nvl((String)request.getParameter("docu_seq"));
		paramMap.put("docu_seq", docu_seq);
		
		//시행
		List<BasicImplDeptVO> basicImplDeptDetailList = basicImplDeptService.getBasicImplDeptDetailList(paramMap);

		mav.addObject("basicImplDeptDetailList", basicImplDeptDetailList);
		mav.addObject("docu_seq", docu_seq);
		
        return mav;
	}
	
	
	/**
	 * 
	 * <pre>
	 * 1. 개요     : 기본결재 라인 저장
	 * 2. 처리내용 : 기본결재 라인 저장한다.
	 * </pre>
	 * @Method Name : basicApprovalInsert
	 * @param request
	 * @return
	 * @throws ModelAndViewDefiningException 
	 * @throws IOException 
	 */
	@RequestMapping("/ea/mgrDocuInfo/basicApprovalInsert.do")
	public void basicApprovalInsert(HttpServletRequest request, HttpServletResponse response) throws ModelAndViewDefiningException, IOException{
		
		Map<String, String> paramMap = new HashMap<String, String>();
		
		HttpSession session = request.getSession();
		MemberVO memberSessionVO = (MemberVO) session.getAttribute("gwUser");
		String search_dept_cd = StringUtil.nvl((String)request.getParameter("search_dept_cd"));
		String search_docu_nm = StringUtil.nvl((String)request.getParameter("search_docu_nm"));
		
		String docu_seq = StringUtil.nvl((String)request.getParameter("docu_seq"));
		String create_no = memberSessionVO.getEmp_no();
		String approval = StringUtil.nvl((String)request.getParameter("approval"));
		String implDept = StringUtil.nvl((String)request.getParameter("implDept"));
		
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
		
		paramMap.put("docu_seq", docu_seq);
		
		logger.debug(">>>>>>>>>>>>>>>>>>>>>기본 결재");
		List<BasicApprovalVO> basicApprovalVOList = new ArrayList<BasicApprovalVO>();
		BasicApprovalVO basicApprovalVO = new BasicApprovalVO();
		if(approvalArr.length > 0){
			String approval_emp_no = "";
			String approval_ordering = "";
			for (int i = 0; i < approvalArr.length; i++) {
				String approvalArrValue[] = approvalArr[i].split("\\|");
				
				approval_emp_no = approvalArrValue[0];
				approval_ordering = approvalArrValue[1];
				
				BasicApprovalVO basicApproval = new BasicApprovalVO();
				basicApproval.setDocu_seq(Integer.parseInt(docu_seq));
				basicApproval.setEmp_no(approval_emp_no);
				basicApproval.setOrdering(Integer.parseInt(approval_ordering));
				basicApproval.setCreate_no(create_no);
				basicApprovalVOList.add(basicApproval);
			}
			
			basicApprovalVO.setBasicApprovalVO(basicApprovalVOList);
			
		}
		logger.debug(">>>>>>>>>>>>>>>>>>>>>기본 시행");
		List<BasicImplDeptVO> basicImplDeptVOList = new ArrayList<BasicImplDeptVO>();
		BasicImplDeptVO basicImplDeptVO = new BasicImplDeptVO();
		if(implDeptArr.length > 0){
			String implDept_dept_cd = "";
			String implDept_ordering = "";
			for (int i = 0; i < implDeptArr.length; i++) {
				String implDeptArrValue[] = implDeptArr[i].split("\\|");
				
				implDept_dept_cd = implDeptArrValue[0];
				implDept_ordering = implDeptArrValue[1];
				
				BasicImplDeptVO basicImplDept = new BasicImplDeptVO();
				basicImplDept.setDocu_seq(Integer.parseInt(docu_seq));
				basicImplDept.setDept_cd(implDept_dept_cd);
				basicImplDept.setOrdering(Integer.parseInt(implDept_ordering));
				basicImplDept.setCreate_no(create_no);
				basicImplDeptVOList.add(basicImplDept);
			}
			
			basicImplDeptVO.setBasicImplDeptVO(basicImplDeptVOList);
		}
		
		int iResult = basicApprovalService.insertBasicApprovalAll(paramMap, basicApprovalVO, basicImplDeptVO);
		
		paramMap.put("search_dept_cd", search_dept_cd);
		paramMap.put("search_docu_nm", search_docu_nm);
		paramMap.put("returnURL", env.getValue("root_dir.url")+"/ea/mgrDocuInfo/basicApproval.do");
		
		if(iResult > 0){
			MarshallerUtil.marshalling("txt", response, CommonUtil.postMessageView("결재라인이 저장 되었습니다.", paramMap));
		}else{
			MarshallerUtil.marshalling("txt", response, CommonUtil.postMessageView("요청 처리 과정에서 에러가 발생하였습니다. 잠시 후 다시 시도하시거나 전산팀에 문의 바랍니다.", paramMap));
		}
	}
}
