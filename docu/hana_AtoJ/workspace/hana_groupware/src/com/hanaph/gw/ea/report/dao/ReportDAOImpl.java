/**
  * Hana Project
  * Copyright 2014 iRush Co.,
  *
  */
package com.hanaph.gw.ea.report.dao;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

import com.hanaph.gw.ea.newReport.vo.ApprovalMasterVO;
import com.hanaph.gw.ea.report.vo.ReportVO;


/**
 * <pre>
 * Class Name : ReportDAOImpl.java
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
@Repository("reportDAO")
public class ReportDAOImpl extends SqlSessionDaoSupport implements ReportDAO{

	/* (non-Javadoc)
	 * @see com.hanaph.gw.ea.report.dao.ReportDAO#getReportCount(java.util.Map)
	 */
	@Override
	public int getReportCount(Map<String, String> paramMap) {
		Integer count = (Integer)getSqlSession().selectOne("report.getReportCount", paramMap);
		return count;
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.ea.report.dao.ReportDAO#getReportList(java.util.Map)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ReportVO> getReportList(Map<String, String> paramMap) {
		return (List<ReportVO>)getSqlSession().selectList("report.getReportList", paramMap);

	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.ea.report.dao.ReportDAO#getMainReportCnt(java.util.Map)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ApprovalMasterVO> getMainReportCnt(Map<String, String> paramMap) {
		return (List<ApprovalMasterVO>)getSqlSession().selectList("report.getMainReportCnt", paramMap);
	}

}
