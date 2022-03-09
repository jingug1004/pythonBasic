/**
  * Hana Project
  * Copyright 2014 iRush Co.,
  *
  */
package com.hanaph.gw.ea.newReport.vo;

/**
 * <pre>
 * Class Name : ParticipationVO.java
 * 설명 : 참가신청서
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
public class ParticipationVO {
	private String approval_seq;	//문서번호
	private String kind;			//종류
	private String content;			//내용
	private String start_ymd; 		//시작일자
	private String end_ymd;			//종료일자
	private String contact_number;	//비상연락처
	
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
	 * @return the content
	 */
	public String getContent() {
		return content;
	}
	/**
	 * @param content the content to set
	 */
	public void setContent(String content) {
		this.content = content;
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
}
