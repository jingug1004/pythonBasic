/**
  * Hana Project
  * Copyright 2014 iRush Co.,
  *
  */
package com.hanaph.gw.ea.person.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hanaph.gw.ea.person.vo.PersonLineVO;

/**
 * <pre>
 * Class Name : PersonLineDAOImpl.java
 * 설명 : 개인결재라인 Master DAOImpl
 *  
 * Modification Information
 *    수정일      수정자              수정 내용
 *  ------------ -------------- --------------------------------
 *  2014. 12. 30.      CHOIILJI         
 * </pre>
 * 
 * @version : 1.0
 * @author  : CHOIILJI(choiilji@irush.co.kr)
 * @since   : 2014. 12. 30.
 */
@Repository("personLineDao")
public class PersonLineDAOImpl extends SqlSessionDaoSupport implements
		PersonLineDAO {
	@Autowired
	SqlSessionFactory sqlSessionFactory;
	
	/* (non-Javadoc)
	 * @see com.hanaph.gw.ea.person.dao.PersonLineDAO#getPersonLineList(java.util.Map)
	 */
	@SuppressWarnings("unchecked")
	
	public List<PersonLineVO> getPersonLineList(Map<String, String> paramMap) {
		return (List<PersonLineVO>)getSqlSession().selectList("person.getPersonLineList", paramMap);
	}

	/*
	 * (non-Javadoc)
	 * @see com.hanaph.gw.ea.person.dao.PersonLineDAO#deletePersonLine(java.util.Map)
	 */
	@Override
	public int deletePersonLine(Map<String, String> paramMap) {
		return (Integer)getSqlSession().delete("person.deletePersonLine", paramMap);
	}
}
