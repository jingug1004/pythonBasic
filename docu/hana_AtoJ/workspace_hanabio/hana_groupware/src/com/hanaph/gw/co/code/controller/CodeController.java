/**
  * Hana Project
  * Copyright 2014 iRush Co.,
  *
  */
package com.hanaph.gw.co.code.controller;

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

import com.hanaph.gw.co.code.service.CodeService;
import com.hanaph.gw.co.code.vo.CodeVO;
import com.hanaph.gw.co.common.utils.CommonUtil;
import com.hanaph.gw.co.common.utils.Environment;
import com.hanaph.gw.co.common.utils.MarshallerUtil;
import com.hanaph.gw.co.common.utils.MenuUtil;
import com.hanaph.gw.co.common.utils.PageUtil;
import com.hanaph.gw.co.common.utils.RequestFilterUtil;
import com.hanaph.gw.co.common.utils.StringUtil;
import com.hanaph.gw.co.menu.service.MenuService;
import com.hanaph.gw.co.menu.vo.MenuVO;
import com.hanaph.gw.pe.member.vo.MemberVO;

/**
 * <pre>
 * Class Name : CodeController.java
 * 설명 : 코드 관리 Controller
 * 
 * Modification Information
 *    수정일      수정자              수정 내용
 *  ------------ -------------- --------------------------------
 *  2014. 10. 14.      Jung.Jin.Muk          
 * </pre>
 * 
 * @version : 
 * @author  : Jung.Jin.Muk(pc123pc@irush.co.kr)
 * @since   : 2014. 10. 14.
 */
@Controller
public class CodeController {
	
	@Autowired
	private CodeService codeService;
	
	@Autowired
	private MenuService menuService;
	
	private Environment env = new Environment();
	private static final Logger logger = Logger.getLogger(CodeController.class);

	
	/**
	 * <pre>
	 * 1. 개요     : 기본코드 관리 리스트
	 * 2. 처리내용 : 코드 리스트를 가져온다.
	 * </pre>
	 * @Method Name : codeList
	 * @param request
	 * @return
	 */
	@RequestMapping("/co/code/codeList.do")
	public ModelAndView codeList(HttpServletRequest request) {
		
		final String MENU_CHILD= "0602";
		
		ModelAndView mav = new ModelAndView("co/code/codeList");
		
		int currentPage = Integer.parseInt(StringUtil.nvl(request.getParameter("currentPage"),"1"));	// 현재 페이지 번호
		int plRowRange = 10;	
		int plPageRange = 10;
		
		String searchType = StringUtil.nvl(request.getParameter("searchType"));			//검색타입
		String searchKeyword = StringUtil.nvl(request.getParameter("searchKeyword"));	//검색어
		
		Map<String, String> paramMap = new HashMap<String, String>();
		
		paramMap.put("searchKeyword", searchKeyword);			//검색어
		paramMap.put("searchType", searchType);					//검색타입
		paramMap.put("s_cd", "000");							//메인코드 리스트만 가져온다.
		paramMap.put("delete_yn", "N");							//삭제안된 것들만 가져온다.
		paramMap.put("page",String.valueOf(currentPage));		//현재페이지번호
		paramMap.put("perPageRow", String.valueOf(plRowRange));	//list에 보여질 row갯수
		
		List<CodeVO> codeList = codeService.getCodeList(paramMap);
		int cnt = codeService.getCodeCount(paramMap);

		PageUtil pu = new PageUtil();
		String pagingStr = pu.autoPaging(cnt, plRowRange, plPageRange, currentPage);
		
		mav.addObject("searchType", searchType);		//검색타입
		mav.addObject("searchKeyword", searchKeyword);	//검색어
		mav.addObject("pagingStr", pagingStr);			//페이지번호
		mav.addObject("codeList", codeList);			//mCode List를 view단으로 넘겨준다.
		
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
	 * 1. 개요     : 코드상세정보Ajax 
	 * 2. 처리내용 : 페이징 처리를 위하여 ajax를 사용하여 포커스를 맞춤.
	 * </pre>
	 * @Method Name : detailCodeAjax
	 * @param request
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("/co/code/detailCodeAjax.do")
	public void detailCodeAjax(HttpServletRequest request, HttpServletResponse response) throws IOException{
		
		boolean result = true;
		CodeVO paramCodeVO = new CodeVO();
		paramCodeVO.setResult(result);
		
		MarshallerUtil.marshalling("json", response, paramCodeVO);	        
	}
	
	/**
	 * <pre>
	 * 1. 개요     : 코드상세정보 
	 * 2. 처리내용 : 코드 상세정보를 보여준다.
	 * </pre>
	 * @Method Name : detailCode
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/co/code/detailCode.do")
	public ModelAndView detailCode(HttpServletRequest request){
		
		ModelAndView mav = new ModelAndView("co/code/codeDetailIfame");
		
		String s_cd = StringUtil.nvl(request.getParameter("s_cd"));	//서브코드
		String m_cd = StringUtil.nvl(request.getParameter("m_cd"));	//메인코드
		
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("m_cd", m_cd);
		paramMap.put("s_cd", s_cd);
		
		List<CodeVO> sCodeList = codeService.getScodeList(paramMap);
		CodeVO detailCode = codeService.detailCode(paramMap);
		
		mav.addObject("detailCode", detailCode);		//m_cd 정보를 view단으로 넘겨준다.
		mav.addObject("sCodeList", sCodeList);			//s_cd List를 view단으로 넘겨준다.
		
		return mav;
	}
	
	/**
	 * <pre>
	 * 1. 개요     : 기본코드등록
	 * 2. 처리내용 : 코드 등록을 한다.
	 * </pre>
	 * @Method Name : insertCode
	 * @param request
	 * @return 
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("/co/code/insertCode.do")
	public ModelAndView insertCode(HttpServletRequest request, HttpServletResponse response )
			throws IOException, ModelAndViewDefiningException{
		
		boolean result = false;		//성공여부
		ModelAndView mav = new ModelAndView("");

		HttpSession session = request.getSession();
		MemberVO memberSessionVO = (MemberVO) session.getAttribute("gwUser");
		
		String s_cd = StringUtil.nvl(request.getParameter("s_cd"));			//서브코드
		String m_cd = StringUtil.nvl(request.getParameter("m_cd"));			//메인코드
		String descr = StringUtil.nvl(request.getParameter("descr"));		//코드설명
		String use_yn = StringUtil.nvl(request.getParameter("use_yn"));		//사용여부
		String cd_nm = StringUtil.nvl(request.getParameter("cd_nm"));		//코드이름
		String ordering = StringUtil.nvl(request.getParameter("ordering"));	//정렬순서
		String create_no = StringUtil.nvl(memberSessionVO.getEmp_no());		//임직원번호
		String status = StringUtil.nvl(request.getParameter("status"));		//코드상태 0.저장 1.저장 2.수정
		
		String cd = "";
		
		//특수문자 제거
		s_cd = RequestFilterUtil.convertToSearchParameter(s_cd);
		m_cd = RequestFilterUtil.convertToSearchParameter(m_cd);
		descr = RequestFilterUtil.convertToSearchParameter(descr);
		use_yn = RequestFilterUtil.convertToSearchParameter(use_yn);
		cd_nm = RequestFilterUtil.convertToSearchParameter(cd_nm);
		ordering = RequestFilterUtil.convertToSearchParameter(ordering);
		cd = m_cd + s_cd;
		
		CodeVO paramCodeVO = new CodeVO();	//저장할 데이터 셋팅
		paramCodeVO.setCd(cd);
		paramCodeVO.setS_cd(s_cd);
		paramCodeVO.setM_cd(m_cd);
		paramCodeVO.setDescr(descr);
		paramCodeVO.setUse_yn(use_yn);
		paramCodeVO.setCd_nm(cd_nm);
		paramCodeVO.setOrdering(ordering);
		paramCodeVO.setCreate_no(create_no);
		paramCodeVO.setDelete_yn("N");
		paramCodeVO.setStatus(status);
		
		String url = "window.parent.location.href='"+env.getValue("root_dir.url")+"/co/code/codeList.do';";
		String str = "";
		
		if("0".equals(status) || "1".equals(status)){ //저장
			str = "저장";
		}else if("2".equals(status)){	//수정
			str = "수정";
		}
		
		result = codeService.insertCode(paramCodeVO);
		
		if(result){
			logger.debug("*********** Code " + str + " 성공 ***********");
			mav = CommonUtil.getMessageView(str + " 되었습니다.", url , "" );
			throw new ModelAndViewDefiningException(mav);
		}else{
			logger.debug("*********** Code " + str + " 실패 ***********");
			mav = CommonUtil.getMessageView(str + " 실패!.", url , "" );
			throw new ModelAndViewDefiningException(mav);
		}
	}
	
	/**
	 * <pre>
	 * 1. 개요     : 코드삭제 
	 * 2. 처리내용 : 코드삭제를 한다.
	 * </pre>
	 * @Method Name : deleteCode
	 * @param request
	 * @param response
	 * @param model
	 * @throws IOException
	 */
	@RequestMapping("/co/code/deleteCode.do")
	public ModelAndView deleteCode(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ModelAndViewDefiningException{
		
		ModelAndView mav = new ModelAndView("");
		
		boolean result = false;
		
		HttpSession session = request.getSession();
		MemberVO memberSessionVO = (MemberVO) session.getAttribute("gwUser");
		
		String s_cd = StringUtil.nvl(request.getParameter("s_cd"));		//서브코드
		String m_cd = StringUtil.nvl(request.getParameter("m_cd"));		//메인코드
		String create_no = StringUtil.nvl(memberSessionVO.getEmp_no());	//임직원번호
		
		s_cd = RequestFilterUtil.convertToSearchParameter(s_cd);
		m_cd = RequestFilterUtil.convertToSearchParameter(m_cd);
		create_no = RequestFilterUtil.convertToSearchParameter(create_no);
		
		/*삭제 데이터 셋팅*/
		CodeVO paramCodeVO = new CodeVO();
		paramCodeVO.setS_cd(s_cd);
		paramCodeVO.setM_cd(m_cd);
		paramCodeVO.setCreate_no(create_no);
		paramCodeVO.setDelete_yn("Y");
		paramCodeVO.setCreate_no(create_no);
		
		result = codeService.deleteCode(paramCodeVO);
		
		String url = "window.parent.location.href='"+env.getValue("root_dir.url")+"/co/code/codeList.do';";
		
		if(result){
			logger.debug("*********** Code 삭제 성공 ***********");
			mav = CommonUtil.getMessageView("삭제 되었습니다.", url , "" );
			throw new ModelAndViewDefiningException(mav);
		}else{
			logger.debug("*********** Code 삭제 실패 ***********");	
		}
		return mav;
	}
	
	/**
	 * <pre>
	 * 1. 개요     : 코드 중복 체크
	 * 2. 처리내용 : 코드 중복 체크를 한다.
	 * </pre>
	 * @Method Name : checkCode
	 * @param request
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("/co/code/checkCode.do")
	public void checkCode(HttpServletRequest request, HttpServletResponse response) throws IOException{
		
		logger.debug("================== code Check Start ==================");
		String result = "";		//사용가능여부
		String cd = StringUtil.nvl(request.getParameter("cd"));	//코드
		
		/*특수문자 제거*/
		cd = RequestFilterUtil.convertToSearchParameter(cd);
		CodeVO codeVo = codeService.checkCode(cd);

		response.setContentType("text/html;charset=UTF-8");
		response.setHeader("Cache-Control", "no-cache");
		
		PrintWriter out = response.getWriter();
		
		if(codeVo == null && cd.length()==6){
			result = "0";
		    out.println(result);
		    logger.debug("*********** 사용 가능Code입니다. ***********");
		}else if("Y".equals(codeVo.getDelete_yn())){
			result = "1";
			out.println(result);
			logger.debug("*********** 삭제된 Code 재사용 합니다. ***********");
		}else{
			result = "2";
			out.println(result);
			logger.debug("*********** 사용 할 수 없는Code입니다. ***********");
		}
		out.close();
		logger.debug("================== code Check End ==================");
	}
}
