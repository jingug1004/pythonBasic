package org.daemon.kcycle.fixedseatKM;

public class SendTradeNow {    	
	public static void main(String[] args) {	
		// 즉시 실행한다.
	    //try {
		//      Thread.sleep(10 * 1000);	// 10초 뒤에 실행
		//} catch (InterruptedException e) {}
		
 		SendTradeKM stKM = new SendTradeKM();
		stKM.execute();		
		
		SendTradeKMef stKMef = new SendTradeKMef();
		stKMef.execute();		 		
	}
}
