package com.hanaph.gw.ea.newReport.service;

import com.hanaph.gw.ea.newReport.vo.ApprovalMasterVO;
import com.hanaph.gw.ea.newReport.vo.ReviewVO;

/**
 * <pre>
 * Class Name : NewReportE01014Service.java
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
public interface NewReportE01014Service {

	/**
	 * <pre>
	 * 1. 개요     : 개발검토서 등록  
	 * 2. 처리내용 : 개발검토서 등록
	 * </pre>
	 * @Method Name : insertDraft
	 * @param paramAmVO
	 * @param paramDfVO
	 * @return
	 */
	String insertReview(ApprovalMasterVO paramAmVO, ReviewVO paramDfVO);
	
	/**
	 * <pre>
	 * 1. 개요     : 개발검토서 상세정보 
	 * 2. 처리내용 : 개발검토서 상세정보
	 * </pre>
	 * @Method Name : reviewDetail
	 * @param approval_seq
	 * @return
	 */
	ReviewVO reviewDetail(String approval_seq);

	/**
	 * <pre>
	 * 1. 개요     : 개발검토서 수정
	 * 2. 처리내용 : 개발검토서 수정
	 * </pre>
	 * @Method Name : updateReview
	 * @param paramAmVO
	 * @param paramDfVO
	 */
	void updateReview(ApprovalMasterVO paramAmVO, ReviewVO paramDfVO);

}
