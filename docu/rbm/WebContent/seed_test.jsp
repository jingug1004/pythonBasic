<%@ include file = "./seedcommon.jsp" %><%
%><%@ page language="java"%>
<%@ page import="java.io.*" %><% 
%><%@ page import="java.util.*" %><%
%><%@ page import="java.sql.*" %><% 
%><%@ page import="javax.sql.*" %><%
%><%@ page import="javax.naming.*" %><%
%><%@ page import="java.net.URLDecoder.*" %>
<%
%><%@ page contentType="text/html; charset=euc-kr"%><%

		//String text = "1234567890abcdefe1111111111111KJBabc";

String text = request.getParameter("SEND");


String key = "5900635100400000111";
		
String orgKey = "5900635100400000111";

StringBuilder trace = new StringBuilder();
trace.append("Plain Text :: [").append(text).append("]");
System.out.println(trace.toString());

//String encryptText = Base64.encode(encrypt(text, key.getBytes(), "UTF-8"));



byte[] encryptbytes = Base64.decode(text);
System.out.println(new String());
String decryptText  = decryptAsString(encryptbytes, key.getBytes(), "UTF-8");
   
trace = new StringBuilder();
trace.append("Decrypt Text :: [").append(decryptText).append("]");

out.println(decryptText);
%>	  
