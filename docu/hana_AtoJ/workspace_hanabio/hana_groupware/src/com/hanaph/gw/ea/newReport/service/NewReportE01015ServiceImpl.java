/**
  * Hana Project
  * Copyright 2014 iRush Co.,
  *
  */
package com.hanaph.gw.ea.newReport.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hanaph.gw.ea.newReport.dao.NewReportDAO;
import com.hanaph.gw.ea.newReport.dao.NewReportE01015DAO;
import com.hanaph.gw.ea.newReport.vo.ApprovalMasterVO;
import com.hanaph.gw.ea.newReport.vo.OvertimeVO;
import com.hanaph.gw.ea.newReport.vo.RequisitionVO;


/**
 * <pre>
 * Class Name : NewReportE01015ServiceImpl.java
 * 설명 : 
 * 
 * Modification Information
 *    수정일      수정자              수정 내용
 *  ------------ -------------- --------------------------------
 *  2015. 12. 11.      물품 구입 청구서
 * </pre>
 * 
 * @version : 
 * @author  : 전산팀
 * @since   : 2015. 12. 11.
 */
@Service(value="newReportE01015Service")
public class NewReportE01015ServiceImpl implements NewReportE01015Service {

	@Autowired 
	NewReportE01015DAO newReportE01015DAO;
	
	/* (non-Javadoc)
	 * @see com.hanaph.gw.ea.newReport.service.NewReportE01015Service#insertRequisition(com.hanaph.gw.ea.newReport.vo.ApprovalMasterVO, com.hanaph.gw.ea.newReport.vo.RequisitionVO)
	 */
	@Override
	@Transactional
	public String insertRequisition(ApprovalMasterVO paramAmVO, RequisitionVO paramRqVO) {
		return newReportE01015DAO.insertRequisition(paramAmVO ,paramRqVO);
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.ea.newReport.service.NewReportE01015Service#requisitionDetail(java.lang.String)
	 */
	@Override
	public List<RequisitionVO> requisitionDetail(String approval_seq) {
		return newReportE01015DAO.requisitionDetail(approval_seq);
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.ea.newReport.service.NewReportE01015Service#updateRequisition(com.hanaph.gw.ea.newReport.vo.ApprovalMasterVO, com.hanaph.gw.ea.newReport.vo.Requisition)
	 */
	@Override
	@Transactional
	public void updateRequisition(ApprovalMasterVO paramAmVO, RequisitionVO paramRqVO) {		
		newReportE01015DAO.updateRequisition (paramAmVO ,paramRqVO);
	}
	
	/* (non-Javadoc)
	 * @see com.hanaph.gw.ea.newReport.service.NewReportE01015Service#searchReqNo(java.lang.String)
	 */
	@Override
	public List<RequisitionVO> searchReqNo(String search_req_ymd) {
		return newReportE01015DAO.searchReqNo(search_req_ymd);
	}
	/* (non-Javadoc)
	 * @see com.hanaph.gw.ea.newReport.service.NewReportE01015Service#searchReqData(java.lang.String)
	 */
	@Override
	public List<RequisitionVO> searchReqData(String search_req_no) {
		return newReportE01015DAO.searchReqData(search_req_no);
	}
}
