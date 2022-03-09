/**
  * Hana Project
  * Copyright 2014 iRush Co.,
  *
  */
package com.hanaph.gw.ea.newReport.dao;

import java.util.List;

import com.hanaph.gw.ea.newReport.vo.ApprovalMasterVO;
import com.hanaph.gw.ea.newReport.vo.OvertimeVO;

/**
 * <pre>
 * Class Name : NewReportE01009DAO.java
 * 설명 : 시간외근무신청서 DAO
 * 
 * Modification Information
 *    수정일      수정자              수정 내용
 *  ------------ -------------- --------------------------------
 *  2015. 1. 16.      CHOIILJI         
 * </pre>
 * 
 * @version : 1.0
 * @author  : CHOIILJI(choiilji@irush.co.kr)
 * @since   : 2015. 1. 16.
 */
public interface NewReportE01009DAO {

	/**
	 * 
	 * <pre>
	 * 1. 개요     : 시간외근무신청서 등록한다.
	 * 2. 처리내용 : 시간외근무신청서 등록한다.
	 * </pre>
	 * @Method Name : insertOvertime
	 * @param paramAmVO
	 * @param paramOtVO
	 * @return
	 */
	String insertOvertime(ApprovalMasterVO paramAmVO, OvertimeVO paramOtVO);
	
	/**
	 * <pre>
	 * 1. 개요     : 시간외근무신청서 상세정보리스트
	 * 2. 처리내용 : 시간외근무신청서 상세정보리스트
	 * </pre>
	 * @Method Name : overtimeDetailList
	 * @param approval_seq
	 * @return
	 */
	List<OvertimeVO> overtimeDetailList(String approval_seq);
	
	/**
	 * <pre>
	 * 1. 개요     : 시간외근무신청서 수정을 한다. 
	 * 2. 처리내용 : 시간외근무신청서 수정을 한다.
	 * </pre>
	 * @Method Name : updateOvertime
	 * @param paramAmVO
	 * @param paramOtVO
	 * @return
	 */
	void updateOvertime(ApprovalMasterVO paramAmVO, OvertimeVO paramOtVO);
}
