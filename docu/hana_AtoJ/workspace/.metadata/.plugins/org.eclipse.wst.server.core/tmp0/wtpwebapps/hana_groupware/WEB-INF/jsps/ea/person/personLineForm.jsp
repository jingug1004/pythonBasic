<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%--
 * @파일명 : personLineForm.jsp
 * @메뉴명 : 개인결재라인 마스터
 * @최초작성일 : 2015/01/28            
 * @author : CHOIILJI                 
 * @수정내역 : 
--%>
<%@ include file ="/common/path.jsp" %>
<!DOCTYPE html>
<html lang="ko">
<head>
	<%@include file="/include/head.jsp"%>
	<script type="text/javascript">
		function goSubject(){
			if(formCheck.isNull(document.getElementById("subject"), "제목을 입력해주세요.")){
				return;
			}
			parent.goNowApprovalSave(document.getElementById("subject"));
			parent.layerClose(); 
		}
	
	</script>
</head>
<body>
<!-- ######## start ####### -->
	
<!--  popup start -->
		<div class="individ_pop">
			<div class="popup_box">
				<h3>개인결재라인등록</h3>
				<div class="pop_con_all pop_register">
					<div class="pop_tit">
						<p class="tit">개인결재라인등록</p>
						<p>설정하신 결재라인을 다음 결재라인명으로 등록합니다.</p>
					</div>
					<div class="search_box03">
						<span>결재라인명</span>
						<input type="text" id="subject" name="subject" value="" />
					</div>
				</div>
				<div class="btn_pop">
					<button type="button" class="type_01"  onclick="javascript:goSubject(); return false;">확인</button>
					<button type="button" class="type_01" onclick="javascript:parent.layerClose(); return false;">취소</button>
				</div>
				<button type="button" class="close" onclick="javascript:parent.layerClose(); return false;"><span class="blind">닫기</span></button>
			</div>
		</div>
		<!--  popup end -->		

<!-- ######## end ######### -->
</body>
</html>