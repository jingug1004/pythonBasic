<%@ page import = "java.sql.*" %>
<%@ page language="java" contentType="text/html; charset=EUC-KR" pageEncoding="EUC-KR"%>

<%
	String sGubunId = request.getParameter("GUBUNID");
	String sRtnFlag = request.getParameter("RTNFLAG");
	String sSignDt 	= request.getParameter("SIGNDT");
	String sDocNo 	= request.getParameter("DOCNO");
	String sSeq 	= request.getParameter("SEQ");
	String sSubject = new String(request.getParameter("SUBJECT").getBytes("ISO8859-1"),"KSC5601");
	
	System.out.println("====================���ڰ��� �۽� �Ķ���� START==========================");
	System.out.println("1.�Ϸù�ȣ >>>>>>>" + sGubunId);
	System.out.println("2.���翩�� >>>>>>>" + sRtnFlag);
	System.out.println("3.�������� >>>>>>>" + sSignDt);
	System.out.println("4.������ȣ >>>>>>>" + sDocNo);
	System.out.println("5.�������� >>>>>>>" + sGubunId.substring(0,2));
	System.out.println("6.���س⵵ >>>>>>>" + sGubunId.substring(2,6));
	System.out.println("7.ȸ�� >>>>>>>" 	  + sGubunId.substring(6,8));
	System.out.println("8.���� >>>>>>>"    + sGubunId.substring(8,9));
	System.out.println("9.���ֹ�ȣ >>>>>>>" + sGubunId.substring(9,11));
	System.out.println("10.����� >>>>>>>" + sGubunId.substring(11,16));
	System.out.println("====================���ڰ��� �۽� �Ķ���� END============================");
	
%>
<%
/******* JDBC Connection *******/
Connection conn = null;
Statement  stmt = null;
Class.forName("oracle.jdbc.driver.OracleDriver");
conn = DriverManager.getConnection("jdbc:oracle:thin:@160.100.12.159:1521:ora10","us_rbm","us_rbm");	//���߼���
//conn = DriverManager.getConnection("jdbc:oracle:thin:@160.100.51.229:1521:ora10","us_rbm","us_rbm");  //�ǿ����
stmt = conn.createStatement();

try {
	/******* SQL ���� *************/
	String SQL;
	
	if(sRtnFlag.equals("G")){		//=====>���ó��
		SQL="INSERT INTO TBJZ_APPROVAL ( " +
		"       GUBUNID, JOB, STND_YEAR, " +
		"       TMS, DAY_ORD, RACE_NO, " +
		"       SEQ, REPORT_ID, SUBJECT, " +
		"       RTNFLAG, " +
		"       INST_ID, " +
		"       INST_DT, " +
		"       UPDT_ID, " +
		"       UPDT_DT " +				       
		") VALUES ('" + sGubunId + "', '" + sGubunId.substring(0,2) + "', '" + sGubunId.substring(2,6) + "', " +
		"          '" + sGubunId.substring(6,8) + "', '" + sGubunId.substring(8,9) + "', '" + sGubunId.substring(9,11) + "', " +
		"          '" + sSeq + "', '" + sGubunId.substring(11,16) + "', '" + sSubject + "', " +
		"          '" + sRtnFlag + "', " +
		"          '" + sGubunId.substring(11,16) + "', " +
		"          SYSDATE, " +
		"          '" + sGubunId.substring(11,16) + "', " +
		"          SYSDATE) ";
	}else{							//=====>����/�ݷ�ó��
		SQL="UPDATE TBJZ_APPROVAL " +
		"   SET RTNFLAG = '" + sRtnFlag + "' " +
		"   ,   SIGN_DT	= '" + sSignDt + "' " +
		//"   ,   DOC_NO	= '" + sDocNo + "' " +
		"	,	UPDT_DT = SYSDATE " +
		" WHERE GUBUNID = '" + sGubunId + "' "; 
	}
	
	stmt.executeUpdate(SQL);

}
	/********* Erroró�� ************/
catch(SQLException e) {
	System.out.println("SQLException >>>>>>>" + e.getMessage());
}
%>
<%
/******** JDBC Close *******/
if ( stmt != null ) try { stmt.close(); } catch (Exception e) {}
if ( conn != null ) try { conn.close(); } catch (Exception e) {}
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>���ֻ������ �۽� URL</title>
</head>
<script type="text/javascript">
</script>
<body>
<h1>>>>>>>>> ���ֻ������ ������ �۽���!!!!!!!!!!!!!!!!!!!!!</h1>
</body>
</html>
<script type="text/javascript">
	self.close();
</script>