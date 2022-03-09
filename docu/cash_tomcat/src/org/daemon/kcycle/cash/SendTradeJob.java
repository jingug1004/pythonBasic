package org.daemon.kcycle.cash;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class SendTradeJob implements Job {
	
	public void execute(JobExecutionContext context) throws JobExecutionException {
		 SendTrade st = new SendTrade();
		 st.execute();
	}
	
}
