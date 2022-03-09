/**
  * Hana Project
  * Copyright 2014 iRush Co.,
  *
  */
package com.hanaph.gw.ea.newReport.vo;

import java.util.List;

/**
 * <pre>
 * Class Name : ImplDeptEmpVO.java
 * 설명 : 문서별 시행부서 직원 VO
 * 
 * Modification Information
 *    수정일      수정자              수정 내용
 *  ------------ -------------- --------------------------------
 *  2015. 1. 16.      CHOIILJI         
 * </pre>
 * 
 * @version : 
 * @author  : CHOIILJI(choiilji@irush.co.kr)
 * @since   : 2015. 1. 16.
 */
public class ImplDeptEmpVO {

	private String approval_seq;//문서번호
	private String dept_cd;//부서코드
	private String emp_no;//사원번호
	private String read_yn;//열람여부
	private String read_dt;//열람시간
	private String create_dt;//등록일시
	private String create_no;//등록자
	private String complete_yn;//시행완료 여부
	private String complete_dt;//시행완료 일시
	private String complete_note;//시행완료시 메모
	private String interest_yn;//관심문서 여부
	
	private String emp_ko_nm;// 사원이름
	private String dept_ko_nm;//부서이름
	private String grad_ko_nm; // 직급명
	
	List<ImplDeptEmpVO> ImplDeptEmpVO;//ImplDeptEmpVOList 

	/**
	 * @return the approval_seq
	 */
	public String getApproval_seq() {
		return approval_seq;
	}

	/**
	 * @param approval_seq the approval_seq to set
	 */
	public void setApproval_seq(String approval_seq) {
		this.approval_seq = approval_seq;
	}

	/**
	 * @return the dept_cd
	 */
	public String getDept_cd() {
		return dept_cd;
	}

	/**
	 * @param dept_cd the dept_cd to set
	 */
	public void setDept_cd(String dept_cd) {
		this.dept_cd = dept_cd;
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
	 * @return the read_yn
	 */
	public String getRead_yn() {
		return read_yn;
	}

	/**
	 * @param read_yn the read_yn to set
	 */
	public void setRead_yn(String read_yn) {
		this.read_yn = read_yn;
	}

	/**
	 * @return the read_dt
	 */
	public String getRead_dt() {
		return read_dt;
	}

	/**
	 * @param read_dt the read_dt to set
	 */
	public void setRead_dt(String read_dt) {
		this.read_dt = read_dt;
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
	 * @return the complete_yn
	 */
	public String getComplete_yn() {
		return complete_yn;
	}

	/**
	 * @param complete_yn the complete_yn to set
	 */
	public void setComplete_yn(String complete_yn) {
		this.complete_yn = complete_yn;
	}

	/**
	 * @return the complete_dt
	 */
	public String getComplete_dt() {
		return complete_dt;
	}

	/**
	 * @param complete_dt the complete_dt to set
	 */
	public void setComplete_dt(String complete_dt) {
		this.complete_dt = complete_dt;
	}

	/**
	 * @return the complete_note
	 */
	public String getComplete_note() {
		return complete_note;
	}

	/**
	 * @param complete_note the complete_note to set
	 */
	public void setComplete_note(String complete_note) {
		this.complete_note = complete_note;
	}

	/**
	 * @return the interest_yn
	 */
	public String getInterest_yn() {
		return interest_yn;
	}

	/**
	 * @param interest_yn the interest_yn to set
	 */
	public void setInterest_yn(String interest_yn) {
		this.interest_yn = interest_yn;
	}

	/**
	 * @return the emp_ko_nm
	 */
	public String getEmp_ko_nm() {
		return emp_ko_nm;
	}

	/**
	 * @param emp_ko_nm the emp_ko_nm to set
	 */
	public void setEmp_ko_nm(String emp_ko_nm) {
		this.emp_ko_nm = emp_ko_nm;
	}

	/**
	 * @return the dept_ko_nm
	 */
	public String getDept_ko_nm() {
		return dept_ko_nm;
	}

	/**
	 * @param dept_ko_nm the dept_ko_nm to set
	 */
	public void setDept_ko_nm(String dept_ko_nm) {
		this.dept_ko_nm = dept_ko_nm;
	}

	/**
	 * @return the grad_ko_nm
	 */
	public String getGrad_ko_nm() {
		return grad_ko_nm;
	}

	/**
	 * @param grad_ko_nm the grad_ko_nm to set
	 */
	public void setGrad_ko_nm(String grad_ko_nm) {
		this.grad_ko_nm = grad_ko_nm;
	}

	/**
	 * @return the implDeptEmpVO
	 */
	public List<ImplDeptEmpVO> getImplDeptEmpVO() {
		return ImplDeptEmpVO;
	}

	/**
	 * @param implDeptEmpVO the implDeptEmpVO to set
	 */
	public void setImplDeptEmpVO(List<ImplDeptEmpVO> implDeptEmpVO) {
		ImplDeptEmpVO = implDeptEmpVO;
	}

}
