<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%--
 * @파일명 : step1E01002.jsp
 * @메뉴명 : step1신규문서작성-기안서
 * @최초작성일 : 2015/01/28            
 * @author : 정진묵                 
 * @수정내역 : 
--%>
<%@ page import="com.hanaph.gw.co.common.utils.StringUtil" %>
<%@ page import="com.hanaph.gw.co.common.utils.RequestFilterUtil" %>
<%@ page import="com.hanaph.gw.ea.newReport.vo.DraftVO" %>
<%
	DraftVO draftVO = (DraftVO)request.getAttribute("draftVO");
	if(draftVO == null){
		draftVO = new DraftVO();
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
		}else if(formCheck.getByteLength(document.frm.controll.value) > 100){
			alert("통제은 한글 50자 (영문 100자) 이상 입력할 수 없습니다");
			document.frm.controll.focus();
			return;
		}else if(formCheck.getByteLength(document.frm.cooperation.value) > 100){
			alert("협조은 한글 50자 (영문 100자) 이상 입력할 수 없습니다");
			document.frm.cooperation.focus();
			return;
		}else if(formCheck.isNull(document.frm.purpose, "기안목적을 입력해 주세요.")){
			return;
		}else if(formCheck.getByteLength(document.frm.purpose.value) > 2000){
			alert("기안목적은 한글 1000자 (영문 2000자) 이상 입력할 수 없습니다");
			document.frm.purpose.focus();
			return;
		}else if(formCheck.isNull(document.frm.content, "기안내용을 입력해 주세요.")){
			return;
		}else if(formCheck.getByteLength(document.frm.content.value) > 2000){
			alert("기안내용은 한글 1000자 (영문 2000자) 이상 입력할 수 없습니다");
			document.frm.content.focus();
			return;
		}else if(formCheck.getByteLength(document.frm.etc.value) > 2000){
			alert("기안기타은 한글 1000자 (영문 2000자) 이상 입력할 수 없습니다");
			document.frm.etc.focus();
			return;
		}
		return true;
	}
</script>
<div class="inner_box no_scroll">
	<strong class="tit_s tit_sample">내 역</strong>
	<table class="tbl_draft">
		<colgroup>
			<col class="cb_w87">
			<col style="">
			<col style="width:87px">
			<col style="">
		</colgroup>
		<tbody>
			<tr>
				<th>시행일자</th>
				<td colspan="3" class="date bdrn">
					<span class="date_box">
						<input type="text" class="serch_date" id="imposition_ymd" name="imposition_ymd" value="<%=StringUtil.nvl(draftVO.getImposition_ymd()) %>" readonly="readonly"/>
						<button type="button" class="btn_date"><span class="blind">날짜선택</span></button>
					</span>
				</td>
			</tr>
			<tr>
				<th>통제</th>
				<td><input type="text" name="controll" id="controll" class="ipt_post" value="<%=RequestFilterUtil.toHtmlTagReplace("", draftVO.getControll()) %>" /></td>
				<th>협조</th>
				<td class="bdrn"><input type="text" name="cooperation" id="cooperation" class="ipt_post" value="<%=RequestFilterUtil.toHtmlTagReplace("", draftVO.getCooperation()) %>"/></td>
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
							<td class="ta"><textarea name="purpose" id="purpose"><%=RequestFilterUtil.toHtmlTagReplace("", draftVO.getPurpose())%></textarea></td>
						</tr>
						<tr>
							<th>기안내용</th>
							<td class="ta"><textarea name="content" id="content"><%=RequestFilterUtil.toHtmlTagReplace("", draftVO.getContent())%></textarea></td>
						</tr>
						<tr>
							<th>기타</th>
							<td class="ta"><textarea name="etc" id="etc"><%=RequestFilterUtil.toHtmlTagReplace("", draftVO.getEtc())%></textarea></td>
						</tr>
					</table>
				</td>
			</tr>
		</tbody>
	</table>
</div>	