package org.daemon.kcycle.fixedseat;

public class SendTradeNow {    	
	public static void main(String[] args) {	
		// ��� �����Ѵ�.
	    //try {
		//      Thread.sleep(10 * 1000);	// 10�� �ڿ� ����
		//} catch (InterruptedException e) {}
		
		//SendTrade st = new SendTrade();
		//st.execute();		

		//SendTradeKwangMyeong stKM = new SendTradeKwangMyeong();
		//stKM.execute();		
		
		SendTradeKMEntrFee stKMef = new SendTradeKMEntrFee();
		stKMef.execute();				
	}
}
