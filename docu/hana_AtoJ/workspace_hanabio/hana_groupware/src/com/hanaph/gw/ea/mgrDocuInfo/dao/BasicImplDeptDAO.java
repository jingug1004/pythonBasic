/**
  * Hana Project
  * Copyright 2014 iRush Co.,
  *
  */
package com.hanaph.gw.ea.mgrDocuInfo.dao;

import java.util.List;
import java.util.Map;

import com.hanaph.gw.ea.mgrDocuInfo.vo.BasicImplDeptVO;

/**
 * <pre>
 * Class Name : BasicImplDeptDAO.java
 * 설명 : 시햅부서 DAO
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
public interface BasicImplDeptDAO {
	/**
	 * 
	 * <pre>
	 * 1. 개요     : 문서별 기본시행라인
	 * 2. 처리내용 : 문서별 기본시행라인 가져온다.
	 * </pre>
	 * @Method Name : getBasicImplDeptDetailList
	 * @param paramMap
	 * @return
	 */
	public List<BasicImplDeptVO> getBasicImplDeptDetailList(Map<String, String> paramMap);
	
	/**
	 * 
	 * <pre>
	 * 1. 개요     : 문서별 기본시행라인 등록
	 * 2. 처리내용 : 문서별 기본시행라인 등록
	 * </pre>
	 * @Method Name : insertBasicImplDept
	 * @param basicImplDeptVO
	 * @return
	 */
	public int insertBasicImplDept(BasicImplDeptVO basicImplDeptVO);
	
	/**
	 * 
	 * <pre>
	 * 1. 개요     : 문서별 기본시행라인 삭제
	 * 2. 처리내용 : 문서별 기본시행라인 삭제
	 * </pre>
	 * @Method Name : deleteBasicImplDept
	 * @param paramMap
	 * @return
	 */
	public int deleteBasicImplDept(Map<String, String> paramMap);
}
