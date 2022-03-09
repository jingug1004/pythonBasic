/**
  * Hana Project
  * Copyright 2014 iRush Co.,
  *
  */
package com.hanaph.gw.ea.newReport.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hanaph.gw.ea.newReport.dao.NewReportE01010DAO;
import com.hanaph.gw.ea.newReport.vo.ApprovalMasterVO;
import com.hanaph.gw.ea.newReport.vo.CertificateVO;

/**
 * <pre>
 * Class Name : NewReportE01010ServiceImpl.java
 * 설명 : 증명서발급신청서 ServiceImpl
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
@Service(value="newReportE01010Service")
public class NewReportE01010ServiceImpl implements NewReportE01010Service {

	@Autowired NewReportE01010DAO newReportE01010DAO;
	
	/* (non-Javadoc)
	 * @see com.hanaph.gw.ea.newReport.service.NewReportE01010Service#insertCertificate(com.hanaph.gw.ea.newReport.vo.ApprovalMasterVO, com.hanaph.gw.ea.newReport.vo.CertificateVO)
	 */
	@Override
	public String insertCertificate(ApprovalMasterVO paramAmVO,
			CertificateVO paramCfcVO) {
		return newReportE01010DAO.insertCertificate(paramAmVO, paramCfcVO);
	}


	/* (non-Javadoc)
	 * @see com.hanaph.gw.ea.newReport.service.NewReportE01010Service#certificateDetailList(java.lang.String)
	 */
	@Override
	public List<CertificateVO> certificateDetailList(String approval_seq) {
		return newReportE01010DAO.certificateDetailList(approval_seq);
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.ea.newReport.service.NewReportE01010Service#updateCertificate(com.hanaph.gw.ea.newReport.vo.ApprovalMasterVO, com.hanaph.gw.ea.newReport.vo.CertificateVO)
	 */
	@Override
	public void updateCertificate(ApprovalMasterVO paramAmVO, CertificateVO paramCfcVO) {
		newReportE01010DAO.updateCertificate(paramAmVO, paramCfcVO);
	}

}
