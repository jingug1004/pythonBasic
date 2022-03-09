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
    
	    
      //��ȸ 
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
      query += "     AND A.HOLOFF_CLS = '0'           ";   //����
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
  

      //�α�  ��� 
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
<title> 2013�⵵ �������������� �ϰ��޾�� ��ûȭ��</title>
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
		    <img src="img/rest_logo.jpg" alt="2013�⵵ �������������� �ϰ��޾�� ��ûȭ��" />
            <p><a href="javascript:void(0)" onclick="zoomOut(); return false;" onfocus="blur()">
            <img src="img/btn_m.gif" alt="���"/></a> <a href="javascript:void(0)" onclick="zoomIn(); return false;" onfocus="blur()">
            <img src="img/btn_p.gif" alt="Ȯ��"/></a> <a href="restPassChange.jsp"><img src="img/btn_pass.jpg" alt="��й�ȣ ����" /></a>
             <a href="restLogOut.jsp"><img src="img/btn_logout.jpg" alt="�α׾ƿ�" /></a></p>
            
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
		<p>Copyright (c) <strong>��������������</strong> All rights Reserved.</p>
		</div>
	</div><!-- //wrap -->
	</form>
</body>
</html>
