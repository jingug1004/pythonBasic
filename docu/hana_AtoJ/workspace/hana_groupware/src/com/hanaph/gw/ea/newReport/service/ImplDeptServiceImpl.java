/**
  * Hana Project
  * Copyright 2014 iRush Co.,
  *
  */
package com.hanaph.gw.ea.newReport.service;

import com.hanaph.gw.ea.newReport.dao.ImplDeptDAO;
import com.hanaph.gw.ea.newReport.vo.ImplDeptEmpVO;
import com.hanaph.gw.ea.newReport.vo.ImplDeptVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

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
	 * @see com.hanaph.gw.ea.newReport.service.ImplDeptService#getImplDeptDetailList(java.util.Map)
	 * ml180122.ml05_T44 김진국 - ImplDeptServiceImpl.java에 getImplDeptDetailListMustOpinion 구현 메서드 추가 - 시의필 실행하면 시행부서와 시행부서의 소속 사원 결과값을 가져와서 쪽지 보내서 알림 기능
	 */
	@Override
	public List<String> getImplDeptDetailListMustOpinion(String approval_seq) {
		return implDeptDao.getImplDeptDetailListMustOpinion(approval_seq);
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
