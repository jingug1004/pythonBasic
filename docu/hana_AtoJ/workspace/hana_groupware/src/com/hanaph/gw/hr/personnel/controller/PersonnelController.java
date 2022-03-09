/**
  * Hana Project
  * Copyright 2014 iRush Co.,
  *
  */
package com.hanaph.gw.hr.personnel.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.hanaph.gw.co.common.utils.MenuUtil;
import com.hanaph.gw.co.common.utils.StringUtil;
import com.hanaph.gw.co.menu.service.MenuService;
import com.hanaph.gw.co.menu.vo.MenuVO;
import com.hanaph.gw.co.personnel.service.DepartmentService;
import com.hanaph.gw.co.personnel.vo.DepartmentVO;
import com.hanaph.gw.hr.personnel.service.PersonnelService;
import com.hanaph.gw.hr.personnel.vo.PersonnelVO;
import com.hanaph.gw.pe.member.service.MemberService;
import com.hanaph.gw.pe.member.vo.MemberVO;

/**
 * <pre>
 * Class Name : PersonnelController.java
 * 설명 : 인사현황 정보 Controller
 * 
 * Modification Information
 *    수정일      수정자              수정 내용
 *  ------------ -------------- --------------------------------
 *  2014. 10. 23.      CHOIILJI         
 * </pre>
 * 
 * @version : 1.0
 * @author  : CHOIILJI(choiilji@irush.co.kr)
 * @since   : 2014. 10. 23.
 */
@Controller
public class PersonnelController {
	
	@Autowired
	private PersonnelService personnelService;
	
	@Autowired
	private DepartmentService departmentService;
	
	@Autowired
	private MemberService  memberService;
	
	@Autowired
	private MenuService menuService;
	/**
	 * 
	 * <pre>
	 * 1. 개요     : 조직 리스트
	 * 2. 처리내용 : 조직 리스트 가져온다.
	 * </pre>
	 * @Method Name : departmentList
	 * @param request
	 * @return
	 */
	@RequestMapping("/hr/personnel/personnelList.do")
	public ModelAndView personnelList(HttpServletRequest request){
		
		final String MENU_CHILD= "0301";
		
		ModelAndView mav = new ModelAndView("hr/personnel/personnelList");
		Map<String, String> paramMap = new HashMap<String, String>();  
		
		List<DepartmentVO> departmentList = departmentService.getDepartmentList(paramMap);
		
		//LNB 메뉴 생성
		String menu_parents = StringUtil.substring(MENU_CHILD, 2);
		MenuUtil mu = new MenuUtil();
		List<MenuVO> lnbMenuList = mu.makeLnb(request, menu_parents , menuService);
		mav.addObject("lnbMenuList", lnbMenuList);
		mav.addObject("MENU_CHILD", MENU_CHILD);
		//LNB 메뉴 생성 END
				
		mav.addObject("departmentList", departmentList);
		
        return mav;
	}
	
	/**
	 * 
	 * <pre>
	 * 1. 개요     : 인사현황, 발령현황, 소속사용자 목록
	 * 2. 처리내용 : 인사현황, 발령현황, 소속사용자 목록
	 * </pre>
	 * @Method Name : personnelDetailIframe
	 * @param request
	 * @return
	 */
	@RequestMapping("/hr/personnel/personnelDetailIframe.do")
	public ModelAndView personnelDetailIframe(HttpServletRequest request){
		
		ModelAndView mav = new ModelAndView("hr/personnel/personnelDetailIframe");
		Map<String, String> paramMap = new HashMap<String, String>();  
		
		String dept_cd = StringUtil.nvl(request.getParameter("dept_cd"), "00000");
		String up_dept_gbn = StringUtil.nvl(request.getParameter("up_dept_gbn"));
		String emp_ko_nm = StringUtil.nvl(request.getParameter("emp_ko_nm"));
		//인사현황
		//하위 조직이 없는 조직은 넘어온 dept_cd 로만 사용자를 검색 하고 하위 조직이 있는 조직은 하위의 조직까지 검색 한다. 
		if(emp_ko_nm.equals("") || emp_ko_nm == null){
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
		List<PersonnelVO> personnelCountList = personnelService.getPersonnelCountList(paramMap);
		//발령현황
		List<PersonnelVO> appointmentCountList = personnelService.getAppointmentCountList(paramMap);
		//소속사용자
		List<MemberVO> memberList = memberService.getMemberList(paramMap);
		mav.addObject("personnelCountList", personnelCountList);
		mav.addObject("appointmentCountList", appointmentCountList);
		mav.addObject("memberList", memberList);
		mav.addObject("emp_ko_nm", emp_ko_nm);
		
        return mav;
	}
}
