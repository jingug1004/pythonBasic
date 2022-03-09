/**
  * Hana Project
  * Copyright 2014 iRush Co.,
  *
  */
package com.hanaph.gw.co.personnel.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hanaph.gw.co.personnel.dao.DepartmentDAO;
import com.hanaph.gw.co.personnel.vo.DepartmentVO;

/**
 * <pre>
 * Class Name : DepartmentServiceImpl.java
 * 설명 : 부서관련 정보 가져오는 Service 구현한 클래스
 * 
 * Modification Information
 *    수정일      수정자              수정 내용
 *  ------------ -------------- --------------------------------
 *  2014. 10. 15.      CHOIILJI         
 * </pre>
 * 
 * @version : 1.0
 * @author  : CHOIILJI(choiilji@irush.co.kr)
 * @since   : 2014. 10. 15.
 */

@Service(value="departmentService")
public class DepartmentServiceImpl implements DepartmentService {

	@Autowired
	private DepartmentDAO departmentDao;
	
	/* (non-Javadoc)
	 * @see com.hanaph.gw.personnel.service.DepartmentService#getDepartmentList(java.util.Map)
	 */
	@Override
	public List<DepartmentVO> getDepartmentList(Map<String, String> paramMap) {
		return departmentDao.getDepartmentList(paramMap);
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.personnel.service.DepartmentService#getSubDepartmentList(java.util.Map)
	 */
	@Override
	public List<DepartmentVO> getDepartmentSubList(Map<String, String> paramMap) {
		return departmentDao.getDepartmentSubList(paramMap);
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.personnel.service.DepartmentService#getDepartmentDetail(java.util.Map)
	 */
	@Override
	public DepartmentVO getDepartmentDetail(Map<String, String> paramMap) {
		return departmentDao.getDepartmentDetail(paramMap);
	}

	/*
	 * (non-Javadoc)
	 * @see com.hanaph.gw.co.personnel.service.DepartmentService#getDepartmentTerminalList(java.util.Map)
	 */
	@Override
	public List<DepartmentVO> getDepartmentTerminalList(Map<String, String> paramMap) {
		return departmentDao.getDepartmentTerminalList(paramMap);
	}

}
