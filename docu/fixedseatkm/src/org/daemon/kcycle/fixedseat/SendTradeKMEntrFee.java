package org.daemon.kcycle.fixedseat;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.sosfo.framework.config.ConfigFactory;
import org.sosfo.framework.connection.PoolManager;
import org.sosfo.framework.log.Log;

/* ������ ������ ���� ���� Ƽ�� ����� */
public class SendTradeKMEntrFee {    
 
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
        Log.debug("DB", this, "(���������)�ڷ� ���� ����------------------------------------>");
		try {
			 long startTime = System.currentTimeMillis();
			
			// 1. �ش� ������ �ڵ带 ȯ�溯������ �����´�.
			PoolManager conOraManger = PoolManager.getInstance();
			connOracle = conOraManger.getConnection("zenius");

			PoolManager conSqlServerManger = PoolManager.getInstance();
			commSqlServer = conSqlServerManger.getConnection("fixedseatkmef");

			connOracle.setAutoCommit(false);

			// 2. �ش� �������� ������ ������ ������ ��ȸ�Ѵ�.						
			// ��Һ��� ���� ���۰��� �����´�
			String CommNo = "";
			String MaxSeq = "";
			long count_src_tot = 0;
			long count_src = 0;
			
			sb.setLength(0);
			sb.append("\n SELECT COMM_NO, NVL(MAX(SEQ_NO),0) AS LAST_SEQ_NO ");
			sb.append("\n FROM   TBIF_FS_STATUS_KM_EF   ");
			sb.append("\n GROUP BY COMM_NO   ");
			 
			Log.debug("DB", this, sb.toString());
			stmt = connOracle.prepareStatement(sb.toString());
			rs = stmt.executeQuery();
			 
			while (rs.next()) {
				MaxSeq = "";
				CommNo = rs.getString("COMM_NO");				 
				MaxSeq = rs.getString("LAST_SEQ_NO");				 
				Log.debug("DB", this, "COMM_NO:"+CommNo);
				Log.debug("DB", this, "MaxSeq:"+MaxSeq);				
				
				ResultSet rs = null; 
				sb.setLength(0);			
				sb.append("\n SELECT SEQ       ");
				sb.append("\n       ,LOC       ");
				//sb.append("\n       ,LOC_NAME  ");
				sb.append("\n       ,SALE_DATE ");
				sb.append("\n       ,SALE_TIME ");
				sb.append("\n       ,MENU_CODE ");
				//sb.append("\n       ,MENU_NAME ");
				sb.append("\n       ,PRICE ");
				sb.append("\n       ,QTY   ");
				sb.append("\n       ,AMT   ");
				sb.append("\n FROM  V_IF_DAILY_IPJANG_DATA  ");
				sb.append("\n WHERE LOC = ?     ");
				sb.append("\n AND   SEQ > ?     ");
				sb.append("\n ORDER BY SEQ      ");
	
				Log.debug("DB", this, sb.toString());
				prest = commSqlServer.prepareStatement(sb.toString());
				prest.setString(1, CommNo);	
				prest.setString(2, MaxSeq);	
				rs = prest.executeQuery();
				
				ArrayList <TradeKm> arrTrade = new ArrayList<TradeKm>();
				TradeKm tdItem;
				TradeKm trMax = new TradeKm();	
				count_src = 0;
				
				while (rs.next()) {
					 count_src ++;
					 tdItem = new TradeKm(
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
								 
					 if (count_src % 5000 == 0) {	// 5,000�� ������ DB������ ����
			             batchInsertTrade(connOracle, arrTrade);
			             trMax = arrTrade.get(arrTrade.size() -1);
			             arrTrade.clear();
					 }
				 }
	
				 if (arrTrade.size() > 0) {	
					 batchInsertTrade(connOracle, arrTrade);
					 trMax = arrTrade.get(arrTrade.size() -1);
				 }
				
				 
				 // 4. ����DB������ �ڷ� ����
	             // 4-1. Ʈ����� ����
	             // 5. ���� ���� �Ϸù�ȣ ����
	             if (count_src > 0) {	// ������ �ڷᰡ �ִ� ���
					 UpdateMaxSeq(connOracle, trMax, count_src);
	             }
	             count_src_tot += count_src;
	 			Log.debug("DB", this, "(��������)���:"+CommNo+", ó���Ǽ�:"+count_src+"��");
			}
            connOracle.commit();
             
			long endTime = System.currentTimeMillis();    		 
			Log.debug("DB", this, "(��������)�� ó���Ǽ�:"+count_src_tot+"��, �ҿ�ð�:"+(endTime - startTime)/1000.000f+"��");
            Log.debug("DB", this, "(��������)�ڷ� ���� ����------------------------------------>");			 
					
		} catch (ClassNotFoundException e) {
			Log.error("ERROR", this, "(��������)JDBC ����̹� �ε尡 �����Ͽ����ϴ�.");
		} catch (Exception e) {
			Log.error("ERROR", this, "(��������)execute : " + "Exception  [" + e.toString()+ "] ");
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

	 
	private int batchInsertTrade(Connection conn, ArrayList <TradeKm> arrTrade) {
		try {
			 // 4-2. Insert ����  ����
    		sb.setLength(0);
			sb.append("\n  ");
			sb.append("\n INSERT INTO tbif_fs_issue_km_ef (              ");
			sb.append("\n  SEQ, LOC, SALE_DATE,   ");
			sb.append("\n  SALE_TIME, MENU_CODE, PRICE,   QTY, AMT  ) ");
			sb.append("\n VALUES(?,?,?,?,?,?,?,?)                  ");
			
			Log.debug("DB", this, sb.toString());
			stmt = conn.prepareStatement(sb.toString());
			
			// 4-3. 1,000�Ǿ� DB�� ����
			int ins_cnt = 0;
			int i = 0;
			for( TradeKm tr : arrTrade) {
				i = 1;
				ins_cnt ++;
				stmt.clearParameters();
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
				if (ins_cnt % 1000 == 0) {	// 1,000�� ������ DB Insert
					stmt.executeBatch();
			    }
			}
	        stmt.executeBatch();			
			stmt.close();
			
		} catch (SQLException e) {
			Log.error("ERROR", this, "(��������)batchInsertTrade : " + "SQLException  [" + e.toString()+ "] ");
			Log.error("ERROR", this, "(��������)SQL String = " + sb.toString());
		}
		return 0;
	}
	
	private int UpdateMaxSeq(Connection conn, TradeKm trade, Long nTransCnt)
	{	
		int i = 0;
		try {
			sb.setLength(0);
			sb.append("\n  ");
			sb.append("\n INSERT INTO tbif_fs_status_km_ef ( ");
			sb.append("\n        SEQ_NO, COMM_NO, ISSUE_DT, TRANS_CNT, UPDT_DTM) ");
			sb.append("\n  VALUES(?,?,?,?,SYSDATE)");
		
			//Log.debug("DB", this, sb.toString());
			i = 1;
			stmt = conn.prepareStatement(sb.toString());
			stmt.setString(i++,   trade.getSeq());
			stmt.setString(i++,   trade.getLoc());
			stmt.setString(i++,   trade.getSaleDate());
			stmt.setLong(i++,     nTransCnt);
			
			stmt.executeUpdate();
     
			stmt.close();
			
		} catch (SQLException e) {
			Log.error("ERROR", this, "(��������)UpdateMaxSeq : " + "SQLException  [" + e.toString()+ "] ");
			Log.error("ERROR", this, "(��������)SQL String = " + sb.toString());
			return -1;
		}
		return 0;
	}	
}
