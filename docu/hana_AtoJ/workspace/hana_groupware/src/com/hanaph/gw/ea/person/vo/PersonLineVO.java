/**
 * Hana Project
 * Copyright 2014 iRush Co.,
 *
 */
package com.hanaph.gw.ea.person.vo;

/**
 * <pre>
 * Class Name : PersonLineVO.java
 * 설명 : 개인결재라인 Master VO
 * 
 * Modification Information
 *    수정일      수정자              수정 내용
 *  ------------ -------------- --------------------------------
 *  2014. 12. 30.      CHOIILJI
 * </pre>
 * 
 * @version : 1.0
 * @author  : CHOIILJI(choiilji@irush.co.kr)
 * @since   : 2014. 12. 30.
 */
public class PersonLineVO {

	private int person_seq;// 개인라인seq
	private String emp_no;// 사원번호
	private String create_dt;// 등록일시
	private String create_no;// 등록자
	private String subject;//제목
	/**
	 * @return the person_seq
	 */
	public int getPerson_seq() {
		return person_seq;
	}
	/**
	 * @param person_seq the person_seq to set
	 */
	public void setPerson_seq(int person_seq) {
		this.person_seq = person_seq;
	}
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
	 * @return the create_dt
	 */
	public String getCreate_dt() {
		return create_dt;
	}
	/**
	 * @param create_dt the create_dt to set
	 */
	public void setCreate_dt(String create_dt) {
		this.create_dt = create_dt;
	}
	/**
	 * @return the create_no
	 */
	public String getCreate_no() {
		return create_no;
	}
	/**
	 * @param create_no the create_no to set
	 */
	public void setCreate_no(String create_no) {
		this.create_no = create_no;
	}
	/**
	 * @return the subject
	 */
	public String getSubject() {
		return subject;
	}
	/**
	 * @param subject the subject to set
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}
	
}
