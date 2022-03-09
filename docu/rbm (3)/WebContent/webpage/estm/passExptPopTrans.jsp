<%@ page language="java" import="java.util.*, java.sql.*" session="true" contentType="text/html;charset=EUC_KR"  pageEncoding="EUC-KR"%>
<%@ include file="comDBManager.jsp" %>
<%
    String empNo = (String)session.getAttribute("EMP_NO");
    String empNm = (String)session.getAttribute("EMP_NM");
    String deptCd = (String)session.getAttribute("DEPT_CD");
    String deptNm = (String)session.getAttribute("DEPT_NM");
    
    String evalYear = (String)session.getAttribute("ESTM_YEAR");
   // String evalNum  = "1";
    
    String evalNum  = (String)session.getAttribute("ESTM_NUM");
    
    //마지막 수정날짜
    String sLastUpdtDtm = request.getParameter("lastUpdtDtm");
    if(sLastUpdtDtm == null )sLastUpdtDtm ="";
    
    //마지막 수정날짜(DB)
    String sDblastUpdtDtm ="";

%>

<%
    String sEvalYear   = evalYear;
    String sEvalNum    = evalNum;
    String sEvalDept   = deptCd;
    String sEvalEmp    = empNo;
    
   
    String sExptEmpNo = (String)request.getParameter("exptEmpNo");
    String sExptRsn = new String(request.getParameter("exptRsn").getBytes("ISO8859-1"),"KSC5601"); 

    
    if(sExptEmpNo == null ) sExptEmpNo= "";
    if(sExptRsn == null) sExptRsn= "";
    

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
        	
            //마지막 저장 시간(평가자별)
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
            
            
           
            
            //마지막 저장 시간과 같지않을 경우 이전화면으로 이동 
            if(!sLastUpdtDtm.equals(sDblastUpdtDtm)){
                out.println("<script >alert('동시 사용자가 있습니다. 다시 조회 후 저장 바랍니다.');</script>");
                out.println("<script >parent.setParentRsn();</script>");
                
                return;
                
            }
            

            query ="";
            
            query += "   UPDATE TBRF_EV_WK_MULT SET   ";
            query += "       EXPT_RSN = ?   ";
            
            query += "      ,ESTM_GRD = ''  ";
            query += "      ,ESTM_SCR =  0   ";
            
            query += "      ,UPDT_ID = ?   ";
            query += "      ,UPDT_DT = SYSDATE   ";
            

            query += "   WHERE ESTM_YEAR = ?   ";
            query += "      AND ESTM_NUM = ?   ";
            query += "      AND ESTM_DEPT = ?   ";
            query += "      AND ESTM_EMP = ?   ";
            query += "      AND EMP_NO = ?   ";

               
             pstmt = con.prepareStatement(query);

              k =1;
             pstmt.setString(k++, sExptRsn);
             pstmt.setString(k++, sEvalEmp);


             pstmt.setString(k++, sEvalYear);
             pstmt.setString(k++, sEvalNum);
             pstmt.setString(k++, sEvalDept);
             pstmt.setString(k++, sEvalEmp);
             
             pstmt.setString(k++, sExptEmpNo);

                 
             pstmt.executeUpdate();        
                    

             
             //마지막 저장 시간(평가자별)
             query ="";
             query += "       SELECT    \n";
             query += "            MAX(UPDT_DT) UPDT_DT  \n";
             query += "       FROM TBRF_EV_WK_MULT   \n";
             query += "   WHERE ESTM_YEAR = ?   ";
             query += "      AND ESTM_NUM = ?   ";
             query += "      AND ESTM_DEPT = ?   ";
             query += "      AND ESTM_EMP = ?   ";
               
             pstmt = con.prepareStatement(query);

              k =1;
             
             pstmt.setString(k++, sEvalYear);
             pstmt.setString(k++, sEvalNum);
             pstmt.setString(k++, sEvalDept);
             pstmt.setString(k++, sEvalEmp);

             rs = pstmt.executeQuery();
             
             if(rs.next()){       
                 sDblastUpdtDtm = rs.getString("UPDT_DT");
             }
        
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

    out.println("<script >parent.setParentRsn('"+sDblastUpdtDtm+"');</script>");
%>

