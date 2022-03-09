<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%--
 * @파일명 : yearendtaxDependentsPopup.jsp
 * @메뉴명 : 연말정산 등록 > 관계인 레이어 팝업
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
	List<YearendtaxVO> dependentsList = (List<YearendtaxVO>)request.getAttribute("dependentsList");
	
	String seq = (String)request.getAttribute("seq");
	String searchType = (String)request.getAttribute("searchType");
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
			var selectedBankCode = $("#rel_jumin_no_" + selectedRow).val();
			var selectrdBankName = $("#rel_nm_" + selectedRow).val();
			var targetSeq = $("#seq").val();
			
			$("#rel_jumin_no_" + targetSeq, top.document).val(selectedBankCode);
			$("#rel_nm_" + targetSeq, top.document).val(selectrdBankName);
			
			layerClose();
		}
	</script>
</head>
<body>
<!-- ######## start ####### -->
	
<!--  popup start --><!-- pop up 가로 사이즈 330px -->
		<div class="individ_pop">
			<div class="popup_box">
				<h3>관계인선택</h3>
				<div class="pop_con_all pop_register pop_yearend rel">
					<div class="search_box03">
						<form name="frm" id="frm" method="post" >
							<input type="hidden" name="seq" id="seq" value="<%=seq %>" />
							<input type="hidden" name="searchCode" id="searchCode" value="<%=searchCode %>" />
							
							<div class="float_l">
								<p>
									<span>주민등록번호</span>
									<input type="text" name="searchCode" id="searchCode" value="<%=searchCode %>" />
								</p>
								<p>
									<span>가족이름</span>
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
								<col style="width:95px"/>
								<col style="width:95px"/>
								<col style=""/>
							</colgroup>
							<thead>
								<tr>
									<th>√</th>
									<th>주민번호</th>
									<th>관계</th>
									<th class="bdrn">성명</th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<td colspan="4" class="inner">
										<div>
											<table>
												<colgroup>
													<col style="width:22px"/>
													<col style="width:95px"/>
													<col style="width:95px"/>
													<col style=""/>												
												</colgroup>
												<tbody>
												<%
													for(int i = 0; i < dependentsList.size(); i++){
														
														YearendtaxVO dependentsDetail = dependentsList.get(i);
														
														String rel_jumin_no = StringUtil.nvl(dependentsDetail.getRel_jumin_no());
														String rel_cd_nm = StringUtil.nvl(dependentsDetail.getRel_cd_nm());
														String rel_nm = StringUtil.nvl(dependentsDetail.getRel_nm());
														
														if (!"".equals(rel_jumin_no) && rel_jumin_no.length() == 13) {
															rel_jumin_no = rel_jumin_no.substring(0, 6) + "-" + rel_jumin_no.substring(6, 13);
														}
												%>
													<tr>
														<td>
															<input type="radio" name="selectedRow" id="selectedRow_<%=i+1 %>" value="<%=i+1 %>" />
															<input type="hidden" name="rel_jumin_no" id="rel_jumin_no_<%=i+1 %>" value="<%=rel_jumin_no %>" />
															<input type="hidden" name="rel_nm" id="rel_nm_<%=i+1 %>" value="<%=rel_nm %>" />
														</td>
														<td><%=rel_jumin_no %></td>
														<td><%=rel_cd_nm %></td>
														<td class="bdrn"><%=rel_nm %></td>
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