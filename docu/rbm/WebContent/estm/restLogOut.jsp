<%@ page language="java" import="java.util.*, java.sql.*, snis.rbm.common.util.*" session="true" contentType="text/html;charset=EUC_KR" %>

<%

          
		  session.setAttribute("EMP_NO",null);
		  session.setAttribute("EMP_NM",null);
		  session.setAttribute("DEPT_CD",null);
		  session.setAttribute("DEPT_NM",null);
		  
          
          //session.invalidate();
          
          out.println("<script >alert('�α׾ƿ� �Ǿ����ϴ�!!');</script>");
	      out.println("<script >location.href='restLogin.jsp';</script>");
  
   
%>


