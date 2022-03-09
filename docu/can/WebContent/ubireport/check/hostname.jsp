<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import="java.net.*" %>

<html>
<head>
<title>Check Hostname</title>
<style>
	body  { font-family:sans-serif, arial; font-size:9pt; }
</style>
</head>
<body style="margin:10">
	Hostname : [<%= InetAddress.getLocalHost().getHostName() %>]<br>
</body>
</html>
