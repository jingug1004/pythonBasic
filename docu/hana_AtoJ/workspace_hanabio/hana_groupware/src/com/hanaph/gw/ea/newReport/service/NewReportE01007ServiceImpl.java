/**
  * Hana Project
  * Copyright 2014 iRush Co.,
  *
  */
package com.hanaph.gw.ea.newReport.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hanaph.gw.ea.newReport.dao.NewReportE01007DAO;
import com.hanaph.gw.ea.newReport.vo.ApprovalMasterVO;
import com.hanaph.gw.ea.newReport.vo.SampleVO;

/**
 * <pre>
 * Class Name : NewReportE01007ServiceImpl.java
 * 설명 : 샘플신청서 ServiceImpl
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
@Service(value="newReportE01007Service")
public class NewReportE01007ServiceImpl implements NewReportE01007Service {

	@Autowired NewReportE01007DAO newReportE01007DAO;
	
	/* (non-Javadoc)
	 * @see com.hanaph.gw.ea.newReport.service.NewReportE01007Service#insertSample(com.hanaph.gw.ea.newReport.vo.ApprovalMasterVO, com.hanaph.gw.ea.newReport.vo.SampleVO)
	 */
	@Override
	public String insertSample(ApprovalMasterVO paramAmVO, SampleVO paramSpVO) {
		return newReportE01007DAO.insertSample(paramAmVO, paramSpVO);
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.ea.newReport.service.NewReportE01007Service#sampleDetailList(java.lang.String)
	 */
	@Override
	public List<SampleVO> sampleDetailList(String approval_seq) {
		return newReportE01007DAO.sampleDetailList(approval_seq);
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.ea.newReport.service.NewReportE01007Service#updateSample(com.hanaph.gw.ea.newReport.vo.ApprovalMasterVO, com.hanaph.gw.ea.newReport.vo.SampleVO)
	 */
	@Override
	public void updateSample(ApprovalMasterVO paramAmVO, SampleVO paramSpVO) {
		newReportE01007DAO.updateSample(paramAmVO, paramSpVO);
	}

}
