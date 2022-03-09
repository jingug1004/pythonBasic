/**
  * Hana Project
  * Copyright 2014 iRush Co.,
  *
  */
package com.hanaph.gw.ea.newReport.service;

import java.util.List;

import com.hanaph.gw.ea.newReport.vo.ApprovalMasterVO;
import com.hanaph.gw.ea.newReport.vo.PurchaseVO;

/**
 * <pre>
 * Class Name : NewReportE01008Service.java
 * 설명 : 구매신청서 Service
 * 
 * Modification Information
 *    수정일      수정자              수정 내용
 *  ------------ -------------- --------------------------------
 *  2015. 1. 16.      CHOIILJI         
 * </pre>
 * 
 * @version : 1.0
 * @author  : CHOIILJI(choiilji@irush.co.kr)
 * @since   : 2015. 1. 16.
 */
public interface NewReportE01008Service {

	/**
	 * 
	 * <pre>
	 * 1. 개요     : 구매신청서 등록한다.
	 * 2. 처리내용 : 구매신청서 등록한다.
	 * </pre>
	 * @Method Name : insertPurchase
	 * @param paramAmVO
	 * @param paramPcVO
	 * @return
	 */
	String insertPurchase(ApprovalMasterVO paramAmVO, PurchaseVO paramPcVO);
	
	/**
	 * <pre>
	 * 1. 개요     : 구매신청서 상세정보리스트
	 * 2. 처리내용 : 구매신청서 상세정보리스트
	 * </pre>
	 * @Method Name : purchaseDetailList
	 * @param approval_seq
	 * @return
	 */
	List<PurchaseVO> purchaseDetailList(String approval_seq);
	
	/**
	 * <pre>
	 * 1. 개요     : 구매신청서 수정
	 * 2. 처리내용 : 구매신청서 수정
	 * </pre>
	 * @Method Name : updatePurchase
	 * @param paramAmVO
	 * @param paramPcVO
	 * @return
	 */
	void updatePurchase(ApprovalMasterVO paramAmVO, PurchaseVO paramPcVO);

}
