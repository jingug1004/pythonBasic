/**
  * Hana Project
  * Copyright 2014 iRush Co.,
  *
  */
package com.hanaph.gw.ea.mgrApproval.dao;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

import com.hanaph.gw.ea.mgrApproval.vo.MgrApprovalReportVO;

/**
 * <pre>
 * Class Name : MgrReportDAOImpl.java
 * 설명 : 관리자 문서 목록 DAOImpl
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
@Repository("mgrReportApprovalDao")
public class MgrApprovalReportDAOImpl extends SqlSessionDaoSupport implements
		MgrApprovalReportDAO {

	/* (non-Javadoc)
	 * @see com.hanaph.gw.ea.mgrApproval.dao.MgrApprovalReportDAO#getMgrApprovalReportList(java.util.Map)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<MgrApprovalReportVO> getMgrApprovalReportList(Map<String, String> paramMap) {
		return (List<MgrApprovalReportVO>)getSqlSession().selectList("mgrApproval.getMgrApprovalReportList", paramMap);
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.ea.mgrApproval.dao.MgrApprovalReportDAO#getMgrApprovalReportCount(java.util.Map)
	 */
	@Override
	public int getMgrApprovalReportCount(Map<String, String> paramMap) {
		return (Integer)getSqlSession().selectOne("mgrApproval.getMgrApprovalReportCount", paramMap);
	}

}
