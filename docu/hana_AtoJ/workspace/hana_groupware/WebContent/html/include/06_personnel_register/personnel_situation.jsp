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
	<h2>인원현황조회</h2>
	<div class="wrap_personnel_situation">
		<div class="wrap_tree">
			<p class="explain">아래의 부서를 선택하시면 소속 사용자가 나옵니다.</p>
			<div class="tree">
				
			</div>
		</div>
		
		<div class="wrap_status ml14">
			<iframe src="<%=GROUP_ROOT%>/html/include/06_personnel_register/personnel_situation_ifr.jsp" frameborder="0" border="0" width="490px" height="550px"></iframe>
		</div>
	</div>
</div>

<!-- ######## end ######### -->
			
		</div>
	</div>
	
	<%@include file="../../include/footer.jsp"%>
</body>
</html>