/**
  * Hana Project
  * Copyright 2014 iRush Co.,
  *
  */
package com.hanaph.gw.ea.receive.dao;

import java.util.List;
import java.util.Map;

import com.hanaph.gw.ea.newReport.vo.ApprovalMasterVO;
import com.hanaph.gw.ea.newReport.vo.ApprovalVO;
import com.hanaph.gw.ea.receive.vo.ReceiveVO;

/**
 * <pre>
 * Class Name : ReceiveDAO.java
 * 설명 : 
 * 
 * Modification Information
 *    수정일      수정자              수정 내용
 *  ------------ -------------- --------------------------------
 *  2015. 1. 20.      Jung.Jin.Muk          
 * </pre>
 * 
 * @version : 
 * @author  : Jung.Jin.Muk(pc123pc@irush.co.kr)
 * @since   : 2015. 1. 20.
 */
public interface ReceiveDAO {

	/**
	 * <pre>
	 * 1. 개요     : 내가받은문서 카운트 
	 * 2. 처리내용 : 내가받은문서 갯수를 가져온다.
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
	 * 1. 개요     : 일괄 결재
	 * 2. 처리내용 : 일괄 결재 한다.
	 * </pre>
	 * @Method Name : updateApproval
	 * @param approvalMasterVO
	 */
	public void updateApproval(ReceiveVO receiveVO);

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
	 * 1. 개요     : 결재 순서
	 * 2. 처리내용 : 결재자가 몇명인지 내가 몇번째 결재순서 인지 가져온다.
	 * </pre>
	 * @Method Name : getApprovalOrdering
	 * @param string
	 * @return 
	 */
	public ReceiveVO getApprovalOrdering(ReceiveVO receiveVO);
	
	/**
	 * <pre>
	 * 1. 개요     : 	내가받은문서 메인 카운트
	 * 2. 처리내용 : 내가받은문서 메인 카운트
	 * </pre>
	 * @Method Name : getMainReceiveCnt
	 * @param paramMap
	 * @return
	 */
	public List<ApprovalMasterVO> getMainReceiveCnt(Map<String, String> paramMap);

	/**
	 * <pre>
	 * 1. 개요     : 내가받은문서 장기미결재 카운트
	 * 2. 처리내용 : 내가받은문서 장기미결재 카운트
	 * </pre>
	 * @Method Name : getLongTermReceiveCount
	 * @param paramMap
	 * @return
	 */
	public int getLongTermReceiveCount(Map<String, String> paramMap);
}
