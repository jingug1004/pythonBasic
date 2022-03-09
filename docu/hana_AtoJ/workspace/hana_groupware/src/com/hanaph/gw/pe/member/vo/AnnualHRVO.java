/**
  * Hana Project
  * Copyright 2014 iRush Co.,
  *
  */
package com.hanaph.gw.pe.member.vo;

/**
 * <pre>
 * Class Name : AnnualHRVO.java
 * 설명 : 
 * 
 * Modification Information
 *    수정일      수정자              수정 내용
 *  ------------ -------------- --------------------------------
 *  2015. 3. 12.      ilji          
 * </pre>
 * 
 * @version : 
 * @author  : ilji(@irush.co.kr)
 * @since   : 2015. 3. 12.
 */
public class AnnualHRVO {
	private String emp_no;//사번
	private String apprv_date;//연차사용계획일자
	private String half_trem;//상하반기 구분(1상반기,2하반기)
	private String vacat_cd;//인사근태코드(연차일자에 근태등록시 사용)
	private String apprv_seq;//그룹웨어(20150311이후신규)문서번호
	private String remark;//사유(사용안함)
	private String first_emp_no;//최초작성자 사번
	private String first_regdate;//최초작성일자
	private String last_emp_no;//최종수정자사번 
	private String last_regdate;//최종수정일자
	private String last_ip;//최종작성IP
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
	 * @return the apprv_date
	 */
	public String getApprv_date() {
		return apprv_date;
	}
	/**
	 * @param apprv_date the apprv_date to set
	 */
	public void setApprv_date(String apprv_date) {
		this.apprv_date = apprv_date;
	}
	/**
	 * @return the half_trem
	 */
	public String getHalf_trem() {
		return half_trem;
	}
	/**
	 * @param half_trem the half_trem to set
	 */
	public void setHalf_trem(String half_trem) {
		this.half_trem = half_trem;
	}
	/**
	 * @return the vacat_cd
	 */
	public String getVacat_cd() {
		return vacat_cd;
	}
	/**
	 * @param vacat_cd the vacat_cd to set
	 */
	public void setVacat_cd(String vacat_cd) {
		this.vacat_cd = vacat_cd;
	}
	/**
	 * @return the apprv_seq
	 */
	public String getApprv_seq() {
		return apprv_seq;
	}
	/**
	 * @param apprv_seq the apprv_seq to set
	 */
	public void setApprv_seq(String apprv_seq) {
		this.apprv_seq = apprv_seq;
	}
	/**
	 * @return the remark
	 */
	public String getRemark() {
		return remark;
	}
	/**
	 * @param remark the remark to set
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}
	/**
	 * @return the first_emp_no
	 */
	public String getFirst_emp_no() {
		return first_emp_no;
	}
	/**
	 * @param first_emp_no the first_emp_no to set
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
	 * @param first_regdate the first_regdate to set
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
	 * @param last_emp_no the last_emp_no to set
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
	 * @param last_regdate the last_regdate to set
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
	 * @param last_ip the last_ip to set
	 */
	public void setLast_ip(String last_ip) {
		this.last_ip = last_ip;
	}
	
	
}
