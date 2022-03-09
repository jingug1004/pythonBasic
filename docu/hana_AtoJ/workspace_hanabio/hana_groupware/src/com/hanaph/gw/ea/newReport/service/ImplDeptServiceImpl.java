/**
  * Hana Project
  * Copyright 2014 iRush Co.,
  *
  */
package com.hanaph.gw.ea.newReport.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hanaph.gw.ea.newReport.dao.ImplDeptDAO;
import com.hanaph.gw.ea.newReport.vo.ImplDeptEmpVO;
import com.hanaph.gw.ea.newReport.vo.ImplDeptVO;

/**
 * <pre>
 * Class Name : ImplDeptServiceImpl.java
 * 설명 : 시행부서 ServiceImpl
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
@Service(value="implDeptService")
public class ImplDeptServiceImpl implements ImplDeptService {

	@Autowired
	private ImplDeptDAO implDeptDao;
	
	/* (non-Javadoc)
	 * @see com.hanaph.gw.ea.newReport.service.ImplDeptService#getImplDeptDetailList(java.util.Map)
	 */
	@Override
	public List<ImplDeptVO> getImplDeptDetailList(Map<String, String> paramMap) {
		return implDeptDao.getImplDeptDetailList(paramMap);
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.ea.newReport.service.ImplDeptService#insertImplDept(com.hanaph.gw.ea.newReport.vo.ImplDeptVO)
	 */
	@Override
	public int insertImplDept(ImplDeptVO implDeptVO) {
		return implDeptDao.insertImplDept(implDeptVO);
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.ea.newReport.service.ImplDeptService#deleteImplDept(java.util.Map)
	 */
	@Override
	public int deleteImplDept(Map<String, String> paramMap) {
		return implDeptDao.deleteImplDept(paramMap);
	}

	/*
	 * (non-Javadoc)
	 * @see com.hanaph.gw.ea.newReport.service.ImplDeptService#insertImplDeptAndEmp(com.hanaph.gw.ea.newReport.vo.ImplDeptVO, com.hanaph.gw.ea.newReport.vo.ImplDeptEmpVO)
	 */
	@Override
	public int insertImplDeptAndEmp(ImplDeptVO implDeptVO, ImplDeptEmpVO ImplDeptEmpVO) {
		return implDeptDao.insertImplDeptAndEmp(implDeptVO, ImplDeptEmpVO);
		
	}
}
