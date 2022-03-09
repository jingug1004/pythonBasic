<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%--
 * @파일명 : yearendtaxCreditCardPopup.jsp
 * @메뉴명 : 연말정산 등록 > 신용카드 팝업
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
	List<YearendtaxVO> creditCardList = (List<YearendtaxVO>)request.getAttribute("creditCardList");
	
	String year = (String)request.getAttribute("year");
%>
<!DOCTYPE html>
<html lang="ko">
<head>
	<%@include file="/include/head.jsp"%>
	<script type="text/javascript">
	
		$(document).ready(function(){
			/* 합계계산 */
			calcAmount("all");
			
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
				calcAmount(target);
			});
		});
	
		/* 합계계산 */
		function calcAmount(target) {
			
			var targetArray = [
				"creditCard","cash","direct","market","pubric_transport",
				"credit_sh","cash_sh","direct_sh","market_sh","pubric_transport_sh",
				"credit_ly","cash_ly","direct_ly","market_ly","pubric_transport_ly"
			]; // 합계 계산 대상 class
			
			for (var i = 0; i < targetArray.length; i++) {
				if ("all" == target || target == targetArray[i]) { // 전체계산이 아닐 경우 특정 값만 계산하도록
					var selector = $("." + targetArray[i]); // class 기준으로 집합 만듬 
					var total = 0; // 합계
					
					selector.each(function(){
						total += Number($(this).val().replace(/,/gi, "")); // 쉼표 제거 후 더함
					});
					
					$("#total_" + targetArray[i]).val(Commons.addComma(total)); // 최종 합계에 쉼표 붙임
				}
			}
		}
		
		/* 행 추가 */
		/* 20150227 행추가 기능 숨김
		부양가족 팝업을 통해서 새 행 추가 가능하도록 */
		function addRow(){
			
			var seq = $("#creditCardList tr").size() + 1;
			var html = '';
			html += '<tr class="creditCardList_'+seq+'">';
			html += '	<th></th>';
			html += '	<td>';
			html += '		<select name="foreign_cd" id="foreign_cd_'+seq+'" class="ipt_disable">';
			html += '			<option value="1" selected>내국인</option>';
			html += '			<option value="9" >외국인</option>';
			html += '		</select>';
			html += '	</td>';
			html += '	<td><input type="text" name="rel_nm" id="rel_nm_'+seq+'" class="ipt_disable" /></td>';
			html += '	<td>';
			html += '		<select name="rel_cd" id="rel_cd_'+seq+'" class="ipt_disable">';
			html += '			<option value="0" selected>소득자본인</option>';
			html += '			<option value="1" >소득자의직계존속</option>';
			html += '			<option value="2" >배우자의직계존속</option>';
			html += '			<option value="3" >배우자</option>';
			html += '			<option value="4" >직계비속(자녀,입양자)</option>';
			html += '			<option value="5" >직계비속(위 항목 제외)</option>';
			html += '			<option value="6" >형제자매</option>';
			html += '			<option value="7" >수급자</option>';
			html += '			<option value="8" >위탁아동</option>';
			html += '		</select>';
			html += '	</td>';
			html += '	<td><input type="text" name="rel_jumin_no" id="rel_jumin_no_'+seq+'" class="ipt_disable" /></td>';
			html += '	<td><input type="text" name="credit_1" id="credit_1_'+seq+'" value="0" class="creditCard onlyNumber" /></td>';
			html += '	<td><input type="text" name="cash_1" id="cash_1_'+seq+'" value="0" class="cash onlyNumber" /></td>';
			html += '	<td><input type="text" name="direct_1" id="direct_1_'+seq+'" value="0" class="direct onlyNumber" /></td>';
			html += '	<td><input type="text" name="market_1" id="market_1_'+seq+'" value="0" class="market onlyNumber" /></td>';
			html += '	<td><input type="text" name="pubric_transport_1" id="pubric_transport_1_'+seq+'" value="0" class="pubric_transport onlyNumber" /></td>';
			html += '	<td><input type="text" name="credit_1_sh" id="credit_1_sh_'+seq+'" value="0" class="credit_sh onlyNumber" /></td>';
			html += '	<td><input type="text" name="cash_1_sh" id="cash_1_sh_'+seq+'" value="0" class="cash_sh onlyNumber" /></td>';
			html += '	<td><input type="text" name="direct_1_sh" id="direct_1_sh_'+seq+'" value="0" class="direct_sh onlyNumber" /></td>';
			html += '	<td><input type="text" name="market_1_sh" id="market_1_sh_'+seq+'" value="0" class="market_sh onlyNumber" /></td>';
			html += '	<td><input type="text" name="pubric_transport_1_sh" id="pubric_transport_1_sh_'+seq+'" value="0" class="pubric_transport_sh onlyNumber" /></td>';
			html += '	<td><input type="text" name="credit_1_ly" id="credit_1_ly_'+seq+'" value="0" class="credit_ly onlyNumber" /></td>';
			html += '	<td><input type="text" name="cash_1_ly" id="cash_1_ly_'+seq+'" value="0" class="cash_ly onlyNumber" /></td>';
			html += '	<td><input type="text" name="direct_1_ly" id="direct_1_ly_'+seq+'" value="0" class="direct_ly onlyNumber" /></td>';
			html += '	<td><input type="text" name="market_1_ly" id="market_1_ly_'+seq+'" value="0" class="market_ly onlyNumber" /></td>';
			html += '	<td class="bdrn"><input type="text" name="pubric_transport_1_ly" id="pubric_transport_1_ly_'+seq+'" value="0" class="pubric_transport_ly onlyNumber" /></td>';
			html += '</tr>';
			html += '<tr class="creditCardList_'+seq+'">';
			html += '	<td colspan="5"></td>';
			html += '	<td><input type="text" name="credit_2" id="credit_2_'+seq+'" value="0" class="creditCard onlyNumber" /></td>';
			html += '	<td><input type="text" name="cash_2" id="cash_2_'+seq+'" value="0" class="cash onlyNumber" /></td>';
			html += '	<td><input type="text" name="direct_2" id="direct_2_'+seq+'" value="0" class="direct onlyNumber" /></td>';
			html += '	<td><input type="text" name="market_2" id="market_2_'+seq+'" value="0" class="market onlyNumber" /></td>';
			html += '	<td><input type="text" name="pubric_transport_2" id="pubric_transport_2_'+seq+'" value="0" class="pubric_transport onlyNumber" /></td>';
			html += '	<td><input type="text" name="credit_2_sh" id="credit_2_sh_'+seq+'" value="0" class="credit_sh onlyNumber" /></td>';
			html += '	<td><input type="text" name="cash_2_sh" id="cash_2_sh_'+seq+'" value="0" class="cash_sh onlyNumber" /></td>';
			html += '	<td><input type="text" name="direct_2_sh" id="direct_2_sh_'+seq+'" value="0" class="direct_sh onlyNumber" /></td>';
			html += '	<td><input type="text" name="market_2_sh" id="market_2_sh_'+seq+'" value="0" class="market_sh onlyNumber" /></td>';
			html += '	<td><input type="text" name="pubric_transport_2_sh" id="pubric_transport_2_sh_'+seq+'" value="0" class="pubric_transport_sh onlyNumber" /></td>';
			html += '	<td><input type="text" name="credit_2_ly" id="credit_2_ly_'+seq+'" value="0" class="credit_ly onlyNumber" /></td>';
			html += '	<td><input type="text" name="cash_2_ly" id="cash_2_ly_'+seq+'" value="0" class="cash_ly onlyNumber" /></td>';
			html += '	<td><input type="text" name="direct_2_ly" id="direct_2_ly_'+seq+'" value="0" class="direct_ly onlyNumber" /></td>';
			html += '	<td><input type="text" name="market_2_ly" id="market_2_ly_'+seq+'" value="0" class="market_ly onlyNumber" /></td>';
			html += '	<td class="bdrn"><input type="text" name="pubric_transport_2_ly" id="pubric_transport_2_ly_'+seq+'" value="0" class="pubric_transport_ly onlyNumber" /></td>';
			html += '</tr>';
			
			$("#creditCardList").append(html); // 행추가
			$(".creditCardList_" + seq + " input[type='text'].onlyNumber").keyup(function(event) { // 유효성 체크 적용
				var keyCode = event.keyCode;
			    if (keyCode != 16 && keyCode != 46 && (keyCode < 35 || keyCode > 40)) { // 16:shift/46:delete/35:end/36:home/37~40:화살표
			    	$(this).val(formCheck.onlyNumber($(this).val()));	
				}
			});
			
			$(".creditCardList_" + seq + " input[type='text'].onlyNumber").blur(function(event) { // 합계 적용
				var cls = $(this).attr("class").split(" ");
				var target = cls[0];
				calcAmount(target);
			});
		}
		
		/* 저장 */
		function procYearendtaxCreditCardAjax(){
			
			var frm = $("#frm");
			
			$.ajax({
				type:"POST",
				data: frm.serialize(),
				url:"<%=GROUP_ROOT%>/yt/yearendtax/procYearendtaxCreditCardAjax.do",
				dataType:"json",
				success:function(data){
					if (data.result == "success") {
						alert("저장되었습니다.");
						opener.getYearendtaxInfoAjax('family');
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
			<h3>그밖의 소득공제 <span>(신용카드 등 사용금액)</span></h3>
		</div>
		<div class="inner">
			<div class="cont_box cont_table06">
				<div class="pop_tit">
					<span class="tit"></span>
				</div>
				<div class="wrap_tbl_yearend">
					<table class="tbl_yearend">
						<tr>
							<td class="box">
								<div>
									<!-- 
									20150227 행추가 기능 숨김
									부양가족 팝업을 통해서 새 행 추가 가능하도록
									 -->
									<!-- <button type="button" class="btn_row letter" onclick="javascript:addRow();">+ 행추가</button> -->
									<form name="frm" id="frm" method="post" >
										<input type="hidden" name="year" id="year" value="<%=year %>" />
										
										<table class="tbl_yearend credit">
											<tbody>
												<tr>
													<td class="inner">
														<div>
															<table>
																<colgroup>
																	<col style="width:22px"/>
																	<col style="width:72px"/>
																	<col style="width:100px"/>
																	<col style="width:128px"/>
																	<col style="width:100px"/>
																	<col style="width:100px"/>
																	<col style="width:100px"/>
																	<col style="width:100px"/>
																	<col style="width:100px"/>
																	<col style="width:100px"/>
																	<col style="width:100px"/>
																	<col style="width:100px"/>
																	<col style="width:100px"/>
																	<col style="width:100px"/>
																	<col style="width:100px"/>
																	<col style="width:100px"/>
																	<col style="width:100px"/>
																	<col style="width:100px"/>
																	<col style="width:100px"/>
																	<col style="width:100px"/>
																</colgroup>
																<thead>
																	<tr>
																		<th colspan="5"></th>
																		<th colspan="5">2014 상반기</th>
																		<th colspan="5">2014 하반기</th>
																		<th colspan="5" class="bdrn type03">2013년 신용카드 등 본인 사용분 입력란</th>
																	</tr>
																	<tr>
																		<th></th>
																		<th>내외국인</th>
																		<th class="type02">성명</th>
																		<th>관계코드</th>
																		<th>관계인주민번호</th>
																		<th>신용카드-국세청</th>
																		<th>현금영수증-국세청</th>
																		<th>직불카드-국세청</th>
																		<th>전통시장-국세청</th>
																		<th>대중교통-국세청</th>
																		<th>신용카드-국세청</th>
																		<th>현금영수증-국세청</th>
																		<th>직불카드-국세청</th>
																		<th>전통시장-국세청</th>
																		<th>대중교통-국세청</th>
																		<th class="type03">신용카드-국세청</th>
																		<th class="type03">현금영수증-국세청</th>
																		<th class="type03">직불카드-국세청</th>
																		<th class="type03">전통시장-국세청</th>
																		<th class="bdrn type03">대중교통-국세청</th>
																	</tr>	
																	<tr>
																		<th colspan="5"></th>
																		<th>그밖의 금액</th>
																		<th>그밖의 금액</th>
																		<th>그밖의 금액</th>
																		<th>그밖의 금액</th>
																		<th>그밖의 금액</th>
																		<th>그밖의 금액</th>
																		<th>그밖의 금액</th>
																		<th>그밖의 금액</th>
																		<th>그밖의 금액</th>
																		<th>그밖의 금액</th>
																		<th class="type03">그밖의 금액</th>
																		<th class="type03">그밖의 금액</th>
																		<th class="type03">그밖의 금액</th>
																		<th class="type03">그밖의 금액</th>
																		<th class="bdrn type03">그밖의 금액</th>
																	</tr>
																</thead>
																<tbody id="creditCardList">
																<%
																	for(int i = 0; i < creditCardList.size(); i++){
																		
																		YearendtaxVO creditCardDetail = creditCardList.get(i);
																		
																		int foreign_cd = creditCardDetail.getForeign_cd();
																		String rel_nm = StringUtil.nvl(creditCardDetail.getRel_nm());
																		String rel_cd = StringUtil.nvl(creditCardDetail.getRel_cd());
																		String rel_jumin_no = StringUtil.nvl(creditCardDetail.getRel_jumin_no());
																		String credit_1 = StringUtil.makeMoneyType(creditCardDetail.getCredit_1());
																		String credit_2 = StringUtil.makeMoneyType(creditCardDetail.getCredit_2());
																		String cash_1 = StringUtil.makeMoneyType(creditCardDetail.getCash_1());
																		String cash_2 = StringUtil.makeMoneyType(creditCardDetail.getCash_2());
																		String direct_1 = StringUtil.makeMoneyType(creditCardDetail.getDirect_1());
																		String direct_2 = StringUtil.makeMoneyType(creditCardDetail.getDirect_2());
																		String market_1 = StringUtil.makeMoneyType(creditCardDetail.getMarket_1());
																		String market_2 = StringUtil.makeMoneyType(creditCardDetail.getMarket_2());
																		String pubric_transport_1 = StringUtil.makeMoneyType(creditCardDetail.getPubric_transport_1());
																		String pubric_transport_2 = StringUtil.makeMoneyType(creditCardDetail.getPubric_transport_2()); 
																		String credit_1_sh = StringUtil.makeMoneyType(creditCardDetail.getCredit_1_sh());
																		String credit_2_sh = StringUtil.makeMoneyType(creditCardDetail.getCredit_2_sh());
																		String cash_1_sh = StringUtil.makeMoneyType(creditCardDetail.getCash_1_sh());
																		String cash_2_sh = StringUtil.makeMoneyType(creditCardDetail.getCash_2_sh());
																		String direct_1_sh = StringUtil.makeMoneyType(creditCardDetail.getDirect_1_sh());
																		String direct_2_sh = StringUtil.makeMoneyType(creditCardDetail.getDirect_2_sh());
																		String market_1_sh = StringUtil.makeMoneyType(creditCardDetail.getMarket_1_sh());
																		String market_2_sh = StringUtil.makeMoneyType(creditCardDetail.getMarket_2_sh());
																		String pubric_transport_1_sh = StringUtil.makeMoneyType(creditCardDetail.getPubric_transport_1_sh());
																		String pubric_transport_2_sh = StringUtil.makeMoneyType(creditCardDetail.getPubric_transport_2_sh());
																		String credit_1_ly = StringUtil.makeMoneyType(creditCardDetail.getCredit_1_ly());
																		String credit_2_ly = StringUtil.makeMoneyType(creditCardDetail.getCredit_2_ly());
																		String cash_1_ly = StringUtil.makeMoneyType(creditCardDetail.getCash_1_ly());
																		String cash_2_ly = StringUtil.makeMoneyType(creditCardDetail.getCash_2_ly());    
																		String direct_1_ly = StringUtil.makeMoneyType(creditCardDetail.getDirect_1_ly());
																		String direct_2_ly = StringUtil.makeMoneyType(creditCardDetail.getDirect_2_ly());
																		String market_1_ly = StringUtil.makeMoneyType(creditCardDetail.getMarket_1_ly());
																		String market_2_ly = StringUtil.makeMoneyType(creditCardDetail.getMarket_2_ly());  
																		String pubric_transport_1_ly = StringUtil.makeMoneyType(creditCardDetail.getPubric_transport_1_ly());
																		String pubric_transport_2_ly = StringUtil.makeMoneyType(creditCardDetail.getPubric_transport_2_ly()); 
																		
																		String foreign_gb = "";
																		
																		if (1 == foreign_cd) {
																			foreign_gb = "내국인";
																		} else if (9 == foreign_cd) {
																			foreign_gb = "외국인";
																		}
																		
																		if ("0".equals(rel_cd)) {
																			rel_cd = "소득자본인";
																		} else if ("1".equals(rel_cd)) {
																			rel_cd = "소득자의직계존속";
																		} else if ("2".equals(rel_cd)) {
																			rel_cd = "배우자의직계존속";
																		} else if ("3".equals(rel_cd)) {
																			rel_cd = "배우자";
																		} else if ("4".equals(rel_cd)) {
																			rel_cd = "직계비속(자녀,입양자)";
																		} else if ("5".equals(rel_cd)) {
																			rel_cd = "직계비속(위 항목 제외)";
																		} else if ("6".equals(rel_cd)) {
																			rel_cd = "형제자매";
																		} else if ("7".equals(rel_cd)) {
																			rel_cd = "수급자";
																		} else if ("8".equals(rel_cd)) {
																			rel_cd = "위탁아동";
																		}
																		
																		if (!"".equals(rel_jumin_no) && rel_jumin_no.length() == 13) {
																			rel_jumin_no = rel_jumin_no.substring(0, 6) + "-" + rel_jumin_no.substring(6, 13);
																		}
																%>
																	<tr class="creditCardList_<%=i+1 %>">
																		<th><%=i+1 %></th>
																		<td>
																			<%-- <select name="foreign_cd" id="foreign_cd_<%=i+1 %>" class="ipt_disable" disabled >
																				<option value="1" <% if(1 == foreign_cd) { %>selected<% } %>>내국인</option>
																				<option value="9" <% if(9 == foreign_cd) { %>selected<% } %>>외국인</option>
																			</select> --%>
																			<input type="text" name="foreign_cd" id="foreign_cd_<%=i+1 %>" class="ipt_disable nation" value="<%=foreign_gb %>" readonly />
																		</td>
																		<td><input type="text" name="rel_nm" id="rel_nm_<%=i+1 %>" class="ipt_disable" value="<%=rel_nm %>" readonly /></td>
																		<td>
																			<%-- <select name="rel_cd" id="rel_cd_<%=i+1 %>" class="ipt_disable" disabled >
																				<option value="0" <% if("0".equals(rel_cd)) { %>selected<% } %>>소득자본인</option>
																				<option value="1" <% if("1".equals(rel_cd)) { %>selected<% } %>>소득자의직계존속</option>
																				<option value="2" <% if("2".equals(rel_cd)) { %>selected<% } %>>배우자의직계존속</option>
																				<option value="3" <% if("3".equals(rel_cd)) { %>selected<% } %>>배우자</option>
																				<option value="4" <% if("4".equals(rel_cd)) { %>selected<% } %>>직계비속(자녀,입양자)</option>
																				<option value="5" <% if("5".equals(rel_cd)) { %>selected<% } %>>직계비속(위 항목 제외)</option>
																				<option value="6" <% if("6".equals(rel_cd)) { %>selected<% } %>>형제자매</option>
																				<option value="7" <% if("7".equals(rel_cd)) { %>selected<% } %>>수급자</option>
																				<option value="8" <% if("8".equals(rel_cd)) { %>selected<% } %>>위탁아동</option>
																			</select> --%>
																			<input type="text" name="rel_cd" id="rel_cd_<%=i+1 %>" class="ipt_disable rel_cd" value="<%=rel_cd %>" readonly />
																		</td>
																		<td><input type="text" name="rel_jumin_no" id="rel_jumin_no_<%=i+1 %>" class="ipt_disable" value="<%=rel_jumin_no %>" readonly /></td>
																		<td><input type="text" name="credit_1" id="credit_1_<%=i+1 %>" class="creditCard onlyNumber" value="<%=credit_1 %>" /></td>
																		<td><input type="text" name="cash_1" id="cash_1_<%=i+1 %>" class="cash onlyNumber" value="<%=cash_1 %>" /></td>
																		<td><input type="text" name="direct_1" id="direct_1_<%=i+1 %>" class="direct onlyNumber" value="<%=direct_1 %>" /></td>
																		<td><input type="text" name="market_1" id="market_1_<%=i+1 %>" class="market onlyNumber" value="<%=market_1 %>" /></td>
																		<td><input type="text" name="pubric_transport_1" id="pubric_transport_1_<%=i+1 %>" class="pubric_transport onlyNumber" value="<%=pubric_transport_1 %>" /></td>
																		<td><input type="text" name="credit_1_sh" id="credit_1_sh_<%=i+1 %>" class="credit_sh onlyNumber" value="<%=credit_1_sh %>" /></td>
																		<td><input type="text" name="cash_1_sh" id="cash_1_sh_<%=i+1 %>" class="cash_sh onlyNumber" value="<%=cash_1_sh %>" /></td>
																		<td><input type="text" name="direct_1_sh" id="direct_1_sh_<%=i+1 %>" class="direct_sh onlyNumber" value="<%=direct_1_sh %>" /></td>
																		<td><input type="text" name="market_1_sh" id="market_1_sh_<%=i+1 %>" class="market_sh onlyNumber" value="<%=market_1_sh %>" /></td>
																		<td><input type="text" name="pubric_transport_1_sh" id="pubric_transport_1_sh_<%=i+1 %>" class="pubric_transport_sh onlyNumber" value="<%=pubric_transport_1_sh %>" /></td>
																		<td><input type="text" name="credit_1_ly" id="credit_1_ly_<%=i+1 %>" class="credit_ly onlyNumber" value="<%=credit_1_ly %>" /></td>
																		<td><input type="text" name="cash_1_ly" id="cash_1_ly_<%=i+1 %>" class="cash_ly onlyNumber" value="<%=cash_1_ly %>" /></td>
																		<td><input type="text" name="direct_1_ly" id="direct_1_ly_<%=i+1 %>" class="direct_ly onlyNumber" value="<%=direct_1_ly %>" /></td>
																		<td><input type="text" name="market_1_ly" id="market_1_ly_<%=i+1 %>" class="market_ly onlyNumber" value="<%=market_1_ly %>" /></td>
																		<td class="bdrn"><input type="text" name="pubric_transport_1_ly" id="pubric_transport_1_ly_<%=i+1 %>" class="pubric_transport_ly onlyNumber" value="<%=pubric_transport_1_ly %>" /></td>
																	</tr>	
																	<tr class="creditCardList_<%=i+1 %>">
																		<td colspan="5"></td>
																		<td><input type="text" name="credit_2" id="credit_2_<%=i+1 %>" class="creditCard onlyNumber" value="<%=credit_2 %>" /></td>
																		<td><input type="text" name="cash_2" id="cash_2_<%=i+1 %>" class="cash onlyNumber" value="<%=cash_2 %>" /></td>
																		<td><input type="text" name="direct_2" id="direct_2_<%=i+1 %>" class="direct onlyNumber" value="<%=direct_2 %>" /></td>
																		<td><input type="text" name="market_2" id="market_2_<%=i+1 %>" class="market onlyNumber" value="<%=market_2 %>" /></td>
																		<td><input type="text" name="pubric_transport_2" id="pubric_transport_2_<%=i+1 %>" class="pubric_transport onlyNumber" value="<%=pubric_transport_2 %>" /></td>
																		<td><input type="text" name="credit_2_sh" id="credit_2_sh_<%=i+1 %>" class="credit_sh onlyNumber" value="<%=credit_2_sh %>" /></td>
																		<td><input type="text" name="cash_2_sh" id="cash_2_sh_<%=i+1 %>" class="cash_sh onlyNumber" value="<%=cash_2_sh %>" /></td>
																		<td><input type="text" name="direct_2_sh" id="direct_2_sh_<%=i+1 %>" class="direct_sh onlyNumber" value="<%=direct_2_sh %>" /></td>
																		<td><input type="text" name="market_2_sh" id="market_2_sh_<%=i+1 %>" class="market_sh onlyNumber" value="<%=market_2_sh %>" /></td>
																		<td><input type="text" name="pubric_transport_2_sh" id="pubric_transport_2_sh_<%=i+1 %>" class="pubric_transport_sh onlyNumber" value="<%=pubric_transport_2_sh %>" /></td>
																		<td><input type="text" name="credit_2_ly" id="credit_2_ly_<%=i+1 %>" class="credit_ly onlyNumber" value="<%=credit_2_ly %>" /></td>
																		<td><input type="text" name="cash_2_ly" id="cash_2_ly_<%=i+1 %>" class="cash_ly onlyNumber" value="<%=cash_2_ly %>" /></td>
																		<td><input type="text" name="direct_2_ly" id="direct_2_ly_<%=i+1 %>" class="direct_ly onlyNumber" value="<%=direct_2_ly %>" /></td>
																		<td><input type="text" name="market_2_ly" id="market_2_ly_<%=i+1 %>" class="market_ly onlyNumber" value="<%=market_2_ly %>" /></td>
																		<td class="bdrn"><input type="text" name="pubric_transport_2_ly" id="pubric_transport_2_ly_<%=i+1 %>" class="pubric_transport_ly onlyNumber" value="<%=pubric_transport_2_ly %>" /></td>
																	</tr>
																<%
																	}
																%>
																</tbody>
															</table>
			
															<table class="tbl_sum">
																<colgroup>
																	<col style="width:22px"/>
																	<col style="width:72px"/>
																	<col style="width:100px"/>
																	<col style="width:100px"/>
																	<col style="width:100px"/>
																	<col style="width:100px"/>
																	<col style="width:100px"/>
																	<col style="width:100px"/>
																	<col style="width:100px"/>
																	<col style="width:100px"/>
																	<col style="width:100px"/>
																	<col style="width:100px"/>
																	<col style="width:100px"/>
																	<col style="width:100px"/>
																	<col style="width:100px"/>
																	<col style="width:100px"/>
																	<col style="width:100px"/>
																	<col style="width:100px"/>
																	<col style="width:100px"/>
																	<col style="width:100px"/>
																</colgroup>									
																<tr>
																	<td colspan="4"></td>
																	<td>합계</td>
																	<td><input type="text" name="total_creditCard" id="total_creditCard" readonly /></td>
																	<td><input type="text" name="total_cash" id="total_cash" readonly /></td>
																	<td><input type="text" name="total_direct" id="total_direct" readonly /></td>
																	<td><input type="text" name="total_market" id="total_market" readonly /></td>
																	<td><input type="text" name="total_pubric_transport" id="total_pubric_transport" readonly /></td>
																	<td><input type="text" name="total_credit_sh" id="total_credit_sh" readonly /></td>
																	<td><input type="text" name="total_cash_sh" id="total_cash_sh" readonly /></td>
																	<td><input type="text" name="total_direct_sh" id="total_direct_sh" readonly /></td>
																	<td><input type="text" name="total_market_sh" id="total_market_sh" readonly /></td>
																	<td><input type="text" name="total_pubric_transport_sh" id="total_pubric_transport_sh" readonly /></td>
																	<td><input type="text" name="total_credit_ly" id="total_credit_ly" readonly /></td>
																	<td><input type="text" name="total_cash_ly" id="total_cash_ly" readonly /></td>
																	<td><input type="text" name="total_direct_ly" id="total_direct_ly" readonly /></td>
																	<td><input type="text" name="total_market_ly" id="total_market_ly" readonly /></td>
																	<td><input type="text" name="total_pubric_transport_ly" id="total_pubric_transport_ly" readonly /></td>
																</tr>
															</table>
														</div>
													</td>
												</tr>
											</tbody>
										</table>
									</form>
								</div>
							</td>
						</tr>
					</table>
				</div>
			</div>
		</div>
		<button type="button" class="close" onclick="javascript:self.close();"><span class="blind">닫기</span></button>
		<div class="box_btn">
			<button type="button" onclick="javascript:procYearendtaxCreditCardAjax();">저장</button><button type="button" onclick="javascript:self.close();">닫기</button>
		</div>
	</div>
</div>
</body>
</html>