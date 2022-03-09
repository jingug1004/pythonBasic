/**
  * Hana Project
  * Copyright 2014 iRush Co.,
  *
  */
package com.hanaph.gw.co.menu.dao;

import java.util.List;
import java.util.Map;

import com.hanaph.gw.co.menu.vo.MenuVO;

/**
 * <pre>
 * Class Name : MenuDAO.java
 * 설명 : 메뉴 관리 DAO
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
public interface MenuDAO {

	/**
	 * <pre>
	 * 1. 개요     : 메뉴 리스트
	 * 2. 처리내용 : 메뉴 리스트를 가져온다.
	 * </pre>
	 * @Method Name : getMenuList
	 * @param paramMap
	 * @return
	 */
	List<MenuVO> getMenuList(Map<String, String> paramMap);

	/**
	 * <pre>
	 * 1. 개요     :  메뉴 상세정보
	 * 2. 처리내용 :
	 * </pre>
	 * @Method Name : getMenuDetail
	 * @param paramMap
	 * @return
	 */
	MenuVO getMenuDetail(Map<String, String> paramMap);

	/**
	 * <pre>
	 * 1. 개요     : 하위 메뉴 리스트 
	 * 2. 처리내용 : 하위 메뉴 리스트를 가져온다
	 * </pre>
	 * @Method Name : getMenuSubList
	 * @param paramMap
	 * @return
	 */
	List<MenuVO> getMenuSubList(Map<String, String> paramMap);

	/**
	 * <pre>
	 * 1. 개요     : 메뉴 저장 
	 * 2. 처리내용 : 메뉴를 저장한다.
	 * </pre>
	 * @Method Name : insertMenu
	 * @param paramMenuVO
	 * @return
	 */
	boolean insertMenu(MenuVO paramMenuVO);
	
	/**
	 * <pre>
	 * 1. 개요     : 메뉴 사용 여부 
	 * 2. 처리내용 : 메뉴 사용 여부를 수정한다.
	 * </pre>
	 * @Method Name : deleteMenu
	 * @param paramMenuVO
	 * @return
	 */
	boolean updateMenuUseYn(MenuVO paramMenuVO);

	/**
	 * <pre>
	 * 1. 개요     : 메뉴 수정 
	 * 2. 처리내용 : 메뉴 정보를 수정한다.
	 * </pre>
	 * @Method Name : updateMenu
	 * @param paramMenuVO
	 * @return
	 */
	boolean updateMenu(MenuVO paramMenuVO);
	
	/**
	 * <pre>
	 * 1. 개요     : 메뉴 삭제 
	 * 2. 처리내용 : 메뉴 삭제를 한다.
	 * </pre>
	 * @Method Name : deleteMenu
	 * @param paramMenuVO
	 * @return
	 */
	boolean deleteMenu(MenuVO paramMenuVO);

	/**
	 * <pre>
	 * 1. 개요     : 메뉴 체크
	 * 2. 처리내용 : 중복된 메뉴를 체크 한다.
	 * </pre>
	 * @Method Name : menuCheck
	 * @param paramMap
	 * @return
	 */
	MenuVO menuCheck(Map<String, String> paramMap);
	
	/**
	 * 
	 * <pre>
	 * 1. 개요     : gnb 메뉴 가져온다.
	 * 2. 처리내용 : 회원별 권한에 매핑된 gnb메뉴리스트 가져온다.
	 * </pre>
	 * @Method Name : getGnbMenuList
	 * @param paramMap
	 * @return
	 */
	List<MenuVO> getGnbMenuList(Map<String, String> paramMap);
	
	/**
	 * 
	 * <pre>
	 * 1. 개요     : lnb 메뉴 가져온다.
	 * 2. 처리내용 : 회원별 권한에 매핑된 lnb메뉴리스트 가져온다.
	 * </pre>
	 * @Method Name : getLnbMenuList
	 * @param paramMap
	 * @return
	 */
	List<MenuVO> getLnbMenuList(Map<String, String> paramMap);

	
}
