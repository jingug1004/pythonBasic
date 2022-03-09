/**
  * Hana Project
  * Copyright 2014 iRush Co.,
  *
  */
package com.hanaph.gw.ea.newReport.dao;

import com.hanaph.gw.ea.newReport.vo.DraftVO;


/**
 * <pre>
 * Class Name : NewReportE01002DAO.java
 * 설명 : 기안서 
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
public interface NewReportE01002DAO {

	/**
	 * <pre>
	 * 1. 개요     : 기안서 등록을 한다. 
	 * 2. 처리내용 : 기안서 등록을 한다.
	 * </pre>
	 * @Method Name : insertDraft
	 * @param paramAmVO
	 * @param paramDfVO
	 * @return
	 */
	void insertDraft(DraftVO paramDfVO);

	/**
	 * <pre>
	 * 1. 개요     : 기안서 상세정보를 가져온다. 
	 * 2. 처리내용 : 기안서 상세정보를 가져온다.
	 * </pre>
	 * @Method Name : draftDetail
	 * @param approval_seq
	 * @return
	 */
	DraftVO draftDetail(String approval_seq);

	/**
	 * <pre>
	 * 1. 개요     : 기안서 수정을 한다.
	 * 2. 처리내용 : 기안서 수정을 한다.
	 * </pre>
	 * @Method Name : updateDraft
	 * @param paramAmVO
	 * @param paramDfVO
	 */
	void updateDraft(DraftVO paramDfVO);


}
