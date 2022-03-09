package com.hanaph.gw.ea.newReport.service;

import com.hanaph.gw.ea.newReport.vo.ApprovalMasterVO;
import com.hanaph.gw.ea.newReport.vo.ParticipationVO;

/**
 * <pre>
 * Class Name : NewReportE01006Service.java
 * 설명 : 참가신청서
 * 
 * Modification Information
 *    수정일      수정자              수정 내용
 *  ------------ -------------- --------------------------------
 *  2015. 1. 16.      Jung.Jin.Muk          
 * </pre>
 * 
 * @version : 
 * @author  : Jung.Jin.Muk(pc123pc@irush.co.kr)
 * @since   : 2015. 1. 16.
 */
public interface NewReportE01006Service {

	/**
	 * <pre>
	 * 1. 개요     : 참가신청서 등록 
	 * 2. 처리내용 : 참가신청서 등록
	 * </pre>
	 * @Method Name : insertParticipation
	 * @param paramAmVO
	 * @param paramPpVO
	 * @return
	 */
	String insertParticipation(ApprovalMasterVO paramAmVO, ParticipationVO paramPpVO);
	
	/**
	 * <pre>
	 * 1. 개요     : 참가신청서 상세
	 * 2. 처리내용 : 참가신청서 상세
	 * </pre>
	 * @Method Name : participationDetail
	 * @param approval_seq
	 * @return
	 */
	ParticipationVO participationDetail(String approval_seq);

	/**
	 * <pre>
	 * 1. 개요     : 참가신청서 수정 
	 * 2. 처리내용 : 참가신청서 수정
	 * </pre>
	 * @Method Name : updateParticipation
	 * @param paramAmVO
	 * @param paramPpVO
	 */
	void updateParticipation(ApprovalMasterVO paramAmVO, ParticipationVO paramPpVO);

}
