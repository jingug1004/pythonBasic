/**
  * Hana Project
  * Copyright 2014 iRush Co.,
  *
  */
package com.hanaph.gw.ea.mgrDocuInfo.dao;

import java.util.List;
import java.util.Map;

import com.hanaph.gw.ea.mgrDocuInfo.vo.DocumentInfoVO;

/**
 * <pre>
 * Class Name : DocumentInfoDAO.java
 * 설명 : 양식정보관리 DAO
 * 
 * Modification Information
 *    수정일      수정자              수정 내용
 *  ------------ -------------- --------------------------------
 *  2014. 12. 18.      CHOIILJI         
 * </pre>
 * 
 * @version : 1.0
 * @author  : CHOIILJI(choiilji@irush.co.kr)
 * @since   : 2014. 12. 18.
 */
public interface DocumentInfoDAO {
	/**
	 * 
	 * <pre>
	 * 1. 개요     : 양식정보 리스트
	 * 2. 처리내용 : 양식정보 리스트 가져온다
	 * </pre>
	 * @Method Name : getDocumentInfoList
	 * @param paramMap
	 * @return
	 */
	public List<DocumentInfoVO> getDocumentInfoList(Map<String, String> paramMap);
	
	/**
	 * 
	 * <pre>
	 * 1. 개요     : 양식정보 리스트 전체 카운트
	 * 2. 처리내용 : 양식정보 리스트 전체 카운트
	 * </pre>
	 * @Method Name : gettDocumentInfoCount
	 * @param paramMap
	 * @return
	 */
	public int getDocumentInfoCount (Map<String, String> paramMap);
	
	/**
	 * 
	 * <pre>
	 * 1. 개요     : 양식정보 상세
	 * 2. 처리내용 : 양식정보 상세 가져온다.
	 * </pre>
	 * @Method Name : getDocumentInfoDetail
	 * @param paramMap
	 * @return
	 */
	public DocumentInfoVO getDocumentInfoDetail(Map<String, String> paramMap);
	
	/**
	 * 
	 * <pre>
	 * 1. 개요     : 양식정보 수정
	 * 2. 처리내용 : 양식정보 수정한다.
	 * </pre>
	 * @Method Name : updateDocumentInfo
	 * @param documentInfoVO
	 * @return
	 */
	public int updateDocumentInfo(DocumentInfoVO documentInfoVO);
	
	/**
	 * 
	 * <pre>
	 * 1. 개요     : 부서별, 문서별, key 값 가져온다. 
	 * 2. 처리내용 : 시간외 근무 내역서만 예외로 자동 생성으로 키값이 필요함.
	 * </pre>
	 * @Method Name : selectDocuSeq
	 * @param paramMap
	 * @return
	 */
	public String selectDocuSeq(Map<String, String> paramMap);
}
