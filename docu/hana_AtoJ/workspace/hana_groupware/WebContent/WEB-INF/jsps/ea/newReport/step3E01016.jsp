<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%--
 * @파일명 : previewE01016.jsp
 * @메뉴명 : 결재문서 미리보기-원부자재 납품확인서
 * @최초작성일 : 2016/01/26            
 * @author : 전산팀          
 * @수정내역 : 
--%>
<%@ page import="java.util.List" %>
<%@ page import="com.hanaph.gw.co.common.utils.StringUtil" %>
<%@ page import="com.hanaph.gw.co.common.utils.RequestFilterUtil" %>
<%@ page import="com.hanaph.gw.ea.newReport.vo.DeliveryVO" %>
<%
	/*원부자재 납품확인서 */
	List<DeliveryVO> deliveryDetailList = (List<DeliveryVO>)request.getAttribute("deliveryDetail");

	Integer listSize = 0; 	
	listSize = deliveryDetailList.size();
%>
<h5>
	<span class="w_date1">입고일 : <%=StringUtil.nvl(deliveryDetailList.get(0).getYmd()) %></span>
	<span class="w_date2">전표번호 : <%=StringUtil.nvl(deliveryDetailList.get(0).getSlip_no()) %></span>
	<span class="w_date3">거래처 : <%=StringUtil.nvl(deliveryDetailList.get(0).getCust_nm()) %></span>	
</h5>
<div class="confirm">
	<table class="over">
		<colgroup>
			<col style="width:9%;">			
			<col style="width:17%;">			
			<col style="width:7%;">
			<col style="width:8%;">
			<col style="width:7%;">
			<col style="width:7%;">
			<col style="width:9%;">
			<col style="width:9%;">
			<col style="width:9%;">
			<col style="width:9%;">
			<col style="width:9%;">						
		</colgroup>
		<thead>
			<tr>			  
				<th style="border-bottom: none;">원부자재코드</th>	
				<th>원부자재명</th>			 
			  	<th>규격</th>	
			  	<th>입고수량</th>		 
			  	<th>단위</th>		  	  										
				<th>단가</th>
				<th>금액</th>		
				<th>공급가액</th>
				<th>부가세</th>
				<th>합계</th>
				<th>비고</th>							  
			</tr>
		</thead>	
		<tbody>
			<% if(deliveryDetailList.size() > 0){
				for(int i=0; deliveryDetailList.size()>i;i++){
					DeliveryVO deliVO = new DeliveryVO();
					deliVO = deliveryDetailList.get(i);					
			%>
			<tr class="in_table">
				<td class="br_none"><%=StringUtil.nvl(deliVO.getMaterial_id()) %></td>				
				<td class="br_none"><%=StringUtil.nvl(deliVO.getMaterial_nm()) %></td>				
				<td class="br_none"><%=StringUtil.nvl(deliVO.getStandard()) %></td>									
				<td class="txtright"><%=StringUtil.nvl(deliVO.getQty()) %></td>
				<td class="br_none"><%=StringUtil.nvl(deliVO.getUnit()) %></td>
				<td class="txtright"><%=StringUtil.nvl(deliVO.getDanga()) %></td>
				<td class="txtright"><%=StringUtil.nvl(deliVO.getAmt()) %></td>
				<%if (i == 0){ %>
				<td rowspan=<%=listSize%> class="txtright"><%=StringUtil.nvl(deliVO.getAmt_sum()) %></td>
				<td rowspan=<%=listSize%> class="txtright"><%=StringUtil.nvl(deliVO.getVat_sum()) %></td>
				<td rowspan=<%=listSize%> class="txtright"><%=StringUtil.nvl(deliVO.getTot_sum()) %></td>
				<%} %>
				<td class="br_none"><%=StringUtil.nvl(deliVO.getBigo()) %></td>
			</tr>			
			<%	}
			}
			%>
		</tbody>
	</table>							
</div>
