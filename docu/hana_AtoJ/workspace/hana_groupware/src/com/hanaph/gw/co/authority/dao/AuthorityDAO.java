/**
  * Hana Project
  * Copyright 2014 iRush Co.,
  *
  */
package com.hanaph.gw.co.authority.dao;

import java.util.List;
import java.util.Map;

import com.hanaph.gw.co.authority.vo.AuthorityVO;
import com.hanaph.gw.co.menu.vo.MenuVO;
import com.hanaph.gw.pe.member.vo.MemberVO;

/**
 * <pre>
 * Class Name : AuthorityDAO.java
 * 설명 : 권한 관리 DAO
 * 
 * Modification Information
 *    수정일      수정자              수정 내용
 *  ------------ -------------- --------------------------------
 *  2014. 10. 27.      Jung.Jin.Muk          
 * </pre>
 * 
 * @version : 
 * @author  : Jung.Jin.Muk(pc123pc@irush.co.kr)
 * @since   : 2014. 10. 27.
 */
public interface AuthorityDAO {

	/**
	 * <pre>
	 * 1. 개요     : 권한 리스트.
	 * 2. 처리내용 : 권한 리스트 가져 온다.
	 * </pre>
	 * @Method Name : authorityList
	 * @param request
	 * @return
	 */
	List<AuthorityVO> getAuthorityList(Map<String, String> paramMap);
	
	/**
	 * <pre>
	 * 1. 개요     : 카운트.
	 * 2. 처리내용 : 권한 리스트 카운트 가져 온다.
	 * </pre>
	 * @Method Name : getAuthorityCount
	 * @param paramMap
	 * @return
	 */
	int getAuthorityCount(Map<String, String> paramMap);
	
	/**
	 * <pre>
	 * 1. 개요     : 권한 시퀀스 
	 * 2. 처리내용 : 권한 시퀀스를 가져온다.
	 * </pre>
	 * @Method Name : getAuth_seq
	 * @return
	 */
	String getAuth_seq();
	
	/**
	 * <pre>
	 * 1. 개요     : 권한 등록.
	 * 2. 처리내용 : 권한을 등록 한다.
	 * </pre>
	 * @Method Name : insertAuthority
	 * @param authVO
	 * @return
	 */
	void insertAuthority(AuthorityVO authVO);
	
	/**
	 * <pre>
	 * 1. 개요     :	권한 메뉴 코드 삭제 
	 * 2. 처리내용 : 권한 메뉴 코드를 삭제 한다.
	 * </pre>
	 * @Method Name : deleteAuthorityMenuCode
	 * @param authVO
	 */
	void deleteAuthorityMenuCode(AuthorityVO authVO);
	
	/**
	 * <pre>
	 * 1. 개요     : 권한 수정
	 * 2. 처리내용 :	권한 이름, 권한 설명을 수정 한다.
	 * </pre>
	 * @Method Name : updateAuthority
	 * @param authVO
	 */
	void updateAuthority(AuthorityVO authVO);
	
	/**
	 * <pre>
	 * 1. 개요     : 권한 메뉴코드 저장
	 * 2. 처리내용 : 권한에 메뉴코드를 저장한다.
	 * </pre>
	 * @Method Name : insertAuthorityMenuCode
	 * @param authVO
	 */
	void insertAuthorityMenuCode(AuthorityVO authVO);
	
	/**
	 * <pre>
	 * 1. 개요     : 권한 상세.
	 * 2. 처리내용 : 권한 상세 정보를 가져 온다.
	 * </pre>
	 * @Method Name : getAuthorityDetail
	 * @param paramMap
	 * @return
	 */
	AuthorityVO getAuthorityDetail(Map<String, String> paramMap);
	
	/**
	 * <pre>
	 * 1. 개요     : 
	 * 2. 처리내용 :
	 * </pre>
	 * @Method Name : getAuthorityMenuList
	 * @param paramMap
	 * @return
	 */
	List<MenuVO> getAuthorityMenuList(Map<String, String> paramMap);

	/**
	 * <pre>
	 * 1. 개요     : <tr>로우 수.
	 * 2. 처리내용 : 레이어 팝업에서 1depth <tr>을 합치기 위한 로우 수 를 가져온다.
	 * </pre>
	 * @Method Name : getAuthorityMenuRow
	 * @param paramMap
	 * @return
	 */
	List<MenuVO> getAuthorityMenuRow(Map<String, String> paramMap);

	/**
	 * <pre>
	 * 1. 개요     : 권한 임직원 삭제
	 * 2. 처리내용 : 권한 임직원 삭제를 한다.
	 * </pre>
	 * @Method Name : deleteAuthorityEmpNo
	 * @param authVO
	 */
	void deleteAuthorityEmpNo(AuthorityVO authVO);
	
	/**
	 * <pre>
	 * 1. 개요     : 권한 임직원 저장
	 * 2. 처리내용 : 권한 임직원 저장을 한다.
	 * </pre>
	 * @Method Name : insertAuthorityEmpNo
	 * @param authVO
	 * @return
	 */
	void insertAuthorityEmpNo(AuthorityVO authVO);

	/**
	 * <pre>
	 * 1. 개요     : 메뉴코드 리스트.
	 * 2. 처리내용 : 등록된 권한 메뉴코드 리스트를 가져온다
	 * </pre>
	 * @Method Name : getMenuCodeList
	 * @param paramMap
	 * @return
	 */
	List<AuthorityVO> getMenuCodeList(Map<String, String> paramMap);

	/**
	 * <pre>
	 * 1. 개요     : 임직원 리스트.
	 * 2. 처리내용 : 등록된 권한 임직원 리스트를 가져온다.
	 * </pre>
	 * @Method Name : getAuthorityMemberList
	 * @param paramMap
	 * @return
	 */
	List<MemberVO> getAuthorityMemberList(Map<String, Object> paramMap);

	/**
	 * <pre>
	 * 1. 개요     : 권한 삭제
	 * 2. 처리내용 : 권한을 삭제 한다.
	 * </pre>
	 * @Method Name : deleteAuthority
	 * @param authVO
	 * @return
	 */
	boolean deleteAuthority(AuthorityVO authVO);

	/**
	 * <pre>
	 * 1. 개요     : 권한 초기화 
	 * 2. 처리내용 : 권한에 맵핑되어 있는 임직원들을 초기화 시킨다.
	 * </pre>
	 * @Method Name : resetAuthMember
	 * @param authVO
	 * @return
	 */
	boolean resetAuthMember(AuthorityVO authVO);

	/**
	 * <pre>
	 * 1. 개요     : 권한 임직원 조회
	 * 2. 처리내용 : 권한 임직원 조회를 할때 임직원이 없으면(등록되어있으면) 맵핑된 권한과 이름을 얼럿으로 보여준다.
	 * </pre>
	 * @Method Name : searchAuthMember
	 * @param emp_ko_nm
	 * @return
	 */
	List<AuthorityVO> searchAuthMember(String emp_ko_nm);



}
