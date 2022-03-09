<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%--
 * @파일명 : yearendtaxInsertForm.jsp
 * @메뉴명 : 연말정산 등록
 * @최초작성일 : 2015/02/16
 * @author : 윤범진                  
 * @수정내역 : 
--%>
<%@ page import="com.hanaph.gw.yt.yearendtax.vo.YearendtaxVO" %>
<%@ page import="com.hanaph.gw.co.common.utils.StringUtil" %>
<%@ include file ="/common/path.jsp" %>
<%
	String year = StringUtil.nvl((String)request.getAttribute("year"));
	String addressCnt = String.valueOf(request.getAttribute("addressCnt"));
	YearendtaxVO yearendtaxDetail = (YearendtaxVO)request.getAttribute("yearendtaxDetail");
	
	/* 기본사항 */
	String pay_sum = StringUtil.makeMoneyType(yearendtaxDetail.getPay_sum());
	String work_amt = StringUtil.makeMoneyType(yearendtaxDetail.getWork_amt());
	String work_pay = StringUtil.makeMoneyType(yearendtaxDetail.getWork_pay());
	String privous_income_tax = StringUtil.makeMoneyType(yearendtaxDetail.getPrivous_income_tax());
	String privous_jumin_tax = StringUtil.makeMoneyType(yearendtaxDetail.getPrivous_jumin_tax());
	String income_tax = StringUtil.makeMoneyType(yearendtaxDetail.getIncome_tax());
	String jing_jumin_tax = StringUtil.makeMoneyType(yearendtaxDetail.getJing_jumin_tax());
	
	/* 기본공제 */
	String family_person_amt = StringUtil.makeMoneyType(yearendtaxDetail.getFamily_person_amt());
	String family_mate_amt = String.valueOf(yearendtaxDetail.getFamily_mate_amt());
	String family_direct_ancestor_cnt = StringUtil.nvl(yearendtaxDetail.getFamily_direct_ancestor_cnt(), "0");
	String family_direct_descendant_cnt = StringUtil.nvl(yearendtaxDetail.getFamily_direct_descendant_cnt(), "0");
	String family_brother_cnt = StringUtil.nvl(yearendtaxDetail.getFamily_brother_cnt(), "0");
	String foster_child_cnt = StringUtil.nvl(yearendtaxDetail.getFoster_child_cnt(), "0");
	String pensioner_cnt = StringUtil.nvl(yearendtaxDetail.getPensioner_cnt(), "0");
	
	/* 추가공제 */
	String family_old2_cnt = String.valueOf(yearendtaxDetail.getFamily_old2_cnt());
	String family_disabled_person_cnt = String.valueOf(yearendtaxDetail.getFamily_disabled_person_cnt());
	String family_women_amt = String.valueOf(yearendtaxDetail.getFamily_women_amt());
	String family_single_parent_amt = String.valueOf(yearendtaxDetail.getFamily_single_parent_amt());
	
	/* 연금보험료공제 */
	String kuk_yeon_amt = StringUtil.makeMoneyType(yearendtaxDetail.getKuk_yeon_amt());
	
	/*특별공제(보험료)*/
	String insurance_health_amt = StringUtil.makeMoneyType(yearendtaxDetail.getInsurance_health_amt());
	String insurance_employ_amt = StringUtil.makeMoneyType(yearendtaxDetail.getInsurance_employ_amt());
	
	/*특별공제(주택자금)*/
	
	String house_lease_loan_amt = StringUtil.makeMoneyType(yearendtaxDetail.getHouse_lease_loan_amt());
	String house_security_loan1_amt = StringUtil.makeMoneyType(yearendtaxDetail.getHouse_security_loan1_amt());
	String house_security_loan2_amt = StringUtil.makeMoneyType(yearendtaxDetail.getHouse_security_loan2_amt());
	String house_security_loan3_amt = StringUtil.makeMoneyType(yearendtaxDetail.getHouse_security_loan3_amt());
	String house_security_loan4_amt = StringUtil.makeMoneyType(yearendtaxDetail.getHouse_security_loan4_amt());
	String house_security_loan5_amt = StringUtil.makeMoneyType(yearendtaxDetail.getHouse_security_loan5_amt());
	
	/*개인연금저축공제*/
	String personal_annuity_amt = StringUtil.makeMoneyType(yearendtaxDetail.getPersonal_annuity_amt());
	
	/*그밖의 소득공제(주택마련저축)*/
	String etc_subscription_deposit_amt = StringUtil.makeMoneyType(yearendtaxDetail.getEtc_subscription_deposit_amt());
	String etc_home_mortgage_amt = StringUtil.makeMoneyType(yearendtaxDetail.getEtc_home_mortgage_amt());
	String etc_house_subscr_deposit_amt = StringUtil.makeMoneyType(yearendtaxDetail.getEtc_house_subscr_deposit_amt());
	
	/*그밖의 소득공제(신용카드 등 사용금액)*/
	String etc_credt_amt = StringUtil.makeMoneyType(yearendtaxDetail.getEtc_credt_amt());
	String etc_cash_receipt_amt = StringUtil.makeMoneyType(yearendtaxDetail.getEtc_cash_receipt_amt());
	String etc_direct_amt = StringUtil.makeMoneyType(yearendtaxDetail.getEtc_direct_amt());
	String etc_market_amt = StringUtil.makeMoneyType(yearendtaxDetail.getEtc_market_amt());
	String etc_pubric_transport_amt = StringUtil.makeMoneyType(yearendtaxDetail.getEtc_pubric_transport_amt());
	String compute_1 = StringUtil.makeMoneyType(yearendtaxDetail.getCompute_1());
	String compute_2 = StringUtil.makeMoneyType(yearendtaxDetail.getCompute_2());
	String compute_3 = StringUtil.makeMoneyType(yearendtaxDetail.getCompute_3());
	String compute_4 = StringUtil.makeMoneyType(yearendtaxDetail.getCompute_4());
	
	/*그밖의 소득공제(기타)*/
	String etc_employ_stock_amt = StringUtil.makeMoneyType(yearendtaxDetail.getEtc_employ_stock_amt());
	String etc_chunk_money_amt = StringUtil.makeMoneyType(yearendtaxDetail.getEtc_chunk_money_amt());
	String etc_long_invest_stock_amt = StringUtil.makeMoneyType(yearendtaxDetail.getEtc_long_invest_stock_amt());
	
	/*세액감면 및 세액공제*/
	String etc_income_tax_reduction_amt = StringUtil.makeMoneyType(yearendtaxDetail.getEtc_income_tax_reduction_amt());
	String smiymjtc_100 = StringUtil.makeMoneyType(yearendtaxDetail.getSmiymjtc_100());
	String smiymjtc_50 = StringUtil.makeMoneyType(yearendtaxDetail.getSmiymjtc_50());
	String yt_salary = StringUtil.makeMoneyType(yearendtaxDetail.getYt_salary());
	String etc_smiymjtc_amt = StringUtil.makeMoneyType(yearendtaxDetail.getEtc_smiymjtc_amt());
	String yt_earned_income_amt = StringUtil.makeMoneyType(yearendtaxDetail.getYt_earned_income_amt());
	String etc_children_amt = StringUtil.makeMoneyType(yearendtaxDetail.getEtc_children_amt());
	String annuity_savng_amt = StringUtil.makeMoneyType(yearendtaxDetail.getAnnuity_savng_amt());
	String insurance_secrity_amt = StringUtil.makeMoneyType(yearendtaxDetail.getInsurance_secrity_amt());
	String insurance_disabled_person_amt = StringUtil.makeMoneyType(yearendtaxDetail.getInsurance_disabled_person_amt());
	String medical_person_amt = StringUtil.makeMoneyType(yearendtaxDetail.getMedical_person_amt());
	String medical_old_amt = StringUtil.makeMoneyType(yearendtaxDetail.getMedical_old_amt());
	String medical_disabled_person_amt = StringUtil.makeMoneyType(yearendtaxDetail.getMedical_disabled_person_amt());
	String medical_etc_amt = StringUtil.makeMoneyType(yearendtaxDetail.getMedical_etc_amt());
	String medical_amt_o = StringUtil.makeMoneyType(yearendtaxDetail.getMedical_amt_o());
	String total_medical_amt = StringUtil.makeMoneyType(yearendtaxDetail.getTotal_medical_amt());
	String medical_calc = StringUtil.makeMoneyType(yearendtaxDetail.getMedical_calc());
	String educate_person_amt = StringUtil.makeMoneyType(yearendtaxDetail.getEducate_person_amt());
	String educate_kindergarten_amt = StringUtil.makeMoneyType(yearendtaxDetail.getEducate_kindergarten_amt());
	String educate_school_amt = StringUtil.makeMoneyType(yearendtaxDetail.getEducate_school_amt());
	String educate_univ_amt = StringUtil.makeMoneyType(yearendtaxDetail.getEducate_univ_amt());
	String educate_disabled_person_amt = StringUtil.makeMoneyType(yearendtaxDetail.getEducate_disabled_person_amt());
	String edcate_amt_o = StringUtil.makeMoneyType(yearendtaxDetail.getEdcate_amt_o());
	String total_edcate_amt = StringUtil.makeMoneyType(yearendtaxDetail.getTotal_edcate_amt());
	String contribue_politic_amt = StringUtil.makeMoneyType(yearendtaxDetail.getContribue_politic_amt());
	String etc_contribue_politic_amt = StringUtil.makeMoneyType(yearendtaxDetail.getEtc_contribue_politic_amt());
	String contribue_law_amt = StringUtil.makeMoneyType(yearendtaxDetail.getContribue_law_amt());
	String contribute_trust_amt = StringUtil.makeMoneyType(yearendtaxDetail.getContribute_trust_amt());
	String contribue_employ_stock_amt = StringUtil.makeMoneyType(yearendtaxDetail.getContribue_employ_stock_amt());
	String contribue_designate_amt = StringUtil.makeMoneyType(yearendtaxDetail.getContribue_designate_amt());
	String contribue_religion_amt = StringUtil.makeMoneyType(yearendtaxDetail.getContribue_religion_amt());
	String contribute_exception_amt = StringUtil.makeMoneyType(yearendtaxDetail.getContribute_exception_amt());
	String yt_standard_deduct_amt = StringUtil.makeMoneyType(yearendtaxDetail.getYt_standard_deduct_amt());
	String etc_sework_tx_amt = StringUtil.makeMoneyType(yearendtaxDetail.getEtc_sework_tx_amt());
	String etc_house_laon_interest_amt = StringUtil.makeMoneyType(yearendtaxDetail.getEtc_house_laon_interest_amt());
	String etc_foreigner_income = StringUtil.makeMoneyType(yearendtaxDetail.getEtc_foreigner_income());
	String etc_foreigner_pay = StringUtil.makeMoneyType(yearendtaxDetail.getEtc_foreigner_pay());
	String house_month_lent_amt = StringUtil.makeMoneyType(yearendtaxDetail.getHouse_month_lent_amt());
	
%>
<!DOCTYPE html>
<html lang="ko">
<head>
	<%@include file="/include/head.jsp"%>
	<script type="text/javascript">
	
	$(document).ready(function(){
		/* 입력 가능 영역 유효성 체크 */
		$("input[type='text'].onlyNumber").keyup(function(event) {
			var keyCode = event.keyCode;
		    if (keyCode != 16 && keyCode != 46 && (keyCode < 35 || keyCode > 40)) { // 16:shift/46:delete/35:end/36:home/37~40:화살표
		    	$(this).val(formCheck.onlyNumber($(this).val()));	
			}
		});
		
		/* 주소정보 수에 따라 기본 셋팅 */
		defaultInfo($("#addressCnt").val());
	});
	
	/* 목록화면 */
	function goYearendtaxList(){
		document.getElementById("frm").action ="<%=GROUP_ROOT%>/yt/yearendtax/yearendtaxList.do";
		document.getElementById("frm").submit();
	}
	
	/* 프로시저 호출 */
	function getYearendtaxInfoAjax(searchType) {
		
		/* 주소정보 유무 파악 */
		var addressCnt = $("#addressCnt").val();
		
		if ("0" == addressCnt) {
			alert("주소정보가 없습니다.");
			Commons.popupOpen('address','<%=GROUP_ROOT%>/yt/yearendtax/yearendtaxAddressPopup.do','820','600');
			return false;
		}
		
		$("#searchType").val(searchType);
				
		$.ajax({
			type:"POST",
			data: $("#frm").serialize(),
			url:"<%=GROUP_ROOT%>/yt/yearendtax/getYearendtaxInfoAjax.do",
			dataType:"json",
			success:function(data){
				
				/* 기본사항 */
				$("#pay_sum").val(Commons.addComma(data.pay_sum));
				$("#work_amt").val(Commons.addComma(data.work_amt));
				$("#work_pay").val(Commons.addComma(data.work_pay));
				$("#privous_income_tax").val(Commons.addComma(data.privous_income_tax));
				$("#privous_jumin_tax").val(Commons.addComma(data.privous_jumin_tax));
				$("#income_tax").val(Commons.addComma(data.income_tax));
				$("#jing_jumin_tax").val(Commons.addComma(data.jing_jumin_tax));
				
				/* 기본공제 */
				$("#family_person_amt").val(Commons.addComma(data.family_person_amt));
				
				var family_mate_amt = data.family_mate_amt;
				
				if ("1500000" == family_mate_amt) {
					$("#family_mate_amt1").prop("checked", true);
				} else if ("0" == family_mate_amt) {
					$("#family_mate_amt2").prop("checked", true);
				}
				
				$("#family_direct_ancestor_cnt").val(data.family_direct_ancestor_cnt);
				$("#family_direct_descendant_cnt").val(data.family_direct_descendant_cnt);
				$("#family_brother_cnt").val(data.family_brother_cnt);
				$("#foster_child_cnt").val(data.foster_child_cnt);
				$("#pensioner_cnt").val(data.pensioner_cnt);
				
				/* 추가공제 */
				$("#family_old2_cnt").val(data.family_old2_cnt);
				$("#family_disabled_person_cnt").val(data.family_disabled_person_cnt);
				
				var family_women_amt = data.family_women_amt;
				var family_single_parent_amt = data.family_single_parent_amt;
				
				if ("500000" == family_women_amt) {
					$("#family_women_amt1").prop("checked", true);
				} else if ("0" == family_women_amt) {
					$("#family_women_amt2").prop("checked", true);
				}
				
				if ("1000000" == family_single_parent_amt) {
					$("#family_single_parent_amt1").prop("checked", true);
				} else if ("0" == family_single_parent_amt) {
					$("#family_single_parent_amt2").prop("checked", true);
				}
				
				/* 연금보험료공제 */
				$("#kuk_yeon_amt").val(Commons.addComma(data.kuk_yeon_amt));
				
				/*특별공제(보험료)*/
				$("#insurance_health_amt").val(Commons.addComma(data.insurance_health_amt));
				$("#insurance_employ_amt").val(Commons.addComma(data.insurance_employ_amt));
				
				/* 특별공제(주택자금) */
				$("#house_lease_loan_amt").val(Commons.addComma(data.house_lease_loan_amt));
				$("#house_security_loan1_amt").val(Commons.addComma(data.house_security_loan1_amt));
				$("#house_security_loan2_amt").val(Commons.addComma(data.house_security_loan2_amt));
				$("#house_security_loan3_amt").val(Commons.addComma(data.house_security_loan3_amt));
				$("#house_security_loan4_amt").val(Commons.addComma(data.house_security_loan4_amt));
				$("#house_security_loan5_amt").val(Commons.addComma(data.house_security_loan5_amt));
				
				/*개인연금저축공제*/
				$("#personal_annuity_amt").val(Commons.addComma(data.personal_annuity_amt));
				
				/*그밖의 소득공제(주택마련저축)*/
				$("#etc_subscription_deposit_amt").val(Commons.addComma(data.etc_subscription_deposit_amt));
				$("#etc_home_mortgage_amt").val(Commons.addComma(data.etc_home_mortgage_amt));
				$("#etc_house_subscr_deposit_amt").val(Commons.addComma(data.etc_house_subscr_deposit_amt));
				
				/*그밖의 소득공제(신용카드 등 사용금액)*/
				$("#etc_credt_amt").val(Commons.addComma(data.etc_credt_amt));
				$("#etc_cash_receipt_amt").val(Commons.addComma(data.etc_cash_receipt_amt));
				$("#etc_direct_amt").val(Commons.addComma(data.etc_direct_amt));
				$("#etc_market_amt").val(Commons.addComma(data.etc_market_amt));
				$("#etc_pubric_transport_amt").val(Commons.addComma(data.etc_pubric_transport_amt));
				$("#compute_1").val(Commons.addComma(data.compute_1));
				$("#compute_2").val(Commons.addComma(data.compute_2));
				$("#compute_3").val(Commons.addComma(data.compute_3));
				$("#compute_4").val(Commons.addComma(data.compute_4));
				
				/*그밖의 소득공제(기타)*/
				$("#etc_employ_stock_amt").val(Commons.addComma(data.etc_employ_stock_amt));
				$("#etc_chunk_money_amt").val(Commons.addComma(data.etc_chunk_money_amt));
				$("#etc_long_invest_stock_amt").val(Commons.addComma(data.etc_long_invest_stock_amt));
				
				/*세액감면 및 세액공제*/
				$("#etc_income_tax_reduction_amt").val(Commons.addComma(data.etc_income_tax_reduction_amt));
				$("#smiymjtc_100").val(Commons.addComma(data.smiymjtc_100));
				$("#smiymjtc_50").val(Commons.addComma(data.smiymjtc_50));
				$("#yt_salary").val(Commons.addComma(data.yt_salary));
				$("#etc_smiymjtc_amt").val(Commons.addComma(data.etc_smiymjtc_amt));
				$("#yt_earned_income_amt").val(Commons.addComma(data.yt_earned_income_amt));
				$("#etc_children_amt").val(Commons.addComma(data.etc_children_amt));
				$("#annuity_savng_amt").val(Commons.addComma(data.annuity_savng_amt));
				$("#insurance_secrity_amt").val(Commons.addComma(data.insurance_secrity_amt));
				$("#insurance_disabled_person_amt").val(Commons.addComma(data.insurance_disabled_person_amt));
				$("#medical_person_amt").val(Commons.addComma(data.medical_person_amt));
				$("#medical_old_amt").val(Commons.addComma(data.medical_old_amt));
				$("#medical_disabled_person_amt").val(Commons.addComma(data.medical_disabled_person_amt));
				$("#medical_etc_amt").val(Commons.addComma(data.medical_etc_amt));
				$("#medical_amt_o").val(Commons.addComma(data.medical_amt_o));
				$("#total_medical_amt").val(Commons.addComma(data.total_medical_amt));
				$("#medical_calc").val(Commons.addComma(data.medical_calc));
				$("#educate_person_amt").val(Commons.addComma(data.educate_person_amt));
				$("#educate_kindergarten_amt").val(Commons.addComma(data.educate_kindergarten_amt));
				$("#educate_school_amt").val(Commons.addComma(data.educate_school_amt));
				$("#educate_univ_amt").val(Commons.addComma(data.educate_univ_amt));
				$("#educate_disabled_person_amt").val(Commons.addComma(data.educate_disabled_person_amt));
				$("#edcate_amt_o").val(Commons.addComma(data.edcate_amt_o));
				$("#total_edcate_amt").val(Commons.addComma(data.total_edcate_amt));
				$("#contribue_politic_amt").val(Commons.addComma(data.contribue_politic_amt));
				$("#etc_contribue_politic_amt").val(Commons.addComma(data.etc_contribue_politic_amt));
				$("#contribue_law_amt").val(Commons.addComma(data.contribue_law_amt));
				$("#contribute_trust_amt").val(Commons.addComma(data.contribute_trust_amt));
				$("#contribue_employ_stock_amt").val(Commons.addComma(data.contribue_employ_stock_amt));
				$("#contribue_designate_amt").val(Commons.addComma(data.contribue_designate_amt));
				$("#contribue_religion_amt").val(Commons.addComma(data.contribue_religion_amt));
				$("#yt_standard_deduct_amt").val(Commons.addComma(data.yt_standard_deduct_amt));
				$("#etc_sework_tx_amt").val(Commons.addComma(data.etc_sework_tx_amt));
				$("#etc_house_laon_interest_amt").val(Commons.addComma(data.etc_house_laon_interest_amt));
				$("#etc_foreigner_income").val(Commons.addComma(data.etc_foreigner_income));
				$("#etc_foreigner_pay").val(Commons.addComma(data.etc_foreigner_pay));
				$("#house_month_lent_amt").val(Commons.addComma(data.house_month_lent_amt));
				
			}
		});
	}
	
	/* 주소정보의 유무에 따라 기능 제어 */
	function defaultInfo(addressCnt) {

		if ("0" == addressCnt) {
			
			/* 입력 영역 readonly 처리 */
			$("#house_lease_loan_amt").addClass("ipt_disable");
			$("#house_lease_loan_amt").attr("readonly", true);
			$("#house_security_loan1_amt").addClass("ipt_disable");
			$("#house_security_loan1_amt").attr("readonly", true);
			$("#house_security_loan2_amt").addClass("ipt_disable");
			$("#house_security_loan2_amt").attr("readonly", true);
			$("#house_security_loan3_amt").addClass("ipt_disable");
			$("#house_security_loan3_amt").attr("readonly", true);
			$("#house_security_loan4_amt").addClass("ipt_disable");
			$("#house_security_loan4_amt").attr("readonly", true);
			$("#house_security_loan5_amt").addClass("ipt_disable");
			$("#house_security_loan5_amt").attr("readonly", true);
			$("#etc_employ_stock_amt").addClass("ipt_disable");
			$("#etc_employ_stock_amt").attr("readonly", true);
			$("#etc_chunk_money_amt").addClass("ipt_disable");
			$("#etc_chunk_money_amt").attr("readonly", true);
			$("#etc_income_tax_reduction_amt").addClass("ipt_disable");
			$("#etc_income_tax_reduction_amt").attr("readonly", true);
			$("#etc_contribue_politic_amt").addClass("ipt_disable");
			$("#etc_contribue_politic_amt").attr("readonly", true);
			$("#etc_sework_tx_amt").addClass("ipt_disable");
			$("#etc_sework_tx_amt").attr("readonly", true);
			$("#etc_house_laon_interest_amt").addClass("ipt_disable");
			$("#etc_house_laon_interest_amt").attr("readonly", true);
			$("#etc_foreigner_income").addClass("ipt_disable");
			$("#etc_foreigner_income").attr("readonly", true);
			$("#etc_foreigner_pay").addClass("ipt_disable");
			$("#etc_foreigner_pay").attr("readonly", true);
			
			/* 주소정보 팝업 열기 */
			getAddressListLayer();
			
		} else {
			
			/* 입력 영역 readonly 제거 */
			$("#house_lease_loan_amt").removeClass("ipt_disable");
			$("#house_lease_loan_amt").attr("readonly", false);
			$("#house_security_loan1_amt").removeClass("ipt_disable");
			$("#house_security_loan1_amt").attr("readonly", false);
			$("#house_security_loan2_amt").removeClass("ipt_disable");
			$("#house_security_loan2_amt").attr("readonly", false);
			$("#house_security_loan3_amt").removeClass("ipt_disable");
			$("#house_security_loan3_amt").attr("readonly", false);
			$("#house_security_loan4_amt").removeClass("ipt_disable");
			$("#house_security_loan4_amt").attr("readonly", false);
			$("#house_security_loan5_amt").removeClass("ipt_disable");
			$("#house_security_loan5_amt").attr("readonly", false);
			$("#etc_employ_stock_amt").removeClass("ipt_disable");
			$("#etc_employ_stock_amt").attr("readonly", false);
			$("#etc_chunk_money_amt").removeClass("ipt_disable");
			$("#etc_chunk_money_amt").attr("readonly", false);
			$("#etc_income_tax_reduction_amt").removeClass("ipt_disable");
			$("#etc_income_tax_reduction_amt").attr("readonly", false);
			$("#etc_contribue_politic_amt").removeClass("ipt_disable");
			$("#etc_contribue_politic_amt").attr("readonly", false);
			$("#etc_sework_tx_amt").removeClass("ipt_disable");
			$("#etc_sework_tx_amt").attr("readonly", false);
			$("#etc_house_laon_interest_amt").removeClass("ipt_disable");
			$("#etc_house_laon_interest_amt").attr("readonly", false);
			$("#etc_foreigner_income").removeClass("ipt_disable");
			$("#etc_foreigner_income").attr("readonly", false);
			$("#etc_foreigner_pay").removeClass("ipt_disable");
			$("#etc_foreigner_pay").attr("readonly", false);
		}
		
		$("#addressCnt").val(addressCnt);
	}
	
	function openPopup(type){
		
		/* 주소정보 유무 파악 */
		var addressCnt = $("#addressCnt").val();
		
		if ("0" == addressCnt) {
			alert("주소정보가 없습니다.");
			Commons.popupOpen('address','<%=GROUP_ROOT%>/yt/yearendtax/yearendtaxAddressPopup.do','820','600');
			return false;
		}
		
		if ("previousWorkplace" == type) {
			Commons.popupOpen('previousWorkplace','<%=GROUP_ROOT%>/yt/yearendtax/yearendtaxPreviousWorkplacePopup.do?year=<%=year %>','820','850');
		} else if ("family" == type) {
			Commons.popupOpen('family','<%=GROUP_ROOT%>/yt/yearendtax/yearendtaxFamilyPopup.do?year=<%=year %>','820','380');
		} else if ("housingFund" == type) {
			Commons.popupOpen('housingFund','<%=GROUP_ROOT%>/yt/yearendtax/yearendtaxHousePopup.do?year=<%=year %>&searchType=housingFund','820','380');
		} else if ("savingP" == type) {
			Commons.popupOpen('saving','<%=GROUP_ROOT%>/yt/yearendtax/yearendtaxSavingPopup.do?year=<%=year %>&searchType=P','820','600');
		} else if ("savingE" == type) {
			Commons.popupOpen('saving','<%=GROUP_ROOT%>/yt/yearendtax/yearendtaxSavingPopup.do?year=<%=year %>&searchType=P','820','600');
		} else if ("creditCard" == type) {
			Commons.popupOpen('creditCard','<%=GROUP_ROOT%>/yt/yearendtax/yearendtaxCreditCardPopup.do?year=<%=year %>','820','545');
		} else if ("savingL" == type) {
			Commons.popupOpen('saving','<%=GROUP_ROOT%>/yt/yearendtax/yearendtaxSavingPopup.do?year=<%=year %>&searchType=P','820','600');
		} else if ("savingA" == type) {
			Commons.popupOpen('saving','<%=GROUP_ROOT%>/yt/yearendtax/yearendtaxSavingPopup.do?year=<%=year %>&searchType=P','820','600');
		} else if ("insurance" == type) {
			Commons.popupOpen('insurance','<%=GROUP_ROOT%>/yt/yearendtax/yearendtaxInsurancePopup.do?year=<%=year %>','820','380');
		} else if ("medical" == type) {
			Commons.popupOpen('medical','<%=GROUP_ROOT%>/yt/yearendtax/yearendtaxMedicalPopup.do?year=<%=year %>','820','600');
		} else if ("educate" == type) {
			Commons.popupOpen('educate','<%=GROUP_ROOT%>/yt/yearendtax/yearendtaxEducatePopup.do?year=<%=year %>','820','600');
		} else if ("contribute" == type) {
			Commons.popupOpen('contribute','<%=GROUP_ROOT%>/yt/yearendtax/yearendtaxContributePopup.do?year=<%=year %>','820','600');
		} else if ("rent" == type) {
			Commons.popupOpen('rent','<%=GROUP_ROOT%>/yt/yearendtax/yearendtaxHousePopup.do?year=<%=year %>&searchType=rent','820','380');
		}
	}
	
	/* 레이어팝업 열기 */
	function getAddressListLayer(){
		$("#addressList").bPopup({
			content:'iframe', //'ajax', 'iframe' or 'image'
			iframeAttr:'scrolling="yes" frameborder="0" width="800px" height="600px"',
			follow: [true, true],
			contentContainer:'.address_content',
			modalClose: false,
            opacity: 0.6,
            positionStyle: 'fixed',
			loadUrl:'<%=GROUP_ROOT%>/yt/yearendtax/yearendtaxAddressPopup.do?type=layer'
		});
	}
	
	/**
	 * 레이어팝업 닫기
	 */
	function layerClose(){ 
		$("#addressList").bPopup().close();
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
				<form name="frm" id="frm" method="post" >
					<input type="hidden" name="year" id="year" value="<%=year %>" />
					<input type="hidden" name="searchType" id="searchType"/>
					<input type="hidden" name="addressCnt" id="addressCnt" value="<%=addressCnt %>"/>
				
					<h2>연말정산 등록</h2>
					<div class="list_btn last_step">
						<div class="list_button tar">
							<button type="button" onclick="javascript:Commons.popupOpen('address','<%=GROUP_ROOT%>/yt/yearendtax/yearendtaxAddressPopup.do','820','600');">우편번호</button>
							<button type="button" onclick="javascript:getYearendtaxInfoAjax('all');">저장</button>
							<button type="button" onclick="javascript:goYearendtaxList();">목록</button>
						</div>
					</div>
					<div class="wrap_enroll_yearend">
						<div class="wrap_list">
							<h3>기본사항</h3>
							<div class="tableA">
								<table class="tbl_list">
									<colgroup>
										<col style="width:80px"/>
										<col style="width:48px"/>
										<col style="width:127px"/>
										<col style=""/>
										<col style="width:148px"/>
										<col style="width:76px"/>
									</colgroup>
									<thead>
										<tr>
											<th colspan="2">입력할 항목</th>
											<th>선택 또는 입력</th>
											<th>항목별 요약설명 및 공제요건</th>
											<th>필수 확인 사항</th>
											<th>세부 항목</th>
										</tr>
									</thead>
									<tbody>
										<tr>
											<th colspan="2">총급여액</th>
											<!-- 비활성 input / select 에 disable ipt_disable 클래스 추가 -->
											<td><input type="text" name="pay_sum" id="pay_sum" class="ipt_disable" value="<%=pay_sum %>" readonly/>원</td>
											<td>연간 근로소득에서 <span>비과세 소득을 뺀</span> 금액을 입력<br />*비과세 소득 : 소득세법 제 12조 제 3호에서 규정하는 비과세 근로소득이 이에 해당됨</td>
											<td rowspan="7" class="essential">정산 기초자료는 총무부에서 생성되어 자동으로 보입니다.<br /><br />종전근무지가 있으신 분은 세부항목을 입력해 주시기 바랍니다.</td>
											<td rowspan="7" class="btn"><button type="button" onclick="javascript:openPopup('previousWorkplace');">종전근무지</button></td>
										</tr>
										<tr>
											<th colspan="2">근로소득공제</th>
											<td><input type="text" name="work_amt" id="work_amt" class="ipt_disable" value="<%=work_amt %>" readonly/>원</td>
											<td>
												<table>
													<thead>
														<tr>
															<th>총급여</th>
															<th>공제액</th>
														</tr>
													</thead>
													<tbody>
														<tr>
															<td>500만원 이하</td>
															<td>총급여액의 100분의 70</td>
														</tr>
														<tr>
															<td>500만원 초과 1,500만원 이하</td>
															<td>350만원 + 500만원 초과하는 금액의 100분의 40</td>
														</tr>
														<tr>
															<td>1,500만 초과 4,500만원 이하</td>
															<td>700만원 + 1,500만원 초과하는 금액의 100분의 15</td>
														</tr>
														<tr>
															<td>4,500만원 초과 1억원 이하</td>
															<td>1,200만원 + 4,500만원 초과하는 금액의 100분의 5</td>
														</tr>
														<tr>
															<td>1억원 초과</td>
															<td>1,475만원 + 1억원 초과하는 금액의 100분의 2</td>
														</tr>
													</tbody>
												</table>
											</td>
										</tr>
										<tr>
											<th colspan="2">근로소득금액</th>
											<td><input type="text" name="work_pay" id="work_pay" class="ipt_disable" value="<%=work_pay %>" readonly/>원</td>
											<td>총급여액에서 근로소득공제를 뺀 금액</td>
										</tr>
										<tr>
											<th rowspan="2">종(전)근무지<br />결정세액</th>
											<th>소득세</th>
											<td><input type="text" name="privous_income_tax" id="privous_income_tax" class="ipt_disable" value="<%=privous_income_tax %>" readonly />원</td>
											<td rowspan="2">해당 과세연도에 종(전)근무지의 근로소득이 있는 경우 종(전)근무지 근로소득 원천징수영수증의 (64)결정세액(소득세)를 입력함</td>
										</tr>
										<tr>
											<th>지방소득세</th>
											<td><input type="text" name="privous_jumin_tax" id="privous_jumin_tax" class="ipt_disable" value="<%=privous_jumin_tax %>" readonly />원</td>
										</tr>
										<tr>
											<th rowspan="2">주(현)근무지<br />기납부세액</th>
											<th>소득세</th>
											<td><input type="text" name="income_tax" id="income_tax" class="ipt_disable" value="<%=income_tax %>" readonly />원</td>
											<td rowspan="2">주(현)근무지에서 매월 급여 지급시 근로소득 간이세액표에 의해 원천징수된 세액인 소득세와 지방소득세 해당금액을 입력함</td>
										</tr>
										<tr>
											<th>지방소득세</th>
											<td><input type="text" name="jing_jumin_tax" id="jing_jumin_tax" class="ipt_disable" value="<%=jing_jumin_tax %>" readonly />원</td>
										</tr>
									</tbody>
								</table>
								<p>
									* 총급여액은 의료비 및 신용카드 등 소득공제 계산 시 활용됨 <br />
									* 근로소득공제는 근무기간에 따른 월할 계산하지 않고 총급여액에 따른 공제금액을 계산함에 유의
								</p>
							</div>	
						</div>
				
						<div class="wrap_list">
							<h3>기본공제</h3>
							<div class="tableA">
								<table class="tbl_list">
									<colgroup>
										<col style="width:43px"/>
										<col style="width:85px"/>
										<col style="width:127px"/>
										<col style=""/>
										<col style="width:148px"/>
										<col style="width:76px"/>
									</colgroup>
									<thead>
										<tr>
											<th colspan="2">입력할 항목</th>
											<th>선택 또는 입력</th>
											<th>항목별 요약설명 및 공제요건</th>
											<th>필수 확인 사항</th>
											<th>세부 항목</th>
										</tr>
									</thead>
									<tbody>
										<tr>
											<th colspan="2">본인기본공제</th>
											<td><input type="text" name="family_person_amt" id="family_person_amt" class="ipt_disable" value="<%=family_person_amt %>" readonly />원</td>
											<td>근로소득자 본인에 대한 기본 공제(연 150만원)</td>
											<td rowspan="9" class="essential">부양가족 입력은 필수 입력 사항입니다.<br /><br />주민번호 별 해당 항목을 선택하시면 정산 계산에 이용됩니다.<br /><br />직계존속 : 조상으로부터 나에게 이르는 혈족<br />직계비속 : 나로부터 직계로 내려가는 혈족</td>
											<td rowspan="9" class="btn"><button type="button" onclick="javascript:openPopup('family');">부양가족</button></td>
										</tr>
										<tr>
											<th colspan="2">배우자공제</th>
											<td>
												<!-- 비활성 선택 영역 span에 disable 클래스 추가-->
												<span class="disable">
													<input type="radio" name="family_mate_amt" id="family_mate_amt1" <% if ("1500000".equals(family_mate_amt)) { %> checked <% } %> disabled /><label for="family_mate_amt1">예</label>
													<input type="radio" name="family_mate_amt" id="family_mate_amt2" <% if ("0".equals(family_mate_amt)) { %> checked <% } %> disabled /><label for="family_mate_amt2">아니요</label>
												</span>
											</td>
											<td>연간 소득금액 합계액 100만원 이하인 배우자에 대해 기본공제<br />(연 150만원) <span>* 사실혼은 제외됨에 유의</span></td>
										</tr>
										<tr>
											<th colspan="2">부양가족공제</th>
											<td></td>
											<td>거주자(배우자 포함)와 생계를 같이하는 연간 소득 금액 합계액 100만원 이하인 부양가족에 대한 기본 공제(1명당 연 150만원)<br />(부양가족이 기본공제대상에 포함되기 위해서는 일반적으로 나이 제한을 적용받음. <span>다만, 부양가족이 소득세법에 따른 장애인에 해당하는 경우 나이 제한을 적용받지 아니함</span></td>
										</tr>
										<tr>
											<th rowspan="6">부양가족공제</th>
											<th>직계존속</th>
											<td class="kinship"><input type="text" name="family_direct_ancestor_cnt" id="family_direct_ancestor_cnt" class="ipt_disable" value="<%=family_direct_ancestor_cnt %>" readonly /></td>
											<td>- 거주자의 직계존속으로서 만 60세 이상(1954.12.31 이전 출생)<br /><span>* 직계존속이 재혼한 경우에는 직계존속과 혼인(사실혼은 제외한다.)중임이 증명되는 사람 포함</span></td>
										</tr>
										<tr>
											<th rowspan="2">직계비속</th>
											<td class="kinship">자녀&middot;입양자<input type="text" name="family_direct_descendant_cnt" id="family_direct_descendant_cnt" class="ipt_disable" value="<%=family_direct_descendant_cnt %>" readonly /></td>
											<td>- 거주자의 직계비속으로서 만 20세 이하(1994.1.1 이후 출생)<br />&middot; 거주자의 직계비속<br />&middot; 거주자의 배우자가 재혼한 경우 당해 배우자가 종전의 배우자와의 혼인(사실혼 제외)중에 출산한 자<br /><span>* 해당 직계비속과 그 배우자가 모두 장애인에 해당하는 경우 그 배우자를 포함</span></td>
										</tr>
										<tr>
											<th class="tal">그외 직계비속</th>
											<td>- 민법 또는 입양촉진 및 절차에 관한 특례법에 따라 입양한 양자 및 사실상 입양상태에 있는 사람으로서 거주자와 생계를 같이하는 사람(만 20세 이하)</td>
										</tr>
										<tr>
											<th>형제자매</th>
											<td class="kinship"><input type="text" name="family_brother_cnt" id="family_brother_cnt" class="ipt_disable" value="<%=family_brother_cnt %>" readonly /></td>
											<td>- 거주자(배우자 포함)의 형제자매로서 20세 이하(1994.1.1 이후 출생) 또는 60세 이상(1954.12.31 이전 출생)인 사람<br /><span>* 형제자매의 배우자는 공제대상에 해당하지 아니함</span></td>
										</tr>
										<tr>
											<th>수급자</th>
											<td class="kinship"><input type="text" name="foster_child_cnt" id="foster_child_cnt" class="ipt_disable" value="<%=foster_child_cnt %>" readonly /></td>
											<td>- 국민기초생활 보장법 제2조 제2호의 수급자</td>
										</tr>
										<tr>
											<th>위탁아동</th>
											<td class="kinship"><input type="text" name="pensioner_cnt" id="pensioner_cnt" class="ipt_disable" value="<%=pensioner_cnt %>" readonly /></td>
											<td>- 아동복지법에 따른 가정위탁을 받아 양육하는 아동으로서 해당 과세기간에 6개월 이상 직접 양육한 위탁아동	<br /><span>* 직전 과세기간에 소득공제를 받지 못한 경우에는 해당 위탁아동에 대한 직전 과세기간의 위탁아동을 포함하여 계산</span></td>
										</tr>	
									</tbody>
								</table>
								<p>
									※ 연간 소득금액 합계액이 100만원을 초과하는 배우자 및 부양가족은 기본공제 대상에 해당하지 아니합니다. <br />
									(연간 소득금액 합계액은 홈텍스(<a href="http://www.hometax.go.kr" target="_blank">www.hometax.go.kr</a>) &gt; 증명발급 &gt; 소득증명 발급을 통해 확인할 수 있음)
								</p>
							</div>	
						</div>
				
						<div class="wrap_tit">
							<h3>추가입력사항</h3>
							<button type="button" data-text-swap="전체닫기" class="btn_show">전체열기</button>
						</div>
				
						<div class="wrap_list list_add">
							<h3>
								추가공제
								<input type="checkbox" name="" class="ipt_chk" id="chk1" /><label for="chk1">입력 여부 선택</label>
								<button type="button" data-text-swap="닫기" class="btn_ipt">입력하기</button>
							</h3>
							<div class="tableA" style="display:none">
								<table class="tbl_list">
									<colgroup>
										<col style="width:121px"/>
										<col style="width:73px"/>
										<col style=""/>
										<col style=""/>
										<col style="width:148px"/>
										<col style="width:76px"/>
									</colgroup>
									<thead>
										<tr>
											<th>입력할 항목</th>
											<th colspan="2">선택 또는 입력</th>
											<th>항목별 요약설명 및 공제요건</th>
											<th>필수 확인 사항</th>
											<th>세부 항목</th>
										</tr>
									</thead>
									<tbody>
										<tr>
											<th>경로우대공제</th>
											<td>70세 이상</td>
											<td class="kinship"><input type="text" name="family_old2_cnt" id="family_old2_cnt" class="ipt_disable" value="<%=family_old2_cnt %>" readonly /></td>
											<td>기본공제대상자가 만 70세 이상(1944.12.31 이전 출생)인 경우 1명당 100만원 공제</td>
											<td rowspan="4" class="essential">항목별 요약설명 및 공제요건은 참조만 하시기 바랍니다.<br /><br />부양가족 입력에서 등록된 내용이 반영됩니다.</td>
											<td rowspan="4"></td>
										</tr>
										<tr>
											<th>장애인공제</th>
											<td></td>
											<td class="kinship"><input type="text" name="family_disabled_person_cnt" id="family_disabled_person_cnt" class="ipt_disable" value="<%=family_disabled_person_cnt %>" readonly /></td>
											<td>
												기본공제대상자 중 소득세법에 따른 장애인에 해당하는 경우 1명당 연 200만원 공제<br />※ 소득세법에 따른 장애인의 범위
												<ol>
													<li>① 장애인 복지법에 의한 장애인</li>
													<li>② 국가유공자 등 예우 및 지원에 관한 법률에 의한 상이자 및 이와 유사한 자로서 근로능력이 없는 자</li>
													<li>③ ① 내지 ② 외에 항시 치료를 요하는 중증환자</li>
												</ol>	
												* 항시 치료를 요하는 중증환자라 함은 지병에 의해 평상시 치료를 요하고 취학&middot;취업이 곤란한 상태에 있는 자<br />&lt;장애인공제를 받고자 하는 경우 장애인등록증 사본 또는 소득세법에서 정한 장애인증명서 등을 제출해야 함&gt;
											</td>
										</tr>
										<tr>
											<th>부녀자공제</th>
											<td colspan="2">
												<span class="disable">
													<input type="radio" name="family_women_amt" id="family_women_amt1" <% if ("500000".equals(family_women_amt)) { %> checked <% } %> disabled /><label for="family_women_amt1">예</label>
													<input type="radio" name="family_women_amt" id="family_women_amt2" <% if ("0".equals(family_women_amt)) { %> checked <% } %> disabled /><label for="family_women_amt2">아니요</label>
												</span>							
											</td>
											<td>근로자 본인이 여성(해당 과세기간의 근로소득금액이 3천만원 이하자)인 경우로서 다음의 어느 하나에 해당하는 경우에만 연 50만원 공제 가능<br />- 배우자가 있는 여성(남편 소득유무와는 관계없음)<br />- 배우자가 없는 여성의 경우 부양가족이 있는 세대주에 해당하는 경우</td>
										</tr>
										<tr>
											<th>한부모공제</th>
											<td colspan="2">
												<span class="disable">
													<input type="radio" name="family_single_parent_amt" id="family_single_parent_amt1" <% if ("1000000".equals(family_single_parent_amt)) { %> checked <% } %> disabled /><label for="family_single_parent_amt1">예</label>
													<input type="radio" name="family_single_parent_amt" id="family_single_parent_amt2" <% if ("0".equals(family_single_parent_amt)) { %> checked <% } %> disabled /><label for="family_single_parent_amt2">아니요</label>
												</span>
											</td>
											<td>배우자가 없는 자로서 기본공제대상인 직계비속 또는 입양자가 있는 경우 연 100만원 공제 가능<br /><span>* 부녀자공제와 중복적용 배제 : 한부모공제 적용</span></td>
										</tr>
									</tbody>
								</table>
							</div>	
						</div>
				
						<div class="wrap_list list_add">
							<h3>
								연금보험료공제
								<input type="checkbox" name="" class="ipt_chk" id="chk2" /><label for="chk2">입력 여부 선택</label>
								<button type="button" data-text-swap="닫기" class="btn_ipt">입력하기</button>
							</h3>
							<div class="tableA" style="display:none">
								<table class="tbl_list">
									<colgroup>
										<col style="width:40px"/>
										<col style="width:81px"/>
										<col style="width:127px"/>
										<col style=""/>
										<col style="width:148px"/>
										<col style="width:76px"/>
									</colgroup>
									<thead>
										<tr>
											<th colspan="2">입력할 항목</th>
											<th>선택 또는 입력</th>
											<th>항목별 요약설명 및 공제요건</th>
											<th>필수 확인 사항</th>
											<th>세부 항목</th>
										</tr>
									</thead>
									<tbody>
										<tr>
											<th rowspan="5">공적연금보험료</th>
											<th>국민연금</th>
											<td><input type="text" name="kuk_yeon_amt" id="kuk_yeon_amt" class="ipt_disable" value="<%=kuk_yeon_amt %>" readonly />원</td>
											<td>국민연금법에 따라 부담하는 연금보험료(사용자 부담금을 제외)로 전액 공제</td>
											<td class="essential">자동으로 반영 되어 나옵니다.</td>
											<td rowspan="5"></td>
										</tr>
										<tr>
											<th>공무원연금</th>
											<td></td>
											<td>공무원연금법에 따라 근로자가 부담하는 기여금(또는 부담금)으로 전액공제</td>
											<td rowspan="4" class="essential">하나제약의 모든 직원은 해당 사항이 없습니다.</td>
										</tr>
										<tr>
											<th>군인연금</th>
											<td></td>
											<td>군인연금법에 따라 근로자가 부담하는 기여금(또는 부담금)으로 전액공제</td>
										</tr>
										<tr>
											<th>사립학교<br />교직원연금</th>
											<td></td>
											<td>사립학교직원연금법에 따라 근로자가 부담하는 기여금(또는 부담금)으로 전액공제</td>
										</tr>
										<tr>
											<th>별정우체국연금</th>
											<td></td>
											<td>별정우체국법에 따라 근로자가 부담하는 기여금(또는 부담금)으로 전액공제</td>
										</tr>
									</tbody>
								</table>
							</div>	
						</div>
				
						<div class="wrap_list list_add">
							<h3>
								특별공제(보험료)
								<input type="checkbox" name="" class="ipt_chk" id="chk3" /><label for="chk3">입력 여부 선택</label>
								<button type="button" data-text-swap="닫기" class="btn_ipt">입력하기</button>
							</h3>
							<div class="tableA" style="display:none">
								<table class="tbl_list">
									<colgroup>
										<col style="width:121px"/>
										<col style="width:60px"/>
										<col style="width:127px"/>
										<col style=""/>
										<col style="width:148px"/>
										<col style="width:76px"/>
									</colgroup>
									<thead>
										<tr>
											<th>입력할 항목</th>
											<th colspan="2">선택 또는 입력</th>
											<th>항목별 요약설명 및 공제요건</th>
											<th>필수 확인 사항</th>
											<th>세부 항목</th>
										</tr>
									</thead>
									<tbody>
										<tr>
											<th>건강보험료 등</th>
											<th>납입금액</th>
											<td><input type="text" name="insurance_health_amt" id="insurance_health_amt" class="ipt_disable" value="<%=insurance_health_amt %>" readonly />원</td>
											<td>국민건강보험법 또는 노인장기요양법에 따라 근로자가 부담하는 보험료 (전액 공제)</td>
											<td rowspan="2" class="essential">건강보험료, 고용보험료는 자동으로 나타납니다.</td>
											<td rowspan="2"></td>
										</tr>
										<tr>
											<th>고용보험료</th>
											<th>납입금액</th>
											<td><input type="text" name="insurance_employ_amt" id="insurance_employ_amt" class="ipt_disable" value="<%=insurance_employ_amt %>" readonly />원</td>
											<td>고용보험법에 따라 근로자가 부담하는 보험료(전액공제)
											</td>
										</tr>
									</tbody>
								</table>
							</div>	
						</div>
				
						<div class="wrap_list list_add">
							<h3>
								특별공제(주택자금)
								<input type="checkbox" name="" class="ipt_chk" id="chk4" /><label for="chk4">입력 여부 선택</label>
								<button type="button" data-text-swap="닫기" class="btn_ipt">입력하기</button>
							</h3>
							<div class="tableA" style="display:none">
								<table class="tbl_list">
									<colgroup>
										<col style="width:101px"/>
										<col style="width:45px"/>
										<col style="width:50px"/>
										<col style="width:127px"/>
										<col style=""/>
										<col style=""/>
										<col style="width:148px"/>
										<col style="width:76px"/>
									</colgroup>
									<thead>
										<tr>
											<th>입력할 항목</th>
											<th colspan="3">선택 또는 입력</th>
											<th colspan="2">항목별 요약설명 및 공제요건</th>
											<th>필수 확인 사항</th>
											<th>세부 항목</th>
										</tr>
									</thead>
									<tbody>
										<tr>
											<th>주택임차차입금</th>
											<th colspan="2">원리금상환액</th>
											<td><input type="text" name="house_lease_loan_amt" id="house_lease_loan_amt" value="<%=house_lease_loan_amt %>" class="onlyNumber" />원</td>
											<td colspan="2">
												<ul>
													<li>○ 공제대상 : 무주택 세대의 세대주(세대주가 주택 관련 공제를 받지 않은 경우 세대원도 가능)로서 근로소득이 있는 자<br />(단, 거주자간 차입의 경우 해당 과세기간 총급여액이 5천만원 이하자) - 단독 세대주 포함</li>
													<li>○ 공제금액 : 원리금상환액 x 40%</li>
													<li>○ 공제한도 : 연 300만원(주택마련저축 납입액 공제와 합하여 연 300만원을 초과할 수 없음)</li>						
												</ul>
											</td>
											<td rowspan="7" class="essential">주택임차차입금 원리금 상환액은 아래 1~2번을 확인하고 입력 바랍니다.<br /><br />1. 금융기관을 이용하는 경우는 화면에 바로 입력합니다.<br />2. 거주자간 : 금전소비대차계약, 임대차계약인 경우에는 주택계약 정보를 등록하여 합니다.<br /><br />장기주택저당차입금은 화면에 바로 입력하시기 바랍니다.</td>
											<td rowspan="7" class="btn"><button type="button" onclick="javascript:openPopup('housingFund');">주택계약</button></td>
										</tr>
										<tr>
											<th rowspan="6">장기주택<br />저당차입금<br />이자상환액</th>
											<th colspan="3">구분</th>
											<th>공제내용</th>
											<th>주택자금 공제한도</th>
										</tr>
										<tr>
											<th rowspan="3">2011년 이전 차입분</th>
											<th>15년 미만<br />(600만원 한도)</th>
											<td><input type="text" name="house_security_loan1_amt" id="house_security_loan1_amt" value="<%=house_security_loan1_amt %>" class="onlyNumber" />원</td>
											<td>'03.12.31 이전 차입분으로 상환기간 10년 이상 15년 미만인 경우에 이자상환액을 입력하여 이 경우 공제한도는 연 600만원을 적용함</td>
											<td rowspan="5">(주택임차차입금원리금상환액공제+월세액 소득공제+장기주택저당차입금이자상환액공제+주택마련저축납입액 공제)금액이 해당 장기주택이자상환액 공제 한도를 초과하는 경우 그 초과하는 금액은 없는 것으로 함</td>
										</tr>
										<tr>
											<th>15년-29년<br />(1,000만원한도)</th>
											<td><input type="text" name="house_security_loan2_amt" id="house_security_loan2_amt" value="<%=house_security_loan2_amt %>" class="onlyNumber" />원</td>
											<td>'12.1.1 이전 차입분으로 다음에 해당하는 경우 공제한도는 연 100만원을 적용함<br />- 상환기간 15년 이상 30년 미만인 경우('12년 이후 다른 금융기관 등으로 차입금 이전하는 경우 포함)<br />- 조세특례제한법 제98조의 3에 따른 양도소득세 과세특례대상을 2009년 2월 12일부터 2010년 2월 11일까지의 기간중에 최초로 취득하는 경우 상환기간 5년 이상</td>
										</tr>
										<tr>
											<th>30년이상<br />(1,500만원한도)</th>
											<td><input type="text" name="house_security_loan3_amt" id="house_security_loan3_amt" value="<%=house_security_loan3_amt %>" class="onlyNumber" />원</td>
											<td>다음에 해당하는 경우 공제한도는 연 1,500만원을 적용함<br />- '12.1.1 이전 차입분으로 상환기간 30년 이상인 경우('12년 이후 다른 금융기관등으로 차입금 이전하는 경우 포함)</td>
										</tr>
										<tr>
											<th rowspan="2">2012년 이후 차입분</th>
											<th>고정금리&middot;비거치 상환대출<br />(1,500만원 한도)</th>
											<td><input type="text" name="house_security_loan4_amt" id="house_security_loan4_amt" value="<%=house_security_loan4_amt %>" class="onlyNumber" />원</td>
											<td>- '12.1.1 이후 신규 차입분(차입금 상환 기간 연장 포함)으로서 상환금액이 15년 이상이고, 차입금의 70% 이상의 금액에 대한 이자를 고정금리 방식으로 지급하는 경우와 원리금의 70% 이상을 비거치식으로 분할상환하는 경우<br />'12.1.1 이후 신규 차입금(차입금 상환기간 연장 포함)으로서 상환</td>
										</tr>
										<tr>
											<th>기타대출<br />(500만원 한도)</th>
											<td><input type="text" name="house_security_loan5_amt" id="house_security_loan5_amt" value="<%=house_security_loan5_amt %>" class="onlyNumber" />원</td>
											<td>기간이 15년 이상인 차입금(고정금리 및 비거치식 대출 제외)의 이자상환액을 입력하며, 이 경우 공제한도는 연 500만원을 적용함</td>
										</tr>
									</tbody>
								</table>
							</div>	
						</div>
				
						<div class="wrap_list list_add">
							<h3>
								개인연금저축공제
								<input type="checkbox" name="" class="ipt_chk" id="chk5" /><label for="chk5">입력 여부 선택</label>
								<button type="button" data-text-swap="닫기" class="btn_ipt">입력하기</button>
							</h3>
							<div class="tableA" style="display:none">
								<table class="tbl_list">
									<colgroup>
										<col style="width:121px"/>
										<col style="width:60px"/>
										<col style="width:127px"/>
										<col style=""/>
										<col style="width:148px"/>
										<col style="width:76px"/>
									</colgroup>
									<thead>
										<tr>
											<th>입력할 항목</th>
											<th colspan="2">선택 또는 입력</th>
											<th>항목별 요약설명 및 공제요건</th>
											<th>필수 확인 사항</th>
											<th>세부 항목</th>
										</tr>
									</thead>
									<tbody>
										<tr>
											<th>개인연금저축</th>
											<th>납입액</th>
											<td><input type="text" name="personal_annuity_amt" id="personal_annuity_amt" class="ipt_disable" value="<%=personal_annuity_amt %>" readonly />원</td>
											<td>근로자 본인 명의로 2000.12.31 이전에 가입하여 해당 과세기간에 불입한 금액<br />- 공제금액 : 납입액 x 40%<br />- 공제한도 : 연 72만원</td>
											<td class="essential">개인연금저축에 대한 금융기관 코드 번호는 국세청 신고 필수 DATA 입니다.</td>
											<td class="btn"><button type="button" onclick="javascript:openPopup('savingP');">개인연금</button></td>
										</tr>
									</tbody>
								</table>
							</div>	
						</div>
				
						<div class="wrap_list list_add">
							<h3>
								그밖의 소득공제(주택마련저축)
								<input type="checkbox" name="" class="ipt_chk" id="chk6" /><label for="chk6">입력 여부 선택</label>
								<button type="button" data-text-swap="닫기" class="btn_ipt">입력하기</button>
							</h3>
							<div class="tableA" style="display:none">
								<table class="tbl_list">
									<colgroup>
										<col style="width:121px"/>
										<col style="width:60px"/>
										<col style="width:127px"/>
										<col style=""/>
										<col style="width:148px"/>
										<col style="width:76px"/>
									</colgroup>
									<thead>
										<tr>
											<th>입력할 항목</th>
											<th colspan="2">선택 또는 입력</th>
											<th>항목별 요약설명 및 공제요건</th>
											<th>필수 확인 사항</th>
											<th>세부 항목</th>
										</tr>
									</thead>
									<tbody>
										<tr>
											<th>청약저축</th>
											<th>납입액</th>
											<td><input type="text" name="etc_subscription_deposit_amt" id="etc_subscription_deposit_amt" class="ipt_disable" value="<%=etc_subscription_deposit_amt %>" readonly />원</td>
											<td>주택법에 따른 청약저축에 납입한 금액(연 납입액 120만원 이하 에 한함)</td>
											<td rowspan="3" class="essential">주택저축에 관련된 내용은 세부항목을 입력하여 합니다.<br /><br />주택저축에 대한 금융기관 코드 번호는 국세청 신고 필수 DATA 입니다.</td>
											<td rowspan="3" class="btn"><button type="button" onclick="javascript:openPopup('savingE');">주택저축</button></td>
										</tr>
										<tr>
											<th>근로자주택마련저축</th>
											<th>납입액</th>
											<td><input type="text" name="etc_home_mortgage_amt" id="etc_home_mortgage_amt" class="ipt_disable" value="<%=etc_home_mortgage_amt %>" readonly />원</td>
											<td>법률 제 7030호 한국주택금융공사법 부칙 제2조에 따라 폐지된 「근로자의 주거안정과 묵돈마련지원에 따른 법률」에 따른 근로자 주택마련저축으로 한다.(월 납입액 15만원 이하에 한함)</td>
										</tr>
										<tr>
											<th>주택청약종합저축</th>
											<th>납입액</th>
											<td><input type="text" name="etc_house_subscr_deposit_amt" id="etc_house_subscr_deposit_amt" class="ipt_disable" value="<%=etc_house_subscr_deposit_amt %>" readonly />원</td>
											<td>저축가입자가 금융회사 등에 무주택확인서를 제출한 과세연도 이후에 납입한 금액만 해당됨(연 납입액 120만원 이하에 한함)</td>
										</tr>
									</tbody>
								</table>
							</div>	
						</div>
				
						<div class="wrap_list list_add">
							<h3>
								그밖의 소득공제(신용카드 등 사용금액)
								<input type="checkbox" name="" class="ipt_chk" id="chk7" /><label for="chk7">입력 여부 선택</label>
								<button type="button" data-text-swap="닫기" class="btn_ipt">입력하기</button>
							</h3>
							<div class="tableA" style="display:none">
								<table class="tbl_list">
									<colgroup>
										<col style="width:121px"/>
										<col style="width:60px"/>
										<col style="width:127px"/>
										<col style=""/>
										<col style="width:148px"/>
										<col style="width:76px"/>
									</colgroup>
									<thead>
										<tr>
											<th>입력할 항목</th>
											<th colspan="2">선택 또는 입력</th>
											<th>항목별 요약설명 및 공제요건</th>
											<th>필수 확인 사항</th>
											<th>세부 항목</th>
										</tr>
									</thead>
									<tbody>
										<tr>
											<th>㉮신용카드<br />(전통시장 · 대중교통 제외)</th>
											<th>사용금액</th>
											<td><input type="text" name="etc_credt_amt" id="etc_credt_amt" class="ipt_disable" value="<%=etc_credt_amt %>" readonly />원</td>
											<td>신용카드를 사용하여 그 대가를 지급하는 금액</td>
											<td rowspan="5" class="essential">신용카드 등 사용금액은 세부항목에 입력하시면 화면에 나타납니다.</td>
											<td rowspan="5" class="btn"><button type="button" onclick="javascript:openPopup('creditCard');">신용카드</button></td>
										</tr>
										<tr>
											<th>㉯현금영수증<br />(전통시장 · 대중교통 제외)</th>
											<th>사용금액</th>
											<td><input type="text" name="etc_cash_receipt_amt" id="etc_cash_receipt_amt" class="ipt_disable" value="<%=etc_cash_receipt_amt %>" readonly />원</td>
											<td>현금영수증(현금거래 사실을 확인받는 것을 포함)</td>
										</tr>
										<tr>
											<th>㉰직불카드 등<br />(전통시장 · 대중교통 제외)</th>
											<th>사용금액</th>
											<td><input type="text" name="etc_direct_amt" id="etc_direct_amt" class="ipt_disable" value="<%=etc_direct_amt %>" readonly />원</td>
											<td>직불카드, 실지명의가 확인되는 기명식선불카드, 기명식선불전자지급수단, 기명식 전자화폐를 사용하여 대가로 지급한 금액</td>
										</tr>
										<tr>
											<th>㉱전통시장사용분</th>
											<th>사용금액</th>
											<td><input type="text" name="etc_market_amt" id="etc_market_amt" class="ipt_disable" value="<%=etc_market_amt %>" readonly />원</td>
											<td>전통시장에서 사용한 신용카드, 직불 · 선불카드, 현금영수증 사용금액</td>
										</tr>
										<tr>
											<th>㉲대중교통 이용분</th>
											<th>사용금액</th>
											<td><input type="text" name="etc_pubric_transport_amt" id="etc_pubric_transport_amt" class="ipt_disable" value="<%=etc_pubric_transport_amt %>" readonly />원</td>
											<td>대중교통을 신용카드, 직불 · 선불카드, 현금영수증으로 사용한 금액</td>
										</tr>
										<tr>
											<th>㉳2013년 본인<br />신용카드 등 사용액</th>
											<th>사용금액</th>
											<td><input type="text" name="compute_1" id="compute_1" class="ipt_disable" value="<%=compute_1 %>" readonly />원</td>
											<td>2013.1.1~2014.12.31 까지 본인이 사용한 신용카드, 현금영수증, 직불카드 · 선불카드 전체 사용액</td>
											<td rowspan="4" class="essential">"본인"만 해당됩니다.<br />㉳ 항목 보다 ㉴ 항목이 큰 경우에만 추가 공제 합니다.<br /><br />공제 금액은 ㉵와 ㉶를 비교하여 계산합니다.</td>
											<td rowspan="4"></td>
										</tr>
										<tr>
											<th>㉴2014년 본인<br />신용카드 등 사용액</th>
											<th>사용금액</th>
											<td><input type="text" name="compute_3" id="compute_3" class="ipt_disable" value="<%=compute_3 %>" readonly />원</td>
											<td>2014.1.1~2014.12.31 까지 본인이 사용한 신용카드, 현금영수증, 직불카드 · 선불카드 전체 사용액</td>
										</tr>
										<tr>
											<th>㉵2013년 본인의 추가<br />공제율 사용액</th>
											<th>사용금액</th>
											<td><input type="text" name="compute_2" id="compute_2" class="ipt_disable" value="<%=compute_2 %>" readonly />원</td>
											<td>신용카드 사용분을 제외한 2013.1.1~2013.12.31 까지 본인이 사용한 전통시장 · 대중교통 · 현금영수증 · 직불카드 · 선불카드 사용액의 합계액 -> 30% 공제율 대상 사용액</td>
										</tr>
										<tr>
											<th>㉶2014년 하반기 본인<br />의 추가공제율 사용액</th>
											<th>사용금액</th>
											<td><input type="text" name="compute_4" id="compute_4" class="ipt_disable" value="<%=compute_4 %>" readonly />원</td>
											<td>신용카드 사용분을 제외한 2014.7.1~2014.12.31 까지 본인이 사용한 전통시장 · 대중교통 · 현금영수증 · 직불카드 · 선불카드 사용액의 합계액 -> 30% 공제율 대상 사용액</td>
										</tr>
									</tbody>
								</table>
							</div>	
						</div>
				
						<div class="wrap_list list_add">
							<h3>
								그밖의 소득공제(기타)
								<input type="checkbox" name="" class="ipt_chk" id="chk8" /><label for="chk8">입력 여부 선택</label>	
								<button type="button" data-text-swap="닫기" class="btn_ipt">입력하기</button>
							</h3>
							<div class="tableA" style="display:none">
								<table class="tbl_list">
									<colgroup>
										<col style="width:121px"/>
										<col style=""/>
										<col style="width:65px"/>
										<col style="width:127px"/>
										<col style=""/>
										<col style="width:148px"/>
										<col style="width:76px"/>
									</colgroup>
									<thead>
										<tr>
											<th>입력할 항목</th>
											<th colspan="3">선택 또는 입력</th>
											<th>항목별 요약설명 및 공제요건</th>
											<th>필수 확인 사항</th>
											<th>세부 항목</th>
										</tr>
									</thead>
									<tbody>
										<tr>
											<th>소기업 · 소상공인 공제부금 소득공제</th>
											<th colspan="2">납입액</th>
											<td></td>
											<td>거주자 중소기업협동조합법 제115조에 따른 소기업 · 소상공인 공제에 가입하여 해당 과세기간에 납부하는 공제부금<br />- 공제금액 : 납입액 전액<br />- 공제한도 : 연 300만원</td>
											<td rowspan="11" class="essential">화면에 직접 입력하시기 바랍니다.</td>
											<td rowspan="11"></td>
										</tr>
										<tr>
											<th rowspan="7">투자조합 출자공제</th>
											<th rowspan="2">2012년</th>
											<th>투자금액<br />(10%)</th>
											<td></td>
											<td rowspan="7">거주자가 중소기업창업투자조합 등에 2014년 12월 31일까지 출자 또는 투자한 금액의 100분의 10(개인이 직접 또는 개인투자조합을 통해 벤처기업에 투자하는 경우 2012년분 : 100분의 20, 2013년분 : 100분의 30, 2014년분 : 5천만원 이하분 100분의 50 : 5천만원 초과분 100분의 30)에 상당하는 금액을 그 출자일 또는 투자일이 속하는 과세연도부터 2년이 되는 날이 속하는 과세연도까지 거주자가 선택하는 1과세연도의 종합소득금액에서 공제<br />- 공제한도 : 근로소득금액의 40%(2013 이전 40%)<br />* 농어촌특별세 과세대상
											</td>
										</tr>
										<tr>
											<th>투자금액<br />(20%)</th>
											<td></td>
										</tr>
										<tr>
											<th rowspan="2">2013년</th>
											<th>투자금액<br />(10%)</th>
											<td></td>
										</tr>
										<tr>
											<th>투자금액<br />(30%)</th>
											<td></td>
										</tr>
										<tr>
											<th rowspan="3">2014년 이후</th>
											<th>투자금액<br />(10%)</th>
											<td></td>
										</tr>
										<tr>
											<th>투자금액<br />(50%)</th>
											<td></td>
										</tr>
										<tr>
											<th>투자금액<br />(30%)</th>
											<td></td>
										</tr>
										<tr>
											<th>우리사주출연금<br />소득공제</th>
											<th colspan="2">출연금</th>
											<td><input type="text" name="etc_employ_stock_amt" id="etc_employ_stock_amt"  value="<%=etc_employ_stock_amt %>" class="onlyNumber" />원</td>
											<td>우리사주조합원이 자사주를 취득하기 위하여 우리사주조합에 출자하는 경우 해당 연도의 출자금액과 400만원 중 적은 금액을 공제</td>
										</tr>
										<tr>
											<th>고용유지 중소기업<br />근로자 소득공제</th>
											<th colspan="2">임금감소액<br />(직전 과세연도의 해당 근로자 연간 임금총액 - 해당 과세연도의 해당 근로자 연간 임금총액)</th>
											<td></td>
											<td>고용유지중소기업에 근로를 제공하는 상시근로자에 대해서 2015년 12월 31일이 속하는 과세연도까지 다음 산식에 따라 계산한 금액을 해당 과세연도의 근로소득금액에서 공제<br />- 공제금액계산 (직전 과세연도의 해당 근로자 연간 임금총액<br />- 해당 과세연도의 해당 근로자 연간 임금총액) * 50%<br />- 공제한도 : 1천만원</td>
										</tr>
										<tr>
											<th>목돈 안드는 전세<br />이자상환액공제</th>
											<th colspan="2">이자상환액</th>
											<td><input type="text" name="etc_chunk_money_amt" id="etc_chunk_money_amt"  value="<%=etc_chunk_money_amt %>" class="onlyNumber" />원</td>
											<td>2015.12.31 까지 임대인이 묵돈 안드는 전세보증금을 금융회사로부터 차입하고 임차인이 해당 차입금의 이자를 상환하는 경우 임대인의 해당 과세기간의 종합소득금액에서 공제<br />- 공제금액 : 임차인의 이자상환액 x 40%<br />- 공제한도 : 연 300만원</td>
										</tr>
										<tr>
											<th>장기집합<br />투자증권저축</th>
											<th colspan="2">납입금액</th>
											<td><input type="text" name="etc_long_invest_stock_amt" id="etc_long_invest_stock_amt" class="ipt_disable" value="<%=etc_long_invest_stock_amt %>" readonly />원</td>
											<td>○ 가입대상 : 총급여액 5,000만원 이하 근로자<br />○ 펀드요건<br />- 자산총액 40%이상을 국내주식에 투자하는 장기 적립식 펀드<br />- 연 납입한도 600만원<br />- 계약기간 10년 이상<br />- 공제금액 : 연 납입액 x 40%<br />- 공제한도 : 연 240만원<br />- 적용기한 : '15.12.31까지 가입분</td>
											<td class="essential">장기집합투자증권저축은 금융기관 계좌번호별 납입금액 등 세부항목을 입력하셔야 합니다.</td>
											<td class="btn"><button type="button" onclick="javascript:openPopup('savingL');">증권저축</button></td>
										</tr>
									</tbody>
								</table>
							</div>	
						</div>
				
						<div class="wrap_list list_add">
							<h3>세액감면 및 세액공제
								<input type="checkbox" name="" class="ipt_chk" id="chk9" /><label for="chk9">입력 여부 선택</label>
								<button type="button" data-text-swap="닫기" class="btn_ipt">입력하기</button>
							</h3>
							<div class="tableA" style="display:none">
								<table class="tbl_list">
									<colgroup>
										<col style="width:32px"/>
										<col style=""/>
										<col style=""/>
										<col style=""/>
										<col style=""/>
										<col style="width:127px"/>
										<col style=""/>
										<col style=""/>
										<col style="width:148px"/>
										<col style="width:76px"/>
									</colgroup>
									<thead>
										<tr>
											<th colspan="3">입력할 항목</th>
											<th colspan="3">선택 또는 입력</th>
											<th colspan="2">항목별 요약설명 및 공제요건</th>
											<th>필수 확인 사항</th>
											<th>세부 항목</th>
										</tr>
									</thead>
									<tbody>
										<tr>
											<th rowspan="7">세액감면</th>
											<th colspan="2">소득세법</th>
											<th colspan="2">감면세액</th>
											<td><input type="text" name="etc_income_tax_reduction_amt" id="etc_income_tax_reduction_amt" class="onlyNumber" value="<%=etc_income_tax_reduction_amt %>"/>원</td>
											<td colspan="2">○ 정부간 협약에 의하여 우리나라에 파견된 외국인이 당사국의 정부로부터 받는 급여<br />- 감면세액 계산<br />산출세액 x (감면대상 근로소득금액/근로소득금액)</td>
											<td class="essential">세액감면 및 세액공제 화면은 변경 사항이 많습니다. 필수 확인 사항을 꼭 참조하시기 바랍니다.</td>
											<td></td>
										</tr>
										<tr>
											<th rowspan="5">조세특례제한법</th>
											<th rowspan="4">중소기업청년</th>
											<th>감면<br />대상</th>
											<th>100%</th>
											<td><input type="text" name="smiymjtc_100" id="smiymjtc_100" class="ipt_disable" value="<%=smiymjtc_100 %>" readonly />원</td>
											<td rowspan="4" colspan="2">○ 근로계약 체결일 현재 연령이 15세 이상 29세 이하 (병역근무기간제외 : 한도 6년)인 사람, 60세 이상인 사람, 장애인이 중소기업에 '12.1.1(60세 이상인 사람, 장애인은 '14.1.1.) ~ '15.12.31. 까지 취업하는 경우 중소기업체에서 받는 근로소득세를 취업일로 부터 3년간 50% 감면 (2013.12.31. 이전 취업자는 100% 감면)<br />- 감면세액의 계산<br />산출세액 x (감면대상 총급여액/해당 근로자 총급여액) x 감면비율 (100%, 50%)</td>
											<td rowspan="4" class="essential">중소기업 감면대상은 관리부서에서 등록된 사람만 가능합니다.<br /><br />해당근로자의 총급여액과 감면세액 자동계산은 정산계산 후 나타납니다.<br />100% + 50% = 총급여</td>
											<td rowspan="4"></td>
										</tr>
										<tr>
											<th>총급<br />여액</th>
											<th>50%</th>
											<td><input type="text" name="smiymjtc_50" id="smiymjtc_50" class="ipt_disable" value="<%=smiymjtc_50 %>" readonly/>원</td>
										</tr>
										<tr>
											<th colspan="2">해당 근로자 총급여액</th>
											<td><input type="text" name="yt_salary" id="yt_salary" class="ipt_disable" value="<%=yt_salary %>" readonly/>원</td>
										</tr>
										<tr>
											<th colspan="2">감면세액<br />자동계산</th>
											<td><input type="text" name="etc_smiymjtc_amt" id="etc_smiymjtc_amt" class="ipt_disable" value="<%=etc_smiymjtc_amt %>" readonly/>원</td>
										</tr>
										<tr>
											<th>외국인 기술자 등</th>
											<th colspan="2">감면세액</th>
											<td></td>
											<td colspan="2">○ 외국인 기술자가 국내에서 최초로 근로를 제공한 날('14.12.31. 이전에 한함)부터 2년이 되는 날이 속하는 달까지 발생한 근로소득세의 50%를 감면<br />('09.12.31. 이전 외국인 기술자는 최초로 근로를 제공한 날로부터 5년간 근로소득세 100% 감면)<br />- 감면세액의 계산<br />산출세액 x (감면대상 근로소득금액/근로소득금액)</td>
											<td rowspan="2" class="essential">하나제약의 직원은 해당사항이 없습니다.</td>
											<td rowspan="2"></td>
										</tr>
										<tr>
											<th colspan="2">조세조약</th>
											<th colspan="2">감면세액</th>
											<td></td>
											<td colspan="2">○ 조세조약의 교직자 조항으로 소득세를 면제받는 교사 및 교수로서 초 · 중 · 고교와 같은 인가된 교육기관 또는 대학에서 강의 또는 연구를 목적으로 근무하는 자에 한하여 적용<br />- 감면세액의 계산<br />산출세액 x (감면대상 근로소득금액/근로소득금액)</td>
										</tr>
										<tr>
											<th rowspan="5">세액공제</th>
											<th colspan="2">근로소득세액공제</th>
											<th colspan="2">(자동계산)</th>
											<td><input type="text" name="yt_earned_income_amt" id="yt_earned_income_amt" class="ipt_disable" value="<%=yt_earned_income_amt %>" readonly/>원</td>
											<td colspan="2">
												○ 근로소득자에 대해 그 근로소득에 대한 종합소득산출세액에서 다음에 해당하는 금액을 공제
												<table>
													<thead>
														<tr>
															<th>근로소득에 대한 종합소득산출세액</th>
															<th>공제액</th>
														</tr>
													</thead>
													<tbody>
														<tr>
															<th>50만원 이하</th>
															<td>산출세액의 100분의 55</td>
														</tr>
														<tr>
															<th>50만원 초과</th>
															<td>27만 5천원 + (50만원을 초과하는 금액의 100분의 30)</td>
														</tr>
													</tbody>
												</table>
												* 근로소득세액공제 한도
												<table>
													<thead>
														<tr>
															<th>총급여액</th>
															<th>세액공제 한도</th>
														</tr>
													</thead>
													<tbody>
														<tr>
															<th>근로소득에 대한</th>
															<td>66만원</td>
														</tr>
														<tr>
															<th>5,500만원 초과<br />7,000만원 이하</th>
															<td>66만원 - [(총급여액 -	 5,500만원) x 1/2]<br />63만원보다 적은 경우 63만원</td>
														</tr>
														<tr>
															<th>7,000만원 초과</th>
															<td>66만원 - [(총급여액 -	 5,500만원) x 1/2]<br />63만원보다 적은 경우 63만원</td>
														</tr>
													</tbody>
												</table>
												○ 다만, 중소기업 취업청년 초득세 감면이 있는 경우 근로소득 세액공제 계산방법<br />- 근로소득세액공제 x (1-감면급여비율*)<br />* 감면급여비율 = 중소기업체로부터 받은 감면대상(100%, 50%) 총급여액 / 해당 근로자의 총 급여액 -> 중소기업 취업 청년 세액감면 계산방법 참조하여 자동계산
											</td>
											<td class="essential">근로소득세액공제는 자동으로 계산됩니다.</td>
											<td></td>
										</tr>
										<tr>
											<th colspan="2">자녀세액공제</th>
											<th colspan="2">(자동계산)</th>
											<td><input type="text" name="etc_children_amt" id="etc_children_amt" class="ipt_disable" value="<%=etc_children_amt %>" readonly/>원</td>
											<td colspan="2">○ 근로자의 기본공제대상자에 해당하는 자녀(입양자 및 위탁아동 포함)에 대해서 다음에 해당하는 금액을 근로소득에 대한 종합소득산출세액에서 공제<br />- 1명 : 연 15만원<br />- 2명 : 연 30만원<br />- 3명 이상인 경우 : 연 30만원과 2명을 초과한 1명당 연 20만원을 합한 금액</td>
											<td class="essential">자녀세액공제는 부양가족에 입력된 정보로 자동으로 계산되어 나타납니다.</td>
											<td></td>
										</tr>
										<tr>
											<th rowspan="3">연금계좌</th>
											<th>과학인</th>
											<th colspan="2">기술공제</th>
											<td></td>
											<td>과학기술인공제회법에 따라 근로자가 부담하는 부담금</td>
											<td rowspan="3"><span>근로자가 연금계좌에 납입한 금액의 합계액(공제대상금액*)의 12%를 근로소득에 대한 종합소득<br />* 공제대상금액 한도 : 연400만원 한도</span></td>
											<td rowspan="2" class="essential">과학기술인공제<br />퇴직연금은<br />하나제약 직원은 관계가 없습니다.</td>
											<td rowspan="2"></td>
										</tr>
										<tr>
											<th>퇴직연금</th>
											<th colspan="2">납입액</th>
											<td></td>
											<td>근로자퇴직급여보장법에 따라 확정 기여형(DC형) 퇴직연금제도 또는 개인형퇴직연금(IRP)제도에 근로자가 부담하는 부담금(확정기여형 퇴직연금 등 회사부담제 제외)</td>
										</tr>
										<tr>
											<th>연금저축</th>
											<th colspan="2">납입액</th>
											<td><input type="text" name="annuity_savng_amt" id="annuity_savng_amt" class="ipt_disable" value="<%=annuity_savng_amt %>" readonly/>원</td>
											<td>근로자 본인 명의로 2001.1.1이후에 연금저축에 가입하여 해당 과세기간에 납입한 금액</td>
											<td class="essential">연금저축은 세부항목을<br />등록하는 경우 나타납니다.</td>
											<td class="btn"><button type="button" onclick="javascript:openPopup('savingA');">연금저축</button></td>
										</tr>
										<tr>
											<th rowspan="2">보험료 세액공제</th>
											<th colspan="2">보장성<br />보험료</th>
											<th colspan="2">납입액</th>
											<td><input type="text" name="insurance_secrity_amt" id="insurance_secrity_amt" class="ipt_disable" value="<%=insurance_secrity_amt %>" readonly/>원</td>
											<td colspan="2">근로자가 지급한 기본공제대상자를 피보험자로 하는 보장성 보험(장애인전용보장성 보험 제외)의 보험료납입액(공제대상금액*)의 12%를 근로소득에 대한 종합소득산출세액에서 공제<br />* 세액공제 대상금액 한도 : 연 100만원 한도</td>
											<td rowspan="2" class="essential">세부항목을 등록하시면 계산되어 나옵니다.</td>
											<td rowspan="2" class="btn"><button type="button" onclick="javascript:openPopup('insurance');">보험료</button></td>
										</tr>
										<tr>
											<th colspan="2">장애인<br />보장성<br />보험료</th>
											<th colspan="2">납입액</th>
											<td><input type="text" name="insurance_disabled_person_amt" id="insurance_disabled_person_amt" class="ipt_disable" value="<%=insurance_disabled_person_amt %>" readonly/>원</td>
											<td colspan="2">근로자가 지급한 기본공제대상자 중 장애인을 피보험자 또는 수익자로 하는 장애인전용보험의 보험료(이 경우 보험계약 또는 보험료납입영수증에 장애인전용보험으로 표시된 것을 말함) 납입액(공제대상금액*)의 12%를 근로소득에 대한 종합소득산출세액에서 공제<br />* 세액공제 대상금액 한도 : 연 100만원 한도</td>
										</tr>
										<tr>
											<th rowspan="6">의료비 세액공제</th>
											<th colspan="2">①본인의료비</th>
											<th colspan="2">지출액</th>
											<td><input type="text" name="medical_person_amt" id="medical_person_amt" class="ipt_disable" value="<%=medical_person_amt %>" readonly/>원</td>
											<td rowspan="3" colspan="2">기본공제대상자(나이 및 소득의 제한을 받지 아니함)를 위하여 해당 근로소득자가 부담한 의료비 중 본인 · 장애인 · 65세 이상인 자를 위하여 지출한 의료비를 제외한 금액(미용 성형수술비용 및 건강증진 의약품 구입비용은 공제대상 제외됨)</td>
											<td rowspan="6" class="essential">세부항목을 등록하시면 계산되어 나옵니다.<br /><br />의료비지급명세서<br />제출대상자<br />2013년 - 200만원 이상<br />2014년 - 의료비 세액 공제액이 있는 근로자<br /><br />총급여액의 3% :<br /><input type="text" name="medical_calc" id="medical_calc" class="ipt_disable" value="<%=medical_calc %>" readonly />원<br />표시된 금액 보다<br />작은 경우는 계산을<br />진행하지 않습니다.</td>
											<td rowspan="6" class="btn"><button type="button" onclick="javascript:openPopup('medical');">의료비</button></td>
										</tr>
										<tr>
											<th colspan="2">②65세 이상 부양가족 의료비</th>
											<th colspan="2">지출액</th>
											<td><input type="text" name="medical_old_amt" id="medical_old_amt" class="ipt_disable" value="<%=medical_old_amt %>" readonly/>원</td>
										</tr>
										<tr>
											<th colspan="2">③장애인 의료비</th>
											<th colspan="2">지출액</th>
											<td><input type="text" name="medical_disabled_person_amt" id="medical_disabled_person_amt" class="ipt_disable" value="<%=medical_disabled_person_amt %>" readonly/>원</td>
										</tr>
										<tr>
											<th colspan="2">④그 밖의 공제 대상자 의료비</th>
											<th colspan="2">지출액</th>
											<td><input type="text" name="medical_etc_amt" id="medical_etc_amt" class="ipt_disable" value="<%=medical_etc_amt %>" readonly/>원</td>
											<td colspan="2">기본공제대상자(나이 및 소득의 제한을 받지 아니함)를 위하여 해당 근로소득자가 부담한 의료비 중 본인 · 장애인 · 65세 이상인 자를 위하여 지출한 의료비를 제외한 금액(미용 성형수술비용 및 건강증진 의약품 구입비용은 공제대상 제외됨)</td>
										</tr>
										<tr>
											<th colspan="2">의료비 세액공제 대상금액</th>
											<th colspan="2">지출액<br />대상금액</th>
											<td><input type="text" name="medical_amt_o" id="medical_amt_o" class="ipt_disable" value="<%=medical_amt_o %>" readonly/>원</td>
											<td colspan="2">* 의료비 세액공제 대상금액 계산</td>
										</tr>
										<tr>
											<th colspan="2">의료비 세액공제</th>
											<th colspan="2">세액공제액</th>
											<td><input type="text" name="total_medical_amt" id="total_medical_amt" class="ipt_disable" value="<%=total_medical_amt %>" readonly/>원</td>
											<td colspan="2">* 의료비 세액공제 대상금액의 15%를 근로소득에 대한 종합소득산출세액의 공제</td>
										</tr>
										<tr>
											<th rowspan="5">교육비 세액공제</th>
											<th colspan="2">근로소득자<br />본인</th>
											<th colspan="2">지출액</th>
											<td><input type="text" name="educate_person_amt" id="educate_person_amt" class="ipt_disable" value="<%=educate_person_amt %>" readonly/>원</td>
											<td rowspan="2" colspan="2">
												○ 근로소득자가 기본공제대상자(나이의 제한을 받지 아니함)을 위하여 지급하는 교육비<br />○ 교육비 공제한도
												<table>
													<thead>
														<tr>
															<th rowspan="2">구분</th>
															<th rowspan="2">본인</th>
															<th colspan="2">부양가족</th>
														</tr>
														<tr>
															<th>취학전아동 초 · 중 · 고생</th>
															<th>대학생</th>
														</tr>
													</thead>
													<tbody>
														<tr>
															<th>1명당 세액공제 대상금액 한도</th>
															<td>전액</td>
															<td>연 300만원</td>
															<td>연 900만원</td>
														</tr>
													</tbody>
												</table>	
											</td>
											<td rowspan="5" class="essential">세부항목을 등록하시면 계산되어 나옵니다.<br /><br />주민번호 별 해당 항목을 입력하시면 정산 계산에 이용됩니다.<br /><br />가족1 취학전아동<br />가족2 초.중.고 생<br />가족3 대학생 교육비 지출부분으로 합산 됩니다.<br />(합산된 공제금액을 표시)<br /><br />연말정산 간소화 서비스를 이용 할 때는 한도에 따라 나눠 입력 하시면 됩니다.</td>
											<td rowspan="5" class="btn"><button type="button" onclick="javascript:openPopup('educate');">교육비</button></td>
										</tr>
										<tr>
											<th colspan="2">기본공제대상자인 배우자 · 직계비속 · 형제자매 · 입양자 및 위탁아동</th>
											<td colspan="3">
												<table>
													<colgroup>
														<col style=""/>
														<col style=""/>
														<col style=""/>
													</colgroup>
													<tbody>
														<tr>
															<th>가족1</th>
															<td>
																<!-- 비활성 input / select 에 disable ipt_disable 클래스 추가 -->
																<select name="kindergarten" id="kindergarten" class="ipt_disable" disabled >
																	<option>취학전아동</option>
																</select>
															</td>
															<td><input type="text" name="educate_kindergarten_amt" id="educate_kindergarten_amt" class="ipt_disable" value="<%=educate_kindergarten_amt %>" readonly />원</td>
														</tr>
														<tr>
															<th>가족2</th>
															<td>
																<select name="school" id="school" class="ipt_disable" disabled >
																	<option>초·중·고 생</option>
																</select>
															</td>
															<td><input type="text" name="educate_school_amt" id="educate_school_amt" class="ipt_disable" value="<%=educate_school_amt %>" readonly />원</td>
														</tr>
														<tr>
															<th>가족3</th>
															<td>
																<select name="univ" id="univ" class="ipt_disable" disabled >
																	<option>대학생 교육</option>
																</select>
															</td>
															<td><input type="text" name="educate_univ_amt" id="educate_univ_amt" class="ipt_disable" value="<%=educate_univ_amt %>" readonly />원</td>
														</tr>
														<tr>
															<th>가족4</th>
															<td></td>
															<td>원</td>
														</tr>
													</tbody>
												</table>
												<a href="#">가족 입력 추가(+)</a>
												<a href="#">가족 입력 삭제(-)</a>
											</td>
										</tr>
										<tr>
											<th colspan="2">장애인<br />특수교육비</th>
											<th colspan="2">교육비<br />지출액</th>
											<td><input type="text" name="educate_disabled_person_amt" id="educate_disabled_person_amt" class="ipt_disable" value="<%=educate_disabled_person_amt %>" readonly />원</td>
											<td colspan="2">○ 근로소득자가 기본공제대상자인 장애인(소득의 제한을 받지 아니함)의 재활교육을 위하여 다음에 해당하는 시설 등에 지급하는 교육비(특수교육비) ⇒ 전액 세액공제대상<br />① 사회복지 사업법에 따른 사회복지시설<br />② 민법에 따라 설립된 비영리법인으로서 보건복지가족부장관이 장애인재활교육을 실시하는 기관으로 인정한 법인<br />③ 장애인 기능 향상과 행동 발달을 위한 발달 재활 서비스를 제공하는 장애 아동 복지 지원법 제21조 제3항에 따라 지방자치단체가 지정한 발달재활서비스 제공기관<br />④ 위 ①의 시설 또는 ②의 법인과 유사한 것으로서 외국에 있는 시설 또는 법인</td>
										</tr>
										<tr>
											<th colspan="2">교육비<br />세액공제<br />대상금액</th>
											<th colspan="2">교육비<br />지출액</th>
											<td><input type="text" name="edcate_amt_o" id="edcate_amt_o" class="ipt_disable" value="<%=edcate_amt_o %>" readonly />원</td>
											<td colspan="2">* 교육비 세액공제 대상금액 계산</td>
										</tr>
										<tr>
											<th colspan="2">교육비<br />세액공제</th>
											<th colspan="2">세액<br />공제액</th>
											<td><input type="text" name="total_edcate_amt" id="total_edcate_amt" class="ipt_disable" value="<%=total_edcate_amt %>" readonly />원</td>
											<td colspan="2">* 교육비 세액공제 대상금액의 15%를 근로소득에 대한 종합소득산출세액에서 공제</td>
										</tr>
										<tr>
											<th rowspan="10">기부금 세액공제</th>
											<th colspan="2">정치자금<br />기부금</th>
											<th colspan="2">기부금액<br />(자동계산)</th>
											<td><input type="text" name="contribue_politic_amt" id="contribue_politic_amt" class="ipt_disable" value="<%=contribue_politic_amt %>" readonly />원<br /><br /><input type="text" name="etc_contribue_politic_amt" id="etc_contribue_politic_amt" value="<%=etc_contribue_politic_amt %>" class="onlyNumber" />원</td>
											<td colspan="2">○ 근로소득자가 정치자금법에 따라 정당(후원회 및 선거관리위원회 포함)에 기부한 정치자금<br />○ 정치자금 기부금 10만원 이하분<br />기부금액의 110분의 100 세액공제(최대 90,909원 한도)<br />○ 정치자금기부금 10만원 초과분<br />- 세액공제 대상금액 한도 : 근로소득금액<br />- 세액공제(①+②)<br />① 10만원초과~3천만원이하 : 해당금액의 100분의 15<br />② 3천만원 초과 : 해당금액의 100분의 25</td>
											<td rowspan="10" class="essential">세부항목을 등록하시면 계산되어 나옵니다.</td>
											<td rowspan="10" class="btn"><button type="button" onclick="javascript:openPopup('contribute');">기부금</button></td>
										</tr>
										<tr>
											<th rowspan="2" colspan="2">법정<br />기부금</th>
											<th colspan="2">전년도 이월액</th>
											<td></td>
											<td rowspan="2" colspan="2">○	해당 과세기간에 지급한 한도 내 법정기부금과 지정기부금을 합한 금액의 100분의 15%(해당금액이 3천만원을 초과하는 경우 그 초과분은 100분의25)에 해당하는 금액을 근로소득에 종합소득산출세액에서 공제<br />○ 세액공제 대상금액 한도<br />: 근로소득금액 - 정치자금기부금(10만원 초과금액)</td>
										</tr>
										<tr>
											<th colspan="2">기부금액</th>
											<td><input type="text" name="contribue_law_amt" id="contribue_law_amt" class="ipt_disable" value="<%=contribue_law_amt %>" readonly />원</td>
										</tr>
										<tr>
											<th rowspan="2" colspan="2">공인신탁<br />기부금</th>
											<th colspan="2">전년도 이월액</th>
											<td></td>
											<td rowspan="2" colspan="2">○	이월 공익신탁기부금은 소득공제대상<br /><span>*세액공제대상 아님 *</span><br />○ 소득공제 한도<br />: [근로소득금액 - 정치자금기부금(10만원 초과금액) - 법정기부금] x 50%<br /><span>○ 2011.6.30까지 지출한 기부금에 한함</span></td>
										</tr>
										<tr>
											<th colspan="2">기부금액</th>
											<td><input type="text" name="contribute_trust_amt" id="contribute_trust_amt" class="ipt_disable" value="<%=contribute_trust_amt %>" readonly />원</td>
										</tr>
										<tr>
											<th colspan="2">우리사주 조합기부금</th>
											<th colspan="2">기부금액</th>
											<td><input type="text" name="contribue_employ_stock_amt" id="contribue_employ_stock_amt" class="ipt_disable" value="<%=contribue_employ_stock_amt %>" readonly />원</td>
											<td colspan="2">○ 우리사주조합원이 아닌 근로자가 우리사주조합에 기부하는 기부금을 근로소득금액에서 공제<br />○ 소득공제 한도 : [근로소득금액 - 정치자금기부금(10만원 초과금액) - 법정기부금 - 50% 공제한도 특례기부금] x 30%<br />○ 2011.6.30까지 지출한 기부금에 한함</td>
										</tr>
										<tr>
											<th rowspan="2" colspan="2">지정기부금<br />(종교단체외)</th>
											<th colspan="2">전년도<br />이월액</th>
											<td></td>
											<td rowspan="4" colspan="2">○ 해당 과세기간에 지급한 한도 내 법정기부금과 지정기부금을 합한 금액의 100분의 15(해당금액이 3천만원을 초과하는 경우 그 초과분은 100분의 25)에 해당하는 금액을 근로소득에 종합소득산출세액에서 공제<br />○ 세액공제 대상금액 한도<br />- 종교단체 기부금이 없는 경우<br />[근로소득금액 - 정치자금기부금(10만원 초과금액) - 법정기부금 - 특례기부금 - 우리사주조합기부금] * 30%<br />* 이하 "지정기부금 세액공제 대상금액"이라 함<br />- 종교단체 기부금이 있는 경우(이월액만 있는 경우 포함)<br />("지정기부금 세액공제 대상금액" x 10%) + min<br />["지정기부금 세액공제 대상금액" x 20%, 종교단체 외 지정기부금*]<br />법정기부금 - 특례기부금 - 우리사주조합기부금] * x 30%</td>
										</tr>
										<tr>
											<th colspan="2">기부금액</th>
											<td><input type="text" name="contribue_designate_amt" id="contribue_designate_amt" class="ipt_disable" value="<%=contribue_designate_amt %>" readonly />원</td>
										</tr>
										<tr>
											<th rowspan="2" colspan="2">지정기부금<br />(종교단체)</th>
											<th colspan="2">전년도<br />이월액</th>
											<td></td>
										</tr>
										<tr>
											<th colspan="2">기부금액</th>
											<td><input type="text" name="contribue_religion_amt" id="contribue_religion_amt" class="ipt_disable" value="<%=contribue_religion_amt %>" readonly />원</td>
										</tr>
										<tr>
											<th colspan="3">표준세액공제</th>
											<td colspan="2"></td>
											<td><input type="text" name="yt_standard_deduct_amt" id="yt_standard_deduct_amt" class="ipt_disable" value="<%=yt_standard_deduct_amt %>" readonly />원</td>
											<td colspan="2">○ 근로자가 소득세법 상 특별소득공제와 특별세액공제, 조세특례제한법 상 정치자금기부금 세액공제와 우리사주조합기부금 소득공제를 신청하지 아니한 경우에 연 12만원을 근로소득에 대한 종합소득산출세액에서 공제</td>
											<td class="essential">연말정산 계산 후 나타납니다.</td>
											<td></td>
										</tr>
										<tr>
											<th rowspan="5">기타세액공제</th>
											<th colspan="2">납세조항공제</th>
											<th colspan="2">공제세액</th>
											<td><input type="text" name="etc_sework_tx_amt" id="etc_sework_tx_amt" value="<%=etc_sework_tx_amt %>" class="onlyNumber" />원</td>
											<td colspan="2">○ 납세조합에 의하여 원천징수된 근로소득에 대한 종합소득 산출세액의 10%</td>
											<td rowspan="4" class="essential">화면에 직접 입력하고 저장하시길 바랍니다.</td>
											<td rowspan="4"></td>
										</tr>
										<tr>
											<th colspan="2">주택차입금</th>
											<th colspan="2">이자상환액</th>
											<td><input type="text" name="etc_house_laon_interest_amt" id="etc_house_laon_interest_amt" value="<%=etc_house_laon_interest_amt %>" class="onlyNumber" />원</td>
											<td colspan="2">○ 95.11.1. ~ 97.12.31. 기간 중에 미분양주택의 취득과 관련하여 국민주택기금으로부터 차입한 대출금의 이자 상환액 중 30%<br />※ 주택차입금 이자세액공제를 받는 차입금의 이자는 장기주택저당차입금 이자상환액공제를 적용받을 수 없음</td>
										</tr>
										<tr>
											<th rowspan="2" colspan="2">외국납부</th>
											<th colspan="2">소득금액</th>
											<td><input type="text" name="etc_foreigner_income" id="etc_foreigner_income" value="<%=etc_foreigner_income %>" class="onlyNumber" />원</td>
											<td rowspan="2" colspan="2">○ 거주자의 근로소득금액에 국외원천소득이 합산되어 있는 경우 그 국외원천소득에 대하여 외국에서 외국납부세액을 납부하였거나 납부할 것이 있을 때<br />- 근로소득자는 해당 과세기간의 종합소득 산출세액에 국외원천소득 이 그 과세기간의 종합소득금액에서 차지하는 비율을 곱하여 금액을 한도로 외국소득세액을 해당 과세기간의 종합소득 산출세액에서 공제</td>
										</tr>
										<tr>
											<th colspan="2">납부세액</th>
											<td><input type="text" name="etc_foreigner_pay" id="etc_foreigner_pay" value="<%=etc_foreigner_pay %>" class="onlyNumber" />원</td>
										</tr>
										<tr>
											<th colspan="2">월세액</th>
											<th colspan="2">납부세액</th>
											<td><input type="text" name="house_month_lent_amt" id="house_month_lent_amt" class="ipt_disable" value="<%=house_month_lent_amt %>" readonly />원</td>
											<td colspan="2">○ 공제대상 : 해당 과세기간 총급여액이 7천만원 이하(종합소득금액이 6천만원 이하)인 근로소득자인 무주택세대의 세대주(세대주가 주택관련 공제를 받지 않은 경우 세대원도 가능)가 국민주택규모의 주택을 임차하기 위해 지급하는 월세액<br />○ 세액공제금액 : 월세 지급액(750만원 한도) x 10%</td>
											<td class="essential">월세액은 세부사항을 등록하여야 합니다.</td>
											<td class="btn"><button type="button" onclick="javascript:openPopup('rent');">주택계약</button></td>
										</tr>
									</tbody>
								</table>
							</div>	
						</div>
					</div>
				</form>
			</div>
			<!-- ######## end ######### -->
		</div>
	</div>
	<!-- 주소지 조회 팝업 -->
	<div id="addressList" style="display:none; width:auto; height:auto;">
		<div class="address_content"></div> 
	</div>
	<%@include file="/include/footer.jsp"%>
</body>
</html>
