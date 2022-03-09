/**
 * Hana Project
 * Copyright 2014 iRush Co.,
 *
 */
package com.hanaph.gw.yt.yearendtax.vo;

/**
 * <pre>
 * Class Name : YearendtaxVO.java
 * 설명 : 연말정산
 * 
 * Modification Information
 *    수정일      수정자              수정 내용
 *  ------------ -------------- --------------------------------
 *  2014. 11. 24.      CHOIILJI
 * </pre>
 * 
 * @version :
 * @author : CHOIILJI(choiilji@irush.co.kr)
 * @since : 2014. 11. 24.
 */
public class YearendtaxVO {
	private String comp_name; //법인명
	private String president; //대표자성명
	private String vou_no; //사업자등록번호
	private String comp_no; //주민등록번호
	private String com_addr; //소재지
	private String emp_ko_nm; //성명
	private String resid_no; //주민등록번호
	private String bas_addr; //주소
	private String encmpy_dt; // 입사일자
	private String bas_start_dt; //근무기간 
	private String bas_end_dt; //근무기간
	private String corporate_nm; //근무처명
	private String corporate_nm2; //근무처명
	private String corporate_no; //사업자등록번호
	private String corporate_no2; //사업자등록번호
	private String start_work_dt; //근무기간
	private String end_work_dt; //근무기간
	private String start_work_dt2; //근무기간
	private String end_work_dt2;  //근무기간
	private String start_reduce_dt2; //감면기간
	private String end_reduce_dt2; //감면기간
	private String start_reduce_dt; //감면기간
	private String end_reduce_dt; //감면기간
	private int salary_amt;  //급여
	private int bonus_amt; //상여
	private int constructive_bonus_amt; //인정상여
	private int stock_option_amt; //주식매수선택권 행사이이익
	private int employ_stock_amt; //인정상여
	private int derector_retirement_amt; //임원
	private int total_salary; //계
	private int foreign_work_amt; //국외근로
	private int oevrtime_amt; //야간근로수동
	private int meternity_amt; //출산보육수당
	private int research_amt; //연구보조비
	private int school_expenses_amt; //비과세학자금
	private int collect_amt; //취재수당
	private int remote_rural_area_amt; //벽지수당
	private int natural_disaster_amt; //천재지변등재해로받는급여
	private int legislation_amt; //법령조례의한미보수위원수당
	private int operation_amt; //작전임무외국주둔군인급여
	private int stock_purchase_amt; //주식매수선택권
	private int foreigner_amt; //외국인기술자소득세면제
	private int employ_stock_amt1; //우리사주조합인출금50
	private int employ_stock_amt2; //우리사주조합인출금75
	private int submarine_mineral_amt; //해저광물자원개발과세특례
	private int guard_embark_amt; //경호수당/승선수당 등
	private int organization_amt; //외국정부국제기관근무자
	private int scholarship_amt; //교육기본법제28조1항장학금
	private int childcare_amt; //보육교사인건비
	private int kindergarten_teacher_amt; //사립유치원수석교사의인건비
	private int smiymjtc_amt; //중소기업청년취업소득세감면
	private int teache_clause_amt; //조세조약상교직자조항의소득세감면
	private int move_amt; //정부지방이전기관종사자이주수당
	private int major_amt; //수련보조수당
	private int total_free; //비과세소득계
	private int prev_reduction_amt; //감면소득계
	private int prev_health_amt; // 건강보험
	private int prev_employ_amt; // 고용보험
	private int prev_kuk_yeon_amt; // 국민연금
	private int prev_annuity_amt; // 개인연금
	private int prev_income_amt; // 소득세 69번
	private int prev_jumin_amt; // 주민세 70번
	private int prev_nong_amt; // 농특세71번
	private int salary_amt2; //급여2
	private int bonus_amt2; //상여2
	private int constructive_bonus_amt2; //인정상여2
	private int stock_option_amt2; //주식매수선택권 행사이익2
	private int employ_stock_amt_2; //우리사주조합인출금2
	private int derector_retirement_amt2; //임원 퇴직소득금액 한도초과액2
	private int total_salary2; //계2
	private int foreign_work_amt2; //국외근로2
	private int oevrtime_amt2; //야간근로수동2
	private int meternity_amt2; //출산보육수당2
	private int research_amt2; //연구보조비2
	private int school_expenses_amt2; //비과세학자금2
	private int collect_amt2; //취재수당2 
	private int remote_rural_area_amt2; //벽지수당2
	private int natural_disaster_amt2; //천재지변등재해로받는급여2
	private int legislation_amt2; //법령조례의한미보수위원수당2
	private int operation_amt2; //작전임무외국주둔군인급여2
	private int stock_purchase_amt2; //주식매수선택권2
	private int foreigner_amt2; //외국인기술자소득세면제2
	private int employ_stock_amt1_2; //우리사주조합인출금50 1_2
	private int employ_stock_amt2_2; //우리사주조합인출금75 2_2
	private int submarine_mineral_amt2; //해저광물자원개발과세특례2
	private int guard_embark_amt2; //경호수당/승선수당 등
	private int organization_amt2; //외국정부국제기관근무자2
	private int scholarship_amt2; //교육기본법제28조1항장학금2
	private int childcare_amt2; //보육교사인건비2
	private int kindergarten_teacher_amt2; //사립유치원수석교사의인건비2
	private int smiymjtc_amt2; //중소기업청년취업소득세감면2
	private int teache_clause_amt2; //조세조약상교직자조항의소득세감면2
	private int move_amt2; //정부지방이전기관종사자이주수당
	private int major_amt2; //수련보조수당2
	private int total_free2; //비과세소득계2
	private int prev_reduction_amt2; //감면소득계2
	private int prev_health_amt2; // 건강보험
	private int prev_employ_amt2; // 고용보험
	private int prev_kuk_yeon_amt2; // 국민연금
	private int prev_annuity_amt2; // 개인연금
	private int prev_income_amt2; // 소득세 69번
	private int prev_jumin_amt2; // 주민세 70번
	private int prev_nong_amt2; // 농특세71번
	private int pay_sum; //급여
	private int bonus_sum; //상여
	private int free_overtime_sum; //야간근로수동
	private int free_tax_sum; //비과세소득계
	private int determine_income_tax; // 결제세액_소득세
	private int determine_jumin_tax; // 결정세액_주민세
	private int determine_nong_tax; // 결정세액_농특세
	private int deduction_income_tax; // 차감징수세액_소득세
	private int deduction_jumin_tax; // 차감징수세액_주민세
	private int deduction_nong_tax; // 차감징수세액_농특세
	private String adjst_div; // 정산구분
	private String year; // 정산년도
	private int pay_sum_total; //합계
	private int kuk_yeon_amt; //국민연금
	private int insurance_health_amt; //의료보험
	private int insurance_employ_amt; //고용보험
	private int jing_income_tax; // 징수한 소득세
	private int jing_jumin_tax; // 징수한 주민세
	private int jing_nong_tax; // 징수한 농특세
	private int jing_jong_income_tax; // 징수한 종전근무지 소득세
	private int jing_jong_jumin_tax; // 징수한 종전근무지 주민세
	private int jing_jong_nong_tax; // 징수한 종전근무지 농특세
	private int foreign_cd; //내외국인코드 1:내국인,9:외국인
	private String seq; // 일련번호
	private String emp_no; //임직원
	private String first_emp_no; // 최초 작성자 사번
	private String first_regdate; // 최초 등록일자
	private String last_emp_no; // 최종 수정자 사번
	private String last_regdate; // 최종 수정일자
	private String last_ip; // 최종작성 ip
	private String ent_uno;
	private String upd_uno;
	private String salary_dt; // 지급일자
	private String smiymjtc_rate;
	private int reduction_amt; // 감면소득계 20-1번
	private int health_amt; // 건강보험
	private int employ_amt; // 고용보험
	private int annuity_amt; // 개인연금
	private int income_amt; // 소득세 69번
	private int jumin_amt; // 주민세 70번
	private int nong_amt; // 농특세71번
	private String rel_cd; // 관계 코드
	private String rel_jumin_no; // 관계인 주민번호
	private String rel_nm; // 성명
    private String choose_yn; // 기본공제대상자선정
    private String pensioner_yn; // 수급자공제
    private String foster_child_yn; // 위탁아동공제
    private String respect_aged_yn; // 경로우대공제
    private String disabled_person; // 장애공제
    private String woman_yn; // 부녀자공제
    private String sixyear_yn; // 6세이하공제
    private String birth_yn; // 출생입양공제
    private String insulance_yn; // 보험공제여부
    private String medical_yn; // 의료공제여부
    private String education_yn; // 교육공제여부
    private String card_yn; // 카드공제여부
    private String contribution_yn; // 기부공제여부
    private String single_parents_yn; // 한부모가족여부
    private String house_gb; // 주택계약 구분
    private String house_nm; // 임대인 성명
    private String house_jumin; // 임대인 주민번호
    private String house_start_dt; // 임대차 시작일
    private String house_end_dt; // 임대차 종료일
    private int house_amt1; // 월세액 - 차입금이자율 - 전세보증금
    private int house_amt2; // 공제금액 - 원리금 상환액계 - X
    private int house_amt3; // X - 원금 - X
    private int house_amt4; // X - 이자 - X
    private int house_amt5; // X - 공제금액 -  X
    private String house_addr; // 임대차 계약서상 주소지 - X - 임대차 계약서상 주소지
	private int credit_1; // 신용카드-국세청
	private int credit_2; // 신용카드-그밖의자료
	private int cash_1; // 현금영수증-국세청
	private int cash_2; // 현금영수증-그밖의자료
	private int direct_1; // 직불카드-국세청
	private int direct_2; // 직불카드-그밖의자료
	private int market_1; // 전통시장-국세청
	private int market_2; // 전통시장-그밖의자료
	private int pubric_transport_1; // 대중교통-국세청
	private int pubric_transport_2; // 대중교통-그밖의자료
	private int credit_1_ly; // 신용카드-국세청 작년사용분 2014년 연말정산 
	private int credit_2_ly; // 신용카드-그밖의자료 작년사용분 2014년 연말정산 
	private int cash_1_ly; // 현금영수증-국세청 작년사용분 2014년 연말정산 
	private int cash_2_ly; // 현금영수증-그밖의자료 작년사용분 2014년 연말정산   
	private int direct_1_ly; // 직불카드-국세청 작년사용분 2014년 연말정산 
	private int direct_2_ly; // 직불카드-그밖의자료 작년사용분 2014년 연말정산 
	private int market_1_ly; // 전통시장-국세청 작년사용분 2014년 연말정산 
	private int market_2_ly;  // 전통시장-그밖의자료 작년사용분 2014년 연말정산 
	private int pubric_transport_1_ly; // 대중교통-국세청 작년사용분 2014년 연말정산 
	private int pubric_transport_2_ly; // 대중교통-그밖의자료 작년사용분 2014년 연말정산 
	private int credit_1_sh; // 신용카드_국세청 2014하반기 이전 컬럼은 상반기로 사용 
	private int credit_2_sh; // 신용카드_그밖의자료 2014하반기 이전 컬럼은 상반기로 사용
	private int cash_1_sh; // 현금영수증_국세청 2014하반기 이전 컬럼은 상반기로 사용
	private int cash_2_sh; // 현금영수증_그밖의자료 2014하반기 이전 컬럼은 상반기로 사용
	private int direct_1_sh; // 직불카드_국세청 2014하반기 이전 컬럼은 상반기로 사용
	private int direct_2_sh; // 직불카드_그밖의자료 2014하반기 이전 컬럼은 상반기로 사용
	private int market_1_sh; // 전통시장_국세청 2014하반기 이전 컬럼은 상반기로 사용
	private int market_2_sh; // 전통시장_그밖의자료 2014하반기 이전 컬럼은 상반기로 사용
	private int pubric_transport_1_sh; // 대중교통_국세청 2014하반기 이전 컬럼은 상반기로 사용
	private int pubric_transport_2_sh; // 대중교통_그밖의자료 2014하반기 이전 컬럼은 상반기로 사용
	private String retir_dt; // 퇴직일자
	private int yt_salary; // 총급여(비과세제외 연말정산21번)
	private int yt_work_deduct_amt; // 근로소득공제(연말정산22번)
	private int yt_work_income_amt; // 근로소득금액(연말정산23번= 21-22)
	private int family_person_amt; // 기본사항_본인공제금액
	private int family_mate_amt; // 기본사항_배우자공제금액
	private int family_dependent_amt; // 기본사항_부양가족공제
	private int family_dependent_cnt; // 기본사항_부양가족 수
	private int family_old_amt; // 추가공제_경로우대공제
	private int family_old2_cnt; // 추가공제_경로우대공제 70세 이상
	private int family_disabled_person_amt; // 추가공제_장애인공제
	private int family_disabled_person_cnt; // 추가공제_장애인공제 수
	private int family_women_amt; // 추가공제_부녀자세대주
	private int family_single_parent_amt; // 추가공제 한부모공제
	private int total_family_amt; // 기본사항추가공제(인적공제계)
	private int house_lease_loan_amt; // 주택_주택임차차입금 원리금상환공제액
	private int house_security_loan1_amt; // 주택_장기주택저당 차입금 이자상환(600)공제액
	private int house_security_loan2_amt; // 주택_장기주택저당 차입금 이자상환(1000)공제액
	private int house_security_loan3_amt; // 주택_장기주택저당 차입금 이자상환(1500)공제액
	private int house_security_loan4_amt; // 주택_장기주택저당 차입금 이자상환(고정금리비거치식 상환)공제액
	private int house_security_loan5_amt; // 주택_장기주택저당 차입금 이자상환(기타대출)공제액
	private int total_house_amt; // 주택공제계
	private int yt_special_deduct_amt; // 특별공제계(연말정산40번)
	private int yt_deduction_amt; // 차감소득금액(연말정산42번=23-41)
	private int personal_annuity_amt; // 그밖의_개인연금저축공제액
	private int etc_subscription_deposit_amt; // 그밖의_청약저축
	private int etc_home_mortgage_amt; // 그밖의_근로자주택마련저축
	private int etc_house_subscr_deposit_amt; // 그밖의_주택청약종합저축
	private int etc_credt_amt; // 그밖의_신용카드
	private int etc_cash_receipt_amt; // 그밖의_현금영수증
	private int etc_direct_amt; // 그밖의_직불카드
	private int etc_market_amt; // 그밖의_전통시장
	private int etc_pubric_transport_amt; // 그밖의_대중교통
	private int total_use_amt; // 신용카드등공제계
	private int etc_employ_stock_amt; // 그밖의_우리사주조합출연금
	private int etc_chunk_money_amt; // 그밖의_목돈안드는전세이자상환액
	private int etc_long_invest_stock_amt; // 그밖의_장기집합투자증권저축 2014년 신설
	private int total_etc_amt; // 그밖의_그밖의소득공제계
	private int etc_exceed_limit_amt; // 그밖의_특별공제 종합한도 초과액
	private int yt_income_tax_standard_amt; // 종합소득과세표준(연말정산50번)
	private int yt_calculate_amt; // 산출세액(연말정산51번)
	private int etc_income_tax_reduction_amt; // 그밖의_소득세법감면세액대상액
	private int etc_smiymjtc_amt; // 중소기업 청년취업 소득세 감면여부 : FNDATA_0에는 만들지 않음 HR_HC_EMPPAY_0에서 참조해서 커서만
	private int yt_earned_income_amt; // 근로소득(연말정산56번)
	private int etc_children_amt; // 그밖의_자녀세액공제 2014년 신설
	private int annunity_retire_amt_o; // 연금보험_퇴직연금공제대상금액 2014년
	private int annuity_retire_amt; // 연금보험_근로자퇴직급여보장법에따른퇴직연금
	private int annuity_saving_amt_o; // 연금보험_개인연금공제대상금액 2014년
	private int annuity_savng_amt; // 연금보험_연금저축계좌 공제금액 납부금액과 다름
	private int total_annuity_amt; // 연금보험공제계_국민연금은 불포함
	private int insurance_secrity_amt; // 보험료_보장성보험료
	private int insurance_amt_o; // 보험료_공제대상금액 2014년 연말정산
	private int insurance_disabled_person_amt; // 보험료_장애인
	private int total_insurance_amt; // 보험료공제계
	private int total_medical_amt; // 의료비공제계
	private int medical_amt_o; // 의료비_공제대상금액 2014년 연말정산
	private int total_edcate_amt; // 교육비공제계
	private int edcate_amt_o; // 교육비_공제대상금액 2014년 연말정산
	private int contribue_politic_amt; // 기부금_정치자금 20
	private int contribue_politic_amt_o; // 기부금_정치자금 20 공제대상금액 2014년 연말정산 (10만원 이하분)
	private int contribue_politic_over_amt; // 기부금_정치자금(10만원 초과분) 2014년 연말정산
	private int contribue_politic_over_amt_o; // 기부금_정치자금 공제대상금액(10만원 초과분) 2014년 연말정산
	private int contribue_law_amt; // 기부금_법정기부금 10
	private int contribue_law_amt_o; // 기부금_법정기부금 공제대상금액 2014년 연말정산
	private int contribute_exception_amt; // 기부금_특례기부금 31
	private int contribute_trust_amt; // 기부금_공익신탁기부금 40
	private int contribue_employ_stock_amt; // 기부금_우리사주(30%) 42
	private int contribue_designate_amt; // 기부금_지정기부금(종교외)(10%) 40
	private int contribue_designate_amt_o; // 기부금_지정기부금 공제대상금액 2014년 연말정산
	private int contribue_religion_amt; // 기부금_지정기부금(종교단체) 41
	private int total_contribue_amt; // 기부금공제계
	private int yt_special_tax_deduct_amt; // 특별세액공제 2014년 연말정산 (연금저축과 특별공제가 세액공제로 이동하면서 합산 컬럼 추가)
	private int yt_standard_deduct_amt; // 표준공제(연말정산41번) 2014년 연말정산 제거
	private int etc_sework_tx_amt; // 그밖의_납세조합공제
	private int etc_house_laon_interest_amt; // 그밖의_주택차입금이자상환액세액공제
	private int etc_contribue_politic_amt; // 그밖의_기부정치자금
	private int etc_foreigner_income; // 그밖의_외국납부 소득금액
	private int etc_foreigner_pay; // 그밖의_외국납부 납부세액
	private int house_month_lent_amt; // 주택_월세액 지출액
	private int yt_tax_deduct_amt; // 세액공제계(연말정산63번)
	private int yt_determine_tax; // 결정세액(연말정산끝)
	private String disabled_person_yn; // 장애공제
	private int insurance_person_1; // 보장성보험-국세청
	private int insurance_person_2; // 보장성보험-그밖의자료
	private int insurance_disabled_person_1; // 장애인보험-국세청
	private int insurance_disabled_person_2; // 장애인보험-그밖의자료
	private int medical_1; // 의료비-국세청
	private int medical_2; // 의료비-그밖의자료
	private int educate_1; // 교육비-국세청
	private int educate_2; // 교육비-그밖의자료
	private int contribute_1; // 기부금-국세청
	private int contribute_2; // 기부금-그밖의자료
	private int dajayeon_cnt; // 추가공제_0에서 20살, 2명 이상은 계산하는 곳에서 처리
	private String medi_gb; // 의료구분 국세청장이 제공하는 의료비 자료-1 ,국민건강보험 공단의 의료비 부담명세서-2 ,진료비 계산서, 약제비 계산서-3 ,장기요양급여비용 명세서-4 ,기타 의료비 영수증-5
	private String vendor_no; // 지급처 사업자 등록번호
	private String vendor_nm; // 지급처 상호
	private String card_cnt; // 카드지급건수
	private int card_amt; // 카드지급금액
	private String edu_org; // 교육기관
	private int public_tx_amt; // 교육비공제금액
	private String gubun; // 국세청 OR 그밖의자료
	private String yr_don_div; // 기부금구분
	private int don_amt; // 기부금
	private String don_nm; // 기부처
	private String don_no; // 기부처번호
	private String don_receipt_no; // 기부영수증번호
	private String gongje_gb; // 소득공제구분 A1-연금저축 P1-개인연금저축 E1- 청약저축 E2-근로자주택마련저축 E3-주택청약종합저축
	private String bank_nm; // 금융기관명
	private String bank_cd; // 금융기관코드
	private String account_no; // 계좌번호
	private int in_amt;// 불입금액
	private String rel_cd_nm; // 관계인 코드 명
	private int work_amt; // 누진공제액
	private int work_pay;
	private int privous_income_tax; // 징수한 종전근무지 소득세
	private int privous_jumin_tax; // 징수한 종전근무지 주민세
	private int income_tax; // 징수한 소득세
	private String family_direct_ancestor_cnt; // 기본사항_직계존속수
	private String family_direct_descendant_cnt; // 기본사항_직계비속수
	private String family_brother_cnt; // 기본사항_형제자매수
	private String foster_child_cnt; // 기본사항_위탁아동
	private String pensioner_cnt; // 기본사항_수급자
	
	private int etc_credt_amt_ly; // 신용카드 작년사용분 2014년 연말정산 
	private int etc_cash_receipt_amt_ly; // 현금영수증 작년사용분 2014년 연말정산 
	private int etc_direct_amt_ly; // 직불카드 작년사용분 2014년 연말정산 
	private int etc_market_amt_ly; // 전통시장 작년사용분 2014년 연말정산 
	private int etc_pubric_transport_amt_ly; // 대중교통 작년사용분 2014년 연말정산 
	private int etc_credt_amt_fh;
	private int etc_cash_amt_fh;
	private int etc_direct_amt_fh;
	private int etc_market_amt_fh;
	private int etc_pubric_transport_amt_fh;
	private int etc_credt_amt_sh;
	private int etc_cash_amt_sh;
	private int etc_direct_amt_sh;
	private int etc_market_amt_sh;
	private int etc_pubric_transport_amt_sh;
	private int compute_1; // 2013년 본인 신용카드 등 사용액
	private int compute_2; // 2013년 본인의 추가 공제율 사용액
	private int compute_3; // 2014년 본인 신용카드 등 사용액
	private int compute_4; // 2014년 하반기 본인의 추가공제율 사용액
	
	private int smiymjtc_100; // 100% 감면금액
	private int smiymjtc_50; // 50% 감면금액
	private int medical_person_amt; // 의료비_본인공제액100%
	private int medical_old_amt; // 의료비_65세이상 FAMILY_OLD1_CNT값 이용
	private int medical_disabled_person_amt; // 의료비 의료비_장애인공제액
	private int medical_etc_amt; // 의료비_그밖의공제대상자공제액
	private int medical_calc;
	private int educate_person_amt; // 교육비_본인 100%
	private int educate_kindergarten_amt; // 교육비_취학전아동 family계산
	private int educate_school_amt; // 교육비_초중고 family계산
	private int educate_univ_amt; // 교육비_대학생 family계산
	private int educate_disabled_person_amt; // 교육비_장애인 특수교육
	private String searchType; // 검색 타입
	private String result; // 결과
	
	private String org_rel_jumin_no; // 관계인 주민번호(원본)
	
	/**
	 * @return the comp_name
	 */
	public String getComp_name() {
		return comp_name;
	}
	/**
	 * @param comp_name the comp_name to set
	 */
	public void setComp_name(String comp_name) {
		this.comp_name = comp_name;
	}
	/**
	 * @return the president
	 */
	public String getPresident() {
		return president;
	}
	/**
	 * @param president the president to set
	 */
	public void setPresident(String president) {
		this.president = president;
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
	 * @return the comp_no
	 */
	public String getComp_no() {
		return comp_no;
	}
	/**
	 * @param comp_no the comp_no to set
	 */
	public void setComp_no(String comp_no) {
		this.comp_no = comp_no;
	}
	/**
	 * @return the com_addr
	 */
	public String getCom_addr() {
		return com_addr;
	}
	/**
	 * @param com_addr the com_addr to set
	 */
	public void setCom_addr(String com_addr) {
		this.com_addr = com_addr;
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
	 * @return the resid_no
	 */
	public String getResid_no() {
		return resid_no;
	}
	/**
	 * @param resid_no the resid_no to set
	 */
	public void setResid_no(String resid_no) {
		this.resid_no = resid_no;
	}
	/**
	 * @return the bas_addr
	 */
	public String getBas_addr() {
		return bas_addr;
	}
	/**
	 * @param bas_addr the bas_addr to set
	 */
	public void setBas_addr(String bas_addr) {
		this.bas_addr = bas_addr;
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
	 * @return the bas_start_dt
	 */
	public String getBas_start_dt() {
		return bas_start_dt;
	}
	/**
	 * @param bas_start_dt the bas_start_dt to set
	 */
	public void setBas_start_dt(String bas_start_dt) {
		this.bas_start_dt = bas_start_dt;
	}
	/**
	 * @return the bas_end_dt
	 */
	public String getBas_end_dt() {
		return bas_end_dt;
	}
	/**
	 * @param bas_end_dt the bas_end_dt to set
	 */
	public void setBas_end_dt(String bas_end_dt) {
		this.bas_end_dt = bas_end_dt;
	}
	/**
	 * @return the corporate_nm
	 */
	public String getCorporate_nm() {
		return corporate_nm;
	}
	/**
	 * @param corporate_nm the corporate_nm to set
	 */
	public void setCorporate_nm(String corporate_nm) {
		this.corporate_nm = corporate_nm;
	}
	/**
	 * @return the corporate_nm2
	 */
	public String getCorporate_nm2() {
		return corporate_nm2;
	}
	/**
	 * @param corporate_nm2 the corporate_nm2 to set
	 */
	public void setCorporate_nm2(String corporate_nm2) {
		this.corporate_nm2 = corporate_nm2;
	}
	/**
	 * @return the corporate_no
	 */
	public String getCorporate_no() {
		return corporate_no;
	}
	/**
	 * @param corporate_no the corporate_no to set
	 */
	public void setCorporate_no(String corporate_no) {
		this.corporate_no = corporate_no;
	}
	/**
	 * @return the corporate_no2
	 */
	public String getCorporate_no2() {
		return corporate_no2;
	}
	/**
	 * @param corporate_no2 the corporate_no2 to set
	 */
	public void setCorporate_no2(String corporate_no2) {
		this.corporate_no2 = corporate_no2;
	}
	/**
	 * @return the start_work_dt
	 */
	public String getStart_work_dt() {
		return start_work_dt;
	}
	/**
	 * @param start_work_dt the start_work_dt to set
	 */
	public void setStart_work_dt(String start_work_dt) {
		this.start_work_dt = start_work_dt;
	}
	/**
	 * @return the end_work_dt
	 */
	public String getEnd_work_dt() {
		return end_work_dt;
	}
	/**
	 * @param end_work_dt the end_work_dt to set
	 */
	public void setEnd_work_dt(String end_work_dt) {
		this.end_work_dt = end_work_dt;
	}
	/**
	 * @return the start_work_dt2
	 */
	public String getStart_work_dt2() {
		return start_work_dt2;
	}
	/**
	 * @param start_work_dt2 the start_work_dt2 to set
	 */
	public void setStart_work_dt2(String start_work_dt2) {
		this.start_work_dt2 = start_work_dt2;
	}
	/**
	 * @return the end_work_dt2
	 */
	public String getEnd_work_dt2() {
		return end_work_dt2;
	}
	/**
	 * @param end_work_dt2 the end_work_dt2 to set
	 */
	public void setEnd_work_dt2(String end_work_dt2) {
		this.end_work_dt2 = end_work_dt2;
	}
	/**
	 * @return the start_reduce_dt2
	 */
	public String getStart_reduce_dt2() {
		return start_reduce_dt2;
	}
	/**
	 * @param start_reduce_dt2 the start_reduce_dt2 to set
	 */
	public void setStart_reduce_dt2(String start_reduce_dt2) {
		this.start_reduce_dt2 = start_reduce_dt2;
	}
	/**
	 * @return the end_reduce_dt2
	 */
	public String getEnd_reduce_dt2() {
		return end_reduce_dt2;
	}
	/**
	 * @param end_reduce_dt2 the end_reduce_dt2 to set
	 */
	public void setEnd_reduce_dt2(String end_reduce_dt2) {
		this.end_reduce_dt2 = end_reduce_dt2;
	}
	/**
	 * @return the start_reduce_dt
	 */
	public String getStart_reduce_dt() {
		return start_reduce_dt;
	}
	/**
	 * @param start_reduce_dt the start_reduce_dt to set
	 */
	public void setStart_reduce_dt(String start_reduce_dt) {
		this.start_reduce_dt = start_reduce_dt;
	}
	/**
	 * @return the end_reduce_dt
	 */
	public String getEnd_reduce_dt() {
		return end_reduce_dt;
	}
	/**
	 * @param end_reduce_dt the end_reduce_dt to set
	 */
	public void setEnd_reduce_dt(String end_reduce_dt) {
		this.end_reduce_dt = end_reduce_dt;
	}
	/**
	 * @return the salary_amt
	 */
	public int getSalary_amt() {
		return salary_amt;
	}
	/**
	 * @param salary_amt the salary_amt to set
	 */
	public void setSalary_amt(int salary_amt) {
		this.salary_amt = salary_amt;
	}
	/**
	 * @return the bonus_amt
	 */
	public int getBonus_amt() {
		return bonus_amt;
	}
	/**
	 * @param bonus_amt the bonus_amt to set
	 */
	public void setBonus_amt(int bonus_amt) {
		this.bonus_amt = bonus_amt;
	}
	/**
	 * @return the constructive_bonus_amt
	 */
	public int getConstructive_bonus_amt() {
		return constructive_bonus_amt;
	}
	/**
	 * @param constructive_bonus_amt the constructive_bonus_amt to set
	 */
	public void setConstructive_bonus_amt(int constructive_bonus_amt) {
		this.constructive_bonus_amt = constructive_bonus_amt;
	}
	/**
	 * @return the stock_option_amt
	 */
	public int getStock_option_amt() {
		return stock_option_amt;
	}
	/**
	 * @param stock_option_amt the stock_option_amt to set
	 */
	public void setStock_option_amt(int stock_option_amt) {
		this.stock_option_amt = stock_option_amt;
	}
	/**
	 * @return the employ_stock_amt
	 */
	public int getEmploy_stock_amt() {
		return employ_stock_amt;
	}
	/**
	 * @param employ_stock_amt the employ_stock_amt to set
	 */
	public void setEmploy_stock_amt(int employ_stock_amt) {
		this.employ_stock_amt = employ_stock_amt;
	}
	/**
	 * @return the derector_retirement_amt
	 */
	public int getDerector_retirement_amt() {
		return derector_retirement_amt;
	}
	/**
	 * @param derector_retirement_amt the derector_retirement_amt to set
	 */
	public void setDerector_retirement_amt(int derector_retirement_amt) {
		this.derector_retirement_amt = derector_retirement_amt;
	}
	/**
	 * @return the total_salary
	 */
	public int getTotal_salary() {
		return total_salary;
	}
	/**
	 * @param total_salary the total_salary to set
	 */
	public void setTotal_salary(int total_salary) {
		this.total_salary = total_salary;
	}
	/**
	 * @return the foreign_work_amt
	 */
	public int getForeign_work_amt() {
		return foreign_work_amt;
	}
	/**
	 * @param foreign_work_amt the foreign_work_amt to set
	 */
	public void setForeign_work_amt(int foreign_work_amt) {
		this.foreign_work_amt = foreign_work_amt;
	}
	/**
	 * @return the oevrtime_amt
	 */
	public int getOevrtime_amt() {
		return oevrtime_amt;
	}
	/**
	 * @param oevrtime_amt the oevrtime_amt to set
	 */
	public void setOevrtime_amt(int oevrtime_amt) {
		this.oevrtime_amt = oevrtime_amt;
	}
	/**
	 * @return the meternity_amt
	 */
	public int getMeternity_amt() {
		return meternity_amt;
	}
	/**
	 * @param meternity_amt the meternity_amt to set
	 */
	public void setMeternity_amt(int meternity_amt) {
		this.meternity_amt = meternity_amt;
	}
	/**
	 * @return the research_amt
	 */
	public int getResearch_amt() {
		return research_amt;
	}
	/**
	 * @param research_amt the research_amt to set
	 */
	public void setResearch_amt(int research_amt) {
		this.research_amt = research_amt;
	}
	/**
	 * @return the school_expenses_amt
	 */
	public int getSchool_expenses_amt() {
		return school_expenses_amt;
	}
	/**
	 * @param school_expenses_amt the school_expenses_amt to set
	 */
	public void setSchool_expenses_amt(int school_expenses_amt) {
		this.school_expenses_amt = school_expenses_amt;
	}
	/**
	 * @return the collect_amt
	 */
	public int getCollect_amt() {
		return collect_amt;
	}
	/**
	 * @param collect_amt the collect_amt to set
	 */
	public void setCollect_amt(int collect_amt) {
		this.collect_amt = collect_amt;
	}
	/**
	 * @return the remote_rural_area_amt
	 */
	public int getRemote_rural_area_amt() {
		return remote_rural_area_amt;
	}
	/**
	 * @param remote_rural_area_amt the remote_rural_area_amt to set
	 */
	public void setRemote_rural_area_amt(int remote_rural_area_amt) {
		this.remote_rural_area_amt = remote_rural_area_amt;
	}
	/**
	 * @return the natural_disaster_amt
	 */
	public int getNatural_disaster_amt() {
		return natural_disaster_amt;
	}
	/**
	 * @param natural_disaster_amt the natural_disaster_amt to set
	 */
	public void setNatural_disaster_amt(int natural_disaster_amt) {
		this.natural_disaster_amt = natural_disaster_amt;
	}
	/**
	 * @return the legislation_amt
	 */
	public int getLegislation_amt() {
		return legislation_amt;
	}
	/**
	 * @param legislation_amt the legislation_amt to set
	 */
	public void setLegislation_amt(int legislation_amt) {
		this.legislation_amt = legislation_amt;
	}
	/**
	 * @return the operation_amt
	 */
	public int getOperation_amt() {
		return operation_amt;
	}
	/**
	 * @param operation_amt the operation_amt to set
	 */
	public void setOperation_amt(int operation_amt) {
		this.operation_amt = operation_amt;
	}
	/**
	 * @return the stock_purchase_amt
	 */
	public int getStock_purchase_amt() {
		return stock_purchase_amt;
	}
	/**
	 * @param stock_purchase_amt the stock_purchase_amt to set
	 */
	public void setStock_purchase_amt(int stock_purchase_amt) {
		this.stock_purchase_amt = stock_purchase_amt;
	}
	/**
	 * @return the foreigner_amt
	 */
	public int getForeigner_amt() {
		return foreigner_amt;
	}
	/**
	 * @param foreigner_amt the foreigner_amt to set
	 */
	public void setForeigner_amt(int foreigner_amt) {
		this.foreigner_amt = foreigner_amt;
	}
	/**
	 * @return the employ_stock_amt1
	 */
	public int getEmploy_stock_amt1() {
		return employ_stock_amt1;
	}
	/**
	 * @param employ_stock_amt1 the employ_stock_amt1 to set
	 */
	public void setEmploy_stock_amt1(int employ_stock_amt1) {
		this.employ_stock_amt1 = employ_stock_amt1;
	}
	/**
	 * @return the employ_stock_amt2
	 */
	public int getEmploy_stock_amt2() {
		return employ_stock_amt2;
	}
	/**
	 * @param employ_stock_amt2 the employ_stock_amt2 to set
	 */
	public void setEmploy_stock_amt2(int employ_stock_amt2) {
		this.employ_stock_amt2 = employ_stock_amt2;
	}
	/**
	 * @return the submarine_mineral_amt
	 */
	public int getSubmarine_mineral_amt() {
		return submarine_mineral_amt;
	}
	/**
	 * @param submarine_mineral_amt the submarine_mineral_amt to set
	 */
	public void setSubmarine_mineral_amt(int submarine_mineral_amt) {
		this.submarine_mineral_amt = submarine_mineral_amt;
	}
	/**
	 * @return the guard_embark_amt
	 */
	public int getGuard_embark_amt() {
		return guard_embark_amt;
	}
	/**
	 * @param guard_embark_amt the guard_embark_amt to set
	 */
	public void setGuard_embark_amt(int guard_embark_amt) {
		this.guard_embark_amt = guard_embark_amt;
	}
	/**
	 * @return the organization_amt
	 */
	public int getOrganization_amt() {
		return organization_amt;
	}
	/**
	 * @param organization_amt the organization_amt to set
	 */
	public void setOrganization_amt(int organization_amt) {
		this.organization_amt = organization_amt;
	}
	/**
	 * @return the scholarship_amt
	 */
	public int getScholarship_amt() {
		return scholarship_amt;
	}
	/**
	 * @param scholarship_amt the scholarship_amt to set
	 */
	public void setScholarship_amt(int scholarship_amt) {
		this.scholarship_amt = scholarship_amt;
	}
	/**
	 * @return the childcare_amt
	 */
	public int getChildcare_amt() {
		return childcare_amt;
	}
	/**
	 * @param childcare_amt the childcare_amt to set
	 */
	public void setChildcare_amt(int childcare_amt) {
		this.childcare_amt = childcare_amt;
	}
	/**
	 * @return the kindergarten_teacher_amt
	 */
	public int getKindergarten_teacher_amt() {
		return kindergarten_teacher_amt;
	}
	/**
	 * @param kindergarten_teacher_amt the kindergarten_teacher_amt to set
	 */
	public void setKindergarten_teacher_amt(int kindergarten_teacher_amt) {
		this.kindergarten_teacher_amt = kindergarten_teacher_amt;
	}
	/**
	 * @return the smiymjtc_amt
	 */
	public int getSmiymjtc_amt() {
		return smiymjtc_amt;
	}
	/**
	 * @param smiymjtc_amt the smiymjtc_amt to set
	 */
	public void setSmiymjtc_amt(int smiymjtc_amt) {
		this.smiymjtc_amt = smiymjtc_amt;
	}
	/**
	 * @return the teache_clause_amt
	 */
	public int getTeache_clause_amt() {
		return teache_clause_amt;
	}
	/**
	 * @param teache_clause_amt the teache_clause_amt to set
	 */
	public void setTeache_clause_amt(int teache_clause_amt) {
		this.teache_clause_amt = teache_clause_amt;
	}
	/**
	 * @return the move_amt
	 */
	public int getMove_amt() {
		return move_amt;
	}
	/**
	 * @param move_amt the move_amt to set
	 */
	public void setMove_amt(int move_amt) {
		this.move_amt = move_amt;
	}
	/**
	 * @return the major_amt
	 */
	public int getMajor_amt() {
		return major_amt;
	}
	/**
	 * @param major_amt the major_amt to set
	 */
	public void setMajor_amt(int major_amt) {
		this.major_amt = major_amt;
	}
	/**
	 * @return the total_free
	 */
	public int getTotal_free() {
		return total_free;
	}
	/**
	 * @param total_free the total_free to set
	 */
	public void setTotal_free(int total_free) {
		this.total_free = total_free;
	}
	/**
	 * @return the prev_reduction_amt
	 */
	public int getPrev_reduction_amt() {
		return prev_reduction_amt;
	}
	/**
	 * @param prev_reduction_amt the prev_reduction_amt to set
	 */
	public void setPrev_reduction_amt(int prev_reduction_amt) {
		this.prev_reduction_amt = prev_reduction_amt;
	}
	/**
	 * @return the prev_health_amt
	 */
	public int getPrev_health_amt() {
		return prev_health_amt;
	}
	/**
	 * @param prev_health_amt the prev_health_amt to set
	 */
	public void setPrev_health_amt(int prev_health_amt) {
		this.prev_health_amt = prev_health_amt;
	}
	/**
	 * @return the prev_employ_amt
	 */
	public int getPrev_employ_amt() {
		return prev_employ_amt;
	}
	/**
	 * @param prev_employ_amt the prev_employ_amt to set
	 */
	public void setPrev_employ_amt(int prev_employ_amt) {
		this.prev_employ_amt = prev_employ_amt;
	}
	/**
	 * @return the prev_kuk_yeon_amt
	 */
	public int getPrev_kuk_yeon_amt() {
		return prev_kuk_yeon_amt;
	}
	/**
	 * @param prev_kuk_yeon_amt the prev_kuk_yeon_amt to set
	 */
	public void setPrev_kuk_yeon_amt(int prev_kuk_yeon_amt) {
		this.prev_kuk_yeon_amt = prev_kuk_yeon_amt;
	}
	/**
	 * @return the prev_annuity_amt
	 */
	public int getPrev_annuity_amt() {
		return prev_annuity_amt;
	}
	/**
	 * @param prev_annuity_amt the prev_annuity_amt to set
	 */
	public void setPrev_annuity_amt(int prev_annuity_amt) {
		this.prev_annuity_amt = prev_annuity_amt;
	}
	/**
	 * @return the prev_income_amt
	 */
	public int getPrev_income_amt() {
		return prev_income_amt;
	}
	/**
	 * @param prev_income_amt the prev_income_amt to set
	 */
	public void setPrev_income_amt(int prev_income_amt) {
		this.prev_income_amt = prev_income_amt;
	}
	/**
	 * @return the prev_jumin_amt
	 */
	public int getPrev_jumin_amt() {
		return prev_jumin_amt;
	}
	/**
	 * @param prev_jumin_amt the prev_jumin_amt to set
	 */
	public void setPrev_jumin_amt(int prev_jumin_amt) {
		this.prev_jumin_amt = prev_jumin_amt;
	}
	/**
	 * @return the prev_nong_amt
	 */
	public int getPrev_nong_amt() {
		return prev_nong_amt;
	}
	/**
	 * @param prev_nong_amt the prev_nong_amt to set
	 */
	public void setPrev_nong_amt(int prev_nong_amt) {
		this.prev_nong_amt = prev_nong_amt;
	}
	/**
	 * @return the salary_amt2
	 */
	public int getSalary_amt2() {
		return salary_amt2;
	}
	/**
	 * @param salary_amt2 the salary_amt2 to set
	 */
	public void setSalary_amt2(int salary_amt2) {
		this.salary_amt2 = salary_amt2;
	}
	/**
	 * @return the bonus_amt2
	 */
	public int getBonus_amt2() {
		return bonus_amt2;
	}
	/**
	 * @param bonus_amt2 the bonus_amt2 to set
	 */
	public void setBonus_amt2(int bonus_amt2) {
		this.bonus_amt2 = bonus_amt2;
	}
	/**
	 * @return the constructive_bonus_amt2
	 */
	public int getConstructive_bonus_amt2() {
		return constructive_bonus_amt2;
	}
	/**
	 * @param constructive_bonus_amt2 the constructive_bonus_amt2 to set
	 */
	public void setConstructive_bonus_amt2(int constructive_bonus_amt2) {
		this.constructive_bonus_amt2 = constructive_bonus_amt2;
	}
	/**
	 * @return the stock_option_amt2
	 */
	public int getStock_option_amt2() {
		return stock_option_amt2;
	}
	/**
	 * @param stock_option_amt2 the stock_option_amt2 to set
	 */
	public void setStock_option_amt2(int stock_option_amt2) {
		this.stock_option_amt2 = stock_option_amt2;
	}
	/**
	 * @return the employ_stock_amt_2
	 */
	public int getEmploy_stock_amt_2() {
		return employ_stock_amt_2;
	}
	/**
	 * @param employ_stock_amt_2 the employ_stock_amt_2 to set
	 */
	public void setEmploy_stock_amt_2(int employ_stock_amt_2) {
		this.employ_stock_amt_2 = employ_stock_amt_2;
	}
	/**
	 * @return the derector_retirement_amt2
	 */
	public int getDerector_retirement_amt2() {
		return derector_retirement_amt2;
	}
	/**
	 * @param derector_retirement_amt2 the derector_retirement_amt2 to set
	 */
	public void setDerector_retirement_amt2(int derector_retirement_amt2) {
		this.derector_retirement_amt2 = derector_retirement_amt2;
	}
	/**
	 * @return the total_salary2
	 */
	public int getTotal_salary2() {
		return total_salary2;
	}
	/**
	 * @param total_salary2 the total_salary2 to set
	 */
	public void setTotal_salary2(int total_salary2) {
		this.total_salary2 = total_salary2;
	}
	/**
	 * @return the foreign_work_amt2
	 */
	public int getForeign_work_amt2() {
		return foreign_work_amt2;
	}
	/**
	 * @param foreign_work_amt2 the foreign_work_amt2 to set
	 */
	public void setForeign_work_amt2(int foreign_work_amt2) {
		this.foreign_work_amt2 = foreign_work_amt2;
	}
	/**
	 * @return the oevrtime_amt2
	 */
	public int getOevrtime_amt2() {
		return oevrtime_amt2;
	}
	/**
	 * @param oevrtime_amt2 the oevrtime_amt2 to set
	 */
	public void setOevrtime_amt2(int oevrtime_amt2) {
		this.oevrtime_amt2 = oevrtime_amt2;
	}
	/**
	 * @return the meternity_amt2
	 */
	public int getMeternity_amt2() {
		return meternity_amt2;
	}
	/**
	 * @param meternity_amt2 the meternity_amt2 to set
	 */
	public void setMeternity_amt2(int meternity_amt2) {
		this.meternity_amt2 = meternity_amt2;
	}
	/**
	 * @return the research_amt2
	 */
	public int getResearch_amt2() {
		return research_amt2;
	}
	/**
	 * @param research_amt2 the research_amt2 to set
	 */
	public void setResearch_amt2(int research_amt2) {
		this.research_amt2 = research_amt2;
	}
	/**
	 * @return the school_expenses_amt2
	 */
	public int getSchool_expenses_amt2() {
		return school_expenses_amt2;
	}
	/**
	 * @param school_expenses_amt2 the school_expenses_amt2 to set
	 */
	public void setSchool_expenses_amt2(int school_expenses_amt2) {
		this.school_expenses_amt2 = school_expenses_amt2;
	}
	/**
	 * @return the collect_amt2
	 */
	public int getCollect_amt2() {
		return collect_amt2;
	}
	/**
	 * @param collect_amt2 the collect_amt2 to set
	 */
	public void setCollect_amt2(int collect_amt2) {
		this.collect_amt2 = collect_amt2;
	}
	/**
	 * @return the remote_rural_area_amt2
	 */
	public int getRemote_rural_area_amt2() {
		return remote_rural_area_amt2;
	}
	/**
	 * @param remote_rural_area_amt2 the remote_rural_area_amt2 to set
	 */
	public void setRemote_rural_area_amt2(int remote_rural_area_amt2) {
		this.remote_rural_area_amt2 = remote_rural_area_amt2;
	}
	/**
	 * @return the natural_disaster_amt2
	 */
	public int getNatural_disaster_amt2() {
		return natural_disaster_amt2;
	}
	/**
	 * @param natural_disaster_amt2 the natural_disaster_amt2 to set
	 */
	public void setNatural_disaster_amt2(int natural_disaster_amt2) {
		this.natural_disaster_amt2 = natural_disaster_amt2;
	}
	/**
	 * @return the legislation_amt2
	 */
	public int getLegislation_amt2() {
		return legislation_amt2;
	}
	/**
	 * @param legislation_amt2 the legislation_amt2 to set
	 */
	public void setLegislation_amt2(int legislation_amt2) {
		this.legislation_amt2 = legislation_amt2;
	}
	/**
	 * @return the operation_amt2
	 */
	public int getOperation_amt2() {
		return operation_amt2;
	}
	/**
	 * @param operation_amt2 the operation_amt2 to set
	 */
	public void setOperation_amt2(int operation_amt2) {
		this.operation_amt2 = operation_amt2;
	}
	/**
	 * @return the stock_purchase_amt2
	 */
	public int getStock_purchase_amt2() {
		return stock_purchase_amt2;
	}
	/**
	 * @param stock_purchase_amt2 the stock_purchase_amt2 to set
	 */
	public void setStock_purchase_amt2(int stock_purchase_amt2) {
		this.stock_purchase_amt2 = stock_purchase_amt2;
	}
	/**
	 * @return the foreigner_amt2
	 */
	public int getForeigner_amt2() {
		return foreigner_amt2;
	}
	/**
	 * @param foreigner_amt2 the foreigner_amt2 to set
	 */
	public void setForeigner_amt2(int foreigner_amt2) {
		this.foreigner_amt2 = foreigner_amt2;
	}
	/**
	 * @return the employ_stock_amt1_2
	 */
	public int getEmploy_stock_amt1_2() {
		return employ_stock_amt1_2;
	}
	/**
	 * @param employ_stock_amt1_2 the employ_stock_amt1_2 to set
	 */
	public void setEmploy_stock_amt1_2(int employ_stock_amt1_2) {
		this.employ_stock_amt1_2 = employ_stock_amt1_2;
	}
	/**
	 * @return the employ_stock_amt2_2
	 */
	public int getEmploy_stock_amt2_2() {
		return employ_stock_amt2_2;
	}
	/**
	 * @param employ_stock_amt2_2 the employ_stock_amt2_2 to set
	 */
	public void setEmploy_stock_amt2_2(int employ_stock_amt2_2) {
		this.employ_stock_amt2_2 = employ_stock_amt2_2;
	}
	/**
	 * @return the submarine_mineral_amt2
	 */
	public int getSubmarine_mineral_amt2() {
		return submarine_mineral_amt2;
	}
	/**
	 * @param submarine_mineral_amt2 the submarine_mineral_amt2 to set
	 */
	public void setSubmarine_mineral_amt2(int submarine_mineral_amt2) {
		this.submarine_mineral_amt2 = submarine_mineral_amt2;
	}
	/**
	 * @return the guard_embark_amt2
	 */
	public int getGuard_embark_amt2() {
		return guard_embark_amt2;
	}
	/**
	 * @param guard_embark_amt2 the guard_embark_amt2 to set
	 */
	public void setGuard_embark_amt2(int guard_embark_amt2) {
		this.guard_embark_amt2 = guard_embark_amt2;
	}
	/**
	 * @return the organization_amt2
	 */
	public int getOrganization_amt2() {
		return organization_amt2;
	}
	/**
	 * @param organization_amt2 the organization_amt2 to set
	 */
	public void setOrganization_amt2(int organization_amt2) {
		this.organization_amt2 = organization_amt2;
	}
	/**
	 * @return the scholarship_amt2
	 */
	public int getScholarship_amt2() {
		return scholarship_amt2;
	}
	/**
	 * @param scholarship_amt2 the scholarship_amt2 to set
	 */
	public void setScholarship_amt2(int scholarship_amt2) {
		this.scholarship_amt2 = scholarship_amt2;
	}
	/**
	 * @return the childcare_amt2
	 */
	public int getChildcare_amt2() {
		return childcare_amt2;
	}
	/**
	 * @param childcare_amt2 the childcare_amt2 to set
	 */
	public void setChildcare_amt2(int childcare_amt2) {
		this.childcare_amt2 = childcare_amt2;
	}
	/**
	 * @return the kindergarten_teacher_amt2
	 */
	public int getKindergarten_teacher_amt2() {
		return kindergarten_teacher_amt2;
	}
	/**
	 * @param kindergarten_teacher_amt2 the kindergarten_teacher_amt2 to set
	 */
	public void setKindergarten_teacher_amt2(int kindergarten_teacher_amt2) {
		this.kindergarten_teacher_amt2 = kindergarten_teacher_amt2;
	}
	/**
	 * @return the smiymjtc_amt2
	 */
	public int getSmiymjtc_amt2() {
		return smiymjtc_amt2;
	}
	/**
	 * @param smiymjtc_amt2 the smiymjtc_amt2 to set
	 */
	public void setSmiymjtc_amt2(int smiymjtc_amt2) {
		this.smiymjtc_amt2 = smiymjtc_amt2;
	}
	/**
	 * @return the teache_clause_amt2
	 */
	public int getTeache_clause_amt2() {
		return teache_clause_amt2;
	}
	/**
	 * @param teache_clause_amt2 the teache_clause_amt2 to set
	 */
	public void setTeache_clause_amt2(int teache_clause_amt2) {
		this.teache_clause_amt2 = teache_clause_amt2;
	}
	/**
	 * @return the move_amt2
	 */
	public int getMove_amt2() {
		return move_amt2;
	}
	/**
	 * @param move_amt2 the move_amt2 to set
	 */
	public void setMove_amt2(int move_amt2) {
		this.move_amt2 = move_amt2;
	}
	/**
	 * @return the major_amt2
	 */
	public int getMajor_amt2() {
		return major_amt2;
	}
	/**
	 * @param major_amt2 the major_amt2 to set
	 */
	public void setMajor_amt2(int major_amt2) {
		this.major_amt2 = major_amt2;
	}
	/**
	 * @return the total_free2
	 */
	public int getTotal_free2() {
		return total_free2;
	}
	/**
	 * @param total_free2 the total_free2 to set
	 */
	public void setTotal_free2(int total_free2) {
		this.total_free2 = total_free2;
	}
	/**
	 * @return the prev_reduction_amt2
	 */
	public int getPrev_reduction_amt2() {
		return prev_reduction_amt2;
	}
	/**
	 * @param prev_reduction_amt2 the prev_reduction_amt2 to set
	 */
	public void setPrev_reduction_amt2(int prev_reduction_amt2) {
		this.prev_reduction_amt2 = prev_reduction_amt2;
	}
	/**
	 * @return the prev_health_amt2
	 */
	public int getPrev_health_amt2() {
		return prev_health_amt2;
	}
	/**
	 * @param prev_health_amt2 the prev_health_amt2 to set
	 */
	public void setPrev_health_amt2(int prev_health_amt2) {
		this.prev_health_amt2 = prev_health_amt2;
	}
	/**
	 * @return the prev_employ_amt2
	 */
	public int getPrev_employ_amt2() {
		return prev_employ_amt2;
	}
	/**
	 * @param prev_employ_amt2 the prev_employ_amt2 to set
	 */
	public void setPrev_employ_amt2(int prev_employ_amt2) {
		this.prev_employ_amt2 = prev_employ_amt2;
	}
	/**
	 * @return the prev_kuk_yeon_amt2
	 */
	public int getPrev_kuk_yeon_amt2() {
		return prev_kuk_yeon_amt2;
	}
	/**
	 * @param prev_kuk_yeon_amt2 the prev_kuk_yeon_amt2 to set
	 */
	public void setPrev_kuk_yeon_amt2(int prev_kuk_yeon_amt2) {
		this.prev_kuk_yeon_amt2 = prev_kuk_yeon_amt2;
	}
	/**
	 * @return the prev_annuity_amt2
	 */
	public int getPrev_annuity_amt2() {
		return prev_annuity_amt2;
	}
	/**
	 * @param prev_annuity_amt2 the prev_annuity_amt2 to set
	 */
	public void setPrev_annuity_amt2(int prev_annuity_amt2) {
		this.prev_annuity_amt2 = prev_annuity_amt2;
	}
	/**
	 * @return the prev_income_amt2
	 */
	public int getPrev_income_amt2() {
		return prev_income_amt2;
	}
	/**
	 * @param prev_income_amt2 the prev_income_amt2 to set
	 */
	public void setPrev_income_amt2(int prev_income_amt2) {
		this.prev_income_amt2 = prev_income_amt2;
	}
	/**
	 * @return the prev_jumin_amt2
	 */
	public int getPrev_jumin_amt2() {
		return prev_jumin_amt2;
	}
	/**
	 * @param prev_jumin_amt2 the prev_jumin_amt2 to set
	 */
	public void setPrev_jumin_amt2(int prev_jumin_amt2) {
		this.prev_jumin_amt2 = prev_jumin_amt2;
	}
	/**
	 * @return the prev_nong_amt2
	 */
	public int getPrev_nong_amt2() {
		return prev_nong_amt2;
	}
	/**
	 * @param prev_nong_amt2 the prev_nong_amt2 to set
	 */
	public void setPrev_nong_amt2(int prev_nong_amt2) {
		this.prev_nong_amt2 = prev_nong_amt2;
	}
	/**
	 * @return the pay_sum
	 */
	public int getPay_sum() {
		return pay_sum;
	}
	/**
	 * @param pay_sum the pay_sum to set
	 */
	public void setPay_sum(int pay_sum) {
		this.pay_sum = pay_sum;
	}
	/**
	 * @return the bonus_sum
	 */
	public int getBonus_sum() {
		return bonus_sum;
	}
	/**
	 * @param bonus_sum the bonus_sum to set
	 */
	public void setBonus_sum(int bonus_sum) {
		this.bonus_sum = bonus_sum;
	}
	/**
	 * @return the free_overtime_sum
	 */
	public int getFree_overtime_sum() {
		return free_overtime_sum;
	}
	/**
	 * @param free_overtime_sum the free_overtime_sum to set
	 */
	public void setFree_overtime_sum(int free_overtime_sum) {
		this.free_overtime_sum = free_overtime_sum;
	}
	/**
	 * @return the free_tax_sum
	 */
	public int getFree_tax_sum() {
		return free_tax_sum;
	}
	/**
	 * @param free_tax_sum the free_tax_sum to set
	 */
	public void setFree_tax_sum(int free_tax_sum) {
		this.free_tax_sum = free_tax_sum;
	}
	/**
	 * @return the determine_income_tax
	 */
	public int getDetermine_income_tax() {
		return determine_income_tax;
	}
	/**
	 * @param determine_income_tax the determine_income_tax to set
	 */
	public void setDetermine_income_tax(int determine_income_tax) {
		this.determine_income_tax = determine_income_tax;
	}
	/**
	 * @return the determine_jumin_tax
	 */
	public int getDetermine_jumin_tax() {
		return determine_jumin_tax;
	}
	/**
	 * @param determine_jumin_tax the determine_jumin_tax to set
	 */
	public void setDetermine_jumin_tax(int determine_jumin_tax) {
		this.determine_jumin_tax = determine_jumin_tax;
	}
	/**
	 * @return the determine_nong_tax
	 */
	public int getDetermine_nong_tax() {
		return determine_nong_tax;
	}
	/**
	 * @param determine_nong_tax the determine_nong_tax to set
	 */
	public void setDetermine_nong_tax(int determine_nong_tax) {
		this.determine_nong_tax = determine_nong_tax;
	}
	/**
	 * @return the deduction_income_tax
	 */
	public int getDeduction_income_tax() {
		return deduction_income_tax;
	}
	/**
	 * @param deduction_income_tax the deduction_income_tax to set
	 */
	public void setDeduction_income_tax(int deduction_income_tax) {
		this.deduction_income_tax = deduction_income_tax;
	}
	/**
	 * @return the deduction_jumin_tax
	 */
	public int getDeduction_jumin_tax() {
		return deduction_jumin_tax;
	}
	/**
	 * @param deduction_jumin_tax the deduction_jumin_tax to set
	 */
	public void setDeduction_jumin_tax(int deduction_jumin_tax) {
		this.deduction_jumin_tax = deduction_jumin_tax;
	}
	/**
	 * @return the deduction_nong_tax
	 */
	public int getDeduction_nong_tax() {
		return deduction_nong_tax;
	}
	/**
	 * @param deduction_nong_tax the deduction_nong_tax to set
	 */
	public void setDeduction_nong_tax(int deduction_nong_tax) {
		this.deduction_nong_tax = deduction_nong_tax;
	}
	/**
	 * @return the adjst_div
	 */
	public String getAdjst_div() {
		return adjst_div;
	}
	/**
	 * @param adjst_div the adjst_div to set
	 */
	public void setAdjst_div(String adjst_div) {
		this.adjst_div = adjst_div;
	}
	/**
	 * @return the year
	 */
	public String getYear() {
		return year;
	}
	/**
	 * @param year the year to set
	 */
	public void setYear(String year) {
		this.year = year;
	}
	/**
	 * @return the pay_sum_total
	 */
	public int getPay_sum_total() {
		return pay_sum_total;
	}
	/**
	 * @param pay_sum_total the pay_sum_total to set
	 */
	public void setPay_sum_total(int pay_sum_total) {
		this.pay_sum_total = pay_sum_total;
	}
	/**
	 * @return the kuk_yeon_amt
	 */
	public int getKuk_yeon_amt() {
		return kuk_yeon_amt;
	}
	/**
	 * @param kuk_yeon_amt the kuk_yeon_amt to set
	 */
	public void setKuk_yeon_amt(int kuk_yeon_amt) {
		this.kuk_yeon_amt = kuk_yeon_amt;
	}
	/**
	 * @return the insurance_health_amt
	 */
	public int getInsurance_health_amt() {
		return insurance_health_amt;
	}
	/**
	 * @param insurance_health_amt the insurance_health_amt to set
	 */
	public void setInsurance_health_amt(int insurance_health_amt) {
		this.insurance_health_amt = insurance_health_amt;
	}
	/**
	 * @return the insurance_employ_amt
	 */
	public int getInsurance_employ_amt() {
		return insurance_employ_amt;
	}
	/**
	 * @param insurance_employ_amt the insurance_employ_amt to set
	 */
	public void setInsurance_employ_amt(int insurance_employ_amt) {
		this.insurance_employ_amt = insurance_employ_amt;
	}
	/**
	 * @return the jing_income_tax
	 */
	public int getJing_income_tax() {
		return jing_income_tax;
	}
	/**
	 * @param jing_income_tax the jing_income_tax to set
	 */
	public void setJing_income_tax(int jing_income_tax) {
		this.jing_income_tax = jing_income_tax;
	}
	/**
	 * @return the jing_jumin_tax
	 */
	public int getJing_jumin_tax() {
		return jing_jumin_tax;
	}
	/**
	 * @param jing_jumin_tax the jing_jumin_tax to set
	 */
	public void setJing_jumin_tax(int jing_jumin_tax) {
		this.jing_jumin_tax = jing_jumin_tax;
	}
	/**
	 * @return the jing_nong_tax
	 */
	public int getJing_nong_tax() {
		return jing_nong_tax;
	}
	/**
	 * @param jing_nong_tax the jing_nong_tax to set
	 */
	public void setJing_nong_tax(int jing_nong_tax) {
		this.jing_nong_tax = jing_nong_tax;
	}
	/**
	 * @return the jing_jong_income_tax
	 */
	public int getJing_jong_income_tax() {
		return jing_jong_income_tax;
	}
	/**
	 * @param jing_jong_income_tax the jing_jong_income_tax to set
	 */
	public void setJing_jong_income_tax(int jing_jong_income_tax) {
		this.jing_jong_income_tax = jing_jong_income_tax;
	}
	/**
	 * @return the jing_jong_jumin_tax
	 */
	public int getJing_jong_jumin_tax() {
		return jing_jong_jumin_tax;
	}
	/**
	 * @param jing_jong_jumin_tax the jing_jong_jumin_tax to set
	 */
	public void setJing_jong_jumin_tax(int jing_jong_jumin_tax) {
		this.jing_jong_jumin_tax = jing_jong_jumin_tax;
	}
	/**
	 * @return the jing_jong_nong_tax
	 */
	public int getJing_jong_nong_tax() {
		return jing_jong_nong_tax;
	}
	/**
	 * @param jing_jong_nong_tax the jing_jong_nong_tax to set
	 */
	public void setJing_jong_nong_tax(int jing_jong_nong_tax) {
		this.jing_jong_nong_tax = jing_jong_nong_tax;
	}
	/**
	 * @return the foreign_cd
	 */
	public int getForeign_cd() {
		return foreign_cd;
	}
	/**
	 * @param foreign_cd the foreign_cd to set
	 */
	public void setForeign_cd(int foreign_cd) {
		this.foreign_cd = foreign_cd;
	}
	/**
	 * @return the seq
	 */
	public String getSeq() {
		return seq;
	}
	/**
	 * @param seq the seq to set
	 */
	public void setSeq(String seq) {
		this.seq = seq;
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
	 * @return the ent_uno
	 */
	public String getEnt_uno() {
		return ent_uno;
	}
	/**
	 * @param ent_uno the ent_uno to set
	 */
	public void setEnt_uno(String ent_uno) {
		this.ent_uno = ent_uno;
	}
	/**
	 * @return the upd_uno
	 */
	public String getUpd_uno() {
		return upd_uno;
	}
	/**
	 * @param upd_uno the upd_uno to set
	 */
	public void setUpd_uno(String upd_uno) {
		this.upd_uno = upd_uno;
	}
	/**
	 * @return the salary_dt
	 */
	public String getSalary_dt() {
		return salary_dt;
	}
	/**
	 * @param salary_dt the salary_dt to set
	 */
	public void setSalary_dt(String salary_dt) {
		this.salary_dt = salary_dt;
	}
	/**
	 * @return the reduction_amt
	 */
	public int getReduction_amt() {
		return reduction_amt;
	}
	/**
	 * @param reduction_amt the reduction_amt to set
	 */
	public void setReduction_amt(int reduction_amt) {
		this.reduction_amt = reduction_amt;
	}
	/**
	 * @return the health_amt
	 */
	public int getHealth_amt() {
		return health_amt;
	}
	/**
	 * @param health_amt the health_amt to set
	 */
	public void setHealth_amt(int health_amt) {
		this.health_amt = health_amt;
	}
	/**
	 * @return the employ_amt
	 */
	public int getEmploy_amt() {
		return employ_amt;
	}
	/**
	 * @param employ_amt the employ_amt to set
	 */
	public void setEmploy_amt(int employ_amt) {
		this.employ_amt = employ_amt;
	}
	/**
	 * @return the annuity_amt
	 */
	public int getAnnuity_amt() {
		return annuity_amt;
	}
	/**
	 * @param annuity_amt the annuity_amt to set
	 */
	public void setAnnuity_amt(int annuity_amt) {
		this.annuity_amt = annuity_amt;
	}
	/**
	 * @return the income_amt
	 */
	public int getIncome_amt() {
		return income_amt;
	}
	/**
	 * @param income_amt the income_amt to set
	 */
	public void setIncome_amt(int income_amt) {
		this.income_amt = income_amt;
	}
	/**
	 * @return the jumin_amt
	 */
	public int getJumin_amt() {
		return jumin_amt;
	}
	/**
	 * @param jumin_amt the jumin_amt to set
	 */
	public void setJumin_amt(int jumin_amt) {
		this.jumin_amt = jumin_amt;
	}
	/**
	 * @return the nong_amt
	 */
	public int getNong_amt() {
		return nong_amt;
	}
	/**
	 * @param nong_amt the nong_amt to set
	 */
	public void setNong_amt(int nong_amt) {
		this.nong_amt = nong_amt;
	}
	/**
	 * @return the smiymjtc_rate
	 */
	public String getSmiymjtc_rate() {
		return smiymjtc_rate;
	}
	/**
	 * @param smiymjtc_rate the smiymjtc_rate to set
	 */
	public void setSmiymjtc_rate(String smiymjtc_rate) {
		this.smiymjtc_rate = smiymjtc_rate;
	}
	/**
	 * @return the rel_cd
	 */
	public String getRel_cd() {
		return rel_cd;
	}
	/**
	 * @param rel_cd the rel_cd to set
	 */
	public void setRel_cd(String rel_cd) {
		this.rel_cd = rel_cd;
	}
	/**
	 * @return the rel_jumin_no
	 */
	public String getRel_jumin_no() {
		return rel_jumin_no;
	}
	/**
	 * @param rel_jumin_no the rel_jumin_no to set
	 */
	public void setRel_jumin_no(String rel_jumin_no) {
		this.rel_jumin_no = rel_jumin_no;
	}
	/**
	 * @return the rel_nm
	 */
	public String getRel_nm() {
		return rel_nm;
	}
	/**
	 * @param rel_nm the rel_nm to set
	 */
	public void setRel_nm(String rel_nm) {
		this.rel_nm = rel_nm;
	}
	/**
	 * @return the choose_yn
	 */
	public String getChoose_yn() {
		return choose_yn;
	}
	/**
	 * @param choose_yn the choose_yn to set
	 */
	public void setChoose_yn(String choose_yn) {
		this.choose_yn = choose_yn;
	}
	/**
	 * @return the pensioner_yn
	 */
	public String getPensioner_yn() {
		return pensioner_yn;
	}
	/**
	 * @param pensioner_yn the pensioner_yn to set
	 */
	public void setPensioner_yn(String pensioner_yn) {
		this.pensioner_yn = pensioner_yn;
	}
	/**
	 * @return the foster_child_yn
	 */
	public String getFoster_child_yn() {
		return foster_child_yn;
	}
	/**
	 * @param foster_child_yn the foster_child_yn to set
	 */
	public void setFoster_child_yn(String foster_child_yn) {
		this.foster_child_yn = foster_child_yn;
	}
	/**
	 * @return the respect_aged_yn
	 */
	public String getRespect_aged_yn() {
		return respect_aged_yn;
	}
	/**
	 * @param respect_aged_yn the respect_aged_yn to set
	 */
	public void setRespect_aged_yn(String respect_aged_yn) {
		this.respect_aged_yn = respect_aged_yn;
	}
	/**
	 * @return the disabled_person
	 */
	public String getDisabled_person() {
		return disabled_person;
	}
	/**
	 * @param disabled_person the disabled_person to set
	 */
	public void setDisabled_person(String disabled_person) {
		this.disabled_person = disabled_person;
	}
	/**
	 * @return the woman_yn
	 */
	public String getWoman_yn() {
		return woman_yn;
	}
	/**
	 * @param woman_yn the woman_yn to set
	 */
	public void setWoman_yn(String woman_yn) {
		this.woman_yn = woman_yn;
	}
	/**
	 * @return the sixyear_yn
	 */
	public String getSixyear_yn() {
		return sixyear_yn;
	}
	/**
	 * @param sixyear_yn the sixyear_yn to set
	 */
	public void setSixyear_yn(String sixyear_yn) {
		this.sixyear_yn = sixyear_yn;
	}
	/**
	 * @return the birth_yn
	 */
	public String getBirth_yn() {
		return birth_yn;
	}
	/**
	 * @param birth_yn the birth_yn to set
	 */
	public void setBirth_yn(String birth_yn) {
		this.birth_yn = birth_yn;
	}
	/**
	 * @return the insulance_yn
	 */
	public String getInsulance_yn() {
		return insulance_yn;
	}
	/**
	 * @param insulance_yn the insulance_yn to set
	 */
	public void setInsulance_yn(String insulance_yn) {
		this.insulance_yn = insulance_yn;
	}
	/**
	 * @return the medical_yn
	 */
	public String getMedical_yn() {
		return medical_yn;
	}
	/**
	 * @param medical_yn the medical_yn to set
	 */
	public void setMedical_yn(String medical_yn) {
		this.medical_yn = medical_yn;
	}
	/**
	 * @return the education_yn
	 */
	public String getEducation_yn() {
		return education_yn;
	}
	/**
	 * @param education_yn the education_yn to set
	 */
	public void setEducation_yn(String education_yn) {
		this.education_yn = education_yn;
	}
	/**
	 * @return the card_yn
	 */
	public String getCard_yn() {
		return card_yn;
	}
	/**
	 * @param card_yn the card_yn to set
	 */
	public void setCard_yn(String card_yn) {
		this.card_yn = card_yn;
	}
	/**
	 * @return the contribution_yn
	 */
	public String getContribution_yn() {
		return contribution_yn;
	}
	/**
	 * @param contribution_yn the contribution_yn to set
	 */
	public void setContribution_yn(String contribution_yn) {
		this.contribution_yn = contribution_yn;
	}
	/**
	 * @return the single_parents_yn
	 */
	public String getSingle_parents_yn() {
		return single_parents_yn;
	}
	/**
	 * @param single_parents_yn the single_parents_yn to set
	 */
	public void setSingle_parents_yn(String single_parents_yn) {
		this.single_parents_yn = single_parents_yn;
	}
	/**
	 * @return the house_gb
	 */
	public String getHouse_gb() {
		return house_gb;
	}
	/**
	 * @param house_gb the house_gb to set
	 */
	public void setHouse_gb(String house_gb) {
		this.house_gb = house_gb;
	}
	/**
	 * @return the house_nm
	 */
	public String getHouse_nm() {
		return house_nm;
	}
	/**
	 * @param house_nm the house_nm to set
	 */
	public void setHouse_nm(String house_nm) {
		this.house_nm = house_nm;
	}
	/**
	 * @return the house_jumin
	 */
	public String getHouse_jumin() {
		return house_jumin;
	}
	/**
	 * @param house_jumin the house_jumin to set
	 */
	public void setHouse_jumin(String house_jumin) {
		this.house_jumin = house_jumin;
	}
	/**
	 * @return the house_start_dt
	 */
	public String getHouse_start_dt() {
		return house_start_dt;
	}
	/**
	 * @param house_start_dt the house_start_dt to set
	 */
	public void setHouse_start_dt(String house_start_dt) {
		this.house_start_dt = house_start_dt;
	}
	/**
	 * @return the house_end_dt
	 */
	public String getHouse_end_dt() {
		return house_end_dt;
	}
	/**
	 * @param house_end_dt the house_end_dt to set
	 */
	public void setHouse_end_dt(String house_end_dt) {
		this.house_end_dt = house_end_dt;
	}
	/**
	 * @return the house_addr
	 */
	public String getHouse_addr() {
		return house_addr;
	}
	/**
	 * @param house_addr the house_addr to set
	 */
	public void setHouse_addr(String house_addr) {
		this.house_addr = house_addr;
	}
	/**
	 * @return the house_amt1
	 */
	public int getHouse_amt1() {
		return house_amt1;
	}
	/**
	 * @param house_amt1 the house_amt1 to set
	 */
	public void setHouse_amt1(int house_amt1) {
		this.house_amt1 = house_amt1;
	}
	/**
	 * @return the house_amt2
	 */
	public int getHouse_amt2() {
		return house_amt2;
	}
	/**
	 * @param house_amt2 the house_amt2 to set
	 */
	public void setHouse_amt2(int house_amt2) {
		this.house_amt2 = house_amt2;
	}
	/**
	 * @return the house_amt3
	 */
	public int getHouse_amt3() {
		return house_amt3;
	}
	/**
	 * @param house_amt3 the house_amt3 to set
	 */
	public void setHouse_amt3(int house_amt3) {
		this.house_amt3 = house_amt3;
	}
	/**
	 * @return the house_amt4
	 */
	public int getHouse_amt4() {
		return house_amt4;
	}
	/**
	 * @param house_amt4 the house_amt4 to set
	 */
	public void setHouse_amt4(int house_amt4) {
		this.house_amt4 = house_amt4;
	}
	/**
	 * @return the house_amt5
	 */
	public int getHouse_amt5() {
		return house_amt5;
	}
	/**
	 * @param house_amt5 the house_amt5 to set
	 */
	public void setHouse_amt5(int house_amt5) {
		this.house_amt5 = house_amt5;
	}
	/**
	 * @return the retir_dt
	 */
	public String getRetir_dt() {
		return retir_dt;
	}
	/**
	 * @param retir_dt the retir_dt to set
	 */
	public void setRetir_dt(String retir_dt) {
		this.retir_dt = retir_dt;
	}
	/**
	 * @return the credit_1
	 */
	public int getCredit_1() {
		return credit_1;
	}
	/**
	 * @param credit_1 the credit_1 to set
	 */
	public void setCredit_1(int credit_1) {
		this.credit_1 = credit_1;
	}
	/**
	 * @return the credit_2
	 */
	public int getCredit_2() {
		return credit_2;
	}
	/**
	 * @param credit_2 the credit_2 to set
	 */
	public void setCredit_2(int credit_2) {
		this.credit_2 = credit_2;
	}
	/**
	 * @return the cash_1
	 */
	public int getCash_1() {
		return cash_1;
	}
	/**
	 * @param cash_1 the cash_1 to set
	 */
	public void setCash_1(int cash_1) {
		this.cash_1 = cash_1;
	}
	/**
	 * @return the cash_2
	 */
	public int getCash_2() {
		return cash_2;
	}
	/**
	 * @param cash_2 the cash_2 to set
	 */
	public void setCash_2(int cash_2) {
		this.cash_2 = cash_2;
	}
	/**
	 * @return the direct_1
	 */
	public int getDirect_1() {
		return direct_1;
	}
	/**
	 * @param direct_1 the direct_1 to set
	 */
	public void setDirect_1(int direct_1) {
		this.direct_1 = direct_1;
	}
	/**
	 * @return the direct_2
	 */
	public int getDirect_2() {
		return direct_2;
	}
	/**
	 * @param direct_2 the direct_2 to set
	 */
	public void setDirect_2(int direct_2) {
		this.direct_2 = direct_2;
	}
	/**
	 * @return the market_1
	 */
	public int getMarket_1() {
		return market_1;
	}
	/**
	 * @param market_1 the market_1 to set
	 */
	public void setMarket_1(int market_1) {
		this.market_1 = market_1;
	}
	/**
	 * @return the market_2
	 */
	public int getMarket_2() {
		return market_2;
	}
	/**
	 * @param market_2 the market_2 to set
	 */
	public void setMarket_2(int market_2) {
		this.market_2 = market_2;
	}
	/**
	 * @return the pubric_transport_1
	 */
	public int getPubric_transport_1() {
		return pubric_transport_1;
	}
	/**
	 * @param pubric_transport_1 the pubric_transport_1 to set
	 */
	public void setPubric_transport_1(int pubric_transport_1) {
		this.pubric_transport_1 = pubric_transport_1;
	}
	/**
	 * @return the pubric_transport_2
	 */
	public int getPubric_transport_2() {
		return pubric_transport_2;
	}
	/**
	 * @param pubric_transport_2 the pubric_transport_2 to set
	 */
	public void setPubric_transport_2(int pubric_transport_2) {
		this.pubric_transport_2 = pubric_transport_2;
	}
	/**
	 * @return the credit_1_ly
	 */
	public int getCredit_1_ly() {
		return credit_1_ly;
	}
	/**
	 * @param credit_1_ly the credit_1_ly to set
	 */
	public void setCredit_1_ly(int credit_1_ly) {
		this.credit_1_ly = credit_1_ly;
	}
	/**
	 * @return the credit_2_ly
	 */
	public int getCredit_2_ly() {
		return credit_2_ly;
	}
	/**
	 * @param credit_2_ly the credit_2_ly to set
	 */
	public void setCredit_2_ly(int credit_2_ly) {
		this.credit_2_ly = credit_2_ly;
	}
	/**
	 * @return the cash_1_ly
	 */
	public int getCash_1_ly() {
		return cash_1_ly;
	}
	/**
	 * @param cash_1_ly the cash_1_ly to set
	 */
	public void setCash_1_ly(int cash_1_ly) {
		this.cash_1_ly = cash_1_ly;
	}
	/**
	 * @return the cash_2_ly
	 */
	public int getCash_2_ly() {
		return cash_2_ly;
	}
	/**
	 * @param cash_2_ly the cash_2_ly to set
	 */
	public void setCash_2_ly(int cash_2_ly) {
		this.cash_2_ly = cash_2_ly;
	}
	/**
	 * @return the direct_1_ly
	 */
	public int getDirect_1_ly() {
		return direct_1_ly;
	}
	/**
	 * @param direct_1_ly the direct_1_ly to set
	 */
	public void setDirect_1_ly(int direct_1_ly) {
		this.direct_1_ly = direct_1_ly;
	}
	/**
	 * @return the direct_2_ly
	 */
	public int getDirect_2_ly() {
		return direct_2_ly;
	}
	/**
	 * @param direct_2_ly the direct_2_ly to set
	 */
	public void setDirect_2_ly(int direct_2_ly) {
		this.direct_2_ly = direct_2_ly;
	}
	/**
	 * @return the market_1_ly
	 */
	public int getMarket_1_ly() {
		return market_1_ly;
	}
	/**
	 * @param market_1_ly the market_1_ly to set
	 */
	public void setMarket_1_ly(int market_1_ly) {
		this.market_1_ly = market_1_ly;
	}
	/**
	 * @return the market_2_ly
	 */
	public int getMarket_2_ly() {
		return market_2_ly;
	}
	/**
	 * @param market_2_ly the market_2_ly to set
	 */
	public void setMarket_2_ly(int market_2_ly) {
		this.market_2_ly = market_2_ly;
	}
	/**
	 * @return the pubric_transport_1_ly
	 */
	public int getPubric_transport_1_ly() {
		return pubric_transport_1_ly;
	}
	/**
	 * @param pubric_transport_1_ly the pubric_transport_1_ly to set
	 */
	public void setPubric_transport_1_ly(int pubric_transport_1_ly) {
		this.pubric_transport_1_ly = pubric_transport_1_ly;
	}
	/**
	 * @return the pubric_transport_2_ly
	 */
	public int getPubric_transport_2_ly() {
		return pubric_transport_2_ly;
	}
	/**
	 * @param pubric_transport_2_ly the pubric_transport_2_ly to set
	 */
	public void setPubric_transport_2_ly(int pubric_transport_2_ly) {
		this.pubric_transport_2_ly = pubric_transport_2_ly;
	}
	/**
	 * @return the credit_1_sh
	 */
	public int getCredit_1_sh() {
		return credit_1_sh;
	}
	/**
	 * @param credit_1_sh the credit_1_sh to set
	 */
	public void setCredit_1_sh(int credit_1_sh) {
		this.credit_1_sh = credit_1_sh;
	}
	/**
	 * @return the credit_2_sh
	 */
	public int getCredit_2_sh() {
		return credit_2_sh;
	}
	/**
	 * @param credit_2_sh the credit_2_sh to set
	 */
	public void setCredit_2_sh(int credit_2_sh) {
		this.credit_2_sh = credit_2_sh;
	}
	/**
	 * @return the cash_1_sh
	 */
	public int getCash_1_sh() {
		return cash_1_sh;
	}
	/**
	 * @param cash_1_sh the cash_1_sh to set
	 */
	public void setCash_1_sh(int cash_1_sh) {
		this.cash_1_sh = cash_1_sh;
	}
	/**
	 * @return the cash_2_sh
	 */
	public int getCash_2_sh() {
		return cash_2_sh;
	}
	/**
	 * @param cash_2_sh the cash_2_sh to set
	 */
	public void setCash_2_sh(int cash_2_sh) {
		this.cash_2_sh = cash_2_sh;
	}
	/**
	 * @return the direct_1_sh
	 */
	public int getDirect_1_sh() {
		return direct_1_sh;
	}
	/**
	 * @param direct_1_sh the direct_1_sh to set
	 */
	public void setDirect_1_sh(int direct_1_sh) {
		this.direct_1_sh = direct_1_sh;
	}
	/**
	 * @return the direct_2_sh
	 */
	public int getDirect_2_sh() {
		return direct_2_sh;
	}
	/**
	 * @param direct_2_sh the direct_2_sh to set
	 */
	public void setDirect_2_sh(int direct_2_sh) {
		this.direct_2_sh = direct_2_sh;
	}
	/**
	 * @return the market_1_sh
	 */
	public int getMarket_1_sh() {
		return market_1_sh;
	}
	/**
	 * @param market_1_sh the market_1_sh to set
	 */
	public void setMarket_1_sh(int market_1_sh) {
		this.market_1_sh = market_1_sh;
	}
	/**
	 * @return the market_2_sh
	 */
	public int getMarket_2_sh() {
		return market_2_sh;
	}
	/**
	 * @param market_2_sh the market_2_sh to set
	 */
	public void setMarket_2_sh(int market_2_sh) {
		this.market_2_sh = market_2_sh;
	}
	/**
	 * @return the pubric_transport_1_sh
	 */
	public int getPubric_transport_1_sh() {
		return pubric_transport_1_sh;
	}
	/**
	 * @param pubric_transport_1_sh the pubric_transport_1_sh to set
	 */
	public void setPubric_transport_1_sh(int pubric_transport_1_sh) {
		this.pubric_transport_1_sh = pubric_transport_1_sh;
	}
	/**
	 * @return the pubric_transport_2_sh
	 */
	public int getPubric_transport_2_sh() {
		return pubric_transport_2_sh;
	}
	/**
	 * @param pubric_transport_2_sh the pubric_transport_2_sh to set
	 */
	public void setPubric_transport_2_sh(int pubric_transport_2_sh) {
		this.pubric_transport_2_sh = pubric_transport_2_sh;
	}
	/**
	 * @return the yt_salary
	 */
	public int getYt_salary() {
		return yt_salary;
	}
	/**
	 * @param yt_salary the yt_salary to set
	 */
	public void setYt_salary(int yt_salary) {
		this.yt_salary = yt_salary;
	}
	/**
	 * @return the yt_work_deduct_amt
	 */
	public int getYt_work_deduct_amt() {
		return yt_work_deduct_amt;
	}
	/**
	 * @param yt_work_deduct_amt the yt_work_deduct_amt to set
	 */
	public void setYt_work_deduct_amt(int yt_work_deduct_amt) {
		this.yt_work_deduct_amt = yt_work_deduct_amt;
	}
	/**
	 * @return the yt_work_income_amt
	 */
	public int getYt_work_income_amt() {
		return yt_work_income_amt;
	}
	/**
	 * @param yt_work_income_amt the yt_work_income_amt to set
	 */
	public void setYt_work_income_amt(int yt_work_income_amt) {
		this.yt_work_income_amt = yt_work_income_amt;
	}
	/**
	 * @return the family_person_amt
	 */
	public int getFamily_person_amt() {
		return family_person_amt;
	}
	/**
	 * @param family_person_amt the family_person_amt to set
	 */
	public void setFamily_person_amt(int family_person_amt) {
		this.family_person_amt = family_person_amt;
	}
	/**
	 * @return the family_mate_amt
	 */
	public int getFamily_mate_amt() {
		return family_mate_amt;
	}
	/**
	 * @param family_mate_amt the family_mate_amt to set
	 */
	public void setFamily_mate_amt(int family_mate_amt) {
		this.family_mate_amt = family_mate_amt;
	}
	/**
	 * @return the family_dependent_amt
	 */
	public int getFamily_dependent_amt() {
		return family_dependent_amt;
	}
	/**
	 * @param family_dependent_amt the family_dependent_amt to set
	 */
	public void setFamily_dependent_amt(int family_dependent_amt) {
		this.family_dependent_amt = family_dependent_amt;
	}
	/**
	 * @return the family_dependent_cnt
	 */
	public int getFamily_dependent_cnt() {
		return family_dependent_cnt;
	}
	/**
	 * @param family_dependent_cnt the family_dependent_cnt to set
	 */
	public void setFamily_dependent_cnt(int family_dependent_cnt) {
		this.family_dependent_cnt = family_dependent_cnt;
	}
	/**
	 * @return the family_old_amt
	 */
	public int getFamily_old_amt() {
		return family_old_amt;
	}
	/**
	 * @param family_old_amt the family_old_amt to set
	 */
	public void setFamily_old_amt(int family_old_amt) {
		this.family_old_amt = family_old_amt;
	}
	/**
	 * @return the family_old2_cnt
	 */
	public int getFamily_old2_cnt() {
		return family_old2_cnt;
	}
	/**
	 * @param family_old2_cnt the family_old2_cnt to set
	 */
	public void setFamily_old2_cnt(int family_old2_cnt) {
		this.family_old2_cnt = family_old2_cnt;
	}
	/**
	 * @return the family_disabled_person_amt
	 */
	public int getFamily_disabled_person_amt() {
		return family_disabled_person_amt;
	}
	/**
	 * @param family_disabled_person_amt the family_disabled_person_amt to set
	 */
	public void setFamily_disabled_person_amt(int family_disabled_person_amt) {
		this.family_disabled_person_amt = family_disabled_person_amt;
	}
	/**
	 * @return the family_disabled_person_cnt
	 */
	public int getFamily_disabled_person_cnt() {
		return family_disabled_person_cnt;
	}
	/**
	 * @param family_disabled_person_cnt the family_disabled_person_cnt to set
	 */
	public void setFamily_disabled_person_cnt(int family_disabled_person_cnt) {
		this.family_disabled_person_cnt = family_disabled_person_cnt;
	}
	/**
	 * @return the family_women_amt
	 */
	public int getFamily_women_amt() {
		return family_women_amt;
	}
	/**
	 * @param family_women_amt the family_women_amt to set
	 */
	public void setFamily_women_amt(int family_women_amt) {
		this.family_women_amt = family_women_amt;
	}
	/**
	 * @return the family_single_parent_amt
	 */
	public int getFamily_single_parent_amt() {
		return family_single_parent_amt;
	}
	/**
	 * @param family_single_parent_amt the family_single_parent_amt to set
	 */
	public void setFamily_single_parent_amt(int family_single_parent_amt) {
		this.family_single_parent_amt = family_single_parent_amt;
	}
	/**
	 * @return the total_family_amt
	 */
	public int getTotal_family_amt() {
		return total_family_amt;
	}
	/**
	 * @param total_family_amt the total_family_amt to set
	 */
	public void setTotal_family_amt(int total_family_amt) {
		this.total_family_amt = total_family_amt;
	}
	/**
	 * @return the house_lease_loan_amt
	 */
	public int getHouse_lease_loan_amt() {
		return house_lease_loan_amt;
	}
	/**
	 * @param house_lease_loan_amt the house_lease_loan_amt to set
	 */
	public void setHouse_lease_loan_amt(int house_lease_loan_amt) {
		this.house_lease_loan_amt = house_lease_loan_amt;
	}
	/**
	 * @return the house_security_loan1_amt
	 */
	public int getHouse_security_loan1_amt() {
		return house_security_loan1_amt;
	}
	/**
	 * @param house_security_loan1_amt the house_security_loan1_amt to set
	 */
	public void setHouse_security_loan1_amt(int house_security_loan1_amt) {
		this.house_security_loan1_amt = house_security_loan1_amt;
	}
	/**
	 * @return the house_security_loan2_amt
	 */
	public int getHouse_security_loan2_amt() {
		return house_security_loan2_amt;
	}
	/**
	 * @param house_security_loan2_amt the house_security_loan2_amt to set
	 */
	public void setHouse_security_loan2_amt(int house_security_loan2_amt) {
		this.house_security_loan2_amt = house_security_loan2_amt;
	}
	/**
	 * @return the house_security_loan3_amt
	 */
	public int getHouse_security_loan3_amt() {
		return house_security_loan3_amt;
	}
	/**
	 * @param house_security_loan3_amt the house_security_loan3_amt to set
	 */
	public void setHouse_security_loan3_amt(int house_security_loan3_amt) {
		this.house_security_loan3_amt = house_security_loan3_amt;
	}
	/**
	 * @return the house_security_loan4_amt
	 */
	public int getHouse_security_loan4_amt() {
		return house_security_loan4_amt;
	}
	/**
	 * @param house_security_loan4_amt the house_security_loan4_amt to set
	 */
	public void setHouse_security_loan4_amt(int house_security_loan4_amt) {
		this.house_security_loan4_amt = house_security_loan4_amt;
	}
	/**
	 * @return the house_security_loan5_amt
	 */
	public int getHouse_security_loan5_amt() {
		return house_security_loan5_amt;
	}
	/**
	 * @param house_security_loan5_amt the house_security_loan5_amt to set
	 */
	public void setHouse_security_loan5_amt(int house_security_loan5_amt) {
		this.house_security_loan5_amt = house_security_loan5_amt;
	}
	/**
	 * @return the total_house_amt
	 */
	public int getTotal_house_amt() {
		return total_house_amt;
	}
	/**
	 * @param total_house_amt the total_house_amt to set
	 */
	public void setTotal_house_amt(int total_house_amt) {
		this.total_house_amt = total_house_amt;
	}
	/**
	 * @return the yt_special_deduct_amt
	 */
	public int getYt_special_deduct_amt() {
		return yt_special_deduct_amt;
	}
	/**
	 * @param yt_special_deduct_amt the yt_special_deduct_amt to set
	 */
	public void setYt_special_deduct_amt(int yt_special_deduct_amt) {
		this.yt_special_deduct_amt = yt_special_deduct_amt;
	}
	/**
	 * @return the yt_deduction_amt
	 */
	public int getYt_deduction_amt() {
		return yt_deduction_amt;
	}
	/**
	 * @param yt_deduction_amt the yt_deduction_amt to set
	 */
	public void setYt_deduction_amt(int yt_deduction_amt) {
		this.yt_deduction_amt = yt_deduction_amt;
	}
	/**
	 * @return the personal_annuity_amt
	 */
	public int getPersonal_annuity_amt() {
		return personal_annuity_amt;
	}
	/**
	 * @param personal_annuity_amt the personal_annuity_amt to set
	 */
	public void setPersonal_annuity_amt(int personal_annuity_amt) {
		this.personal_annuity_amt = personal_annuity_amt;
	}
	/**
	 * @return the etc_subscription_deposit_amt
	 */
	public int getEtc_subscription_deposit_amt() {
		return etc_subscription_deposit_amt;
	}
	/**
	 * @param etc_subscription_deposit_amt the etc_subscription_deposit_amt to set
	 */
	public void setEtc_subscription_deposit_amt(int etc_subscription_deposit_amt) {
		this.etc_subscription_deposit_amt = etc_subscription_deposit_amt;
	}
	/**
	 * @return the etc_home_mortgage_amt
	 */
	public int getEtc_home_mortgage_amt() {
		return etc_home_mortgage_amt;
	}
	/**
	 * @param etc_home_mortgage_amt the etc_home_mortgage_amt to set
	 */
	public void setEtc_home_mortgage_amt(int etc_home_mortgage_amt) {
		this.etc_home_mortgage_amt = etc_home_mortgage_amt;
	}
	/**
	 * @return the etc_house_subscr_deposit_amt
	 */
	public int getEtc_house_subscr_deposit_amt() {
		return etc_house_subscr_deposit_amt;
	}
	/**
	 * @param etc_house_subscr_deposit_amt the etc_house_subscr_deposit_amt to set
	 */
	public void setEtc_house_subscr_deposit_amt(int etc_house_subscr_deposit_amt) {
		this.etc_house_subscr_deposit_amt = etc_house_subscr_deposit_amt;
	}
	/**
	 * @return the etc_credt_amt
	 */
	public int getEtc_credt_amt() {
		return etc_credt_amt;
	}
	/**
	 * @param etc_credt_amt the etc_credt_amt to set
	 */
	public void setEtc_credt_amt(int etc_credt_amt) {
		this.etc_credt_amt = etc_credt_amt;
	}
	/**
	 * @return the etc_cash_receipt_amt
	 */
	public int getEtc_cash_receipt_amt() {
		return etc_cash_receipt_amt;
	}
	/**
	 * @param etc_cash_receipt_amt the etc_cash_receipt_amt to set
	 */
	public void setEtc_cash_receipt_amt(int etc_cash_receipt_amt) {
		this.etc_cash_receipt_amt = etc_cash_receipt_amt;
	}
	/**
	 * @return the etc_direct_amt
	 */
	public int getEtc_direct_amt() {
		return etc_direct_amt;
	}
	/**
	 * @param etc_direct_amt the etc_direct_amt to set
	 */
	public void setEtc_direct_amt(int etc_direct_amt) {
		this.etc_direct_amt = etc_direct_amt;
	}
	/**
	 * @return the etc_market_amt
	 */
	public int getEtc_market_amt() {
		return etc_market_amt;
	}
	/**
	 * @param etc_market_amt the etc_market_amt to set
	 */
	public void setEtc_market_amt(int etc_market_amt) {
		this.etc_market_amt = etc_market_amt;
	}
	/**
	 * @return the etc_pubric_transport_amt
	 */
	public int getEtc_pubric_transport_amt() {
		return etc_pubric_transport_amt;
	}
	/**
	 * @param etc_pubric_transport_amt the etc_pubric_transport_amt to set
	 */
	public void setEtc_pubric_transport_amt(int etc_pubric_transport_amt) {
		this.etc_pubric_transport_amt = etc_pubric_transport_amt;
	}
	/**
	 * @return the total_use_amt
	 */
	public int getTotal_use_amt() {
		return total_use_amt;
	}
	/**
	 * @param total_use_amt the total_use_amt to set
	 */
	public void setTotal_use_amt(int total_use_amt) {
		this.total_use_amt = total_use_amt;
	}
	/**
	 * @return the etc_employ_stock_amt
	 */
	public int getEtc_employ_stock_amt() {
		return etc_employ_stock_amt;
	}
	/**
	 * @param etc_employ_stock_amt the etc_employ_stock_amt to set
	 */
	public void setEtc_employ_stock_amt(int etc_employ_stock_amt) {
		this.etc_employ_stock_amt = etc_employ_stock_amt;
	}
	/**
	 * @return the etc_chunk_money_amt
	 */
	public int getEtc_chunk_money_amt() {
		return etc_chunk_money_amt;
	}
	/**
	 * @param etc_chunk_money_amt the etc_chunk_money_amt to set
	 */
	public void setEtc_chunk_money_amt(int etc_chunk_money_amt) {
		this.etc_chunk_money_amt = etc_chunk_money_amt;
	}
	/**
	 * @return the etc_long_invest_stock_amt
	 */
	public int getEtc_long_invest_stock_amt() {
		return etc_long_invest_stock_amt;
	}
	/**
	 * @param etc_long_invest_stock_amt the etc_long_invest_stock_amt to set
	 */
	public void setEtc_long_invest_stock_amt(int etc_long_invest_stock_amt) {
		this.etc_long_invest_stock_amt = etc_long_invest_stock_amt;
	}
	/**
	 * @return the total_etc_amt
	 */
	public int getTotal_etc_amt() {
		return total_etc_amt;
	}
	/**
	 * @param total_etc_amt the total_etc_amt to set
	 */
	public void setTotal_etc_amt(int total_etc_amt) {
		this.total_etc_amt = total_etc_amt;
	}
	/**
	 * @return the etc_exceed_limit_amt
	 */
	public int getEtc_exceed_limit_amt() {
		return etc_exceed_limit_amt;
	}
	/**
	 * @param etc_exceed_limit_amt the etc_exceed_limit_amt to set
	 */
	public void setEtc_exceed_limit_amt(int etc_exceed_limit_amt) {
		this.etc_exceed_limit_amt = etc_exceed_limit_amt;
	}
	/**
	 * @return the yt_income_tax_standard_amt
	 */
	public int getYt_income_tax_standard_amt() {
		return yt_income_tax_standard_amt;
	}
	/**
	 * @param yt_income_tax_standard_amt the yt_income_tax_standard_amt to set
	 */
	public void setYt_income_tax_standard_amt(int yt_income_tax_standard_amt) {
		this.yt_income_tax_standard_amt = yt_income_tax_standard_amt;
	}
	/**
	 * @return the yt_calculate_amt
	 */
	public int getYt_calculate_amt() {
		return yt_calculate_amt;
	}
	/**
	 * @param yt_calculate_amt the yt_calculate_amt to set
	 */
	public void setYt_calculate_amt(int yt_calculate_amt) {
		this.yt_calculate_amt = yt_calculate_amt;
	}
	/**
	 * @return the etc_income_tax_reduction_amt
	 */
	public int getEtc_income_tax_reduction_amt() {
		return etc_income_tax_reduction_amt;
	}
	/**
	 * @param etc_income_tax_reduction_amt the etc_income_tax_reduction_amt to set
	 */
	public void setEtc_income_tax_reduction_amt(int etc_income_tax_reduction_amt) {
		this.etc_income_tax_reduction_amt = etc_income_tax_reduction_amt;
	}
	/**
	 * @return the etc_smiymjtc_amt
	 */
	public int getEtc_smiymjtc_amt() {
		return etc_smiymjtc_amt;
	}
	/**
	 * @param etc_smiymjtc_amt the etc_smiymjtc_amt to set
	 */
	public void setEtc_smiymjtc_amt(int etc_smiymjtc_amt) {
		this.etc_smiymjtc_amt = etc_smiymjtc_amt;
	}
	/**
	 * @return the yt_earned_income_amt
	 */
	public int getYt_earned_income_amt() {
		return yt_earned_income_amt;
	}
	/**
	 * @param yt_earned_income_amt the yt_earned_income_amt to set
	 */
	public void setYt_earned_income_amt(int yt_earned_income_amt) {
		this.yt_earned_income_amt = yt_earned_income_amt;
	}
	/**
	 * @return the etc_children_amt
	 */
	public int getEtc_children_amt() {
		return etc_children_amt;
	}
	/**
	 * @param etc_children_amt the etc_children_amt to set
	 */
	public void setEtc_children_amt(int etc_children_amt) {
		this.etc_children_amt = etc_children_amt;
	}
	/**
	 * @return the annunity_retire_amt_o
	 */
	public int getAnnunity_retire_amt_o() {
		return annunity_retire_amt_o;
	}
	/**
	 * @param annunity_retire_amt_o the annunity_retire_amt_o to set
	 */
	public void setAnnunity_retire_amt_o(int annunity_retire_amt_o) {
		this.annunity_retire_amt_o = annunity_retire_amt_o;
	}
	/**
	 * @return the annuity_retire_amt
	 */
	public int getAnnuity_retire_amt() {
		return annuity_retire_amt;
	}
	/**
	 * @param annuity_retire_amt the annuity_retire_amt to set
	 */
	public void setAnnuity_retire_amt(int annuity_retire_amt) {
		this.annuity_retire_amt = annuity_retire_amt;
	}
	/**
	 * @return the annuity_saving_amt_o
	 */
	public int getAnnuity_saving_amt_o() {
		return annuity_saving_amt_o;
	}
	/**
	 * @param annuity_saving_amt_o the annuity_saving_amt_o to set
	 */
	public void setAnnuity_saving_amt_o(int annuity_saving_amt_o) {
		this.annuity_saving_amt_o = annuity_saving_amt_o;
	}
	/**
	 * @return the annuity_savng_amt
	 */
	public int getAnnuity_savng_amt() {
		return annuity_savng_amt;
	}
	/**
	 * @param annuity_savng_amt the annuity_savng_amt to set
	 */
	public void setAnnuity_savng_amt(int annuity_savng_amt) {
		this.annuity_savng_amt = annuity_savng_amt;
	}
	/**
	 * @return the total_annuity_amt
	 */
	public int getTotal_annuity_amt() {
		return total_annuity_amt;
	}
	/**
	 * @param total_annuity_amt the total_annuity_amt to set
	 */
	public void setTotal_annuity_amt(int total_annuity_amt) {
		this.total_annuity_amt = total_annuity_amt;
	}
	/**
	 * @return the insurance_secrity_amt
	 */
	public int getInsurance_secrity_amt() {
		return insurance_secrity_amt;
	}
	/**
	 * @param insurance_secrity_amt the insurance_secrity_amt to set
	 */
	public void setInsurance_secrity_amt(int insurance_secrity_amt) {
		this.insurance_secrity_amt = insurance_secrity_amt;
	}
	/**
	 * @return the insurance_amt_o
	 */
	public int getInsurance_amt_o() {
		return insurance_amt_o;
	}
	/**
	 * @param insurance_amt_o the insurance_amt_o to set
	 */
	public void setInsurance_amt_o(int insurance_amt_o) {
		this.insurance_amt_o = insurance_amt_o;
	}
	/**
	 * @return the insurance_disabled_person_amt
	 */
	public int getInsurance_disabled_person_amt() {
		return insurance_disabled_person_amt;
	}
	/**
	 * @param insurance_disabled_person_amt the insurance_disabled_person_amt to set
	 */
	public void setInsurance_disabled_person_amt(int insurance_disabled_person_amt) {
		this.insurance_disabled_person_amt = insurance_disabled_person_amt;
	}
	/**
	 * @return the total_insurance_amt
	 */
	public int getTotal_insurance_amt() {
		return total_insurance_amt;
	}
	/**
	 * @param total_insurance_amt the total_insurance_amt to set
	 */
	public void setTotal_insurance_amt(int total_insurance_amt) {
		this.total_insurance_amt = total_insurance_amt;
	}
	/**
	 * @return the total_medical_amt
	 */
	public int getTotal_medical_amt() {
		return total_medical_amt;
	}
	/**
	 * @param total_medical_amt the total_medical_amt to set
	 */
	public void setTotal_medical_amt(int total_medical_amt) {
		this.total_medical_amt = total_medical_amt;
	}
	/**
	 * @return the medical_amt_o
	 */
	public int getMedical_amt_o() {
		return medical_amt_o;
	}
	/**
	 * @param medical_amt_o the medical_amt_o to set
	 */
	public void setMedical_amt_o(int medical_amt_o) {
		this.medical_amt_o = medical_amt_o;
	}
	/**
	 * @return the total_edcate_amt
	 */
	public int getTotal_edcate_amt() {
		return total_edcate_amt;
	}
	/**
	 * @param total_edcate_amt the total_edcate_amt to set
	 */
	public void setTotal_edcate_amt(int total_edcate_amt) {
		this.total_edcate_amt = total_edcate_amt;
	}
	/**
	 * @return the edcate_amt_o
	 */
	public int getEdcate_amt_o() {
		return edcate_amt_o;
	}
	/**
	 * @param edcate_amt_o the edcate_amt_o to set
	 */
	public void setEdcate_amt_o(int edcate_amt_o) {
		this.edcate_amt_o = edcate_amt_o;
	}
	/**
	 * @return the contribue_politic_amt
	 */
	public int getContribue_politic_amt() {
		return contribue_politic_amt;
	}
	/**
	 * @param contribue_politic_amt the contribue_politic_amt to set
	 */
	public void setContribue_politic_amt(int contribue_politic_amt) {
		this.contribue_politic_amt = contribue_politic_amt;
	}
	/**
	 * @return the contribue_politic_amt_o
	 */
	public int getContribue_politic_amt_o() {
		return contribue_politic_amt_o;
	}
	/**
	 * @param contribue_politic_amt_o the contribue_politic_amt_o to set
	 */
	public void setContribue_politic_amt_o(int contribue_politic_amt_o) {
		this.contribue_politic_amt_o = contribue_politic_amt_o;
	}
	/**
	 * @return the contribue_politic_over_amt
	 */
	public int getContribue_politic_over_amt() {
		return contribue_politic_over_amt;
	}
	/**
	 * @param contribue_politic_over_amt the contribue_politic_over_amt to set
	 */
	public void setContribue_politic_over_amt(int contribue_politic_over_amt) {
		this.contribue_politic_over_amt = contribue_politic_over_amt;
	}
	/**
	 * @return the contribue_politic_over_amt_o
	 */
	public int getContribue_politic_over_amt_o() {
		return contribue_politic_over_amt_o;
	}
	/**
	 * @param contribue_politic_over_amt_o the contribue_politic_over_amt_o to set
	 */
	public void setContribue_politic_over_amt_o(int contribue_politic_over_amt_o) {
		this.contribue_politic_over_amt_o = contribue_politic_over_amt_o;
	}
	/**
	 * @return the contribue_law_amt
	 */
	public int getContribue_law_amt() {
		return contribue_law_amt;
	}
	/**
	 * @param contribue_law_amt the contribue_law_amt to set
	 */
	public void setContribue_law_amt(int contribue_law_amt) {
		this.contribue_law_amt = contribue_law_amt;
	}
	/**
	 * @return the contribue_law_amt_o
	 */
	public int getContribue_law_amt_o() {
		return contribue_law_amt_o;
	}
	/**
	 * @param contribue_law_amt_o the contribue_law_amt_o to set
	 */
	public void setContribue_law_amt_o(int contribue_law_amt_o) {
		this.contribue_law_amt_o = contribue_law_amt_o;
	}
	/**
	 * @return the contribute_exception_amt
	 */
	public int getContribute_exception_amt() {
		return contribute_exception_amt;
	}
	/**
	 * @param contribute_exception_amt the contribute_exception_amt to set
	 */
	public void setContribute_exception_amt(int contribute_exception_amt) {
		this.contribute_exception_amt = contribute_exception_amt;
	}
	/**
	 * @return the contribute_trust_amt
	 */
	public int getContribute_trust_amt() {
		return contribute_trust_amt;
	}
	/**
	 * @param contribute_trust_amt the contribute_trust_amt to set
	 */
	public void setContribute_trust_amt(int contribute_trust_amt) {
		this.contribute_trust_amt = contribute_trust_amt;
	}
	/**
	 * @return the contribue_employ_stock_amt
	 */
	public int getContribue_employ_stock_amt() {
		return contribue_employ_stock_amt;
	}
	/**
	 * @param contribue_employ_stock_amt the contribue_employ_stock_amt to set
	 */
	public void setContribue_employ_stock_amt(int contribue_employ_stock_amt) {
		this.contribue_employ_stock_amt = contribue_employ_stock_amt;
	}
	/**
	 * @return the contribue_designate_amt
	 */
	public int getContribue_designate_amt() {
		return contribue_designate_amt;
	}
	/**
	 * @param contribue_designate_amt the contribue_designate_amt to set
	 */
	public void setContribue_designate_amt(int contribue_designate_amt) {
		this.contribue_designate_amt = contribue_designate_amt;
	}
	/**
	 * @return the contribue_designate_amt_o
	 */
	public int getContribue_designate_amt_o() {
		return contribue_designate_amt_o;
	}
	/**
	 * @param contribue_designate_amt_o the contribue_designate_amt_o to set
	 */
	public void setContribue_designate_amt_o(int contribue_designate_amt_o) {
		this.contribue_designate_amt_o = contribue_designate_amt_o;
	}
	/**
	 * @return the contribue_religion_amt
	 */
	public int getContribue_religion_amt() {
		return contribue_religion_amt;
	}
	/**
	 * @param contribue_religion_amt the contribue_religion_amt to set
	 */
	public void setContribue_religion_amt(int contribue_religion_amt) {
		this.contribue_religion_amt = contribue_religion_amt;
	}
	/**
	 * @return the total_contribue_amt
	 */
	public int getTotal_contribue_amt() {
		return total_contribue_amt;
	}
	/**
	 * @param total_contribue_amt the total_contribue_amt to set
	 */
	public void setTotal_contribue_amt(int total_contribue_amt) {
		this.total_contribue_amt = total_contribue_amt;
	}
	/**
	 * @return the yt_special_tax_deduct_amt
	 */
	public int getYt_special_tax_deduct_amt() {
		return yt_special_tax_deduct_amt;
	}
	/**
	 * @param yt_special_tax_deduct_amt the yt_special_tax_deduct_amt to set
	 */
	public void setYt_special_tax_deduct_amt(int yt_special_tax_deduct_amt) {
		this.yt_special_tax_deduct_amt = yt_special_tax_deduct_amt;
	}
	/**
	 * @return the yt_standard_deduct_amt
	 */
	public int getYt_standard_deduct_amt() {
		return yt_standard_deduct_amt;
	}
	/**
	 * @param yt_standard_deduct_amt the yt_standard_deduct_amt to set
	 */
	public void setYt_standard_deduct_amt(int yt_standard_deduct_amt) {
		this.yt_standard_deduct_amt = yt_standard_deduct_amt;
	}
	/**
	 * @return the etc_sework_tx_amt
	 */
	public int getEtc_sework_tx_amt() {
		return etc_sework_tx_amt;
	}
	/**
	 * @param etc_sework_tx_amt the etc_sework_tx_amt to set
	 */
	public void setEtc_sework_tx_amt(int etc_sework_tx_amt) {
		this.etc_sework_tx_amt = etc_sework_tx_amt;
	}
	/**
	 * @return the etc_house_laon_interest_amt
	 */
	public int getEtc_house_laon_interest_amt() {
		return etc_house_laon_interest_amt;
	}
	/**
	 * @param etc_house_laon_interest_amt the etc_house_laon_interest_amt to set
	 */
	public void setEtc_house_laon_interest_amt(int etc_house_laon_interest_amt) {
		this.etc_house_laon_interest_amt = etc_house_laon_interest_amt;
	}
	/**
	 * @return the etc_contribue_politic_amt
	 */
	public int getEtc_contribue_politic_amt() {
		return etc_contribue_politic_amt;
	}
	/**
	 * @param etc_contribue_politic_amt the etc_contribue_politic_amt to set
	 */
	public void setEtc_contribue_politic_amt(int etc_contribue_politic_amt) {
		this.etc_contribue_politic_amt = etc_contribue_politic_amt;
	}
	/**
	 * @return the etc_foreigner_income
	 */
	public int getEtc_foreigner_income() {
		return etc_foreigner_income;
	}
	/**
	 * @param etc_foreigner_income the etc_foreigner_income to set
	 */
	public void setEtc_foreigner_income(int etc_foreigner_income) {
		this.etc_foreigner_income = etc_foreigner_income;
	}
	/**
	 * @return the etc_foreigner_pay
	 */
	public int getEtc_foreigner_pay() {
		return etc_foreigner_pay;
	}
	/**
	 * @param etc_foreigner_pay the etc_foreigner_pay to set
	 */
	public void setEtc_foreigner_pay(int etc_foreigner_pay) {
		this.etc_foreigner_pay = etc_foreigner_pay;
	}
	/**
	 * @return the house_month_lent_amt
	 */
	public int getHouse_month_lent_amt() {
		return house_month_lent_amt;
	}
	/**
	 * @param house_month_lent_amt the house_month_lent_amt to set
	 */
	public void setHouse_month_lent_amt(int house_month_lent_amt) {
		this.house_month_lent_amt = house_month_lent_amt;
	}
	/**
	 * @return the yt_tax_deduct_amt
	 */
	public int getYt_tax_deduct_amt() {
		return yt_tax_deduct_amt;
	}
	/**
	 * @param yt_tax_deduct_amt the yt_tax_deduct_amt to set
	 */
	public void setYt_tax_deduct_amt(int yt_tax_deduct_amt) {
		this.yt_tax_deduct_amt = yt_tax_deduct_amt;
	}
	/**
	 * @return the yt_determine_tax
	 */
	public int getYt_determine_tax() {
		return yt_determine_tax;
	}
	/**
	 * @param yt_determine_tax the yt_determine_tax to set
	 */
	public void setYt_determine_tax(int yt_determine_tax) {
		this.yt_determine_tax = yt_determine_tax;
	}
	/**
	 * @return the disabled_person_yn
	 */
	public String getDisabled_person_yn() {
		return disabled_person_yn;
	}
	/**
	 * @param disabled_person_yn the disabled_person_yn to set
	 */
	public void setDisabled_person_yn(String disabled_person_yn) {
		this.disabled_person_yn = disabled_person_yn;
	}
	/**
	 * @return the insurance_person_1
	 */
	public int getInsurance_person_1() {
		return insurance_person_1;
	}
	/**
	 * @param insurance_person_1 the insurance_person_1 to set
	 */
	public void setInsurance_person_1(int insurance_person_1) {
		this.insurance_person_1 = insurance_person_1;
	}
	/**
	 * @return the insurance_person_2
	 */
	public int getInsurance_person_2() {
		return insurance_person_2;
	}
	/**
	 * @param insurance_person_2 the insurance_person_2 to set
	 */
	public void setInsurance_person_2(int insurance_person_2) {
		this.insurance_person_2 = insurance_person_2;
	}
	/**
	 * @return the insurance_disabled_person_1
	 */
	public int getInsurance_disabled_person_1() {
		return insurance_disabled_person_1;
	}
	/**
	 * @param insurance_disabled_person_1 the insurance_disabled_person_1 to set
	 */
	public void setInsurance_disabled_person_1(int insurance_disabled_person_1) {
		this.insurance_disabled_person_1 = insurance_disabled_person_1;
	}
	/**
	 * @return the insurance_disabled_person_2
	 */
	public int getInsurance_disabled_person_2() {
		return insurance_disabled_person_2;
	}
	/**
	 * @param insurance_disabled_person_2 the insurance_disabled_person_2 to set
	 */
	public void setInsurance_disabled_person_2(int insurance_disabled_person_2) {
		this.insurance_disabled_person_2 = insurance_disabled_person_2;
	}
	/**
	 * @return the medical_1
	 */
	public int getMedical_1() {
		return medical_1;
	}
	/**
	 * @param medical_1 the medical_1 to set
	 */
	public void setMedical_1(int medical_1) {
		this.medical_1 = medical_1;
	}
	/**
	 * @return the medical_2
	 */
	public int getMedical_2() {
		return medical_2;
	}
	/**
	 * @param medical_2 the medical_2 to set
	 */
	public void setMedical_2(int medical_2) {
		this.medical_2 = medical_2;
	}
	/**
	 * @return the educate_1
	 */
	public int getEducate_1() {
		return educate_1;
	}
	/**
	 * @param educate_1 the educate_1 to set
	 */
	public void setEducate_1(int educate_1) {
		this.educate_1 = educate_1;
	}
	/**
	 * @return the educate_2
	 */
	public int getEducate_2() {
		return educate_2;
	}
	/**
	 * @param educate_2 the educate_2 to set
	 */
	public void setEducate_2(int educate_2) {
		this.educate_2 = educate_2;
	}
	/**
	 * @return the contribute_1
	 */
	public int getContribute_1() {
		return contribute_1;
	}
	/**
	 * @param contribute_1 the contribute_1 to set
	 */
	public void setContribute_1(int contribute_1) {
		this.contribute_1 = contribute_1;
	}
	/**
	 * @return the contribute_2
	 */
	public int getContribute_2() {
		return contribute_2;
	}
	/**
	 * @param contribute_2 the contribute_2 to set
	 */
	public void setContribute_2(int contribute_2) {
		this.contribute_2 = contribute_2;
	}
	/**
	 * @return the dajayeon_cnt
	 */
	public int getDajayeon_cnt() {
		return dajayeon_cnt;
	}
	/**
	 * @param dajayeon_cnt the dajayeon_cnt to set
	 */
	public void setDajayeon_cnt(int dajayeon_cnt) {
		this.dajayeon_cnt = dajayeon_cnt;
	}
	/**
	 * @return the medi_gb
	 */
	public String getMedi_gb() {
		return medi_gb;
	}
	/**
	 * @param medi_gb the medi_gb to set
	 */
	public void setMedi_gb(String medi_gb) {
		this.medi_gb = medi_gb;
	}
	/**
	 * @return the vendor_no
	 */
	public String getVendor_no() {
		return vendor_no;
	}
	/**
	 * @param vendor_no the vendor_no to set
	 */
	public void setVendor_no(String vendor_no) {
		this.vendor_no = vendor_no;
	}
	/**
	 * @return the vendor_nm
	 */
	public String getVendor_nm() {
		return vendor_nm;
	}
	/**
	 * @param vendor_nm the vendor_nm to set
	 */
	public void setVendor_nm(String vendor_nm) {
		this.vendor_nm = vendor_nm;
	}
	/**
	 * @return the card_amt
	 */
	public int getCard_amt() {
		return card_amt;
	}
	/**
	 * @param card_amt the card_amt to set
	 */
	public void setCard_amt(int card_amt) {
		this.card_amt = card_amt;
	}
	/**
	 * @return the card_cnt
	 */
	public String getCard_cnt() {
		return card_cnt;
	}
	/**
	 * @param card_cnt the card_cnt to set
	 */
	public void setCard_cnt(String card_cnt) {
		this.card_cnt = card_cnt;
	}
	/**
	 * @return the edu_org
	 */
	public String getEdu_org() {
		return edu_org;
	}
	/**
	 * @param edu_org the edu_org to set
	 */
	public void setEdu_org(String edu_org) {
		this.edu_org = edu_org;
	}
	/**
	 * @return the public_tx_amt
	 */
	public int getPublic_tx_amt() {
		return public_tx_amt;
	}
	/**
	 * @param public_tx_amt the public_tx_amt to set
	 */
	public void setPublic_tx_amt(int public_tx_amt) {
		this.public_tx_amt = public_tx_amt;
	}
	/**
	 * @return the gubun
	 */
	public String getGubun() {
		return gubun;
	}
	/**
	 * @param gubun the gubun to set
	 */
	public void setGubun(String gubun) {
		this.gubun = gubun;
	}
	/**
	 * @return the yr_don_div
	 */
	public String getYr_don_div() {
		return yr_don_div;
	}
	/**
	 * @param yr_don_div the yr_don_div to set
	 */
	public void setYr_don_div(String yr_don_div) {
		this.yr_don_div = yr_don_div;
	}
	/**
	 * @return the don_amt
	 */
	public int getDon_amt() {
		return don_amt;
	}
	/**
	 * @param don_amt the don_amt to set
	 */
	public void setDon_amt(int don_amt) {
		this.don_amt = don_amt;
	}
	/**
	 * @return the don_nm
	 */
	public String getDon_nm() {
		return don_nm;
	}
	/**
	 * @param don_nm the don_nm to set
	 */
	public void setDon_nm(String don_nm) {
		this.don_nm = don_nm;
	}
	/**
	 * @return the don_no
	 */
	public String getDon_no() {
		return don_no;
	}
	/**
	 * @param don_no the don_no to set
	 */
	public void setDon_no(String don_no) {
		this.don_no = don_no;
	}
	/**
	 * @return the don_receipt_no
	 */
	public String getDon_receipt_no() {
		return don_receipt_no;
	}
	/**
	 * @param don_receipt_no the don_receipt_no to set
	 */
	public void setDon_receipt_no(String don_receipt_no) {
		this.don_receipt_no = don_receipt_no;
	}
	/**
	 * @return the gongje_gb
	 */
	public String getGongje_gb() {
		return gongje_gb;
	}
	/**
	 * @param gongje_gb the gongje_gb to set
	 */
	public void setGongje_gb(String gongje_gb) {
		this.gongje_gb = gongje_gb;
	}
	/**
	 * @return the bank_nm
	 */
	public String getBank_nm() {
		return bank_nm;
	}
	/**
	 * @param bank_nm the bank_nm to set
	 */
	public void setBank_nm(String bank_nm) {
		this.bank_nm = bank_nm;
	}
	/**
	 * @return the bank_cd
	 */
	public String getBank_cd() {
		return bank_cd;
	}
	/**
	 * @param bank_cd the bank_cd to set
	 */
	public void setBank_cd(String bank_cd) {
		this.bank_cd = bank_cd;
	}
	/**
	 * @return the account_no
	 */
	public String getAccount_no() {
		return account_no;
	}
	/**
	 * @param account_no the account_no to set
	 */
	public void setAccount_no(String account_no) {
		this.account_no = account_no;
	}
	/**
	 * @return the in_amt
	 */
	public int getIn_amt() {
		return in_amt;
	}
	/**
	 * @param in_amt the in_amt to set
	 */
	public void setIn_amt(int in_amt) {
		this.in_amt = in_amt;
	}
	/**
	 * @return the rel_cd_nm
	 */
	public String getRel_cd_nm() {
		return rel_cd_nm;
	}
	/**
	 * @param rel_cd_nm the rel_cd_nm to set
	 */
	public void setRel_cd_nm(String rel_cd_nm) {
		this.rel_cd_nm = rel_cd_nm;
	}
	/**
	 * @return the work_amt
	 */
	public int getWork_amt() {
		return work_amt;
	}
	/**
	 * @param work_amt the work_amt to set
	 */
	public void setWork_amt(int work_amt) {
		this.work_amt = work_amt;
	}
	/**
	 * @return the work_pay
	 */
	public int getWork_pay() {
		return work_pay;
	}
	/**
	 * @param work_pay the work_pay to set
	 */
	public void setWork_pay(int work_pay) {
		this.work_pay = work_pay;
	}
	/**
	 * @return the privous_income_tax
	 */
	public int getPrivous_income_tax() {
		return privous_income_tax;
	}
	/**
	 * @param privous_income_tax the privous_income_tax to set
	 */
	public void setPrivous_income_tax(int privous_income_tax) {
		this.privous_income_tax = privous_income_tax;
	}
	/**
	 * @return the privous_jumin_tax
	 */
	public int getPrivous_jumin_tax() {
		return privous_jumin_tax;
	}
	/**
	 * @param privous_jumin_tax the privous_jumin_tax to set
	 */
	public void setPrivous_jumin_tax(int privous_jumin_tax) {
		this.privous_jumin_tax = privous_jumin_tax;
	}
	/**
	 * @return the income_tax
	 */
	public int getIncome_tax() {
		return income_tax;
	}
	/**
	 * @param income_tax the income_tax to set
	 */
	public void setIncome_tax(int income_tax) {
		this.income_tax = income_tax;
	}
	/**
	 * @return the family_direct_ancestor_cnt
	 */
	public String getFamily_direct_ancestor_cnt() {
		return family_direct_ancestor_cnt;
	}
	/**
	 * @param family_direct_ancestor_cnt the family_direct_ancestor_cnt to set
	 */
	public void setFamily_direct_ancestor_cnt(String family_direct_ancestor_cnt) {
		this.family_direct_ancestor_cnt = family_direct_ancestor_cnt;
	}
	/**
	 * @return the family_direct_descendant_cnt
	 */
	public String getFamily_direct_descendant_cnt() {
		return family_direct_descendant_cnt;
	}
	/**
	 * @param family_direct_descendant_cnt the family_direct_descendant_cnt to set
	 */
	public void setFamily_direct_descendant_cnt(String family_direct_descendant_cnt) {
		this.family_direct_descendant_cnt = family_direct_descendant_cnt;
	}
	/**
	 * @return the family_brother_cnt
	 */
	public String getFamily_brother_cnt() {
		return family_brother_cnt;
	}
	/**
	 * @param family_brother_cnt the family_brother_cnt to set
	 */
	public void setFamily_brother_cnt(String family_brother_cnt) {
		this.family_brother_cnt = family_brother_cnt;
	}
	/**
	 * @return the foster_child_cnt
	 */
	public String getFoster_child_cnt() {
		return foster_child_cnt;
	}
	/**
	 * @param foster_child_cnt the foster_child_cnt to set
	 */
	public void setFoster_child_cnt(String foster_child_cnt) {
		this.foster_child_cnt = foster_child_cnt;
	}
	/**
	 * @return the pensioner_cnt
	 */
	public String getPensioner_cnt() {
		return pensioner_cnt;
	}
	/**
	 * @param pensioner_cnt the pensioner_cnt to set
	 */
	public void setPensioner_cnt(String pensioner_cnt) {
		this.pensioner_cnt = pensioner_cnt;
	}
	/**
	 * @return the etc_credt_amt_ly
	 */
	public int getEtc_credt_amt_ly() {
		return etc_credt_amt_ly;
	}
	/**
	 * @param etc_credt_amt_ly the etc_credt_amt_ly to set
	 */
	public void setEtc_credt_amt_ly(int etc_credt_amt_ly) {
		this.etc_credt_amt_ly = etc_credt_amt_ly;
	}
	/**
	 * @return the etc_cash_receipt_amt_ly
	 */
	public int getEtc_cash_receipt_amt_ly() {
		return etc_cash_receipt_amt_ly;
	}
	/**
	 * @param etc_cash_receipt_amt_ly the etc_cash_receipt_amt_ly to set
	 */
	public void setEtc_cash_receipt_amt_ly(int etc_cash_receipt_amt_ly) {
		this.etc_cash_receipt_amt_ly = etc_cash_receipt_amt_ly;
	}
	/**
	 * @return the etc_direct_amt_ly
	 */
	public int getEtc_direct_amt_ly() {
		return etc_direct_amt_ly;
	}
	/**
	 * @param etc_direct_amt_ly the etc_direct_amt_ly to set
	 */
	public void setEtc_direct_amt_ly(int etc_direct_amt_ly) {
		this.etc_direct_amt_ly = etc_direct_amt_ly;
	}
	/**
	 * @return the etc_market_amt_ly
	 */
	public int getEtc_market_amt_ly() {
		return etc_market_amt_ly;
	}
	/**
	 * @param etc_market_amt_ly the etc_market_amt_ly to set
	 */
	public void setEtc_market_amt_ly(int etc_market_amt_ly) {
		this.etc_market_amt_ly = etc_market_amt_ly;
	}
	/**
	 * @return the etc_pubric_transport_amt_ly
	 */
	public int getEtc_pubric_transport_amt_ly() {
		return etc_pubric_transport_amt_ly;
	}
	/**
	 * @param etc_pubric_transport_amt_ly the etc_pubric_transport_amt_ly to set
	 */
	public void setEtc_pubric_transport_amt_ly(int etc_pubric_transport_amt_ly) {
		this.etc_pubric_transport_amt_ly = etc_pubric_transport_amt_ly;
	}
	/**
	 * @return the etc_credt_amt_fh
	 */
	public int getEtc_credt_amt_fh() {
		return etc_credt_amt_fh;
	}
	/**
	 * @param etc_credt_amt_fh the etc_credt_amt_fh to set
	 */
	public void setEtc_credt_amt_fh(int etc_credt_amt_fh) {
		this.etc_credt_amt_fh = etc_credt_amt_fh;
	}
	/**
	 * @return the etc_cash_amt_fh
	 */
	public int getEtc_cash_amt_fh() {
		return etc_cash_amt_fh;
	}
	/**
	 * @param etc_cash_amt_fh the etc_cash_amt_fh to set
	 */
	public void setEtc_cash_amt_fh(int etc_cash_amt_fh) {
		this.etc_cash_amt_fh = etc_cash_amt_fh;
	}
	/**
	 * @return the etc_direct_amt_fh
	 */
	public int getEtc_direct_amt_fh() {
		return etc_direct_amt_fh;
	}
	/**
	 * @param etc_direct_amt_fh the etc_direct_amt_fh to set
	 */
	public void setEtc_direct_amt_fh(int etc_direct_amt_fh) {
		this.etc_direct_amt_fh = etc_direct_amt_fh;
	}
	/**
	 * @return the etc_market_amt_fh
	 */
	public int getEtc_market_amt_fh() {
		return etc_market_amt_fh;
	}
	/**
	 * @param etc_market_amt_fh the etc_market_amt_fh to set
	 */
	public void setEtc_market_amt_fh(int etc_market_amt_fh) {
		this.etc_market_amt_fh = etc_market_amt_fh;
	}
	/**
	 * @return the etc_pubric_transport_amt_fh
	 */
	public int getEtc_pubric_transport_amt_fh() {
		return etc_pubric_transport_amt_fh;
	}
	/**
	 * @param etc_pubric_transport_amt_fh the etc_pubric_transport_amt_fh to set
	 */
	public void setEtc_pubric_transport_amt_fh(int etc_pubric_transport_amt_fh) {
		this.etc_pubric_transport_amt_fh = etc_pubric_transport_amt_fh;
	}
	/**
	 * @return the etc_credt_amt_sh
	 */
	public int getEtc_credt_amt_sh() {
		return etc_credt_amt_sh;
	}
	/**
	 * @param etc_credt_amt_sh the etc_credt_amt_sh to set
	 */
	public void setEtc_credt_amt_sh(int etc_credt_amt_sh) {
		this.etc_credt_amt_sh = etc_credt_amt_sh;
	}
	/**
	 * @return the etc_cash_amt_sh
	 */
	public int getEtc_cash_amt_sh() {
		return etc_cash_amt_sh;
	}
	/**
	 * @param etc_cash_amt_sh the etc_cash_amt_sh to set
	 */
	public void setEtc_cash_amt_sh(int etc_cash_amt_sh) {
		this.etc_cash_amt_sh = etc_cash_amt_sh;
	}
	/**
	 * @return the etc_direct_amt_sh
	 */
	public int getEtc_direct_amt_sh() {
		return etc_direct_amt_sh;
	}
	/**
	 * @param etc_direct_amt_sh the etc_direct_amt_sh to set
	 */
	public void setEtc_direct_amt_sh(int etc_direct_amt_sh) {
		this.etc_direct_amt_sh = etc_direct_amt_sh;
	}
	/**
	 * @return the etc_market_amt_sh
	 */
	public int getEtc_market_amt_sh() {
		return etc_market_amt_sh;
	}
	/**
	 * @param etc_market_amt_sh the etc_market_amt_sh to set
	 */
	public void setEtc_market_amt_sh(int etc_market_amt_sh) {
		this.etc_market_amt_sh = etc_market_amt_sh;
	}
	/**
	 * @return the etc_pubric_transport_amt_sh
	 */
	public int getEtc_pubric_transport_amt_sh() {
		return etc_pubric_transport_amt_sh;
	}
	/**
	 * @param etc_pubric_transport_amt_sh the etc_pubric_transport_amt_sh to set
	 */
	public void setEtc_pubric_transport_amt_sh(int etc_pubric_transport_amt_sh) {
		this.etc_pubric_transport_amt_sh = etc_pubric_transport_amt_sh;
	}
	/**
	 * @return the compute_1
	 */
	public int getCompute_1() {
		return compute_1;
	}
	/**
	 * @param compute_1 the compute_1 to set
	 */
	public void setCompute_1(int compute_1) {
		this.compute_1 = compute_1;
	}
	/**
	 * @return the compute_2
	 */
	public int getCompute_2() {
		return compute_2;
	}
	/**
	 * @param compute_2 the compute_2 to set
	 */
	public void setCompute_2(int compute_2) {
		this.compute_2 = compute_2;
	}
	/**
	 * @return the compute_3
	 */
	public int getCompute_3() {
		return compute_3;
	}
	/**
	 * @param compute_3 the compute_3 to set
	 */
	public void setCompute_3(int compute_3) {
		this.compute_3 = compute_3;
	}
	/**
	 * @return the compute_4
	 */
	public int getCompute_4() {
		return compute_4;
	}
	/**
	 * @param compute_4 the compute_4 to set
	 */
	public void setCompute_4(int compute_4) {
		this.compute_4 = compute_4;
	}
	/**
	 * @return the smiymjtc_100
	 */
	public int getSmiymjtc_100() {
		return smiymjtc_100;
	}
	/**
	 * @param smiymjtc_100 the smiymjtc_100 to set
	 */
	public void setSmiymjtc_100(int smiymjtc_100) {
		this.smiymjtc_100 = smiymjtc_100;
	}
	/**
	 * @return the smiymjtc_50
	 */
	public int getSmiymjtc_50() {
		return smiymjtc_50;
	}
	/**
	 * @param smiymjtc_50 the smiymjtc_50 to set
	 */
	public void setSmiymjtc_50(int smiymjtc_50) {
		this.smiymjtc_50 = smiymjtc_50;
	}
	/**
	 * @return the searchType
	 */
	public String getSearchType() {
		return searchType;
	}
	/**
	 * @param searchType the searchType to set
	 */
	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}
	/**
	 * @return the result
	 */
	public String getResult() {
		return result;
	}
	/**
	 * @param result the result to set
	 */
	public void setResult(String result) {
		this.result = result;
	}
	/**
	 * @return the org_rel_jumin_no
	 */
	public String getOrg_rel_jumin_no() {
		return org_rel_jumin_no;
	}
	/**
	 * @param org_rel_jumin_no the org_rel_jumin_no to set
	 */
	public void setOrg_rel_jumin_no(String org_rel_jumin_no) {
		this.org_rel_jumin_no = org_rel_jumin_no;
	}
	/**
	 * @return the medical_person_amt
	 */
	public int getMedical_person_amt() {
		return medical_person_amt;
	}
	/**
	 * @param medical_person_amt the medical_person_amt to set
	 */
	public void setMedical_person_amt(int medical_person_amt) {
		this.medical_person_amt = medical_person_amt;
	}
	/**
	 * @return the medical_old_amt
	 */
	public int getMedical_old_amt() {
		return medical_old_amt;
	}
	/**
	 * @param medical_old_amt the medical_old_amt to set
	 */
	public void setMedical_old_amt(int medical_old_amt) {
		this.medical_old_amt = medical_old_amt;
	}
	/**
	 * @return the medical_disabled_person_amt
	 */
	public int getMedical_disabled_person_amt() {
		return medical_disabled_person_amt;
	}
	/**
	 * @param medical_disabled_person_amt the medical_disabled_person_amt to set
	 */
	public void setMedical_disabled_person_amt(int medical_disabled_person_amt) {
		this.medical_disabled_person_amt = medical_disabled_person_amt;
	}
	/**
	 * @return the medical_etc_amt
	 */
	public int getMedical_etc_amt() {
		return medical_etc_amt;
	}
	/**
	 * @param medical_etc_amt the medical_etc_amt to set
	 */
	public void setMedical_etc_amt(int medical_etc_amt) {
		this.medical_etc_amt = medical_etc_amt;
	}
	/**
	 * @return the medical_calc
	 */
	public int getMedical_calc() {
		return medical_calc;
	}
	/**
	 * @param medical_calc the medical_calc to set
	 */
	public void setMedical_calc(int medical_calc) {
		this.medical_calc = medical_calc;
	}
	/**
	 * @return the educate_person_amt
	 */
	public int getEducate_person_amt() {
		return educate_person_amt;
	}
	/**
	 * @param educate_person_amt the educate_person_amt to set
	 */
	public void setEducate_person_amt(int educate_person_amt) {
		this.educate_person_amt = educate_person_amt;
	}
	/**
	 * @return the educate_kindergarten_amt
	 */
	public int getEducate_kindergarten_amt() {
		return educate_kindergarten_amt;
	}
	/**
	 * @param educate_kindergarten_amt the educate_kindergarten_amt to set
	 */
	public void setEducate_kindergarten_amt(int educate_kindergarten_amt) {
		this.educate_kindergarten_amt = educate_kindergarten_amt;
	}
	/**
	 * @return the educate_school_amt
	 */
	public int getEducate_school_amt() {
		return educate_school_amt;
	}
	/**
	 * @param educate_school_amt the educate_school_amt to set
	 */
	public void setEducate_school_amt(int educate_school_amt) {
		this.educate_school_amt = educate_school_amt;
	}
	/**
	 * @return the educate_univ_amt
	 */
	public int getEducate_univ_amt() {
		return educate_univ_amt;
	}
	/**
	 * @param educate_univ_amt the educate_univ_amt to set
	 */
	public void setEducate_univ_amt(int educate_univ_amt) {
		this.educate_univ_amt = educate_univ_amt;
	}
	/**
	 * @return the educate_disabled_person_amt
	 */
	public int getEducate_disabled_person_amt() {
		return educate_disabled_person_amt;
	}
	/**
	 * @param educate_disabled_person_amt the educate_disabled_person_amt to set
	 */
	public void setEducate_disabled_person_amt(int educate_disabled_person_amt) {
		this.educate_disabled_person_amt = educate_disabled_person_amt;
	}
	
}
