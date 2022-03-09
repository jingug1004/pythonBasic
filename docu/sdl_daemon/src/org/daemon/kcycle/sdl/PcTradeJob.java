package org.daemon.kcycle.sdl;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class PcTradeJob implements Job {
	
	public void execute(JobExecutionContext context) throws JobExecutionException {
		
		 PcTrade st = new PcTrade();
		 st.execute();
	}
	
}
