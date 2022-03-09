/**
  * Hana Project
  * Copyright 2014 iRush Co.,
  *
  */
package com.hanaph.gw.co.authority.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hanaph.gw.co.authority.dao.AuthorityDAO;
import com.hanaph.gw.co.authority.vo.AuthorityVO;
import com.hanaph.gw.co.menu.vo.MenuVO;
import com.hanaph.gw.pe.member.vo.MemberVO;

/**
 * <pre>
 * Class Name : AuthorityServiceImpl.java
 * 설명 : 권한 관리 service
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
@Service(value="authorityService")
public class AuthorityServiceImpl implements AuthorityService {
	
	private static final Logger logger = Logger.getLogger(AuthorityServiceImpl.class);

	@Autowired
	private AuthorityDAO authorityDAO;
	
	@Autowired
	SqlSessionFactory sqlSessionFactory;
	
	/* (non-Javadoc)
	 * @see com.hanaph.gw.co.authority.service.AuthorityService#getAuthorityList(java.util.Map)
	 */
	public List<AuthorityVO> getAuthorityList(Map<String, String> paramMap) {
		return authorityDAO.getAuthorityList(paramMap);
	}
	
	/* (non-Javadoc)
	 * @see com.hanaph.gw.co.authority.service.AuthorityService#getAuthorityCount(java.util.Map)
	 */
	public int getAuthorityCount(Map<String, String> paramMap) {
		return authorityDAO.getAuthorityCount(paramMap);
	}
	
	/* (non-Javadoc)
	 * @see com.hanaph.gw.co.authority.service.AuthorityService#insertAuthority(com.hanaph.gw.co.authority.vo.AuthorityVO)
	 */
	@Transactional
	public void insertAuthority(AuthorityVO authVO) {
		
		authorityDAO.insertAuthority(authVO);
		
		String auth_seq = authorityDAO.getAuth_seq();
		authVO.setAuth_seq(auth_seq);
		
		if(authVO.getMenu_cd().indexOf("|") > -1){
			String menu_cd = authVO.getMenu_cd();
			String[] menu_cds = menu_cd.split("\\|");
			
			for(int i= 0; i<menu_cds.length; i++){
				authVO.setMenu_cd(menu_cds[i]);
				authorityDAO.insertAuthorityMenuCode(authVO);
			}
		}else if("".equals(authVO.getMenu_cd())){
		}else{
			authorityDAO.insertAuthorityMenuCode(authVO);
		}
	}
	
	/* (non-Javadoc)
	 * @see com.hanaph.gw.co.authority.service.AuthorityService#insertAuthorityMenu(com.hanaph.gw.co.authority.vo.AuthorityVO)
	 */
	@Override
	@Transactional
	public void insertAuthorityMenu(AuthorityVO authVO) {

		authorityDAO.deleteAuthorityMenuCode(authVO);	//메뉴코드 등록하기전 데이터를 초기화 시킨다.
		authorityDAO.updateAuthority(authVO);			//권한이름,권한설명 수정을 한다
		
		if(authVO.getMenu_cd().indexOf("|") > -1){
			String menu_cd = authVO.getMenu_cd();
			String[] menu_cds = menu_cd.split("\\|");
			
			for(int i= 0; i<menu_cds.length; i++){
				authVO.setMenu_cd(menu_cds[i]);
				authorityDAO.insertAuthorityMenuCode(authVO);
			}
		}else{
			authorityDAO.insertAuthorityMenuCode(authVO);
		}
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.co.authority.service.AuthorityService#insertAuthorityEmpNo(com.hanaph.gw.co.authority.vo.AuthorityVO)
	 */
	@Override
	public void insertAuthorityEmpNo(AuthorityVO authVO) {
		authorityDAO.insertAuthorityEmpNo(authVO);
	}
	
	/* (non-Javadoc)
	 * @see com.hanaph.gw.co.authority.service.AuthorityService#getAuthorityDetail(java.util.Map)
	 */
	public AuthorityVO getAuthorityDetail(Map<String, String> paramMap) {
		return authorityDAO.getAuthorityDetail(paramMap);
	}
	
	/* (non-Javadoc)
	 * @see com.hanaph.gw.co.authority.service.AuthorityService#getAuthorityMenuList(java.util.Map)
	 */
	public List<MenuVO> getAuthorityMenuList(Map<String, String> paramMap) {
		return authorityDAO.getAuthorityMenuList(paramMap);
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.co.authority.service.AuthorityService#getAuthorityMenuRow(java.util.Map)
	 */
	public List<MenuVO> getAuthorityMenuRow(Map<String, String> paramMap) {
		return authorityDAO.getAuthorityMenuRow(paramMap);
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.co.authority.service.AuthorityService#getMenuCodeList(java.util.Map)
	 */
	public List<AuthorityVO> getMenuCodeList(Map<String, String> paramMap) {
		return authorityDAO.getMenuCodeList(paramMap);
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.co.authority.service.AuthorityService#getAuthorityMemberList(java.util.Map)
	 */
	public List<MemberVO> getAuthorityMemberList(Map<String, Object> paramMap) {
		return authorityDAO.getAuthorityMemberList(paramMap);
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.co.authority.service.AuthorityService#deleteAuthority(com.hanaph.gw.co.authority.vo.AuthorityVO)
	 */
	public boolean deleteAuthority(AuthorityVO authVO) {
		boolean result = false;
		if(!"".equals(authVO.getAuth_seq()) && authVO.getAuth_seq().indexOf("|") > -1){
			String auth_seq = authVO.getAuth_seq();
			String[] auth_seqs = auth_seq.split("\\|");
			
			for(int i= 0; i<auth_seqs.length; i++){
				authVO.setAuth_seq(auth_seqs[i]);
				authorityDAO.deleteAuthorityMenuCode(authVO);
				authorityDAO.deleteAuthorityEmpNo(authVO);
				result = authorityDAO.deleteAuthority(authVO);
			}
		}else{
			authorityDAO.deleteAuthorityMenuCode(authVO);
			authorityDAO.deleteAuthorityEmpNo(authVO);
			return authorityDAO.deleteAuthority(authVO);
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.co.authority.service.AuthorityService#resetAuthMember(com.hanaph.gw.co.authority.vo.AuthorityVO)
	 */
	public boolean resetAuthMember(AuthorityVO authVO) {
		return authorityDAO.resetAuthMember(authVO);
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.co.authority.service.AuthorityService#searchAuthMember(java.lang.String)
	 */
	public List<AuthorityVO> searchAuthMember(String emp_ko_nm) {
		return authorityDAO.searchAuthMember(emp_ko_nm);
	}

}
