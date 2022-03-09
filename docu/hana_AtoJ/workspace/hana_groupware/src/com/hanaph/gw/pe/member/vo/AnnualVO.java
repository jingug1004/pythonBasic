/**
 * Hana Project
 * Copyright 2014 iRush Co.,
 *
 */
package com.hanaph.gw.pe.member.vo;

/**
 * <pre>
 * Class Name : AnnualVO.java
 * 설명 : 연차사용내역
 * 
 * Modification Information
 *    수정일      수정자              수정 내용
 *  ------------ -------------- --------------------------------
 *  2014. 10. 30.      CHOIILJI
 * </pre>
 * 
 * @version :
 * @author : CHOIILJI(choiilji@irush.co.kr)
 * @since : 2014. 10. 30.
 */
public class AnnualVO {
	private String rq_emp_no;// 사번
	private String apprv_seq;//문서번호
	private String rq_emp_ko_nm;//
	private String rq_vacat_cd;// 근태코드
	private String rq_vacat_nm;// 근태코드
	private String rq_fr_dt;// 시작일자
	private String rq_to_dt;// 종료일자
	private String rq_emp_nm;// 신청자 성명
	private float rq_wk_day;// 근태일수
	private String rq_remark;// 사유
	private String rq_apprv_dt;// 승인일-사용안함
	private String rq_apprv_emp_no;// 승인자 사번-사용안함-사용안함
	private String rq_apprv_yn;// 승인여부-사용안함
	private String approval_seq;// 결재번호-사용안함
	private String approval_flag;// 결재상태-사용안함
	private String first_emp_no;// 최초작성자 사번
	private String first_regdate;// 최초작성일자
	private String last_emp_no;// 최종수정자사번
	private String last_regdate;// 최종수정일자
	private String last_ip;// 최종작성IP
	private String dept_ko_nm; //부서명
	
	private String gubun;//휴가테이블 구분
	
	/**
	 * @return the rq_emp_no
	 */
	public String getRq_emp_no() {
		return rq_emp_no;
	}
	/**
	 * @param rq_emp_no the rq_emp_no to set
	 */
	public void setRq_emp_no(String rq_emp_no) {
		this.rq_emp_no = rq_emp_no;
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
	 * @return the rq_emp_ko_nm
	 */
	public String getRq_emp_ko_nm() {
		return rq_emp_ko_nm;
	}
	/**
	 * @param rq_emp_ko_nm the rq_emp_ko_nm to set
	 */
	public void setRq_emp_ko_nm(String rq_emp_ko_nm) {
		this.rq_emp_ko_nm = rq_emp_ko_nm;
	}
	/**
	 * @return the rq_vacat_cd
	 */
	public String getRq_vacat_cd() {
		return rq_vacat_cd;
	}
	/**
	 * @param rq_vacat_cd the rq_vacat_cd to set
	 */
	public void setRq_vacat_cd(String rq_vacat_cd) {
		this.rq_vacat_cd = rq_vacat_cd;
	}
	/**
	 * @return the rq_vacat_nm
	 */
	public String getRq_vacat_nm() {
		return rq_vacat_nm;
	}
	/**
	 * @param rq_vacat_nm the rq_vacat_nm to set
	 */
	public void setRq_vacat_nm(String rq_vacat_nm) {
		this.rq_vacat_nm = rq_vacat_nm;
	}
	/**
	 * @return the rq_fr_dt
	 */
	public String getRq_fr_dt() {
		return rq_fr_dt;
	}
	/**
	 * @param rq_fr_dt the rq_fr_dt to set
	 */
	public void setRq_fr_dt(String rq_fr_dt) {
		this.rq_fr_dt = rq_fr_dt;
	}
	/**
	 * @return the rq_to_dt
	 */
	public String getRq_to_dt() {
		return rq_to_dt;
	}
	/**
	 * @param rq_to_dt the rq_to_dt to set
	 */
	public void setRq_to_dt(String rq_to_dt) {
		this.rq_to_dt = rq_to_dt;
	}
	/**
	 * @return the rq_emp_nm
	 */
	public String getRq_emp_nm() {
		return rq_emp_nm;
	}
	/**
	 * @param rq_emp_nm the rq_emp_nm to set
	 */
	public void setRq_emp_nm(String rq_emp_nm) {
		this.rq_emp_nm = rq_emp_nm;
	}
	
	/**
	 * @return the rq_wk_day
	 */
	public float getRq_wk_day() {
		return rq_wk_day;
	}
	/**
	 * @param rq_wk_day the rq_wk_day to set
	 */
	public void setRq_wk_day(float rq_wk_day) {
		this.rq_wk_day = rq_wk_day;
	}
	/**
	 * @return the rq_remark
	 */
	public String getRq_remark() {
		return rq_remark;
	}
	/**
	 * @param rq_remark the rq_remark to set
	 */
	public void setRq_remark(String rq_remark) {
		this.rq_remark = rq_remark;
	}
	/**
	 * @return the rq_apprv_dt
	 */
	public String getRq_apprv_dt() {
		return rq_apprv_dt;
	}
	/**
	 * @param rq_apprv_dt the rq_apprv_dt to set
	 */
	public void setRq_apprv_dt(String rq_apprv_dt) {
		this.rq_apprv_dt = rq_apprv_dt;
	}
	/**
	 * @return the rq_apprv_emp_no
	 */
	public String getRq_apprv_emp_no() {
		return rq_apprv_emp_no;
	}
	/**
	 * @param rq_apprv_emp_no the rq_apprv_emp_no to set
	 */
	public void setRq_apprv_emp_no(String rq_apprv_emp_no) {
		this.rq_apprv_emp_no = rq_apprv_emp_no;
	}
	/**
	 * @return the rq_apprv_yn
	 */
	public String getRq_apprv_yn() {
		return rq_apprv_yn;
	}
	/**
	 * @param rq_apprv_yn the rq_apprv_yn to set
	 */
	public void setRq_apprv_yn(String rq_apprv_yn) {
		this.rq_apprv_yn = rq_apprv_yn;
	}
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
	 * @return the approval_flag
	 */
	public String getApproval_flag() {
		return approval_flag;
	}
	/**
	 * @param approval_flag the approval_flag to set
	 */
	public void setApproval_flag(String approval_flag) {
		this.approval_flag = approval_flag;
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
	 * @return the gubun
	 */
	public String getGubun() {
		return gubun;
	}
	/**
	 * @param gubun the gubun to set
	 */
	public void setGubun(String gubun) {
		this.gubun = gubun;
	}
}
