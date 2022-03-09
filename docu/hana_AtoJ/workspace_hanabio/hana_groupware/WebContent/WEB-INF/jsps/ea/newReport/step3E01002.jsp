<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%--
 * @파일명 : step3E01002.jsp
 * @메뉴명 : 문서 상세조회 팝업-기안서
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
<div class="inner_box no_scroll non_ipt">
	<strong class="tit_s tit_sample">내 역</strong>
	<table class="tbl_draft">
		<colgroup>
			<col class="cb_w85">
			<col style="">
			<col style="width:87px">
			<col style="">
		</colgroup>
		<tbody>
			<tr>
				<th>시행일자</th>
				<td colspan="3" class="date bdrn">
					<%=StringUtil.nvl(draftVO.getImposition_ymd()) %>
				</td>
			</tr>
			<tr>
				<th>통제</th>
				<td><%=RequestFilterUtil.toHtmlTagReplace("", draftVO.getControll()) %></td>
				<th>협조</th>
				<td class="bdrn"><%=RequestFilterUtil.toHtmlTagReplace("", draftVO.getCooperation()) %></td>
			</tr>
			<tr>
				<th>내용</th>
				<td colspan="3" class="inner bdrn">
					<table>
						<colgroup>
							<col style="width:134px"/>
							<col style=""/>
						</colgroup>
						<tr>
							<th>기안목적</th>
							<td class="ta"><%=StringUtil.nl2br(RequestFilterUtil.toHtmlTagReplace("", draftVO.getPurpose()))%></td>
						</tr>
						<tr>
							<th>기안내용</th>
							<td class="ta"><%=StringUtil.nl2br(RequestFilterUtil.toHtmlTagReplace("", draftVO.getContent()))%></td>
						</tr>
						<tr>
							<th>기타</th>
							<td class="ta"><%=StringUtil.nl2br(RequestFilterUtil.toHtmlTagReplace("", draftVO.getEtc()))%></td>
						</tr>
					</table>
				</td>
			</tr>
		</tbody>
	</table>
</div>	