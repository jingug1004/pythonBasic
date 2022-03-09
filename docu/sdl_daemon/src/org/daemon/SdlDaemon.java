package org.daemon;
import org.apache.log4j.Logger;
import org.daemon.kcycle.sdl.SdlTradeJob;
import org.daemon.kcycle.sdl.PcTradeJob;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.sosfo.framework.config.ConfigFactory;
import org.sosfo.framework.log.Log;

public class SdlDaemon {

    private static ConfigFactory cf = ConfigFactory.getInstance();
    private static Logger logger2 = Logger.getLogger("A1");

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
		    logger2.fatal("org.daemon.kcycle.sdl.SdlTrade.startSdlTradeDaily error : ", e);
		}
		
		return false;
    }
    
    public boolean startPcTradeDaily() {
		try {
		    SchedulerFactory schedFact = new org.quartz.impl.StdSchedulerFactory();
		    Scheduler sched = schedFact.getScheduler();
	
		    sched.start();
		    JobDetail pcjobDetail = new JobDetail("PcTradeJob", "PcTradeGroup;", PcTradeJob.class);
		    pcjobDetail.getJobDataMap().put("type", "FULL");
		    CronTrigger trigger = new CronTrigger("PcTradeJob", "PcTradeGroup");
	
		    trigger.setCronExpression(cf.getConfiguration("pcPeriod.properties").getString("scheduler.PcTradeDaily.cron"));
		    sched.scheduleJob(pcjobDetail, trigger);
		} catch (Exception e) {
		    e.printStackTrace();
		    logger2.fatal("org.daemon.kcycle.sdl.SdlTrade.startPcTradeDaily error : ", e);
		}
		
		return false;
    }
     
    public static void main(String[] args){
    	SdlDaemon daemon = new SdlDaemon();
        
    	logger2.debug("daemon start >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        
		try {
			logger2.debug("daemon execute TEST >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
			daemon.startSdlTradeDaily();	// 일단위로 실행용
			daemon.startPcTradeDaily();
		} catch (Exception e) {
		    e.printStackTrace();
		    logger2.error("org.daemon.Daemon error : ", e);
		    logger2.debug("daemon end >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		}
    }
}
