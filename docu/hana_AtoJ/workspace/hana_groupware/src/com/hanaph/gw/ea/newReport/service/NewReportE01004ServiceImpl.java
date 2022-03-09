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
import com.hanaph.gw.ea.newReport.dao.NewReportE01004DAO;
import com.hanaph.gw.ea.newReport.vo.ApprovalMasterVO;
import com.hanaph.gw.ea.newReport.vo.VaporizeVO;


/**
 * <pre>
 * Class Name : NewReportE01004ServiceImpl.java
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
@Service(value="newReportE01004Service")
public class NewReportE01004ServiceImpl implements NewReportE01004Service {
	
	@Autowired 
	NewReportE01004DAO newReportE01004DAO;

	@Autowired 
	NewReportDAO newReportDAO;
	
	/* (non-Javadoc)
	 * @see com.hanaph.gw.ea.newReport.service.NewReportE01004Service#insertVaporize(com.hanaph.gw.ea.newReport.vo.ApprovalMasterVO, com.hanaph.gw.ea.newReport.vo.VaporizeVO)
	 */
	@Override
	@Transactional
	public String insertVaporize(ApprovalMasterVO paramAmVO, VaporizeVO paramVrVO) {
		String approval_seq = newReportDAO.insertApprovalMaster(paramAmVO);
		paramVrVO.setApproval_seq(approval_seq);
		newReportE01004DAO.insertVaporize(paramVrVO);
		return approval_seq;
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.ea.newReport.service.NewReportE01004Service#vaporizeDetail(java.lang.String)
	 */
	@Override
	public VaporizeVO vaporizeDetail(String approval_seq) {
		return newReportE01004DAO.vaporizeDetail(approval_seq);
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.ea.newReport.service.NewReportE01004Service#updateVaporize(com.hanaph.gw.ea.newReport.vo.ApprovalMasterVO, com.hanaph.gw.ea.newReport.vo.VaporizeVO)
	 */
	@Override
	@Transactional
	public void updateVaporize(ApprovalMasterVO paramAmVO, VaporizeVO paramVrVO) {
		newReportDAO.updateApprovalMaster(paramAmVO);
		newReportE01004DAO.updateVaporize(paramVrVO);
	}
	

}
