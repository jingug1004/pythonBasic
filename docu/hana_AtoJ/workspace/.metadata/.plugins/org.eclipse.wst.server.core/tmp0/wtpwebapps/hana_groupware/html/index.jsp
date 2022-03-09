<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ include file ="/common/path.jsp" %>
<html lang="ko">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	
	<!--[if lte IE 9]><script src="asset/js/IE9.js"></script><![endif]-->
	<!--[if lte IE 9]><script src="asset/js/html5.js"></script><![endif]-->
	
	<title>title</title>

	<link rel="stylesheet" type="text/css" href="../asset/css/fonts.css" />
	<link rel="stylesheet" type="text/css" href="../asset/css/main.css" />
	
	<script type="text/javascript" src="../asset/js/jquery-1.11.1.min.js"></script>
</head>
<body>
	<%@include file="include/header.jsp"%>
	<%@include file="include/gnb.jsp"%>
	
	<div class="wrap_con">
		<div class="content">
			<%@include file="include/location.jsp"%>
			<%@include file="include/lnb.jsp"%>
			<%//@include file="include/content.jsp"%>
			<%//@include file="include/00_notify/noticeboard.jsp"%>
			<%@include file="include/00_notify/noticeboard_detail.jsp"%>
			<%//@include file="include/05_code/menu_management.jsp"%>
			<%//@include file="include/05_code/code_management.jsp"%>			
			<%//@include file="include/05_code/authority.jsp"%>
		</div>
	</div>
	
	<%@include file="include/footer.jsp"%>
</body>
</html>