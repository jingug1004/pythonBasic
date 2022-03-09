/**
  * Hana Project
  * Copyright 2014 iRush Co.,
  *
  */
package com.hanaph.gw.ea.newReport.vo;

import java.util.List;

/**
 * <pre>
 * Class Name : CertificateVO.java
 * 설명 : 증명서발급신청서 VO
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
public class CertificateVO {
	private int seq;				//증명서발급신청서 시퀀스
	private String approval_seq;	//문서번호
	private String certi_nm;		//증명서종류
	private String certi_cd;		//증명서코드
	private String qty;				//수량
	private String yongdo;			//용도
	private List<CertificateVO> certificateVO; //CertificateVO List 
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
	 * @return the certi_nm
	 */
	public String getCerti_nm() {
		return certi_nm;
	}
	/**
	 * @param certi_nm the certi_nm to set
	 */
	public void setCerti_nm(String certi_nm) {
		this.certi_nm = certi_nm;
	}
	/**
	 * @return the certi_cd
	 */
	public String getCerti_cd() {
		return certi_cd;
	}
	/**
	 * @param certi_cd the certi_cd to set
	 */
	public void setCerti_cd(String certi_cd) {
		this.certi_cd = certi_cd;
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
	 * @return the certificateVO
	 */
	public List<CertificateVO> getCertificateVO() {
		return certificateVO;
	}
	/**
	 * @param certificateVO the certificateVO to set
	 */
	public void setCertificateVO(List<CertificateVO> certificateVO) {
		this.certificateVO = certificateVO;
	}
	
	
}
