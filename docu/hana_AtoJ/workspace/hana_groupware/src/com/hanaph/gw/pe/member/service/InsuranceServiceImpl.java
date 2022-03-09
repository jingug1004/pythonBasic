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

import com.hanaph.gw.pe.member.dao.InsuranceDAO;
import com.hanaph.gw.pe.member.vo.InsuranceVO;

/**
 * <pre>
 * Class Name : InsuranceServiceImpl.java
 * 설명 : 건강보험 연말정산 환급/징수 Service 구현한 class
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
public class InsuranceServiceImpl implements InsuranceService {

	@Autowired
	InsuranceDAO insuranceDao;
	
	/* (non-Javadoc)
	 * @see com.hanaph.gw.member.service.InsuranceService#getInsuranceList(java.util.Map)
	 */
	@Override
	public List<InsuranceVO> getInsuranceList(Map<String, String> paramMap) {
		return insuranceDao.getInsuranceList(paramMap);
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.member.service.InsuranceService#getInsuranceCount(java.util.Map)
	 */
	@Override
	public int getInsuranceCount(Map<String, String> paramMap) {
		return insuranceDao.getInsuranceCount(paramMap);
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.member.service.InsuranceService#getInsuranceDetail(java.util.Map)
	 */
	@Override
	public InsuranceVO getInsuranceDetail(Map<String, String> paramMap) {
		return insuranceDao.getInsuranceDetail(paramMap);
	}

}
