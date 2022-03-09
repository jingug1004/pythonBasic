/**
  * Hana Project
  * Copyright 2014 iRush Co.,
  *
  */
package com.hanaph.gw.ea.newReport.dao;

import java.util.List;
import java.util.Map;

import com.hanaph.gw.ea.newReport.vo.ImplDeptEmpVO;

/**
 * <pre>
 * Class Name : ImplDeptEmpDAO.java
 * 설명 : 문서별 시행부서 직원 DAO
 * 
 * Modification Information
 *    수정일      수정자              수정 내용
 *  ------------ -------------- --------------------------------
 *  2015. 1. 16.      CHOIILJI         
 * </pre>
 * 
 * @version : 
 * @author  : CHOIILJI(choiilji@irush.co.kr)
 * @since   : 2015. 1. 16.
 */
public interface ImplDeptEmpDAO {

	/**
	 * 
	 * <pre>
	 * 1. 개요     : 문서별 시행부서직원 등록
	 * 2. 처리내용 : 문서별 시행부서직원 등록
	 * </pre>
	 * @Method Name : insertImplDeptEmp
	 * @param implDeptEmpVO
	 * @return
	 */
	public boolean insertImplDeptEmp(ImplDeptEmpVO implDeptEmpVO);
	
	/**
	 * 
	 * <pre>
	 * 1. 개요     : 문서별 시행부서직원 삭제
	 * 2. 처리내용 : 문서별 시행부서직원 삭제
	 * </pre>
	 * @Method Name : deleteImplDeptEmp
	 * @param implDeptEmpVO
	 * @return
	 */
	public boolean deleteImplDeptEmp(ImplDeptEmpVO implDeptEmpVO);
	
	/**
	 * 
	 * <pre>
	 * 1. 개요     : 문서별 시행부서직원 열람여부 업데이트
	 * 2. 처리내용 : 문서별 시행부서직원 열람여부 업데이트
	 * </pre>
	 * @Method Name : updateImplDeptEmpReadYN
	 * @param implDeptEmpVO
	 * @return
	 */
	public boolean updateImplDeptEmpReadYN(ImplDeptEmpVO implDeptEmpVO);
	
	/**
	 * 
	 * <pre>
	 * 1. 개요     : 문서별 시행부서직원 상제정보 가져온다.
	 * 2. 처리내용 : 문서별 시행부서직원 상제정보 가져온다.
	 * </pre>
	 * @Method Name : getImplDeptEmpDetail
	 * @param paramMap
	 * @return
	 */
	public ImplDeptEmpVO getImplDeptEmpDetail(Map<String, String> paramMap);
	
	/**
	 * 
	 * <pre>
	 * 1. 개요     : 문서별 시행부서직원 리스트
	 * 2. 처리내용 : 문서별 시행부서직원 리스트
	 * </pre>
	 * @Method Name : getImplDeptEmpList
	 * @param paramMap
	 * @return
	 */
	public List<ImplDeptEmpVO> getImplDeptEmpList(Map<String, String> paramMap);
	
	/**
	 * 
	 * <pre>
	 * 1. 개요     : 문서별 시행부서직원 완료여부 업데이트
	 * 2. 처리내용 : 문서별 시행부서직원 완료여부 업데이트
	 * </pre>
	 * @Method Name : updateImplDeptEmpComplete
	 * @param implDeptEmpVO
	 * @return
	 */
	public boolean updateImplDeptEmpComplete(ImplDeptEmpVO implDeptEmpVO);
	
	/**
	 * 
	 * <pre>
	 * 1. 개요     : 문서별 시행부서직원 완료여부 삭제
	 * 2. 처리내용 : 문서별 시행부서직원 완료여부 삭제
	 * </pre>
	 * @Method Name : deleteImplDeptEmpComplete
	 * @param implDeptEmpVO
	 * @return
	 */
	public boolean deleteImplDeptEmpComplete(ImplDeptEmpVO implDeptEmpVO);
	
	/**
	 * 
	 * <pre>
	 * 1. 개요     : 관심문서 저장한다.
	 * 2. 처리내용 : 관심문서 저장한다.
	 * </pre>
	 * @Method Name : insertImplDeptEmpInterestYN
	 * @param implDeptEmpVO
	 * @return
	 */
	public boolean insertImplDeptEmpInterestYN(ImplDeptEmpVO implDeptEmpVO);
	
}
