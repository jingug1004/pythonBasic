/**
  * Hana Project
  * Copyright 2014 iRush Co.,
  *
  */
package com.hanaph.gw.ea.newReport.service;

import com.hanaph.gw.ea.newReport.vo.ImplDeptEmpVO;
import com.hanaph.gw.ea.newReport.vo.ImplDeptVO;

import java.util.List;
import java.util.Map;

/**
 * <pre>
 * Class Name : ImplDeptService.java
 * 설명 : 시행부서 Service
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
public interface ImplDeptService {
	/**
	 * 
	 * <pre>
	 * 1. 개요     : 문서별 시행라인
	 * 2. 처리내용 : 문서별 시행라인 가져온다.
	 * </pre>
	 * @Method Name : getImplDeptDetailList
	 * @param paramMap
	 * @return
	 */
	public List<ImplDeptVO> getImplDeptDetailList(Map<String, String> paramMap);

	/**
	 *
	 * <pre>
	 * 1. 개요     : 문서별 시행부서의 사원 정보
	 * 2. 처리내용 : 문서별 시행부서의 사원 정보 가져온다.
	 * ml180122.ml04_T43 김진국 - ImplDeptService.java에 getImplDeptDetailListMustOpinion 인터페이스 만듬 - 시의필 실행하면 시행부서와 시행부서의 소속 사원 결과값을 가져와서 쪽지 보내서 알림 기능
	 * </pre>
	 * @Method Name : getImplDeptDetailList
	 * @return
	 */
	public List<String> getImplDeptDetailListMustOpinion(String approval_seq);

	/**
	 * 
	 * <pre>
	 * 1. 개요     : 문서별 시행라인 등록
	 * 2. 처리내용 : 문서별 시행라인 등록
	 * </pre>
	 * @Method Name : insertImplDept
	 * @param implDeptVO
	 * @return
	 */
	public int insertImplDept(ImplDeptVO implDeptVO);
	
	/**
	 * 
	 * <pre>
	 * 1. 개요     : 문서별 시행라인 삭제
	 * 2. 처리내용 : 문서별 시행라인 삭제
	 * </pre>
	 * @Method Name : deleteImplDept
	 * @param paramMap
	 * @return
	 */
	public int deleteImplDept(Map<String, String> paramMap);	
	
	/**
	 * 
	 * <pre>
	 * 1. 개요     : 문서별 시행라인 등록
	 * 2. 처리내용 : 문서별 시행라인,시행라인 임직원  등록
	 * </pre>
	 * @Method Name : insertImplDept
	 * @param implDeptVO
	 * @param ImplDeptEmpVO
	 * @return
	 */
	public int insertImplDeptAndEmp(ImplDeptVO implDeptVO, ImplDeptEmpVO ImplDeptEmpVO) ;
}
