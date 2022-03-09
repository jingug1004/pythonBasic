/**
  * Hana Project
  * Copyright 2014 iRush Co.,
  *
  */
package com.hanaph.gw.ea.implement.vo;

import com.hanaph.gw.ea.newReport.vo.ApprovalMasterVO;

/**
 * <pre>
 * Class Name : ImplementReportVO.java
 * 설명 : 시행문서조회 VO
 * 
 * Modification Information
 *    수정일      수정자              수정 내용
 *  ------------ -------------- --------------------------------
 *  2015. 1. 16.      CHOIILJI         
 * </pre>
 * 
 * @version : 1.0
 * @author  : CHOIILJI(choiilji@irush.co.kr)
 * @since   : 2015. 1. 16.
 */
public class ImplementReportVO extends ApprovalMasterVO{

	private String read_yn; //열람여부
	private String read_dt; //열람시간
	private String docu_nm; //문서명 	
	private String docu_cd; //문서코드 
	private String emp_ko_nm; //부서명
	private String interest_yn ; //
	private String option_read_yn;	//의견 있는지 없는지 여부
	 
	private int share_target_cnt;//공유대상자 카운트
	private int share_target_read_cnt;//공유대상자 읽은 카운트
	private int impl_dept_cnt;//시행부서 카운트
	private int impl_dept_read_cnt;//시행부서 카운트
	private int overtime_detail_cnt;//시간외 근무 내역서 카운트
	private String overtime_detail_seq;//시간외 근무 내역서 approvql_seq
	private String impl_dept_complete_yn;//부서별 시행완료 여부
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
	 * @return the docu_nm
	 */
	public String getDocu_nm() {
		return docu_nm;
	}
	/**
	 * @param docu_nm the docu_nm to set
	 */
	public void setDocu_nm(String docu_nm) {
		this.docu_nm = docu_nm;
	}
	/**
	 * @return the docu_cd
	 */
	public String getDocu_cd() {
		return docu_cd;
	}
	/**
	 * @param docu_cd the docu_cd to set
	 */
	public void setDocu_cd(String docu_cd) {
		this.docu_cd = docu_cd;
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
	 * @return the option_read_yn
	 */
	public String getOption_read_yn() {
		return option_read_yn;
	}
	/**
	 * @param option_read_yn the option_read_yn to set
	 */
	public void setOption_read_yn(String option_read_yn) {
		this.option_read_yn = option_read_yn;
	}
	/**
	 * @return the share_target_cnt
	 */
	public int getShare_target_cnt() {
		return share_target_cnt;
	}
	/**
	 * @param share_target_cnt the share_target_cnt to set
	 */
	public void setShare_target_cnt(int share_target_cnt) {
		this.share_target_cnt = share_target_cnt;
	}
	/**
	 * @return the share_target_read_cnt
	 */
	public int getShare_target_read_cnt() {
		return share_target_read_cnt;
	}
	/**
	 * @param share_target_read_cnt the share_target_read_cnt to set
	 */
	public void setShare_target_read_cnt(int share_target_read_cnt) {
		this.share_target_read_cnt = share_target_read_cnt;
	}
	/**
	 * @return the impl_dept_cnt
	 */
	public int getImpl_dept_cnt() {
		return impl_dept_cnt;
	}
	/**
	 * @param impl_dept_cnt the impl_dept_cnt to set
	 */
	public void setImpl_dept_cnt(int impl_dept_cnt) {
		this.impl_dept_cnt = impl_dept_cnt;
	}
	/**
	 * @return the impl_dept_read_cnt
	 */
	public int getImpl_dept_read_cnt() {
		return impl_dept_read_cnt;
	}
	/**
	 * @param impl_dept_read_cnt the impl_dept_read_cnt to set
	 */
	public void setImpl_dept_read_cnt(int impl_dept_read_cnt) {
		this.impl_dept_read_cnt = impl_dept_read_cnt;
	}
	/**
	 * @return the overtime_detail_cnt
	 */
	public int getOvertime_detail_cnt() {
		return overtime_detail_cnt;
	}
	/**
	 * @param overtime_detail_cnt the overtime_detail_cnt to set
	 */
	public void setOvertime_detail_cnt(int overtime_detail_cnt) {
		this.overtime_detail_cnt = overtime_detail_cnt;
	}
	/**
	 * @return the overtime_detail_seq
	 */
	public String getOvertime_detail_seq() {
		return overtime_detail_seq;
	}
	/**
	 * @param overtime_detail_seq the overtime_detail_seq to set
	 */
	public void setOvertime_detail_seq(String overtime_detail_seq) {
		this.overtime_detail_seq = overtime_detail_seq;
	}
	/**
	 * @return the impl_dept_complete_yn
	 */
	public String getImpl_dept_complete_yn() {
		return impl_dept_complete_yn;
	}
	/**
	 * @param impl_dept_complete_yn the impl_dept_complete_yn to set
	 */
	public void setImpl_dept_complete_yn(String impl_dept_complete_yn) {
		this.impl_dept_complete_yn = impl_dept_complete_yn;
	}
	
	
	
}
