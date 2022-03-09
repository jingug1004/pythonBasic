<%@ page language="java" import="java.util.*, java.sql.*" session="true" contentType="text/html;charset=EUC_KR" %>
<%@ page import="javax.naming.*, javax.sql.*" %>
<%!  
	String getData(Vector rVector, int idx, String colname) {
		String strResult = "";
		Hashtable tHash = (Hashtable)rVector.elementAt(idx);
		strResult = (String)tHash.get(colname);
		return strResult;
	}

	int getIntData(Vector rVector, int idx, String colname) {
		int intResult = 0;
		String strResult = "";
		Hashtable tHash = (Hashtable)rVector.elementAt(idx);
		strResult = (String)tHash.get(colname);
		try {
			if(strResult != null) {
				intResult = Integer.parseInt(strResult);	
			}
		} catch(Exception e) {
			intResult = 0;
		}
		return intResult;
	}

	float getFloatData(Vector rVector, int idx, String colname) {
		float floatResult = 0;
		String strResult = "";
		Hashtable tHash = (Hashtable)rVector.elementAt(idx);
		strResult = (String)tHash.get(colname);
		try {
			if(strResult != null) {
				floatResult = Float.parseFloat(strResult);	
			}
		} catch(Exception e) {
			floatResult = 0;
		}
		return floatResult;
	}
	
	String getMesg(String MesgId) {
		String mesg = "";
		if (MesgId == null) return mesg;
		if (MesgId == "")   return mesg;
		int mesgNo = Integer.parseInt(MesgId);
		switch(mesgNo) {
			case 101:
				mesg = "정상적으로 등록했습니다.";
				break;
			case 102:	 
				mesg = "오류가 발생했습니다.";
				break;
			case 201:
				mesg = "정상적으로 삭제했습니다.";
				break;
			case 202:	 
				mesg = "오류가 발생했습니다.";
				break;
		}
		return mesg;
	}

%>
<%
	String strMessage   = request.getParameter("message");
	strMessage=getMesg(strMessage);
    
    int cnt01=0;		// 회차별 매출액
    int cnt02=0;		// 누적 매출건수
    int cnt03=0;		// 당회차 매출정보 건수
    //long startTime = System.currentTimeMillis();    	
    //long endTime = 0;    	
    
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    Connection con = null;
    
    try { 
    	Context ctx = new InitialContext();
    	if (ctx != null) { 
    		//DataSource ds = (DataSource)ctx.lookup("java:/comp/env/jdbc/RBM");
    		DataSource ds = (DataSource)ctx.lookup("jdbc/RBM");
    		if (ds != null) {
    			con = ds.getConnection();    			
    		}
    	}    	
    	
        
    } catch(SQLException e) {
        out.println(e.toString());
        e.printStackTrace();   
        return;
    }   
    Vector rVect01 = new Vector();    
    Vector rVect02 = new Vector();    
    Vector rVect03 = new Vector();  
    String day_desc = "";
    try {    	
    	
		// 최근 회차 매출액 및 누적 매출액조회
        String query = "";
         
        query  ="";
        query += " WITH X AS (                                                                                                                   \n";
        query += " SELECT MEET_CD, STND_YEAR, MAX(TMS) AS TMS                                                                                    \n";
        query += " FROM   VW_SDL_INFO                                                                                                            \n";
        query += " WHERE  RACE_DAY < TO_CHAR(SYSDATE - DECODE(TO_CHAR(SYSDATE, 'd'), 5, 1, 7, 1, 1, 2, 0), 'yyyymmdd')                            \n";
        query += " AND    MEET_CD IN ('001','003')                                                                                              \n";
        query += " AND    STND_YEAR BETWEEN TO_CHAR(SYSDATE,'YYYY') -1 AND TO_CHAR(SYSDATE,'YYYY')                                              \n";
        query += " GROUP BY MEET_CD, STND_YEAR                                                                                                   \n";
        query += " )                                                                                                                             \n";
        
        query += " SELECT 1 AS JOB_TYPE                                                                                                          \n";
        query += "       ,STND_YEAR                                                                                                              \n";
        query += "       ,MEET_NM                                                                                                                \n";
        query += "       ,TMS                                                                                                                    \n";
        query += "       ,TRIM(TO_CHAR(CUR_TMS_AMT,'999,999')) AS CUR_TMS_AMT                                                                \n";
        query += "       ,TRIM(TO_CHAR(PREV_TMS_AMT, '999,999')) AS PREV_TMS_AMT                                                                       \n";
        query += "       ,ROUND((CUR_TMS_AMT - PREV_TMS_AMT)/PREV_TMS_AMT*100,1) AS PREV_TMS_RATIO                                               \n";
        query += "       ,TRIM(TO_CHAR(LAST_YEAR_TMS_AMT,'999,999')) AS LAST_YEAR_TMS_AMT                                                              \n";
        query += "       ,ROUND((CUR_TMS_AMT - LAST_YEAR_TMS_AMT)/LAST_YEAR_TMS_AMT*100,1) AS LAST_YEAR_TMS_RATIO                                \n";
        query += " FROM (                                                                                                                        \n";
        query += " SELECT DECODE(B.MEET_CD,'001','경륜','003','경정') AS MEET_NM                                                                   \n";
        query += "       ,ROUND(SUM(CASE WHEN B.STND_YEAR=X.STND_YEAR AND B.TMS = X.TMS THEN DIV_TOTAL END)/100000000) AS CUR_TMS_AMT            \n";
        query += "       ,ROUND(SUM(CASE WHEN B.STND_YEAR=X.STND_YEAR AND B.TMS = (X.TMS -1) THEN DIV_TOTAL END)/100000000)  AS PREV_TMS_AMT     \n";
        query += "       ,ROUND(SUM(CASE WHEN B.STND_YEAR=X.STND_YEAR -1 AND B.TMS = X.TMS THEN DIV_TOTAL END)/100000000)  AS LAST_YEAR_TMS_AMT  \n";
        query += "       ,MIN(X.STND_YEAR) AS STND_YEAR                                                                                          \n";
        query += "       ,MIN(X.TMS) AS TMS                                                                                                      \n";
        query += " FROM  X, TBES_SDL_DT_SUM B                                                                                                    \n";
        query += " WHERE B.STND_YEAR BETWEEN X.STND_YEAR -1 AND X.STND_YEAR                                                                      \n";
        query += " AND   B.MEET_CD  IN ('001','003')                                                                                             \n";
        query += " AND   X.MEET_CD = B.MEET_CD                                                                                                   \n";
        query += " GROUP BY B.MEET_CD                                                                                                            \n";
        query += " ORDER BY 1                                                                                                                    \n";
        query += " )                                                                                                                             \n";

        query += " UNION ALL                                                                                                         \n";

        query += " SELECT 2 AS JOB_TYPE                                                                                              \n";
        query += "       ,STND_YEAR                                                                                                  \n";
        query += "       ,MEET_NM                                                                                                    \n";
        query += "       ,TMS                                                                                                        \n";
        query += "       ,TRIM(TO_CHAR(PLAN_AMT,'999,999')) AS PLAN_AMT                                                                    \n";
        query += "       ,TRIM(TO_CHAR(CUR_CUM_AMT,'999,999')) AS CUR_CUM_AMT                                                              \n";
        query += "       ,ROUND(CUR_CUM_AMT/PLAN_AMT*100,1) AS CUR_CUM_RATIO                                                         \n";
        query += "       ,TRIM(TO_CHAR(LAST_YEAR_CUM_AMT,'999,999')) AS LAST_YEAR_CUM_AMT                                                  \n";
        query += "       ,ROUND((CUR_CUM_AMT-LAST_YEAR_CUM_AMT)/LAST_YEAR_CUM_AMT*100,1) AS LAST_YEAR_CUM_RATIO                      \n";
        query += " FROM (                                                                                                            \n";
        query += "         SELECT DECODE(B.MEET_CD,'001','경륜','003','경정') AS MEET_NM                                               \n";
        query += "               ,ROUND(TO_NUMBER(CD_NM)/100) AS PLAN_AMT                                                            \n";
        query += "               ,ROUND(SUM(CASE WHEN B.STND_YEAR=X.STND_YEAR THEN DIV_TOTAL END)/100000000) AS CUR_CUM_AMT          \n";
        query += "               ,ROUND(SUM(CASE WHEN B.STND_YEAR=X.STND_YEAR -1 AND B.TMS <= X.TMS THEN DIV_TOTAL END)/100000000)   \n";
        query += "                                                                                           AS LAST_YEAR_CUM_AMT    \n";
        query += "               ,MIN(X.STND_YEAR) AS STND_YEAR                                                                      \n";
        query += "               ,MIN(X.TMS) AS TMS                                                                                  \n";
        query += "         FROM X                                                                                                    \n";
        query += "             ,TBRK_SPEC_CD C                                                                                       \n";
        query += "             ,TBES_SDL_DT_SUM B                                                                                    \n";
        query += "         WHERE B.STND_YEAR BETWEEN X.STND_YEAR -1 AND X.STND_YEAR                                                  \n";
        query += "         AND   B.MEET_CD = X.MEET_CD                                                                               \n";
        query += "         AND   X.MEET_CD    = C.CD_TERM1                                                                           \n";
        query += "         AND   X.STND_YEAR = C.CD_TERM2                                                                            \n";
        query += "         AND   C.GRP_CD = '163'                                                                                    \n";
        query += "         GROUP BY DECODE(B.MEET_CD,'001','경륜','003','경정'), CD_NM                                                 \n";
        query += "         ORDER BY 1                                                                                                 \n";
        query += "         )                                                                                                          \n"; 
        query += " ORDER BY 1,3                                                                                                      \n"; 
        
        query  = "SELECT  JOB_TYPE            \n";
        query += "       ,STND_YEAR           \n";
        query += "       ,MEET_NM             \n";
        query += "       ,TMS                 \n";
        query += "       ,NVL(CUR_TMS_AMT,0) AS CUR_TMS_AMT         \n";
        query += "       ,NVL(PREV_TMS_AMT,0) AS PREV_TMS_AMT        \n";
        query += "       ,NVL(PREV_TMS_RATIO,0) AS PREV_TMS_RATIO      \n";
        query += "       ,NVL(LAST_YEAR_TMS_AMT,0) AS LAST_YEAR_TMS_AMT   \n";
        query += "       ,NVL(LAST_YEAR_TMS_RATIO,0) AS LAST_YEAR_TMS_RATIO  \n";
        query += " FROM  TBRS_SALES_RSLT_RTLY \n";    
        query += " ORDER BY JOB_TYPE, MEET_NM \n";
        
        pstmt = con.prepareStatement(query);
        
		rs = pstmt.executeQuery();
        //endTime = System.currentTimeMillis();  
		//System.out.println("소요시간1:"+(endTime - startTime)/1000.000f+"초");
		//startTime = endTime;
		
        while (rs.next()) {
			Hashtable rHash = new Hashtable();
			rHash.put("JOB_TYPE",     	   		rs.getString("JOB_TYPE"));
			rHash.put("STND_YEAR",     	   		rs.getString("STND_YEAR"));
			rHash.put("MEET_NM",     	   		rs.getString("MEET_NM"));
			rHash.put("TMS",     	   			rs.getString("TMS"));
			rHash.put("CUR_TMS_AMT",       		rs.getString("CUR_TMS_AMT"));
			rHash.put("PREV_TMS_RATIO",    		rs.getString("PREV_TMS_RATIO"));
			rHash.put("PREV_TMS_AMT",      		rs.getString("PREV_TMS_AMT"));
			rHash.put("LAST_YEAR_TMS_AMT", 		rs.getString("LAST_YEAR_TMS_AMT"));
			rHash.put("LAST_YEAR_TMS_RATIO",    rs.getString("LAST_YEAR_TMS_RATIO"));
			
			rVect01.addElement(rHash);	
			cnt01 ++;
		}
        //endTime = System.currentTimeMillis();  
		//System.out.println("소요시간2:"+(endTime - startTime)/1000.000f+"초");
		//startTime = endTime;
		
        // 차트용 자료 조회
        query  =" WITH X AS (                                                                                                   \n";
        query +="  SELECT MEET_CD, STND_YEAR, MAX(TMS) AS TMS                                                                   \n";
        query +="  FROM   VW_SDL_INFO                                                                                           \n";
        query +="  WHERE RACE_DAY < TO_CHAR(SYSDATE - DECODE(TO_CHAR(SYSDATE, 'd'), 5, 1, 7, 1, 1, 2, 0), 'yyyymmdd')           \n";
        query +="  AND     MEET_CD IN ('001','003')                                                                             \n";
        query +="  AND     STND_YEAR = TO_CHAR(SYSDATE,'YYYY')                                                                  \n";
        query +="  GROUP BY MEET_CD, STND_YEAR                                                                                  \n";
        query +="  )                                                                                                            \n";
        query +="  SELECT B.MEET_CD AS MEET_CD                                                                                  \n";
        query +="        ,B.TMS ||'회차' AS TMS                                                                                 \n";
        query +="        ,ROUND(SUM(CASE WHEN B.STND_YEAR=X.STND_YEAR THEN DIV_TOTAL END)/100000000) AS CUR_YEAR_TMS_AMT        \n";
        query +="        ,ROUND(SUM(CASE WHEN B.STND_YEAR=X.STND_YEAR -1 THEN DIV_TOTAL END)/100000000)  AS LAST_YEAR_TMS_AMT   \n";
        query +="  FROM  X, (                                                                                                   \n";
        query +="            SELECT STND_YEAR, MEET_CD, TMS, SUM(DIV_TOTAL) AS DIV_TOTAL                                        \n";
        query +="            FROM   TBES_SDL_DT_SUM                                                                             \n";
        query +="            WHERE STND_YEAR BETWEEN TO_CHAR(SYSDATE, 'YYYY') -1 AND TO_CHAR(SYSDATE,'YYYY')                    \n";
        query +="            AND     MEET_CD IN ('001','003')                                                                   \n";
        query +="            GROUP BY STND_YEAR, MEET_CD, TMS) B                                                                \n";                   
        query +="  WHERE 1=1                                                                                                    \n";
        query +="  AND   X.MEET_CD = B.MEET_CD                                                                                  \n";
        query +="  AND   B.TMS BETWEEN X.TMS -5 AND X.TMS                                                                       \n";
        query +="  GROUP BY B.MEET_CD, B.TMS                                                                                    \n";
        query +="  ORDER BY B.MEET_CD, B.TMS                                                                                    \n";
        
        query  = "SELECT  MEET_CD                          \n";
        query += "            ,TMS                         \n";
        query += "            ,CUR_YEAR_TMS_AMT            \n";
        query += "            ,LAST_YEAR_TMS_AMT           \n";
        query += "FROM TBRS_SALES_RSLT_RTLY_5TMS           \n";
        query += "ORDER BY MEET_CD, ODR_NO DESC            \n";
        
        pstmt = con.prepareStatement(query);

        rs = pstmt.executeQuery();
        //endTime = System.currentTimeMillis();  
		//System.out.println("소요시간3:"+(endTime - startTime)/1000.000f+"초");
		//startTime = endTime;
		
        while (rs.next()) {
			Hashtable rHash = new Hashtable();
			rHash.put("MEET_CD",     	   		rs.getString("MEET_CD"));
			rHash.put("TMS",     	   			rs.getString("TMS"));
			rHash.put("CUR_YEAR_TMS_AMT",       rs.getString("CUR_YEAR_TMS_AMT"));
			rHash.put("LAST_YEAR_TMS_AMT",    	rs.getString("LAST_YEAR_TMS_AMT"));
			
			rVect02.addElement(rHash);	
			cnt02 ++;
		}
        //endTime = System.currentTimeMillis();  
		//System.out.println("소요시간4:"+(endTime - startTime)/1000.000f+"초");
		//startTime = endTime;

        // 당일 경주정보 조회
        query  ="  SELECT '◈ '||DECODE(MEET_CD,'001','경륜','경정') || ' 제'||TMS||'회차 '||DAY_ORD||'일차 ('||TO_CHAR(SYSDATE,'YYYY')||'년 '||TO_CHAR(SYSDATE, 'MM')||'월 '||TO_CHAR(SYSDATE, 'DD')||'일)' AS DAY_DESC \n";
		query +="  FROM   VW_SDL_INFO                                                                                             \n";
		query +="  WHERE  RACE_DAY = TO_CHAR(SYSDATE,'YYYYMMDD')                                                                 \n";
		query +="  AND    MEET_CD IN ('001','003')                                                                               \n";
		pstmt = con.prepareStatement(query);

        rs = pstmt.executeQuery();
        //endTime = System.currentTimeMillis();  
		//System.out.println("소요시간5:"+(endTime - startTime)/1000.000f+"초");
		//startTime = endTime;
		
        if (rs.next()) {
			day_desc = rs.getString("DAY_DESC");
		}    
        
        // 당회차 매출조회
		query  = "  WITH X AS (                                              \n";
		query += "      SELECT MEET_CD, STND_YEAR, TMS                       \n";            
		query += "      FROM   VW_SDL_INFO                                   \n";            
		query += "      WHERE  RACE_DAY=TO_CHAR(SYSDATE,'YYYYMMDD')          \n";            
		query += "      AND    MEET_CD IN ('001','003')                      \n";
		query += "  )                                                        \n";
		query += "  SELECT DECODE(RACE_YOIL, 'MON', '월요일',                \n";
		query += "                           'TUE', '화요일',                \n";
		query += "                           'WED', '수요일',                \n";
		query += "                           'THU', '목요일',                \n";
		query += "                           'FRI', '금요일',                \n";
		query += "                           'SAT', '토요일',                \n";
		query += "                           'SUN', '일요일',                \n";
		query += "                           '합계') AS DAY_NM               \n";
		query += "        ,P.DAY_ORD                                         \n";
		query += "        ,ROUND(SUM(AMOUNT)/100000000) AS AMT               \n";
		query += "  FROM   TBES_SDL_PB P, VW_SDL_INFO I                      \n";                                
		query += "  WHERE (P.MEET_CD, P.STND_YEAR, P.TMS) IN                 \n";                     
		query += "        (SELECT MEET_CD, STND_YEAR, TMS FROM X)            \n";                     
		query += "  AND   AMOUNT > 0                                         \n";
		query += "  AND   P.MEET_CD = I.MEET_CD                              \n";
		query += "  AND   P.STND_YEAR = I.STND_YEAR                          \n";
		query += "  AND   P.TMS = I.TMS                                      \n";
		query += "  AND   P.DAY_ORD = I.DAY_ORD                              \n";
		query += "  AND   I.STND_YEAR = TO_CHAR(SYSDATE, 'YYYY')             \n";                          
		query += "  GROUP BY P.DAY_ORD, I.RACE_YOIL                          \n";
		query += "  UNION ALL                                                \n";
		query += "  SELECT '합계', 99, NVL(ROUND(SUM(AMOUNT)/100000000),0) AS AMT    \n";
		query += "  FROM  TBES_SDL_PB P                                      \n";
		query += "  WHERE (P.MEET_CD, P.STND_YEAR, P.TMS) IN                 \n";                     
		query += "        (SELECT MEET_CD, STND_YEAR, TMS FROM X)            \n";                     
		query += "  AND   AMOUNT > 0                                         \n";
		query += "  AND   P.STND_YEAR = TO_CHAR(SYSDATE, 'YYYY')             \n";     
		query += "  ORDER BY 2                                               \n";		  		
        pstmt = con.prepareStatement(query);

        rs = pstmt.executeQuery();
        //endTime = System.currentTimeMillis();  
		//System.out.println("소요시간6:"+(endTime - startTime)/1000.000f+"초");
		//startTime = endTime;
		
        while (rs.next()) {
			Hashtable rHash = new Hashtable();
			rHash.put("DAY_NM",   rs.getString("DAY_NM"));
			rHash.put("AMT",      rs.getString("AMT"));
			
			rVect03.addElement(rHash);	
			cnt03 ++;
		}    
        //endTime = System.currentTimeMillis();  
		//System.out.println("소요시간7:"+(endTime - startTime)/1000.000f+"초");
		//startTime = endTime;
    } catch(SQLException e) {
    	out.println(e.toString());
    } finally {
        if(con != null) try{con.close();}       catch(Exception e){}
        if(pstmt != null) try{pstmt.close();}   catch(SQLException sqle){}
	}

    
    
%>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
	<link rel="stylesheet" href="css/resttable.css" type="text/css" />
	<link rel="stylesheet" href="css/webfont.css" type="text/css" />
	<style type="text/css">
		* {margin:0; padding:0}
		.c_both {clear:both}
		#title {float:center; text-align:center; height: 150px; margin:10px;  border:0px solid red;}
		#title_1 {width:500px;  height: 25px; background-image:url(image/kspo_s_title_1.gif); background-repeat: no-repeat; font-weight:bold; border:0px solid blue;}
		
		.row01  {padding-top:10px; font-size:16px; font-size:16px; }
		.row02  {padding-top:10px; font-size:16px; font-size:16px; }
		.col01  {width:45%; float:left;  height: 250px; margin-left:15px;  border:0px solid red}
		.col02  {width:45%; float:right; height: 250px; margin-right:15px; border:0px solid red}		
		.col03h {width:45%; float:left;  height: 30px;  margin-left:15px;  border:0px solid red}
		.col04h {width:45%; float:right; height: 30px;  margin-right:15px; border:0px solid red}		
		.col03  {width:45%; float:left;  height: 100px; margin-left:15px;  border:0px solid red}
		.col04  {width:45%; float:right; height: 100px; margin-right:15px; border:0px solid red}		
		.col05  {width:45%; float:left;  height: 25px;  margin-left:15px;  border:0px solid red}
		.col06  {width:45%; float:right; height: 25px;  margin-right:15px; border:0px solid red}		
		.col07  {width:98%; float:left;  height: 50px;  margin-left:15px;  font-weight:bold; border:0px solid red}
	</style>		
			
	<script>
		function goLink(flag) {
			window.open('http://rbm.kcycle.or.kr/','','target=_blank');
		}
	</script>
	
	<script type="text/javascript" src="https://www.google.com/jsapi"></script>
	<script type="text/javascript">
	  google.load("visualization", "1", {packages:["corechart"]});
	  google.setOnLoadCallback(drawChart);
	  function drawChart() {
	    var data = google.visualization.arrayToDataTable([
	      ['회차', '금년도', '전년도']
	      /*
	      ['37회차',  500,      450],
	      ['38회차',  700,      500],
	      ['39회차',  800,      600],
	      ['40회차',  900,      700],
	      ['41회차',  1170,      860]
	      */
		<%
		if(rVect02 != null) {
			if (cnt02>0) {
				for(int i=0; i<cnt02; i++) {	
					if (getData(rVect02,i,"MEET_CD").equals("001")) {
		%>
		    ,['<%=getData(rVect02,i,"TMS")%>',<%=getData(rVect02,i,"CUR_YEAR_TMS_AMT")%>,<%=getData(rVect02,i,"LAST_YEAR_TMS_AMT")%>]
		<%
						}
					}
				}
			}
		%>
	    ]);
	
	    var options = {
	      title: '',
	      legend:{position:'top',textStyle:{fontSize: 15, fontName:'나눔고딕' },alignment:'center'},
	      chartArea:{left:50,top:60,width:"90%",height:"60%"},
	      hAxis:{textStyle:{fontSize:14, fontName:'나눔고딕'}},
	      vAxis:{textStyle:{fontSize:14, fontName:'나눔고딕'}},
	      tooltip:{textStyle:{fontSize:14, fontName:'나눔고딕'}},
	      pointSize:10
	    };
	
	    var options_kboat = {
	      title: '',
	      legend:{position:'top',textStyle:{fontSize: 15, fontName:'나눔고딕' },alignment:'center'},
	      chartArea:{left:50,top:60,width:"90%",height:"60%"},
	      hAxis:{textStyle:{fontSize:14, fontName:'나눔고딕'}},
	      vAxis:{textStyle:{fontSize:14, fontName:'나눔고딕'},viewWindow:{min:120, max:200}},
	      tooltip:{textStyle:{fontSize:14, fontName:'나눔고딕'}},
	      pointSize:10
	    };
	
	    var chart = new google.visualization.LineChart(document.getElementById('chart_div'));
	    chart.draw(data, options);
	    
	    
	    var data3 = google.visualization.arrayToDataTable([
	      ['회차', '금년도', '전년도']
	      /*
	      ['37회차',  500,      450],
	      ['38회차',  700,      500],
	      ['39회차',  800,      600],
	      ['40회차',  900,      700],
	      ['41회차',  1170,      860]
	      */
		<%
		if(rVect02 != null) {
			if (cnt02>0) {
				for(int i=0; i<cnt02; i++) {	
					if (getData(rVect02,i,"MEET_CD").equals("003")) {
		%>
		    ,['<%=getData(rVect02,i,"TMS")%>',<%=getData(rVect02,i,"CUR_YEAR_TMS_AMT")%>,<%=getData(rVect02,i,"LAST_YEAR_TMS_AMT")%>]
		<%
						}
					}
				}
			}
		%>
	    ]);
	
	    var chart3 = new google.visualization.LineChart(document.getElementById('chart_div3'));
	    chart3.draw(data3, options_kboat);

	  }
	</script>
</head>
<body style="font-family:'나눔고딕', NanumGothic, '굴림', 'Gulim'">

	<div id="title">
		<table width=100% height=100%>
			<tr><td valign=bottom>
				<img src="image/kspo_title.gif" alt="경륜경정 매출정보">
			</td></tr>
		</table>
	</div>

	<div class="row01">
		<div class="col05" style="color:#454545;"><div id='title_1'><%=day_desc%></div>
		</div>
		<div class="col06" style="text-align:right;">(단위 : 억원)</div>
		<div style="clear:both"></div>
		<div class="col07" style="color:#454545;">
			<%
			int k=0;
			if (rVect03 != null) {
				out.print("<table width='100%' height=50% class='list'><tr style='background-image:url(image/kspo_s_title_bg.gif);' >");
				for (int i=0; i<cnt03;i++) {
					//if (cnt03 != 3 || i != 1) {
						out.print("<td valign='middle' style = 'text-align:right;' width='8%'><img src='image/b1.gif'> "+getData(rVect03,i,"DAY_NM")+" : </td>");
						out.print("<td style = 'text-align:left;' width='8%'>"+getData(rVect03,i,"AMT")+"</td>");
						k += 2;
					//}
				}
				for (int j=k;j<12;j++) {
					out.print("<td width='8%'></td>");					
				}
				out.print("</tr></table>");
			}
			%>		
		</div>
		<div style="clear:both"></div>
	</div>
	
	<div class="row02" >
		<div class="col01" style="color:#454545;"><img src="image/kspo_s_title_2.gif" alt="◈ 최근 5회차 경륜 매출">
			<div id="chart_div" style="width:'100%'; height: 200px;"></div>
		</div>
		<div class="col02" style="color:#454545;"><img src="image/kspo_s_title_3.gif" alt="◈ 최근 5회차 경정 매출">
			<div id="chart_div3" style="width: '100%'; height: 200px;"></div>
		</div>
		<div style="clear:both"></div>
	</div>
	<div class="row02" >
		<div class="col03h">
			<img src="image/kspo_s_title_4.gif" alt="◈ 최근 회차별 매출액 비교">
		</div>
		<div class="col04h">
			<img src="image/kspo_s_title_5.gif" alt="◈ 전년동회 대비 매출액 비교">
		</div>
		<div class="col03" style="color:#454545;">
			<table class='list' width='95%'>
				<tr>
					<th class="right_b blue" width="25%"  align=center style = "text-align:center;">구분</th>
					<th class="right_b blue" width="25%"  align=center>금회</th>
					<th class="right_b blue" width="25%" align=center>전회</th>
					<th class="right_b blue" width="25%" align=center>증감률</th>
					
				</tr>
				<%
				if(rVect01 != null) {
					for(int i=0; i<cnt01; i++) {
						if (getIntData(rVect01,i, "JOB_TYPE") == 1) {
				%>
				<tr>	
					<td style = "text-align:center;"><%=getData(rVect01,i,"MEET_NM")%>(<%=getData(rVect01,i,"TMS")%>회차)</td>
					<td><%=getData(rVect01,i,"CUR_TMS_AMT")%></td>
					<td><%=getData(rVect01,i,"PREV_TMS_AMT")%></td>
					<%
						if (getFloatData(rVect01,i,"PREV_TMS_RATIO") < 0) {
					%>
					<td><font color="blue">△<%=Math.abs(getFloatData(rVect01,i,"PREV_TMS_RATIO"))%>%</font></td>
					<%
						} else {
					%>
					<td><%=getFloatData(rVect01,i,"PREV_TMS_RATIO")%>%</td>
					<%
						}
					%>
					
				</tr>
				<%	
							}
						}
					}
				%>
			</table>	
		</div>
		<div class="col04" style="color:#454545;">
			<table class='list' width='95%'>				
				<tr>
					<th class="right_b blue" width="25%"  align=center style = "text-align:center;">구분</th>
					<th class="right_b blue" width="25%"  align=center>금회</th>
					<th class="right_b blue" width="25%" align=center>전년동회</th>
					<th class="right_b blue" width="25%" align=center>증감률</th>
					
				</tr>
				<%
				if(rVect01 != null) {
					for(int i=0; i<2; i++) {
						if (getIntData(rVect01,i, "JOB_TYPE") == 1) {
				%>
				<tr>	
					<td style = "text-align:center;"><%=getData(rVect01,i,"MEET_NM")%>(<%=getData(rVect01,i,"TMS")%>회차)</td>
					<td><%=getData(rVect01,i,"CUR_TMS_AMT")%></td>
					<td><%=getData(rVect01,i,"LAST_YEAR_TMS_AMT")%></td>
					<%
						if (getFloatData(rVect01,i,"LAST_YEAR_TMS_RATIO") < 0) {
					%>
					<td><font color="blue">△<%=Math.abs(getFloatData(rVect01,i,"LAST_YEAR_TMS_RATIO"))%>%</font></td>
					<%
						} else {
					%>
					<td><%=getFloatData(rVect01,i,"LAST_YEAR_TMS_RATIO")%>%</td>
					<%
						}
					%>
				</tr>
				<%	
						}
					}
				}	
				%>
			</table>		
		</div>
		<div style="clear:both"></div>
	</div>
	
	<div class="row02" >
		<div class="col03h">
			<img src="image/kspo_s_title_6.gif" alt="◈ <%=getData(rVect01,0,"STND_YEAR")%>년 목표대비 누적매출액  달성률">
		</div>
		<div class="col04h">
			<img src="image/kspo_s_title_7.gif" alt="◈ 전년동회 대비 누적매출액 비교">
		</div>
		<div class="col03" style="color:#454545;">
			<table class='list' width='95%'>				
				<tr>
					<th class="right_b blue" width="25%"  align=center style = "text-align:center;">구분</th>
					<th class="right_b blue" width="25%"  align=center>목표</th>
					<th class="right_b blue" width="25%" align=center>실적</th>
					<th class="right_b blue" width="25%" align=center>달성률</th>
					
				</tr>
				<%
				if(rVect01 != null) {
					for(int i=2; i<4; i++) {	
						if (getIntData(rVect01,i, "JOB_TYPE") == 2) {
				%>
				<tr>	
					<td style = "text-align:center;"><%=getData(rVect01,i,"MEET_NM")%></td>
					<td><%=getData(rVect01,i,"CUR_TMS_AMT")%></td>
					<td><%=getData(rVect01,i,"PREV_TMS_AMT")%></td>
					<%
						if (getFloatData(rVect01,i,"PREV_TMS_RATIO") < 0) {
					%>
					<td><font color="blue">△<%=Math.abs(getFloatData(rVect01,i,"PREV_TMS_RATIO"))%>%</font></td>
					<%
						} else {
					%>
					<td><%=getFloatData(rVect01,i,"PREV_TMS_RATIO")%>%</td>
					<%
						}
					%>
					
				</tr>
				<%	
						}
					}
				}	
				%>
			</table>
		</div>
		<div class="col04" style="color:#454545;">
			<table class='list' width='95%'>				
				<tr>
					<th class="right_b blue" width="25%"  align=center style = "text-align:center;">구분</th>
					<th class="right_b blue" width="25%" align=center>실적</th>
					<th class="right_b blue" width="25%" align=center>전년동회</th>
					<th class="right_b blue" width="25%" align=center>증감률</th>
					
				</tr>
				<%			
				if(rVect01 != null) {
					for(int i=2; i<4; i++) {	
						if (getIntData(rVect01,i, "JOB_TYPE") == 2) {
				%>
				<tr>	
					<td style = "text-align:center;"><%=getData(rVect01,i,"MEET_NM")%></td>
					<td><%=getData(rVect01,i,"PREV_TMS_AMT")%></td>
					
					<td><%=getData(rVect01,i,"LAST_YEAR_TMS_AMT")%></td>
					<%
						if (getFloatData(rVect01,i,"LAST_YEAR_TMS_RATIO") < 0) {
					%>
					<td><font color="blue">△<%=Math.abs(getFloatData(rVect01,i,"LAST_YEAR_TMS_RATIO"))%>%</font></td>
					<%
						} else {
					%>
					<td><%=getFloatData(rVect01,i,"LAST_YEAR_TMS_RATIO")%>%</td>
					<%
							}
						}
					%>
				</tr>
				<%	
					}
				}	
				%>
			</table>
		</div>
		<div style="clear:both"></div>
	</div>
	
</body>
</html>