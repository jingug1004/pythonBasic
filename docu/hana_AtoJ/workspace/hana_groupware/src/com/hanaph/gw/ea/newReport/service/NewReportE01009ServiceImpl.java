/**
  * Hana Project
  * Copyright 2014 iRush Co.,
  *
  */
package com.hanaph.gw.ea.newReport.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hanaph.gw.ea.newReport.dao.NewReportE01009DAO;
import com.hanaph.gw.ea.newReport.vo.ApprovalMasterVO;
import com.hanaph.gw.ea.newReport.vo.OvertimeVO;

/**
 * <pre>
 * Class Name : NewReportE01009ServiceImpl.java
 * 설명 : 시간외근무신청서 ServiceImpl
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
@Service(value="newReportE01009Service")
public class NewReportE01009ServiceImpl implements NewReportE01009Service {

	@Autowired NewReportE01009DAO newReportE01009DAO;
	
	/* (non-Javadoc)
	 * @see com.hanaph.gw.ea.newReport.service.NewReportE01009Service#insertOvertime(com.hanaph.gw.ea.newReport.vo.ApprovalMasterVO, com.hanaph.gw.ea.newReport.vo.OvertimeVO)
	 */
	@Override
	public String insertOvertime(ApprovalMasterVO paramAmVO,
			OvertimeVO paramOtVO) {
		return newReportE01009DAO.insertOvertime(paramAmVO, paramOtVO);
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.ea.newReport.service.NewReportE01009Service#overtimeDetailList(java.lang.String)
	 */
	@Override
	public List<OvertimeVO> overtimeDetailList(String approval_seq) {
		return newReportE01009DAO.overtimeDetailList(approval_seq);
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.ea.newReport.service.NewReportE01009Service#updateOvertime(com.hanaph.gw.ea.newReport.vo.ApprovalMasterVO, com.hanaph.gw.ea.newReport.vo.OvertimeVO)
	 */
	@Override
	public void updateOvertime(ApprovalMasterVO paramAmVO, OvertimeVO paramOtVO) {
		newReportE01009DAO.updateOvertime(paramAmVO, paramOtVO);
	}

}
