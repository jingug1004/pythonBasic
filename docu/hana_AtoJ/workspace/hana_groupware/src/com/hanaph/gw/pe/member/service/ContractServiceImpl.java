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

import com.hanaph.gw.pe.member.dao.ContractDAO;
import com.hanaph.gw.pe.member.vo.ContractVO;

/**
 * <pre>
 * Class Name : ContractServiceImpl.java
 * 설명 : 연봉계약서 정보 Service 구현한 class
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
@Service
public class ContractServiceImpl implements ContractService {

	@Autowired ContractDAO contractDao;
	
	/* (non-Javadoc)
	 * @see com.hanaph.gw.member.service.ContractService#getContractList(java.util.Map)
	 */
	@Override
	public List<ContractVO> getContractList(Map<String, String> paramMap) {
		return contractDao.getContractList(paramMap);
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.member.service.ContractService#getContractCount(java.util.Map)
	 */
	@Override
	public int getContractCount(Map<String, String> paramMap) {
		return contractDao.getContractCount(paramMap);
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.member.service.ContractService#getContractDetail(java.util.Map)
	 */
	@Override
	public ContractVO getContractDetail(Map<String, String> paramMap) {
		return contractDao.getContractDetail(paramMap);
	}

}
