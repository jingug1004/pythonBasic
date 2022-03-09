<%@ page language="java" import="java.util.*, java.sql.*" session="true" contentType="text/html;charset=EUC_KR" %>
<%@ include file="comDBManager.jsp" %>
<%
	String empNo = (String)session.getAttribute("EMP_NO");
	String empNm = (String)session.getAttribute("EMP_NM");
	String deptCd = (String)session.getAttribute("DEPT_CD");
	String deptNm = (String)session.getAttribute("DEPT_NM");
    
	String evalYear = (String)session.getAttribute("ESTM_YEAR");
    String evalNum  = (String)session.getAttribute("ESTM_NUM");
  
    if(empNm == null || empNm.equals("")){
        out.println("<script >location.href='estmLogin.jsp';</script>");
   }
    
	String sOpenYn ="N";
	String sEstmOpnYn ="N";
	String sEstmEnd  ="N";
	String sEstmYn ="Y";
	
    String sEvalYear   = evalYear;
    //String sEvalNum    = "1";
    String sEvalNum    = evalNum;
    String sEvalDept   = deptCd;
    String sEvalEmp    = empNo;
    
    String sEstmDeptNm ="";
%>
<%
 
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	
	ArrayList estmDeptList = new ArrayList(10);
	
	
	try { 
	    con = DriverManager.getConnection(jdbcUrl, dbUser, dbPass);
	} catch(SQLException e) {
	     
	    out.println(e.toString());
	    e.printStackTrace();   
	    return;
	}
	
    try {
    	
       //�α�  ��� 
   	   String query ="";
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
       
       int i =1;
       
       pstmt.setString(i++, evalYear);
       pstmt.setString(i++, evalNum);
       pstmt.setString(i++, "estmMain.jsp");
       pstmt.setString(i++, empNo);
       
       
       pstmt.setString(i++,request.getRemoteAddr());
       
       pstmt.setString(i++, sEvalYear);
       pstmt.setString(i++, sEvalNum);
       pstmt.setString(i++, "estmMain.jsp");       
       
       pstmt.executeUpdate();
          
     
       
        //����Ȯ������ ��ȸ 
         query = "";
        
        query ="";
        query += "       SELECT    \n";
        //query += "           NVL(OPEN_YN,'N')  OPEN_YN  \n";
        query += "              CASE WHEN TO_CHAR(SYSDATE, 'YYYYMMDDHH24') >= '2014011220' THEN NVL(OPEN_YN,'N') ELSE 'N' END AS OPEN_YN  \n";
        query += "             ,NVL(ESTM_OPN_YN,'N')  ESTM_OPN_YN  \n";
        query += "             ,NVL(ESTM_END_YN,'N')  ESTM_END_YN  \n";
        query += "       FROM  TBRF_EV_MASTER  \n";
        query += "       WHERE ESTM_YEAR= ?    \n";
        query += "         AND ESTM_NUM= ?     \n";
         
          
        pstmt = con.prepareStatement(query);

         i =1;

        pstmt.setString(i++, sEvalYear);
        pstmt.setString(i++, sEvalNum);
   
        

        rs = pstmt.executeQuery();
        
        if(rs.next()){
        	sOpenYn    = rs.getString("OPEN_YN");
        	sEstmOpnYn = rs.getString("ESTM_OPN_YN");
        	sEstmEnd   = rs.getString("ESTM_END_YN");

        }
        
      
        if(sEstmOpnYn == null) sEstmOpnYn="";
        if(sEstmEnd == null) sEstmEnd="";
        if(sEstmOpnYn.equals("N") || sEstmEnd.equals("Y")){
        	sEstmYn = "N";
        }

        

	      //�� Ȯ��  
	      query = "";
	      
	      query += "        SELECT       ";
	      query += "              ESTM_DEPT   ";
	      query += "              ,(SELECT MAX(DEPT_NM) FROM TBRF_EV_DEPT B    ";
	      query += "                       WHERE B.ESTM_YEAR = ESTM_YEAR   ";
	      query += "                           AND B.ESTM_NUM = ESTM_NUM   ";
	      query += "                           AND B.DEPT_CD =  ESTM_DEPT) ESTM_DEPT_NM   ";
	      query += "              ,MAX(CFM_YN) CFM_YN   ";
	      query += "        FROM TBRF_EV_WK_MULT      ";
	      query += "        WHERE ESTM_YEAR= ?    ";
	      query += "              AND ESTM_NUM= ?   ";
	      query += "              AND ESTM_EMP= ?   ";
	      query += "              AND MULT_ESTM_GRP <> 0   ";
	      
	      query += "        GROUP BY ESTM_DEPT   ";
	       
	        
	      pstmt = con.prepareStatement(query);
	
	    
	      i =1;
	
	      pstmt.setString(i++, sEvalYear);
	      pstmt.setString(i++, sEvalNum);
	      pstmt.setString(i++, sEvalEmp);        
	      
	
	      rs = pstmt.executeQuery();
	      
	      estmDeptList = getResultSetToMap(rs);
	      
	     
	      if(estmDeptList.size()>1){
              HashMap map = new HashMap();
              
              map = (HashMap)estmDeptList.get(0);

              sEstmDeptNm = (String)map.get("ESTM_DEPT_NM");
	      }
	      
	      /*
	      if(rs.next()){
	    	  if(rs.getString("CFM_YN") != null && rs.getString("CFM_YN").equals("Y") ){
	    		  sEstmEndYn ="Y";
	    	  }
	      }
	      */
      
  
      
	  } catch(SQLException e ) {
	      
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
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr" />
<link rel="stylesheet" href="css/default.css" type="text/css" />
<![If IE 7]>
<link rel="stylesheet" href="css/ie7.css" type="text/css" />
<![endif]>
<script src="zoom.js" type="text/javascript"></script>
<script language="JavaScript" type="text/JavaScript">

function estmCfm(){

       estmForm.action="estmCfm.jsp";
       estmForm.submit();
        
}

function estmResult(){

       estmForm.action="estmResult.jsp";
       estmForm.submit();
        
}
function setEstmDeptNm(sDeptNm){

    estmForm.estmDeptNm.value= sDeptNm;
}



</script>
</head>
<body>
<form name="estmForm" method="post" action="" >
<input type="hidden" name="estmDeptNm" id="estmDeptNm" value="<%=sEstmDeptNm %>" />
	<div class="wrap">
		<div class="logo">
			<img src="img/logo.jpg" alt="KSPO ���ֻ������/������ �� �Ͽ����� �򰡽ý���" />
            <p><a href="javascript:void(0)" onclick="zoomOut(); return false;" onfocus="blur()">
            <img src="img/btn_m.gif" alt="���"/></a> <a href="javascript:void(0)" onclick="zoomIn(); return false;" onfocus="blur()">
            <img src="img/btn_p.gif" alt="Ȯ��"/></a> <a href="passChange.jsp"><img src="img/btn_pass.jpg" alt="��й�ȣ ����" /></a> 
            <a href="estmLogin.jsp"><img src="img/btn_logout.jpg" alt="�α׾ƿ�" /></a></p>
            
		</div>
		<div class="body">
			<div class="result_page">
				<img src="img/tit_result.jpg" alt="�� ���� �� ��� ��� Ȯ��" />
				<div class="result" >
					<div class="gray_box">
						<p>�ϴ��� �ٸ��� ��ư�� �����Ͽ� �ٸ��򰡸� ������ �� ������, �� Ȯ�� ���Ŀ��� �򰡳����� ������ �� �����ϴ�.</p>
						<p>��ü �򰡰� ����� ���� ���κ��� �����򰡵�� Ȯ���� �����մϴ�.</p>
					</div>
					<ul>
						<li><strong>�ٸ�����</strong> : <%=empNm %> (<%=deptNm %>)</li>
						<li><strong>�ٸ��� �������</strong>: 
						     <%
						         String sEstmEndYn ="N";
					             String estmDept ="N";
						         String estmDeptNm ="N";
						         String sChecked ="";
                                 for(int k = 0; k<estmDeptList.size();k++){
                                     HashMap map = new HashMap();
                                     
                                     map = (HashMap)estmDeptList.get(k);
                                     
                                     estmDept = (String)map.get("ESTM_DEPT");
                                     if(estmDept==null) estmDept ="";
                                     
                                     estmDeptNm = (String)map.get("ESTM_DEPT_NM");
                                     if(estmDeptNm==null) estmDeptNm ="";
                                     
                                     sEstmEndYn = (String)map.get("CFM_YN");
                                     if(sEstmEndYn==null) sEstmEndYn ="";
                                     
                                     // if(estmDept.equals(deptCd)){ 
                                    	 
                                     if(k == 0){ 
                                    	  sChecked = "checked";
                                     
                                     }else{
                                    	 sChecked = "";
                                     }
                                   if(estmDeptList.size() == 1){
                             %>
                                	<%=sEstmEndYn.equals("Y")?"�Ϸ�":"����" %>
                             <%
                                   }else{
                             %>
                                    <input type="radio"  style="vertical-align:middle;margin-left:6px;  " <%=sChecked %> id ="estmDeptCd" name ="estmDeptCd"  value="<%=estmDept%>" onclick="setEstmDeptNm('<%=estmDeptNm %>')"  /><%=estmDeptNm %>(<%=sEstmEndYn.equals("Y")?"�Ϸ�":"����" %>)
                                    
                             <%   
                                   }
                                 }
                             %>
						</li>
						<li><strong>������</strong> : <%=sOpenYn.equals("Y")?"Ȯ��":"��Ȯ��" %></li>
					</ul>
				</div>
				<p>
				 <%//if(sEstmYn.equals("Y")){%>
                    <a href="javascript:estmCfm()"><img src="img/btn_value.jpg" alt="�ٸ���"/></a>
                  <%//} %>
				 
				 <%//if(sOpenYn.equals("Y")){%>
				    <a href="javascript:estmResult()"><img src="img/btn_grade_check.jpg" alt="�򰡰�� ���Ȯ��"/></a></p>
				  <%//} %>
			</div><!-- //login_page -->
		</div><!-- //body -->
		<div class="copy">
		<p>Copyright (c) <strong>���ֻ������</strong> All rights Reserved.</p>
		</div>
	</div><!-- //wrap -->
	</form>
</body>
</html>
