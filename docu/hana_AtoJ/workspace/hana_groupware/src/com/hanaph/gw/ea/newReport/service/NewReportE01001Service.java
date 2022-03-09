package com.hanaph.gw.ea.newReport.service;

import com.hanaph.gw.ea.newReport.vo.ApprovalMasterVO;
import com.hanaph.gw.ea.newReport.vo.VacationVO;

/**
 * <pre>
 * Class Name : NewReportE01001Service.java
 * 설명 : 휴가신청서
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
public interface NewReportE01001Service {

	/**
	 * <pre>
	 * 1. 개요     : 휴가신청서 등록 
	 * 2. 처리내용 : 휴가신청서 등록
	 * </pre>
	 * @Method Name : insertVacation
	 * @param paramAmVO
	 * @param paramVcVO
	 * @return
	 */
	String insertVacation(ApprovalMasterVO paramAmVO, VacationVO paramVcVO);

	/**
	 * <pre>
	 * 1. 개요     : 휴가신청서 상세 
	 * 2. 처리내용 : 휴가신청서 상세
	 * </pre>
	 * @Method Name : vacationDetail
	 * @param approval_seq
	 * @return
	 */
	VacationVO vacationDetail(String approval_seq);
	
	/**
	 * <pre>
	 * 1. 개요     : 휴가신청서 수정 
	 * 2. 처리내용 : 휴가신청서 수정
	 * </pre>
	 * @Method Name : updateVacation
	 * @param paramAmVO
	 * @param paramVcVO
	 */
	void updateVacation(ApprovalMasterVO paramAmVO, VacationVO paramVcVO);

}
