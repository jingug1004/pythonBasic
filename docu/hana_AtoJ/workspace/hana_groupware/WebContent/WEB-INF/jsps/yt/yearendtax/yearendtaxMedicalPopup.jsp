<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%--
 * @파일명 : yearendtaxMedicalPopup.jsp
 * @메뉴명 : 연말정산 등록 > 의료비 팝업
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
	List<YearendtaxVO> medicalList = (List<YearendtaxVO>)request.getAttribute("medicalList");
	
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
			
			var targetArray = ["card_cnt","card_amt"]; // 합계 계산 대상 name
			
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
			
			var seq = $("#medicalList tr").size() + 1;
			var html = '';
			html += '<tr id="medicalList_'+seq+'">';
			html += '	<th></th>';
			html += '	<td class="search">';
			html += '		<span class="find_box">';
			html += '			<input type="text" name="rel_jumin_no" id="rel_jumin_no_'+seq+'" class="serch_date" readonly />';
			html += '			<button type="button" class="btn_find" onclick="javascript:getDependentsListLayer(\''+seq+'\');"><span class="blind">찾아보기</span></button>';
			html += '		</span>';
			html += '	</td>';
			html += '	<td><input type="text" name="rel_nm" id="rel_nm_'+seq+'" class="ipt_disable" readonly/></td>';
			html += '	<td>';
			html += '		<select name="medi_gb" id="medi_gb_'+seq+'" class="sec_cls">';
			html += '			<option value="1" selected>국세청장이 제공하는 의료비 자료</option>';
			html += '			<option value="2" >국민건강보험 공단의 의료비 부담명세서</option>';
			html += '			<option value="3" >진료비 계산서, 약제비 계산서</option>';
			html += '			<option value="4" >장기요양급여비용 명세서</option>';
			html += '			<option value="5" >기타 의료비 영수증</option>';
			html += '		</select>';
			html += '	</td>';
			html += '	<td><input type="text" name="vendor_no" id="vendor_no_'+seq+'" /></td>';
			html += '	<td><input type="text" name="vendor_nm" id="vendor_nm_'+seq+'" class="ipt_workplace"/></td>';
			html += '	<td><input type="text" name="card_cnt" id="card_cnt_'+seq+'" class="ipt_count" value="1" /></td>';
			html += '	<td><input type="text" name="card_amt" id="card_amt_'+seq+'" class="ipt_sum onlyNumber" value="0" /></td>';
			html += '	<td class="bdrn del_btn_cnt"><a href="javascript:removeRow(\''+seq+'\');"><img alt="삭제" src="/hanagw/asset/img/popup_btn_close01.gif"></a></td>';
			html += '</tr>';
			
			$("#medicalList").append(html); // 행추가
			$("#medicalList_" + seq + " input[type='text'].onlyNumber").keyup(function(event) { // 유효성 체크 적용
				var keyCode = event.keyCode;
			    if (keyCode != 16 && keyCode != 46 && (keyCode < 35 || keyCode > 40)) { // 16:shift/46:delete/35:end/36:home/37~40:화살표
			    	$(this).val(formCheck.onlyNumber($(this).val()));	
				}
			});
			
			$("#medicalList_" + seq + " input[type='text'].onlyNumber").blur(function(event) { // 금액 입력 후 합계 계산
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
				loadUrl:'<%=GROUP_ROOT%>/yt/yearendtax/yearendtaxDependentsPopup.do?searchType=medical&year=' + $("#year").val() + '&seq=' + seq,
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
			$("#medicalList_"+seq).remove();
			calcAmount("all");
		}
		
		/* 저장 */
		function procYearendtaxMedicalAjax(){
			
			/* 유효성 체크 */
			for (var i = 1; i <= $("#medicalList tr").length; i++) {
					
				var rel_nm = $("#rel_nm_"+i);
				var vendor_no = $("#vendor_no_"+i);
				var vendor_nm = $("#vendor_nm_"+i);
				var cart_cnt = $("#card_cnt_"+i);
				
				if (formCheck.isEmpty(rel_nm.val())) {
					alert("관계인을 선택해주세요.");
					getDependentsListLayer(i);
					return false;
				}
				
				if (formCheck.getByteLength(formCheck.removeChar(vendor_no.val())) != 10) {
					alert("올바른 지급처사업자번호를 입력해주세요.");
					vendor_no.focus();
					return false;
				}
				
				if (formCheck.getByteLength(vendor_nm.val()) > 30) {
					alert("지급처 사업자명은 한글 15자, 영어 30자 이내로 입력해주세요.(30byte)");
					vendor_nm.focus();
					return false;
				}
				
				if (formCheck.isNumer(cart_cnt.val())) {
					alert("지급건수는 숫자만 입력할 수 있습니다.");
					cart_cnt.focus();
					return false;
				}
			}
			
			var frm = $("#frm");
			
			$.ajax({
				type:"POST",
				data: frm.serialize(),
				url:"<%=GROUP_ROOT%>/yt/yearendtax/procYearendtaxMedicalAjax.do",
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

<div class="wrap_pop_bg">
	<div class="wrap_statement_overtime">
		<div class="top">
			<h3>특별 공제 <span>(의료비)</span></h3>
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
									<button type="button" class="btn_row letter" onclick="javascript:addRow();">+ 행추가</button>
									<form name="frm" id="frm" method="post" >
										<input type="hidden" name="year" id="year" value="<%=year %>" />
										
										<table class="tbl_yearend medical">
											<tbody>
												<tr>
													<td class="inner">
														<div>
															<table>
																<colgroup>
																	<col style="width:22px"/>
																	<col style="width:126px"/>
																	<col style="width:100px"/>
																	<col style="width:212px"/>
																	<col style="width:100px"/>
																	<col style="width:215px"/>
																	<col style="width:51px"/>
																	<col style="width:61px"/>
																	<col style="width:35px;" />
																</colgroup>
																<thead>
																	<tr>
																		<th></th>
																		<th>관계인주민번호</th>
																		<th>이름</th>
																		<th>증빙구분</th>
																		<th>지급처사업자번호</th>
																		<th>지급처 사업자명</th>
																		<th>지급건수</th>
																		<th>지급금액</th>
																		<th class="bdrn"></th>
																	</tr>	
			
																</thead>
																<tbody id="medicalList">
																<%
																	for(int i = 0; i < medicalList.size(); i++){
																		
																		YearendtaxVO insuranceDetail = medicalList.get(i);
																		
																		String rel_jumin_no = StringUtil.nvl(insuranceDetail.getRel_jumin_no());
																		String rel_nm = StringUtil.nvl(insuranceDetail.getRel_nm());
																		String medi_gb = StringUtil.nvl(insuranceDetail.getMedi_gb());
																		String vendor_no = StringUtil.nvl(insuranceDetail.getVendor_no());
																		String vendor_nm = StringUtil.nvl(insuranceDetail.getVendor_nm());
																		String card_cnt = StringUtil.nvl(insuranceDetail.getCard_cnt());
																		String card_amt = StringUtil.makeMoneyType(insuranceDetail.getCard_amt());
																		
																		if (!"".equals(rel_jumin_no) && rel_jumin_no.length() == 13) {
																			rel_jumin_no = rel_jumin_no.substring(0, 6) + "-" + rel_jumin_no.substring(6, 13);
																		}
																		
																		if (!"".equals(vendor_no) && vendor_no.length() == 10) {
																			vendor_no = vendor_no.substring(0, 3) + "-" + vendor_no.substring(3, 5) + "-" + vendor_no.substring(5, 10);
																		}
																%>
																	<tr id="medicalList_<%=i+1 %>">
																		<th><%=i+1 %></th>
																		<td class="search">
																			<span class="find_box">
																				<input type="text" name="rel_jumin_no" id="rel_jumin_no_<%=i+1 %>" value="<%=rel_jumin_no %>" class="serch_date" readonly />
																				<button type="button" class="btn_find" onclick="javascript:getDependentsListLayer('<%=i+1 %>');"><span class="blind">찾아보기</span></button>
																			</span>
																		</td>
																		<td><input type="text" name="rel_nm" id="rel_nm_<%=i+1 %>" value="<%=rel_nm %>" class="ipt_disable" readonly /></td>
																		<td>
																			<select name="medi_gb" id="medi_gb_<%=i+1 %>" class="sec_cls">
																				<option value="1" <% if("1".equals(medi_gb)) { %>selected<% } %>>국세청장이 제공하는 의료비 자료</option>
																				<option value="2" <% if("2".equals(medi_gb)) { %>selected<% } %>>국민건강보험 공단의 의료비 부담명세서</option>
																				<option value="3" <% if("3".equals(medi_gb)) { %>selected<% } %>>진료비 계산서, 약제비 계산서</option>
																				<option value="4" <% if("4".equals(medi_gb)) { %>selected<% } %>>장기요양급여비용 명세서</option>
																				<option value="5" <% if("5".equals(medi_gb)) { %>selected<% } %>>기타 의료비 영수증</option>
																			</select>
																		</td>
																		<td><input type="text" name="vendor_no" id="vendor_no_<%=i+1 %>" value="<%=vendor_no %>" /></td>
																		<td><input type="text" name="vendor_nm" id="vendor_nm_<%=i+1 %>" value="<%=vendor_nm %>" class="ipt_workplace"/></td>
																		<td><input type="text" name="card_cnt" id="card_cnt_<%=i+1 %>" value="<%=card_cnt %>" class="ipt_count"/></td>
																		<td><input type="text" name="card_amt" id="card_amt_<%=i+1 %>" value="<%=card_amt %>" class="ipt_sum onlyNumber"/></td>
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
																	<col style="width:126px"/>
																	<col style="width:100px"/>
																	<col style="width:212px"/>
																	<col style="width:100px"/>
																	<col style="width:215px"/>
																	<col style="width:51px"/>
																	<col style="width:61px"/>
																</colgroup>									
																<tr>
																	<td colspan="6">합계</td>
																	<td><input type="text" name="total_card_cnt" id="total_card_cnt" value="0" class="ipt_count" readonly /></td>
																	<td><input type="text" name="total_card_amt" id="total_card_amt" value="0" class="ipt_sum" readonly /></td>
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
			<button type="button" onclick="javascript:procYearendtaxMedicalAjax();">저장</button><button type="button" onclick="javascript:self.close();">닫기</button>
		</div>
		<!-- 관계인 조회 팝업 -->
		<div id="dependentsList" style="display:none; width:auto; height:auto;">
			<div class="dependents_content"></div> 
		</div>
	</div>
</div>
</body>
</html>