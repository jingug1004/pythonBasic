<%@ page language="java" contentType="text/html;charset=EUC-KR" pageEncoding="EUC-KR" %>
<%@ page import="kspo.util.*,java.net.*" %>

<%

    KspoSms kspoSms = new KspoSms();
	try
	{
    	
		
	    /*
	    kspoSms.sendSms(strSysGubun,strSendPhoneNumber,strSendUserId,strSendUserName,strReceivePhoneNumber,strReceiveName,strSendSubject)
	    
	    private static String strSysGubun = "";			// �ý��۱���(10�ڸ�����)  (��:ECS)
	    
	    private static String strSendPhoneNumber = "";		// �۽��� ��ȣ
	    private static String strSendUserId = "";			// �۽��� ���			(��:05073)
	    private static String strSendUserName = "";		// �۽��� ����(��:ȫ�浿)	
	    private static String strReceivePhoneNumber = "";	// ������ ��ȣ, �������ΰ�� ; �� ���� (��:01020717775)
	    private static String strReceiveName = "";			// �����ڸ�, �������ΰ�� ; �� ����
	    private static String strSendSubject = "";			// ������ ����. (80Bytes����)
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
		 //strResult = kspoSms.sendSms("RBM","01025748525","05073","�߽���","01025748525","������",strSendSubject);
		 
	     // �׽�Ʈ ������ �ּ� Ǯ���ּ��� 
	     //strResult = kspoSms.sendSms(strSysGubun,strSendPhoneNumber,strSendUserId,strSendUserName,strReceivePhoneNumber,strReceiveName,strSendSubject);
    
    }
    catch( Exception e ) 
    { 
    	System.out.println("e"+e);
		out.println("�����߻�:"+e);
    }
%>