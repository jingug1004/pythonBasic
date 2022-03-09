<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%--
 * @파일명 : step3E01005.jsp
 * @메뉴명 : 문서 상세조회 팝업-근태계
 * @최초작성일 : 2015/01/28            
 * @author : 정진묵                 
 * @수정내역 : 
--%>
<%@ page import="com.hanaph.gw.co.common.utils.StringUtil" %>
<%@ page import="com.hanaph.gw.co.common.utils.RequestFilterUtil" %>
<%@ page import="com.hanaph.gw.ea.newReport.vo.CommuteVO" %>
<%@ page import="com.hanaph.gw.ea.newReport.vo.ApprovalMasterVO" %>
<%
	/* 근태계 */
	CommuteVO commuteVO = (CommuteVO)request.getAttribute("commuteVO");
	if(commuteVO == null){
		commuteVO = new CommuteVO();
	}
	
	CommuteVO commuteTardy = (CommuteVO)request.getAttribute("commuteTardy");
	if(commuteTardy == null){
		commuteTardy = new CommuteVO();
	}
	
	/* 결재마스터 */
	ApprovalMasterVO approvalMasterVO = (ApprovalMasterVO)request.getAttribute("approvalMasterVO");
	if(approvalMasterVO == null){
		approvalMasterVO = new ApprovalMasterVO();
	}	
	
	String state = StringUtil.nvl(approvalMasterVO.getState());
%>
<div class="inner_box no_scroll">
	<strong class="tit_s tit_sample">내 역</strong>
	<table class="tbl_report">
		<colgroup>
			<col style="width:86px"> <!-- 2015-02-13 수정 -->
			<col style="">
			<col style="width:86px">
			<col style="">
		</colgroup>
		<tbody>
			<tr>
				<th>내용</th>
				<td><%=RequestFilterUtil.toHtmlTagReplace("", commuteVO.getKind())%></td>
				<th>사전보고유무</th>
				<td class="bdrn"><%=RequestFilterUtil.toHtmlTagReplace("", commuteVO.getBogo_yn())%></td>
			</tr>
			<tr>
				<th>사전보고수령자</th>
				<td><%=RequestFilterUtil.toHtmlTagReplace("", commuteVO.getBogo_receiver())%></td>
				<th>근태계 날짜</th>
				<td class="bdrn date">
					<%=StringUtil.nvl(commuteVO.getImposition_ymd())%>
				</td>
			</tr>
			<tr>
				<th>미보고사유</th>
				<td colspan="3" class="bdrn"><%=RequestFilterUtil.toHtmlTagReplace("", commuteVO.getMibogo_reason())%></td>
			</tr>
			<tr>
				<th>사유</th>
				<td colspan="3" class="bdrn inner">
					<table>
						<colgroup>
						<%if(!"E02004".equals(state)){ %>
							<col style="width:65px"/>
							<col style="width:186px"/>
							<col style="width:50px"/>
						<%}else{ %>
							<col style="width:65px"/>
							<col style="width:186px"/>
						<%}%>
						</colgroup>
						<tbody>
							<tr>
								<th class="bdtn">지각(출근시각)</th>
								<td class="bdrn bdtn"><%=StringUtil.nvl(commuteVO.getLate_tm())%></td>
								<td class="bdrn bdtn"></td>
								<td></td>
								<td>지각  <%=commuteTardy.getTardy()%> 회</td>													
							</tr>
							<tr>									
								<th>조퇴(발생시각)</th>
								<td class="bdrn" colspan="4"><%=StringUtil.nvl(commuteVO.getLeave_tm())%></td>																
							</tr>
							<tr>								
								<th>결근(결근기간)</th>
								<td class="bdrn date" colspan="4">
									<%=StringUtil.nvl(commuteVO.getStart_absence_ymd())%>
									~
									<%=StringUtil.nvl(commuteVO.getEnd_absence_ymd())%>
								</td>																	
							</tr>
							<tr>								
								<th>내용</th>
								<td class="bdrn" colspan="4"><%=StringUtil.nl2br(RequestFilterUtil.toHtmlTagReplace("", commuteVO.getReason()))%></td>								
							</tr>
						</tbody>
					</table>
				</td>
			</tr>
			<tr>
				<th>비고</th>
				<td colspan="3" class="bdrn etc">
					-해당 사유 내용은 구체적으로 기입<br />
					-예정된 지각, 조퇴, 결근의 경우 해당 날짜 및 시간 명시<br />
					-지각, 조퇴, 결근은 선보고를 원칙으로 하며, 미보고시 별도의 미보고 사유를 기입
				</td>
			</tr>
		</tbody>
	</table>
</div>	