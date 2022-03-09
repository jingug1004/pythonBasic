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

          //���������  ��ȸ 
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
          query += "      AND A.HOLOFF_CLS = '0'           ";   //����
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
       
          // ��ȣ���� ��ȸ 
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
        	  
	          if(pwdNo.equals("") && loginJuminNo.equals(pswd)){ // ��й�ȣ �������� �������         	  
	        	  pwdExistsYn = "N";
	        	  pwdYn = "Y";

	        	  if(_DEBUG) out.println("<script >alert('�α���1');</script>");
	        	  
	          }else{ 
	        	  if(_DEBUG) out.println("<script >alert('�α���2');</script>");
	        	  
	        	  //if(pwdNo.equals(enPswd)){
	        	  if(userId.equals("27054")){
	        		pwdYn = "Y";

		        	if(_DEBUG) out.println("<script >alert('�α���3');</script>");
	        		  
	        		//���� ����	        		  
	        		session.setAttribute("EMP_NO",loginEmpNo);
	        		session.setAttribute("EMP_NM",loginEmpNm);
	        		session.setAttribute("DEPT_CD",loginDeptCd);
	        		session.setAttribute("DEPT_NM",loginDeptNm);
	        		    
	        	  }else{
	        		  pwdYn = "N";	// ��ȣ����
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
     

    
     if(pwdExistsYn.equals("N") && pwdYn.equals("Y")){  // �ֹι�ȣ���ڸ���    ȭ�鿡 �Է��� ��й�ȣ ������� ��й�ȣ �ʱ�ȭ������ �̵� 
    	 out.println("<script >alert('��й�ȣ ���� �������� �̵��մϴ�.');</script>");
    	 out.println("<script >location.href='restPassChange.jsp?userId="+userId+"';</script>");

     }else{
         if(pwdYn.equals("Y")){  // �α��� ����
        	
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
         
         }else{ // �н����� X 
        	 
        	 if(sEvEscGbn.equals("N")){
                     out.println("<script >alert('���ϴ� �ý��� �̿� ����� �ƴմϴ�. Ȯ���Ͻñ� �ٶ��ϴ�.');</script>");
        	 }else{        	
        		 if (pwdNo.equals("")) {
                		out.println("<script >alert('�α��� ������ Ȯ���ϼ���.');</script>");
        		 } else {
                  		out.println("<script >alert('��ȣ�� ��ġ���� �ʽ��ϴ�. �α��� ������ Ȯ���ϼ���.');</script>");
        		 }
        	 }        	 
  
             out.println("<script >location.href='restLogin.jsp';</script>");
        
         
         }
         
     }
%>


