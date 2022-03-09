<%@ page language="java" import="java.util.*, java.sql.*" session="true" contentType="text/html;charset=EUC_KR" %>
<%@ page import="javax.naming.*, javax.sql.*" %>
<%!  
	String getData(Vector rVector, int idx, String colname) {
		String strResult = "";
		Hashtable tHash = (Hashtable)rVector.elementAt(idx);
		strResult = (String)tHash.get(colname);
//System.out.println("getData("+","+idx+","+colname+")="+strResult);	
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
    int cnt04=0;		// 일자별 매출액
    //long startTime = System.currentTimeMillis();    	
    //long endTime = 0;    	
    
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    Connection con = null;
    
    try { 
    	Context ctx = new InitialContext();
    	if (ctx != null) { 
    		DataSource ds = (DataSource)ctx.lookup("java:/comp/env/jdbc/RBM"); 
    		//DataSource ds = (DataSource)ctx.lookup("jdbc/RBM");
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
    Vector rVect04 = new Vector();  
    String day_desc = "";
    try {    	
    	
		// 최근 회차 매출액 및 누적 매출액조회
        String query = "";
         
        query  = " --1.회차 매출액 및 누적 매출액                          \n";
        query += " WITH SALES_AMT /* rsm2090_s03 */                                                                                           \n";
        query += " AS (                                                                                                                       \n";
        query += "     -- 당회차 매출액                                                                                                                                                                                                                            \n";
        query += "     SELECT RN AS JOB_TYPE                                                                                                      \n";
        query += "              ,A.MEET_CD                                                                                                        \n";
        query += "              ,A.STND_YEAR                                                                                                      \n";
        query += "              ,A.TMS                                                                                                            \n";
        query += "             ,SUM(DIV_TOTAL) AS DIV_TOTAL                                                                                       \n";
        query += "     FROM VW_SDL_DT_SUM_GSUM A,                                                                                                 \n";
        query += "             (SELECT MEET_CD, STND_YEAR, TMS, RN                                                                                \n";
        query += "                 FROM (                                                                                                         \n";
        query += "                         SELECT MEET_CD, STND_YEAR, TMS,                                                                        \n";
        query += "                                ROW_NUMBER() OVER (PARTITION BY MEET_CD ORDER BY STND_YEAR DESC, TMS DESC) AS RN                \n";
        query += "                         FROM VW_SDL_INFO                                                                                       \n";
        query += "                         WHERE RACE_DAY < TO_CHAR(SYSDATE - DECODE(TO_CHAR(SYSDATE, 'd'), 5, 1, 7, 1, 1, 2, 0), 'yyyymmdd')     \n";                    
        query += "                          AND   MEET_CD IN ('001','003')                                                                        \n";
        query += "                          AND   STND_YEAR BETWEEN TO_CHAR(SYSDATE,'YYYY') -1 AND TO_CHAR(SYSDATE,'YYYY')                        \n";
        query += "                          GROUP BY MEET_CD, STND_YEAR, TMS                                                                      \n";
        query += "                          )                                                                                                     \n";
        query += "                 WHERE RN < 3 ) B                                                                                               \n";
        query += "     WHERE A.MEET_CD = B.MEET_CD                                                                                                \n";
        query += "     AND    A.STND_YEAR = B.STND_YEAR                                                                                           \n";
        query += "     AND    A.TMS          = B.TMS                                                                                              \n";
        query += "     GROUP BY  A.STND_YEAR, A.MEET_CD, A.TMS, B.RN                                                                              \n";
        query += "     UNION ALL                                                                                                                  \n";
        query += "     -- 당해년도 매출목표                                                                                                       \n";
        query += "     SELECT 3 AS TMS                                                                                                            \n";
        query += "              ,SUBSTR(CD,5,3)                                                                                                   \n";
        query += "              ,SUBSTR(CD,1,4)                                                                                                   \n";
        query += "              ,0                                                                                                                \n";
        query += "              ,TO_NUMBER(CD_NM) * 1000000 AS DIV_TOTAL                                                                          \n";
        query += "     FROM TBRK_SPEC_CD                                                                                                          \n";
        query += "     WHERE GRP_CD = '163'                                                                                                       \n";
        query += "     AND    CD LIKE  TO_CHAR(SYSDATE, 'YYYY')||'%'                                                                              \n";
        query += "     UNION ALL                                                                                                                  \n";
        query += "     -- 연간 매출누적금액                                                                                                       \n";
        query += "     SELECT DECODE(STND_YEAR,  TO_CHAR(SYSDATE,'YYYY'), 4, 5) TMS                                                               \n";
        query += "              ,MEET_CD                                                                                                          \n";
        query += "              ,STND_YEAR                                                                                                        \n";
        query += "              ,0                                                                                                                \n";
        query += "             ,SUM(DIV_TOTAL) AS DIV_TOTAL                                                                                       \n";
        query += "     FROM VW_SDL_DT_SUM_GSUM                                                                                                    \n";
        query += "     WHERE STND_YEAR BETWEEN  '2014' /**P*/ -1 AND  '2014' /**P*/                                                               \n";
        query += "     AND    MEET_CD  IN ('001', '003')                                                                                          \n";
        query += "     AND    TMS     <=  '29' /**P*/                                                                                             \n";
        query += "     GROUP BY STND_YEAR, MEET_CD                                                                                                \n";
        query += "     )                                                                                                                          \n";
        query += " SELECT MEET_CD                                                                                                             \n";
        query += "          ,TMS                                                                                                              \n";
        query += "          ,COL1 AS THIS_TMS_AMT                                                                                                             \n";
        query += "          ,COL2 AS PREV_TMS_AMT                                                                                                             \n";
        query += "          ,CASE WHEN COL1 < COL2 THEN '△'                                                                                  \n";
        query += "                  WHEN COL1 = COL2 THEN '-'                                                                                 \n";
        query += "                  ELSE '_'                                                                                                   \n";
        query += "                  END AS TMS_DIFF_SIGN                                                                                             \n";
        query += "         ,DECODE(COL2,0,0,ROUND(ABS(COL2-COL1)/COL2*100,1)) AS TMS_DIFF                                                       \n";
        query += "         ,TO_CHAR(COL4,'99,999') AS YEAR_PLAN                                                                                                              \n";
        query += "         ,TO_CHAR(COL5,'99,999') AS YEAR_AMT                                                                                                              \n";
        query += "         ,DECODE(COL4,0,0,ROUND(ABS(COL5/COL4)*100,1)) AS YEAR_ARCH_RATE                                                              \n";
        query += "         ,TO_CHAR(COL7,'99,999') AS PREV_YEAR_AMT                                                                                                              \n";
        query += "          ,CASE WHEN COL5 < COL7 THEN '△'                                                                                  \n";
        query += "                  WHEN COL5 = COL7 THEN '-'                                                                                 \n";
        query += "                  ELSE '_'                                                                                                   \n";
        query += "                  END AS YEAR_DIFF_SIGN                                                                                             \n";
        query += "         ,DECODE(COL7,0,0,ROUND(ABS(COL5-COL7)/COL7*100,1)) AS YEAR_DIFF_RATE                                               \n";
        query += " FROM (                                                                                                                     \n";
        query += "         SELECT MEET_CD                                                                                                     \n";
        query += "                  ,MAX(TMS) AS TMS                                                                                          \n";
        query += "                  ,ROUND(SUM(DECODE(JOB_TYPE,1,DIV_TOTAL))/100000000,0) AS COL1                                               \n";
        query += "                  ,ROUND(SUM(DECODE(JOB_TYPE,2,DIV_TOTAL))/100000000,0) AS COL2                                               \n";
        query += "                  ,ROUND(SUM(DECODE(JOB_TYPE,3,DIV_TOTAL))/100000000,0) AS COL4                                               \n";
        query += "                  ,ROUND(SUM(DECODE(JOB_TYPE,4,DIV_TOTAL))/100000000,0) AS COL5                                               \n";
        query += "                  ,ROUND(SUM(DECODE(JOB_TYPE,5,DIV_TOTAL))/100000000,0) AS COL7                                               \n";
        query += "         FROM SALES_AMT                                                                                                     \n";
        query += "         GROUP BY MEET_CD                                                                                                   \n";
        query += "         )                                                                                                                  \n";
        
        // 매일 아침7시에 생성되는 통계테이블에서 조회한다
        query  = " SELECT MEET_CD            \n";
        query += "       ,TMS                \n";
        query += "       ,THIS_TMS_AMT       \n";
        query += "       ,PREV_TMS_AMT       \n";
        query += "       ,TMS_DIFF_SIGN      \n";
        query += "       ,TMS_DIFF           \n";
        query += "       ,YEAR_PLAN          \n";
        query += "       ,YEAR_AMT           \n";
        query += "       ,YEAR_ARCH_RATE     \n";
        query += "       ,PREV_YEAR_AMT      \n";
        query += "       ,YEAR_DIFF_SIGN     \n";
        query += "       ,YEAR_DIFF_RATE     \n";
        query += " FROM TBRS_SALES_RSLT_SUMMRY \n";
                
        pstmt = con.prepareStatement(query);
        
		rs = pstmt.executeQuery();
        //endTime = System.currentTimeMillis();  
		//System.out.println("소요시간1:"+(endTime - startTime)/1000.000f+"초");
		//startTime = endTime;
		
        while (rs.next()) {
			Hashtable rHash = new Hashtable();
			rHash.put("MEET_CD",     	   	rs.getString("MEET_CD"));
			rHash.put("TMS",     	   		rs.getString("TMS"));
			rHash.put("THIS_TMS_AMT",     	rs.getString("THIS_TMS_AMT"));
			rHash.put("PREV_TMS_AMT",     	rs.getString("PREV_TMS_AMT"));
			rHash.put("TMS_DIFF_SIGN",      rs.getString("TMS_DIFF_SIGN"));
			rHash.put("TMS_DIFF",    		rs.getString("TMS_DIFF"));
			rHash.put("YEAR_PLAN",      	rs.getString("YEAR_PLAN"));
			rHash.put("YEAR_AMT", 			rs.getString("YEAR_AMT"));
			rHash.put("YEAR_ARCH_RATE", 	rs.getString("YEAR_ARCH_RATE"));
			rHash.put("PREV_YEAR_AMT", 		rs.getString("PREV_YEAR_AMT"));
			rHash.put("YEAR_DIFF_SIGN",    	rs.getString("YEAR_DIFF_SIGN"));
			rHash.put("YEAR_DIFF_RATE",    	rs.getString("YEAR_DIFF_RATE"));
			
			rVect01.addElement(rHash);	
			cnt01 ++;
		}
        //endTime = System.currentTimeMillis();  
		//System.out.println("소요시간2:"+(endTime - startTime)/1000.000f+"초");
		//startTime = endTime;
		
        // 차트용 자료 조회 
        query  =" -- 2. 차트용 자료(회차별 매출액)  \n";
        query +=" WITH X AS (                                                                                                   \n";
        query +="  SELECT MEET_CD, STND_YEAR, MAX(TMS) AS TMS                                                                   \n";
        query +="  FROM   VW_SDL_INFO                                                                                           \n";
        //query +="  WHERE RACE_DAY < TO_CHAR(SYSDATE - DECODE(TO_CHAR(SYSDATE, 'd'), 5, 1, 7, 1, 1, 2, 0), 'yyyymmdd')           \n";
        query +="  WHERE RACE_DAY < TO_CHAR(SYSDATE, 'yyyymmdd')                                                                \n";
        query +="  AND     MEET_CD IN ('001','003')                                                                             \n";
        query +="  AND     STND_YEAR = TO_CHAR(SYSDATE,'YYYY')                                                                  \n";
        query +="  GROUP BY MEET_CD, STND_YEAR                                                                                  \n";
        query +="  )                                                                                                            \n";
        query +="  SELECT B.MEET_CD AS MEET_CD                                                                                  \n";
        query +="        ,B.RACE_DY ||'\\n'||B.TMS ||'회' AS TMS                                                                \n";
        query +="        ,NVL(ROUND(SUM(CASE WHEN B.STND_YEAR=X.STND_YEAR THEN DIV_TOTAL END)/100000000),0) AS CUR_YEAR_TMS_AMT        \n";
        query +="        ,NVL(ROUND(SUM(CASE WHEN B.STND_YEAR=X.STND_YEAR -1 THEN DIV_TOTAL END)/100000000),0)  AS LAST_YEAR_TMS_AMT   \n";
        query +="  FROM  X, (                                                                                                   \n";
        query +="            SELECT A.STND_YEAR, A.MEET_CD, A.TMS 																\n";
        query +="                   ,TO_CHAR(TO_DATE(RACE_DAY,'YYYYMMDD'), 'DY') AS RACE_DY										\n";
        query +="                   ,TO_CHAR(TO_DATE(RACE_DAY,'YYYYMMDD')-1, 'D') AS RACE_D										\n";
        query +="                   ,SUM(DIV_TOTAL) AS DIV_TOTAL                           		             					\n";
        query +="            FROM   TBES_SDL_DT_SUM A, VW_SDL_INFO B                                                            \n";
        query +="            WHERE  A.STND_YEAR BETWEEN TO_CHAR(SYSDATE, 'YYYY') -1 AND TO_CHAR(SYSDATE,'YYYY')                 \n";
        query +="            AND    A.MEET_CD IN ('001','003')             	                                                    \n";    
        query +="    		 AND    A.MEET_CD   = B.MEET_CD                 	                                                \n";
        query +="            AND    A.STND_YEAR = B.STND_YEAR                   	                                            \n";
        query +="            AND    A.TMS       = B.TMS                                                                 		\n";
        query +="            AND    A.DAY_ORD   = B.DAY_ORD                                                                     \n";
        query +="            GROUP BY A.STND_YEAR, A.MEET_CD, A.TMS, B.RACE_DAY                                                 \n";                   
        query +="            ) B                                                                                                \n";                   
        query +="  WHERE 1=1                                                                                                    \n";
        query +="  AND   X.MEET_CD = B.MEET_CD                                                                                  \n";
        query +="  AND   B.TMS BETWEEN X.TMS -2 AND X.TMS                                                                       \n";
        query +="  AND   B.RACE_DY||B.TMS != TO_CHAR(SYSDATE, 'DY')||X.TMS                                                      \n";
        query +="  GROUP BY B.MEET_CD, B.TMS, B.RACE_DY, B.RACE_D                                                               \n";
        query +="  ORDER BY B.MEET_CD, B.TMS, B.RACE_D                                                                          \n";
        
        query  =" -- 2. 차트용 자료(회차별 매출액)         \n";
        query +=" SELECT MEET_CD                      \n";
        query +="        ,TMS                         \n";
        query +="        ,CUR_YEAR_TMS_AMT            \n";
        query +="        ,LAST_YEAR_TMS_AMT           \n";
        query +=" FROM   TBRS_SALES_RSLT_SUMMRY_CHART \n";
        
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
        query  ="  --3. 당일 경주정보 조회 \n";
        query +="  SELECT '◈ '||DECODE(MEET_CD,'001','경륜','경정') || ' 제'||TMS||'회차 '||DAY_ORD||'일차 ('||TO_CHAR(SYSDATE,'YYYY')||'년 '||TO_CHAR(SYSDATE, 'MM')||'월 '||TO_CHAR(SYSDATE, 'DD')||'일)' AS DAY_DESC \n";
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

		
        // 지점 차트용 자료 조회
		query  = " WITH X AS (                                                                                                                      \n";
		query += "     SELECT MEET_CD, STND_YEAR, TMS, RNUM                                                                                         \n";
		query += "     FROM (                                                                                                                       \n";
		query += "             SELECT MEET_CD, STND_YEAR, TMS, ROW_NUMBER() OVER(PARTITION BY MEET_CD ORDER BY STND_YEAR DESC, TMS DESC) AS RNUM    \n";                                                            
		query += "             FROM  (SELECT MEET_CD, STND_YEAR, TMS                                                                                \n";
		query += "                       FROM VW_SDL_INFO                                                                                           \n";
		query += "                       WHERE RACE_DAY < TO_CHAR(SYSDATE - DECODE(TO_CHAR(SYSDATE, 'd'), 5, 1, 7, 1, 1, 2, 0), 'yyyymmdd')         \n";
		query += "                       GROUP BY MEET_CD, STND_YEAR, TMS)                                                                          \n";               
		query += "             WHERE MEET_CD IN ('001','003')                                                                                       \n";
		query += "             )                                                                                                                    \n";
		query += "     WHERE RNUM < 3                                                                                                               \n";
		query += "     )                                                                                                                            \n";
		query += " SELECT A.MEET_CD, A.COMM_NO, C.CD_NM AS COMM_NM,                                                                                 \n";
		query += "          ROUND(SUM(DECODE(X.RNUM, 1, DIV_TOTAL, 0))/100000000,1) AS CUR_TMS_AMT,                                            \n";
		query += "          ROUND(SUM(DECODE(X.RNUM, 2, DIV_TOTAL, 0))/100000000,1) AS PREV_TMS_AMT                                            \n";
		query += " FROM  (                                                                                                                          \n";
		query += "           SELECT STND_YEAR, MEET_CD, TMS,                                                                                        \n";
		query += "                     CASE WHEN SELL_CD IN ('02','04') THEN '29'                                                                   \n";
		query += "                            WHEN COMM_NO < 10 THEN '01'                                                                           \n";
		query += "                             ELSE COMM_NO END AS COMM_NO,                                                                         \n";
		query += "                     SUM(DIV_TOTAL) AS DIV_TOTAL                                                                                  \n";
		query += "           FROM VW_SDL_DT_SUM_GSUM                                                                                                \n";
		query += "           WHERE STND_YEAR BETWEEN TO_CHAR(SYSDATE,'YYYY') -1 AND TO_CHAR(SYSDATE,'YYYY')                                         \n";
		query += "           AND    MEET_CD IN ('001','003')                                                                                        \n";
		query += "           GROUP BY STND_YEAR, MEET_CD, TMS,                                                                                      \n";
		query += "                     CASE WHEN SELL_CD IN ('02','04') THEN '29'                                                                   \n";
		query += "                            WHEN COMM_NO < 10 THEN '01'                                                                           \n";
		query += "                             ELSE COMM_NO END                                                                                     \n";
		query += "           ) A, X, TBRK_SPEC_CD C                                                                                                 \n";
		query += " WHERE A.MEET_CD = X.MEET_CD                                                                                                      \n";
		query += " AND    A.STND_YEAR = X.STND_YEAR                                                                                                 \n";
		query += " AND    A.TMS = X.TMS                                                                                                             \n";
		query += " AND    A.COMM_NO = C.CD(+)                                                                                                       \n";
		query += " AND    C.GRP_CD(+) = '060'                                                                                                       \n";
		query += " GROUP BY A.MEET_CD, A.COMM_NO, C.CD_NM                                                                                           \n";
		query += " ORDER BY 1, 4 DESC                                                                                                               \n";
		            

        query  = " -- 4. 지점 차트용 자료 조회                         \n";
        query += " SELECT MEET_CD                     \n";
		query += "       ,COMM_NO                     \n";
		query += "       ,COMM_NM                     \n";
		query += "       ,CUR_TMS_AMT                 \n";
		query += "       ,PREV_TMS_AMT                \n";
        query += " FROM  TBRS_SALES_RSLT_SUMMRY_BRNC  \n";
        query += " ORDER BY 1, 4 DESC                 \n";
        
        pstmt = con.prepareStatement(query);

        rs = pstmt.executeQuery();
        //endTime = System.currentTimeMillis();  
		//System.out.println("소요시간3:"+(endTime - startTime)/1000.000f+"초");
		//startTime = endTime;
		
        while (rs.next()) {
			Hashtable rHash = new Hashtable();
			rHash.put("MEET_CD",     	   		rs.getString("MEET_CD"));
			rHash.put("COMM_NM",     	   		rs.getString("COMM_NM"));
			rHash.put("CUR_TMS_AMT",            rs.getString("CUR_TMS_AMT"));
			rHash.put("PREV_TMS_AMT",    	    rs.getString("PREV_TMS_AMT"));
			
			rVect04.addElement(rHash);	
			cnt04 ++;
		}
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
	<link rel="stylesheet" href="css/resttable2.css" type="text/css" />
	<link rel="stylesheet" href="css/webfont.css" type="text/css" />
	<style type="text/css">
		* {margin:0; padding:0}
		.c_both {clear:both}
		.title {float:center; text-align:center; height: 80px; margin:10px;  border:0px solid green;}
		#title_1 {width:500px;  height: 25px; background-image:url(image/kspo_s_title_1.gif); background-repeat: no-repeat; font-weight:bold; border:0px solid blue;}
		
		.row01  {padding-top:10px; font-size:16px; font-size:16px; border:0px solid blue;}
		.row02  {padding-top:10px; font-size:16px; font-size:16px; border:0px solid blue;}
		.col01  {width:48%; float:left;  height: 230px; margin-left:15px;  border:0px solid red}
		.col02  {width:48%; float:right; height: 230px; margin-right:15px; border:0px solid red}		
		.col03h {width:48%; float:left;  height: 30px;  margin-left:15px;  border:0px solid red}
		.col04h {width:48%; float:right; height: 30px;  margin-right:15px; border:0px solid red}		
		.col03  {width:48%; float:left;  height: 100px; margin-left:15px;  border:0px solid red}
		.col04  {width:48%; float:right; height: 100px; margin-right:15px; border:0px solid red}		
		.col05  {width:48%; float:left;  height: 25px;  margin-left:15px;  border:0px solid red}
		.col06  {width:48%; float:right; height: 25px;  margin-right:15px; border:0px solid red}		
		.col07  {width:98%; float:left;  height: 50px;  margin-left:15px;  font-weight:bold; border:0px solid red}
		.col08  {width:48%; float:left;  height: 230px; margin-left:15px;  border:0px solid red}
		.col09  {width:48%; float:right; height: 230px; margin-right:15px; border:0px solid red}		
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

	    var options = {
	      title: '',
	      legend:{position:'top',textStyle:{fontSize: 15, fontName:'나눔고딕' },alignment:'center'},
	      chartArea:{left:50,top:60,width:"90%",height:"50%"},
	      hAxis:{textStyle:{fontSize:14, fontName:'나눔고딕'}},
	      vAxis:{textStyle:{fontSize:14, fontName:'나눔고딕'}},
	      tooltip:{textStyle:{fontSize:14, fontName:'나눔고딕'}},
	      pointSize:10
	    };
	
	    var options_kboat = {
	      title: '',
	      legend:{position:'top',textStyle:{fontSize: 15, fontName:'나눔고딕' },alignment:'center'},
	      chartArea:{left:50,top:60,width:"90%",height:"50%"},
	      hAxis:{textStyle:{fontSize:14, fontName:'나눔고딕'}},
	      vAxis:{textStyle:{fontSize:14, fontName:'나눔고딕'}},
	      tooltip:{textStyle:{fontSize:14, fontName:'나눔고딕'}},
	      pointSize:10
	    };
		  
	    var options_jijum = {
	      title: '',
	      legend:{position:'top',textStyle:{fontSize: 15, fontName:'나눔고딕' },alignment:'center'},
	      chartArea:{left:50,top:60,width:"90%",height:"50%"},
	      hAxis:{textStyle:{fontSize:14, fontName:'나눔고딕'}},
	      vAxis:{textStyle:{fontSize:14, fontName:'나눔고딕'}},
	      tooltip:{textStyle:{fontSize:14, fontName:'나눔고딕'}},
	      pointSize:10
	    };
	    
	    var data = google.visualization.arrayToDataTable([
	      ['회차', '금년도', '전년도']	      
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
	
	    var chart = new google.visualization.LineChart(document.getElementById('chart_div'));
	    chart.draw(data, options);
	    
	    var data3 = google.visualization.arrayToDataTable([
	      ['회차', '금년도', '전년도']
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

		<!-- 경륜 지점 매출 -->
	    var data10 = google.visualization.arrayToDataTable([
	      ['지점', '당회차', '전회차']	      
		<%
		if(rVect04 != null) {
			if (cnt04>0) {
				for(int i=0; i<cnt04; i++) {	
					if (getData(rVect04,i,"MEET_CD").equals("001")) {
		%>
		    ,['<%=getData(rVect04,i,"COMM_NM")%>',<%=getData(rVect04,i,"CUR_TMS_AMT")%>,<%=getData(rVect04,i,"PREV_TMS_AMT")%>]
		<%
						}
					}
				}
			}
		%>
	    ]);
	
	    var chart10 = new google.visualization.LineChart(document.getElementById('chart_div10'));
	    chart10.draw(data10, options_jijum);
	    
		<!-- 경정 지점 매출 -->
	    var data13 = google.visualization.arrayToDataTable([
	      ['지점', '당회차', '전회차']     
		<%
		if(rVect04 != null) {
			if (cnt04>0) {
				for(int i=0; i<cnt04; i++) {	
					if (getData(rVect04,i,"MEET_CD").equals("003")) {
		%>
		    ,['<%=getData(rVect04,i,"COMM_NM")%>',<%=getData(rVect04,i,"CUR_TMS_AMT")%>,<%=getData(rVect04,i,"PREV_TMS_AMT")%>]
		<%
						}
					}
				}
			}
		%>
	    ]);
	
	    var chart13 = new google.visualization.LineChart(document.getElementById('chart_div13'));
	    chart13.draw(data13, options_jijum);	    

	  }
	</script>
</head>
<body style="font-family:'나눔고딕', NanumGothic, '굴림', 'Gulim'">

	<div class="title">
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
	</div>
	
	<div class="row02" >
		<div class="col01" style="color:#454545;"><img src="image/kspo_s_title_8.gif" alt="◈ 최근 5회차 경륜 매출">
			<div id="chart_div"  style="width: '100%'; height: 200px;"></div>
		</div>
		<div class="col02" style="color:#454545;"><img src="image/kspo_s_title_9.gif" alt="◈ 최근 5회차 경정 매출">
			<div id="chart_div3" style="width: '100%'; height: 200px;"></div>
		</div>
		<div style="clear:both"></div>
	</div>
	
	
	<div class="row02" >
		<div class="col03h">
			<img src="image/kspo_s_title_10.gif" alt="◈ 경륜 최근 회차별 매출액 비교">
		</div>
		<div class="col04h">
			<img src="image/kspo_s_title_11.gif" alt="◈ 경정 최근 회차별 매출액 비교">
		</div>
		<div class="col03" style="color:#454545;">
			<table class='list' width='95%'>
				<tr>
					<th class="right_b blue" colspan="3" width="25%"  align=center style = "text-align:center;">실적</th>
					<th class="right_b blue" colspan="3" width="25%"  align=center style = "text-align:center;">연간 누계</th>
					<th class="right_b blue" colspan="2" width="25%"  align=center style = "text-align:center;">전년동기 대비</th>
				</tr>
				<tr>
					<th class="right_b blue" width="11%"  align=center>
						<%
							if(rVect01 != null) {
								for(int i=0; i<cnt01; i++) {
									if ("001".equals(getData(rVect01,i, "MEET_CD"))) {
										%>
										<%=getData(rVect01,i,"TMS")%>회차
										<%										
									}
										
								}									
							}								
						%>					
						</th>
					<th class="right_b blue" width="11%" align=center>전회</th>
					<th class="right_b blue" width="13%" align=center>증감률</th>
					<th class="right_b blue" width="13%"  align=center>계획</th>
					<th class="right_b blue" width="12%" align=center>실적</th>
					<th class="right_b blue" width="13%" align=center>달성률</th>
					<th class="right_b blue" width="12%" align=center>실적</th>
					<th class="right_b blue" width="13%" align=center>증감률</th>					
				</tr>
				<%
				if(rVect01 != null) {
					for(int i=0; i<cnt01; i++) {
						if ("001".equals(getData(rVect01,i, "MEET_CD"))) {
				%>
				<tr>	
					<td><%=getData(rVect01,i,"THIS_TMS_AMT")%></td>
					<td><%=getData(rVect01,i,"PREV_TMS_AMT")%></td>
					<td><font color="blue">
					<%
						if ("△".equals(getData(rVect01,i,"TMS_DIFF_SIGN"))) {
					%>
					△
					<%
						}
					%>
					<%=getFloatData(rVect01,i,"TMS_DIFF")%>%</font></td>
					<td><%=getData(rVect01,i,"YEAR_PLAN")%></td>
					<td><%=getData(rVect01,i,"YEAR_AMT")%></td>
					<td><font color="blue"><%=getFloatData(rVect01,i,"YEAR_ARCH_RATE")%>%</font></td>
					<td><%=getData(rVect01,i,"PREV_YEAR_AMT")%></td>
					<td><font color="blue">
					<%
						if ("△".equals(getData(rVect01,i,"YEAR_DIFF_SIGN"))) {
					%>
					△
					<%
						}
					%>
					<%=getFloatData(rVect01,i,"YEAR_DIFF_RATE")%>%</font></td>
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
					<th class="right_b blue" colspan="3" width="25%"  align=center style = "text-align:center;">실적</th>
					<th class="right_b blue" colspan="3" width="25%"  align=center style = "text-align:center;">연간 누계</th>
					<th class="right_b blue" colspan="2" width="25%"  align=center style = "text-align:center;">전년동기 대비</th>
				</tr>
				<tr>
					<th class="right_b blue" width="11%"  align=center>
						<%
							if(rVect01 != null) {
								for(int i=0; i<cnt01; i++) {
									if ("003".equals(getData(rVect01,i, "MEET_CD"))) {
										%>
										<%=getData(rVect01,i,"TMS")%>회차
										<%										
									}
										
								}									
							}								
						%>					
						</th>
					<th class="right_b blue" width="11%" align=center>전회</th>
					<th class="right_b blue" width="13%" align=center>증감률</th>
					<th class="right_b blue" width="13%"  align=center>계획</th>
					<th class="right_b blue" width="12%" align=center>실적</th>
					<th class="right_b blue" width="13%" align=center>달성률</th>
					<th class="right_b blue" width="12%" align=center>실적</th>
					<th class="right_b blue" width="13%" align=center>증감률</th>					
				</tr>
				<%
				if(rVect01 != null) {
					for(int i=0; i<cnt01; i++) {
						if ("003".equals(getData(rVect01,i, "MEET_CD"))) {
				%>
				<tr>	
					<td><%=getData(rVect01,i,"THIS_TMS_AMT")%></td>
					<td><%=getData(rVect01,i,"PREV_TMS_AMT")%></td>
					<td><font color="blue">
					<%
						if ("△".equals(getData(rVect01,i,"TMS_DIFF_SIGN"))) {
					%>
					△
					<%
						}
					%>
					<%=getFloatData(rVect01,i,"TMS_DIFF")%>%</font></td>
					<td><%=getData(rVect01,i,"YEAR_PLAN")%></td>
					<td><%=getData(rVect01,i,"YEAR_AMT")%></td>
					<td><font color="blue"><%=getFloatData(rVect01,i,"YEAR_ARCH_RATE")%>%</font></td>
					<td><%=getData(rVect01,i,"PREV_YEAR_AMT")%></td>
					<td><font color="blue">
					<%
						if ("△".equals(getData(rVect01,i,"YEAR_DIFF_SIGN"))) {
					%>
					△
					<%
						}
					%>
					<%=getFloatData(rVect01,i,"YEAR_DIFF_RATE")%>%</font></td>
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
		<div class="col08" style="color:#454545;"><img src="image/kspo_s_title_8.gif" alt="◈ 최근 5회차 경륜 매출">
			<div id="chart_div10"  style="width: '100%'; height: 200px;"></div>
		</div>
		<div class="col09" style="color:#454545;"><img src="image/kspo_s_title_9.gif" alt="◈ 최근 5회차 경정 매출">
			<div id="chart_div13" style="width: '100%'; height: 200px;"></div>
		</div>
		<div style="clear:both"></div>
	</div>
		
</body>
</html>