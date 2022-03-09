/**
  * Hana Project
  * Copyright 2014 iRush Co.,
  *
  */
package com.hanaph.gw.pe.member.dao;

import java.util.List;
import java.util.Map;

import com.hanaph.gw.pe.member.vo.InsuranceVO;

/**
 * <pre>
 * Class Name : InsuranceDAO.java
 * 설명 : 건강보험 연말정산 환급/징수 DAO interface
 * 
 * Modification Information
 *    수정일      수정자              수정 내용
 *  ------------ -------------- --------------------------------
 *  2014. 10. 30.      CHOIILJI         
 * </pre>
 * 
 * @version : 
 * @author  : CHOIILJI(choiilji@irush.co.kr)
 * @since   : 2014. 10. 30.
 */
public interface InsuranceDAO {
	/**
	 * 
	 * <pre>
	 * 1. 개요     : 건강보험 리스트
	 * 2. 처리내용 : 건강보험 리스트 가져온다.
	 * </pre>
	 * @Method Name : getInsuranceList
	 * @param paramMap
	 * @return
	 */
	public List<InsuranceVO> getInsuranceList(Map<String, String> paramMap);
	
	/**
	 * 
	 * <pre>
	 * 1. 개요     : 건강보험 리스트 전체 카운트
	 * 2. 처리내용 : 건강보험 리스트 전체 카운트
	 * </pre>
	 * @Method Name : getInsuranceCount
	 * @param paramMap
	 * @return
	 */
	public int getInsuranceCount (Map<String, String> paramMap);
	
	/**
	 * 
	 * <pre>
	 * 1. 개요     : 급여상세 정보
	 * 2. 처리내용 : 급여상세 정보 가져온다.
	 * </pre>
	 * @Method Name : getInsuranceDetail
	 * @param paramMap
	 * @return
	 */
	public InsuranceVO getInsuranceDetail(Map<String, String> paramMap);
}
