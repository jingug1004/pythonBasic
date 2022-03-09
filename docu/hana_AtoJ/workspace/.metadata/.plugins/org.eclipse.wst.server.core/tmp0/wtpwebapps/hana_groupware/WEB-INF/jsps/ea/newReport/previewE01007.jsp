<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%--
 * @파일명 : previewE01007.jsp
 * @메뉴명 : 결재문서 미리보기 -sample신청서
 * @최초작성일 : 2015/01/28            
 * @author : CHOIILJI                 
 * @수정내역 : 
--%>
<%@ page import="java.util.List" %>
<%@ page import="com.hanaph.gw.co.common.utils.StringUtil" %>
<%@ page import="com.hanaph.gw.co.common.utils.RequestFilterUtil" %>
<%@ page import="com.hanaph.gw.ea.newReport.vo.SampleVO" %>
<%
	//샘플신청서
	List<SampleVO> sampleDetailList = (List<SampleVO>)request.getAttribute("sampleDetailList");
%>
			<table class="tbl_sample">
				<colgroup>
					<col style="width:50px">
					<col style="">
					<col style="">
					<col style="">
					<col style="">
					<col style="">
					<col style="">
					<col style="width:188px">
					<col style="width:88px">
				</colgroup>
				<thead>
					<th>담당자</th>
					<th>거래처</th>
					<th>DR.명</th>
					<th>품명</th>
					<th>포장단위</th>
					<th>수량</th>
					<th>용도</th>
					<th>배송지</th>
					<th class="bdr2">전화번호</th>
				</thead>
				<tbody>
				<%
				if(sampleDetailList.size() > 0){
					for(int i=0; sampleDetailList.size()>i;i++){
						SampleVO sampleVO = new SampleVO();
						sampleVO = sampleDetailList.get(i);
				%>
					<tr>
						<td><%=RequestFilterUtil.toHtmlTagReplace("", sampleVO.getResponsible()) %></td>
						<td><%=RequestFilterUtil.toHtmlTagReplace("", sampleVO.getCust_nm()) %></td>
						<td><%=RequestFilterUtil.toHtmlTagReplace("", sampleVO.getDr_nm()) %></td>
						<td><%=RequestFilterUtil.toHtmlTagReplace("", sampleVO.getProduct_nm()) %></td>
						<td><%=StringUtil.nvl(sampleVO.getPacking_unit()) %></td>
						<td><%=StringUtil.makeMoneyType(Integer.parseInt(sampleVO.getQty())) %></td>
						<td><%=RequestFilterUtil.toHtmlTagReplace("", sampleVO.getYongdo()) %></td>
						<td><%=RequestFilterUtil.toHtmlTagReplace("", sampleVO.getAddress()) %></td>
						<td class="bdr2"><%=StringUtil.nvl(sampleVO.getCall_number()) %></td>
					</tr>	
				<%	} 
				}%>				
				</tbody>
			</table>