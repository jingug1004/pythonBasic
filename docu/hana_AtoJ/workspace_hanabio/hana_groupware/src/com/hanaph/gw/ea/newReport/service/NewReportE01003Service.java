package com.hanaph.gw.ea.newReport.service;

import com.hanaph.gw.ea.newReport.vo.ApprovalMasterVO;
import com.hanaph.gw.ea.newReport.vo.IncompanyVO;

/**
 * <pre>
 * Class Name : NewReportE01003Service.java
 * 설명 : 사내통신
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
public interface NewReportE01003Service {

	/**
	 * <pre>
	 * 1. 개요     : 사내통신 등록 
	 * 2. 처리내용 : 사내통신 등록
	 * </pre>
	 * @Method Name : insertIncompany
	 * @param paramAmVO
	 * @param paramIcVO
	 * @return
	 */
	String insertIncompany(ApprovalMasterVO paramAmVO, IncompanyVO paramIcVO);
	
	/**
	 * <pre>
	 * 1. 개요     : 사내통신 상세 
	 * 2. 처리내용 :
	 * </pre>
	 * @Method Name : incompanyDetail
	 * @param approval_seq
	 * @return
	 */
	IncompanyVO incompanyDetail(String approval_seq);

	/**
	 * 
	 * <pre>
	 * 1. 개요     : 사내통신 수정 
	 * 2. 처리내용 : 사내통신 수정
	 * </pre>
	 * @Method Name : updateIncompany
	 * @param paramAmVO
	 * @param paramIcVO
	 */
	void updateIncompany(ApprovalMasterVO paramAmVO, IncompanyVO paramIcVO);

}
