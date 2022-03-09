<%@ page language="java" import="java.util.*, java.sql.*" session="true" contentType="text/html;charset=EUC_KR" %>
<%@ page import="javax.naming.*, javax.sql.*" %>
<%@ include file="comDBManager.jsp" %>

<%-- 사용자 로그인 확인 --%>

<%
	boolean _DEBUG = false;

    // 사용자 정보
	String strUserId        = (String)session.getAttribute("EMP_NO");
	String strUserName      = (String)session.getAttribute("EMP_NM");
	String strUserGroupId   = (String)session.getAttribute("DEPT_CD");
	String strUserGroupName = (String)session.getAttribute("DEPT_NM");

	// request 정보
	String strRsvYear = request.getParameter("rsvYear");
	String strRsvTms  = request.getParameter("rsvTms");
	String strRstId   = request.getParameter("rstId");
	String strRstSeq  = request.getParameter("rstSeq");
	String strMobile  = request.getParameter("mobile");
	
	String sEvalYear   = "2013";
	String sEvalNum    = "99";  
	
	// 디비접속 정보
    PreparedStatement pstmt = null;
    int nUpdtCnt = 0;
    
    try { 
        con = DriverManager.getConnection(jdbcUrl, dbUser, dbPass);
    	Context ctx = new InitialContext();
    	if (ctx != null) {
    		DataSource ds = (DataSource)ctx.lookup("java:/comp/env/jdbc/RBM");
    		if (ds != null) {
    			con = ds.getConnection();    			
    		}
    	}    	
        
    } catch(SQLException e) {
        out.println(e.toString());
        e.printStackTrace();   
        return;
    }
    
    try {
         String query = "";
         
         query  ="";
	  	 query +="\n merge into tbrf_app_rst_detl dst       ";                      
	  	 query +="\n 	using (                             ";               
	  	 query +="\n 		select                          ";
	  	 query +="\n 			? as user_id,               ";
	  	 query +="\n 			? as user_name,             ";
	  	 query +="\n 			? as dept_cd,               ";
	  	 query +="\n 			? as dept_name,             ";
	  	 query +="\n 			? as rsv_year,              ";
	  	 query +="\n 			? as rsv_tms,               ";
	  	 query +="\n 			? as rst_id,                ";
	  	 query +="\n 			? as rst_seq                ";
	  	 query +="\n 		from dual                       ";
	  	 query +="\n 	) src                               ";               
	  	 query +="\n 	on (                                ";               
	  	 query +="\n 	  dst.user_id = src.user_id       	";               
	  	 query +="\n 	  and dst.rsv_year = src.rsv_year 	";               
	  	 query +="\n 	  and dst.rsv_tms = src.rsv_tms   	";               
	  	 query +="\n 	)                                         	";               
	  	 query +="\n when matched then                           	";                 
	  	 query +="\n 	update set dst.rst_id = ?               ";
	  	 query +="\n 	          ,dst.rst_seq = ?              ";
	  	 query +="\n 	          ,dst.reg_dt = sysdate         ";
	  	 query +="\n when not matched then                                        	";
	  	 query +="\n 	insert (                                                   	";
	  	 query +="\n 		dst.user_id, dst.user_name, dst.dept_cd,    ";
	  	 query +="\n 		dst.dept_name, dst.rsv_year, dst.rsv_tms,   ";
	  	 query +="\n 		dst.rst_id, dst.rst_seq, dst.reg_dt         ";
	  	 query +="\n 	)	values                                                  ";
	  	 query +="\n 	(                                                          	";
	  	 query +="\n 		src.user_id, src.user_name, src.dept_cd,    ";
	  	 query +="\n 		src.dept_name, src.rsv_year, src.rsv_tms,   ";
	  	 query +="\n 		src.rst_id, src.rst_seq, sysdate)   			";

         pstmt = con.prepareStatement(query);
            
		 int i=1;
		 pstmt.setString(i++, strUserId);
		 pstmt.setString(i++, strUserName);
		 pstmt.setString(i++, strUserGroupId);
		 pstmt.setString(i++, strUserGroupName);
		 pstmt.setString(i++, strRsvYear);
		 pstmt.setString(i++, strRsvTms);
		 pstmt.setString(i++, strRstId);
		 pstmt.setString(i++, strRstSeq);
		 pstmt.setString(i++, strRstId);
		 pstmt.setString(i++, strRstSeq);
		
		 if (_DEBUG) {
            System.out.println("#####strUserId:"+strUserId);		
            System.out.println("#####strUserName:"+strUserName);
            System.out.println("#####strUserGroupId:"+strUserGroupId);
            System.out.println("#####strUserGroupName:"+strUserGroupName);
            System.out.println("#####strRsvYear:"+strRsvYear);
            System.out.println("#####strRsvTms:"+strRsvTms);
            System.out.println("#####strRstId:"+strRstId);
            System.out.println("#####strRstSeq:"+strRstSeq);
            System.out.println("#####strRstId:"+strRstId);
            System.out.println("#####strRstSeq:"+strRstSeq);
		 }

		 nUpdtCnt = pstmt.executeUpdate();

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
	      pstmt.setString(i++, "restRegister.jsp");
	      pstmt.setString(i++, strUserId);
	      pstmt.setString(i++, request.getRemoteAddr());
	      pstmt.setString(i++, sEvalYear);
	      pstmt.setString(i++, sEvalNum);
	      pstmt.setString(i++, "restRegister.jsp");       

	      pstmt.executeUpdate();
		      		 
		 
	} catch(Exception e) { 
		e.printStackTrace();
		out.println(e);
	} finally {
		if(con != null) try{con.close();}	catch(Exception e){}
		if(pstmt != null) try{pstmt.close();}	catch(SQLException sqle){}
	}
	String message = "";
	if(nUpdtCnt > 0) {
		message = "101";
	} else {	
		message = "102";
	}	

	String sUrl="";
	if ("Y".equals(strMobile)) sUrl = "restMain_m.jsp";
	else                       sUrl = "restMain.jsp";
%>
<script>
	document.location.href ="<%=sUrl%>?message="+"<%=message%>";
</script>
