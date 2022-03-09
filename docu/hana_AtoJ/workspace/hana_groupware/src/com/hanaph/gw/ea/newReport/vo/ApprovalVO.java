/**
 * Hana Project
 * Copyright 2014 iRush Co.,
 *
 */
package com.hanaph.gw.ea.newReport.vo;

import java.util.List;

/**
 * <pre>
 * Class Name : ApprovalVO.java
 * 설명 : step2.결재라인지정 VO
 * 
 * Modification Information
 *    수정일      수정자              수정 내용
 *  ------------ -------------- --------------------------------
 *  2014. 12. 30.      CHOIILJI
 * </pre>
 * 
 * @version : 1.0
 * @author : CHOIILJI(choiilji@irush.co.kr)
 * @since : 2014. 12. 30.
 */
public class ApprovalVO {
	private String emp_no;// 사원번호
	private String emp_ko_nm;// 사원이름
	private String approval_seq;// _seq 문서번호
	private String state;// 결재상태
	private String approval_dt;// 결재일시
	private int ordering;// 결재순서
	private String confirm_yn;// 열확인자여부
	private String read_dt;// 열람일시
	private String create_dt;// 등록일시
	private String create_no;// 등록자
	private String dept_nm; //부서명
	private String state_nm; //상태명
	private String group_div; //그룹구분-물품 구입 청구확인서 결재라인 2번
	
	private List<ApprovalVO> ApprovalVO;// ApprovalVOList
	
	private String grad_ko_nm;//
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
	 * @return the state
	 */
	public String getState() {
		return state;
	}
	/**
	 * @param state the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}
	/**
	 * @return the approval_dt
	 */
	public String getApproval_dt() {
		return approval_dt;
	}
	/**
	 * @param approval_dt the approval_dt to set
	 */
	public void setApproval_dt(String approval_dt) {
		this.approval_dt = approval_dt;
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
	 * @return the approvalVO
	 */
	public List<ApprovalVO> getApprovalVO() {
		return ApprovalVO;
	}
	/**
	 * @param approvalVO the approvalVO to set
	 */
	public void setApprovalVO(List<ApprovalVO> approvalVO) {
		ApprovalVO = approvalVO;
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
	 * @return the state_nm
	 */
	public String getState_nm() {
		return state_nm;
	}
	/**
	 * @param state_nm the state_nm to set
	 */
	public void setState_nm(String state_nm) {
		this.state_nm = state_nm;
	}
	public String getGroup_div() {
		return group_div;
	}
	public void setGroup_div(String group_div) {
		this.group_div = group_div;
	}
	
	
}
