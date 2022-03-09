/**
  * Hana Project
  * Copyright 2014 iRush Co.,
  *
  */
package com.hanaph.gw.co.personnel.service;

import java.util.List;
import java.util.Map;

import com.hanaph.gw.co.personnel.vo.DepartmentVO;


/**
 * <pre>
 * Class Name : DepartmentService.java
 * 설명 : 부서관련 정보 가져오는 Service
 * 
 * Modification Information
 *    수정일      수정자              수정 내용
 *  ------------ -------------- --------------------------------
 *  2014. 10. 15.      CHOIILJI         
 * </pre>
 * 
 * @version : 1.0
 * @author  : CHOIILJI(choiilji@irush.co.kr)
 * @since   : 2014. 10. 15.
 */
public interface DepartmentService {
	
	/**
	 * 
	 * <pre>
	 * 1. 개요     : 조직정보 리스트
	 * 2. 처리내용 : 조직정보 리스트 가져온다.
	 * </pre>
	 * @Method Name : getDepartmentList
	 * @param paramMap
	 * @return
	 */
	public List<DepartmentVO> getDepartmentList(Map<String, String> paramMap);
	
	/**
	 * 
	 * <pre>
	 * 1. 개요     : 하위 조직정보
	 * 2. 처리내용 : 하위 조직정보 가져온다.
	 * </pre>
	 * @Method Name : getSubDepartmentList
	 * @param paramMap
	 * @return
	 */
	public List<DepartmentVO> getDepartmentSubList(Map<String, String> paramMap);
	
	/**
	 * 
	 * <pre>
	 * 1. 개요     : 조직상세정보
	 * 2. 처리내용 : 조직상세정보 가져온다.
	 * </pre>
	 * @Method Name : getDepartmentDetail
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	public DepartmentVO getDepartmentDetail(Map<String, String> paramMap);	

	/**
	 * 
	 * <pre>
	 * 1. 개요     : 조직정보 터미널 리스트
	 * 2. 처리내용 : 조직정보 터미널 리스트 가져온다.
	 * </pre>
	 * @Method Name : getDepartmentTerminalList
	 * @param paramMap
	 * @return
	 */
	public List<DepartmentVO> getDepartmentTerminalList(Map<String, String> paramMap);
	
}
