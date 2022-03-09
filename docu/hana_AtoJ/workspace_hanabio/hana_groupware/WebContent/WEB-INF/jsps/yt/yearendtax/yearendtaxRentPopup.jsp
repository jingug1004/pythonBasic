<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%--
 * @파일명 : yearendtaxRentPopup.jsp
 * @메뉴명 : 연말정산 등록 > 월세액 팝업
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
	List<YearendtaxVO> houseList = (List<YearendtaxVO>)request.getAttribute("houseList");
	
	String year = (String)request.getAttribute("year");
	String searchType = (String)request.getAttribute("searchType");
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
		});
	
		/* 행 추가 */
		function addRow(){
			
			var seq = $("#houseList tr").size() + 1;
			var html = '';
			html += '<tr id="houseList_'+seq+'">';
			html += '	<th></th>';
			html += '	<td>';
			html += '		<select name="house_gb" id="house_gb_'+seq+'">';
			html += '			<option value="1" selected>월세액</option>';
			html += '		</select>';
			html += '	</td>';
			html += '	<td><input type="text" name="house_nm" id="house_nm_'+seq+'" /></td>';
			html += '	<td><input type="text" name="house_jumin" id="house_jumin_'+seq+'" /></td>';
			html += '	<td class="date">';
			html += '		<span class="date_box">';
			html += '			<input type="text" class="serch_date" name="house_start_dt" id="house_start_dt_'+seq+'" />';
			html += '			<button type="button" class="btn_date"><span class="blind">날짜선택</span></button>';
			html += '		</select>';
			html += '	</td>';
			html += '	<td class="date">';
			html += '		<span class="date_box">';
			html += '			<input type="text" class="serch_date" name="house_end_dt" id="house_end_dt_'+seq+'" />';
			html += '			<button type="button" class="btn_date"><span class="blind">날짜선택</span></button>';
			html += '		</select>';
			html += '	</td>';
			html += '	<td><input type="text" name="house_amt1" id="house_amt1_'+seq+'" value="0" class="onlyNumber" /></td>';
			html += '	<td><input type="text" name="house_amt2" id="house_amt2_'+seq+'" value="0" class="onlyNumber" /></td>';
			html += '	<td><input type="text" name="house_addr" id="house_addr_'+seq+'" class="address" /></td>';
			html += '	<td class="bdrn del_btn_cnt"><a href="javascript:removeRow(\''+seq+'\');"><img alt="삭제" src="/hanagw/asset/img/popup_btn_close01.gif"></a></td>';
			html += '</tr>';
			
			$("#houseList").append(html); // 행추가
			$("#houseList_" + seq + " input[type='text'].onlyNumber").keyup(function(event) { // 유효성 체크 적용
				var keyCode = event.keyCode;
			    if (keyCode != 16 && keyCode != 46 && (keyCode < 35 || keyCode > 40)) { // 16:shift/46:delete/35:end/36:home/37~40:화살표
			    	$(this).val(formCheck.onlyNumber($(this).val()));	
				}
			});
			
			$("#houseList_" + seq + " .btn_date").prev().datepicker({"dateFormat":"yy-mm-dd"});
			$("#houseList_" + seq + " .btn_date").click(function() { // 달력 기능 적용
		        $(this).prev().datepicker({"dateFormat":"yy-mm-dd"});
				$(this).prev().focus();
			});
		}
		
		/* 저장 */
		function procYearendtaxHouseAjax(){
			
			/* 입력선택은 1개씩만 가능하도록 체크 */
			var house_gb1 = 0;
			
			$("select[name='house_gb']").each(function(){
				if ("1" == $(this).val()) {
					house_gb1++;
				}
			});
			
			if (house_gb1 > 1) {
				alert("금전소비대 항목은 1개만 선택 할 수 있습니다..");
				return false;
			}
			
			/* 유효성 체크 */
			for (var i = 1; i <= $("#houseList tr").length; i++) {
					
				var house_nm = $("#house_nm_"+i);
				var house_jumin = $("#house_jumin_"+i);
				var house_start_dt = $("#house_start_dt_"+i);
				var house_end_dt = $("#house_end_dt_"+i);
				var house_addr = $("#house_addr_"+i);
				
				if (formCheck.getByteLength(house_nm.val()) > 20) {
					alert("성명(상호)은 한글 10자 혹은 영문 20자 이내로 입력해주세요.(20byte)");
					house_nm.focus();
					return false;
				}
				
				if (formCheck.getByteLength(formCheck.removeChar(house_jumin.val())) != 10 && formCheck.getByteLength(formCheck.removeChar(house_jumin.val())) != 13) {
					alert("올바른 주민번호(사업자번호)를 입력해주세요.");
					house_jumin.focus();
					return false;
				}
				
				if (!formCheck.isDateFormat(house_start_dt.val())) {
					alert("올바른 계약기간을 입력해주세요.");
					house_start_dt.focus();
					return false;
				}
				
				if (!formCheck.isDateFormat(house_end_dt.val())) {
					alert("올바른 계약기간을 입력해주세요.");
					house_end_dt.focus();
					return false;
				}
				
				if (formCheck.dateChk(house_start_dt.val(), house_end_dt.val()) < 0) {
					alert("계약기간을 확인하세요.");
					return false;
				}
				
				if (formCheck.getByteLength(house_addr.val()) > 100) {
					alert("주소는 한글 50자 혹은 영문 100자 이내로 입력해주세요.(100byte)");
					house_addr.focus();
					return false;
				}
			}
			
			var frm = $("#frm");
			
			$.ajax({
				type:"POST",
				data: frm.serialize(),
				url:"<%=GROUP_ROOT%>/yt/yearendtax/procYearendtaxHouseAjax.do",
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
		
		/* 행 삭제 */
		function removeRow(seq){
			$("#houseList_"+seq).remove();
		}
	</script>
</head>
<body>

<!-- pop up 가로 사이즈 820px -->
<div class="wrap_pop_bg">
	<div class="wrap_statement_overtime">
		<div class="top">
			<h3>월세액</h3>
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
										<input type="hidden" name="searchType" id="searchType" value="<%=searchType %>" />
										
										<table class="tbl_yearend house">
											<tbody>
												<tr>
													<td class="inner">
														<div>
															<table class="tbl_yearend mgn">
																<colgroup>
																	<col style="width:22px"/>
																	<col style="width:72px"/>
																	<col style="width:100px"/>
																	<col style="width:100px"/>
																	<col style="width:101px"/>
																	<col style="width:101px"/>
																	<col style="width:100px"/>
																	<col style="width:100px"/>
																	<col style="width:230px"/>
																	<col style="width:35px" />
																</colgroup>
																<thead>
																	<tr>
																		<th></th>
																		<th>입력선택</th>
																		<th>성명 (상호)</th>
																		<th>주민번호 or 사업자번호</th>
																		<th>계약기간 시작</th>
																		<th>계약기간 종료</th>
																		<th>월세액 합계</th>
																		<th>공제금액</th>
																		<th>임대차 계약서상 주소지</th>
																		<th class="bdrn"></th>
																	</tr>
																</thead>
																<tbody id="houseList">
																<%
																	for(int i = 0; i < houseList.size(); i++){
																		
																		YearendtaxVO houseDetail = houseList.get(i);
																		
																		String seq = StringUtil.nvl(houseDetail.getSeq());
																		String house_gb = StringUtil.nvl(houseDetail.getHouse_gb());
																		String house_nm = StringUtil.nvl(houseDetail.getHouse_nm());
																		String house_jumin = StringUtil.nvl(houseDetail.getHouse_jumin());
																		String house_start_dt = StringUtil.nvl(houseDetail.getHouse_start_dt());
																		String house_end_dt = StringUtil.nvl(houseDetail.getHouse_end_dt());
																		String house_amt1 = StringUtil.makeMoneyType(houseDetail.getHouse_amt1());
																		String house_amt2 = StringUtil.makeMoneyType(houseDetail.getHouse_amt2());
																		String house_addr = StringUtil.nvl(houseDetail.getHouse_addr());
																		
																		if (!"".equals(house_jumin) && house_jumin.length() == 13) {
																			house_jumin = house_jumin.substring(0, 6) + "-" + house_jumin.substring(6, 13);
																		} else if (!"".equals(house_jumin) && house_jumin.length() == 10) {
																			house_jumin = house_jumin.substring(0, 3) + "-" + house_jumin.substring(3, 5) + "-" + house_jumin.substring(5, 10);
																		}
																		
																		if (!"".equals(house_start_dt) && house_start_dt.length() == 8) {
																			house_start_dt = house_start_dt.substring(0, 4) + "-" + house_start_dt.substring(4, 6) + "-" + house_start_dt.substring(6, 8);
																		}
																		
																		if (!"".equals(house_end_dt) && house_end_dt.length() == 8) {
																			house_end_dt = house_end_dt.substring(0, 4) + "-" + house_end_dt.substring(4, 6) + "-" + house_end_dt.substring(6, 8);
																		}
																%>
																	<tr id="houseList_<%=i+1 %>">
																		<th><%=i+1 %></th>
																		<td>
																			<select name="house_gb" id="house_gb_<%=i+1 %>">
																				<option value="1" <% if("1".equals(house_gb)) { %>selected<% } %>>월세액</option>
																			</select>
																		</td>
																		<td><input type="text" name="house_nm" id="house_nm_<%=i+1 %>" value="<%=house_nm %>" /></td>
																		<td><input type="text" name="house_jumin" id="house_jumin_<%=i+1 %>" value="<%=house_jumin %>" /></td>
																		<td class="date">
																			<span class="date_box">
																				<input type="text" class="serch_date" name="house_start_dt" id="house_start_dt_<%=i+1 %>" value="<%=house_start_dt %>" />
																				<button type="button" class="btn_date"><span class="blind">날짜선택</span></button>
																			</span>															
																		</td>
																		<td class="date">
																			<span class="date_box">
																				<input type="text" class="serch_date" name="house_end_dt" id="house_end_dt_<%=i+1 %>" value="<%=house_end_dt %>" />
																				<button type="button" class="btn_date"><span class="blind">날짜선택</span></button>
																			</span>															
																		</td>
																		<td><input type="text" name="house_amt1" id="house_amt1_<%=i+1 %>" value="<%=house_amt1 %>" class="onlyNumber" /></td>
																		<td><input type="text" name="house_amt2" id="house_amt2_<%=i+1 %>" value="<%=house_amt2 %>" class="onlyNumber" /></td>
																		<td><input type="text" name="house_addr" id="house_addr_<%=i+1 %>" value="<%=house_addr %>" class="address" /></td>
																		<td class="bdrn del_btn_cnt"><a href="javascript:removeRow('<%=i+1 %>');"><img alt="삭제" src="/hanagw/asset/img/popup_btn_close01.gif"></a></td>
																	</tr>
																<%	
																	}
																%>
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
			<button type="button" onclick="javascript:procYearendtaxHouseAjax();">저장</button><button type="button" onclick="javascript:self.close();">닫기</button>
		</div>
	</div>
</div>
</body>
</html>