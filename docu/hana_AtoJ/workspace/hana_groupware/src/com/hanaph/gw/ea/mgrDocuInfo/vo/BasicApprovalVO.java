/**
 * Hana Project
 * Copyright 2014 iRush Co.,
 *
 */
package com.hanaph.gw.ea.mgrDocuInfo.vo;

import java.util.List;

/**
 * <pre>
 * Class Name : BasicApprovalVO.java
 * 설명 : 문서별 기본결재라인 VO
 * 
 * Modification Information
 *    수정일      수정자              수정 내용
 *  ------------ -------------- --------------------------------
 *  2014. 12. 23.      CHOIILJI
 * </pre>
 * 
 * @version : 1.0
 * @author : CHOIILJI(choiilji@irush.co.kr)
 * @since : 2014. 12. 23.
 */
public class BasicApprovalVO {
	private int docu_seq;// 문서seq
	private String emp_no;// 사원번호
	private String emp_ko_nm;// 사원이름
	private int ordering;// 결재순서
	private String confirm_yn;// 확인자여부
	private String create_dt;// 등록일시
	private String create_no;// 등록자
	
	private List<BasicApprovalVO> BasicApprovalVO;//
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
	 * @return the confirm_yn
	 */
	public String getConfirm_yn() {
		return confirm_yn;
	}
	/**
	 * @param confirm_yn the confirm_yn to set
	 */
	public void setConfirm_yn(String confirm_yn) {
		this.confirm_yn = confirm_yn;
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
	 * @return the basicApprovalVO
	 */
	public List<BasicApprovalVO> getBasicApprovalVO() {
		return BasicApprovalVO;
	}
	/**
	 * @param basicApprovalVO the basicApprovalVO to set
	 */
	public void setBasicApprovalVO(List<BasicApprovalVO> basicApprovalVO) {
		BasicApprovalVO = basicApprovalVO;
	}
}
