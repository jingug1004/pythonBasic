/**
  * Hana Project
  * Copyright 2014 iRush Co.,
  *
  */
package com.hanaph.gw.pe.member.service;

import java.util.List;
import java.util.Map;

import com.hanaph.gw.pe.member.vo.MemberVO;

/**
 * <pre>
 * Class Name : MemberService.java
 * 설명 : 회원정보 Service interface
 * 
 * Modification Information
 *    수정일      수정자              수정 내용
 *  ------------ -------------- --------------------------------
 *  2014. 10. 16.      CHOIILJI         
 * </pre>
 * 
 * @version : 
 * @author  : CHOIILJI(choiilji@irush.co.kr)
 * @since   : 2014. 10. 16.
 */
public interface MemberService {

	/**
	 * 
	 * <pre>
	 * 1. 개요     : 사원정보 리스트
	 * 2. 처리내용 : 사원정보 가져온다.
	 * </pre>
	 * @Method Name : getMemberList
	 * @param paramMap
	 * @return
	 */
	public List<MemberVO> getMemberList(Map<String, String> paramMap);
	
	/**
	 * 
	 * <pre>
	 * 1. 개요     : 사원정보
	 * 2. 처리내용 : 사원상세정보 가져온다.
	 * </pre>
	 * @Method Name : getMemberDetail
	 * @param paramMap
	 * @return
	 */
	public MemberVO getMemberDetail(Map<String, String> paramMap);
	
	/**
	 * 
	 * <pre>
	 * 1. 개요     : 사진정보 가져온다.
	 * 2. 처리내용 :
	 * </pre>
	 * @Method Name : getMemberPhoto
	 * @param paramMap
	 * @return
	 */
	public MemberVO getMemberPhoto(Map<String, String> paramMap);
}
