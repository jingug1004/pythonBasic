/**
  * Hana Project
  * Copyright 2014 iRush Co.,
  *
  */
package com.hanaph.gw.pe.member.controller;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Calendar;
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

import com.hanaph.gw.co.code.service.CodeService;
import com.hanaph.gw.co.code.vo.CodeVO;
import com.hanaph.gw.co.common.utils.CommonUtil;
import com.hanaph.gw.co.common.utils.Environment;
import com.hanaph.gw.co.common.utils.MarshallerUtil;
import com.hanaph.gw.co.common.utils.MenuUtil;
import com.hanaph.gw.co.common.utils.PageUtil;
import com.hanaph.gw.co.common.utils.StringUtil;
import com.hanaph.gw.co.menu.service.MenuService;
import com.hanaph.gw.co.menu.vo.MenuVO;
import com.hanaph.gw.pe.member.service.AnnualService;
import com.hanaph.gw.pe.member.vo.AnnualHRVO;
import com.hanaph.gw.pe.member.vo.AnnualMgrVO;
import com.hanaph.gw.pe.member.vo.AnnualVO;
import com.hanaph.gw.pe.member.vo.MemberVO;

/**
 * <pre>
 * Class Name : AnnualController.java
 * 설명 : 연차사용내역 정보 Controller
 * 
 * Modification Information
 *    수정일      수정자              수정 내용
 *  ------------ -------------- --------------------------------
 *  2014. 10. 30.      CHOIILJI         
 * </pre>
 * 
 * @version : 
 * @author  : CHOIILJI(choiilji@irush.co.kr)
 * @since   : 2014. 10. 30.
 */
@Controller
public class AnnualController {
	private Environment env = new Environment();
	
	@Autowired
	private AnnualService annualService;
	
	@Autowired
	private MenuService menuService;
	
	@Autowired
	private CodeService codeService;
	
	/**
	 * 
	 * <pre>
	 * 1. 개요     : 연차사용내역 리스트
	 * 2. 처리내용 : 연차사용내역 리스트 가져온다.
	 * </pre>
	 * @Method Name : salaryList
	 * @param request
	 * @return
	 */
	@RequestMapping("/pe/member/annualList.do")
	public ModelAndView annualList(HttpServletRequest request){
		
		final String MENU_CHILD= "0403";
		
		ModelAndView mav = new ModelAndView("pe/member/annualList");
		Map<String, String> paramMap = new HashMap<String, String>();  
		
		Calendar cal =  Calendar.getInstance();
		String year = String.valueOf(cal.get(Calendar.YEAR));
		String month = String.format("%02d", cal.get(Calendar.MONTH)+1);
		String day = String.format("%02d", cal.get(Calendar.DATE));
		
		String search_start_dt = StringUtil.nvl((String)request.getParameter("search_start_dt"), year+"-01-01");
		String search_end_dt = StringUtil.nvl((String)request.getParameter("search_end_dt"), year+"-"+month+"-"+day);
		
		int currentPage = Integer.parseInt(StringUtil.nvl(request.getParameter("currentPage"),"1"));	// 현재 페이지 번호
		int plRowRange = 10;	//한페이지당 보여줄 게시물 수 	
		int plPageRange = 10;	//페이지 번호 수
		
		//세션에서 회원정보 가져 온다.
		HttpSession session = request.getSession(false);
		MemberVO memberSessionVO = (MemberVO) session.getAttribute("gwUser");
		String emp_no = memberSessionVO.getEmp_no();
		
		paramMap.put("emp_no", emp_no);
		paramMap.put("search_start_dt", search_start_dt.replaceAll("-", ""));
		paramMap.put("search_end_dt", search_end_dt.replaceAll("-", ""));
		paramMap.put("page",String.valueOf(currentPage));		
		paramMap.put("perPageRow", String.valueOf(plRowRange));
		List<AnnualVO> annualList = annualService.getAnnualList(paramMap);
		//전체 카운트
		int annualTotalCount = annualService.getAnnualCount(paramMap);
		//총 사용일
		float annualUsedCount = annualService.getAnnualUsedCount(paramMap);
		
		//페이징
		PageUtil pu = new PageUtil();
		String pagingStr = pu.autoPaging(annualTotalCount, plRowRange, plPageRange, currentPage);
		
		//LNB 메뉴 생성
		String menu_parents = StringUtil.substring(MENU_CHILD, 2);
		MenuUtil mu = new MenuUtil();
		List<MenuVO> lnbMenuList = mu.makeLnb(request, menu_parents , menuService);
		mav.addObject("lnbMenuList", lnbMenuList);
		mav.addObject("MENU_CHILD", MENU_CHILD);
				
		mav.addObject("annualList", annualList);
		mav.addObject("search_start_dt", search_start_dt);
		mav.addObject("search_end_dt", search_end_dt);
		mav.addObject("currentPage", currentPage);
		mav.addObject("pagingStr", pagingStr);
		mav.addObject("annualTotalCount", annualTotalCount);
		mav.addObject("annualUsedCount", annualUsedCount);
				
		return mav;
	}
	
	/**
	 * 
	 * <pre>
	 * 1. 개요     : 연차 사용계획서 
	 * 2. 처리내용 : 연차 사용계획서 달력보여준다.
	 * </pre>
	 * @Method Name : annualList
	 * @param request
	 * @return
	 */
	@RequestMapping("/pe/member/annualPlanList.do")
	public ModelAndView annualPlanList(HttpServletRequest request){
		
		final String MENU_CHILD= "0402";
		
		ModelAndView mav = new ModelAndView("pe/member/annualPlanList");
		Map<String, String> paramMap = new HashMap<String, String>();  
		
		Calendar cal =  Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH)+1;
		int day = cal.get(Calendar.DATE);
		
		int startMonth = 1;
		if( month>=1 && month <=6){
			startMonth = 1;
		}else{
			startMonth = 7;
		}
		int date = 0;

		//세션에서 회원정보 가져 온다.
		HttpSession session = request.getSession(false);
		MemberVO memberSessionVO = (MemberVO) session.getAttribute("gwUser");
		String emp_no = memberSessionVO.getEmp_no();
		
		//개인 연차 정보 가져온다.
		paramMap.put("emp_no", emp_no);
		paramMap.put("yr_year", Integer.toString(year));
		/*CHOE 2017.01.16 HANAHR.F_GET_WORK_DAYS_YMD 사용*/
		DecimalFormat df  = new DecimalFormat("00");
		//paramMap.put("yr_mmdd", Integer.toString(cal.get(Calendar.MONTH)+cal.get(Calendar.DATE)));
		//CHOE 20170629 1에서 변경
		paramMap.put("yr_mmdd", df.format(cal.get(Calendar.MONTH) + 1) + df.format(cal.get(Calendar.DATE)));						
		//System.out.println("--- annualPlanList mm : "+ df.format(cal.get(Calendar.MONTH) + 2));
		
		AnnualMgrVO annualPlan = annualService.getAnnualPlan(paramMap);
		if(annualPlan == null){
			annualPlan = new AnnualMgrVO();
		}
		
		//공통, 휴일, 개인등록한 연차 정보 가져온다.
		paramMap.put("rq_year", Integer.toString(year));
		List<AnnualVO> annualCommonDTList =  annualService.getAnnualCommonDT(paramMap);
		
		//공동연차 카운트 
		int annualcommonDtCnt = annualService.getAnnualCommonDTCount(paramMap);//전체 연차 표기  
		int annualcommonDtCntHalf = annualService.getAnnualCommonDTCountHalf(paramMap);//연차사용계획 상반기만 표기
		//달력
		StringBuffer annualPlanList = new StringBuffer();
		
		// 연속된 월을 출력하기위한 반복문 및 변수 k
		for (int k = 0; k <= 5; k++) {
			if( k%2==0){
				annualPlanList.append("<div class=\"group_calendar\">\n");
				annualPlanList.append("	<div class=\"float_l\">\n");
			}else{
				annualPlanList.append("	<div class=\"float_r\">\n");
			}	
			

			// 입력된 년도와 같은 년도에서 연속된 월을 출력하기 위한 부분
			// 나머지 부분의 주석은 위와 동일
			
			
			
			annualPlanList.append("		<h4 class=\"month\">" + (startMonth + k) + "월 "+"</h4>\n");

			annualPlanList.append("		<table>\n");
			annualPlanList.append("			<colgroup>\n");
			annualPlanList.append("				<col style=\"width:70px\"/>\n");
			annualPlanList.append("				<col style=\"width:70px\"/>\n");
			annualPlanList.append("				<col style=\"width:70px\"/>\n");
			annualPlanList.append("				<col style=\"width:70px\"/>\n");
			annualPlanList.append("				<col style=\"width:70px\"/>\n");
			annualPlanList.append("				<col style=\"width:70px\"/>\n");
			annualPlanList.append("				<col style=\"width:70px\"/>\n");
			annualPlanList.append("			</colgroup>\n");
			annualPlanList.append("			<thead>\n");
			annualPlanList.append("				<tr>\n");
			annualPlanList.append("					<th class=\"fc_r\">일요일</th>\n");
			annualPlanList.append("					<th>월요일</th>\n");
			annualPlanList.append("					<th>화요일</th>\n");
			annualPlanList.append("					<th>수요일</th>\n");
			annualPlanList.append("					<th>목요일</th>\n");
			annualPlanList.append("					<th>금요일</th>\n");
			annualPlanList.append("					<th class=\"fc_b\">토요일</th>\n");
			annualPlanList.append("				</tr>\n");
			annualPlanList.append("			</thead>\n");
			annualPlanList.append("			<tbody>\n");
			annualPlanList.append("				<tr>\n");

			// 출력될 년도까지의 총 일수를 구함(윤년인 년도 포함하여 고려한 식)
			date = (year - 1) * 365 + (year - 1) / 4 - (year - 1) / 100 + (year - 1) / 400;

			// 윤년일 경우 2월을 29일로 설정하기위해 만든 부분
			// 배열을 이용하여 각 월의 일수를 저장해둠
			int leafyear = 28;
			if ((year % 4 == 0 && year / 100 != 0) || (year % 400 == 0)){
				leafyear = 29;
			}	
			int[] mon = { 31, leafyear, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
			// 출력될 월의 전월까지의 총 일수를 구함
			for (int i = 0; i < startMonth + (k - 1); i++){
				date += mon[i];
			}		
			
			// 마지막 날의 요일을 구하는 부분
			// 총 일수를 7로남은 나머지만큼이 전월 마지막주의 일수
			// 마지막주의 일수만큼 공백(\t) 생성
			// 단, 마지막주의 일수가 6인경우 아무것도 입력하지 않음
			int lastday = 0;
			lastday = (date) % 7;
			if ((lastday == 6)){
				annualPlanList.append("");
			}else{
				for (int i = 0; i <= lastday; i++){
					annualPlanList.append("					<td>&nbsp;</td>\n");
				}
			}	

			// 해당 월의 일수를 배열로부터 가져온 후 출력
			// 총 일수 = date + i
			// 총 일수를 7로 나눌 경우 나머지가 0이면 일요일, 6이면 토요일
			for (int i = 1; i <= mon[startMonth + k - 1]; i++) {
				if ((date + i) % 7 == 6){
					annualPlanList.append("					<td class=\"fc_b\">"+i+"</td></tr>\n");
				} else if ((date + i) % 7 == 0){
					annualPlanList.append("				<tr><td class=\"fc_r\">"+i+"</td>\n");
				} else{
					//공동연차, 휴일인 경우 공동연차 표시한다.
					String commonDT = "";
					boolean gubn = false;
					boolean commonDTHoli = false;
					if(annualCommonDTList.size() > 0){
						for (int j = 0; j < annualCommonDTList.size(); j++) {
							AnnualVO annualVO = annualCommonDTList.get(j);
							int commonMonth = Integer.parseInt(annualVO.getRq_fr_dt().substring(4, 6));
							
							if(commonMonth == (startMonth + k)){
								int commonFrDay = Integer.parseInt(annualVO.getRq_fr_dt().substring(6, 8));
								int commonToDay = Integer.parseInt(annualVO.getRq_to_dt().substring(6, 8));
								if(commonFrDay <= i && commonToDay >= i){
									commonDT = annualVO.getRq_remark();
									if(commonDT.length() > 4){
										commonDT = commonDT.substring(0,4);
									}
									if("75030".equals(annualVO.getRq_vacat_cd())){
										commonDTHoli = true;
									}
									if("3".equals(annualVO.getGubun())){
										gubn = true;
									}
								}
							}
						}
					}
					if(!commonDT.equals("") && commonDT != null){
						if(commonDTHoli){
							annualPlanList.append("					<td class=\"fc_r\">"+i+"<span class=\"fc_r\">"+commonDT+"</span></td>\n");
						}else{
							if(gubn){
								annualPlanList.append("					<td onclick='javascript:goDeleteSelectDay("+year+""+String.format("%02d", (startMonth + k))+""+String.format("%02d", i)+");'>"+i+"<span class=\"blk\">"+commonDT+"</span></td>\n");
							}else{
								annualPlanList.append("					<td>"+i+"<span class=\"blk\">"+commonDT+"</span></td>\n");
							}
						}
					}else{
						annualPlanList.append("					<td onclick='javascript:goSelectDay("+year+""+String.format("%02d", (startMonth + k))+""+String.format("%02d", i)+");'>"+i+"</td>\n");
					}
				}
				
				//마지막 주 남은 일자 
				if(mon[startMonth + k - 1] == i){
					for (int j = 0; j < 6 - ((date + i) % 7); j++){
						annualPlanList.append("					<td>&nbsp;</td>\n");
					}
				}
					
			}
			annualPlanList.append("				</tr>\n");
			annualPlanList.append("			</tbody>\n");
			annualPlanList.append("		</table>\n");
			
			annualPlanList.append("	</div>\n");		
			if( k % 2==1){
			annualPlanList.append("</div>\n");
			}
		}

		
		//LNB 메뉴 생성
		String menu_parents = StringUtil.substring(MENU_CHILD, 2);
		MenuUtil mu = new MenuUtil();
		List<MenuVO> lnbMenuList = mu.makeLnb(request, menu_parents , menuService);
		mav.addObject("lnbMenuList", lnbMenuList);
		mav.addObject("MENU_CHILD", MENU_CHILD);
		
		//연차사용계획 마감여부체크
		//boolean annualClosedYN = annualService.getAnnualClosedYN(paramMap);		
		boolean annualClosedYN = true;
		mav.addObject("annualPlan", annualPlan);
		mav.addObject("year", year);
		mav.addObject("month", month);
		mav.addObject("day", day);
		mav.addObject("annualPlanList", annualPlanList);
		mav.addObject("annualcommonDtCnt", annualcommonDtCnt); //연차사용계획 전체 표기
		mav.addObject("annualcommonDtCntHalf", annualcommonDtCntHalf); //연차사용계획 상반기만 표기
		mav.addObject("annualClosedYN", annualClosedYN);
		mav.addObject("startMonth", startMonth);
		return mav;
	}
	
	/**
	 * 
	 * <pre>
	 * 1. 개요     : 연차지정통보서 
	 * 2. 처리내용 : 미사용 연차유급휴가 사용시기 지정통보서
	 * </pre>
	 * @Method Name : annualPlanNotify
	 * @param request
	 * @return
	 */
	@RequestMapping("/pe/member/annualPlanNotify.do")
	public ModelAndView annualPlanNotify(HttpServletRequest request){
		
		final String MENU_CHILD= "0404";
		
		ModelAndView mav = new ModelAndView("pe/member/annualPlanNotify");
		Map<String, String> paramMap = new HashMap<String, String>();  
		
		Calendar cal =  Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH)+1;
		int day = cal.get(Calendar.DATE);
		
		//세션에서 회원정보 가져 온다.
		HttpSession session = request.getSession(false);
		MemberVO memberSessionVO = (MemberVO) session.getAttribute("gwUser");
		String emp_no = memberSessionVO.getEmp_no();
		
		paramMap.put("emp_no", emp_no);
		paramMap.put("rq_fr_dt", Integer.toString(year));
		AnnualMgrVO annualPlanNotify = annualService.getAnnualPlanNotify(paramMap);
		if(annualPlanNotify == null){
			annualPlanNotify = new AnnualMgrVO();
		}
		System.out.println("---------- AnnualController.java annualPlanNotify November : "+StringUtil.nvl(annualPlanNotify.getNovember()));
		//LNB 메뉴 생성
		String menu_parents = StringUtil.substring(MENU_CHILD, 2);
		MenuUtil mu = new MenuUtil();
		List<MenuVO> lnbMenuList = mu.makeLnb(request, menu_parents , menuService);
		mav.addObject("lnbMenuList", lnbMenuList);
		mav.addObject("MENU_CHILD", MENU_CHILD);
		
		mav.addObject("annualPlanNotify", annualPlanNotify);
		mav.addObject("year", year);
		mav.addObject("month", month);
		mav.addObject("day", day);
		
		return mav;
	}
	
	/**
	 * 
	 * <pre>
	 * 1. 개요     : 연차사용계획 등록 폼.
	 * 2. 처리내용 : 연차사용계획 등록 폼.
	 * </pre>
	 * @Method Name : annualPlanFormPopup
	 * @param request
	 * @return
	 */
	@RequestMapping("/pe/member/annualPlanFormPopup.do")
	public ModelAndView annualPlanFormPopup(HttpServletRequest request){
		
		ModelAndView mav = new ModelAndView("pe/member/annualPlanFormPopup");
		
		String rq_fr_dt = StringUtil.nvl(request.getParameter("rq_fr_dt"));
		String startMonth = StringUtil.nvl(request.getParameter("startMonth"));
		
		/*휴가종류 parameter*/
		Map<String, String> parmaMap = new HashMap<String, String>();
		parmaMap.put("m_cd", "E06");
		parmaMap.put("use_yn", "Y");
		List<CodeVO> sCodeList = codeService.getCodeList(parmaMap);
		
		mav.addObject("rq_fr_dt", rq_fr_dt);
		mav.addObject("startMonth", startMonth);
		mav.addObject("sCodeList", sCodeList);
		return mav;
	}
	
	/**
	 * 
	 * <pre>
	 * 1. 개요     : 연차사용계획 저장
	 * 2. 처리내용 : 연차사용계획 저장한다.
	 * </pre>
	 * @Method Name : annualPlanInsert
	 * @param request
	 * @return
	 * @throws IOException 
	 * @throws ModelAndViewDefiningException 
	 */
	@RequestMapping("/pe/member/annualPlanInsert.do")
	public void annualPlanInsert(HttpServletRequest request, HttpServletResponse response) throws IOException, ModelAndViewDefiningException{
		
		HttpSession session = request.getSession();
		MemberVO memberSessionVO = (MemberVO) session.getAttribute("gwUser");
		String emp_no = memberSessionVO.getEmp_no();
		String rq_fr_dt = StringUtil.nvl((String)request.getParameter("rq_fr_dt"));
		String remark = StringUtil.nvl((String)request.getParameter("remark"));
		String vacat_cd = StringUtil.nvl((String)request.getParameter("vacat_cd"));
		String startMonth = StringUtil.nvl((String)request.getParameter("startMonth"));

		if(!startMonth.equals("1")){
			startMonth = "2";
		}
		
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
		
		AnnualHRVO annualHRVO = new AnnualHRVO();
		annualHRVO.setEmp_no(emp_no);
		annualHRVO.setApprv_date(rq_fr_dt);
		annualHRVO.setHalf_trem(startMonth);
		annualHRVO.setVacat_cd(vacat_cd.replace("E06", "42"));
		annualHRVO.setRemark(remark);
		annualHRVO.setFirst_emp_no(emp_no);
		annualHRVO.setLast_emp_no(emp_no);
		annualHRVO.setLast_ip(ip);
		
		boolean bResult = false;
		
		//연차사용계획 마감여부체크
		ModelAndView mav = new ModelAndView("");
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("emp_no", emp_no);
		paramMap.put("yr_year", rq_fr_dt.substring(0,4));
		//boolean annualClosedYN = annualService.getAnnualClosedYN(paramMap);
		boolean annualClosedYN = true;
		if(annualClosedYN){
			mav = CommonUtil.getMessageView("등록기간이 아닙니다.", "parent.location.reload();parent.layerClose();","");
			throw new ModelAndViewDefiningException(mav);
		}else{
			bResult = annualService.insertAnnualPlan(annualHRVO);
		}

		if(bResult){
			String script = "<script>alert('저장되었습니다.'); parent.location.reload(); parent.layerClose();</script>";
			MarshallerUtil.marshalling("txt", response, script);
		}else{
			String script = "<script>alert('요청 처리 과정에서 에러가 발생하였습니다. 잠시 후 다시 시도하시거나 전산팀에 문의 바랍니다.'); parent.location.reload(); parent.layerClose();</script>";
			MarshallerUtil.marshalling("txt", response, script);
		}
	}
	
	/**
	 * 
	 * <pre>
	 * 1. 개요     : 연차사용계획 삭제
	 * 2. 처리내용 : 연차사용계획 삭제한다.
	 * </pre>
	 * @Method Name : annualPlanDelete
	 * @param request
	 * @return
	 * @throws IOException 
	 * @throws ModelAndViewDefiningException 
	 */
	@RequestMapping("/pe/member/annualPlanDelete.do")
	public void annualPlanDelete(HttpServletRequest request, HttpServletResponse response) throws IOException, ModelAndViewDefiningException{
		
		Map<String, String> paramMap = new HashMap<String, String>();
		
		HttpSession session = request.getSession();
		MemberVO memberSessionVO = (MemberVO) session.getAttribute("gwUser");
		String emp_no = memberSessionVO.getEmp_no();
		String rq_fr_dt = StringUtil.nvl((String)request.getParameter("rq_fr_dt"));
		
		AnnualHRVO annualHRVO = new AnnualHRVO();
		annualHRVO.setEmp_no(emp_no);
		annualHRVO.setApprv_date(rq_fr_dt);

		boolean bResult = false;

		//연차사용계획 마감여부체크
		ModelAndView mav = new ModelAndView("");
		paramMap.put("returnURL", env.getValue("root_dir.url")+"/pe/member/annualPlanList.do");
		paramMap.put("emp_no", emp_no);
		paramMap.put("yr_year", rq_fr_dt.substring(0,4));
		//boolean annualClosedYN = annualService.getAnnualClosedYN(paramMap);
		boolean annualClosedYN = true;
		if(annualClosedYN){
			String url = "window.location.href='"+env.getValue("root_dir.url")+"/pe/member/annualPlanList.do';";
			mav = CommonUtil.getMessageView("등록기간이 아닙니다.", url , "" );
			throw new ModelAndViewDefiningException(mav);
		}else{
			bResult = annualService.deleteAnnualPlan(annualHRVO);
		}
		
		if(bResult){
			MarshallerUtil.marshalling("txt", response, CommonUtil.postMessageView("삭제되었습니다.", paramMap));
		}else{
			MarshallerUtil.marshalling("txt", response, CommonUtil.postMessageView("요청 처리 과정에서 에러가 발생하였습니다. 잠시 후 다시 시도하시거나 전산팀에 문의 바랍니다.", paramMap));
		}
	}
}

