	<%@ page language="java" import="java.util.*, java.sql.*" session="true" contentType="text/html;charset=EUC_KR" %>
	<HTML>
	<HEAD> 
	<META NAME="Generator" CONTENT="EditPlus">
	<META NAME="Author" CONTENT="">
	<META NAME="Keywords" CONTENT="">
	<META NAME="Description" CONTENT="">
	</HEAD>
	
	<BODY>
	<h1>MsSQL JDBC Test page</h1>
	<br>
	<table border=1 cellpadding=0 cellspacing=0>
	
	  
	<% 
	
	 Connection con = null;
	 Statement stmt = null;
	 ResultSet rs = null;
	
	// String jdbcUrl ="jdbc:microsoft:sqlserver://150.103.10.120:1433;DatabaseName=tososfo";
	 //String dbUser = "sosfo_trans";
	 //String dbPass = "kosicsosfo";
	 String jdbcUrl ="jdbc:sqlserver://160.100.12.56:1066;DatabaseName=solbipos"; 
	 String dbUser = "rbm";
	 String dbPass = "rbm1234";
	 
	   
	try { 
	    // Class.forName("com.microsoft.jdbcx.sqlserver.SQLServerDataSource");
	   // Class.forName("com.microsoft.jdbc.sqlserver.SQLServerDriver");
	     Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver"); 
	     con = DriverManager.getConnection(jdbcUrl, dbUser, dbPass);
	 } catch(SQLException e) {
	     out.println("MsSQL connection error! err_code="+e.getErrorCode()+"<br>"); 
	     out.println(e.toString());
	     e.printStackTrace();	
	     return;
	 }
	 
	
	 try {
	  stmt = con.createStatement();
	
	  String query = " select * from dbo.syscolumns ";
	  rs = stmt.executeQuery(query);
	
	  while(rs.next()) {
	   out.println("<tr>");
	   out.println("<td>" + rs.getString(1) + "</td>");
	   out.println("<td>" + rs.getString(2) + "</td>");
	   out.println("</tr>");
	  }
	 } catch(SQLException e ) {
	  out.println(e.toString());
	 }
	
	 
	
	 try {
	  if(rs !=null) rs.close();
	  if(stmt != null) stmt.close();
	  if(con != null) con.close();
	 } catch(SQLException e) {
	  out.println(e.toString());
	 }
	
	 
	
	%>
	
	
	</table>
	</BODY>
	</HTML>
	
