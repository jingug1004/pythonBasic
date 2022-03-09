/**
  * Hana Project
  * Copyright 2014 iRush Co.,
  *
  */
package com.hanaph.gw.ea.newReport.vo;

/**
 * <pre>
 * Class Name : CommuteVO.java
 * 설명 : 근태계
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
public class CommuteVO {
	private String approval_seq;		//문서번호
	private	String kind;				//내용
	private	String bogo_yn;				//사전보고유무
	private String bogo_receiver;		//사전보고수령자
	private String imposition_ymd;		//근태계날짜
	private String mibogo_reason;		//미보고사유
	private String late_tm;				//지각출근시각
	private String leave_tm;			//조퇴발생시간
	private String start_absence_ymd;	//결근시작일자
	private String end_absence_ymd;		//결근종료일자
	private String reason;				//사유내용
	
	private String tardy;				//CHOE 20161118 지각한 일수
	
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
	 * @return the kind
	 */
	public String getKind() {
		return kind;
	}
	/**
	 * @param kind the kind to set
	 */
	public void setKind(String kind) {
		this.kind = kind;
	}
	/**
	 * @return the bogo_yn
	 */
	public String getBogo_yn() {
		return bogo_yn;
	}
	/**
	 * @param bogo_yn the bogo_yn to set
	 */
	public void setBogo_yn(String bogo_yn) {
		this.bogo_yn = bogo_yn;
	}
	/**
	 * @return the bogo_receiver
	 */
	public String getBogo_receiver() {
		return bogo_receiver;
	}
	/**
	 * @param bogo_receiver the bogo_receiver to set
	 */
	public void setBogo_receiver(String bogo_receiver) {
		this.bogo_receiver = bogo_receiver;
	}
	/**
	 * @return the imposition_ymd
	 */
	public String getImposition_ymd() {
		return imposition_ymd;
	}
	/**
	 * @param imposition_ymd the imposition_ymd to set
	 */
	public void setImposition_ymd(String imposition_ymd) {
		this.imposition_ymd = imposition_ymd;
	}
	/**
	 * @return the mibogo_reason
	 */
	public String getMibogo_reason() {
		return mibogo_reason;
	}
	/**
	 * @param mibogo_reason the mibogo_reason to set
	 */
	public void setMibogo_reason(String mibogo_reason) {
		this.mibogo_reason = mibogo_reason;
	}
	/**
	 * @return the late_tm
	 */
	public String getLate_tm() {
		return late_tm;
	}
	/**
	 * @param late_tm the late_tm to set
	 */
	public void setLate_tm(String late_tm) {
		this.late_tm = late_tm;
	}
	/**
	 * @return the leave_tm
	 */
	public String getLeave_tm() {
		return leave_tm;
	}
	/**
	 * @param leave_tm the leave_tm to set
	 */
	public void setLeave_tm(String leave_tm) {
		this.leave_tm = leave_tm;
	}
	/**
	 * @return the start_absence_ymd
	 */
	public String getStart_absence_ymd() {
		return start_absence_ymd;
	}
	/**
	 * @param start_absence_ymd the start_absence_ymd to set
	 */
	public void setStart_absence_ymd(String start_absence_ymd) {
		this.start_absence_ymd = start_absence_ymd;
	}
	/**
	 * @return the end_absence_ymd
	 */
	public String getEnd_absence_ymd() {
		return end_absence_ymd;
	}
	/**
	 * @param end_absence_ymd the end_absence_ymd to set
	 */
	public void setEnd_absence_ymd(String end_absence_ymd) {
		this.end_absence_ymd = end_absence_ymd;
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
	public String getTardy() {
		return tardy;
	}
	public void setTardy(String tardy) {
		this.tardy = tardy;
	}
	
}
