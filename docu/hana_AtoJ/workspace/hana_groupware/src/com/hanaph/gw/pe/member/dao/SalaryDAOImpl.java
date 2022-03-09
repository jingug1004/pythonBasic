/**
  * Hana Project
  * Copyright 2014 iRush Co.,
  *
  */
package com.hanaph.gw.pe.member.dao;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

import com.hanaph.gw.pe.member.vo.SalaryVO;

/**
 * <pre>
 * Class Name : SalaryDAOImpl.java
 * 설명 : 급여 정보 DAO 구현한 class
 * 
 * Modification Information
 *    수정일      수정자              수정 내용
 *  ------------ -------------- --------------------------------
 *  2014. 10. 29.      CHOIILJI         
 * </pre>
 * 
 * @version : 
 * @author  : CHOIILJI(choiilji@irush.co.kr)
 * @since   : 2014. 10. 29.
 */
@Repository("salaryDao")
public class SalaryDAOImpl extends SqlSessionDaoSupport implements SalaryDAO {

	/* (non-Javadoc)
	 * @see com.hanaph.gw.member.dao.SalaryDAO#getSalaryList(java.util.Map)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<SalaryVO> getSalaryList(Map<String, String> paramMap) {
		return (List<SalaryVO>)getSqlSession().selectList("member.getSalaryList", paramMap);
	}

	/*
	 * (non-Javadoc)
	 * @see com.hanaph.gw.member.dao.SalaryDAO#getSalaryCount(java.util.Map)
	 */
	@Override
	public int getSalaryCount(Map<String, String> paramMap) {
		Integer cnt = (Integer)getSqlSession().selectOne("member.getSalaryCount", paramMap);
		return cnt;
	}
	
	/* (non-Javadoc)
	 * @see com.hanaph.gw.member.dao.SalaryDAO#getSalaryDetail(java.util.Map)
	 */
	@Override
	public SalaryVO getSalaryDetail(Map<String, String> paramMap) {
		return (SalaryVO)getSqlSession().selectOne("member.getSalaryDetail", paramMap);
	}
}
