/**
  * Hana Project
  * Copyright 2014 iRush Co.,
  *
  */
package com.hanaph.gw.ea.mgrApproval.vo;

import com.hanaph.gw.ea.newReport.vo.ApprovalMasterVO;

/**
 * <pre>
 * Class Name : MgrReportVO.java
 * 설명 : 관리자 문서 목록 VO
 * 
 * Modification Information
 *    수정일      수정자              수정 내용
 *  ------------ -------------- --------------------------------
 *  2015. 1. 22.      CHOIILJI         
 * </pre>
 * 
 * @version : 1.0
 * @author  : CHOIILJI(choiilji@irush.co.kr)
 * @since   : 2015. 1. 22.
 */
public class MgrApprovalReportVO extends ApprovalMasterVO{
	
	private String approval_state;//결재상태
	private String approval_state_nm;//결재상태값
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
	 * @return the approval_state_nm
	 */
	public String getApproval_state_nm() {
		return approval_state_nm;
	}
	/**
	 * @param approval_state_nm the approval_state_nm to set
	 */
	public void setApproval_state_nm(String approval_state_nm) {
		this.approval_state_nm = approval_state_nm;
	}
	
	

}
