<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
	<%@include file="../../include/head.jsp"%>
</head>
<body>
	<%@include file="../../include/header.jsp"%>
	<%@include file="../../include/gnb.jsp"%>
	
	<div class="wrap_con">
		<div class="content">
			<%@include file="../../include/location.jsp"%>
			<%@include file="../../include/lnb.jsp"%>
			
			<!-- ######## start ####### -->
	<div class="cont float_left">
		<h2>문서결재라인<span class="type"> 등록</span></h2>
		<div class="list_btn last_step">
			<div class="list_button">
			<button class="type_01">결재라인 적용하기</button>
			<button class="type_01">취소</button>
			</div>
		</div>
		<div class="settlement_box">
			<ul class="group">
				<li><span class="tit">양식명</span><span>기안서</span></li>
				<li><span class="tit">부서</span><span class="bdrn">영업부</span></li>
			</ul>
			<p class="txt">＊아래의 부서를 선택하시면 소속 사용자가 우측에 나옵니다.</p>
			<div class="settle_individ mgn">				
				<div class="fl">
					<div class="cont_box04 mgn h494">
						
					</div>					
				</div>
				<div class="fr">
				<div class="wrap_settle_cont">
						<div class="settle_cont01 fl posa">
							<iframe src="http://localhost:8080/hanagw/html/include/01_approval/report_style_ifr01.jsp" id="settFrame" frameborder="0" border="0" scrolling="no" width="432px" height="265px"></iframe>
						</div>			
						<div class="settle_cont02 fr mt52 h212">
							<iframe src="http://localhost:8080/hanagw/html/include/01_approval/report_style_ifr02.jsp" id="apprFrame" frameborder="0" border="0" scrolling="no" width="152px" height="265px"></iframe>
						</div>
					</div>

					<div class="wrap_settle_cont mt20">
						<div class="settle_cont01 fl">
								<iframe src="http://localhost:8080/hanagw/html/include/01_approval/report_style_ifr03.jsp" frameborder="0" border="0" scrolling="no" width="280px" height="212px"></iframe>
						</div>
						<div class="settle_cont02 fr">
								<iframe src="http://localhost:8080/hanagw/html/include/01_approval/report_style_ifr04.jsp" id="coopFrame" frameborder="0" border="0" scrolling="no" width="152px" height="212px"></iframe>
						</div>
					</div>
				</div>
			</div>
		</div>
		<!-- popup "settlement_individual_register.jsp" 삽입 -->
		</div>
	</div>
	


<!-- ######## end ######### -->
			
		</div>
	</div>
	
	<%@include file="../../include/footer.jsp"%>
</body>
</html>