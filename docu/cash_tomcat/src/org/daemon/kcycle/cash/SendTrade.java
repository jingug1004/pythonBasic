package org.daemon.kcycle.cash;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.sosfo.framework.config.ConfigFactory;
import org.sosfo.framework.connection.PoolManager;
import org.sosfo.framework.log.Log;

public class SendTrade {    

	StringBuffer sb = new StringBuffer();
	ResultSet rsAcc241 = null;
	ResultSet rsAcc242 = null;
	ResultSet rs241 = null;
	ResultSet rs242 = null;
	ResultSet rsAccToday241 = null;
	ResultSet rsAccToday242 = null;
	
	PreparedStatement stmt = null;
	PreparedStatement prest = null;
	Connection connOracle = null;
	Connection connAccess241 = null;
	Connection connAccess242 = null;
	Connection connAccessToday241 = null;
	Connection connAccessToday242 = null;
	
	String MAX_TRANSITION_SEQ = "";
	String MAX_TRADE_DATE = "";
	String connName= "";
	
	private static ConfigFactory cf = ConfigFactory.getInstance();

	// 오라클에 해당 데이터가 있는지 조회 
	public boolean isExist(Connection con, Trade trade) {
		StringBuffer sb = new StringBuffer();
		sb.append("\n SELECT count(*) ");
		sb.append("\n   FROM TBRC_T_TRADE_MSR	");
		sb.append("\n  WHERE SDATE = '"+trade.getSDATE()+"'");
		sb.append("\n    AND M_NO = '"+trade.getM_NO()+"'");
		sb.append("\n    AND AMNT = "+trade.getAMNT());
		sb.append("\n    AND FTIME = '"+trade.getFTIME()+"'");
		sb.append("\n    AND ILNO = "+trade.getILNO());
		sb.append("\n    AND PAY_GBN = '"+trade.getPAY_GBN()+"'");
		sb.append("\n    AND QNTY = "+trade.getQNTY());
		sb.append("\n    AND RTN = '"+trade.getRTN()+"'");
		sb.append("\n    AND USE_CD = '"+trade.getUSE_CD()+"'");
		
		//System.out.println(sb.toString());
		
		boolean isExist = false;
		Statement stmt = null;
		ResultSet rs = null;
		try{
			
			stmt = con.createStatement();
			rs = stmt.executeQuery(sb.toString());
			if(rs.next()){
				int count = rs.getInt(1);
				if(count==1) isExist = true;
			}
			
			rs.close();
			stmt.close();
			
		}catch(Exception e){
			try{ if(rs!=null) rs.close(); }catch(Exception ee){}
			try{ if(stmt!=null) stmt.close(); }catch(Exception ee){}
			
			isExist = false;
			throw new RuntimeException("SendTrade class 의 getCountByDataInOracle()에서 DB 오류 발생");
		}
		
		return isExist;
	}
	
	public List<Trade> getTradeData(Connection oraCon, Connection mdbCon, String m_no){
		StringBuffer sb = new StringBuffer();
		sb.append("\n SELECT SDATE, LOC AS M_NO, ILNO, RTN, USE_CD, PAY_GBN, QNTY, AMNT, FTIME ");
		sb.append("\n   FROM MKT_DESC_TB	");
		sb.append("\n  WHERE SDATE > '20150101'	");
		sb.append("\n    AND SDATE > NOW() -31	"); 
		sb.append("\n    AND LOC = '"+m_no+"'	");
		
		System.out.println(sb.toString());
		
		ArrayList<Trade> list = new ArrayList<Trade>();
		Statement stmt = null;
		ResultSet rs = null;
		try{
			
			stmt = mdbCon.createStatement();
			rs = stmt.executeQuery(sb.toString());
			while(rs.next()){
				Trade tr = new Trade(
						rs.getString(1),
						rs.getString(2),
						rs.getString(3),
						rs.getString(4),
						rs.getString(5),
						rs.getString(6),
						/*new String(rs.getString(6).getBytes("8859_1"), "utf-8"),*/
						rs.getString(7),
						rs.getString(8),
						rs.getString(9)
				);
				list.add(tr);	
			}
			
			rs.close();
			stmt.close();
		}catch(Exception e){
			try{ if(rs!=null) rs.close(); }catch(Exception ee){}
			try{ if(stmt!=null) stmt.close(); }catch(Exception ee){}
			
			throw new RuntimeException("SendTrade class 의 isEqualData()에서 DB 오류 발생");
		}
		
		return list;
		
	}
	
	
	public void execute() {
		Log.debug("DB", this, "자료 전송 시작------------------------------------>");
		try {
			long startTime = System.currentTimeMillis();
			/*
			PoolManager conAcc241Manger = PoolManager.getInstance();
			connAccess241 = conAcc241Manger.getConnection("access241");
			connAccess242 = conAcc241Manger.getConnection("access242");
						
			connAccess241.setAutoCommit(false);
			connAccess242.setAutoCommit(false);
			
			PoolManager conOra241Manger = PoolManager.getInstance();
			connOracle = conOra241Manger.getConnection("usrbm");
						
			List<Trade> data241 = getTradeData(connOracle, connAccess241, "01");
			List<Trade> data242 = getTradeData(connOracle, connAccess242, "02");
			
			System.out.println(data241.size());
			System.out.println(data242.size());
			
			delete241mdb(connOracle, (ArrayList<Trade>)data241);
			delete242mdb(connOracle, (ArrayList<Trade>)data242);
		
			batchInsert241mdb(connOracle, (ArrayList<Trade>)data241);
			batchInsert242mdb(connOracle, (ArrayList<Trade>)data242);
			*/
			
			
			//2. 241mdb 파일을 조회한다.
			PoolManager conAcc241Manger = PoolManager.getInstance();
			connAccess241 = conAcc241Manger.getConnection("access241");
						
			connAccess241.setAutoCommit(false);
			
			PoolManager conOra241Manger = PoolManager.getInstance();
			connOracle = conOra241Manger.getConnection("usrbm");
						
			List<Trade> data241 = getTradeData(connOracle, connAccess241, "02");
			
			System.out.println(data241.size());
			
			delete241mdb(connOracle, (ArrayList<Trade>)data241);
			
			batchInsert241mdb(connOracle, (ArrayList<Trade>)data241);
			
			/*
			//2. 242mdb 파일을 조회한다.
			PoolManager conAcc242Manger = PoolManager.getInstance();
			connAccess242 = conAcc242Manger.getConnection("access242");
						
			connAccess242.setAutoCommit(false);
			
			PoolManager conOra242Manger = PoolManager.getInstance();
			connOracle = conOra242Manger.getConnection("usrbm");
						
			List<Trade> data242 = getTradeData(connOracle, connAccess242, "02");
			
			System.out.println(data242.size());
			
			delete242mdb(connOracle, (ArrayList<Trade>)data242);
		
			batchInsert242mdb(connOracle, (ArrayList<Trade>)data242);
			*/
			
			 
		} catch (ClassNotFoundException e) {
			Log.error("ERROR", this, "JDBC 드라이버 로드가 실패하였습니다.");
		} catch (Exception e) {
			Log.error("ERROR", this, "execute : " + "Exception  [" + e.toString()+ "] ");
		} finally {
			if (rsAcc241 != null) {
				try { rsAcc241.close(); } catch (SQLException e) { }
				rsAcc241 = null;
			}
			if (rsAcc242 != null) {
				try { rsAcc242.close(); } catch (SQLException e) { }
				rsAcc242 = null;
			}
			if (rs241 != null) {
				try { rs241.close(); } catch (SQLException e) { }
				rs241 = null;
			}
			if (rs242 != null) {
				try { rs242.close(); } catch (SQLException e) { }
				rs242 = null;
			}
			if (rsAccToday241 != null) {
				try { rsAccToday241.close(); } catch (SQLException e) { }
				rsAccToday241 = null;
			}
			if (rsAccToday242 != null) {
				try { rsAccToday242.close(); } catch (SQLException e) { }
				rsAccToday242 = null;
			}			
			if (stmt != null) {
				try { stmt.close(); } catch (SQLException e) {  }
				stmt = null;
			}
			if (connOracle != null) {
				try { connOracle.close(); } catch (SQLException e) { }
				connOracle = null;
			}
			if (connAccess241 != null) {
				try { connAccess241.close(); } catch (SQLException e) { }
				connAccess241 = null;
			}
			if (connAccess242 != null) {
				try { connAccess242.close(); } catch (SQLException e) { }
				connAccess242 = null;
			}
		} 
	}


	private void delete241mdb(Connection connOracle2, ArrayList<Trade> data241) {
		try {
			 // 4-2. delete 문장  생성
			sb.setLength(0);
			sb.append("\n  ");
			sb.append("\n DELETE FROM TBRC_T_TRADE_MSR ");
			sb.append("\n  WHERE SDATE > TO_CHAR(SYSDATE -31, 'YYYYMMDD') "); //데이터 확인 후 지울 날짜 다시 조정
			sb.append("\n    AND M_NO = '4' "); 
			sb.append("\n    AND PAY_GBN = '현금' ");
			
			//Log.debug("DB", this, sb.toString());
			Statement stmt = connOracle2.createStatement();
			stmt.execute(sb.toString());
			
			stmt.close();
			
			
		} catch (SQLException e) {
			Log.error("ERROR", this, "delete241mdb : " + "SQLException  [" + e.toString()+ "] ");
			Log.error("ERROR", this, "SQL String = " + sb.toString());
		}
	}
	
	private void delete242mdb(Connection connOracle2, ArrayList<Trade> data241) {
		try {
			 // 4-2. delete 문장  생성
			sb.setLength(0);
			sb.append("\n  ");
			sb.append("\n DELETE FROM TBRC_T_TRADE_MSR ");
			sb.append("\n  WHERE SDATE > TO_CHAR(SYSDATE -31, 'YYYYMMDD') "); //데이터 확인 후 지울 날짜 다시 조정
			sb.append("\n    AND M_NO = '6' "); 
			sb.append("\n    AND PAY_GBN = '현금' ");
			
			//Log.debug("DB", this, sb.toString());
			Statement stmt = connOracle2.createStatement();
			stmt.execute(sb.toString());
			
			stmt.close();
			
			
		} catch (SQLException e) {
			Log.error("ERROR", this, "delete242mdb : " + "SQLException  [" + e.toString()+ "] ");
			Log.error("ERROR", this, "SQL String = " + sb.toString());
		}
	}
	
	private int batchInsert241mdb(Connection conn, ArrayList <Trade> arrTrade241) {
		try {
			 // 4-2. Insert 문장  생성
    		sb.setLength(0);
			sb.append("\n  ");
			sb.append("\n INSERT INTO TBRC_T_TRADE_MSR ( ");
			sb.append("\n  SDATE, M_NO, ILNO, RTN, USE_CD, ");
			sb.append("\n  PAY_GBN, QNTY, AMNT, FTIME, UPDT_ID, UPDT_DTM) ");
			sb.append("\n   VALUES(?,?,?,?,?,?,?,?,?,?,SYSDATE) ");
			
			//Log.debug("DB", this, sb.toString());
			stmt = conn.prepareStatement(sb.toString());
			
			// 4-3. 1,000건씩 DB에 저장
			int ins_cnt = 0;
			for( Trade tr : arrTrade241) {
				ins_cnt ++;
				stmt.setString(1,  tr.getSDATE());
				stmt.setString(2,  "4");
				stmt.setString(3,  tr.getILNO());
				stmt.setString(4,  tr.getRTN());
				stmt.setString(5,  tr.getUSE_CD());
				stmt.setString(6,  "현금");
				stmt.setString(7,  tr.getQNTY());
				stmt.setString(8,  tr.getAMNT());
				stmt.setString(9,  tr.getFTIME());
				stmt.setString(10, "BATCH");
			    				
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
	
	private int batchInsert242mdb(Connection conn, ArrayList <Trade> arrTrade242) {
		try {
			 // 4-2. Insert 문장  생성
    		sb.setLength(0);
			sb.append("\n  ");
			sb.append("\n INSERT INTO TBRC_T_TRADE_MSR ( ");
			sb.append("\n  SDATE, M_NO, ILNO, RTN, USE_CD, ");
			sb.append("\n  PAY_GBN, QNTY, AMNT, FTIME, UPDT_ID, UPDT_DTM) ");
			sb.append("\n   VALUES(?,?,?,?,?,?,?,?,?,?,SYSDATE) ");
			
			//Log.debug("DB", this, sb.toString());
			stmt = conn.prepareStatement(sb.toString());
			
			// 4-3. 1,000건씩 DB에 저장
			int ins_cnt = 0;
			for( Trade tr : arrTrade242) {
				ins_cnt ++;
				stmt.setString(1,  tr.getSDATE());
				stmt.setString(2,  "6");
				stmt.setString(3,  tr.getILNO());
				stmt.setString(4,  tr.getRTN());
				stmt.setString(5,  tr.getUSE_CD());
				stmt.setString(6,  "현금");
				stmt.setString(7,  tr.getQNTY());
				stmt.setString(8,  tr.getAMNT());
				stmt.setString(9,  tr.getFTIME());
				stmt.setString(10, "BATCH");
			    				
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
	
}
