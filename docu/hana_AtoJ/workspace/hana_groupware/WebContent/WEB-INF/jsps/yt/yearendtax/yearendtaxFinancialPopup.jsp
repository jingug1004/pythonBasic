<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%--
 * @파일명 : yearendtaxFinancialPopup.jsp
 * @메뉴명 : 연말정산 등록 > 금융기관 레이어 팝업
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
	List<YearendtaxVO> financialList = (List<YearendtaxVO>)request.getAttribute("financialList");
	
	String year = (String)request.getAttribute("year");
	String seq = (String)request.getAttribute("seq");
	String searchCode = (String)request.getAttribute("searchCode");
	String searchName = (String)request.getAttribute("searchName");
%>
<!DOCTYPE html>
<html lang="ko">
<head>
	<%@include file="/include/head.jsp"%>
	<script type="text/javascript">
	
		/* 검색 */
		function goSearch(){
			$("#frm").submit();
		}
		
		/* 레이어 닫기 */
		function layerClose(){
			top.layerClose();
		}
		
		/* 금융기관 선택 */
		function selectBank(){
			
			var selectedRow = $(":radio[name='selectedRow']:checked").val();
			var selectedBankCode = $("#bank_cd_" + selectedRow).val();
			var selectrdBankName = $("#bank_nm_" + selectedRow).val();
			var targetSeq = $("#seq").val();
			
			$("#bank_cd_" + targetSeq, top.document).val(selectedBankCode);
			$("#bank_nm_" + targetSeq, top.document).val(selectrdBankName);
			
			layerClose();
		}
	</script>
</head>
<body>
<!-- ######## start ####### -->
	
<!--  popup start --><!-- pop up 가로 사이즈 330px -->
		<div class="individ_pop">
			<div class="popup_box">
				<h3>금융기관선택</h3>
				<div class="pop_con_all pop_register pop_yearend">
					<div class="search_box03">
						<form name="frm" id="frm" method="post" >
							<input type="hidden" name="year" id="year" value="<%=year %>" />
							<input type="hidden" name="seq" id="seq" value="<%=seq %>" />
							
							<div class="float_l">
								<p>
									<span>금융기관코드</span>
									<input type="text" name="searchCode" id="searchCode" value="<%=searchCode %>" />
								</p>
								<p>
									<span>금융기관명</span>
									<input type="text" name="searchName" id="searchName" value="<%=searchName %>" />
								</p>
							</div>
							<button type="button" class="search02" onclick="javascript:goSearch();">검색</button>
						</form>
					</div>
					<div class="box_scroll">
						<table>
							<colgroup>
								<col style="width:22px"/>
								<col style="width:65px"/>
								<col style=""/>
							</colgroup>
							<thead>
								<tr>
									<th></th>
									<th>은행코드</th>
									<th class="bdrn">금융기관명</th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<td colspan="3" class="inner">
										<div>
											<table>
												<colgroup>
													<col style="width:22px"/>
													<col style="width:65px"/>
													<col style=""/>													
												</colgroup>
												<tbody>
												<%
													for(int i = 0; i < financialList.size(); i++){
														
														YearendtaxVO financialDetail = financialList.get(i);
														
														String bank_cd = StringUtil.nvl(financialDetail.getBank_cd());
														String bank_nm = StringUtil.nvl(financialDetail.getBank_nm());
												%>
													<tr>
														<td>
															<input type="radio" name="selectedRow" id="selectedRow_<%=i+1 %>" value="<%=i+1 %>" />
															<input type="hidden" name="bank_cd" id="bank_cd_<%=i+1 %>" value="<%=bank_cd %>" />
															<input type="hidden" name="bank_nm" id="bank_nm_<%=i+1 %>" value="<%=bank_nm %>" />
														</td>
														<td><%=bank_cd %></td>
														<td class="bdrn"><%=bank_nm %></td>
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
					</div>
				</div>
				<div class="btn_pop">
					<button type="button" class="type_01" onclick="javascript:selectBank();">확인</button>
					<button type="button" class="type_01" onclick="javascript:layerClose();">닫기</button>
				</div>
				<button type="button" class="close" onclick="javascript:layerClose();"><span class="blind">닫기</span></button>
			</div>
		</div>
		<!--  popup end -->		

<!-- ######## end ######### -->
</body>
</html>