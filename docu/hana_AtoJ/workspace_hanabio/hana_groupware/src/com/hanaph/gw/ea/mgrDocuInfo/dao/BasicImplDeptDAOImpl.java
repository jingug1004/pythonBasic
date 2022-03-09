/**
  * Hana Project
  * Copyright 2014 iRush Co.,
  *
  */
package com.hanaph.gw.ea.mgrDocuInfo.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hanaph.gw.ea.mgrDocuInfo.vo.BasicImplDeptVO;

/**
 * <pre>
 * Class Name : BasicImplDeptDAOImpl.java
 * 설명 : 시햅부서 DAOImpl
 * 
 * Modification Information
 *    수정일      수정자              수정 내용
 *  ------------ -------------- --------------------------------
 *  2015. 1. 8.      CHOIILJI         
 * </pre>
 * 
 * @version : 1.0
 * @author  : CHOIILJI(choiilji@irush.co.kr)
 * @since   : 2015. 1. 8.
 */
@Repository("basicImplDeptDao")
public class BasicImplDeptDAOImpl extends SqlSessionDaoSupport implements
		BasicImplDeptDAO {

	@Autowired
	SqlSessionFactory sqlSessionFactory;
	
	/* (non-Javadoc)
	 * @see com.hanaph.gw.ea.mgrDocuInfo.dao.BasicImplDeptDAO#getBasicImplDeptDetailList(java.util.Map)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<BasicImplDeptVO> getBasicImplDeptDetailList(
			Map<String, String> paramMap) {
		return (List<BasicImplDeptVO>)getSqlSession().selectList("mgrDocuInfo.getBasicImplDeptDetailList", paramMap);
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.ea.mgrDocuInfo.dao.BasicImplDeptDAO#insertBasicImplDept(com.hanaph.gw.ea.mgrDocuInfo.vo.BasicImplDeptVO)
	 */
	@Override
	public int insertBasicImplDept(BasicImplDeptVO basicImplDeptVO) {
		SqlSession sqlBatchSession = sqlSessionFactory.openSession(ExecutorType.BATCH);
		return (Integer)sqlBatchSession.insert("mgrDocuInfo.insertBasicImplDept", basicImplDeptVO);
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.ea.mgrDocuInfo.dao.BasicImplDeptDAO#deleteBasicImplDept(java.util.Map)
	 */
	@Override
	public int deleteBasicImplDept(Map<String, String> paramMap) {
		return (Integer)getSqlSession().delete("mgrDocuInfo.deleteBasicImplDept", paramMap);
	}

}
