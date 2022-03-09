<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%--
 * @파일명 : previewE01014.jsp
 * @메뉴명 : 결재문서 미리보기-개발검토서
 * @최초작성일 : 2015/12/10            
 * @author : 전산팀          
 * @수정내역 : 
--%>
<%@ page import="com.hanaph.gw.co.common.utils.StringUtil" %>
<%@ page import="com.hanaph.gw.co.common.utils.RequestFilterUtil" %>
<%@ page import="com.hanaph.gw.ea.newReport.vo.ReviewVO" %>
<%
	/* 개발검토서 */
	ReviewVO reviewVO = (ReviewVO)request.getAttribute("reviewVO");
	if(reviewVO == null){
		reviewVO = new ReviewVO();
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
			<th>검토요청일</th>
			<td colspan="3" class="bdr2"><%=StringUtil.nvl(reviewVO.getImposition_ymd()) %></td>
		</tr>							
		<tr>
			<th>통제</th>
			<td><%=RequestFilterUtil.toHtmlTagReplace("", reviewVO.getControll()) %></td>
			<th>협조</th>
			<td class="bdr2"><%=RequestFilterUtil.toHtmlTagReplace("", reviewVO.getCooperation()) %></td>
		</tr>				
		<tr>
			<td colspan="4" class="bdr2 ta">
				<div>
					<ul>
						<li>
							<span>[목적]</span>
							<!-- 각 항목 별 내용은 다음 div에 출력-->
							<div><%=StringUtil.nl2br(RequestFilterUtil.toHtmlTagReplace("", reviewVO.getPurpose()))%></div>
						</li>
						<li>
							<span>[내용]</span>
							<!-- 각 항목 별 내용은 다음 div에 출력-->
							<div><%=StringUtil.nl2br(RequestFilterUtil.toHtmlTagReplace("", reviewVO.getContent()))%></div>
						</li>
						<li>
							<span>[기타]</span>
							<!-- 각 항목 별 내용은 다음 div에 출력-->
							<div><%=StringUtil.nl2br(RequestFilterUtil.toHtmlTagReplace("", reviewVO.getEtc()))%></div>
						</li>
					</ul>
				</div>
			</td>
		</tr>	
	</tbody>
</table>