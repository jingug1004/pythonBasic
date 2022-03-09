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

import com.hanaph.gw.pe.member.dao.SalaryDAO;
import com.hanaph.gw.pe.member.vo.SalaryVO;

/**
 * <pre>
 * Class Name : SalaryServiceImpl.java
 * 설명 : 급여 정보 Service 구현한 class
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
@Service(value="salaryService")
public class SalaryServiceImpl implements SalaryService {

	@Autowired
	SalaryDAO salaryDAO;
	
	/* (non-Javadoc)
	 * @see com.hanaph.gw.member.service.SalaryService#getSalaryList(java.util.Map)
	 */
	@Override
	public List<SalaryVO> getSalaryList(Map<String, String> paramMap) {
		return salaryDAO.getSalaryList(paramMap);
	}

	/*
	 * (non-Javadoc)
	 * @see com.hanaph.gw.member.service.SalaryService#getSalaryCount(java.util.Map)
	 */
	@Override
	public int getSalaryCount(Map<String, String> paramMap) {
		return salaryDAO.getSalaryCount(paramMap);
	}
	
	/* (non-Javadoc)
	 * @see com.hanaph.gw.member.service.SalaryService#getSalaryDetail(java.util.Map)
	 */
	@Override
	public SalaryVO getSalaryDetail(Map<String, String> paramMap) {
		return salaryDAO.getSalaryDetail(paramMap);
	}

	

}
