/**
  * Hana Project
  * Copyright 2014 iRush Co.,
  *
  */
package com.hanaph.gw.pe.member.dao;

import java.util.List;
import java.util.Map;

import com.hanaph.gw.pe.member.vo.AnnualHRVO;
import com.hanaph.gw.pe.member.vo.AnnualMgrVO;
import com.hanaph.gw.pe.member.vo.AnnualVO;

/**
 * <pre>
 * Class Name : AnnualDAO.java
 * 설명 : 연차사용내역 정보 DAO interface
 * 
 * Modification Information
 *    수정일      수정자              수정 내용
 *  ------------ -------------- --------------------------------
 *  2014. 10. 30.      CHOIILJI         
 * </pre>
 * 
 * @version : 
 * @author  : CHOIILJI(choiilji@irush.co.kr)
 * @since   : 2014. 10. 30.
 */
public interface AnnualDAO {
	/**
	 * 
	 * <pre>
	 * 1. 개요     : 연차사용내역 리스트
	 * 2. 처리내용 : 연차사용내역 리스트 가져온다.
	 * </pre>
	 * @Method Name : getAnnualList
	 * @param paramMap
	 * @return
	 */
	public List<AnnualVO> getAnnualList(Map<String, String> paramMap);
	
	/**
	 * 
	 * <pre>
	 * 1. 개요     : 연차사용내역 리스트 전체 카운트
	 * 2. 처리내용 : 연차사용내역 리스트 전체 카운트
	 * </pre>
	 * @Method Name : getAnnualCount
	 * @param paramMap
	 * @return
	 */
	public int getAnnualCount (Map<String, String> paramMap);
	
	/**
	 * 
	 * <pre>
	 * 1. 개요     : 연차총사용일
	 * 2. 처리내용 : 연차총사용일
	 * </pre>
	 * @Method Name : getAnnualUsedCount
	 * @param paramMap
	 * @return
	 */
	public float getAnnualUsedCount (Map<String, String> paramMap);
	
	/**
	 * 
	 * <pre>
	 * 1. 개요     : 연차휴가사용계획서
	 * 2. 처리내용 : 유효 연차 일수 사용연차 일수 가져온다.
	 * </pre>
	 * @Method Name : getAnnualPlan
	 * @param paramMap
	 * @return
	 */
	public AnnualMgrVO getAnnualPlan(Map<String, String> paramMap);
	
	/**
	 * 
	 * <pre>
	 * 1. 개요     : 연차휴가사용계획서 공동연차 조회
	 * 2. 처리내용 : 연차휴가사용계획서에서 사용할 공동연차 사용일 가져온다. 
	 * </pre>
	 * @Method Name : getAnnualCommonDT
	 * @param paramMap
	 * @return
	 */
	public List<AnnualVO> getAnnualCommonDT(Map<String, String> paramMap);
	
	/**
	 * 
	 * <pre>
	 * 1. 개요     : 공동연차사용내역 전체 카운트
	 * 2. 처리내용 : 공동연차사용내역 전체 카운트
	 * </pre>
	 * @Method Name : getAnnualCommonDTCount
	 * @param paramMap
	 * @return
	 */	
	
	public int getAnnualCommonDTCount (Map<String, String> paramMap);
	
	/**
	 * 
	 * <pre>
	 * 1. 개요     : 미사용 연차유급휴가 사용시기 지정통보서
	 * 2. 처리내용 : 미사용 연차유급휴가 사용시기 지정통보서
	 * </pre>
	 * @Method Name : getAnnualPlanNotify
	 * @param paramMap
	 * @return
	 */
	
	public int getAnnualCommonDTCountHalf(Map<String, String> paramMap);
	
	/**
	 * 
	 * <pre>
	 * 1. 개요     : 미사용 연차유급휴가 사용시기 지정통보서 상반기
	 * 2. 처리내용 : 미사용 연차유급휴가 사용시기 지정통보서 상반기
	 * </pre>
	 * @Method Name : getAnnualCommonDTCountHalf
	 * @param paramMap
	 * @return
	 */
	
	public AnnualMgrVO getAnnualPlanNotify(Map<String, String> paramMap);
	
	/**
	 * 
	 * <pre>
	 * 1. 개요     : 연차사용계획 등록 가능여부
	 * 2. 처리내용 : 연차사용계획 등록 가능여부
	 * </pre>
	 * @Method Name : getAnnualClosedYN
	 * @param paramMap
	 * @return
	 */
	public boolean getAnnualClosedYN(Map<String, String> paramMap);
	
	/**
	 * 
	 * <pre>
	 * 1. 개요     : 연차사용계획 등록한다.
	 * 2. 처리내용 : 연차사용계획 등록한다.
	 * </pre>
	 * @Method Name : insertAnnualPlan
	 * @param annualHRVO
	 * @return
	 */
	public boolean insertAnnualPlan(AnnualHRVO annualHRVO);
	
	/**
	 * 
	 * <pre>
	 * 1. 개요     : 연차사용계획 삭제한다.
	 * 2. 처리내용 : 연차사용계획 삭제한다.
	 * </pre>
	 * @Method Name : deleteAnnualPlan
	 * @param annualHRVO
	 * @return
	 */
	public boolean deleteAnnualPlan(AnnualHRVO annualHRVO);
	

	/**
	 * <pre>
	 * 1. 개요     : 연차 상신
	 * 2. 처리내용 : 휴가 신청서 결재가 취소 되었을때 연차를 db에서 삭제한다.
	 * </pre>
	 * @Method Name : insertAnnual
	 * @param annualVO
	 * @return
	 */
	public boolean insertAnnual(AnnualVO annualVO);

	/**
	 * <pre>
	 * 1. 개요     : 연차 상신 취소
	 * 2. 처리내용 : 휴가 신청서 결재가 취소 되었을때 연차를 db에서 삭제한다.
	 * </pre>
	 * @Method Name : deleteAnnual
	 * @param annualVO
	 * @return
	 */
	public boolean deleteAnnual(AnnualVO annualVO);
}
