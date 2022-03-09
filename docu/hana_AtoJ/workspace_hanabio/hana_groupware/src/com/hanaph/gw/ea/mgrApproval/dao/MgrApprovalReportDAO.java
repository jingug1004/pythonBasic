/**
  * Hana Project
  * Copyright 2014 iRush Co.,
  *
  */
package com.hanaph.gw.ea.mgrApproval.dao;

import java.util.List;
import java.util.Map;

import com.hanaph.gw.ea.mgrApproval.vo.MgrApprovalReportVO;


/**
 * <pre>
 * Class Name : MgrApprovalReportDAO.java
 * 설명 : 관리자 문서 목록 DAO
 * 
 * Modification Information
 *    수정일      수정자              수정 내용
 *  ------------ -------------- --------------------------------
 *  2015. 1. 22.      CHOIILJI         
 * </pre>
 * 
 * @version : 1.0
 * @author  : CHOIILJI(choiilji@irush.co.kr)
 * @since   : 2015. 1. 22.
 */
public interface MgrApprovalReportDAO {

	/**
	 * 
	 * <pre>
	 * 1. 개요     : 관리자 문서 리스트
	 * 2. 처리내용 : 관리자 문서 리스트 가져온다
	 * </pre>
	 * @Method Name : getMgrApprovalReportList
	 * @param paramMap
	 * @return
	 */
	public List<MgrApprovalReportVO> getMgrApprovalReportList(Map<String, String> paramMap);
	
	/**
	 * 
	 * <pre>
	 * 1. 개요     : 관리자 문서 리스트 전체 카운트
	 * 2. 처리내용 : 관리자 문서 리스트 전체 카운트
	 * </pre>
	 * @Method Name : getMgrApprovalReportCount
	 * @param paramMap
	 * @return
	 */
	public int getMgrApprovalReportCount (Map<String, String> paramMap);
}
