/**
  * Hana Project
  * Copyright 2014 iRush Co.,
  *
  */
package com.hanaph.gw.yt.yearendtax.controller;

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

import com.hanaph.gw.co.authority.service.AuthorityService;
import com.hanaph.gw.co.common.utils.MarshallerUtil;
import com.hanaph.gw.co.common.utils.MenuUtil;
import com.hanaph.gw.co.common.utils.StringUtil;
import com.hanaph.gw.co.menu.service.MenuService;
import com.hanaph.gw.co.menu.vo.MenuVO;
import com.hanaph.gw.pe.member.vo.MemberVO;
import com.hanaph.gw.yt.yearendtax.service.YearendtaxService;
import com.hanaph.gw.yt.yearendtax.vo.YearendtaxAddressVO;
import com.hanaph.gw.yt.yearendtax.vo.YearendtaxVO;

/**
 * <pre>
 * Class Name : YearendtaxController.java
 * 설명 : 연말정산 Controller
 * 
 * Modification Information
 *    수정일      수정자              수정 내용
 *  ------------ -------------- --------------------------------
 *  2014. 11. 24.      CHOIILJI         
 * </pre>
 * 
 * @version : 
 * @author  : CHOIILJI(choiilji@irush.co.kr)
 * @since   : 2014. 11. 24.
 */
@Controller
public class YearendtaxController {
	
	@Autowired
	private YearendtaxService yearendtaxService;
	
	@Autowired
	private MenuService menuService;

	@Autowired
	private AuthorityService authorityService;
	
	/**
	 * 
	 * <pre>
	 * 1. 개요     : 연말정산 리스트
	 * 2. 처리내용 : 연말정산 리스트 가져온다.
	 * </pre>
	 * @Method Name : yearendtaxList
	 * @param request
	 * @return
	 */
	@RequestMapping("/yt/yearendtax/yearendtaxList.do")
	public ModelAndView yearendtaxList(HttpServletRequest request){
		
		final String MENU_CHILD= "0501";
		
		ModelAndView mav = new ModelAndView("yt/yearendtax/yearendtaxList");
		
		//LNB 메뉴 생성 START
		String menu_parents = StringUtil.substring(MENU_CHILD, 2);
		MenuUtil mu = new MenuUtil();
		List<MenuVO> lnbMenuList = mu.makeLnb(request, menu_parents , menuService);
		mav.addObject("lnbMenuList", lnbMenuList);
		mav.addObject("MENU_CHILD", MENU_CHILD);
		//LNB 메뉴 생성 END
		
		return mav;
	}
	
	/**
	 * 
	 * <pre>
	 * 1. 개요     : 연말정산상세 정보
	 * 2. 처리내용 : 연말정산상세 정보 가져온다.
	 * </pre>
	 * @Method Name : yearendtaxDetail
	 * @param request
	 * @return
	 */
	@RequestMapping("/yt/yearendtax/yearendtaxDetail.do")
	public ModelAndView yearendtaxDetail(HttpServletRequest request){
		
		final String MENU_CHILD= "0501";
		
		ModelAndView mav = new ModelAndView("yt/yearendtax/yearendtaxDetail");
		Map<String, String> paramMap = new HashMap<String, String>();
		
		//세션에서 회원정보 가져 온다.
		HttpSession session = request.getSession(false);
		MemberVO memberSessionVO = (MemberVO) session.getAttribute("gwUser");
		String emp_no = memberSessionVO.getEmp_no();
		String year = StringUtil.nvl(request.getParameter("year"));

		paramMap.put("emp_no", emp_no);
		paramMap.put("year", year);
		paramMap.put("adjst_div", "0");
		
		YearendtaxVO yearendtaxDetail = yearendtaxService.getYearendtaxDetail(paramMap);
		YearendtaxVO yearendtaxDetail2 = yearendtaxService.getYearendtaxDetail2(paramMap);
		List<YearendtaxVO> yearendtaxDetail3 = yearendtaxService.getYearendtaxDetail3(paramMap);
		
		/*관리자 권한 리스트 가져오기*/
		int [] authIdxArray = {1,2};
		Map<String, Object> paramMap1 = new HashMap<String, Object>();
		paramMap1.put("authIdxArray", authIdxArray);
		List<MemberVO> authorityMemberList = authorityService.getAuthorityMemberList(paramMap1);
		
		//LNB 메뉴 생성 START
		String menu_parents = StringUtil.substring(MENU_CHILD, 2);
		MenuUtil mu = new MenuUtil();
		List<MenuVO> lnbMenuList = mu.makeLnb(request, menu_parents , menuService);
		mav.addObject("lnbMenuList", lnbMenuList);
		mav.addObject("MENU_CHILD", MENU_CHILD);
		//LNB 메뉴 생성 END
		
		mav.addObject("year", year); //년도
		mav.addObject("emp_no", emp_no); //임직원
		mav.addObject("authorityMemberList", authorityMemberList); //관리자
		mav.addObject("yearendtaxDetail", yearendtaxDetail); //원천징수1페이지
		mav.addObject("yearendtaxDetail2", yearendtaxDetail2); //원천징수2페이지
		mav.addObject("yearendtaxDetail3", yearendtaxDetail3); //원천징수3페이지
        return mav;
	}
	
	
	/**
	 * <pre>
	 * 1. 개요     : 인쇄 팝업
	 * 2. 처리내용 : 연말정산 상세페이지 인쇄
	 * </pre>
	 * @Method Name : previewYearendtaxDetail
	 * @param request
	 * @return
	 */
	@RequestMapping("/yt/yearendtax/previewYearendtaxDetail.do")
	public ModelAndView previewYearendtaxDetail(HttpServletRequest request){
		
		final String MENU_CHILD= "0501";
		
		ModelAndView mav = new ModelAndView("yt/yearendtax/previewYearendtaxDetail");
		Map<String, String> paramMap = new HashMap<String, String>();
		
		//세션에서 회원정보 가져 온다.
		HttpSession session = request.getSession(false);
		MemberVO memberSessionVO = (MemberVO) session.getAttribute("gwUser");
		String emp_no = memberSessionVO.getEmp_no();
		String year = StringUtil.nvl(request.getParameter("year"));

		paramMap.put("emp_no", emp_no);
		paramMap.put("year", year);
		paramMap.put("adjst_div", "0");
		
		YearendtaxVO yearendtaxDetail = yearendtaxService.getYearendtaxDetail(paramMap);
		YearendtaxVO yearendtaxDetail2 = yearendtaxService.getYearendtaxDetail2(paramMap);
		List<YearendtaxVO> yearendtaxDetail3 = yearendtaxService.getYearendtaxDetail3(paramMap);
		
		//LNB 메뉴 생성 START
		String menu_parents = StringUtil.substring(MENU_CHILD, 2);
		MenuUtil mu = new MenuUtil();
		List<MenuVO> lnbMenuList = mu.makeLnb(request, menu_parents , menuService);
		mav.addObject("lnbMenuList", lnbMenuList);
		mav.addObject("MENU_CHILD", MENU_CHILD);
		//LNB 메뉴 생성 END
				
		mav.addObject("yearendtaxDetail", yearendtaxDetail); //원천징수1페이지
		mav.addObject("yearendtaxDetail2", yearendtaxDetail2); //원천징수2페이지
		mav.addObject("yearendtaxDetail3", yearendtaxDetail3); //원천징수3페이지
        return mav;
		
	}
	
	/**
	 * <pre>
	 * 1. 개요     : 연말정산등록페이지
	 * 2. 처리내용 : 연말정산 등록페이지로 이동한다.
	 * </pre>
	 * @Method Name : yearendtaxInsertForm
	 * @param request
	 * @return
	 */		
	@RequestMapping("/yt/yearendtax/yearendtaxInsertForm.do")
	public ModelAndView yearendtaxInsertForm(HttpServletRequest request){
		
		final String MENU_CHILD= "0501";
		
		ModelAndView mav = new ModelAndView("yt/yearendtax/yearendtaxInsertForm");
		Map<String, String> paramMap = new HashMap<String, String>();
		
		//세션에서 회원정보 가져 온다.
		HttpSession session = request.getSession(false);
		MemberVO memberSessionVO = (MemberVO) session.getAttribute("gwUser");
		String emp_no = memberSessionVO.getEmp_no();
		String year = StringUtil.nvl(request.getParameter("year"));
		
		paramMap.put("emp_no", emp_no);
		paramMap.put("year", year);
		paramMap.put("adjst_div", "0");
		
		YearendtaxVO yearendtaxDetail = yearendtaxService.getYearendtaxDetail0(paramMap);
		
		List<YearendtaxAddressVO> yearendtaxAddressList = yearendtaxService.getYearendtaxAddressList(paramMap);
		int addressCnt = 0;
		addressCnt = yearendtaxAddressList.size();
		
		//LNB 메뉴 생성 START
		String menu_parents = StringUtil.substring(MENU_CHILD, 2);
		MenuUtil mu = new MenuUtil();
		List<MenuVO> lnbMenuList = mu.makeLnb(request, menu_parents , menuService);
		mav.addObject("lnbMenuList", lnbMenuList);
		mav.addObject("MENU_CHILD", MENU_CHILD);
		//LNB 메뉴 생성 END
		
		mav.addObject("yearendtaxDetail", yearendtaxDetail);
		mav.addObject("addressCnt", addressCnt);
		mav.addObject("year", year);
		return mav;
	}
	
	/**
	 * <pre>
	 * 1. 개요     : 종전 근무지 정보 팝업
	 * 2. 처리내용 : 종전 근무지 정보 팝업을 가져온다.
	 * </pre>
	 * @Method Name : yearendtaxPreviousWorkplacePopup
	 * @param request
	 * @return
	 */		
	@RequestMapping("/yt/yearendtax/yearendtaxPreviousWorkplacePopup.do")
	public ModelAndView yearendtaxPreviousWorkplacePopup(HttpServletRequest request){
		
		ModelAndView mav = new ModelAndView("yt/yearendtax/yearendtaxPreviousWorkplacePopup");
		Map<String, String> paramMap = new HashMap<String, String>();
		
		//세션에서 회원정보 가져 온다.
		HttpSession session = request.getSession(false);
		MemberVO memberSessionVO = (MemberVO) session.getAttribute("gwUser");
		String emp_no = memberSessionVO.getEmp_no();
		String year = StringUtil.nvl(request.getParameter("year"));
		
		paramMap.put("emp_no", emp_no);
		paramMap.put("year", year);
		paramMap.put("adjst_div", "0");
		
		List<YearendtaxVO> previousWorkplaceList = yearendtaxService.getYearendtaxPreviousWorkplaceList(paramMap);
		
		mav.addObject("year", year);
		mav.addObject("previousWorkplaceList", previousWorkplaceList);
		return mav;
	}
	
	/**
	 * <pre>
	 * 1. 개요     : 부양가족 팝업
	 * 2. 처리내용 : 부양가족 팝업을 가져온다.
	 * </pre>
	 * @Method Name : yearendtaxFamilyPopup
	 * @param request
	 * @return
	 */		
	@RequestMapping("/yt/yearendtax/yearendtaxFamilyPopup.do")
	public ModelAndView yearendtaxFamilyPopup(HttpServletRequest request){
		
		ModelAndView mav = new ModelAndView("yt/yearendtax/yearendtaxFamilyPopup");
		Map<String, String> paramMap = new HashMap<String, String>();
		
		//세션에서 회원정보 가져 온다.
		HttpSession session = request.getSession(false);
		MemberVO memberSessionVO = (MemberVO) session.getAttribute("gwUser");
		String emp_no = memberSessionVO.getEmp_no();
		String year = StringUtil.nvl(request.getParameter("year"));
		
		paramMap.put("emp_no", emp_no);
		paramMap.put("year", year);
		paramMap.put("adjst_div", "0");
		
		List<YearendtaxVO> familyList = yearendtaxService.getYearendtaxFamilyList(paramMap);
		
		mav.addObject("year", year);
		mav.addObject("familyList", familyList);
		return mav;
	}
	
	/**
	 * <pre>
	 * 1. 개요     : 주택자금 팝업
	 * 2. 처리내용 : 주택자금 팝업을 가져온다.
	 * </pre>
	 * @Method Name : yearendtaxHousePopup
	 * @param request
	 * @return
	 */		
	@RequestMapping("/yt/yearendtax/yearendtaxHousePopup.do")
	public ModelAndView yearendtaxHousePopup(HttpServletRequest request){
		
		ModelAndView mav = new ModelAndView();
		Map<String, String> paramMap = new HashMap<String, String>();
		
		//세션에서 회원정보 가져 온다.
		HttpSession session = request.getSession(false);
		MemberVO memberSessionVO = (MemberVO) session.getAttribute("gwUser");
		String emp_no = memberSessionVO.getEmp_no();
		String year = StringUtil.nvl(request.getParameter("year"));
		String searchType = StringUtil.nvl(request.getParameter("searchType"));
		
		paramMap.put("emp_no", emp_no);
		paramMap.put("year", year);
		paramMap.put("searchType", searchType);
		paramMap.put("adjst_div", "0");
		
		List<YearendtaxVO> houseList = yearendtaxService.getYearendtaxHouseList(paramMap);
		
		if ("housingFund".equals(searchType)) {
			mav.setViewName("yt/yearendtax/yearendtaxHousingFundPopup");
		} else if ("rent".equals(searchType)) {
			mav.setViewName("yt/yearendtax/yearendtaxRentPopup");
		}
		
		mav.addObject("year", year);
		mav.addObject("searchType", searchType);
		mav.addObject("houseList", houseList);
		return mav;
	}
	
	/**
	 * <pre>
	 * 1. 개요     : 신용카드 사용금액 팝업
	 * 2. 처리내용 : 신용카드 사용금액 팝업을 가져온다.
	 * </pre>
	 * @Method Name : yearendtaxCreditCardPopup
	 * @param request
	 * @return
	 */		
	@RequestMapping("/yt/yearendtax/yearendtaxCreditCardPopup.do")
	public ModelAndView yearendtaxCreditCardPopup(HttpServletRequest request){
		
		ModelAndView mav = new ModelAndView("yt/yearendtax/yearendtaxCreditCardPopup");
		Map<String, String> paramMap = new HashMap<String, String>();
		
		//세션에서 회원정보 가져 온다.
		HttpSession session = request.getSession(false);
		MemberVO memberSessionVO = (MemberVO) session.getAttribute("gwUser");
		String emp_no = memberSessionVO.getEmp_no();
		String year = StringUtil.nvl(request.getParameter("year"));
		
		paramMap.put("emp_no", emp_no);
		paramMap.put("year", year);
		paramMap.put("adjst_div", "0");
		
		List<YearendtaxVO> creditCardList = yearendtaxService.getYearendtaxCreditCardList(paramMap);
		
		mav.addObject("year", year);
		mav.addObject("creditCardList", creditCardList);
		return mav;
	}
	
	/**
	 * <pre>
	 * 1. 개요     : 보험료 팝업
	 * 2. 처리내용 : 보험료 팝업을 가져온다.
	 * </pre>
	 * @Method Name : yearendtaxInsurancePopup
	 * @param request
	 * @return
	 */		
	@RequestMapping("/yt/yearendtax/yearendtaxInsurancePopup.do")
	public ModelAndView yearendtaxInsurancePopup(HttpServletRequest request){
		
		ModelAndView mav = new ModelAndView("yt/yearendtax/yearendtaxInsurancePopup");
		Map<String, String> paramMap = new HashMap<String, String>();
		
		//세션에서 회원정보 가져 온다.
		HttpSession session = request.getSession(false);
		MemberVO memberSessionVO = (MemberVO) session.getAttribute("gwUser");
		String emp_no = memberSessionVO.getEmp_no();
		String year = StringUtil.nvl(request.getParameter("year"));
		
		paramMap.put("emp_no", emp_no);
		paramMap.put("year", year);
		paramMap.put("adjst_div", "0");
		
		List<YearendtaxVO> insuranceList = yearendtaxService.getYearendtaxInsuranceList(paramMap);
		
		mav.addObject("year", year);
		mav.addObject("insuranceList", insuranceList);
		return mav;
	}
	
	/**
	 * <pre>
	 * 1. 개요     : 의료비 팝업
	 * 2. 처리내용 : 의료비 팝업을 가져온다.
	 * </pre>
	 * @Method Name : yearendtaxMedicalPopup
	 * @param request
	 * @return
	 */		
	@RequestMapping("/yt/yearendtax/yearendtaxMedicalPopup.do")
	public ModelAndView yearendtaxMedicalPopup(HttpServletRequest request){
		
		ModelAndView mav = new ModelAndView("yt/yearendtax/yearendtaxMedicalPopup");
		Map<String, String> paramMap = new HashMap<String, String>();
		
		//세션에서 회원정보 가져 온다.
		HttpSession session = request.getSession(false);
		MemberVO memberSessionVO = (MemberVO) session.getAttribute("gwUser");
		String emp_no = memberSessionVO.getEmp_no();
		String year = StringUtil.nvl(request.getParameter("year"));
		
		paramMap.put("emp_no", emp_no);
		paramMap.put("year", year);
		paramMap.put("adjst_div", "0");
		
		List<YearendtaxVO> medicalList = yearendtaxService.getYearendtaxMedicalList(paramMap);
		
		mav.addObject("year", year);
		mav.addObject("medicalList", medicalList);
		return mav;
	}
	
	/**
	 * <pre>
	 * 1. 개요     : 교육비 팝업
	 * 2. 처리내용 : 교육비 팝업을 가져온다.
	 * </pre>
	 * @Method Name : yearendtaxEducatePopup
	 * @param request
	 * @return
	 */		
	@RequestMapping("/yt/yearendtax/yearendtaxEducatePopup.do")
	public ModelAndView yearendtaxEducatePopup(HttpServletRequest request){
		
		ModelAndView mav = new ModelAndView("yt/yearendtax/yearendtaxEducatePopup");
		Map<String, String> paramMap = new HashMap<String, String>();
		
		//세션에서 회원정보 가져 온다.
		HttpSession session = request.getSession(false);
		MemberVO memberSessionVO = (MemberVO) session.getAttribute("gwUser");
		String emp_no = memberSessionVO.getEmp_no();
		String year = StringUtil.nvl(request.getParameter("year"));
		
		paramMap.put("emp_no", emp_no);
		paramMap.put("year", year);
		paramMap.put("adjst_div", "0");
		
		List<YearendtaxVO> educateList = yearendtaxService.getYearendtaxEducateList(paramMap);
		
		mav.addObject("year", year);
		mav.addObject("educateList", educateList);
		return mav;
	}
	
	/**
	 * <pre>
	 * 1. 개요     : 기부금 팝업
	 * 2. 처리내용 : 기부금 팝업을 가져온다.
	 * </pre>
	 * @Method Name : yearendtaxContributePopup
	 * @param request
	 * @return
	 */		
	@RequestMapping("/yt/yearendtax/yearendtaxContributePopup.do")
	public ModelAndView yearendtaxContributePopup(HttpServletRequest request){
		
		ModelAndView mav = new ModelAndView("yt/yearendtax/yearendtaxContributePopup");
		Map<String, String> paramMap = new HashMap<String, String>();
		
		//세션에서 회원정보 가져 온다.
		HttpSession session = request.getSession(false);
		MemberVO memberSessionVO = (MemberVO) session.getAttribute("gwUser");
		String emp_no = memberSessionVO.getEmp_no();
		String year = StringUtil.nvl(request.getParameter("year"));
		
		paramMap.put("emp_no", emp_no);
		paramMap.put("year", year);
		paramMap.put("adjst_div", "0");
		
		List<YearendtaxVO> contributeList = yearendtaxService.getYearendtaxContributeList(paramMap);
		
		mav.addObject("year", year);
		mav.addObject("contributeList", contributeList);
		return mav;
	}
	
	/**
	 * <pre>
	 * 1. 개요     : 개인연금 팝업
	 * 2. 처리내용 : 개인연금 팝업을 가져온다.
	 * </pre>
	 * @Method Name : yearendtaxSavingPopup
	 * @param request
	 * @return
	 */		
	@RequestMapping("/yt/yearendtax/yearendtaxSavingPopup.do")
	public ModelAndView yearendtaxSavingPopup(HttpServletRequest request){
		
		ModelAndView mav = new ModelAndView("yt/yearendtax/yearendtaxSavingPopup");
		Map<String, String> paramMap = new HashMap<String, String>();
		
		//세션에서 회원정보 가져 온다.
		HttpSession session = request.getSession(false);
		MemberVO memberSessionVO = (MemberVO) session.getAttribute("gwUser");
		String emp_no = memberSessionVO.getEmp_no();
		String year = StringUtil.nvl(request.getParameter("year"));
		String searchType = StringUtil.nvl(request.getParameter("searchType"));
		
		paramMap.put("emp_no", emp_no);
		paramMap.put("year", year);
		paramMap.put("adjst_div", "0");
		paramMap.put("searchType", searchType);
		
		List<YearendtaxVO> savingList = yearendtaxService.getYearendtaxSavingList(paramMap);
		
		mav.addObject("year", year);
		mav.addObject("savingList", savingList);
		mav.addObject("searchType", searchType);
		return mav;
	}
	
	/**
	 * <pre>
	 * 1. 개요     : 금융기관 팝업
	 * 2. 처리내용 : 금융기관 팝업을 가져온다.
	 * </pre>
	 * @Method Name : yearendtaxFinancialPopup
	 * @param request
	 * @return
	 */		
	@RequestMapping("/yt/yearendtax/yearendtaxFinancialPopup.do")
	public ModelAndView yearendtaxFinancialPopup(HttpServletRequest request){
		
		ModelAndView mav = new ModelAndView("yt/yearendtax/yearendtaxFinancialPopup");
		Map<String, String> paramMap = new HashMap<String, String>();
		
		String year = StringUtil.nvl(request.getParameter("year"));
		String seq = StringUtil.nvl(request.getParameter("seq"));
		String searchCode = StringUtil.nvl(request.getParameter("searchCode"), "");
		String searchName = StringUtil.nvl(request.getParameter("searchName"), "");
		
		paramMap.put("year", year);
		paramMap.put("adjst_div", "0");
		paramMap.put("searchCode", searchCode);
		paramMap.put("searchName", searchName);
		
		List<YearendtaxVO> financialList = yearendtaxService.getYearendtaxFinancialList(paramMap);
		
		mav.addObject("financialList", financialList);
		mav.addObject("year", year);
		mav.addObject("seq", seq);
		mav.addObject("searchCode", searchCode);
		mav.addObject("searchName", searchName);
		return mav;
	}
	
	/**
	 * <pre>
	 * 1. 개요     : 관계인 팝업
	 * 2. 처리내용 : 관계인 팝업을 가져온다.
	 * </pre>
	 * @Method Name : yearendtaxDependentsPopup
	 * @param request
	 * @return
	 */		
	@RequestMapping("/yt/yearendtax/yearendtaxDependentsPopup.do")
	public ModelAndView yearendtaxDependentsPopup(HttpServletRequest request){
		
		ModelAndView mav = new ModelAndView("yt/yearendtax/yearendtaxDependentsPopup");
		Map<String, String> paramMap = new HashMap<String, String>();
		
		//세션에서 회원정보 가져 온다.
		HttpSession session = request.getSession(false);
		MemberVO memberSessionVO = (MemberVO) session.getAttribute("gwUser");
		String emp_no = memberSessionVO.getEmp_no();
		String year = StringUtil.nvl(request.getParameter("year"));
		String seq = StringUtil.nvl(request.getParameter("seq"));
		String searchType = StringUtil.nvl(request.getParameter("searchType"), "");
		String searchCode = StringUtil.nvl(request.getParameter("searchCode"), "");
		String searchName = StringUtil.nvl(request.getParameter("searchName"), "");
		
		paramMap.put("emp_no", emp_no);
		paramMap.put("year", year);
		paramMap.put("searchType", searchType);
		paramMap.put("searchCode", searchCode);
		paramMap.put("searchName", searchName);
		
		List<YearendtaxVO> dependentsList = yearendtaxService.getYearendtaxDependentsList(paramMap);
		
		mav.addObject("dependentsList", dependentsList);
		mav.addObject("year", year);
		mav.addObject("seq", seq);
		mav.addObject("searchType", searchType);
		mav.addObject("searchCode", searchCode);
		mav.addObject("searchName", searchName);
		return mav;
	}
	
	/**
	 * <pre>
	 * 1. 개요     : 관리정보입력 (우편번호)팝업
	 * 2. 처리내용 : 관리정보입력 (우편번호)팝업
	 * </pre>
	 * @Method Name : yearendtaxAddressPopup
	 * @param request
	 * @return
	 */
	@RequestMapping("/yt/yearendtax/yearendtaxAddressPopup.do")
	public ModelAndView yearendtaxAddressPopup(HttpServletRequest request){
		
		ModelAndView mav = new ModelAndView("yt/yearendtax/yearendtaxAddressPopup");
		
		String year = StringUtil.nvl(request.getParameter("year"),"");
		String type = StringUtil.nvl(request.getParameter("type"));
		
		Map<String, String> paramMap = new HashMap<String, String>();
		
		//세션에서 회원정보 가져 온다.
		HttpSession session = request.getSession(false);
		MemberVO memberSessionVO = (MemberVO) session.getAttribute("gwUser");
		String emp_no = memberSessionVO.getEmp_no();
		
		paramMap.put("emp_no", emp_no);
		
		List<YearendtaxAddressVO> yearendtaxAddressList = yearendtaxService.getYearendtaxAddressList(paramMap);
		
		mav.addObject("emp_no", emp_no);
		mav.addObject("year", year);
		mav.addObject("type", type);
		mav.addObject("yearendtaxAddressList", yearendtaxAddressList);
		return mav;
		
	}
	
	/**
	 * <pre>
	 * 1. 개요     : 우편번호 레이어 팝업
	 * 2. 처리내용 : 우편번호 레이어 팝업
	 * </pre>
	 * @Method Name : yearendtaxZipcodePopup
	 * @param request
	 * @return
	 */
	@RequestMapping("/yt/yearendtax/yearendtaxZipcodePopup.do")
	public ModelAndView yearendtaxZipcodePopup(HttpServletRequest request){
		
		String seq = StringUtil.nvl(request.getParameter("seq")); //행추가한 로우번호

		ModelAndView mav = new ModelAndView("yt/yearendtax/yearendtaxZipcodePopup");
		
		mav.addObject("seq", seq);
		return mav;
	}
	
	/**
	 * <pre>
	 * 1. 개요     : 주소 찾기
	 * 2. 처리내용 : 주소 찾기
	 * </pre>
	 * @Method Name : searchAddress
	 * @param request
	 * @return
	 */
	@RequestMapping("/yt/yearendtax/searchAddress.do")
	public ModelAndView searchAddress(HttpServletRequest request){
		
		ModelAndView mav = new ModelAndView("yt/yearendtax/yearendtaxZipcodePopup");

		String searchType = StringUtil.nvl(request.getParameter("searchType"),"dong"); //검색타입
		String searchKeyword = StringUtil.nvl(request.getParameter("searchKeyword")); //검색어
		String seq = StringUtil.nvl(request.getParameter("seq")); //행추가한 로우번호
		
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("searchKeyword", searchKeyword);
		paramMap.put("searchType", searchType);

		
		List<YearendtaxAddressVO> searchAddressList = yearendtaxService.getSearchAddressList(paramMap);
		
		mav.addObject("seq", seq);
		mav.addObject("searchType", searchType);
		mav.addObject("searchKeyword", searchKeyword);
		mav.addObject("searchAddressList", searchAddressList);
		
		return mav;
	}
	
	/**
	 * <pre>
	 * 1. 개요     : 주소 저장 
	 * 2. 처리내용 : 주소 저장
	 * </pre>
	 * @Method Name : insertYearendtaxAddress
	 * @param request
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("/yt/yearendtax/insertYearendtaxAddress.do")
	public void insertYearendtaxAddress(HttpServletRequest request, HttpServletResponse response) throws IOException{
		
		/*parameter 셋팅*/
		Map<String, Object> paramMap = new HashMap<String, Object>();
		
		String[] emp_no = request.getParameterValues("emp_no"); //사원번호
		String[] tel_no = request.getParameterValues("tel_no"); //휴대전화번호
		String[] zip_cd = request.getParameterValues("zip_cd"); //우편번호
		String[] address1 = request.getParameterValues("address1"); //주소1
		String[] address2 = request.getParameterValues("address2"); //주소2
		
		paramMap.put("emp_no",emp_no);
		paramMap.put("tel_no",tel_no);
		paramMap.put("zip_cd",zip_cd);
		paramMap.put("address1",address1);
		paramMap.put("address2",address2);
		
		yearendtaxService.insertYearendtaxAddress(paramMap);
		
		Map<String, String> paramMap1 = new HashMap<String, String>();
		paramMap1.put("emp_no", emp_no[0]);
		List<YearendtaxAddressVO> yearendtaxAddressList = yearendtaxService.getYearendtaxAddressList(paramMap1);
		
		/*결과 반환*/
		YearendtaxAddressVO yearendtaxAddressVO = new YearendtaxAddressVO();
		
		yearendtaxAddressVO.setAddressCnt(yearendtaxAddressList.size());
		
		MarshallerUtil.marshalling("json", response, yearendtaxAddressVO);
		
	}
	
	/**
	 * <pre>
	 * 1. 개요     : 주택계약 팝업 저장
	 * 2. 처리내용 : 주택계약 팝업 내용을 저장한다.
	 * </pre>
	 * @Method Name : procYearendtaxHouseAjax
	 * @param request
	 * @param response
	 * @throws IOException
	 */		
	@RequestMapping("/yt/yearendtax/procYearendtaxHouseAjax.do")
	public void procYearendtaxHouseAjax(HttpServletRequest request, HttpServletResponse response) throws IOException{
		
		/*parameter 셋팅*/
		Map<String, Object> paramMap = new HashMap<String, Object>();
		
		//세션에서 회원정보 가져 온다.
		HttpSession session = request.getSession(false);
		MemberVO memberSessionVO = (MemberVO) session.getAttribute("gwUser");
		String emp_no = memberSessionVO.getEmp_no();
		String year = StringUtil.nvl(request.getParameter("year"));
		String searchType = StringUtil.nvl(request.getParameter("searchType"));
		
		String[] house_gb = request.getParameterValues("house_gb"); // 입력선택
		String[] house_nm = request.getParameterValues("house_nm"); // 성명(상호)
		String[] house_jumin = request.getParameterValues("house_jumin"); // 주민번호 or 사업자번호
		String[] house_start_dt = request.getParameterValues("house_start_dt"); // 계약기간 시작
		String[] house_end_dt = request.getParameterValues("house_end_dt"); // 계약기간 종료
		String[] house_amt1 = request.getParameterValues("house_amt1"); // 월세액 합계 
		String[] house_amt2 = request.getParameterValues("house_amt2"); // 공제금액
		String[] house_addr = request.getParameterValues("house_addr"); // 임대차 계약서상 주소지
		
		paramMap.put("emp_no", emp_no);
		paramMap.put("year", year);
		paramMap.put("adjst_div", "0");
		paramMap.put("searchType", searchType);
		paramMap.put("house_gb", house_gb);
		paramMap.put("house_nm", house_nm);
		paramMap.put("house_jumin", house_jumin);
		paramMap.put("house_start_dt", house_start_dt);
		paramMap.put("house_end_dt", house_end_dt);
		paramMap.put("house_amt1", house_amt1);
		paramMap.put("house_amt2", house_amt2);
		paramMap.put("house_addr", house_addr);
		
		String result = yearendtaxService.procYearendtaxHouse(paramMap); // 주택계약 내용 삭제/등록
		
		/*결과 반환*/
		YearendtaxVO yearendtaxVO = new YearendtaxVO();
		
		yearendtaxVO.setResult(result);
		
		MarshallerUtil.marshalling("json", response, yearendtaxVO);
	}
	
	/**
	 * <pre>
	 * 1. 개요     : 개인연금 팝업 저장
	 * 2. 처리내용 : 개인연금 팝업 내용을 저장한다.
	 * </pre>
	 * @Method Name : procYearendtaxSavingAjax
	 * @param request
	 * @param response
	 * @throws IOException
	 */		
	@RequestMapping("/yt/yearendtax/procYearendtaxSavingAjax.do")
	public void procYearendtaxSavingAjax(HttpServletRequest request, HttpServletResponse response) throws IOException{
		
		/*parameter 셋팅*/
		Map<String, Object> paramMap = new HashMap<String, Object>();
		
		//세션에서 회원정보 가져 온다.
		HttpSession session = request.getSession(false);
		MemberVO memberSessionVO = (MemberVO) session.getAttribute("gwUser");
		String emp_no = memberSessionVO.getEmp_no();
		String year = StringUtil.nvl(request.getParameter("year"));
		String searchType = StringUtil.nvl(request.getParameter("searchType"));
		
		String[] gongje_gb = request.getParameterValues("gongje_gb"); // 소득공제구분
		String[] bank_nm = request.getParameterValues("bank_nm"); // 금융기관명
		String[] bank_cd = request.getParameterValues("bank_cd"); // 금융기관코드
		String[] account_no = request.getParameterValues("account_no"); // 계좌번호
		String[] in_amt = request.getParameterValues("in_amt"); // 불입금액
		
		paramMap.put("emp_no", emp_no);
		paramMap.put("year", year);
		paramMap.put("adjst_div", "0");
		paramMap.put("searchType", searchType);
		paramMap.put("gongje_gb", gongje_gb);
		paramMap.put("bank_nm", bank_nm);
		paramMap.put("bank_cd", bank_cd);
		paramMap.put("account_no", account_no);
		paramMap.put("in_amt", in_amt);
		
		String result = yearendtaxService.procYearendtaxSaving(paramMap); // 주택계약 내용 삭제/등록
		
		/*결과 반환*/
		YearendtaxVO yearendtaxVO = new YearendtaxVO();
		
		yearendtaxVO.setResult(result);
		
		MarshallerUtil.marshalling("json", response, yearendtaxVO);
	}
	
	/**
	 * <pre>
	 * 1. 개요     : 부양가족 팝업 저장
	 * 2. 처리내용 : 부양가족 팝업 내용을 저장한다.
	 * </pre>
	 * @Method Name : procYearendtaxFamilyAjax
	 * @param request
	 * @param response
	 * @throws IOException
	 */		
	@RequestMapping("/yt/yearendtax/procYearendtaxFamilyAjax.do")
	public void procYearendtaxFamilyAjax(HttpServletRequest request, HttpServletResponse response) throws IOException{
		
		/*parameter 셋팅*/
		Map<String, Object> paramMap = new HashMap<String, Object>();
		
		//세션에서 회원정보 가져 온다.
		HttpSession session = request.getSession(false);
		MemberVO memberSessionVO = (MemberVO) session.getAttribute("gwUser");
		String emp_no = memberSessionVO.getEmp_no();
		String year = StringUtil.nvl(request.getParameter("year"));
		
		String[] foreign_cd = request.getParameterValues("foreign_cd"); // 내외국인
		String[] rel_nm = request.getParameterValues("rel_nm"); // 성명
		String[] rel_cd = request.getParameterValues("rel_cd"); // 관계코드
		String[] rel_jumin_no = request.getParameterValues("rel_jumin_no"); // 관계인주민번호(수정)
		String[] org_rel_jumin_no = request.getParameterValues("org_rel_jumin_no"); // 관계인주민번호(original)
		String[] choose_yn = request.getParameterValues("val_choose_yn"); // 기본
		String[] pensioner_yn = request.getParameterValues("val_pensioner_yn"); // 수급자
		String[] foster_child_yn = request.getParameterValues("val_foster_child_yn"); // 위탁아동
		String[] respect_aged_yn = request.getParameterValues("val_respect_aged_yn"); // 경로우대
		String[] disabled_person_yn = request.getParameterValues("val_disabled_person_yn"); // 장애인
		String[] woman_yn = request.getParameterValues("val_woman_yn"); // 부녀자
		String[] single_parents_yn = request.getParameterValues("val_single_parents_yn"); // 한부모
		String[] insulance_yn = request.getParameterValues("val_insulance_yn"); // 보험료
		String[] medical_yn = request.getParameterValues("val_medical_yn"); // 의료비
		String[] education_yn = request.getParameterValues("val_education_yn"); // 교육비
		String[] card_yn = request.getParameterValues("val_card_yn"); // 신용카드
		String[] contribution_yn = request.getParameterValues("val_contribution_yn"); // 기부금
		String[] delete_yn = request.getParameterValues("delete_yn"); // 삭제여부
		
		paramMap.put("emp_no", emp_no);
		paramMap.put("year", year);
		paramMap.put("adjst_div", "0");
		paramMap.put("foreign_cd", foreign_cd);
		paramMap.put("rel_nm", rel_nm);
		paramMap.put("rel_cd", rel_cd);
		paramMap.put("rel_jumin_no", rel_jumin_no);
		paramMap.put("org_rel_jumin_no", org_rel_jumin_no);
		paramMap.put("choose_yn", choose_yn);
		paramMap.put("pensioner_yn", pensioner_yn);
		paramMap.put("foster_child_yn", foster_child_yn);
		paramMap.put("respect_aged_yn", respect_aged_yn);
		paramMap.put("disabled_person_yn", disabled_person_yn);
		paramMap.put("woman_yn", woman_yn);
		paramMap.put("single_parents_yn", single_parents_yn);
		paramMap.put("insulance_yn", insulance_yn);
		paramMap.put("medical_yn", medical_yn);
		paramMap.put("education_yn", education_yn);
		paramMap.put("card_yn", card_yn);
		paramMap.put("contribution_yn", contribution_yn);
		paramMap.put("delete_yn", delete_yn);
		
		String result = yearendtaxService.procYearendtaxFamily(paramMap); // 부양가족 내용 삭제/등록/수정
		
		/*결과 반환*/
		YearendtaxVO yearendtaxVO = new YearendtaxVO();
		
		yearendtaxVO.setResult(result);
		
		MarshallerUtil.marshalling("json", response, yearendtaxVO);
	}
	
	/**
	 * <pre>
	 * 1. 개요     : 보험료 팝업 저장
	 * 2. 처리내용 : 보험료 팝업 내용을 저장한다.
	 * </pre>
	 * @Method Name : procYearendtaxInsuranceAjax
	 * @param request
	 * @param response
	 * @throws IOException
	 */		
	@RequestMapping("/yt/yearendtax/procYearendtaxInsuranceAjax.do")
	public void procYearendtaxInsuranceAjax(HttpServletRequest request, HttpServletResponse response) throws IOException{
		
		/*parameter 셋팅*/
		Map<String, Object> paramMap = new HashMap<String, Object>();
		
		//세션에서 회원정보 가져 온다.
		HttpSession session = request.getSession(false);
		MemberVO memberSessionVO = (MemberVO) session.getAttribute("gwUser");
		String emp_no = memberSessionVO.getEmp_no();
		String year = StringUtil.nvl(request.getParameter("year"));
		
		String[] rel_jumin_no = request.getParameterValues("rel_jumin_no"); // 관계인주민번호
		String[] insurance_person_1 = request.getParameterValues("insurance_person_1"); // 보장성보험-국세청
		String[] insurance_person_2 = request.getParameterValues("insurance_person_2"); // 보장성보험-그밖의자료
		String[] insurance_disabled_person_1 = request.getParameterValues("insurance_disabled_person_1"); // 장애인보장성보험-국세청
		String[] insurance_disabled_person_2 = request.getParameterValues("insurance_disabled_person_2"); // 장애인보장성보험-그밖의자료
		
		paramMap.put("emp_no", emp_no);
		paramMap.put("year", year);
		paramMap.put("adjst_div", "0");
		paramMap.put("rel_jumin_no", rel_jumin_no);
		paramMap.put("insurance_person_1", insurance_person_1);
		paramMap.put("insurance_person_2", insurance_person_2);
		paramMap.put("insurance_disabled_person_1", insurance_disabled_person_1);
		paramMap.put("insurance_disabled_person_2", insurance_disabled_person_2);
		
		String result = yearendtaxService.procYearendtaxInsurance(paramMap); // 보험료 수정
		
		/*결과 반환*/
		YearendtaxVO yearendtaxVO = new YearendtaxVO();
		
		yearendtaxVO.setResult(result);
		
		MarshallerUtil.marshalling("json", response, yearendtaxVO);
	}
	
	/**
	 * <pre>
	 * 1. 개요     : 신용카드 팝업 저장 
	 * 2. 처리내용 : 신용카드 팝업 내용을 저장한다.
	 * </pre>
	 * @Method Name : procYearendtaxCreditCardAjax
	 * @param request
	 * @param response
	 * @throws IOException
	 */		
	@RequestMapping("/yt/yearendtax/procYearendtaxCreditCardAjax.do")
	public void procYearendtaxCreditCardAjax(HttpServletRequest request, HttpServletResponse response) throws IOException{
		
		/*parameter 셋팅*/
		Map<String, Object> paramMap = new HashMap<String, Object>();
		
		//세션에서 회원정보 가져 온다.
		HttpSession session = request.getSession(false);
		MemberVO memberSessionVO = (MemberVO) session.getAttribute("gwUser");
		String emp_no = memberSessionVO.getEmp_no();
		String year = StringUtil.nvl(request.getParameter("year"));
		
		String[] rel_jumin_no = request.getParameterValues("rel_jumin_no"); // 관계인주민번호
		
		/*2014 상반기*/
		String[] credit_1 = request.getParameterValues("credit_1"); // 신용카드 - 국세청
		String[] credit_2 = request.getParameterValues("credit_2"); // 신용카드 - 그 밖의 금액
		String[] cash_1 = request.getParameterValues("cash_1"); // 현금영수증 - 국세청
		String[] cash_2 = request.getParameterValues("cash_2"); // 현금영수증 - 그 밖의 금액
		String[] direct_1 = request.getParameterValues("direct_1"); // 직불카드 - 국세청
		String[] direct_2 = request.getParameterValues("direct_2"); // 직불카드 - 그 밖의 금액
		String[] market_1 = request.getParameterValues("market_1"); // 전통시장 - 국세청
		String[] market_2 = request.getParameterValues("market_2"); // 전통시장 - 그 밖의 금액
		String[] pubric_transport_1 = request.getParameterValues("pubric_transport_1"); // 대중교통 - 국세청
		String[] pubric_transport_2 = request.getParameterValues("pubric_transport_2"); // 대중교통 - 그 밖의 금액
		
		/*2014 하반기*/
		String[] credit_1_sh = request.getParameterValues("credit_1_sh"); // 신용카드 - 국세청
		String[] credit_2_sh = request.getParameterValues("credit_2_sh"); // 신용카드 - 그 밖의 금액
		String[] cash_1_sh = request.getParameterValues("cash_1_sh"); // 현금영수증 - 국세청
		String[] cash_2_sh = request.getParameterValues("cash_2_sh"); // 현금영수증 - 그 밖의 금액
		String[] direct_1_sh = request.getParameterValues("direct_1_sh"); // 직불카드 - 국세청
		String[] direct_2_sh = request.getParameterValues("direct_2_sh"); // 직불카드 - 그 밖의 금액
		String[] market_1_sh = request.getParameterValues("market_1_sh"); // 전통시장 - 국세청
		String[] market_2_sh = request.getParameterValues("market_2_sh"); // 전통시장 - 그 밖의 금액
		String[] pubric_transport_1_sh = request.getParameterValues("pubric_transport_1_sh"); // 대중교통 - 국세청
		String[] pubric_transport_2_sh = request.getParameterValues("pubric_transport_2_sh"); // 대중교통 - 그 밖의 금액
		
		/*2013*/
		String[] credit_1_ly = request.getParameterValues("credit_1_ly"); // 신용카드 - 국세청
		String[] credit_2_ly = request.getParameterValues("credit_2_ly"); // 신용카드 - 그 밖의 금액
		String[] cash_1_ly = request.getParameterValues("cash_1_ly"); // 현금영수증 - 국세청
		String[] cash_2_ly = request.getParameterValues("cash_2_ly"); // 현금영수증 - 그 밖의 금액
		String[] direct_1_ly = request.getParameterValues("direct_1_ly"); // 직불카드 - 국세청
		String[] direct_2_ly = request.getParameterValues("direct_2_ly"); // 직불카드 - 그 밖의 금액
		String[] market_1_ly = request.getParameterValues("market_1_ly"); // 전통시장 - 국세청
		String[] market_2_ly = request.getParameterValues("market_2_ly"); // 전통시장 - 그 밖의 금액
		String[] pubric_transport_1_ly = request.getParameterValues("pubric_transport_1_ly"); // 대중교통 - 국세청
		String[] pubric_transport_2_ly = request.getParameterValues("pubric_transport_2_ly"); // 대중교통 - 그 밖의 금액
		
		paramMap.put("emp_no", emp_no);
		paramMap.put("year", year);
		paramMap.put("adjst_div", "0");
		paramMap.put("rel_jumin_no", rel_jumin_no);
		paramMap.put("credit_1", credit_1);
		paramMap.put("credit_2", credit_2);
		paramMap.put("cash_1", cash_1);
		paramMap.put("cash_2", cash_2);
		paramMap.put("direct_1", direct_1);
		paramMap.put("direct_2", direct_2);
		paramMap.put("market_1", market_1);
		paramMap.put("market_2", market_2);
		paramMap.put("pubric_transport_1", pubric_transport_1);
		paramMap.put("pubric_transport_2", pubric_transport_2);
		paramMap.put("credit_1_sh", credit_1_sh);
		paramMap.put("credit_2_sh", credit_2_sh);
		paramMap.put("cash_1_sh", cash_1_sh);
		paramMap.put("cash_2_sh", cash_2_sh);
		paramMap.put("direct_1_sh", direct_1_sh);
		paramMap.put("direct_2_sh", direct_2_sh);
		paramMap.put("market_1_sh", market_1_sh);
		paramMap.put("market_2_sh", market_2_sh);
		paramMap.put("pubric_transport_1_sh", pubric_transport_1_sh);
		paramMap.put("pubric_transport_2_sh", pubric_transport_2_sh);
		paramMap.put("credit_1_ly", credit_1_ly);
		paramMap.put("credit_2_ly", credit_2_ly);
		paramMap.put("cash_1_ly", cash_1_ly);
		paramMap.put("cash_2_ly", cash_2_ly);
		paramMap.put("direct_1_ly", direct_1_ly);
		paramMap.put("direct_2_ly", direct_2_ly);
		paramMap.put("market_1_ly", market_1_ly);
		paramMap.put("market_2_ly", market_2_ly);
		paramMap.put("pubric_transport_1_ly", pubric_transport_1_ly);
		paramMap.put("pubric_transport_2_ly", pubric_transport_2_ly);
		
		String result = yearendtaxService.procYearendtaxCreditCard(paramMap); // 신용카드 수정
		
		/*결과 반환*/
		YearendtaxVO yearendtaxVO = new YearendtaxVO();
		
		yearendtaxVO.setResult(result);
		
		MarshallerUtil.marshalling("json", response, yearendtaxVO);
	}
	
	/**
	 * <pre>
	 * 1. 개요     : 교육비 팝업 저장
	 * 2. 처리내용 : 교육비 팝업 내용을 저장한다.
	 * </pre>
	 * @Method Name : procYearendtaxEducateAjax
	 * @param request
	 * @param response
	 * @throws IOException
	 */		
	@RequestMapping("/yt/yearendtax/procYearendtaxEducateAjax.do")
	public void procYearendtaxEducateAjax(HttpServletRequest request, HttpServletResponse response) throws IOException{
		
		/*parameter 셋팅*/
		Map<String, Object> paramMap = new HashMap<String, Object>();
		
		//세션에서 회원정보 가져 온다.
		HttpSession session = request.getSession(false);
		MemberVO memberSessionVO = (MemberVO) session.getAttribute("gwUser");
		String emp_no = memberSessionVO.getEmp_no();
		String year = StringUtil.nvl(request.getParameter("year"));
		
		String[] rel_jumin_no = request.getParameterValues("rel_jumin_no"); // 주민번호
		String[] edu_org = request.getParameterValues("edu_org"); // 교육기관
		String[] public_tx_amt = request.getParameterValues("public_tx_amt"); // 공과금
		
		paramMap.put("emp_no", emp_no);
		paramMap.put("year", year);
		paramMap.put("adjst_div", "0");
		paramMap.put("rel_jumin_no", rel_jumin_no);
		paramMap.put("edu_org", edu_org);
		paramMap.put("public_tx_amt", public_tx_amt);
		
		String result = yearendtaxService.procYearendtaxEducate(paramMap); // 교육비 내용 삭제/등록
		
		/*결과 반환*/
		YearendtaxVO yearendtaxVO = new YearendtaxVO();
		
		yearendtaxVO.setResult(result);
		
		MarshallerUtil.marshalling("json", response, yearendtaxVO);
	}
	
	/**
	 * <pre>
	 * 1. 개요     : 의료비 팝업 저장 
	 * 2. 처리내용 : 의료비 팝업 내용을 저장한다.
	 * </pre>
	 * @Method Name : procYearendtaxMedicalAjax
	 * @param request
	 * @param response
	 * @throws IOException
	 */		
	@RequestMapping("/yt/yearendtax/procYearendtaxMedicalAjax.do")
	public void procYearendtaxMedicalAjax(HttpServletRequest request, HttpServletResponse response) throws IOException{
		
		/*parameter 셋팅*/
		Map<String, Object> paramMap = new HashMap<String, Object>();
		
		//세션에서 회원정보 가져 온다.
		HttpSession session = request.getSession(false);
		MemberVO memberSessionVO = (MemberVO) session.getAttribute("gwUser");
		String emp_no = memberSessionVO.getEmp_no();
		String year = StringUtil.nvl(request.getParameter("year"));
		
		String[] rel_jumin_no = request.getParameterValues("rel_jumin_no"); // 주민번호
		String[] medi_gb = request.getParameterValues("medi_gb"); // 증빙구분
		String[] vendor_no = request.getParameterValues("vendor_no"); // 지급처사업자번호
		String[] vendor_nm = request.getParameterValues("vendor_nm"); // 지급처 사업자명
		String[] card_cnt = request.getParameterValues("card_cnt"); // 지급건수
		String[] card_amt = request.getParameterValues("card_amt"); // 지급금액
		
		paramMap.put("emp_no", emp_no);
		paramMap.put("year", year);
		paramMap.put("adjst_div", "0");
		paramMap.put("rel_jumin_no", rel_jumin_no);
		paramMap.put("medi_gb", medi_gb);
		paramMap.put("vendor_no", vendor_no);
		paramMap.put("vendor_nm", vendor_nm);
		paramMap.put("card_cnt", card_cnt);
		paramMap.put("card_amt", card_amt);
		
		String result = yearendtaxService.procYearendtaxMedical(paramMap); // 교육비 내용 삭제/등록
		
		/*결과 반환*/
		YearendtaxVO yearendtaxVO = new YearendtaxVO();
		
		yearendtaxVO.setResult(result);
		
		MarshallerUtil.marshalling("json", response, yearendtaxVO);
	}
	
	/**
	 * <pre>
	 * 1. 개요     : 기부금 팝업 저장
	 * 2. 처리내용 : 기부금 팝업 내용을 저장한다.
	 * </pre>
	 * @Method Name : procYearendtaxContributeAjax
	 * @param request
	 * @param response
	 * @throws IOException
	 */		
	@RequestMapping("/yt/yearendtax/procYearendtaxContributeAjax.do")
	public void procYearendtaxContributeAjax(HttpServletRequest request, HttpServletResponse response) throws IOException{
		
		/*parameter 셋팅*/
		Map<String, Object> paramMap = new HashMap<String, Object>();
		
		//세션에서 회원정보 가져 온다.
		HttpSession session = request.getSession(false);
		MemberVO memberSessionVO = (MemberVO) session.getAttribute("gwUser");
		String emp_no = memberSessionVO.getEmp_no();
		String year = StringUtil.nvl(request.getParameter("year"));
		
		String[] rel_jumin_no = request.getParameterValues("rel_jumin_no"); // 주민번호
		String[] gubun = request.getParameterValues("gubun"); // 구분
		String[] yr_don_div = request.getParameterValues("yr_don_div"); // 기부금유형
		String[] don_amt = request.getParameterValues("don_amt"); // 기부금액
		String[] don_nm = request.getParameterValues("don_nm"); // 기부처상호
		String[] don_no = request.getParameterValues("don_no"); // 기부처사업자번호
		String[] don_receipt_no = request.getParameterValues("don_receipt_no"); // 기부처영수증번호
		
		paramMap.put("emp_no", emp_no);
		paramMap.put("year", year);
		paramMap.put("adjst_div", "0");
		paramMap.put("rel_jumin_no", rel_jumin_no);
		paramMap.put("gubun", gubun);
		paramMap.put("yr_don_div", yr_don_div);
		paramMap.put("don_amt", don_amt);
		paramMap.put("don_nm", don_nm);
		paramMap.put("don_no", don_no);
		paramMap.put("don_receipt_no", don_receipt_no);
		
		String result = yearendtaxService.procYearendtaxContribute(paramMap); // 교육비 내용 삭제/등록
		
		/*결과 반환*/
		YearendtaxVO yearendtaxVO = new YearendtaxVO();
		
		yearendtaxVO.setResult(result);
		
		MarshallerUtil.marshalling("json", response, yearendtaxVO);
	}
	
	/**
	 * <pre>
	 * 1. 개요     : 연말정산 프로시저 호출 후 새로운 정보 불러옴
	 * 2. 처리내용 : 연말정산 프로시저 호출 후 새로운 정보 불러온다.
	 * </pre>
	 * @Method Name : getYearendtaxInfoAjax
	 * @param request
	 * @param response
	 * @throws IOException
	 */		
	@RequestMapping("/yt/yearendtax/getYearendtaxInfoAjax.do")
	public void getYearendtaxInfoAjax(HttpServletRequest request, HttpServletResponse response) throws IOException{
		
		Map<String, String> paramMap = new HashMap<String, String>();
		
		//세션에서 회원정보 가져 온다.
		HttpSession session = request.getSession(false);
		MemberVO memberSessionVO = (MemberVO) session.getAttribute("gwUser");
		String emp_no = memberSessionVO.getEmp_no();
		String year = StringUtil.nvl(request.getParameter("year"));
		String searchType = StringUtil.nvl(request.getParameter("searchType"), "all");
		
		/*등록화면 입력가능 금액*/
		String house_lease_loan_amt = StringUtil.nvl(request.getParameter("house_lease_loan_amt")); // 주택임차차입금
		String house_security_loan1_amt = StringUtil.nvl(request.getParameter("house_security_loan1_amt")); // 장기주택 저당차입금 이자상환액1
		String house_security_loan2_amt = StringUtil.nvl(request.getParameter("house_security_loan2_amt")); // 장기주택 저당차입금 이자상환액2
		String house_security_loan3_amt = StringUtil.nvl(request.getParameter("house_security_loan3_amt")); // 장기주택 저당차입금 이자상환액3
		String house_security_loan4_amt = StringUtil.nvl(request.getParameter("house_security_loan4_amt")); // 장기주택 저당차입금 이자상환액4
		String house_security_loan5_amt = StringUtil.nvl(request.getParameter("house_security_loan5_amt")); // 장기주택 저당차입금 이자상환액5
		String etc_employ_stock_amt = StringUtil.nvl(request.getParameter("etc_employ_stock_amt")); // 우리사주출연금 소득공제
		String etc_chunk_money_amt = StringUtil.nvl(request.getParameter("etc_chunk_money_amt")); // 목돈 안드는 전세 이자상환액 공제
		String etc_income_tax_reduction_amt = StringUtil.nvl(request.getParameter("etc_income_tax_reduction_amt")); // 소득세법
		String etc_contribue_politic_amt = StringUtil.nvl(request.getParameter("etc_contribue_politic_amt")); // 정치자금 기부금
		String etc_sework_tx_amt = StringUtil.nvl(request.getParameter("etc_sework_tx_amt")); // 납세조항공제
		String etc_house_laon_interest_amt = StringUtil.nvl(request.getParameter("etc_house_laon_interest_amt")); // 주택차입금
		String etc_foreigner_income = StringUtil.nvl(request.getParameter("etc_foreigner_income")); // 외국납부 소득금액
		String etc_foreigner_pay = StringUtil.nvl(request.getParameter("etc_foreigner_pay")); // 외국납부 납부세액
		
		paramMap.put("emp_no", emp_no);
		paramMap.put("year", year);
		paramMap.put("adjst_div", "0");
		paramMap.put("searchType", searchType);
		paramMap.put("house_lease_loan_amt", house_lease_loan_amt);
		paramMap.put("house_security_loan1_amt", house_security_loan1_amt);
		paramMap.put("house_security_loan2_amt", house_security_loan2_amt);
		paramMap.put("house_security_loan3_amt", house_security_loan3_amt);
		paramMap.put("house_security_loan4_amt", house_security_loan4_amt);
		paramMap.put("house_security_loan5_amt", house_security_loan5_amt);
		paramMap.put("etc_employ_stock_amt", etc_employ_stock_amt);
		paramMap.put("etc_chunk_money_amt", etc_chunk_money_amt);
		paramMap.put("etc_income_tax_reduction_amt", etc_income_tax_reduction_amt);
		paramMap.put("etc_contribue_politic_amt", etc_contribue_politic_amt);
		paramMap.put("etc_sework_tx_amt", etc_sework_tx_amt);
		paramMap.put("etc_house_laon_interest_amt", etc_house_laon_interest_amt);
		paramMap.put("etc_foreigner_income", etc_foreigner_income);
		paramMap.put("etc_foreigner_pay", etc_foreigner_pay);
		
		YearendtaxVO yearendtaxDetail = new YearendtaxVO();
		String result = "success"; // 결과
		
		try {
			
			if ("all".equals(searchType)) {
				/*등록화면 저장*/
				yearendtaxService.updateYearendtaxInfo(paramMap);
			}
			
			yearendtaxService.getProcedureCall(paramMap); // 프로시저 호출
			
			yearendtaxDetail = yearendtaxService.getYearendtaxDetail0(paramMap);
			
		} catch (Exception e) {
			e.printStackTrace();
			result = e.toString();
		} finally {
			yearendtaxDetail.setResult(result);
		}
		
		MarshallerUtil.marshalling("json", response, yearendtaxDetail);
	}
	
	/**
	 * <pre>
	 * 1. 개요     : 종전 근무지 팝업 저장
	 * 2. 처리내용 : 종전 근무지 팝업 내용을 저장한다.
	 * </pre>
	 * @Method Name : procYearendtaxPreviousWorkplaceAjax
	 * @param request
	 * @param response
	 * @throws IOException
	 */		
	@RequestMapping("/yt/yearendtax/procYearendtaxPreviousWorkplaceAjax.do")
	public void procYearendtaxPreviousWorkplaceAjax(HttpServletRequest request, HttpServletResponse response) throws IOException{
		
		/*parameter 셋팅*/
		Map<String, Object> paramMap = new HashMap<String, Object>();
		
		//세션에서 회원정보 가져 온다.
		HttpSession session = request.getSession(false);
		MemberVO memberSessionVO = (MemberVO) session.getAttribute("gwUser");
		String emp_no = memberSessionVO.getEmp_no();
		String year = StringUtil.nvl(request.getParameter("year"));
		
		String[] seq = request.getParameterValues("seq"); // 일련번호
		String[] corporate_nm = request.getParameterValues("corporate_nm"); // 회사명
		String[] president = request.getParameterValues("president"); // 대표자명
		String[] corporate_no = request.getParameterValues("corporate_no"); // 사업자번호
		String[] start_work_dt = request.getParameterValues("start_work_dt"); // 근무기간 fr
		String[] end_work_dt = request.getParameterValues("end_work_dt"); // 근무기간 to
		String[] start_reduce_dt = request.getParameterValues("start_reduce_dt"); // 감면기간 fr
		String[] end_reduce_dt = request.getParameterValues("end_reduce_dt"); // 감면기간 to
		String[] salary_dt = request.getParameterValues("salary_dt"); // 지급일자
		String[] salary_amt = request.getParameterValues("salary_amt"); // 급여총액
		String[] bonus_amt = request.getParameterValues("bonus_amt"); // 상여총액
		String[] constructive_bonus_amt = request.getParameterValues("constructive_bonus_amt"); // 인정상여
		String[] health_amt = request.getParameterValues("health_amt"); // 건강보험
		String[] employ_amt = request.getParameterValues("employ_amt"); // 고용보험
		String[] kuk_yeon_amt = request.getParameterValues("kuk_yeon_amt"); // 국민연금
		String[] annuity_amt = request.getParameterValues("annuity_amt"); // 개인연금
		String[] income_amt = request.getParameterValues("income_amt"); // 소득세
		String[] jumin_amt = request.getParameterValues("jumin_amt"); // 지방소득세
		String[] nong_amt = request.getParameterValues("nong_amt"); // 농특세
		String[] total_salary = request.getParameterValues("total_salary"); // 근무처별소득명세합계
		String[] stock_option_amt = request.getParameterValues("stock_option_amt"); // 주식매수선택권 행사이익
		String[] employ_stock_amt = request.getParameterValues("employ_stock_amt"); // 우리사주조합인출금
		String[] derector_retirement_amt = request.getParameterValues("derector_retirement_amt"); // 임원퇴직소득금액한도초과액
		String[] total_free = request.getParameterValues("total_free"); // 비과세소득계
		String[] reduction_amt = request.getParameterValues("reduction_amt"); // 감면소득계
		String[] foreign_work_amt = request.getParameterValues("foreign_work_amt"); // 국외근로비과세
		String[] oevrtime_amt = request.getParameterValues("oevrtime_amt"); // 연장근무(야간수당) 비과세
		String[] meternity_amt = request.getParameterValues("meternity_amt"); // 출산보육수당
		String[] research_amt = request.getParameterValues("research_amt"); // 연구보조비
		String[] school_expenses_amt = request.getParameterValues("school_expenses_amt"); // 학자금
		String[] collect_amt = request.getParameterValues("collect_amt"); // 취재수당
		String[] remote_rural_area_amt = request.getParameterValues("remote_rural_area_amt"); // 벽지수당
		String[] natural_disaster_amt = request.getParameterValues("natural_disaster_amt"); // 천재지변 재해로 받는 수당
		String[] stock_purchase_amt = request.getParameterValues("stock_purchase_amt"); // 주식매수선택권
		String[] foreigner_amt = request.getParameterValues("foreigner_amt"); // 외국인기술자소득세면제
		String[] employ_stock_amt1 = request.getParameterValues("employ_stock_amt1"); // 우리사주조합인출금50%
		String[] employ_stock_amt2 = request.getParameterValues("employ_stock_amt2"); // 우리사주조합인출금75%
		String[] guard_embark_amt = request.getParameterValues("guard_embark_amt"); // 경호수당, 승선수당
		String[] smiymjtc_amt = request.getParameterValues("smiymjtc_amt"); // 중소기업청년취업소득세감면
		String[] major_amt = request.getParameterValues("major_amt"); // 전공의수련보조수당
		String[] submarine_mineral_amt = request.getParameterValues("submarine_mineral_amt"); // 해저광물자원개발을위한과세특례
		String[] scholarship_amt = request.getParameterValues("scholarship_amt"); // 교육기본법제28조제1항에따라받는장학금
		String[] organization_amt = request.getParameterValues("organization_amt"); // 외국정부또는국제기관에근무하는사람에대한비과세
		String[] kindergarten_teacher_amt = request.getParameterValues("kindergarten_teacher_amt"); // 시립유치원수석교사의인건비유아교육법시행령
		String[] childcare_amt = request.getParameterValues("childcare_amt"); // 보육교사인건비영유아보육법시행령
		String[] teache_clause_amt = request.getParameterValues("teache_clause_amt"); // 조세조약상교직자조항의소득세감면
		String[] move_amt = request.getParameterValues("move_amt"); // 정부공공기관중지방이전기관종사자이수수당
		String[] legislation_amt = request.getParameterValues("legislation_amt"); // 법령조례에의한보수를받지않는의원수당
		String[] operation_amt = request.getParameterValues("operation_amt"); // 작전임무수행을위해외국에주둔하는군인받는급여
		String[] smiymjtc_rate = request.getParameterValues("smiymjtc_rate"); // 중소기업청년취업소득세감면 금액이 있는 경우 감면 비율
		
		paramMap.put("emp_no", emp_no);
		paramMap.put("year", year);
		paramMap.put("adjst_div", "0");
		paramMap.put("seq", seq);
		paramMap.put("corporate_nm", corporate_nm);
		paramMap.put("president", president);
		paramMap.put("corporate_no", corporate_no);
		paramMap.put("start_work_dt", start_work_dt);
		paramMap.put("end_work_dt", end_work_dt);
		paramMap.put("start_reduce_dt", start_reduce_dt);
		paramMap.put("end_reduce_dt", end_reduce_dt);
		paramMap.put("salary_dt", salary_dt);
		paramMap.put("salary_amt", salary_amt);
		paramMap.put("bonus_amt", bonus_amt);
		paramMap.put("constructive_bonus_amt", constructive_bonus_amt);
		paramMap.put("health_amt", health_amt);
		paramMap.put("employ_amt", employ_amt);
		paramMap.put("kuk_yeon_amt", kuk_yeon_amt);
		paramMap.put("annuity_amt", annuity_amt);
		paramMap.put("income_amt", income_amt);
		paramMap.put("jumin_amt", jumin_amt);
		paramMap.put("nong_amt", nong_amt);
		paramMap.put("total_salary", total_salary);
		paramMap.put("stock_option_amt", stock_option_amt);
		paramMap.put("employ_stock_amt", employ_stock_amt);
		paramMap.put("derector_retirement_amt", derector_retirement_amt);
		paramMap.put("total_free", total_free);
		paramMap.put("reduction_amt", reduction_amt);
		paramMap.put("foreign_work_amt", foreign_work_amt);
		paramMap.put("oevrtime_amt", oevrtime_amt);
		paramMap.put("meternity_amt", meternity_amt);
		paramMap.put("research_amt", research_amt);
		paramMap.put("school_expenses_amt", school_expenses_amt);
		paramMap.put("collect_amt", collect_amt);
		paramMap.put("remote_rural_area_amt", remote_rural_area_amt);
		paramMap.put("natural_disaster_amt", natural_disaster_amt);
		paramMap.put("stock_purchase_amt", stock_purchase_amt);
		paramMap.put("foreigner_amt", foreigner_amt);
		paramMap.put("employ_stock_amt1", employ_stock_amt1);
		paramMap.put("employ_stock_amt2", employ_stock_amt2);
		paramMap.put("guard_embark_amt", guard_embark_amt);
		paramMap.put("smiymjtc_amt", smiymjtc_amt);
		paramMap.put("major_amt", major_amt);
		paramMap.put("submarine_mineral_amt", submarine_mineral_amt);
		paramMap.put("scholarship_amt", scholarship_amt);
		paramMap.put("organization_amt", organization_amt);
		paramMap.put("kindergarten_teacher_amt", kindergarten_teacher_amt);
		paramMap.put("childcare_amt", childcare_amt);
		paramMap.put("teache_clause_amt", teache_clause_amt);
		paramMap.put("move_amt", move_amt);
		paramMap.put("legislation_amt", legislation_amt);
		paramMap.put("operation_amt", operation_amt);
		paramMap.put("smiymjtc_rate", smiymjtc_rate);
		
		String result = yearendtaxService.procYearendtaxPreviousWorkplace(paramMap); // 종전 근무지 삭제/등록
		
		/*결과 반환*/
		YearendtaxVO yearendtaxVO = new YearendtaxVO();
		
		yearendtaxVO.setResult(result);
		
		MarshallerUtil.marshalling("json", response, yearendtaxVO);
	}
}
