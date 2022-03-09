/**
  * Hana Project
  * Copyright 2014 iRush Co.,
  *
  */
package com.hanaph.gw.ea.newReport.service;

import java.util.List;

import com.hanaph.gw.ea.newReport.vo.ApprovalMasterVO;
import com.hanaph.gw.ea.newReport.vo.CertificateVO;

/**
 * <pre>
 * Class Name : NewReportE01010Service.java
 * 설명 : 증명서발급신청서 Service
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
public interface NewReportE01010Service {

	/**
	 * 
	 * <pre>
	 * 1. 개요     : 증명서발급신청서 등록한다.
	 * 2. 처리내용 : 증명서발급신청서 등록한다.
	 * </pre>
	 * @Method Name : insertCertificate
	 * @param paramAmVO
	 * @param paramCfcVO
	 * @return
	 */
	String insertCertificate(ApprovalMasterVO paramAmVO, CertificateVO paramCfcVO);

	
	/**
	 * <pre>
	 * 1. 개요     : 증명서발급신청서 상세정보리스트
	 * 2. 처리내용 : 증명서발급신청서 상세정보리스트
	 * </pre>
	 * @Method Name : certificateDetailList
	 * @param approval_seq
	 * @return
	 */
	List<CertificateVO> certificateDetailList(String approval_seq);
	
	/**
	 * <pre>
	 * 1. 개요     : 증명서발급신청서 수정 
	 * 2. 처리내용 : 증명서발급신청서 수정
	 * </pre>
	 * @Method Name : updateCertificate
	 * @param paramAmVO
	 * @param paramCfcVO
	 * @return
	 */
	void updateCertificate(ApprovalMasterVO paramAmVO, CertificateVO paramCfcVO);

}
