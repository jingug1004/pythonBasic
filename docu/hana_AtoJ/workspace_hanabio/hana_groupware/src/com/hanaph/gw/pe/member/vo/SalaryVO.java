/**
 * Hana Project
 * Copyright 2014 iRush Co.,
 *
 */
package com.hanaph.gw.pe.member.vo;

/**
 * <pre>
 * Class Name : SalaryVO.java
 * 설명 : 급여 정보
 * 
 * Modification Information
 *    수정일      수정자              수정 내용
 *  ------------ -------------- --------------------------------
 *  2014. 10. 29.      CHOIILJI
 * </pre>
 * 
 * @version :
 * @author : CHOIILJI(choiilji@irush.co.kr)
 * @since : 2014. 10. 29.
 */
public class SalaryVO {
	private String pay_date;// 급여일
	private String emp_no;// 사원ID
	private String emp_name;// 사원명
	private String dept_name;// 부서명
	private String jik_name;// 직급명
	private String first_emp_no;// 최초작성자사번
	private String first_regdate;// 최초등록일자
	private String last_emp_no;// 최종수정자사번
	private String last_regdate;// 최종수정일자
	private String last_ip;// 최종작성IP
	private String bigo;// 비고
	private int pay_amt1;// 기본급
	private int pay_amt2;// 생휴수당
	private int pay_amt3;// 출납수당
	private int pay_amt4;// 특수직무수당
	private int pay_amt5;// 생산비과세
	private int pay_amt6;// 업무수당
	private int pay_amt7;// 식대(비)
	private int pay_amt8;// 식대
	private int pay_amt9;// 직급수당
	private int pay_amt10;// 건강보험환급금
	private int pay_amt11;// 가족수당
	private int pay_amt12;// 누락분
	private int pay_amt13;// 감액
	private int pay_amt14;// 기타수당
	private int pay_amt15;// 기타지급
	private int pay_amt16;// 특별근무시간
	private int pay_amt17;// 자격수당
	private int pay_amt18;// 상여금
	private int pay_amt19;// 인센티브
	private int pay_amt20;// 연장근로수당
	private int pay_amt21;// 야간근로수당
	private int pay_amt22;// 휴일근로수당
	private int pay_amt23;// 특수직무수당
	private int pay_amt24;// 소득세환급분
	private int pay_amt25;// 주민세환급분
	private int pay_amt26;// 농특세환급분
	private int pay_amt27;// 소득세
	private int pay_amt28;// 주민세
	private int pay_amt29;// 국민연금
	private int pay_amt30;// 건강보험
	private int pay_amt31;// 고용보험
	private int pay_amt32;// 상여근태감액
	private int pay_amt33;// 채무공제
	private int pay_amt34;// 재형저축
	private int pay_amt35;// 장기저축
	private int pay_amt36;// 재형기금
	private int pay_amt37;// 가지급금
	private int pay_amt38;// 기타공제
	private int pay_amt39;// 농특세
	private int pay_amt40;// 사우회비
	private int pay_amt41;// 사우회비상환
	private int pay_amt42;// 소득세징수분
	private int pay_amt43;// 주민세징수분
	private int pay_amt44;// 농특세징수분
	private int pay_amt45;// 보증보험
	private int pay_amt46;// 총지급액
	private int pay_amt47;// 공제합계
	private int pay_amt48;// 차인지급액
	/**
	 * @return the pay_date
	 */
	public String getPay_date() {
		return pay_date;
	}
	/**
	 * @param pay_date the pay_date to set
	 */
	public void setPay_date(String pay_date) {
		this.pay_date = pay_date;
	}
	/**
	 * @return the emp_no
	 */
	public String getEmp_no() {
		return emp_no;
	}
	/**
	 * @param emp_no the emp_no to set
	 */
	public void setEmp_no(String emp_no) {
		this.emp_no = emp_no;
	}
	/**
	 * @return the emp_name
	 */
	public String getEmp_name() {
		return emp_name;
	}
	/**
	 * @param emp_name the emp_name to set
	 */
	public void setEmp_name(String emp_name) {
		this.emp_name = emp_name;
	}
	/**
	 * @return the dept_name
	 */
	public String getDept_name() {
		return dept_name;
	}
	/**
	 * @param dept_name the dept_name to set
	 */
	public void setDept_name(String dept_name) {
		this.dept_name = dept_name;
	}
	/**
	 * @return the jik_name
	 */
	public String getJik_name() {
		return jik_name;
	}
	/**
	 * @param jik_name the jik_name to set
	 */
	public void setJik_name(String jik_name) {
		this.jik_name = jik_name;
	}
	/**
	 * @return the first_emp_no
	 */
	public String getFirst_emp_no() {
		return first_emp_no;
	}
	/**
	 * @param first_emp_no the first_emp_no to set
	 */
	public void setFirst_emp_no(String first_emp_no) {
		this.first_emp_no = first_emp_no;
	}
	/**
	 * @return the first_regdate
	 */
	public String getFirst_regdate() {
		return first_regdate;
	}
	/**
	 * @param first_regdate the first_regdate to set
	 */
	public void setFirst_regdate(String first_regdate) {
		this.first_regdate = first_regdate;
	}
	/**
	 * @return the last_emp_no
	 */
	public String getLast_emp_no() {
		return last_emp_no;
	}
	/**
	 * @param last_emp_no the last_emp_no to set
	 */
	public void setLast_emp_no(String last_emp_no) {
		this.last_emp_no = last_emp_no;
	}
	/**
	 * @return the last_regdate
	 */
	public String getLast_regdate() {
		return last_regdate;
	}
	/**
	 * @param last_regdate the last_regdate to set
	 */
	public void setLast_regdate(String last_regdate) {
		this.last_regdate = last_regdate;
	}
	/**
	 * @return the last_ip
	 */
	public String getLast_ip() {
		return last_ip;
	}
	/**
	 * @param last_ip the last_ip to set
	 */
	public void setLast_ip(String last_ip) {
		this.last_ip = last_ip;
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
	 * @return the pay_amt1
	 */
	public int getPay_amt1() {
		return pay_amt1;
	}
	/**
	 * @param pay_amt1 the pay_amt1 to set
	 */
	public void setPay_amt1(int pay_amt1) {
		this.pay_amt1 = pay_amt1;
	}
	/**
	 * @return the pay_amt2
	 */
	public int getPay_amt2() {
		return pay_amt2;
	}
	/**
	 * @param pay_amt2 the pay_amt2 to set
	 */
	public void setPay_amt2(int pay_amt2) {
		this.pay_amt2 = pay_amt2;
	}
	/**
	 * @return the pay_amt3
	 */
	public int getPay_amt3() {
		return pay_amt3;
	}
	/**
	 * @param pay_amt3 the pay_amt3 to set
	 */
	public void setPay_amt3(int pay_amt3) {
		this.pay_amt3 = pay_amt3;
	}
	/**
	 * @return the pay_amt4
	 */
	public int getPay_amt4() {
		return pay_amt4;
	}
	/**
	 * @param pay_amt4 the pay_amt4 to set
	 */
	public void setPay_amt4(int pay_amt4) {
		this.pay_amt4 = pay_amt4;
	}
	/**
	 * @return the pay_amt5
	 */
	public int getPay_amt5() {
		return pay_amt5;
	}
	/**
	 * @param pay_amt5 the pay_amt5 to set
	 */
	public void setPay_amt5(int pay_amt5) {
		this.pay_amt5 = pay_amt5;
	}
	/**
	 * @return the pay_amt6
	 */
	public int getPay_amt6() {
		return pay_amt6;
	}
	/**
	 * @param pay_amt6 the pay_amt6 to set
	 */
	public void setPay_amt6(int pay_amt6) {
		this.pay_amt6 = pay_amt6;
	}
	/**
	 * @return the pay_amt7
	 */
	public int getPay_amt7() {
		return pay_amt7;
	}
	/**
	 * @param pay_amt7 the pay_amt7 to set
	 */
	public void setPay_amt7(int pay_amt7) {
		this.pay_amt7 = pay_amt7;
	}
	/**
	 * @return the pay_amt8
	 */
	public int getPay_amt8() {
		return pay_amt8;
	}
	/**
	 * @param pay_amt8 the pay_amt8 to set
	 */
	public void setPay_amt8(int pay_amt8) {
		this.pay_amt8 = pay_amt8;
	}
	/**
	 * @return the pay_amt9
	 */
	public int getPay_amt9() {
		return pay_amt9;
	}
	/**
	 * @param pay_amt9 the pay_amt9 to set
	 */
	public void setPay_amt9(int pay_amt9) {
		this.pay_amt9 = pay_amt9;
	}
	/**
	 * @return the pay_amt10
	 */
	public int getPay_amt10() {
		return pay_amt10;
	}
	/**
	 * @param pay_amt10 the pay_amt10 to set
	 */
	public void setPay_amt10(int pay_amt10) {
		this.pay_amt10 = pay_amt10;
	}
	/**
	 * @return the pay_amt11
	 */
	public int getPay_amt11() {
		return pay_amt11;
	}
	/**
	 * @param pay_amt11 the pay_amt11 to set
	 */
	public void setPay_amt11(int pay_amt11) {
		this.pay_amt11 = pay_amt11;
	}
	/**
	 * @return the pay_amt12
	 */
	public int getPay_amt12() {
		return pay_amt12;
	}
	/**
	 * @param pay_amt12 the pay_amt12 to set
	 */
	public void setPay_amt12(int pay_amt12) {
		this.pay_amt12 = pay_amt12;
	}
	/**
	 * @return the pay_amt13
	 */
	public int getPay_amt13() {
		return pay_amt13;
	}
	/**
	 * @param pay_amt13 the pay_amt13 to set
	 */
	public void setPay_amt13(int pay_amt13) {
		this.pay_amt13 = pay_amt13;
	}
	/**
	 * @return the pay_amt14
	 */
	public int getPay_amt14() {
		return pay_amt14;
	}
	/**
	 * @param pay_amt14 the pay_amt14 to set
	 */
	public void setPay_amt14(int pay_amt14) {
		this.pay_amt14 = pay_amt14;
	}
	/**
	 * @return the pay_amt15
	 */
	public int getPay_amt15() {
		return pay_amt15;
	}
	/**
	 * @param pay_amt15 the pay_amt15 to set
	 */
	public void setPay_amt15(int pay_amt15) {
		this.pay_amt15 = pay_amt15;
	}
	/**
	 * @return the pay_amt16
	 */
	public int getPay_amt16() {
		return pay_amt16;
	}
	/**
	 * @param pay_amt16 the pay_amt16 to set
	 */
	public void setPay_amt16(int pay_amt16) {
		this.pay_amt16 = pay_amt16;
	}
	/**
	 * @return the pay_amt17
	 */
	public int getPay_amt17() {
		return pay_amt17;
	}
	/**
	 * @param pay_amt17 the pay_amt17 to set
	 */
	public void setPay_amt17(int pay_amt17) {
		this.pay_amt17 = pay_amt17;
	}
	/**
	 * @return the pay_amt18
	 */
	public int getPay_amt18() {
		return pay_amt18;
	}
	/**
	 * @param pay_amt18 the pay_amt18 to set
	 */
	public void setPay_amt18(int pay_amt18) {
		this.pay_amt18 = pay_amt18;
	}
	/**
	 * @return the pay_amt19
	 */
	public int getPay_amt19() {
		return pay_amt19;
	}
	/**
	 * @param pay_amt19 the pay_amt19 to set
	 */
	public void setPay_amt19(int pay_amt19) {
		this.pay_amt19 = pay_amt19;
	}
	/**
	 * @return the pay_amt20
	 */
	public int getPay_amt20() {
		return pay_amt20;
	}
	/**
	 * @param pay_amt20 the pay_amt20 to set
	 */
	public void setPay_amt20(int pay_amt20) {
		this.pay_amt20 = pay_amt20;
	}
	/**
	 * @return the pay_amt21
	 */
	public int getPay_amt21() {
		return pay_amt21;
	}
	/**
	 * @param pay_amt21 the pay_amt21 to set
	 */
	public void setPay_amt21(int pay_amt21) {
		this.pay_amt21 = pay_amt21;
	}
	/**
	 * @return the pay_amt22
	 */
	public int getPay_amt22() {
		return pay_amt22;
	}
	/**
	 * @param pay_amt22 the pay_amt22 to set
	 */
	public void setPay_amt22(int pay_amt22) {
		this.pay_amt22 = pay_amt22;
	}
	/**
	 * @return the pay_amt23
	 */
	public int getPay_amt23() {
		return pay_amt23;
	}
	/**
	 * @param pay_amt23 the pay_amt23 to set
	 */
	public void setPay_amt23(int pay_amt23) {
		this.pay_amt23 = pay_amt23;
	}
	/**
	 * @return the pay_amt24
	 */
	public int getPay_amt24() {
		return pay_amt24;
	}
	/**
	 * @param pay_amt24 the pay_amt24 to set
	 */
	public void setPay_amt24(int pay_amt24) {
		this.pay_amt24 = pay_amt24;
	}
	/**
	 * @return the pay_amt25
	 */
	public int getPay_amt25() {
		return pay_amt25;
	}
	/**
	 * @param pay_amt25 the pay_amt25 to set
	 */
	public void setPay_amt25(int pay_amt25) {
		this.pay_amt25 = pay_amt25;
	}
	/**
	 * @return the pay_amt26
	 */
	public int getPay_amt26() {
		return pay_amt26;
	}
	/**
	 * @param pay_amt26 the pay_amt26 to set
	 */
	public void setPay_amt26(int pay_amt26) {
		this.pay_amt26 = pay_amt26;
	}
	/**
	 * @return the pay_amt27
	 */
	public int getPay_amt27() {
		return pay_amt27;
	}
	/**
	 * @param pay_amt27 the pay_amt27 to set
	 */
	public void setPay_amt27(int pay_amt27) {
		this.pay_amt27 = pay_amt27;
	}
	/**
	 * @return the pay_amt28
	 */
	public int getPay_amt28() {
		return pay_amt28;
	}
	/**
	 * @param pay_amt28 the pay_amt28 to set
	 */
	public void setPay_amt28(int pay_amt28) {
		this.pay_amt28 = pay_amt28;
	}
	/**
	 * @return the pay_amt29
	 */
	public int getPay_amt29() {
		return pay_amt29;
	}
	/**
	 * @param pay_amt29 the pay_amt29 to set
	 */
	public void setPay_amt29(int pay_amt29) {
		this.pay_amt29 = pay_amt29;
	}
	/**
	 * @return the pay_amt30
	 */
	public int getPay_amt30() {
		return pay_amt30;
	}
	/**
	 * @param pay_amt30 the pay_amt30 to set
	 */
	public void setPay_amt30(int pay_amt30) {
		this.pay_amt30 = pay_amt30;
	}
	/**
	 * @return the pay_amt31
	 */
	public int getPay_amt31() {
		return pay_amt31;
	}
	/**
	 * @param pay_amt31 the pay_amt31 to set
	 */
	public void setPay_amt31(int pay_amt31) {
		this.pay_amt31 = pay_amt31;
	}
	/**
	 * @return the pay_amt32
	 */
	public int getPay_amt32() {
		return pay_amt32;
	}
	/**
	 * @param pay_amt32 the pay_amt32 to set
	 */
	public void setPay_amt32(int pay_amt32) {
		this.pay_amt32 = pay_amt32;
	}
	/**
	 * @return the pay_amt33
	 */
	public int getPay_amt33() {
		return pay_amt33;
	}
	/**
	 * @param pay_amt33 the pay_amt33 to set
	 */
	public void setPay_amt33(int pay_amt33) {
		this.pay_amt33 = pay_amt33;
	}
	/**
	 * @return the pay_amt34
	 */
	public int getPay_amt34() {
		return pay_amt34;
	}
	/**
	 * @param pay_amt34 the pay_amt34 to set
	 */
	public void setPay_amt34(int pay_amt34) {
		this.pay_amt34 = pay_amt34;
	}
	/**
	 * @return the pay_amt35
	 */
	public int getPay_amt35() {
		return pay_amt35;
	}
	/**
	 * @param pay_amt35 the pay_amt35 to set
	 */
	public void setPay_amt35(int pay_amt35) {
		this.pay_amt35 = pay_amt35;
	}
	/**
	 * @return the pay_amt36
	 */
	public int getPay_amt36() {
		return pay_amt36;
	}
	/**
	 * @param pay_amt36 the pay_amt36 to set
	 */
	public void setPay_amt36(int pay_amt36) {
		this.pay_amt36 = pay_amt36;
	}
	/**
	 * @return the pay_amt37
	 */
	public int getPay_amt37() {
		return pay_amt37;
	}
	/**
	 * @param pay_amt37 the pay_amt37 to set
	 */
	public void setPay_amt37(int pay_amt37) {
		this.pay_amt37 = pay_amt37;
	}
	/**
	 * @return the pay_amt38
	 */
	public int getPay_amt38() {
		return pay_amt38;
	}
	/**
	 * @param pay_amt38 the pay_amt38 to set
	 */
	public void setPay_amt38(int pay_amt38) {
		this.pay_amt38 = pay_amt38;
	}
	/**
	 * @return the pay_amt39
	 */
	public int getPay_amt39() {
		return pay_amt39;
	}
	/**
	 * @param pay_amt39 the pay_amt39 to set
	 */
	public void setPay_amt39(int pay_amt39) {
		this.pay_amt39 = pay_amt39;
	}
	/**
	 * @return the pay_amt40
	 */
	public int getPay_amt40() {
		return pay_amt40;
	}
	/**
	 * @param pay_amt40 the pay_amt40 to set
	 */
	public void setPay_amt40(int pay_amt40) {
		this.pay_amt40 = pay_amt40;
	}
	/**
	 * @return the pay_amt41
	 */
	public int getPay_amt41() {
		return pay_amt41;
	}
	/**
	 * @param pay_amt41 the pay_amt41 to set
	 */
	public void setPay_amt41(int pay_amt41) {
		this.pay_amt41 = pay_amt41;
	}
	/**
	 * @return the pay_amt42
	 */
	public int getPay_amt42() {
		return pay_amt42;
	}
	/**
	 * @param pay_amt42 the pay_amt42 to set
	 */
	public void setPay_amt42(int pay_amt42) {
		this.pay_amt42 = pay_amt42;
	}
	/**
	 * @return the pay_amt43
	 */
	public int getPay_amt43() {
		return pay_amt43;
	}
	/**
	 * @param pay_amt43 the pay_amt43 to set
	 */
	public void setPay_amt43(int pay_amt43) {
		this.pay_amt43 = pay_amt43;
	}
	/**
	 * @return the pay_amt44
	 */
	public int getPay_amt44() {
		return pay_amt44;
	}
	/**
	 * @param pay_amt44 the pay_amt44 to set
	 */
	public void setPay_amt44(int pay_amt44) {
		this.pay_amt44 = pay_amt44;
	}
	/**
	 * @return the pay_amt45
	 */
	public int getPay_amt45() {
		return pay_amt45;
	}
	/**
	 * @param pay_amt45 the pay_amt45 to set
	 */
	public void setPay_amt45(int pay_amt45) {
		this.pay_amt45 = pay_amt45;
	}
	/**
	 * @return the pay_amt46
	 */
	public int getPay_amt46() {
		return pay_amt46;
	}
	/**
	 * @param pay_amt46 the pay_amt46 to set
	 */
	public void setPay_amt46(int pay_amt46) {
		this.pay_amt46 = pay_amt46;
	}
	/**
	 * @return the pay_amt47
	 */
	public int getPay_amt47() {
		return pay_amt47;
	}
	/**
	 * @param pay_amt47 the pay_amt47 to set
	 */
	public void setPay_amt47(int pay_amt47) {
		this.pay_amt47 = pay_amt47;
	}
	/**
	 * @return the pay_amt48
	 */
	public int getPay_amt48() {
		return pay_amt48;
	}
	/**
	 * @param pay_amt48 the pay_amt48 to set
	 */
	public void setPay_amt48(int pay_amt48) {
		this.pay_amt48 = pay_amt48;
	}

	
}
