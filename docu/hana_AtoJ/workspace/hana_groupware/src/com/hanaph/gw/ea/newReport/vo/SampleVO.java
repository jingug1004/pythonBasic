/**
  * Hana Project
  * Copyright 2014 iRush Co.,
  *
  */
package com.hanaph.gw.ea.newReport.vo;

import java.util.List;

/**
 * <pre>
 * Class Name : SampleVO.java
 * 설명 : 샘플신청서 VO
 * 
 * Modification Information
 *    수정일      수정자              수정 내용
 *  ------------ -------------- --------------------------------
 *  2014. 12. 29.      Jung.Jin.Muk          
 * </pre>
 * 
 * @version : 1.0
 * @author  : Jung.Jin.Muk(pc123pc@irush.co.kr)
 * @since   : 2014. 12. 29.
 */

public class SampleVO {
	private int seq; 				//샘플신청서시퀀스
	private String approval_seq; 	//문서번호
	private String cust_nm;			//거래처명
	private String cust_cd;			//거래처코드
	private String dr_nm; 			//dr명
	private String product_nm; 		//품명
	private String packing_unit;	//포장단위
	private String qty; 			//수량
	private String yongdo;			//용도
	private String address;			//배송지
	private String call_number;		//전화번호
	private String responsible;		//담당자
	private List<SampleVO> sampleVO;//SampleVO List
	/**
	 * @return the seq
	 */
	public int getSeq() {
		return seq;
	}
	/**
	 * @param seq the seq to set
	 */
	public void setSeq(int seq) {
		this.seq = seq;
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
	 * @return the dr_nm
	 */
	public String getDr_nm() {
		return dr_nm;
	}
	/**
	 * @param dr_nm the dr_nm to set
	 */
	public void setDr_nm(String dr_nm) {
		this.dr_nm = dr_nm;
	}
	/**
	 * @return the product_nm
	 */
	public String getProduct_nm() {
		return product_nm;
	}
	/**
	 * @param product_nm the product_nm to set
	 */
	public void setProduct_nm(String product_nm) {
		this.product_nm = product_nm;
	}
	/**
	 * @return the packing_unit
	 */
	public String getPacking_unit() {
		return packing_unit;
	}
	/**
	 * @param packing_unit the packing_unit to set
	 */
	public void setPacking_unit(String packing_unit) {
		this.packing_unit = packing_unit;
	}
	/**
	 * @return the qty
	 */
	public String getQty() {
		return qty;
	}
	/**
	 * @param qty the qty to set
	 */
	public void setQty(String qty) {
		this.qty = qty;
	}
	/**
	 * @return the yongdo
	 */
	public String getYongdo() {
		return yongdo;
	}
	/**
	 * @param yongdo the yongdo to set
	 */
	public void setYongdo(String yongdo) {
		this.yongdo = yongdo;
	}
	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}
	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}
	/**
	 * @return the call_number
	 */
	public String getCall_number() {
		return call_number;
	}
	/**
	 * @param call_number the call_number to set
	 */
	public void setCall_number(String call_number) {
		this.call_number = call_number;
	}
	/**
	 * @return the sampleVO
	 */
	public List<SampleVO> getSampleVO() {
		return sampleVO;
	}
	/**
	 * @param sampleVO the sampleVO to set
	 */
	public void setSampleVO(List<SampleVO> sampleVO) {
		this.sampleVO = sampleVO;
	}
	/**
	 * @param responsible the responsible to set
	 */
	public String getResponsible() {
		return responsible;
	}
	public void setResponsible(String responsible) {
		this.responsible = responsible;
	}
	
	
	
}
