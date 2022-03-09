/**
  * Hana Project
  * Copyright 2014 iRush Co.,
  *
  */
package com.hanaph.gw.ea.person.vo;

import java.util.List;

/**
 * <pre>
 * Class Name : PersonImplDeptVO.java
 * 설명 : 개인 시행부서 VO
 * 
 * Modification Information
 *    수정일      수정자              수정 내용
 *  ------------ -------------- --------------------------------
 *  2015. 1. 8.      CHOIILJI         
 * </pre>
 * 
 * @version : 1.0
 * @author  : CHOIILJI(choiilji@irush.co.kr)
 * @since   : 2015. 1. 8.
 */
public class PersonImplDeptVO {
	private String dept_cd;// 부서코드
	private String dept_ko_nm;// 부서이름
	private int person_seq;// 개인라인seq
	private int ordering;// 협조순서
	private String create_dt;// 등록일시
	private String create_no;// 등록자
	
	List <PersonImplDeptVO>PersonImplDeptVO;//PersonImplDeptVOList
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
	 * @return the ordering
	 */
	public int getOrdering() {
		return ordering;
	}
	/**
	 * @param ordering the ordering to set
	 */
	public void setOrdering(int ordering) {
		this.ordering = ordering;
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
	 * @return the personImplDeptVO
	 */
	public List<PersonImplDeptVO> getPersonImplDeptVO() {
		return PersonImplDeptVO;
	}
	/**
	 * @param personImplDeptVO the personImplDeptVO to set
	 */
	public void setPersonImplDeptVO(List<PersonImplDeptVO> personImplDeptVO) {
		PersonImplDeptVO = personImplDeptVO;
	}
	
	
}
