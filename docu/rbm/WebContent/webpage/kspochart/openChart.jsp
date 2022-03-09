<%@ page language="java" contentType="text/html; charset=EUC-KR" pageEncoding="EUC-KR"%>
<%@ page import="java.util.*, java.sql.*,java.lang.*,javax.sql.*,javax.naming.*,java.io.*,java.net.*" session="true"%>
<%@ include file="/webpage/kspochart/util.jsp" %>
<% 
//클라이언트에서 넘겨주는 파라미터
String sMeetCd    = request.getParameter("MEET_CD");
String sRaceDay   = request.getParameter("RACE_DAY");
String sServiceNm = request.getParameter("SERVICE");

//웹서버 호출할 때 필요한 설정 세팅
//String sURL = "http://rbm.kcycle.or.kr";	//운영
String sURL = "http://localhost:19080/rbm";	//운영
String sProgram = "";		//호출하는 프로그램
String sParam = "";			//프로그램 호출할 때 넘겨주는 파라미터
String sWinOptions = "";	//Window를 열 때 설정하는 옵션

if("rsm2100".equals(sServiceNm)) { //매출조회 > 매출현황 > 매출비교그래프
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