/**
  * Hana Project
  * Copyright 2014 iRush Co.,
  *
  */
package com.hanaph.gw.ea.newReport.service;


import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hanaph.gw.ea.newReport.dao.NewReportDAO;
import com.hanaph.gw.ea.newReport.dao.NewReportE01005DAO;
import com.hanaph.gw.ea.newReport.vo.ApprovalMasterVO;
import com.hanaph.gw.ea.newReport.vo.CommuteVO;


/**
 * <pre>
 * Class Name : NewReportE01005ServiceImpl.java
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
@Service(value="newReportE01005Service")
public class NewReportE01005ServiceImpl implements NewReportE01005Service {

	@Autowired 
	NewReportE01005DAO newReportE01005DAO;

	@Autowired 
	NewReportDAO newReportDAO;
	
	/* (non-Javadoc)
	 * @see com.hanaph.gw.ea.newReport.service.NewReportE01005Service#insertCommute(com.hanaph.gw.ea.newReport.vo.ApprovalMasterVO, com.hanaph.gw.ea.newReport.vo.CommuteVO)
	 */
	@Override
	@Transactional
	public String insertCommute(ApprovalMasterVO paramAmVO, CommuteVO paramCmVO) {
		String approval_seq = newReportDAO.insertApprovalMaster(paramAmVO);
		paramCmVO.setApproval_seq(approval_seq);
		newReportE01005DAO.insertCommute(paramCmVO);
		return approval_seq; 
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.ea.newReport.service.NewReportE01005Service#commuteDetail(java.lang.String)
	 */
	@Override
	public CommuteVO commuteDetail(String approval_seq) {
		return newReportE01005DAO.commuteDetail(approval_seq);
	}
	
	/* (non-Javadoc)
	 * @see com.hanaph.gw.ea.newReport.service.NewReportE01005Service#commuteTardy(java.util.Map)
	 */
	@Override
	public CommuteVO commuteTardy(Map<String, String> paramMap) {
		return newReportE01005DAO.commuteTardy(paramMap);
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.ea.newReport.service.NewReportE01005Service#updateCommute(com.hanaph.gw.ea.newReport.vo.ApprovalMasterVO, com.hanaph.gw.ea.newReport.vo.CommuteVO)
	 */
	@Override
	@Transactional
	public void updateCommute(ApprovalMasterVO paramAmVO, CommuteVO paramCmVO) {
		newReportDAO.updateApprovalMaster(paramAmVO);
		newReportE01005DAO.updateCommute(paramCmVO);
	}
	

}
