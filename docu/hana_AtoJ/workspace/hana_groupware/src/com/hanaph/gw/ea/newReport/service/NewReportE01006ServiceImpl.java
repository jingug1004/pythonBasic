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
import com.hanaph.gw.ea.newReport.dao.NewReportE01006DAO;
import com.hanaph.gw.ea.newReport.vo.ApprovalMasterVO;
import com.hanaph.gw.ea.newReport.vo.ParticipationVO;


/**
 * <pre>
 * Class Name : NewReportE01006ServiceImpl.java
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
@Service(value="newReportE01006Service")
public class NewReportE01006ServiceImpl implements NewReportE01006Service {

	@Autowired 
	NewReportE01006DAO newReportE01006DAO;
	
	@Autowired 
	NewReportDAO newReportDAO;

	/* (non-Javadoc)
	 * @see com.hanaph.gw.ea.newReport.service.NewReportE01006Service#insertParticipation(com.hanaph.gw.ea.newReport.vo.ApprovalMasterVO, com.hanaph.gw.ea.newReport.vo.ParticipationVO)
	 */
	@Override
	@Transactional
	public String insertParticipation(ApprovalMasterVO paramAmVO, ParticipationVO paramPpVO) {
		String approval_seq = newReportDAO.insertApprovalMaster(paramAmVO);
		paramPpVO.setApproval_seq(approval_seq);
		newReportE01006DAO.insertParticipation(paramPpVO);
		return approval_seq;
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.ea.newReport.service.NewReportE01006Service#participationDetail(java.lang.String)
	 */
	@Override
	public ParticipationVO participationDetail(String approval_seq) {
		return newReportE01006DAO.participationDetail(approval_seq);
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.ea.newReport.service.NewReportE01006Service#updateParticipation(com.hanaph.gw.ea.newReport.vo.ApprovalMasterVO, com.hanaph.gw.ea.newReport.vo.ParticipationVO)
	 */
	@Override
	@Transactional
	public void updateParticipation(ApprovalMasterVO paramAmVO, ParticipationVO paramPpVO) {
		newReportDAO.updateApprovalMaster(paramAmVO);
		newReportE01006DAO.updateParticipation(paramPpVO);
	}
	

}
