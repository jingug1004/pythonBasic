<%@ page language="java" import="java.util.*, java.sql.*" session="true" contentType="text/html;charset=EUC_KR" %>
<%@ page import="javax.naming.*, javax.sql.*" %>
<%@ include file="estmLib.jsp" %>
<%
    String empNo = (String)session.getAttribute("EMP_NO");
	String empNm = (String)session.getAttribute("EMP_NM");

	
    String evalYear = (String)session.getAttribute("ESTM_YEAR");
    String evalNum  = (String)session.getAttribute("ESTM_NUM");

    String deptCd = "";
    String deptNm = "";
   
    String loginEmpNo = "";
    String loginEmpNm = "";
    String loginDeptNm = "";
    String loginDeptCd= "";

    
    //공동근무자일경우 세션 변경 
    String sEstmDeptCd   = request.getParameter("estmDeptCd");  
    String sEstmDeptNm   =request.getParameter("estmDeptNm");
    
    if(sEstmDeptNm ==null) sEstmDeptNm="";
    sEstmDeptNm   = new String(sEstmDeptNm.getBytes("ISO8859-1"),"KSC5601");//공동근무자일경우 

    if(sEstmDeptCd != null && !sEstmDeptCd.equals("")){
        
        session.setAttribute("DEPT_CD",sEstmDeptCd);
        session.setAttribute("DEPT_NM",sEstmDeptNm);        
    }
    
    
    
    if(empNm == null || empNm.equals("")){
         out.println("<script >location.href='estmLogin.jsp';</script>");
    }
    
    
    
    
    deptCd = (String)session.getAttribute("DEPT_CD");
    deptNm = (String)session.getAttribute("DEPT_NM");
    
    
    
    String sEvalYear   = evalYear;
    //String sEvalNum    = "1";
    String sEvalNum    = evalNum;
	
	String grdNm ="";
	
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    

    try { 
    	Context ctx = new InitialContext();
    	if (ctx != null) { 
    		DataSource ds = null;
    		if (_TOMCAT) ds = (DataSource)ctx.lookup("java:/comp/env/jdbc/RBM");
    		else         ds = (DataSource)ctx.lookup("jdbc/RBM");
    		
    		if (ds != null) {
    			con = ds.getConnection();    			
    		}
    	}    
    } catch(SQLException e) {
        out.println(e.toString());
        e.printStackTrace();   
        return;
    } 
    
	
    String query ="";
    query ="";
    query += "   SELECT   \n";
    query += "       A.ESTM_YEAR   \n";
    query += "      ,A.ESTM_NUM   \n";
    query += "      ,A.DEPT_CD   \n";
    query += "      ,A.EMP_NO   \n";
    query += "      ,A.EMP_NM   \n";
    query += "      ,A.RES_NO   \n";
    query += "      ,A.ESTM_DEPT   \n";
    query += "      ,A.PWD_NO   \n";
    query += "      ,A.WK_JOB_CD   \n";
    query += "      ,A.APT_DT   \n";
    query += "      ,A.JOB_TIT_CD   \n";
    query += "      ,A.JOB_TIT_NM   \n";
    query += "      ,A.HP_NO   \n";
    query += "      ,B.DEPT_NM  \n";
    query += "   FROM TBRF_EV_EMP  A \n";
    query += "        ,TBRF_EV_DEPT B   \n";
    query += "   WHERE 1=1   \n";
    query += "     AND A.ESTM_YEAR = B.ESTM_YEAR   \n";
    query += "     AND A.ESTM_NUM = B.ESTM_NUM   \n";
    query += "     AND A.ESTM_DEPT = B.DEPT_CD   \n";
    
    query += "     AND A.EMP_NO= ?   \n";
    query += "     AND A.ESTM_YEAR = ?   \n";
    query += "     AND A.ESTM_NUM  =?   \n";
    
    query += "     AND A.WRK_GBN = '001'   \n"; 
   
    pstmt = con.prepareStatement(query);

  
    int i =1;
    pstmt.setString(i++, empNo );
    pstmt.setString(i++, sEvalYear );
    pstmt.setString(i++, sEvalNum);

    rs = pstmt.executeQuery();
    
    

    while (rs.next()) {
 
        
        loginEmpNo = rs.getString("EMP_NO"); 
        loginEmpNm = rs.getString("EMP_NM");
        loginDeptCd = rs.getString("DEPT_CD");
        loginDeptNm = rs.getString("DEPT_NM");
    }

    
    
    //로그  기록 
     query ="";
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
    pstmt.setString(i++, "estmResult.jsp");
    pstmt.setString(i++, empNo);
    
    
    pstmt.setString(i++,request.getRemoteAddr());
    
    pstmt.setString(i++, sEvalYear);
    pstmt.setString(i++, sEvalNum);
    pstmt.setString(i++, "estmResult.jsp");       
    
    pstmt.executeUpdate();
    
    
    
    //평가등급 조회 
    query ="";
    query += "   SELECT   \n";
    query += "        TOT_GRD  \n";
    query += "   FROM TBRF_EV_TOTAL_RSLT A   \n";
    query += "    WHERE  A.ESTM_YEAR = ?   \n";
    query += "           AND A.ESTM_NUM = ?   \n";
    query += "           AND A.EMP_NO =?   \n";

 
	  pstmt = con.prepareStatement(query);
	
	
	  i =1;
	
	  pstmt.setString(i++, sEvalYear);
	  pstmt.setString(i++, sEvalNum);
	  //pstmt.setString(i++, loginDeptCd);
	  pstmt.setString(i++, empNo);        
	  
	
	  rs = pstmt.executeQuery();
	  
	  if(rs.next()){
		  grdNm = rs.getString("TOT_GRD");
	  }

	  if(grdNm == null) grdNm = "";

     try {
          if(rs !=null) rs.close();
          if(pstmt != null) pstmt.close();
          if(con != null) con.close();
     } catch(SQLException e) {
       out.println(e.toString());
     } finally {
         if(con != null) try{con.close();}       catch(Exception e){}
         if(pstmt != null) try{pstmt.close();}   catch(SQLException sqle){}
         if(rs !=null) rs.close();
     }
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>지원직 및 일용직계약 평가시스템</title>
<meta http-equiv="Content-Type" content="text/html; charset=eu-kr" />
<link rel="stylesheet" href="css/default.css" type="text/css" />
<![If IE 7]>
<link rel="stylesheet" href="css/ie7.css" type="text/css" />
<![endif]>
<script src="zoom.js" type="text/javascript"></script>
<script language="JavaScript" type="text/JavaScript">
</script>
</head>

<body>
<form name="estmForm" method="post" action="">
	<div class="wrap">
		<div class="logo">			 
			<img src="img/logo.jpg" alt="KSPO 경주사업본부/지원직 및 일용계약직 평가시스템" />
            <p><a href="javascript:void(0)" onclick="zoomOut(); return false;" onfocus="blur()">
            <img src="img/btn_m.gif" alt="축소"/></a> <a href="javascript:void(0)" onclick="zoomIn(); return false;" onfocus="blur()">
            <img src="img/btn_p.gif" alt="확대"/></a> <a href="passChange.jsp"><img src="img/btn_pass.jpg" alt="비밀번호 변경" /></a> 
            <a href="estmLogOut.jsp"><img src="img/btn_logout.jpg" alt="로그아웃" /></a></p>
            
		</div>
		<div class="body">
			<div class="check_page">
				<img src="img/tit_check.jpg" alt="평가 진행 및 결과 등급 확인" />
				<div class="check">
					<ul><li style="margin-right:20px;"><strong>소속</strong> : <%=loginDeptNm %></li>
					    <li style="margin-right:20px;"><strong>사번</strong> : <%=empNo %></li>
					    <li><strong>성명</strong> : <%=empNm %></li>
					</ul>
					<div>
						<p>한해 수고 많으셨습니다.<br/>귀하의 <%=sEvalYear %>년도 근무실적 평가결과는 <strong><%=grdNm %>등급</strong> 입니다.</p>
					</div>
				</div>
				<p><a href="estmMain.jsp"><img src="img/btn_home.jpg" alt="첫 화면"/></a></p>
			</div><!-- //check_page -->
		</div><!-- //body -->
		<div class="copy">
		<p>Copyright (c) <strong>경륜경정사업본부</strong> All rights Reserved.</p>
		</div>
	</div><!-- //wrap -->
</form>
</body>
</html>
