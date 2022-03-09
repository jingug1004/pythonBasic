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

import com.hanaph.gw.pe.member.vo.InsuranceVO;

/**
 * <pre>
 * Class Name : InsuranceDAOImpl.java
 * 설명 : 건강보험 연말정산 환급/징수 DAO 구현한 class
 * 
 * Modification Information
 *    수정일      수정자              수정 내용
 *  ------------ -------------- --------------------------------
 *  2014. 10. 30.      CHOIILJI         
 * </pre>
 * 
 * @version : 
 * @author  : CHOIILJI(choiilji@irush.co.kr)
 * @since   : 2014. 10. 30.
 */
@Repository("insuranceDao")
public class InsuranceDAOImpl extends SqlSessionDaoSupport implements
		InsuranceDAO {

	/* (non-Javadoc)
	 * @see com.hanaph.gw.member.dao.InsuranceDAO#getInsuranceList(java.util.Map)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<InsuranceVO> getInsuranceList(Map<String, String> paramMap) {
		return (List<InsuranceVO>)getSqlSession().selectList("member.getInsuranceList", paramMap);
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.member.dao.InsuranceDAO#getInsuranceCount(java.util.Map)
	 */
	@Override
	public int getInsuranceCount(Map<String, String> paramMap) {
		Integer cnt = (Integer)getSqlSession().selectOne("member.getInsuranceCount", paramMap);
		return cnt;
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.member.dao.InsuranceDAO#getInsuranceDetail(java.util.Map)
	 */
	@Override
	public InsuranceVO getInsuranceDetail(Map<String, String> paramMap) {
		return (InsuranceVO)getSqlSession().selectOne("member.getInsuranceDetail", paramMap);
	}
}
