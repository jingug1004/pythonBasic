<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%--
 * @파일명 : yearendtaxPreviousWorkplacePopup.jsp
 * @메뉴명 : 연말정산 등록 > 종전근무지 팝업
 * @최초작성일 : 2015/02/16
 * @author : 윤범진                  
 * @수정내역 : 
--%>
<%@ page import="com.hanaph.gw.yt.yearendtax.vo.YearendtaxVO" %>
<%@ page import="com.hanaph.gw.co.common.utils.StringUtil" %>
<%@ page import="java.util.List" %>
<%@ include file ="/common/path.jsp" %>
<%
	@SuppressWarnings("unchecked")
	List<YearendtaxVO> previousWorkplaceList = (List<YearendtaxVO>)request.getAttribute("previousWorkplaceList");

	String year = (String)request.getAttribute("year");
	
%>
<!DOCTYPE html>
<html lang="ko">
<head>
	<%@include file="/include/head.jsp"%>
	<script type="text/javascript">
	
		$(document).ready(function(){
			
			/* 금액 입력 영역 유효성 체크 */
			$("input[type='text'].onlyNumber").keyup(function(event) {
				var keyCode = event.keyCode;
			    if (keyCode != 16 && keyCode != 46 && (keyCode < 35 || keyCode > 40)) { // 16:shift/46:delete/35:end/36:home/37~40:화살표
			    	$(this).val(formCheck.onlyNumber($(this).val()));	
				}
			});
			
			/* 금액 입력 후 합계 반영 */
			$("input[type='text'].onlyNumber").blur(function(event) {
				var cls = $(this).attr("class").split(" ");
				var target = cls[0];
				var seq = $(this).attr("id").split("_");
				var targetSeq = seq[seq.length-1];
				
				calcAmount(target, targetSeq);
			});
		});
		
		/* 합계계산 */
		function calcAmount(target, targetSeq) {
			var targetArray = ["total_salary", "total_free", "reduction_amt"]; // 합계 계산 대상 name
			
			for (var i = 0; i < targetArray.length; i++) {
				if (target == targetArray[i]) { // 전체계산이 아닐 경우 특정 값만 계산하도록
					var selector = $("#previousWorkplaceList_" + targetSeq + " ." + targetArray[i]); // class 기준으로 집합 만듬 
					var total = 0; // 합계
					
					selector.each(function(){
						total += Number($(this).val().replace(/,/gi, "")); // 쉼표 제거 후 더함
					});
					
					$("#" + targetArray[i] + "_" + targetSeq).val(Commons.addComma(total)); // 최종 합계에 쉼표 붙임
				}
			}
		}
	
		/* 행 추가 */
		function addRow(){
			
			var seq = $("#previousWorkplaceList > tbody > tr").size() + 1;
			var html = '';
			html += '<tr id="previousWorkplaceList_'+seq+'">';
			html += '	<td class="box">';
			html += '		<div class="device">';
			html += '			<input type="hidden" name="seq" id="seq_'+seq+'" value="'+seq+'"/>';
			html += '			<div class="pop_tit txtleft">';
			html += '				<span class="tit">'+seq+'</span>';
			html += '			</div>';
			html += '			<button type="button" class="btn_row letter" onclick="javascript:removeRow(\''+seq+'\');">- 행삭제</button>';
			html += '			<table class="tbl_yearend mgn">';
			html += '				<colgroup>';
			html += '					<col style="width:71px">';
			html += '					<col style="">';
			html += '					<col style="width:71px">';
			html += '					<col style="">';
			html += '					<col style="width:71px">';
			html += '					<col style="">';
			html += '					<col style="width:71px">';
			html += '					<col style="">';
			html += '				</colgroup>';
			html += '				<tbody>';
			html += '					<tr>';
			html += '						<th>회사명</th>';
			html += '						<td colspan="3"><input type="text" name="corporate_nm" id="corporate_nm_'+seq+'" /></td>';
			html += '						<th>대표자명</th>';
			html += '						<td><input type="text" name="president" id="president_'+seq+'" class="w89" /></td>';
			html += '						<th>사업자번호</th>';
			html += '						<td><input type="text" name="corporate_no" id="corporate_no_'+seq+'" class="w89" /></td>';
			html += '					</tr>';	
			html += '					<tr>';
			html += '						<th>근무기간</th>';
			html += '						<td colspan="3" class="date">';
			html += '							<span class="date_box">';
			html += '								<input type="text" name="start_work_dt" id="start_work_dt_'+seq+'" class="serch_date" />';
			html += '								<button type="button" class="btn_date"><span class="blind">날짜선택</span></button>';
			html += '							</span>';
			html += '							부터';
			html += '							<span class="date_box">';
			html += '								<input type="text" name="end_work_dt" id="end_work_dt_'+seq+'" class="serch_date" />';
			html += '								<button type="button" class="btn_date"><span class="blind">날짜선택</span></button>';
			html += '							</span>';
			html += '							까지';
			html += '						</td>';
			html += '						<th>감면기간</th>';
			html += '						<td colspan="3" class="date">';
			html += '							<span class="date_box">';
			html += '								<input type="text" name="start_reduce_dt" id="start_reduce_dt_'+seq+'" class="serch_date" />';
			html += '								<button type="button" class="btn_date"><span class="blind">날짜선택</span></button>';
			html += '							</span>';
			html += '							부터';
			html += '							<span class="date_box">';
			html += '								<input type="text" name="end_reduce_dt" id="end_reduce_dt_'+seq+'" class="serch_date" />';
			html += '								<button type="button" class="btn_date"><span class="blind">날짜선택</span></button>';
			html += '							</span>';
			html += '							까지';
			html += '						</td>';
			html += '					</tr>';
			html += '					<tr>';
			html += '						<th>지급일자</th>';
			html += '						<td class="date">';
			html += '							<span class="date_box">';
			html += '								<input type="text" name="salary_dt" id="salary_dt_'+seq+'" class="serch_date" />';
			html += '								<button type="button" class="btn_date"><span class="blind">날짜선택</span></button>';
			html += '							</span>';
			html += '						</td>';
			html += '						<th class="type01">급여총액</th>';
			html += '						<td><input type="text" name="salary_amt" id="salary_amt_'+seq+'" class="total_salary onlyNumber w89" value="0" /></td>';
			html += '						<th class="type01">상여총액</th>';
			html += '						<td><input type="text" name="bonus_amt" id="bonus_amt_'+seq+'" class="total_salary onlyNumber w89" value="0" /></td>';
			html += '						<th class="type01">인정상여</th>';
			html += '						<td><input type="text" name="constructive_bonus_amt" id="constructive_bonus_amt_'+seq+'" class="total_salary onlyNumber w89" value="0" /></td>';
			html += '					</tr>';	
			html += '					<tr>';
			html += '						<th>건강보험</th>';
			html += '						<td><input type="text" name="health_amt" id="health_amt_'+seq+'" class="onlyNumber w89" value="0" /></td>';
			html += '						<th>고용보험</th>';
			html += '						<td><input type="text" name="employ_amt" id="employ_amt_'+seq+'" class="onlyNumber w89" value="0" /></td>';
			html += '						<th>국민연금</th>';
			html += '						<td><input type="text" name="kuk_yeon_amt" id="kuk_yeon_amt_'+seq+'" class="onlyNumber w89" value="0" /></td>';
			html += '						<th>개인연금</th>';
			html += '						<td><input type="text" name="annuity_amt" id="annuity_amt_'+seq+'" class="onlyNumber w89" value="0" /></td>';
			html += '					</tr>';
			html += '					<tr>';
			html += '						<th>소득세</th>';
			html += '						<td><input type="text" name="income_amt" id="income_amt_'+seq+'" class="onlyNumber w89" value="0" /></td>';
			html += '						<th>지방소득세</th>';
			html += '						<td><input type="text" name="jumin_amt" id="jumin_amt_'+seq+'" class="onlyNumber w89" value="0" /></td>';
			html += '						<th>농특세</th>';
			html += '						<td><input type="text" name="nong_amt" id="nong_amt_'+seq+'" class="onlyNumber w89" value="0" /></td>';
			html += '						<td colspan="2">*소득세는 결정세액 입력하세요</td>';
			html += '					</tr>';
			html += '				</tbody>';
			html += '			</table>';
			html += '			<table class="tbl_yearend">';
			html += '				<colgroup>';
			html += '					<col style="width:123px">';
			html += '					<col style="">';
			html += '					<col style="width:123px">';
			html += '					<col style="">';
			html += '					<col style="width:123px">';
			html += '					<col style="">';
			html += '				</colgroup>';
			html += '				<tbody>';
			html += '					<tr>';
			html += '						<th class="type01">근무처별소득명세합계</th>';
			html += '						<td colspan="5"><input type="text" name="total_salary" id="total_salary_'+seq+'" class="ipt_disable onlyNumber w89" readonly value="0" /></td>';
			html += '					</tr>';	
			html += '					<tr>';
			html += '						<th class="type01">주식매수선택권 행사이익</th>';
			html += '						<td><input type="text" name="stock_option_amt" id="stock_option_amt_'+seq+'" class="total_salary onlyNumber w89" value="0" /></td>';
			html += '						<th class="type01">우리사주조합인출금</th>';
			html += '						<td><input type="text" name="employ_stock_amt" id="employ_stock_amt_'+seq+'" class="total_salary onlyNumber w89" value="0" /></td>';
			html += '						<th class="type01">임원퇴직소득금액한도초과액</th>';
			html += '						<td><input type="text" name="derector_retirement_amt" id="derector_retirement_amt_'+seq+'" class="total_salary onlyNumber w89" value="0" /></td>';
			html += '					</tr>';
			html += '				</tbody>';
			html += '			</table>';
			html += '			<table class="tbl_yearend">';
			html += '				<colgroup>';
			html += '					<col style="width:91px">';
			html += '					<col style="">';
			html += '					<col style="">';
			html += '					<col style="">';
			html += '					<col style="">';
			html += '					<col style="">';
			html += '				</colgroup>';
			html += '				<tbody>';
			html += '					<tr>';
			html += '						<th class="type02">비과세소득계</th>';
			html += '						<td colspan="3"><input type="text" name="total_free" id="total_free_'+seq+'" class="ipt_disable onlyNumber w89" readonly value="0" /></td>';
			html += '						<th class="type03">감면소득계</th>';
			html += '						<td><input type="text" name="reduction_amt" id="reduction_amt_'+seq+'" class="ipt_disable onlyNumber w89" readonly value="0" /></td>';
			html += '					</tr>';	
			html += '					<tr>';
			html += '						<th class="type02">국외근로비과세</th>';
			html += '						<td><input type="text" name="foreign_work_amt" id="foreign_work_amt_'+seq+'" class="total_free onlyNumber w89" value="0" /></td>';
			html += '						<th class="type02">연장근무(야간수당) 비과세</th>';
			html += '						<td><input type="text" name="oevrtime_amt" id="oevrtime_amt_'+seq+'" class="total_free onlyNumber w89" value="0" /></td>';
			html += '						<th class="type02">출산보육수당</th>';
			html += '						<td><input type="text" name="meternity_amt" id="meternity_amt_'+seq+'" class="total_free onlyNumber w89" value="0" /></td>';
			html += '					</tr>';
			html += '					<tr>';
			html += '						<th class="type02">연구보조비</th>';
			html += '						<td><input type="text" name="research_amt" id="research_amt_'+seq+'" class="total_free onlyNumber w89" value="0" /></td>';
			html += '						<th class="type02">학자금</th>';
			html += '						<td><input type="text" name="school_expenses_amt" id="school_expenses_amt_'+seq+'" class="total_free onlyNumber w89" value="0" /></td>';
			html += '						<th class="type02">취재수당</th>';
			html += '						<td><input type="text" name="collect_amt" id="collect_amt_'+seq+'" class="total_free onlyNumber w89" value="0" /></td>';
			html += '					</tr>';
			html += '					<tr>';
			html += '						<th class="type02">벽지수당</th>';
			html += '						<td><input type="text" name="remote_rural_area_amt" id="remote_rural_area_amt_'+seq+'" class="total_free onlyNumber w89" value="0" /></td>';
			html += '						<th class="type02">천재지변 재해로 받는 수당</th>';
			html += '						<td><input type="text" name="natural_disaster_amt" id="natural_disaster_amt_'+seq+'" class="total_free onlyNumber w89" value="0" /></td>';
			html += '						<th class="type02">주식매수선택권</th>';
			html += '						<td><input type="text" name="stock_purchase_amt" id="stock_purchase_amt_'+seq+'" class="total_free onlyNumber w89" value="0" /></td>';
			html += '					</tr>';
			html += '					<tr>';
			html += '						<th class="type03">외국인기술자소득세면제</th>';
			html += '						<td><input type="text" name="foreigner_amt" id="foreigner_amt_'+seq+'" class="reduction_amt onlyNumber w89" value="0" /></td>';
			html += '						<th class="type02">우리사주조합인출금50%</th>';
			html += '						<td><input type="text" name="employ_stock_amt1" id="employ_stock_amt1_'+seq+'" class="total_free onlyNumber w89" value="0" /></td>';
			html += '						<th class="type02">우리사주조합인출금75%</th>';
			html += '						<td><input type="text" name="employ_stock_amt2" id="employ_stock_amt2_'+seq+'" class="total_free onlyNumber w89" value="0" /></td>';
			html += '					</tr>';
			html += '					<tr>';
			html += '						<th class="type02">경호수당, 승선수당</th>';
			html += '						<td><input type="text" name="guard_embark_amt" id="guard_embark_amt_'+seq+'" class="total_free onlyNumber w89" value="0" /></td>';
			html += '						<th class="type03">중소기업청년취업소득세감면</th>';
			html += '						<td><input type="text" name="smiymjtc_amt" id="smiymjtc_amt_'+seq+'" class="reduction_amt onlyNumber w89" value="0" /></td>';
			html += '						<th class="type02">전공의수련보조수당</th>';
			html += '						<td><input type="text" name="major_amt" id="major_amt_'+seq+'" class="total_free onlyNumber w89" value="0" /></td>';
			html += '					</tr>';
			html += '					<tr>';
			html += '						<th colspan="2" class="type03">해저광물자원개발을위한과세특례</th>';
			html += '						<td><input type="text" name="submarine_mineral_amt" id="submarine_mineral_amt_'+seq+'" class="reduction_amt onlyNumber w89" value="0" /></td>';
			html += '						<th colspan="2" class="type02">교육기본법제28조제1항에따라받는장학금</th>';
			html += '						<td><input type="text" name="scholarship_amt" id="scholarship_amt_'+seq+'" class="total_free onlyNumber w89" value="0" /></td>';
			html += '					</tr>';
			html += '					<tr>';
			html += '						<th colspan="2" class="type02">외국정부또는국제기관에근무하는사람에대한비과세</th>';
			html += '						<td><input type="text" name="organization_amt" id="organization_amt_'+seq+'" class="total_free onlyNumber w89" value="0" /></td>';
			html += '						<th colspan="2" class="type02">시립유치원수석교사의인건비유아교육법시행령</th>';
			html += '						<td><input type="text" name="kindergarten_teacher_amt" id="kindergarten_teacher_amt_'+seq+'" class="total_free onlyNumber w89" value="0" /></td>';
			html += '					</tr>';
			html += '					<tr>';
			html += '						<th colspan="2" class="type02">보육교사인건비영유아보육법시행령</th>';
			html += '						<td><input type="text" name="childcare_amt" id="childcare_amt_'+seq+'" class="total_free onlyNumber w89" value="0" /></td>';
			html += '						<th colspan="2" class="type03">조세조약상교직자조항의소득세감면</th>';
			html += '						<td><input type="text" name="teache_clause_amt" id="teache_clause_amt_'+seq+'" class="reduction_amt onlyNumber w89" value="0" /></td>';
			html += '					</tr>';
			html += '					<tr>';
			html += '						<th colspan="2" class="type02">정부공공기관중지방이전기관종사자이수수당</th>';
			html += '						<td><input type="text" name="move_amt" id="move_amt_'+seq+'" class="total_free onlyNumber w89" value="0" /></td>';
			html += '						<th colspan="2" class="type02">법령조례에의한보수를받지않는의원수당</th>';
			html += '						<td><input type="text" name="legislation_amt" id="legislation_amt_'+seq+'" class="total_free onlyNumber w89" value="0" /></td>';
			html += '					</tr>';
			html += '					<tr>';
			html += '						<th colspan="2" class="type02">작전임무수행을위해외국에주둔하는군인받는급여</th>';
			html += '						<td><input type="text" name="operation_amt" id="operation_amt_'+seq+'" class="total_free onlyNumber w89" value="0" /></td>';
			html += '						<th colspan="2" class="type02"></th>';
			html += '						<td></td>';
			html += '					</tr>';
			html += '				</tbody>';
			html += '			</table>';
			html += '			<p class="refer">';
			html += '				중소기업청년취업소득세감면 금액이 있는 경우 감면 비율';
			html += '				<input type="text" name="smiymjtc_rate" id="smiymjtc_rate_'+seq+'" value="0" /> %';
			html += '			</p>';
			html += '		</div>';
			html += '	</td>';
			html += '</tr>';
			
			$("#previousWorkplaceList").append(html); // 행추가
			
			$("#previousWorkplaceList_" + seq + " input[type='text'].onlyNumber").keyup(function(event) { // 유효성 체크 적용
				var keyCode = event.keyCode;
			    if (keyCode != 16 && keyCode != 46 && (keyCode < 35 || keyCode > 40)) { // 16:shift/46:delete/35:end/36:home/37~40:화살표
			    	$(this).val(formCheck.onlyNumber($(this).val()));	
				}
			});
			
			$("#previousWorkplaceList_" + seq + " input[type='text'].onlyNumber").blur(function(event) { // 합계 적용
				var cls = $(this).attr("class").split(" ");
				var target = cls[0];
				var seq = $(this).attr("id").split("_");
				var targetSeq = seq[seq.length-1];
				
				calcAmount(target, targetSeq);
			});
			
			$("#previousWorkplaceList_" + seq + " .btn_date").prev().datepicker({"dateFormat":"yy-mm-dd"});
			$("#previousWorkplaceList_" + seq + " .btn_date").click(function() { // 달력 기능 적용
		        $(this).prev().datepicker({"dateFormat":"yy-mm-dd"});
				$(this).prev().focus();
			});
		}
	
		/* 행 삭제 */
		function removeRow(seq){
			$("#previousWorkplaceList_"+seq).remove();
		}
		
		/* 저장 */
		function procYearendtaxPreviousWorkplaceAjax(){
			
			/* 유효성 체크 */
			for (var i = 1; i <= 1; i++) {
				console.log($("#previousWorkplaceList > tbody > tr").length);
				var corporate_nm = $("#corporate_nm_"+i);
				var president = $("#president_"+i);
				var corporate_no = $("#corporate_no_"+i);
				var start_work_dt = $("#start_work_dt_"+i);
				var end_work_dt = $("#end_work_dt_"+i);
				var start_reduce_dt = $("#start_reduce_dt_"+i);
				var end_reduce_dt = $("#end_reduce_dt_"+i);
				var salary_dt = $("#salary_dt_"+i);
				var smiymjtc_rate = $("#smiymjtc_rate_"+i);
				
				if (formCheck.getByteLength(corporate_nm.val()) > 30) {
					alert("회사명은 한글 15자 혹은 영문 30자 이내로 입력해주세요.(30byte)");
					corporate_nm.focus();
					return false;
				}
				
				if (formCheck.getByteLength(president.val()) > 60) {
					alert("대표자명은 한글 30자 혹은 영문 60자 이내로 입력해주세요.(60byte)");
					president.focus();
					return false;
				}
				
				if (formCheck.getByteLength(formCheck.removeChar(corporate_no.val())) != 10) {
					alert("올바른 사업자번호를 입력해주세요.");
					corporate_no.focus();
					return false;
				}
				
				if (!formCheck.isDateFormat(start_work_dt.val())) {
					alert("올바른 근무기간을 입력해주세요.");
					start_work_dt.focus();
					return false;
				}
				
				if (!formCheck.isDateFormat(end_work_dt.val())) {
					alert("올바른 근무기간을 입력해주세요.");
					end_work_dt.focus();
					return false;
				}
				
				if (formCheck.dateChk(start_work_dt.val(), end_work_dt.val()) < 0) {
					alert("근무기간을 확인하세요.");
					return false;
				}
				
				if (!formCheck.isDateFormat(start_reduce_dt.val())) {
					alert("올바른 감면기간을 입력해주세요.");
					start_reduce_dt.focus();
					return false;
				}
				
				if (!formCheck.isDateFormat(end_reduce_dt.val())) {
					alert("올바른 감면기간을 입력해주세요.");
					end_reduce_dt.focus();
					return false;
				}
				
				if (formCheck.dateChk(start_reduce_dt.val(), end_reduce_dt.val()) < 0) {
					alert("감면기간을 확인하세요.");
					return false;
				}
			}
			
			var frm = $("#frm");
			
			$.ajax({
				type:"POST",
				data: frm.serialize(),
				url:"<%=GROUP_ROOT%>/yt/yearendtax/procYearendtaxPreviousWorkplaceAjax.do",
				dataType:"json",
				success:function(data){
					if (data.result == "success") {
						alert("저장되었습니다.");
						opener.getYearendtaxInfoAjax('pay');
						self.close();
					} else {
						alert(data.result);
					}
				}
			});
		}
	</script>
</head>
<body>

<!-- pop up 가로 사이즈 820px -->
<div class="wrap_pop_bg">
	<div class="wrap_statement_overtime">
		<div class="top">
			<h3>기본사항 <span>(종전 근무지)</span></h3>
		</div>
		<form name="frm" id="frm" method="post" >
			<input type="hidden" name="year" id="year" value="<%=year %>" />
			
			<div class="inner">
				<div class="cont_box cont_table06">
					<div class="before_com">
						<button type="button" class="btn_row letter" onclick="javascript:addRow();">+ 행추가</button>
					</div>
					<div class="wrap_tbl_yearend">
					
							<table class="tbl_yearend" id="previousWorkplaceList">
								<tbody>
								<%
									for(int i = 0; i < previousWorkplaceList.size(); i++){
										
										YearendtaxVO previousWorkplaceDetail = previousWorkplaceList.get(0);
			
										String seq = StringUtil.nvl(previousWorkplaceDetail.getSeq());
										String corporate_nm = StringUtil.nvl(previousWorkplaceDetail.getCorporate_nm());
										String president = StringUtil.nvl(previousWorkplaceDetail.getPresident());
										String corporate_no = StringUtil.nvl(previousWorkplaceDetail.getCorporate_no());
										String start_work_dt = StringUtil.nvl(previousWorkplaceDetail.getStart_work_dt());
										String end_work_dt = StringUtil.nvl(previousWorkplaceDetail.getEnd_work_dt());
										String start_reduce_dt = StringUtil.nvl(previousWorkplaceDetail.getStart_reduce_dt());
										String end_reduce_dt = StringUtil.nvl(previousWorkplaceDetail.getEnd_reduce_dt());
										String salary_dt = StringUtil.nvl(previousWorkplaceDetail.getSalary_dt());
										String salary_amt = StringUtil.makeMoneyType(previousWorkplaceDetail.getSalary_amt());
										String bonus_amt = StringUtil.makeMoneyType(previousWorkplaceDetail.getBonus_amt());
										String constructive_bonus_amt = StringUtil.makeMoneyType(previousWorkplaceDetail.getConstructive_bonus_amt());
										String health_amt = StringUtil.makeMoneyType(previousWorkplaceDetail.getHealth_amt());
										String employ_amt = StringUtil.makeMoneyType(previousWorkplaceDetail.getEmploy_amt());
										String kuk_yeon_amt = StringUtil.makeMoneyType(previousWorkplaceDetail.getKuk_yeon_amt());
										String annuity_amt = StringUtil.makeMoneyType(previousWorkplaceDetail.getAnnuity_amt());
										String income_amt = StringUtil.makeMoneyType(previousWorkplaceDetail.getIncome_amt());
										String jumin_amt = StringUtil.makeMoneyType(previousWorkplaceDetail.getJumin_amt());
										String nong_amt = StringUtil.makeMoneyType(previousWorkplaceDetail.getNong_amt());
										String total_salary = StringUtil.makeMoneyType(previousWorkplaceDetail.getTotal_salary());
										String stock_option_amt = StringUtil.makeMoneyType(previousWorkplaceDetail.getStock_option_amt());
										String employ_stock_amt = StringUtil.makeMoneyType(previousWorkplaceDetail.getEmploy_stock_amt());
										String derector_retirement_amt = StringUtil.makeMoneyType(previousWorkplaceDetail.getDerector_retirement_amt());
										String total_free = StringUtil.makeMoneyType(previousWorkplaceDetail.getTotal_free());
										String reduction_amt = StringUtil.makeMoneyType(previousWorkplaceDetail.getReduction_amt());
										String foreign_work_amt = StringUtil.makeMoneyType(previousWorkplaceDetail.getForeign_work_amt());
										String oevrtime_amt = StringUtil.makeMoneyType(previousWorkplaceDetail.getOevrtime_amt());
										String meternity_amt = StringUtil.makeMoneyType(previousWorkplaceDetail.getMeternity_amt());
										String research_amt = StringUtil.makeMoneyType(previousWorkplaceDetail.getResearch_amt());
										String school_expenses_amt = StringUtil.makeMoneyType(previousWorkplaceDetail.getSchool_expenses_amt());
										String collect_amt = StringUtil.makeMoneyType(previousWorkplaceDetail.getCollect_amt());
										String remote_rural_area_amt = StringUtil.makeMoneyType(previousWorkplaceDetail.getRemote_rural_area_amt());
										String natural_disaster_amt = StringUtil.makeMoneyType(previousWorkplaceDetail.getNatural_disaster_amt());
										String stock_purchase_amt = StringUtil.makeMoneyType(previousWorkplaceDetail.getStock_purchase_amt());
										String foreigner_amt = StringUtil.makeMoneyType(previousWorkplaceDetail.getForeigner_amt());
										String employ_stock_amt1 = StringUtil.makeMoneyType(previousWorkplaceDetail.getEmploy_stock_amt1());
										String employ_stock_amt2 = StringUtil.makeMoneyType(previousWorkplaceDetail.getEmploy_stock_amt2());
										String guard_embark_amt = StringUtil.makeMoneyType(previousWorkplaceDetail.getGuard_embark_amt());
										String smiymjtc_amt = StringUtil.makeMoneyType(previousWorkplaceDetail.getSmiymjtc_amt());
										String major_amt = StringUtil.makeMoneyType(previousWorkplaceDetail.getMajor_amt());
										String submarine_mineral_amt = StringUtil.makeMoneyType(previousWorkplaceDetail.getSubmarine_mineral_amt());
										String scholarship_amt = StringUtil.makeMoneyType(previousWorkplaceDetail.getScholarship_amt());
										String organization_amt = StringUtil.makeMoneyType(previousWorkplaceDetail.getOrganization_amt());
										String kindergarten_teacher_amt = StringUtil.makeMoneyType(previousWorkplaceDetail.getKindergarten_teacher_amt());
										String childcare_amt = StringUtil.makeMoneyType(previousWorkplaceDetail.getChildcare_amt());
										String teache_clause_amt = StringUtil.makeMoneyType(previousWorkplaceDetail.getTeache_clause_amt());
										String move_amt = StringUtil.makeMoneyType(previousWorkplaceDetail.getMove_amt());
										String legislation_amt = StringUtil.makeMoneyType(previousWorkplaceDetail.getLegislation_amt());
										String operation_amt = StringUtil.makeMoneyType(previousWorkplaceDetail.getOperation_amt());
										String smiymjtc_rate = StringUtil.nvl(previousWorkplaceDetail.getSmiymjtc_rate(), "0");
										
										if (!"".equals(corporate_no) && corporate_no.length() == 10) {
											corporate_no = corporate_no.substring(0, 3) + "-" + corporate_no.substring(3, 5) + "-" + corporate_no.substring(5, 10);
										}
										
										if (!"".equals(start_work_dt) && start_work_dt.length() == 8) {
											start_work_dt = start_work_dt.substring(0, 4) + "-" + start_work_dt.substring(4, 6) + "-" + start_work_dt.substring(6, 8);
										}
										
										if (!"".equals(end_work_dt) && end_work_dt.length() == 8) {
											end_work_dt = end_work_dt.substring(0, 4) + "-" + end_work_dt.substring(4, 6) + "-" + end_work_dt.substring(6, 8);
										}
										
										if (!"".equals(start_reduce_dt) && start_reduce_dt.length() == 8) {
											start_reduce_dt = start_reduce_dt.substring(0, 4) + "-" + start_reduce_dt.substring(4, 6) + "-" + start_reduce_dt.substring(6, 8);
										}
										
										if (!"".equals(end_reduce_dt) && end_reduce_dt.length() == 8) {
											end_reduce_dt = end_reduce_dt.substring(0, 4) + "-" + end_reduce_dt.substring(4, 6) + "-" + end_reduce_dt.substring(6, 8);
										}
			
										if (!"".equals(salary_dt) && salary_dt.length() == 8) {
											salary_dt = salary_dt.substring(0, 4) + "-" + salary_dt.substring(4, 6) + "-" + salary_dt.substring(6, 8);
										}
								%>
									<tr id="previousWorkplaceList_<%=seq %>">
										<td class="box">
											<div class="device">
												<input type="hidden" name="seq" id="seq_<%=seq %>" value="<%=seq %>"/>
												<div class="pop_tit txtleft">
													<span class="tit"><%=seq %></span>
												</div>
												<button type="button" class="btn_row letter" onclick="javascript:removeRow('<%=seq %>');">- 행삭제</button>
												<table class="tbl_yearend mgn">
													<colgroup>
														<col style="width:71px"/>
														<col style="">
														<col style="width:71px">
														<col style="">
														<col style="width:71px">
														<col style="">
														<col style="width:71px">
														<col style="">
													</colgroup>
													<tbody>
														<tr>
															<th>회사명</th>
															<td colspan="3"><input type="text" name="corporate_nm" id="corporate_nm_<%=seq %>" value="<%=corporate_nm %>" /></td>
															<th>대표자명</th>
															<td><input type="text" name="president" id="president_<%=seq %>" value="<%=president %>" class="w89" /></td>
															<th>사업자번호</th>
															<td><input type="text" name="corporate_no" id="corporate_no_<%=seq %>" value="<%=corporate_no %>" class="w89" /></td>
														</tr>	
														<tr>
															<th>근무기간</th>
															<td colspan="3" class="date">
																<span class="date_box">
																	<input type="text" name="start_work_dt" id="start_work_dt_<%=seq %>" class="serch_date" value="<%=start_work_dt %>">
																	<button type="button" class="btn_date"><span class="blind">날짜선택</span></button>
																</span>
																부터
																<span class="date_box">
																	<input type="text" name="end_work_dt" id="end_work_dt_<%=seq %>" class="serch_date" value="<%=end_work_dt %>">
																	<button type="button" class="btn_date"><span class="blind">날짜선택</span></button>
																</span>
																까지
															</td>
															<th>감면기간</th>
															<td colspan="3" class="date">
																<span class="date_box">
																	<input type="text" name="start_reduce_dt" id="start_reduce_dt_<%=seq %>" class="serch_date" value="<%=start_reduce_dt %>">
																	<button type="button" class="btn_date"><span class="blind">날짜선택</span></button>
																</span>
																부터
																<span class="date_box">
																	<input type="text" name="end_reduce_dt" id="end_reduce_dt_<%=seq %>" class="serch_date" value="<%=end_reduce_dt %>">
																	<button type="button" class="btn_date"><span class="blind">날짜선택</span></button>
																</span>
																까지
															</td>
														</tr>
														<tr>
															<th>지급일자</th>
															<td class="date">
																<span class="date_box">
																	<input type="text" name="salary_dt" id="salary_dt_<%=seq %>" class="serch_date" value="<%=salary_dt %>">
																	<button type="button" class="btn_date"><span class="blind">날짜선택</span></button>
																</span>
															</td>
															<th class="type01">급여총액</th>
															<td><input type="text" name="salary_amt" id="salary_amt_<%=seq %>" class="total_salary onlyNumber w89" value="<%=salary_amt %>" /></td>
															<th class="type01">상여총액</th>
															<td><input type="text" name="bonus_amt" id="bonus_amt_<%=seq %>" class="total_salary onlyNumber w89" value="<%=bonus_amt %>" /></td>
															<th class="type01">인정상여</th>
															<td><input type="text" name="constructive_bonus_amt" id="constructive_bonus_amt_<%=seq %>" class="total_salary onlyNumber w89" value="<%=constructive_bonus_amt %>" /></td>
														</tr>	
														<tr>
															<th>건강보험</th>
															<td><input type="text" name="health_amt" id="health_amt_<%=seq %>" class="onlyNumber w89" value="<%=health_amt %>" /></td>
															<th>고용보험</th>
															<td><input type="text" name="employ_amt" id="employ_amt_<%=seq %>" class="onlyNumber w89" value="<%=employ_amt %>" /></td>
															<th>국민연금</th>
															<td><input type="text" name="kuk_yeon_amt" id="kuk_yeon_amt_<%=seq %>" class="onlyNumber w89" value="<%=kuk_yeon_amt %>" /></td>
															<th>개인연금</th>
															<td><input type="text" name="annuity_amt" id="annuity_amt_<%=seq %>" class="onlyNumber w89" value="<%=annuity_amt %>" /></td>
														</tr>
														<tr>
															<th>소득세</th>
															<td><input type="text" name="income_amt" id="income_amt_<%=seq %>" class="onlyNumber w89" value="<%=income_amt %>" /></td>
															<th>지방소득세</th>
															<td><input type="text" name="jumin_amt" id="jumin_amt_<%=seq %>" class="onlyNumber w89" value="<%=jumin_amt %>" /></td>
															<th>농특세</th>
															<td><input type="text" name="nong_amt" id="nong_amt_<%=seq %>" class="onlyNumber w89" value="<%=nong_amt %>" /></td>
															<td colspan="2">*소득세는 결정세액 입력하세요</td>
														</tr>
													</tbody>
												</table>
				
												<table class="tbl_yearend">
													<colgroup>
														<col style="width:123px"/>
														<col style="">
														<col style="width:123px">
														<col style="">
														<col style="width:123px">
														<col style="">
													</colgroup>
													<tbody>
														<tr>
															<th class="type01">근무처별소득명세합계</th>
															<td colspan="5"><input type="text" name="total_salary" id="total_salary_<%=seq %>" value="<%=total_salary %>" class="ipt_disable onlyNumber w89" readonly /></td>
														</tr>	
														<tr>
															<th class="type01">주식매수선택권 행사이익</th>
															<td><input type="text" name="stock_option_amt" id="stock_option_amt_<%=seq %>" class="total_salary onlyNumber w89" value="<%=stock_option_amt %>" /></td>
															<th class="type01">우리사주조합인출금</th>
															<td><input type="text" name="employ_stock_amt" id="employ_stock_amt_<%=seq %>" class="total_salary onlyNumber w89" value="<%=employ_stock_amt %>" /></td>
															<th class="type01">임원퇴직소득금액한도초과액</th>
															<td><input type="text" name="derector_retirement_amt" id="derector_retirement_amt_<%=seq %>" class="total_salary onlyNumber w89" value="<%=derector_retirement_amt %>" /></td>
														</tr>
													</tbody>
												</table>
				
												<table class="tbl_yearend">
													<colgroup>
														<col style="width:91px"/>
														<col style="">
														<col style="">
														<col style="">
														<col style="">
														<col style="">
													</colgroup>
													<tbody>
														<tr>
															<th class="type02">비과세소득계</th>
															<td colspan="3"><input type="text" name="total_free" id="total_free_<%=seq %>" value="<%=total_free %>" class="ipt_disable onlyNumber w89" readonly /></td>
															<th class="type03">감면소득계</th>
															<td><input type="text" name="reduction_amt" id="reduction_amt_<%=seq %>" value="<%=reduction_amt %>" class="ipt_disable onlyNumber w89" readonly /></td>
														</tr>	
														<tr>
															<th class="type02">국외근로비과세</th>
															<td><input type="text" name="foreign_work_amt" id="foreign_work_amt_<%=seq %>" class="total_free onlyNumber w89" value="<%=foreign_work_amt %>" /></td>
															<th class="type02">연장근무(야간수당) 비과세</th>
															<td><input type="text" name="oevrtime_amt" id="oevrtime_amt_<%=seq %>" class="total_free onlyNumber w89" value="<%=oevrtime_amt %>" /></td>
															<th class="type02">출산보육수당</th>
															<td><input type="text" name="meternity_amt" id="meternity_amt_<%=seq %>" class="total_free onlyNumber w89" value="<%=meternity_amt %>" /></td>
														</tr>
														<tr>
															<th class="type02">연구보조비</th>
															<td><input type="text" name="research_amt" id="research_amt_<%=seq %>" class="total_free onlyNumber w89" value="<%=research_amt %>" /></td>
															<th class="type02">학자금</th>
															<td><input type="text" name="school_expenses_amt" id="school_expenses_amt_<%=seq %>" class="total_free onlyNumber w89" value="<%=school_expenses_amt %>" /></td>
															<th class="type02">취재수당</th>
															<td><input type="text" name="collect_amt" id="collect_amt_<%=seq %>" class="total_free onlyNumber w89" value="<%=collect_amt %>" /></td>
														</tr>
														<tr>
															<th class="type02">벽지수당</th>
															<td><input type="text" name="remote_rural_area_amt" id="remote_rural_area_amt_<%=seq %>" class="total_free onlyNumber w89" value="<%=remote_rural_area_amt %>" /></td>
															<th class="type02">천재지변 재해로 받는 수당</th>
															<td><input type="text" name="natural_disaster_amt" id="natural_disaster_amt_<%=seq %>" class="total_free onlyNumber w89" value="<%=natural_disaster_amt %>" /></td>
															<th class="type02">주식매수선택권</th>
															<td><input type="text" name="stock_purchase_amt" id="stock_purchase_amt_<%=seq %>" class="total_free onlyNumber w89" value="<%=stock_purchase_amt %>" /></td>
														</tr>
														<tr>
															<th class="type03">외국인기술자소득세면제</th>
															<td><input type="text" name="foreigner_amt" id="foreigner_amt_<%=seq %>" class="reduction_amt onlyNumber w89" value="<%=foreigner_amt %>" /></td>
															<th class="type02">우리사주조합인출금50%</th>
															<td><input type="text" name="employ_stock_amt1" id="employ_stock_amt1_<%=seq %>" class="total_free onlyNumber w89" value="<%=employ_stock_amt1 %>" /></td>
															<th class="type02">우리사주조합인출금75%</th>
															<td><input type="text" name="employ_stock_amt2" id="employ_stock_amt2_<%=seq %>" class="total_free onlyNumber w89" value="<%=employ_stock_amt2 %>" /></td>
														</tr>
														<tr>
															<th class="type02">경호수당, 승선수당</th>
															<td><input type="text" name="guard_embark_amt" id="guard_embark_amt_<%=seq %>" class="total_free onlyNumber w89" value="<%=guard_embark_amt %>" /></td>
															<th class="type03">중소기업청년취업소득세감면</th>
															<td><input type="text" name="smiymjtc_amt" id="smiymjtc_amt_<%=seq %>" class="reduction_amt onlyNumber w89" value="<%=smiymjtc_amt %>" /></td>
															<th class="type02">전공의수련보조수당</th>
															<td><input type="text" name="major_amt" id="major_amt_<%=seq %>" class="total_free onlyNumber w89" value="<%=major_amt %>" /></td>
														</tr>
														<tr>
															<th colspan="2" class="type03">해저광물자원개발을위한과세특례</th>
															<td><input type="text" name="submarine_mineral_amt" id="submarine_mineral_amt_<%=seq %>" class="reduction_amt onlyNumber w89" value="<%=submarine_mineral_amt %>" /></td>
															<th colspan="2" class="type02">교육기본법제28조제1항에따라받는장학금</th>
															<td><input type="text" name="scholarship_amt" id="scholarship_amt_<%=seq %>" class="total_free onlyNumber w89" value="<%=scholarship_amt %>" /></td>
														</tr>
														<tr>
															<th colspan="2" class="type02">외국정부또는국제기관에근무하는사람에대한비과세</th>
															<td><input type="text" name="organization_amt" id="organization_amt_<%=seq %>" class="total_free onlyNumber w89" value="<%=organization_amt %>" /></td>
															<th colspan="2" class="type02">시립유치원수석교사의인건비유아교육법시행령</th>
															<td><input type="text" name="kindergarten_teacher_amt" id="kindergarten_teacher_amt_<%=seq %>" class="total_free onlyNumber w89" value="<%=kindergarten_teacher_amt %>" /></td>
														</tr>
														<tr>
															<th colspan="2" class="type02">보육교사인건비영유아보육법시행령</th>
															<td><input type="text" name="childcare_amt" id="childcare_amt_<%=seq %>" class="total_free onlyNumber w89" value="<%=childcare_amt %>" /></td>
															<th colspan="2" class="type03">조세조약상교직자조항의소득세감면</th>
															<td><input type="text" name="teache_clause_amt" id="teache_clause_amt_<%=seq %>" class="reduction_amt onlyNumber w89" value="<%=teache_clause_amt %>" /></td>
														</tr>
														<tr>
															<th colspan="2" class="type02">정부공공기관중지방이전기관종사자이수수당</th>
															<td><input type="text" name="move_amt" id="move_amt_<%=seq %>" class="total_free onlyNumber w89" value="<%=move_amt %>" /></td>
															<th colspan="2" class="type02">법령조례에의한보수를받지않는의원수당</th>
															<td><input type="text" name="legislation_amt" id="legislation_amt_<%=seq %>" class="total_free onlyNumber w89" value="<%=legislation_amt %>" /></td>
														</tr>
														<tr>
															<th colspan="2" class="type02">작전임무수행을위해외국에주둔하는군인받는급여</th>
															<td><input type="text" name="operation_amt" id="operation_amt_<%=seq %>" class="total_free onlyNumber w89" value="<%=operation_amt %>" /></td>
															<th colspan="2" class="type02"></th>
															<td></td>
														</tr>
													</tbody>
												</table>
												<p class="refer">
													중소기업청년취업소득세감면 금액이 있는 경우 감면 비율
													<input type="text" name="smiymjtc_rate" id="smiymjtc_rate_<%=seq %>" value="<%=smiymjtc_rate %>" /> %
												</p>
											</div>
										</td>
									</tr>
								<%
									}
								%>
								</tbody>
							</table>
					</div>
				</div>
			</div>
		</form>
		<button type="button" class="close" onclick="javascript:self.close();"><span class="blind">닫기</span></button>
		<div class="box_btn">
			<button type="button" onclick="javascript:procYearendtaxPreviousWorkplaceAjax();">저장</button><button type="button" onclick="javascript:self.close();">닫기</button>
		</div>
	</div>
</div>
</body>
</html>