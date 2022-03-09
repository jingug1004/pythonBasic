/**
  * Hana Project
  * Copyright 2014 iRush Co.,
  *
  */
package com.hanaph.gw.ea.newReport.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hanaph.gw.ea.newReport.dao.NewReportE01008DAO;
import com.hanaph.gw.ea.newReport.vo.ApprovalMasterVO;
import com.hanaph.gw.ea.newReport.vo.PurchaseVO;

/**
 * <pre>
 * Class Name : NewReportE01008ServiceImpl.java
 * 설명 : 구매신청서 ServiceImpl
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
@Service(value="newReportE01008Service")
public class NewReportE01008ServiceImpl implements NewReportE01008Service {

	@Autowired NewReportE01008DAO newReportE01008DAO;
	
	/* (non-Javadoc)
	 * @see com.hanaph.gw.ea.newReport.service.NewReportE01008Service#insertPurchase(com.hanaph.gw.ea.newReport.vo.ApprovalMasterVO, com.hanaph.gw.ea.newReport.vo.PurchaseVO)
	 */
	@Override
	public String insertPurchase(ApprovalMasterVO paramAmVO,
			PurchaseVO paramPcVO) {
		return newReportE01008DAO.insertPurchase(paramAmVO, paramPcVO);
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.ea.newReport.service.NewReportE01008Service#purchaseDetailList(java.lang.String)
	 */
	@Override
	public List<PurchaseVO> purchaseDetailList(String approval_seq) {
		return newReportE01008DAO.purchaseDetailList(approval_seq);
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.ea.newReport.service.NewReportE01008Service#updatePurchase(com.hanaph.gw.ea.newReport.vo.ApprovalMasterVO, com.hanaph.gw.ea.newReport.vo.PurchaseVO)
	 */
	@Override
	public void updatePurchase(ApprovalMasterVO paramAmVO, PurchaseVO paramPcVO) {
		newReportE01008DAO.updatePurchase(paramAmVO, paramPcVO);
	}

}
