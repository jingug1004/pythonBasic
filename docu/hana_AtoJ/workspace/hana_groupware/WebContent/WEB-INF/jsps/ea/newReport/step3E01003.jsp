<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%--
 * @파일명 : step3E01003.jsp
 * @메뉴명 : 문서 상세조회 팝업-사내통신
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
<div class="inner_box no_scroll non_ipt">
	<strong class="tit_s tit_sample">내 역</strong>
	<table class="tbl_draft">
		<colgroup>
			<col class="cb_w85">
			<col style="">
			<col style="width:86px">
			<col style="">
		</colgroup>
		<tbody>
			<tr>
				<th>수신</th>
				<td colspan="3" class="bdrn"><%=RequestFilterUtil.toHtmlTagReplace("", incompanyVO.getReception()) %></td>
			</tr>
			<tr>
				<th>발신</th>
				<td><%=RequestFilterUtil.toHtmlTagReplace("", incompanyVO.getSend()) %></td>
				<th>참조</th>
				<td class="bdrn"><%=RequestFilterUtil.toHtmlTagReplace("", incompanyVO.getReference()) %></td>
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
							<th>목적</th>
							<td class="ta"><%=RequestFilterUtil.toHtmlTagReplace("", StringUtil.nvl(incompanyVO.getPurpose())) %></td>
						</tr>
						<tr>
							<th>내용</th>
							<td class="ta"><%=StringUtil.nl2br(RequestFilterUtil.toHtmlTagReplace("", incompanyVO.getContent())) %></td>
						</tr>
						<tr>
							<th>기타</th>
							<td class="ta"><%=StringUtil.nl2br(RequestFilterUtil.toHtmlTagReplace("", incompanyVO.getEtc())) %></td>
						</tr>
					</table>
				</td>
			</tr>
		</tbody>
	</table>
</div>