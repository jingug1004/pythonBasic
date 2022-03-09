/**
  * Hana Project
  * Copyright 2015.,
  * CHOE @since   : 2015. 3. 27.
  */
package com.hanaph.gw.ea.newReport.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hanaph.gw.ea.newReport.dao.NewReportDAO;
import com.hanaph.gw.ea.newReport.dao.NewReportE01013DAO;
import com.hanaph.gw.ea.newReport.vo.ApprovalMasterVO;
import com.hanaph.gw.ea.newReport.vo.NewMadicineVO;

/**
 * <pre>
 * Class Name : NewReportE01013ServiceImpl.java
 * 설명 : 
 * 
 * Modification Information
 *    수정일      수정자              수정 내용
 *  ------------ -------------- --------------------------------
 *  2015. 3. 27.      shchoe          
 * </pre>
 * 
 * @version : 
 * @author  : shchoe
 * @since   : 2015. 3. 27.
 */
@Service(value="newReportE01013Service")
public class NewReportE01013ServiceImpl implements NewReportE01013Service {
	
	@Autowired 
	NewReportE01013DAO newReportE01013DAO;

	@Autowired 
	NewReportDAO newReportDAO;
	
	
	@Override
	@Transactional
	public String insertNewMadicine(ApprovalMasterVO paramAmVO, NewMadicineVO paramVrVO) {
		String approval_seq = newReportDAO.insertApprovalMaster(paramAmVO);
		paramVrVO.setApproval_seq(approval_seq);
		newReportE01013DAO.insertNewMadicine(paramVrVO);
		return approval_seq;
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.ea.newReport.service.NewReportE01013Service#vaporizeDetail(java.lang.String)
	 */
	@Override
	public NewMadicineVO NewMadicineDetail(String approval_seq) {
		return newReportE01013DAO.NewMadicineDetail(approval_seq);
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.ea.newReport.service.NewReportE01013Service#updateVaporize(com.hanaph.gw.ea.newReport.vo.ApprovalMasterVO, com.hanaph.gw.ea.newReport.vo.VaporizeVO)
	 */
	@Override
	@Transactional
	public void updateNewMadicine(ApprovalMasterVO paramAmVO, NewMadicineVO paramVrVO) {
		newReportDAO.updateApprovalMaster(paramAmVO);
		newReportE01013DAO.updateNewMadicine(paramVrVO);
	}

}
