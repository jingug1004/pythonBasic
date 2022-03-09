<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%--
 * @파일명 : previewE01005.jsp
 * @메뉴명 : 결재문서 미리보기-근태계
 * @최초작성일 : 2015/01/28            
 * @author : 정진묵           
 * @수정내역 : 
--%>
<%@ page import="com.hanaph.gw.co.common.utils.StringUtil" %>
<%@ page import="com.hanaph.gw.co.common.utils.RequestFilterUtil" %>
<%@ page import="com.hanaph.gw.ea.newReport.vo.CommuteVO" %>
<%
	/* 근태계 */
	CommuteVO commuteVO = (CommuteVO)request.getAttribute("commuteVO");
	if(commuteVO == null){
		commuteVO = new CommuteVO();
	}
%>
<table class="tbl_report">
	<colgroup>
		<col style="width:99px">
		<col style="">
		<col style="width:99px">
		<col style="">
	</colgroup>
	<tbody>
		<tr>
			<th>내용</th>
			<td><%=RequestFilterUtil.toHtmlTagReplace("", commuteVO.getKind())%></td>
			<th>사전보고유무</th>
			<td class="bdr2"><%=RequestFilterUtil.toHtmlTagReplace("", commuteVO.getBogo_yn())%></td>
		</tr>
		<tr>
			<th>사전보고수령자</th>
			<td><%=RequestFilterUtil.toHtmlTagReplace("", commuteVO.getBogo_receiver())%></td>
			<th>근태계 날짜</th>
			<td class="bdr2"><%=StringUtil.nvl(commuteVO.getImposition_ymd())%></td>
		</tr>
		<tr>
			<th>미보고사유</th>
			<td colspan="3" class="bdr2"><%=RequestFilterUtil.toHtmlTagReplace("", commuteVO.getMibogo_reason())%></td>
		</tr>
		<tr>
			<th>사유</th>
			<td colspan="3" class="bdrn inner">
				<table>
					<colgroup>
						<col style="width:102px"/>
						<col style=""/>
					</colgroup>
					<tbody>
						<tr>
							<th class="bdtn">지각(출근시각)</th>
							<td class="bdtn"><%=StringUtil.nvl(commuteVO.getLate_tm())%></td>
						</tr>
						<tr>
							<th>조퇴(발생시각)</th>
							<td><%=StringUtil.nvl(commuteVO.getLeave_tm())%></td>										
						</tr>
						<tr>
							<th>결근(결근기간)</th>
							<td>
								<%=StringUtil.nvl(commuteVO.getStart_absence_ymd())%>
								~
								<%=StringUtil.nvl(commuteVO.getEnd_absence_ymd())%>
							</td>										
						</tr>
						<tr>
							<th>내용</th>
							<td class="ta">
								<div><%=StringUtil.nl2br(RequestFilterUtil.toHtmlTagReplace("", commuteVO.getReason()))%></div>
							</td>										
						</tr>
					</tbody>
				</table>
			</td>
		</tr>
		<tr>
			<th>비고</th>
			<td colspan="3" class="etc bdr2">
				-해당 사유 내용은 구체적으로 기입<br />
				-예정된 지각, 조퇴, 결근의 경우 해당 날짜 및 시간 명시<br />
				-지각, 조퇴, 결근은 선보고를 원칙으로 하며, 미보고시 별도의 미보고 사유를 기입
			</td>
		</tr>
	</tbody>
</table>