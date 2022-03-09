/**
  * Hana Project
  * Copyright 2014 iRush Co.,
  *
  */
package com.hanaph.gw.co.menu.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hanaph.gw.co.menu.dao.MenuDAO;
import com.hanaph.gw.co.menu.vo.MenuVO;

/**
 * <pre>
 * Class Name : MenuServiceImpl.java
 * 설명 : 메뉴 관리 ServiceImpl
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
@Service(value="menuService")
public class MenuServiceImpl implements MenuService {
	
	@Autowired
	MenuDAO menuDAO;

	/* (non-Javadoc)
	 * @see com.hanaph.gw.co.menu.service.MenuService#getMenuList(java.util.Map)
	 */
	public List<MenuVO> getMenuList(Map<String, String> paramMap) {
		return menuDAO.getMenuList(paramMap);
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.co.menu.service.MenuService#getMenuDetail(java.util.Map)
	 */
	public MenuVO getMenuDetail(Map<String, String> paramMap) {
		return menuDAO.getMenuDetail(paramMap);
	}
	
	/* (non-Javadoc)
	 * @see com.hanaph.gw.co.menu.service.MenuService#getMenuSubList(java.util.Map)
	 */
	public List<MenuVO> getMenuSubList(Map<String, String> paramMap) {
		return menuDAO.getMenuSubList(paramMap);
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.co.menu.service.MenuService#insertMenu(com.hanaph.gw.co.menu.vo.MenuVO)
	 */
	public boolean insertMenu(MenuVO paramMenuVO) {
		if("0".equals(paramMenuVO.getStatus())){ 		//상태값이 0이면 저장 1이면 수정
			return menuDAO.insertMenu(paramMenuVO);
		}else{
			if("3".equals(paramMenuVO.getStatus())){	//상태값이 3 이면 하위메뉴 use_yn 전부 N으로 일괄 처리 한다
				menuDAO.updateMenuUseYn(paramMenuVO);
			}
			return menuDAO.updateMenu(paramMenuVO);
		}
	}
	
	/* (non-Javadoc)
	 * @see com.hanaph.gw.co.menu.service.MenuService#deleteMenu(com.hanaph.gw.co.menu.vo.MenuVO)
	 */
	public boolean deleteMenu(MenuVO paramMenuVO) {
		return menuDAO.deleteMenu(paramMenuVO);
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.co.menu.service.MenuService#menuCheck(java.util.Map)
	 */
	public MenuVO menuCheck(Map<String, String> paramMap) {
		return menuDAO.menuCheck(paramMap);
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.co.menu.service.MenuService#getGnbMenuList(java.util.Map)
	 */
	@Override
	public List<MenuVO> getGnbMenuList(Map<String, String> paramMap) {
		return menuDAO.getGnbMenuList(paramMap);
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.co.menu.service.MenuService#getLnbMenuList(java.util.Map)
	 */
	@Override
	public List<MenuVO> getLnbMenuList(Map<String, String> paramMap) {
		return menuDAO.getLnbMenuList(paramMap);
	}
}
