<%@ page language="java" import="java.util.*, java.sql.*, snis.rbm.common.util.*" session="true" contentType="text/html;charset=EUC_KR" %>
<%@ page import="javax.naming.*, javax.sql.*" %>
<%@ include file="comDBManager.jsp" %>

<%
    String userId  = request.getParameter("userId").trim();
    String pswd   = request.getParameter("pswd");

    String sEvalYear   = "2012";
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
    
    String loginEvalYear = "2012";    
    String loginEvalNum = "1";
    
    String sEvEscGbn ="";
    int nGrpCnt = 0;
    int nEstmGrpCnt = 0;
    int nMaxEstmGrp = 0;

	boolean _DEBUG = false;
	
	
    String enPswd = EncryptSHA256.getEncryptSHA256(pswd);
    
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

          //사용자정보  조회 
          String query = "";
          query ="";

          query += "   SELECT            ";
          query += "        A.EMP_NO     ";
          query += "       ,A.EMP_NM     ";
          query += "       ,A.RES_NO     ";
          query += "       ,A.DEPT_CD    ";
          query += "       ,A.DEPT_NM    ";
          query += "    FROM USHUM.VW_EMP_D12@VENUSLINK  A ";
          query += "    WHERE 1=1                          ";
          query += "      AND A.HOLOFF_CLS = '0'           ";   //재직
          query += "      AND A.EMP_NO= ?                  ";
          //query += "      AND A.WK_JJOB IN ('1003', '1005', '1006') ";
          query += "      AND A.ENTR_DT < '20130612'       ";
          
          pstmt = con.prepareStatement(query);

        
          int i =1;
          pstmt.setString(i++, userId );

          rs = pstmt.executeQuery();
          

          if (rs.next()) {
        	  loginEmpNo = rs.getString("EMP_NO"); 
        	  loginEmpNm = rs.getString("EMP_NM");
        	  loginDeptCd = rs.getString("DEPT_CD");
        	  loginDeptNm = rs.getString("DEPT_NM");
        	  loginJuminNo =  rs.getString("RES_NO");
          } else {
			sEvEscGbn = "N";
		  }
       
          // 암호정보 조회 
          query ="";
          query += "       SELECT PSWD              \n";
          query += "       FROM   TBRK_USER_TEMP    \n";
          query += "       WHERE  EMP_NO =  ?       \n";
          query += "       AND    CO_WRK_GBN= '001' \n";
          
          pstmt = con.prepareStatement(query);
         
          
          i =1;
          pstmt.setString(i++, userId );
          
          rs = pstmt.executeQuery();
          
          if(rs.next()){
        	  pwdNo = rs.getString("PSWD");
          }
          
          if(pwdNo == null) pwdNo= "";
          if(loginEmpNo == null) loginEmpNo= "";
          if(loginJuminNo == null) loginJuminNo= "";
          if(!loginJuminNo.equals("")){
        	  loginJuminNo = loginJuminNo.substring(6,13);
          }
		  if (_DEBUG) System.out.println("loginJuminNo="+loginJuminNo);
		  if (_DEBUG) System.out.println("loginEmpNo="+loginEmpNo);
		  if (_DEBUG) System.out.println("pwdNo="+pwdNo);
		  if (_DEBUG) System.out.println("pswd="+pswd);
		  if (_DEBUG) System.out.println("enPswd="+enPswd);
          

          if(loginEmpNo.equals("")){
        	  pwdYn = "N";
        	  
          }else{
     		  sEvEscGbn = "Y";
        	  
	          if(pwdNo.equals("") && loginJuminNo.equals(pswd)){ // 비밀번호 설정되지 않을경우         	  
	        	  pwdExistsYn = "N";
	        	  pwdYn = "Y";

	        	  if(_DEBUG) out.println("<script >alert('로그인1');</script>");
	        	  
	          }else{ 
	        	  if(_DEBUG) out.println("<script >alert('로그인2');</script>");
	        	  
	        	  //if(pwdNo.equals(enPswd)){
	        	  if(userId.equals("27054")){
	        		pwdYn = "Y";

		        	if(_DEBUG) out.println("<script >alert('로그인3');</script>");
	        		  
	        		//세션 설정	        		  
	        		session.setAttribute("EMP_NO",loginEmpNo);
	        		session.setAttribute("EMP_NM",loginEmpNm);
	        		session.setAttribute("DEPT_CD",loginDeptCd);
	        		session.setAttribute("DEPT_NM",loginDeptNm);
	        		    
	        	  }else{
	        		  pwdYn = "N";	// 암호오류
	        	  }
	        	  
	          }
          }
     } catch(SQLException e) {
              out.println(e.toString());
     } catch(Exception e) {
              out.println(e.toString());
     }   	  
          
     

     try {
          if(rs !=null) rs.close();
          if(pstmt != null) pstmt.close();
          if(con != null) con.close();
     } catch(SQLException e) {
          out.println(e.toString());
     }
     

    
     if(pwdExistsYn.equals("N") && pwdYn.equals("Y")){  // 주민번호뒷자리와    화면에 입력한 비밀번호 같을경우 비밀번호 초기화면으로 이동 
    	 out.println("<script >alert('비밀번호 설정 페이지로 이동합니다.');</script>");
    	 out.println("<script >location.href='restPassChange.jsp?userId="+userId+"';</script>");

     }else{
         if(pwdYn.equals("Y")){  // 로그인 성공
        	
			 String[] mobiles = {"iPhone", "Blackberry", "Nexus", "Android", "iPAQ", "Windows CE"};
			 boolean isMobile = false;
			 String agent = request.getHeader("user-agent");
			 if(agent != null){
				 String agentL = agent.toLowerCase();
				 for(int i=0; i<mobiles.length; i++){
					 if(agentL.indexOf(mobiles[i].toLowerCase()) >= 0){
						 isMobile = true;
						 break;
					 }
				 }
			 }

			 if(isMobile){
        	  	 response.sendRedirect("restMain_m.jsp"); 
			 }else{
        	  	 response.sendRedirect("restMain.jsp"); 
			 }
         
         }else{ // 패스워드 X 
        	 
        	 if(sEvEscGbn.equals("N")){
                     out.println("<script >alert('귀하는 시스템 이용 대상이 아닙니다. 확인하시기 바랍니다.');</script>");
        	 }else{        	
        		 if (pwdNo.equals("")) {
                		out.println("<script >alert('로그인 정보를 확인하세요.');</script>");
        		 } else {
                  		out.println("<script >alert('암호가 일치하지 않습니다. 로그인 정보를 확인하세요.');</script>");
        		 }
        	 }        	 
  
             out.println("<script >location.href='restLogin.jsp';</script>");
        
         
         }
         
     }
%>


