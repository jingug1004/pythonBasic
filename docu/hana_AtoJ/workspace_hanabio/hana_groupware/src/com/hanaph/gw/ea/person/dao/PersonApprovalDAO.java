/**
  * Hana Project
  * Copyright 2014 iRush Co.,
  *
  */
package com.hanaph.gw.ea.person.dao;

import java.util.List;
import java.util.Map;

import com.hanaph.gw.ea.person.vo.PersonApprovalVO;
import com.hanaph.gw.ea.person.vo.PersonImplDeptVO;
import com.hanaph.gw.ea.person.vo.PersonLineVO;

/**
 * <pre>
 * Class Name : PersonApprovalDAO.java
 * 설명 : 개인결재라인 DAO
 * 
 * Modification Information
 *    수정일      수정자              수정 내용
 *  ------------ -------------- --------------------------------
 *  2014. 12. 30.      CHOIILJI         
 * </pre>
 * 
 * @version : 1.0
 * @author  : CHOIILJI(choiilji@irush.co.kr)
 * @since   : 2014. 12. 30.
 */
public interface PersonApprovalDAO {
	/**
	 * 
	 * <pre>
	 * 1. 개요     : 개인결재라인
	 * 2. 처리내용 : 개인결재라인 가져온다.
	 * </pre>
	 * @Method Name : getPersonApprovalDetailList
	 * @param paramMap
	 * @return
	 */
	public List<PersonApprovalVO> getPersonApprovalDetailList(Map<String, String> paramMap);
	
	/**
	 * 
	 * <pre>
	 * 1. 개요     : 개인결재라인
	 * 2. 처리내용 : 개인결재라인 전체 등록
	 * </pre>
	 * @Method Name : insertPersonApprovalAll
	 * @param paramMap
	 * @param personApprovalVO
	 * @param personImplDeptVO
	 * @return
	 */
	public int insertPersonApprovalAll(Map<String, String> paramMap, PersonLineVO personLineVO,
			PersonApprovalVO personApprovalVO,
			PersonImplDeptVO personImplDeptVO);
	
	/**
	 * 
	 * <pre>
	 * 1. 개요     : 개인결재라인 등록
	 * 2. 처리내용 : 개인결재라인 등록
	 * </pre> 
	 * @Method Name : insertPersonApproval
	 * @param personApprovalVO
	 * @return
	 */
	public int insertPersonApproval(PersonApprovalVO personApprovalVO);
	
	/**
	 * 
	 * <pre>
	 * 1. 개요     : 개인결재라인 삭제
	 * 2. 처리내용 : 개인결재라인 삭제
	 * </pre>
	 * @Method Name : deletePersonApproval
	 * @param paramMap
	 * @return
	 */
	public int deletePersonApproval(Map<String, String> paramMap);	
}
