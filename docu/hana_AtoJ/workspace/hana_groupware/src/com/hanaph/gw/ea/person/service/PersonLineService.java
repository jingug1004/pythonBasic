/**
  * Hana Project
  * Copyright 2014 iRush Co.,
  *
  */
package com.hanaph.gw.ea.person.service;

import java.util.List;
import java.util.Map;

import com.hanaph.gw.ea.person.vo.PersonLineVO;

/**
 * <pre>
 * Class Name : PersonLineService.java
 * 설명 : 개인결재라인 Master Service
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
public interface PersonLineService {
	/**
	 * 
	 * <pre>
	 * 1. 개요     : 개인결재라인 Master 
	 * 2. 처리내용 : 개인결재라인 Master 리스트
	 * </pre>
	 * @Method Name : getPersonLineList
	 * @param paramMap
	 * @return
	 */
	public List<PersonLineVO> getPersonLineList(Map<String, String> paramMap);
	
	/**
	 * 
	 * <pre>
	 * 1. 개요     : 개인결재라인 Master 
	 * 2. 처리내용 : 개인결재라인 Master 삭제
	 * </pre>
	 * @Method Name : deletePersonLine
	 * @param cooperationVO
	 * @return
	 */
	public int deletePersonLine(Map<String, String> paramMap);
}
