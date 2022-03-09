<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%--
 * @파일명 : step1E01005.jsp
 * @메뉴명 : step1신규문서작성-근태계
 * @최초작성일 : 2015/01/28            
 * @author : 정진묵                 
 * @수정내역 : 
--%>
<%@ page import="com.hanaph.gw.co.common.utils.StringUtil" %>
<%@ page import="com.hanaph.gw.co.common.utils.RequestFilterUtil" %>
<%@ page import="com.hanaph.gw.ea.newReport.vo.CommuteVO" %>
<%
	CommuteVO commuteVO = (CommuteVO)request.getAttribute("commuteVO");
	if(commuteVO == null){
		commuteVO = new CommuteVO();
	}
	
	CommuteVO commuteTardy = (CommuteVO)request.getAttribute("commuteTardy");
	if(commuteTardy == null){
		commuteTardy = new CommuteVO();
	}
%>
<script type="text/javascript">
	/**
	 * 벨리데이션 체크
	 * @returns {Boolean}
	 */
	function saveStep1(){
		if(formCheck.isNull(document.frm.subject, "제목을 입력해 주세요.")){
			return;
		}else if(formCheck.getByteLength(document.frm.subject.value) > 100){
			alert("제목은 한글 50자 (영문 100자) 이상 입력할 수 없습니다");
			document.frm.subject.focus();
			return;
		}else if(formCheck.isNull(document.frm.kind, "내용을 입력해 주세요.")){
			return;	
		}else if(formCheck.getByteLength(document.frm.kind.value) > 100){
			alert("내용은 한글 50자 (영문 100자) 이상 입력할 수 없습니다");
			document.frm.kind.focus();
			return;
		}else if(formCheck.getByteLength(document.frm.bogo_yn.value) > 100){
			alert("사전보고유무는 한글 50자 (영문 100자) 이상 입력할 수 없습니다");
			document.frm.bogo_yn.focus();
			return;
		}else if(formCheck.getByteLength(document.frm.bogo_receiver.value) > 100){
			alert("사전보고수령자는 한글 50자 (영문 100자) 이상 입력할 수 없습니다");
			document.frm.bogo_receiver.focus();
			return;
		}else if(formCheck.isNull(document.frm.imposition_ymd, "근태계 날짜를 선택해 주세요.")){
			$("#imposition_ymd_date").prev().datepicker({"dateFormat":"yy-mm-dd"});
			$("#imposition_ymd_date").prev().focus();
			return;
		}else if(formCheck.getByteLength(document.frm.mibogo_reason.value) > 100){
			alert("미보고사유은 한글 100자 (영문 200자) 이상 입력할 수 없습니다");
			document.frm.mibogo_reason.focus();
			return;
		}else if(formCheck.getByteLength(document.frm.reason.value) > 2000){
			alert("사유 내용은 한글 1000자 (영문 2000자) 이상 입력할 수 없습니다");
			document.frm.reason.focus();
			return;
		}
		return true;
	}
	 
	 /* 날짜셋팅 */
	 function ymd_setting(){
		 $("#end_absence_ymd").val($("#start_absence_ymd").val());
	 }
	 
</script>
<div class="inner_box no_scroll">
	<strong class="tit_s tit_sample">내 역</strong>
	<table class="tbl_report">
		<colgroup>
			<col class="cb_w87">
			<col style="">
			<col style="width:86px">
			<col style="">
		</colgroup>
		<tbody>
			<tr>
				<th>내용</th>
				<td><input type="text" name="kind" id="kind" value="<%=RequestFilterUtil.toHtmlTagReplace("", commuteVO.getKind())%>" /></td>
				<th>사전보고유무</th>
				<td class="bdrn"><input type="text" name="bogo_yn" id="bogo_yn" value="<%=RequestFilterUtil.toHtmlTagReplace("", commuteVO.getBogo_yn())%>" /></td>
			</tr>
			<tr>
				<th>사전보고수령자</th>
				<td><input type="text" name="bogo_receiver" id="bogo_receiver" value="<%=RequestFilterUtil.toHtmlTagReplace("", commuteVO.getBogo_receiver())%>" /></td>
				<th>근태계 날짜</th>
				<td class="bdrn date">
					<span class="date_box">
						<input type="text" class="serch_date" name="imposition_ymd" id="imposition_ymd" readonly="readonly" value="<%=StringUtil.nvl(commuteVO.getImposition_ymd())%>" />
						<button type="button" class="btn_date" id="imposition_ymd_date"><span class="blind">날짜선택</span></button>
					</span>
				</td>
			</tr>
			<tr>
				<th>미보고사유</th>
				<td colspan="3" class="bdrn"><input type="text" name="mibogo_reason" id="mibogo_reason" value="<%=RequestFilterUtil.toHtmlTagReplace("", commuteVO.getMibogo_reason())%>" /></td>
			</tr>
			<tr>
				<th>사유</th>
				<td colspan="3" class="bdrn inner">
					<table>
						<colgroup>
							<col style="width:65px"/>
							<col style="width:186px"/>
							<col style="width:50px"/>
						</colgroup>
						<tbody>
							<tr>
								<th class="bdtn">지각(출근시각)</th>
								<td class="bdrn bdtn"><input type="text" name="late_tm" id="late_tm" value="<%=StringUtil.nvl(commuteVO.getLate_tm())%>" maxlength="100" /></td>
								<td>지각  <%=commuteTardy.getTardy()%> 회</td>
							</tr>
							<tr>
								<th>조퇴(발생시각)</th>
								<td class="bdrn" colspan="2"><input type="text" name="leave_tm" id="leave_tm" value="<%=StringUtil.nvl(commuteVO.getLeave_tm())%>" maxlength="100" /></td>										
							</tr>
							<tr>
								<th>결근(결근기간)</th>
								<td class="bdrn date" colspan="2">
									<span class="date_box">
										<input type="text" class="serch_date" name="start_absence_ymd" id="start_absence_ymd" onchange="javascript:ymd_setting();" readonly="readonly" value="<%=StringUtil.nvl(commuteVO.getStart_absence_ymd())%>"/>
										<button type="button" class="btn_date"><span class="blind">날짜선택</span></button>
									</span>
									~
									<span class="date_box">
										<input type="text" class="serch_date" name="end_absence_ymd" id="end_absence_ymd" readonly="readonly" value="<%=StringUtil.nvl(commuteVO.getEnd_absence_ymd())%>"/>
										<button type="button" class="btn_date"><span class="blind">날짜선택</span></button>
									</span>
								</td>										
							</tr>
							<tr>
								<th>내용</th>
								<td class="bdrn" colspan="2"><textarea name="reason" id="reason" ><%=RequestFilterUtil.toHtmlTagReplace("", commuteVO.getReason())%></textarea></td>
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