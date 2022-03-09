<%@ page language="java" import="java.util.*, java.sql.*, snis.rbm.common.util.*" session="true" contentType="text/html;charset=EUC_KR" %>
<%@ page import="javax.naming.*, javax.sql.*" %>
<%@ include file="comDBManager.jsp" %>

<%
    String sUserId    = request.getParameter("userId");
    String sOldPswd   = request.getParameter("oldPswd");
    String sNewPswd   = request.getParameter("newPswd");
    String sCfmPswd   = request.getParameter("cfmPswd");
    String sFirstYn   = request.getParameter("firstYn");  //Y : 주민번호로 체크 .N : 기존비밀번호로체크 
    
    String sOldEmpPswd ="";
    String sOldPswdEnc    ="";

    PreparedStatement pstmt = null;
    ResultSet rs = null;

	boolean _DEBUG = false;
    
    
    sNewPswd = EncryptSHA256.getEncryptSHA256(sNewPswd);
    sCfmPswd = EncryptSHA256.getEncryptSHA256(sCfmPswd);
    sOldPswdEnc = EncryptSHA256.getEncryptSHA256(sOldPswd);
    
    
    String loginJuminNo ="";
    String sussessYn ="N";
    String saveYn ="N";
    
    
    try { 
//        con = DriverManager.getConnection(jdbcUrl, dbUser, dbPass);
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
         
          String query ="";
          if(sFirstYn.equals("Y")){
              query += "  SELECT            ";
              query += "       A.EMP_NO     ";
              query += "      ,A.EMP_NM     ";
              query += "      ,A.RES_NO     ";
              query += "      ,A.DEPT_CD    ";
              query += "      ,A.DEPT_NM    ";
              query += "   FROM USHUM.VW_EMP_D12@VENUSLINK  A ";
              query += "   WHERE 1=1                          ";
              query += "     AND A.HOLOFF_CLS = '0'           ";   //재직
              query += "     AND A.EMP_NO= ?                  ";
              //query += "     AND A.WK_JJOB IN ('1003', '1005', '1006') ";
         
              pstmt = con.prepareStatement(query);

        
              int i =1;
              pstmt.setString(i++, sUserId );
    
              rs = pstmt.executeQuery();


              if (rs.next()) { 
        		  sOldEmpPswd  =  rs.getString("RES_NO");
        		  sOldEmpPswd  =  sOldEmpPswd.substring(6,13);
			  }
          } else {
              query += "  SELECT            ";
              query += "         A.PSWD     ";
              query += "   FROM  TBRK_USER_TEMP  A ";
              query += "   WHERE 1=1                          ";
              query += "     AND A.EMP_NO= ?                  ";
              query += "     AND A.CO_WRK_GBN= '001'          ";
              

              pstmt = con.prepareStatement(query);

        
              int i =1;
              pstmt.setString(i++, sUserId );
    
              rs = pstmt.executeQuery();


              if (rs.next()) { 
        		  sOldEmpPswd  =  rs.getString("PSWD");
        		  sOldPswd = sOldPswdEnc;
	          }
		  }
          if (_DEBUG) System.out.println(query);
         
    
          
          if(sOldEmpPswd.equals(sOldPswd)){
        	  //저장

                query ="";                 
                query += "   UPDATE TBRK_USER_TEMP         \n";
				query += "      SET PSWD = ?               \n";
                query += "         ,PSWD_UPDT_DT = SYSDATE \n";
                query += "   WHERE EMP_NO = ?              \n";
                query += "     AND CO_WRK_GBN= '001'       \n";

                   
                 pstmt = con.prepareStatement(query);

                 int k =1;
                 pstmt.setString(k++, sNewPswd);              
                 pstmt.setString(k++, sUserId);
                 
                 int result= pstmt.executeUpdate();
                            
                 if(result > 0){
                	  sussessYn = "Y";
                	  saveYn = "Y";
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
         out.println("<script >location.href='restLogin.jsp';</script>");
         
     }else{
    	
	         out.println("<script >alert('기존비밀번호를 확인하세요 .');</script>");
	         
            if(sFirstYn.equals("Y")){
                out.println("<script >location.href='restPassChange.jsp?userId="+sUserId+"';</script>");
            }else{
                out.println("<script >location.href='resttPassChange.jsp';</script>");
            }
	     
     }
 
%>


