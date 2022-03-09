/**
  * Hana Project
  * Copyright 2014 iRush Co.,
  *
  */
package com.hanaph.gw.ea.report.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hanaph.gw.ea.newReport.vo.ApprovalMasterVO;
import com.hanaph.gw.ea.report.dao.ReportDAO;
import com.hanaph.gw.ea.report.vo.ReportVO;

/**
 * <pre>
 * Class Name : ReportServiceImpl.java
 * 설명 : 
 * 
 * Modification Information
 *    수정일      수정자              수정 내용
 *  ------------ -------------- --------------------------------
 *  2015. 1. 20.      Jung.Jin.Muk          
 * </pre>
 * 
 * @version : 
 * @author  : Jung.Jin.Muk(pc123pc@irush.co.kr)
 * @since   : 2015. 1. 20.
 */
@Service(value="ReportService")
public class ReportServiceImpl implements ReportService {
	
	@Autowired
	private ReportDAO reportDAO;

	/* (non-Javadoc)
	 * @see com.hanaph.gw.ea.report.service.ReportService#getReportCount(java.util.Map)
	 */
	@Override
	public int getReportCount(Map<String, String> paramMap) {
		return reportDAO.getReportCount(paramMap);
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.ea.report.service.ReportService#getReportList(java.util.Map)
	 */
	@Override
	public List<ReportVO> getReportList(Map<String, String> paramMap) {
		return reportDAO.getReportList(paramMap);
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.ea.report.service.ReportService#getMainReportCnt(java.util.Map)
	 */
	@Override
	public List<ApprovalMasterVO> getMainReportCnt(Map<String, String> paramMap) {
		return reportDAO.getMainReportCnt(paramMap);
	}

}
