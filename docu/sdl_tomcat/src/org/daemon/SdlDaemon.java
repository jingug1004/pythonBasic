package org.daemon;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.daemon.kcycle.sdl.SdlTradeJob;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.sosfo.framework.config.ConfigFactory;
import org.sosfo.framework.connection.PoolManager;
import org.sosfo.framework.log.Log;

public class SdlDaemon extends HttpServlet {

    private static ConfigFactory cf = ConfigFactory.getInstance();

    @Override
    public void init() throws ServletException {
        System.out.println(this.getServletName()+" is init start");
        SdlDaemon daemon = new SdlDaemon();
        
        Log.debug("DB", daemon, "daemon start >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        
		try {
			Log.debug("DB", daemon, "daemon execute TEST >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
			daemon.startSdlTradeDaily();	// 일단위로 실행용
		} catch (Exception e) {
		    e.printStackTrace();
		    Log.error("ERROR", "org.daemon.Daemon", "ERROR: " + e);
	        Log.debug("DB", daemon, "daemon end >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		}
    }
    
    // 일단위로 실행용
    public boolean startSdlTradeDaily() {
		try {
		    SchedulerFactory schedFact = new org.quartz.impl.StdSchedulerFactory();
		    Scheduler sched = schedFact.getScheduler();
	
		    sched.start();
		    JobDetail jobDetail = new JobDetail("SdlTradeJob", "SdlTradeGroup;", SdlTradeJob.class);
		    jobDetail.getJobDataMap().put("type", "FULL");
		    CronTrigger trigger = new CronTrigger("SdlTradeJob", "SdlTradeGroup");
	
		    trigger.setCronExpression(cf.getConfiguration("period.properties").getString("scheduler.SdlTradeDaily.cron"));
		    sched.scheduleJob(jobDetail, trigger);
		} catch (Exception e) {
		    e.printStackTrace();
		    Log.fatal("ERROR", "org.daemon.kcycle.sdl.SdlTrade", "startSdlTradeDaily ERROR : " + e);
		}
		
		return false;
    }
    /*
    public static void main(String[] args) {

    	SdlDaemon daemon = new SdlDaemon();
        Log.debug("DB", daemon, "데몬 실행 >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        
		try { 
	
		    daemon.startSdlTradeDaily();	// 일단위로 실행용
		    
		} catch (Exception e) {
		    e.printStackTrace();
		    Log.error("ERROR", "org.daemon.Daemon", "ERROR: " + e);
	        Log.debug("DB", daemon, "데몬 종료 >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		}

    }
    */
}
