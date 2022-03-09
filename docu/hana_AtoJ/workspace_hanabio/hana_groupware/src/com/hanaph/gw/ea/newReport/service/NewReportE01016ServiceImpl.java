/**
  * Hana Project
  * Copyright 2014 iRush Co.,
  *
  */
package com.hanaph.gw.ea.newReport.service;


import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hanaph.gw.ea.newReport.dao.NewReportDAO;
import com.hanaph.gw.ea.newReport.dao.NewReportE01016DAO;
import com.hanaph.gw.ea.newReport.vo.ApprovalMasterVO;
import com.hanaph.gw.ea.newReport.vo.OvertimeVO;
import com.hanaph.gw.ea.newReport.vo.DeliveryVO;


/**
 * <pre>
 * Class Name : NewReportE01016ServiceImpl.java
 * 설명 : 
 * 
 * Modification Information
 *    수정일      수정자              수정 내용
 *  ------------ -------------- --------------------------------
 *  2016. 01. 25.      원부자재 납품확인서
 * </pre>
 * 
 * @version : 
 * @author  : 전산팀
 * @since   : 2016. 01. 25.
 */
@Service(value="newReportE01016Service")
public class NewReportE01016ServiceImpl implements NewReportE01016Service {

	@Autowired 
	NewReportE01016DAO newReportE01016DAO;
	
	/* (non-Javadoc)
	 * @see com.hanaph.gw.ea.newReport.service.NewReportE01016Service#insertDelivery(com.hanaph.gw.ea.newReport.vo.ApprovalMasterVO, com.hanaph.gw.ea.newReport.vo.DeliveryVO)
	 */
	@Override
	@Transactional
	public String insertDelivery(ApprovalMasterVO paramAmVO, DeliveryVO paramDlVO) {
		return newReportE01016DAO.insertDelivery(paramAmVO ,paramDlVO);
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.ea.newReport.service.NewReportE01016Service#deliveryDetail(java.lang.String)
	 */
	@Override
	public List<DeliveryVO> deliveryDetail(String approval_seq) {
		return newReportE01016DAO.deliveryDetail(approval_seq);
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.ea.newReport.service.NewReportE01016Service#updateDelivery(com.hanaph.gw.ea.newReport.vo.ApprovalMasterVO, com.hanaph.gw.ea.newReport.vo.Delivery)
	 */
	@Override
	@Transactional
	public void updateDelivery(ApprovalMasterVO paramAmVO, DeliveryVO paramDlVO) {		
		newReportE01016DAO.updateDelivery (paramAmVO ,paramDlVO);
	}
	
	/* (non-Javadoc)
	 * @see com.hanaph.gw.ea.newReport.service.NewReportE010165Service#searchSlipNo(java.lang.String)
	 */
	@Override
	public List<DeliveryVO> searchSlipNo(String search_ymd) {
		return newReportE01016DAO.searchSlipNo(search_ymd);
	}
	/* (non-Javadoc)
	 * @see com.hanaph.gw.ea.newReport.service.NewReportE01016Service#searchSlipData(java.lang.String)
	 */
	@Override
	public List<DeliveryVO> searchSlipData(String search_no) {
		return newReportE01016DAO.searchSlipData(search_no);
	}
}
