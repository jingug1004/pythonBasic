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
import com.hanaph.gw.ea.newReport.dao.NewReportE01003DAO;
import com.hanaph.gw.ea.newReport.vo.ApprovalMasterVO;
import com.hanaph.gw.ea.newReport.vo.IncompanyVO;


/**
 * <pre>
 * Class Name : NewReportE01003ServiceImpl.java
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
@Service(value="newReportE01003Service")
public class NewReportE01003ServiceImpl implements NewReportE01003Service {

	@Autowired 
	NewReportE01003DAO newReportE01003DAO;

	@Autowired 
	NewReportDAO newReportDAO;
	
	/* (non-Javadoc)
	 * @see com.hanaph.gw.ea.newReport.service.NewReportE01003Service#insertIncompany(com.hanaph.gw.ea.newReport.vo.ApprovalMasterVO, com.hanaph.gw.ea.newReport.vo.IncompanyVO)
	 */
	@Override
	@Transactional
	public String insertIncompany(ApprovalMasterVO paramAmVO, IncompanyVO paramIcVO) {
		String approval_seq = newReportDAO.insertApprovalMaster(paramAmVO);
		paramIcVO.setApproval_seq(approval_seq);
		newReportE01003DAO.insertIncompany(paramIcVO);
		return approval_seq;
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.ea.newReport.service.NewReportE01003Service#incompanyDetail(java.lang.String)
	 */
	@Override
	public IncompanyVO incompanyDetail(String approval_seq) {
		return newReportE01003DAO.incompanyDetail(approval_seq);
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.ea.newReport.service.NewReportE01003Service#updateIncompany(com.hanaph.gw.ea.newReport.vo.ApprovalMasterVO, com.hanaph.gw.ea.newReport.vo.IncompanyVO)
	 */
	@Override
	@Transactional
	public void updateIncompany(ApprovalMasterVO paramAmVO, IncompanyVO paramIcVO) {
		newReportDAO.updateApprovalMaster(paramAmVO);
		newReportE01003DAO.updateIncompany(paramIcVO);
	}
}
