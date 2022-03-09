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
import com.hanaph.gw.ea.newReport.dao.NewReportE01001DAO;
import com.hanaph.gw.ea.newReport.vo.ApprovalMasterVO;
import com.hanaph.gw.ea.newReport.vo.VacationVO;


/**
 * <pre>
 * Class Name : NewReportE01001ServiceImpl.java
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
@Service(value="newReportE01001Service")
public class NewReportE01001ServiceImpl implements NewReportE01001Service {

	@Autowired 
	NewReportE01001DAO newReportE01001DAO;
	
	@Autowired 
	NewReportDAO newReportDAO;

	/* (non-Javadoc)
	 * @see com.hanaph.gw.ea.newReport.service.NewReportE01001Service#insertVacation(com.hanaph.gw.ea.newReport.vo.ApprovalMasterVO, com.hanaph.gw.ea.newReport.vo.VacationVO)
	 */
	@Override
	@Transactional
	public String insertVacation(ApprovalMasterVO paramAmVO, VacationVO paramVcVO) {
		String approval_seq = newReportDAO.insertApprovalMaster(paramAmVO);
		paramVcVO.setApproval_seq(approval_seq);
		newReportE01001DAO.insertVacation(paramVcVO);
		return approval_seq;
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.ea.newReport.service.NewReportE01001Service#vacationDetail(java.lang.String)
	 */
	@Override
	public VacationVO vacationDetail(String approval_seq) {
		return newReportE01001DAO.vacationDetail(approval_seq);
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.ea.newReport.service.NewReportE01001Service#updateVacation(com.hanaph.gw.ea.newReport.vo.ApprovalMasterVO, com.hanaph.gw.ea.newReport.vo.VacationVO)
	 */
	@Override
	@Transactional
	public void updateVacation(ApprovalMasterVO paramAmVO, VacationVO paramVcVO) {
		newReportDAO.updateApprovalMaster(paramAmVO);
		newReportE01001DAO.updateVacation(paramVcVO);
	}
	

}
