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
import com.hanaph.gw.ea.newReport.dao.NewReportE01011DAO;
import com.hanaph.gw.ea.newReport.vo.AccidentVO;
import com.hanaph.gw.ea.newReport.vo.ApprovalMasterVO;


/**
 * <pre>
 * Class Name : NewReportE01011ServiceImpl.java
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
@Service(value="newReportE01011Service")
public class NewReportE01011ServiceImpl implements NewReportE01011Service {

	@Autowired 
	NewReportE01011DAO newReportE01011DAO;
	
	@Autowired 
	NewReportDAO newReportDAO;

	/* (non-Javadoc)
	 * @see com.hanaph.gw.ea.newReport.service.NewReportE01011Service#insertAccident(com.hanaph.gw.ea.newReport.vo.ApprovalMasterVO, com.hanaph.gw.ea.newReport.vo.AccidentVO)
	 */
	@Override
	@Transactional
	public String insertAccident(ApprovalMasterVO paramAmVO, AccidentVO paramAdVO) {
		String approval_seq = newReportDAO.insertApprovalMaster(paramAmVO);
		paramAdVO.setApproval_seq(approval_seq);
		newReportE01011DAO.insertAccident(paramAdVO);
		return approval_seq;
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.ea.newReport.service.NewReportE01011Service#accidentDetail(java.lang.String)
	 */
	@Override
	public AccidentVO accidentDetail(String approval_seq) {
		return newReportE01011DAO.accidentDetail(approval_seq);
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.ea.newReport.service.NewReportE01011Service#updateAccident(com.hanaph.gw.ea.newReport.vo.ApprovalMasterVO, com.hanaph.gw.ea.newReport.vo.AccidentVO)
	 */
	@Override
	@Transactional
	public void updateAccident(ApprovalMasterVO paramAmVO, AccidentVO paramAdVO) {
		newReportDAO.updateApprovalMaster(paramAmVO);
		newReportE01011DAO.updateAccident(paramAdVO);
	}
	

}
