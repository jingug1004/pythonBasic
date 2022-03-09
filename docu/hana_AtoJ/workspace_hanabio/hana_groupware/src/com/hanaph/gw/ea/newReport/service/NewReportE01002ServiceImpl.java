/**
  * Hana Project
  * Copyright 2014 iRush Co.,
  *
  */
package com.hanaph.gw.ea.newReport.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hanaph.gw.ea.newReport.dao.NewReportDAO;
import com.hanaph.gw.ea.newReport.dao.NewReportE01002DAO;
import com.hanaph.gw.ea.newReport.vo.ApprovalMasterVO;
import com.hanaph.gw.ea.newReport.vo.DraftVO;


/**
 * <pre>
 * Class Name : NewReportE01002ServiceImpl.java
 * 설명 : 
 * 
 * Modification Information
 *    수정일      수정자              수정 내용
 *  ------------ -------------- --------------------------------
 *  2015. 1. 16.      Jung.Jin.Muk          
 * </pre>
 * 
 * @version : 
 * @author  : Jung.Jin.Muk(pc123pc@irush.co.kr)
 * @since   : 2015. 1. 16.
 */
@Service(value="newReportE01002Service")
public class NewReportE01002ServiceImpl implements NewReportE01002Service {

	@Autowired 
	NewReportE01002DAO newReportE01002DAO;

	@Autowired 
	NewReportDAO newReportDAO;
	
	/* (non-Javadoc)
	 * @see com.hanaph.gw.ea.newReport.service.NewReportE01002Service#insertDraft(com.hanaph.gw.ea.newReport.vo.ApprovalMasterVO, com.hanaph.gw.ea.newReport.vo.DraftVO)
	 */
	@Override
	@Transactional
	public String insertDraft(ApprovalMasterVO paramAmVO, DraftVO paramDfVO) {
		String approval_seq = newReportDAO.insertApprovalMaster(paramAmVO);
		paramDfVO.setApproval_seq(approval_seq);
		newReportE01002DAO.insertDraft(paramDfVO);
		return approval_seq;
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.ea.newReport.service.NewReportE01002Service#draftDetail(java.lang.String)
	 */
	@Override
	public DraftVO draftDetail(String approval_seq) {
		return newReportE01002DAO.draftDetail(approval_seq);
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.ea.newReport.service.NewReportE01002Service#updateDraft(com.hanaph.gw.ea.newReport.vo.ApprovalMasterVO, com.hanaph.gw.ea.newReport.vo.DraftVO)
	 */
	@Override
	@Transactional
	public void updateDraft(ApprovalMasterVO paramAmVO, DraftVO paramDfVO) {
		newReportDAO.updateApprovalMaster(paramAmVO);
		newReportE01002DAO.updateDraft(paramDfVO);
	}
	

}
