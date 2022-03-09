/**
  * Hana Project
  * Copyright 2014 iRush Co.,
  *
  */
package com.hanaph.gw.ea.newReport.vo;

import java.util.List;

/**
 * <pre>
 * Class Name : RequisitionVO.java
 * 설명 : 물품 구입 청구서 or 청구확인서
 * 
 * Modification Information
 *    수정일      수정자              수정 내용
 *  ------------ -------------- --------------------------------
 *  2015. 12. 10.      물품 구입 청구서 or 청구확인서          
 * </pre>
 * 
 * @version : 
 * @author  : 전산팀
 * @since   : 2015. 12. 11.
 */
public class RequisitionVO {
	private String seq;	//번호 
	private String approval_seq;	//문서번호
	private String req_ymd;	//청구번호 SALE.BUY0301 REQ_YMD 같음
	private String req_no;	//청구번호 SALE.BUY0302 REQ_NO 같음
	private String material_id;	//원부자재코드 SALE.BUY0302 MATERIAL_ID 같음
	private String material_nm;	//원부자재명 SALE.INV0003 MATERIAL_NM 같음
	private String standard;	//규격 SALE.INV0003 STANDARD 같음
	private String unit;	//단위 SALE.INV0011 UNIT 같음
	private String cust_id;	//거래처코드 SALE.BUY0302 CUST_ID 같음
	private String cust_nm;	//거래처명 SALE.INV0011 CUST_NM 같음
	private String qty;	//수량 SALE.BUY0302 QTY 같음
	private String danga;	//단가 SALE.BUY0302 DANGA 같음
	private String amt;	//금액 SALE.BUY0302 AMT 같음
	private String import_yn;	//수입여부 SALE.BUY0302 IMPORT_YN 같음
	private String hyunjaego_qty;	//현재고수량 SALE.BUY0302 HYUNJAEGO_QTY 같음
	private String usage;	//사용처 SALE.BUY0302 USAGE 같음
	private String ipgo_ymd;	//입고일 SALE.BUY0302 IPGO_YMD 같음
	private String bigo;	//비고 SALE.BUY0302 BIGO 같음
	private String tax_ymd;	//비고 SALE.INV0301 TAX_YMD 같음
	private String sawon_id;	//비고 SALE.INV0301 SAWON_ID 같음
	private String sawon_nm;	//청구번호 도움정보 SALE.SALE0007 SAWON_NM - CHOE 20160202 청구번호 뒤에 작성자명 표시 : SAWON_ID와 완전 다름 
	private String requestno; //그룹웨어 구입청구 번호
	private List<RequisitionVO> requisitionVO;//RequisitionVO List
	
	private String approval_seq_old;//물품 구입 청구서 문서번호 물품 구입 청구확인서에 등록
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
	public String getSeq() {
		return seq;
	}
	public void setSeq(String seq) {
		this.seq = seq;
	}
	public String getReq_ymd() {
		return req_ymd;
	}
	public void setReq_ymd(String req_ymd) {
		this.req_ymd = req_ymd;
	}	
	public String getReq_no() {
		return req_no;
	}
	public void setReq_no(String req_no) {
		this.req_no = req_no;
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
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public String getStandard() {
		return standard;
	}
	public void setStandard(String standard) {
		this.standard = standard;
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
	public String getImport_yn() {
		return import_yn;
	}
	public void setImport_yn(String import_yn) {
		this.import_yn = import_yn;
	}
	public String getHyunjaego_qty() {
		return hyunjaego_qty;
	}
	public void setHyunjaego_qty(String hyunjaego_qty) {
		this.hyunjaego_qty = hyunjaego_qty;
	}
	public String getUsage() {
		return usage;
	}
	public void setUsage(String usage) {
		this.usage = usage;
	}
	public String getIpgo_ymd() {
		return ipgo_ymd;
	}
	public void setIpgo_ymd(String ipgo_ymd) {
		this.ipgo_ymd = ipgo_ymd;
	}
	public String getBigo() {
		return bigo;
	}
	public void setBigo(String bigo) {
		this.bigo = bigo;
	}
	public List<RequisitionVO> getRequisitionVO() {
		return requisitionVO;
	}
	public void setRequisitionVO(List<RequisitionVO> requisitionVO) {
		this.requisitionVO = requisitionVO;
	}
	public String getApproval_seq_old() {
		return approval_seq_old;
	}
	public void setApproval_seq_old(String approval_seq_old) {
		this.approval_seq_old = approval_seq_old;
	}
	public String getTax_ymd() {
		return tax_ymd;
	}
	public void setTax_ymd(String tax_ymd) {
		this.tax_ymd = tax_ymd;
	}
	public String getSawon_id() {
		return sawon_id;
	}
	public void setSawon_id(String sawon_id) {
		this.sawon_id = sawon_id;
	}
	public String getSawon_nm() {
		return sawon_nm;
	}
	public void setSawon_nm(String sawon_nm) {
		this.sawon_nm = sawon_nm;
	}
	public String getRequestno() {
		return requestno;
	}
	public void setRequestno(String requestno) {
		this.requestno = requestno;
	}
	
	
	
}
