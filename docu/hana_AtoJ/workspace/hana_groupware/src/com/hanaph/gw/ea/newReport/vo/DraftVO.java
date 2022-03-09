/**
  * Hana Project
  * Copyright 2014 iRush Co.,
  *
  */
package com.hanaph.gw.ea.newReport.vo;

/**
 * <pre>
 * Class Name : DraftVO.java
 * 설명 : 기안서
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
public class DraftVO {
	private String approval_seq;	//문서번호
	private String imposition_ymd; //시행일자
	private String controll;		//통제
	private String cooperation;	//협조
	private String purpose;		//기안목적
	private String content;		//기안내용
	private String etc;			//기타
	
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
	 * @return the controll
	 */
	public String getControll() {
		return controll;
	}
	/**
	 * @param controll the controll to set
	 */
	public void setControll(String controll) {
		this.controll = controll;
	}
	/**
	 * @return the cooperation
	 */
	public String getCooperation() {
		return cooperation;
	}
	/**
	 * @param cooperation the cooperation to set
	 */
	public void setCooperation(String cooperation) {
		this.cooperation = cooperation;
	}
	/**
	 * @return the purpose
	 */
	public String getPurpose() {
		return purpose;
	}
	/**
	 * @param purpose the purpose to set
	 */
	public void setPurpose(String purpose) {
		this.purpose = purpose;
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
	 * @return the etc
	 */
	public String getEtc() {
		return etc;
	}
	/**
	 * @param etc the etc to set
	 */
	public void setEtc(String etc) {
		this.etc = etc;
	}
}
