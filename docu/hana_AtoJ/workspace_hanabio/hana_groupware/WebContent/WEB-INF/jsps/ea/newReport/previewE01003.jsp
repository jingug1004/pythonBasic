<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%--
 * @파일명 : previewE01003.jsp
 * @메뉴명 : 결재문서 미리보기-사내통신
 * @최초작성일 : 2015/01/28            
 * @author : 정진묵           
 * @수정내역 : 
--%>
<%@ page import="com.hanaph.gw.co.common.utils.StringUtil" %>
<%@ page import="com.hanaph.gw.co.common.utils.RequestFilterUtil" %>
<%@ page import="com.hanaph.gw.ea.newReport.vo.IncompanyVO" %>
<%
	/* 사내통신 */
	IncompanyVO incompanyVO = (IncompanyVO)request.getAttribute("incompanyVO");
	if(incompanyVO == null){
		incompanyVO = new IncompanyVO();
	}
%>
<table class="tbl_communication">
	<colgroup>
		<col style="width:99px">
		<col style="">
		<col style="width:99px">
		<col style="">
	</colgroup>
	<tbody>
		<tr>
			<th>수신</th>
			<td colspan="3" class="bdr2"><%=RequestFilterUtil.toHtmlTagReplace("", incompanyVO.getReception()) %></td>
		</tr>				
		<tr>
			<th>발신</th>
			<td><%=RequestFilterUtil.toHtmlTagReplace("", incompanyVO.getSend()) %></td>
			<th>참조</th>
			<td class="bdr2"><%=RequestFilterUtil.toHtmlTagReplace("", incompanyVO.getReference()) %></td>
		</tr>						
			<td colspan="4" class="bdr2 ta">
				<div>
					<ul>
						<li>
							<span>[목적]</span>
							<!-- 각 항목 별 내용은 다음 div에 출력-->
							<div><%=StringUtil.nl2br(RequestFilterUtil.toHtmlTagReplace("", incompanyVO.getPurpose())) %></div>
						</li>
						<li>
							<span>[내용]</span>
							<!-- 각 항목 별 내용은 다음 div에 출력-->
							<div><%=StringUtil.nl2br(RequestFilterUtil.toHtmlTagReplace("", incompanyVO.getContent())) %></div>
						</li>
						<li>
							<span>[기타]</span>
							<!-- 각 항목 별 내용은 다음 div에 출력-->
							<div><%=StringUtil.nl2br(RequestFilterUtil.toHtmlTagReplace("", incompanyVO.getEtc())) %></div>
						</li>
					</ul>
				</div>
			</td>
	</tbody>
</table>