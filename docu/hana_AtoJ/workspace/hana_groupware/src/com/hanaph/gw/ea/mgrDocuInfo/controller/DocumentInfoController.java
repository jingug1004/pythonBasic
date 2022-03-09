/**
  * Hana Project
  * Copyright 2014 iRush Co.,
  *
  */
package com.hanaph.gw.ea.mgrDocuInfo.controller;

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
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.ModelAndViewDefiningException;

import com.hanaph.gw.co.common.utils.CommonUtil;
import com.hanaph.gw.co.common.utils.Environment;
import com.hanaph.gw.co.common.utils.MarshallerUtil;
import com.hanaph.gw.co.common.utils.MenuUtil;
import com.hanaph.gw.co.common.utils.RequestFilterUtil;
import com.hanaph.gw.co.common.utils.StringUtil;
import com.hanaph.gw.co.menu.service.MenuService;
import com.hanaph.gw.co.menu.vo.MenuVO;
import com.hanaph.gw.co.personnel.service.DepartmentService;
import com.hanaph.gw.co.personnel.vo.DepartmentVO;
import com.hanaph.gw.ea.mgrDocuInfo.service.DocumentInfoService;
import com.hanaph.gw.ea.mgrDocuInfo.vo.DocumentInfoVO;
import com.hanaph.gw.pe.member.vo.MemberVO;

/**
 * <pre>
 * Class Name : DocumentInfoController.java
 * 설명 : 양식정보관리 Controller
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
public class DocumentInfoController {
	
	private Environment env = new Environment();
	
	@Autowired
	private MenuService menuService;
	
	@Autowired
	private DocumentInfoService documentInfoService;
	
	@Autowired
	private DepartmentService departmentService;
	
	/**
	 * 
	 * <pre>
	 * 1. 개요     : 양식정보 리스트
	 * 2. 처리내용 : 양식정보 리스트 가져온다.
	 * </pre>
	 * @Method Name : getDocumentInfoList
	 * @param request
	 * @return
	 */
	@RequestMapping("/ea/mgrDocuInfo/docuInfoList.do")
	public ModelAndView getDocumentInfoList(HttpServletRequest request){
		
		final String MENU_CHILD= "020502";
		
		ModelAndView mav = new ModelAndView("ea/mgrDocuInfo/docuInfoList");
		Map<String, String> paramMap = new HashMap<String, String>();  
		
		String search_dept_cd = StringUtil.nvl((String)request.getParameter("search_dept_cd"), "0000");
		String search_docu_nm = StringUtil.nvl((String)request.getParameter("search_docu_nm"));
		
		paramMap.put("search_dept_cd", search_dept_cd);
		paramMap.put("search_docu_nm", search_docu_nm);
		
		//양식정보 리스트
		List<DocumentInfoVO> documentInfoList = documentInfoService.getDocumentInfoList(paramMap);
		//전체 카운트
		int documentInfoTotalCount = documentInfoService.getDocumentInfoCount(paramMap);
		//부서정보 리스트
		paramMap.put("up_dept_cd", "0000");
		List<DepartmentVO> deptList = departmentService.getDepartmentTerminalList(paramMap);
		
		//LNB 메뉴 생성
		String menu_parents = StringUtil.substring(MENU_CHILD, 2);
		MenuUtil mu = new MenuUtil();
		List<MenuVO> lnbMenuList = mu.makeLnb(request, menu_parents , menuService);
		mav.addObject("lnbMenuList", lnbMenuList);
		mav.addObject("MENU_CHILD", MENU_CHILD);
				
		mav.addObject("documentInfoList", documentInfoList);
		mav.addObject("deptList", deptList);
		mav.addObject("search_dept_cd", search_dept_cd);
		mav.addObject("search_docu_nm", search_docu_nm);
		mav.addObject("documentInfoTotalCount", documentInfoTotalCount);
				
		return mav;
	}
	
	
	/**
	 * 
	 * <pre>
	 * 1. 개요     : 양식정보 상세
	 * 2. 처리내용 : 양식정보 상세 가져온다.
	 * </pre>
	 * @Method Name : getDocumentInfoUpdateForm
	 * @param request
	 * @return
	 */
	@RequestMapping("/ea/mgrDocuInfo/docuInfoUpdateForm.do")
	public ModelAndView getDocumentInfoUpdateForm(HttpServletRequest request){
		
		final String MENU_CHILD= "020502";
		
		ModelAndView mav = new ModelAndView("ea/mgrDocuInfo/docuInfoUpdateForm");
		Map<String, String> paramMap = new HashMap<String, String>();  
		
		String docu_seq = StringUtil.nvl((String)request.getParameter("docu_seq"));
		String search_dept_cd = StringUtil.nvl((String)request.getParameter("search_dept_cd"));
		String search_docu_nm = StringUtil.nvl((String)request.getParameter("search_docu_nm"));
		
		paramMap.put("docu_seq", docu_seq);
		//양식정보 리스트
		DocumentInfoVO documentInfoDetail = documentInfoService.getDocumentInfoDetail(paramMap);
		
		//LNB 메뉴 생성
		String menu_parents = StringUtil.substring(MENU_CHILD, 2);
		MenuUtil mu = new MenuUtil();
		List<MenuVO> lnbMenuList = mu.makeLnb(request, menu_parents , menuService);
		mav.addObject("lnbMenuList", lnbMenuList);
		mav.addObject("MENU_CHILD", MENU_CHILD);
				
		mav.addObject("documentInfoDetail", documentInfoDetail);
		mav.addObject("search_dept_cd", search_dept_cd);
		mav.addObject("search_docu_nm", search_docu_nm);
				
		return mav;
	}
	
	/**
	 * 
	 * <pre>
	 * 1. 개요     : 양식정보 수정
	 * 2. 처리내용 : 양식정보 수정한다.
	 * </pre>
	 * @Method Name : getDocumentInfoUpdate
	 * @param request
	 * @return
	 * @throws ModelAndViewDefiningException 
	 * @throws IOException 
	 */
	@RequestMapping("/ea/mgrDocuInfo/docuInfoUpdate.do")
	public void getDocumentInfoUpdate(HttpServletRequest request, HttpServletResponse response) throws ModelAndViewDefiningException, IOException{
		
		Map<String, String> paramMap = new HashMap<String, String>(); 

		HttpSession session = request.getSession();
		MemberVO memberSessionVO = (MemberVO) session.getAttribute("gwUser");
		
		String search_dept_cd = StringUtil.nvl((String)request.getParameter("search_dept_cd"));
		String search_docu_nm = StringUtil.nvl((String)request.getParameter("search_docu_nm"));
		
		String decide_yn = StringUtil.nvl((String)request.getParameter("decide_yn"), "N");
		String security_yn = StringUtil.nvl((String)request.getParameter("security_yn"), "N");
		String contents = StringUtil.nvl((String)request.getParameter("contents"));
		contents = RequestFilterUtil.convertToSearchParameter(contents);
		String use_yn = StringUtil.nvl((String)request.getParameter("use_yn"));
		String docu_seq = StringUtil.nvl((String)request.getParameter("docu_seq"));
		String modify_no = memberSessionVO.getEmp_no();
		
		DocumentInfoVO documentInfoVO = new DocumentInfoVO();
		documentInfoVO.setDecide_yn(decide_yn);
		documentInfoVO.setSecurity_yn(security_yn);
		documentInfoVO.setContents(contents);
		documentInfoVO.setUse_yn(use_yn);
		documentInfoVO.setModify_no(modify_no);		
		documentInfoVO.setDocu_seq(Integer.parseInt(docu_seq));
		
		//양식정보 업데이트
		int iResult = documentInfoService.updateDocumentInfo(documentInfoVO);

		paramMap.put("docu_seq", docu_seq);
		paramMap.put("search_dept_cd", search_dept_cd);
		paramMap.put("search_docu_nm", search_docu_nm);
		paramMap.put("returnURL", env.getValue("root_dir.url")+"/ea/mgrDocuInfo/docuInfoUpdateForm.do");
		
		if(iResult > 0){
			MarshallerUtil.marshalling("txt", response, CommonUtil.postMessageView("수정되었습니다.", paramMap));
		}else{
			MarshallerUtil.marshalling("txt", response, CommonUtil.postMessageView("요청 처리 과정에서 에러가 발생하였습니다. 잠시 후 다시 시도하시거나 전산팀에 문의 바랍니다.", paramMap));
		}
	}
}

