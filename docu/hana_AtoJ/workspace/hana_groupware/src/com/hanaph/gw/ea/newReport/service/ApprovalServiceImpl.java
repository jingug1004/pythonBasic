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

import com.hanaph.gw.ea.newReport.dao.ApprovalDAO;
import com.hanaph.gw.ea.newReport.vo.ApprovalMasterVO;
import com.hanaph.gw.ea.newReport.vo.ApprovalVO;
import com.hanaph.gw.ea.newReport.vo.ImplDeptVO;

/**
 * <pre>
 * Class Name : ApprovalServiceImpl.java
 * 설명 : step2.결재라인지정 ServiceImpl
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
@Service(value="approvalService")
public class ApprovalServiceImpl implements ApprovalService {

	@Autowired
	private ApprovalDAO approvalDao;
	
	/* (non-Javadoc)
	 * @see com.hanaph.gw.ea.newReport.service.ApprovalService#getApprovalDetailList(java.util.Map)
	 */
	@Override
	public List<ApprovalVO> getApprovalDetailList(Map<String, String> paramMap) {
		return approvalDao.getApprovalDetailList(paramMap);
	}

	/*
	 * (non-Javadoc)
	 * @see com.hanaph.gw.ea.newReport.service.ApprovalService#insertApprovalAll(java.util.Map, java.util.List, java.util.List, java.util.List, com.hanaph.gw.ea.newReport.vo.ApprovalMasterVO)
	 */
	@Override
	public int insertApprovalAll(Map<String, String> paramMap,
			List<ApprovalVO> approvalVOList,
			List<ImplDeptVO> implDeptVOList,
			ApprovalMasterVO approvalMasterVO) {
		return approvalDao.insertApprovalAll(paramMap, approvalVOList, implDeptVOList, approvalMasterVO);
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.ea.newReport.service.ApprovalService#insertApproval(com.hanaph.gw.ea.newReport.vo.ApprovalVO)
	 */
	@Override
	public int insertApproval(ApprovalVO approvalVO) {
		return approvalDao.insertApproval(approvalVO);
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.ea.newReport.service.ApprovalService#deleteApproval(java.util.Map)
	 */
	@Override
	public int deleteApproval(Map<String, String> paramMap) {
		return approvalDao.deleteApproval(paramMap);
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.ea.newReport.service.ApprovalService#getApprovalNowEmpNo(java.util.Map)
	 */
	@Override
	public ApprovalVO getApprovalNowEmpNo(Map<String, String> paramMap) {
		return approvalDao.getApprovalNowEmpNo(paramMap);

	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.ea.newReport.service.ApprovalService#getApprovalPrevCheckEmpNo(java.util.Map)
	 */
	@Override
	public ApprovalVO getApprovalPrevCheckEmpNo(Map<String, String> paramMap) {
		return approvalDao.getApprovalPrevCheckEmpNo(paramMap);

	}

}
