/**
  * Hana Project
  * Copyright 2014 iRush Co.,
  *
  */
package com.hanaph.gw.ea.receive.service;

import java.util.List;
import java.util.Map;

import com.hanaph.gw.ea.newReport.vo.ApprovalMasterVO;
import com.hanaph.gw.ea.newReport.vo.ApprovalVO;
import com.hanaph.gw.ea.newReport.vo.ImplDeptEmpVO;
import com.hanaph.gw.ea.receive.vo.ReceiveVO;
import com.hanaph.gw.pe.member.vo.AnnualVO;

/**
 * <pre>
 * Class Name : ReceiveService.java
 * 설명 : 
 * 
 * Modification Information
 *    수정일      수정자              수정 내용
 *  ------------ -------------- --------------------------------
 *  2015. 1. 19.      Jung.Jin.Muk          
 * </pre>
 * 
 * @version : 
 * @author  : Jung.Jin.Muk(pc123pc@irush.co.kr)
 * @since   : 2015. 1. 19.
 */
public interface ReceiveService {

	/**
	 * <pre>
	 * 1. 개요     : 내가받은문서 카운트 
	 * 2. 처리내용 : 내가받은문서 총 갯수를 가져온다.
	 * </pre>
	 * @Method Name : getReceiveCount
	 * @param paramMap
	 * @return
	 */
	public int getReceiveCount(Map<String, String> paramMap);

	/**
	 * <pre>
	 * 1. 개요     : 내가받은문서 리스트
	 * 2. 처리내용 : 내가받은문서 리스트를 가져온다.
	 * </pre>
	 * @Method Name : getReceiveList
	 * @param paramMap
	 * @return
	 */
	public List<ReceiveVO> getReceiveList(Map<String, String> paramMap);

	/**
	 * <pre>
	 * 1. 개요     : 결재 승인,반려
	 * 2. 처리내용 : 결재 승인,반려를 한다.
	 * </pre>
	 * @Method Name : updateApproval
	 * @param map
	 * @return
	 */
	public void updateApproval(ApprovalMasterVO approvalMasterVO, ReceiveVO receiveVO, ImplDeptEmpVO implDeptEmpVO, AnnualVO annualVO);

	/**
	 * <pre>
	 * 1. 개요     : 시행부서 의견 필수
	 * 2. 처리내용 : 시행부서 의견 필수를 선택하면 바로 전 결재 승인에 대하여 취소 승인
	 * </pre>
	 * @Method Name : updateApprovalMustOpinion
	 * @param map
	 * @return
	 */
	public void updateApprovalMustOpinion(ApprovalMasterVO approvalMasterVO, ReceiveVO receiveVO, ImplDeptEmpVO implDeptEmpVO, AnnualVO annualVO);

	/**
	 * <pre>
	 * 1. 개요     : 일괄결재 
	 * 2. 처리내용 : 일괄결재를 한다.
	 * </pre>
	 * @Method Name : updateApproval
	 * @param map
	 * @return
	 */
	public void updateAllApproval(ApprovalMasterVO approvalMasterVO, ReceiveVO receiveVO);

	/**
	 * <pre>
	 * 1. 개요     : 열람여부 상세정보
	 * 2. 처리내용 : 열람여부 상세정보를 가져온다.
	 * </pre>
	 * @Method Name : getReceiveReadYnDetail
	 * @param paramMap
	 * @return
	 */
	public ApprovalVO getReceiveReadYnDetail(Map<String, String> paramMap);

	/**
	 * <pre>
	 * 1. 개요     : 열람여부 업데이트 
	 * 2. 처리내용 : 열람여부 업데이트를 한다.
	 * </pre>
	 * @Method Name : updateReceiveReadYn
	 * @param receiveVO
	 */
	public void updateReceiveReadYn(ApprovalVO approvalVO);

	/**
	 * <pre>
	 * 1. 개요     : 내가받은문서 메인 카운트
	 * 2. 처리내용 : 내가받은문서 메인 카운트
	 * </pre>
	 * @Method Name : getMainReceiveCnt
	 * @param paramMap
	 * @return
	 */
	public List<ApprovalMasterVO> getMainReceiveCnt(Map<String, String> paramMap);

	/**
	 * <pre>
	 * 1. 개요     : 내가받은 문서 장기미결재 카운트
	 * 2. 처리내용 : 내가받은 문서 장기미결재 카운트
	 * </pre>
	 * @Method Name : getLongTermReceiveCount
	 * @param paramMap
	 * @return
	 */
	public int getLongTermReceiveCount(Map<String, String> paramMap);
}
