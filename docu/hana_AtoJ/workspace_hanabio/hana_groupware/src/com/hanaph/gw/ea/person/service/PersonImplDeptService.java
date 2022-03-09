/**
  * Hana Project
  * Copyright 2014 iRush Co.,
  *
  */
package com.hanaph.gw.ea.person.service;

import java.util.List;
import java.util.Map;

import com.hanaph.gw.ea.person.vo.PersonImplDeptVO;

/**
 * <pre>
 * Class Name : PersonImplDeptService.java
 * 설명 : 개인 시행부서 Service
 * 
 * Modification Information
 *    수정일      수정자              수정 내용
 *  ------------ -------------- --------------------------------
 *  2015. 1. 8.      CHOIILJI         
 * </pre>
 * 
 * @version : 1.0
 * @author  : CHOIILJI(choiilji@irush.co.kr)
 * @since   : 2015. 1. 8.
 */
public interface PersonImplDeptService {
	/**
	 * 
	 * <pre>
	 * 1. 개요     : 개인시행부서
	 * 2. 처리내용 : 개인시행부서 가져온다.
	 * </pre>
	 * @Method Name : getPersonImplDeptDetailList
	 * @param paramMap
	 * @return
	 */
	public List<PersonImplDeptVO> getPersonImplDeptDetailList(Map<String, String> paramMap);
	
	/**
	 * 
	 * <pre>
	 * 1. 개요     : 개인시행부서 등록
	 * 2. 처리내용 : 개인시행부서 등록
	 * </pre>
	 * @Method Name : insertPersonImplDept
	 * @param personImplDeptVO
	 * @return
	 */
	public int insertPersonImplDept(PersonImplDeptVO personImplDeptVO);
	
	/**
	 * 
	 * <pre>
	 * 1. 개요     : 개인시행부서 삭제
	 * 2. 처리내용 : 개인시행부서 삭제
	 * </pre>
	 * @Method Name : deletePersonImplDept
	 * @param paramMap
	 * @return
	 */
	public int deletePersonImplDept(Map<String, String> paramMap);
}
