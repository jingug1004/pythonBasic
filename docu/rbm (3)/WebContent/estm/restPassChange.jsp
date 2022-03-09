<%@ page language="java" import="java.util.*, java.sql.*" session="true" contentType="text/html;charset=EUC_KR" %>
<%@ page import="javax.naming.*, javax.sql.*" %>
<%@ include file="comDBManager.jsp" %>
<%
    String userId = request.getParameter("userId");

    String sFirstYn = "Y";
    if(userId == null || userId.equals("")){
    	userId = (String)session.getAttribute("EMP_NO");
    	
    	sFirstYn = "N";
    }

	String sEvalYear   = "2013";
	String sEvalNum    = "99";  

  
	
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    
    
    String loginEmpNo = "";
    String loginEmpNm = "";
    String loginDeptNm = "";
    String loginDeptCd= "";

    
    
	try { 
//	    con = DriverManager.getConnection(jdbcUrl, dbUser, dbPass);
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
    
	    
      //조회 
      String query = "";
       
      query ="";
      query += "  SELECT            ";
      query += "         A.EMP_NO     ";
      query += "        ,A.EMP_NM     ";
      query += "        ,A.RES_NO     ";
      query += "        ,A.DEPT_CD    ";
      query += "        ,A.DEPT_NM    ";
      query += "   FROM USHUM.VW_EMP_D12@VENUSLINK  A ";
      query += "   WHERE 1=1                          ";
      query += "     AND A.HOLOFF_CLS = '0'           ";   //재직
      query += "     AND A.EMP_NO= ?                  ";
      //query += "     AND A.WK_JJOB IN ('1003', '1005', '1006') ";
     
      pstmt = con.prepareStatement(query);

    
      int i =1;
      pstmt.setString(i++, userId );

      rs = pstmt.executeQuery();
      
      

      while (rs.next()) {
          
          loginEmpNo = rs.getString("EMP_NO"); 
          loginEmpNm = rs.getString("EMP_NM");
          loginDeptCd = rs.getString("DEPT_CD");
          loginDeptNm = rs.getString("DEPT_NM");

      }
  

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
      pstmt.setString(i++, "restPassChange.jsp");
      pstmt.setString(i++, userId);
      pstmt.setString(i++,request.getRemoteAddr());
      pstmt.setString(i++, sEvalYear);
      pstmt.setString(i++, sEvalNum);
      pstmt.setString(i++, "restPassChange.jsp");       

      pstmt.executeUpdate();
	      
	 }catch(SQLException e) {
	          out.println(e.toString());
	 }        
	      
 

	 try {
	      if(rs !=null) rs.close();
	      if(pstmt != null) pstmt.close();
	      if(con != null) con.close();
	 } catch(SQLException e) {
	   out.println(e.toString());
	 }
 
 
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title> 2013년도 경륜경정사업본부 하계휴양소 신청화면</title>
<meta http-equiv="Content-Type" content="text/html; charset=eu-kr" />
<link rel="stylesheet" href="css/default.css" type="text/css" />
<link rel="stylesheet" href="css/restmain.css" type="text/css" />
<link rel="stylesheet" href="css/testtable.css" type="text/css" />
<![If IE 7]>
<link rel="stylesheet" href="css/ie7.css" type="text/css" />
<![endif]>
<script src="zoom.js" type="text/javascript"></script>
<script language="JavaScript" type="text/JavaScript">

  function formInit(){
      estmForm.oldPswd.focus();  
  }
  
  function savePass(){
  
   if(estmForm.oldPswd.value == "null" ||  estmForm.oldPswd.value == ""){
        alert("기존 비밀번호를 입력하여 주세요");
        estmForm.oldPswd.focus();
        return;        
    }

    if(estmForm.newPswd.value == "null" ||  estmForm.newPswd.value == ""){
        alert("변경 비밀번호를 입력하여 주세요");
        estmForm.newPswd.focus();
        return;        
    }

    if(estmForm.cfmPswd.value == "null" ||  estmForm.cfmPswd.value == ""){
        alert("확인 비밀번호 입력하여 주세요");
        estmForm.cfmPswd.focus();
        return;        
    }
      
      
     if(estmForm.oldPswd.value  == estmForm.newPswd.value){
        alert("기존비밀번호와  변경 비밀번호가 같습니다. ");
        estmForm.newPswd.focus();
        return;        
    }
     if(estmForm.newPswd.value  != estmForm.cfmPswd.value){
        alert("변경비밀번호와  비밀번호 확인이 다릅니다. ");
        estmForm.cfmPswd.focus();
        return;        
    }
      
       estmForm.action="restPassChangeSave.jsp";
       estmForm.submit();
  
  }
  
  
  function editClear(){
        estmForm.oldPswd.value ="";
        estmForm.newPswd.value ="";
        estmForm.cfmPswd.value ="";
        
        estmForm.oldPswd.focus();
  
  }
</script>
</head>
<body onload="formInit()">
<form name="estmForm" method="post" action="" >
 <input type="hidden" name="userId" value="<%=userId %>" />
 <input type="hidden" name="firstYn" value="<%=sFirstYn %>" />
 <input type="hidden" name="evalYear" value="<%=sEvalYear %>" />
 <input type="hidden" name="evalNum" value="<%=sEvalNum %>" />

	<div class="wrap">
		<div class="logo">		   
		    <img src="img/rest_logo.jpg" alt="2013년도 경륜경정사업본부 하계휴양소 신청화면" />
            <p><a href="javascript:void(0)" onclick="zoomOut(); return false;" onfocus="blur()">
            <img src="img/btn_m.gif" alt="축소"/></a> <a href="javascript:void(0)" onclick="zoomIn(); return false;" onfocus="blur()">
            <img src="img/btn_p.gif" alt="확대"/></a> <a href="restPassChange.jsp"><img src="img/btn_pass.jpg" alt="비밀번호 변경" /></a>
             <a href="restLogOut.jsp"><img src="img/btn_logout.jpg" alt="로그아웃" /></a></p>
            
		</div>
		<div class="body">
			<div class="passchange_page">
				<img src="img/tit_passchange.jpg" alt="비밀번호 변경" />
				<div class="passchange">
					<ul><li style="margin-right:28px;"><strong>소속</strong> : <%=loginDeptNm %></li><li style="margin-right:28px;"><strong>사번</strong> : <%=loginEmpNo %></li><li><strong>성명</strong> : <%=loginEmpNm %></li></ul>
					<div>
						<ul>
							<li>기존 비밀번호 <input type="password" name="oldPswd" /><strong>(주민번호 뒤 7자리 입력)</strong></li>
							<li>변경 비밀번호 <input type="password" name="newPswd" /><strong>(사용할 비밀번호 입력)</strong></li>
							<li>비밀번호 확인 <input type="password"  name="cfmPswd" /><strong>(사용할 비밀번호 재입력)</strong></li>
						</ul>
					</div>
				</div>
				<p><a href="javascript:savePass()"><img src="img/btn_ok.jpg" alt="확인"/></a> 
				    <a href="javascript:editClear()"><img src="img/btn_cancel.jpg" alt="취소"/></a></p>
			</div><!-- //login_page -->
		</div><!-- //body -->
		<div class="copy">
		<p>Copyright (c) <strong>경륜경정사업본부</strong> All rights Reserved.</p>
		</div>
	</div><!-- //wrap -->
	</form>
</body>
</html>
