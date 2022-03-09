<%@ page language="java" import="java.util.*, java.sql.*,java.lang.*,javax.sql.*,javax.naming.*,java.io.*,java.net.*,java.text.*" session="true" contentType="text/html;charset=EUC_KR" %>
<%@ include file="common/comDBManager.jsp" %>
<%
  String sRaceDay = request.getParameter("race_day");
  String sWinNo = request.getParameter("win_no");
  String sTsn =request.getParameter("tsn");

  if(sRaceDay == null) sRaceDay="";
  if(sWinNo == null) sWinNo="";
  if(sTsn == null) sTsn="";


%>
<%!

private double getDouble(Object obj){
    double value=0;
    if(obj!=null){
        value = Double.parseDouble((String)obj) ;
    }
    return value;
}

private String getCommaValue(double obj){
    return numformat(obj,"###,###");
}

public  String numformat(double src, String format) {
    try {
        return new DecimalFormat(format).format(src);
    } catch (Exception ex) {
        return "";
    }
    }

public  String format(double src, DecimalFormat format) {
try {
    return  format.format(src);
} catch (Exception ex) {
    return "";
}
}

%>
<% 

    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    String query ="";
   
    ArrayList tfList = new ArrayList(10); 
    int k =0;
    
    try { 
         Context context = new InitialContext();
         DataSource ds = (DataSource)context.lookup("jdbc/DAS");
         con = ds.getConnection();
         

    } catch(SQLException e) {
         
        out.println(e.toString());
        e.printStackTrace();   
        return;
    }
    
    

     try {
        
    	    //창구번호로 조회
    	    
    	    if(sTsn.equals("")){
	            query = "";
	            query = "";
                query += "              SELECT                                                                                                            ";
                query += "                      YEAR_TOTE,                                                                             \n ";
                query += "                      TO_CHAR(TO_DATE(DATE_TOTE,'MMDD'),'MM-DD') DATE_TOTE,                                                             \n ";
                query += "                      TO_CHAR(TO_DATE(TIME_TOTE,'HH24MISS'),'HH24:MI:SS') TIME_TOTE,                         \n ";
                query += "                      GUBUN,                                                                                 \n ";
                query += "                      CASE                                                                                   \n ";
                query += "                       WHEN GUBUN<>'POOL'  THEN                                                              \n ";
                query += "                          SUBSTR(TSN,4,1) || '-' ||                                                           \n ";
                query += "                          SUBSTR(TSN,5,4) || '-' ||                                                           \n ";
                query += "                          SUBSTR(TSN,9,4) || '-' ||                                                           \n ";
                query += "                          SUBSTR(TSN,13,4)                                                                    \n ";
                query += "                      ELSE  TSN                                                                               \n ";
                query += "                      END TSN,                                                                               \n ";
                query += "                      WIN_NO,                                                                                \n ";
                query += "                      TELLER_ID,                                                                             \n ";
                query += "                      TRAN_TYPE,                                                                             \n ";
                query += "                      STATUS,                                                                                \n ";
                query += "                      PERF_NO,                                                                               \n ";
                query += "                      MEET_NO,                                                                               \n ";
                query += "                      RACE_NO,                                                                               \n ";
                query += "                      AMOUNT,                                                                                \n ";
                query += "                      CONT2                                                                                  \n ";
                query += "                   FROM                                                                                      \n ";
                query += "                   (                                                                                         \n ";
                query += "                      SELECT                                                                                 \n ";
                query += "                          YEAR_TOTE,                                                                         \n ";
                query += "                          DATE_TOTE,                                                                         \n ";
                query += "                          TIME_TOTE,                                                                         \n ";
                query += "                          GUBUN,                                                                             \n ";
                query += "                          TSN,                                                                               \n ";
                query += "                          TFA,                                                                               \n ";
                query += "                          WIN_NO,                                                                            \n ";
                query += "                          TELLER_ID,                                                                         \n ";
                query += "                          TRAN_TYPE,                                                                         \n ";
                query += "                          DECODE(STATUS,1,'VALID','INVALID')  STATUS,                                        \n ";
                query += "                          TO_CHAR(PERF_NO) PERF_NO,                                                          \n ";
                query += "                          TO_CHAR(MEET_NO) MEET_NO,                                                          \n ";
                query += "                          TO_CHAR(RACE_NO) RACE_NO,                                                          \n ";
                query += "                          AMOUNT,                                                                            \n ";
                query += "                          CASE                                                                               \n ";
                query += "                           WHEN STATUS <>'1' THEN PKG_DAS.FUNC_GET_RECORD_STATUS(STATUS)||'<BR>'||BET1_SUMMARY||'<BR>'||BET2_SUMMARY||'<BR>'||BET3_SUMMARY     \n ";
                query += "                           ELSE BET1_SUMMARY||'<BR>'||BET2_SUMMARY||'<BR>'||BET3_SUMMARY                     \n ";
                query += "                          END CONT2                                                                                               \n  ";
                query += "                      FROM                                                                                                          \n  ";
                query += "                          VW_TF_SELL_01 B                                                                                         \n  ";
                query += "                      WHERE                                                                                                         \n  ";
                query += "                          YEAR_TOTE=SUBSTR( ? ,1,4) -- race_day                                                                           \n  ";
                query += "                          AND DATE_TOTE=SUBSTR( ? ,5,4) -- race_day                                                                      \n  ";
                query += "                          AND WIN_NO = ? -- win_no    \n  ";
                
                
         
                query += "                      UNION ALL                                                                              \n  ";
                query += "                      SELECT                                                                                                        \n  ";
                query += "                          YEAR_TOTE,                                                                                                     \n  ";
                query += "                          DATE_TOTE,                                                                         \n  ";
                query += "                          TIME_TOTE,                                                                         \n  ";
                query += "                          GUBUN,                                                                                             \n  ";
                query += "                          TSN,                                                                               \n  ";
                query += "                          TFA,                                                                               \n ";
                query += "                          WIN_NO,                                                                                                      \n  ";
                query += "                          TELLER_ID,                                                                                                   \n  ";
                query += "                          TRAN_TYPE,                                                                         \n  ";
                query += "                          DECODE(STATUS,1,'VALID','INVALID')  STATUS,                                               \n  ";
                query += "                          '' PERF_NO,                                                                                                  \n  ";
                query += "                          '' MEET_NO,                                                                                                  \n  ";
                query += "                          'PAID:' RACE_NO,                                                                                             \n  ";
                query += "                          AMOUNT_PAID AMOUNT,                                                                                       \n  ";
                query += "                          CASE                                                                               \n  ";
                query += "                           WHEN STATUS <>'1' THEN PKG_DAS.FUNC_GET_RECORD_STATUS(STATUS)                     \n  ";
                query += "                           ELSE ''                                                                           \n  ";
                query += "                          END CONT2                                                                                               \n  ";
                query += "                      FROM                                                                                                          \n  ";
                query += "                          VW_TF_CASH_S01_01                                                                                         \n  ";
                query += "                      WHERE                                                                                                         \n  ";
                query += "                          YEAR_TOTE=SUBSTR(?,1,4) -- race_day                                                                          \n  ";
                query += "                          AND DATE_TOTE=SUBSTR(?,5,4) -- race_day                                                                      \n  ";
                query += "                          AND WIN_NO=?  -- win_no    \n  ";
                
                query += "                      UNION ALL                                                                              \n  ";
                query += "                      SELECT                                                                                                        \n  ";
                query += "                          YEAR_TOTE,                                                                                                     \n  ";
                query += "                          DATE_TOTE,                                                                         \n  ";
                query += "                          TIME_TOTE,                                                                         \n  ";
                query += "                          GUBUN,                                                                                             \n  ";
                query += "                          TSN,                                                                               \n  ";
                query += "                          TFA,                                                                               \n ";
                query += "                          WIN_NO,                                                                                                      \n  ";
                query += "                          TELLER_ID,                                                                                                   \n  ";
                query += "                          TRAN_TYPE,                                                                                                 \n  ";
                query += "                          DECODE(STATUS,1,'VALID','INVALID')  STATUS,                                               \n  ";
                query += "                          '' PERF,                                                                                                  \n  ";
                query += "                          '' MEET,                                                                                                  \n  ";
                query += "                          '' RACE,                                                                                                  \n  ";
                query += "                          AMOUNT,                                                                                                   \n  ";
                query += "                          CASE                                                                               \n  ";
                query += "                           WHEN STATUS <>'1' THEN PKG_DAS.FUNC_GET_RECORD_STATUS(STATUS)                     \n  ";
                query += "                           ELSE ''                                                                           \n  ";
                query += "                          END CONT2                                                                                                   \n  ";
                query += "                      FROM                                                                                                          \n  ";
                query += "                          VW_TF_VOUCHER_SELL_01                                                                                     \n  ";
                query += "                      WHERE                                                                                                         \n  ";
                query += "                          YEAR_TOTE=SUBSTR(?,1,4) -- race_day                                                                           \n  ";
                query += "                          AND DATE_TOTE=SUBSTR(?,5,4) -- race_day                                                                      \n  ";
                query += "                          AND WIN_NO=? -- win_no    \n  ";
                
                query += "                      UNION ALL                                                                              \n  ";
                query += "                      SELECT                                                                                                        \n  ";
                query += "                          YEAR_TOTE,                                                                                                     \n  ";
                query += "                          DATE_TOTE,                                                                         \n  ";
                query += "                          TIME_TOTE,                                                                         \n  ";
                query += "                          GUBUN,                                                                                            \n  ";
                query += "                          TSN,                                                                               \n  ";
                query += "                          TFA,                                                                               \n ";
                query += "                          WIN_NO,                                                                                                      \n  ";
                query += "                          TELLER_ID,                                                                                                   \n  ";
                query += "                          TRAN_TYPE,                                                                         \n  ";
                query += "                          DECODE(STATUS,1,'VALID','INVALID')  STATUS,                                               \n  ";
                query += "                          '' PERF_NO,                                                                                                  \n  ";
                query += "                          '' MEET_NO,                                                                                                  \n  ";
                query += "                          'PAID:' RACE_NO,                                                                                             \n  ";
                query += "                          AMOUNT_PAID AMOUNT,                                                                                       \n  ";
                query += "                          CASE                                                                               \n  ";
                query += "                           WHEN STATUS <>'1' THEN PKG_DAS.FUNC_GET_RECORD_STATUS(STATUS)                     \n  ";
                query += "                           ELSE ''                                                                           \n  ";
                query += "                          END CONT2                                                                                                   \n  ";
                query += "                      FROM                                                                                                          \n  ";
                query += "                          VW_TF_VOUCH_CASH_S01_01                                                                                   \n  ";
                query += "                      WHERE                                                                                                         \n  ";
                query += "                          YEAR_TOTE=SUBSTR(?,1,4) -- race_day                                                                           \n  ";
                query += "                          AND DATE_TOTE=SUBSTR(?,5,4) -- race_day                                                                      \n  ";
                query += "                          AND WIN_NO=?    -- win_no    \n  ";
                
                query += "                      UNION ALL                                                                                                     \n  ";
                query += "                      SELECT                                                                                                        \n  ";
                query += "                          YEAR_TOTE,                                                                                                     \n  ";
                query += "                          DATE_TOTE,                                                                         \n  ";
                query += "                          TIME_TOTE,                                                                                          \n  ";
                query += "                          'DRAW' GUBUN,                                                                                             \n  ";
                query += "                          TSN,                                                                               \n  ";
                query += "                          TFA,                                                                               \n ";
                query += "                          WINDOW_ID WIN_NO,                                                                                            \n  ";
                query += "                          TELLER_ID,                                                                                         \n  ";
                query += "                          FUNC_TRANS_TYPE(TRANS_TYPE) TRAN_TYPE,                                                                     \n  ";
                query += "                          DECODE(RECORD_STATUS,1,'VALID','INVALID')  STATUS,                                 \n  ";
                query += "                          '' PERF,                                                                                                  \n  ";
                query += "                          '' MEET,                                                                                                  \n  ";
                query += "                          'CHECK:' RACE,                                                                                            \n  ";
                query += "                          AMOUNT,                                                                                                   \n  ";
                query += "                          CASE                                                                               \n  ";
                query += "                           WHEN RECORD_STATUS <>'1' THEN PKG_DAS.FUNC_GET_RECORD_STATUS(RECORD_STATUS)       \n  ";
                query += "                           ELSE ''                                                                           \n  ";
                query += "                          END CONT2                                                                                                  \n  ";
                query += "                      FROM                                                                                                          \n  ";
                query += "                          TF_DRAW_01                                                                                                \n  ";
                query += "                      WHERE                                                                                                         \n  ";
                query += "                          YEAR_TOTE=SUBSTR(?,1,4)      -- race_day                                                                     \n  ";
                query += "                          AND DATE_TOTE=SUBSTR(?,5,4)  -- race_day                                                                    \n  ";
                query += "                          AND WINDOW_ID=?         -- win_no    \n  ";
                
                query += "                      UNION ALL                                                                                                     \n  ";
                query += "                      SELECT                                                                                                        \n  ";
                query += "                          YEAR_TOTE,                                                                                                     \n  ";
                query += "                          DATE_TOTE,                                                                         \n  ";
                query += "                          TIME_TOTE,                                                                                          \n  ";
                query += "                          'RTRN' GUBUN,                                                                                             \n  ";
                query += "                          TSN,                                                                               \n  ";
                query += "                          TFA,                                                                               \n ";
                query += "                          WINDOW_ID WIN_NO,                                                                                            \n  ";
                query += "                          TELLER_ID,                                                                                         \n  ";
                query += "                          FUNC_TRANS_TYPE(TRANS_TYPE) TRAN_TYPE,                                                                     \n  ";
                query += "                          DECODE(RECORD_STATUS,1,'VALID','INVALID')  STATUS,                                 \n  ";
                query += "                          '' PERF_NO,                                                                                                  \n  ";
                query += "                          '' MEET_NO,                                                                                                  \n  ";
                query += "                          'CHECK:' RACE_NO,                                                                                            \n  ";
                query += "                          AMOUNT,                                                                                                   \n  ";
                query += "                          CASE                                                                               \n  ";
                query += "                           WHEN RECORD_STATUS <>'1' THEN PKG_DAS.FUNC_GET_RECORD_STATUS(RECORD_STATUS)       \n  ";
                query += "                           ELSE ''                                                                           \n  ";
                query += "                          END CONT2                                                                                                  \n  ";
                query += "                      FROM                                                                                                          \n  ";
                query += "                          TF_RETURN_01                                                                                              \n  ";
                query += "                      WHERE                                                                                                         \n  ";
                query += "                          YEAR_TOTE=SUBSTR(?,1,4)     -- race_day                                                                        \n  ";
                query += "                          AND DATE_TOTE=SUBSTR(?,5,4) -- race_day                                                                     \n  ";
                query += "                          AND WINDOW_ID=? -- win_no    \n  ";
                
                query += "                      UNION ALL                                                                                                     \n  ";
                query += "                      SELECT                                                                                                        \n  ";
                query += "                          YEAR_TOTE,                                                                                                     \n  ";
                query += "                          DATE_TOTE,                                                                         \n  ";
                query += "                          TIME_TOTE,                                                                                           \n  ";
                query += "                          'POOL' GUBUN,                                                                                             \n  ";
                query += "                          'CLOSE POOLS FOR CARD:'|| WRITE_CARD_NUMBER TSN,                                                          \n  ";
                query += "                          TFA,                                                                               \n ";
                query += "                          '' WIN_NO,                                                                                                   \n  ";
                query += "                          TO_NUMBER('') TELLER_ID,                                                                                     \n  ";
                query += "                          '' TRAN_TYPE,                                                                                              \n  ";
                query += "                          '' STATUS,                                                                                                \n  ";
                query += "                          TO_CHAR(INFO_PERF_NO) PERF_NO,                                                                               \n  ";
                query += "                          TO_CHAR(INFO_MEET_NO) MEET_NO,                                                                               \n  ";
                query += "                          TO_CHAR(WRITE_RACE_NUMBER) RACE_NO,                                                                          \n  ";
                query += "                          TO_NUMBER('') AMOUNT,                                                                                     \n  ";
                query += "                          '' CONT2                                                                                                  \n  ";
                query += "                      FROM                                                                                                          \n  ";
                query += "                          TF_CARD_UPD_S59_01                                                                                        \n  ";
                query += "                      WHERE                                                                                                         \n  ";
                query += "                          YEAR_TOTE=SUBSTR(?,1,4)     -- race_day                                                                       \n  ";
                query += "                          AND DATE_TOTE=SUBSTR(?,5,4) -- race_day     \n  ";
                
                query += "                      UNION ALL                                                                            \n  ";
                query += "                      SELECT                                                                               \n  ";
                query += "                            YEAR_TOTE,                                                                     \n  ";
                query += "                            DATE_TOTE,                                                                     \n  ";
                query += "                            TIME_TOTE,                                                                     \n  ";
                query += "                            'CASH' GUBUN,                                                                  \n  ";
                query += "                            TSN,                                                                           \n  ";
                query += "                            TFA,                                                                               \n ";
                query += "                            WINDOW_ID WIN_NO,                                                              \n  ";
                query += "                            TELLER_ID,                                                                     \n  ";
                query += "                            PKG_DAS.FUNC_GET_TRANS_TYPE (TRANS_TYPE) TRAN_TYPE,                            \n  ";
                query += "                            DECODE(RECORD_STATUS,1,'VALID','INVALID')  STATUS,                             \n  ";
                query += "                          '' PERF_NO,                                                                                                 \n  ";
                query += "                          '' MEET_NO,                                                                                                 \n  ";
                query += "                          '' RACE_NO,                                                                      \n  ";
                query += "                          TO_NUMBER('0') AMOUNT,                                                           \n  ";
                query += "                          PKG_DAS.FUNC_GET_RECORD_STATUS(RECORD_STATUS) CONT2                                \n  ";
                query += "                       FROM TF_CASH_S00_01                                                                 \n  ";
                query += "                      WHERE                                                                                                        \n  ";
                query += "                          YEAR_TOTE=SUBSTR(?,1,4)        -- race_day                                                                  \n  ";
                query += "                          AND DATE_TOTE=SUBSTR(?,5,4)    -- race_day                                          \n  ";
                query += "                          AND WINDOW_ID=?         -- win_no    \n  ";
                
                
                query += "                      UNION ALL                                                                                                    \n  ";
               
                query += "                   SELECT                                                                              \n  ";
                query += "                         YEAR_TOTE,                                                                    \n  ";
                query += "                         DATE_TOTE,                                                                    \n  ";
                query += "                         TIME_TOTE,                                                                    \n  ";
                query += "                         'VCASH' GUBUN,                                                                 \n  ";
                query += "                         TSN,                                                                          \n  ";
                query += "                         TFA,                                                                               \n ";
                query += "                         WINDOW_ID WIN_NO,                                                             \n  ";
                query += "                         TELLER_ID,                                                                    \n  ";
                query += "                         PKG_DAS.FUNC_GET_TRANS_TYPE (TRANS_TYPE) TRAN_TYPE,                           \n  ";
                query += "                         DECODE(RECORD_STATUS,1,'VALID','INVALID')  STATUS,                            \n  ";
                query += "                       '' PERF_NO,                                                                                           \n  ";
                query += "                       '' MEET_NO,                                                                                           \n  ";
                query += "                       '' RACE_NO,                                                                     \n  ";
                query += "                       TO_NUMBER('0') AMOUNT,                                                          \n  ";
                query += "                         PKG_DAS.FUNC_GET_RECORD_STATUS(RECORD_STATUS) CONT2                               \n  ";
                query += "                    FROM TF_VOUCH_CASH_S00_01                                                                \n  ";
                query += "                   WHERE                                                                                                  \n  ";
                query += "                       YEAR_TOTE=SUBSTR(?,1,4)            -- race_day                                                        \n  ";
                query += "                       AND DATE_TOTE=SUBSTR(?,5,4)        -- race_day                                       \n  ";
                query += "                       AND WINDOW_ID=?    -- win_no    \n  ";
                
              
                query += "                      UNION ALL                                                                                                    \n  ";
    
                query += "                   SELECT                                                                              \n  ";
                query += "                         YEAR_TOTE,                                                                    \n  ";
                query += "                         DATE_TOTE,                                                                    \n  ";
                query += "                         TIME_TOTE,                                                                    \n  ";
                query += "                         'IRSC' GUBUN,                                                                 \n  ";
                query += "                         TSN,                                                                          \n  ";
                query += "                         TFA,                                                                               \n ";
                query += "                         WINDOW_ID WIN_NO,                                                             \n  ";
                query += "                         TELLER_ID,                                                                    \n  ";
                query += "                         PKG_DAS.FUNC_GET_TRANS_TYPE (TRANS_TYPE) TRAN_TYPE,                           \n  ";
                query += "                         DECODE(RECORD_STATUS,1,'VALID','INVALID')  STATUS,                            \n  ";
                query += "                       '' PERF_NO,                                                                                           \n  ";
                query += "                       '' MEET_NO,                                                                                           \n  ";
                query += "                          'PAID:' RACE_NO,                                                                                             \n  ";
                query += "                          AMOUNT_PAID AMOUNT,                                                                                       \n  ";
                query += "                          CASE                                                                               \n  ";
                query += "                           WHEN RECORD_STATUS <>'1' THEN PKG_DAS.FUNC_GET_RECORD_STATUS(RECORD_STATUS)                     \n  ";
                query += "                           ELSE ''                                                                           \n  ";
                query += "                          END CONT2                                                                    \n  ";
                query += "                    FROM TF_IRS_CASH_S01_01_CRA                                                               \n  ";
                query += "                   WHERE                                                                                                  \n  ";
                query += "                       YEAR_TOTE=SUBSTR(?,1,4)     -- race_day                                                                \n  ";
                query += "                       AND DATE_TOTE=SUBSTR(?,5,4) -- race_day                                            \n  ";
                query += "                       AND WINDOW_ID=?        -- win_no    \n  ";
                

                query += "                   )                                                                                         \n  ";
                query += "                   ORDER BY YEAR_TOTE, DATE_TOTE, TFA --1,2,3       \n  ";
                
                
               pstmt = con.prepareStatement(query);

               k =1;
            
               pstmt.setString(k++, sRaceDay);
               pstmt.setString(k++, sRaceDay);
               pstmt.setString(k++, "00"+sWinNo);

               pstmt.setString(k++, sRaceDay);
               pstmt.setString(k++, sRaceDay);
               pstmt.setString(k++, "00"+sWinNo);
              
               pstmt.setString(k++, sRaceDay);
               pstmt.setString(k++, sRaceDay);
               pstmt.setString(k++, "00"+sWinNo);             

               pstmt.setString(k++, sRaceDay);
               pstmt.setString(k++, sRaceDay);
               pstmt.setString(k++, "00"+sWinNo);

               pstmt.setString(k++, sRaceDay);
               pstmt.setString(k++, sRaceDay);
               pstmt.setString(k++, "00"+sWinNo);

               pstmt.setString(k++, sRaceDay);
               pstmt.setString(k++, sRaceDay);
               pstmt.setString(k++, "00"+sWinNo);

               pstmt.setString(k++, sRaceDay);
               pstmt.setString(k++, sRaceDay);

               pstmt.setString(k++, sRaceDay);
               pstmt.setString(k++, sRaceDay);
               pstmt.setString(k++, "00"+sWinNo);

               pstmt.setString(k++, sRaceDay);
               pstmt.setString(k++, sRaceDay);
               pstmt.setString(k++, "00"+sWinNo);

               pstmt.setString(k++, sRaceDay);
               pstmt.setString(k++, sRaceDay);
               pstmt.setString(k++, "00"+sWinNo);               

               rs = pstmt.executeQuery();
              


               tfList = getResultSetToMap(rs);
	            
	            
	         
	           
	           
    	    }else{ //TSN 조회
    	    	
    	    	query="";
                query += "           SELECT                                                                                             \n ";
                query += "                      YEAR_TOTE,                                                                              \n ";
                query += "                      TO_CHAR(TO_DATE(DATE_TOTE,'MMDD'),'MM-DD') DATE_TOTE,                                   \n ";
                query += "                      TO_CHAR(TO_DATE(TIME_TOTE,'HH24MISS'),'HH24:MI:SS') TIME_TOTE,                          \n ";
                query += "                      GUBUN,                                                                                  \n ";
                query += "                      SUBSTR(TSN,4,1) || '-' ||SUBSTR(TSN,5,4) || '-' ||SUBSTR(TSN,9,4) || '-' ||SUBSTR(TSN,13,4) TSN,   \n ";
                query += "                      WIN_NO,     \n ";
                query += "                      TELLER_ID,     \n ";
                query += "                      TRAN_TYPE,     \n ";
                query += "                      STATUS,     \n ";
                query += "                      PERF_NO,     \n ";
                query += "                      MEET_NO,     \n ";
                query += "                      RACE_NO,     \n ";
                query += "                      AMOUNT,     \n ";
                query += "                      REPLACE(CONT2, '<BR>', chr(13)||chr(10)) AS CONT2     \n ";
                query += "                   FROM     \n ";
                query += "                   (     \n ";
                query += "                      SELECT     \n ";
                query += "                          YEAR_TOTE,     \n ";
                query += "                          DATE_TOTE,     \n ";
                query += "                          TIME_TOTE,     \n ";
                query += "                          GUBUN,     \n ";
                query += "                          TSN,     \n ";
                query += "                          TFA,     \n ";
                query += "                          WIN_NO,     \n ";
                query += "                          TELLER_ID,     \n ";
                query += "                          TRAN_TYPE,     \n ";
                query += "                          DECODE(STATUS,1,'VALID','INVALID')  STATUS,     \n ";
                query += "                          TO_CHAR(PERF_NO) PERF_NO,     \n ";
                query += "                          TO_CHAR(MEET_NO) MEET_NO,     \n ";
                query += "                          TO_CHAR(RACE_NO) RACE_NO,     \n ";
                query += "                          AMOUNT,                          \n ";
                query += "                          CASE                               \n ";
                query += "                           WHEN STATUS <>'1' THEN PKG_DAS.FUNC_GET_RECORD_STATUS(STATUS)||'<BR>'||BET1_SUMMARY||'<BR>'||BET2_SUMMARY||'<BR>'||BET3_SUMMARY    \n ";
                query += "                           ELSE BET1_SUMMARY||'<BR>'||BET2_SUMMARY||'<BR>'||BET3_SUMMARY    \n ";
                query += "                          END CONT2       \n ";
                query += "                      FROM               \n ";
                query += "                          VW_TF_SELL_01 B   \n ";
                query += "                      WHERE                    \n ";
                query += "                          TSN = '002'||REPLACE(UPPER(?),'-','')    -- tsn                                                           \n ";
                query += "                      UNION ALL             \n ";
                query += "                      SELECT       \n ";
                query += "                          YEAR_TOTE,     \n ";
                query += "                          DATE_TOTE,     \n ";
                query += "                          TIME_TOTE,     \n ";
                query += "                          GUBUN,         \n ";
                query += "                          TSN,           \n ";
                query += "                          TFA,     \n ";
                query += "                          WIN_NO,     \n ";
                query += "                          TELLER_ID,     \n ";
                query += "                          TRAN_TYPE,     \n ";
                query += "                          DECODE(STATUS,1,'VALID','INVALID')  STATUS,     \n ";
                query += "                          '' PERF_NO,     \n ";
                query += "                          '' MEET_NO,     \n ";
                query += "                          'PAID:' RACE_NO,    \n ";
                query += "                          AMOUNT_PAID AMOUNT,     \n ";
                query += "                          CASE     \n ";
                query += "                           WHEN STATUS <>'1' THEN PKG_DAS.FUNC_GET_RECORD_STATUS(STATUS)     \n ";
                query += "                           ELSE ''     \n ";
                query += "                          END CONT2     \n ";
                query += "                      FROM     \n ";
                query += "                          VW_TF_CASH_S01_01 B     \n ";
                query += "                      WHERE                        \n ";
                query += "                          TSN = '002'||REPLACE(UPPER(?),'-','')  -- tsn     \n ";
                query += "                      UNION ALL     \n ";
                query += "                      SELECT       \n ";
                query += "                          YEAR_TOTE,     \n ";
                query += "                          DATE_TOTE,     \n ";
                query += "                          TIME_TOTE,     \n ";
                query += "                          GUBUN,     \n ";
                query += "                          TSN,     \n ";
                query += "                          TFA,     \n ";
                query += "                          WIN_NO,     \n ";
                query += "                          TELLER_ID,     \n ";
                query += "                          TRAN_TYPE,     \n ";
                query += "                          DECODE(STATUS,1,'VALID','INVALID')  STATUS,     \n ";
                query += "                          '' PERF,     \n ";
                query += "                          '' MEET,     \n ";
                query += "                          '' RACE,     \n ";
                query += "                          AMOUNT,     \n ";
                query += "                          CASE     \n ";
                query += "                           WHEN STATUS <>'1' THEN PKG_DAS.FUNC_GET_RECORD_STATUS(STATUS)     \n ";
                query += "                           ELSE ''     \n ";
                query += "                          END CONT2     \n ";
                query += "                      FROM     \n ";
                query += "                          VW_TF_VOUCHER_SELL_01 B     \n ";
                query += "                      WHERE     \n ";
                query += "                          TSN = '002'||REPLACE(UPPER(?),'-','')  -- tsn     \n ";
                query += "                      UNION ALL     \n ";
                query += "                      SELECT     \n ";
                query += "                          YEAR_TOTE,     \n ";
                query += "                          DATE_TOTE,     \n ";
                query += "                          TIME_TOTE,     \n ";
                query += "                          GUBUN,     \n ";
                query += "                          TSN,     \n ";
                query += "                          TFA,     \n ";
                query += "                          WIN_NO,     \n ";
                query += "                          TELLER_ID,     \n ";
                query += "                          TRAN_TYPE,     \n ";
                query += "                          DECODE(STATUS,1,'VALID','INVALID')  STATUS,     \n ";
                query += "                          '' PERF_NO,     \n ";
                query += "                          '' MEET_NO,     \n ";
                query += "                          'PAID:' RACE_NO,     \n ";
                query += "                          AMOUNT_PAID AMOUNT,     \n ";
                query += "                          CASE     \n ";
                query += "                           WHEN STATUS <>'1' THEN PKG_DAS.FUNC_GET_RECORD_STATUS(STATUS)     \n ";
                query += "                           ELSE ''     \n ";
                query += "                          END CONT2     \n ";
                query += "                      FROM     \n ";
                query += "                          VW_TF_VOUCH_CASH_S01_01 B     \n ";
                query += "                      WHERE     \n ";
                query += "                          TSN = '002'||REPLACE(UPPER(?),'-','') -- tsn     \n ";
                query += "                      UNION ALL     \n ";
                query += "                      SELECT        \n ";
                query += "                            YEAR_TOTE,     \n ";
                query += "                            DATE_TOTE,     \n ";
                query += "                            TIME_TOTE,     \n ";
                query += "                            'CASH' GUBUN,     \n ";
                query += "                            TSN,     \n ";
                query += "                            TFA,     \n ";
                query += "                            WINDOW_ID WIN_NO,     \n ";
                query += "                            TELLER_ID,     \n ";
                query += "                            PKG_DAS.FUNC_GET_TRANS_TYPE (TRANS_TYPE) TRAN_TYPE,     \n ";
                query += "                            DECODE(RECORD_STATUS,1,'VALID','INVALID')  STATUS,     \n ";
                query += "                          '' PERF_NO,     \n ";
                query += "                          '' MEET_NO,     \n ";
                query += "                          '' RACE_NO,     \n ";
                query += "                          TO_NUMBER('0') AMOUNT,     \n ";
                query += "                           PKG_DAS.FUNC_GET_RECORD_STATUS(RECORD_STATUS) CONT2     \n ";
                query += "                       FROM TF_CASH_S00_01 B     \n ";
                query += "                      WHERE     \n ";
                query += "                          TSN = '002'||REPLACE(UPPER(?),'-','')  -- tsn     \n ";
                query += "                      UNION ALL     \n ";
                query += "                   SELECT     \n ";
                query += "                         YEAR_TOTE,     \n ";
                query += "                         DATE_TOTE,     \n ";
                query += "                         TIME_TOTE,     \n ";
                query += "                         'CASH' GUBUN,     \n ";
                query += "                         TSN,     \n ";
                query += "                         TFA,     \n ";
                query += "                         WINDOW_ID WIN_NO,     \n ";
                query += "                         TELLER_ID,     \n ";
                query += "                         PKG_DAS.FUNC_GET_TRANS_TYPE (TRANS_TYPE) TRAN_TYPE,     \n ";
                query += "                         DECODE(RECORD_STATUS,1,'VALID','INVALID')  STATUS,     \n ";
                query += "                       '' PERF_NO,     \n ";
                query += "                       '' MEET_NO,     \n ";
                query += "                       '' RACE_NO,     \n ";
                query += "                       TO_NUMBER('0') AMOUNT,     \n ";
                query += "                       PKG_DAS.FUNC_GET_RECORD_STATUS(RECORD_STATUS) CONT2     \n ";
                query += "                    FROM TF_VOUCH_CASH_S00_01 B     \n ";
                query += "                   WHERE     \n ";
                query += "                       TSN = '002'||REPLACE(UPPER(?),'-','')  -- tsn     \n ";
                query += "                   UNION ALL     \n ";
                query += "                   SELECT     \n ";
                query += "                          YEAR_TOTE,     \n ";
                query += "                          DATE_TOTE,     \n ";
                query += "                          TIME_TOTE,     \n ";
                query += "                          'IRSC' GUBUN,     \n ";
                query += "                          TSN,     \n ";
                query += "                          TFA,     \n ";
                query += "                          WINDOW_ID WIN_NO,     \n ";
                query += "                          TELLER_ID,     \n ";
                query += "                          PKG_DAS.FUNC_GET_TRANS_TYPE (TRANS_TYPE) TRAN_TYPE,     \n ";
                query += "                          DECODE(RECORD_STATUS,1,'VALID','INVALID')  STATUS,     \n ";
                query += "                          '' PERF_NO,     \n ";
                query += "                          '' MEET_NO,     \n ";
                query += "                          'PAID:' RACE_NO,     \n ";
                query += "                          AMOUNT_PAID AMOUNT,     \n ";
                query += "                          CASE     \n ";
                query += "                              WHEN RECORD_STATUS <>'1' THEN PKG_DAS.FUNC_GET_RECORD_STATUS(RECORD_STATUS)     \n ";
                query += "                              ELSE ''     \n ";
                query += "                          END CONT2     \n ";
                query += "                    FROM TF_IRS_CASH_S01_01_CRA B     \n ";
                query += "                   WHERE     \n ";
                query += "                       TSN = '002' || REPLACE(UPPER(?),'-','') -- tsn     \n ";
                query += "                     )   \n ";
   				query += "               ORDER BY YEAR_TOTE, DATE_TOTE, TFA --1,2,3     \n ";
    	    	
    	    	
    	    	
    	    	
                pstmt = con.prepareStatement(query);

                k =1;
                
                pstmt.setString(k++, sTsn);
                pstmt.setString(k++, sTsn);
                pstmt.setString(k++, sTsn);
                pstmt.setString(k++, sTsn);

                pstmt.setString(k++, sTsn);
                pstmt.setString(k++, sTsn);
                pstmt.setString(k++, sTsn);
              
                rs = pstmt.executeQuery();

                tfList = getResultSetToMap(rs);
    	    }

    	 
  
     } catch(SQLException e ) {
       
    	 e.printStackTrace();
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
<title>TF Search</title>
<link rel='stylesheet' href='css/table.css'>
</head>
<body>
<center>
<table border="0" cellpadding="0" cellspacing="0" width="100%">
<tr>
    <td>
    <table width="100%" cellspacing="1" cellpadding="2" border="0" class="tab">

<%
if(tfList.size() >0){

    for(int i =0; i < tfList.size() ; i++){
    	
        HashMap tfmap = new HashMap();
        
        tfmap = (HashMap)tfList.get(i);
%>
        <tr height="20" bgcolor="#FFFFFF">
            <td width="110" class="td_w_ce1"><%=tfmap.get("YEAR_TOTE")%>-<%=tfmap.get("DATE_TOTE")%> <%=tfmap.get("TIME_TOTE")%></td>
            <td width="50" class="td_w_ce1"><%=tfmap.get("GUBUN")== null?"":tfmap.get("GUBUN") %></td>
            <td width="160" class="td_w_ce1"><%=tfmap.get("TSN")== null?"":tfmap.get("TSN")%></td>
            <td width="40" class="td_w_ce1"><%=tfmap.get("WIN_NO")== null?"":tfmap.get("WIN_NO")%></td>
            <td width="60" class="td_w_ce1"><%=tfmap.get("TELLER_ID")== null?"":tfmap.get("TELLER_ID")%></td>
            <td width="50" class="td_w_ce1"><%=tfmap.get("TRAN_TYPE")== null?"":tfmap.get("TRAN_TYPE")%></td>
            <td width="35" class="td_w_ce1"><%=tfmap.get("PERF_NO")== null?"":tfmap.get("PERF_NO")%></td>
            <td width="35" class="td_w_ce1"><%=tfmap.get("MEET_NO")== null?"":tfmap.get("MEET_NO")%></td>
            <td width="50" class="td_w_ce1"><%=tfmap.get("RACE_NO")== null?"":tfmap.get("RACE_NO")%></td>
            <td width="85" class="td_w_ce1"><%=tfmap.get("STATUS")== null?"":tfmap.get("STATUS")%></td>
            <td width="80" class="td_w_c"><%=tfmap.get("AMOUNT")== null?"":getCommaValue(getDouble((String)tfmap.get("AMOUNT")))%></td>
            <td width="" class="td_w_ce1"><%=tfmap.get("CONT2")== null?"":tfmap.get("CONT2")%></td>         
        </tr> 
<%
    }
}else{
%>
        <tr height="20" bgcolor="#FFFFFF">
            <td colspan="12" width="100%" class="td_w_ce1">조회된 자료가 업습니다.</td>       
        </tr> 
<%    
}   
%>
    </table>
    </td>
</tr>
</table>
</center>
</body>
</html>
