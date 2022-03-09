/**
  * Hana Project
  * Copyright 2014 iRush Co.,
  *
  */
package com.hanaph.gw.ea.newReport.dao;

import java.util.List;
import java.util.Map;

import com.hanaph.gw.ea.newReport.vo.ApprovalMasterVO;
import com.hanaph.gw.ea.newReport.vo.ApprovalVO;
import com.hanaph.gw.ea.newReport.vo.ImplDeptVO;

/**
 * <pre>
 * Class Name : ApprovalDAO.java
 * 설명 : step2.결재라인지정 DAO
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
public interface ApprovalDAO {
	
	/**
	 * 
	 * <pre>
	 * 1. 개요     : 문서별 결재라인
	 * 2. 처리내용 : 문서별 결재라인 가져온다.
	 * </pre>
	 * @Method Name : getApprovalDetailList
	 * @param paramMap
	 * @return
	 */
	public List<ApprovalVO> getApprovalDetailList(Map<String, String> paramMap);
	
	/**
	 * 
	 * <pre>
	 * 1. 개요     : 결재라인 저장
	 * 2. 처리내용 : 결재라인 stet2 저장한다.
	 * </pre>
	 * @Method Name : insertApprovalAll
	 * @param paramMap
	 * @param approvalVOList
	 * @param approvalMasterVO
	 * @return
	 */
	public int insertApprovalAll(Map<String, String> paramMap, 
			List<ApprovalVO> approvalVOList, 
			List<ImplDeptVO> implDeptVOList,ApprovalMasterVO approvalMasterVO);
	
	/**
	 * 
	 * <pre>
	 * 1. 개요     : 문서별 결재라인 등록
	 * 2. 처리내용 : 문서별 결재라인 등록
	 * </pre> 
	 * @Method Name : insertApproval
	 * @param approvalVO
	 * @return
	 */
	public int insertApproval(ApprovalVO approvalVO);
	
	/**
	 * 
	 * <pre>
	 * 1. 개요     : 문서별 결재라인 삭제
	 * 2. 처리내용 : 문서별 결재라인 삭제
	 * </pre>
	 * @Method Name : deleteApproval
	 * @param paramMap
	 * @return
	 */
	public int deleteApproval(Map<String, String> paramMap);

	/**
	 * <pre>
	 * 1. 개요     : 내 결재 차례
	 * 2. 처리내용 : 내 결재 차례를 가져온다.
	 * </pre>
	 * @Method Name : getApprovalNowEmpNo
	 * @param paramMap
	 * @return
	 */
	public ApprovalVO getApprovalNowEmpNo(Map<String, String> paramMap);

	/**
	 * <pre>
	 * 1. 개요     : 이전 결재자 체크
	 * 2. 처리내용 : 이전 결재자 체크
	 * </pre>
	 * @Method Name : getApprovalPrevCheckEmpNo
	 * @param paramMap
	 * @return
	 */
	public ApprovalVO getApprovalPrevCheckEmpNo(Map<String, String> paramMap);	
}
