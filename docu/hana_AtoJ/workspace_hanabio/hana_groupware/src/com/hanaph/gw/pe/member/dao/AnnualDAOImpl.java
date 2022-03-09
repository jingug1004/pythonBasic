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

import com.hanaph.gw.co.common.utils.StringUtil;
import com.hanaph.gw.pe.member.vo.AnnualHRVO;
import com.hanaph.gw.pe.member.vo.AnnualMgrVO;
import com.hanaph.gw.pe.member.vo.AnnualVO;

/**
 * <pre>
 * Class Name : AnnualDAOImpl.java
 * 설명 : 연차사용내역 정보 DAO 구현한 class
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
@Repository("annualDao")
public class AnnualDAOImpl extends SqlSessionDaoSupport implements AnnualDAO {

	/* (non-Javadoc)
	 * @see com.hanaph.gw.member.dao.AnnualDAO#getAnnualList(java.util.Map)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<AnnualVO> getAnnualList(Map<String, String> paramMap) {
		return (List<AnnualVO>)getSqlSession().selectList("member.getAnnualList", paramMap);
	}

	/*
	 * (non-Javadoc)
	 * @see com.hanaph.gw.member.dao.AnnualDAO#getAnnualCount(java.util.Map)
	 */
	@Override
	public int getAnnualCount(Map<String, String> paramMap) {
		Integer cnt = (Integer)getSqlSession().selectOne("member.getAnnualCount", paramMap);
		return cnt;
	}

	/*
	 * (non-Javadoc)
	 * @see com.hanaph.gw.member.dao.AnnualDAO#getAnnualUsedCount(java.util.Map)
	 */
	@Override
	public float getAnnualUsedCount(Map<String, String> paramMap) {
		float cnt = (Float)getSqlSession().selectOne("member.getAnnualUsedCount", paramMap);
		return cnt;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.hanaph.gw.member.dao.AnnualDAO#getAnnualPlan(java.util.Map)
	 */
	@Override
	public AnnualMgrVO getAnnualPlan(Map<String, String> paramMap) {
		return (AnnualMgrVO)getSqlSession().selectOne("member.getAnnualPlan", paramMap);
	}

	/*
	 * (non-Javadoc)
	 * @see com.hanaph.gw.member.dao.AnnualDAO#getAnnualCommonDT(java.util.Map)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<AnnualVO> getAnnualCommonDT(Map<String, String> paramMap) {
		return (List<AnnualVO>)getSqlSession().selectList("member.getAnnualCommonDT", paramMap);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.hanaph.gw.pe.member.dao.AnnualDAO#getAnnualCommonDTCount(java.util.Map)
	 */
	@Override
	public int getAnnualCommonDTCount(Map<String, String> paramMap) {
		Integer cnt = (Integer)getSqlSession().selectOne("member.getAnnualCommonDTCount", paramMap);
		return cnt;
	}
	
	/*
	 * (non-Javadoc) 
	 * @see com.hanaph.gw.pe.member.dao.AnnualDAO#getAnnualCommonDTCountHalf(java.util.Map)
	 */
	@Override
	public int getAnnualCommonDTCountHalf(Map<String, String> paramMap) {
		Integer cnt = (Integer)getSqlSession().selectOne("member.getAnnualCommonDTCountHalf", paramMap);
		return cnt;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.hanaph.gw.member.dao.AnnualDAO#getAnnualPlanNotify(java.util.Map)
	 */
	@Override
	public AnnualMgrVO getAnnualPlanNotify(Map<String, String> paramMap) {
		return (AnnualMgrVO)getSqlSession().selectOne("member.getAnnualPlanNotify", paramMap);
	}

	/*
	 * (non-Javadoc)
	 * @see com.hanaph.gw.pe.member.dao.AnnualDAO#getAnnualClosedYN(java.util.Map)
	 */
	@Override
	public boolean getAnnualClosedYN(Map<String, String> paramMap) {
		boolean bResult = false;
		String sResult = (String)getSqlSession().selectOne("member.getAnnualClosedYN", paramMap);
		if("Y".equals(StringUtil.nvl(sResult))){
			bResult = true;
		}
		return bResult;
	}

	/*
	 * (non-Javadoc)
	 * @see com.hanaph.gw.pe.member.dao.AnnualDAO#insertAnnualPlan(com.hanaph.gw.pe.member.vo.AnnualHRVO)
	 */
	@Override
	public boolean insertAnnualPlan(AnnualHRVO annualHRVO) {
		boolean bResult = false;
		int iResult = (Integer)getSqlSession().insert("member.insertAnnualPlan", annualHRVO);
		if(iResult>0){
			bResult = true;
		}
		return bResult;
	}

	/*
	 * (non-Javadoc)
	 * @see com.hanaph.gw.pe.member.dao.AnnualDAO#deleteAnnualPlan(com.hanaph.gw.pe.member.vo.AnnualHRVO)
	 */
	@Override
	public boolean deleteAnnualPlan(AnnualHRVO annualHRVO) {
		boolean bResult = false;
		int iResult = (Integer)getSqlSession().insert("member.deleteAnnualPlan", annualHRVO);
		if(iResult>0){
			bResult = true;
		}
		return bResult;
	}

	
	/* (non-Javadoc)
	 * @see com.hanaph.gw.pe.member.dao.AnnualDAO#insertAnnual(com.hanaph.gw.pe.member.vo.AnnualVO)
	 */
	@Override
	public boolean insertAnnual(AnnualVO annualVO) {
		Integer count = getSqlSession().insert("member.insertAnnual", annualVO);
		if(count != 0){
			return true;
		}else{
			return false;
		}
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.pe.member.dao.AnnualDAO#deleteAnnual(com.hanaph.gw.pe.member.vo.AnnualVO)
	 */
	@Override
	public boolean deleteAnnual(AnnualVO annualVO) {
		Integer count = getSqlSession().insert("member.deleteAnnual", annualVO);
		if(count != 0){
			return true;
		}else{
			return false;
		}
	}
}
