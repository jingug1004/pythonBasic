/**
  * Hana Project
  * Copyright 2014 iRush Co.,
  *
  */
package com.hanaph.gw.pe.member.service;

import java.util.List;
import java.util.Map;

import com.hanaph.gw.pe.member.vo.SalaryVO;

/**
 * <pre>
 * Class Name : SalaryService.java
 * 설명 : 급여 정보 Service interface
 * 
 * Modification Information
 *    수정일      수정자              수정 내용
 *  ------------ -------------- --------------------------------
 *  2014. 10. 29.      CHOIILJI         
 * </pre>
 * 
 * @version : 
 * @author  : CHOIILJI(choiilji@irush.co.kr)
 * @since   : 2014. 10. 29.
 */
public interface SalaryService {
	/**
	 * 
	 * <pre>
	 * 1. 개요     : 급여 리스트
	 * 2. 처리내용 : 급여 리스트 가져온다.
	 * </pre>
	 * @Method Name : getSalaryList
	 * @param paramMap
	 * @return
	 */
	public List<SalaryVO> getSalaryList(Map<String, String> paramMap);
	
	/**
	 * 
	 * <pre>
	 * 1. 개요     : 급여 리스트 전체 카운트
	 * 2. 처리내용 : 급여 리스트 전체 카운트
	 * </pre>
	 * @Method Name : getSalaryCount
	 * @param paramMap
	 * @return
	 */
	public int getSalaryCount (Map<String, String> paramMap);
	
	/**
	 * 
	 * <pre>
	 * 1. 개요     : 급여상세 정보
	 * 2. 처리내용 : 급여상세 정보 가져온다.
	 * </pre>
	 * @Method Name : getSalaryDetail
	 * @param paramMap
	 * @return
	 */
	public SalaryVO getSalaryDetail(Map<String, String> paramMap);
}
