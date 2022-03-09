package org.daemon.kcycle.fixedseatKM;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class SendTradeJob implements Job {    

	public void execute(JobExecutionContext context) throws JobExecutionException {

		 SendTradeKM stKM = new SendTradeKM();
		 stKM.execute();

		 SendTradeKMef stKMef = new SendTradeKMef();
		 stKMef.execute();
	}
}
