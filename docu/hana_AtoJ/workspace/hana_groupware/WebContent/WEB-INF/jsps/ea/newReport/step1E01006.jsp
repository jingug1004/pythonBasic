<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%--
 * @파일명 : step1E01006.jsp
 * @메뉴명 : step1신규문서작성-참가신청서
 * @최초작성일 : 2015/01/28            
 * @author : 정진묵                 
 * @수정내역 : 
--%>
<%@ page import="com.hanaph.gw.co.common.utils.StringUtil" %>
<%@ page import="com.hanaph.gw.co.common.utils.RequestFilterUtil" %>
<%@ page import="com.hanaph.gw.ea.newReport.vo.ParticipationVO" %>
<%
	ParticipationVO participationVO = (ParticipationVO)request.getAttribute("participationVO");
	if(participationVO == null){
		participationVO = new ParticipationVO();
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
		}else if(formCheck.isNull(document.frm.kind, "종류를 입력해 주세요.")){
			return;
		}else if(formCheck.isNull(document.frm.content, "사무 및 내용을 입력해 주세요.")){
			return;
		}else if(formCheck.getByteLength(document.frm.kind.value) > 100){
			alert("종류는 한글 50자 (영문 100자) 이상 입력할 수 없습니다");
			document.frm.kind.focus();
			return;
		}else if(formCheck.getByteLength(document.frm.content.value) > 2000){
			alert("사무 및 내용 한글 1000자 (영문 2000자) 이상 입력할 수 없습니다");
			document.frm.content.focus();
			return;
		}else if(formCheck.isNull(document.frm.start_ymd, "시작 날짜를 선택해 주세요.")){
			$("#start_ymd_date").prev().datepicker({"dateFormat":"yy-mm-dd"});
			$("#start_ymd_date").prev().focus();
			return;
		}else if(formCheck.isNull(document.frm.end_ymd, "마지막 날짜를 선택해 주세요.")){
			$("#end_ymd_date").prev().datepicker({"dateFormat":"yy-mm-dd"});
			$("#end_ymd_date").prev().focus();
			return;
		}else if(formCheck.isNumDash(document.frm.contact_number.value)){
			alert("비상연락처는 숫자,\"-\"만 입력가능 합니다 ");
			document.frm.contact_number.focus();
			return;
		}
		return true;
	}
	 
	 /* 날짜셋팅 */
	 function ymd_setting(){
		 $("#end_ymd").val($("#start_ymd").val());
	 }
</script>
<div class="inner_box no_scroll">
	<strong class="tit_s tit_sample">내 역</strong>
	<table class="tbl_participate">
		<colgroup>
			<col class="cb_w86">
			<col style="">
		</colgroup>
		<tbody>
			<tr>
				<th>종류</th>
				<td class="bdrn"><input type="text" name="kind" id="kind" value="<%=RequestFilterUtil.toHtmlTagReplace("", participationVO.getKind()) %>" /></td>
			</tr>
			<tr>
				<th>사무 및 내용</th>
				<td class="bdrn"><textarea name="content" id="content"><%=RequestFilterUtil.toHtmlTagReplace("", participationVO.getContent()) %></textarea></td>
			</tr>
			<tr>
				<th>기간</th>
				<td class="bdrn">
					<span class="date_box">
						<input type="text" class="serch_date" name="start_ymd" id="start_ymd" onchange="javascript:ymd_setting();" readonly="readonly" value="<%=StringUtil.nvl(participationVO.getStart_ymd()) %>"/>
						<button type="button" class="btn_date" id="start_ymd_date"><span class="blind">날짜선택</span></button>
					</span> ~ 
					<span class="date_box">
						<input type="text" class="serch_date" name="end_ymd" id="end_ymd" readonly="readonly" value="<%=StringUtil.nvl(participationVO.getEnd_ymd()) %>"/>
						<button type="button" class="btn_date" id="end_ymd_date"><span class="blind">날짜선택</span></button>
					</span>
				</td>
			</tr>
			<tr>
				<th>비상연락처</th>
				<td class="bdrn"><input type="text" name="contact_number" id="contact_number" value="<%=StringUtil.nvl(participationVO.getContact_number()) %>" maxlength="100"/></td>
			</tr>
		</tbody>
	</table>
</div>	