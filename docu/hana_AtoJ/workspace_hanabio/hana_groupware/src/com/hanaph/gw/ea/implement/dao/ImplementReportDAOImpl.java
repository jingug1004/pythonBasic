/**
  * Hana Project
  * Copyright 2014 iRush Co.,
  *
  */
package com.hanaph.gw.ea.implement.dao;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

import com.hanaph.gw.ea.implement.vo.ImplementReportVO;

/**
 * <pre>
 * Class Name : ImplementReportDAOImpl.java
 * 설명 : 시행문서조회 DAOImpl
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
@Repository("implementReportDao")
public class ImplementReportDAOImpl extends SqlSessionDaoSupport implements
		ImplementReportDAO {

	/* (non-Javadoc)
	 * @see com.hanaph.gw.ea.implement.dao.ImplementDAO#getImplementList(java.util.Map)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ImplementReportVO> getImplementList(Map<String, String> paramMap) {
		return (List<ImplementReportVO>)getSqlSession().selectList("implement.getImplementList", paramMap);
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.ea.implement.dao.ImplementDAO#getImplementCount(java.util.Map)
	 */
	@Override
	public int getImplementCount(Map<String, String> paramMap) {
		return (Integer)getSqlSession().selectOne("implement.getImplementCount", paramMap);
	}

}
