<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="ko">
<head>
	<%@include file="../include/head.jsp"%>	
</head>
<body>

<div class="wrap_reset_pass">
	<div class="pass_menu">
		<h3>비밀번호변경</h3>
		<ul class="input_txt">
			<li>
				<span class="txt_name">현재비밀번호</span>
				<input type="text" class="" />
			</li>
			<li>
				<span class="txt_name">새비밀번호</span>
				<input type="text" class="" />
			</li>
			<li>
				<span class="txt_name">새비밀번호확인</span>
				<input type="text" class="" /> 
			</li>
		</ul>
		<div class="btn_box mt20">
			<button class="type_02">확인</button>
			<button class="type_02">취소</button>
		</div>
		
		<button class="close"><span class="blind">닫기</span></button>
	</div>
</div>

</body>
</html>