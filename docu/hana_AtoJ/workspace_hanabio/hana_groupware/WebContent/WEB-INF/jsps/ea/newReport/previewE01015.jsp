<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%--
 * @파일명 : previewE01015.jsp
 * @메뉴명 : 결재문서 미리보기-물품 구입 청구서
 * @최초작성일 : 2015/12/11            
 * @author : 전산팀          
 * @수정내역 : 
--%>
<%@ page import="java.util.List" %>
<%@ page import="com.hanaph.gw.co.common.utils.StringUtil" %>
<%@ page import="com.hanaph.gw.co.common.utils.RequestFilterUtil" %>
<%@ page import="com.hanaph.gw.ea.newReport.vo.RequisitionVO" %>
<%
	/* 물품 구입 청구확인서 */
	List<RequisitionVO> requisitionDetailList = (List<RequisitionVO>)request.getAttribute("requisitionDetail");
%>
<h5>
	<span class="w_date1">청구일 : <%=StringUtil.nvl(requisitionDetailList.get(0).getReq_ymd()) %></span>
	<span class="w_date2">청구번호 : <%=StringUtil.nvl(requisitionDetailList.get(0).getReq_no()) %></span>	
</h5>
<div class="confirm">
	<table class="over">
		<colgroup>			
			<col style="width:17%;">
			<col style="width:11%;">
			<col style="width:9%;">
			<col style="width:10%;">
			<col style="width:10%;">
			<col style="width:7%;">
			<col style="width:9%;">
			<col style="width:8%;">
			<col style="width:8%;">
			<col style="width:12%;">	
			<col style="width:10%;">
			<col style="width:10%;">				
		</colgroup>
		<thead>
			<tr>			  
				<th>원부자재명</th>			 
			  	<th>규격</th>	
			  	<th>사용목적</th>		 
			  	<th>납기요구일</th>		  	  										
				<th style="border-bottom: none;">거래처</th>
				<th>현재고</th>				
				<th>청구수량</th>				
				<th>단위</th>
				<th>예정단가</th>			
				<th>비고</hr>	
				<th>납품일자</th>			
				<th>납품확인자</hr>			  
			</tr>
		</thead>	
		<tbody>
			<% if(requisitionDetailList.size() > 0){
				for(int i=0; requisitionDetailList.size()>i;i++){
					RequisitionVO reqVO = new RequisitionVO();
					reqVO = requisitionDetailList.get(i);
			%>
			<tr class="in_table">
				<td class="br_none"><%=StringUtil.nvl(reqVO.getMaterial_nm()) %></td>
				<td class="br_none"><%=StringUtil.nvl(reqVO.getStandard()) %></td>						
				<td class="br_none"><%=StringUtil.nvl(reqVO.getUsage()) %></td>
				<td class="br_none"><%=StringUtil.nvl(reqVO.getIpgo_ymd()) %></td>
				<td class="br_none"><%=StringUtil.nvl(reqVO.getCust_nm()) %></td>
				<td class="txtright"><%=StringUtil.nvl(reqVO.getHyunjaego_qty()) %></td>
				<td class="txtright"><%=StringUtil.nvl(reqVO.getQty()) %></td>
				<td class="br_none"><%=StringUtil.nvl(reqVO.getUnit()) %></td>
				<td class="txtright"><%=StringUtil.nvl(reqVO.getDanga()) %></td>
				<td class="br_none"><%=StringUtil.nvl(reqVO.getBigo()) %></td>
				<td class="br_none"><%=StringUtil.nvl(reqVO.getTax_ymd()) %></td>
				<td class="br_none"><%=StringUtil.nvl(reqVO.getSawon_id()) %></td>
			</tr>
			<%
				}
			}
			%>
		<tr class="in_table">
			<th class="append_tit text_l">첨부파일</th>
			<td colspan="12" class="append_tit bdr2 text_l">aaa.jpg</td>
		</tr>
		</tbody>
	</table>							
</div>