<%@ page language="java" import="java.util.*, java.sql.*" session="true" contentType="text/html;charset=EUC_KR" %>
<%@ include file="comDBManager.jsp" %>
<%
    String userId = request.getParameter("userId");

    String sFirstYn = "Y";
    if(userId == null || userId.equals("")){
    	userId = (String)session.getAttribute("EMP_NO");
    	
    	sFirstYn = "N";
    }

	String sEvalYear   = "";
	String sEvalNum    = "";  

  
	
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    
    
    String loginEmpNo = "";
    String loginEmpNm = "";
    String loginDeptNm = "";
    String loginDeptCd= "";

    
    
	try { 
	    con = DriverManager.getConnection(jdbcUrl, dbUser, dbPass);
	} catch(SQLException e) {
	     
	    out.println(e.toString());
	    e.printStackTrace();   
	    return;
	}



   try {
    
	    
       //ȸ����ȸ 
       String query = "";
       
       query ="";
       query += "       SELECT    \n";
       query += "            ESTM_YEAR  \n";
       query += "           ,ESTM_NUM   \n";
       query += "       FROM TBRF_EV_MASTER   \n";
       query += "       WHERE ESTM_YEAR = TO_CHAR(SYSDATE, 'YYYY')    \n";
       query += "           AND ESTM_NUM = (SELECT MAX(ESTM_NUM) FROM TBRF_EV_MASTER WHERE ESTM_YEAR = TO_CHAR(SYSDATE, 'YYYY'))     \n";
        
         
       pstmt = con.prepareStatement(query);

       rs = pstmt.executeQuery();
       
       if(rs.next()){
       	sEvalYear =  rs.getString("ESTM_YEAR");    
       	//sEvalNum =  "1";
       	
       	sEvalNum =  rs.getString("ESTM_NUM");
       }
        
    
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
      pstmt.setString(i++, userId );
      pstmt.setString(i++, sEvalYear );
      pstmt.setString(i++, sEvalNum);

      rs = pstmt.executeQuery();
      
      

      while (rs.next()) {
   
          
          loginEmpNo = rs.getString("EMP_NO"); 
          loginEmpNm = rs.getString("EMP_NM");
          loginDeptCd = rs.getString("DEPT_CD");
          loginDeptNm = rs.getString("DEPT_NM");


      }
  

      //�α�  ��� 
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
      pstmt.setString(i++, "passChange.jsp");
      pstmt.setString(i++, userId);
      
      
      pstmt.setString(i++,request.getRemoteAddr());
      
      pstmt.setString(i++, sEvalYear);
      pstmt.setString(i++, sEvalNum);
      pstmt.setString(i++, "passChange.jsp");       
      
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
<title> ������ �� �Ͽ������ �򰡽ý��� </title>
<meta http-equiv="Content-Type" content="text/html; charset=eu-kr" />
<link rel="stylesheet" href="css/default.css" type="text/css" />
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
        alert("���� ��й�ȣ�� �Է��Ͽ� �ּ���");
        estmForm.oldPswd.focus();
        return;        
    }

    if(estmForm.newPswd.value == "null" ||  estmForm.newPswd.value == ""){
        alert("���� ��й�ȣ�� �Է��Ͽ� �ּ���");
        estmForm.newPswd.focus();
        return;        
    }

    if(estmForm.cfmPswd.value == "null" ||  estmForm.cfmPswd.value == ""){
        alert("Ȯ�� ��й�ȣ �Է��Ͽ� �ּ���");
        estmForm.cfmPswd.focus();
        return;        
    }
      
      
     if(estmForm.oldPswd.value  == estmForm.newPswd.value){
        alert("������й�ȣ��  ���� ��й�ȣ�� �����ϴ�. ");
        estmForm.newPswd.focus();
        return;        
    }
     if(estmForm.newPswd.value  != estmForm.cfmPswd.value){
        alert("�����й�ȣ��  ��й�ȣ Ȯ���� �ٸ��ϴ�. ");
        estmForm.cfmPswd.focus();
        return;        
    }
      
       estmForm.action="passChangeSave.jsp";
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
		    <img src="img/logo.jpg" alt="KSPO ���ֻ������/������ �� �Ͽ����� �򰡽ý���" />
            <p><a href="javascript:void(0)" onclick="zoomOut(); return false;" onfocus="blur()">
            <img src="img/btn_m.gif" alt="���"/></a> <a href="javascript:void(0)" onclick="zoomIn(); return false;" onfocus="blur()">
            <img src="img/btn_p.gif" alt="Ȯ��"/></a> <a href="passChange.jsp"><img src="img/btn_pass.jpg" alt="��й�ȣ ����" /></a>
             <a href="estmLogin.jsp"><img src="img/btn_logout.jpg" alt="�α׾ƿ�" /></a></p>
            
		</div>
		<div class="body">
			<div class="passchange_page">
				<img src="img/tit_passchange.jpg" alt="��й�ȣ ����" />
				<div class="passchange">
					<ul><li style="margin-right:28px;"><strong>�Ҽ�</strong> : <%=loginDeptNm %></li><li style="margin-right:28px;"><strong>���</strong> : <%=loginEmpNo %></li><li><strong>����</strong> : <%=loginEmpNm %></li></ul>
					<div>
						<ul>
							<li>���� ��й�ȣ <input type="password" name="oldPswd" /><strong>(�ֹι�ȣ �� 7�ڸ� �Է�)</strong></li>
							<li>���� ��й�ȣ <input type="password" name="newPswd" /><strong>(����� ��й�ȣ �Է�)</strong></li>
							<li>��й�ȣ Ȯ�� <input type="password"  name="cfmPswd" /><strong>(����� ��й�ȣ ���Է�)</strong></li>
						</ul>
					</div>
				</div>
				<p><a href="javascript:savePass()"><img src="img/btn_ok.jpg" alt="Ȯ��"/></a> 
				    <a href="javascript:editClear()"><img src="img/btn_cancel.jpg" alt="���"/></a></p>
			</div><!-- //login_page -->
		</div><!-- //body -->
		<div class="copy">
		<p>Copyright (c) <strong>���ֻ������</strong> All rights Reserved.</p>
		</div>
	</div><!-- //wrap -->
	</form>
</body>
</html>
