/**
  * Hana Project
  * Copyright 2014 iRush Co.,
  *
  */
package com.hanaph.gw.ea.mgrApproval.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hanaph.gw.ea.mgrApproval.dao.MgrApprovalReportDAO;
import com.hanaph.gw.ea.mgrApproval.vo.MgrApprovalReportVO;

/**
 * <pre>
 * Class Name : MgrApprovalReportServiceImpl.java
 * 설명 : 관리자 문서 목록 ServiceImpl
 * 
 * Modification Information
 *    수정일      수정자              수정 내용
 *  ------------ -------------- --------------------------------
 *  2015. 1. 22.      CHOIILJI         
 * </pre>
 * 
 * @version : 1.0
 * @author  : CHOIILJI(choiilji@irush.co.kr)
 * @since   : 2015. 1. 22.
 */
@Service(value="mgrApprovalReportService")
public class MgrApprovalReportServiceImpl implements MgrApprovalReportService {

	@Autowired
	private MgrApprovalReportDAO mgrApprovalReportDao;
	/* (non-Javadoc)
	 * @see com.hanaph.gw.ea.mgrApproval.service.MgrApprovalReportService#getMgrApprovalReportList(java.util.Map)
	 */
	@Override
	public List<MgrApprovalReportVO> getMgrApprovalReportList(Map<String, String> paramMap) {
		return mgrApprovalReportDao.getMgrApprovalReportList(paramMap);
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.ea.mgrApproval.service.MgrApprovalReportService#getMgrApprovalReportCount(java.util.Map)
	 */
	@Override
	public int getMgrApprovalReportCount(Map<String, String> paramMap) {
		return mgrApprovalReportDao.getMgrApprovalReportCount(paramMap);
	}

}
