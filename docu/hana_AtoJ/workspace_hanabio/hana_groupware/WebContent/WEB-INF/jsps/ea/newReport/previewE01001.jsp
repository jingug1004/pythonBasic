<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%--
 * @파일명 : previewE01001.jsp
 * @메뉴명 : 결재문서 미리보기-휴가신청서
 * @최초작성일 : 2015/01/28            
 * @author : 정진묵           
 * @수정내역 : 
--%>
<%@ page import="com.hanaph.gw.pe.member.vo.AnnualMgrVO" %>
<%@ page import="com.hanaph.gw.ea.newReport.vo.VacationVO" %>
<%@ page import="com.hanaph.gw.co.common.utils.StringUtil" %>
<%@ page import="com.hanaph.gw.co.common.utils.RequestFilterUtil" %>
<%

	/*휴가 신청서*/
	VacationVO vacationVO = (VacationVO)request.getAttribute("vacationVO");
	String emp_ko_nm = StringUtil.nvl((String)request.getAttribute("emp_ko_nm"));
	AnnualMgrVO annualPlan = (AnnualMgrVO)request.getAttribute("annualPlan");

	if(vacationVO == null){
		vacationVO = new VacationVO();
	}

	float yr_year_used_day = 0;
	if(Float.parseFloat(annualPlan.getYr_year_used_day()) < 0){
		yr_year_used_day = Float.parseFloat(annualPlan.getYr_year_used_day());
	}
	
	float reserved_day = annualPlan.getUseable_days() - annualPlan.getUsed_days() + yr_year_used_day;
	
%>
<table class="tbl_content">
	<colgroup>
		<col style="width:99px">
		<col style="">
	</colgroup>
	<tbody>
		<tr>
			<th>휴가종류</th>
			<td class="bdr2"><%=RequestFilterUtil.toHtmlTagReplace("", vacationVO.getVacation_kind())%></td>
		</tr>	
		<tr>
			<th>휴가사유</th>
			<td class="ta bdr2"><div><%=StringUtil.nl2br(RequestFilterUtil.toHtmlTagReplace("", vacationVO.getReason()))%></div></td>
		</tr>	
		<tr>
			<th>휴가기간</th>
			<td class="bdr2"><%=StringUtil.nvl(vacationVO.getStart_ymd())%> ~ <%=StringUtil.nvl(vacationVO.getEnd_ymd())%></td>
		</tr>	
		<tr>
			<th>비상연락처</th>
			<td class="bdr2"><%=StringUtil.nvl(vacationVO.getContact_number())%></td>
		</tr>	
						
	</tbody>
</table>