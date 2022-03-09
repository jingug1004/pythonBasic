/**
  * Hana Project
  * Copyright 2014 iRush Co.,
  *
  */
package com.hanaph.gw.of.common.vo;

/**
 * <pre>
 * Class Name : CommonTargetVo.java
 * 설명 : 읽은여부 (열람일시) VO
 * 
 * Modification Information
 *    수정일      수정자              수정 내용
 *  ------------ -------------- --------------------------------
 *  2014. 12. 16.      CHOIILJI         
 * </pre>
 * 
 * @version : 
 * @author  : CHOIILJI(choiilji@irush.co.kr)
 * @since   : 2014. 12. 16.
 */
public class CommonTargetVO {

	private String emp_no; // 사번
	private String emp_ko_nm; // 이름
	private String grad_cd; // 직급코드
	private String grad_ko_nm; // 직급명
	private String dept_cd; // 부서코드
	private String dept_ko_nm; // 부서명
	private String seq; //글 번호
	private String read_yn;//글 확인여부
	private String read_dt;//글 확인 일시
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
	 * @return the grad_cd
	 */
	public String getGrad_cd() {
		return grad_cd;
	}
	/**
	 * @param grad_cd the grad_cd to set
	 */
	public void setGrad_cd(String grad_cd) {
		this.grad_cd = grad_cd;
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
	 * @return the seq
	 */
	public String getSeq() {
		return seq;
	}
	/**
	 * @param seq the seq to set
	 */
	public void setSeq(String seq) {
		this.seq = seq;
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
	
}
