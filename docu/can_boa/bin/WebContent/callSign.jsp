<%@ page import="java.io.*" contentType="text/html;charset=utf-8" %>
<%@ page import="SafeIdentity.*" %>


<%  	
  /*
  2014.12.10 jdlee
  핸디전자결재 연동
  
  */
String ssoToken = "";

String sRecvUser = "";
String sRecvUserPW = "";

String sErrMsg = "";

try
{
	request.setCharacterEncoding("utf-8"); 
	
	String formid	= request.getParameter("formid");

	String lvToken = "";	//올드토큰
	String sid  = request.getParameter("empcode");
	String spwd = request.getParameter("userpswd");
	String clientIP = request.getRemoteAddr();
	
	int nResult = -1;
	
	int apiCode  = 0;
	String apiMsg  = "";
	
	// SSO API 시작부분
	SSO	sso = new SSO("368B184727E89AB69FAF");  // 그대로 사용한다. 
	nResult = sso.regUserSession( sid, clientIP, true);

	
	if( nResult >= 0 )  // Logon Ok
	{		
	 
		sso.putValue("ID",sid);
	  sso.putValue("USERID","U00"+sid);		//sso.putValue("USERID","U0070541_0"); 이 방법도 상관없음.
		sso.putValue("PWD",spwd);
	
		//신규 토큰 생성
		String sessionToken = sso.getToken();	
		ssoToken = sso.makeToken(3, sessionToken, "GID_DEMO1", clientIP );
		
		
		if( ssoToken != null ) 
		{
			try {
			     sso.verifyToken(ssoToken,clientIP);
			     apiCode = sso.getLastError();
			     apiMsg = sso.getLastErrorMsg();
			
			     if(apiCode >= 0){//success
			         ///////////////////////////////////////////////////////////
			         //////////////////////// 인증 처리 로직 ////////////////
			         ///////////////////////////////////////////////////////////
			         //1.ID가져오기: sso.getValueUserID();
			         //2.비밀번호가져오기: sso.getValue("PWD");  => 암호화된 비밀번호 리턴
			         sRecvUser    = sso.getValueUserID(); //아이디;
	    				 sRecvUserPW  = sso.getValue("PWD");
			     } else {//failed
		         //오류 처리 로직
		         sErrMsg = "오류:"+ "apiCode<0";
		     }
			}
			catch (Exception e) {
			     //out.println("[verifySSOToken1]"+e.getMessage());
			     sErrMsg = "오류:"+ e.getMessage();
			}
		} 
	
	}
	else 
	{
		nResult = sso.getLastError();
		out.println("===오류:"+nResult);
	}
}
catch(Exception e)
{
	out.println("오류발생:"+e);
}
%>

<html>
<head>
	<script language='javascript'>

   // 페이지 이동
    function callSign()
    {
        var thisForm = document.forms[0];

        thisForm.submit();
    }

	</script>
</head>
<BODY onLoad='callSign();'>

<form action="http://jupiter.kspo.or.kr/jsp/openapi/sso.jsp" method="post" target="_blank">
	<INPUT TYPE='hidden' NAME='target'     		VALUE='giancall'>
	<INPUT TYPE='hidden' NAME='SSOTOK'     		VALUE='<%=ssoToken%>'>
	<INPUT TYPE='hidden' NAME='formid'    		VALUE='00000018i'>
	<INPUT TYPE='hidden' NAME='empcode'     	VALUE='<%=request.getParameter("empcode")%>'> <!--사번 U00 -->
	<INPUT TYPE='hidden' NAME='deptcode'     	VALUE='<%=request.getParameter("deptcode")%>'>
	<INPUT TYPE='hidden' NAME='miskey'     		VALUE='<%=request.getParameter("miskey")%>'>
	<INPUT TYPE='hidden' NAME='title'     		VALUE='<%=request.getParameter("title")%>'>       <!--제목-->
	<INPUT TYPE='hidden' NAME='fld_value'     VALUE='<%=request.getParameter("fld_value")%>'>  <!--본문-->
	<INPUT TYPE='hidden' NAME='JOB'     			VALUE='<%=request.getParameter("job")%>'>
	<INPUT TYPE='hidden' NAME='creatType'     VALUE='<%=request.getParameter("creatType")%>'>
	<INPUT TYPE='hidden' NAME='seq'     			VALUE='<%=request.getParameter("SEQ")%>'>
	<%
	if( request.getParameter("fld_attach") != null )
	{
	%>
	<INPUT TYPE='hidden' NAME='fld_attach'  	VALUE='<%=request.getParameter("fld_attach")%>'>
	<%
	}
	%>
</form>
 
</body>
</html>