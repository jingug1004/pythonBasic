
package org.daemon.kcycle.cash;

public class Trade {

	String mSDATE;
	String mM_NO;
	String mILNO;
	String mRTN;
	String mUSE_CD;
	String mPAY_GBN;
	String mQNTY;
	String mAMNT;
	String mFTIME;
	
	Trade () {
		
	}
	
	Trade(
			String pSDATE,
			String pM_NO,
			String pILNO,
			String pRTN,
			String pUSE_CD,
			String pPAY_GBN,
			String pQNTY,
			String pAMNT,
			String pFTIME) {
		
		mSDATE		= pSDATE;
		mM_NO		= pM_NO;
		mILNO		= pILNO;
		mRTN		= pRTN;
		mUSE_CD		= pUSE_CD;
		mPAY_GBN	= pPAY_GBN;
		mQNTY		= pQNTY;
		mAMNT		= pAMNT;
		mFTIME		= pFTIME;
	}
	
	public String getSDATE() {
		return mSDATE;
	}
	 
	public String getM_NO() {
		return mM_NO;
	}
	 
	public String getILNO() {
		return mILNO;
	}
	 
	public String getRTN() {
		return mRTN;
	}
	 
	public String getUSE_CD() {
		return mUSE_CD;
	}
	 
	public String getPAY_GBN() {
		return mPAY_GBN;
	}
	 
	public String getQNTY() {
		return mQNTY;
	}
	 
	public String getAMNT() {
		return mAMNT;
	}

	public String getFTIME() {
		return mFTIME;
	}
}
