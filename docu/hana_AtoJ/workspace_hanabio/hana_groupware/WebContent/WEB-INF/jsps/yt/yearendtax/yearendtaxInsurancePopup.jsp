<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%--
 * @파일명 : yearendtaxInsurancePopup.jsp
 * @메뉴명 : 연말정산 등록 > 보험료 팝업
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
	List<YearendtaxVO> insuranceList = (List<YearendtaxVO>)request.getAttribute("insuranceList");
	
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
			
			/* 금액 입력 후 합계 계산 */
			$("input[type='text'].onlyNumber").blur(function(event) {
				calcAmount($(this).attr("name"));
			});
		});
	
		/* 합계계산 */
		function calcAmount(target) {
			
			var targetArray = ["insurance_person_1","insurance_person_2","insurance_disabled_person_1","insurance_disabled_person_2"]; // 합계 계산 대상 name
			
			for (var i = 0; i < targetArray.length; i++) {
				if ("all" == target || target == targetArray[i]) { // 전체계산이 아닐 경우 특정 값만 계산하도록
					var selector = $("input[name='"+targetArray[i]+"']"); // name 기준으로 집합 만듬 
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
			
			var seq = $("#insuranceList tr").size() + 1;
			var html = '';
			html += '<tr id="insuranceList_'+seq+'">';
			html += '	<th></th>';
			html += '	<td>';
			html += '		<select name="foreign_cd" id="foreign_cd_'+seq+'">';
			html += '			<option value="1" selected>내국인</option>';
			html += '			<option value="9" >외국인</option>';
			html += '		</select>';
			html += '	</td>';
			html += '	<td><input type="text" name="rel_nm" id="rel_nm_'+seq+'" /></td>';
			html += '	<td>';
			html += '		<select name="rel_cd" id="rel_cd_'+seq+'" class="sec_code">';
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
			html += '	<td><input type="text" name="rel_jumin_no" id="rel_jumin_no_'+seq+'" /></td>';
			html += '	<td><input type="text" name="insurance_person_1" id="insurance_person_1_'+seq+'" value="0" class="onlyNumber" /></td>';
			html += '	<td><input type="text" name="insurance_person_2" id="insurance_person_2_'+seq+'" value="0" class="onlyNumber" /></td>';
			html += '	<td><input type="text" name="insurance_disabled_person_1" id="insurance_disabled_person_1_'+seq+'" value="0" class="onlyNumber" /></td>';
			html += '	<td class="bdrn"><input type="text" name="insurance_disabled_person_2" id="insurance_disabled_person_2_'+seq+'" value="0" class="onlyNumber" /></td>';
			html += '</tr>';
			
			$("#insuranceList").append(html); // 행추가
			$("#insuranceList_" + seq + " input[type='text'].onlyNumber").keyup(function(event) { // 유효성 체크 적용
				var keyCode = event.keyCode;
			    if (keyCode != 16 && keyCode != 46 && (keyCode < 35 || keyCode > 40)) { // 16:shift/46:delete/35:end/36:home/37~40:화살표
			    	$(this).val(formCheck.onlyNumber($(this).val()));	
				}
			});
			
			$("#insuranceList_" + seq + " input[type='text'].onlyNumber").blur(function(event) { // 금액 입력 후 합계 계산
				calcAmount($(this).attr("name"));
			});
		}
		
		/* 저장 */
		function procYearendtaxInsuranceAjax(){
			
			var frm = $("#frm");
			
			$.ajax({
				type:"POST",
				data: frm.serialize(),
				url:"<%=GROUP_ROOT%>/yt/yearendtax/procYearendtaxInsuranceAjax.do",
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
			<h3>특별 공제 <span>(보험료)</span></h3>
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
										
										<table class="tbl_yearend insurance">
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
																</colgroup>
																<thead>
																	<tr>
																		<th></th>
																		<th class="type02">내외국인</th>
																		<th class="type02">성명</th>
																		<th class="type02">관계코드</th>
																		<th class="type02">관계인주민번호</th>
																		<th>보장성보험-국세청</th>
																		<th>보장성보험-그밖의자료</th>
																		<th>장애인보장성보험-국세청</th>
																		<th class="bdrn">장애인보장성보험-그밖의자료</th>
																	</tr>	
			
																</thead>
																<tbody id="insuranceList">
																<%
																	for(int i = 0; i < insuranceList.size(); i++){
																		
																		YearendtaxVO insuranceDetail = insuranceList.get(i);
																		
																		int foreign_cd = insuranceDetail.getForeign_cd();
																		String rel_nm = StringUtil.nvl(insuranceDetail.getRel_nm());
																		String rel_cd = StringUtil.nvl(insuranceDetail.getRel_cd());
																		String rel_jumin_no = StringUtil.nvl(insuranceDetail.getRel_jumin_no());
																		String insurance_person_1 = StringUtil.makeMoneyType(insuranceDetail.getInsurance_person_1());
																		String insurance_person_2 = StringUtil.makeMoneyType(insuranceDetail.getInsurance_person_2());
																		String insurance_disabled_person_1 = StringUtil.makeMoneyType(insuranceDetail.getInsurance_disabled_person_1());
																		String insurance_disabled_person_2 = StringUtil.makeMoneyType(insuranceDetail.getInsurance_disabled_person_1());
																		
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
																	<tr id="insuranceList_<%=i+1 %>">
																		<th><%=i+1 %></th>
																		<td>
																			<%-- <select name="foreign_cd" id="foreign_cd_<%=i+1 %>" class="ipt_disable" disabled >
																				<option value="1" <% if(1 == foreign_cd) { %>selected<% } %>>내국인</option>
																				<option value="9" <% if(9 == foreign_cd) { %>selected<% } %>>외국인</option>
																			</select> --%>
																			<input type="text" name="foreign_cd" id="foreign_cd_<%=i+1 %>" class="ipt_disable nation" value="<%=foreign_gb %>" readonly />
																		</td>
																		<td><input type="text" name="rel_nm" id="rel_nm_<%=i+1 %>" value="<%=rel_nm %>" class="ipt_disable" readonly /></td>
																		<td>
																			<%-- <select name="rel_cd" id="rel_cd_<%=i+1 %>" class="ipt_disable" disabled>
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
																		<td><input type="text" name="rel_jumin_no" id="rel_jumin_no_<%=i+1 %>" value="<%=rel_jumin_no %>" class="ipt_disable" readonly/></td>
																		<td><input type="text" name="insurance_person_1" id="insurance_person_1_<%=i+1 %>" value="<%=insurance_person_1 %>" class="onlyNumber" /></td>
																		<td><input type="text" name="insurance_person_2" id="insurance_person_2_<%=i+1 %>" value="<%=insurance_person_2 %>" class="onlyNumber" /></td>
																		<td><input type="text" name="insurance_disabled_person_1" id="insurance_disabled_person_1_<%=i+1 %>" value="<%=insurance_disabled_person_1 %>" class="onlyNumber" /></td>
																		<td class="bdrn"><input type="text" name="insurance_disabled_person_2" id="insurance_disabled_person_2_<%=i+1 %>" value="<%=insurance_disabled_person_2 %>" class="onlyNumber" /></td>
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
																	<col style="width:98px"/>
																	<col style="width:100px"/>
																	<col style="width:100px"/>
																	<col style="width:100px"/>
																	<col style="width:100px"/>
																	<col style="width:100px"/>
																</colgroup>									
																<tr>
																	<td colspan="4"></td>
																	<td>합계</td>
																	<td><input type="text" name="total_insurance_person_1" id="total_insurance_person_1" readonly /></td>
																	<td><input type="text" name="total_insurance_person_2" id="total_insurance_person_2" readonly /></td>
																	<td><input type="text" name="total_insurance_disabled_person_1" id="total_insurance_disabled_person_1" readonly /></td>
																	<td><input type="text" name="total_insurance_disabled_person_2" id="total_insurance_disabled_person_2" readonly /></td>
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
			<button type="button" onclick="javascript:procYearendtaxInsuranceAjax();">저장</button><button type="button" onclick="javascript:self.close();">닫기</button>
		</div>
	</div>
</div>
</body>
</html>