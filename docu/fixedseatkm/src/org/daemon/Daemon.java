package org.daemon;
import org.daemon.kcycle.fixedseatKM.*;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.sosfo.framework.config.ConfigFactory;
import org.sosfo.framework.log.Log;

public class Daemon {

    private static ConfigFactory cf = ConfigFactory.getInstance();

     
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
		    Log.fatal("ERROR", "org.daemon.kcycle.fixedseatKM.SendTradeKM", "startSendTradeDaily ERROR : " + e);
		}
		return false;

    }    
    

    public static void main(String[] args) {

    	Daemon daemon = new Daemon();
        Log.debug("DB", daemon, "데몬 실행 >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        
		try { 
	
		    daemon.startSendTradeDaily();	// 일단위로 실행용
		    
		} catch (Exception e) {
		    e.printStackTrace();
		    Log.error("ERROR", "org.daemon.Daemon", "ERROR: " + e);
	        Log.debug("DB", daemon, "데몬 종료 >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		}

    }

}
