/**
  * Hana Project
  * Copyright 2014 iRush Co.,
  *
  */
package com.hanaph.gw.ea.newReport.dao;

import com.hanaph.gw.ea.newReport.vo.ParticipationVO;


/**
 * <pre>
 * Class Name : NewReportE01006DAO.java
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
public interface NewReportE01006DAO {

	/**
	 * <pre>
	 * 1. 개요     : 참가신청서를 등록 한다.
	 * 2. 처리내용 : 참가신청서를 등록 한다.
	 * </pre>
	 * @Method Name : insertParticipation
	 * @param paramAmVO
	 * @param paramPpVO
	 * @return
	 */
	void insertParticipation(ParticipationVO paramPpVO);

	/**
	 * <pre>
	 * 1. 개요     : 참가신청서 상세정보를 가져온다.
	 * 2. 처리내용 : 참가신청서 상세정보를 가져온다.
	 * </pre>
	 * @Method Name : participationDetail
	 * @param approval_seq
	 * @return
	 */
	ParticipationVO participationDetail(String approval_seq);

	/**
	 * <pre>
	 * 1. 개요     : 참가신청서 수정을 한다. 
	 * 2. 처리내용 : 참가신청서 수정을 한다.
	 * </pre>
	 * @Method Name : updateParticipation
	 * @param paramAmVO
	 * @param paramPpVO
	 */
	void updateParticipation(ParticipationVO paramPpVO);

}
