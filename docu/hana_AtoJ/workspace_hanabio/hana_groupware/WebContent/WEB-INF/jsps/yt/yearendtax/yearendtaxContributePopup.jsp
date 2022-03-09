<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%--
 * @파일명 : yearendtaxContributePopup.jsp
 * @메뉴명 : 연말정산 등록 > 기부금 팝업
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
	List<YearendtaxVO> contributeList = (List<YearendtaxVO>)request.getAttribute("contributeList");
	
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
			
			var targetArray = ["don_amt"]; // 합계 계산 대상 name
			
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
		function addRow(){
			
			var seq = $("#contributeList tr").size() + 1;
			var html = '';
			html += '<tr id="contributeList_'+seq+'">';
			html += '	<th></th>';
			html += '	<td>';
			html += '		<select name="gubun" id="gubun_'+seq+'">';
			html += '			<option value="1" selected>기타</option>';
			html += '			<option value="2">국세청</option>';
			html += '		</select>';
			html += '	</td>';
			html += '	<td class="search">';
			html += '		<span class="find_box">';
			html += '			<input type="text" name="rel_jumin_no" id="rel_jumin_no_'+seq+'" class="serch_date" readonly />';
			html += '			<button type="button" class="btn_find" onclick="javascript:getDependentsListLayer(\''+seq+'\');"><span class="blind">찾아보기</span></button>';
			html += '		</span>';
			html += '	</td>';
			html += '	<td><input type="text" name="rel_nm" id="rel_nm_'+seq+'" class="ipt_disable" readonly /></td>';
			html += '	<td>';
			html += '		<select name="yr_don_div" id="yr_don_div_'+seq+'" >';
			html += '			<option value="10" selected>법정기부금</option>';
			html += '			<option value="20">정치자금기부금</option>';
			html += '			<option value="30">특례기부금(공익신탁제외)</option>';
			html += '			<option value="31">공익신탁 기부금</option>';
			html += '			<option value="40">지정기부금(종교신탁제외)</option>';
			html += '			<option value="41">종교단체 기부금</option>';
			html += '			<option value="42">우리사주조합기부금</option>';
			html += '		</select>';
			html += '	</td>';
			html += '	<td><input type="text" name="don_amt" id="don_amt_'+seq+'" value="0" class="onlyNumber" /></td>';
			html += '	<td><input type="text" name="don_nm" id="don_nm_'+seq+'" /></td>';
			html += '	<td><input type="text" name="don_no" id="don_no_'+seq+'" /></td>';
			html += '	<td><input type="text" name="don_receipt_no" id="don_receipt_no_'+seq+'" /></td>';
			html += '	<td class="bdrn del_btn_cnt"><a href="javascript:removeRow(\''+seq+'\');"><img alt="삭제" src="/hanagw/asset/img/popup_btn_close01.gif"></a></td>';
			html += '</tr>';
			
			$("#contributeList").append(html); // 행추가
			$("#contributeList_" + seq + " input[type='text'].onlyNumber").keyup(function(event) { // 유효성 체크 적용
				var keyCode = event.keyCode;
			    if (keyCode != 16 && keyCode != 46 && (keyCode < 35 || keyCode > 40)) { // 16:shift/46:delete/35:end/36:home/37~40:화살표
			    	$(this).val(formCheck.onlyNumber($(this).val()));	
				}
			});
			
			$("#contributeList_" + seq + " input[type='text'].onlyNumber").blur(function(event) { // 금액 입력 후 합계 계산
				calcAmount($(this).attr("name"));
			});
		}
		
		/* 관계인 레이어팝업 열기 */
		function getDependentsListLayer(seq){
			$("#dependentsList").bPopup({
				content:'iframe', //'ajax', 'iframe' or 'image'
				iframeAttr:'scrolling="yes" frameborder="0" width="348px" height="470px"',
				follow: [true, true],
				contentContainer:'.dependents_content',
				modalClose: false,
	            opacity: 0.6,
	            positionStyle: 'fixed',
				loadUrl:'<%=GROUP_ROOT%>/yt/yearendtax/yearendtaxDependentsPopup.do?searchType=contribute&year=' + $("#year").val() + '&seq=' + seq,
			});
		}
		
		/**
		 * 레이어팝업 닫기
		 */
		function layerClose(){ 
			$("#dependentsList").bPopup().close();
		}
		
		/* 행 삭제 */
		function removeRow(seq){
			$("#contributeList_"+seq).remove();
			calcAmount("all");
		}
		
		/* 저장 */
		function procYearendtaxContributeAjax(){
			
			/* 유효성 체크 */
			for (var i = 1; i <= $("#contributeList tr").length; i++) {
					
				var rel_nm = $("#rel_nm_"+i);
				var don_nm = $("#don_nm_"+i);
				var don_no = $("#don_no_"+i);
				var don_receipt_no = $("#don_receipt_no_"+i);
				
				if (formCheck.isEmpty(rel_nm.val())) {
					alert("관계인을 선택해주세요.");
					getDependentsListLayer(i);
					return false;
				}
				
				if (formCheck.getByteLength(don_nm.val()) > 30) {
					alert("기부처 상호는 한글 15자, 영어 30자 이내로 입력해주세요.(30byte)");
					don_nm.focus();
					return false;
				}
				
				if (formCheck.getByteLength(formCheck.removeChar(don_no.val())) != 10) {
					alert("올바른 기부처사업자번호 입력해주세요.");
					don_no.focus();
					return false;
				}
				
				if (formCheck.getByteLength(don_receipt_no.val()) > 20) {
					alert("기부처 상호는 20자 이내로 입력해주세요.(20byte)");
					don_receipt_no.focus();
					return false;
				}
			}
			
			var frm = $("#frm");
			
			$.ajax({
				type:"POST",
				data: frm.serialize(),
				url:"<%=GROUP_ROOT%>/yt/yearendtax/procYearendtaxContributeAjax.do",
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
			<h3>특별공제 <span>(기부금)</span></h3>
		</div>
		<div class="inner">
			<div class="cont_box cont_table06">
				<div class="pop_tit">
					<span class="tit"></span>
				</div>
				<div class="wrap_tbl_yearend">
					<table class="tbl_yearend contribute">
						<tr>
							<td class="box">
								<div>
									<button type="button" class="btn_row letter" onclick="javascript:addRow();">+ 행추가</button>
									<form name="frm" id="frm" method="post" >
										<input type="hidden" name="year" id="year" value="<%=year %>" />
										
										<table class="tbl_yearend medical">
											<tbody>
												<tr>
													<td class="inner">
														<div>
															<table class="tbl_yearend mgn">
																<colgroup>
																	<col style="width:22px"/>
																	<col style="width:72px"/>
																	<col style="width:148px"/>
																	<col style="width:100px"/>
																	<col style="width:168px"/>
																	<col style="width:100px"/>
																	<col style="width:100px"/>
																	<col style="width:100px"/>
																	<col style="width:100px"/>
																	<col style="width:35px" />
																</colgroup>
																<thead>
																	<tr>
																		<th></th>
																		<th>구분</th>
																		<th>주민번호</th>
																		<th>성명</th>
																		<th>기부금유형</th>
																		<th>기부금액</th>
																		<th>기부처상호</th>
																		<th>기부처사업자번호</th>
																		<th>기부처영수증번호</th>
																		<th class="bdrn"></th>
																	</tr>
																</thead>
																<tbody id="contributeList">
																<%
																	for(int i = 0; i < contributeList.size(); i++){
																		
																		YearendtaxVO insuranceDetail = contributeList.get(i);
																		
																		String gubun = StringUtil.nvl(insuranceDetail.getGubun());
																		String rel_jumin_no = StringUtil.nvl(insuranceDetail.getRel_jumin_no());
																		String rel_nm = StringUtil.nvl(insuranceDetail.getRel_nm());
																		String yr_don_div = StringUtil.nvl(insuranceDetail.getYr_don_div());
																		String don_amt = StringUtil.makeMoneyType(insuranceDetail.getDon_amt());
																		String don_nm = StringUtil.nvl(insuranceDetail.getDon_nm());
																		String don_no = StringUtil.nvl(insuranceDetail.getDon_no());
																		String don_receipt_no = StringUtil.nvl(insuranceDetail.getDon_receipt_no());
																		
																		if (!"".equals(rel_jumin_no) && rel_jumin_no.length() == 13) {
																			rel_jumin_no = rel_jumin_no.substring(0, 6) + "-" + rel_jumin_no.substring(6, 13);
																		}
																		
																		if (!"".equals(don_no) && don_no.length() == 10) {
																			don_no = don_no.substring(0, 3) + "-" + don_no.substring(3, 5) + "-" + don_no.substring(5, 10);
																		}
																		
																%>
																	<tr id="contributeList_<%=i+1 %>">
																		<th><%=i+1 %></th>
																		<td>
																			<select name="gubun" id="gubun_<%=i+1 %>">
																				<option value="1" <% if("1".equals(gubun)) { %>selected<% } %>>기타</option>
																				<option value="2" <% if("2".equals(gubun)) { %>selected<% } %>>국세청</option>
																			</select>
																		</td>
																		<td class="search">
																			<span class="find_box">
																				<input type="text" name="rel_jumin_no" id="rel_jumin_no_<%=i+1 %>" value="<%=rel_jumin_no %>" class="serch_date" readonly>
																				<button type="button" class="btn_find" onclick="javascript:getDependentsListLayer('<%=i+1 %>');"><span class="blind">찾아보기</span></button>
																			</span>
																		</td>
																		<td><input type="text" name="rel_nm" id="rel_nm_<%=i+1 %>" value="<%=rel_nm %>" class="ipt_disable" readonly/></td>
																		<td>
																			<select name="yr_don_div" id="yr_don_div_<%=i+1 %>">
																				<option value="10" <% if("10".equals(yr_don_div)) { %>selected<% } %>>법정기부금</option>
																				<option value="20" <% if("20".equals(yr_don_div)) { %>selected<% } %>>정치자금기부금</option>
																				<option value="30" <% if("30".equals(yr_don_div)) { %>selected<% } %>>특례기부금(공익신탁제외)</option>
																				<option value="31" <% if("31".equals(yr_don_div)) { %>selected<% } %>>공익신탁 기부금</option>
																				<option value="40" <% if("40".equals(yr_don_div)) { %>selected<% } %>>지정기부금(종교신탁제외)</option>
																				<option value="41" <% if("41".equals(yr_don_div)) { %>selected<% } %>>종교단체 기부금</option>
																				<option value="42" <% if("42".equals(yr_don_div)) { %>selected<% } %>>우리사주조합기부금</option>
																			</select>
																		</td>
																		<td><input type="text" name="don_amt" id="don_amt_<%=i+1 %>" value="<%=don_amt %>" class="onlyNumber "/></td>
																		<td><input type="text" name="don_nm" id="don_nm_<%=i+1 %>" value="<%=don_nm %>" /></td>
																		<td><input type="text" name="don_no" id="don_no_<%=i+1 %>" value="<%=don_no %>" /></td>
																		<td><input type="text" name="don_receipt_no" id="don_receipt_no_<%=i+1 %>" value="<%=don_receipt_no %>" /></td>
																		<td class="bdrn del_btn_cnt"><a href="javascript:removeRow('<%=i+1 %>');"><img alt="삭제" src="/hanagw/asset/img/popup_btn_close01.gif"></a></td>
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
																	<col style="width:148px"/>
																	<col style="width:100px"/>
																	<col style="width:168px"/>
																	<col style="width:100px"/>
																	<col style="width:100px"/>
																	<col style="width:100px"/>
																	<col style="width:100px"/>
																</colgroup>	
																<tbody>
																	<tr>
																		<td colspan="5">합계</td>
																		<td><input type="text" name="total_don_amt" id="total_don_amt" readonly /></td>
																		<td></td>
																		<td></td>
																		<td></td>
																	</tr>
																</tbody>
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
			<button type="button" onclick="javascript:procYearendtaxContributeAjax();">저장</button><button type="button" onclick="javascript:self.close();">닫기</button>
		</div>
		<!-- 관계인 조회 팝업 -->
		<div id="dependentsList" style="display:none; width:auto; height:auto;">
			<div class="dependents_content"></div> 
		</div>
	</div>
</div>
</body>
</html>