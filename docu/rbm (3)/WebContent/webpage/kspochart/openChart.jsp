<%@ page language="java" contentType="text/html; charset=EUC-KR" pageEncoding="EUC-KR"%>
<%@ page import="java.util.*, java.sql.*,java.lang.*,javax.sql.*,javax.naming.*,java.io.*,java.net.*" session="true"%>
<%@ include file="/webpage/kspochart/util.jsp" %>
<% 
//Ŭ���̾�Ʈ���� �Ѱ��ִ� �Ķ����
String sMeetCd    = request.getParameter("MEET_CD");
String sRaceDay   = request.getParameter("RACE_DAY");
String sServiceNm = request.getParameter("SERVICE");

//������ ȣ���� �� �ʿ��� ���� ����
//String sURL = "http://rbm.kcycle.or.kr";	//�
String sURL = "http://localhost:19080/rbm";	//�
String sProgram = "";		//ȣ���ϴ� ���α׷�
String sParam = "";			//���α׷� ȣ���� �� �Ѱ��ִ� �Ķ����
String sWinOptions = "";	//Window�� �� �� �����ϴ� �ɼ�

if("rsm2100".equals(sServiceNm)) { //������ȸ > ������Ȳ > ����񱳱׷���
	sProgram = "/webpage/kspochart/rsm2100/chart.jsp";
	sParam = "?MEET_CD="+sMeetCd+"&RACE_DAY="+sRaceDay;
	sWinOptions = "fullscreen";
}
%>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script>
function openChartWindow() {
	var winid = window.open("<%=sURL+sProgram+sParam%>","","<%=sWinOptions%>");
}
</script>
</head>
<body onload="javascript:openChartWindow();">
</body>
</html>