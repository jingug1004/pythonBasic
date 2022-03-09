<%@ page language="java" import="java.util.*, java.sql.*,java.lang.*,javax.sql.*,javax.naming.*,java.io.*,java.net.*" session="true" contentType="text/html;charset=euc-kr" %>
<%@ include file="../util.jsp" %>
<%@ include file="../comDBManager.jsp" %>
<%
   String sMeetCd  = nullToStr(request.getParameter("MEET_CD"),"");
   String sRaceDay = nullToStr(request.getParameter("RACE_DAY"),"");
   int iMaxCnt  = strToInt(request.getParameter("MAX_CNT"),0);

   //sMeetCd  = "003";
   //sRaceDay = "20161102";
%>
<% 

	Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    String query ="";
    int i =1;
    
    ArrayList salesList = new ArrayList(10);  
    
    try { 
    	 Context context = new InitialContext();
         DataSource ds = (DataSource)context.lookup(SDataSource);
         con = ds.getConnection();
    } catch(SQLException e) {
         
        out.println(e.toString());
        e.printStackTrace();   
        return;
    }
    
    Vector rVect001 = new Vector();
    Vector rVect002 = new Vector(); 
    Vector rVect003 = new Vector();
    Vector rVect004 = new Vector();
    int cnt001 = 0;
    int cnt002 = 0;
    int cnt003 = 0;
    int cnt004 = 0;

     try {
         
    	 query ="";
    	 query += " SELECT TMS, SELL_CD, COUNT(RACE_NO) CNT FROM (           \n";
    	 query += " 	SELECT TMS, SELL_CD, RACE_NO FROM VW_PC_PAYOFFS      \n";
    	 query += " 	WHERE 1=1                                            \n";
    	 query += " 	AND STND_YEAR = SUBSTR(?,0,4)                        \n";
    	 query += " 	AND MEET_CD = ?                                      \n";
    	 query += " 	AND DAY_ORD = '1'                                    \n";
    	 query += " 	GROUP BY TMS, SELL_CD, RACE_NO                       \n";
    	 query += " )                                                        \n";
    	 query += " GROUP BY TMS, SELL_CD                                    \n";
    	 query += " ORDER BY 1,2                                             \n";
         
         pstmt = con.prepareStatement(query);

         int k =1;
         
         pstmt.setString(k++, sRaceDay);
         pstmt.setString(k++, sMeetCd);

         rs = pstmt.executeQuery();
         String tmpSellCd = "";
         while (rs.next()) {
        	Hashtable rHash = new Hashtable();
        	tmpSellCd = rs.getString("SELL_CD");
 			
 			rHash.put("TMS"      ,rs.getString("TMS"));
 			rHash.put("SELL_CD"      ,rs.getString("SELL_CD"));
 			rHash.put("CNT" ,nullToStr(rs.getString("CNT"),"0"));
 			
 			if("01".equals(tmpSellCd)) {
 				rVect001.addElement(rHash);
 				cnt001++;
 			}
 			if("02".equals(tmpSellCd)) {
 				rVect002.addElement(rHash);
 				cnt002++;
 			}
 			if("03".equals(tmpSellCd)) {
 				rVect003.addElement(rHash);
 				cnt003++;
 			}
 			if("04".equals(tmpSellCd)) {
 				rVect004.addElement(rHash);
 				cnt004++;
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

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<link href="../css/default.css?v=20140119" rel="stylesheet" type="text/css">
<link href="../css/sub_contents.css?v=20140119" rel="stylesheet" type="text/css">

<title>Insert title here</title>
</head>
<body>
<table cellpadding="0" cellspacing="0" class="tb_data4 playResultList">
<thead>
<tr>
<th scope="col">회차</th>
<%
for(i=0; i<iMaxCnt; i++) {	
	out.print("<th scope='col'>"+(i+1)+"</th>");
}
%>
</tr>
</thead>

<%
if ("001".equals(sMeetCd)) {
	out.print("<tr>");
	out.print("<td class=\"chang\">광명</td>");
	for(i=0; i<iMaxCnt; i++) {	
		out.print("<td class=\"chang\">"+getData(rVect001,i,"CNT")+"</td>");
	}
	out.print("</tr>");
}	

if ("003".equals(sMeetCd)) {
	out.print("<tr>");
	out.print("<td class=\"chang\">경정</td>");
	for(i=0; i<iMaxCnt; i++) {	
		out.print("<td class=\"chang\">"+getData(rVect003,i,"CNT")+"</td>");
	}
	out.print("</tr>");
}

	out.print("<tr>");
	out.print("<td class=\"chang\">창원</td>");
	for(i=0; i<iMaxCnt; i++) {	
		out.print("<td class=\"chang\">"+getData(rVect002,i,"CNT")+"</td>");
	}
	out.print("</tr>");
	
	out.print("<tr>");
	out.print("<td class=\"chang\">부산</td>");
	for(i=0; i<iMaxCnt; i++) {	
		out.print("<td class=\"chang\">"+getData(rVect004,i,"CNT")+"</td>");
	}
	out.print("</tr>");

%>
</table>
</body>
</html>