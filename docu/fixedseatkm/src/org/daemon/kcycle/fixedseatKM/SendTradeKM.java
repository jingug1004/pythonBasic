package org.daemon.kcycle.fixedseatKM;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.sosfo.framework.config.ConfigFactory;
import org.sosfo.framework.connection.PoolManager;
import org.sosfo.framework.log.Log;

public class SendTradeKM {    
 
	static String COMM_NO = "";
	StringBuffer sb = new StringBuffer();
	ResultSet rs = null;
	PreparedStatement stmt = null;
	PreparedStatement prest = null;
	Connection connOracle = null;
	Connection commSqlServer = null;
	
	String MAX_TRANSITION_SEQ = "";
	String MAX_TRADE_DATE = "";
	
	private static ConfigFactory cf = ConfigFactory.getInstance();
	
	 public void execute() {
        Log.debug("DB", this, "자료 전송 시작------------------------------------>");
		try {
			 long startTime = System.currentTimeMillis();
			
			// 1. 해당 지점의 코드를 환경변수에서 가져온다.
			PoolManager conOraManger = PoolManager.getInstance();
			connOracle = conOraManger.getConnection("usrbm");
			
			// 2. 해당 지점에서 전송한 마지막 연번을 조회한다.
			String CommNo = "";
			long count_src_tot = 0;
			long count_src = 0;
			
			long MaxSeq = getLastTradeSeq(connOracle);
			Log.debug("DB", this, "MaxSeq:"+MaxSeq);
			
			PoolManager conMySqlManger = PoolManager.getInstance();
			commSqlServer = conMySqlManger.getConnection("fixedseatKM");
			
			connOracle.setAutoCommit(false);
			
			ResultSet rs = null;
			
			sb.setLength(0);
			sb.append("\n SELECT T.JOB_TYPE, T.SEQ, T.LOC, T.SALE_DATE,	");
			sb.append("\n        T.SALE_TIME, T.MENU_CODE, T.PRICE,	");
			sb.append("\n        T.QTY, T.AMT	");
			sb.append("\n FROM (       ");
			sb.append("\n 		SELECT 'F' AS JOB_TYPE,	");
			sb.append("\n 				(A.POSALES_DATE + A.POSALES_POS_NO + A.POSALES_BILL + B.POSALES1_SEQ) AS SEQ,	");
			sb.append("\n 				A.POSALES_LOC AS LOC,	");
			sb.append("\n 				A.POSALES_DATE AS SALE_DATE,	");
			sb.append("\n 				A.POSALES_TIME AS SALE_TIME,	");
			sb.append("\n 				B.POSALES1_SI_CODE AS MENU_CODE,	");
			sb.append("\n 				B.POSALES1_PRICE AS PRICE,	");
			sb.append("\n 				B.POSALES1_QTY AS QTY,	");
			sb.append("\n 				B.POSALES1_AMT AS AMT	");
			sb.append("\n 		  FROM KIOSK_POSALES A, KIOSK_POSALES1 B	");
			sb.append("\n 		 WHERE A.POSALES_COM = B.POSALES1_COM   	");
			sb.append("\n 		   AND A.POSALES_DATE = B.POSALES1_DATE   	");
			sb.append("\n 		   AND A.POSALES_LOC = B.POSALES1_LOC     	");
			sb.append("\n 		   AND A.POSALES_POS_NO = B.POSALES1_POS_NO 	");
			sb.append("\n 		   AND A.POSALES_BILL = B.POSALES1_BILL	");
			sb.append("\n 		) T	");
			sb.append("\n WHERE T.SEQ > ?	");
			sb.append("\n ORDER BY T.SEQ	");

			
			Log.debug("DB", this, sb.toString());
			prest = commSqlServer.prepareStatement(sb.toString());
			//prest.setString(1, CommNo);	
			prest.setLong(1, MaxSeq);	
			rs = prest.executeQuery();
			
			ArrayList <TradeKm> arrTrade = new ArrayList<TradeKm>();
			TradeKm tdItem;
			TradeKm trMax = new TradeKm();	
			count_src = 0;
			
			while (rs.next()) {
				 count_src ++;
				 tdItem = new TradeKm(
							 rs.getString("JOB_TYPE"),			 
							 rs.getString("SEQ"),
							 rs.getString("LOC"),
							 "", //rs.getString("LOC_NAME"),
							 rs.getString("SALE_DATE"),
							 rs.getString("SALE_TIME"),
							 rs.getString("MENU_CODE"),
							 "", //rs.getString("MENU_NAME"),
							 rs.getLong("PRICE"),
							 rs.getLong("QTY"),
							 rs.getLong("AMT"));
				 
				 arrTrade.add(tdItem);
							 
				 if (count_src % 10000 == 0) {	// 10,000건 단위로 DB서버로 전송
		             batchInsertTrade(connOracle, arrTrade);
		             trMax = arrTrade.get(arrTrade.size() -1);
		             arrTrade.clear();
				 }
			 }

			 if (arrTrade.size() > 0) {	
				 batchInsertTrade(connOracle, arrTrade);
				 trMax = arrTrade.get(arrTrade.size() -1);
			 }
			
			 
			 // 4. 연동DB서버에 자료 저장
             // 4-1. 트랙잭션 시작
             // 5. 최종 전송 일련번호 저장
             if (count_src > 0) {	// 전송한 자료가 있는 경우
				 UpdateMaxSeq(connOracle, trMax, count_src);
             }
             connOracle.commit();
             
			 long endTime = System.currentTimeMillis();    		 
			 Log.debug("DB", this, "처리건수:"+count_src+"건, 소요시간:"+(endTime - startTime)/1000.000f+"초");
             Log.debug("DB", this, "자료 전송 종료------------------------------------>");
			 
					
		} catch (ClassNotFoundException e) {
			Log.error("ERROR", this, "JDBC 드라이버 로드가 실패하였습니다.");
		} catch (Exception e) {
			Log.error("ERROR", this, "execute : " + "Exception  [" + e.toString()+ "] ");
		} finally {
			if (rs != null) {
				try { rs.close(); } catch (SQLException e) { }
				rs = null;
			}
			if (stmt != null) {
				try { stmt.close(); } catch (SQLException e) {  }
				stmt = null;
			}
			if (connOracle != null) {
				try { connOracle.close(); } catch (SQLException e) { }
				connOracle = null;
			}
			if (commSqlServer != null) {
				try { commSqlServer.close(); } catch (SQLException e) { }
				commSqlServer = null;
			}
		} 
	}
	 
	 public static void main(String[] args){
		 new SendTradeKM().execute();
	 }


	 private long getLastTradeSeq(Connection conn)
	 {
		long LastSeq = 0;
		 try {
			// 2-2. 최종 자료 조회			 
			sb.setLength(0);
			sb.append("\n SELECT NVL(MAX(SEQ),0) AS LAST_SEQ ");
			sb.append("\n FROM   TBRC_FS_STATUS_KM   ");
			sb.append("\n WHERE  JOB_TYPE = 'F'      ");	// 광명경륜장 지정좌석실
			sb.append("\n   AND  LOC = '80021'      ");	// 광명경륜장 지점번호
			 
			Log.debug("DB", this, sb.toString());
			stmt = conn.prepareStatement(sb.toString());
			rs = stmt.executeQuery();
			 
			if (rs.next()) {
				 LastSeq = rs.getLong("LAST_SEQ");				 
			}
	        rs.close();
			rs = null;
		} catch (SQLException e) {
			Log.error("ERROR", this, "getLastTradeSeq : " + "SQLException  [" + e.toString()+ "] ");
			Log.error("ERROR", this, "SQL String = " + sb.toString());
			return -1;
		}

        return LastSeq;
	 }
	 
	private int batchInsertTrade(Connection conn, ArrayList <TradeKm> arrTrade) {
		try {
			 // 4-2. Insert 문장  생성
    		sb.setLength(0);
			sb.append("\n  ");
			sb.append("\n INSERT INTO TBRC_FS_ISSUE_KM (              ");
			sb.append("\n  JOB_TYPE, SEQ, LOC, SALE_DATE,   ");
			sb.append("\n  SALE_TIME, MENU_CODE, PRICE,   QTY, AMT  ) ");
			sb.append("\n VALUES(?,?,?,?,?,?,?,?,?)                  ");
			
			Log.debug("DB", this, sb.toString());
			stmt = conn.prepareStatement(sb.toString());
			
			// 4-3. 1,000건씩 DB에 저장
			int ins_cnt = 0;
			int i = 0;
			for( TradeKm tr : arrTrade) {
				i = 1;
				ins_cnt ++;
				stmt.clearParameters();
				stmt.setString(i++, tr.getJobType());
				stmt.setString(i++, tr.getSeq());
				stmt.setString(i++, tr.getLoc());
				//stmt.setString(i++, tr.getLocName());
				stmt.setString(i++, tr.getSaleDate());
				stmt.setString(i++, tr.getSaleTime());				
				stmt.setString(i++, tr.getMenuCode());				
				//stmt.setString(i++, tr.getMenuName());				
				stmt.setLong(i++,   tr.getPrice());
				stmt.setLong(i++,   tr.getQty());
				stmt.setLong(i++,   tr.getAmt());
			     
				stmt.addBatch();
				if (ins_cnt % 1000 == 0) {	// 1,000건 단위로 DB Insert
					stmt.executeBatch();
			    }
			}
	        stmt.executeBatch();			
			stmt.close();
			
		} catch (SQLException e) {
			Log.error("ERROR", this, "batchInsertTrade : " + "SQLException  [" + e.toString()+ "] ");
			Log.error("ERROR", this, "SQL String = " + sb.toString());
		}
		return 0;
	}
	
	private int UpdateMaxSeq(Connection conn, TradeKm trade, Long nTransCnt)
	{	
		int i = 0;
		try {
			sb.setLength(0);
			sb.append("\n  ");
			sb.append("\n INSERT INTO TBRC_FS_STATUS_KM ( ");
			sb.append("\n        JOB_TYPE, LOC, SEQ, ISSUE_DT, TRANS_CNT, UPDT_DTM) ");
			sb.append("\n  VALUES(?,?,?,?,?,SYSDATE)");
			
		
			//Log.debug("DB", this, sb.toString());
			i = 1;
			stmt = conn.prepareStatement(sb.toString());
			stmt.setString(i++,   trade.getJobType());
			stmt.setString(i++,   trade.getLoc());
			stmt.setString(i++,   trade.getSeq());
			stmt.setString(i++,   trade.getSaleDate());
			stmt.setLong(i++,     nTransCnt);
			
			stmt.executeUpdate();
     
			stmt.close();
			
		} catch (SQLException e) {
			Log.error("ERROR", this, "UpdateMaxSeq : " + "SQLException  [" + e.toString()+ "] ");
			Log.error("ERROR", this, "SQL String = " + sb.toString());
			return -1;
		}
		return 0;
	}	
}
