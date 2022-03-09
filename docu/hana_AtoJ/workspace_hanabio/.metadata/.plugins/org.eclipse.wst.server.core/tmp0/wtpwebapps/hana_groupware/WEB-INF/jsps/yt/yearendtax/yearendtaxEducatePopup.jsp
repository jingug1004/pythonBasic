<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%--
 * @파일명 : yearendtaxEducatePopup.jsp
 * @메뉴명 : 연말정산 등록 > 교육비 팝업
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
	List<YearendtaxVO> educateList = (List<YearendtaxVO>)request.getAttribute("educateList");
	
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
			
			var targetArray = ["public_tx_amt"]; // 합계 계산 대상 name
			
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
			
			var seq = $("#educateList tr").size() + 1;
			var html = '';
			html += '<tr id="educateList_'+seq+'">';
			html += '	<th></th>';
			html += '	<td class="search">';
			html += '		<span class="find_box">';
			html += '			<input type="text" name="rel_jumin_no" id="rel_jumin_no_'+seq+'" class="serch_date" readonly />';
			html += '			<button type="button" class="btn_find" onclick="javascript:getDependentsListLayer(\''+seq+'\');"><span class="blind">찾아보기</span></button>';
			html += '		</span>';
			html += '	</td>';
			html += '	<td><input type="text" name="rel_nm" id="rel_nm_'+seq+'" class="ipt_disable" readonly /></td>';
			html += '	<td>';
			html += '		<select name="edu_org" id="edu_org'+seq+'" class="sec_cls">';
			html += '			<option value="10" selected>취학전아동</option>';
			html += '			<option value="20" >초.중.고 학생</option>';
			html += '			<option value="30" >대학교 교육비</option>';
			html += '		</select>';
			html += '	</td>';
			html += '	<td><input type="text" name="public_tx_amt" id="public_tx_amt_'+seq+'" value="0" class="onlyNumber" /></td>';
			html += '	<td class="del_btn_cnt"><a href="javascript:removeRow(\''+seq+'\');"><img alt="삭제" src="/hanagw/asset/img/popup_btn_close01.gif"></a></td>';
			html += '</tr>';
			
			$("#educateList").append(html); // 행추가
			$("#educateList_" + seq + " input[type='text'].onlyNumber").keyup(function(event) { // 유효성 체크 적용
				var keyCode = event.keyCode;
			    if (keyCode != 16 && keyCode != 46 && (keyCode < 35 || keyCode > 40)) { // 16:shift/46:delete/35:end/36:home/37~40:화살표
			    	$(this).val(formCheck.onlyNumber($(this).val()));	
				}
			});
			
			$("#educateList_" + seq + " input[type='text'].onlyNumber").blur(function(event) { // 금액 입력 후 합계 계산
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
				loadUrl:'<%=GROUP_ROOT%>/yt/yearendtax/yearendtaxDependentsPopup.do?searchType=educate&year=' + $("#year").val() + '&seq=' + seq,
			});
		}
		
		/**
		 * 레이어팝업 닫기
		 */
		function layerClose(){ 
			$("#dependentsList").bPopup().close();
		}
		
		/* 저장 */
		function procYearendtaxEducateAjax(){
			
			/* 유효성 체크 */
			for (var i = 1; i <= $("#educateList tr").length; i++) {
					
				var rel_nm = $("#rel_nm_"+i);
				
				if (formCheck.isEmpty(rel_nm.val())) {
					alert("관계인을 선택해주세요.");
					getDependentsListLayer(i);
					return false;
				}
			}
			
			var frm = $("#frm");
			
			$.ajax({
				type:"POST",
				data: frm.serialize(),
				url:"<%=GROUP_ROOT%>/yt/yearendtax/procYearendtaxEducateAjax.do",
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
			$("#educateList_"+seq).remove();
			calcAmount("all");
		}
	</script>
</head>
<body>

<div class="wrap_pop_bg">
	<div class="wrap_statement_overtime">
		<div class="top">
			<h3>특별공제 <span>(교육비)</span></h3>
		</div>
		<div class="inner">
			<div class="cont_box cont_table06">
				<div class="pop_tit">
					<span class="tit"></span>
				</div>
				<div class="wrap_tbl_yearend">
					<table class="tbl_yearend educate">
						<tr>
							<td class="box">
								<div>
									<button type="button" class="btn_row letter" onclick="javascript:addRow();">+ 행추가</button>
									<form name="frm" id="frm" method="post" >
										<input type="hidden" name="year" id="year" value="<%=year %>" />
										
										<table class="tbl_yearend mgn">
											<colgroup>
												<col style="width:22px"/>
												<col style="width:148px"/>
												<col style=""/>
												<col style=""/>
												<col style=""/>
												<col style="width:35px"/>
											</colgroup>
											<thead>
												<tr>
													<th></th>
													<th>주민번호</th>
													<th>성명</th>
													<th>교육기관</th>
													<th>공과금</th>
													<th></th>
												</tr>
											</thead>
											<tbody id="educateList">
											<%
												for(int i = 0; i < educateList.size(); i++){
													
													YearendtaxVO insuranceDetail = educateList.get(i);
													
													String rel_jumin_no = StringUtil.nvl(insuranceDetail.getRel_jumin_no());
													String rel_nm = StringUtil.nvl(insuranceDetail.getRel_nm());
													String edu_org = StringUtil.nvl(insuranceDetail.getEdu_org());
													String public_tx_amt = StringUtil.makeMoneyType(insuranceDetail.getPublic_tx_amt());
													
													if (!"".equals(rel_jumin_no) && rel_jumin_no.length() == 13) {
														rel_jumin_no = rel_jumin_no.substring(0, 6) + "-" + rel_jumin_no.substring(6, 13);
													}
													
											%>
												<tr id="educateList_<%=i+1 %>">
													<th><%=i+1 %></th>
													<td class="search">
														<span class="find_box">
															<input type="text" name="rel_jumin_no" id="rel_jumin_no_<%=i+1 %>" value="<%=rel_jumin_no %>" class="serch_date" readonly />
															<button type="button" class="btn_find" onclick="javascript:getDependentsListLayer('<%=i+1 %>');"><span class="blind">찾아보기</span></button>
														</span>
													</td>
													<td><input type="text" name="rel_nm" id="rel_nm_<%=i+1 %>" value="<%=rel_nm %>" class="ipt_disable" readonly /></td>
													<td>
														<select name="edu_org" id="edu_org_<%=i+1 %>">
															<option value="10" <% if("10".equals(edu_org)) { %>selected<% } %>>취학전아동</option>
															<option value="20" <% if("20".equals(edu_org)) { %>selected<% } %>>초.중.고 학생</option>
															<option value="30" <% if("30".equals(edu_org)) { %>selected<% } %>>대학교 교육비</option>
														</select>
													</td>
													<td><input type="text" name="public_tx_amt" id="public_tx_amt_<%=i+1 %>" value="<%=public_tx_amt %>" class="onlyNumber" /></td>
													<td class="del_btn_cnt"><a href="javascript:removeRow('<%=i+1 %>');"><img alt="삭제" src="/hanagw/asset/img/popup_btn_close01.gif"></a></td>
												</tr>
											<%
												}
											%>
											</tbody>
										</table>
		
										<table class="tbl_sum">
											<colgroup>
												<col style="width:22px"/>
												<col style="width:132px"/>
												<col style=""/>
												<col style=""/>
												<col style=""/>
											</colgroup>	
											<tbody>
												<tr>
													<td colspan="4"></td>
													<td>합계 <input type="text" name="total_public_tx_amt" id="total_public_tx_amt" readonly /></td>
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
			<button type="button" onclick="javascript:procYearendtaxEducateAjax();">저장</button><button type="button" onclick="javascript:self.close();">닫기</button>
		</div>
		<!-- 관계인 조회 팝업 -->
		<div id="dependentsList" style="display:none; width:auto; height:auto;">
			<div class="dependents_content"></div> 
		</div>
	</div>
</div>
</body>
</html>