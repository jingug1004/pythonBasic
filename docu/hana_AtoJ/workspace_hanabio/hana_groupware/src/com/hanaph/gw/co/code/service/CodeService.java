/**
  * Hana Project
  * Copyright 2014 iRush Co.,
  *
  */
package com.hanaph.gw.co.code.service;

import java.util.List;
import java.util.Map;

import com.hanaph.gw.co.code.vo.CodeVO;

/**
 * <pre>
 * Class Name : CodeService.java
 * 설명 : 코드 관리 Service
 * 
 * Modification Information
 *    수정일      수정자              수정 내용
 *  ------------ -------------- --------------------------------
 *  2014. 10. 14.      Jung.Jin.Muk          
 * </pre>
 * 
 * @version : 
 * @author  : Jung.Jin.Muk(pc123pc@irush.co.kr)
 * @since   : 2014. 10. 14.
 */
public interface CodeService {
	
	/**
	 * <pre>
	 * 1. 개요     : 코드 카운트
	 * 2. 처리내용 : 코드 카운트를 가져온다.
	 * </pre>
	 * @Method Name : getCodeCount
	 * @param paramMap
	 * @return
	 */
	public int getCodeCount(Map<String, String> paramMap);
	
	/**
	 * <pre>
	 * 1. 개요     : 코드 리스트
	 * 2. 처리내용 : 메인 코드 리스트를 가져온다.
	 * </pre>
	 * @Method Name : getCodeList
	 * @param paramMap
	 * @return
	 */
	public List<CodeVO> getCodeList(Map<String, String> paramMap);

	/**
	 * <pre>
	 * 1. 개요     : 코드 리스트
	 * 2. 처리내용 : 서브 코드 리스트를 가져온다.
	 * </pre>
	 * @Method Name : getScodeList
	 * @param paramMap
	 * @return
	 */
	public List<CodeVO> getScodeList(Map<String, String> paramMap);
	
	/**
	 * <pre>
	 * 1. 개요     : 코드 상세 정보 
	 * 2. 처리내용 : 코드 상세 정보를 가져온다.
	 * </pre>
	 * @Method Name : detailCode
	 * @param paramMap
	 * @return
	 */
	public CodeVO detailCode(Map<String, String> paramMap);

	/**
	 * <pre>
	 * 1. 개요     : 코드 저장
	 * 2. 처리내용 : 코드를 저장한다.
	 * </pre>
	 * @Method Name : insertCode
	 * @param paramCodeVO
	 * @return
	 */
	public boolean insertCode(CodeVO paramCodeVO);

	/**
	 * <pre>
	 * 1. 개요     : 코드 삭제
	 * 2. 처리내용 : 코드를 삭제한다.
	 * </pre>
	 * @Method Name : deleteCode
	 * @param paramCodeVO
	 * @return
	 */
	public boolean deleteCode(CodeVO paramCodeVO);

	/**
	 * <pre>
	 * 1. 개요     : 코드 체크
	 * 2. 처리내용 : 코드 중복 체크를 한다.
	 * </pre>
	 * @Method Name : checkCode
	 * @param cd
	 * @return
	 */
	public CodeVO checkCode(String cd);



}
