<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
	<%@include file="../../include/head.jsp"%>
</head>
<body>
<!-- ######## start ####### -->
	
<!--  popup start -->
		<div class="settle_pop">
			<div class="popup_box">
				<h3>시행부서 추가</h3>
				<div class="wrap_settle_pop">
					<div class="box_sign_overtime">
						<span class="tit">시행부서 추가</span>				
					</div>
					<div class="pop_con_all pop_register mt15">
						<div class="float_l w308 posa">
							<iframe src="http://localhost:8080/hanagw/html/include/popup/statement_processing_pop_ifr01.jsp" id="" frameborder="0" border="0" scrolling="no" width="520px" height="265px"></iframe>
						</div>
						<div class="float_r h193 mt52">
							<iframe src="http://localhost:8080/hanagw/html/include/popup/statement_processing_pop_ifr02.jsp" id="coopFrame" frameborder="0" border="0" scrolling="no" width="188px" height="214px"></iframe>
						</div>
					</div>
				</div>
				<button class="close"><span class="blind">닫기</span></button>
			</div>
		</div>
		<!--  popup end -->		

<!-- ######## end ######### -->

<script src="http://localhost:8080/hanagw/asset/js/jquery-1.11.1.min.js"></script>
<script src="http://localhost:8080/hanagw/asset/js/droplist.js"></script>
</body>
</html>