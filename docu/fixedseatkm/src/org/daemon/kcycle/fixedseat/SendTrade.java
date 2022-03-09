package org.daemon.kcycle.fixedseat;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.sosfo.framework.config.ConfigFactory;
import org.sosfo.framework.connection.PoolManager;
import org.sosfo.framework.log.Log;

public class SendTrade {    
 
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
        Log.debug("DB", this, "�ڷ� ���� ����------------------------------------>");
		try {
			 long startTime = System.currentTimeMillis();
			
			// 1. �ش� ������ �ڵ带 ȯ�溯������ �����´�.
			PoolManager conOraManger = PoolManager.getInstance();
			connOracle = conOraManger.getConnection("zenius");

			// 2. �ش� �������� ������ ������ ������ ��ȸ�Ѵ�.
			long MaxSeq = getLastTradeSeq(connOracle);
			Log.debug("DB", this, "MaxSeq:"+MaxSeq);
						
			PoolManager conMySqlManger = PoolManager.getInstance();
			commSqlServer = conMySqlManger.getConnection("fixedseat");

			connOracle.setAutoCommit(false);
			
			ResultSet rs = null;
			
			sb.setLength(0);			
			sb.append("\n SELECT id        ");
			sb.append("\n       ,date      ");
			sb.append("\n       ,time      ");
			sb.append("\n       ,seat      ");
			sb.append("\n       ,amount    ");
			sb.append("\n       ,uni_code  ");
			sb.append("\n       ,uni_sub   ");
			sb.append("\n       ,pk        ");
			sb.append("\n FROM  UNICOM99.TKT_ISSUE  ");
			sb.append("\n WHERE pk > ?     ");
			sb.append("\n ORDER BY pk      ");

			Log.debug("DB", this, sb.toString());
			prest = commSqlServer.prepareStatement(sb.toString());
			prest.setLong(1,  MaxSeq);	
			rs = prest.executeQuery();
			
			ArrayList <Trade> arrTrade = new ArrayList<Trade>();
			Trade tdItem;
			Trade trMax = new Trade();	
			long count_src = 0;
			
			while (rs.next()) {
				 count_src ++;
				 tdItem = new Trade(
							 rs.getString("ID"),
							 rs.getString("DATE"),
							 rs.getString("TIME"),
							 rs.getString("SEAT"),
							 rs.getLong("AMOUNT"),
							 rs.getString("UNI_CODE"),
							 rs.getString("UNI_SUB"),
							 rs.getDouble("PK"));
				 arrTrade.add(tdItem);
							 
				 if (count_src % 10000 == 0) {	// 10,000�� ������ DB������ ����
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
             connOracle.commit();
             
			 long endTime = System.currentTimeMillis();    		 
			 Log.debug("DB", this, "ó���Ǽ�:"+count_src+"��, �ҿ�ð�:"+(endTime - startTime)/1000.000f+"��");
             Log.debug("DB", this, "�ڷ� ���� ����------------------------------------>");
			 
					
		} catch (ClassNotFoundException e) {
			Log.error("ERROR", this, "JDBC ����̹� �ε尡 �����Ͽ����ϴ�.");
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


	 private long getLastTradeSeq(Connection conn)
	 {
		long LastSeq = 0;
		 try {
			// 2-2. ���� �ڷ� ��ȸ			 
			sb.setLength(0);
			sb.append("\n SELECT NVL(MAX(SEQ_NO),0) AS LAST_SEQ_NO ");
			sb.append("\n FROM   TBIF_FS_STATUS    ");
			 
			Log.debug("DB", this, sb.toString());
			stmt = conn.prepareStatement(sb.toString());
			rs = stmt.executeQuery();
			 
			if (rs.next()) {
				 LastSeq = rs.getLong("LAST_SEQ_NO");				 
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
	 
	private int batchInsertTrade(Connection conn, ArrayList <Trade> arrTrade) {
		try {
			 // 4-2. Insert ����  ����
    		sb.setLength(0);
			sb.append("\n  ");
			sb.append("\n INSERT INTO tbif_fs_issue (              ");
			sb.append("\n  COMM_NO, ISSUE_DT, ISSUE_TM, SEAT_NO,   ");
			sb.append("\n  AMOUNT,  SEQ_NO,   UNI_CODE, UNI_SUB  ) ");
			sb.append("\n VALUES(?,?,?,?,?,?,?,?)                  ");
			
			Log.debug("DB", this, sb.toString());
			stmt = conn.prepareStatement(sb.toString());
			
			// 4-3. 1,000�Ǿ� DB�� ����
			int ins_cnt = 0;
			int i = 0;
			for( Trade tr : arrTrade) {
				i = 1;
				ins_cnt ++;
				stmt.clearParameters();
				stmt.setString(i++, tr.getId());
				stmt.setString(i++, tr.getDate());
				stmt.setString(i++, tr.getTime().toString().replaceAll(":", ""));
				stmt.setString(i++, tr.getSeat());
				stmt.setLong(i++,   tr.getAmount());				
				stmt.setDouble(i++, tr.getPk());
				stmt.setString(i++, tr.getUniCode());
				stmt.setString(i++, tr.getUniSub());
			     
				stmt.addBatch();
				if (ins_cnt % 1000 == 0) {	// 1,000�� ������ DB Insert
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
	
	private int UpdateMaxSeq(Connection conn, Trade trade, Long nTransCnt)
	{	
		int i = 0;
		try {
			sb.setLength(0);
			sb.append("\n  ");
			sb.append("\n INSERT INTO tbif_fs_status ( ");
			sb.append("\n        SEQ_NO, COMM_NO, ISSUE_DT, TRANS_CNT, UPDT_DTM) ");
			sb.append("\n  VALUES(?,?,?,?,SYSDATE)");
		
			//Log.debug("DB", this, sb.toString());
			i = 1;
			stmt = conn.prepareStatement(sb.toString());
			stmt.setDouble(i++,   trade.getPk());
			stmt.setString(i++, trade.getId());
			stmt.setString(i++, trade.getDate());
			stmt.setLong(i++,   nTransCnt);
			
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
