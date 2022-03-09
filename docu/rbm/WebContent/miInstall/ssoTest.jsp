<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ page import="xroads.XroadToWebMail,java.io.*,java.net.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>Insert title here</title>
</head>
<%
String lvToken2 = "";
XroadToWebMail bean = new XroadToWebMail();
String lvToken  = request.getParameter("SSOTOK");
String strUserIp = request.getRemoteAddr();
String strLoginId         = bean.GetID(lvToken,strUserIp); //아이디
String strLoginPassword   = bean.GetPWD(lvToken,strUserIp);
String message = "";
// http://localhost:8080/rbm/miInstall/ssoTest.jsp?SSOTOK=D49E0B839010FEAF0C37C2E400AE276097ACBEE01A34CFB9281278C1DCF67C27E8F463721C52309DB5E1516D09271D8F16BB82385474C877A2F5216F155A0A8BE550B3BF42959522F522B989643D6902

	 /* **
	  토큰 생성부분 
    	try
    	{
    		String userID = strLoginId;
    		String userPW = strLoginPassword;
    		
    		URL url = new URL( "http://jupiter.kspo.or.kr/UFWeb/public/getToken.jsp?a="+userID+"&c=001049076087126064122107");
    		//UFWeb/portal/xssoLogin.jsp 페이지 호출
    		//c=암호화된 비밀번호
    		
    		URLConnection con = url.openConnection(); 
    		con.setConnectTimeout(1000);
    		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream())); 
    		
    		String strLine;
    		while( (strLine = bufferedReader.readLine()) != null )
    		{
    			 
    		  	if ( !strLine.equals("") )
    		  		lvToken2 = strLine;
    		}
    		bufferedReader.close();
    	} catch (SocketTimeoutException se) { 
    		se.printStackTrace();
		} catch (Exception e) { 
			e.printStackTrace();
    		//throw new AppException("LoginServlet.getLvToken(), ", e);
    	} 
		*/
%>
<body>
		 strUserIp:<%= strUserIp%><br>
		 strLoginId:<%= strLoginId%><br>
		 strLoginPassword:<%= strLoginPassword%><br> 
		 lvToken:<%= lvToken%><br> 
		 lvToken2:<%= lvToken2%><br> 
</body>
</html>