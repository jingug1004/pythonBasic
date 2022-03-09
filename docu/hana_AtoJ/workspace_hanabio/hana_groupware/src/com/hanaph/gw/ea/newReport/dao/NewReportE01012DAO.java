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
 * Class Name : NewReportE01012DAO.java
 * 설명 : 시간외근무내역서 DAO
 * 
 * Modification Information
 *    수정일      수정자              수정 내용
 *  ------------ -------------- --------------------------------
 *  2015. 1. 16.      CHOIILJI         
 * </pre>
 * 
 * @version : 
 * @author  : CHOIILJI(choiilji@irush.co.kr)
 * @since   : 2015. 1. 16.
 */
public interface NewReportE01012DAO {

	/**
	 * 
	 * <pre>
	 * 1. 개요     : 시간외근무내역서 등록한다.
	 * 2. 처리내용 : 시간외근무내역서 등록한다. (시간외근무내역서만 정보 저장시 시간외근무신청서 결재 라인을 저장한다.)
	 * </pre>
	 * @Method Name : insertOvertimeDetail
	 * @param paramAmVO
	 * @param paramOtVO
	 * @return
	 */
	String insertOvertimeDetail(ApprovalMasterVO paramAmVO, OvertimeVO paramOtVO);
	
	/**
	 * <pre>
	 * 1. 개요     : 시간외근무내역서 상세정보리스트
	 * 2. 처리내용 : 시간외근무내역서 상세정보리스트
	 * </pre>
	 * @Method Name : overtimeDetailDetailList
	 * @param approval_seq
	 * @return
	 */
	List<OvertimeVO> overtimeDetailDetailList(String approval_seq);
	
	/**
	 * <pre>
	 * 1. 개요     : 시간외근무신청서 approval_seq 를 가진 시간외 근무 내역서 조회
	 * 2. 처리내용 : 시간외근무신청서 approval_seq 를 가진 시간외 근무 내역서 조회
	 * </pre>
	 * @Method Name : overtimeExist
	 * @param approval_seq_old
	 * @return
	 */
	boolean overtimeExist(String approval_seq_old);
	
	/**
	 * <pre>
	 * 1. 개요     : 시간외근무내역서 수정을 한다. 
	 * 2. 처리내용 : 시간외근무내역서 수정을 한다.
	 * </pre>
	 * @Method Name : updateOvertime
	 * @param paramAmVO
	 * @param paramOtVO
	 * @return
	 */
	void updateOvertimeDetail(ApprovalMasterVO paramAmVO, OvertimeVO paramOtVO);
}
