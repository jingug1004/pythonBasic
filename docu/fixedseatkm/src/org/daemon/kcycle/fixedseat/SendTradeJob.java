package org.daemon.kcycle.fixedseat;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class SendTradeJob implements Job {    

	public void execute(JobExecutionContext context) throws JobExecutionException {
		 SendTrade st = new SendTrade();
		 st.execute();
		 
		 SendTradeKwangMyeong stKM = new SendTradeKwangMyeong();
		 stKM.execute();

		 SendTradeKMEntrFee stKMef = new SendTradeKMEntrFee();
		 stKMef.execute();
	}
}
