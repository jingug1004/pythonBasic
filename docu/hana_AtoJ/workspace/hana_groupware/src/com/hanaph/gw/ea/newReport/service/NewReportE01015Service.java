package com.hanaph.gw.ea.newReport.service;

import java.util.List;

import com.hanaph.gw.ea.newReport.vo.ApprovalMasterVO;
import com.hanaph.gw.ea.newReport.vo.RequisitionVO;

/**
 * <pre>
 * Class Name : NewReportE01015Service.java
 * 설명 : 기안서
 * 
 * Modification Information
 *    수정일      수정자              수정 내용
 *  ------------ -------------- --------------------------------
 *  2015. 12. 11.      물품 구입 청구서           
 * </pre>
 * 
 * @version : 
 * @author  : 전산팀
 * @since   : 2015. 12. 11.
 */
public interface NewReportE01015Service {

	/**
	 * <pre>
	 * 1. 개요     : 물품 구입 청구서 등록  
	 * 2. 처리내용 : 물품 구입 청구서 등록
	 * </pre>
	 * @Method Name : insertRequisition
	 * @param paramAmVO
	 * @param paramRqVO
	 * @return
	 */
	String insertRequisition(ApprovalMasterVO paramAmVO, RequisitionVO paramRqVO);
	
	/**
	 * <pre>
	 * 1. 개요     : 물품 구입 청구서 상세정보 
	 * 2. 처리내용 : 물품 구입 청구서 상세정보
	 * </pre>
	 * @Method Name : requsitionDetail
	 * @param approval_seq
	 * @return
	 */
	List<RequisitionVO> requisitionDetail(String approval_seq);

	/**
	 * <pre>
	 * 1. 개요     : 물품 구입 청구서 수정
	 * 2. 처리내용 : 물품 구입 청구서 수정
	 * </pre>
	 * @Method Name : updateRequisition
	 * @param paramAmVO
	 * @param paramRqVO
	 */
	void updateRequisition(ApprovalMasterVO paramAmVO, RequisitionVO paramRqVO);
	
	/**
	 * <pre>
	 * 1. 개요     : 물품 구입 청구서 청구번호 조회 
	 * 2. 처리내용 : 물품 구입 청구서 청구번호 조회
	 * </pre>
	 * @Method Name : searchReqNo
	 * @param search_req_ymd
	 * @return
	 */
	List<RequisitionVO> searchReqNo(String search_req_ymd);

	/**
	 * <pre>
	 * 1. 개요     : 물품 구입 청구서 청구자료 조회
	 * 2. 처리내용 : 물품 구입 청구서 청구자료 조회
	 * </pre>
	 * @Method Name : searchReqData
	 * @param search_req_no
	 * @param 
	 */
	List<RequisitionVO> searchReqData(String search_req_no);
		
}
