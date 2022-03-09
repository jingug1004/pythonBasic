/**
  * Hana Project
  * Copyright 2014 iRush Co.,
  *
  */
package com.hanaph.gw.ea.mgrDocuInfo.service;

import java.util.List;
import java.util.Map;

import com.hanaph.gw.ea.mgrDocuInfo.vo.BasicApprovalVO;
import com.hanaph.gw.ea.mgrDocuInfo.vo.BasicImplDeptVO;

/**
 * <pre>
 * Class Name : BasicApprovalService.java
 * 설명 : 문서별 기본결재라인 Service
 * 
 * Modification Information
 *    수정일      수정자              수정 내용
 *  ------------ -------------- --------------------------------
 *  2014. 12. 22.      CHOIILJI         
 * </pre>
 * 
 * @version : 1.0
 * @author  : CHOIILJI(choiilji@irush.co.kr)
 * @since   : 2014. 12. 22.
 */
public interface BasicApprovalService {
	/**
	 * 
	 * <pre>
	 * 1. 개요     : 문서별 기본결재라인
	 * 2. 처리내용 : 문서별 기본결재라인 가져온다.
	 * </pre>
	 * @Method Name : getBasicApprovalDetailList
	 * @param paramMap
	 * @return
	 */
	public List<BasicApprovalVO> getBasicApprovalDetailList(Map<String, String> paramMap);
	
	/**
	 * 
	 * <pre>
	 * 1. 개요     : 문서별 기본 결재, 시행라인 저장한다.
	 * 2. 처리내용 : 문서별 기본 결재, 시행라인 저장한다.
	 * </pre>
	 * @Method Name : insertBasicApprovalAll
	 * @param paramMap
	 * @param basicApprovalVO
	 * @param basicImplDeptVO
	 * @return
	 */
	public int insertBasicApprovalAll(Map<String, String> paramMap, 
			BasicApprovalVO basicApprovalVO, 
			BasicImplDeptVO basicImplDeptVO);
	
	/**
	 * 
	 * <pre>
	 * 1. 개요     : 문서별 기본결재라인 등록
	 * 2. 처리내용 : 문서별 기본결재라인 등록
	 * </pre> 
	 * @Method Name : insertBasicApproval
	 * @param basicApprovalVO
	 * @return
	 */
	public int insertBasicApproval(BasicApprovalVO basicApprovalVO);
	
	/**
	 * 
	 * <pre>
	 * 1. 개요     : 문서별 기본결재라인 수정
	 * 2. 처리내용 : 문서별 기본결재라인 수정
	 * </pre>
	 * @Method Name : deleteBasicApproval
	 * @param paramMap
	 * @return
	 */
	public int deleteBasicApproval(Map<String, String> paramMap);	
}
