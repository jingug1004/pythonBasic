<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import="java.sql.*, javax.sql.*, javax.naming.*" %>
<%

	Connection conn = null;
	String name = "jdbc/BOADS";

	try
	{
		//lookup
		//Context ctx = new InitialContext();
		//Context env = (Context)ctx.lookup("java:comp/env");
		//DataSource ds = (DataSource)env.lookup(name);

		//nonlookup
		Context ctx = new InitialContext();
		DataSource ds = (DataSource)ctx.lookup(name);

		conn = ds.getConnection();

		if( conn == null ) out.println("Connection Fail! (" + name + ")");
		else out.println("Connection Success! (" + name + ")");
	}
	catch(Exception e)
	{ 
		out.println(e.getMessage()); 
	}
	finally
	{ 
		if( conn != null ) try { conn.close(); } catch(Exception e) {}
	}

%>
