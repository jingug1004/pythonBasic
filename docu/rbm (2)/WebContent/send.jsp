<%@ page language="java" contentType="text/html; charset=EUC-KR" pageEncoding="EUC-KR"%>
<%@ page import="java.sql.*" %>
<%@ page import="javax.sql.*" %>
<%@ page import="javax.naming.*" %>
<%@ page import="xroads.XroadToWebMail,java.io.*,java.net.*" %>
<%
	String sSendUser = request.getParameter("SENDUSER");
	String sRecvUser = request.getParameter("RECVUSER");
	String sTitle    = new String(request.getParameter("TITLE").getBytes("ISO8859-1"),"KSC5601");
	String sDesc     = new String(request.getParameter("DESC").getBytes("ISO8859-1"),"KSC5601");
	String sLinkUrl  = new String(request.getParameter("LINKURL").getBytes("ISO8859-1"),"KSC5601");
 

	
%>

<html>
<script type="text/javascript">
function send()
{
	form1.submit();
}
</script>
<body onload='send()'>
<form name='form1' method=post action='http://140.101.1.120/messenger/ConNotify.asp'>
<input type="text" name="MSG_ID"      	value= '1'>
<input type="text" name="SYS_NAME" 		value= '경주사업관리'>
<input type="text" name="MSG_CODE"  	value= 'NOTICE'>
<input type="text" name="SND_USER"   	value= 'b0015'>
<input type="text" name="RCV_USER"  	value= 'b0015'>
<input type="text" name="DOC_NAME"  	value= '<%=sTitle%>'>
<input type="text" name="DOC_URL"     	value= '<%= sLinkUrl %>' >
<input type="text" name="DOC_DESC"   	value= '<%=sDesc%>'>
<input type="text" name="ALARM_TYPE"    value= ''>
</form>
</body>
</html>