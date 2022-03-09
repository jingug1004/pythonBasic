/**
  * Hana Project
  * Copyright 2014 iRush Co.,
  *
  */
package com.hanaph.gw.co.menu.controller;

import java.io.IOException;
import java.io.PrintWriter;
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

import com.hanaph.gw.co.code.controller.CodeController;
import com.hanaph.gw.co.common.utils.CommonUtil;
import com.hanaph.gw.co.common.utils.Environment;
import com.hanaph.gw.co.common.utils.MenuUtil;
import com.hanaph.gw.co.common.utils.RequestFilterUtil;
import com.hanaph.gw.co.common.utils.StringUtil;
import com.hanaph.gw.co.menu.service.MenuService;
import com.hanaph.gw.co.menu.vo.MenuVO;
import com.hanaph.gw.pe.member.vo.MemberVO;

/**
 * <pre>
 * Class Name : MenuController.java
 * 설명 : 메뉴 관리 Controller
 * 
 * Modification Information
 *    수정일      수정자              수정 내용
 *  ------------ -------------- --------------------------------
 *  2014. 10. 28.      Jung.Jin.Muk          
 * </pre>
 * 
 * @version : 
 * @author  : Jung.Jin.Muk(pc123pc@irush.co.kr)
 * @since   : 2014. 10. 28.
 */
@Controller
public class MenuController {
	
	@Autowired
	private MenuService menuService;
	private Environment env = new Environment();
	private static final Logger logger = Logger.getLogger(CodeController.class);

	/**
	 * <pre>
	 * 1. 개요     :	메뉴 리스트 페이지
	 * 2. 처리내용 : 메뉴 리스트 페이지를 트리형식으로 보여준다.
	 * </pre>
	 * @Method Name : menuList
	 * @param request
	 * @return
	 */
	@RequestMapping("/co/menu/menuList.do")
	public ModelAndView menuList(HttpServletRequest request) {
		final String MENU_CHILD= "0601";
		ModelAndView mav = new ModelAndView("co/menu/menuList");
		
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("menu_child", "-1");	//최상위 메뉴 하드코딩
		
		List<MenuVO> menuList = menuService.getMenuList(paramMap);
		mav.addObject("menuList", menuList);
		
		/*LNB 메뉴 생성 START*/
		String menu_parents = StringUtil.substring(MENU_CHILD, 2);
		MenuUtil mu = new MenuUtil();
		List<MenuVO> lnbMenuList = mu.makeLnb(request, menu_parents , menuService);
		mav.addObject("lnbMenuList", lnbMenuList);
		mav.addObject("MENU_CHILD", MENU_CHILD);
		/*LNB 메뉴 생성 END*/
		
		return mav;
	}
	
	/**
	 * <pre>
	 * 1. 개요     : 아이프레임 상세메뉴정보 및 하위메뉴리스트 
	 * 2. 처리내용 : 아이프레임 상세메뉴정보 및 하위메뉴리스트를 보여준다.
	 * </pre>
	 * @Method Name : menuDetailSubListIframe
	 * @param request
	 * @return
	 */
	@RequestMapping("/co/menu/menuDetailSubListIframe.do")
	public ModelAndView menuListIframe(HttpServletRequest request){

		ModelAndView mav = new ModelAndView("co/menu/menuDetailSubListIframe");
		
		String menu_parents = StringUtil.nvl(request.getParameter("menu_parents"),"-1");
		String menu_child = StringUtil.nvl(request.getParameter("menu_child"),"00");
		
		/*최상위메뉴(그룹웨어) -1를 00코드로 바꿔준다.*/ 
		if("-1".equals(menu_child)){
			menu_parents = "00";
		}
		
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("menu_parents", menu_parents);
		paramMap.put("menu_child", menu_child);
		
		/*메뉴 상세정보를 가져온다.*/
		MenuVO menuDetail = menuService.getMenuDetail(paramMap);
		
		/*하위메뉴리스트를 가져온다.*/
		List<MenuVO> menuSubList = menuService.getMenuSubList(paramMap);
		
		mav.addObject("menuDetail",menuDetail);		//메뉴상세정보
		mav.addObject("menuSubList",menuSubList);	//하위메뉴리스트
		
		return mav;
	}
	
	/**
	 * <pre>
	 * 1. 개요     :	메뉴등록 
	 * 2. 처리내용 :	메뉴를 등록한다.
	 * </pre>
	 * @Method Name : insertMenu
	 * @param request
	 * @return
	 * @throws ModelAndViewDefiningException 
	 */
	@RequestMapping("/co/menu/insertMenu.do")
	public ModelAndView insertMenu(HttpServletRequest request) throws ModelAndViewDefiningException {
		
		boolean result = false;		//성공여부
		ModelAndView mav = new ModelAndView("");

		HttpSession session = request.getSession();
		MemberVO memberSessionVO = (MemberVO) session.getAttribute("gwUser");
		
		String status = StringUtil.nvl(request.getParameter("status")); 			//저장인지 수정인지 구분상태 0.저장 1.수정
		String menu_parents = StringUtil.nvl(request.getParameter("menu_parents")); //상위메뉴
		String menu_child = StringUtil.nvl(request.getParameter("menu_child"));		//하위메뉴
		String menu_nm = StringUtil.nvl(request.getParameter("menu_nm"));			//메뉴이름
		String use_yn = StringUtil.nvl(request.getParameter("use_yn"));				//사용여부
		String ordering = StringUtil.nvl(request.getParameter("ordering"));			//정렬순서
		String descr = StringUtil.nvl(request.getParameter("descr"));				//메뉴설명
		String url = StringUtil.nvl(request.getParameter("url"));					//메뉴url
		String create_no = StringUtil.nvl(memberSessionVO.getEmp_no());				//임직원번호
		
		/*특수문자 제거*/
		menu_parents = RequestFilterUtil.convertToSearchParameter(menu_parents);
		menu_child = RequestFilterUtil.convertToSearchParameter(menu_child);
		menu_nm = RequestFilterUtil.convertToSearchParameter(menu_nm);
		use_yn = RequestFilterUtil.convertToSearchParameter(use_yn);
		ordering = RequestFilterUtil.convertToSearchParameter(ordering);
		descr = RequestFilterUtil.convertToSearchParameter(descr);
		url = RequestFilterUtil.convertToSearchParameter(url);
		create_no = RequestFilterUtil.convertToSearchParameter(create_no);
		
		/*저장할 데이터 셋팅*/
		MenuVO paramMenuVO = new MenuVO();	
		paramMenuVO.setMenu_parents(menu_parents);
		paramMenuVO.setMenu_child(menu_child);
		paramMenuVO.setMenu_nm(menu_nm);
		paramMenuVO.setUse_yn(use_yn);
		paramMenuVO.setOrdering(ordering);
		paramMenuVO.setDescr(descr);
		paramMenuVO.setUrl(url);
		paramMenuVO.setCreate_no(create_no);
		paramMenuVO.setStatus(status);
		
		String returnUrl = "window.parent.location.href='"+env.getValue("root_dir.url")+"/co/menu/menuList.do?';";
		String str = "";
		
		if("0".equals(status)){ //저장
			str = "저장";
		}else if("1".equals(status)){	//수정
			paramMenuVO.setDelete_yn("N");
			str = "수정";
		}else if("2".equals(status)){	//삭제된메뉴 재등록
			paramMenuVO.setDelete_yn("N");
			str = "저장";
		}else if("3".equals(status)){	//하위 사용여부 일괄 N
			paramMenuVO.setDelete_yn("N");
			str = "수정";
		}
		
		result = menuService.insertMenu(paramMenuVO);
		
		if(result){
			logger.debug("*********** Menu " + str + " 성공 ***********");
			mav = CommonUtil.getMessageView(str + " 되었습니다.", returnUrl , "" );
			throw new ModelAndViewDefiningException(mav);
		}else{
			logger.debug("*********** Menu " + str + " 실패 ***********");
			mav = CommonUtil.getMessageView(str + " 실패!.", returnUrl , "" );
			throw new ModelAndViewDefiningException(mav);
		}
	}
	
	/**
	 * <pre>
	 * 1. 개요     : 메뉴 삭제
	 * 2. 처리내용 : 메뉴를 삭제 한다.
	 * </pre>
	 * @Method Name : deleteMenu
	 * @param request
	 * @return
	 * @throws ModelAndViewDefiningException
	 */
	@RequestMapping("/co/menu/deleteMenu.do")
	public ModelAndView deleteMenu(HttpServletRequest request) throws ModelAndViewDefiningException {
		
		boolean result = false;		//성공여부
		ModelAndView mav = new ModelAndView("");

		HttpSession session = request.getSession();
		MemberVO memberSessionVO = (MemberVO) session.getAttribute("gwUser");
		
		String menu_parents = StringUtil.nvl(request.getParameter("menu_parents")); //상위메뉴
		String menu_child = StringUtil.nvl(request.getParameter("menu_child"));		//하위메뉴
		
		MenuVO paramMenuVO = new MenuVO();	//저장할 데이터 셋팅
		paramMenuVO.setDelete_yn("Y");
		paramMenuVO.setModify_no(memberSessionVO.getEmp_no());
		paramMenuVO.setMenu_parents(menu_parents);
		paramMenuVO.setMenu_child(menu_child);
		
		result = menuService.deleteMenu(paramMenuVO);
		
		String returnUrl = "window.parent.location.href='"+env.getValue("root_dir.url")+"/co/menu/menuList.do?';";
		
		if(result){
			logger.debug("*********** Menu 삭제 성공 ***********");
			mav = CommonUtil.getMessageView("삭제 되었습니다.", returnUrl , "" );
			throw new ModelAndViewDefiningException(mav);
		}else{
			logger.debug("*********** Menu 삭제 실패 ***********");
			mav = CommonUtil.getMessageView("삭제 실패!.", returnUrl , "" );
			throw new ModelAndViewDefiningException(mav);
		}
	}
	
	/**
	 * <pre>
	 * 1. 개요     : 메뉴번호 중복체크
	 * 2. 처리내용 : 메뉴번호 중복체크를 한다.
	 * </pre>
	 * @Method Name : menuCheck
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/co/menu/menuCheck.do")
	public void menuCheck(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		logger.debug("================== Menu Check Start ==================");
		
		String result = "";		//사용가능여부
		
		String menu_parents = StringUtil.nvl(request.getParameter("menu_parents"));	//상위메뉴
		String menu_child = StringUtil.nvl(request.getParameter("menu_child"));		//하위메뉴
		
		/*특수문자 제거*/
		menu_parents = RequestFilterUtil.convertToSearchParameter(menu_parents);
		menu_child = RequestFilterUtil.convertToSearchParameter(menu_child);
		
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("menu_parents", menu_parents);
		paramMap.put("menu_child", menu_child);
		
		MenuVO menuVO = menuService.menuCheck(paramMap);

		response.setContentType("text/html;charset=UTF-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter out = response.getWriter();
		
		if(menuVO == null){
		    result = "1";
		    out.println(result);
		    logger.debug("*********** 사용 가능Menu입니다. ***********");
		}else if("Y".equals(menuVO.getDelete_yn())){
		    result = "2";
		    out.println(result);
			logger.debug("*********** 삭제된 메뉴Menu 재사용 합니다. ***********");
		}else{
			logger.debug("*********** 사용 할 수 없는Menu입니다. ***********");
		}
		out.close();
		logger.debug("================== Menu Check End ==================");
	}
	
	
}

