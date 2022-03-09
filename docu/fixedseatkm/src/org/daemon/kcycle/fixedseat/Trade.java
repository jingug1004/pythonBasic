
package org.daemon.kcycle.fixedseat;

public class Trade {

	String  mId;
	String  mDate;
	String  mTime;
	String  mSeat;
	Long    mAmount;
	String  mUniCode;
	String  mUniSub;
	Double  mPk;
	
	Trade () {
		
	}
	
	Trade(
			String  pId,
			String  pDate,
			String  pTime,
			String  pSeat,
			Long    pAmount,
			String  pUniCode,
			String  pUniSub,
			Double  pPk 
		  ) {		
		mId      = pId;
		mDate    = pDate;
		mTime    = pTime;
		mSeat    = pSeat;
		mAmount  = pAmount;
		mUniCode = pUniCode;
		mUniSub  = pUniSub;
		mPk      = pPk;
	}
	
	public String getId() {
		return mId;
	}	 
	public String getDate() {
		return mDate;
	}	 
	public String getTime() {
		return mTime;
	}	 
	public String getSeat() {
		return mSeat;
	}	 
	public Long getAmount() {
		return mAmount;
	}	 
	public String getUniCode() {
		return mUniCode;
	}	 
	public String getUniSub() {
		return mUniSub;
	}
	public Double getPk() {
		return mPk;
	}	 
}
