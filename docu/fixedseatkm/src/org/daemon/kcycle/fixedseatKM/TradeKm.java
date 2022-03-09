
package org.daemon.kcycle.fixedseatKM;

public class TradeKm {

	String  mJobType;
	String  mSeq;
	String  mLoc;
	String  mLocName;
	String  mSaleDate;
	String  mSaleTime;
	String  mMenuCode;
	String  mMenuName;
	Long    mPrice;
	Long    mQty;
	Long    mAmt;
	
	TradeKm () {
		
	}
	
	TradeKm(
			String  pJobType,
			String  pSeq,
			String  pLoc,
			String  pLocName,
			String  pSaleDate,
			String  pSaleTime,
			String  pMenuCode,
			String  pMenuName,
			Long    pPrice,
			Long    pQty,
			Long    pAmt
		  ) {	
		mJobType  = pJobType;
		mSeq      = pSeq;
		mLoc      = pLoc;
		mLocName  = pLocName;
		mSaleDate = pSaleDate;
		mSaleTime = pSaleTime;
		mMenuCode = pMenuCode;
		mMenuName = pMenuName;
		mPrice    = pPrice;
		mQty      = pQty;
		mAmt      = pAmt;
	}

	public String getJobType() {
		return mJobType;
	}	
	public String getSeq() {
		return mSeq;
	}	 
	public String getLoc() {
		return mLoc;
	}	 
	public String getLocName() {
		return mLocName;
	}	 
	public String getSaleDate() {
		return mSaleDate;
	}	 
	public String getSaleTime() {
		return mSaleTime;
	}	 
	public String getMenuCode() {
		return mMenuCode;
	}	 
	public String getMenuName() {
		return mMenuName;
	}	 
	public Long getPrice() {
		return mPrice;
	}	 
	public Long getQty() {
		return mQty;
	}
	public Long getAmt() {
		return mAmt;
	}	 
}
