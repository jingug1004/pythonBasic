/**
  * Hana Project
  * Copyright 2014 iRush Co.,
  *
  */
package com.hanaph.gw.ea.newReport.vo;

/**
 * <pre>
 * Class Name : AccidentVO.java
 * 설명 : 사고보고서
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
public class AccidentVO {
	private String approval_seq; //문서번호
	private String cust_nm; //거래처명
	private String cust_cd; //거래처코드
	private String branch_office; //지점
	private String incharge; //담당자
	private String accident_kind; //사고구분
	private String total_sale; //총매출
	private String total_collect; //총수금
	private String occurrence_ymd; //사고발생일
	private String last_trade_ymd; //최종거래일
	private String supyo1; //어음수표 금액
	private String supyo2; //어음수표 만기일
	private String supyo3; //어음수표 부동산소재지/발행인
	private String supyo4; //어음수표 배서인
	private String boheom1; //보증보험 금액
	private String boheom2; //보증보험 만기일
	private String boheom3; //보증보험 부동산소재지/발행인
	private String boheom4; //보증보험 배서인
	private String bojeung1; //지급보증 금액
	private String bojeung2; //지급보증 만기일
	private String bojeung3; //지급보증 부동산소재지/발행인
	private String bojeung4; //지급보증 배서인
	private String etc1; //기타 금액
	private String etc2; //기타 만기일
	private String etc3; //기타 부동산소재지/발행인
	private String etc4; //기타 배서인
	private String return_collection_amt; //반품회수액
	private String money_collection_amt; //금전회수액
	private String bogo_content; //보고내용
	private String bogo_year; //보고년도
	private String bogo_month; //보고월
	private String bogo_day; //보고일
	private String cust1; //거래처 거래처이름/상호
	private String cust2; //거래처 주민/사업자번호
	private String cust3; //거래처 주소/소재지
	private String ceo1; //대표자 거래처이름/상호
	private String ceo2; //대표자 주민/사업자번호
	private String ceo3; //대표자 주소/소재지
	private String surety11; //연대보증인1 거래처이름/상호
	private String surety12; //연대보증인1 주민/사업자번호
	private String surety13; //연대보증인1 주소/소재지
	private String surety21; //연대보증인2 거래처이름/상호
	private String surety22; //연대보증인2 주민/사업자번호
	private String surety23; //연대보증인2 주소/소재지
	private String credit_amt; //외상물품대금
	private String notcome_bill; //미도래어음
	private String bankrupt_amt; //부도채권총액
	private String property1; //부동산 금액
	private String property2; //부동산 만기일
	private String property3; //부동산 부동산소재지/발행인
	private String property4; //부동산 배서인
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
	 * @return the branch_office
	 */
	public String getBranch_office() {
		return branch_office;
	}
	/**
	 * @param branch_office the branch_office to set
	 */
	public void setBranch_office(String branch_office) {
		this.branch_office = branch_office;
	}
	/**
	 * @return the incharge
	 */
	public String getIncharge() {
		return incharge;
	}
	/**
	 * @param incharge the incharge to set
	 */
	public void setIncharge(String incharge) {
		this.incharge = incharge;
	}
	/**
	 * @return the accident_kind
	 */
	public String getAccident_kind() {
		return accident_kind;
	}
	/**
	 * @param accident_kind the accident_kind to set
	 */
	public void setAccident_kind(String accident_kind) {
		this.accident_kind = accident_kind;
	}
	/**
	 * @return the total_sale
	 */
	public String getTotal_sale() {
		return total_sale;
	}
	/**
	 * @param total_sale the total_sale to set
	 */
	public void setTotal_sale(String total_sale) {
		this.total_sale = total_sale;
	}
	/**
	 * @return the total_collect
	 */
	public String getTotal_collect() {
		return total_collect;
	}
	/**
	 * @param total_collect the total_collect to set
	 */
	public void setTotal_collect(String total_collect) {
		this.total_collect = total_collect;
	}
	/**
	 * @return the occurrence_ymd
	 */
	public String getOccurrence_ymd() {
		return occurrence_ymd;
	}
	/**
	 * @param occurrence_ymd the occurrence_ymd to set
	 */
	public void setOccurrence_ymd(String occurrence_ymd) {
		this.occurrence_ymd = occurrence_ymd;
	}
	/**
	 * @return the last_trade_ymd
	 */
	public String getLast_trade_ymd() {
		return last_trade_ymd;
	}
	/**
	 * @param last_trade_ymd the last_trade_ymd to set
	 */
	public void setLast_trade_ymd(String last_trade_ymd) {
		this.last_trade_ymd = last_trade_ymd;
	}
	/**
	 * @return the supyo1
	 */
	public String getSupyo1() {
		return supyo1;
	}
	/**
	 * @param supyo1 the supyo1 to set
	 */
	public void setSupyo1(String supyo1) {
		this.supyo1 = supyo1;
	}
	/**
	 * @return the supyo2
	 */
	public String getSupyo2() {
		return supyo2;
	}
	/**
	 * @param supyo2 the supyo2 to set
	 */
	public void setSupyo2(String supyo2) {
		this.supyo2 = supyo2;
	}
	/**
	 * @return the supyo3
	 */
	public String getSupyo3() {
		return supyo3;
	}
	/**
	 * @param supyo3 the supyo3 to set
	 */
	public void setSupyo3(String supyo3) {
		this.supyo3 = supyo3;
	}
	/**
	 * @return the supyo4
	 */
	public String getSupyo4() {
		return supyo4;
	}
	/**
	 * @param supyo4 the supyo4 to set
	 */
	public void setSupyo4(String supyo4) {
		this.supyo4 = supyo4;
	}
	/**
	 * @return the boheom1
	 */
	public String getBoheom1() {
		return boheom1;
	}
	/**
	 * @param boheom1 the boheom1 to set
	 */
	public void setBoheom1(String boheom1) {
		this.boheom1 = boheom1;
	}
	/**
	 * @return the boheom2
	 */
	public String getBoheom2() {
		return boheom2;
	}
	/**
	 * @param boheom2 the boheom2 to set
	 */
	public void setBoheom2(String boheom2) {
		this.boheom2 = boheom2;
	}
	/**
	 * @return the boheom3
	 */
	public String getBoheom3() {
		return boheom3;
	}
	/**
	 * @param boheom3 the boheom3 to set
	 */
	public void setBoheom3(String boheom3) {
		this.boheom3 = boheom3;
	}
	/**
	 * @return the boheom4
	 */
	public String getBoheom4() {
		return boheom4;
	}
	/**
	 * @param boheom4 the boheom4 to set
	 */
	public void setBoheom4(String boheom4) {
		this.boheom4 = boheom4;
	}
	/**
	 * @return the bojeung1
	 */
	public String getBojeung1() {
		return bojeung1;
	}
	/**
	 * @param bojeung1 the bojeung1 to set
	 */
	public void setBojeung1(String bojeung1) {
		this.bojeung1 = bojeung1;
	}
	/**
	 * @return the bojeung2
	 */
	public String getBojeung2() {
		return bojeung2;
	}
	/**
	 * @param bojeung2 the bojeung2 to set
	 */
	public void setBojeung2(String bojeung2) {
		this.bojeung2 = bojeung2;
	}
	/**
	 * @return the bojeung3
	 */
	public String getBojeung3() {
		return bojeung3;
	}
	/**
	 * @param bojeung3 the bojeung3 to set
	 */
	public void setBojeung3(String bojeung3) {
		this.bojeung3 = bojeung3;
	}
	/**
	 * @return the bojeung4
	 */
	public String getBojeung4() {
		return bojeung4;
	}
	/**
	 * @param bojeung4 the bojeung4 to set
	 */
	public void setBojeung4(String bojeung4) {
		this.bojeung4 = bojeung4;
	}
	/**
	 * @return the etc1
	 */
	public String getEtc1() {
		return etc1;
	}
	/**
	 * @param etc1 the etc1 to set
	 */
	public void setEtc1(String etc1) {
		this.etc1 = etc1;
	}
	/**
	 * @return the etc2
	 */
	public String getEtc2() {
		return etc2;
	}
	/**
	 * @param etc2 the etc2 to set
	 */
	public void setEtc2(String etc2) {
		this.etc2 = etc2;
	}
	/**
	 * @return the etc3
	 */
	public String getEtc3() {
		return etc3;
	}
	/**
	 * @param etc3 the etc3 to set
	 */
	public void setEtc3(String etc3) {
		this.etc3 = etc3;
	}
	/**
	 * @return the etc4
	 */
	public String getEtc4() {
		return etc4;
	}
	/**
	 * @param etc4 the etc4 to set
	 */
	public void setEtc4(String etc4) {
		this.etc4 = etc4;
	}
	/**
	 * @return the return_collection_amt
	 */
	public String getReturn_collection_amt() {
		return return_collection_amt;
	}
	/**
	 * @param return_collection_amt the return_collection_amt to set
	 */
	public void setReturn_collection_amt(String return_collection_amt) {
		this.return_collection_amt = return_collection_amt;
	}
	/**
	 * @return the money_collection_amt
	 */
	public String getMoney_collection_amt() {
		return money_collection_amt;
	}
	/**
	 * @param money_collection_amt the money_collection_amt to set
	 */
	public void setMoney_collection_amt(String money_collection_amt) {
		this.money_collection_amt = money_collection_amt;
	}
	/**
	 * @return the bogo_content
	 */
	public String getBogo_content() {
		return bogo_content;
	}
	/**
	 * @param bogo_content the bogo_content to set
	 */
	public void setBogo_content(String bogo_content) {
		this.bogo_content = bogo_content;
	}
	/**
	 * @return the bogo_year
	 */
	public String getBogo_year() {
		return bogo_year;
	}
	/**
	 * @param bogo_year the bogo_year to set
	 */
	public void setBogo_year(String bogo_year) {
		this.bogo_year = bogo_year;
	}
	/**
	 * @return the bogo_month
	 */
	public String getBogo_month() {
		return bogo_month;
	}
	/**
	 * @param bogo_month the bogo_month to set
	 */
	public void setBogo_month(String bogo_month) {
		this.bogo_month = bogo_month;
	}
	/**
	 * @return the bogo_day
	 */
	public String getBogo_day() {
		return bogo_day;
	}
	/**
	 * @param bogo_day the bogo_day to set
	 */
	public void setBogo_day(String bogo_day) {
		this.bogo_day = bogo_day;
	}
	/**
	 * @return the cust1
	 */
	public String getCust1() {
		return cust1;
	}
	/**
	 * @param cust1 the cust1 to set
	 */
	public void setCust1(String cust1) {
		this.cust1 = cust1;
	}
	/**
	 * @return the cust2
	 */
	public String getCust2() {
		return cust2;
	}
	/**
	 * @param cust2 the cust2 to set
	 */
	public void setCust2(String cust2) {
		this.cust2 = cust2;
	}
	/**
	 * @return the cust3
	 */
	public String getCust3() {
		return cust3;
	}
	/**
	 * @param cust3 the cust3 to set
	 */
	public void setCust3(String cust3) {
		this.cust3 = cust3;
	}
	/**
	 * @return the ceo1
	 */
	public String getCeo1() {
		return ceo1;
	}
	/**
	 * @param ceo1 the ceo1 to set
	 */
	public void setCeo1(String ceo1) {
		this.ceo1 = ceo1;
	}
	/**
	 * @return the ceo2
	 */
	public String getCeo2() {
		return ceo2;
	}
	/**
	 * @param ceo2 the ceo2 to set
	 */
	public void setCeo2(String ceo2) {
		this.ceo2 = ceo2;
	}
	/**
	 * @return the ceo3
	 */
	public String getCeo3() {
		return ceo3;
	}
	/**
	 * @param ceo3 the ceo3 to set
	 */
	public void setCeo3(String ceo3) {
		this.ceo3 = ceo3;
	}
	/**
	 * @return the surety11
	 */
	public String getSurety11() {
		return surety11;
	}
	/**
	 * @param surety11 the surety11 to set
	 */
	public void setSurety11(String surety11) {
		this.surety11 = surety11;
	}
	/**
	 * @return the surety12
	 */
	public String getSurety12() {
		return surety12;
	}
	/**
	 * @param surety12 the surety12 to set
	 */
	public void setSurety12(String surety12) {
		this.surety12 = surety12;
	}
	/**
	 * @return the surety13
	 */
	public String getSurety13() {
		return surety13;
	}
	/**
	 * @param surety13 the surety13 to set
	 */
	public void setSurety13(String surety13) {
		this.surety13 = surety13;
	}
	/**
	 * @return the surety21
	 */
	public String getSurety21() {
		return surety21;
	}
	/**
	 * @param surety21 the surety21 to set
	 */
	public void setSurety21(String surety21) {
		this.surety21 = surety21;
	}
	/**
	 * @return the surety22
	 */
	public String getSurety22() {
		return surety22;
	}
	/**
	 * @param surety22 the surety22 to set
	 */
	public void setSurety22(String surety22) {
		this.surety22 = surety22;
	}
	/**
	 * @return the surety23
	 */
	public String getSurety23() {
		return surety23;
	}
	/**
	 * @param surety23 the surety23 to set
	 */
	public void setSurety23(String surety23) {
		this.surety23 = surety23;
	}
	/**
	 * @return the credit_amt
	 */
	public String getCredit_amt() {
		return credit_amt;
	}
	/**
	 * @param credit_amt the credit_amt to set
	 */
	public void setCredit_amt(String credit_amt) {
		this.credit_amt = credit_amt;
	}
	/**
	 * @return the notcome_bill
	 */
	public String getNotcome_bill() {
		return notcome_bill;
	}
	/**
	 * @param notcome_bill the notcome_bill to set
	 */
	public void setNotcome_bill(String notcome_bill) {
		this.notcome_bill = notcome_bill;
	}
	/**
	 * @return the bankrupt_amt
	 */
	public String getBankrupt_amt() {
		return bankrupt_amt;
	}
	/**
	 * @param bankrupt_amt the bankrupt_amt to set
	 */
	public void setBankrupt_amt(String bankrupt_amt) {
		this.bankrupt_amt = bankrupt_amt;
	}
	/**
	 * @return the property1
	 */
	public String getProperty1() {
		return property1;
	}
	/**
	 * @param property1 the property1 to set
	 */
	public void setProperty1(String property1) {
		this.property1 = property1;
	}
	/**
	 * @return the property2
	 */
	public String getProperty2() {
		return property2;
	}
	/**
	 * @param property2 the property2 to set
	 */
	public void setProperty2(String property2) {
		this.property2 = property2;
	}
	/**
	 * @return the property3
	 */
	public String getProperty3() {
		return property3;
	}
	/**
	 * @param property3 the property3 to set
	 */
	public void setProperty3(String property3) {
		this.property3 = property3;
	}
	/**
	 * @return the property4
	 */
	public String getProperty4() {
		return property4;
	}
	/**
	 * @param property4 the property4 to set
	 */
	public void setProperty4(String property4) {
		this.property4 = property4;
	}
}
