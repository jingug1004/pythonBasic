package org.daemon.kcycle.fixedseatKM;

public class SendTradeNow {    	
	public static void main(String[] args) {	
		// ��� �����Ѵ�.
	    //try {
		//      Thread.sleep(10 * 1000);	// 10�� �ڿ� ����
		//} catch (InterruptedException e) {}
		
 		SendTradeKM stKM = new SendTradeKM();
		stKM.execute();		
		
		SendTradeKMef stKMef = new SendTradeKMef();
		stKMef.execute();		 		
	}
}
