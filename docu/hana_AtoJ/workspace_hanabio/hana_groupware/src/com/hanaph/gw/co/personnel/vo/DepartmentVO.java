/**
 * Hana Project
 * Copyright 2014 iRush Co.,
 *
 */
package com.hanaph.gw.co.personnel.vo;

/**
 * <pre>
 * Class Name : DepartmentVO.java
 * 설명 : 부서관련 정보
 * 
 * Modification Information
 *    수정일      수정자              수정 내용
 *  ------------ -------------- --------------------------------
 *  2014. 10. 15.      CHOIILJI
 * </pre>
 * 
 * @version : 1.0
 * @author : CHOIILJI(choiilji@irush.co.kr)
 * @since : 2014. 10. 15.
 */
public class DepartmentVO {
	private String dept_cd; // 부서코드
	private String dept_ko_nm; // 부서명(한글)
	private String dept_en_nm; // 부서명(영문)
	private String dept_nm1; // 부문명
	private int dept_amt; // 부운영비
	private String media_cd; // 원가구분
	private String use_yn; // 사용유무
	private String dept_owner; // 부서장
	private String old_dept_cd; // 이전부서코드
	private String up_dept_cd; // 상위부서코드
	private String oprt_cd; // 조직코드
	private String level_cd; // 부서레벨
	private int sort_no; // 정렬순서
	private String first_emp_no; // 최초작성자사번
	private String first_regdate; // 최초등록일자
	private String last_emp_no; // 최종수정자사번
	private String last_regdate; // 최종수정일자
	private String last_ip; // 최종작성IP
	private String hist_reason_cd; // 이력사유코드
	private String hist_reason_nm; // 이력사유코드
	private String sale_dept_cd; //
	private String up_dept_nm; // 상위부서명
	private String up_dept_gbn; // 상위부서 유무 구분
	private String hist_frdt;// 이력시작일
	private String hist_todt;// 이력종료일

	/**
	 * @return the dept_cd
	 */
	public String getDept_cd() {
		return dept_cd;
	}

	/**
	 * @param dept_cd
	 *            the dept_cd to set
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
	 * @param dept_ko_nm
	 *            the dept_ko_nm to set
	 */
	public void setDept_ko_nm(String dept_ko_nm) {
		this.dept_ko_nm = dept_ko_nm;
	}

	/**
	 * @return the dept_en_nm
	 */
	public String getDept_en_nm() {
		return dept_en_nm;
	}

	/**
	 * @param dept_en_nm
	 *            the dept_en_nm to set
	 */
	public void setDept_en_nm(String dept_en_nm) {
		this.dept_en_nm = dept_en_nm;
	}

	/**
	 * @return the dept_nm1
	 */
	public String getDept_nm1() {
		return dept_nm1;
	}

	/**
	 * @param dept_nm1
	 *            the dept_nm1 to set
	 */
	public void setDept_nm1(String dept_nm1) {
		this.dept_nm1 = dept_nm1;
	}

	/**
	 * @return the dept_amt
	 */
	public int getDept_amt() {
		return dept_amt;
	}

	/**
	 * @param dept_amt
	 *            the dept_amt to set
	 */
	public void setDept_amt(int dept_amt) {
		this.dept_amt = dept_amt;
	}

	/**
	 * @return the media_cd
	 */
	public String getMedia_cd() {
		return media_cd;
	}

	/**
	 * @param media_cd
	 *            the media_cd to set
	 */
	public void setMedia_cd(String media_cd) {
		this.media_cd = media_cd;
	}

	/**
	 * @return the use_yn
	 */
	public String getUse_yn() {
		return use_yn;
	}

	/**
	 * @param use_yn
	 *            the use_yn to set
	 */
	public void setUse_yn(String use_yn) {
		this.use_yn = use_yn;
	}

	/**
	 * @return the dept_owner
	 */
	public String getDept_owner() {
		return dept_owner;
	}

	/**
	 * @param dept_owner
	 *            the dept_owner to set
	 */
	public void setDept_owner(String dept_owner) {
		this.dept_owner = dept_owner;
	}

	/**
	 * @return the old_dept_cd
	 */
	public String getOld_dept_cd() {
		return old_dept_cd;
	}

	/**
	 * @param old_dept_cd
	 *            the old_dept_cd to set
	 */
	public void setOld_dept_cd(String old_dept_cd) {
		this.old_dept_cd = old_dept_cd;
	}

	/**
	 * @return the up_dept_cd
	 */
	public String getUp_dept_cd() {
		return up_dept_cd;
	}

	/**
	 * @param up_dept_cd
	 *            the up_dept_cd to set
	 */
	public void setUp_dept_cd(String up_dept_cd) {
		this.up_dept_cd = up_dept_cd;
	}

	/**
	 * @return the oprt_cd
	 */
	public String getOprt_cd() {
		return oprt_cd;
	}

	/**
	 * @param oprt_cd
	 *            the oprt_cd to set
	 */
	public void setOprt_cd(String oprt_cd) {
		this.oprt_cd = oprt_cd;
	}

	/**
	 * @return the level_cd
	 */
	public String getLevel_cd() {
		return level_cd;
	}

	/**
	 * @param level_cd
	 *            the level_cd to set
	 */
	public void setLevel_cd(String level_cd) {
		this.level_cd = level_cd;
	}

	/**
	 * @return the sort_no
	 */
	public int getSort_no() {
		return sort_no;
	}

	/**
	 * @param sort_no
	 *            the sort_no to set
	 */
	public void setSort_no(int sort_no) {
		this.sort_no = sort_no;
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

	/**
	 * @return the hist_reason_cd
	 */
	public String getHist_reason_cd() {
		return hist_reason_cd;
	}

	/**
	 * @param hist_reason_cd
	 *            the hist_reason_cd to set
	 */
	public void setHist_reason_cd(String hist_reason_cd) {
		this.hist_reason_cd = hist_reason_cd;
	}

	/**
	 * @return the hist_reason_nm
	 */
	public String getHist_reason_nm() {
		return hist_reason_nm;
	}

	/**
	 * @param hist_reason_nm
	 *            the hist_reason_nm to set
	 */
	public void setHist_reason_nm(String hist_reason_nm) {
		this.hist_reason_nm = hist_reason_nm;
	}

	/**
	 * @return the sale_dept_cd
	 */
	public String getSale_dept_cd() {
		return sale_dept_cd;
	}

	/**
	 * @param sale_dept_cd
	 *            the sale_dept_cd to set
	 */
	public void setSale_dept_cd(String sale_dept_cd) {
		this.sale_dept_cd = sale_dept_cd;
	}

	/**
	 * @return the up_dept_nm
	 */
	public String getUp_dept_nm() {
		return up_dept_nm;
	}

	/**
	 * @param up_dept_nm
	 *            the up_dept_nm to set
	 */
	public void setUp_dept_nm(String up_dept_nm) {
		this.up_dept_nm = up_dept_nm;
	}

	/**
	 * @return the up_dept_gbn
	 */
	public String getUp_dept_gbn() {
		return up_dept_gbn;
	}

	/**
	 * @param up_dept_gbn
	 *            the up_dept_gbn to set
	 */
	public void setUp_dept_gbn(String up_dept_gbn) {
		this.up_dept_gbn = up_dept_gbn;
	}

	/**
	 * @return the hist_frdt
	 */
	public String getHist_frdt() {
		return hist_frdt;
	}

	/**
	 * @param hist_frdt
	 *            the hist_frdt to set
	 */
	public void setHist_frdt(String hist_frdt) {
		this.hist_frdt = hist_frdt;
	}

	/**
	 * @return the hist_todt
	 */
	public String getHist_todt() {
		return hist_todt;
	}

	/**
	 * @param hist_todt
	 *            the hist_todt to set
	 */
	public void setHist_todt(String hist_todt) {
		this.hist_todt = hist_todt;
	}

}
