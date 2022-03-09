package org.daemon.kcycle.sdl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.daemon.SdlDaemon;
import org.sosfo.framework.config.ConfigFactory;
import org.sosfo.framework.connection.PoolManager;
import org.sosfo.framework.log.Log;

public class SdlTrade {    

	StringBuffer sb = new StringBuffer();
	
	PreparedStatement stmt = null;
	PreparedStatement prest = null;
	Connection connOracle = null;
		
	private static ConfigFactory cf = ConfigFactory.getInstance();
	
	public void execute() {
		
		Log.debug("DB", this, "sdl 자료 전송 시작------------------------------------>");
		try {
			
			PoolManager conusrbmManger = PoolManager.getInstance();
			connOracle = conusrbmManger.getConnection("usrbm");
						
			connOracle.setAutoCommit(false);
			
			//1. 오늘 날짜 조회
			String sDate = sDateChk(connOracle);
			//String sDate = "20171109";
						
			//2. 경주 실행 여부 조회
			String sRaceDayChk = raceDayChk(connOracle, sDate);
			
			//3. 경주 실행 시 조회
			if(sRaceDayChk != null){
				
				//3-1. 경주 실행 시 현재 경주번호 조회
				String sRaceNoChk = raceNoChk(connOracle, sDate);
				
				//4. 경주 실행 시 경기 확정 여부 조회
				String sOfficialYnChk = officialYnChk(connOracle, sDate, sRaceNoChk);
				
				if(sOfficialYnChk != null){
					//5. sdl 프로시저 실행
					sdlDaemon(connOracle, sOfficialYnChk, sDate, sRaceNoChk);					
				}
				
			}else{
				return;
			}
			 
		} catch (ClassNotFoundException e) {
			Log.error("ERROR", this, "JDBC 드라이버 로드가 실패하였습니다.");
		} catch (Exception e) {
			Log.error("ERROR", this, "execute : " + "Exception  [" + e.toString()+ "] ");
		} finally {		
			if (stmt != null) {
				try { stmt.close(); } catch (SQLException e) {  }
				stmt = null;
			}
			if (connOracle != null) {
				try { connOracle.close(); } catch (SQLException e) { }
				connOracle = null;
			}
		} 
	}
	
	//1. 오늘 날짜 조회
	public String sDateChk(Connection oraCon){
		StringBuffer sb = new StringBuffer();
		sb.append("\n SELECT TO_CHAR(SYSDATE, 'YYYYMMDD') SDATE FROM DUAL");
		
		String sDate = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		try{
			stmt = oraCon.createStatement();
			rs = stmt.executeQuery(sb.toString());
			if(rs.next()) sDate = rs.getString(1);
			
			rs.close();
			stmt.close();
		}catch(Exception e){
			try{ if(rs!=null) rs.close(); }catch(Exception ee){}
			try{ if(stmt!=null) stmt.close(); }catch(Exception ee){}
			
			throw new RuntimeException("SdlTrade class 의 sDateChk()에서 DB 오류 발생");
		}
		System.out.println("sDate===="+sDate);
		return sDate;
		
	}
	
	//2. 경주 실행 여부 조회
	public String raceDayChk(Connection oraCon, String sDate){
		StringBuffer sb = new StringBuffer();
		//경주일자 조회 후 경주가 없는 경우 실행하지 않음
		sb.append("\n SELECT MAX(STND_YEAR)	STND_YEAR");
		sb.append("\n   FROM VW_SDL_INFO");
		sb.append("\n  WHERE RACE_DAY = '"+sDate+"'");
		
		String sStndyear = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		try{
			stmt = oraCon.createStatement();
			rs = stmt.executeQuery(sb.toString());
			if(rs.next()) sStndyear = rs.getString(1);
			
			rs.close();
			stmt.close();
		}catch(Exception e){
			try{ if(rs!=null) rs.close(); }catch(Exception ee){}
			try{ if(stmt!=null) stmt.close(); }catch(Exception ee){}
			
			throw new RuntimeException("SdlTrade class 의 raceDayChk()에서 DB 오류 발생");
		}
		System.out.println("sStndyear===="+sStndyear);
		return sStndyear;
	}
	
	//3. 경주 실행 시 현재 경주번호 조회
	public String raceNoChk(Connection oraCon, String sDate){
		StringBuffer sb = new StringBuffer();
		//경주가 있는 경우 해당 경주 확정 여부 조회
		sb.append("\n SELECT MAX(S_RACE_NO) RACE_NO");
		sb.append("\n   FROM T_SDL_RS@ODDSDBLINK");
		sb.append("\n  WHERE C_RACE_DY = '"+sDate+"'");
		
		String sRaceNo = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		try{
			stmt = oraCon.createStatement();
			rs = stmt.executeQuery(sb.toString());
			if(rs.next()) sRaceNo = rs.getString(1);
			
			rs.close();
			stmt.close();
		}catch(Exception e){
			try{ if(rs!=null) rs.close(); }catch(Exception ee){}
			try{ if(stmt!=null) stmt.close(); }catch(Exception ee){}
			
			throw new RuntimeException("SdlTrade class 의 raceNoChk()에서 DB 오류 발생");
		}
		System.out.println("sRaceNo===="+sRaceNo);
		return sRaceNo;
	}
	
	//4. 경주 실행 시 경기 확정 여부 조회
	public String officialYnChk(Connection oraCon, String sDate, String sRaceNoChk){
		StringBuffer sb = new StringBuffer();
		//경주가 있는 경우 해당 경주 확정 여부 조회
		sb.append("\n SELECT C_OFFICIAL_YN");
		sb.append("\n   FROM T_SDL_RS@ODDSDBLINK");
		sb.append("\n  WHERE C_RACE_DY = '"+sDate+"'");
		sb.append("\n    AND S_RACE_NO = '"+sRaceNoChk+"'");
		sb.append("\n    GROUP BY C_OFFICIAL_YN");
		
		String sOfficialYn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		try{
			stmt = oraCon.createStatement();
			rs = stmt.executeQuery(sb.toString());
			if(rs.next()) sOfficialYn = rs.getString(1);
			
			rs.close();
			stmt.close();
		}catch(Exception e){
			try{ if(rs!=null) rs.close(); }catch(Exception ee){}
			try{ if(stmt!=null) stmt.close(); }catch(Exception ee){}
			
			throw new RuntimeException("SdlTrade class 의 officialYnChk()에서 DB 오류 발생");
		}
		System.out.println("sOfficialYn===="+sOfficialYn);
		return sOfficialYn;
	}
	
	//5. sdl 프로시저 실행
	protected void sdlDaemon(Connection oraCon, String sOfficialYnChk, String sDate, String sRaceNoChk)  
    {
    	CallableStatement proc =  null;
    	
    	int iCnt = 0;
    	String sSdate  = null;
    	String sRaceNo = null;
    	String sOfficialYn = null;
    	
		
        try {
        	sSdate = sDate;
        	sRaceNo = sRaceNoChk;
        	sOfficialYn = sOfficialYnChk;
        			
        	int i = 1;
        	
        	//경주 확정이 아닌 경우 실행 
        	if ("N".equals(sOfficialYn)) {
        		
        		System.out.println("====SP_IMPORT_SDL_TM START====");
	        	proc = oraCon.prepareCall("{call SP_IMPORT_SDL_TM('"+sDate+"','"+sRaceNo+"')}");
	        	System.out.println("====SP_IMPORT_SDL_TM END====");
	        	
	        	System.out.println("====SP_IMPORT_SDL_PB START====");
	        	proc = oraCon.prepareCall("{call SP_IMPORT_SDL_PB('"+sDate+"','"+sRaceNo+"')}");
	        	System.out.println("====SP_IMPORT_SDL_PB END====");
	        	
	        	System.out.println("====SP_IMPORT_SDL_PBS START====");
	        	proc = oraCon.prepareCall("{call SP_IMPORT_SDL_PBS('"+sDate+"','"+sRaceNo+"')}");
	        	System.out.println("====SP_IMPORT_SDL_PBS END====");
	        	
	        	System.out.println("====SP_IMPORT_SDL_PT START====");
	        	proc = oraCon.prepareCall("{call SP_IMPORT_SDL_PT('"+sDate+"','"+sRaceNo+"')}");
	        	System.out.println("====SP_IMPORT_SDL_PT END====");
	        	
	        	System.out.println("====SP_IMPORT_SDL_PTS START====");
	        	proc = oraCon.prepareCall("{call SP_IMPORT_SDL_PTS('"+sDate+"','"+sRaceNo+"')}");
	        	System.out.println("====SP_IMPORT_SDL_PTS END====");
        		
        	}
        	
        	//경주 확정인 경우 실행 
        	if ("Y".equals(sOfficialYn)) {	
        		
        		System.out.println("====SP_IMPORT_SDL_DT START====");
	        	proc = oraCon.prepareCall("{call SP_IMPORT_SDL_DT('"+sDate+"','"+sRaceNo+"')}");
	        	System.out.println("====SP_IMPORT_SDL_DT END====");
	        	
	        	System.out.println("====SP_IMPORT_SDL_FN START====");
	        	proc = oraCon.prepareCall("{call SP_IMPORT_SDL_FN('"+sDate+"','"+sRaceNo+"')}");
	        	System.out.println("====SP_IMPORT_SDL_FN END====");
	        	
	        	System.out.println("====SP_IMPORT_SDL_RS START====");
	        	proc = oraCon.prepareCall("{call SP_IMPORT_SDL_RS('"+sDate+"','"+sRaceNo+"')}");
	        	System.out.println("====SP_IMPORT_SDL_RS END====");
		        	
        	}	
        	
        	proc.setQueryTimeout(120);
        	proc.execute();
        	//proc.close();
        	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
    }
	
}
