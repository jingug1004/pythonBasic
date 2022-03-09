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
		<h2>개인결재라인</h2>
		<div class="settlement_box">
			<p class="list_t">문서 내용과 결재라인을 최종 확인하시고  요청버튼을 클릭하시면 상신됩니다.</p>
			<p class="mt5">작성완료버튼을 클릭하시면 내가 올린 문서에 작성 중 상태로 등록됩니다. </p>
			<div class="settle_individ">				
				<div class="fl">
					<div class="search_box01">
						<span>결재라인</span>
						<select>
							<option value="">-직접선택-</option>
							<option value="">[개인] 기안서0811</option>
							<option value="">[개인] 기안서1012</option>
						</select>
					</div>
					<div class="cont_box04">
						
					</div>					
				</div>
				<div class="fr">
					<div class="wrap_settle_cont">
						<div class="settle_cont01 fl posa">
							<iframe src="http://localhost:8080/hanagw/html/include/01_approval/settlement_individual_ifr01.jsp" id="settFrame" frameborder="0" border="0" scrolling="no" width="432px" height="265px"></iframe>
						</div>			
						<div class="settle_cont02 fr mt52 h212">
							<iframe src="http://localhost:8080/hanagw/html/include/01_approval/settlement_individual_ifr02.jsp" id="apprFrame" frameborder="0" border="0" scrolling="no" width="152px" height="265px"></iframe>
						</div>
					</div>

					<div class="wrap_settle_cont mt20">
						<div class="settle_cont01 fl">
								<iframe src="http://localhost:8080/hanagw/html/include/01_approval/settlement_individual_ifr03.jsp" frameborder="0" border="0" scrolling="no" width="280px" height="212px"></iframe>
						</div>
						<div class="settle_cont02 fr">
								<iframe src="http://localhost:8080/hanagw/html/include/01_approval/settlement_individual_ifr04.jsp" id="coopFrame" frameborder="0" border="0" scrolling="no" width="152px" height="212px"></iframe>
						</div>
					</div>
					<div class="list_btn last_step">
						<div class="list_button">
						<button class="type_01">현재결재라인저장</button>
						<button class="type_01">현재결재라인삭제</button>
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