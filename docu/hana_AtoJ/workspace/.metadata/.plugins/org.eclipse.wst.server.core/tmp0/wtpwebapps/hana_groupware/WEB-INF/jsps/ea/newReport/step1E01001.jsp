<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%--
 * @파일명 : step1E01001.jsp
 * @메뉴명 : step1신규문서작성-휴가신청서
 * @최초작성일 : 2015/01/28            
 * @author : 정진묵                 
 * @수정내역 : 
--%>
<%@ page import="com.hanaph.gw.pe.member.vo.AnnualMgrVO" %>
<%@ page import="com.hanaph.gw.ea.newReport.vo.VacationVO" %>
<%@ page import="com.hanaph.gw.co.code.vo.CodeVO" %> 
<%@ page import="com.hanaph.gw.co.common.utils.StringUtil" %>
<%@ page import="com.hanaph.gw.co.common.utils.RequestFilterUtil" %>
<%@ page import="java.util.List" %>

<%
	String emp_ko_nm = StringUtil.nvl((String)request.getAttribute("emp_ko_nm"));//임직원이름
	AnnualMgrVO annualPlan = (AnnualMgrVO)request.getAttribute("annualPlan");//연차정보
	VacationVO vacationVO = (VacationVO)request.getAttribute("vacationVO");//휴가정보
	List<CodeVO> codeList = (List<CodeVO>)request.getAttribute("codeList"); //게시판종류
	
	int annualcommonDtCnt = 0; //공동연차사용일수
	annualcommonDtCnt = ((Integer)request.getAttribute("annualcommonDtCnt")).intValue(); 

	if(vacationVO == null){
		vacationVO = new VacationVO();
	}

	float yr_year_used_day = 0;
	if(Float.parseFloat(annualPlan.getYr_year_used_day()) < 0){
		yr_year_used_day = Float.parseFloat(annualPlan.getYr_year_used_day());
	}
	
	float reserved_day = annualPlan.getUseable_days() - annualPlan.getUsed_days() + yr_year_used_day;
	
%>
<script type="text/javascript">
	
	$(window).load(function() {
		var alertMessage = "<%=emp_ko_nm %> 님의 남은 연차 일은 <%=reserved_day%>일 입니다.";
		alertMessage += "\n\n남은 연차일 (<%=reserved_day%>일)= 연차사용 가능일(<%=annualPlan.getUseable_days()%>)일- 올해";
		alertMessage += "\n연차 사용일 (<%=annualPlan.getUsed_days()%>일)- 지난해 연차 사용일 (<%=yr_year_used_day%>일)";
		alertMessage += "\n\n위와 같은 메시지를 작성자에게 안내 하였습니다.";
		
		alert(alertMessage);
	});

	/**
	 * 벨리데이션 체크
	 * @returns {Boolean}
	 */
	function saveStep1(){
		if(formCheck.isNull(document.frm.subject, "제목을 입력해 주세요.")){
			return;
		}else if(formCheck.getByteLength(document.frm.subject.value) > 100){
			alert("제목은 한글 50자 (영문 100자) 이상 입력할수 없습니다");
			document.frm.subject.focus();
			return;
		}else if(formCheck.isNull(document.frm.reason, "휴가사유를 입력해 주세요.")){
				return;
		}else if(formCheck.getByteLength(document.frm.reason) > 100){
			alert("휴가 사유는 1000자 (영문 2000자) 이상 입력할수 없습니다");
			document.frm.subject.focus();
			return;
		}else if(formCheck.isNumDash(document.frm.contact_number.value)){
			alert("전화번호 형식이 아닙니다.\n하이픈(-)만 사용할 수 있습니다.");
			document.frm.contact_number.focus();
			return;
		}else if(formCheck.dateChk($("#start_ymd").val(),$("#end_ymd").val())<0){
			alert("휴가기간 시작 날짜가 휴가기간 종료 날짜 보다 이후 입니다.");
			return
		}
		return true;
	}
	
	 /* 날짜셋팅 */
	 function ymd_setting(){
		 $("#end_ymd").val($("#start_ymd").val());
	 }
	 
	 function readonlyCheck(){
		 /*반차일경우는 날짜를 하루만 선택할수 있다.*/
		 if($("#vacation_kind").val() == 'E06220'){
			$('#end_ymd').attr("disabled", "disabled");
		 }else{
			 $('#end_ymd').removeAttr("disabled"); 
		 }
	 }
	 
</script>
<div class="inner_box no_scroll">
	<strong class="tit_s">내역</strong>
	<table>
		<colgroup>
			<col class="cb_w86">
			<col style="width:229px">
			<col style="width:86px">
			<col style="width:90px">
			<col style="width:100px">
			<col style="">
		</colgroup>
		<tr>
			<th>휴가종류</th>
			<td class="holliday">
				<select class="serch_select01" id="vacation_kind" name="vacation_kind" onchange="readonlyCheck();">
				<%
				if(codeList.size()!=0){
					for(int i=0;i<codeList.size();i++){
						CodeVO codeVO = new CodeVO();
						codeVO = codeList.get(i);
						if("Y".equals(codeVO.getUse_yn())){
				%>
								<option value="<%=codeVO.getCd()%>" <%=StringUtil.nvl(vacationVO.getCd()).equals(codeVO.getCd())? "selected": ""%>  ><%=codeVO.getCd_nm()%></option>
				<%
						}
					}
				}
				%>
				</select>
			</td>
			<th>남은 연차일</th>
			<td class="annual"><%=reserved_day %></td>
			<th>연차사용가능일</th>
			<td class="bdrn annual"><%=annualPlan.getUseable_days() %></td>
		</tr>
		<tr>
			<th>휴가기간</th>
			<td>
				<span class="date_box">
					<input type="text" class="serch_date" name="start_ymd" id="start_ymd" onchange="javascript:ymd_setting();" value="<%=StringUtil.nvl(vacationVO.getStart_ymd())%>" readonly="readonly"/>
					<button type="button" class="btn_date"><span class="blind">날짜선택</span></button>
				</span> ~ 
				<span class="date_box">
					<input type="text" class="serch_date" name="end_ymd" id="end_ymd" value="<%=StringUtil.nvl(vacationVO.getEnd_ymd())%>" readonly="readonly"/>
					<button type="button" class="btn_date"><span class="blind">날짜선택</span></button>
				</span>
			</td>
			<th>올해 사용일</th>
			<td class="annual"><%=annualPlan.getUsed_days() %>(<%=annualcommonDtCnt%>)</td>
			<th>지난해 사용일</th>
			<td class="bdrn annual"><%=yr_year_used_day %></td>
		</tr>
		<tr>
			<th>휴가사유</th>
			<td colspan="5" class="bdrn cause"><textarea name="reason" id="reason"><%=RequestFilterUtil.toHtmlTagReplace("", vacationVO.getReason())%></textarea></td>
		</tr>
		<tr>
			<th>비상연락처</th>
			<td colspan="5" class="bdrn"><input type="text" name="contact_number" id="contact_number" value="<%=StringUtil.nvl(vacationVO.getContact_number())%>" maxlength="20"/></td>
		</tr>
	</table>
</div>	
