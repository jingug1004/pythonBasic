/**
  * Hana Project
  * Copyright 2014 iRush Co.,
  *
  */
package com.hanaph.gw.ea.mgrDocuInfo.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hanaph.gw.ea.mgrDocuInfo.dao.BasicImplDeptDAO;
import com.hanaph.gw.ea.mgrDocuInfo.vo.BasicImplDeptVO;

/**
 * <pre>
 * Class Name : BasicImplDeptServiceImpl.java
 * 설명 : 시햅부서 ServiceImpl
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
@Service(value="basicImplDeptService")
public class BasicImplDeptServiceImpl implements BasicImplDeptService {

	@Autowired
	private BasicImplDeptDAO basicImplDeptDao;
	
	/* (non-Javadoc)
	 * @see com.hanaph.gw.ea.mgrDocuInfo.service.BasicImplDeptService#getBasicImplDeptList(java.util.Map)
	 */
	@Override
	public List<BasicImplDeptVO> getBasicImplDeptDetailList(Map<String, String> paramMap) {
		return basicImplDeptDao.getBasicImplDeptDetailList(paramMap);
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.ea.mgrDocuInfo.service.BasicImplDeptService#insertBasicImplDept(com.hanaph.gw.ea.mgrDocuInfo.vo.BasicImplDeptVO)
	 */
	@Override
	public int insertBasicImplDept(BasicImplDeptVO basicImplDeptVO) {
		return basicImplDeptDao.insertBasicImplDept(basicImplDeptVO);
	}

	/*
	 * (non-Javadoc)
	 * @see com.hanaph.gw.ea.mgrDocuInfo.service.BasicImplDeptService#deleteBasicImplDept(java.util.Map)
	 */
	@Override
	public int deleteBasicImplDept(Map<String, String> paramMap) {
		return basicImplDeptDao.deleteBasicImplDept(paramMap);
	}

}
