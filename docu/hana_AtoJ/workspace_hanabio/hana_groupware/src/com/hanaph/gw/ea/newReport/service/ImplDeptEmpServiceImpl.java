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

import com.hanaph.gw.ea.newReport.dao.ImplDeptEmpDAO;
import com.hanaph.gw.ea.newReport.vo.ImplDeptEmpVO;

/**
 * <pre>
 * Class Name : ImplDeptEmpServiceImpl.java
 * 설명 : 문서별 시행부서 직원 ServiceImpl
 * 
 * Modification Information
 *    수정일      수정자              수정 내용
 *  ------------ -------------- --------------------------------
 *  2015. 1. 16.      CHOIILJI         
 * </pre>
 * 
 * @version : 
 * @author  : CHOIILJI(choiilji@irush.co.kr)
 * @since   : 2015. 1. 16.
 */
@Service(value="implDeptEmpService")
public class ImplDeptEmpServiceImpl implements ImplDeptEmpService {

	@Autowired
	private ImplDeptEmpDAO implDeptEmpDao;
	
	/* (non-Javadoc)
	 * @see com.hanaph.gw.ea.newReport.service.ImplDeptEmpService#insertImplDeptEmp(java.util.Map)
	 */
	@Override
	public boolean insertImplDeptEmp(ImplDeptEmpVO implDeptEmpVO) {
		return implDeptEmpDao.insertImplDeptEmp(implDeptEmpVO);
	}


	/*
	 * (non-Javadoc)
	 * @see com.hanaph.gw.ea.newReport.service.ImplDeptEmpService#updateImplDeptEmpReadYN(com.hanaph.gw.ea.newReport.vo.ImplDeptEmpVO)
	 */
	@Override
	public boolean updateImplDeptEmpReadYN(ImplDeptEmpVO implDeptEmpVO) {
		return implDeptEmpDao.updateImplDeptEmpReadYN(implDeptEmpVO);
	}


	/*
	 * (non-Javadoc)
	 * @see com.hanaph.gw.ea.newReport.service.ImplDeptEmpService#getImplDeptEmpDetail(com.hanaph.gw.ea.newReport.vo.ImplDeptEmpVO)
	 */
	@Override
	public ImplDeptEmpVO getImplDeptEmpDetail(Map<String, String> paramMap) {
		return implDeptEmpDao.getImplDeptEmpDetail(paramMap);
	}

	/*
	 * (non-Javadoc)
	 * @see com.hanaph.gw.ea.newReport.service.ImplDeptEmpService#getImplDeptEmpList(java.util.Map)
	 */
	@Override
	public List<ImplDeptEmpVO> getImplDeptEmpList(Map<String, String> paramMap) {
		return implDeptEmpDao.getImplDeptEmpList(paramMap);
	}


	/*
	 * (non-Javadoc)
	 * @see com.hanaph.gw.ea.newReport.service.ImplDeptEmpService#updateImplDeptEmpComplete(com.hanaph.gw.ea.newReport.vo.ImplDeptEmpVO)
	 */
	@Override
	public boolean updateImplDeptEmpComplete(ImplDeptEmpVO implDeptEmpVO) {
		return implDeptEmpDao.updateImplDeptEmpComplete(implDeptEmpVO);
	}

	/*
	 * (non-Javadoc)
	 * @see com.hanaph.gw.ea.newReport.service.ImplDeptEmpService#deleteImplDeptEmpComplete(com.hanaph.gw.ea.newReport.vo.ImplDeptEmpVO)
	 */
	@Override
	public boolean deleteImplDeptEmpComplete(ImplDeptEmpVO implDeptEmpVO) {
		return implDeptEmpDao.deleteImplDeptEmpComplete(implDeptEmpVO);
	}


	/*
	 * (non-Javadoc)
	 * @see com.hanaph.gw.ea.newReport.service.ImplDeptEmpService#insertImplDeptEmpInterestYN(com.hanaph.gw.ea.newReport.vo.ImplDeptEmpVO)
	 */
	@Override
	public boolean insertImplDeptEmpInterestYN(ImplDeptEmpVO implDeptEmpVO) {
		return implDeptEmpDao.insertImplDeptEmpInterestYN(implDeptEmpVO);
	}
}
