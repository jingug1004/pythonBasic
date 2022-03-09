<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%--
 * @파일명 : step3E01001.jsp
 * @메뉴명 : 문서 상세조회 팝업-휴가신청서
 * @최초작성일 : 2015/01/28            
 * @author : 정진묵                 
 * @수정내역 : 
--%>
<%@ page import="com.hanaph.gw.pe.member.vo.AnnualMgrVO" %>
<%@ page import="com.hanaph.gw.ea.newReport.vo.VacationVO" %>
<%@ page import="com.hanaph.gw.co.common.utils.StringUtil" %>
<%@ page import="com.hanaph.gw.co.common.utils.RequestFilterUtil" %>
<%@ page import="com.hanaph.gw.ea.newReport.vo.ApprovalMasterVO" %>
<%

	/*휴가 신청서*/
	VacationVO vacationVO = (VacationVO)request.getAttribute("vacationVO");
	String emp_ko_nm = StringUtil.nvl((String)request.getAttribute("emp_ko_nm"));
	AnnualMgrVO annualPlan = (AnnualMgrVO)request.getAttribute("annualPlan");
	
	int annualcommonDtCnt = 0; //공동연차사용일수
	annualcommonDtCnt = ((Integer)request.getAttribute("annualcommonDtCnt")).intValue();
	
	/* 결재마스터 */
	ApprovalMasterVO approvalMasterVO = (ApprovalMasterVO)request.getAttribute("approvalMasterVO");
	if(approvalMasterVO == null){
		approvalMasterVO = new ApprovalMasterVO();
	}
	
	String state = StringUtil.nvl(approvalMasterVO.getState());
	
	if(vacationVO == null){
		vacationVO = new VacationVO();
	}

	float yr_year_used_day = 0;
	if(Float.parseFloat(annualPlan.getYr_year_used_day()) < 0){
		yr_year_used_day = Float.parseFloat(annualPlan.getYr_year_used_day());
	}
	
	float reserved_day = annualPlan.getUseable_days() - annualPlan.getUsed_days() + yr_year_used_day;
%>
<div class="inner_box no_scroll non_ipt">
	<strong class="tit_s">내역</strong>
	<table>
		<colgroup>
		<%if(!"E02004".equals(state)){ %>
			<col class="cb_w85">
			<col style="width:229px">
			<col style="width:86px">
			<col style="width:90px">
			<col style="width:100px">
			<col style="">			
		<%}else{ %>
			<col class="cb_w85">
			<col style="">
		<%}%>
		</colgroup>
		<tr>
			<th>휴가종류</th>
			<td <%if("E02004".equals(state)){%>colspan="5" class="holliday bdrn"<%}else{%>class="holliday"<%}%>><%=StringUtil.nvl(vacationVO.getVacation_kind())%></td>
		<%if(!"E02004".equals(state)){ %>
			<th>남은 연차일</th>
			<td class="annual"><%=reserved_day %></td>
			<th>연차사용가능일</th>
			<td class="bdrn annual"><%=annualPlan.getUseable_days() %></td>
		<%}%>
		</tr>
		<tr>
			<th>휴가기간</th>
			<td <%if("E02004".equals(state)){%>colspan="5" class="bdrn"<%}%>>
				<%=StringUtil.nvl(vacationVO.getStart_ymd())%> ~ <%=StringUtil.nvl(vacationVO.getEnd_ymd())%>
			</td>
		<%if(!"E02004".equals(state)){ %>
			<th>올해 사용일</th>
			<td class="annual"><%=annualPlan.getUsed_days() %>(<%=annualcommonDtCnt%>)</td>
			<th>지난해 사용일</th>
			<td class="bdrn annual"><%=yr_year_used_day %></td>
		<%}%>
		</tr>
		<tr>
			<th>휴가사유</th>
			<td colspan="5" class="bdrn cause"><%=StringUtil.nl2br(RequestFilterUtil.toHtmlTagReplace("", vacationVO.getReason()))%></td>
		</tr>
		<tr>
			<th>비상연락처</th>
			<td colspan="5" class="bdrn"><%=StringUtil.nvl(vacationVO.getContact_number())%></td>
		</tr>
	</table>
</div>	
