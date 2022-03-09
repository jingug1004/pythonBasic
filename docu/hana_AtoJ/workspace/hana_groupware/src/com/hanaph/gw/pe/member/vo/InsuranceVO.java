/**
 * Hana Project
 * Copyright 2014 iRush Co.,
 *
 */
package com.hanaph.gw.pe.member.vo;

/**
 * <pre>
 * Class Name : InsuranceVO.java
 * 설명 : 건강보험 연말정산 환급/징수
 * 
 * Modification Information
 *    수정일      수정자              수정 내용
 *  ------------ -------------- --------------------------------
 *  2014. 10. 30.      CHOIILJI
 * </pre>
 * 
 * @version :
 * @author : CHOIILJI(choiilji@irush.co.kr)
 * @since : 2014. 10. 30.
 */
public class InsuranceVO {
	private String import_year;// 등록대상 YEAR
	private String sawon_no;// 2012년 신규인사기준
	private String sawon_name;// ;//신규인사기준(공백없음)
	private String jumin_no;//
	private String payment_1;// 납부한총보험료 - 2
	private String payment_2;// 납부한총보험료(건강) - 2
	private String payment_3;// 납부한총보험료(장기요양) - 2
	private String t_pay;// 총급여액 - 1
	private String month;// 근무개월 -1
	private String month_pay;//
	private String decide_1;// 확정보험료 - 3
	private String decide_2;// 확정보험료(건강) - 3
	private String decide_3;// 확정보험료(장기요양) - 3
	private String calc_1;// 정산보험료
	private String calc_2;// 정산보험료(건강)
	private String calc_3;// 정산보험료(장기요양)
	private String burden_1;// 가입자부담금 - 4
	private String burden_2;// 가입자부담금(건강) - 4
	private String burden_3;// 가입자부담금(장기요양) - 4
	private String temp_1;// 4월총납부액
	private String temp_2;// 5월가입자부담금
	private String temp_3;// =ABS(가입자부담금) + ABS(5월가입자부담금)
	/**
	 * @return the import_year
	 */
	public String getImport_year() {
		return import_year;
	}
	/**
	 * @param import_year the import_year to set
	 */
	public void setImport_year(String import_year) {
		this.import_year = import_year;
	}
	/**
	 * @return the sawon_no
	 */
	public String getSawon_no() {
		return sawon_no;
	}
	/**
	 * @param sawon_no the sawon_no to set
	 */
	public void setSawon_no(String sawon_no) {
		this.sawon_no = sawon_no;
	}
	/**
	 * @return the sawon_name
	 */
	public String getSawon_name() {
		return sawon_name;
	}
	/**
	 * @param sawon_name the sawon_name to set
	 */
	public void setSawon_name(String sawon_name) {
		this.sawon_name = sawon_name;
	}
	/**
	 * @return the jumin_no
	 */
	public String getJumin_no() {
		return jumin_no;
	}
	/**
	 * @param jumin_no the jumin_no to set
	 */
	public void setJumin_no(String jumin_no) {
		this.jumin_no = jumin_no;
	}
	/**
	 * @return the payment_1
	 */
	public String getPayment_1() {
		return payment_1;
	}
	/**
	 * @param payment_1 the payment_1 to set
	 */
	public void setPayment_1(String payment_1) {
		this.payment_1 = payment_1;
	}
	/**
	 * @return the payment_2
	 */
	public String getPayment_2() {
		return payment_2;
	}
	/**
	 * @param payment_2 the payment_2 to set
	 */
	public void setPayment_2(String payment_2) {
		this.payment_2 = payment_2;
	}
	/**
	 * @return the payment_3
	 */
	public String getPayment_3() {
		return payment_3;
	}
	/**
	 * @param payment_3 the payment_3 to set
	 */
	public void setPayment_3(String payment_3) {
		this.payment_3 = payment_3;
	}
	/**
	 * @return the t_pay
	 */
	public String getT_pay() {
		return t_pay;
	}
	/**
	 * @param t_pay the t_pay to set
	 */
	public void setT_pay(String t_pay) {
		this.t_pay = t_pay;
	}
	/**
	 * @return the month
	 */
	public String getMonth() {
		return month;
	}
	/**
	 * @param month the month to set
	 */
	public void setMonth(String month) {
		this.month = month;
	}
	/**
	 * @return the month_pay
	 */
	public String getMonth_pay() {
		return month_pay;
	}
	/**
	 * @param month_pay the month_pay to set
	 */
	public void setMonth_pay(String month_pay) {
		this.month_pay = month_pay;
	}
	/**
	 * @return the decide_1
	 */
	public String getDecide_1() {
		return decide_1;
	}
	/**
	 * @param decide_1 the decide_1 to set
	 */
	public void setDecide_1(String decide_1) {
		this.decide_1 = decide_1;
	}
	/**
	 * @return the decide_2
	 */
	public String getDecide_2() {
		return decide_2;
	}
	/**
	 * @param decide_2 the decide_2 to set
	 */
	public void setDecide_2(String decide_2) {
		this.decide_2 = decide_2;
	}
	/**
	 * @return the decide_3
	 */
	public String getDecide_3() {
		return decide_3;
	}
	/**
	 * @param decide_3 the decide_3 to set
	 */
	public void setDecide_3(String decide_3) {
		this.decide_3 = decide_3;
	}
	/**
	 * @return the calc_1
	 */
	public String getCalc_1() {
		return calc_1;
	}
	/**
	 * @param calc_1 the calc_1 to set
	 */
	public void setCalc_1(String calc_1) {
		this.calc_1 = calc_1;
	}
	/**
	 * @return the calc_2
	 */
	public String getCalc_2() {
		return calc_2;
	}
	/**
	 * @param calc_2 the calc_2 to set
	 */
	public void setCalc_2(String calc_2) {
		this.calc_2 = calc_2;
	}
	/**
	 * @return the calc_3
	 */
	public String getCalc_3() {
		return calc_3;
	}
	/**
	 * @param calc_3 the calc_3 to set
	 */
	public void setCalc_3(String calc_3) {
		this.calc_3 = calc_3;
	}
	/**
	 * @return the burden_1
	 */
	public String getBurden_1() {
		return burden_1;
	}
	/**
	 * @param burden_1 the burden_1 to set
	 */
	public void setBurden_1(String burden_1) {
		this.burden_1 = burden_1;
	}
	/**
	 * @return the burden_2
	 */
	public String getBurden_2() {
		return burden_2;
	}
	/**
	 * @param burden_2 the burden_2 to set
	 */
	public void setBurden_2(String burden_2) {
		this.burden_2 = burden_2;
	}
	/**
	 * @return the burden_3
	 */
	public String getBurden_3() {
		return burden_3;
	}
	/**
	 * @param burden_3 the burden_3 to set
	 */
	public void setBurden_3(String burden_3) {
		this.burden_3 = burden_3;
	}
	/**
	 * @return the temp_1
	 */
	public String getTemp_1() {
		return temp_1;
	}
	/**
	 * @param temp_1 the temp_1 to set
	 */
	public void setTemp_1(String temp_1) {
		this.temp_1 = temp_1;
	}
	/**
	 * @return the temp_2
	 */
	public String getTemp_2() {
		return temp_2;
	}
	/**
	 * @param temp_2 the temp_2 to set
	 */
	public void setTemp_2(String temp_2) {
		this.temp_2 = temp_2;
	}
	/**
	 * @return the temp_3
	 */
	public String getTemp_3() {
		return temp_3;
	}
	/**
	 * @param temp_3 the temp_3 to set
	 */
	public void setTemp_3(String temp_3) {
		this.temp_3 = temp_3;
	}
	
	
}
