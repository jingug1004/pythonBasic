package com.hanaph.gw.ea.newReport.service;

import com.hanaph.gw.ea.newReport.vo.AccidentVO;
import com.hanaph.gw.ea.newReport.vo.ApprovalMasterVO;

/**
 * <pre>
 * Class Name : NewReportE01001Service.java
 * 설명 : 사고보고서
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
public interface NewReportE01011Service {

	/**
	 * <pre>
	 * 1. 개요     : 사고보고서 등록 
	 * 2. 처리내용 : 사고보고서 등록
	 * </pre>
	 * @Method Name : insertAccident
	 * @param paramAmVO
	 * @param paramAdVO
	 * @return
	 */
	String insertAccident(ApprovalMasterVO paramAmVO, AccidentVO paramAdVO);
	
	/**
	 * 
	 * <pre>
	 * 1. 개요     : 사고보고서 상세 
	 * 2. 처리내용 : 사고보고서 상세
	 * </pre>
	 * @Method Name : accidentDetail
	 * @param approval_seq
	 * @return
	 */
	AccidentVO accidentDetail(String approval_seq);

	/**
	 * <pre>
	 * 1. 개요     : 사고보고서 수정
	 * 2. 처리내용 : 사고보고서 수정
	 * </pre>
	 * @Method Name : updateAccident
	 * @param paramAmVO
	 * @param paramAdVO
	 */
	void updateAccident(ApprovalMasterVO paramAmVO, AccidentVO paramAdVO);

}
