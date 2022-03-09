<%@ page language="java" import="java.util.*, java.sql.*" session="true" contentType="text/html;charset=EUC_KR" %>
<%@ include file="comDBManager.jsp" %>
<%
    String empNo = (String)session.getAttribute("EMP_NO");
    String empNm = (String)session.getAttribute("EMP_NM");
    String deptCd = (String)session.getAttribute("DEPT_CD");
    String deptNm = (String)session.getAttribute("DEPT_NM");
    
    String evalYear = (String)session.getAttribute("ESTM_YEAR");
    //String evalNum  = "1";
    
    String evalNum  = (String)session.getAttribute("ESTM_NUM");
     
    
    
    //������ ������¥
    String sLastUpdtDtm = request.getParameter("lastUpdtDtm");
    if(sLastUpdtDtm == null )sLastUpdtDtm ="";
    
    //������ ������¥(DB)
    String sDblastUpdtDtm ="";
   

%>

<%
    String sEvalYear   = evalYear;
    String sEvalNum    = evalNum;
    String sEvalDept   = deptCd;
    String sEvalEmp    = empNo;
    
   
    String sExptEmpNo = (String)request.getParameter("exptEmpNo");
    String sExptEmpYn = (String)request.getParameter("exptEmpYn");
    
    if(sExptEmpNo == null ) sExptEmpNo= "";
    if(sExptEmpYn == null || sExptEmpYn.equals("")) sExptEmpYn= "N";
    

    
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    String query = "";
    
    if(!sExptEmpNo.equals("")){
        
        try { 
            con = DriverManager.getConnection(jdbcUrl, dbUser, dbPass);
            
        } catch(SQLException e) {
             
            out.println(e.toString());
            e.printStackTrace();   
            return;
        }
        
        try{

        	
            //������ ���� �ð�(���ں�)
            query ="";
            query += "       SELECT    \n";
            query += "            MAX(UPDT_DT) UPDT_DT  \n";
            query += "       FROM TBRF_EV_WK_MULT   \n";
            query += "   WHERE ESTM_YEAR = ?   ";
            query += "      AND ESTM_NUM = ?   ";
            query += "      AND ESTM_DEPT = ?   ";
            query += "      AND ESTM_EMP = ?   ";
              
            pstmt = con.prepareStatement(query);

            int k =1;
            
            pstmt.setString(k++, sEvalYear);
            pstmt.setString(k++, sEvalNum);
            pstmt.setString(k++, sEvalDept);
            pstmt.setString(k++, sEvalEmp);

            rs = pstmt.executeQuery();
            
            if(rs.next()){       
            	sDblastUpdtDtm = rs.getString("UPDT_DT");
            }
            
            
            
            
            //������ ���� �ð��� �������� ��� ����ȭ������ �̵� 
            if(!sLastUpdtDtm.equals(sDblastUpdtDtm)){
                out.println("<script >alert('���� ����ڰ� �ֽ��ϴ�. �ٽ� ��ȸ �� ���� �ٶ��ϴ�.');</script>");
                out.println("<script >parent.location.href='estmCfm.jsp';</script>");
                
                return;
            	
            }
            
           
            
            
            //���ܿ���, ���ܻ��� ���� 
            query ="";
            
            query += "   UPDATE TBRF_EV_WK_MULT SET   ";
            query += "       EXPT_YN = ?   ";
            
 
            
            query += "      ,UPDT_ID = ?   ";
            query += "      ,UPDT_DT = SYSDATE   ";
            
            if(sExptEmpYn.equals("N")){

            	query += "      ,EXPT_RSN = '' ";
            	
            	
            }else{
                query += "      ,ESTM_GRD = ''  ";
                query += "      ,ESTM_SCR =  0   ";
            	
            }
            
            query += "   WHERE ESTM_YEAR = ?   ";
            query += "      AND ESTM_NUM = ?   ";
            query += "      AND ESTM_DEPT = ?   ";
            query += "      AND ESTM_EMP = ?   ";
            query += "      AND EMP_NO = ?   ";

               
             pstmt = con.prepareStatement(query);

              k =1;
             pstmt.setString(k++, sExptEmpYn);
             pstmt.setString(k++, sEvalEmp);


             pstmt.setString(k++, sEvalYear);
             pstmt.setString(k++, sEvalNum);
             pstmt.setString(k++, sEvalDept);
             pstmt.setString(k++, sEvalEmp);
             
             pstmt.setString(k++, sExptEmpNo);

                 
             pstmt.executeUpdate();        
                    

        
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
    
    }
    
    

    


%>
<%
   // out.println("<script >alert('����Ǿ����ϴ�.');</script>");
    out.println("<script >parent.location.href='estmCfm.jsp?exptGbnYn=Y';</script>");
%>

