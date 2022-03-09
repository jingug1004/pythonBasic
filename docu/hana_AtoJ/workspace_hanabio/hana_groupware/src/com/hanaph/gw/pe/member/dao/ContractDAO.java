/**
  * Hana Project
  * Copyright 2014 iRush Co.,
  *
  */
package com.hanaph.gw.pe.member.dao;

import java.util.List;
import java.util.Map;

import com.hanaph.gw.pe.member.vo.ContractVO;

/**
 * <pre>
 * Class Name : ContractDAO.java
 * 설명 : 연봉계약서 정보 DAO interface
 * 
 * Modification Information
 *    수정일      수정자              수정 내용
 *  ------------ -------------- --------------------------------
 *  2014. 11. 4.      CHOIILJI         
 * </pre>
 * 
 * @version : 
 * @author  : CHOIILJI(choiilji@irush.co.kr)
 * @since   : 2014. 11. 4.
 */
public interface ContractDAO {
	/**
	 * 
	 * <pre>
	 * 1. 개요     : 연봉계약서 리스트
	 * 2. 처리내용 : 연봉계약서 리스트 가져온다
	 * </pre>
	 * @Method Name : getContractList
	 * @param paramMap
	 * @return
	 */
	public List<ContractVO> getContractList(Map<String, String> paramMap);
	
	/**
	 * 
	 * <pre>
	 * 1. 개요     : 연봉계약서 리스트 전체 카운트
	 * 2. 처리내용 : 연봉계약서 리스트 전체 카운트
	 * </pre>
	 * @Method Name : getContractCount
	 * @param paramMap
	 * @return
	 */
	public int getContractCount (Map<String, String> paramMap);
	
	/**
	 * 
	 * <pre>
	 * 1. 개요     : 연봉계약서상세 정보
	 * 2. 처리내용 : 연봉계약서상세 정보 가져온다.
	 * </pre>
	 * @Method Name : getContractDetail
	 * @param paramMap
	 * @return
	 */
	public ContractVO getContractDetail(Map<String, String> paramMap);
}
