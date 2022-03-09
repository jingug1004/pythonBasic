/**
  * Hana Project
  * Copyright 2014 iRush Co.,
  *
  */
package com.hanaph.gw.co.menu.dao;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

import com.hanaph.gw.co.menu.vo.MenuVO;

/**
 * <pre>
 * Class Name : MenuDAOImpl.java
 * 설명 : 메뉴 관리 DAOImpl
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
@Repository("menuDao")
public class MenuDAOImpl extends SqlSessionDaoSupport implements MenuDAO {

	/* (non-Javadoc)
	 * @see com.hanaph.gw.co.menu.dao.MenuDAO#getMenuList(java.util.Map)
	 */
	@SuppressWarnings("unchecked")
	public List<MenuVO> getMenuList(Map<String, String> paramMap) {
		return (List<MenuVO>)getSqlSession().selectList("mgrMenu.getMenuList", paramMap);
	}
	
	/* (non-Javadoc)
	 * @see com.hanaph.gw.co.menu.dao.MenuDAO#getMenuDetail(java.util.Map)
	 */
	public MenuVO getMenuDetail(Map<String, String> paramMap) {
		return (MenuVO)getSqlSession().selectOne("mgrMenu.getMenuDetail", paramMap);
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.co.menu.dao.MenuDAO#getMenuSubList(java.util.Map)
	 */
	@SuppressWarnings("unchecked")
	public List<MenuVO> getMenuSubList(Map<String, String> paramMap) {
		return (List<MenuVO>)getSqlSession().selectList("mgrMenu.getMenuSubList", paramMap);
	}
	
	/* (non-Javadoc)
	 * @see com.hanaph.gw.co.menu.dao.MenuDAO#insertMenu(com.hanaph.gw.co.menu.vo.MenuVO)
	 */
	public boolean insertMenu(MenuVO paramMenuVO) {
		Integer count = getSqlSession().insert("mgrMenu.insertMenu", paramMenuVO);
		if(count != 0){
			return true;
		}else{
			return false;
		}
	}
	
	/* (non-Javadoc)
	 * @see com.hanaph.gw.co.menu.dao.MenuDAO#updateMenuUseYn(com.hanaph.gw.co.menu.vo.MenuVO)
	 */
	public boolean updateMenuUseYn(MenuVO paramMenuVO) {
		Integer count = getSqlSession().update("mgrMenu.updateMenuUseYn", paramMenuVO);
		if(count != 0){
			return true;
		}else{
			return false;
		}
	}
	
	/* (non-Javadoc)
	 * @see com.hanaph.gw.co.menu.dao.MenuDAO#updateMenu(com.hanaph.gw.co.menu.vo.MenuVO)
	 */
	public boolean updateMenu(MenuVO paramMenuVO) {
		Integer count = getSqlSession().update("mgrMenu.updateMenu", paramMenuVO);
		if(count != 0){
			return true;
		}else{
			return false;
		}
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.co.menu.dao.MenuDAO#deleteMenu(com.hanaph.gw.co.menu.vo.MenuVO)
	 */
	public boolean deleteMenu(MenuVO paramMenuVO) {
		Integer count = getSqlSession().update("mgrMenu.deleteMenu", paramMenuVO);
						getSqlSession().update("mgrMenu.deleteAllMenu", paramMenuVO);
		if(count != 0){
			return true;
		}else{
			return false;
		}	
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.co.menu.dao.MenuDAO#menuCheck(java.util.Map)
	 */
	public MenuVO menuCheck(Map<String, String> paramMap) {
		return (MenuVO)getSqlSession().selectOne("mgrMenu.menuCheck", paramMap);
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.co.menu.dao.MenuDAO#getGnbMenuList(java.util.Map)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<MenuVO> getGnbMenuList(Map<String, String> paramMap) {
		return (List<MenuVO>)getSqlSession().selectList("mgrMenu.getGnbMenuList", paramMap);
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.co.menu.dao.MenuDAO#getLnbMenuList(java.util.Map)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<MenuVO> getLnbMenuList(Map<String, String> paramMap) {
		return (List<MenuVO>)getSqlSession().selectList("mgrMenu.getLnbMenuList", paramMap);
	}

}
