/**
 * Hana Project
 * Copyright 2014 iRush Co.,
 *
 */
package com.hanaph.gw.pe.member.vo;

/**
 * <pre>
 * Class Name : AnnualMgrVO.java
 * 설명 : 연차휴가관리 (HR_WK_YEARLY_0)
 * 
 * Modification Information
 *    수정일      수정자              수정 내용
 *  ------------ -------------- --------------------------------
 *  2014. 10. 31.      CHOIILJI
 * </pre>
 * 
 * @version :
 * @author : CHOIILJI(choiilji@irush.co.kr)
 * @since : 2014. 10. 31.
 */
public class AnnualMgrVO {
	private String dept_ko_nm; // 부서명
	private String grad_ko_nm; // 직급명
	private String emp_ko_nm; // 이름
	private String encmpy_dt; // 입사일자

	private String yr_emp_no;// 사번
	private String yr_year;// 연차계산 년
	private String yr_year_flag;// 만근여부 (1=만근, 0= 292일 미만)
	private String yr_month_flag;// 1일입사여부 (1=1일입사, 0= 중도입사)
	private String yr_work_years;// 실근무년수
	private String yr_work_months;// 실근무월수
	private String yr_work_days;// 실근무일수
	private String yr_add_day;// 내규에인정한근무년수 /2 = 추가연차발생일
	private String yr_nonuse_day;// 지난해 미사용일수 (-마이너스인 경우에만 의미 있음)
	private String yr_company_year;// 회사에서인정한근무년수
	private String yr_empty3;// 여분3
	private String first_emp_no;// 최초작성자 사번
	private String first_regdate;// 최초작성일자
	private String last_emp_no;// 최종수정자사번
	private String last_regdate;// 최종수정일자
	private String last_ip;// 최종작성IP
	
	private float add_useable_days;//
	private float used_days;//
	private String checks;//
	private String yr_year_used_day;//
	private String engag_div;//
	private String media_cd;// 공동연차 사용일수 
	private float useable_days;// 미사용한  연차휴가 

	private String november; // 11월
	private String december; // 12월
	private String use_between_day; // 연차휴가 사용대상 기간
	private String create_between_day; // 연차휴가 발생대상 기간
	private float used_days_half;//CHOE 20150710 연차사용계획서에 미사용 연차를 상반기 사용일자만 적어라고 요청
	private float joint_days_remain;//CHOE 20151105 연차지정통보서 남은 공동연차일을 계산 - 총무부 우승훈 요청 
	
	/**
	 * @return the dept_ko_nm
	 */
	public String getDept_ko_nm() {
		return dept_ko_nm;
	}
	/**
	 * @param dept_ko_nm the dept_ko_nm to set
	 */
	public void setDept_ko_nm(String dept_ko_nm) {
		this.dept_ko_nm = dept_ko_nm;
	}
	/**
	 * @return the grad_ko_nm
	 */
	public String getGrad_ko_nm() {
		return grad_ko_nm;
	}
	/**
	 * @param grad_ko_nm the grad_ko_nm to set
	 */
	public void setGrad_ko_nm(String grad_ko_nm) {
		this.grad_ko_nm = grad_ko_nm;
	}
	/**
	 * @return the emp_ko_nm
	 */
	public String getEmp_ko_nm() {
		return emp_ko_nm;
	}
	/**
	 * @param emp_ko_nm the emp_ko_nm to set
	 */
	public void setEmp_ko_nm(String emp_ko_nm) {
		this.emp_ko_nm = emp_ko_nm;
	}
	/**
	 * @return the encmpy_dt
	 */
	public String getEncmpy_dt() {
		return encmpy_dt;
	}
	/**
	 * @param encmpy_dt the encmpy_dt to set
	 */
	public void setEncmpy_dt(String encmpy_dt) {
		this.encmpy_dt = encmpy_dt;
	}
	/**
	 * @return the yr_emp_no
	 */
	public String getYr_emp_no() {
		return yr_emp_no;
	}
	/**
	 * @param yr_emp_no the yr_emp_no to set
	 */
	public void setYr_emp_no(String yr_emp_no) {
		this.yr_emp_no = yr_emp_no;
	}
	/**
	 * @return the yr_year
	 */
	public String getYr_year() {
		return yr_year;
	}
	/**
	 * @param yr_year the yr_year to set
	 */
	public void setYr_year(String yr_year) {
		this.yr_year = yr_year;
	}
	/**
	 * @return the yr_year_flag
	 */
	public String getYr_year_flag() {
		return yr_year_flag;
	}
	/**
	 * @param yr_year_flag the yr_year_flag to set
	 */
	public void setYr_year_flag(String yr_year_flag) {
		this.yr_year_flag = yr_year_flag;
	}
	/**
	 * @return the yr_month_flag
	 */
	public String getYr_month_flag() {
		return yr_month_flag;
	}
	/**
	 * @param yr_month_flag the yr_month_flag to set
	 */
	public void setYr_month_flag(String yr_month_flag) {
		this.yr_month_flag = yr_month_flag;
	}
	/**
	 * @return the yr_work_years
	 */
	public String getYr_work_years() {
		return yr_work_years;
	}
	/**
	 * @param yr_work_years the yr_work_years to set
	 */
	public void setYr_work_years(String yr_work_years) {
		this.yr_work_years = yr_work_years;
	}
	/**
	 * @return the yr_work_months
	 */
	public String getYr_work_months() {
		return yr_work_months;
	}
	/**
	 * @param yr_work_months the yr_work_months to set
	 */
	public void setYr_work_months(String yr_work_months) {
		this.yr_work_months = yr_work_months;
	}
	/**
	 * @return the yr_work_days
	 */
	public String getYr_work_days() {
		return yr_work_days;
	}
	/**
	 * @param yr_work_days the yr_work_days to set
	 */
	public void setYr_work_days(String yr_work_days) {
		this.yr_work_days = yr_work_days;
	}
	/**
	 * @return the yr_add_day
	 */
	public String getYr_add_day() {
		return yr_add_day;
	}
	/**
	 * @param yr_add_day the yr_add_day to set
	 */
	public void setYr_add_day(String yr_add_day) {
		this.yr_add_day = yr_add_day;
	}
	/**
	 * @return the yr_nonuse_day
	 */
	public String getYr_nonuse_day() {
		return yr_nonuse_day;
	}
	/**
	 * @param yr_nonuse_day the yr_nonuse_day to set
	 */
	public void setYr_nonuse_day(String yr_nonuse_day) {
		this.yr_nonuse_day = yr_nonuse_day;
	}
	/**
	 * @return the yr_company_year
	 */
	public String getYr_company_year() {
		return yr_company_year;
	}
	/**
	 * @param yr_company_year the yr_company_year to set
	 */
	public void setYr_company_year(String yr_company_year) {
		this.yr_company_year = yr_company_year;
	}
	/**
	 * @return the yr_empty3
	 */
	public String getYr_empty3() {
		return yr_empty3;
	}
	/**
	 * @param yr_empty3 the yr_empty3 to set
	 */
	public void setYr_empty3(String yr_empty3) {
		this.yr_empty3 = yr_empty3;
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
	 * @return the add_useable_days
	 */
	public float getAdd_useable_days() {
		return add_useable_days;
	}
	/**
	 * @param add_useable_days the add_useable_days to set
	 */
	public void setAdd_useable_days(float add_useable_days) {
		this.add_useable_days = add_useable_days;
	}
	/**
	 * @return the used_days
	 */
	public float getUsed_days() {
		return used_days;
	}
	/**
	 * @param used_days the used_days to set
	 */
	public void setUsed_days(float used_days) {
		this.used_days = used_days;
	}
	/**
	 * @return the checks
	 */
	public String getChecks() {
		return checks;
	}
	/**
	 * @param checks the checks to set
	 */
	public void setChecks(String checks) {
		this.checks = checks;
	}
	/**
	 * @return the yr_year_used_day
	 */
	public String getYr_year_used_day() {
		return yr_year_used_day;
	}
	/**
	 * @param yr_year_used_day the yr_year_used_day to set
	 */
	public void setYr_year_used_day(String yr_year_used_day) {
		this.yr_year_used_day = yr_year_used_day;
	}
	/**
	 * @return the engag_div
	 */
	public String getEngag_div() {
		return engag_div;
	}
	/**
	 * @param engag_div the engag_div to set
	 */
	public void setEngag_div(String engag_div) {
		this.engag_div = engag_div;
	}
	/**
	 * @return the media_cd
	 */
	public String getMedia_cd() {
		return media_cd;
	}
	/**
	 * @param media_cd the media_cd to set
	 */
	public void setMedia_cd(String media_cd) {
		this.media_cd = media_cd;
	}
	/**
	 * @return the useable_days
	 */
	public float getUseable_days() {
		return useable_days;
	}
	/**
	 * @param useable_days the useable_days to set
	 */
	public void setUseable_days(float useable_days) {
		this.useable_days = useable_days;
	}
	/**
	 * @return the november
	 */
	public String getNovember() {
		return november;
	}
	/**
	 * @param november the november to set
	 */
	public void setNovember(String november) {
		this.november = november;
	}
	/**
	 * @return the december
	 */
	public String getDecember() {
		return december;
	}
	/**
	 * @param december the december to set
	 */
	public void setDecember(String december) {
		this.december = december;
	}
	/**
	 * @return the use_between_day
	 */
	public String getUse_between_day() {
		return use_between_day;
	}
	/**
	 * @param use_between_day the use_between_day to set
	 */
	public void setUse_between_day(String use_between_day) {
		this.use_between_day = use_between_day;
	}
	/**
	 * @return the create_between_day
	 */
	public String getCreate_between_day() {
		return create_between_day;
	}
	/**
	 * @param create_between_day the create_between_day to set
	 */
	public void setCreate_between_day(String create_between_day) {
		this.create_between_day = create_between_day;
	}
	/**
	 * @return the used_days_half
	 * CHOE 20150710 총무부 요청 연차사용계획의 사용 연차를 상반기 사용분만 적어달라고 요청
	 */
	public float getUsed_days_half() {
		return used_days_half;
	}
	
	/**
	 * @return the used_days_half
	 * CHOE 20151105 총무부 요청 연차지정통보서의 11월 12월 사용하지 않은 공동연차일
	 */
	public float getJoint_days_remain() {
		return joint_days_remain;
		
	/**
	 * @return the used_days_half
	 * CHOE 20151105 총무부 요청 연차지정통보서의 11월 12월 사용하지 않은 공동연차일
	 */
	}
	public void setJoint_days_remain(float joint_days_remain) {
		this.joint_days_remain = joint_days_remain;
	}
	
}
