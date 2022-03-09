<%@ page language="java" import="java.util.*, java.sql.*, snis.rbm.common.util.*" session="true" contentType="text/html;charset=EUC_KR" %>
<%@ include file="comDBManager.jsp" %>

<%
    String userId  = request.getParameter("userId").trim();
    String pswd   = request.getParameter("pswd");

    String sEvalYear   = "2013";
    String sEvalNum    = "1";    

    PreparedStatement pstmt = null;
    ResultSet rs = null;
    
    String pwdNo = "";
    String pwdExistsYn = "";
    String pwdYn = "";
    
    String loginEmpNo = "";
    String loginEmpNm = "";
    String loginDeptCd = "";
    String loginDeptNm = "";
    String loginJuminNo = "";
    
    String loginEvalYear = "2013";    
    String loginEvalNum = "1";
    
    String sEvEscGbn ="N";
    int nGrpCnt = 0;
    int nEstmGrpCnt = 0;
    int nMaxEstmGrp = 0;
   
    
    
    String enPswd = EncryptSHA256.getEncryptSHA256(pswd);  //des.Encode(pswd);  //    Des des = new Des();
    
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
        	     //loginEvalYear =  rs.getString("ESTM_YEAR");    
        	     
        	     //loginEvalNum =  rs.getString("ESTM_NUM");
        	     
        	     
        	     sEvalYear =  rs.getString("ESTM_YEAR");  
        	     
        	     sEvalNum =  rs.getString("ESTM_NUM");
        	     
        	     //
        	     //loginEvalNum =  "2";
        	    // sEvalNum="2";
         }
         

         
          //���������  ��ȸ 
          query ="";

          query += "              SELECT      ";
          query += "                   A.ESTM_YEAR      ";
          query += "                  ,A.ESTM_NUM      ";
          
          query += "                  ,A.EMP_NO      ";
          query += "                  ,A.EMP_NM      ";
          query += "                  ,FC_DEC(A.RES_NO) RES_NO     ";
          query += "                  ,A.ESTM_DEPT      ";
          query += "                  ,A.PWD_NO      ";
          query += "                  ,A.WK_JOB_CD      ";
          query += "                  ,A.APT_DT      ";
          query += "                  ,A.JOB_TIT_CD      ";
          query += "                  ,A.JOB_TIT_NM      ";
          query += "                  ,A.HP_NO      ";
          query += "                  ,C.DEPT_CD      ";
          query += "                  ,B.DEPT_NM     ";
          query += "               FROM TBRF_EV_EMP  A    ";
          query += "                    ,(SELECT  ESTM_YEAR, ESTM_NUM, ESTM_EMP, MAX(ESTM_DEPT) DEPT_CD      ";
          query += "                          FROM TBRF_EV_WK_MULT        ";
          query += "                          GROUP BY ESTM_YEAR, ESTM_NUM, ESTM_EMP) C      ";
          query += "                    ,TBRF_EV_DEPT B      ";
          query += "               WHERE 1=1      ";
            
          query += "                 AND A.ESTM_YEAR = C.ESTM_YEAR(+)      ";
          query += "                 AND A.ESTM_NUM = C.ESTM_NUM(+)      ";
          query += "                 AND A.EMP_NO = C.ESTM_EMP(+)      ";
          
          query += "                 AND B.ESTM_YEAR = C.ESTM_YEAR      ";
          query += "                 AND B.ESTM_NUM = C.ESTM_NUM      ";
          query += "                 AND B.DEPT_CD = C.DEPT_CD      ";
          
          query += "                 AND A.EMP_NO= ?      ";
          query += "                 AND A.ESTM_YEAR = ?      ";
          query += "                 AND A.ESTM_NUM  =?      ";
          query += "                 AND A.WRK_GBN = '001'      ";
          

           
         
          pstmt = con.prepareStatement(query);

        
          int i =1;
          pstmt.setString(i++, userId );
          pstmt.setString(i++, sEvalYear );
          pstmt.setString(i++, sEvalNum);

          rs = pstmt.executeQuery();
          
          

          while (rs.next()) {
        	  pwdNo = rs.getString("PWD_NO");   
        	  
        	  loginEmpNo = rs.getString("EMP_NO"); 
        	  loginEmpNm = rs.getString("EMP_NM");
        	  loginDeptCd = rs.getString("DEPT_CD");
        	  loginDeptNm = rs.getString("DEPT_NM");
        	  loginJuminNo =  rs.getString("RES_NO");

          }
       
          //����
          
          query ="";
          query += "       SELECT    \n";
          query += "             DECODE(COUNT(*),0,'N','Y') ESTM_ESC_GBN  \n";
          query += "            ,COUNT(MULT_ESTM_GRP) GRP_CNT  \n";
          query += "            ,SUM(DECODE(MULT_ESTM_GRP,0,0,1)) ESTM_GRP_CTN  \n";
          query += "            ,MAX(MULT_ESTM_GRP)  MAX_ESTM_GRP  \n";
          query += "       FROM TBRF_EV_WK_MULT   \n";
          query += "       WHERE ESTM_YEAR =  ?    \n";
          query += "            AND ESTM_NUM = ?   \n";
          query += "            AND ESTM_EMP =  ?    \n";
          
          
          
          
          
           

          pstmt = con.prepareStatement(query);
         
          
          i =1;
          pstmt.setString(i++, sEvalYear );
          pstmt.setString(i++, sEvalNum );
          pstmt.setString(i++, userId );
          
          rs = pstmt.executeQuery();
          
          if(rs.next()){
        	  sEvEscGbn = rs.getString("ESTM_ESC_GBN");
        	  nGrpCnt = rs.getInt("GRP_CNT");
        	  nEstmGrpCnt = rs.getInt("ESTM_GRP_CTN");
        	  nMaxEstmGrp =  rs.getInt("MAX_ESTM_GRP");
          }
          
          if(sEvEscGbn == null) sEvEscGbn= "";
          
          if(sEvEscGbn.equals("Y")){  //���������..
        	  if(nEstmGrpCnt <= 0 ){  //�׷��� 0�ϰ�� ����
        		  sEvEscGbn ="N";
        		  
        	  }else{ 
        		  if(nGrpCnt != nEstmGrpCnt ){ // �򰡱׷��� 0�� �ƴ� �׷��� ������ ��  ��� 
        			  sEvEscGbn ="Y";        			  
        		  }else{
        			  if(nMaxEstmGrp == 0) sEvEscGbn ="N";   
        			  else sEvEscGbn ="Y";  
        		  }
        			  
        	  }
        		  
        	  
          }
 
          
          if(pwdNo == null) pwdNo= "";
          if(loginEmpNo == null) loginEmpNo= "";
          if(loginJuminNo == null) loginJuminNo= "";
          if(!loginJuminNo.equals("")){
        	  //loginJuminNo = loginJuminNo.substring(0,7);
        	 
          }
          

          if(loginEmpNo.equals("")){
        	  pwdYn = "N";
        	  
          }else{
        	  
	          if(pwdNo.equals("") && loginJuminNo.equals(pswd)){ // ��й�ȣ �������� �������         	  
	        	  pwdExistsYn = "N";
	        	  pwdYn = "Y";
	        	  
	        	  
	          }else{ 
	        	  if(pwdNo.equals(enPswd)){
	        		  pwdYn = "Y";
	        		 
	        		  //���� ����
	        		  session.setAttribute("EMP_NO",loginEmpNo);
	        		  session.setAttribute("EMP_NM",loginEmpNm);
	        		  session.setAttribute("DEPT_CD",loginDeptCd);
	        		  session.setAttribute("DEPT_NM",loginDeptNm);
	        		  
	        		  session.setAttribute("ESTM_YEAR",loginEvalYear);
                      session.setAttribute("ESTM_NUM",loginEvalNum);

	     
	        		    
	        	  }else{
	        		  pwdYn = "N";
	        	  }
	        	  
	          }
          }
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
         
     // 2013�⵵�� �ٸ��򰡰� ���� ��� ��ȸ�� ����
     //if(sEvEscGbn.equals("N")){
     //    out.println("<script >alert('���ϴ� �ٸ��� ����� �ƴմϴ�. Ȯ���Ͻñ� �ٶ��ϴ�.');</script>");
     //    out.println("<script >location.href='estmLogin.jsp';</script>");
     //    return;      
     //}
     System.out.println("pwdExistsYn:"+pwdExistsYn);
     System.out.println("pwdYn:"+pwdYn);
     
     if(pwdExistsYn.equals("N") && pwdYn.equals("Y")){  // �ֹι�ȣ���ڸ���    ȭ�鿡 �Է��� ��й�ȣ ������� ��й�ȣ �ʱ�ȭ������ �̵� 
    	 out.println("<script >alert('��й�ȣ ���� �������� �̵��մϴ�.');</script>");
    	 out.println("<script >location.href='passChange.jsp?userId="+userId+"';</script>");

     }else{
         if(pwdYn.equals("Y")){  // �α��� ����
        	
        	 response.sendRedirect("estmMain.jsp"); 
         
         }else{ // �н����� X , ���̵� x 
        	 
        	 if(sEvEscGbn.equals("N")){        		 
                 out.println("<script >alert('���ϴ� �ٸ��� ����� �ƴմϴ�. Ȯ���Ͻñ� �ٶ��ϴ�..');</script>");
        	 }else{        		 
                 out.println("<script >alert('�α��� ������ Ȯ���ϼ���.');</script>");
        	 }        	 
  
             out.println("<script >location.href='estmLogin.jsp';</script>");
        
         
         }
         
     }
%>


