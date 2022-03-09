<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%--
 * @파일명 : previewE01006.jsp
 * @메뉴명 : 결재문서 미리보기-참가신청서
 * @최초작성일 : 2015/01/28            
 * @author : 정진묵           
 * @수정내역 : 
--%>
<%@ page import="com.hanaph.gw.co.common.utils.StringUtil" %>
<%@ page import="com.hanaph.gw.co.common.utils.RequestFilterUtil" %>
<%@ page import="com.hanaph.gw.ea.newReport.vo.ParticipationVO" %>
<%
	/* 참가 신청서 */
	ParticipationVO participationVO = (ParticipationVO)request.getAttribute("participationVO");
	if(participationVO == null){
		participationVO = new ParticipationVO();
	}
%>
<table class="tbl_participate">
	<colgroup>
		<col style="width:99px"/>
		<col style=""/>
	</colgroup>
	<tbody>
		<tr>
			<th>종류</th>
			<td class="bdr2"><%=RequestFilterUtil.toHtmlTagReplace("", participationVO.getKind()) %></td>
		</tr>
		<tr>
			<th>사무 및 내용</th>
			<td class="bdr2 ta">
				<div><%=StringUtil.nl2br(RequestFilterUtil.toHtmlTagReplace("", participationVO.getContent())) %></div>
			</td>
		</tr>
		<tr>
			<th>기간</th>
			<td class="bdr2">
				<%=StringUtil.nvl(participationVO.getStart_ymd()) %>
				~ 
				<%=StringUtil.nvl(participationVO.getEnd_ymd()) %>
			</td>
		</tr>
		<tr>
			<th>비상연락처</th>
			<td class="bdr2"><%=StringUtil.nvl(participationVO.getContact_number()) %></td>
		</tr>
	</tbody>
</table>