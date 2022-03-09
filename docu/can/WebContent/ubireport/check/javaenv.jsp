<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<title>Check Java Environment</title>
<style>
body {
	font-size:10pt;
	font-family: Arial;
	color: #000000;
	line-height:18px;
}
</style>
<body>

<%

	java.util.Enumeration e = System.getProperties().propertyNames(); 
	while(e.hasMoreElements()) {
	
		String key = (String)e.nextElement();
		if( key.indexOf("class.path") != -1 || key.indexOf("loader") != -1 ) out.print("<li><font color='red'>" + key + " : " + System.getProperty(key) + "</font></li>");
		else out.print("<li>" + key + " : " + System.getProperty(key) + "<br>");
	}
%>

</body>
</html>