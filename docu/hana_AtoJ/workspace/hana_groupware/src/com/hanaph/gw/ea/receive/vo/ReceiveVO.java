/**
  * Hana Project
  * Copyright 2014 iRush Co.,
  *
  */
package com.hanaph.gw.ea.receive.vo;

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
public class ReceiveVO extends CommonVO {
	private String approval_seq;	//문서번호
	private String docu_nm;			//문서명
	private String subject;			//제목
	private String req_nm;			//기안자
	private String req_dt; 			//기안일
	private String docu_cd; 		//문서상태코드
	private String docu_state; 		//문서상태이름
	private String approval_state; 	//결재상태이름
	private String last_line_nm;    //결재라인 마지막 결재자
	private String state;			//문서상태
	private String gubun;			//결재 구분
	private int ordering;			//현재순서
	private int maxorder;			//마지막결재순서
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
	 * @return the req_nm
	 */
	public String getReq_nm() {
		return req_nm;
	}
	/**
	 * @param req_nm the req_nm to set
	 */
	public void setReq_nm(String req_nm) {
		this.req_nm = req_nm;
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
	 * @return the approval_state
	 */
	public String getApproval_state() {
		return approval_state;
	}
	/**
	 * @param approval_state the approval_state to set
	 */
	public void setApproval_state(String approval_state) {
		this.approval_state = approval_state;
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
	 * @return the maxorder
	 */
	public int getMaxorder() {
		return maxorder;
	}
	/**
	 * @param maxorder the maxorder to set
	 */
	public void setMaxorder(int maxorder) {
		this.maxorder = maxorder;
	}
	
}
