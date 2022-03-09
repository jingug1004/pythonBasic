/**
  * Hana Project
  * Copyright 2014 iRush Co.,
  *
  */
package com.hanaph.gw.ea.report.vo;

import com.hanaph.gw.co.common.vo.CommonVO;

/**
 * <pre>
 * Class Name : ReportVO.java
 * 설명 : 
 * 
 * Modification Information
 *    수정일      수정자              수정 내용
 *  ------------ -------------- --------------------------------
 *  2015. 1. 20.      Jung.Jin.Muk          
 * </pre>
 * 
 * @version : 
 * @author  : Jung.Jin.Muk(pc123pc@irush.co.kr)
 * @since   : 2015. 1. 20.
 */
public class ReportVO extends CommonVO{
	private String approval_seq;	//문서번호
	private String docu_nm;			//문서명
	private String docu_seq;		//문서번호
	private String docu_cd;			//문서코드
	private String subject;			//제목
	private String make_dt;			//작성일
	private String step_state;		//스텝상태
	private String req_dt; 			//기안일
	private String state; 			//문서상태코드
	private String state_nm; 		//문서상태이름
	private String last_line_nm;    //결재라인 마지막 결재자
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
	 * @return the docu_seq
	 */
	public String getDocu_seq() {
		return docu_seq;
	}
	/**
	 * @param docu_seq the docu_seq to set
	 */
	public void setDocu_seq(String docu_seq) {
		this.docu_seq = docu_seq;
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
		
}
