/**
  * Hana Project
  * Copyright 2014 iRush Co.,
  *
  */
package com.hanaph.gw.ea.newReport.vo;

import java.util.List;

/**
 * <pre>
 * Class Name : DeliveryVO.java
 * 설명 : 물품 구입 청구서 or 청구확인서
 * 
 * Modification Information
 *    수정일      수정자              수정 내용
 *  ------------ -------------- --------------------------------
 *  2016. 01. 25.      원부자재 납품확인서       
 * </pre>
 * 
 * @version : 
 * @author  : 전산팀
 * @since   : 2016. 01. 25.
 */
public class DeliveryVO {
	private String seq;	//번호 
	private String approval_seq;	//문서번호
	private String ymd;	//입고일자 SALE.INV0301 YMD 같음
	private String slip_no;	//전표번호 SALE.INV0301 SLIP_NO 같음
	private String cust_id;	//거래처코드 SALE.INV0301 CUST_ID 같음
	private String cust_nm;	//거래처명 SALE.INV0011 CUST_NM 같음	
	private String material_id;	//원부자재코드 SALE.INV0302 MATERIAL_ID 같음
	private String material_nm;	//원부자재명 SALE.INV0003 MATERIAL_NM 같음	
	private String standard;	//규격 SALE.INV0003 STANDARD 같음
	private String unit;	//단위 SALE.INV0011 UNIT 같음	
	private String qty;	//수량 SALE.INV0302 QTY 같음
	private String danga;	//단가 SALE.INV0302 DANGA 같음
	private String amt;	//금액 SALE.INV0302 AMT 같음
	private String balju_no;	//수입여부 SALE.INV0302  BALJU_NO 같음
	private String amt_sum;	//전체 공급가액 SALE.INV0301 AMT_SUM 같음
	private String vat_sum;	//전체 부가세합계 SALE.INV0301 VAT_AMT 같음
	private String tot_sum;	//전체 합계 SALE.INV0301 AMT_SUM + VAT_AMT
	private String bigo;	//전체 합계 SALE.INV0302 BIGO 같음
	private String sawon_nm;	//전표번호 도움정보 SALE.SALE0007 SAWON_NM - CHOE 전표번호 뒤에 작성자명 표시 
	private List<DeliveryVO> deliveryVO;//DeliveryVO List	
	public String getSeq() {
		return seq;
	}
	public void setSeq(String seq) {
		this.seq = seq;
	}
	public String getApproval_seq() {
		return approval_seq;
	}
	public void setApproval_seq(String approval_seq) {
		this.approval_seq = approval_seq;
	}
	public String getYmd() {
		return ymd;
	}
	public void setYmd(String ymd) {
		this.ymd = ymd;
	}
	public String getSlip_no() {
		return slip_no;
	}
	public void setSlip_no(String slip_no) {
		this.slip_no = slip_no;
	}
	public String getCust_id() {
		return cust_id;
	}
	public void setCust_id(String cust_id) {
		this.cust_id = cust_id;
	}
	public String getCust_nm() {
		return cust_nm;
	}
	public void setCust_nm(String cust_nm) {
		this.cust_nm = cust_nm;
	}
	public String getMaterial_id() {
		return material_id;
	}
	public void setMaterial_id(String material_id) {
		this.material_id = material_id;
	}
	public String getMaterial_nm() {
		return material_nm;
	}
	public void setMaterial_nm(String material_nm) {
		this.material_nm = material_nm;
	}
	public String getStandard() {
		return standard;
	}
	public void setStandard(String standard) {
		this.standard = standard;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public String getQty() {
		return qty;
	}
	public void setQty(String qty) {
		this.qty = qty;
	}
	public String getDanga() {
		return danga;
	}
	public void setDanga(String danga) {
		this.danga = danga;
	}
	public String getAmt() {
		return amt;
	}
	public void setAmt(String amt) {
		this.amt = amt;
	}
	public String getBalju_no() {
		return balju_no;
	}
	public void setBalju_no(String balju_no) {
		this.balju_no = balju_no;
	}
	public String getAmt_sum() {
		return amt_sum;
	}
	public void setAmt_sum(String amt_sum) {
		this.amt_sum = amt_sum;
	}
	public String getVat_sum() {
		return vat_sum;
	}
	public void setVat_sum(String vat_sum) {
		this.vat_sum = vat_sum;
	}
	public List<DeliveryVO> getDeliveryVO() {
		return deliveryVO;
	}
	public void setDeliveryVO(List<DeliveryVO> deliveryVO) {
		this.deliveryVO = deliveryVO;
	}
	public String getSawon_nm() {
		return sawon_nm;
	}
	public void setSawon_nm(String sawon_nm) {
		this.sawon_nm = sawon_nm;
	}
	public String getTot_sum() {
		return tot_sum;
	}
	public void setTot_sum(String tot_sum) {
		this.tot_sum = tot_sum;
	}
	public String getBigo() {
		return bigo;
	}
	public void setBigo(String bigo) {
		this.bigo = bigo;
	}
	
	
	
}
