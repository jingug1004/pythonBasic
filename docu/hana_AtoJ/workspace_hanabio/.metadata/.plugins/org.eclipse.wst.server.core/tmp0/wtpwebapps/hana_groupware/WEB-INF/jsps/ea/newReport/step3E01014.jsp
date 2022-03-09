<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%--
 * @파일명 : step3E010014.jsp
 * @메뉴명 : 문서 상세조회 팝업-개발검토서
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
				<th>검토요청일</th>
				<td colspan="3" class="date bdrn">
					<%=StringUtil.nvl(reviewVO.getImposition_ymd()) %>
				</td>
			</tr>
			<tr>
				<th>통제</th>
				<td><%=RequestFilterUtil.toHtmlTagReplace("", reviewVO.getControll()) %></td>
				<th>협조</th>
				<td class="bdrn"><%=RequestFilterUtil.toHtmlTagReplace("", reviewVO.getCooperation()) %></td>
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
							<th>검토목적</th>
							<td class="ta"><%=StringUtil.nl2br(RequestFilterUtil.toHtmlTagReplace("", reviewVO.getPurpose()))%></td>
						</tr>
						<tr>
							<th>검토내용</th>
							<td class="ta"><%=StringUtil.nl2br(RequestFilterUtil.toHtmlTagReplace("", reviewVO.getContent()))%></td>
						</tr>
						<tr>
							<th>기타</th>
							<td class="ta"><%=StringUtil.nl2br(RequestFilterUtil.toHtmlTagReplace("", reviewVO.getEtc()))%></td>
						</tr>
					</table>
				</td>
			</tr>
		</tbody>
	</table>
</div>	