<%@ page language="java" import="java.util.*, java.sql.*, snis.rbm.common.util.*" session="true" contentType="text/html;charset=EUC_KR" %>
<%@ include file="comDBManager.jsp" %>

<%
    String sUserId    = request.getParameter("userId");
    String sOldPswd   = request.getParameter("oldPswd");
    String sNewPswd   = request.getParameter("newPswd");
    String sCfmPswd   = request.getParameter("cfmPswd");
    String sFirstYn   = request.getParameter("firstYn");  //Y : 주민번호로 체크 .N : 기존비밀번호로체크 

    String sEvalYear   = request.getParameter("evalYear");
    //String sEvalNum    ="1";
    String sEvalNum    = request.getParameter("evalNum");
    
    String sOldEmpPswd ="";
    String sOldPswdEnc    ="";
    
    

    PreparedStatement pstmt = null;
    ResultSet rs = null;

    
    //Des des = new Des();
    
    //sNewPswd = des.Encode(sNewPswd);
    //sCfmPswd = des.Encode(sCfmPswd);
    //sOldPswdEnc = des.Encode(sOldPswd);
    
    sNewPswd = EncryptSHA256.getEncryptSHA256(sNewPswd);
    sCfmPswd = EncryptSHA256.getEncryptSHA256(sCfmPswd);
    sOldPswdEnc = EncryptSHA256.getEncryptSHA256(sOldPswd);    
    
    
    
    String loginJuminNo ="";
    String sussessYn ="N";
    String saveYn ="N";
    
    try { 
        con = DriverManager.getConnection(jdbcUrl, dbUser, dbPass);
    } catch(SQLException e) {
         
        out.println(e.toString());
        e.printStackTrace();   
        return;
    }

     try {
         
         
          String query ="";
          query += "   SELECT   \n";
          query += "       A.ESTM_YEAR   \n";
          query += "      ,A.ESTM_NUM   \n";
          query += "      ,A.DEPT_CD   \n";
          query += "      ,A.EMP_NO   \n";
          query += "      ,A.EMP_NM   \n";
          query += "      ,FC_DEC(A.RES_NO) RES_NO   \n";
          query += "      ,PWD_NO   \n";
          query += "   FROM TBRF_EV_EMP  A \n";
          query += "   WHERE 1=1   \n";
          query += "     AND A.EMP_NO= ?   \n";
          query += "     AND A.ESTM_YEAR = ?   \n";
          query += "     AND A.ESTM_NUM  =?   \n";
          query += "     AND A.WRK_GBN = '001'   \n";
           
         
          pstmt = con.prepareStatement(query);

        
          int i =1;
          pstmt.setString(i++, sUserId );
          pstmt.setString(i++, sEvalYear );
          pstmt.setString(i++, sEvalNum);

          rs = pstmt.executeQuery();


          while (rs.next()) { 
        	  if(sFirstYn.equals("Y")){
        		  sOldEmpPswd  =  rs.getString("RES_NO");
        		  sOldEmpPswd  =  sOldEmpPswd.substring(0,7);
        	  }else{
        		  sOldEmpPswd  =  rs.getString("PWD_NO");
        		  sOldPswd = sOldPswdEnc;
        	  }
          }

         
    
          
          if(sOldEmpPswd.equals(sOldPswd)){
        	  //저장

              
        	  
                query ="";                 
                query += "   UPDATE TBRF_EV_EMP SET   ";
                query += "       PWD_NO = ?   ";
                query += "      ,UPDT_DT = SYSDATE   ";
                query += "   WHERE EMP_NO = ?    ";
                query += "     AND ESTM_YEAR = ?   \n";
                query += "     AND ESTM_NUM  = ?   \n";
                query += "     AND WRK_GBN = '001'   \n";

                   
                 pstmt = con.prepareStatement(query);

                 int k =1;
                 pstmt.setString(k++, sNewPswd);              
                 
                 pstmt.setString(k++, sUserId);
                 pstmt.setString(k++, sEvalYear);
                 pstmt.setString(k++, sEvalNum);

                 
                 int result= pstmt.executeUpdate();
                            
                 if(result > 0){
                	  sussessYn = "Y";
                	  saveYn = "Y";
                	  
                	  
                	  
                	  /*경주사업시스템 사용자 비밀번호 수정*/
                      query ="";                 
                      query += "   UPDATE TBRK_USER SET   ";
                      query += "       PSWD = ?   ";
                      query += "      ,UPDT_ID = ?   ";
                      query += "      ,UPDT_DT = SYSDATE   ";
                      query += "   WHERE USER_ID = ?    ";

                         
                      pstmt = con.prepareStatement(query);

                      k=1;
                      pstmt.setString(k++, sNewPswd);              
                      
                      pstmt.setString(k++, sUserId);
                      pstmt.setString(k++, sUserId);
                      
                      
                      pstmt.executeUpdate();

                 }else{
                	 saveYn = "N";
                 }
                 
                 
                 

          }else{ //주민번호 다름 
        	  sussessYn = "N";
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
     
     
     if(sussessYn.equals("Y")){
    	 
         out.println("<script >alert('저장되었습니다. 로그인 화면으로 이동합니다. ');</script>");
         out.println("<script >location.href='estmLogin.jsp';</script>");
         
     }else{
    	
	         out.println("<script >alert('기존비밀번호를 확인하세요 .');</script>");
	         
            if(sFirstYn.equals("Y")){
                out.println("<script >location.href='passChange.jsp?userId="+sUserId+"';</script>");
            }else{
                out.println("<script >location.href='passChange.jsp';</script>");
            }
	     
     }
 
%>


