/**
  * Hana Project
  * Copyright 2014 iRush Co.,
  *
  */
package com.hanaph.gw.co.authority.controller;

import java.io.IOException;
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

import com.hanaph.gw.co.authority.service.AuthorityService;
import com.hanaph.gw.co.authority.vo.AuthorityVO;
import com.hanaph.gw.co.common.utils.CommonUtil;
import com.hanaph.gw.co.common.utils.Environment;
import com.hanaph.gw.co.common.utils.MarshallerUtil;
import com.hanaph.gw.co.common.utils.MenuUtil;
import com.hanaph.gw.co.common.utils.PageUtil;
import com.hanaph.gw.co.common.utils.RequestFilterUtil;
import com.hanaph.gw.co.common.utils.StringUtil;
import com.hanaph.gw.co.menu.service.MenuService;
import com.hanaph.gw.co.menu.vo.MenuVO;
import com.hanaph.gw.co.personnel.service.DepartmentService;
import com.hanaph.gw.co.personnel.vo.DepartmentVO;
import com.hanaph.gw.pe.member.service.MemberService;
import com.hanaph.gw.pe.member.vo.MemberVO;

/**
 * <pre>
 * Class Name : AuthorityController.java
 * 설명 : 권한 관리 Controller
 * 
 * Modification Information
 *    수정일      수정자              수정 내용
 *  ------------ -------------- --------------------------------
 *  2014. 10. 27.      Jung.Jin.Muk          
 * </pre>
 * 
 * @version : 
 * @author  : Jung.Jin.Muk(pc123pc@irush.co.kr)
 * @since   : 2014. 10. 27.
 */
@Controller
public class AuthorityController {
	
	@Autowired
	private AuthorityService authorityService;
	
	@Autowired
	private DepartmentService departmentService;
	
	@Autowired
	private MenuService menuService;
	
	@Autowired
	private MemberService memberService;
	
	private Environment env = new Environment();
	
	private static final Logger logger = Logger.getLogger(AuthorityController.class);

	/**
	 * <pre>
	 * 1. 개요     : 권한 리스트 
	 * 2. 처리내용 : 권한 리스트를 가져온다.
	 * </pre>
	 * @Method Name : authorityList
	 * @param request
	 * @return
	 */
	@RequestMapping("/co/authority/authorityList.do")
	public ModelAndView authorityList(HttpServletRequest request){
		
		final String MENU_CHILD= "0603";
		
		ModelAndView mav = new ModelAndView("co/authority/authorityList");
		
		int currentPage = Integer.parseInt(StringUtil.nvl(request.getParameter("currentPage"),"1"));	// 현재 페이지 번호
		int plRowRange = 10;	
		int plPageRange = 10;
		
		String searchType = StringUtil.nvl(request.getParameter("searchType"));
		String searchKeyword = StringUtil.nvl(request.getParameter("searchKeyword"));
		
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("searchKeyword", searchKeyword);			//검색어
		paramMap.put("searchType", searchType);					//검색타입
		paramMap.put("delete_yn", "N");							//삭제여부
		paramMap.put("page",String.valueOf(currentPage));		//현재페이지번호
		paramMap.put("perPageRow", String.valueOf(plRowRange));	//list에 보여질 row갯수
		
		List<AuthorityVO> authorityList = authorityService.getAuthorityList(paramMap);
		int cnt = authorityService.getAuthorityCount(paramMap);
		/*페이징*/
		PageUtil pu = new PageUtil();
		String pagingStr = pu.autoPaging(cnt, plRowRange, plPageRange, currentPage);

		/*LNB 메뉴 생성 START*/
		String menu_parents = StringUtil.substring(MENU_CHILD, 2);
		MenuUtil mu = new MenuUtil();
		List<MenuVO> lnbMenuList = mu.makeLnb(request, menu_parents , menuService);
		mav.addObject("lnbMenuList", lnbMenuList);
		mav.addObject("MENU_CHILD", MENU_CHILD);
		/*LNB 메뉴 생성 END*/
		
		mav.addObject("searchType", searchType);		//검색타입
		mav.addObject("searchKeyword", searchKeyword);	//검색어
		mav.addObject("authorityList",authorityList);	//권한리스트
		mav.addObject("pagingStr", pagingStr);			//페이지번호
		mav.addObject("cnt", cnt);						//카운트

		
		return mav;
		
	}

	/**
	 * <pre>
	 * 1. 개요     : 권한 등록 
	 * 2. 처리내용 : 권한 등록을 한다.
	 * </pre>
	 * @Method Name : insertAuthority
	 * @param request
	 * @return
	 * @throws ModelAndViewDefiningException
	 */
	@RequestMapping("/co/authority/insertAuthority.do")
	public ModelAndView insertAuthority(HttpServletRequest request) throws ModelAndViewDefiningException{
		
		
		ModelAndView mav = new ModelAndView();
		HttpSession session = request.getSession();
		MemberVO memberSessionVO = (MemberVO) session.getAttribute("gwUser");
		
		String auth_nm = StringUtil.nvl(request.getParameter("auth_nm"));		//권한이름
		String menu_cd = StringUtil.nvl(request.getParameter("menu_cd"));		//메뉴코드
		String auth_seq  = StringUtil.nvl(request.getParameter("auth_seq"));	//권한시퀀스
		String descr = StringUtil.nvl(request.getParameter("descr"));			//권한설명
		String emp_no = StringUtil.nvl(request.getParameter("emp_no"));			//사원번호
		String status = StringUtil.nvl(request.getParameter("status"));			//상태
		
		auth_nm = RequestFilterUtil.convertToSearchParameter(auth_nm);		
		descr = RequestFilterUtil.convertToSearchParameter(descr);		

		AuthorityVO authVO = new AuthorityVO();
		authVO.setAuth_nm(auth_nm);
		authVO.setMenu_cd(menu_cd);
		authVO.setAuth_seq(auth_seq);
		authVO.setDescr(descr);
		authVO.setEmp_no(emp_no);
		authVO.setCreate_no(memberSessionVO.getEmp_no());
		authVO.setStatus(status);
		
		/*1.권한 등록 , 2. 선택한 해당 권한 메뉴 등록 3. 권한 임직원 맵핑*/
		if("1".equals(authVO.getStatus())){
			authorityService.insertAuthority(authVO);
		}else if("2".equals(authVO.getStatus())){
			authorityService.insertAuthorityMenu(authVO);
		}else{
			authorityService.insertAuthorityEmpNo(authVO);
		}
		
		String url = "window.parent.location.href='"+env.getValue("root_dir.url")+"/co/authority/authorityList.do';";
		mav = CommonUtil.getMessageView("저장 되었습니다.", url , "" );
		throw new ModelAndViewDefiningException(mav);
		
	}
	
	/**
	 * <pre>
	 * 1. 개요     : 메뉴 리스트
	 * 2. 처리내용 : 메뉴선택 팝업에서 메뉴리스트를 가져온다.
	 * </pre>
	 * @Method Name : menuList
	 * @param request
	 * @return
	 */
	@RequestMapping("/co/authority/authorityMenuListPopup.do")
	public ModelAndView menuList(HttpServletRequest request){
		
		ModelAndView mav = new ModelAndView("co/authority/authorityMenuListPopup");
		
		String auth_seq = StringUtil.nvl(request.getParameter("auth_seq"));
		
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("menu_child", "-1");	//최상위 메뉴 하드코딩
		paramMap.put("menu_child", "00");	//1depth(대메뉴) 하드코딩
		paramMap.put("auth_seq", auth_seq);	//권한목록 시퀀스번호
		
		AuthorityVO  authorityDetail = null;
		List<AuthorityVO> menuCodeList = null;
		
		/*권한 등록 일때는 권한상세정보를 안불러 온다.*/
		if(!"".equals(auth_seq)){
			authorityDetail = authorityService.getAuthorityDetail(paramMap);
			menuCodeList = authorityService.getMenuCodeList(paramMap);
		}
		List<MenuVO> menuList = authorityService.getAuthorityMenuList(paramMap);
		List<MenuVO> authorityMenuRow = authorityService.getAuthorityMenuRow(paramMap);
		
		mav.addObject("authorityDetail", authorityDetail);		//권한상세정보
		mav.addObject("menuList", menuList);					//메뉴리스트
		mav.addObject("authorityMenuRow", authorityMenuRow);	//권한메뉴리스트html rowspan값
		mav.addObject("menuCodeList", menuCodeList);			//메뉴코드리스트
		mav.addObject("auth_seq", auth_seq);					//메뉴시퀀스
		
		return mav;
		
	}
	
	/**
	 * <pre>
	 * 1. 개요     : 임직원 리스트
	 * 2. 처리내용 : 직원 리스트를 가져온다. 
	 * </pre>
	 * @Method Name : personnelList
	 * @param request
	 * @return
	 */
	@RequestMapping("/co/authority/authorityDepartListPopup.do")
	public ModelAndView authorityDepartListPopup(HttpServletRequest request){
		
		ModelAndView mav = new ModelAndView("co/authority/authorityDepartListPopup");
		
        return mav;
	}
	
	/**
	 * <pre>
	 * 1. 개요     : 조직 리스트
	 * 2. 처리내용 : 조직 리스트를 가져온다.
	 * </pre>
	 * @Method Name : authorityDepartListPopupIframe
	 * @param request
	 * @return
	 */
	@RequestMapping("/co/authority/authorityDepartListPopupIframe.do")
	public ModelAndView authorityDepartListPopupIframe(HttpServletRequest request){

		ModelAndView mav = new ModelAndView("co/authority/authorityDepartListPopupIframe");
		Map<String, String> paramMap = new HashMap<String, String>();  
		
		List<DepartmentVO> departmentList = departmentService.getDepartmentList(paramMap);
		
		mav.addObject("departmentList", departmentList);	//조직리스트
		
        return mav;
        
	}
	
	/**
	 * <pre>
	 * 1. 개요     : 권한 삭제 
	 * 2. 처리내용 : 권한 삭제를 한다.
	 * </pre>
	 * @Method Name : deleteAuthority
	 * @param request
	 * @return
	 * @throws ModelAndViewDefiningException
	 */
	@RequestMapping("/co/authority/deleteAuthority.do")
	public ModelAndView deleteAuthority(HttpServletRequest request) throws ModelAndViewDefiningException{
		boolean result = false;
		
		ModelAndView mav = new ModelAndView();
		HttpSession session = request.getSession();
		MemberVO memberSessionVO = (MemberVO) session.getAttribute("gwUser");
		
		String auth_seq = StringUtil.nvl(request.getParameter("auth_seq"));	//권한시퀀스
		auth_seq = RequestFilterUtil.convertToSearchParameter(auth_seq);		

		AuthorityVO authVO = new AuthorityVO();
		authVO.setAuth_seq(auth_seq);
		authVO.setModify_no(memberSessionVO.getEmp_no());
		
		result = authorityService.deleteAuthority(authVO);
		String url = "window.location.href='"+env.getValue("root_dir.url")+"/co/authority/authorityList.do';";
		if(result){
			logger.debug("*********** 권한 삭제 성공 ***********");
			mav = CommonUtil.getMessageView("삭제 되었습니다.", url , "" );
			throw new ModelAndViewDefiningException(mav);
		}else{
			logger.debug("*********** 권한 삭제 실패!! ***********");
			mav = CommonUtil.getMessageView("삭제 실패!", url , "" );
			throw new ModelAndViewDefiningException(mav);
		}
	}
	
	/**
	 * <pre>
	 * 1. 개요     : 권한 초기화 
	 * 2. 처리내용 : 권한에 맵핑되어 있는 임직원들을 초기화 시킨다.
	 * </pre>
	 * @Method Name : resetAuthMember
	 * @param request
	 * @return
	 * @throws ModelAndViewDefiningException
	 */
	@RequestMapping("/co/authority/resetAuthMember.do")
	public ModelAndView resetAuthMember(HttpServletRequest request) throws ModelAndViewDefiningException{
		boolean result = false;
		
		ModelAndView mav = new ModelAndView();
		
		AuthorityVO authVO = new AuthorityVO();
		authVO.setAuth_seq(request.getParameter("auth_seq"));
		
		result = authorityService.resetAuthMember(authVO);
		String url = "window.location.href='"+env.getValue("root_dir.url")+"/co/authority/authorityList.do';";
		
		if(result){
			logger.debug("*********** 초기화 성공 ***********");
			mav = CommonUtil.getMessageView("초기화 되었습니다.", url , "" );
			throw new ModelAndViewDefiningException(mav);
		}else{
			logger.debug("*********** 초기화 실패!! ***********");
			mav = CommonUtil.getMessageView("초기화 실패!", url , "" );
			throw new ModelAndViewDefiningException(mav);
		}
	}
	
	/**
	 * <pre>
	 * 1. 개요     : 권한 임직원 조회
	 * 2. 처리내용 : 맵핑된 임직원이 있는지 조회를 한다.
	 * </pre>
	 * @Method Name : searchAuthMember
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/co/authority/searchAuthMember.do")
	public void searchAuthMember(HttpServletRequest request, HttpServletResponse response) throws IOException{
		
		String emp_ko_nm = StringUtil.nvl(request.getParameter("emp_ko_nm"));	//임직원한글이름
		
		AuthorityVO authVO = new AuthorityVO();
		
		if(!"".equals(emp_ko_nm)){
			
			List<AuthorityVO> searchAuthMember = authorityService.searchAuthMember(emp_ko_nm);
			
			if(searchAuthMember.size() != 0){
				for(int i=0; i<searchAuthMember.size(); i++){
					authVO.setEmp_no_list(searchAuthMember);
				}
			}
			
			MarshallerUtil.marshalling("json", response, authVO);
		}
	}
}
