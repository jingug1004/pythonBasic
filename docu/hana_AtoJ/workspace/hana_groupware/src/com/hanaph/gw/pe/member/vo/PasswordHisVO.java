/**
 * Hana Project
 * Copyright 2014 iRush Co.,
 *
 */
package com.hanaph.gw.pe.member.vo;

/**
 * <pre>
 * Class Name : PasswordHisVO.java
 * 설명 : 패스워드 히스토리
 * 
 * Modification Information
 *    수정일      수정자              수정 내용
 *  ------------ -------------- --------------------------------
 *  2014. 10. 27.      CHOIILJI
 * </pre>
 * 
 * @version :
 * @author : CHOIILJI(choiilji@irush.co.kr)
 * @since : 2014. 10. 27.
 */
public class PasswordHisVO {
	private String emp_no; // 사번
	private int seq; // 순번
	private String pw_expire_dt; // 비밀번호종료시점
	private String password; // 비밀번호
	private String first_emp_no; // 최초작성자
	private String first_regdate; // 최초작성일
	private String last_emp_no; // 최종작성자
	private String last_regdate; // 최종작성일
	private String last_ip; // 최종접속IP

	/**
	 * @return the emp_no
	 */
	public String getEmp_no() {
		return emp_no;
	}

	/**
	 * @param emp_no
	 *            the emp_no to set
	 */
	public void setEmp_no(String emp_no) {
		this.emp_no = emp_no;
	}

	/**
	 * @return the seq
	 */
	public int getSeq() {
		return seq;
	}

	/**
	 * @param seq
	 *            the seq to set
	 */
	public void setSeq(int seq) {
		this.seq = seq;
	}

	/**
	 * @return the pw_expire_dt
	 */
	public String getPw_expire_dt() {
		return pw_expire_dt;
	}

	/**
	 * @param pw_expire_dt
	 *            the pw_expire_dt to set
	 */
	public void setPw_expire_dt(String pw_expire_dt) {
		this.pw_expire_dt = pw_expire_dt;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password
	 *            the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the first_emp_no
	 */
	public String getFirst_emp_no() {
		return first_emp_no;
	}

	/**
	 * @param first_emp_no
	 *            the first_emp_no to set
	 */
	public void setFirst_emp_no(String first_emp_no) {
		this.first_emp_no = first_emp_no;
	}

	/**
	 * @return the first_regdate
	 */
	public String getFirst_regdate() {
		return first_regdate;
	}

	/**
	 * @param first_regdate
	 *            the first_regdate to set
	 */
	public void setFirst_regdate(String first_regdate) {
		this.first_regdate = first_regdate;
	}

	/**
	 * @return the last_emp_no
	 */
	public String getLast_emp_no() {
		return last_emp_no;
	}

	/**
	 * @param last_emp_no
	 *            the last_emp_no to set
	 */
	public void setLast_emp_no(String last_emp_no) {
		this.last_emp_no = last_emp_no;
	}

	/**
	 * @return the last_regdate
	 */
	public String getLast_regdate() {
		return last_regdate;
	}

	/**
	 * @param last_regdate
	 *            the last_regdate to set
	 */
	public void setLast_regdate(String last_regdate) {
		this.last_regdate = last_regdate;
	}

	/**
	 * @return the last_ip
	 */
	public String getLast_ip() {
		return last_ip;
	}

	/**
	 * @param last_ip
	 *            the last_ip to set
	 */
	public void setLast_ip(String last_ip) {
		this.last_ip = last_ip;
	}

}
