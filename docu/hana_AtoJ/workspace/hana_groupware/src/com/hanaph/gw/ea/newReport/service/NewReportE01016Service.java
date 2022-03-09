package com.hanaph.gw.ea.newReport.service;

import java.util.List;
import java.util.Map;

import com.hanaph.gw.ea.newReport.vo.ApprovalMasterVO;
import com.hanaph.gw.ea.newReport.vo.DeliveryVO;

/**
 * <pre>
 * Class Name : NewReportE01016Service.java
 * 설명 : 기안서
 * 
 * Modification Information
 *    수정일      수정자              수정 내용
 *  ------------ -------------- --------------------------------
 *  2016. 01. 25.      원부자재 납품확인서           
 * </pre>
 * 
 * @version : 
 * @author  : 전산팀
 * @since   : 2016. 01. 25.
 */
public interface NewReportE01016Service {

	/**
	 * <pre>
	 * 1. 개요     : 원부자재 납품확인서 등록  
	 * 2. 처리내용 : 원부자재 납품확인서 등록
	 * </pre>
	 * @Method Name : insertDelivery
	 * @param paramAmVO
	 * @param paramDlVO
	 * @return
	 */
	String insertDelivery(ApprovalMasterVO paramAmVO, DeliveryVO paramDlVO);
	
	/**
	 * <pre>
	 * 1. 개요     : 원부자재 납품확인서 상세정보 
	 * 2. 처리내용 : 원부자재 납품확인서 상세정보
	 * </pre>
	 * @Method Name : deliveryDetail
	 * @param approval_seq
	 * @return
	 */
	List<DeliveryVO> deliveryDetail(String approval_seq);

	/**
	 * <pre>
	 * 1. 개요     : 원부자재 납품확인서 수정
	 * 2. 처리내용 : 원부자재 납품확인서 수정
	 * </pre>
	 * @Method Name : updateDelivery
	 * @param paramAmVO
	 * @param paramDlVO
	 */
	void updateDelivery(ApprovalMasterVO paramAmVO, DeliveryVO paramDlVO);
	
	/**
	 * <pre>
	 * 1. 개요     : 원부자재 납품확인서 전표번호 조회 
	 * 2. 처리내용 : 원부자재 납품확인서 전표번호 조회
	 * </pre>
	 * @Method Name : searchSlipNo
	 * @param search_ymd
	 * @return
	 */
	List<DeliveryVO> searchSlipNo(String search_ymd);

	/**
	 * <pre>
	 * 1. 개요     : 원부자재 납품확인서 전표자료 조회
	 * 2. 처리내용 : 원부자재 납품확인서 전표자료 조회
	 * </pre>
	 * @Method Name : searchSlipData
	 * @param search_no
	 * @param 
	 */
	List<DeliveryVO> searchSlipData(String search_no);
		
}
