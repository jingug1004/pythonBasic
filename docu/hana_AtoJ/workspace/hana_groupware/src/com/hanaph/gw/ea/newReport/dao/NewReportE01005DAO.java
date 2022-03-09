/**
  * Hana Project
  * Copyright 2014 iRush Co.,
  *
  */
package com.hanaph.gw.ea.newReport.dao;

import java.util.Map;

import com.hanaph.gw.ea.newReport.vo.CommuteVO;


/**
 * <pre>
 * Class Name : NewReportE01005DAO.java
 * 설명 : 근태계
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
public interface NewReportE01005DAO {

	/**
	 * <pre>
	 * 1. 개요     : 근태계 등록 한다.
	 * 2. 처리내용 : 근태계 등록 한다.
	 * </pre>
	 * @Method Name : insertCommute
	 * @param paramAmVO
	 * @param paramCmVO
	 * @return
	 */
	void insertCommute(CommuteVO paramCmVO);

	/**
	 * <pre>
	 * 1. 개요     : 근태계 상세정보 가져온다. 
	 * 2. 처리내용 : 근태계 상세정보 가져온다.
	 * </pre>
	 * @Method Name : commuteDetail
	 * @param approval_seq
	 * @return
	 */
	CommuteVO commuteDetail(String approval_seq);
	
	/**
	 * <pre>
	 * 1. 개요     : 근태계 지각 정보 가져온다. 
	 * 2. 처리내용 : 근태계 지작정보 가져온다.
	 * </pre>
	 * @Method Name : commuteTardy
	 * @param emp_no ,year
	 * @return
	 */
	CommuteVO commuteTardy(Map<String, String> paramMap);

	/**
	 * <pre>
	 * 1. 개요     : 근태계 수정 한다.
	 * 2. 처리내용 : 근태계 수정 한다.
	 * </pre>
	 * @Method Name : commuteDetail
	 * @param paramAmVO
	 * @param paramCmVO
	 */
	void updateCommute(CommuteVO paramCmVO);

}
