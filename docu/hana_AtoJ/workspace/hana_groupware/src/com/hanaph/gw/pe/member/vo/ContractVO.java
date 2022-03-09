/**
 * Hana Project
 * Copyright 2014 iRush Co.,
 *
 */
package com.hanaph.gw.pe.member.vo;

/**
 * <pre>
 * Class Name : ContractVO.java
 * 설명 : 연봉계약서 HR_PY_ANNUAL_SALARY
 * 
 * Modification Information
 *    수정일      수정자              수정 내용
 *  ------------ -------------- --------------------------------
 *  2014. 11. 4.      CHOIILJI
 * </pre>
 * 
 * @version :
 * @author : CHOIILJI(choiilji@irush.co.kr)
 * @since : 2014. 11. 4.
 */
public class ContractVO {
	private String emp_ko_nm;//성명
	private String dept_ko_nm;//부서명
	private String grad_ko_nm;//직급명
	private String bizplc_nm;//회사명
	private String bizplc_addr;//회사주소
	private String bizplc_prsd;//대표이사
	private String ymd_output_ko;//연봉계약서 출력일
	private String ymd_start_ko;//연봉계약서 시작일
	private String ymd_end_ko;//연봉계약서 종료일
	
	private String emp_no;// 사번
	private String ymd_start;// 연봉계약서 시작일
	private String ymd_end;// 연봉계약서 종료일
	private String ymd_output;// 연봉계약서 출력일
	private int work_months;// 근무 개월
	private int annual_salary;// 연봉
	private int severance_pay;// 퇴직금
	private String bigo;//
	private String first_emp_no;// 최초작성자사번
	private String first_regdate;// 최초등록일자
	private String last_emp_no;// 최종수정자사번
	private String last_regdate;// 최종수정일자
	private String last_ip;// 최종작성IP
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
	 * @return the bizplc_nm
	 */
	public String getBizplc_nm() {
		return bizplc_nm;
	}
	/**
	 * @param bizplc_nm the bizplc_nm to set
	 */
	public void setBizplc_nm(String bizplc_nm) {
		this.bizplc_nm = bizplc_nm;
	}
	/**
	 * @return the bizplc_addr
	 */
	public String getBizplc_addr() {
		return bizplc_addr;
	}
	/**
	 * @param bizplc_addr the bizplc_addr to set
	 */
	public void setBizplc_addr(String bizplc_addr) {
		this.bizplc_addr = bizplc_addr;
	}
	/**
	 * @return the bizplc_prsd
	 */
	public String getBizplc_prsd() {
		return bizplc_prsd;
	}
	/**
	 * @param bizplc_prsd the bizplc_prsd to set
	 */
	public void setBizplc_prsd(String bizplc_prsd) {
		this.bizplc_prsd = bizplc_prsd;
	}
	/**
	 * @return the ymd_output_ko
	 */
	public String getYmd_output_ko() {
		return ymd_output_ko;
	}
	/**
	 * @param ymd_output_ko the ymd_output_ko to set
	 */
	public void setYmd_output_ko(String ymd_output_ko) {
		this.ymd_output_ko = ymd_output_ko;
	}
	/**
	 * @return the ymd_start_ko
	 */
	public String getYmd_start_ko() {
		return ymd_start_ko;
	}
	/**
	 * @param ymd_start_ko the ymd_start_ko to set
	 */
	public void setYmd_start_ko(String ymd_start_ko) {
		this.ymd_start_ko = ymd_start_ko;
	}
	/**
	 * @return the ymd_end_ko
	 */
	public String getYmd_end_ko() {
		return ymd_end_ko;
	}
	/**
	 * @param ymd_end_ko the ymd_end_ko to set
	 */
	public void setYmd_end_ko(String ymd_end_ko) {
		this.ymd_end_ko = ymd_end_ko;
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
	 * @return the ymd_start
	 */
	public String getYmd_start() {
		return ymd_start;
	}
	/**
	 * @param ymd_start the ymd_start to set
	 */
	public void setYmd_start(String ymd_start) {
		this.ymd_start = ymd_start;
	}
	/**
	 * @return the ymd_end
	 */
	public String getYmd_end() {
		return ymd_end;
	}
	/**
	 * @param ymd_end the ymd_end to set
	 */
	public void setYmd_end(String ymd_end) {
		this.ymd_end = ymd_end;
	}
	/**
	 * @return the ymd_output
	 */
	public String getYmd_output() {
		return ymd_output;
	}
	/**
	 * @param ymd_output the ymd_output to set
	 */
	public void setYmd_output(String ymd_output) {
		this.ymd_output = ymd_output;
	}
	/**
	 * @return the work_months
	 */
	public int getWork_months() {
		return work_months;
	}
	/**
	 * @param work_months the work_months to set
	 */
	public void setWork_months(int work_months) {
		this.work_months = work_months;
	}
	/**
	 * @return the annual_salary
	 */
	public int getAnnual_salary() {
		return annual_salary;
	}
	/**
	 * @param annual_salary the annual_salary to set
	 */
	public void setAnnual_salary(int annual_salary) {
		this.annual_salary = annual_salary;
	}
	/**
	 * @return the severance_pay
	 */
	public int getSeverance_pay() {
		return severance_pay;
	}
	/**
	 * @param severance_pay the severance_pay to set
	 */
	public void setSeverance_pay(int severance_pay) {
		this.severance_pay = severance_pay;
	}
	/**
	 * @return the bigo
	 */
	public String getBigo() {
		return bigo;
	}
	/**
	 * @param bigo the bigo to set
	 */
	public void setBigo(String bigo) {
		this.bigo = bigo;
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
