
package snis.rbm.business.rem4090.activity;

public class Trade {

	String mTRANSITION_SEQ;
	String mMACHINE_ID;
	String mTRADE_DATE;
	String mTERM_ID;
	String mCARD_ID;
	String mCARD_SERIAL;
	String mREQUEST_FEE;
	String mCARD_USER_CODE;
	String mCARD_USER_TYPE;
	String mCOMM_NO;
	String mDIV_NO;
	
	Trade () {
		
	}
	
	Trade(
			String pTRANSITION_SEQ,
			String pMACHINE_ID,
			String pTRADE_DATE,
			String pTERM_ID,
			String pCARD_ID,
			String pCARD_SERIAL,
			String pREQUEST_FEE,
			String pCARD_USER_CODE,
			String pCARD_USER_TYPE,
			String pCOMM_NO, 
			String pDIV_NO) {
		
		mTRANSITION_SEQ = pTRANSITION_SEQ;
		mMACHINE_ID     = pMACHINE_ID;
		mTRADE_DATE     = pTRADE_DATE;
		mTERM_ID        = pTERM_ID;
		mCARD_ID        = pCARD_ID;
		mCARD_SERIAL    = pCARD_SERIAL;
		mREQUEST_FEE    = pREQUEST_FEE;
		mCARD_USER_CODE = pCARD_USER_CODE;
		mCARD_USER_TYPE = pCARD_USER_TYPE;
		mCOMM_NO        = pCOMM_NO;
		mDIV_NO         = pDIV_NO;
	}
	
	public String getTRANSITION_SEQ() {
		return mTRANSITION_SEQ;
	}
	 
	public String getMACHINE_ID() {
		return mMACHINE_ID;
	}
	 
	public String getTRADE_DATE() {
		return mTRADE_DATE;
	}
	 
	public String getTERM_ID() {
		return mTERM_ID;
	}
	 
	public String getCARD_ID() {
		return mCARD_ID;
	}
	 
	public String getCARD_SERIAL() {
		return mCARD_SERIAL;
	}
	 
	public String getREQUEST_FEE() {
		return mREQUEST_FEE;
	}
	 
	public String getCARD_USER_CODE() {
		return mCARD_USER_CODE;
	}

	public String getCARD_USER_TYPE() {
		return mCARD_USER_TYPE;
	}
	public String getCOMM_NO() {
		return mCOMM_NO;
	}
	public String getDIV_NO() {
		return mDIV_NO;
	}
}
