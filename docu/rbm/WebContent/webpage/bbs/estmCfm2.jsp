<%@ page language="java" import="java.util.*, java.sql.*,java.lang.*" session="true" contentType="text/html;charset=euc-kr" %>
<%@ include file="comDBManager.jsp" %>
<%
	
    

%>

<% 

    PreparedStatement pstmt = null;
    ResultSet rs = null;
    
    ArrayList distrList = new ArrayList(10);    
    ArrayList wkMultList = new ArrayList(10);    
    ArrayList itmList = new ArrayList(10);
 
    
    
    try { 
        con = DriverManager.getConnection(jdbcUrl, dbUser, dbPass);
    } catch(SQLException e) {
         
        out.println(e.toString());
        e.printStackTrace();   
        return;
    }
    
    

     try {
    	 
         //로그  기록 
         String query ="";
    
         //마지막 저장 시간(평가자별)
         query ="";
         query += "       SELECT    \n";
         query += "            *  \n";
         query += "       FROM BBS_DOC   \n";
         query += "   WHERE BBS_ID = 'B0010832'   ";
         query += "      AND doc_yearmon = '201201'   ";

           
         pstmt = con.prepareStatement(query);

         int k =1;
         
         //pstmt.setString(k++, sEvalYear);
        
         rs = pstmt.executeQuery();
         
         if(rs.next()){       
            out.println("doc_subject ==>" + rs.getString("DOC_SUBJECT"));
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
     
 
     

%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title> KSPO 게시판 조회 </title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr" />
<link rel="stylesheet" href="css/default.css" type="text/css" />

<![If IE 7]>
<link rel="stylesheet" href="css/ie7.css" type="text/css" />
<![endif]>
<script src="zoom.js" type="text/javascript"></script>
<script language="JavaScript" type="text/JavaScript">
    
</script>
</head>
<body >
<OBJECT id=ActiveFileMgr border=0 name=ActiveFileMgr 
codeBase="http://jupiter.kspo.or.kr:80/UFWeb/AttVolume00/ClientPrg/ActiveFileManager.cab#version=2,2,0,21" 
classid=clsid:CF48A140-65BD-439A-9BFB-527703BD9BB6 width=0 height=0><PARAM NAME="AttachSizeLimit" VALUE="10"><PARAM NAME="AttachNumberLimit" VALUE="3"><PARAM NAME="ServerAddr" VALUE="jupiter.kspo.or.kr"><PARAM NAME="ServerFolder" VALUE="/arraydata/UFWeb/AttVolume01/Bbs/20120113"><PARAM NAME="ServerBaseFolder" VALUE="/UFWeb/AttVolume01/Bbs/20120113"><PARAM NAME="ServerUser" VALUE="008033072089125088056058"><PARAM NAME="ServerPassword" VALUE="008033072112121089058099005011056"><PARAM NAME="ServerPort" VALUE="80"></OBJECT>
<form name="estmForm" method="post" action="">
  
 </form>

</body>
</html>
