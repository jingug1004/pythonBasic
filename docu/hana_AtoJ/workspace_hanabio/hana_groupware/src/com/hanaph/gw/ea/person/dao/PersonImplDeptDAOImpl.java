/**
  * Hana Project
  * Copyright 2014 iRush Co.,
  *
  */
package com.hanaph.gw.ea.person.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hanaph.gw.ea.person.vo.PersonImplDeptVO;

/**
 * <pre>
 * Class Name : PersonImplDeptDAOImpl.java
 * 설명 : 개인 시행부서 DAOImpl
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
@Repository("personImplDeptDao")
public class PersonImplDeptDAOImpl extends SqlSessionDaoSupport implements
		PersonImplDeptDAO {

	@Autowired
	SqlSessionFactory sqlSessionFactory;
	
	/* (non-Javadoc)
	 * @see com.hanaph.gw.ea.person.dao.PersonImplDeptDAO#getPersonImplDeptDetailList(java.util.Map)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PersonImplDeptVO> getPersonImplDeptDetailList(
			Map<String, String> paramMap) {
		return (List<PersonImplDeptVO>)getSqlSession().selectList("person.getPersonImplDeptDetailList", paramMap);
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.ea.person.dao.PersonImplDeptDAO#insertPersonImplDept(com.hanaph.gw.ea.person.vo.PersonImplDeptVO)
	 */
	@Override
	public int insertPersonImplDept(PersonImplDeptVO personImplDeptVO) {
		SqlSession sqlBatchSession = sqlSessionFactory.openSession(ExecutorType.BATCH);
		return (Integer)sqlBatchSession.insert("person.insertPersonImplDept", personImplDeptVO);
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.ea.person.dao.PersonImplDeptDAO#deletePersonImplDept(java.util.Map)
	 */
	@Override
	public int deletePersonImplDept(Map<String, String> paramMap) {
		return (Integer)getSqlSession().delete("person.deletePersonImplDept", paramMap);
	}

}
