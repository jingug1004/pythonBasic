<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="ko">
<head>
	<%@include file="../../include/head.jsp"%>	
</head>
<body>

<div class="wrap_popup_menu">
	<div class="popup_menu">
		<h3>결재라인지정<span> STEP2</span></h3>
		<!-- start 2015-02-11 수정 -->
		<!-- 
		<div class="list_btn01">
			<div class="list_button">
				<button class="type2">이전</button>
				<button class="type2">결재라인적용</button>
			</div>
		</div>
		 -->
		
		<div class="top_btn_area">
			<button class="type2">삭제</button>
			<div class="fr">
				<button class="type2">이전</button>
				<button class="type2">결재라인적용청</button>
			</div>
		</div>
		<!-- end 2015-02-11 수정 -->
					
		<div class="popup_list">
			<div class="settl_txt">
				<p class="tit"><em>STEP2</em>. 결재라인지정 </p>
				<p>해당 문서의 결재라인을 지정하고 완료가 되면 결재라인 적용 버튼을 클릭하시기 바랍니다.  </p>
				<p>STEP3으로 이동합니다.</p>
			</div>
			<div class="fl">
				<div class="search_box01">
					<span>결재라인</span>
					<select>
						<option value="">기본결재라인</option>
						<option value="">기본결재라인01</option>
						<option value="">기본결재라인02</option>
					</select>
				</div>
				<div class="cont_box04">
					
				</div>		
			</div>
			<div class="fr">
				<div class="wrap_settle_cont">
					<div class="settle_cont01 fl posa">
						<iframe src="http://localhost:8080/hanagw/html/include/01_approval/settlement_select_ifr01.jsp" id="settFrame" frameborder="0" border="0" scrolling="no" width="432px" height="265px"></iframe>
					</div>			
					<div class="settle_cont02 fr mt52 h212">
						<iframe src="http://localhost:8080/hanagw/html/include/01_approval/settlement_select_ifr02.jsp" id="apprFrame" frameborder="0" border="0" scrolling="no" width="152px" height="265px"></iframe>
					</div>
				</div>

				<div class="wrap_settle_cont mt20">
					<div class="settle_cont01 fl">
							<iframe src="http://localhost:8080/hanagw/html/include/01_approval/settlement_select_ifr03.jsp" frameborder="0" border="0" scrolling="no" width="280px" height="212px"></iframe>
					</div>
					<div class="settle_cont02 fr">
							<iframe src="http://localhost:8080/hanagw/html/include/01_approval/settlement_select_ifr04.jsp" id="coopFrame" frameborder="0" border="0" scrolling="no" width="152px" height="212px"></iframe>
					</div>
				</div>
				<div class="list_btn last_step">
					<div class="list_button">
					<button class="type_01">현재결재라인저장</button>
					</div>
				</div>
			</div>
		</div>
		<button class="close"><span class="blind">닫기</span></button>
	</div>
</div>
</div>

<script src="http://localhost:8080/hanagw/asset/js/jquery-1.11.1.min.js"></script>
<script src="http://localhost:8080/hanagw/asset/js/droplist.js"></script>
</body>
</html>