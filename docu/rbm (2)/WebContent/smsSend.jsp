<%@ page language="java" contentType="text/html;charset=EUC-KR" pageEncoding="EUC-KR" %>
<%@ page import="kspo.util.*,java.net.*" %>

<%

    KspoSms kspoSms = new KspoSms();
	try
	{
    	
		
	    /*
	    kspoSms.sendSms(strSysGubun,strSendPhoneNumber,strSendUserId,strSendUserName,strReceivePhoneNumber,strReceiveName,strSendSubject)
	    
	    private static String strSysGubun = "";			// 시스템구분(10자리이하)  (예:ECS)
	    
	    private static String strSendPhoneNumber = "";		// 송신자 번호
	    private static String strSendUserId = "";			// 송신자 사번			(예:05073)
	    private static String strSendUserName = "";		// 송신자 성명(예:홍길동)	
	    private static String strReceivePhoneNumber = "";	// 수신자 번호, 여러명인경우 ; 로 구분 (예:01020717775)
	    private static String strReceiveName = "";			// 수신자명, 여러명인경우 ; 로 구분
	    private static String strSendSubject = "";			// 전송할 문자. (80Bytes이하)
		*/
		
		
		String strSysGubun            = "RBM";
		String strSendPhoneNumber     = request.getParameter("strSendPhoneNumber");
		if(strSendPhoneNumber == null) strSendPhoneNumber ="";
		
		String strSendUserId          = request.getParameter("strSendUserId");
		if(strSendUserId == null) strSendUserId ="";
		
		String strSendUserName        = request.getParameter("strSendUserName");
		if(strSendUserName == null) strSendUserName= "";
		strSendUserName               = new String(strSendUserName.getBytes("8859_1"),"EUC-KR");
		
		String strReceivePhoneNumber  = request.getParameter("strReceivePhoneNumber");
		if(strReceivePhoneNumber == null) strReceivePhoneNumber= "";
		
		String strReceiveName         = request.getParameter("strReceiveName");
	    if(strReceiveName == null) strReceiveName= "";
	    strReceiveName        = new String(strReceiveName.getBytes("8859_1"),"EUC-KR");
	        
	        
		String strSendSubject         = request.getParameter("strSendSubject");
        if(strSendSubject == null) strSendSubject= "";
        strSendSubject                = new String(strSendSubject.getBytes("8859_1"),"EUC-KR");
		
		
	    
    	 String strResult = "";
		 //strResult = kspoSms.sendSms("RBM","01025748525","05073","발신자","01025748525","수신자",strSendSubject);
		 
	     // 테스트 끝나면 주석 풀어주세요 
	     //strResult = kspoSms.sendSms(strSysGubun,strSendPhoneNumber,strSendUserId,strSendUserName,strReceivePhoneNumber,strReceiveName,strSendSubject);
    
    }
    catch( Exception e ) 
    { 
    	System.out.println("e"+e);
		out.println("오류발생:"+e);
    }
%>