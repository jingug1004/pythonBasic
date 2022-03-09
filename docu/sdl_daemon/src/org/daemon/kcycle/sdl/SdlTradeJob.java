package org.daemon.kcycle.sdl;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class SdlTradeJob implements Job {
	
	public void execute(JobExecutionContext context) throws JobExecutionException {

		SdlTrade st = new SdlTrade();
		st.execute();
	}
	
}
