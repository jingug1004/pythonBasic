/**
  * Hana Project
  * Copyright 2014 iRush Co.,
  *
  */
package com.hanaph.gw.hr.personnel.vo;

/**
 * <pre>
 * Class Name : PersonnelVO.java
 * 설명 : 인사현황 정보 VO
 * 
 * Modification Information
 *    수정일      수정자              수정 내용
 *  ------------ -------------- --------------------------------
 *  2014. 10. 23.      CHOIILJI         
 * </pre>
 * 
 * @version : 1.0
 * @author  : CHOIILJI(choiilji@irush.co.kr)
 * @since   : 2014. 10. 23.
 */
public class PersonnelVO {
	private String dept_nm;//부서명
	private int personnel_cnt;//부서별직원수
	private String encmpy_cd;//구분
	private int today_cnt;//오늘 발령현황
	private int month_cnt;//이달 발령현황  
	private int year_cnt;//올해 발령현황
	private int last_year_cnt;//지난해 발령현황
	/**
	 * @return the dept_nm
	 */
	public String getDept_nm() {
		return dept_nm;
	}
	/**
	 * @param dept_nm the dept_nm to set
	 */
	public void setDept_nm(String dept_nm) {
		this.dept_nm = dept_nm;
	}
	/**
	 * @return the personnel_cnt
	 */
	public int getPersonnel_cnt() {
		return personnel_cnt;
	}
	/**
	 * @param personnel_cnt the personnel_cnt to set
	 */
	public void setPersonnel_cnt(int personnel_cnt) {
		this.personnel_cnt = personnel_cnt;
	}
	
	/**
	 * @return the encmpy_cd
	 */
	public String getEncmpy_cd() {
		return encmpy_cd;
	}
	/**
	 * @param encmpy_cd the encmpy_cd to set
	 */
	public void setEncmpy_cd(String encmpy_cd) {
		this.encmpy_cd = encmpy_cd;
	}
	/**
	 * @return the today_cnt
	 */
	public int getToday_cnt() {
		return today_cnt;
	}
	/**
	 * @param today_cnt the today_cnt to set
	 */
	public void setToday_cnt(int today_cnt) {
		this.today_cnt = today_cnt;
	}
	/**
	 * @return the month_cnt
	 */
	public int getMonth_cnt() {
		return month_cnt;
	}
	/**
	 * @param month_cnt the month_cnt to set
	 */
	public void setMonth_cnt(int month_cnt) {
		this.month_cnt = month_cnt;
	}
	/**
	 * @return the year_cnt
	 */
	public int getYear_cnt() {
		return year_cnt;
	}
	/**
	 * @param year_cnt the year_cnt to set
	 */
	public void setYear_cnt(int year_cnt) {
		this.year_cnt = year_cnt;
	}
	/**
	 * @return the last_year_cnt
	 */
	public int getLast_year_cnt() {
		return last_year_cnt;
	}
	/**
	 * @param last_year_cnt the last_year_cnt to set
	 */
	public void setLast_year_cnt(int last_year_cnt) {
		this.last_year_cnt = last_year_cnt;
	}
	
	
}
