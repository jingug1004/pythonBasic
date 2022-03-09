/**
  * Hana Project
  * Copyright 2014 iRush Co.,
  *
  */
package com.hanaph.gw.yt.yearendtax.service;

import java.util.List;
import java.util.Map;

import com.hanaph.gw.yt.yearendtax.vo.YearendtaxAddressVO;
import com.hanaph.gw.yt.yearendtax.vo.YearendtaxVO;


/**
 * <pre>
 * Class Name : YearendtaxService.java
 * 설명 : 연말정산 Service interface
 * 
 * Modification Information
 *    수정일      수정자              수정 내용
 *  ------------ -------------- --------------------------------
 *  2014. 11. 24.      CHOIILJI         
 * </pre>
 * 
 * @version : 
 * @author  : CHOIILJI(choiilji@irush.co.kr)
 * @since   : 2014. 11. 24.
 */
public interface YearendtaxService {
	/**
	 * 
	 * <pre>
	 * 1. 개요     : 연말정산 리스트
	 * 2. 처리내용 : 연말정산 리스트 가져온다.
	 * </pre>
	 * @Method Name : getYearendtaxList
	 * @param paramMap
	 * @return
	 */
	public List<YearendtaxVO> getYearendtaxList(Map<String, String> paramMap);
	
	/**
	 * 
	 * <pre>
	 * 1. 개요     : 연말정산 리스트 전체 카운트
	 * 2. 처리내용 : 연말정산 리스트 전체 카운트
	 * </pre>
	 * @Method Name : getYearendtaxCount
	 * @param paramMap
	 * @return
	 */
	public int getYearendtaxCount (Map<String, String> paramMap);
	
	/**
	 * 
	 * <pre>
	 * 1. 개요     : 연말정산상세 정보
	 * 2. 처리내용 : 연말정산상세 정보 가져온다.
	 * </pre>
	 * @Method Name : getYearendtaxDetail
	 * @param paramMap
	 * @return
	 */
	public YearendtaxVO getYearendtaxDetail(Map<String, String> paramMap);

	/**
	 * <pre>
	 * 1. 개요     : 종전 근무지 정보
	 * 2. 처리내용 : 종전 근무지 정보를 가져온다.
	 * </pre>
	 * @Method Name : getYearendtaxPreviousWorkplaceList
	 * @param paramMap
	 * @return
	 */		
	public List<YearendtaxVO> getYearendtaxPreviousWorkplaceList(Map<String, String> paramMap);

	/**
	 * <pre>
	 * 1. 개요     : 부양가족 목록
	 * 2. 처리내용 : 부양가족 목록을 가져온다.
	 * </pre>
	 * @Method Name : getYearendtaxDependentsList
	 * @param paramMap
	 * @return
	 */		
	public List<YearendtaxVO> getYearendtaxFamilyList(Map<String, String> paramMap);

	/**
	 * <pre>
	 * 1. 개요     : 주택자금 목록
	 * 2. 처리내용 : 주택자금 목록을 가져온다.
	 * </pre>
	 * @Method Name : getYearendtaxHouseList
	 * @param paramMap
	 * @return
	 */		
	public List<YearendtaxVO> getYearendtaxHouseList(Map<String, String> paramMap);

	/**
	 * <pre>
	 * 1. 개요     : 신용카드 사용금액 목록
	 * 2. 처리내용 : 신용카드 사용금액 목록을 가져온다.
	 * </pre>
	 * @Method Name : getYearendtaxCreditCardList
	 * @param paramMap
	 * @return
	 */		
	public List<YearendtaxVO> getYearendtaxCreditCardList(Map<String, String> paramMap);

	/**
	 * <pre>
	 * 1. 개요     : 연말정산상세 2페이지 정보
	 * 2. 처리내용 : 연말정산상세 2페이지 정보
	 * </pre>
	 * @Method Name : getYearendtaxDetail2
	 * @param paramMap
	 * @return
	 */
	public YearendtaxVO getYearendtaxDetail2(Map<String, String> paramMap);

	/**
	 * <pre>
	 * 1. 개요     : 연말정산상세 2페이지 정보
	 * 2. 처리내용 : 연말정산상세 2페이지 정보
	 * </pre>
	 * @Method Name : getYearendtaxDetail3
	 * @param paramMap
	 * @return
	 */
	public List<YearendtaxVO> getYearendtaxDetail3(Map<String, String> paramMap);

	/**
	 * <pre>
	 * 1. 개요     : 보험료 목록
	 * 2. 처리내용 : 보험료 목록을 가져온다.
	 * </pre>
	 * @Method Name : getYearendtaxInsuranceList
	 * @param paramMap
	 * @return
	 */		
	public List<YearendtaxVO> getYearendtaxInsuranceList(Map<String, String> paramMap);

	/**
	 * <pre>
	 * 1. 개요     : 의료비 목록
	 * 2. 처리내용 : 의료비 목록을 가져온다.
	 * </pre>
	 * @Method Name : getYearendtaxMedicalList
	 * @param paramMap
	 * @return
	 */		
	public List<YearendtaxVO> getYearendtaxMedicalList(Map<String, String> paramMap);

	/**
	 * <pre>
	 * 1. 개요     : 교육비 목록
	 * 2. 처리내용 : 교육비 목록을 가져온다.
	 * </pre>
	 * @Method Name : getYearendtaxEducateList
	 * @param paramMap
	 * @return
	 */		
	public List<YearendtaxVO> getYearendtaxEducateList(Map<String, String> paramMap);

	/**
	 * <pre>
	 * 1. 개요     : 기부금 목록
	 * 2. 처리내용 : 기부금 목록을 가져온다.
	 * </pre>
	 * @Method Name : getYearendtaxContributeList
	 * @param paramMap
	 * @return
	 */		
	public List<YearendtaxVO> getYearendtaxContributeList(Map<String, String> paramMap);

	/**
	 * <pre>
	 * 1. 개요     : 개인연금 목록
	 * 2. 처리내용 : 개인연금 목록을 가져온다.
	 * </pre>
	 * @Method Name : getYearendtaxSavingList
	 * @param paramMap
	 * @return
	 */		
	public List<YearendtaxVO> getYearendtaxSavingList(Map<String, String> paramMap);

	/**
	 * <pre>
	 * 1. 개요     : 금융기관 목록
	 * 2. 처리내용 : 금융기관 목록을 가져온다.
	 * </pre>
	 * @Method Name : getYearendtaxFinancialList
	 * @param paramMap
	 * @return
	 */		
	public List<YearendtaxVO> getYearendtaxFinancialList(Map<String, String> paramMap);

	/**
	 * <pre>
	 * 1. 개요     : 관계인 목록
	 * 2. 처리내용 : 관계인 목록을 가져온다.
	 * </pre>
	 * @Method Name : getYearendtaxDependentsList
	 * @param paramMap
	 * @return
	 */		
	public List<YearendtaxVO> getYearendtaxDependentsList(Map<String, String> paramMap);

	/**
	 * <pre>
	 * 1. 개요     : 연말정산 등록화면
	 * 2. 처리내용 : 저장되어있는 연말정산 내용을 가져온다.
	 * </pre>
	 * @Method Name : getYearendtaxDetail0
	 * @param paramMap
	 * @return
	 */		
	public YearendtaxVO getYearendtaxDetail0(Map<String, String> paramMap);

	/**
	 * <pre>
	 * 1. 개요     : 주소찾기
	 * 2. 처리내용 : 동, 도로명으로 주소를 찾는다.
	 * </pre>
	 * @Method Name : getSearchAddressList
	 * @param paramMap
	 * @return
	 */
	public List<YearendtaxAddressVO> getSearchAddressList(Map<String, String> paramMap);

	/**
	 * <pre>
	 * 1. 개요     : 주소 저장 
	 * 2. 처리내용 : 주소 저장
	 * </pre>
	 * @Method Name : insertYearendtaxAddress
	 * @param paramAddressVO
	 */
	public void insertYearendtaxAddress(Map<String, Object> paramMap);

	/**
	 * <pre>
	 * 1. 개요     : 연말정산 프로시저 호출
	 * 2. 처리내용 : 연말정산 프로시저를 호출한다.
	 * </pre>
	 * @Method Name : procYearendtaxAjax
	 * @param paramMap
	 * @return
	 */		
	public void getProcedureCall(Map<String, String> paramMap);

	/**
	 * <pre>
	 * 1. 개요     : 주택계약 내용 삭제 후 등록
	 * 2. 처리내용 : 주택계약 내용을 삭제 후 등록한다.
	 * </pre>
	 * @Method Name : procYearendtaxHouse
	 * @param paramMap
	 * @return 
	 */		
	public String procYearendtaxHouse(Map<String, Object> paramMap);

	/**
	 * <pre>
	 * 1. 개요     : 개인연금 내용 삭제 후 등록
	 * 2. 처리내용 : 개인연금 내용을 삭제 후 등록한다.
	 * </pre>
	 * @Method Name : procYearendtaxSaving
	 * @param paramMap
	 * @return
	 */		
	public String procYearendtaxSaving(Map<String, Object> paramMap);

	/**
	 * <pre>
	 * 1. 개요     : 부양가족 저장
	 * 2. 처리내용 : 부양가족을 저장(등록/수정/삭제)한다.
	 * </pre>
	 * @Method Name : procYearendtaxFamilyAjax
	 * @param paramMap
	 * @return
	 */		
	public String procYearendtaxFamily(Map<String, Object> paramMap);

	/**
	 * <pre>
	 * 1. 개요     : 보험료 저장
	 * 2. 처리내용 : 보험료를 저장한다.
	 * </pre>
	 * @Method Name : procYearendtaxInsuranceAjax
	 * @param paramMap
	 * @return
	 */		
	public String procYearendtaxInsurance(Map<String, Object> paramMap);

	/**
	 * <pre>
	 * 1. 개요     : 신용카드 저장
	 * 2. 처리내용 : 신용카드 내용을 저장한다.
	 * </pre>
	 * @Method Name : procYearendtaxCreditCardAjax
	 * @param paramMap
	 * @return
	 */		
	public String procYearendtaxCreditCard(Map<String, Object> paramMap);

	/**
	 * <pre>
	 * 1. 개요     : 교육비 저장
	 * 2. 처리내용 : 교육비 내용을 저장한다.
	 * </pre>
	 * @Method Name : procYearendtaxEducate
	 * @param paramMap
	 * @return
	 */		
	public String procYearendtaxEducate(Map<String, Object> paramMap);

	/**
	 * <pre>
	 * 1. 개요     : 주소 리스트
	 * 2. 처리내용 : 주소 리스트를 가져온다.
	 * </pre>
	 * @Method Name : getYearendtaxAddressList
	 * @param paramMap
	 * @return
	 */
	public List<YearendtaxAddressVO> getYearendtaxAddressList(Map<String, String> paramMap);

	/**
	 * <pre>
	 * 1. 개요     : 의료비 저장
	 * 2. 처리내용 : 의료비 내용을 저장한다.
	 * </pre>
	 * @Method Name : procYearendtaxMedical
	 * @param paramMap
	 * @return
	 */		
	public String procYearendtaxMedical(Map<String, Object> paramMap);

	/**
	 * <pre>
	 * 1. 개요     : 기부금 저장 
	 * 2. 처리내용 : 기부금 내용을 저장한다.
	 * </pre>
	 * @Method Name : procYearendtaxContribute
	 * @param paramMap
	 * @return
	 */		
	public String procYearendtaxContribute(Map<String, Object> paramMap);

	/**
	 * <pre>
	 * 1. 개요     : 종전 근무지 저장
	 * 2. 처리내용 : 종전 근무지 내용을 저장한다.
	 * </pre>
	 * @Method Name : procYearendtaxPreviousWorkplace
	 * @param paramMap
	 * @return
	 */		
	public String procYearendtaxPreviousWorkplace(Map<String, Object> paramMap);

	/**
	 * <pre>
	 * 1. 개요     : 연말정산 입력 저장
	 * 2. 처리내용 : 연말정산 입력 내용을 저장한다.
	 * </pre>
	 * @Method Name : updateYearendtaxInfo
	 * @param paramMap
	 */		
	public void updateYearendtaxInfo(Map<String, String> paramMap);
}
