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
	
	String[] aEmpNo =  request.getParameterValues("empNo");
	
	String[] aItemCd =  request.getParameterValues("itemCd");
	
	String[] aMultEstmGrp =  request.getParameterValues("multEstmGrp");
	String[] aGrdScr =  request.getParameterValues("grdScr");
	
	String[] aGrd =  request.getParameterValues("estmScrNm");
	
	String distrCd = request.getParameter("distrCd");
	String sCfmYn = request.getParameter("cfmYn");
	
	 
    String tmpItmScr ="";
    
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    String query = "";
    
	if(aEmpNo != null && aEmpNo.length > 0){
        
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
                out.println("<script >parent.location.href='estmCfm.jsp';</script>");
                
                return;
                
            }
	    	
	    	
	    	//평가정보 저장 
			for(int i =0 ; i < aEmpNo.length ;i++){
		
				if(aItemCd != null && aItemCd.length >0){
			            query ="";
			            
			            query += "   UPDATE TBRF_EV_WK_MULT SET   ";
			            query += "       ESTM_SCR = ?   ";
			            query += "      ,ESTM_GRD = ?   ";
			            
			            query += "      ,CFM_YN = ?   ";         
			            
			            query += "      ,UPDT_ID = ?   ";
			            query += "      ,UPDT_DT = SYSDATE   ";
			            query += "   WHERE ESTM_YEAR = ?   ";
			            query += "      AND ESTM_NUM = ?   ";
			            query += "      AND ESTM_DEPT = ?   ";
			            query += "      AND ESTM_EMP = ?   ";
			            query += "      AND EMP_NO = ?    ";
			              
			            pstmt = con.prepareStatement(query);
	
			             k =1;
			            pstmt.setString(k++, aGrdScr[i]);
			            pstmt.setString(k++, aGrd[i]);
			            
			            //확정구분
			            pstmt.setString(k++, sCfmYn);
			            
			            
			            pstmt.setString(k++, sEvalEmp);
	
			            pstmt.setString(k++, sEvalYear);
			            pstmt.setString(k++, sEvalNum);
			            pstmt.setString(k++, sEvalDept);
			            pstmt.setString(k++, sEvalEmp);
			            pstmt.setString(k++, aEmpNo[i]);

			            
	
			            int result = pstmt.executeUpdate();
		            

					for(int j =0 ; j < aItemCd.length ;j++){
		            
						
		
			            tmpItmScr = request.getParameter((i+1)+ "_itm_"+ aItemCd[j] +"_scr");
			            
		                   query ="";
		                    
		                   query += "   UPDATE TBRF_EV_WK_MULT_DT SET   ";
		                   query += "       DISTR_CD=?   ";
		                   query += "      ,GRD=?   ";
		                   query += "      ,UPDT_ID =?   ";
		                   query += "      ,UPDT_DT = SYSDATE   ";
		                   query += "   WHERE ESTM_YEAR = ?   ";
	                       query += "      AND ESTM_NUM = ?   ";
		                   query += "      AND ESTM_DEPT = ?   ";
		                   query += "      AND ESTM_LITEM_CD = ?    ";
		                   query += "      AND ESTM_EMP = ?   ";
		                   
		                   query += "      AND MULT_ESTM_GRP = ?   ";
		                   query += "      AND EMP_NO = ?   ";
	
		                      
		                    pstmt = con.prepareStatement(query);
		
		                    k =1;
		                    pstmt.setString(k++, distrCd);
		                    pstmt.setString(k++, tmpItmScr);
		                    pstmt.setString(k++, sEvalEmp);
		
		                    pstmt.setString(k++, sEvalYear);
		                    pstmt.setString(k++, sEvalNum);
		                    pstmt.setString(k++, sEvalDept);
		                    pstmt.setString(k++, aItemCd[j]);
		                    pstmt.setString(k++, sEvalEmp);
		                    
		                    pstmt.setString(k++, aMultEstmGrp[i]);
		                    pstmt.setString(k++, aEmpNo[i]);
	
		                        
		                    pstmt.executeUpdate();
	
					}
		        
		        }
					
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
	out.println("<script >alert('저장되었습니다.');</script>");
	out.println("<script >parent.location.href='estmCfm.jsp';</script>");
%>

