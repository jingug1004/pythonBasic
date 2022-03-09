<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%--
 * @파일명 : previewE01008.jsp
 * @메뉴명 : 결재문서 미리보기 -구매신청서
 * @최초작성일 : 2015/01/28            
 * @author : CHOIILJI                 
 * @수정내역 : 
--%>
<%@ page import="java.util.List" %>
<%@ page import="com.hanaph.gw.co.common.utils.StringUtil" %>
<%@ page import="com.hanaph.gw.co.common.utils.RequestFilterUtil" %>
<%@ page import="com.hanaph.gw.ea.newReport.vo.PurchaseVO" %>
<%	//구매신청서 
	List<PurchaseVO> purchaseDetailList = (List<PurchaseVO>)request.getAttribute("purchaseDetailList");
%>	
			<table class="tbl_sample">
				<colgroup>
					<col style="width:107px">
					<col style="width:72px">
					<col style="width:54px">
					<col style="width:102px">
					<col style="">
					<col style="width:152px">
				</colgroup>
				<thead>
					<th>품명</th>
					<th>규격</th>
					<th>수량</th>
					<th>납품요구일</th>
					<th>사무 및 목적</th>
					<th class="bdr2">비고</th>
				</thead>
				<tbody>
				<%
				if(purchaseDetailList.size() > 0){
					for(int i=0; purchaseDetailList.size()>i;i++){
						PurchaseVO purchaseVO = new PurchaseVO();
						purchaseVO = purchaseDetailList.get(i);
				%>
										
					<tr>
						<td><%=RequestFilterUtil.toHtmlTagReplace("", purchaseVO.getProduct_nm())%></td>
						<td><%=RequestFilterUtil.toHtmlTagReplace("", purchaseVO.getStandard())%></td>
						<td><%=StringUtil.makeMoneyType(Integer.parseInt(purchaseVO.getQty()))%></td>
						<td><%=StringUtil.nvl(purchaseVO.getDeliver_req_ymd())%></td>
						<td><%=RequestFilterUtil.toHtmlTagReplace("", purchaseVO.getPurpose())%></td>
						<td class="bdr2"><%=RequestFilterUtil.toHtmlTagReplace("", purchaseVO.getBigo())%></td>
					</tr>
				<%		
					}
				}	
				%>					
				</tbody>
			</table>