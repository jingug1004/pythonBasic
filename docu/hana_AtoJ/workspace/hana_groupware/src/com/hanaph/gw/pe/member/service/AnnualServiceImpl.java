/**
  * Hana Project
  * Copyright 2014 iRush Co.,
  *
  */
package com.hanaph.gw.pe.member.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hanaph.gw.pe.member.dao.AnnualDAO;
import com.hanaph.gw.pe.member.vo.AnnualHRVO;
import com.hanaph.gw.pe.member.vo.AnnualMgrVO;
import com.hanaph.gw.pe.member.vo.AnnualVO;

/**
 * <pre>
 * Class Name : AnnualServiceImpl.java
 * 설명 : 연차사용내역 정보 Service 구현한 class
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
@Service
public class AnnualServiceImpl implements AnnualService {

	@Autowired
	AnnualDAO annualDAO;
	/* (non-Javadoc)
	 * @see com.hanaph.gw.member.service.AnnualService#getAnnualList(java.util.Map)
	 */
	@Override
	public List<AnnualVO> getAnnualList(Map<String, String> paramMap) {
		return annualDAO.getAnnualList(paramMap);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.hanaph.gw.member.service.AnnualService#getAnnualCount(java.util.Map)
	 */
	@Override
	public int getAnnualCount(Map<String, String> paramMap) {
		return annualDAO.getAnnualCount(paramMap);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.hanaph.gw.member.service.AnnualService#getAnnualUsedCount(java.util.Map)
	 */
	@Override
	public float getAnnualUsedCount(Map<String, String> paramMap) {
		return annualDAO.getAnnualUsedCount(paramMap);
	}

	/*
	 * (non-Javadoc)
	 * @see com.hanaph.gw.member.service.AnnualService#getAnnualPlan(java.util.Map)
	 */
	@Override
	public AnnualMgrVO getAnnualPlan(Map<String, String> paramMap) {
		return annualDAO.getAnnualPlan(paramMap);
	}

	/*
	 * (non-Javadoc)
	 * @see com.hanaph.gw.member.service.AnnualService#getAnnualCommonDT(java.util.Map)
	 */
	@Override
	public List<AnnualVO> getAnnualCommonDT(Map<String, String> paramMap) {
		return annualDAO.getAnnualCommonDT(paramMap);
	}
	
	
	/*
	 * (non-Javadoc)
	 * @see com.hanaph.gw.pe.member.service.AnnualService#getAnnualCommonDTCount(java.util.Map)
	 */
	@Override
	public int getAnnualCommonDTCount(Map<String, String> paramMap) {
		return annualDAO.getAnnualCommonDTCount(paramMap);
	}
	
	/*
	 * (non-Javadoc) 
	 * @see com.hanaph.gw.member.service.AnnualService#getAnnualCommonDTCountHalf(java.util.Map)
	 */
	@Override
	public int getAnnualCommonDTCountHalf(Map<String, String> paramMap) {
		return annualDAO.getAnnualCommonDTCountHalf(paramMap);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.hanaph.gw.member.service.AnnualService#getAnnualPlanNotify(java.util.Map)
	 */
	@Override
	public AnnualMgrVO getAnnualPlanNotify(Map<String, String> paramMap) {
		return annualDAO.getAnnualPlanNotify(paramMap);
	}

	/*
	 * (non-Javadoc)
	 * @see com.hanaph.gw.pe.member.service.AnnualService#getAnnualClosedYN(java.util.Map)
	 */
	@Override
	public boolean getAnnualClosedYN(Map<String, String> paramMap) {
		return annualDAO.getAnnualClosedYN(paramMap);
	}

	/*
	 * (non-Javadoc)
	 * @see com.hanaph.gw.pe.member.service.AnnualService#insertAnnualPlan(com.hanaph.gw.pe.member.vo.AnnualHRVO)
	 */
	@Override
	public boolean insertAnnualPlan(AnnualHRVO annualHRVO) {
		return annualDAO.insertAnnualPlan(annualHRVO);
	}

	/*
	 * (non-Javadoc)
	 * @see com.hanaph.gw.pe.member.service.AnnualService#deleteAnnualPlan(com.hanaph.gw.pe.member.vo.AnnualHRVO)
	 */
	@Override
	public boolean deleteAnnualPlan(AnnualHRVO annualHRVO) {
		return annualDAO.deleteAnnualPlan(annualHRVO);
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.pe.member.service.AnnualService#insertAnnual(com.hanaph.gw.pe.member.vo.AnnualVO)
	 */
	@Override
	public boolean insertAnnual(AnnualVO annualVO) {
		// TODO Auto-generated method stub
		return annualDAO.insertAnnual(annualVO);
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.pe.member.service.AnnualService#deleteAnnual(com.hanaph.gw.pe.member.vo.AnnualVO)
	 */
	@Override
	public boolean deleteAnnual(AnnualVO annualVO) {
		return annualDAO.deleteAnnual(annualVO);
	}
}
