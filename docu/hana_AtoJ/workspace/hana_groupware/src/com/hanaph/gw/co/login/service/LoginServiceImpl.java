/**
 * Hana Project
 * Copyright 2014 iRush Co.,
 *
 */
package com.hanaph.gw.co.login.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hanaph.gw.co.login.dao.LoginDAO;
import com.hanaph.gw.co.login.vo.LoginVO;
import com.hanaph.gw.pe.member.vo.PasswordHisVO;

/**
 * <pre>
 * Class Name : LoginServiceImpl.java
 * 설명 : 로그인 Service 구현한 class
 * 
 * Modification Information
 *    수정일      수정자              수정 내용
 *  ------------ -------------- --------------------------------
 *  2014. 10. 17.      CHOIILJI
 * </pre>
 * 
 * @version : 1.0
 * @author : CHOIILJI(choiilji@irush.co.kr)
 * @since : 2014. 10. 17.
 */
@Service
public class LoginServiceImpl implements LoginService {

	@Autowired
	LoginDAO loginDAO;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.hanaph.gw.login.service.LoginService#loginProc(java.util.Map)
	 */
	@Override
	public LoginVO getLogin(Map<String, String> paramMap) {
		return loginDAO.getLogin(paramMap);
	}

	/*
	 * (non-Javadoc)
	 * @see com.hanaph.gw.member.service.LoginService#updatePassword(java.util.Map)
	 */
	@Transactional
	@Override
	public LoginVO updatePassword(LoginVO paramLoginVO, PasswordHisVO paramPasswordHisVO) {
		
		LoginVO resultVO = new LoginVO();
		
		resultVO = loginDAO.updatePassword(paramLoginVO);
		if("0".equals(resultVO.getOut_CODE())){
			loginDAO.insertHisPassword(paramPasswordHisVO);
		}
		
		return resultVO;
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.co.login.service.LoginService#callPasswordValidateProcedure(java.util.Map)
	 */
	@Override
	public LoginVO callPasswordValidateProcedure(Map<String, String> paramMap) {
		return loginDAO.callPasswordValidateProcedure(paramMap);
	}

}
