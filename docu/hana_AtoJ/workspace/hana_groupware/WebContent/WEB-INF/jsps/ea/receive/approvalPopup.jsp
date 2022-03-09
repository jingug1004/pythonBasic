<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%--
 * @파일명 : approvalPopup.jsp
 * @메뉴명 : 결재상세팝업
 * @최초작성일 : 2015/02/10            
 * @author : Jung.Jin.Muk(pc123pc@irush.co.kr)                 
 * @수정내역 : 
--%>
<%@ page import="com.hanaph.gw.co.common.utils.StringUtil" %>
<%@ include file ="/common/path.jsp" %>
<% 
	String gubun = StringUtil.nvl((String)request.getAttribute("gubun")); //구분
	String emp_ko_nm = StringUtil.nvl((String)request.getAttribute("emp_ko_nm")); //임직원 이름

	String str = "";
	
	if("1".equals(gubun)){
		str = "결재";
	}else if("2".equals(gubun)){
		str = "반려";
	}else if("3".equals(gubun)){
		str = "전결";
	}else if("4".equals(gubun)){
		str = "반려취소";
	}else if("5".equals(gubun)){
		str = "승인취소";
	}else if("6".equals(gubun)){
		str = "전결취소";
	}else if("7".equals(gubun)){
		str = "요청취소";
	}else if("8".equals(gubun)){
		str = "일괄결재";
	}
%>
<!DOCTYPE html>
<html lang="ko">
<head>
	<%@include file="/include/head.jsp"%>
	<script type="text/javascript">
	/**
	 * 반려 사유 벨리데이션 체크
	 */
	function goParentApproval(){
		<%if("2".equals(gubun)){ %>
			if(document.getElementById("rejection_reason").value ==""){
				alert("반려 사유를 입력해 주세요.");
				document.getElementById("rejection_reason").focus();
				return;
			}
			parent.document.getElementById("rejection_reason").value = document.getElementById("rejection_reason").value;
			
		<%}%>
		<%if("7".equals(gubun)){//요청취소%>
			parent.approvalCompleteReject();
		<%}else{%>
			parent.updateApproval('<%=gubun%>');
		<%}%>
	} 
	</script>
</head>
<body>
<div class="individ_pop">
	<div class="popup_box">
		<h3><%=str%>하기</h3>
		<div class="pop_con_all pop_register">
			<div class="box_sign_all">
				<p><span><%=emp_ko_nm%></span>님 <em><%=str%></em> 하시겠습니까?</p>
			</div>
			<%if("2".equals(gubun)){//반려 %>
			<div class="box_scroll">
				<textarea name="rejection_reason" id="rejection_reason" class="ta_return"></textarea>
			</div>
			<%} %>
		</div>
		<div class="btn_pop">
			<button type="button" class="type_01" onclick="javascript:goParentApproval();">확인</button>
			<button type="button" class="type_01" onclick="parent.layerClose();">취소</button>
		</div>
		<button type="button" class="close" onclick="parent.layerClose();"><span class="blind">닫기</span></button>
	</div>
</div>
</body>
</html>