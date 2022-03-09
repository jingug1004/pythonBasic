package org.daemon.kcycle.sdl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;
import org.sosfo.framework.connection.PoolManager;

public class SdlTrade {    

	private static Logger logger2 = Logger.getLogger("A1");
	
	public void execute() {
		
		logger2.info("SdlTrade:execute >>> Start SDL data sending!");
		Connection connOracle = null;

		try {
			
			PoolManager conusrbmManger = PoolManager.getInstance();
			connOracle = conusrbmManger.getConnection("usrbm");
						
			//1. 경주 실행 여부 조회
			String sRaceDayChk = raceDayChk(connOracle);

			//2. 경주 실행 시 프로시저 실행
			if(sRaceDayChk != null) sdlDaemon(connOracle);
			else return;

		} catch (ClassNotFoundException e) {
			logger2.error("SdlTrade:execute >>> JDBC Driver Load Fail");
		} catch (Exception e) {
			logger2.error("SdlTrade:execute >>> execute : ", e);
		} finally {		
			if (connOracle != null) {
				try { connOracle.close(); } catch (SQLException e) { }
				connOracle = null;
			}
		} 
	}
	
	//1. 경주 실행 여부 조회
	public String raceDayChk(Connection oraCon){
		StringBuffer sb = new StringBuffer();
		
		//경주일자 조회 후 경주가 없는 경우 실행하지 않음
		sb.append("\n SELECT MAX(STND_YEAR)	STND_YEAR ");
		sb.append("\n   FROM VW_SDL_INFO ");
		sb.append("\n  WHERE RACE_DAY = TO_CHAR(SYSDATE,'YYYYMMDD') ");
		
		
		String sStndyear = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		try{
			stmt = oraCon.createStatement();
			rs = stmt.executeQuery(sb.toString());
			if(rs.next()) sStndyear = rs.getString(1);
		}catch(Exception e){
			
			logger2.error("SdlTrade:sDateChk >>> : ", e);
		}finally {
			try{ if(rs!=null) rs.close(); }catch(Exception ee){}
			try{ if(stmt!=null) stmt.close(); }catch(Exception ee){}
		}
		
		return sStndyear;
	}
	
	//2. sdl 프로시저 실행
	protected void sdlDaemon(Connection oraCon)
    {
    	CallableStatement proc =  null;
    	
        try {
        	long startTime = System.currentTimeMillis();
       	
        	System.out.println("====startTime:" + startTime);
        			
    		System.out.println("====sRaceEndYn N : SP_IMPORT_SDL_PROCEDURE(TM) START====");
    		proc = oraCon.prepareCall("{call SP_IMPORT_SDL_TM('','','')}");
    		proc.setQueryTimeout(120);
        	proc.execute();
        	System.out.println("====SP_IMPORT_SDL_PROCEDURE(TM) END====");
        	
        	System.out.println("====sRaceEndYn N : SP_IMPORT_SDL_PROCEDURE(PB) START====");
        	proc = oraCon.prepareCall("{call SP_IMPORT_SDL_PB('','','')}");
        	proc.setQueryTimeout(120);
        	proc.execute();
        	System.out.println("====SP_IMPORT_SDL_PROCEDURE(PB) END====");
        	
        	System.out.println("====sRaceEndYn N : SP_IMPORT_SDL_PROCEDURE(PBS) START====");
        	proc = oraCon.prepareCall("{call SP_IMPORT_SDL_PBS('','','')}");
        	proc.setQueryTimeout(120);
        	proc.execute();
        	System.out.println("====SP_IMPORT_SDL_PROCEDURE(PBS) END====");
        	
        	System.out.println("====sRaceEndYn N : SP_IMPORT_SDL_PROCEDURE(PTS) START====");
        	proc = oraCon.prepareCall("{call SP_IMPORT_SDL_PTS('','','')}");
        	proc.setQueryTimeout(120);
        	proc.execute();
        	System.out.println("====SP_IMPORT_SDL_PROCEDURE(PTS) END====");
    		
        	System.out.println("====sRaceEndYn Y : SP_IMPORT_SDL_PROCEDURE(PT) START====");
        	proc = oraCon.prepareCall("{call SP_IMPORT_SDL_PT('','','')}");
        	proc.setQueryTimeout(120);
        	proc.execute();
        	System.out.println("====SP_IMPORT_SDL_PROCEDURE(PT) END====");
        	
        	
        	System.out.println("====sRaceEndYn Y : SP_IMPORT_SDL_PROCEDURE(DT) START====");
        	proc = oraCon.prepareCall("{call SP_IMPORT_SDL_DT('','','')}");
        	proc.setQueryTimeout(120);
        	proc.execute();
        	System.out.println("====SP_IMPORT_SDL_PROCEDURE(DT) END====");
        	
        	
        	System.out.println("====sRaceEndYn Y : SP_IMPORT_SDL_PROCEDURE(FN) START====");
        	proc = oraCon.prepareCall("{call SP_IMPORT_SDL_FN('','','')}");
        	proc.setQueryTimeout(120);
        	proc.execute();
        	System.out.println("====SP_IMPORT_SDL_PROCEDURE(FN) END====");
        	
        	
        	System.out.println("====sRaceEndYn Y : SP_IMPORT_SDL_PROCEDURE(RS) START====");
        	proc = oraCon.prepareCall("{call SP_IMPORT_SDL_RS('','','')}");
        	proc.setQueryTimeout(120);
        	proc.execute();
        	System.out.println("====SP_IMPORT_SDL_PROCEDURE(RS) END====");
        	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger2.error("SdlTrade:sdlDaemon >>> : ", e);
		} finally {
			try{ if(proc!=null) proc.close(); }catch(Exception ee){}
		} 
    }
	
}
