<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%--
 * @파일명 : receiveList.jsp
 * @메뉴명 : 내가받은문서
 * @최초작성일 : 2015/02/16            
 * @author : Jung.Jin.Muk(pc123pc@irush.co.kr)                 
 * @수정내역 : 
--%>
<%@ page import="java.util.List" %>
<%@ page import="com.hanaph.gw.co.common.utils.StringUtil" %>
<%@ page import="com.hanaph.gw.yt.yearendtax.vo.YearendtaxVO" %>
<%@ page import="java.util.List" %>   
<%@ include file ="/common/path.jsp" %>
<%
	YearendtaxVO yearendtaxDetail = (YearendtaxVO)request.getAttribute("yearendtaxDetail"); //1페이지
	YearendtaxVO yearendtaxDetail2 = (YearendtaxVO)request.getAttribute("yearendtaxDetail2"); //2페이지
	List<YearendtaxVO> yearendtaxDetail3 = (List<YearendtaxVO>)request.getAttribute("yearendtaxDetail3"); //3페이지
	List<MemberVO> authorityMemberList = (List<MemberVO>)request.getAttribute("authorityMemberList"); //관리자
	String emp_no = StringUtil.nvl((String)request.getAttribute("emp_no")); //임직원
	String year = StringUtil.nvl((String)request.getAttribute("year")); //년도
	
	if(yearendtaxDetail == null){
		yearendtaxDetail = new YearendtaxVO();
	}
	if(yearendtaxDetail2 == null){
		yearendtaxDetail2 = new YearendtaxVO();
	}
	
	int foreign_cd = 1; //거주자1,내국인1만 선택되도록 함
	
	/* 근무처별소득명세 */
	String comp_name = StringUtil.nvl(yearendtaxDetail.getComp_name());	//(1)법인명(상호)
	String president = StringUtil.nvl(yearendtaxDetail.getPresident()); //(2)대표자(성명)
	String vou_no = StringUtil.nvl(yearendtaxDetail.getVou_no()); //(3)사업자등록번호
	String comp_no = StringUtil.nvl(yearendtaxDetail.getComp_no());	//(4)주민등록번호
	String com_addr = StringUtil.nvl(yearendtaxDetail.getCom_addr()); //(5)소재지(주소)
	String emp_ko_nm = StringUtil.nvl(yearendtaxDetail.getEmp_ko_nm()); //(6)성명
	String resid_no = StringUtil.nvl(yearendtaxDetail.getResid_no()); //(7)주민등록번호
	String bas_addr = StringUtil.nvl(yearendtaxDetail.getBas_addr()); //(8)주소
 	String corporate_nm = StringUtil.nvl(yearendtaxDetail.getCorporate_nm()); //(9)근무처명
	String corporate_nm2 = StringUtil.nvl(yearendtaxDetail.getCorporate_nm2()); //(9)근무처명
	String corporate_no = StringUtil.nvl(yearendtaxDetail.getCorporate_no()); //(10)사업자등록번호
	String corporate_no2 = StringUtil.nvl(yearendtaxDetail.getCorporate_no2()); //(10)사업자등록번호
	String bas_start_dt = StringUtil.nvl(yearendtaxDetail.getBas_start_dt()); //(11)근무기간
	String bas_end_dt = StringUtil.nvl(yearendtaxDetail.getBas_end_dt()); //(11)근무기간
	String start_work_dt = StringUtil.nvl(yearendtaxDetail.getStart_work_dt()); //(11)근무기간
	String end_work_dt = StringUtil.nvl(yearendtaxDetail.getEnd_work_dt()); //(11)근무기간
	String start_work_dt2 = StringUtil.nvl(yearendtaxDetail.getStart_work_dt2()); //(11)근무기간
	String end_work_dt2 = StringUtil.nvl(yearendtaxDetail.getEnd_work_dt2()); //(11)근무기간
	String start_reduce_dt = StringUtil.nvl(yearendtaxDetail.getStart_reduce_dt()); //(12)감면기간
	String end_reduce_dt = StringUtil.nvl(yearendtaxDetail.getEnd_reduce_dt()); //(12)감면기간
	String start_reduce_dt2 = StringUtil.nvl(yearendtaxDetail.getStart_reduce_dt2()); //(12)감면기간
	String end_reduce_dt2 = StringUtil.nvl(yearendtaxDetail.getStart_reduce_dt2()); //(12)감면기간
	
	String bas_end_year = "";
	String bas_end_month = "";
	String bas_end_day = "";
	
	if(!"".equals(StringUtil.nvl(bas_end_dt))){
		bas_end_year = yearendtaxDetail.getBas_end_dt().substring(0,4);
		bas_end_month = yearendtaxDetail.getBas_end_dt().substring(4,6);
		bas_end_day = yearendtaxDetail.getBas_end_dt().substring(6,8);
	}
	
	String str_bas_start_dt = "";
	String str_bas_end_dt = "";
	
	if(!"".equals(StringUtil.nvl(bas_start_dt))){
		str_bas_start_dt = yearendtaxDetail.getBas_start_dt().substring(0,4) + "." + yearendtaxDetail.getBas_start_dt().substring(4,6) + "." + yearendtaxDetail.getBas_start_dt().substring(6,8);
	}
	if(!"".equals(StringUtil.nvl(bas_end_dt))){
		str_bas_end_dt = yearendtaxDetail.getBas_end_dt().substring(0,4) + "." + yearendtaxDetail.getBas_end_dt().substring(4,6) + "." + yearendtaxDetail.getBas_end_dt().substring(6,8);
	}
	
	String str_start_work_dt = "";
	String str_end_work_dt = "";
	
	if(!"".equals(StringUtil.nvl(start_work_dt))){
		str_start_work_dt = yearendtaxDetail.getStart_work_dt().substring(0,4) + "." + yearendtaxDetail.getStart_work_dt().substring(4,6) + "." + yearendtaxDetail.getStart_work_dt().substring(6,8);
	}
	if(!"".equals(StringUtil.nvl(end_work_dt))){
		str_end_work_dt = yearendtaxDetail.getEnd_work_dt().substring(0,4) + "." + yearendtaxDetail.getEnd_work_dt().substring(4,6) + "." + yearendtaxDetail.getEnd_work_dt().substring(6,8);
	}
	
	String str_start_work_dt2 = "";
	String str_end_work_dt2 = "";
	
	if(!"".equals(StringUtil.nvl(start_work_dt2))){
		str_start_work_dt2 = yearendtaxDetail.getStart_work_dt2().substring(0,4) + "." + yearendtaxDetail.getStart_work_dt2().substring(4,6) + "." + yearendtaxDetail.getStart_work_dt2().substring(6,8);
	}
	if(!"".equals(StringUtil.nvl(end_work_dt2))){
		str_end_work_dt2 = yearendtaxDetail.getEnd_work_dt2().substring(0,4) + "." + yearendtaxDetail.getEnd_work_dt2().substring(4,6) + "." + yearendtaxDetail.getEnd_work_dt2().substring(6,8);
	}
	
	String str_start_reduce_dt = "";
	String str_end_reduce_dt = "";
	
	if(!"".equals(StringUtil.nvl(start_reduce_dt))){
		str_start_reduce_dt = yearendtaxDetail.getStart_reduce_dt().substring(0,4) + "." + yearendtaxDetail.getStart_reduce_dt().substring(4,6) + "." + yearendtaxDetail.getStart_reduce_dt().substring(6,8);
	}
	if(!"".equals(StringUtil.nvl(end_reduce_dt))){
		str_end_reduce_dt = yearendtaxDetail.getEnd_reduce_dt().substring(0,4) + "." + yearendtaxDetail.getEnd_reduce_dt().substring(4,6) + "." + yearendtaxDetail.getEnd_reduce_dt().substring(6,8);
	}
	
	String str_start_reduce_dt2 = "";
	String str_end_reduce_dt2 = "";
	
	if(!"".equals(StringUtil.nvl(start_reduce_dt2))){
		str_start_reduce_dt2 = yearendtaxDetail.getStart_reduce_dt2().substring(0,4) + "." + yearendtaxDetail.getStart_reduce_dt2().substring(4,6) + "." + yearendtaxDetail.getStart_reduce_dt2().substring(6,8);
	}
	if(!"".equals(StringUtil.nvl(end_reduce_dt2))){
		str_end_reduce_dt2 = yearendtaxDetail.getEnd_reduce_dt2().substring(0,4) + "." + yearendtaxDetail.getEnd_reduce_dt2().substring(4,6) + "." + yearendtaxDetail.getEnd_reduce_dt2().substring(6,8);
	}
	
	int pay_sum = yearendtaxDetail.getPay_sum(); //(13)급여
	int salary_amt = yearendtaxDetail.getSalary_amt(); //(13)급여
	int salary_amt2 = yearendtaxDetail.getSalary_amt2(); //(13)급여
	int sum_salary = pay_sum + salary_amt + salary_amt2;
	
	int bonus_sum = yearendtaxDetail.getBonus_sum(); //(14)상여
	int bonus_amt = yearendtaxDetail.getBonus_amt(); //(14)상여
	int bonus_amt2 = yearendtaxDetail.getBonus_amt2(); //(14)상여
	int sum_bonus = bonus_sum + bonus_amt + bonus_amt2;
	
	int constructive_bonus_amt = yearendtaxDetail.getConstructive_bonus_amt(); //(15)인정상여
	int constructive_bonus_amt2 = yearendtaxDetail.getConstructive_bonus_amt2(); //(15)인정상여
	int sum_constructive = constructive_bonus_amt + constructive_bonus_amt2;
	
	int stock_option_amt = yearendtaxDetail.getStock_option_amt(); //(15)-1주식매수선택권 행사이익
	int stock_option_amt2= yearendtaxDetail.getStock_option_amt2(); //(15)-1주식매수선택권 행사이익
	int sum_stock = stock_option_amt + stock_option_amt2;
	
	int employ_stock_amt = yearendtaxDetail.getEmploy_stock_amt(); //(15)-2우리사주조합인출금
	int employ_stock_amt_2= yearendtaxDetail.getEmploy_stock_amt_2(); //(15)-2우리사주조합인출금
	int sum_employ = employ_stock_amt + employ_stock_amt_2;
	
	int derector_retirement_amt = yearendtaxDetail.getDerector_retirement_amt(); //(15)-3임원 퇴직소득금액 한도초과액
	int derector_retirement_amt2 = yearendtaxDetail.getDerector_retirement_amt2(); //(15)-3임원 퇴직소득금액 한도초과액
	int sum_derector = derector_retirement_amt + derector_retirement_amt2; 

	int sum_total = pay_sum + bonus_sum;//계
	int total_salary = yearendtaxDetail.getTotal_salary(); //계
	int total_salary2 = yearendtaxDetail.getTotal_salary2(); //계2
	int pay_sum_total = yearendtaxDetail.getPay_sum_total(); //합계
	
	/* 비과세및 감면소득명세 */
	int foreign_work_amt = yearendtaxDetail.getForeign_work_amt(); //(18)국외근로
	int foreign_work_amt2 = yearendtaxDetail.getForeign_work_amt2(); //(18)국외근로
	int sum_foreign = foreign_work_amt + foreign_work_amt2;
	
	int free_overtime_sum = yearendtaxDetail.getFree_overtime_sum(); //(18)-1야간근로수동
	int	oevrtime_amt = yearendtaxDetail.getOevrtime_amt(); //(18)-1야간근로수동
	int	oevrtime_amt2 = yearendtaxDetail.getOevrtime_amt2(); //(18)-1야간근로수동
	int sum_oevrtime = free_overtime_sum + oevrtime_amt + oevrtime_amt2;
	
	int	meternity_amt = yearendtaxDetail.getMeternity_amt(); //(18)-2출산보육수당
	int	meternity_amt2 = yearendtaxDetail.getMeternity_amt2(); //(18)-2출산보육수당2
	int	sum_meternity = meternity_amt + meternity_amt2;
	
	int	research_amt = yearendtaxDetail.getResearch_amt(); //(18)-4연구보조비
	int	research_amt2 = yearendtaxDetail.getResearch_amt2(); //(18)-4연구보조비2
	int sum_research = research_amt + research_amt2;
	
	int school_expenses_amt = yearendtaxDetail.getSchool_expenses_amt(); //(18)-5비과세학자금
	int school_expenses_amt2 = yearendtaxDetail.getSchool_expenses_amt2(); //(18)-5비과세학자금2
	int sum_school = school_expenses_amt + school_expenses_amt2;
	
	int collect_amt = yearendtaxDetail.getCollect_amt(); //(18)-6취재수당
	int collect_amt2 = yearendtaxDetail.getCollect_amt2(); //(18)-6취재수당2
	int sum_collect = collect_amt + collect_amt2;

	int remote_rural_area_amt = yearendtaxDetail.getRemote_rural_area_amt(); //(18)-7벽지수당
	int remote_rural_area_amt2 = yearendtaxDetail.getRemote_rural_area_amt2(); //(18)-7벽지수당2
	int sum_remote_rural_area = remote_rural_area_amt + remote_rural_area_amt2;

	int natural_disaster_amt = yearendtaxDetail.getNatural_disaster_amt(); //(18)-8천재지변등재해로받는급여
	int natural_disaster_amt2 = yearendtaxDetail.getNatural_disaster_amt2(); //(18)-8천재지변등재해로받는급여2
	int sum_natural_disaster = natural_disaster_amt + natural_disaster_amt2;
	
	int legislation_amt = yearendtaxDetail.getLegislation_amt(); //(18)-9법령조례의한미보수위원수당
	int legislation_amt2 = yearendtaxDetail.getLegislation_amt2(); //(18)-9법령조례의한미보수위원수당2
	int sum_legislation = legislation_amt + legislation_amt2;

	int operation_amt = yearendtaxDetail.getOperation_amt(); //(18)-10작전임무외국주둔군인급여
	int operation_amt2 = yearendtaxDetail.getOperation_amt2(); //(18)-10작전임무외국주둔군인급여2
	int sum_operation = operation_amt + operation_amt2;

	int stock_purchase_amt = yearendtaxDetail.getStock_purchase_amt(); //(18)-11주식매수선택권
	int stock_purchase_amt2 = yearendtaxDetail.getStock_purchase_amt2(); //(18)-11주식매수선택권2
	int sum_stock_purchase = stock_purchase_amt + stock_purchase_amt2;

	int foreigner_amt = yearendtaxDetail.getForeigner_amt(); //(18)-12외국인기술자소득세면제
	int foreigner_amt2 = yearendtaxDetail.getForeigner_amt2(); //(18)-12외국인기술자소득세면제2
	int sum_foreigner = foreigner_amt + foreigner_amt2;

	int employ_stock_amt1 = yearendtaxDetail.getEmploy_stock_amt1(); //(18)-14우리사주조합인출금50
	int employ_stock_amt1_2 = yearendtaxDetail.getEmploy_stock_amt1_2(); //(18)-14우리사주조합인출금50 1_2
	int sum_employ_stock = employ_stock_amt1 + employ_stock_amt1_2;

	int employ_stock_amt2 = yearendtaxDetail.getEmploy_stock_amt2(); //(18)-15우리사주조합인출금75
	int employ_stock_amt2_2 = yearendtaxDetail.getEmploy_stock_amt2_2(); //(18)-15우리사주조합인출금75 2_2
	int sum_employ_stock2 = employ_stock_amt2 + employ_stock_amt2_2;

	int submarine_mineral_amt = yearendtaxDetail.getSubmarine_mineral_amt(); //(18)-17해저광물자원개발과세특례
	int submarine_mineral_amt2 = yearendtaxDetail.getSubmarine_mineral_amt2(); //(18)-17해저광물자원개발과세특례2
	int sum_submarine_mineral = submarine_mineral_amt + submarine_mineral_amt2;

	int guard_embark_amt = yearendtaxDetail.getGuard_embark_amt(); //(18)-18경호수당/승선수당 등
	int guard_embark_amt2 = yearendtaxDetail.getGuard_embark_amt2(); //(18)-18경호수당/승선수당 등
	int sum_guard_embark = guard_embark_amt + guard_embark_amt2;

	int organization_amt = yearendtaxDetail.getOrganization_amt(); //(18)-19 외국정부국제기관근무자
	int organization_amt2 = yearendtaxDetail.getOrganization_amt2(); //(18)-19 외국정부국제기관근무자2
	int sum_organization = organization_amt + organization_amt2;

	int scholarship_amt = yearendtaxDetail.getScholarship_amt(); //(18)-21 교육기본법제28조1항장학금
	int scholarship_amt2 = yearendtaxDetail.getScholarship_amt2(); //(18)-21 교육기본법제28조1항장학금2
	int sum_scholarship = scholarship_amt + scholarship_amt2;

	int childcare_amt = yearendtaxDetail.getChildcare_amt(); //(18)-22 보육교사인건비
	int childcare_amt2 = yearendtaxDetail.getChildcare_amt2(); //(18)-22 보육교사인건비2
	int sum_childcare = childcare_amt + childcare_amt2;

	int kindergarten_teacher_amt = yearendtaxDetail.getKindergarten_teacher_amt(); //(18)-23 사립유치원수석교사의인건비
	int kindergarten_teacher_amt2 = yearendtaxDetail.getKindergarten_teacher_amt2(); //(18)-23 사립유치원수석교사의인건비2
	int sum_kindergarten = kindergarten_teacher_amt + kindergarten_teacher_amt2;

	int smiymjtc_amt = yearendtaxDetail.getSmiymjtc_amt(); //(18)-24 중소기업청년취업소득세감면
	int smiymjtc_amt2 = yearendtaxDetail.getSmiymjtc_amt2(); //(18)-24 중소기업청년취업소득세감면2
	int sum_smiymjtc = smiymjtc_amt + smiymjtc_amt2;

	int teache_clause_amt = yearendtaxDetail.getTeache_clause_amt(); //(18)-25 조세조약상교직자조항의소득세감면
	int teache_clause_amt2 = yearendtaxDetail.getTeache_clause_amt2(); //(18)-25 조세조약상교직자조항의소득세감면2
	int sum_teache_clause = teache_clause_amt + teache_clause_amt2;

	int move_amt = yearendtaxDetail.getMove_amt(); //(18)-26 정부지방이전기관종사자이주수당
	int move_amt2 = yearendtaxDetail.getMove_amt2(); //(18)-26 정부지방이전기관종사자이주수당
	int sum_move = move_amt + move_amt2;

	int major_amt = yearendtaxDetail.getMajor_amt(); //(19) 수련보조수당
	int major_amt2 = yearendtaxDetail.getMajor_amt2(); //(19) 수련보조수당2
	int sum_major = major_amt + major_amt2;

	int free_tax_sum = yearendtaxDetail.getFree_tax_sum(); //(20) 비과세소득계
	int total_free = yearendtaxDetail.getTotal_free(); //(20) 비과세소득계
	int total_free2 = yearendtaxDetail.getTotal_free2(); //(20) 비과세소득계2
	int sum_free_tax = free_tax_sum + total_free + total_free2;

	int prev_reduction_amt = yearendtaxDetail.getPrev_reduction_amt(); //(20)-1 감면소득계
	int prev_reduction_amt2 = yearendtaxDetail.getPrev_reduction_amt2(); //(20)-1 감면소득계2
	int sum_prev_reduction = prev_reduction_amt + prev_reduction_amt2;

	/* 세액명세 */
	int determine_income_tax = yearendtaxDetail.getDetermine_income_tax();
	int determine_jumin_tax = yearendtaxDetail.getDetermine_jumin_tax();
	int determine_nong_tax = yearendtaxDetail.getDetermine_nong_tax();
	int prev_income_amt = yearendtaxDetail.getPrev_income_amt();
	int prev_jumin_amt = yearendtaxDetail.getPrev_jumin_amt();
	int prev_nong_amt = yearendtaxDetail.getPrev_nong_amt();
	int prev_income_amt2 = yearendtaxDetail.getPrev_income_amt2();
	int prev_jumin_amt2 = yearendtaxDetail.getPrev_jumin_amt2();
	int prev_nong_amt2 = yearendtaxDetail.getPrev_nong_amt2();
	
	int jing_jong_income_tax = yearendtaxDetail.getJing_jong_income_tax();
	int jing_jong_jumin_tax = yearendtaxDetail.getJing_jong_jumin_tax();
	int jing_jong_nong_tax = yearendtaxDetail.getJing_jong_nong_tax();
	int jing_income_tax = yearendtaxDetail.getJing_income_tax();
	int jing_jumin_tax = yearendtaxDetail.getJing_jumin_tax();
	int jing_nong_tax = yearendtaxDetail.getJing_nong_tax();
	int deduction_income_tax = yearendtaxDetail.getDeduction_income_tax();
	int deduction_jumin_tax = yearendtaxDetail.getDeduction_jumin_tax();
	int deduction_nong_tax = yearendtaxDetail.getDeduction_nong_tax();
	
	int kuk_yeon_amt = yearendtaxDetail.getKuk_yeon_amt(); //국민연금 
	int insurance_health_amt = yearendtaxDetail.getInsurance_health_amt(); //의료보험
	int insurance_employ_amt = yearendtaxDetail.getInsurance_employ_amt(); //고용보험
	
	//2번째 페이지
	int yt_salary = yearendtaxDetail2.getYt_salary(); //(21)총급여((16), 다만 외국인단일세율 적용시에는 연간근로소득)
	int yt_work_deduct_amt = yearendtaxDetail2.getYt_work_deduct_amt(); //(22)근로소득공제
	int yt_work_income_amt = yearendtaxDetail2.getYt_work_income_amt(); //(23)근로소득공제
	int family_person_amt = yearendtaxDetail2.getFamily_person_amt(); //(24)본        인
	int family_mate_amt = yearendtaxDetail2.getFamily_mate_amt(); //(25)배   우   자
	int family_dependent_amt = yearendtaxDetail2.getFamily_dependent_amt(); //(26)부 양  가 족
	int family_dependent_cnt = yearendtaxDetail2.getFamily_dependent_cnt(); //부양가족 명
	int family_old2_cnt = yearendtaxDetail2.getFamily_old2_cnt(); //경로우대 명
	int family_disabled_person_cnt = yearendtaxDetail2.getFamily_disabled_person_cnt(); //장애인 명
	int family_old_amt = yearendtaxDetail2.getFamily_old_amt(); //(27)경 로  우 대
	int family_disabled_person_amt = yearendtaxDetail2.getFamily_disabled_person_amt(); //(28)장   애   인
	int family_women_amt = yearendtaxDetail2.getFamily_women_amt(); //(29)부   녀   자
	int family_single_parent_amt = yearendtaxDetail2.getFamily_single_parent_amt(); //(30)한 부 모 가 족  
	//int kuk_yeon_amt = yearendtaxDetail2.getKuk_yeon_amt(); //(31)국민연금보험료
	//int insurance_health_amt = yearendtaxDetail2.getInsurance_health_amt(); // (33)보험료 (가)건강보험료
	//int insurance_employ_amt = yearendtaxDetail2.getInsurance_employ_amt(); // (33)보험료 (나)고용보험료
	int house_lease_loan_amt = yearendtaxDetail2.getHouse_lease_loan_amt(); //(34)주택자금(가)주택임차차입금원리금상환액-대출기관
	int house_security_loan1_amt = yearendtaxDetail2.getHouse_security_loan1_amt(); //(34)주택자금(다)1
	int house_security_loan2_amt = yearendtaxDetail2.getHouse_security_loan2_amt(); //(34)주택자금(다)2
	int house_security_loan3_amt = yearendtaxDetail2.getHouse_security_loan3_amt(); //(34)주택자금(다)3
	int house_security_loan4_amt = yearendtaxDetail2.getHouse_security_loan4_amt(); //(34)주택자금(다)4
	int house_security_loan5_amt = yearendtaxDetail2.getHouse_security_loan5_amt(); //(34)주택자금(다)5
	int yt_special_deduct_amt = yearendtaxDetail2.getYt_special_deduct_amt(); //(36)계
	int yt_deduction_amt = yearendtaxDetail2.getYt_deduction_amt(); //(37)차 감 소 득 금 액
	int personal_annuity_amt = yearendtaxDetail2.getPersonal_annuity_amt(); //(38)개인연금저축
	int etc_subscription_deposit_amt = yearendtaxDetail2.getEtc_subscription_deposit_amt(); //(40)주택마련저축소득공제(가)청약저축
	int etc_house_subscr_deposit_amt = yearendtaxDetail2.getEtc_house_subscr_deposit_amt(); //(40)주택마련저축소득공제(나)주택청약종합저축
	int etc_home_mortgage_amt = yearendtaxDetail2.getEtc_home_mortgage_amt(); //(40)주택마련저축소득공제(다)근로자주택마련저축
	int total_use_amt = yearendtaxDetail2.getTotal_use_amt(); //(42)신용카드 등 사용액
	int etc_employ_stock_amt = yearendtaxDetail2.getEtc_employ_stock_amt(); //(43)우리사주조합 출연금
	int etc_chunk_money_amt = yearendtaxDetail2.getEtc_chunk_money_amt(); //(46)목돈 안드는 전세 이자상환액
	int etc_long_invest_stock_amt = yearendtaxDetail2.getEtc_long_invest_stock_amt(); //(47)장기집합투자증권저축
	int total_etc_amt = yearendtaxDetail2.getTotal_etc_amt(); //(48)그 밖의 소득공제 계
	int etc_exceed_limit_amt = yearendtaxDetail2.getEtc_exceed_limit_amt(); //(49)특별공제 종합한도 초과액
	int yt_income_tax_standard_amt = yearendtaxDetail2.getYt_income_tax_standard_amt(); //(50)종합소득 과세표준
	int yt_calculate_amt = yearendtaxDetail2.getYt_calculate_amt(); //(51)산출세액
	int etc_income_tax_reduction_amt = yearendtaxDetail2.getEtc_income_tax_reduction_amt(); //(52) 「소득세법」
	int etc_smiymjtc_amt = yearendtaxDetail2.getEtc_smiymjtc_amt(); //(54) 「조세특례제한법」제30조
	int sum_etc = etc_income_tax_reduction_amt + etc_smiymjtc_amt; //(56)세 액 감 면 계
	int yt_earned_income_amt = yearendtaxDetail2.getYt_earned_income_amt(); //(57)근로소득
	int etc_children_amt = yearendtaxDetail2.getEtc_children_amt(); //(58)자녀
	int annunity_retire_amt_o = yearendtaxDetail2.getAnnunity_retire_amt_o(); //(60)「근로자퇴직급여보장법」에 따른 퇴직연금 공제대상금액
	int annuity_retire_amt = yearendtaxDetail2.getAnnuity_retire_amt(); //(60)「근로자퇴직급여보장법」에 따른 퇴직연금 세액공제액 
	int annuity_saving_amt_o = yearendtaxDetail2.getAnnuity_saving_amt_o(); //(61)연금저축
	int annuity_savng_amt = yearendtaxDetail2.getAnnuity_savng_amt(); //(61)연금저축
	int insurance_amt_o = yearendtaxDetail2.getInsurance_amt_o(); //(62)보장성보험
	int total_insurance_amt = yearendtaxDetail2.getTotal_insurance_amt();//(62)보장성보험
	int medical_amt_o = yearendtaxDetail2.getMedical_amt_o(); //(63)의  료  비
	int total_medical_amt = yearendtaxDetail2.getTotal_medical_amt(); //(63)의  료  비
	int edcate_amt_o = yearendtaxDetail2.getEdcate_amt_o(); //(64)교  육  비
	int total_edcate_amt = yearendtaxDetail2.getTotal_edcate_amt(); //(64)교  육  비
	int contribue_politic_amt_o = yearendtaxDetail2.getContribue_politic_amt_o(); //(65)기부금
	int contribue_politic_amt = yearendtaxDetail2.getContribue_politic_amt(); //(65)기부금
	int contribue_politic_over_amt_o = yearendtaxDetail2.getContribue_politic_over_amt_o(); //(65)기부금
	int contribue_politic_over_amt = yearendtaxDetail2.getContribue_politic_over_amt(); //(65)기부금
	int contribue_law_amt = yearendtaxDetail2.getContribue_law_amt(); //(65)기부금
	int compute_4 = yearendtaxDetail2.getContribue_designate_amt(); //(65)기부금
	int yt_special_tax_deduct_amt = yearendtaxDetail2.getYt_special_tax_deduct_amt(); //(66)계
	int yt_standard_deduct_amt = yearendtaxDetail2.getYt_standard_deduct_amt(); //(67)표 준  공 제
	int etc_sework_tx_amt = yearendtaxDetail2.getEtc_sework_tx_amt(); //(68)납세조합공제
	int etc_house_laon_interest_amt = yearendtaxDetail2.getEtc_house_laon_interest_amt(); //(69)주택차입금
	int etc_foreigner_income = yearendtaxDetail2.getEtc_foreigner_income(); //(70)외국납부
	int house_month_lent_amt = yearendtaxDetail2.getHouse_month_lent_amt(); //(71)월세액
	int yt_tax_deduct_amt = yearendtaxDetail2.getYt_tax_deduct_amt(); //(72)세액공제계
	int yt_determine_tax = yearendtaxDetail2.getYt_determine_tax(); //결 정 세 액 (51)-(56)-(72)
	
	/* 3페이지 */
	int dajayeon_cnt = 0; //자녀수
	int sum_medical_1 = 0; //의료비1
	int sum_medical_2 = 0; //의료비2
	int sum_educate_1 = 0; //교육비1
	int sum_educate_2 = 0; //교육비2
	int sum_credit_1  = 0; //신용카드1
	int sum_credit_2  = 0; //신용카드2
	int sum_direct_1  = 0; //직불카드1
	int sum_direct_2  = 0; //직불카드2
	int sum_cash_1 = 0; //현금영수증1
	int sum_cash_2 = 0; //현금영수증2
	int sum_market_1 = 0; //전통시장1
	int sum_market_2 = 0; //전통시장2
	int sum_pubric_transport_1 = 0; //대중교통1
	int sum_pubric_transport_2 = 0; //대중교통2
	int sum_contribute_1 = 0; //기부금1
	int sum_contribute_2 = 0; //기부금2
	
	if(yearendtaxDetail3 != null){
		for(int i=0; yearendtaxDetail3.size()>i;i++){
			YearendtaxVO yearendtaxVO = new YearendtaxVO();
			yearendtaxVO = yearendtaxDetail3.get(i);
			dajayeon_cnt = yearendtaxVO.getDajayeon_cnt();
			sum_medical_1 += yearendtaxVO.getMedical_1();
			sum_medical_2 += yearendtaxVO.getMedical_2();
			sum_educate_1 += yearendtaxVO.getEducate_1();
			sum_educate_2 += yearendtaxVO.getEducate_2();
			sum_credit_1 += yearendtaxVO.getCredit_1();
			sum_credit_2 += yearendtaxVO.getCredit_2();
			sum_direct_1 += yearendtaxVO.getDirect_1();
			sum_direct_2 += yearendtaxVO.getDirect_2();
			sum_cash_1 += yearendtaxVO.getCash_1();
			sum_cash_2 += yearendtaxVO.getCash_2();
			sum_market_1 += yearendtaxVO.getMarket_1();
			sum_market_2 += yearendtaxVO.getMarket_2();
			sum_pubric_transport_1 += yearendtaxVO.getPubric_transport_1();
			sum_pubric_transport_2 += yearendtaxVO.getPubric_transport_2();
			sum_contribute_1 += yearendtaxVO.getContribute_1();
			sum_contribute_2 += yearendtaxVO.getContribute_2();
		}
	}
	
	int all_sum_insurance = 0;	//국세청 보장성
	int all_sum_insurance2 = 0; //기타 보장성
	
%>
<!DOCTYPE html>
<html lang="ko">
<head>
	<%@include file="/include/head.jsp"%>
	<script type="text/javascript">
		$(document).ready(function(){
			/*보장성 보험 값셋팅*/
			$("#sum_insurance").text($("#all_sum_insurance").val());
			$("#sum_insurance2").text($("#all_sum_insurance2").val());
		});
		
		/* 미리보기 */
		function previewYearendtaxDetail(year){
			Commons.popupOpen('previewPop','<%=GROUP_ROOT%>/yt/yearendtax/previewYearendtaxDetail.do?year='+year,'850','900');
		}
		
		/* 목록화면 */
		function goYearendtaxList(){
			location.href = "<%=GROUP_ROOT%>/yt/yearendtax/yearendtaxList.do";
		}
		
	</script>
</head>
<body>
	<%@include file="/include/header.jsp"%>
	<%@include file="/include/gnb.jsp"%>
	
	<div class="wrap_con">
		<div class="content">
			<%@include file="/include/location.jsp"%>
			<%@include file="/include/lnb.jsp"%>
			<!-- ######## start ####### -->
			<div class="cont float_left">
				<h2>원천징수영수증</h2>
				<div class="list_btn last_step">
					<div class="list_button tar">
						<button type="button" onclick="javascript:goYearendtaxList();">목록</button>
						<%
						if(authorityMemberList != null){
							for(int i=0; authorityMemberList.size()>i;i++){
								MemberVO memberVO = new MemberVO();
								memberVO = authorityMemberList.get(i);
								if(emp_no.equals(memberVO.getEmp_no())){
						%>
						<button type="button" onclick="javascript:previewYearendtaxDetail('<%=year%>');">미리보기</button>
						<%
								}
							}
						} 
						%>
					</div>
				</div>
				<div class="wrap_withholding_receipt">
					<ul class="tab">
						<!-- 활성화 탭 버튼 : li에 on 클래스 추가-->
						<li class="on" rel="tab1">PAGE 1</li>
						<li rel="tab2">PAGE 2</li>
						<li rel="tab3">PAGE 3</li>
					</ul>
					<div class="wrap_receipt_container">
						<div id="tab1" class="wrap_receipt_midbox">
							<div class="page_info">(1 쪽)</div>
							<div class="wrap_receipt">
								<div class="top">
									<div class="tit">
										<h3>[<span class="space"></span>]근로소득 원천징수영수증<br />[<span class="space"></span>]근로소득 <span>지급명세서</span></h3>
										<p>([<span>√</span>] 소득자 보관용&nbsp;&nbsp;[<span></span>] 발행자 보관용&nbsp;&nbsp;[<span></span>] 발행자 보고용)</p>
									</div>
									<div class="number">
										<strong>관리<br />번호</strong>
										<span></span>
									</div>
									<div class="tell">
										<table>
											<colgroup>
												<col style="width:80px"/>
												<col style="width:28px"/>
												<col style="width:91px"/>
												<col style=""/>
											</colgroup>
											<tbody>
												<tr>
													<th colspan="2">거주구분</th>
													<!-- 체크 표시된 영역 span에 on 클래스 추가-->
													<td colspan="2" class="bdrn">
														<%if(1==foreign_cd){%><span><em class="select_on"><img src="/hanagw/asset/img/bg_select.png"></em>거주자1</span><%}else{%><span>거주자1<em style="display:none"></em></span><%}%>/
														<%if(0==foreign_cd){%><span><em class="select_on2"><img src="/hanagw/asset/img/bg_select2.png"></em>비거주자2</span><%}else{%><span>비거주자2<em style="display:none"></em></span><%}%></td> <!-- 2015-02-27 수정 -->
												</tr>
												<tr>
													<th>거주지국</th>
													<td></td>
													<th>거주지국코드</th>
													<td class="bdrn"></td>
												</tr>
												<tr>
													<th colspan="2">내 · 외국인</th>
													<td colspan="2" class="bdrn">
														<%if(1==foreign_cd){%><span class="on"><em class="select_on"><img src="/hanagw/asset/img/bg_select.png"></em>내국인1</span><%}else{%><span>내국인1<em style="display:none"></em></span><%}%>/
														<%if(0==foreign_cd){%><span><em class="select_on"><img src="/hanagw/asset/img/bg_select.png"></em>외국인9</span><%}else{%><span><em style="display:none"></em>외국인9</span><%}%></td><!-- 2015-02-27 수정 -->
												</tr>
												<tr>
													<th colspan="2">외국인단일세율적용</th>
													<td colspan="2" class="bdrn">여1 / 부2</td> <!-- 2015-02-25 수정 -->
												</tr>
												<tr>
													<th>국적</th>
													<td></td>
													<th>국적코드</th>
													<td class="bdrn"></td>
												</tr>
												<tr>
													<th colspan="2">세대주여부</th>
													<td colspan="2" class="bdrn">세대주1, 세대원2</td> <!-- 2015-02-25 수정 -->
												</tr>
												<tr>
													<th colspan="2">연말정산구분</th>
													<td colspan="2" class="bdrn">계속근로1, 중도퇴사2</td> <!-- 2015-02-25 수정 -->
													
												</tr>
											</tbody>
										</table>
									</div>
								</div>
								<div class="main">
									<table class="tbl_receipt">
										<!-- start 2015-02-25 수정 -->
										<colgroup>
											<col style="width:100px;" />
											<col style="width:130px;" />
											<col />
											<col style="width:130px;" />
											<col style="width:180px;" />
										</colgroup>
										<!-- end 2015-02-25 수정 -->
										<tbody>
											<tr>
												<th rowspan="3" class="role">징&nbsp;&nbsp;수<br />의무자</th>
												<th class="bdrn">(1) 법인명(상호)</th>
												<td class="txtcnt"><%=comp_name %></td>
												<th class="bdrn" class="txtcnt">(2) 대표자(성명)</th>
												<td class="bdrn txtcnt"><%=president %></td>
											</tr>
											<tr>
												<th class="bdrn">(3) 사업자등록번호</th>
												<td class="txtcnt"><%=vou_no %></td>
												<th class="bdrn">(4) 주민등록번호</th>
												<td class="bdrn txtcnt"><%=comp_no %></td>
											</tr>
											<tr>
												<th class="bdrn">(5) 소재지(주소)</th>
												<td colspan="3" class="bdrn"><%=com_addr %></td>
											</tr>
											<tr>
												<th rowspan="2" class="role">소득자</th>
												<th class="bdrn">(6) 성명</th>
												<td class="txtcnt"><%=emp_ko_nm %></td>
												<th class="bdrn">(7) 주민등록번호</th>
												<td class="bdrn txtcnt"><%=resid_no %></td>
											</tr>
											<tr>
												<th class="bdrn">(8) 주소</th>
												<td colspan="3" class="bdrn"><%=bas_addr %></td>
											</tr>
											<tr>
												<td colspan="5" class="inner nobd">
													<div>
														<table class="tbl_receipt">
															<!-- start 2015-02-25 수정 -->
															<colgroup>
																<col style="width:28px"/>
																<col style="width:208px"/>
																<col style="width:22px"/>
																<col style="width:120px"/>
																<col style="width:120px"/>
																<col style="width:120px"/>
																<col />
																<col style="width:90px"/>
															</colgroup>
															<!-- end 2015-02-25 수정 -->
															<tbody>
																<tr class="tit_row">
																	<th rowspan="13"><span>Ⅰ<br />근<br />무<br />처<br />별<br />소<br />득<br />명<br />세</span></th>
																	<th colspan="2" class="tac">구분</th>
																	<th class="tac">주(현)</th>
																	<th class="tac">종(전)</th>
																	<th class="tac">종(전)</th>
																	<th class="tac pdzero">(16)-1납세조합</th> <!-- 2015-02-25 수정 -->
																	<th class="bdrn tac">합계</th>
																</tr>
																<tr>
																	<th colspan="2">(9) 근무처명</th>
																	<td class="ipt_disable img_size"><img src="/hanagw/asset/img/bg_gbox.jpg"></td> <!-- 2015-02-27 수정 -->
																	<td><%=corporate_nm %></td>
																	<td><%=corporate_nm2 %></td>
																	<td></td>
																	<td class="bdrn"></td>
																</tr>
																<tr>
																	<th colspan="2">(10) 사업자등록번호</th>
																	<td class="ipt_disable img_size"><img src="/hanagw/asset/img/bg_gbox.jpg"></td> <!-- 2015-02-27 수정 -->
																	<td><%=corporate_no %></td>
																	<td><%=corporate_no2 %></td>
																	<td></td>
																	<td class="bdrn"></td>
																</tr>
																<tr>
																	<th colspan="2">(11) 근무기간</th>
																	<td class="pdzero numspace"><%=str_bas_start_dt%>~<%=str_bas_end_dt %></td>
																	<td class="pdzero numspace"><%=str_start_work_dt%>~<%=str_end_work_dt%></td>
																	<td class="pdzero numspace"><%=str_start_work_dt2%>~<%=str_start_work_dt2%></td>
																	<td></td>
																	<td class="bdrn"></td>
																</tr>
																<tr>
																	<th colspan="2">(12) 감면기간</th>
																	<td class="pdzero"></td>
																	<td class="pdzero numspace"><%=str_start_reduce_dt %>~<%=str_end_reduce_dt %></td>
																	<td class="pdzero numspace"><%=str_start_reduce_dt2 %>~<%=str_end_reduce_dt2 %></td>
																	<td></td>
																	<td class="bdrn"></td>
																</tr>
																<tr>
																	<th colspan="2">(13) 급여</th>
																	<td class="txtright"><%=StringUtil.makeMoneyTypeIsNull(pay_sum)%></td>
																	<td class="txtright"><%=StringUtil.makeMoneyTypeIsNull(salary_amt)%></td>
																	<td class="txtright"><%=StringUtil.makeMoneyTypeIsNull(salary_amt2)%></td>
																	<td></td>
																	<td class="bdrn txtright"><%=StringUtil.makeMoneyTypeIsNull(sum_salary)%></td>
																</tr>
																<tr>
																	<th colspan="2">(14) 상여</th>
																	<td class="txtright"><%=StringUtil.makeMoneyTypeIsNull(bonus_sum)%></td>
																	<td class="txtright"><%=StringUtil.makeMoneyTypeIsNull(bonus_amt)%></td>
																	<td class="txtright"><%=StringUtil.makeMoneyTypeIsNull(bonus_amt2)%></td>
																	<td></td>
																	<td class="bdrn txtright"><%=StringUtil.makeMoneyTypeIsNull(sum_bonus)%></td>
																</tr>
																<tr>
																	<th colspan="2">(15) 인정상여</th>
																	<td></td>
																	<td class="txtright"><%=StringUtil.makeMoneyTypeIsNull(constructive_bonus_amt)%></td>
																	<td class="txtright"><%=StringUtil.makeMoneyTypeIsNull(constructive_bonus_amt2)%></td>
																	<td></td>
																	<td class="bdrn txtright"><%=StringUtil.makeMoneyTypeIsNull(sum_constructive)%></td>
																</tr>
																<tr>
																	<th colspan="2">(15)-1 주식매수선택권 행사이익</th>
																	<td></td>
																	<td class="txtright"><%=StringUtil.makeMoneyTypeIsNull(stock_option_amt)%></td>
																	<td class="txtright"><%=StringUtil.makeMoneyTypeIsNull(stock_option_amt2)%></td>
																	<td></td>
																	<td class="bdrn txtright"><%=StringUtil.makeMoneyTypeIsNull(sum_stock)%></td>
																</tr>
																<tr>
																	<th colspan="2">(15)-2 우리사주조합인출금</th>
																	<td></td>
																	<td class="txtright"><%=StringUtil.makeMoneyTypeIsNull(employ_stock_amt)%></td>
																	<td class="txtright"><%=StringUtil.makeMoneyTypeIsNull(employ_stock_amt_2)%></td>
																	<td></td>
																	<td class="bdrn txtright"><%=StringUtil.makeMoneyTypeIsNull(sum_employ)%></td>
																</tr>
																<tr>
																	<th colspan="2">(15)-3 임원 퇴직소득금액 한도초과액</th>
																	<td></td>
																	<td class="txtright"><%=StringUtil.makeMoneyTypeIsNull(derector_retirement_amt)%></td>
																	<td class="txtright"><%=StringUtil.makeMoneyTypeIsNull(derector_retirement_amt2)%></td>
																	<td></td>
																	<td class="bdrn txtright"><%=StringUtil.makeMoneyTypeIsNull(sum_derector)%></td>
																</tr>
																<tr>
																	<th colspan="2">(15)-4</th>
																	<td></td>
																	<td></td>
																	<td></td>
																	<td></td>
																	<td class="bdrn"></td>
																</tr>
																<tr>
																	<th colspan="2">(16) 계</th>
																	<td class="txtright"><%=StringUtil.makeMoneyTypeIsNull(sum_total)%></td>
																	<td class="txtright"><%=StringUtil.makeMoneyTypeIsNull(total_salary)%></td>
																	<td class="txtright"><%=StringUtil.makeMoneyTypeIsNull(total_salary2)%></td>
																	<td></td>
																	<td class="bdrn txtright"><%=StringUtil.makeMoneyTypeIsNull(pay_sum_total)%></td>
																</tr>
																<tr class="tit_row">
																	<th rowspan="30"><span>Ⅱ<br />비<br />과<br />세<br />및<br />감<br />면<br />소<br />득<br />명<br />세</span></th>
																	<th class="tal">(18) 국외근로</th>
																	<td></td>
																	<td></td>
																	<td class="txtright"><%=StringUtil.makeMoneyTypeIsNull(foreign_work_amt)%></td>
																	<td class="txtright"><%=StringUtil.makeMoneyTypeIsNull(foreign_work_amt2)%></td>
																	<td></td>
																	<td class="bdrn txtright"><%=StringUtil.makeMoneyTypeIsNull(sum_foreign)%></td>
																</tr>
																<tr>
																	<th>(18)-1 야간근로수당</th>
																	<td></td>
																	<td class="txtright"><%=StringUtil.makeMoneyTypeIsNull(free_overtime_sum)%></td>
																	<td class="txtright"><%=StringUtil.makeMoneyTypeIsNull(oevrtime_amt)%></td>
																	<td class="txtright"><%=StringUtil.makeMoneyTypeIsNull(oevrtime_amt2)%></td>
																	<td class="txtright"></td>
																	<td class="bdrn"><%=StringUtil.makeMoneyTypeIsNull(sum_oevrtime)%></td>
																</tr>
																<tr>
																	<th>(18)-2 출산보육수당</th>
																	<td></td>
																	<td></td>
																	<td class="txtright"><%=StringUtil.makeMoneyTypeIsNull(meternity_amt)%></td>
																	<td class="txtright"><%=StringUtil.makeMoneyTypeIsNull(meternity_amt2)%></td>
																	<td></td>
																	<td class="bdrn txtright"><%=StringUtil.makeMoneyTypeIsNull(sum_meternity)%></td>
																</tr>
																<tr>
																	<th>(18)-3</th>
																	<td></td>
																	<td></td>
																	<td></td>
																	<td></td>
																	<td></td>
																	<td class="bdrn txtright"></td>
																</tr>
																<tr>
																	<th>(18)-4 연구보조비</th>
																	<td></td>
																	<td></td>
																	<td class="txtright"><%=StringUtil.makeMoneyTypeIsNull(research_amt)%></td>
																	<td class="txtright"><%=StringUtil.makeMoneyTypeIsNull(research_amt2)%></td>
																	<td></td>
																	<td class="bdrn txtright"><%=StringUtil.makeMoneyTypeIsNull(sum_research)%></td>
																</tr>
																<tr>
																	<th>(18)-5 비과세학자금</th>
																	<td></td>
																	<td></td>
																	<td class="txtright"><%=StringUtil.makeMoneyTypeIsNull(school_expenses_amt)%></td>
																	<td class="txtright"><%=StringUtil.makeMoneyTypeIsNull(school_expenses_amt2)%></td>
																	<td></td>
																	<td class="bdrn txtright"><%=StringUtil.makeMoneyTypeIsNull(sum_school)%></td>
																</tr>
																<tr>
																	<th>(18)-6 취재수당</th>
																	<td></td>
																	<td></td>
																	<td class="txtright"><%=StringUtil.makeMoneyTypeIsNull(collect_amt)%></td>
																	<td class="txtright"><%=StringUtil.makeMoneyTypeIsNull(collect_amt2)%></td>
																	<td></td>
																	<td class="bdrn txtright"><%=StringUtil.makeMoneyTypeIsNull(sum_collect)%></td>
																</tr>
																<tr>
																	<th>(18)-7 벽지수당</th>
																	<td></td>
																	<td></td>
																	<td class="txtright"><%=StringUtil.makeMoneyTypeIsNull(remote_rural_area_amt)%></td>
																	<td class="txtright"><%=StringUtil.makeMoneyTypeIsNull(remote_rural_area_amt2)%></td>
																	<td></td>
																	<td class="bdrn txtright"><%=StringUtil.makeMoneyTypeIsNull(sum_remote_rural_area)%></td>
																</tr>
																<tr>
																	<th>(18)-8 천재지변등재해로받는급여</th>
																	<td></td>
																	<td></td>
																	<td class="txtright"><%=StringUtil.makeMoneyTypeIsNull(natural_disaster_amt)%></td>
																	<td class="txtright"><%=StringUtil.makeMoneyTypeIsNull(natural_disaster_amt2)%></td>
																	<td></td>
																	<td class="bdrn txtright"><%=StringUtil.makeMoneyTypeIsNull(sum_natural_disaster)%></td>
																</tr>
																<tr>
																	<th>(18)-9 법령조례의한미보수위원수당</th>
																	<td></td>
																	<td></td>
																	<td class="txtright"><%=StringUtil.makeMoneyTypeIsNull(legislation_amt)%></td>
																	<td class="txtright"><%=StringUtil.makeMoneyTypeIsNull(legislation_amt2)%></td>
																	<td></td>
																	<td class="bdrn txtright"><%=StringUtil.makeMoneyTypeIsNull(sum_legislation)%></td>
																</tr>
																<tr>
																	<th>(18)-10 작전임무외국주둔군인급여</th>
																	<td></td>
																	<td></td>
																	<td class="txtright"><%=StringUtil.makeMoneyTypeIsNull(operation_amt)%></td>
																	<td class="txtright"><%=StringUtil.makeMoneyTypeIsNull(operation_amt2)%></td>
																	<td></td>
																	<td class="bdrn txtright"><%=StringUtil.makeMoneyTypeIsNull(sum_operation)%></td>
																</tr>
																<tr>
																	<th>(18)-11 주식매수선택권</th>
																	<td></td>
																	<td></td>
																	<td class="txtright"><%=StringUtil.makeMoneyTypeIsNull(stock_purchase_amt)%></td>
																	<td class="txtright"><%=StringUtil.makeMoneyTypeIsNull(stock_purchase_amt2)%></td>
																	<td></td>
																	<td class="bdrn txtright"><%=StringUtil.makeMoneyTypeIsNull(sum_stock_purchase)%></td>
																</tr>
																<tr>
																	<th>(18)-12 외국인기술자소득세면제</th>
																	<td></td>
																	<td></td>
																	<td class="txtright"><%=StringUtil.makeMoneyTypeIsNull(foreigner_amt)%></td>
																	<td class="txtright"><%=StringUtil.makeMoneyTypeIsNull(foreigner_amt2)%></td>
																	<td></td>
																	<td class="bdrn txtright"><%=StringUtil.makeMoneyTypeIsNull(sum_foreigner)%></td>
																</tr>
																<tr>
																	<th>(18)-13</th>
																	<td></td>
																	<td></td>
																	<td></td>
																	<td></td>
																	<td></td>
																	<td class="bdrn"></td>
																</tr>
																<tr>
																	<th>(18)-14 우리사주조합인출금50</th>
																	<td></td>
																	<td></td>
																	<td class="txtright"><%=StringUtil.makeMoneyTypeIsNull(employ_stock_amt1)%></td>
																	<td class="txtright"><%=StringUtil.makeMoneyTypeIsNull(employ_stock_amt1_2)%></td>
																	<td></td>
																	<td class="bdrn txtright"><%=StringUtil.makeMoneyTypeIsNull(sum_employ_stock)%></td>
																</tr>
																<tr>
																	<th>(18)-15 우리사주조합인출금75</th>
																	<td></td>
																	<td></td>
																	<td class="txtright"><%=StringUtil.makeMoneyTypeIsNull(employ_stock_amt2)%></td>
																	<td class="txtright"><%=StringUtil.makeMoneyTypeIsNull(employ_stock_amt2_2)%></td>
																	<td></td>
																	<td class="bdrn txtright"><%=StringUtil.makeMoneyTypeIsNull(sum_employ_stock2)%></td>
																</tr>
																<tr>
																	<th>(18)-16</th>
																	<td></td>
																	<td></td>
																	<td></td>
																	<td></td>
																	<td></td>
																	<td class="bdrn txtright"></td>
																</tr>
																<tr>
																	<th>(18)-17 해저광물자원개발과세특례</th>
																	<td></td>
																	<td></td>
																	<td class="txtright"><%=StringUtil.makeMoneyTypeIsNull(submarine_mineral_amt)%></td>
																	<td class="txtright"><%=StringUtil.makeMoneyTypeIsNull(submarine_mineral_amt2)%></td>
																	<td></td>
																	<td class="bdrn txtright"><%=StringUtil.makeMoneyTypeIsNull(sum_submarine_mineral)%></td>
																</tr>
																<tr>
																	<th>(18)-18 경호수당/승선수당 등</th>
																	<td></td>
																	<td></td>
																	<td class="txtright"><%=StringUtil.makeMoneyTypeIsNull(guard_embark_amt)%></td>
																	<td class="txtright"><%=StringUtil.makeMoneyTypeIsNull(guard_embark_amt2)%></td>
																	<td></td>
																	<td class="bdrn txtright"><%=StringUtil.makeMoneyTypeIsNull(sum_guard_embark)%></td>
																</tr>
																<tr>
																	<th>(18)-19 외국정부국제기관근무자</th>
																	<td></td>
																	<td></td>
																	<td class="txtright"><%=StringUtil.makeMoneyTypeIsNull(organization_amt) %></td>
																	<td class="txtright"><%=StringUtil.makeMoneyTypeIsNull(organization_amt2)%></td>
																	<td></td>
																	<td class="bdrn txtright"><%=StringUtil.makeMoneyTypeIsNull(sum_organization)%></td>
																</tr>
																<tr>
																	<th>(18)-20</th>
																	<td></td>
																	<td></td>
																	<td></td>
																	<td></td>
																	<td></td>
																	<td class="bdrn"></td>
																</tr>
																<tr>
																	<th>(18)-21 교육기본법제28조1항장학금</th>
																	<td></td>
																	<td></td>
																	<td class="txtright"><%=StringUtil.makeMoneyTypeIsNull(scholarship_amt)%></td>
																	<td class="txtright"><%=StringUtil.makeMoneyTypeIsNull(scholarship_amt2)%></td>
																	<td></td>
																	<td class="bdrn txtright"><%=StringUtil.makeMoneyTypeIsNull(sum_scholarship)%></td>
																</tr>
																<tr>
																	<th>(18)-22 보육교사인건비</th>
																	<td></td>
																	<td></td>
																	<td class="txtright"><%=StringUtil.makeMoneyTypeIsNull(childcare_amt)%></td>
																	<td class="txtright"><%=StringUtil.makeMoneyTypeIsNull(childcare_amt2)%></td>
																	<td></td>
																	<td class="bdrn txtright"><%=StringUtil.makeMoneyTypeIsNull(sum_childcare)%></td>
																</tr>
																<tr>
																	<th>(18)-23 사립유치원수석교사의인건비</th>
																	<td></td>
																	<td></td>
																	<td class="txtright"><%=StringUtil.makeMoneyTypeIsNull(kindergarten_teacher_amt)%></td>
																	<td class="txtright"><%=StringUtil.makeMoneyTypeIsNull(kindergarten_teacher_amt2)%></td>
																	<td></td>
																	<td class="bdrn txtright"><%=StringUtil.makeMoneyTypeIsNull(sum_kindergarten)%></td>
																</tr>
																<tr>
																	<th>(18)-24 중소기업청년취업소득세감면</th>
																	<td></td>
																	<td></td>
																	<td class="txtright"><%=StringUtil.makeMoneyTypeIsNull(smiymjtc_amt)%></td>
																	<td class="txtright"><%=StringUtil.makeMoneyTypeIsNull(smiymjtc_amt2)%></td>
																	<td></td>
																	<td class="bdrn txtright"><%=StringUtil.makeMoneyTypeIsNull(sum_smiymjtc)%></td>
																</tr>
																<tr>
																	<th>(18)-25 조세조약상교직자조항의소득세감면</th>
																	<td></td>
																	<td></td>
																	<td class="txtright"><%=StringUtil.makeMoneyTypeIsNull(teache_clause_amt)%></td>
																	<td class="txtright"><%=StringUtil.makeMoneyTypeIsNull(teache_clause_amt2)%></td>
																	<td></td>
																	<td class="bdrn txtright"><%=StringUtil.makeMoneyTypeIsNull(sum_teache_clause)%></td>
																</tr>
																<tr>
																	<th>(18)-26 정부지방이전기관종사자이주수당</th>
																	<td></td>
																	<td></td>
																	<td class="txtright"><%=StringUtil.makeMoneyTypeIsNull(move_amt)%></td>
																	<td class="txtright"><%=StringUtil.makeMoneyTypeIsNull(move_amt2)%></td>
																	<td></td>
																	<td class="bdrn txtright"><%=StringUtil.makeMoneyTypeIsNull(sum_move)%></td>
																</tr>
																<tr>
																	<th>(19) 수련보조수당</th>
																	<td></td>
																	<td></td>
																	<td class="txtright"><%=StringUtil.makeMoneyTypeIsNull(major_amt)%></td>
																	<td class="txtright"><%=StringUtil.makeMoneyTypeIsNull(major_amt2)%></td>
																	<td></td>
																	<td class="bdrn txtright"><%=StringUtil.makeMoneyTypeIsNull(sum_major)%></td>
																</tr>
																<tr>
																	<th>(20) 비과세소득계</th>
																	<td></td>
																	<td class="txtright"><%=StringUtil.makeMoneyTypeIsNull(free_tax_sum)%></td>
																	<td class="txtright"><%=StringUtil.makeMoneyTypeIsNull(total_free)%></td>
																	<td class="txtright"><%=StringUtil.makeMoneyTypeIsNull(total_free2)%></td>
																	<td></td>
																	<td class="bdrn txtright"><%=StringUtil.makeMoneyTypeIsNull(sum_free_tax)%></td>
																</tr>
																<tr>
																	<th>(20)-1 감면소득계</th>
																	<td></td>
																	<td></td>
																	<td class="txtright"><%=StringUtil.makeMoneyTypeIsNull(prev_reduction_amt)%></td>
																	<td class="txtright"><%=StringUtil.makeMoneyTypeIsNull(prev_reduction_amt2)%></td>
																	<td></td>
																	<td class="bdrn txtright"><%=StringUtil.makeMoneyTypeIsNull(sum_prev_reduction)%></td>
																</tr>
																<tr>
																	<td colspan="8" class="inner nobd">
																		<table class="tbl_receipt">
																			<colgroup>
																				<col style="width:28px"/>
																				<col style="width:80px;" /> <!-- 2015-02-25 수정 -->
																				<col style="width:112px;" /> <!-- 2015-02-25 수정 -->
																				<col style="width:96px;" /> <!-- 2015-02-25 수정 -->
																				<col /> <!-- 2015-02-25 수정 -->
																				<col style="width:101px"/>
																				<col style="width:101px"/>
																				<col style="width:101px"/>
																			</colgroup>
																			<tbody>
																				<tr class="tit_row">
																					<th rowspan="8"><span>Ⅲ<br />세<br />액<br />명<br />세</span></th>
																					<th colspan="4" class="txtcnt">구 &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;분</th> <!-- 2015-02-25 수정 -->
																					<th class="txtcnt">(80) 소득세</th> <!-- 2015-02-25 수정 -->
																					<th class="txtcnt">(81) 지방소득세</th> <!-- 2015-02-25 수정 -->
																					<th class="bdrn txtcnt">(82) 농어촌특별세</th> <!-- 2015-02-25 수정 -->
																				</tr>
																				<tr>
																					<th colspan="4">(74) 결정세액</th>
																					<td class="txtright"><%=StringUtil.makeMoneyTypeIsNull(determine_income_tax)%></td>
																					<td class="txtright"><%=StringUtil.makeMoneyTypeIsNull(determine_jumin_tax)%></td>
																					<td class="bdrn txtright"><%=StringUtil.makeMoneyTypeIsNull(determine_nong_tax)%></td>
																				</tr>
																				<tr>
																					<th rowspan="4" class="txtcnt">기납부<br>세 &nbsp; 액</th> <!-- 2015-02-25 수정 -->
																					<th rowspan="3">(75) 종(전)근무지<br />(결정세액란의<br />세액 기재)</th>
																					<th rowspan="3">사업자등록번호</th>
																					<td></td>
																					<td class="txtright"><%=StringUtil.makeMoneyTypeIsNull(prev_income_amt)%></td>
																					<td class="txtright"><%=StringUtil.makeMoneyTypeIsNull(prev_jumin_amt)%></td>
																					<td class="bdrn txtright"><%=StringUtil.makeMoneyTypeIsNull(prev_nong_amt)%></td>
																				</tr>
																				<tr>
																					<td></td>
																					<td class="txtright"><%=StringUtil.makeMoneyTypeIsNull(prev_income_amt2)%></td>
																					<td class="txtright"><%=StringUtil.makeMoneyTypeIsNull(prev_jumin_amt2)%></td>
																					<td class="bdrn txtright"><%=StringUtil.makeMoneyTypeIsNull(prev_nong_amt2)%></td>
																				</tr>
																				<tr>
																					<td></td>
																					<td class="txtright"><%=StringUtil.makeMoneyTypeIsNull(jing_jong_income_tax)%></td>
																					<td class="txtright"><%=StringUtil.makeMoneyTypeIsNull(jing_jong_jumin_tax)%></td>
																					<td class="bdrn txtright"><%=StringUtil.makeMoneyTypeIsNull(jing_jong_nong_tax)%></td>
																				</tr>
																				<tr>
																					<th colspan="3">(76) 주(현)근무지</th>
																					<td class="txtright"><%=StringUtil.makeMoneyTypeIsNull(jing_income_tax)%></td>
																					<td class="txtright"><%=StringUtil.makeMoneyTypeIsNull(jing_jumin_tax)%></td>
																					<td class="bdrn txtright"><%=StringUtil.makeMoneyTypeIsNull(jing_nong_tax)%></td>
																				</tr>
																				<tr>
																					<th colspan="4">(77) 납부특례세액</th>
																					<td></td>
																					<td></td>
																					<td class="bdrn txtright"></td>
																				</tr>
																				<tr>
																					<th colspan="4">(78) 차감징수세액 (74) - (75) - (76) - (77)</th>
																					<td class="txtright"><%=StringUtil.makeMoneyTypeIsNull(deduction_income_tax)%></td>
																					<td class="txtright"><%=StringUtil.makeMoneyTypeIsNull(deduction_jumin_tax)%></td>
																					<td class="bdrn txtright"><%=StringUtil.makeMoneyTypeIsNull(deduction_nong_tax)%></td>
																				</tr>
																			</tbody>
																		</table>
																	</td>
																</tr>
															</tbody>
														</table>
													</div>
												</td>
											</tr>
										</tbody>
									</table>
								</div>	
								<div class="summary">
									<ul>
										<li>국민연금 : <span><%=StringUtil.makeMoneyTypeIsNull(kuk_yeon_amt)%></span></li>
										<li>의료보험 : <span><%=StringUtil.makeMoneyTypeIsNull(insurance_health_amt)%></span></li>
										<li>고용보험 : <span><%=StringUtil.makeMoneyTypeIsNull(insurance_employ_amt)%></span></li>
									</ul>
									<div>
										<p>위의 원천징수액(근로소득)을 영수(지급)합니다. <span><em><%=bas_end_year%></em>년 <em><%=bas_end_month%></em>월 <em><%=bas_end_day%></em>일</span></p>
										<div>
											<span class="sir">세 무 서 장 <em></em>귀하</span>
											<span class="obligor ">징수(보고)의무자 <em></em></span>
											<span class="sign">하나제약주식회사 <em></em>(서명 또는 인)</span>
										</div>
									</div>
								</div>
							</div>
						</div>
						<div id="tab2" class="wrap_receipt_midbox">
							<div class="page_info">(2 쪽)</div>			
							<div class="wrap_receipt">
								<div class="main page2">
									<table class="tbl_receipt lheight">
										<colgroup>
											<col style="width:28px"/>
											<col style="width:28px"/>
											<col style="width:28px"/>
											<col style="width:58px"/>
											<col style="width:58px"/> 
											<col style="width:58px"/> 
											<col style="width:78px"/> 
											<col style=""/>
											<col style="width:28px"/>
											<col style="width:28px"/>
											<col style="width:28px"/>
											<col style="width:58px"/>
											<col style="width:58px"/>
											<col style="width:78px"/>
											<col style=""/>
										</colgroup>
										<tbody>
											<tr class="tit_row bdtn">
												<th rowspan="41" class="bdb2"><span>Ⅳ<br />정<br />산<br />명<br />세</span></th>
												<th colspan="6">(21) 총급여((16), 다만 외국인단일세율 적용시에는 연간근로소득)</th>
												<td class="bdr2 txtright"><%=StringUtil.makeMoneyTypeIsNull(yt_salary)%></td>
												<th colspan="6">(49) 특별공제 종합한도 초과액</th>
												<td class="bdrn txtright"><%=StringUtil.makeMoneyTypeIsNull(etc_exceed_limit_amt)%></td>
											</tr>
											<tr>
												<th colspan="6">(22) 근로소득공제</th>
												<td class="bdr2 txtright"><%=StringUtil.makeMoneyTypeIsNull(yt_work_deduct_amt)%></td>
												<th colspan="6">(50) 종합소득 과세표준</th>
												<td class="bdrn txtright"><%=StringUtil.makeMoneyTypeIsNull(yt_income_tax_standard_amt)%></td>
											</tr>
											<tr>
												<th colspan="6">(23) 근로소득금액</th>
												<td class="bdr2 txtright"><%=StringUtil.makeMoneyTypeIsNull(yt_work_income_amt)%></td>
												<th colspan="6">(51) 산출세액</th>
												<td class="bdrn txtright"><%=StringUtil.makeMoneyTypeIsNull(yt_calculate_amt)%></td>
											</tr>
											<tr>
												<th rowspan="24" class="tac"><span class="tit_row_s">종<br />합<br />소<br />득<br />공<br />제</span></th>
												<th rowspan="3" class="tac">기본공제</th>
												<th colspan="4">(24) 본인</th>
												<td class="bdr2 txtright"><%=StringUtil.makeMoneyTypeIsNull(family_person_amt)%></td>
												<th rowspan="5" class="tac"><span class="tit_row_s">세<br />액<br />감<br />면</span></th>
												<th colspan="5">(52) 「소득세법」</th>
												<td class="bdrn txtright"><%=StringUtil.makeMoneyTypeIsNull(etc_income_tax_reduction_amt)%></td>
											</tr>
											<tr>
												<th colspan="4">(25) 배우자</th>
												<td class="bdr2 txtright"><%=StringUtil.makeMoneyTypeIsNull(family_mate_amt)%></td>
												<th colspan="5">(53) 「조세특례제한법」 (53)-1 제외</th>
												<td class="bdrn txtright"></td>
											</tr>
											<tr>
												<th colspan="4" class="person">(26) 부양가족<span>(<em><%=family_dependent_cnt%></em>명)</span></th>
												<td class="bdr2 txtright"><%=StringUtil.makeMoneyTypeIsNull(family_dependent_amt)%></td>
												<th colspan="5">(54) 「조세특례제한법」 제30조</th>
												<td class="bdrn txtright"><%=StringUtil.makeMoneyTypeIsNull(etc_smiymjtc_amt)%></td>
											</tr>		
											<tr>
												<th rowspan="4" class="tac">추가공제</th>
												<th colspan="4" class="person">(27) 경로우대<span>(<em><%=family_old2_cnt%></em>명)</span></th>
												<td class="bdr2 txtright"><%=StringUtil.makeMoneyTypeIsNull(family_old_amt)%></td>
												<th colspan="5">(55) 조세조약</th>
												<td class="bdrn txtright"></td>
											</tr>								
											<tr>
												<th colspan="4" class="person">(28) 장애인<span>(<em><%=family_disabled_person_cnt%></em>명)</span></th>
												<td class="bdr2 txtright"><%=StringUtil.makeMoneyTypeIsNull(family_disabled_person_amt)%></td>
												<th colspan="5">(56) 세액감면계</th>
												<td class="bdrn txtright"><%=StringUtil.makeMoneyTypeIsNull(sum_etc)%></td>
											</tr>	
											<tr>
												<th colspan="4">(29) 부녀자</th>
												<td class="bdr2 txtright"><%=StringUtil.makeMoneyTypeIsNull(family_women_amt)%></td>
												<th rowspan="27" class="tac"><span class="tit_row_s">세<br />액<br />공<br />제</span></th>
												<th colspan="5">(57) 근로소득</th>
												<td class="bdrn txtright"><%=StringUtil.makeMoneyTypeIsNull(yt_earned_income_amt)%></td>
											</tr>	
											<tr>
												<th colspan="4">(30) 한부모가족</th>
												<td class="bdr2 txtright"><%=StringUtil.makeMoneyTypeIsNull(family_single_parent_amt)%></td>
												<th colspan="5">(58) 자녀</th>
												<td class="bdrn txtright"><%=StringUtil.makeMoneyTypeIsNull(etc_children_amt)%></td>
											</tr>	
											<tr>
												<th rowspan="5" class="tac">연금보험료공제</th>
												<th colspan="4">(31) 국민연금보험료</th>
												<td class="bdr2 txtright"><%=StringUtil.makeMoneyTypeIsNull(kuk_yeon_amt)%></td>
												<th rowspan="6" class="tac">연금계좌</th>
												<th rowspan="2" colspan="3">(59) 과학기술인공제</th>
												<th>공제대상금액</th>
												<td class="bdrn txtright"></td>
											</tr>	
											<tr>
												<th rowspan="4">(32) 공적연금보험료공제</th>
												<th colspan="3">(가) 공무원연금</th>
												<td class="bdr2 txtright"></td>
												<th>세액공제액</th>
												<td class="bdrn txtright"></td>
											</tr>	
											<tr>
												<th colspan="3">(나) 군인연금</th>
												<td class="bdr2 txtright"></td>
												<th rowspan="2" colspan="3">(60) 「근로자퇴직급여보장법」에 따른 퇴직연금</th>
												<th>공제대상금액</th>
												<td class="bdrn txtright"><%=StringUtil.makeMoneyTypeIsNull(annunity_retire_amt_o)%></td>
											</tr>	
											<tr>
												<th colspan="3">(다) 사립학교교직원연금</th>
												<td class="bdr2 txtright"></td>
												<th>세액공제액</th>
												<td class="bdrn txtright"><%=StringUtil.makeMoneyTypeIsNull(annuity_retire_amt)%></td>
											</tr>	
											<tr>
												<th colspan="3">(라) 별정우체국연금</th>
												<td class="bdr2 txtright"></td>
												<th rowspan="2" colspan="3">(61) 연금저축</th>
												<th>공제대상금액</th>
												<td class="bdrn txtright"><%=StringUtil.makeMoneyTypeIsNull(annuity_saving_amt_o)%></td>
											</tr>	
											<tr>
												<th rowspan="12" class="tac">특별공제</th>
												<th rowspan="2" class="pdzero">(33) 보험료</th>
												<th colspan="3" class="pdzero">(가) 건강보험료(노인장기요양보험료포함)</th>
												<td class="bdr2 txtright"><%=StringUtil.makeMoneyTypeIsNull(insurance_health_amt)%></td>
												<th>세액공제액</th>
												<td class="bdrn txtright"><%=StringUtil.makeMoneyTypeIsNull(annuity_savng_amt)%></td>
											</tr>	
											<tr>
												<th colspan="3">(나) 고용보험료</th>
												<td class="bdr2 txtright"><%=StringUtil.makeMoneyTypeIsNull(insurance_employ_amt)%></td>
												<th rowspan="14" class="tac">특별세액공제</th>
												<th rowspan="2" colspan="3">(62) 보장성보험</th>
												<th>공제대상금액</th>
												<td class="bdrn txtright"><%=StringUtil.makeMoneyTypeIsNull(insurance_amt_o)%></td>
											</tr>	
											<tr>
												<th rowspan="8">(34) 주택자금</th>
												<th rowspan="2" colspan="2">(가) 주택임차차입금원리금상환액</th>
												<th>대출기관</th>
												<td class="bdr2 txtright"><%=StringUtil.makeMoneyTypeIsNull(house_lease_loan_amt)%></td>
												<th>세액공제액</th>
												<td class="bdrn txtright"><%=StringUtil.makeMoneyTypeIsNull(total_insurance_amt)%></td>
											</tr>	
											<tr>
												<th>거주자</th>
												<td class="bdr2 txtright"></td>
												<th rowspan="2" colspan="3">(63) 의료비</th>
												<th>공제대상금액</th>
												<td class="bdrn txtright"><%=StringUtil.makeMoneyTypeIsNull(medical_amt_o)%></td>
											</tr>	
											<tr>
												<th colspan="3">(나)</th>
												<td class="bdr2 txtright"></td>
												<th>세액공제액</th>
												<td class="bdrn txtright"><%=StringUtil.makeMoneyTypeIsNull(total_medical_amt)%></td>
											</tr>	
											<tr>
												<th rowspan="5">(다) 장기주택저당차입금이자상환액</th>
												<th rowspan="3">2011 이전 차입분</th>
												<th>15년 미만</th>
												<td class="bdr2 txtright"><%=StringUtil.makeMoneyTypeIsNull(house_security_loan1_amt)%></td>
												<th rowspan="2" colspan="3">(64) 교육비</th>
												<th>공제대상금액</th>
												<td class="bdrn txtright"><%=StringUtil.makeMoneyTypeIsNull(edcate_amt_o)%></td>
											</tr>	
											<tr>
												<th>15년~29년</th>
												<td class="bdr2 txtright"><%=StringUtil.makeMoneyTypeIsNull(house_security_loan2_amt)%></td>
												<th>세액공제액</th>
												<td class="bdrn txtright"><%=StringUtil.makeMoneyTypeIsNull(total_edcate_amt)%></td>
											</tr>	
											<tr>
												<th>30년 이상</th>
												<td class="bdr2 txtright"><%=StringUtil.makeMoneyTypeIsNull(house_security_loan3_amt)%></td>
												<th rowspan="6">(65) 기부금</th>
												<th rowspan="4">(가) 정치자금기부금</th>
												<th rowspan="2">10만원이하</th>
												<th>공제대상금액</th>
												<td class="bdrn txtright"><%=StringUtil.makeMoneyTypeIsNull(contribue_politic_amt_o)%></td>
											</tr>	
											<tr>
												<th rowspan="2">2012 이후 차입분(15년이상)</th>
												<th>고정금리 · 비거치 상환대출</th>
												<td class="bdr2 txtright"><%=StringUtil.makeMoneyTypeIsNull(house_security_loan4_amt)%></td>
												<th>세액공제액</th>
												<td class="bdrn txtright"><%=StringUtil.makeMoneyTypeIsNull(contribue_politic_amt)%></td>
											</tr>	
											<tr>
												<th>그 밖의 대출</th>
												<td class="bdr2 txtright"><%=StringUtil.makeMoneyTypeIsNull(house_security_loan5_amt)%></td>
												<th rowspan="2">10만원초과</th>
												<th>공제대상금액</th>
												<td class="bdrn txtright"><%=StringUtil.makeMoneyTypeIsNull(contribue_politic_over_amt_o)%></td>
											</tr>	
											<tr>
												<th colspan="4">(35) 기부금(이월분)</th>
												<td class="bdr2 txtright"></td>
												<th>세액공제액</th>
												<td class="bdrn txtright"><%=StringUtil.makeMoneyTypeIsNull(contribue_politic_over_amt)%></td>
											</tr>	
											<tr>
												<th colspan="4">(36) 계</th>
												<td class="bdr2 txtright"><%=StringUtil.makeMoneyTypeIsNull(yt_special_deduct_amt)%></td>
												<th colspan="3">(나) 법정기부금</th>
												<td class="bdrn txtright"><%=StringUtil.makeMoneyTypeIsNull(contribue_law_amt)%></td>
											</tr>	
											<tr>
												<th colspan="6">(37) 차감소득금액</th>
												<td class="bdr2 txtright"><%=StringUtil.makeMoneyTypeIsNull(yt_deduction_amt)%></td>
												<th colspan="3">(다) 지정기부금</th>
												<td class="bdrn txtright"><%=StringUtil.makeMoneyTypeIsNull(compute_4)%></td>
											</tr>	
											<tr>
												<th rowspan="13" class="tac bdb2"><span class="tit_row_s">그<br />밖<br />의<br />소<br />득<br />공<br />제</span></th>
												<th colspan="5">(38) 개인연금저축</th>
												<td class="bdr2 txtright"><%=StringUtil.makeMoneyTypeIsNull(personal_annuity_amt)%></td>
												<th colspan="4">(66) 계</th>
												<td class="bdrn txtright"><%=StringUtil.makeMoneyTypeIsNull(yt_special_tax_deduct_amt)%></td>
											</tr>	
											<tr>
												<th colspan="5">(39) 소기업 · 소상공인 공제부금</th>
												<td class="bdr2 txtright"></td>
												<th colspan="4">(67) 표준공제</th>
												<td class="bdrn txtright"><%=StringUtil.makeMoneyTypeIsNull(yt_standard_deduct_amt)%></td>
											</tr>	
											<tr>
												<th rowspan="3" colspan="2">(40) 주택마련저축소득공제</th>
												<th colspan="3">(가) 청약저축</th>
												<td class="bdr2 txtright"><%=StringUtil.makeMoneyTypeIsNull(etc_subscription_deposit_amt)%></td>	
												<th colspan="5">(68) 납세조합공제</th>
												<td class="bdrn txtright"><%=StringUtil.makeMoneyTypeIsNull(etc_sework_tx_amt)%></td>
											</tr>	
											<tr>
												<th colspan="3">(나) 주택청약종합저축</th>
												<td class="bdr2 txtright"><%=StringUtil.makeMoneyTypeIsNull(etc_house_subscr_deposit_amt)%></td>	
												<th colspan="5">(69) 주택차입금</th>
												<td class="bdrn txtright"><%=StringUtil.makeMoneyTypeIsNull(etc_house_laon_interest_amt)%></td>
											</tr>	
											<tr>
												<th colspan="3">(다) 근로자주택마련저축</th>
												<td class="bdr2 txtright"><%=StringUtil.makeMoneyTypeIsNull(etc_home_mortgage_amt)%></td>	
												<th colspan="5">(70) 외국납부</th>
												<td class="bdrn txtright"><%=StringUtil.makeMoneyTypeIsNull(etc_foreigner_income)%></td>
											</tr>	
											<tr>
												<th colspan="5">(41) 투자조합출자등 소득공제</th>
												<td class="bdr2 txtright"></td>	
												<th colspan="5">(71) 월세액</th>
												<td class="bdrn txtright"><%=StringUtil.makeMoneyTypeIsNull(house_month_lent_amt)%></td>
											</tr>	
											<tr>
												<th colspan="5">(42) 신용카드 등 사용액</th>
												<td class="bdr2 txtright"><%=StringUtil.makeMoneyTypeIsNull(total_use_amt)%></td>	
												<th colspan="5">(72) 세액공제계</th>
												<td class="bdrn txtright"><%=StringUtil.makeMoneyTypeIsNull(yt_tax_deduct_amt)%></td>
											</tr>	
											<tr>
												<th colspan="5">(43) 우리사주조합 출연금</th>
												<td class="bdr2 txtright"><%=StringUtil.makeMoneyTypeIsNull(etc_employ_stock_amt)%></td>	
												<th colspan="6"><span class="tdbold">결정세액 (51)-(56)-(72)</span></th>
												<td class="bdrn txtright"><%=StringUtil.makeMoneyTypeIsNull(yt_determine_tax)%></td>
											</tr>	
											<tr>
												<th colspan="5">(44) 우리사주조합 기부금</th>
												<td class="bdr2 txtright"></td>
												<td colspan="7" class="bdrn"></td>
											</tr>	
											<tr>
												<th colspan="5">(45) 고용유지 중소기업 근로자</th>
												<td class="bdr2 txtright"></td>
												<td colspan="7" class="bdrn"></td>
											</tr>	
											<tr>
												<th colspan="5">(46) 묵돈 안드는 전세 이자상환액</th>
												<td class="bdr2 txtright"><%=StringUtil.makeMoneyTypeIsNull(etc_chunk_money_amt)%></td>
												<td colspan="7" class="bdrn"></td>
											</tr>	
											<tr>
												<th colspan="5">(47) 장기집합투자증권저축</th>
												<td class="bdr2 txtright"><%=StringUtil.makeMoneyTypeIsNull(etc_long_invest_stock_amt)%></td>
												<td colspan="7" class="bdrn"></td>
											</tr>	
											<tr class="bdb2">
												<th colspan="5">(48) 그 밖의 소득공제 계</th>
												<td class="bdr2 txtright"><%=StringUtil.makeMoneyTypeIsNull(total_etc_amt)%></td>
												<td colspan="7" class="bdrn"></td>
											</tr>	
										</tbody>
									</table>
								</div>	
							</div>
						</div>
						<div id="tab3" class="wrap_receipt_midbox">
							<div class="page_info">(3 쪽)</div>			
							<div class="wrap_receipt">
								<div class="main page3">
									<table class="tbl_receipt">
										<colgroup>
											<col style="width:30px"/>
											<col style="width:85px"/>
											<col style="width:22px"/>
											<col style="width:22px"/>
											<col style="width:35px"/>
											<col style="width:42px"/>
											<col style=""/>
											<col style=""/>
											<col style=""/>
											<col style=""/>
											<col style=""/>
											<col style=""/>
											<col style=""/>
											<col style=""/>
											<col style=""/>
											<col style=""/>
										</colgroup>
										<tbody>
											<tr>
												<th colspan="16" class="bdrn nobd">(79) 소득공제 명세(인적공제항목은 해당란에 "O"표시 (장애인 해당시 해당코드를 기재)를 하며, 각종 소득공제에 항목은 공제를 위하여 실제 지출한 금액을 기입합니다)</th>
											</tr>
											<tr>
												<th colspan="5">인적공제 항목</th>
												<th colspan="11" class="bdrn">각종 소득공제 항목</th>
											</tr>
											<tr>
												<th>관계<br />코드</th>
												<th>성명</th>
												<th colspan="2">기본<br />공제</th>
												<th>경로<br />우대</th>
												<th rowspan="2">자료<br />구분</th>
												<th colspan="2">보험료</th>
												<th rowspan="2">의료비</th>
												<th rowspan="2">교육비</th>
												<th colspan="5">신용카드 등 사용액공제</th>
												<th rowspan="2" class="bdrn">기부금</th>
											</tr>
											<tr>
												<th>내 · 외국인</th>
												<th>주민등록번호</th>
												<th>부녀자</th>
												<th>학부모</th>
												<th>장애인</th>
												<th>건강 · 고용 등</th>
												<th>보장성</th>
												<th>신용카드 (전통시장,대중교통비제외)</th>
												<th>직불카드 (전통시장,대중교통비제외)</th>
												<th>현금영수증 (전통시장,대중교통비제외)</th>
												<th>전통시장 사용액</th>
												<th>대중교통 이용액</th>
											</tr>
											<tr>
												<th rowspan="2" colspan="2">인적공제항목에 해당하는 인원수를 기재<br />(자녀: <span><%=dajayeon_cnt%></span>명)</th>
												<td></td>
												<td></td>
												<td class="tac"></td>
												<th>국세청</th>
												<td></td>
												<td id="sum_insurance" class="txtright numspace"></td>
												<td class="txtright numspace"><%=StringUtil.makeMoneyTypeIsNull(sum_medical_1)%></td>
												<td class="txtright numspace"><%=StringUtil.makeMoneyTypeIsNull(sum_educate_1)%></td>
												<td class="txtright numspace"><%=StringUtil.makeMoneyTypeIsNull(sum_credit_1)%></td>
												<td class="txtright numspace"><%=StringUtil.makeMoneyTypeIsNull(sum_direct_1)%></td>
												<td class="txtright numspace"><%=StringUtil.makeMoneyTypeIsNull(sum_cash_1)%></td>
												<td class="txtright numspace"><%=StringUtil.makeMoneyTypeIsNull(sum_market_1)%></td>
												<td class="txtright numspace"><%=StringUtil.makeMoneyTypeIsNull(sum_pubric_transport_1)%></td>
												<td class="bdrn txtright numspace"><%=StringUtil.makeMoneyTypeIsNull(sum_contribute_1)%></td>
											</tr>
											<tr>
												<td></td>
												<td></td>
												<td class="tac"></td>
												<th>기타</th>
												<td></td>
												<td id="sum_insurance2" class="txtright numspace"></td>
												<td class="txtright numspace"><%=StringUtil.makeMoneyTypeIsNull(sum_medical_2)%></td>
												<td class="txtright numspace"><%=StringUtil.makeMoneyTypeIsNull(sum_educate_2)%></td>
												<td class="txtright numspace"><%=StringUtil.makeMoneyTypeIsNull(sum_credit_2)%></td>
												<td class="txtright numspace"><%=StringUtil.makeMoneyTypeIsNull(sum_direct_2)%></td>
												<td class="txtright numspace"><%=StringUtil.makeMoneyTypeIsNull(sum_cash_2)%></td>
												<td class="txtright numspace"><%=StringUtil.makeMoneyTypeIsNull(sum_market_2)%></td>
												<td class="txtright numspace"><%=StringUtil.makeMoneyTypeIsNull(sum_pubric_transport_2)%></td>
												<td class="bdrn txtright numspace"><%=StringUtil.makeMoneyTypeIsNull(sum_contribute_2)%></td>
											</tr>
											<%
											if(yearendtaxDetail3 != null){
												for(int i=0; yearendtaxDetail3.size()>i;i++){
													YearendtaxVO yearendtaxVO = new YearendtaxVO();
													yearendtaxVO = yearendtaxDetail3.get(i);
													int sum_insurance = yearendtaxVO.getInsurance_person_1() + yearendtaxVO.getInsurance_disabled_person_1() + yearendtaxVO.getInsurance_health_amt() + yearendtaxVO.getInsurance_employ_amt();
													int sum_insurance2 = yearendtaxVO.getInsurance_person_2() + yearendtaxVO.getInsurance_disabled_person_2();
													
													all_sum_insurance += sum_insurance;
													all_sum_insurance2 += sum_insurance2;
											%>
											<tr>
												<th><%=yearendtaxVO.getRel_cd()%></th>
												<td class="tac"><%=yearendtaxVO.getRel_nm()%></td>
												<td colspan="2" class="tac"><%if("Y".equals(yearendtaxVO.getChoose_yn())){%>O<%}else{%><%}%></td>
												<td class="tac"><%if("Y".equals(yearendtaxVO.getRespect_aged_yn())){%>O<%}else{%><%}%></td>
												<th>국세청</th>
												<td></td>
												<td class="txtright numspace"><%=StringUtil.makeMoneyTypeIsNull(sum_insurance)%></td>
												<td class="txtright numspace"><%=StringUtil.makeMoneyTypeIsNull(yearendtaxVO.getMedical_1())%></td>
												<td class="txtright numspace"><%=StringUtil.makeMoneyTypeIsNull(yearendtaxVO.getEducate_1())%></td>
												<td class="txtright numspace"><%=StringUtil.makeMoneyTypeIsNull(yearendtaxVO.getCredit_1())%></td>
												<td class="txtright numspace"><%=StringUtil.makeMoneyTypeIsNull(yearendtaxVO.getDirect_1())%></td>
												<td class="txtright numspace"><%=StringUtil.makeMoneyTypeIsNull(yearendtaxVO.getCash_1())%></td>
												<td class="txtright numspace"><%=StringUtil.makeMoneyTypeIsNull(yearendtaxVO.getMarket_1())%></td>
												<td class="txtright numspace"><%=StringUtil.makeMoneyTypeIsNull(yearendtaxVO.getPubric_transport_1())%></td>
												<td class="bdrn txtright numspace"><%=StringUtil.makeMoneyTypeIsNull(yearendtaxVO.getContribute_1())%></td>
											</tr>
											<tr>
												<th><%=yearendtaxVO.getForeign_cd()%></th>
												<td class="tac numspace"><%=yearendtaxVO.getRel_jumin_no()%></td>
												<td><%if("Y".equals(yearendtaxVO.getWoman_yn())){%>O<%}else{%><%}%></td>
												<td class="tac"><%if("Y".equals(yearendtaxVO.getSingle_parents_yn())){%>O<%}else{%><%}%></td>
												<td class="tac"><%if("Y".equals(yearendtaxVO.getDisabled_person_yn())){%>O<%}else{%><%}%></td>
												<th>기타</th>
												<td></td>
												<td class="txtright numspace"><%=StringUtil.makeMoneyTypeIsNull(sum_insurance2)%></td>
												<td class="txtright numspace"><%=StringUtil.makeMoneyTypeIsNull(yearendtaxVO.getMedical_2())%></td>
												<td class="txtright numspace"><%=StringUtil.makeMoneyTypeIsNull(yearendtaxVO.getEducate_2())%></td>
												<td class="txtright numspace"><%=StringUtil.makeMoneyTypeIsNull(yearendtaxVO.getCredit_2())%></td>
												<td class="txtright numspace"><%=StringUtil.makeMoneyTypeIsNull(yearendtaxVO.getDirect_2())%></td>
												<td class="txtright numspace"><%=StringUtil.makeMoneyTypeIsNull(yearendtaxVO.getCash_2())%></td>
												<td class="txtright numspace"><%=StringUtil.makeMoneyTypeIsNull(yearendtaxVO.getMarket_2())%></td>
												<td class="txtright numspace"><%=StringUtil.makeMoneyTypeIsNull(yearendtaxVO.getPubric_transport_2())%></td>
												<td class="bdrn txtright numspace"><%=StringUtil.makeMoneyTypeIsNull(yearendtaxVO.getContribute_2())%></td>
											</tr>
											<%
												}
											}
											%>
											<input type="hidden" id="all_sum_insurance" name="all_sum_insurance" value="<%=StringUtil.makeMoneyTypeIsNull(all_sum_insurance) %>"/>
											<input type="hidden" id="all_sum_insurance2" name="all_sum_insurance2" value="<%=StringUtil.makeMoneyTypeIsNull(all_sum_insurance2) %>" />
										</tbody>
									</table>
								</div>	
							</div>
						</div>
					</div>
				</div>
			</div>
			<!-- ######## end ######### -->
		</div>
	</div>
	
	<%@include file="/include/footer.jsp"%>
</body>
</html>