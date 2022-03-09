/**
  * Hana Project
  * Copyright 2014 iRush Co.,
  *
  */
package com.hanaph.gw.co.common.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.hanaph.gw.co.menu.service.MenuService;
import com.hanaph.gw.co.menu.vo.MenuVO;
import com.hanaph.gw.pe.member.vo.MemberVO;

/**
 * 
 * <pre>
 * Class Name : MenuUtil.java
 * 설명 : 메뉴 정보 Util
 * 
 * Modification Information
 *    수정일      수정자              수정 내용
 *  ------------ -------------- --------------------------------
 *  2014. 11. 7.      CHOIILJI         
 * </pre>
 * 
 * @version : 
 * @author  : CHOIILJI(choiilji@irush.co.kr)
 * @since   : 2014. 11. 7.
 */
public class MenuUtil {

	Environment ev = new Environment();
	
	public MenuUtil(){
		
	}
	
	public List<MenuVO> makeLnb(HttpServletRequest request, String menu_parents, MenuService menuService){
		
		Map<String, String> paramMap = new HashMap<String, String>();
		/*세션에서 회원정보 가져 온다.*/
		HttpSession session = request.getSession(false);
		MemberVO memberSessionVO = (MemberVO) session.getAttribute("gwUser");
		String emp_no = memberSessionVO.getEmp_no();

		paramMap.put("emp_no", emp_no);
		paramMap.put("menu_parents", menu_parents);

		List<MenuVO> lnbMenuList = menuService.getLnbMenuList(paramMap);
		return lnbMenuList;
		
	}
}
