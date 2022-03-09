/**
 * Hana Project
 * Copyright 2014 iRush Co.,
 *
 */
package com.hanaph.gw.co.login.service;

import java.util.Map;

import com.hanaph.gw.co.login.vo.LoginVO;
import com.hanaph.gw.pe.member.vo.PasswordHisVO;

/**
 * <pre>
 * Class Name : LoginService.java
 * 설명 : 로그인 Service interface
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
public interface LoginService {

	/**
	 * 
	 * <pre>
	 * 1. 개요     : 로그인 
	 * 2. 처리내용 : 로그인 처리 한다.
	 * </pre>
	 * @Method Name : getLogin
	 * @param paramMap
	 * @return
	 */
	public LoginVO getLogin(Map<String, String> paramMap);
	
	/**
	 * 
	 * <pre>
	 * 1. 개요     : 패스워드 변경
	 * 2. 처리내용 : 패스워드 변경한다.
	 * </pre>
	 * @Method Name : updatePassword
	 * @param paramMap
	 * @return
	 */
	public LoginVO updatePassword(LoginVO paramLoginVO, PasswordHisVO paramPasswordHisVO);

	/**
	 * <pre>
	 * 1. 개요     : 비밀번호 유효성 검사 프로시저 호출
	 * 2. 처리내용 : 비밀번호 유효성 검사 프로시저 호출
	 * </pre>
	 * @Method Name : getPasswordValidateProcedure
	 * @param paramMap
	 * @return 
	 */		
	public LoginVO callPasswordValidateProcedure(Map<String, String> paramMap);
	
}
