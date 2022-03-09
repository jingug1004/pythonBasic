/**
  * Hana Project
  * Copyright 2014 iRush Co.,
  *
  */
package com.hanaph.gw.ea.newReport.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hanaph.gw.ea.newReport.dao.NewReportE01012DAO;
import com.hanaph.gw.ea.newReport.vo.ApprovalMasterVO;
import com.hanaph.gw.ea.newReport.vo.OvertimeVO;

/**
 * <pre>
 * Class Name : NewReportE01012ServiceImpl.java
 * 설명 : 시간외근무내역서 ServiceImpl
 * 
 * Modification Information
 *    수정일      수정자              수정 내용
 *  ------------ -------------- --------------------------------
 *  2015. 1. 16.      CHOIILJI         
 * </pre>
 * 
 * @version : 
 * @author  : CHOIILJI(choiilji@irush.co.kr)
 * @since   : 2015. 1. 16.
 */
@Service(value="newReportE01012Service")
public class NewReportE01012ServiceImpl implements NewReportE01012Service {

	@Autowired NewReportE01012DAO newReportE01021DAO;
	
	/* (non-Javadoc)
	 * @see com.hanaph.gw.ea.newReport.service.NewReportE01012Service#insertOvertimeDetail(com.hanaph.gw.ea.newReport.vo.ApprovalMasterVO, com.hanaph.gw.ea.newReport.vo.OvertimeVO)
	 */
	@Override
	public String insertOvertimeDetail(ApprovalMasterVO paramAmVO,
			OvertimeVO paramOtVO) {
		return newReportE01021DAO.insertOvertimeDetail(paramAmVO, paramOtVO);
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.ea.newReport.service.NewReportE01012Service#overtimeDetailDetailList(java.lang.String)
	 */
	@Override
	public List<OvertimeVO> overtimeDetailDetailList(String approval_seq) {
		return newReportE01021DAO.overtimeDetailDetailList(approval_seq);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.hanaph.gw.ea.newReport.service.NewReportE01012Service#overtimeExist(java.lang.String)
	 */
	@Override
	public boolean overtimeExist(String approval_seq_old) {
		return newReportE01021DAO.overtimeExist(approval_seq_old);
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.ea.newReport.service.NewReportE01012Service#updateOvertimeDetail(com.hanaph.gw.ea.newReport.vo.ApprovalMasterVO, com.hanaph.gw.ea.newReport.vo.OvertimeVO)
	 */
	@Override
	public void updateOvertimeDetail(ApprovalMasterVO paramAmVO, OvertimeVO paramOtVO) {
		newReportE01021DAO.updateOvertimeDetail(paramAmVO, paramOtVO);
	}

}
