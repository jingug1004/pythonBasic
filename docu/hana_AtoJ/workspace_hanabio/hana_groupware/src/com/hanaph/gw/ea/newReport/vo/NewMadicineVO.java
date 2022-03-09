/**
  * Hana Project
  * Copyright 2014 iRush Co.,
  *
  */
package com.hanaph.gw.ea.newReport.vo;

/**
 * <pre>
 * Class Name : NewMadicineVO.java
 * 설명 : 
 * 
 * Modification Information
 *    수정일      수정자              수정 내용
 *  ------------ -------------- --------------------------------
 *  2015. 3. 26.      shchoe          
 * </pre>
 * 
 * @version : 
 * @author  : shchoe(@irush.co.kr)
 * @since   : 2015. 3. 26.
 */
public class NewMadicineVO {
	private String approval_seq; //문서번호
	private String imposition_ymd; //시행일자
	private String hospital_nm; //병원명
	private String medical_nm; //진료과명
	private String doctor_nm; //의사명
	private String item_nm; //제품명
	private String change_item; //교체품목
	private String change_object; //교체대상
	private String add_item; //추가품목
	private String time_limit_dt; //요청기한
	private String discharge_dt; //dc날짜
	private String begin_dt; //처방개시일
	private String estimated_sales_item_1; //예상매출품목1
	private String estimated_sales_amt_1; //예상매출액1
	private String estimated_sales_item_2; //예상매출품목2
	private String estimated_sales_amt_2; //예상매출액2
	private String estimated_sales_item_3; //예상매출품목3
	private String estimated_sales_amt_3; //예상매출액3
	private String estimated_sales_item_4; //예상매출품목4
	private String estimated_sales_amt_4; //예상매출액4
	private String estimated_sales_item_5; //예상매출품목5
	private String estimated_sales_amt_5; //예상매출액5
	private String paper_form; //서류양식
	private String other_paper; //기타 필요서류
	private String unusual; //특이사항
	private String side_div; //원내외구분
	
	public String getApproval_seq() {
		return approval_seq;
	}
	public void setApproval_seq(String approval_seq) {
		this.approval_seq = approval_seq;
	}
	public String getImposition_ymd() {
		return imposition_ymd;
	}
	public void setImposition_ymd(String imposition_ymd) {
		this.imposition_ymd = imposition_ymd;
	}
	public String getHospital_nm() {
		return hospital_nm;
	}
	public void setHospital_nm(String hospital_nm) {
		this.hospital_nm = hospital_nm;
	}
	public String getMedical_nm() {
		return medical_nm;
	}
	public void setMedical_nm(String medical_nm) {
		this.medical_nm = medical_nm;
	}
	public String getDoctor_nm() {
		return doctor_nm;
	}
	public void setDoctor_nm(String doctor_nm) {
		this.doctor_nm = doctor_nm;
	}
	public String getItem_nm() {
		return item_nm;
	}
	public void setItem_nm(String item_nm) {
		this.item_nm = item_nm;
	}
	public String getChange_item() {
		return change_item;
	}
	public void setChange_item(String change_item) {
		this.change_item = change_item;
	}
	public String getChange_object() {
		return change_object;
	}
	public void setChange_object(String change_object) {
		this.change_object = change_object;
	}
	public String getAdd_item() {
		return add_item;
	}
	public void setAdd_item(String add_item) {
		this.add_item = add_item;
	}
	public String getTime_limit_dt() {
		return time_limit_dt;
	}
	public void setTime_limit_dt(String time_limit_dt) {
		this.time_limit_dt = time_limit_dt;
	}
	public String getDischarge_dt() {
		return discharge_dt;
	}
	public void setDischarge_dt(String discharge_dt) {
		this.discharge_dt = discharge_dt;
	}
	public String getBegin_dt() {
		return begin_dt;
	}
	public void setBegin_dt(String begin_dt) {
		this.begin_dt = begin_dt;
	}
	public String getEstimated_sales_item_1() {
		return estimated_sales_item_1;
	}
	public void setEstimated_sales_item_1(String estimated_sales_item_1) {
		this.estimated_sales_item_1 = estimated_sales_item_1;
	}
	public String getEstimated_sales_amt_1() {
		return estimated_sales_amt_1;
	}
	public void setEstimated_sales_amt_1(String estimated_sales_amt_1) {
		this.estimated_sales_amt_1 = estimated_sales_amt_1;
	}
	public String getEstimated_sales_item_2() {
		return estimated_sales_item_2;
	}
	public void setEstimated_sales_item_2(String estimated_sales_item_2) {
		this.estimated_sales_item_2 = estimated_sales_item_2;
	}
	public String getEstimated_sales_amt_2() {
		return estimated_sales_amt_2;
	}
	public void setEstimated_sales_amt_2(String estimated_sales_amt_2) {
		this.estimated_sales_amt_2 = estimated_sales_amt_2;
	}
	public String getEstimated_sales_item_3() {
		return estimated_sales_item_3;
	}
	public void setEstimated_sales_item_3(String estimated_sales_item_3) {
		this.estimated_sales_item_3 = estimated_sales_item_3;
	}
	public String getEstimated_sales_amt_3() {
		return estimated_sales_amt_3;
	}
	public void setEstimated_sales_amt_3(String estimated_sales_amt_3) {
		this.estimated_sales_amt_3 = estimated_sales_amt_3;
	}
	public String getEstimated_sales_item_4() {
		return estimated_sales_item_4;
	}
	public void setEstimated_sales_item_4(String estimated_sales_item_4) {
		this.estimated_sales_item_4 = estimated_sales_item_4;
	}
	public String getEstimated_sales_amt_4() {
		return estimated_sales_amt_4;
	}
	public void setEstimated_sales_amt_4(String estimated_sales_amt_4) {
		this.estimated_sales_amt_4 = estimated_sales_amt_4;
	}
	public String getEstimated_sales_item_5() {
		return estimated_sales_item_5;
	}
	public void setEstimated_sales_item_5(String estimated_sales_item_5) {
		this.estimated_sales_item_5 = estimated_sales_item_5;
	}
	public String getEstimated_sales_amt_5() {
		return estimated_sales_amt_5;
	}
	public void setEstimated_sales_amt_5(String estimated_sales_amt_5) {
		this.estimated_sales_amt_5 = estimated_sales_amt_5;
	}
	public String getPaper_form() {
		return paper_form;
	}
	public void setPaper_form(String paper_form) {
		this.paper_form = paper_form;
	}
	public String getOther_paper() {
		return other_paper;
	}
	public void setOther_paper(String other_paper) {
		this.other_paper = other_paper;
	}
	public String getUnusual() {
		return unusual;
	}
	public void setUnusual(String unusual) {
		this.unusual = unusual;
	}
	public String getSide_div() {
		return side_div;
	}
	public void setSide_div(String side_div) {
		this.side_div = side_div;
	}	
}
