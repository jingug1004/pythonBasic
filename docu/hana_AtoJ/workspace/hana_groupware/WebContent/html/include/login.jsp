<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
	<%@include file="../include/head.jsp"%>
</head>
<body>
	<div class="wrap_header no_bg">
		<div class="header">
			<h1><a href="#"><img src="<%=GROUP_ROOT%>/asset/img/header_logo.jpg" alt="" /></a></h1>
		</div>
	</div>
	
	<div class="wrap_con login_bg">
		<div class="wrap_login">
			<div class="login">
				<div class="inner">
					<p class="slogan">
						For the Best Medicine, Better Life
					</p>
					<h1>
						하나제약 주식회사 <span>그룹웨어</span>
					</h1>
					<p class="txt">
						하나제약 주식회사 그룹웨어입니다.<br />
						그룹웨어 시스템을 이용하시려면 로그인을 하시기 바랍니다.
					</p>
					<div class="wrap_log">
						<div class="wrap_input">
							<p class="line">
								<label>아이디</label> <input type="text" />
							</p>
							<p class="line">
								<label>비밀번호</label> <input type="password" />
							</p>
							<p class="chk">
								<input type="checkbox" id="save_id" class="save_id_chk" /> <label for="save_id" class="save_id">아이디 저장</label>
							</p>
						</div>
						<div class="wrap_btn">
							<button class="btn_login">로그인</button>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	
	<%@include file="../include/footer.jsp"%>
</body>
</html>