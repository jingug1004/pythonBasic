<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%--
 * @파일명 : yearendtaxSavingPopup.jsp
 * @메뉴명 : 연말정산 등록 > 연금저축 팝업
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
	List<YearendtaxVO> savingList = (List<YearendtaxVO>)request.getAttribute("savingList");
	
	String year = (String)request.getAttribute("year");
	String searchType = (String)request.getAttribute("searchType"); // 검색 구분 - P : 개인연금 / E : 주택저축 / L : 증권저축 / A : 연금저축 
	String title = "";
	
	if ("P".equals(searchType)) {
		title = "개인연금저축";
	} else if ("E".equals(searchType)) { 
		title = "특별공제 <span>(주택마련저축)</span>";
	} else if ("L".equals(searchType)) {
		title = "장기집합투자증권저축";
	} else if ("A".equals(searchType)) {
		title = "연금저축공제";
	}
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
			
			var targetArray = ["in_amt"]; // 합계 계산 대상 name
			
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
			
			var seq = $("#savingList tr").size() + 1;
			var searchType = $("#searchType").val();
			var html = '';
			html += '<tr id="savingList_'+seq+'">';
			html += '	<th></th>';
			html += '	<td>';
			html += '		<select name="gongje_gb" id="gongje_gb_'+seq+'" class="sec_cls">';
			
			if ("P" == searchType) {
				html += '			<option value="P1" selected>개인연금저축</option>';
			} else if ("E" == searchType) {
				html += '			<option value="E1" selected>청약저축</option>';
				html += '			<option value="E2">근로자주택마련저축</option>';
				html += '			<option value="E3">주택청약종합저축</option>';
			} else if ("L" == searchType) {
				html += '			<option value="L1" selected>장기집합투자증권</option>';
			} else if ("A" == searchType) {
				html += '			<option value="A1" selected>연금저축계좌</option>';
			}
			
			html += '		</select>';
			html += '	</td>';
			html += '	<td class="search">';
			html += '		<span class="find_box">';
			html += '			<input type="text" name="bank_nm" id="bank_nm_'+seq+'" class="serch_date" readonly >';
			html += '			<button type="button" class="btn_find" onclick="javascript:getFinancialListLayer(\''+seq+'\');"><span class="blind">찾아보기</span></button>';
			html += '		</span>';
			html += '	</td>';
			html += '	<td><input type="text" name="bank_cd" id="bank_cd_'+seq+'" class="ipt_disable" readonly /></td>';
			html += '	<td><input type="text" name="account_no" id="account_no_'+seq+'" /></td>';
			html += '	<td><input type="text" name="in_amt" id="in_amt_'+seq+'" value="0" class="onlyNumber" /></td>';
			html += '	<td class="del_btn_cnt"><a href="javascript:removeRow(\''+seq+'\');"><img alt="삭제" src="/hanagw/asset/img/popup_btn_close01.gif"></a></td>';
			html += '</tr>';
			
			$("#savingList").append(html); // 행추가
			$("#savingList_" + seq + " input[type='text'].onlyNumber").keyup(function(event) { // 유효성 체크 적용
				var keyCode = event.keyCode;
			    if (keyCode != 16 && keyCode != 46 && (keyCode < 35 || keyCode > 40)) { // 16:shift/46:delete/35:end/36:home/37~40:화살표
			    	$(this).val(formCheck.onlyNumber($(this).val()));	
				}
			});
			
			$("#savingList_" + seq + " input[type='text'].onlyNumber").blur(function(event) { // 금액 입력 후 합계 계산
				calcAmount($(this).attr("name"));
			});
		}
		
		/* 금융기관 레이어팝업 열기 */
		function getFinancialListLayer(seq){
			$("#financialList").bPopup({
				content:'iframe', //'ajax', 'iframe' or 'image'
				iframeAttr:'scrolling="yes" frameborder="0" width="348px" height="470px"',
				follow: [true, true],
				contentContainer:'.financial_content',
				modalClose: false,
	            opacity: 0.6,
	            positionStyle: 'fixed',
				loadUrl:'<%=GROUP_ROOT%>/yt/yearendtax/yearendtaxFinancialPopup.do?year=' + $("#year").val() + '&seq=' + seq,
			});
		}
		
		/**
		 * 레이어팝업 닫기
		 */
		function layerClose(){ 
			$("#financialList").bPopup().close();
		}
		
		/* 저장 */
		function procYearendtaxSavingAjax(){
			
			/* 유효성 체크 */
			for (var i = 1; i <= $("#savingList tr").length; i++) {
					
				var bank_cd = $("#bank_cd_"+i);
				var account_no = $("#account_no_"+i);
				
				if (formCheck.isEmpty(bank_cd.val())) {
					alert("금융기관을 선택해주세요.");
					getFinancialListLayer(i);
					return false;
				}
				
				if (formCheck.getByteLength(account_no.val().replace(/,/gi, "")) > 20) {
					alert("계좌번호는 숫자 20자 이내로 입력해주세요.(20byte)");
					account_no.focus();
					return false;
				}
				
				if (formCheck.isEmpty(account_no.val()) || formCheck.isNumDash(account_no.val())) {
					alert("계좌번호를 입력해주세요.");
					account_no.focus();
					return false;
				}
			}
			
			var frm = $("#frm");
			
			$.ajax({
				type:"POST",
				data: frm.serialize(),
				url:"<%=GROUP_ROOT%>/yt/yearendtax/procYearendtaxSavingAjax.do",
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
			$("#savingList_"+seq).remove();
			calcAmount("all");
		}
	</script>
</head>
<body>

<!-- pop up 가로 사이즈 820px -->
<div class="wrap_pop_bg">
	<div class="wrap_statement_overtime">
		<div class="top">
			<h3><%=title %></h3>
		</div>
		<div class="inner">
			<div class="cont_box cont_table06">
				<div class="pop_tit">
					<span class="tit"></span>
				</div>
				<div class="wrap_tbl_yearend">
					<table class="tbl_yearend saving">
						<tr>
							<td class="box">
								<div>
									<button type="button" class="btn_row" onclick="javascript:addRow();">+ 행추가</button>
									<form name="frm" id="frm" method="post" >
										<input type="hidden" name="year" id="year" value="<%=year %>" />
										<input type="hidden" name="searchType" id="searchType" value="<%=searchType %>" />
										
										<table class="tbl_yearend mgn">
											<colgroup>
												<col style="width:22px"/>
												<col style="width:132px"/>
												<col style="width:152px"/>
												<col style=""/>
												<col style=""/>
												<col style=""/>
												<col style="width:30px" />
											</colgroup>
											<thead>
												<tr>
													<th></th>
													<th>소득공제구분</th>
													<th>금융기관명</th>
													<th>금융기관코드</th>
													<th>계좌번호</th>
													<th>불입금액</th>
													<th></th>
												</tr>
											</thead>
											<tbody id="savingList">
											<%
												for(int i = 0; i < savingList.size(); i++){
													
													YearendtaxVO savingDetail = savingList.get(i);
													
													String gongje_gb = StringUtil.nvl(savingDetail.getGongje_gb());
													String bank_nm = StringUtil.nvl(savingDetail.getBank_nm());
													String bank_cd = StringUtil.nvl(savingDetail.getBank_cd());
													String account_no = StringUtil.nvl(savingDetail.getAccount_no());
													String in_amt = StringUtil.makeMoneyType(savingDetail.getIn_amt());
													
											%>
												<tr id="savingList_<%=i+1 %>">
													<th><%=i+1 %></th>
													<td>
														<select name="gongje_gb" id="gongje_gb_<%=i+1 %>" class="sec_cls">
														<%
															if ("P".equals(searchType)) {
														%>
															<option value="P1" <% if("P1".equals(gongje_gb)) { %>selected<% } %>>개인연금저축</option>
														<%
															} else if ("E".equals(searchType)) {
														%>
															<option value="E1" <% if("E1".equals(gongje_gb)) { %>selected<% } %>>청약저축</option>
															<option value="E2" <% if("E2".equals(gongje_gb)) { %>selected<% } %>>근로자주택마련저축</option>
															<option value="E3" <% if("E3".equals(gongje_gb)) { %>selected<% } %>>주택청약종합저축</option>
														<%
															} else if ("L".equals(searchType)) {
														%>
															<option value="L1" <% if("L1".equals(gongje_gb)) { %>selected<% } %>>장기집합투자증권</option>
														<%
															} else if ("A".equals(searchType)) {
														%>
															<option value="A1" <% if("A1".equals(gongje_gb)) { %>selected<% } %>>연금저축계좌</option>
														<%
															}
														%>
														</select>
													</td>
													<td class="search">
														<span class="find_box">
															<input type="text" name="bank_nm" id="bank_nm_<%=i+1 %>" value="<%=bank_nm %>" class="serch_date" readonly >
															<button type="button" class="btn_find" onclick="javascript:getFinancialListLayer('<%=i+1 %>');"><span class="blind">찾아보기</span></button>
														</span>
													</td>
													<td><input type="text" name="bank_cd" id="bank_cd_<%=i+1 %>" value="<%=bank_cd %>" class="ipt_disable" readonly /></td>
													<td><input type="text" name="account_no" id="account_no_<%=i+1 %>" value="<%=account_no %>" /></td>
													<td><input type="text" name="in_amt" id="in_amt_<%=i+1 %>" value="<%=in_amt %>" class="onlyNumber" /></td>
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
												<col style="width:152px"/>
												<col style=""/>
												<col style=""/>
												<col style=""/>
											</colgroup>	
											<tbody>
												<tr>
													<td colspan="5"></td>
													<td>합계 <input type="text" name="total_in_amt" id="total_in_amt" readonly /></td>
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
			<button type="button" onclick="javascript:procYearendtaxSavingAjax();">저장</button><button type="button" onclick="javascript:self.close();">닫기</button>
		</div>
		<!-- 금융기관 조회 팝업 -->
		<div id="financialList" style="display:none; width:auto; height:auto;">
			<div class="financial_content"></div> 
		</div>
	</div>
</div>
</body>
</html>