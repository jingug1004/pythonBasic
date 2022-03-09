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
<div class="inner_box no_scroll non_ipt">
	<strong class="tit_s tit_sample">내 역</strong>
	<table class="tbl_draft">
		<colgroup>
			<col style=""cb_w87">
			<col style="width:98px">
			<col style="width:98px">
			<col style="">
			<col style="width:70px">
			<col style="width:70px">
			<col style="width:98px">
		</colgroup>		
		<tbody>
			<% if(requisitionDetailList.size() > 0){
				for(int i=0; requisitionDetailList.size()>i;i++){
					RequisitionVO reqVO = new RequisitionVO();
					reqVO = requisitionDetailList.get(i);
					if (i==0){						
			%>
			<tr>
				<th>청구일</th>
				<td colspan="6">							
					<%=StringUtil.nvl(reqVO.getReq_ymd()) %>					
				</td>
			</tr>
			<tr>
				<th>청구번호</th>
				<td colspan="6">
					<%=StringUtil.nvl(reqVO.getReq_no()) %>	
				</td>
			</tr>			
			<tr>
				<th colspan="4" >원부자재명</th>				
				<th colspan="3" >규격</th>				
			</tr>
			<tr>
				<th colspan="2">사용목적</th>
				<th>납기요구일</th>
				<th>거래처</th>
				<th>수량</th>
				<th>단위</th>
				<th>측정단가</th>
			</tr>
			<% 		} %>
			<tr>
				<td colspan="4">					
					<%=StringUtil.nvl(reqVO.getMaterial_nm()) %>
				</td>
				<td colspan="3">	
					<%=StringUtil.nvl(reqVO.getStandard()) %>
				</td>
			</tr>
			<tr>
				<td colspan="2">
					<%=StringUtil.nvl(reqVO.getUsage()) %>
				</td>
				<td>
					<%=StringUtil.nvl(reqVO.getIpgo_ymd()) %>
				</td>
				<td>	
					<%=StringUtil.nvl(reqVO.getCust_nm()) %>
				</td>
				<td>
					<%=StringUtil.makeMoneyType(reqVO.getQty()) %>
				</td>	
				<td>
					<%=StringUtil.nvl(reqVO.getUnit()) %>					
				</td>							
				<td>
					<%=StringUtil.makeMoneyType(reqVO.getDanga()) %>
				</td>					
			</tr>
			<%	}
			}	
			%>
		</tbody>
	</table>