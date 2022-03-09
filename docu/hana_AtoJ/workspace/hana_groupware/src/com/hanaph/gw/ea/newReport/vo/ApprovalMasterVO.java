/**
  * Hana Project
  * Copyright 2014 iRush Co.,
  *
  */
package com.hanaph.gw.ea.newReport.vo;

import com.hanaph.gw.co.common.vo.CommonVO;

/**
 * <pre>
 * Class Name : ApprovalMasterVO.java
 * 설명 : 결재마스 (문서 기본정보)
 * 
 * Modification Information
 *    수정일      수정자              수정 내용
 *  ------------ -------------- --------------------------------
 *  2015. 1. 5.      Jung.Jin.Muk          
 * </pre>
 * 
 * @version : 
 * @author  : Jung.Jin.Muk(pc123pc@irush.co.kr)
 * @since   : 2015. 1. 5.
 */
public class ApprovalMasterVO extends CommonVO{
	private String approval_seq;	//문서번호
	private int docu_seq;			//문서seq
	private String subject;			//제목
	private String make_dt; 		//작성일시
	private String state; 			//문서상태
	private String rejection_reason;//반려사유
	private String security_yn;		//대외비여부
	private String decide_yn;		//전결여부
	private String step_state;		//스탭상태
	private String docu_cd; 		//문서코드
	private String docu_state;		//문서결재상태
	private String req_dt; 			//기안일
	private String emp_ko_nm; 		// 이름
	private String docu_nm;			// 문서명
	private String dept_ko_nm; 		// 부서명(한글)
	private String state_nm; 		//문서상태이름
	private String last_line_nm;	//결재라인 마지막 결재자
	private String process_state; 	//현제 프로세스 상태
	private int cnt;				//메인 내가받은문서 카운트

	//	ml180116.ml03_T05 김진국 - ApprovalMasterVO.java에 mustopinion 멤버변수 추가(private String mustopinion;), Getter,Setter 추가 - 오브젝트를 GW_EA_APPROVAL_MASTER 테이블 DB와 맵핑하기 위해서
	private String mustopinion;		// 시행부서 의견 필수 유무
	
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
	 * @return the subject
	 */
	public String getSubject() {
		return subject;
	}
	/**
	 * @param subject the subject to set
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}
	/**
	 * @return the make_dt
	 */
	public String getMake_dt() {
		return make_dt;
	}
	/**
	 * @param make_dt the make_dt to set
	 */
	public void setMake_dt(String make_dt) {
		this.make_dt = make_dt;
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
	 * @return the rejection_reason
	 */
	public String getRejection_reason() {
		return rejection_reason;
	}
	/**
	 * @param rejection_reason the rejection_reason to set
	 */
	public void setRejection_reason(String rejection_reason) {
		this.rejection_reason = rejection_reason;
	}
	/**
	 * @return the security_yn
	 */
	public String getSecurity_yn() {
		return security_yn;
	}
	/**
	 * @param security_yn the security_yn to set
	 */
	public void setSecurity_yn(String security_yn) {
		this.security_yn = security_yn;
	}
	/**
	 * @return the decide_yn
	 */
	public String getDecide_yn() {
		return decide_yn;
	}
	/**
	 * @param decide_yn the decide_yn to set
	 */
	public void setDecide_yn(String decide_yn) {
		this.decide_yn = decide_yn;
	}
	/**
	 * @return the step_state
	 */
	public String getStep_state() {
		return step_state;
	}
	/**
	 * @param step_state the step_state to set
	 */
	public void setStep_state(String step_state) {
		this.step_state = step_state;
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
	 * @return the docu_state
	 */
	public String getDocu_state() {
		return docu_state;
	}
	/**
	 * @param docu_state the docu_state to set
	 */
	public void setDocu_state(String docu_state) {
		this.docu_state = docu_state;
	}
	/**
	 * @return the req_dt
	 */
	public String getReq_dt() {
		return req_dt;
	}
	/**
	 * @param req_dt the req_dt to set
	 */
	public void setReq_dt(String req_dt) {
		this.req_dt = req_dt;
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
	/**
	 * @return the last_line_nm
	 */
	public String getLast_line_nm() {
		return last_line_nm;
	}
	/**
	 * @param last_line_nm the last_line_nm to set
	 */
	public void setLast_line_nm(String last_line_nm) {
		this.last_line_nm = last_line_nm;
	}
	/**
	 * @return the process_state
	 */
	public String getProcess_state() {
		return process_state;
	}
	/**
	 * @param process_state the process_state to set
	 */
	public void setProcess_state(String process_state) {
		this.process_state = process_state;
	}
	/**
	 * @return the cnt
	 */
	public int getCnt() {
		return cnt;
	}
	/**
	 * @param cnt the cnt to set
	 */
	public void setCnt(int cnt) {
		this.cnt = cnt;
	}

	public String getMustopinion() {
		return mustopinion;
	}

	public void setMustopinion(String mustopinion) {
		this.mustopinion = mustopinion;
	}

	@java.lang.Override
	public java.lang.String toString() {
		return "ApprovalMasterVO{" +
				"approval_seq='" + approval_seq + '\'' +
				", docu_seq=" + docu_seq +
				", subject='" + subject + '\'' +
				", make_dt='" + make_dt + '\'' +
				", state='" + state + '\'' +
				", rejection_reason='" + rejection_reason + '\'' +
				", security_yn='" + security_yn + '\'' +
				", decide_yn='" + decide_yn + '\'' +
				", step_state='" + step_state + '\'' +
				", docu_cd='" + docu_cd + '\'' +
				", docu_state='" + docu_state + '\'' +
				", req_dt='" + req_dt + '\'' +
				", emp_ko_nm='" + emp_ko_nm + '\'' +
				", docu_nm='" + docu_nm + '\'' +
				", dept_ko_nm='" + dept_ko_nm + '\'' +
				", state_nm='" + state_nm + '\'' +
				", last_line_nm='" + last_line_nm + '\'' +
				", process_state='" + process_state + '\'' +
				", cnt=" + cnt +
				", mustopinion='" + mustopinion + '\'' +
				'}';
	}
}
