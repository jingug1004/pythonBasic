/**
  * Hana Project
  * Copyright 2014 iRush Co.,
  *
  */
package com.hanaph.gw.ea.implement.service;

import java.util.List;
import java.util.Map;

import com.hanaph.gw.ea.implement.vo.ImplementReportVO;

/**
 * <pre>
 * Class Name : ImplementReportService.java
 * 설명 : 시행문서조회 Servics
 * 
 * Modification Information
 *    수정일      수정자              수정 내용
 *  ------------ -------------- --------------------------------
 *  2015. 1. 16.      CHOIILJI         
 * </pre>
 * 
 * @version : 1.0 
 * @author  : CHOIILJI(choiilji@irush.co.kr)
 * @since   : 2015. 1. 16.
 */
public interface ImplementReportService {
	/**
	 * 
	 * <pre>
	 * 1. 개요     : 시행문서 리스트
	 * 2. 처리내용 : 시행문서 리스트 가져온다
	 * </pre>
	 * @Method Name : getImplementList
	 * @param paramMap
	 * @return
	 */
	public List<ImplementReportVO> getImplementList(Map<String, String> paramMap);
	
	/**
	 * 
	 * <pre>
	 * 1. 개요     : 시행문서 리스트 전체 카운트
	 * 2. 처리내용 : 시행문서 리스트 전체 카운트
	 * </pre>
	 * @Method Name : getImplementCount
	 * @param paramMap
	 * @return
	 */
	public int getImplementCount (Map<String, String> paramMap);
}
