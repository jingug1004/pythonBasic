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
	<h2>메뉴관리</h2>
	<div class="management_box">
		<div class="cont_box fl menu_lft">
			//iframe
		</div>
		<div class="menu_cont fr">
			<iframe src="<%=GROUP_ROOT%>/html/include/05_code/menu_management_ifr.jsp" frameborder="0" border="0" width="518px" height="545px" style="overflow:hidden"></iframe>			
		</div>
	</div>
</div>

<!-- ######## end ######### -->
			
		</div>
	</div>
	
	<%@include file="../../include/footer.jsp"%>
</body>
</html>