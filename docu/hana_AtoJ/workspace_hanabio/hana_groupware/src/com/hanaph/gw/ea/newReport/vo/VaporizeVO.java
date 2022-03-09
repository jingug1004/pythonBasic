/**
  * Hana Project
  * Copyright 2014 iRush Co.,
  *
  */
package com.hanaph.gw.ea.newReport.vo;

/**
 * <pre>
 * Class Name : VaporizeVO.java
 * 설명 : 기화기기안서
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
public class VaporizeVO {
	private String approval_seq;	//문서번호
	private String imposition_ymd;	//시행일자
	private String kind_cd;			//종류코드
	private String cust_nm;			//거래처명
	private String cust_cd;			//거래처코드
	private String ceo_nm;			//대표자명
	private String model_qty;		//기종및수량
	private String month_use_qty;	//월사용수량
	private String content;			//내용
	private String unusual;			//특이사항
	
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
	 * @return the kind_cd
	 */
	public String getKind_cd() {
		return kind_cd;
	}
	/**
	 * @param kind_cd the kind_cd to set
	 */
	public void setKind_cd(String kind_cd) {
		this.kind_cd = kind_cd;
	}
	/**
	 * @return the cust_nm
	 */
	public String getCust_nm() {
		return cust_nm;
	}
	/**
	 * @param cust_nm the cust_nm to set
	 */
	public void setCust_nm(String cust_nm) {
		this.cust_nm = cust_nm;
	}
	/**
	 * @return the cust_cd
	 */
	public String getCust_cd() {
		return cust_cd;
	}
	/**
	 * @param cust_cd the cust_cd to set
	 */
	public void setCust_cd(String cust_cd) {
		this.cust_cd = cust_cd;
	}
	/**
	 * @return the ceo_nm
	 */
	public String getCeo_nm() {
		return ceo_nm;
	}
	/**
	 * @param ceo_nm the ceo_nm to set
	 */
	public void setCeo_nm(String ceo_nm) {
		this.ceo_nm = ceo_nm;
	}
	/**
	 * @return the model_qty
	 */
	public String getModel_qty() {
		return model_qty;
	}
	/**
	 * @param model_qty the model_qty to set
	 */
	public void setModel_qty(String model_qty) {
		this.model_qty = model_qty;
	}
	/**
	 * @return the month_use_qty
	 */
	public String getMonth_use_qty() {
		return month_use_qty;
	}
	/**
	 * @param month_use_qty the month_use_qty to set
	 */
	public void setMonth_use_qty(String month_use_qty) {
		this.month_use_qty = month_use_qty;
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
	 * @return the unusual
	 */
	public String getUnusual() {
		return unusual;
	}
	/**
	 * @param unusual the unusual to set
	 */
	public void setUnusual(String unusual) {
		this.unusual = unusual;
	}
}
