<%@ page language="java" import="java.util.*, java.sql.*" session="true" contentType="text/html;charset=EUC_KR" %>
<%@ include file="comDBManager.jsp" %>

<%-- 사용자 로그인 확인 --%>

<%
    // 사용자 정보
	String strUserId        = (String)session.getAttribute("EMP_NO");

	// request 정보
	String strRsvYear = request.getParameter("rsvYear");
	String strRsvTms  = request.getParameter("rsvTms");
	String strRstId   = request.getParameter("rstId");
	String strRstSeq  = request.getParameter("rstSeq");
	String strMobile  = request.getParameter("mobile");
	
	String sEvalYear   = "2013";
	String sEvalNum    = "99";  
	
	// 디비접속 정보
	PreparedStatement pstmt=null;
	String message = "";
	int nDltCnt = 0;
	int i=1;
    try { 
        con = DriverManager.getConnection(jdbcUrl, dbUser, dbPass);
    } catch(SQLException e) {
        out.println(e.toString());
        e.printStackTrace();   
        return;
    }
    
    try {
        String query = "";
		query += "\n delete from tbrf_app_rst_detl ";
		query += "\n where 1=1                     ";
		query += "\n and user_id = ?               ";
		query += "\n and rsv_year = ?              ";
		query += "\n and rsv_tms = ?               ";

		pstmt=con.prepareStatement(query);
		pstmt.setString(i++, strUserId);
		pstmt.setString(i++, strRsvYear);
		pstmt.setString(i++, strRsvTms);
		
		nDltCnt = pstmt.executeUpdate();
		
		
		//로그  기록 
	      query ="";                
	      query += "   INSERT INTO TBRF_EV_OUT_LOG   ";
	      query += "     (  ";
	      query += "            ESTM_YEAR   ";
	      query += "           ,ESTM_NUM   ";
	      query += "           ,JSP_PAGE   ";
	      query += "           ,EMP_NO   ";      
	      query += "           ,REMOTE_IP   ";
	      query += "           ,CONN_TIME   ";
	      query += "           ,SEQ   ";      
	      query += "     ) ";
	      query += "    VALUES  ";
	      query += "       (  ";      
	      query += "            ?   ";
	      query += "           ,?   ";
	      query += "           ,?   ";
	      query += "           ,?   ";      
	      query += "           ,?   ";
	      query += "           ,SYSDATE   ";
	      query += "           ,(SELECT NVL(MAX(SEQ),0) + 1  FROM  TBRF_EV_OUT_LOG WHERE  ESTM_YEAR = ? AND ESTM_NUM = ? AND JSP_PAGE =? )   ";      
	      query += "      ) ";
	        
	      pstmt = con.prepareStatement(query);
	      
	       i =1;
	      
	      pstmt.setString(i++, sEvalYear);
	      pstmt.setString(i++, sEvalNum);
	      pstmt.setString(i++, "restDelete.jsp");
	      pstmt.setString(i++, strUserId);
	      pstmt.setString(i++, request.getRemoteAddr());
	      pstmt.setString(i++, sEvalYear);
	      pstmt.setString(i++, sEvalNum);
	      pstmt.setString(i++, "restDelete.jsp");       

	      pstmt.executeUpdate();
	      
	} catch(Exception e) { 
		e.printStackTrace();
		out.println(e);
		nDltCnt = 0;		
	} finally {
		if(pstmt != null) try{pstmt.close();} catch(SQLException sqle){}
	}

    if(nDltCnt > 0) {
	    message = "201";
    } else {	
	    message = "202";
    }	

	String sUrl="";
	if ("Y".equals(strMobile)) sUrl = "restMain_m.jsp";
	else                       sUrl = "restMain.jsp";
%>
<script>
	document.location.href ="<%=sUrl%>?message="+"<%=message%>";
</script>
