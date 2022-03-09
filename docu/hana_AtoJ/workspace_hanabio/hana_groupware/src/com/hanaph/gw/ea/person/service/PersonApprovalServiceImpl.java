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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.hanaph.gw.ea.person.dao.PersonApprovalDAO;
import com.hanaph.gw.ea.person.dao.PersonImplDeptDAO;
import com.hanaph.gw.ea.person.dao.PersonLineDAO;
import com.hanaph.gw.ea.person.vo.PersonApprovalVO;
import com.hanaph.gw.ea.person.vo.PersonImplDeptVO;
import com.hanaph.gw.ea.person.vo.PersonLineVO;

/**
 * <pre>
 * Class Name : PersonApprovalServiceImpl.java
 * 설명 : 개인결재라인 ServiceImpl
 * 
 * Modification Information
 *    수정일      수정자              수정 내용
 *  ------------ -------------- --------------------------------
 *  2014. 12. 30.      CHOIILJI         
 * </pre>
 * 
 * @version : 1.0
 * @author  : CHOIILJI(choiilji@irush.co.kr)
 * @since   : 2014. 12. 30.
 */
@Service(value="personApprovalService")
public class PersonApprovalServiceImpl implements PersonApprovalService {

	@Autowired
	private PersonApprovalDAO personApprovalDao;
	
	@Autowired
	private PersonImplDeptDAO personImplDeptDao;
	
	@Autowired
	private PersonLineDAO personLineDao;
	
	/* (non-Javadoc)
	 * @see com.hanaph.gw.ea.person.service.PersonApprovalService#getPersonApprovalDetailList(java.util.Map)
	 */
	@Override
	public List<PersonApprovalVO> getPersonApprovalDetailList(
			Map<String, String> paramMap) {
		return personApprovalDao.getPersonApprovalDetailList(paramMap);
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.ea.person.service.PersonApprovalService#insertPersonApprovalAll(java.util.Map, java.util.List, java.util.List, java.util.List)
	 */
	@Override
	public int insertPersonApprovalAll(Map<String, String> paramMap, PersonLineVO personLineVO,
			PersonApprovalVO personApprovalVO,
			PersonImplDeptVO personImplDeptVO) {
		return personApprovalDao.insertPersonApprovalAll(paramMap, personLineVO, personApprovalVO, personImplDeptVO);
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.ea.person.service.PersonApprovalService#insertPersonApproval(com.hanaph.gw.ea.person.vo.PersonApprovalVO)
	 */
	@Override
	public int insertPersonApproval(PersonApprovalVO personApprovalVO) {
		return personApprovalDao.insertPersonApproval(personApprovalVO);
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.ea.person.service.PersonApprovalService#deletePersonApproval(java.util.Map)
	 */
	@Override
	public int deletePersonApproval(Map<String, String> paramMap) {
		return personImplDeptDao.deletePersonImplDept(paramMap);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.hanaph.gw.ea.person.service.PersonApprovalService#deletePersonApprovalAll(java.util.Map)
	 */
	@Override
	@Transactional
	public int deletePersonApprovalAll(Map<String, String> paramMap) {
		try{
			//개인라인
			personLineDao.deletePersonLine(paramMap);
			//결재라인
			personApprovalDao.deletePersonApproval(paramMap);
			//시행라인
			personImplDeptDao.deletePersonImplDept(paramMap);
			return 1;
		}catch(Exception ex){
			ex.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return 0;
		}finally{
		}
	}

}
