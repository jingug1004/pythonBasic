<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%--
 * @파일명 : previewYearendtaxDetail.jsp
 * @메뉴명 : 연말정산등록 > 우편번호 팝업 > 우편번호 찾기 레이어 팝업
 * @최초작성일 : 2015/02/16
 * @author : 정진묵               
 * @수정내역 : 
--%>
<%@ page import="com.hanaph.gw.yt.yearendtax.vo.YearendtaxAddressVO" %>
<%@ page import="com.hanaph.gw.co.common.utils.StringUtil" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList"%>
<%@ include file ="/common/path.jsp" %>
<%
	@SuppressWarnings("unchecked")
	List<YearendtaxAddressVO> searchAddressList = (List<YearendtaxAddressVO>)request.getAttribute("searchAddressList");
	
	if(searchAddressList == null){
		searchAddressList = new ArrayList<YearendtaxAddressVO>();
	}
	String seq = StringUtil.nvl((String)request.getAttribute("seq"));
	String searchKeyword = StringUtil.nvl((String)request.getAttribute("searchKeyword"));
	String searchType = StringUtil.nvl((String)request.getAttribute("searchType"));
	
%>
<!DOCTYPE html>
<html lang="ko">
<head>
	<%@include file="/include/head.jsp"%>
	<script type="text/javascript">
	
		/* 
		 * 검색 
		 */
		function goSearch(){
			document.frm.action="<%=GROUP_ROOT%>/yt/yearendtax/searchAddress.do";
			document.frm.submit();
		}
		
		/* 
		 * 주소 선택
		 */
		function selectAddress(){
			
			var r = $('input:radio[name="check"]:checked').val();
			
			if(r != null){
				
				parent.document.getElementById("zip_cd_<%=seq%>").value=$("#zip_cd_"+r).text();
				parent.document.getElementById("address1_<%=seq%>").value=$("#address1_"+r).val();
				
				window.parent.layerClose();
				
			}else{
				alert("주소를 선택해 주세요.");
			}
			
		}
		
	</script>
</head>
<body>
<div class="individ_pop">
	<div class="popup_box">
		<h3>주소찾기</h3>
		<div class="pop_con_all pop_register pop_yearend rel">
			<div class="search_box03">
				<form name="frm" id="frm" method="post" >
					<div class="float_l">
						<select class="serch_select02" id="searchType" name="searchType">
							<option value="dong" <% if("dong".equals(searchType)) { %> selected <%} %>>읍면동(지번)</option>
							<option value="doro" <% if("doro".equals(searchType)) { %> selected <%} %>>도로명</option>
						</select>
						<input type="text" name="searchKeyword" id="searchKeyword" style="width:133px" onKeyPress="if(event.keyCode=='13'){goSearch(); return false;}" value="<%=searchKeyword%>" />
						<input type="hidden" name="seq" id="seq" value="<%=seq%>" />
					</div>
					<button type="button" class="search" onclick="javascript:goSearch();">검색</button>
				</form>
			</div>
			<div class="box_scroll">
				<table>
					<colgroup>
						<col style="width:22px"/>
						<col style="width:95px"/>
						<col style=""/>
					</colgroup>
					<thead>
						<tr>
							<th></th>
							<th>우편번호</th>
							<th class="bdrn">주소</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td colspan="3" class="inner">
								<div>
									<table>
										<colgroup>
											<col style="width:22px"/>
											<col style="width:95px"/>
											<col style=""/>												
										</colgroup>
										<tbody>
											<%
											if(searchAddressList.size()!=0){
												for(int i=0; searchAddressList.size()>i; i++){
													YearendtaxAddressVO yearendtaxAddressVO = new YearendtaxAddressVO();
													yearendtaxAddressVO=searchAddressList.get(i);
											%>
											<tr>
												<td><input type="radio" id="check" name="check" value="<%=i+1%>" /></td>
												<td id="zip_cd_<%=i+1%>"><%=yearendtaxAddressVO.getZip_cd().substring(0,3)%>-<%=yearendtaxAddressVO.getZip_cd().substring(3,6)%></td>
												<td class="bdrn"><%=yearendtaxAddressVO.getSido()%> <%=yearendtaxAddressVO.getGungu()%> <%=yearendtaxAddressVO.getLaw_dong_nm()%> <%=yearendtaxAddressVO.getBldg_no1()%>-<%=yearendtaxAddressVO.getBldg_no2()%></td>
												<td style="display: none;">
													<input type="hidden" id ="address1_<%=i+1%>" name="address1" value="<%=yearendtaxAddressVO.getSido()%> <%=yearendtaxAddressVO.getGungu()%> <%=yearendtaxAddressVO.getLaw_dong_nm()%> <%=yearendtaxAddressVO.getBldg_no1()%>-<%=yearendtaxAddressVO.getBldg_no2()%>" />
												</td>
											</tr>
											<%
														}
											}else{
											%>
												<tr><td class="bdrn" colspan="3">데이터가 없습니다.</td></tr>
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
			<button type="button" class="type_01" onclick="javascript:selectAddress();">확인</button>
			<button type="button" class="type_01" onclick="window.parent.layerClose();">닫기</button>
		</div>
		<button type="button" class="close" onclick="window.parent.layerClose();"><span class="blind">닫기</span></button>
	</div>
</div>
</body>
</html>