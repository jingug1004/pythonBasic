package org.daemon;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.daemon.kcycle.cash.SendTradeJob;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.sosfo.framework.config.ConfigFactory;
import org.sosfo.framework.connection.PoolManager;
import org.sosfo.framework.log.Log;

public class CashDaemon extends HttpServlet {

    private static ConfigFactory cf = ConfigFactory.getInstance();

    @Override
    public void init() throws ServletException {
        System.out.println(this.getServletName()+" is init start");
        CashDaemon daemon = new CashDaemon();
        
        Log.debug("DB", daemon, "daemon start >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        
		try {
			Log.debug("DB", daemon, "daemon execute TEST >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
			daemon.startSendTradeDaily();	// 일단위로 실행용
		} catch (Exception e) {
		    e.printStackTrace();
		    Log.error("ERROR", "org.daemon.Daemon", "ERROR: " + e);
	        Log.debug("DB", daemon, "daemon end >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		}
    }
    
    // 일단위로 실행용
    public boolean startSendTradeDaily() {
		try {
		    SchedulerFactory schedFact = new org.quartz.impl.StdSchedulerFactory();
		    Scheduler sched = schedFact.getScheduler();
	
		    sched.start();
		    JobDetail jobDetail = new JobDetail("sendTradeJob", "sendTradeGroup;", SendTradeJob.class);
		    jobDetail.getJobDataMap().put("type", "FULL");
		    CronTrigger trigger = new CronTrigger("sendTradeJob", "sendTradeGroup");
	
		    trigger.setCronExpression(cf.getConfiguration("period.properties").getString("scheduler.sendTradeDaily.cron"));
		    sched.scheduleJob(jobDetail, trigger);
		} catch (Exception e) {
		    e.printStackTrace();
		    Log.fatal("ERROR", "org.daemon.kcycle.tmoney.SendTrade", "startSendTradeDaily ERROR : " + e);
		}
		
		return false;
    }
}
