/**
  * Hana Project
  * Copyright 2014 iRush Co.,
  *
  */
package com.hanaph.gw.co.code.dao;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

import com.hanaph.gw.co.code.vo.CodeVO;

/**
 * <pre>
 * Class Name : CodeDAOImpl.java
 * 설명 : 코드 관리 DAOImpl
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
@Repository("codeDao")
public class CodeDAOImpl extends SqlSessionDaoSupport implements CodeDAO {
	
	/* (non-Javadoc)
	 * @see com.hanaph.gw.co.code.dao.CodeDAO#getCodeCount(java.util.Map)
	 */
	public int getCodeCount(Map<String, String> paramMap) {
		Integer count = (Integer)getSqlSession().selectOne("mgrCode.getCodeCount", paramMap);
		return count;	
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.co.code.dao.CodeDAO#getCodeList(java.util.Map)
	 */
	@SuppressWarnings("unchecked")
	public List<CodeVO> getCodeList(Map<String, String> paramMap) {
		return (List<CodeVO>)getSqlSession().selectList("mgrCode.getCodeList", paramMap);
	}
	
	/* (non-Javadoc)
	 * @see com.hanaph.gw.co.code.dao.CodeDAO#getScodeList(java.util.Map)
	 */
	@SuppressWarnings("unchecked")
	public List<CodeVO> getScodeList(Map<String, String> paramMap) {
		return (List<CodeVO>)getSqlSession().selectList("mgrCode.getScodeList", paramMap);
	}
	
	/* (non-Javadoc)
	 * @see com.hanaph.gw.co.code.dao.CodeDAO#detailCode(java.util.Map)
	 */
	public CodeVO detailCode(Map<String, String> paramMap) {
		return (CodeVO)getSqlSession().selectOne("mgrCode.detailCode", paramMap);
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.co.code.dao.CodeDAO#insertCode(com.hanaph.gw.co.code.vo.CodeVO)
	 */
	public boolean insertCode(CodeVO paramCodeVO) {
		Integer count = getSqlSession().insert("mgrCode.insertCode", paramCodeVO);
		if(count != 0){
			return true;
		}else{
			return false;
		}
	}
	
	/* (non-Javadoc)
	 * @see com.hanaph.gw.co.code.dao.CodeDAO#deleteCode(com.hanaph.gw.co.code.vo.CodeVO)
	 */
	public boolean deleteCode(CodeVO paramCodeVO) {
		Integer count = getSqlSession().insert("mgrCode.deleteCode", paramCodeVO);
		if(count != 0){
			return true;
		}else{
			return false;
		}
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.co.code.dao.CodeDAO#checkCode(java.lang.String)
	 */
	public CodeVO checkCode(String cd) {
		return (CodeVO)getSqlSession().selectOne("mgrCode.checkCode", cd);
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.co.code.dao.CodeDAO#updateCode(com.hanaph.gw.co.code.vo.CodeVO)
	 */
	public boolean updateCode(CodeVO paramCodeVO) {
		Integer count = getSqlSession().update("mgrCode.updateCode", paramCodeVO);
						getSqlSession().insert("mgrCode.updateCodeLog", paramCodeVO);
		if(count != 0){
			return true;
		}else{
			return false;
		}
	}

	/* (non-Javadoc)
	 * @see com.hanaph.saleon.member.dao.LoginUserDAO#getOracleUserList()
	 */
	/* (non-Javadoc)
	 * @see com.hanaph.gw.co.code.dao.CodeDAO#getOracleUserList()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, String>> getOracleUserList() {
		return  (List<Map<String, String>>)getSqlSession().selectList("mgrCode.getOracleUserList");
	}
}
