/**
  * Hana Project
  * Copyright 2014 iRush Co.,
  *
  */
package com.hanaph.gw.co.authority.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hanaph.gw.co.authority.vo.AuthorityVO;
import com.hanaph.gw.co.menu.vo.MenuVO;
import com.hanaph.gw.pe.member.vo.MemberVO;

/**
 * <pre>
 * Class Name : AuthorityDAOImpl.java
 * 설명 : 권한 관리 DAOImpl
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
@Repository("authorityDAO")
public class AuthorityDAOImpl extends SqlSessionDaoSupport implements AuthorityDAO {

	@Autowired
	SqlSessionFactory sqlSessionFactory;
	
	/* (non-Javadoc)
	 * @see com.hanaph.gw.co.authority.dao.AuthorityDAO#getAuthorityList(java.util.Map)
	 */
	@SuppressWarnings("unchecked")
	public List<AuthorityVO> getAuthorityList(Map<String, String> paramMap) {
		return (List<AuthorityVO>)getSqlSession().selectList("mgrAuthority.getAuthorityList", paramMap);
	}
	
	/* (non-Javadoc)
	 * @see com.hanaph.gw.co.authority.dao.AuthorityDAO#getAuthorityCount(java.util.Map)
	 */
	public int getAuthorityCount(Map<String, String> paramMap) {
		Integer count = (Integer)getSqlSession().selectOne("mgrAuthority.getAuthorityCount", paramMap);
		return count;	
	}
	/* (non-Javadoc)
	 * @see com.hanaph.gw.co.authority.dao.AuthorityDAO#getAuth_seq()
	 */
	public String getAuth_seq(){
		return (String)getSqlSession().selectOne("mgrAuthority.getAuth_seq");
	}
	
	/* (non-Javadoc)
	 * @see com.hanaph.gw.co.authority.dao.AuthorityDAO#insertAuthority(com.hanaph.gw.co.authority.vo.AuthorityVO)
	 */
	public void insertAuthority(AuthorityVO authVO) {
		getSqlSession().insert("mgrAuthority.insertAuthority", authVO);
	}
	
	/* (non-Javadoc)
	 * @see com.hanaph.gw.co.authority.dao.AuthorityDAO#deleteAuthorityMenuCode(com.hanaph.gw.co.authority.vo.AuthorityVO)
	 */
	public void deleteAuthorityMenuCode(AuthorityVO authVO) {
		getSqlSession().update("mgrAuthority.deleteAuthorityMenuCode", authVO);
	}
	
	/* (non-Javadoc)
	 * @see com.hanaph.gw.co.authority.dao.AuthorityDAO#insertAuthorityMenuCode(com.hanaph.gw.co.authority.vo.AuthorityVO)
	 */
	public void insertAuthorityMenuCode(AuthorityVO authVO) {
		getSqlSession().insert("mgrAuthority.insertAuthorityMenuCode", authVO);
	}
	
	/* (non-Javadoc)
	 * @see com.hanaph.gw.co.authority.dao.AuthorityDAO#updateAuthority(com.hanaph.gw.co.authority.vo.AuthorityVO)
	 */
	public void updateAuthority(AuthorityVO authVO) {
		getSqlSession().insert("mgrAuthority.updateAuthority", authVO);

	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.co.authority.dao.AuthorityDAO#getAuthorityDetail(java.util.Map)
	 */
	public AuthorityVO getAuthorityDetail(Map<String, String> paramMap) {
		return (AuthorityVO)getSqlSession().selectOne("mgrAuthority.getAuthorityDetail", paramMap);
	}
	
	/* (non-Javadoc)
	 * @see com.hanaph.gw.co.authority.dao.AuthorityDAO#getAuthorityMenuList(java.util.Map)
	 */
	@SuppressWarnings("unchecked")
	public List<MenuVO> getAuthorityMenuList(Map<String, String> paramMap) {
		return (List<MenuVO>)getSqlSession().selectList("mgrAuthority.getAuthorityMenuList", paramMap);
	}
	
	/* (non-Javadoc)
	 * @see com.hanaph.gw.co.authority.dao.AuthorityDAO#getAuthorityMenuRow(java.util.Map)
	 */
	@SuppressWarnings("unchecked")
	public List<MenuVO> getAuthorityMenuRow(Map<String, String> paramMap) {
		return (List<MenuVO>)getSqlSession().selectList("mgrAuthority.getAuthorityMenuRow", paramMap);
	}
	
	/* (non-Javadoc)
	 * @see com.hanaph.gw.co.authority.dao.AuthorityDAO#deleteAuthorityEmpNo(com.hanaph.gw.co.authority.vo.AuthorityVO)
	 */
	public void deleteAuthorityEmpNo(AuthorityVO authVO) {
		getSqlSession().delete("mgrAuthority.deleteAuthorityEmpNo", authVO);
	}
	
	/* (non-Javadoc)
	 * @see com.hanaph.gw.co.authority.dao.AuthorityDAO#insertAuthorityEmpNo(com.hanaph.gw.co.authority.vo.AuthorityVO)
	 */
	public void insertAuthorityEmpNo(AuthorityVO authVO) {
		
		SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH);
		sqlSession.commit(false);
		try{
			//authorityDAO.deleteAuthorityEmpNo(authVO); //임직원 권한 등록하기전 데이터를 초기화 시킨다.
			getSqlSession().delete("mgrAuthority.deleteAuthorityEmpNo", authVO);
			
			if(!"".equals(authVO.getEmp_no()) && authVO.getEmp_no().indexOf("|") > -1){
				String emp_no = authVO.getEmp_no();
				String[] emp_nos = emp_no.split("\\|");
				
				for(int i= 0; i<emp_nos.length; i++){
					authVO.setEmp_no(emp_nos[i]);
					sqlSession.insert("mgrAuthority.insertAuthorityEmpNo", authVO);
				}
			}else{
				sqlSession.insert("mgrAuthority.insertAuthorityEmpNo", authVO);
			}
				sqlSession.commit();
		}catch(RuntimeException e){
			logger.debug("권한 임직원 등록 실패 :: ", e);
			sqlSession.rollback();
		}finally{
			sqlSession.close();
		}
		
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.co.authority.dao.AuthorityDAO#getMenuCodeList(java.util.Map)
	 */
	@SuppressWarnings("unchecked")
	public List<AuthorityVO> getMenuCodeList(Map<String, String> paramMap) {
		return (List<AuthorityVO>)getSqlSession().selectList("mgrAuthority.getMenuCodeList", paramMap);
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.co.authority.dao.AuthorityDAO#getAuthorityMemberList(java.util.Map)
	 */
	@SuppressWarnings("unchecked")
	public List<MemberVO> getAuthorityMemberList(Map<String, Object> paramMap) {
		return (List<MemberVO>)getSqlSession().selectList("mgrAuthority.getAuthorityMemberList", paramMap);
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.co.authority.dao.AuthorityDAO#deleteAuthority(com.hanaph.gw.co.authority.vo.AuthorityVO)
	 */
	public boolean deleteAuthority(AuthorityVO authVO) {
		Integer count = getSqlSession().update("mgrAuthority.deleteAuthority", authVO);
		if(count != 0){
			return true;
		}else{
			return false;
		}
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.co.authority.dao.AuthorityDAO#resetAuthMember(com.hanaph.gw.co.authority.vo.AuthorityVO)
	 */
	public boolean resetAuthMember(AuthorityVO authVO) {
		Integer count = getSqlSession().delete("mgrAuthority.resetAuthMember", authVO);
		if(count != 0){
			return true;
		}else{
			return false;
		}
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.co.authority.dao.AuthorityDAO#searchAuthMember(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public List<AuthorityVO> searchAuthMember(String emp_ko_nm) {
		return (List<AuthorityVO>)getSqlSession().selectList("mgrAuthority.searchAuthMember", emp_ko_nm);
	}

	
}
