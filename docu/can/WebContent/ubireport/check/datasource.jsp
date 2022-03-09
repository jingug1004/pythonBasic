<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import="java.sql.*, javax.sql.*, javax.naming.*" %>

<%

	String uri = request.getRequestURI();

	String type = request.getParameter("type");
	String dsNm = request.getParameter("dsNm");

	String driver = request.getParameter("driver");
	String url = request.getParameter("url");
	String user = request.getParameter("user");
	String password = request.getParameter("password");

	String sql = request.getParameter("sql");

	if( type == null || type.equalsIgnoreCase("null") ) type = "";
	if( dsNm == null || dsNm.equalsIgnoreCase("null") ) dsNm = "";

	if( driver == null || driver.equalsIgnoreCase("null") ) driver = "";
	if( url == null || url.equalsIgnoreCase("null") ) url = "";
	if( user == null || user.equalsIgnoreCase("null") ) user = "";
	if( password == null || password.equalsIgnoreCase("null") ) password = "";

	if( sql == null || sql.equalsIgnoreCase("null") ) sql = "";

	String msg = "";

	Connection conn = null;
	PreparedStatement ps = null;
	ResultSet rs = null;

%>


<html>
<head>
<title>Check DataSource</title>
<style>
	body  { font-family:sans-serif, arial; font-size:9pt; }
	table { font-family:sans-serif, arial; font-size:9pt; }
</style>
<script language="JavaScript">
<!--
	
	function setField() {

		var check = document.dbinfo.type.value;
		if( check == "direct" ) {

			document.all["nondirect"].style.visibility = "hidden";
			document.all["direct1"].style.visibility = "visible";
			document.all["direct2"].style.visibility = "visible";
			document.all["direct3"].style.visibility = "visible";
			document.all["direct4"].style.visibility = "visible";
		}
		else {

			document.all["nondirect"].style.visibility = "visible";
			document.all["direct1"].style.visibility = "hidden";
			document.all["direct2"].style.visibility = "hidden";
			document.all["direct3"].style.visibility = "hidden";
			document.all["direct4"].style.visibility = "hidden";
		}
	}

	function checkFiled() {

		if( document.dbinfo.type.value == "direct" ) {

			if(document.dbinfo.driver.value == "") {

				alert("Plaese Input Driver Name.(ex : oracle.jdbc.driver.OracleDriver)");
				document.dbinfo.driver.focus();
				return false;
			}
			else if(document.dbinfo.url.value == "") {

				alert("Please Input DB URL.(ex : jdbc:oracle:thin:@dbip:dbport:dbsid)");
				document.dbinfo.url.focus();
				return false;
			}
			else document.dbinfo.submit();
		}
		else {

			if(document.dbinfo.dsNm.value == "") {

				alert("Please Input DataSource Name.(ex : jdbc/demo)");
				document.dbinfo.dsNm.focus();
				return false;
			}
			else document.dbinfo.submit();
		}
	}

	function checkFiled2() {

		if( document.dbinfo.sql.value == "" ) {

			alert("Plaese Input SQL statment.");
			document.dbinfo.sql.focus();
			return false;
		}
		else {

			document.dbinfo.submit();
		}
	}

//-->
</script>
</head>
<body style="margin:10" onload="setField()">
	<h2>Check DataSource<h2>
	<form name="dbinfo" method="post" action="<%= uri %>">
	<table width="585" border="1" cellspacing="0" cellpadding="0" bordercolor="#808080" bordercolorlight="#C0C0C0" bordercolordark="#F6F6F6">
		<tr height="20">
			<td align="center" bgcolor="#C0C0C0" width="100">WAS Type</td>
			<td align="center" width="192">
				<select name="type" onchange="setField()">
					<option value="lookup" <%= type.equals("lookup")?"selected":"" %>>lookup</option>
					<option value="nonlookup" <%= type.equals("nonlookup")?"selected":"" %>>nonlookup</option>
					<option value="direct" <%= type.equals("direct")?"selected":"" %>>direct</option>
				</select>
			</td>
			<td align="center" bgcolor="#C0C0C0" width="100">DataSource</td>
			<td align="center" width="192">
				<div id="nondirect" style="visibility:">
				<input type="text" name="dsNm" size="20" value="<%= dsNm %>">
				</div>
			</td>
		</tr>
		<tr height="20">
			<td align="center" bgcolor="#C0C0C0" width="100">Driver</td>
			<td align="center">
				<div id="direct1" style="visibility:hidden">
					<input type="text" name="driver" size="20" value="<%= driver %>">
				</div>
			</td>
			<td align="center" bgcolor="#C0C0C0" width="100">URL</td>
			<td align="center">
				<div id="direct2" style="visibility:hidden">
					<input type="text" name="url" size="20" value="<%= url %>">
				</div>
			</td>
		</tr>
		<tr height="20">
			<td align="center" bgcolor="#C0C0C0" width="100">User</td>
			<td align="center">
				<div id="direct3" style="visibility:hidden">
					<input type="text" name="user" size="20" value="<%= user %>">
				</div>
			</td>
			<td align="center" bgcolor="#C0C0C0" width="100">Password</td>
			<td align="center">
				<div id="direct4" style="visibility:hidden">
					<input type="text" name="password" size="20" value="<%= password %>">
				</div>
			</td>
		</tr>
	</table>
	<table width="585" border="0" cellspacing="0" cellpadding="0" bordercolor="#808080" bordercolorlight="#C0C0C0" bordercolordark="#F6F6F6">
		<tr height="30">
			<td align="center">
				<input type="button" value="DB Connect Test" onclick="checkFiled()">
			</td>
		</tr>
	</table>

<%



	if( type.equals("lookup") || type.equals("nonlookup") || type.equals("direct") ) {

		try {

			if( type.equals("lookup") ) { //lookup type (Tomcat, resin, Jrun ...) 

				Context ctx = new InitialContext();
				Context env = (Context)ctx.lookup("java:comp/env");
				DataSource ds = (DataSource)env.lookup(dsNm);
				conn = ds.getConnection();
			}
			else if( type.equals("nonlookup") ) { //nonlookup type (Jeus, WebLogic, WebSphere ...)

				Context ctx = new InitialContext();
				DataSource ds = (DataSource)ctx.lookup(dsNm);
				conn = ds.getConnection();
			}
			else { //direct

				Class.forName(driver);
				conn = DriverManager.getConnection(url, user, password);
			}

			if( conn == null ) msg = "Connection Fail!!!";
			else msg = "Connection Success!!!";
		}
		catch(Exception e) {

			msg = e.getMessage();
		}
		finally { 

%>

	<table width="585" border="1" cellspacing="0" cellpadding="0" bordercolor="#808080" bordercolorlight="#C0C0C0" bordercolordark="#F6F6F6">
		<tr height="20">
			<td align="center" bgcolor="#C0C0C0">Result(<%= type %>)</td>
		</tr>
		<tr height="50">
			<td align="center" valign="middle"><%= msg %></td>
		</tr>
	</table>

<%

			if( conn != null ) {

%>

	<br>
	<table width="585" border="1" cellspacing="0" cellpadding="0" bordercolor="#808080" bordercolorlight="#C0C0C0" bordercolordark="#F6F6F6">
		<tr height="20">
			<td align="center" bgcolor="#C0C0C0">SQL</td>
		</tr>
		<tr height="50">
			<td>
			<textarea name="sql" rows="8" cols="80"><%= sql %></textarea>
			</td>
		</tr>
	</table>
	<table width="585" border="0" cellspacing="0" cellpadding="0" bordercolor="#808080" bordercolorlight="#C0C0C0" bordercolordark="#F6F6F6">
		<tr height="30">
			<td align="center">
				<input type="button" value="Data View" onclick="checkFiled2()">
			</td>
		</tr>
	</table>

<%

				if( sql != null && !sql.equals("") ) {

%>

	<table width="585" border="1" cellspacing="0" cellpadding="0" bordercolor="#808080" bordercolorlight="#C0C0C0" bordercolordark="#F6F6F6">


<%

					try {

						ps = conn.prepareStatement(sql);
						rs = ps.executeQuery();

						ResultSetMetaData rsmd = rs.getMetaData();
						int cnt = rsmd.getColumnCount();

						out.println("<tr>");
						for(int i = 0; i <= cnt; i++) {

							if( i == 0 ) out.println("<td bgcolor=\"#C0C0C0\" align=\"center\">No</td>");
							else out.println("<td bgcolor=\"#C0C0C0\" align=\"center\">" + rsmd.getColumnLabel(i) + "</td>");
						}

						int row_cnt = 0;
						while( rs.next() ) {

							out.println("<tr>");
							for(int i = 0; i <= cnt; i++) {

								if( i == 0 ) out.println("<td align=\"center\">" + ++row_cnt + "</td>");
								else out.println("<td align=\"center\">" + rs.getString(i) + "</td>");
							}
							out.println("</tr>");
						}

						if( row_cnt == 0 ) {

							out.println("<tr height=\"100\"><td align=\"center\" colspan=\"" + (cnt + 1) + "\">조회 내역이 없습니다.</td></tr>");
						}
					}
					catch(Exception ee) {

						out.println("<tr><td bgcolor=\"#C0C0C0\" align=\"center\">Error Message</td></tr>");
						out.println("<tr height=\"100\"><td align=\"left\" valign=\"top\">" + ee.getMessage() + "</td></tr>");
					}

%>

	</table>

<%

				}

				if( rs != null ) try{ rs.close(); rs = null; } catch (Exception ex) {}
				if( ps != null ) try{ ps.close(); ps = null; } catch (Exception ex) {}
				if( conn != null ) try { conn.close(); conn = null; } catch(Exception e) {}
			}
		}
	}

%>
	</form>

</body>
</html>
