/**
  * Hana Project
  * Copyright 2014 iRush Co.,
  *
  */
package com.hanaph.gw.co.login.dao;

import java.util.HashMap;
import java.util.Map;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

import com.hanaph.gw.co.login.vo.LoginVO;
import com.hanaph.gw.pe.member.vo.PasswordHisVO;


/**
 * <pre>
 * Class Name : LoginDAOImpl.java
 * 설명 : 로그인 정보 DAO 구현한 class
 * 
 * Modification Information
 *    수정일      수정자              수정 내용
 *  ------------ -------------- --------------------------------
 *  2014. 10. 17.      CHOIILJI         
 * </pre>
 * 
 * @version : 1.0
 * @author  : CHOIILJI(choiilji@irush.co.kr)
 * @since   : 2014. 10. 17.
 */
@Repository("loginDAO")
public class LoginDAOImpl extends SqlSessionDaoSupport implements LoginDAO {

	/* (non-Javadoc)
	 * @see com.hanaph.gw.login.dao.LoginDAO#loginProc(java.util.Map)
	 */
	@Override
	public LoginVO getLogin(Map<String, String> paramMap) {
		return (LoginVO)getSqlSession().selectOne("member.getLogin", paramMap);
	}

	/*
	 * (non-Javadoc)
	 * @see com.hanaph.gw.member.dao.LoginDAO#updatePassword(com.hanaph.gw.member.vo.LoginVO)
	 */
	@Override
	public LoginVO updatePassword(LoginVO paramLoginVO) {
		
		LoginVO resultVO = new LoginVO();
		
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("in_EMP_NO", paramLoginVO.getEmp_no());
		paramMap.put("in_PASSWORD", paramLoginVO.getNew_password());
		
		getSqlSession().selectOne("member.callPasswordAccord", paramMap);
		
		resultVO.setOut_CODE(paramMap.get("out_CODE"));
		resultVO.setOut_MSG(paramMap.get("out_MSG"));
		resultVO.setOut_COUNT(paramMap.get("out_COUNT"));
		
		return resultVO;
	}

	/*
	 * (non-Javadoc)
	 * @see com.hanaph.gw.member.dao.LoginDAO#insertHisPassword(com.hanaph.gw.member.vo.PasswordHisVO)
	 */
	@Override
	public int insertHisPassword(PasswordHisVO paramPasswordHisVO) {
		Integer cnt = (Integer)getSqlSession().insert("member.insertHisPassword", paramPasswordHisVO);
		return cnt;
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.co.login.dao.LoginDAO#callPasswordValidateProcedure(java.util.Map)
	 */
	@Override
	public LoginVO callPasswordValidateProcedure(Map<String, String> paramMap) {
		
		LoginVO resultVO = new LoginVO();
		
		getSqlSession().selectOne("member.callPasswordValidate", paramMap);
		
		resultVO.setOut_CODE(paramMap.get("out_CODE"));
		resultVO.setOut_MSG(paramMap.get("out_MSG"));
		resultVO.setOut_COUNT(paramMap.get("out_COUNT"));
		
		return resultVO;
	}
}
