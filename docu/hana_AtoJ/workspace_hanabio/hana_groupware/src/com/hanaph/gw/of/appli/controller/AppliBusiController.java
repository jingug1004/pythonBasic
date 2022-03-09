/**
  * Hana Project
  * Copyright 2016 CHOE
  *
  */
package com.hanaph.gw.of.appli.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialException;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.ModelAndViewDefiningException;

import com.hanaph.gw.co.authority.service.AuthorityService;
import com.hanaph.gw.co.common.utils.CommonUtil;
import com.hanaph.gw.co.common.utils.Environment;
import com.hanaph.gw.co.common.utils.MenuUtil;
import com.hanaph.gw.co.common.utils.RequestFilterUtil;
import com.hanaph.gw.co.common.utils.StringUtil;
import com.hanaph.gw.co.menu.service.MenuService;
import com.hanaph.gw.co.menu.vo.MenuVO;
import com.hanaph.gw.co.personnel.service.DepartmentService;
import com.hanaph.gw.co.personnel.vo.DepartmentVO;
import com.hanaph.gw.of.appli.service.AppliBusiService;
import com.hanaph.gw.of.appli.vo.AppliBusiVO;
import com.hanaph.gw.of.message.controller.MessageController;
import com.hanaph.gw.of.message.vo.MessageVO;
import com.hanaph.gw.pe.member.vo.MemberVO;


/**
 * <pre>
 * Class Name : AppliBusiController.java
 * 설명 : 그룹웨어에서 명함 신청서를 받기 위해 양식 화면
 * 
 * Modification Information
 *    수정일      수정자              수정 내용
 *  ------------ -------------- --------------------------------
 *  2016. 11. 07.      CHOE      
 * </pre>
 * 
 * @version : 
 * @author  : CHOE
 * @since   : 2016. 11. 07.
 */
@Controller
public class AppliBusiController {

	@Autowired
	private AppliBusiService  applibusiService;
	
	@Autowired
	private MenuService menuService;
	
	@Autowired
	private DepartmentService departmentService;
	
	@Autowired
	private AuthorityService authorityService;
	
	private static final Logger logger = Logger.getLogger(MessageController.class);
	private Environment env = new Environment();
	
	/**
	 * 
	 * <pre>
	 * 1. 개요     : 명함신청서
	 * 2. 처리내용 : 
	 * </pre>
	 * @Method Name : insert
	 * @param request
	 * @return
	 */
	@RequestMapping("/of/appli/applibusi.do")
	public ModelAndView newAppliBuis(HttpServletRequest request){
		
		ModelAndView mav = new ModelAndView("of/appli/appliBusi");
		Map<String, String> paramMap = new HashMap<String, String>(); 
		//System.out.println("--- AppliBusiController newAppliBuis start");
		
		/******* LNB start *******/
		final String MENU_CHILD= "0104";
		//LNB 메뉴 생성
		String menu_parents = StringUtil.substring(MENU_CHILD, 2);
		MenuUtil mu = new MenuUtil();
		List<MenuVO> lnbMenuList = mu.makeLnb(request, menu_parents , menuService);
		mav.addObject("lnbMenuList", lnbMenuList);
		mav.addObject("MENU_CHILD", MENU_CHILD);
		/******* LNB end *******/
		
		//String emp_no = StringUtil.nvl(request.getParameter("emp_no"));
		
		//paramMap.put("emp_no", emp_no);		
		/* CHOE 20161107 해당 화면은 자료를 찾아오는 기능이 없다
		AppliBusiVO applibusiDetail = applibusiService.getapplibusiDetail(paramMap);
		if(applibusiDetail == null){
			applibusiDetail = new AppliBusiVO();
		}
		mav.addObject("applibusiDetail", applibusiDetail);*/
        return mav;
	}
	
	/**
	 * <pre>
	 * 1. 개요     :	명함 신청 
	 * 2. 처리내용 :	db에  데이터를 저장 한다.
	 * </pre>
	 * @Method Name : InsertAppliBusi
	 * @param request
	 * @return
	 */
	@RequestMapping("/of/appli/insertAppliBusi.do")
	public ModelAndView insertAppliBusi(HttpServletRequest request, HttpServletResponse response )
			throws IOException, ModelAndViewDefiningException{
		//System.out.println("--- AppliBusiController insertAppliBusi start");  //정상값 나옴
		ModelAndView mav = new ModelAndView("");
		
		HttpSession session = request.getSession();		
		//AppliBusiVO applibusiSessionVO = (AppliBusiVO) session.getAttribute("gwUser");
		MemberVO memberSessionVO = (MemberVO) session.getAttribute("gwUser");
				
		//String ymd = StringUtil.nvl(request.getParameter("ymd"); //등록일
		//String emp_no = StringUtil.nvl(request.getParameter("emp_no")); //등록자
		String busi_emp_no = StringUtil.nvl(request.getParameter("busi_emp_no")); //신청자 사번
		String busi_emp_nm = StringUtil.nvl(request.getParameter("busi_emp_nm")); //신청자 이름
		String busi_dept_nm = StringUtil.nvl(request.getParameter("busi_dept_nm")); //신청자 부서
		String busi_grade_nm = StringUtil.nvl(request.getParameter("busi_grade_nm")); //신청자 직급
		//System.out.println("--- AppliBusiController insertAppliBusi busi_emp_no" + busi_emp_no);
		//System.out.println("--- AppliBusiController insertAppliBusi busi_emp_nm" + busi_emp_nm);
		//System.out.println("--- AppliBusiController insertAppliBusi busi_dept_nm" + busi_dept_nm);
		//System.out.println("--- AppliBusiController insertAppliBusi busi_grade_nm" + busi_grade_nm);		
		
		String addr1 = StringUtil.nvl(request.getParameter("addr1")); //주소1
		String tel1 = StringUtil.nvl(request.getParameter("tel1")); //전화1
		String fax1 = StringUtil.nvl(request.getParameter("fax1")); //팩스1
		String mobile = StringUtil.nvl(request.getParameter("mobile")); //휴대전화
		String email = StringUtil.nvl(request.getParameter("email")); //이메일
		
		String addr2 = StringUtil.nvl(request.getParameter("addr2")); //주소2
		String tel2 = StringUtil.nvl(request.getParameter("tel2")); //전화2
		String fax2 = StringUtil.nvl(request.getParameter("fax2")); //팩스2
		
		String addr3 = StringUtil.nvl(request.getParameter("addr3")); //주소3
		String tel3 = StringUtil.nvl(request.getParameter("tel3")); //전화3
		String fax3 = StringUtil.nvl(request.getParameter("fax3")); //팩스3
		
		String busi_emp_nm_en = StringUtil.nvl(request.getParameter("busi_emp_nm_en")); //신청자 이름
		String busi_dept_nm_en = StringUtil.nvl(request.getParameter("busi_dept_nm_en")); //신청자 부서
		String busi_grade_nm_en = StringUtil.nvl(request.getParameter("busi_grade_nm_en")); //신청자 직급
		
		String addr1_en = StringUtil.nvl(request.getParameter("addr1_en")); //주소1
		String tel1_en = StringUtil.nvl(request.getParameter("tel1_en")); //전화1
		String fax1_en = StringUtil.nvl(request.getParameter("fax1_en")); //팩스1
		String mobile_en = StringUtil.nvl(request.getParameter("mobile_en")); //휴대전화
		String email_en = StringUtil.nvl(request.getParameter("email_en")); //이메일
		
		String addr2_en = StringUtil.nvl(request.getParameter("addr2_en")); //주소2
		String tel2_en = StringUtil.nvl(request.getParameter("tel2_en")); //전화2
		String fax2_en = StringUtil.nvl(request.getParameter("fax2_en")); //팩스2
		
		String addr3_en = StringUtil.nvl(request.getParameter("addr3_en")); //주소3
		String tel3_en = StringUtil.nvl(request.getParameter("tel3_en")); //전화3
		String fax3_en = StringUtil.nvl(request.getParameter("fax3_en")); //팩스3
				
		AppliBusiVO applibusiVO = new AppliBusiVO();
		applibusiVO.setEmp_no(memberSessionVO.getEmp_no());
		//System.out.println("--- AppliBusiController insertAppliBusi memberSessionVO.getEmp_no()" + memberSessionVO.getEmp_no());
		applibusiVO.setBusi_emp_no(busi_emp_no);
		applibusiVO.setBusi_emp_nm(busi_emp_nm);
		applibusiVO.setBusi_dept_nm(busi_dept_nm);
		applibusiVO.setBusi_grade_nm(busi_grade_nm);
		
		applibusiVO.setAddr1(addr1);
		applibusiVO.setTel1(tel1);
		applibusiVO.setFax1(fax1);
		applibusiVO.setMobile(mobile);
		applibusiVO.setEmail(email);
		
		applibusiVO.setAddr2(addr2);
		applibusiVO.setTel2(tel2);
		applibusiVO.setFax2(fax2);
		
		applibusiVO.setAddr3(addr3);
		applibusiVO.setTel3(tel3);
		applibusiVO.setFax3(fax3);
				
		applibusiVO.setBusi_emp_nm_en(busi_emp_nm_en);
		applibusiVO.setBusi_dept_nm_en(busi_dept_nm_en);
		applibusiVO.setBusi_grade_nm_en(busi_grade_nm_en);
		
		applibusiVO.setAddr1_en(addr1_en);
		applibusiVO.setTel1_en(tel1_en);
		applibusiVO.setFax1_en(fax1_en);
		applibusiVO.setMobile_en(mobile_en);
		applibusiVO.setEmail_en(email_en);
		
		applibusiVO.setAddr2_en(addr2_en);
		applibusiVO.setTel2_en(tel2_en);
		applibusiVO.setFax2_en(fax2_en);
		
		applibusiVO.setAddr3_en(addr3_en);
		applibusiVO.setTel3_en(tel3_en);
		applibusiVO.setFax3_en(fax3_en);		
		
		String seq = "";		
		String url = "window.location.href='"+env.getValue("root_dir.url")+"/of/appli/applibusi.do';";
		
		seq = applibusiService.insertAppliBusi(applibusiVO);
		
		if(!"".equals(seq)){			
			
			logger.debug("*********** 명한 신청 성공 ***********");
			mav = CommonUtil.getMessageView("명함 신청 되었습니다.", url , "" );
			throw new ModelAndViewDefiningException(mav);
		}else{
			logger.debug("*********** 명한 신청 실패!! ***********");
			mav = CommonUtil.getMessageView("명한 신청 실패!.", url , "" );
			throw new ModelAndViewDefiningException(mav);
		}
		
	}
}
