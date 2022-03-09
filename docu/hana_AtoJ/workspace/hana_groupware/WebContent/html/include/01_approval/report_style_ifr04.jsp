<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="ko">
<head>
	<%@include file="../../include/head.jsp"%>	
</head>
<body>
	<!-- <div class="settle_men fl" data-role="ui-droplist" data-type="basic">
		<h4>협조부서</h4>
		<ul class="select_list01 fl coopList" data-role="ui-droplist-list">
			<li data-role="ui-droplist-item"><a href="#">영업기획팀</a></li>
			<li data-role="ui-droplist-item"><a href="#">전산팀</a></li>
		</ul>
		<div class="btn_list01 fr">
			<a href="#" class="btn_top" data-role="ui-droplist-btn-up"><img src="../../../asset/img/popup_btn_top.gif" alt="위로" /></a>
			<a href="#" class="btn_bottom" data-role="ui-droplist-btn-down"><img src="../../../asset/img/popup_btn_bottom.gif" alt="아래로" /></a>
			<a href="#" class="btn_close" data-role="ui-droplist-btn-remove"><img src="../../../asset/img/popup_btn_close02.gif" alt="삭제" /></a>
		</div>
	</div> -->

	<div class="settle_men fr" data-role="ui-droplist" data-type="basic">
		<h4>시행부서</h4>
		<ul class="select_list01 fl exeList" data-role="ui-droplist-list">
			<li data-role="ui-droplist-item"><a href="#">구매팀</a></li>
		</ul>
		<div class="btn_list01 fr">
			<a href="#" class="btn_top" data-role="ui-droplist-btn-up"><img src="../../../asset/img/popup_btn_top.gif" alt="위로" /></a>
			<a href="#" class="btn_bottom" data-role="ui-droplist-btn-down"><img src="../../../asset/img/popup_btn_bottom.gif" alt="아래로" /></a>
			<a href="#" class="btn_close" data-role="ui-droplist-btn-remove"><img src="../../../asset/img/popup_btn_close02.gif" alt="삭제" /></a>
		</div>
	</div>

<script src="http://localhost:8080/hanagw/asset/js/jquery-1.11.1.min.js"></script>
<script src="http://localhost:8080/hanagw/asset/js/droplist.js"></script>
</body>
</html>