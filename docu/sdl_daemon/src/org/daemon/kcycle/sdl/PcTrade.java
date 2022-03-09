package org.daemon.kcycle.sdl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;
import org.sosfo.framework.connection.PoolManager;

public class PcTrade {    

	private static Logger logger2 = Logger.getLogger("A1");
	
	public void execute() {
		
		logger2.info("SdlTrade:execute >>> Start PC data sending!");
		Connection connOracle = null;

		try {		
			PoolManager conusrbmManger = PoolManager.getInstance();
			connOracle = conusrbmManger.getConnection("usrbm");
						
			//1. 경주정보 조회(meetCd / raceDay)
			// [0] = meetCd / [1] = raceDay
			String[] raceInfo = getRaceInfo(connOracle);
			// 경주 없으면 여기서 종료
			if(raceInfo[0]==null || raceInfo[1] == null) return;
			
			SavePCall.callUrl(raceInfo[0], raceInfo[1]);

		} catch (ClassNotFoundException e) {
			logger2.error("PcTrade:execute >>> JDBC Driver Load Fail");
		} catch (Exception e) {
			logger2.error("PcTrade:execute >>> execute : ", e);
		} finally {		
			if (connOracle != null) {
				try { connOracle.close(); } catch (SQLException e) { }
				connOracle = null;
			}
		} 
	}
	
	public String[] getRaceInfo(Connection connOracle) throws Exception{
		StringBuffer sb = new StringBuffer();
		
		sb.append("\n  SELECT MEET_CD, RACE_DAY ");
		sb.append("\n    FROM VW_SDL_INFO ");
		sb.append("\n   WHERE RACE_DAY = TO_CHAR(SYSDATE,'YYYYMMDD') ");
		sb.append("\n  AND ROWNUM = 1 ");
		
		String sStndyear = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		String[] result = new String[2];
		try{
			stmt = connOracle.createStatement();
			rs = stmt.executeQuery(sb.toString());
			if(rs.next()) {
				result[0] = rs.getString(1);
				result[1] = rs.getString(2);
			}
		}catch(Exception e){
			logger2.error("SdlTrade:sDateChk >>> : ", e);
		}finally {
			try{ if(rs!=null) rs.close(); }catch(Exception ee){}
			try{ if(stmt!=null) stmt.close(); }catch(Exception ee){}
		}
		
		return result;
	}
	
}
