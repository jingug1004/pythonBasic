/**
  * Hana Project
  * Copyright 2014 iRush Co.,
  *
  */
package com.hanaph.gw.ea.person.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hanaph.gw.ea.person.dao.PersonImplDeptDAO;
import com.hanaph.gw.ea.person.vo.PersonImplDeptVO;

/**
 * <pre>
 * Class Name : PersonImplDeptServiceImpl.java
 * 설명 : 개인 시행부서 ServiceImpl
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
@Service(value="personImplDeptService")
public class PersonImplDeptServiceImpl implements PersonImplDeptService {

	@Autowired
	private PersonImplDeptDAO personImplDeptDao;
	/* (non-Javadoc)
	 * @see com.hanaph.gw.ea.person.service.PersonImplDeptService#getPersonImplDeptDetailList(java.util.Map)
	 */
	@Override
	public List<PersonImplDeptVO> getPersonImplDeptDetailList(
			Map<String, String> paramMap) {
		return personImplDeptDao.getPersonImplDeptDetailList(paramMap);
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.ea.person.service.PersonImplDeptService#insertPersonImplDept(com.hanaph.gw.ea.person.vo.PersonImplDeptVO)
	 */
	@Override
	public int insertPersonImplDept(PersonImplDeptVO personImplDeptVO) {
		return personImplDeptDao.insertPersonImplDept(personImplDeptVO);
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.ea.person.service.PersonImplDeptService#deletePersonImplDept(java.util.Map)
	 */
	@Override
	public int deletePersonImplDept(Map<String, String> paramMap) {
		return personImplDeptDao.deletePersonImplDept(paramMap);
	}

}
