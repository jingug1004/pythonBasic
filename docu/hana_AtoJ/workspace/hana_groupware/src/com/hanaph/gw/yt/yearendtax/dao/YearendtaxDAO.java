/**
  * Hana Project
  * Copyright 2014 iRush Co.,
  *
  */
package com.hanaph.gw.yt.yearendtax.dao;

import java.util.List;
import java.util.Map;

import com.hanaph.gw.yt.yearendtax.vo.YearendtaxAddressVO;
import com.hanaph.gw.yt.yearendtax.vo.YearendtaxVO;


/**
 * <pre>
 * Class Name : YearendtaxDAO.java
 * 설명 : 연말정산 DAO interface
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
public interface YearendtaxDAO {
	/**
	 * 
	 * <pre>
	 * 1. 개요     : 연말정산 리스트
	 * 2. 처리내용 : 연말정산 리스트 가져온다
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
	 * 1. 개요     : 연말정산상세 3페이지 정보
	 * 2. 처리내용 : 연말정산상세 3페이지 정보
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
	 * 1. 개요     : 연말정산 기본사항
	 * 2. 처리내용 : 연말정산 기본사항을 가져온다.
	 * </pre>
	 * @Method Name : getYearendtaxInfo1
	 * @param paramMap
	 * @return
	 */		
	public YearendtaxVO getYearendtaxInfo1(Map<String, String> paramMap);

	/**
	 * <pre>
	 * 1. 개요     : 연말정산 기본공제
	 * 2. 처리내용 : 연말정산 기본공제 내용을 가져온다.
	 * </pre>
	 * @Method Name : getYearendtaxInfo2
	 * @param paramMap
	 * @return
	 */		
	public YearendtaxVO getYearendtaxInfo2(Map<String, String> paramMap);

	/**
	 * <pre>
	 * 1. 개요     : 연말정산 추가공제
	 * 2. 처리내용 : 연말정산 추가공제 내용을 가져온다.
	 * </pre>
	 * @Method Name : getYearendtaxInfo3
	 * @param paramMap
	 * @return
	 */		
	public YearendtaxVO getYearendtaxInfo3(Map<String, String> paramMap);

	/**
	 * <pre>
	 * 1. 개요     : 연말정산 연금보험료공제
	 * 2. 처리내용 : 연말정산 연금보험료공제 내용을 가져온다.
	 * </pre>
	 * @Method Name : getYearendtaxInfo4
	 * @param paramMap
	 * @return
	 */		
	public YearendtaxVO getYearendtaxInfo4(Map<String, String> paramMap);

	/**
	 * <pre>
	 * 1. 개요     : 연말정산 특별공제(보험료)
	 * 2. 처리내용 : 연말정산 특별공제(보험료) 내용을 가져온다.
	 * </pre>
	 * @Method Name : getYearendtaxInfo5
	 * @param paramMap
	 * @return
	 */		
	public YearendtaxVO getYearendtaxInfo5(Map<String, String> paramMap);

	/**
	 * <pre>
	 * 1. 개요     : 연말정산 특별공제(주택자금)
	 * 2. 처리내용 : 연말정산 특별공제(주택자금) 내용을 가져온다.
	 * </pre>
	 * @Method Name : getYearendtaxInfo6
	 * @param paramMap
	 * @return
	 */		
	public YearendtaxVO getYearendtaxInfo6(Map<String, String> paramMap);

	/**
	 * <pre>
	 * 1. 개요     : 개인연금저축공제
	 * 2. 처리내용 : 개인연금저축공제 내용을 가져온다.
	 * </pre>
	 * @Method Name : getYearendtaxInfo7
	 * @param paramMap
	 * @return
	 */		
	public YearendtaxVO getYearendtaxInfo7(Map<String, String> paramMap);

	/**
	 * <pre>
	 * 1. 개요     : 그밖의 소득공제(주택마련저축)
	 * 2. 처리내용 : 그밖의 소득공제(주택마련저축) 내용을 가져온다.
	 * </pre>
	 * @Method Name : getYearendtaxInfo8
	 * @param paramMap
	 * @return
	 */		
	public YearendtaxVO getYearendtaxInfo8(Map<String, String> paramMap);

	/**
	 * <pre>
	 * 1. 개요     : 그밖의 소득공제(신용카드 등 사용금액)
	 * 2. 처리내용 : 그밖의 소득공제(신용카드 등 사용금액) 내용을 가져온다.
	 * </pre>
	 * @Method Name : getYearendtaxInfo9
	 * @param paramMap
	 * @return
	 */		
	public YearendtaxVO getYearendtaxInfo9(Map<String, String> paramMap);

	/**
	 * <pre>
	 * 1. 개요     : 그밖의 소득공제(기타)
	 * 2. 처리내용 : 그밖의 소득공제(기타) 내용을 가져온다.
	 * </pre>
	 * @Method Name : getYearendtaxInfo10
	 * @param paramMap
	 * @return
	 */		
	public YearendtaxVO getYearendtaxInfo10(Map<String, String> paramMap);

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
	 * 1. 개요     : 세액감면 및 세액공제
	 * 2. 처리내용 : 세액감면 및 세액공제 내용을 가져온다.
	 * </pre>
	 * @Method Name : getYearendtaxInfo11
	 * @param paramMap
	 * @return
	 */		
	public YearendtaxVO getYearendtaxInfo11(Map<String, String> paramMap);
	

	/**
	 * <pre>
	 * 1. 개요     : 주소 저장
	 * 2. 처리내용 : 주소 저장
	 * </pre>
	 * @Method Name : insertYearendtaxAddress
	 * @param paramAddressVO
	 * @return
	 */
	public void insertYearendtaxAddress(Map<String, Object> paramMap);

	/**
	 * <pre>
	 * 1. 개요     : 저장 프로시저 호출
	 * 2. 처리내용 : 저장 프로시저를 호출한다.
	 * </pre>
	 * @Method Name : getProcedureCall
	 * @param paramVO
	 */		
	public void getProcedureCall(YearendtaxVO yearendtaxVO);

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
	 * 1. 개요     : 부양가족 내용 저장
	 * 2. 처리내용 : 부양가족 내용을 저장(등록/수정/삭제)한다.
	 * </pre>
	 * @Method Name : procYearendtaxFamilyAjax
	 * @param paramMap
	 * @return
	 */		
	public String procYearendtaxFamily(Map<String, Object> paramMap);

	/**
	 * <pre>
	 * 1. 개요     : 보험료 내용 저장
	 * 2. 처리내용 : 보험료 내용을 저장한다.
	 * </pre>
	 * @Method Name : procYearendtaxInsuranceAjax
	 * @param paramMap
	 * @return
	 */		
	public String procYearendtaxInsurance(Map<String, Object> paramMap);

	/**
	 * <pre>
	 * 1. 개요     : 신용카드 내용 저장
	 * 2. 처리내용 : 신용카드 내용을 저장한다.
	 * </pre>
	 * @Method Name : procYearendtaxCreditCardAjax
	 * @param paramMap
	 * @return
	 */		
	public String procYearendtaxCreditCard(Map<String, Object> paramMap);

	/**
	 * <pre>
	 * 1. 개요     : 교육비 내용 저장
	 * 2. 처리내용 : 교육비 내용을 저장한다.
	 * </pre>
	 * @Method Name : procYearendtaxEducate
	 * @param paramMap
	 * @return
	 */		
	public String procYearendtaxEducate(Map<String, Object> paramMap);

	/**
	 * 
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
	 * 1. 개요     : 의료비 내용 저장
	 * 2. 처리내용 : 의료비 내용을 저장한다.
	 * </pre>
	 * @Method Name : procYearendtaxMedical
	 * @param paramMap
	 * @return
	 */		
	public String procYearendtaxMedical(Map<String, Object> paramMap);

	/**
	 * <pre>
	 * 1. 개요     : 기부금 내용 저장
	 * 2. 처리내용 : 기부금 내용을 저장한다.
	 * </pre>
	 * @Method Name : procYearendtaxContribute
	 * @param paramMap
	 * @return
	 */		
	public String procYearendtaxContribute(Map<String, Object> paramMap);

	/**
	 * <pre>
	 * 1. 개요     : 종전 근무지 내용 저장
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
	 * @param paramVO
	 */		
	public void updateYearendtaxInfo(YearendtaxVO yearendtaxVO);

}
