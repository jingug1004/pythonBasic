<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%--
 * @파일명 : step1E01003.jsp
 * @메뉴명 : step1신규문서작성-사내통신
 * @최초작성일 : 2015/01/28            
 * @author : 정진묵                 
 * @수정내역 : 
--%>
<%@ page import="com.hanaph.gw.co.common.utils.StringUtil" %>
<%@ page import="com.hanaph.gw.co.common.utils.RequestFilterUtil" %>
<%@ page import="com.hanaph.gw.ea.newReport.vo.IncompanyVO" %>
<%
	IncompanyVO incompanyVO = (IncompanyVO)request.getAttribute("incompanyVO");
	if(incompanyVO == null){
		incompanyVO = new IncompanyVO();
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
		}else if(formCheck.getByteLength(document.frm.reception.value) > 100){
			alert("수신은 한글 50자 (영문 100자) 이상 입력할 수 없습니다");
			document.frm.reception.focus();
			return;
		}else if(formCheck.getByteLength(document.frm.send.value) > 100){
			alert("발신은 한글 50자 (영문 100자) 이상 입력할 수 없습니다");
			document.frm.send.focus();
			return;
		}else if(formCheck.getByteLength(document.frm.reference.value) > 100){
			alert("참조는 한글 50자 (영문 100자) 이상 입력할 수 없습니다");
			document.frm.reference.focus();
			return;
		}else if(formCheck.isNull(document.frm.purpose, "목적을 입력해 주세요.")){
			return;
		}else if(formCheck.getByteLength(document.frm.purpose.value) > 2000){
			alert("목적은 한글 1000자 (영문 2000자) 이상 입력할 수 없습니다");
			document.frm.purpose.focus();
			return;
		}else if(formCheck.isNull(document.frm.content, "내용을 입력해 주세요.")){
			return;
		}else if(formCheck.getByteLength(document.frm.content.value) > 2000){
			alert("내용은 한글 1000자 (영문 2000자) 이상 입력할 수 없습니다");
			document.frm.content.focus();
			return;
		}else if(formCheck.getByteLength(document.frm.etc.value) > 2000){
			alert("기타은 한글 1000자 (영문 2000자) 이상 입력할 수 없습니다");
			document.frm.etc.focus();
			return;
		}
		return true;
	}
</script>
<div class="inner_box no_scroll">
	<strong class="tit_s tit_sample">내 역	</strong>
	<table class="tbl_draft">
		<colgroup>
			<col class="cb_w87">
			<col style="">
			<col style="width:86px">
			<col style="">
		</colgroup>
		<tbody>
			<tr>
				<th>수신</th>
				<td colspan="3" class="bdrn"><input type="text" name="reception" id="reception" class="ipt_get" value="<%=RequestFilterUtil.toHtmlTagReplace("", incompanyVO.getReception()) %>" /></td>
			</tr>
			<tr>
				<th>발신</th>
				<td><input type="text" name="send" id="send" class="ipt_post" value="<%=RequestFilterUtil.toHtmlTagReplace("", incompanyVO.getSend()) %>" /></td>
				<th>참조</th>
				<td class="bdrn"><input type="text" name="reference" id="reference" class="ipt_post" value="<%=RequestFilterUtil.toHtmlTagReplace("", incompanyVO.getReference()) %>" /></td>
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
							<td class="ta"><textarea name="purpose" id="purpose"><%=RequestFilterUtil.toHtmlTagReplace("", incompanyVO.getPurpose()) %></textarea></td>
						</tr>
						<tr>
							<th>내용</th>
							<td class="ta"><textarea name="content" id="content"><%=RequestFilterUtil.toHtmlTagReplace("", incompanyVO.getContent()) %></textarea></td>
						</tr>
						<tr>
							<th>기타</th>
							<td class="ta"><textarea name="etc" id="etc"><%=RequestFilterUtil.toHtmlTagReplace("", incompanyVO.getEtc()) %></textarea></td>
						</tr>
					</table>
				</td>
			</tr>
		</tbody>
	</table>
</div>