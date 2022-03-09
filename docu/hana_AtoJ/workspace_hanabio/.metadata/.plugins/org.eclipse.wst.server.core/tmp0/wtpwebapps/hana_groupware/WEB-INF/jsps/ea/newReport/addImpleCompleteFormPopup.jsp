<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%--
 * @파일명 : annualPlanFormPopup.jsp
 * @메뉴명 : 연차휴가사용계획서 등록 팝업
 * @최초작성일 : 2015/01/28            
 * @author : CHOIILJI                 
 * @수정내역 : 
--%>
<%@ include file ="/common/path.jsp" %>
<%
	String approval_seq = (String)request.getAttribute("approval_seq");	
%>
<!DOCTYPE html>
<html lang="ko">
<head>
	<%@include file="/include/head.jsp"%>
	<script type="text/javascript">
		function goSave(){
			if(confirm("저장하시겠습니까?") == true){
				document.getElementById("frm").action = "<%=GROUP_ROOT %>/ea/newReport/implCompleteInsert.do";
				document.getElementById("frm").submit();
			}
		}
	</script>
</head>
<body>
<!-- ######## start ####### -->
	
<!--  popup start -->
		<div class="individ_pop">
			<div class="popup_box">
				<h3>시행완료등록</h3>
				<div class="pop_con_all pop_register">
					<div class="pop_tit">
						<p class="tit">시행부서 시행완료</p>
					</div>
					<form name="frm" id="frm" method="post" >
					<input type ="hidden" id="approval_seq" name="approval_seq" value="<%=approval_seq%>">
					<div class="search_box03">
						<span class="ml14">내용</span>
						<input class="w210" type="text" id="complete_note" name="complete_note" value="" />
					</div>
					</form>
				</div>
				<div class="btn_pop">
					<button type="button" class="type_01" onclick="javascript:goSave(); return false;">확인</button>
					<button type="button" class="type_01" onclick="javascript:parent.layerClose(); return false;">취소</button>
				</div>
				<button type="button" class="close" onclick="javascript:parent.layerClose(); return false;"><span class="blind">닫기</span></button>
			</div>
		</div>
		<!--  popup end -->		

<!-- ######## end ######### -->
</body>
</html>