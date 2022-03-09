<%@ page language="java" import="java.util.*, java.sql.*, snis.rbm.common.util.*" session="true" contentType="text/html;charset=EUC_KR" %>
<%@ page import="javax.naming.*, javax.sql.*" %>
<%@ include file="estmLib.jsp" %>
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
   
    //Des des = new Des();    
    //String enPswd = des.Encode(pswd);
    String enPswd = EncryptSHA256.getEncryptSHA256(pswd);
    
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
          

     try {
    	 
    	 //회차조회 
         String query = "";
         
         query =  "";
         query += "       SELECT /*estmLoginSave.jsp:s01*/   \n";
         query += "            ESTM_YEAR  \n";
         query += "           ,ESTM_NUM   \n";
         query += "       FROM TBRF_EV_MASTER   \n";
         query += "       WHERE ROWNUM  = 1      \n";
         query += "       ORDER BY ESTM_YEAR DESC, ESTM_NUM DESC \n";         
         //query += "       WHERE ESTM_YEAR = TO_CHAR(SYSDATE, 'YYYY')    \n";
         //query += "           AND ESTM_NUM = (SELECT MAX(ESTM_NUM) FROM TBRF_EV_MASTER WHERE ESTM_YEAR = TO_CHAR(SYSDATE, 'YYYY'))     \n";
          
           
         pstmt = con.prepareStatement(query);

         rs = pstmt.executeQuery();
         
         if(rs.next()){
        	     loginEvalYear =  rs.getString("ESTM_YEAR");   
        	     
        	     loginEvalNum =  rs.getString("ESTM_NUM");
        	     
        	     
        	     sEvalYear =  rs.getString("ESTM_YEAR");  
        	     
        	     sEvalNum =  rs.getString("ESTM_NUM");
        	     
        	     //
        	     //loginEvalNum =  "2";
        	    // sEvalNum="2";
         }
         

         
          //사용자정보  조회 
          query ="";
          query += "  SELECT /*estmLoginSave.jsp:s02*/     ";
          query += "       A.ESTM_YEAR      ";
          query += "      ,A.ESTM_NUM      ";          
          query += "      ,A.EMP_NO      ";
          query += "      ,A.EMP_NM      ";
          query += "      ,FC_DEC(A.RES_NO) RES_NO     ";
          query += "      ,A.ESTM_DEPT      ";
          query += "      ,A.PWD_NO      ";
          query += "      ,A.WK_JOB_CD      ";
          query += "      ,A.APT_DT      ";
          query += "      ,A.JOB_TIT_CD      ";
          query += "      ,A.JOB_TIT_NM      ";
          query += "      ,A.HP_NO      ";
          query += "      ,B.DEPT_CD      ";
          query += "      ,B.DEPT_NM     ";
          query += "   FROM TBRF_EV_EMP  A    ";
          query += "        ,(SELECT  ESTM_YEAR, ESTM_NUM, ESTM_EMP, MAX(ESTM_DEPT) DEPT_CD      ";
          query += "              FROM TBRF_EV_WK_MULT        ";
          query += "              GROUP BY ESTM_YEAR, ESTM_NUM, ESTM_EMP) C      ";
          query += "        ,TBRF_EV_DEPT B      ";
          query += "   WHERE 1=1      ";            
          query += "     AND A.ESTM_YEAR = C.ESTM_YEAR(+)      ";
          query += "     AND A.ESTM_NUM = C.ESTM_NUM(+)      ";
          query += "     AND A.EMP_NO = C.ESTM_EMP(+)      ";          
          query += "     AND B.ESTM_YEAR = C.ESTM_YEAR      ";
          query += "     AND B.ESTM_NUM = C.ESTM_NUM      ";
          query += "     AND B.DEPT_CD = C.DEPT_CD      ";          
          query += "     AND A.EMP_NO= ?      ";
          query += "     AND A.ESTM_YEAR = ?      ";
          query += "     AND A.ESTM_NUM  =?      ";
          query += "     AND A.WRK_GBN = '001'      ";
          
          //2013년도는 다면평가를 실시하지 않음
          query ="";
          query += "  SELECT      ";
          query += "       A.ESTM_YEAR      ";
          query += "      ,A.ESTM_NUM      ";          
          query += "      ,A.EMP_NO      ";
          query += "      ,A.EMP_NM      ";
          query += "      ,FC_DEC(A.RES_NO) RES_NO     ";
          query += "      ,A.ESTM_DEPT      ";
          query += "      ,A.PWD_NO      ";
          query += "      ,A.WK_JOB_CD      ";
          query += "      ,A.APT_DT      ";
          query += "      ,A.JOB_TIT_CD      ";
          query += "      ,A.JOB_TIT_NM      ";
          query += "      ,A.HP_NO      ";
          query += "      ,B.DEPT_CD      ";
          query += "      ,B.DEPT_NM     ";
          query += "   FROM TBRF_EV_EMP  A    ";
          query += "       ,TBRF_EV_DEPT B      ";
          query += "   WHERE 1=1      ";            
          query += "     AND A.ESTM_YEAR = B.ESTM_YEAR      ";
          query += "     AND A.ESTM_NUM  = B.ESTM_NUM      ";
          query += "     AND A.DEPT_CD   = B.DEPT_CD      ";          
          query += "     AND A.EMP_NO    = ?      ";
          query += "     AND A.ESTM_YEAR = ?      ";
          query += "     AND A.ESTM_NUM  =?      ";
          query += "     AND A.WRK_GBN = '001'      ";
          
          pstmt = con.prepareStatement(query);

        
          int i =1;
          pstmt.setString(i++, userId );
          pstmt.setString(i++, sEvalYear );
          pstmt.setString(i++, sEvalNum);

          rs = pstmt.executeQuery();
          

          while (rs.next()) {
        	  pwdNo        = rs.getString("PWD_NO");           	  
        	  loginEmpNo   = rs.getString("EMP_NO"); 
        	  loginEmpNm   = rs.getString("EMP_NM");
        	  loginDeptCd  = rs.getString("DEPT_CD");
        	  loginDeptNm  = rs.getString("DEPT_NM");
        	  loginJuminNo =  rs.getString("RES_NO");
          }
          //System.out.println("pwdNo="+pwdNo);
          //System.out.println("loginEmpNo="+loginEmpNo);
          //System.out.println("loginEmpNm="+loginEmpNm);
          //System.out.println("loginDeptCd="+loginDeptCd);
          //System.out.println("loginDeptNm="+loginDeptNm);
          //System.out.println("loginJuminNo="+loginJuminNo);

          //비대상          
          query ="";
          query += "      SELECT    \n";
          query += "             DECODE(COUNT(*),0,'N','Y') ESTM_ESC_GBN  \n";
          query += "            ,COUNT(MULT_ESTM_GRP) GRP_CNT  \n";
          query += "            ,SUM(DECODE(MULT_ESTM_GRP,0,0,1)) ESTM_GRP_CTN  \n";
          query += "            ,MAX(MULT_ESTM_GRP)  MAX_ESTM_GRP  \n";
          query += "       FROM TBRF_EV_WK_MULT    \n";
          query += "       WHERE ESTM_YEAR =  ?    \n";
          query += "         AND ESTM_NUM  = ?     \n";
          query += "         AND ESTM_EMP  =  ?    \n";
          
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
          
          if(sEvEscGbn.equals("Y")){  //대상이지만..
        	  if(nEstmGrpCnt <= 0 ){  //그룹이 0일경우 비대상
        		  sEvEscGbn ="N";
        		  
        	  }else{ 
        		  if(nGrpCnt != nEstmGrpCnt ){ // 평가그룹중 0이 아닌 그룹이 있으면 평가  대상 
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
          
    	  System.out.println("loginEmpNo="+loginEmpNo);       	  
    	  System.out.println("pwdNo="+pwdNo);       	  
    	  System.out.println("loginJuminNo="+loginJuminNo);       	  
    	   System.out.println("pswd="+pswd);       	  

          if(loginEmpNo.equals("")){
        	  pwdYn = "N";
        	  
          }else{
        	  
        	  
	          if(pwdNo.equals("") && loginJuminNo.equals(pswd)){ // 비밀번호 설정되지 않을경우         	  
	        	  pwdExistsYn = "N";
	        	  pwdYn = "Y";

	        	  //out.println("<script >alert('로그인1');</script>");
	        	  
	          }else{ 
	        	  //out.println("<script >alert('로그인2');</script>");
	        	  
	        	  if(pwdNo.equals(enPswd)){
	        		  pwdYn = "Y";

		        	  //out.println("<script >alert('로그인3');</script>");
	        		  
	        		  //세션 설정	        		  
	        		  session.setAttribute("EMP_NO",loginEmpNo);
	        		  session.setAttribute("EMP_NM",loginEmpNm);
	        		  session.setAttribute("DEPT_CD",loginDeptCd);
	        		  session.setAttribute("DEPT_NM",loginDeptNm);
	        		  
	        		  session.setAttribute("ESTM_YEAR",loginEvalYear);
                      session.setAttribute("ESTM_NUM",loginEvalNum);

	        		    
	        	  }else{
	        		  pwdYn = "N";	// 암호오류
	        	  }
	        	  
	          }
          }
     }catch(SQLException e) {
              out.println(e.toString());
     } finally {
         if(con != null) try{con.close();}       catch(Exception e){}
         if(pstmt != null) try{pstmt.close();}   catch(SQLException sqle){}
         if(rs !=null) rs.close();
 	}  	  
          
     //System.out.println("pwdExistsYn="+pwdExistsYn);
     //System.out.println("sEvEscGbn="+sEvEscGbn);
     //System.out.println("pwdNo="+pwdNo);
     //System.out.println("pwdYn="+pwdYn);
  
     //if(sEvEscGbn.equals("N")){    	
     //    out.println("<script >alert('귀하는 다면평가 대상이 아닙니다. 확인하시기 바랍니다.');</script>");
     //    out.println("<script >location.href='estmLogin.jsp';</script>");
     //    return;      
     //}
     sEvEscGbn = "Y";
     
     if(pwdExistsYn.equals("N") && pwdYn.equals("Y")){  // 주민번호뒷자리와    화면에 입력한 비밀번호 같을경우 비밀번호 초기화면으로 이동 
    	 out.println("<script >alert('비밀번호 설정 페이지로 이동합니다.');</script>");
    	 out.println("<script >location.href='passChange.jsp?userId="+userId+"';</script>");

     }else{
         if(pwdYn.equals("Y")){  // 로그인 성공
        	
        	 response.sendRedirect("estmMain.jsp"); 
         
         }else{ // 패스워드 X , 아이디 x 
        	 
        	 if(sEvEscGbn.equals("N")){
        		 
                 out.println("<script >alert('귀하는 다면평가 대상이 아닙니다. 확인하시기 바랍니다..');</script>");
        	 }else{        	
        		 if (pwdNo.equals("")) {
                 	out.println("<script >alert('로그인 정보를 확인하세요.');</script>");
        		 } else {
                  	out.println("<script >alert('암호가 일치하지 않습니다. 로그인 정보를 확인하세요.');</script>");
        		 }
        	 }        	 
  
             out.println("<script >location.href='estmLogin.jsp';</script>");
        
         
         }
         
     }
%>


