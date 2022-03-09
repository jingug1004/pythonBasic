/**
  * Hana Project
  * Copyright 2014 iRush Co.,
  *
  */
package com.hanaph.gw.ea.mgrDocuInfo.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hanaph.gw.ea.mgrDocuInfo.dao.BasicApprovalDAO;
import com.hanaph.gw.ea.mgrDocuInfo.vo.BasicApprovalVO;
import com.hanaph.gw.ea.mgrDocuInfo.vo.BasicImplDeptVO;

/**
 * <pre>
 * Class Name : BasicApprovalServiceImpl.java
 * 설명 : 문서별 기본결재라인 ServiceImpl
 * 
 * Modification Information
 *    수정일      수정자              수정 내용
 *  ------------ -------------- --------------------------------
 *  2014. 12. 22.      CHOIILJI         
 * </pre>
 * 
 * @version : 1.0
 * @author  : CHOIILJI(choiilji@irush.co.kr)
 * @since   : 2014. 12. 22.
 */
@Service(value="basicApprovalService")
public class BasicApprovalServiceImpl implements BasicApprovalService {

	@Autowired
	BasicApprovalDAO basicApprovalDao;
	
	@Autowired
	SqlSessionFactory sqlSessionFactory;
	
	/*
	 * (non-Javadoc)
	 * @see com.hanaph.gw.ea.mgrDocuInfo.service.BasicApprovalService#getBasicApprovalDetailList(java.util.Map)
	 */
	@Override
	public List<BasicApprovalVO> getBasicApprovalDetailList(Map<String, String> paramMap) {
		return basicApprovalDao.getBasicApprovalDetailList(paramMap);
	}

	/*
	 * (non-Javadoc)
	 * @see com.hanaph.gw.ea.mgrDocuInfo.service.BasicApprovalService#insertBasicApprovalAll(com.hanaph.gw.ea.mgrDocuInfo.vo.BasicApprovalVO, com.hanaph.gw.ea.mgrDocuInfo.vo.BasicReferenceVO, com.hanaph.gw.ea.mgrDocuInfo.vo.BasicCooperationVO)
	 */
	@Override
	public int insertBasicApprovalAll(Map<String, String> paramMap, 
			BasicApprovalVO basicApprovalVO, 
			BasicImplDeptVO basicImplDeptVO){
		return basicApprovalDao.insertBasicApprovalAll(paramMap, basicApprovalVO, basicImplDeptVO);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.hanaph.gw.ea.mgrDocuInfo.service.BasicApprovalService#insertBasicApproval(com.hanaph.gw.ea.mgrDocuInfo.vo.BasicApprovalVO)
	 */
	@Override
	public int insertBasicApproval(BasicApprovalVO basicApprovalVO) {
		return basicApprovalDao.insertBasicApproval(basicApprovalVO);
	}

	/*
	 * (non-Javadoc)
	 * @see com.hanaph.gw.ea.mgrDocuInfo.service.BasicApprovalService#deleteBasicApproval(java.util.Map)
	 */
	@Override
	public int deleteBasicApproval(Map<String, String> paramMap) {
		return basicApprovalDao.deleteBasicApproval(paramMap);
	}

	

	
}
