<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%--
 * @파일명 : previewE01009.jsp
 * @메뉴명 : 결재문서 미리보기  - 시간외근무신청서
 * @최초작성일 : 2015/01/28            
 * @author : CHOIILJI                 
 * @수정내역 : 
--%>
<%@ page import="java.util.List" %>
<%@ page import="com.hanaph.gw.co.common.utils.StringUtil" %>
<%@ page import="com.hanaph.gw.co.common.utils.RequestFilterUtil" %>
<%@ page import="com.hanaph.gw.ea.newReport.vo.OvertimeVO" %>
<%
	//시간외근무신청서
	List<OvertimeVO> overtimeDetailList = (List<OvertimeVO>)request.getAttribute("overtimeDetailList");
%>
			<table class="tbl_sample">
				<colgroup>
					<col style="width:60px">
					<col style="width:60px">
					<col style="width:78px">
					<col style="width:194px">
					<col style="width:84px">
					<col style="width:94px">
					<col style="width:94px">
					<col style="width:94px">
				</colgroup>
				<thead>
					<th>부서명</th>
					<th>성명</th>
					<th>근무구분</th>
					<th>업무내용</th>
					<th>근무예정일</th>
					<th>근무시작시간</th>
					<th>근무종료시간</th>
					<th class="bdr2">근무예정시간</th>
				</thead>
				<tbody>
				<%
				if(overtimeDetailList.size() > 0){
					for(int i=0; overtimeDetailList.size()>i;i++){
						OvertimeVO overtimeVO = new OvertimeVO();
						overtimeVO = overtimeDetailList.get(i);
				%>
					<tr>
						<td><%=StringUtil.nvl(overtimeVO.getDept_nm()) %></td>
						<td><%=StringUtil.nvl(overtimeVO.getEmp_nm()) %></td>
						<td><%=StringUtil.nvl(overtimeVO.getBigo_nm()) %></td>
						<td><%=RequestFilterUtil.toHtmlTagReplace("", overtimeVO.getBiz_content()) %></td>
						<td><%=StringUtil.nvl(overtimeVO.getWork_due_ymd()) %></td>
						<td><%=String.format("%02d", Integer.parseInt(overtimeVO.getWork_start_hh())) %> : <%=String.format("%02d", Integer.parseInt(overtimeVO.getWork_start_mi())) %></td>
						<td><%=String.format("%02d", Integer.parseInt(overtimeVO.getWork_end_hh())) %> : <%=String.format("%02d", Integer.parseInt(overtimeVO.getWork_end_mi())) %></td>
						<td class="bdr2"><%=String.format("%02d", Integer.parseInt(overtimeVO.getWork_due_hh())) %> : <%=String.format("%02d", Integer.parseInt(overtimeVO.getWork_due_mi())) %></td>
					</tr>	
				<%		
					}
				}	
				%>	
				</tbody>
			</table>	