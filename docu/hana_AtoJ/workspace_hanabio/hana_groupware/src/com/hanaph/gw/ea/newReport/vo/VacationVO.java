/**
  * Hana Project
  * Copyright 2014 iRush Co.,
  *
  */
package com.hanaph.gw.ea.newReport.vo;

/**
 * <pre>
 * Class Name : newReport.java
 * 설명 : 휴가신청서
 * 
 * Modification Information
 *    수정일      수정자              수정 내용
 *  ------------ -------------- --------------------------------
 *  2014. 12. 29.      Jung.Jin.Muk          
 * </pre>
 * 
 * @version : 
 * @author  : Jung.Jin.Muk(pc123pc@irush.co.kr)
 * @since   : 2014. 12. 29.
 */
public class VacationVO {
	private String approval_seq;	//문서번호
	private String vacation_kind;	//휴가종류
	private String cd; 				//휴가종류코드
	private String start_ymd;		//휴가시작일자
	private String end_ymd;			//휴가종료일자
	private String reason;			//휴가사유
	private String contact_number;	//비상연락처
	private float holiday_cnt;      //등록된 휴일 계산
	
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
	 * @return the vacation_kind
	 */
	public String getVacation_kind() {
		return vacation_kind;
	}
	/**
	 * @param vacation_kind the vacation_kind to set
	 */
	public void setVacation_kind(String vacation_kind) {
		this.vacation_kind = vacation_kind;
	}
	/**
	 * @return the cd
	 */
	public String getCd() {
		return cd;
	}
	/**
	 * @param cd the cd to set
	 */
	public void setCd(String cd) {
		this.cd = cd;
	}
	/**
	 * @return the start_ymd
	 */
	public String getStart_ymd() {
		return start_ymd;
	}
	/**
	 * @param start_ymd the start_ymd to set
	 */
	public void setStart_ymd(String start_ymd) {
		this.start_ymd = start_ymd;
	}
	/**
	 * @return the end_ymd
	 */
	public String getEnd_ymd() {
		return end_ymd;
	}
	/**
	 * @param end_ymd the end_ymd to set
	 */
	public void setEnd_ymd(String end_ymd) {
		this.end_ymd = end_ymd;
	}
	/**
	 * @return the reason
	 */
	public String getReason() {
		return reason;
	}
	/**
	 * @param reason the reason to set
	 */
	public void setReason(String reason) {
		this.reason = reason;
	}
	/**
	 * @return the contact_number
	 */
	public String getContact_number() {
		return contact_number;
	}
	/**
	 * @param contact_number the contact_number to set
	 */
	public void setContact_number(String contact_number) {
		this.contact_number = contact_number;
	}
	public float getHoliday_cnt() {
		return holiday_cnt;
	}
	public void setHoliday_cnt(float holiday_cnt) {
		this.holiday_cnt = holiday_cnt;
	}
	
	
}
