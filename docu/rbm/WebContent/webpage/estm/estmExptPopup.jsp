<%@ page language="java" import="java.util.*, java.sql.*" session="true" contentType="text/html;charset=EUC_KR" %>
<%@ include file="comDBManager.jsp" %>
<%
    String empNo = (String)session.getAttribute("EMP_NO");
    String empNm = (String)session.getAttribute("EMP_NM");
    String deptCd = (String)session.getAttribute("DEPT_CD");
    String deptNm = (String)session.getAttribute("DEPT_NM");
    
    String evalYear = (String)session.getAttribute("ESTM_YEAR");
    String evalNum  = (String)session.getAttribute("ESTM_NUM");
    
    String sOpenYn ="N";
    String sEstmEndYn ="N";
    
    
    String sEvalYear   = evalYear;
    //String sEvalNum    = "1";
    String sEvalNum    = evalNum;
    
    String sEvalDept   = deptCd;
    String sEvalEmp    = empNo;
    
    String sExptEmpNo  = request.getParameter("exptEmpNo");
    if(sExptEmpNo ==null) sExptEmpNo= "";
    
    String sRowNum  = request.getParameter("rowNum");
    
    String sExptRsn = "";
    String sCfmYn = "";
    
    String sLastUpdtDtm ="";
    
    
%>
<%
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    
    
    try { 
        con = DriverManager.getConnection(jdbcUrl, dbUser, dbPass);
    } catch(SQLException e) {
         
        out.println(e.toString());
        e.printStackTrace();   
        return;
    }



   try {
    
        
       //회차조회 
       String query = "";
       
       query ="";
       query += "       SELECT    \n";
       query += "            EXPT_RSN  \n";
       query += "           ,CFM_YN   \n";
       query += "       FROM TBRF_EV_WK_MULT   \n";
       query += "   WHERE ESTM_YEAR = ?   ";
       query += "      AND ESTM_NUM = ?   ";
       query += "      AND ESTM_DEPT = ?   ";
       query += "      AND ESTM_EMP = ?   ";
       query += "      AND EMP_NO = ?   ";
         
       pstmt = con.prepareStatement(query);

       int k =1;
       
       pstmt.setString(k++, sEvalYear);
       pstmt.setString(k++, sEvalNum);
       pstmt.setString(k++, sEvalDept);
       pstmt.setString(k++, sEvalEmp);
       pstmt.setString(k++, sExptEmpNo);       
       
       rs = pstmt.executeQuery();
       
 
       
       if(rs.next()){       
    	     sExptRsn = rs.getString("EXPT_RSN");
    	     sCfmYn = rs.getString("CFM_YN");
       }
      
  
       if(sExptRsn == null) sExptRsn= "";
       if(sCfmYn == null || sCfmYn.equals("")) sCfmYn = "N";
       
       
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
    	   sLastUpdtDtm = rs.getString("UPDT_DT");
       }
       
       System.out.println("sLastUpdtDtm"+sLastUpdtDtm);
       
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
 
 
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title> 지원직 및 일용직계약 평가시스템 </title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr" />
<link rel="stylesheet" href="css/defaultPop.css" type="text/css" />
<script language="JavaScript" type="text/JavaScript">
    function formInit(){
            estmForm.exptRsn.focus();
    }
    
    function strCharByte(chStr){
        var nStrByte = 0;
        var stmpCh;

        for(var i = 0; i <= chStr.length;i++){
               
               stmpCh = escape(chStr.charAt(i));

               if(stmpCh.substring(0,2) == '%u'){
                  if(stmpCh.substring(2,4) == '00')
                     nStrByte += 1;
                   else
                     nStrByte += 2; //한글    

              } else if(stmpCh.substring(0,1) == '%'){
                  if(parseInt(stmpCh.substring(1,3), 16) >127 ) 
                        nStrByte += 2;
                   else
                        nStrByte += 1;
             
               } else{

                     nStrByte += 1;
               }

        }

        return nStrByte;

    }


    function saveExptRsn(){
    
         var mlength="200";
         var valueLength=strCharByte(estmForm.exptRsn.value)-1;

         if (estmForm.exptRsn.value.length>=mlength) {
              alert("제외사유는 한글 100자로 제한되어 있습니다.");
              estmForm.exptRsn.focus();
              return;
          
         }

        estmForm.target = "HiddenFrame";
        estmForm.action="passExptPopTrans.jsp";
        estmForm.submit();
    }
    
    
    function setParentRsn(sLastDtm){        

        var obj =opener.document.getElementById('<%=sRowNum%>'+'_exptRSN');
        var objLastUpDt =opener.document.getElementById("lastUpdtDtm");

        obj.value = estmForm.exptRsn.value;
        objLastUpDt.value=sLastDtm;

        this.close();
    }
</script>
</head>
<body onload="formInit()">
<form name="estmForm" method="post" action="">
 <input type ="hidden" name="exptEmpNo" value="<%=sExptEmpNo %>">
 <input type ="hidden" name="lastUpdtDtm" value="<%=sLastUpdtDtm %>">
 
 <div class="body">
     <div class="grade_page">
        <div class="grade">
         <div class="gray_grade" style="height:50;width:150">
            <ui><b>▶다면평가 제외사유 입력</b></ui>
         </div>
		  <table style="width:496;height:190" border="1">
		      <thead>
		          <tr><th valign="center" style="width:480px;">제외사유</th></tr>
		      </thead>
		      <tbody>
		          <tr>
		              <td style="width:496px;padding-bottom:10px"><textarea name="exptRsn" maxlength="200" style="width:496px;height:105px;"><%=sExptRsn %></textarea></td>
		           </tr>
		           <tr>
		              <td align="right" style="width:496px;padding-top:3px"><%if(!sCfmYn.equals("Y")){ %><a href="javascript:saveExptRsn()"><img src="img/btn_save.gif" alt="저장"></a><%} %>
		              <a href="javascript:this.close()"><img src="img/btn_close.gif" alt="저장"></a> </td>
		           </tr>                       
		      </tbody>
          </table>
		</div>
	  </div>
 </div>
 </form>
 <iframe name="HiddenFrame" src="" width="0" height="0" frameborder="0" scrolling="no"></iframe>
</body>
</html>
