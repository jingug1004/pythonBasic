package org.daemon.kcycle.cash;

public class SendTradeNow {

	public static void main(String[] args) {	
		// 즉시 실행한다.
	    //try {
		//      Thread.sleep(10 * 1000);	// 10초 뒤에 실행
		//} catch (InterruptedException e) {}
		
		SendTrade st = new SendTrade();
		st.execute();		
	}
	
}
