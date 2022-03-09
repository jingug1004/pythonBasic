<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%--
 * @파일명 : error.jsp
 * @메뉴명 : 에러페이지
 * @최초작성일 : 2015/01/28            
 * @author : CHOIILJI                 
 * @수정내역 : 
--%>
<%@ page isErrorPage="true" %>
<%@ include file ="/common/path.jsp" %>
<%
	response.setStatus(HttpServletResponse.SC_OK);
%>
<!DOCTYPE html>
<html lang="ko">
<head>
	<%@include file="/include/head.jsp"%>
</head>
<body>
	<div class="wrap_header">
		<div class="header">
			<h1><a href="#" onclick="location.href='<%=GROUP_ROOT%>/main/main.do'"><img src="<%=GROUP_ROOT%>/asset/img/header_logo.jpg" alt="하나제약 그룹웨어" /></a></h1>
		</div>
	</div>
	
	<div class="wrap_con error_bg">
		<div class="wrap_error">
			<div class="error">
				<p>요청 처리 과정에서 <span>에러</span>가 발생하였습니다.</p>
				<p>잠시 후 다시 시도하시거나 전산팀에 문의 바랍니다.</p>
				
			</div>
		</div>
	</div>
	<%@include file="/include/footer.jsp"%>
</body>
</html>