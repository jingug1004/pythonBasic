<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%--
 * @파일명 : index.jsp
 * @메뉴명 : /sale_on/ 경로 호출 시 로드 되는 index페이지    
 * @최초작성일 : 2015/01/16            
 * @author : 김재갑                  
 * @수정내역 : 
--%>
<%@ include file ="/common/path.jsp" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>Insert title here</title>
	<%@ include file ="/include/head.jsp" %>
</head>
<body>
<script>
 location.href = "<%=ONLINE_ROOT%>/main.do";
</script>
</body>
</html>