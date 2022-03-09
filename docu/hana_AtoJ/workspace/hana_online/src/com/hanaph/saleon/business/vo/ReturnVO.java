/**
  * Hana Project
  * Copyright 2014 iRush Co.,
  *
  */
package com.hanaph.saleon.business.vo;

/**
 * <pre>
 * Class Name : ReturnVO.java
 * 설명 : 반품현황 VO
 * 
 * Modification Information
 *    수정일      수정자              수정 내용
 *  //////////// ////////////// ////////////////////////////////
 *  2014. 10. 30.      Beomjin          
 * </pre>
 * 
 * @version : 
 * @author  : Beomjin(@irush.co.kr)
 * @since   : 2014. 10. 30.
 */
public class ReturnVO {
	
	private String ym; // 거래월
	private String ymd; // 거래일자
	private String deal_gb; // 거래 구분
	private String cust_gb1; // 거래처 구분1
	private String cust_id; // 거래처 코드
	private String cust_nm; // 거래처명
	private String vou_no; // 사업자 번호
	private String rcust_id; // 납품처 코드
	private String rcust_nm; // 납품처 명
	private String sawon_id; // 사원 코드
	private String sawon_nm; // 사원 명
	private String dept_cd; // 부서코드
	private String sawon_idh; // 담당사원
	private String sawon_nmh; // 담당사원명
	private String dept_cdh; // 담당사원 부서
	private String item_id; // 제품코드
	private String item_nm; // 제품명
	private String standard; // 단위
	private String qty; // 수량
	private String dc_qty; // 할증수량
	private String dc_amt; // 할인액
	private String dc_en_yn; // 할인정리여부
	private String danga; // 단가
	private String amt; // 공급가액
	private String vat; // 세액
	private String rcust_gb1; // 거래처 구분1
	private String rcust_gb1h; // 거래처 구분1
	private String tot; // 합계금액
	private int total_cnt; // 목록 총 수
	private String total_amt; // 총 공급가액
	private String total_vat; // 총 세액
	private String total_tot; // 총 합계금액
	
	private String prod_no;	//제조번호
	private String use_ymd_to;	//사용기한
	private String banpum_qty;	//반품수량
	private String barcode_qty;	//반품수량
	private String manual_qty;	//수기수량
	private String banpum_reason;	//반품사유코드
	private String banpum_reason_nm; //사유 명
	private String cust_sawon_id;	//거래처담당사번 
	private String rcust_sawon_id;	//납품처담당사원 - 반품보고사원 
	private String create_sawon_id;	//작성자사번
	private String seongin_state;	//승인상태 1-대기,2-승인,3-불가
	private String seongin_no_reason;	//승인불가사유
	private String jumun_no;	//주문번호
	private String input_dt;	//입력일자
	private String input_worker;	//입력사번
	private String modify_dt;	//수정일자
	private String modify_worker;	//수정사번
	private String last_order_ymd;	 //최종주문일자(해당거래처 남품처 제품에 대한) 
	private String input_state;	//입력상태 0-대기 1-확정
	private String cust_sawon_nm;	//담당자명
	private String total_b_qty;	//담당자명
	

	
	
	/**
	 * @return the ym
	 */
	public String getYm() {
		return ym;
	}
	/**
	 * @param ym the ym to set
	 */
	public void setYm(String ym) {
		this.ym = ym;
	}
	/**
	 * @return the ymd
	 */
	public String getYmd() {
		return ymd;
	}
	/**
	 * @param ymd the ymd to set
	 */
	public void setYmd(String ymd) {
		this.ymd = ymd;
	}
	/**
	 * @return the deal_gb
	 */
	public String getDeal_gb() {
		return deal_gb;
	}
	/**
	 * @param deal_gb the deal_gb to set
	 */
	public void setDeal_gb(String deal_gb) {
		this.deal_gb = deal_gb;
	}
	/**
	 * @return the cust_gb1
	 */
	public String getCust_gb1() {
		return cust_gb1;
	}
	/**
	 * @param cust_gb1 the cust_gb1 to set
	 */
	public void setCust_gb1(String cust_gb1) {
		this.cust_gb1 = cust_gb1;
	}
	/**
	 * @return the cust_id
	 */
	public String getCust_id() {
		return cust_id;
	}
	/**
	 * @param cust_id the cust_id to set
	 */
	public void setCust_id(String cust_id) {
		this.cust_id = cust_id;
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
	 * @return the vou_no
	 */
	public String getVou_no() {
		return vou_no;
	}
	/**
	 * @param vou_no the vou_no to set
	 */
	public void setVou_no(String vou_no) {
		this.vou_no = vou_no;
	}
	/**
	 * @return the rcust_id
	 */
	public String getRcust_id() {
		return rcust_id;
	}
	/**
	 * @param rcust_id the rcust_id to set
	 */
	public void setRcust_id(String rcust_id) {
		this.rcust_id = rcust_id;
	}
	/**
	 * @return the rcust_nm
	 */
	public String getRcust_nm() {
		return rcust_nm;
	}
	/**
	 * @param rcust_nm the rcust_nm to set
	 */
	public void setRcust_nm(String rcust_nm) {
		this.rcust_nm = rcust_nm;
	}
	/**
	 * @return the sawon_id
	 */
	public String getSawon_id() {
		return sawon_id;
	}
	/**
	 * @param sawon_id the sawon_id to set
	 */
	public void setSawon_id(String sawon_id) {
		this.sawon_id = sawon_id;
	}
	/**
	 * @return the sawon_nm
	 */
	public String getSawon_nm() {
		return sawon_nm;
	}
	/**
	 * @param sawon_nm the sawon_nm to set
	 */
	public void setSawon_nm(String sawon_nm) {
		this.sawon_nm = sawon_nm;
	}
	/**
	 * @return the dept_cd
	 */
	public String getDept_cd() {
		return dept_cd;
	}
	/**
	 * @param dept_cd the dept_cd to set
	 */
	public void setDept_cd(String dept_cd) {
		this.dept_cd = dept_cd;
	}
	/**
	 * @return the sawon_idh
	 */
	public String getSawon_idh() {
		return sawon_idh;
	}
	/**
	 * @param sawon_idh the sawon_idh to set
	 */
	public void setSawon_idh(String sawon_idh) {
		this.sawon_idh = sawon_idh;
	}
	/**
	 * @return the sawon_nmh
	 */
	public String getSawon_nmh() {
		return sawon_nmh;
	}
	/**
	 * @param sawon_nmh the sawon_nmh to set
	 */
	public void setSawon_nmh(String sawon_nmh) {
		this.sawon_nmh = sawon_nmh;
	}
	/**
	 * @return the dept_cdh
	 */
	public String getDept_cdh() {
		return dept_cdh;
	}
	/**
	 * @param dept_cdh the dept_cdh to set
	 */
	public void setDept_cdh(String dept_cdh) {
		this.dept_cdh = dept_cdh;
	}
	/**
	 * @return the item_id
	 */
	public String getItem_id() {
		return item_id;
	}
	/**
	 * @param item_id the item_id to set
	 */
	public void setItem_id(String item_id) {
		this.item_id = item_id;
	}
	/**
	 * @return the item_nm
	 */
	public String getItem_nm() {
		return item_nm;
	}
	/**
	 * @param item_nm the item_nm to set
	 */
	public void setItem_nm(String item_nm) {
		this.item_nm = item_nm;
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
	 * @return the dc_qty
	 */
	public String getDc_qty() {
		return dc_qty;
	}
	/**
	 * @param dc_qty the dc_qty to set
	 */
	public void setDc_qty(String dc_qty) {
		this.dc_qty = dc_qty;
	}
	/**
	 * @return the dc_amt
	 */
	public String getDc_amt() {
		return dc_amt;
	}
	/**
	 * @param dc_amt the dc_amt to set
	 */
	public void setDc_amt(String dc_amt) {
		this.dc_amt = dc_amt;
	}
	/**
	 * @return the dc_en_yn
	 */
	public String getDc_en_yn() {
		return dc_en_yn;
	}
	/**
	 * @param dc_en_yn the dc_en_yn to set
	 */
	public void setDc_en_yn(String dc_en_yn) {
		this.dc_en_yn = dc_en_yn;
	}
	/**
	 * @return the danga
	 */
	public String getDanga() {
		return danga;
	}
	/**
	 * @param danga the danga to set
	 */
	public void setDanga(String danga) {
		this.danga = danga;
	}
	/**
	 * @return the amt
	 */
	public String getAmt() {
		return amt;
	}
	/**
	 * @param amt the amt to set
	 */
	public void setAmt(String amt) {
		this.amt = amt;
	}
	/**
	 * @return the vat
	 */
	public String getVat() {
		return vat;
	}
	/**
	 * @param vat the vat to set
	 */
	public void setVat(String vat) {
		this.vat = vat;
	}
	/**
	 * @return the rcust_gb1
	 */
	public String getRcust_gb1() {
		return rcust_gb1;
	}
	/**
	 * @param rcust_gb1 the rcust_gb1 to set
	 */
	public void setRcust_gb1(String rcust_gb1) {
		this.rcust_gb1 = rcust_gb1;
	}
	/**
	 * @return the rcust_gb1h
	 */
	public String getRcust_gb1h() {
		return rcust_gb1h;
	}
	/**
	 * @param rcust_gb1h the rcust_gb1h to set
	 */
	public void setRcust_gb1h(String rcust_gb1h) {
		this.rcust_gb1h = rcust_gb1h;
	}
	/**
	 * @return the tot
	 */
	public String getTot() {
		return tot;
	}
	/**
	 * @param tot the tot to set
	 */
	public void setTot(String tot) {
		this.tot = tot;
	}
	/**
	 * @return the total_cnt
	 */
	public int getTotal_cnt() {
		return total_cnt;
	}
	/**
	 * @param total_cnt the total_cnt to set
	 */
	public void setTotal_cnt(int total_cnt) {
		this.total_cnt = total_cnt;
	}
	/**
	 * @return the total_amt
	 */
	public String getTotal_amt() {
		return total_amt;
	}
	/**
	 * @param total_amt the total_amt to set
	 */
	public void setTotal_amt(String total_amt) {
		this.total_amt = total_amt;
	}
	/**
	 * @return the total_vat
	 */
	public String getTotal_vat() {
		return total_vat;
	}
	/**
	 * @param total_vat the total_vat to set
	 */
	public void setTotal_vat(String total_vat) {
		this.total_vat = total_vat;
	}
	/**
	 * @return the total_tot
	 */
	public String getTotal_tot() {
		return total_tot;
	}
	/**
	 * @param total_tot the total_tot to set
	 */
	public void setTotal_tot(String total_tot) {
		this.total_tot = total_tot;
	}
	/**
	 * @return the prod_no
	 */
	public String getProd_no() {
		return prod_no;
	}
	/**
	 * @param prod_no the prod_no to set
	 */
	public void setProd_no(String prod_no) {
		this.prod_no = prod_no;
	}
	/**
	 * @return the use_ymd_to
	 */
	public String getUse_ymd_to() {
		return use_ymd_to;
	}
	/**
	 * @param use_ymd_to the use_ymd_to to set
	 */
	public void setUse_ymd_to(String use_ymd_to) {
		this.use_ymd_to = use_ymd_to;
	}
	/**
	 * @return the banpum_qty
	 */
	public String getBanpum_qty() {
		return banpum_qty;
	}
	/**
	 * @param banpum_qty the banpum_qty to set
	 */
	public void setBanpum_qty(String banpum_qty) {
		this.banpum_qty = banpum_qty;
	}
	/**
	 * @return the barcode_qty
	 */
	public String getBarcode_qty() {
		return barcode_qty;
	}
	/**
	 * @param barcode_qty the barcode_qty to set
	 */
	public void setBarcode_qty(String barcode_qty) {
		this.barcode_qty = barcode_qty;
	}
	/**
	 * @return the manual_qty
	 */
	public String getManual_qty() {
		return manual_qty;
	}
	/**
	 * @param manual_qty the manual_qty to set
	 */
	public void setManual_qty(String manual_qty) {
		this.manual_qty = manual_qty;
	}
	/**
	 * @return the banpum_reason
	 */
	public String getBanpum_reason() {
		return banpum_reason;
	}
	/**
	 * @param banpum_reason the banpum_reason to set
	 */
	public void setBanpum_reason(String banpum_reason) {
		this.banpum_reason = banpum_reason;
	}
	/**
	 * @return the banpum_reason_nm
	 */
	public String getBanpum_reason_nm() {
		return banpum_reason_nm;
	}
	/**
	 * @param banpum_reason_nm the banpum_reason_nm to set
	 */
	public void setBanpum_reason_nm(String banpum_reason_nm) {
		this.banpum_reason_nm = banpum_reason_nm;
	}
	/**
	 * @return the cust_sawon_id
	 */
	public String getCust_sawon_id() {
		return cust_sawon_id;
	}
	/**
	 * @param cust_sawon_id the cust_sawon_id to set
	 */
	public void setCust_sawon_id(String cust_sawon_id) {
		this.cust_sawon_id = cust_sawon_id;
	}
	/**
	 * @return the rcust_sawon_id
	 */
	public String getRcust_sawon_id() {
		return rcust_sawon_id;
	}
	/**
	 * @param rcust_sawon_id the rcust_sawon_id to set
	 */
	public void setRcust_sawon_id(String rcust_sawon_id) {
		this.rcust_sawon_id = rcust_sawon_id;
	}
	/**
	 * @return the create_sawon_id
	 */
	public String getCreate_sawon_id() {
		return create_sawon_id;
	}
	/**
	 * @param create_sawon_id the create_sawon_id to set
	 */
	public void setCreate_sawon_id(String create_sawon_id) {
		this.create_sawon_id = create_sawon_id;
	}
	/**
	 * @return the seongin_state
	 */
	public String getSeongin_state() {
		return seongin_state;
	}
	/**
	 * @param seongin_state the seongin_state to set
	 */
	public void setSeongin_state(String seongin_state) {
		this.seongin_state = seongin_state;
	}
	/**
	 * @return the seongin_no_reason
	 */
	public String getSeongin_no_reason() {
		return seongin_no_reason;
	}
	/**
	 * @param seongin_no_reason the seongin_no_reason to set
	 */
	public void setSeongin_no_reason(String seongin_no_reason) {
		this.seongin_no_reason = seongin_no_reason;
	}
	/**
	 * @return the jumun_no
	 */
	public String getJumun_no() {
		return jumun_no;
	}
	/**
	 * @param jumun_no the jumun_no to set
	 */
	public void setJumun_no(String jumun_no) {
		this.jumun_no = jumun_no;
	}
	/**
	 * @return the input_dt
	 */
	public String getInput_dt() {
		return input_dt;
	}
	/**
	 * @param input_dt the input_dt to set
	 */
	public void setInput_dt(String input_dt) {
		this.input_dt = input_dt;
	}
	/**
	 * @return the input_worker
	 */
	public String getInput_worker() {
		return input_worker;
	}
	/**
	 * @param input_worker the input_worker to set
	 */
	public void setInput_worker(String input_worker) {
		this.input_worker = input_worker;
	}
	/**
	 * @return the modify_dt
	 */
	public String getModify_dt() {
		return modify_dt;
	}
	/**
	 * @param modify_dt the modify_dt to set
	 */
	public void setModify_dt(String modify_dt) {
		this.modify_dt = modify_dt;
	}
	/**
	 * @return the modify_worker
	 */
	public String getModify_worker() {
		return modify_worker;
	}
	/**
	 * @param modify_worker the modify_worker to set
	 */
	public void setModify_worker(String modify_worker) {
		this.modify_worker = modify_worker;
	}
	/**
	 * @return the last_order_ymd
	 */
	public String getLast_order_ymd() {
		return last_order_ymd;
	}
	/**
	 * @param last_order_ymd the last_order_ymd to set
	 */
	public void setLast_order_ymd(String last_order_ymd) {
		this.last_order_ymd = last_order_ymd;
	}
	/**
	 * @return the input_state
	 */
	public String getInput_state() {
		return input_state;
	}
	/**
	 * @param input_state the input_state to set
	 */
	public void setInput_state(String input_state) {
		this.input_state = input_state;
	}
	/**
	 * @return the cust_sawon_nm
	 */
	public String getCust_sawon_nm() {
		return cust_sawon_nm;
	}
	/**
	 * @param cust_sawon_nm the cust_sawon_nm to set
	 */
	public void setCust_sawon_nm(String cust_sawon_nm) {
		this.cust_sawon_nm = cust_sawon_nm;
	}
	/**
	 * @return the total_b_qty
	 */
	public String getTotal_b_qty() {
		return total_b_qty;
	}
	/**
	 * @param total_b_qty the total_b_qty to set
	 */
	public void setTotal_b_qty(String total_b_qty) {
		this.total_b_qty = total_b_qty;
	}
}
