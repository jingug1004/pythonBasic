/**
  * Hana Project
  * Copyright 2014 iRush Co.,
  *
  */
package com.hanaph.gw.ea.newReport.dao;

import com.hanaph.gw.ea.newReport.vo.ReviewVO;


/**
 * <pre>
 * Class Name : NewReportE01014DAO.java
 * 설명 : 기안서 
 * 
 * Modification Information
 *    수정일      수정자              수정 내용
 *  ------------ -------------- --------------------------------
 *  2015. 12. 10.      개발검토서          
 * </pre>
 * 
 * @version : 
 * @author  : 전산팀
 * @since   : 2015. 12. 10.
 */
public interface NewReportE01014DAO {

	/**
	 * <pre>
	 * 1. 개요     : 개발검토서 등록을 한다. 
	 * 2. 처리내용 : 개발검토서 등록을 한다.
	 * </pre>
	 * @Method Name : insertReview
	 * @param paramAmVO
	 * @param paramDfVO
	 * @return
	 */
	void insertReview(ReviewVO paramDfVO);

	/**
	 * <pre>
	 * 1. 개요     : 개발검토서 상세정보를 가져온다. 
	 * 2. 처리내용 : 개발검토서 상세정보를 가져온다.
	 * </pre>
	 * @Method Name : reviewDetail
	 * @param approval_seq
	 * @return
	 */
	ReviewVO reviewDetail(String approval_seq);

	/**
	 * <pre>
	 * 1. 개요     : 개발검토서 수정을 한다.
	 * 2. 처리내용 : 개발검토서 수정을 한다.
	 * </pre>
	 * @Method Name : updateReview
	 * @param paramAmVO
	 * @param paramDfVO
	 */
	void updateReview(ReviewVO paramDfVO);


}
