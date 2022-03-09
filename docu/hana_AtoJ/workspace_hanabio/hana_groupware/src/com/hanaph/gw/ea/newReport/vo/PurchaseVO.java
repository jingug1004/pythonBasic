/**
  * Hana Project
  * Copyright 2014 iRush Co.,
  *
  */
package com.hanaph.gw.ea.newReport.vo;

import java.util.List;

/**
 * <pre>
 * Class Name : PurchaseVO.java
 * 설명 : 구매신청서 VO
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
public class PurchaseVO {
	private int seq; 				 //구매신청서 시퀀스
	private String approval_seq; 	 //문서번호
	private String product_nm; 		 //품명
	private String standard; 		 //규격
	private String qty;				 //수량
	private String deliver_req_ymd;  //납품요구일
	private String purpose; 		 //사무및목적
	private String bigo; 			 //비고
	private List<PurchaseVO> purchaseVO;//PurchaseVO List
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
	 * @return the standard
	 */
	public String getStandard() {
		return standard;
	}
	/**
	 * @param standard the standard to set
	 */
	public void setStandard(String standard) {
		this.standard = standard;
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
	 * @return the deliver_req_ymd
	 */
	public String getDeliver_req_ymd() {
		return deliver_req_ymd;
	}
	/**
	 * @param deliver_req_ymd the deliver_req_ymd to set
	 */
	public void setDeliver_req_ymd(String deliver_req_ymd) {
		this.deliver_req_ymd = deliver_req_ymd;
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
	 * @return the bigo
	 */
	public String getBigo() {
		return bigo;
	}
	/**
	 * @param bigo the bigo to set
	 */
	public void setBigo(String bigo) {
		this.bigo = bigo;
	}
	/**
	 * @return the purchaseVO
	 */
	public List<PurchaseVO> getPurchaseVO() {
		return purchaseVO;
	}
	/**
	 * @param purchaseVO the purchaseVO to set
	 */
	public void setPurchaseVO(List<PurchaseVO> purchaseVO) {
		this.purchaseVO = purchaseVO;
	}
	
}
