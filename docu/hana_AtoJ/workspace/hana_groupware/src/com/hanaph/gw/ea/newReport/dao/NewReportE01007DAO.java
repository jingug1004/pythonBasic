/**
  * Hana Project
  * Copyright 2014 iRush Co.,
  *
  */
package com.hanaph.gw.ea.newReport.dao;

import java.util.List;

import com.hanaph.gw.ea.newReport.vo.ApprovalMasterVO;
import com.hanaph.gw.ea.newReport.vo.SampleVO;

/**
 * <pre>
 * Class Name : NewReportE01007DAO.java
 * 설명 : 샘플신청서 DAO
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
public interface NewReportE01007DAO {

	/**
	 * 
	 * <pre>
	 * 1. 개요     : 샘플신청서 등록한다.
	 * 2. 처리내용 : 샘플신청서 등록한다.
	 * </pre>
	 * @Method Name : insertSample
	 * @param paramAmVO
	 * @param paramSpVO
	 * @return
	 */
	String insertSample(ApprovalMasterVO paramAmVO, SampleVO paramSpVO);
	
	/**
	 * 
	 * <pre>
	 * 1. 개요     : 샘플신청서 상세정보리스트
	 * 2. 처리내용 : 샘플신청서 상세정보리스트
	 * </pre>
	 * @Method Name : sampleDetailList
	 * @param approval_seq
	 * @return
	 */
	List<SampleVO> sampleDetailList(String approval_seq);
	
	/**
	 * <pre>
	 * 1. 개요     : 샘플신청서 수정을 한다. 
	 * 2. 처리내용 : 샘플신청서 수정을 한다.
	 * </pre>
	 * @Method Name : updateSample
	 * @param paramAmVO
	 * @param paramSpVO
	 * @return
	 */
	void updateSample(ApprovalMasterVO paramAmVO, SampleVO paramSpVO);
}
