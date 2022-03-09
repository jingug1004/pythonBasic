<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>Test Page</title>
</head>
<script type="text/javascript">
function approval(arg1){
	alert(arg1);
}
</script>
<body>
<h1>Test.....1</h1>
<BR>
<BR>
<input type='button' value='기 안' onclick='approval("기안");'>
<input type='button' value='결 재' onclick='approval("결재");'>
</body>
</html>