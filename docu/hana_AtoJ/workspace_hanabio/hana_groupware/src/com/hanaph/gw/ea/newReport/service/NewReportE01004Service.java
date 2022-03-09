package com.hanaph.gw.ea.newReport.service;

import com.hanaph.gw.ea.newReport.vo.ApprovalMasterVO;
import com.hanaph.gw.ea.newReport.vo.VaporizeVO;

/**
 * <pre>
 * Class Name : NewReportE01004Service.java
 * 설명 : 기화기기안서
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
public interface NewReportE01004Service {

	/**
	 * <pre>
	 * 1. 개요     : 기화기기안서 등록 
	 * 2. 처리내용 : 기화기기안서 등록
	 * </pre>
	 * @Method Name : insertVaporize
	 * @param paramAmVO
	 * @param paramVrVO
	 * @return
	 */
	String insertVaporize(ApprovalMasterVO paramAmVO, VaporizeVO paramVrVO);
	
	/**
	 * <pre>
	 * 1. 개요     : 기화기기안서 상세
	 * 2. 처리내용 : 기화기기안서 상세
	 * </pre>
	 * @Method Name : vaporizeDetail
	 * @param approval_seq
	 * @return
	 */
	VaporizeVO vaporizeDetail(String approval_seq);

	/**
	 * <pre>
	 * 1. 개요     : 기화기기안서 수정
	 * 2. 처리내용 : 기화기기안서 수정
	 * </pre>
	 * @Method Name : updateVaporize
	 * @param paramAmVO
	 * @param paramVrVO
	 */
	void updateVaporize(ApprovalMasterVO paramAmVO, VaporizeVO paramVrVO);

}
