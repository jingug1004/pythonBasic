/**
 * Hana Project
 * Copyright 2014 iRush Co.,
 *
 */
package com.hanaph.gw.co.login.vo;

/**
 * <pre>
 * Class Name : LoginVO.java
 * 설명 : 로그인 정보 VO
 * 
 * Modification Information
 *    수정일      수정자              수정 내용
 *  ------------ -------------- --------------------------------
 *  2014. 10. 17.      CHOIILJI		최초작성
 * </pre>
 * 
 * @version : 1.0
 * @author : CHOIILJI(choiilji@irush.co.kr)
 * @since : 2014. 10. 17.
 */
public class LoginVO {
	private String emp_no;// 사번(로그인아이디)
	private String pass_word;// 비밀번호
	private String new_password;// 비밀번호
	private String out_CODE; // 프로시저 결과 코드
	private String out_MSG; // 프로시저 결과 메세지
	private String out_COUNT; // 프로시저 결과 카운트
	
	/**
	 * @return the emp_no
	 */
	public String getEmp_no() {
		return emp_no;
	}
	/**
	 * @param emp_no the emp_no to set
	 */
	public void setEmp_no(String emp_no) {
		this.emp_no = emp_no;
	}
	/**
	 * @return the pass_word
	 */
	public String getPass_word() {
		return pass_word;
	}
	/**
	 * @param pass_word the pass_word to set
	 */
	public void setPass_word(String pass_word) {
		this.pass_word = pass_word;
	}
	/**
	 * @return the new_password
	 */
	public String getNew_password() {
		return new_password;
	}
	/**
	 * @param new_password the new_password to set
	 */
	public void setNew_password(String new_password) {
		this.new_password = new_password;
	}
	/**
	 * @return the out_CODE
	 */
	public String getOut_CODE() {
		return out_CODE;
	}
	/**
	 * @param out_CODE the out_CODE to set
	 */
	public void setOut_CODE(String out_CODE) {
		this.out_CODE = out_CODE;
	}
	/**
	 * @return the out_MSG
	 */
	public String getOut_MSG() {
		return out_MSG;
	}
	/**
	 * @param out_MSG the out_MSG to set
	 */
	public void setOut_MSG(String out_MSG) {
		this.out_MSG = out_MSG;
	}
	/**
	 * @return the out_COUNT
	 */
	public String getOut_COUNT() {
		return out_COUNT;
	}
	/**
	 * @param out_COUNT the out_COUNT to set
	 */
	public void setOut_COUNT(String out_COUNT) {
		this.out_COUNT = out_COUNT;
	}
}
