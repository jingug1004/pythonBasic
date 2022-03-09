package org.daemon.kcycle.fixedseat;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.sosfo.framework.config.ConfigFactory;
import org.sosfo.framework.connection.PoolManager;
import org.sosfo.framework.log.Log;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.HashMap;

public class CheckSchedule implements Job {    

	StringBuffer sb = new StringBuffer();
	ResultSet rs = null;
	PreparedStatement stmt = null;
	Connection connOracle = null;
	 
	Map PerioInfo = null;
	
	private static ConfigFactory cf = ConfigFactory.getInstance();
	
	public void execute(JobExecutionContext context) throws JobExecutionException {
			Log.debug("DB", this, "CheckSchedule executed !!!!");
		try {
			
			PoolManager conOraManger = PoolManager.getInstance();
			connOracle = conOraManger.getConnection("zenius");

			// 2. ����ֱ⸦ ��ȸ�Ѵ�.
			PerioInfo = GetTransPeriod(connOracle);
						
			long nPeriod    = Long.parseLong(PerioInfo.get("PERIOD").toString());
			String sStartTm = PerioInfo.get("START_TM").toString();
			String sEndTm   = PerioInfo.get("END_TM").toString();
			String curTime = "";
			Log.debug("DB", this, "nPeriod:"+nPeriod);
			Log.debug("DB", this, "sStartTm:"+sStartTm);
			Log.debug("DB", this, "sEndTm:"+sEndTm);
            Log.debug("DB", this, "������ �� ����:::::::::::::::::::::::::::::::::::::");
            while (nPeriod > 0) {
            	Thread.sleep(nPeriod);
           	 
            	if (Integer.parseInt(curTime) >= Integer.parseInt(sStartTm)) {
            		if (Integer.parseInt(curTime) >= Integer.parseInt(sEndTm)) {
            			break;
            		}
                	SimpleDateFormat sd = new SimpleDateFormat("HHmm");
                	curTime = sd.format(new Date());                	
            		Log.debug("DB", this, "curTime:"+curTime);
            		
                	(new SendTradeJob()).execute(context);		// �ڷ� ���
                	
                	Log.debug("DB", this, "�����쿡 ���� ��� ó��");
            	}            		
			}
            Log.debug("DB", this, "������ �� ����:::::::::::::::::::::::::::::::::::::");
			 
					
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
		} 
	}


	 private Map GetTransPeriod(Connection conn)
	 {
		 Map periodMap = new HashMap();
		 
		 try {
			// 2-2. �ڷ���� �ֱ� ��ȸ			 
			sb.setLength(0);
			sb.append("\n SELECT TERM AS PERIOD, START_TM, END_TM ");
			sb.append("\n FROM TBIF_FS_PERIOD     ");
			sb.append("\n WHERE TRAN_DT = TO_CHAR(SYSDATE, 'YYYYMMDD') ");
			 
			Log.debug("DB", this, sb.toString());
			stmt = conn.prepareStatement(sb.toString());
			
			rs = stmt.executeQuery();
			 
			if (rs.next()) {
				periodMap.put("PERIOD",   rs.getLong("PERIOD"));	
				periodMap.put("START_TM", rs.getLong("START_TM"));	
				periodMap.put("END_TM",   rs.getLong("END_TM"));	
			} else {
				periodMap.put("PERIOD",   0);	
				periodMap.put("START_TM", "1000");	
				periodMap.put("END_TM",   "1000");	
			}
			System.out.println("�����ֱ�:"+periodMap.get("PERIOD").toString());
			rs.close();
			rs = null;
			
		} catch (SQLException e) {
			Log.error("ERROR", this, "GetTransPeriod : " + "SQLException  [" + e.toString()+ "] ");
			Log.error("ERROR", this, "SQL String = " + sb.toString());
		}

        return periodMap;
	 }
	 
}
