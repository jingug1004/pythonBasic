/**
  * Hana Project
  * Copyright 2014 iRush Co.,
  *
  */
package com.hanaph.gw.ea.mgrDocuInfo.vo;

import java.util.List;

/**
 * <pre>
 * Class Name : BasicImplDeptVO.java
 * 설명 : 시행부서 VO
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
public class BasicImplDeptVO {
	private int docu_seq;// 문서seq
	private String dept_cd;// 부서코드
	private String dept_ko_nm;// 부서이름
	private int ordering;// 협조순서
	private String create_dt;// 등록일시
	private String create_no;// 등록자
	
	private List<BasicImplDeptVO> BasicImplDeptVO;// BasicImplDeptVOList
	/**
	 * @return the docu_seq
	 */
	public int getDocu_seq() {
		return docu_seq;
	}
	/**
	 * @param docu_seq the docu_seq to set
	 */
	public void setDocu_seq(int docu_seq) {
		this.docu_seq = docu_seq;
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
	 * @return the basicImplDeptVO
	 */
	public List<BasicImplDeptVO> getBasicImplDeptVO() {
		return BasicImplDeptVO;
	}
	/**
	 * @param basicImplDeptVO the basicImplDeptVO to set
	 */
	public void setBasicImplDeptVO(List<BasicImplDeptVO> basicImplDeptVO) {
		BasicImplDeptVO = basicImplDeptVO;
	}
	
}
