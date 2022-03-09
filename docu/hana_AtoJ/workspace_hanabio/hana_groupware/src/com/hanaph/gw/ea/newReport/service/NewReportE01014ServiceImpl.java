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
import com.hanaph.gw.ea.newReport.dao.NewReportE01014DAO;
import com.hanaph.gw.ea.newReport.vo.ApprovalMasterVO;
import com.hanaph.gw.ea.newReport.vo.ReviewVO;


/**
 * <pre>
 * Class Name : NewReportE01014ServiceImpl.java
 * 설명 : 
 * 
 * Modification Information
 *    수정일      수정자              수정 내용
 *  ------------ -------------- --------------------------------
 *  2015. 12. 10.      개발검토서         
 * </pre>
 * 
 * @version : 
 * @author  : 전산팀
 * @since   : 2015. 12. 10.
 */
@Service(value="newReportE01014Service")
public class NewReportE01014ServiceImpl implements NewReportE01014Service {

	@Autowired 
	NewReportE01014DAO newReportE01014DAO;

	@Autowired 
	NewReportDAO newReportDAO;
	
	/* (non-Javadoc)
	 * @see com.hanaph.gw.ea.newReport.service.NewReportE01014Service#insertReview(com.hanaph.gw.ea.newReport.vo.ApprovalMasterVO, com.hanaph.gw.ea.newReport.vo.ReviewVO)
	 */
	@Override
	@Transactional
	public String insertReview(ApprovalMasterVO paramAmVO, ReviewVO paramDfVO) {
		String approval_seq = newReportDAO.insertApprovalMaster(paramAmVO);
		paramDfVO.setApproval_seq(approval_seq);
		newReportE01014DAO.insertReview(paramDfVO);
		return approval_seq;
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.ea.newReport.service.NewReportE01014Service#reviewDetail(java.lang.String)
	 */
	@Override
	public ReviewVO reviewDetail(String approval_seq) {
		return newReportE01014DAO.reviewDetail(approval_seq);
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.ea.newReport.service.NewReportE01014Service#updateReview(com.hanaph.gw.ea.newReport.vo.ApprovalMasterVO, com.hanaph.gw.ea.newReport.vo.ReviewVO)
	 */
	@Override
	@Transactional
	public void updateReview(ApprovalMasterVO paramAmVO, ReviewVO paramDfVO) {
		newReportDAO.updateApprovalMaster(paramAmVO);
		newReportE01014DAO.updateReview(paramDfVO);
	}
	

}
