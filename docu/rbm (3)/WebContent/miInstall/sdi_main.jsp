<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<%@ page import="xroads.XroadToWebMail,java.io.*" %>
<%  	
	XroadToWebMail bean = new XroadToWebMail();

    String callToken   = request.getParameter("CALLTOK");
    if(callToken == null) callToken="";



	String lvToken  	= request.getParameter("SSOTOK");
	if(lvToken == null ) lvToken="";
	
   String strUserIp    = request.getRemoteAddr();
   String strLoginId   ="";//아이디
   String strLoginPassword   = "";
   String strPswdEnc   = "N";
	if ("".equals(callToken) && "".equals(lvToken))
	{
		strLoginId = request.getParameter("USER_ID");
		strLoginPassword = request.getParameter("PSWD");
		strPswdEnc   = "N";
		
	} else if(!lvToken.equals("")){
	    strLoginId   = bean.GetID(lvToken,strUserIp); //아이디
	    strLoginPassword   = bean.GetPWD(lvToken,strUserIp);
	    strPswdEnc   = "Y";
	}

	
	// http://localhost:8080/rbm/miInstall/sdi_main.jsp?SSOTOK=4241E10BEAB371C7389E9D5461BF428839679D0CEBCE9CCFAC9BDCE7E219FBCB5CA43006B7E2DA2EBA323DB63086752C26C1AB71527A2C9BC9CDFB850D4AA9E4E57E321259F68A130EB67F5ADD4C3BD7
			// http://localhost:8080/rbm/miInstall/sdi_main.jsp?CALLTOK=74221
    //String str_GBL_WEB_ID			= "74221";
    //String str_GBL_CERTIFIES_KEY 	= "seh_74221";
    //String strLoginId = "74221";
    //String strLoginPassword = "seh_74221";
%>


<HTML>
<HEAD>
<TITLE></TITLE>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="./image/style.css" type="text/css">

<SCRIPT LANGUAGE="JavaScript" src="http://localhost:8080/rbm/script/MiInstaller320.js"></SCRIPT>

<SCRIPT  LANGUAGE="JavaScript" type="text/JavaScript">
<!--

    function fn_Load() {

        MiPlatformCtrl.Key              = "RBM_WEB";
        MiPlatformCtrl.ConnectRetry     = 0;
        MiPlatformCtrl.WaitTimeInterval = 300; // second
        MiPlatformCtrl.autosize         = false;
        MiPlatformCtrl.StartXml         = "http://localhost:8080/rbm/rbm_ui/rbm_ci_main_sdi.xml";
        MiPlatformCtrl.InitUrl          = "common::comSdiMain.xml";
        MiPlatformCtrl.DoRun();
    }

    function fn_UnLoad() {
        
        //var rtn = document.all.namedItem("MiPlatformCtrl");
        //rtn.Exit();
    }

//-->
</SCRIPT>

<SCRIPT LANGUAGE=JavaScript FOR=MiPlatformCtrl EVENT=PlatformError(errorcode, errormsg)>
</SCRIPT>

<SCRIPT LANGUAGE=JavaScript FOR=MiPlatformCtrl EVENT=BeforeNavigate(disp,strReqID,strServiceID,strUrl)>
</SCRIPT>

<SCRIPT language=JavaScript FOR=MiPlatformCtrl EVENT=NavigateComplete(pDisp,strReqId,strServiceID,strUrl)>
</SCRIPT>

<SCRIPT LANGUAGE=JavaScript FOR=MiPlatformCtrl EVENT=LoadCompleted(disp,strReqID,strServiceID,strUrl)>
{
    MiPlatformCtrl.SetGlobalVariableValue("GBL_WEB_ID"	,"<%=strLoginId%>" );		
	MiPlatformCtrl.SetGlobalVariableValue("GBL_PWD"		,"<%=strLoginPassword%>" );	
	MiPlatformCtrl.SetGlobalVariableValue("GBL_CALL_ID" ,"<%=callToken%>" );
	MiPlatformCtrl.SetGlobalVariableValue("GBL_PWD_ENC" ,"<%=strPswdEnc%>" );
	
}
</SCRIPT>

<SCRIPT LANGUAGE=JavaScript FOR=MiPlatformCtrl EVENT=ExitApplication()>
{
    // self.close();
}
</SCRIPT>

<SCRIPT LANGUAGE=JavaScript FOR=MiPlatformCtrl EVENT=UserNotify(id,msg)>
{
    if( id == 100 ) {

       window.location = "./ssoerror.jsp";

    }
}
</SCRIPT>

</HEAD>

<BODY leftmargin=0 topmargin=0 scroll="no" onload="fn_Load();" onunload="fn_UnLoad();">

<SCRIPT LANGUAGE="JavaScript">
   document.write('<OBJECT ID="MiPlatformCtrl" border="0" top="0" left="0" width="100%" height="100%" visible="true" ' +
                   '     CLASSID="CLSID:761C6511-03CE-4B78-ACD8-645CEF3CB714"> ' +
                   ' </OBJECT> ');
</SCRIPT>

</BODY>
</HTML>