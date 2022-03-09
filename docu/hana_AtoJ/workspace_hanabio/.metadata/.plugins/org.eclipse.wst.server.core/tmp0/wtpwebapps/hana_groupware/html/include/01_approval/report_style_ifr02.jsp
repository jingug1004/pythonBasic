<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="ko">
<head>
	<%@include file="../../include/head.jsp"%>	
</head>
<body>
	<div class="settle_men fl" data-role="ui-droplist" data-type="radio">
		<h4>결재자</h4>
		<ul class="select_list fl apprList" data-role="ui-droplist-list">
			<li data-role="ui-droplist-item"><input type="radio" name="appr" id="a01" /><label for="a01">아무개</label></li>
			<li data-role="ui-droplist-item"><input type="radio" name="appr" id="a02" /><label for="a02">홍길동</label></li>
			<li data-role="ui-droplist-item"><input type="radio" name="appr" id="a03" /><label for="a03">정임원</label></li>
			<li data-role="ui-droplist-item"><input type="radio" name="appr" id="a04" /><label for="a04">아무개</label></li>
			<li data-role="ui-droplist-item"><input type="radio" name="appr" id="a05" /><label for="a05">홍길동</label></li>
			<li data-role="ui-droplist-item"><input type="radio" name="appr" id="a06" /><label for="a06">정임원</label></li>
		</ul>
		<div class="btn_list fr">
			<a href="#" class="btn_top" data-role="ui-droplist-btn-up"><img src="../../../asset/img/popup_btn_top.gif" alt="위로"></a>
			<a href="#" class="btn_bottom" data-role="ui-droplist-btn-down"><img src="../../../asset/img/popup_btn_bottom.gif" alt="아래로"></a>
			<a href="#" class="btn_close" data-role="ui-droplist-btn-remove"><img src="../../../asset/img/popup_btn_close02.gif" alt="삭제"></a>
		</div>
	</div>

	<!-- <div class="settle_men fr" data-role="ui-droplist" data-type="basic">
		<h4>참조자</h4>
		<ul class="select_list fl refeList" data-role="ui-droplist-list">
			<li data-role="ui-droplist-item"><a href="#">최일지</a></li>														
		</ul>
		<div class="btn_list fr">
			<a href="#" class="btn_top" data-role="ui-droplist-btn-up"><img src="../../../asset/img/popup_btn_top.gif" alt="위로"></a>
			<a href="#" class="btn_bottom" data-role="ui-droplist-btn-down"><img src="../../../asset/img/popup_btn_bottom.gif" alt="아래로"></a>
			<a href="#" class="btn_close" data-role="ui-droplist-btn-remove"><img src="../../../asset/img/popup_btn_close02.gif" alt="삭제"></a>
		</div>
	</div> -->

<script src="http://localhost:8080/hanagw/asset/js/jquery-1.11.1.min.js"></script>
<script src="http://localhost:8080/hanagw/asset/js/droplist.js"></script>
</body>
</html>