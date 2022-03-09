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

import com.hanaph.gw.pe.member.vo.ContractVO;

/**
 * <pre>
 * Class Name : ContractDAOImpl.java
 * 설명 : 연봉계약서 정보 DAO 구현한 class
 * 
 * Modification Information
 *    수정일      수정자              수정 내용
 *  ------------ -------------- --------------------------------
 *  2014. 11. 4.      CHOIILJI         
 * </pre>
 * 
 * @version : 
 * @author  : CHOIILJI(choiilji@irush.co.kr)
 * @since   : 2014. 11. 4.
 */
@Repository("contractDao")
public class ContractDAOImpl extends SqlSessionDaoSupport implements
		ContractDAO {

	/* (non-Javadoc)
	 * @see com.hanaph.gw.member.dao.ContractDAO#getContractList(java.util.Map)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ContractVO> getContractList(Map<String, String> paramMap) {
		return (List<ContractVO>)getSqlSession().selectList("member.getContractList", paramMap);
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.member.dao.ContractDAO#getContractCount(java.util.Map)
	 */
	@Override
	public int getContractCount(Map<String, String> paramMap) {
		Integer cnt = (Integer)getSqlSession().selectOne("member.getContractCount", paramMap);
		return cnt;
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.member.dao.ContractDAO#getContractDetail(java.util.Map)
	 */
	@Override
	public ContractVO getContractDetail(Map<String, String> paramMap) {
		return (ContractVO)getSqlSession().selectOne("member.getContractDetail", paramMap);
	}

}
