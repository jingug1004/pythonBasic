<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%--
 * @파일명 : previewE01002.jsp
 * @메뉴명 : 결재문서 미리보기-기안서
 * @최초작성일 : 2015/01/28            
 * @author : 정진묵           
 * @수정내역 : 
--%>
<%@ page import="com.hanaph.gw.co.common.utils.StringUtil" %>
<%@ page import="com.hanaph.gw.co.common.utils.RequestFilterUtil" %>
<%@ page import="com.hanaph.gw.ea.newReport.vo.DraftVO" %>
<%
	/* 기안서 */
	DraftVO draftVO = (DraftVO)request.getAttribute("draftVO");
	if(draftVO == null){
		draftVO = new DraftVO();
	}
%>
<table class="tbl_draft">
	<colgroup>
		<col style="width:99px">
		<col style="">
		<col style="width:99px">
		<col style="">
	</colgroup>
	<tbody>
		<tr>
			<th>시행일자</th>
			<td colspan="3" class="bdr2"><%=StringUtil.nvl(draftVO.getImposition_ymd()) %></td>
		</tr>							
		<tr>
			<th>통제</th>
			<td><%=RequestFilterUtil.toHtmlTagReplace("", draftVO.getControll()) %></td>
			<th>협조</th>
			<td class="bdr2"><%=RequestFilterUtil.toHtmlTagReplace("", draftVO.getCooperation()) %></td>
		</tr>				
		<tr>
			<td colspan="4" class="bdr2 ta">
				<div>
					<ul>
						<li>
							<span>[목적]</span>
							<!-- 각 항목 별 내용은 다음 div에 출력-->
							<div><%=StringUtil.nl2br(RequestFilterUtil.toHtmlTagReplace("", draftVO.getPurpose()))%></div>
						</li>
						<li>
							<span>[내용]</span>
							<!-- 각 항목 별 내용은 다음 div에 출력-->
							<div><%=StringUtil.nl2br(RequestFilterUtil.toHtmlTagReplace("", draftVO.getContent()))%></div>
						</li>
						<li>
							<span>[기타]</span>
							<!-- 각 항목 별 내용은 다음 div에 출력-->
							<div><%=StringUtil.nl2br(RequestFilterUtil.toHtmlTagReplace("", draftVO.getEtc()))%></div>
						</li>
					</ul>
				</div>
			</td>
		</tr>	
	</tbody>
</table>