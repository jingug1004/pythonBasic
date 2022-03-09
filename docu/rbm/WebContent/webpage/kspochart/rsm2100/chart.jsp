<%@ page language="java" import="java.util.*, java.sql.*,java.lang.*,javax.sql.*,javax.naming.*,java.io.*,java.net.*" session="true" contentType="text/html;charset=euc-kr" %>
<%@ include file="../util.jsp" %>
<%@ include file="../comDBManager.jsp" %>
<%
/*
매출조회 > 매출현황 > 매출비교 그래프
*/
   String sMeetCd  = nullToStr(request.getParameter("MEET_CD"),"");
   String sRaceDay = nullToStr(request.getParameter("RACE_DAY"),"");
   String sAutoYn = nullToStr(request.getParameter("AUTO_YN"),"N");
   String sCalendar = "";
   if(sRaceDay.length() == 8 ) 
	   sCalendar = sRaceDay.substring(0,4) + "-" + sRaceDay.substring(4,6)  + "-" + sRaceDay.substring(6);
   
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
    
    Vector rVect000 = new Vector();
    Vector rVect001 = new Vector(); 
    Vector rVect002 = new Vector();
    Vector rVect004 = new Vector();
    int cnt000 = 0;
    int cnt001 = 0;
    int cnt002 = 0;
    int cnt004 = 0;

     try {
         
         query ="";
         query += " WITH A AS (                                                                            \n";
         query += "     SELECT                                                                             \n";
         query += "     	STND_YEAR, DECODE(SELL_CD,'03','01',SELL_CD) SELL_CD, TMS,                     \n";
         query += "         SUM(DECODE(STND_YEAR, SUBSTR(?,1,4),     NET_AMT,0)) N_STND_AMT,               \n";
         query += "         SUM(DECODE(STND_YEAR, SUBSTR(?,1,4)*1 -1,NET_AMT,0)) B_STND_AMT,               \n";
         query += "         SUM(DECODE(STND_YEAR, SUBSTR(?,1,4)*1 -2,NET_AMT,0)) B2_STND_AMT               \n";
         query += "     FROM VW_PC_PAYOFFS T1                                                              \n";
         if("003".equals(sMeetCd)) query += "     WHERE MEET_CD = '003'                                    \n";
         if("001".equals(sMeetCd)) query += "     WHERE MEET_CD = '001'                                    \n";
         query += " 	AND STND_YEAR >= SUBSTR(?,1,4)*1 -2                                                \n";
         query += " 	AND T1.TMS <= '50'                                                                 \n";
         query += " 	GROUP BY MEET_CD, STND_YEAR, TMS, SELL_CD                                          \n";
         query += " 	UNION ALL                                                                          \n";
         query += " 	SELECT --                                                                          \n";
         query += "     	T1.STND_YEAR, DECODE(T1.SELL_CD,'03','01',T1.SELL_CD) SELL_CD, T1.TMS,         \n";
         query += "         SUM(DECODE(T1.STND_YEAR, SUBSTR(?,1,4), T1.DIV_TOTAL,0)) N_STND_AMT,           \n";
         query += "         0 B_STND_AMT,                                                                  \n";
         query += "         0 B2_STND_AMT                                                                  \n";
         query += "     FROM TBES_SDL_DT T1, VW_SDL_INFO T2                                                \n";
         if("003".equals(sMeetCd)) query += "     WHERE T1.MEET_CD = '003'                                 \n";
         if("001".equals(sMeetCd)) query += "     WHERE T1.MEET_CD = '001'                                 \n";
         query += "     AND T1.MEET_CD = T2.MEET_CD                                                        \n";
         query += "     AND T1.STND_YEAR = T2.STND_YEAR                                                    \n";
         query += "     AND T1.TMS = T2.TMS                                                                \n";
         query += "     AND T1.DAY_ORD = T2.DAY_ORD                                                        \n";
         query += "     AND T2.RACE_DAY = TO_CHAR(SYSDATE,'YYYYMMDD')                                      \n";
         query += " 	GROUP BY T1.MEET_CD, T1.STND_YEAR, T1.TMS, T1.SELL_CD                              \n";
         query += " )                                                                                      \n";
         query += " SELECT                                                                                 \n";
         query += "  NVL(SELL_CD, '00') SELL_CD,                                                           \n";
         query += "  TMS,                                                                                  \n";
  		 query += "  NVL(N_STND_AMT,0) N_STND_AMT,                                                         \n";
		 query += "  NVL(B_STND_AMT,0) B_STND_AMT,                                                         \n";
		 query += "  NVL(B2_STND_AMT,0) B2_STND_AMT                                                        \n";
		 query += " FROM (                                                                                 \n";
         query += "   SELECT SELL_CD, TMS,                                                                 \n";
         query += "        ROUND(DECODE(SUM(N_STND_AMT),0,NULL,SUM(N_STND_AMT))/1000000) N_STND_AMT,       \n";
         query += "        ROUND(DECODE(SUM(B_STND_AMT),0,NULL,SUM(B_STND_AMT))/1000000) B_STND_AMT,       \n";
         query += "        ROUND(DECODE(SUM(B2_STND_AMT),0,NULL,SUM(B2_STND_AMT))/1000000) B2_STND_AMT     \n";
         query += "   FROM A                                                                               \n";
         query += "   WHERE TMS IS NOT NULL                                                                \n";
         query += "   GROUP BY ROLLUP(SELL_CD), TMS                                                        \n";
         query += " )                                                                                      \n";
         query += " ORDER BY SELL_CD, TMS                                                                  \n";
         
         pstmt = con.prepareStatement(query);

         int k =1;
         
         pstmt.setString(k++, sRaceDay);
         pstmt.setString(k++, sRaceDay);
         pstmt.setString(k++, sRaceDay);
         pstmt.setString(k++, sRaceDay);
         pstmt.setString(k++, sRaceDay);
         

         rs = pstmt.executeQuery();
         String tmpSellCd = "";
         while (rs.next()) {
        	 Hashtable rHash = new Hashtable();
        	 tmpSellCd = rs.getString("SELL_CD");
 			rHash.put("SELL_CD"    ,tmpSellCd);
 			rHash.put("TMS"        ,rs.getString("TMS"));
 			rHash.put("N_STND_AMT" ,nullToStr(rs.getString("N_STND_AMT"),"0"));
 			rHash.put("B_STND_AMT" ,nullToStr(rs.getString("B_STND_AMT"),"0"));
 			rHash.put("B2_STND_AMT",nullToStr(rs.getString("B2_STND_AMT"),"0"));
 			
 			if("00".equals(tmpSellCd)) {
 				rVect000.addElement(rHash);
 				cnt000++;
 			}
 			if("01".equals(tmpSellCd)) {
 				rVect001.addElement(rHash);
 				cnt001++;
 			}
 			if("02".equals(tmpSellCd)) {
 				rVect002.addElement(rHash);
 				cnt002++;
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
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<title>매출비교 그래프</title>
	<link rel="stylesheet" href="//code.jquery.com/ui/1.11.2/themes/smoothness/jquery-ui.css">
	<script src="//code.jquery.com/jquery-1.10.2.js"></script>
	<script src="//code.jquery.com/ui/1.11.2/jquery-ui.js"></script>
	<link href="../css/default.css?v=20140119" rel="stylesheet" type="text/css">
	<link href="../css/sub_contents.css?v=20140119" rel="stylesheet" type="text/css">
	<script type="text/javascript">
	</script>
</head>
<script>
var data000;
var data001;
var data002;
var data004;
	<%
	
	if (cnt000>0) {
		out.print("data000 = [");
		out.print("{\n");
		out.print("label: \"금년\",\n");
		out.print("color: \"#BC101D\",\n");
		out.print("data:[\n");
		for(i=0; i<cnt000; i++) {	
			if (getData(rVect000,i,"SELL_CD").equals("00")) {
				out.print("[" + getData(rVect000,i,"TMS")+","+getData(rVect000,i,"N_STND_AMT") + "]");
				if(i < cnt000-1 ) out.print(",");
			}
		}
		out.print("]\n");
		out.print("},\n");
		
		out.print("{\n");
		out.print("label: \"전년\",\n");
		out.print("color: \"#80BFFF\",\n");
		out.print("data:[\n");
		for(i=0; i<cnt000; i++) {	
			if (getData(rVect000,i,"SELL_CD").equals("00")) {
				out.print("[" + getData(rVect000,i,"TMS")+","+getData(rVect000,i,"B_STND_AMT") + "]");
				if(i < cnt000-1 ) out.print(",");
			}
		}
		out.print("]\n");
		out.print("},\n");
		
		out.print("{\n");
		out.print("label: \"전전년\",\n");
		out.print("color: \"#A8E73F\",\n");
		out.print("data:[\n");
		for(i=0; i<cnt000; i++) {	
			if (getData(rVect000,i,"SELL_CD").equals("00")) {
				out.print("[" + getData(rVect000,i,"TMS")+","+getData(rVect000,i,"B2_STND_AMT") + "]");
				if(i < cnt000-1 ) out.print(",");
			}
		}
		out.print("]\n");
		out.print("}\n");
		out.print("];\n");
		
	}
	
	if (cnt001>0) {
		out.print("data001 = [");
		out.print("{\n");
		out.print("caption: \"금년\",\n");
		out.print("color: \"#BC101D\",\n");
		out.print("data:[\n");
		for(i=0; i<cnt001; i++) {	
			if (getData(rVect001,i,"SELL_CD").equals("01")) {
				out.print("[" + getData(rVect001,i,"TMS")+","+getData(rVect001,i,"N_STND_AMT") + "]");
				if(i < cnt001-1 ) out.print(",");
			}
		}
		out.print("]\n");
		out.print("},\n");
		
		out.print("{\n");
		out.print("caption: \"전년\",\n");
		out.print("color: \"#80BFFF\",\n");
		out.print("data:[\n");
		for(i=0; i<cnt001; i++) {	
			if (getData(rVect001,i,"SELL_CD").equals("01")) {
				out.print("[" + getData(rVect001,i,"TMS")+","+getData(rVect001,i,"B_STND_AMT") + "]");
				if(i < cnt001-1 ) out.print(",");
			}
		}
		out.print("]\n");
		out.print("},\n");
		
		out.print("{\n");
		out.print("caption: \"전전년\",\n");
		out.print("color: \"#A8E73F\",\n");
		out.print("data:[\n");
		for(i=0; i<cnt001; i++) {	
			if (getData(rVect001,i,"SELL_CD").equals("01")) {
				out.print("[" + getData(rVect001,i,"TMS")+","+getData(rVect001,i,"B2_STND_AMT") + "]");
				if(i < cnt001-1 ) out.print(",");
			}
		}
		out.print("]\n");
		out.print("}\n");
		out.print("];\n");
		
	}
		
	if (cnt002>0) {
		out.print("data002 = [");
		out.print("{\n");
		out.print("caption: \"금년\",\n");
		out.print("color: \"#BC101D\",\n");
		out.print("data:[\n");
		for(i=0; i<cnt002; i++) {	
			if (getData(rVect002,i,"SELL_CD").equals("02")) {
				out.print("[" + getData(rVect002,i,"TMS")+","+getData(rVect002,i,"N_STND_AMT") + "]");
				if(i < cnt002-1 ) out.print(",");
			}
		}
		out.print("]\n");
		out.print("},\n");
		
		out.print("{\n");
		out.print("caption: \"전년\",\n");
		out.print("color: \"#80BFFF\",\n");
		out.print("data:[\n");
		for(i=0; i<cnt002; i++) {	
			if (getData(rVect002,i,"SELL_CD").equals("02")) {
				out.print("[" + getData(rVect002,i,"TMS")+","+getData(rVect002,i,"B_STND_AMT") + "]");
				if(i < cnt002-1 ) out.print(",");
			}
		}
		out.print("]\n");
		out.print("},\n");
		
		out.print("{\n");
		out.print("caption: \"전전년\",\n");
		out.print("color: \"#A8E73F\",\n");
		out.print("data:[\n");
		for(i=0; i<cnt002; i++) {	
			if (getData(rVect002,i,"SELL_CD").equals("02")) {
				out.print("[" + getData(rVect002,i,"TMS")+","+getData(rVect002,i,"B2_STND_AMT") + "]");
				if(i < cnt002-1 ) out.print(",");
			}
		}
		out.print("]\n");
		out.print("}\n");
		out.print("];\n");
	}
	
	if (cnt004>0) {
		out.print("data004 = [");
		out.print("{\n");
		out.print("caption: \"금년\",\n");
		out.print("color: \"#BC101D\",\n");
		out.print("data:[\n");
		for(i=0; i<cnt004; i++) {	
			if (getData(rVect004,i,"SELL_CD").equals("04")) {
				out.print("[" + getData(rVect004,i,"TMS")+","+getData(rVect004,i,"N_STND_AMT") + "]");
				if(i < cnt004-1 ) out.print(",");
			}
		}
		out.print("]\n");
		out.print("},\n");
		
		out.print("{\n");
		out.print("caption: \"전년\",\n");
		out.print("color: \"#80BFFF\",\n");
		out.print("data:[\n");
		for(i=0; i<cnt004; i++) {	
			if (getData(rVect004,i,"SELL_CD").equals("04")) {
				out.print("[" + getData(rVect004,i,"TMS")+","+getData(rVect004,i,"B_STND_AMT") + "]");
				if(i < cnt004-1 ) out.print(",");
			}
		}
		out.print("]\n");
		out.print("},\n");
		
		out.print("{\n");
		out.print("caption: \"전전년\",\n");
		out.print("color: \"#A8E73F\",\n");
		out.print("data:[\n");
		for(i=0; i<cnt004; i++) {	
			if (getData(rVect004,i,"SELL_CD").equals("04")) {
				out.print("[" + getData(rVect004,i,"TMS")+","+getData(rVect004,i,"B2_STND_AMT") + "]");
				if(i < cnt004-1 ) out.print(",");
			}
		}
		out.print("]\n");
		out.print("}\n");
		out.print("];\n");
	}
	%>

	$(function() {
		  $( "#calendar" ).datepicker({
		    dateFormat: 'yy-mm-dd',
		    prevText: '이전 달',
		    nextText: '다음 달',
		    monthNames: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
		    monthNamesShort: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
		    dayNames: ['일','월','화','수','목','금','토'],
		    dayNamesShort: ['일','월','화','수','목','금','토'],
		    dayNamesMin: ['일','월','화','수','목','금','토'],
		    showMonthAfterYear: true,
		    changeMonth: true,
		    changeYear: true,
		    yearSuffix: '년'
		  });
		});
	
	function sendFrm() {
		var f = document.pFrm;
		var obj = document.getElementsByName('R_MEET_CD');
		var checked_index = -1;
		var checked_value = '';
		for( i=0; i<obj.length; i++ ) {
			if(obj[i].checked) {
				checked_index = i;
				checked_value = obj[i].value;
			}
		}
		f.MEET_CD.value = checked_value;
		
		obj = document.getElementsByName('R_AUTO_YN');
		checked_index = -1;
		checked_value = '';
		for( i=0; i<obj.length; i++ ) {
			if(obj[i].checked) {
				checked_index = i;
				checked_value = obj[i].value;
			}
		}
		f.AUTO_YN.value = checked_value;
		
		if(f.calendar.value.length < 10) {
			alert("날짜가 형식에 맞지않습니다. 2016-12-01");
			return;
		}

		f.RACE_DAY.value = f.calendar.value.replace(/\-/g,'');
		f.submit();
	}

	var autoFlag = false;
	function autoReload() {
		var reloadTime = document.pFrm.autoTime.value;
		if(autoFlag) {
			setTimeout("sendFrm()",1000*60*reloadTime);
		}
	}
	
	function setAutoFlag(flag) {
		autoFlag = flag;
		if(!flag) document.pFrm.autoTime.disabled = true;
		else document.pFrm.autoTime.disabled = false;
	}
	
</script>

<body width="95%">
<div class="sub_cont_wrap top_text" >
&nbsp;
</div>
<div class="bordTop sb_title">
	<h5 class="titleH4small fl">매출비교 그래프 </h5>
</div>
<div class="searchBox">
<form name="pFrm" target="_self" action="chart.jsp" method="get">
<input type="hidden" name="RACE_DAY" value="<%=sRaceDay%>">
<input type="hidden" name="MEET_CD" value="<%=sRaceDay%>">
<input type="hidden" name="AUTO_YN" value="<%=sRaceDay%>">
<fieldset>
	<p class="searchText">
		날짜: <input type="text" size="10" id="calendar" name="calendar" value="<%=sCalendar%>">
		&nbsp;&nbsp;&nbsp;&nbsp;
		<input type="radio" name="R_MEET_CD" value="001" <%if("001".equals(sMeetCd)) out.print("checked");%>>경륜
		<input type="radio" name="R_MEET_CD" value="003" <%if("003".equals(sMeetCd)) out.print("checked");%>>경정
		&nbsp;&nbsp;&nbsp;&nbsp;
		<input type="radio" name="R_AUTO_YN" value="N" <%if("N".equals(sAutoYn)) out.print("checked");%> onClick="javascript:setAutoFlag(false);">수동
		<input type="radio" name="R_AUTO_YN" value="Y" <%if("Y".equals(sAutoYn)) out.print("checked");%> onClick="javascript:setAutoFlag(true);">자동
		<select name="autoTime" disabled="true" onChange="javascript:autoReload();">
			<option value="5">5분</option>
			<option value="10">10분</option>
			<option value="15">15분</option>
		</select>
		&nbsp;&nbsp;&nbsp;&nbsp;
		<a href="javascript:sendFrm();"><img src="../images/search.jpg"></a>&nbsp;&nbsp;
		&nbsp;&nbsp;&nbsp;&nbsp;
		하단 회차별 숫자는 광명/미사리 개최경주를 창원/부산에서 판매한 경주 수
		&nbsp;&nbsp;&nbsp;&nbsp;
		(단위: 백만원)
		&nbsp;&nbsp;&nbsp;&nbsp;
		<a href="javascript:window.close();"><img src="../images/close.jpg"></a>
	
	</p>
</fieldset>
</form>
</div>
<div class="printPop">
<%
	if (cnt000>0) {
%>
		<iframe src="chart000.jsp?MEET_CD=<%=sMeetCd%>" width="99%" height="210px" scrolling="no" frameborder="0"></iframe>
<%		
    }
	if ("001".equals(sMeetCd) && cnt001>0) {
%>
		<iframe src="chart001.jsp?MEET_CD=<%=sMeetCd%>" width="99%" height="210px" scrolling="no" frameborder="0"></iframe>
<%
	}
	if ("003".equals(sMeetCd) && cnt001>0) {
%>
		<iframe src="chart003.jsp?MEET_CD=<%=sMeetCd%>" width="99%" height="210px" scrolling="no" frameborder="0"></iframe>
<%
	}		
	if (cnt002>0) {
%>
		<iframe src="chart002.jsp?MEET_CD=<%=sMeetCd%>" width="99%" height="210px" scrolling="no" frameborder="0"></iframe>
<%		
	}
	if (cnt004>0) {
%>
		<iframe src="chart004.jsp?MEET_CD=<%=sMeetCd%>" width="99%" height="210px" scrolling="no" frameborder="0"></iframe>
<%
	}
%>
<iframe src="raceCnt.jsp?MEET_CD=<%=sMeetCd%>&RACE_DAY=<%=sRaceDay%>&MAX_CNT=<%=cnt000%>" width="98.5%" height="130px" scrolling="no" frameborder="0"></iframe>
</div>
</body>
</html>
