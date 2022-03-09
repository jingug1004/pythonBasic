/**
  * Hana Project
  * Copyright 2014 iRush Co.,
  *
  */
package com.hanaph.gw.ea.newReport.dao;

import com.hanaph.gw.ea.newReport.vo.VacationVO;


/**
 * <pre>
 * Class Name : NewReportE01001DAO.java
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
public interface NewReportE01001DAO {

	/**
	 * <pre>
	 * 1. 개요     : 휴가신청서를 등록 한다.
	 * 2. 처리내용 : 휴가신청서를 등록 한다.
	 * </pre>
	 * @Method Name : insertVacation
	 * @param paramAmVO
	 * @param paramVcVO
	 * @return
	 */
	void insertVacation(VacationVO paramVcVO);

	/**
	 * 
	 * <pre>
	 * 1. 개요     : 휴가신청서 상세 정보를 가져온다. 
	 * 2. 처리내용 : 휴가신청서 상세 정보를 가져온다.
	 * </pre>
	 * @Method Name : vacationDetail
	 * @param approval_seq
	 * @return
	 */
	VacationVO vacationDetail(String approval_seq);
	
	/**
	 * <pre>
	 * 1. 개요     : 휴가신청서 수정 한다.
	 * 2. 처리내용 : 휴가신청서 수정 한다.
	 * </pre>
	 * @Method Name : updateVacation
	 * @param paramAmVO
	 * @param paramVcVO
	 */
	void updateVacation(VacationVO paramVcVO);

}
