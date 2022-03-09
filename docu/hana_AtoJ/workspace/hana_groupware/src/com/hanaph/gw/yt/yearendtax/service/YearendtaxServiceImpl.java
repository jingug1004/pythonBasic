/**
  * Hana Project
  * Copyright 2014 iRush Co.,
  *
  */
package com.hanaph.gw.yt.yearendtax.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hanaph.gw.yt.yearendtax.dao.YearendtaxDAO;
import com.hanaph.gw.yt.yearendtax.vo.YearendtaxAddressVO;
import com.hanaph.gw.yt.yearendtax.vo.YearendtaxVO;

/**
 * <pre>
 * Class Name : YearendtaxServiceImpl.java
 * 설명 : 연말정산 Service 구현한 class
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
@Service
public class YearendtaxServiceImpl implements YearendtaxService {

	@Autowired
	private YearendtaxDAO yearendtaxDao; 
	/* (non-Javadoc)
	 * @see com.hanaph.gw.yearendtax.service.YearendtaxService#getYearendtaxList(java.util.Map)
	 */
	@Override
	public List<YearendtaxVO> getYearendtaxList(Map<String, String> paramMap) {
		return yearendtaxDao.getYearendtaxList(paramMap);
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.yearendtax.service.YearendtaxService#getYearendtaxCount(java.util.Map)
	 */
	@Override
	public int getYearendtaxCount(Map<String, String> paramMap) {
		return yearendtaxDao.getYearendtaxCount(paramMap);
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.yearendtax.service.YearendtaxService#getYearendtaxDetail(java.util.Map)
	 */
	@Override
	public YearendtaxVO getYearendtaxDetail(Map<String, String> paramMap) {
		return yearendtaxDao.getYearendtaxDetail(paramMap);
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.yt.yearendtax.service.YearendtaxService#getYearendtaxPreviousWorkplaceDetail(java.util.Map)
	 */
	@Override
	public List<YearendtaxVO> getYearendtaxPreviousWorkplaceList(Map<String, String> paramMap) {
		return yearendtaxDao.getYearendtaxPreviousWorkplaceList(paramMap);
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.yt.yearendtax.service.YearendtaxService#getYearendtaxDependentsDetail(java.util.Map)
	 */
	@Override
	public List<YearendtaxVO> getYearendtaxFamilyList(Map<String, String> paramMap) {
		return yearendtaxDao.getYearendtaxFamilyList(paramMap);
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.yt.yearendtax.service.YearendtaxService#getYearendtaxHouseList(java.util.Map)
	 */
	@Override
	public List<YearendtaxVO> getYearendtaxHouseList(Map<String, String> paramMap) {
		return yearendtaxDao.getYearendtaxHouseList(paramMap);
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.yt.yearendtax.service.YearendtaxService#getYearendtaxCreditCardList(java.util.Map)
	 */
	@Override
	public List<YearendtaxVO> getYearendtaxCreditCardList(Map<String, String> paramMap) {
		return yearendtaxDao.getYearendtaxCreditCardList(paramMap);
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.yt.yearendtax.service.YearendtaxService#getYearendtaxDetail2(java.util.Map)
	 */
	@Override
	public YearendtaxVO getYearendtaxDetail2(Map<String, String> paramMap) {
		return yearendtaxDao.getYearendtaxDetail2(paramMap);
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.yt.yearendtax.service.YearendtaxService#getYearendtaxDetail3(java.util.Map)
	 */
	@Override
	public List<YearendtaxVO> getYearendtaxDetail3(Map<String, String> paramMap) {
		return yearendtaxDao.getYearendtaxDetail3(paramMap);
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.yt.yearendtax.service.YearendtaxService#getYearendtaxInsuranceList(java.util.Map)
	 */
	@Override
	public List<YearendtaxVO> getYearendtaxInsuranceList(Map<String, String> paramMap) {
		return yearendtaxDao.getYearendtaxInsuranceList(paramMap);
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.yt.yearendtax.service.YearendtaxService#getYearendtaxMedicalList(java.util.Map)
	 */
	@Override
	public List<YearendtaxVO> getYearendtaxMedicalList(Map<String, String> paramMap) {
		return yearendtaxDao.getYearendtaxMedicalList(paramMap);
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.yt.yearendtax.service.YearendtaxService#getYearendtaxEducateList(java.util.Map)
	 */
	@Override
	public List<YearendtaxVO> getYearendtaxEducateList(Map<String, String> paramMap) {
		return yearendtaxDao.getYearendtaxEducateList(paramMap);
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.yt.yearendtax.service.YearendtaxService#getYearendtaxContributeList(java.util.Map)
	 */
	@Override
	public List<YearendtaxVO> getYearendtaxContributeList(Map<String, String> paramMap) {
		return yearendtaxDao.getYearendtaxContributeList(paramMap);
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.yt.yearendtax.service.YearendtaxService#getYearendtaxSavingList(java.util.Map)
	 */
	@Override
	public List<YearendtaxVO> getYearendtaxSavingList(Map<String, String> paramMap) {
		return yearendtaxDao.getYearendtaxSavingList(paramMap);
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.yt.yearendtax.service.YearendtaxService#getYearendtaxFinancialList(java.util.Map)
	 */
	@Override
	public List<YearendtaxVO> getYearendtaxFinancialList(Map<String, String> paramMap) {
		return yearendtaxDao.getYearendtaxFinancialList(paramMap);
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.yt.yearendtax.service.YearendtaxService#getYearendtaxDependentsList(java.util.Map)
	 */
	@Override
	public List<YearendtaxVO> getYearendtaxDependentsList(Map<String, String> paramMap) {
		return yearendtaxDao.getYearendtaxDependentsList(paramMap);
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.yt.yearendtax.service.YearendtaxService#getYearendtaxDetail0(java.util.Map)
	 */
	@Override
	public YearendtaxVO getYearendtaxDetail0(Map<String, String> paramMap) {
		
		YearendtaxVO returnVO = new YearendtaxVO();
		
		/*기본사항*/
		YearendtaxVO resultVO1 = yearendtaxDao.getYearendtaxInfo1(paramMap);
		
		if (resultVO1 == null) {
			resultVO1 = new YearendtaxVO();
		}
		
		returnVO.setPay_sum(resultVO1.getPay_sum());
		returnVO.setWork_amt(resultVO1.getWork_amt());
		returnVO.setWork_pay(resultVO1.getWork_pay());
		returnVO.setPrivous_income_tax(resultVO1.getPrivous_income_tax());
		returnVO.setPrivous_jumin_tax(resultVO1.getPrivous_jumin_tax());
		returnVO.setIncome_tax(resultVO1.getIncome_tax());
		returnVO.setJing_jumin_tax(resultVO1.getJing_jumin_tax());
		
		/*기본공제*/
		YearendtaxVO resultVO2 = yearendtaxDao.getYearendtaxInfo2(paramMap);
		
		if (resultVO2 == null) {
			resultVO2 = new YearendtaxVO();
		}
		
		returnVO.setFamily_person_amt(resultVO2.getFamily_person_amt());
		returnVO.setFamily_mate_amt(resultVO2.getFamily_mate_amt());
		returnVO.setFamily_direct_ancestor_cnt(resultVO2.getFamily_direct_ancestor_cnt());
		returnVO.setFamily_direct_descendant_cnt(resultVO2.getFamily_direct_descendant_cnt());
		returnVO.setFamily_brother_cnt(resultVO2.getFamily_brother_cnt());
		returnVO.setFoster_child_cnt(resultVO2.getFoster_child_cnt());
		returnVO.setPensioner_cnt(resultVO2.getPensioner_cnt());
		
		/*추가공제*/
		YearendtaxVO resultVO3 = yearendtaxDao.getYearendtaxInfo3(paramMap);
		
		if (resultVO3 == null) {
			resultVO3 = new YearendtaxVO();
		}
		
		returnVO.setFamily_old2_cnt(resultVO3.getFamily_old2_cnt());
		returnVO.setFamily_disabled_person_cnt(resultVO3.getFamily_disabled_person_cnt());
		returnVO.setFamily_women_amt(resultVO3.getFamily_women_amt());
		returnVO.setFamily_single_parent_amt(resultVO3.getFamily_single_parent_amt());
		
		/*연금보험료공제*/
		YearendtaxVO resultVO4 = yearendtaxDao.getYearendtaxInfo4(paramMap);
		
		if (resultVO4 == null) {
			resultVO4 = new YearendtaxVO();
		}
		
		returnVO.setKuk_yeon_amt(resultVO4.getKuk_yeon_amt());
		
		/*특별공제(보험료)*/
		YearendtaxVO resultVO5 = yearendtaxDao.getYearendtaxInfo5(paramMap);
		
		if (resultVO5 == null) {
			resultVO5 = new YearendtaxVO();
		}
		
		returnVO.setInsurance_health_amt(resultVO5.getInsurance_health_amt());
		returnVO.setInsurance_employ_amt(resultVO5.getInsurance_employ_amt());
		
		/*특별공제(주택자금)*/
		YearendtaxVO resultVO6 = yearendtaxDao.getYearendtaxInfo6(paramMap);
		
		if (resultVO6 == null) {
			resultVO6 = new YearendtaxVO();
		}
		
		returnVO.setHouse_lease_loan_amt(resultVO6.getHouse_lease_loan_amt());
		returnVO.setHouse_security_loan1_amt(resultVO6.getHouse_security_loan1_amt());
		returnVO.setHouse_security_loan2_amt(resultVO6.getHouse_security_loan2_amt());
		returnVO.setHouse_security_loan3_amt(resultVO6.getHouse_security_loan3_amt());
		returnVO.setHouse_security_loan4_amt(resultVO6.getHouse_security_loan4_amt());
		returnVO.setHouse_security_loan5_amt(resultVO6.getHouse_security_loan5_amt());
		
		/*개인연금저축공제*/
		YearendtaxVO resultVO7 = yearendtaxDao.getYearendtaxInfo7(paramMap);
		
		if (resultVO7 == null) {
			resultVO7 = new YearendtaxVO();
		}
		
		returnVO.setPersonal_annuity_amt(resultVO7.getPersonal_annuity_amt());
		
		/*그밖의 소득공제(주택마련저축)*/
		YearendtaxVO resultVO8 = yearendtaxDao.getYearendtaxInfo8(paramMap);
		
		if (resultVO8 == null) {
			resultVO8 = new YearendtaxVO();
		}
		
		returnVO.setEtc_subscription_deposit_amt(resultVO8.getEtc_subscription_deposit_amt());
		returnVO.setEtc_home_mortgage_amt(resultVO8.getEtc_home_mortgage_amt());
		returnVO.setEtc_house_subscr_deposit_amt(resultVO8.getEtc_house_subscr_deposit_amt());
		
		/*그밖의 소득공제(신용카드 등 사용금액)*/
		YearendtaxVO resultVO9 = yearendtaxDao.getYearendtaxInfo9(paramMap);
		
		if (resultVO9 == null) {
			resultVO9 = new YearendtaxVO();
		}
		
		int etc_credt_amt_ly = resultVO9.getEtc_credt_amt_ly();
		int etc_cash_receipt_amt_ly = resultVO9.getEtc_cash_receipt_amt_ly();
		int etc_direct_amt_ly = resultVO9.getEtc_direct_amt_ly();
		int etc_market_amt_ly = resultVO9.getEtc_market_amt_ly();
		int etc_pubric_transport_amt_ly = resultVO9.getEtc_pubric_transport_amt_ly();
		int etc_credt_amt_fh = resultVO9.getEtc_credt_amt_fh();
		int etc_cash_amt_fh = resultVO9.getEtc_cash_amt_fh();
		int etc_direct_amt_fh = resultVO9.getEtc_direct_amt_fh();
		int etc_market_amt_fh = resultVO9.getEtc_market_amt_fh();
		int etc_pubric_transport_amt_fh = resultVO9.getEtc_pubric_transport_amt_fh();
		int etc_credt_amt_sh = resultVO9.getEtc_credt_amt_sh();
		int etc_cash_amt_sh = resultVO9.getEtc_cash_amt_sh();
		int etc_direct_amt_sh = resultVO9.getEtc_direct_amt_sh();
		int etc_market_amt_sh = resultVO9.getEtc_market_amt_sh();
		int etc_pubric_transport_amt_sh = resultVO9.getEtc_pubric_transport_amt_sh();
		
		int compute_1 = etc_credt_amt_ly + etc_cash_receipt_amt_ly + etc_direct_amt_ly + etc_market_amt_ly + etc_pubric_transport_amt_ly;
		int compute_2 = etc_cash_receipt_amt_ly + etc_direct_amt_ly + etc_market_amt_ly + etc_pubric_transport_amt_ly;
		int compute_3 = etc_credt_amt_fh + etc_cash_amt_fh + etc_direct_amt_fh + etc_market_amt_fh + etc_pubric_transport_amt_fh + etc_credt_amt_sh + etc_cash_amt_sh + etc_direct_amt_sh + etc_market_amt_sh + etc_pubric_transport_amt_sh;
		int compute_4 = etc_cash_amt_sh + etc_direct_amt_sh + etc_market_amt_sh + etc_pubric_transport_amt_sh;
		
		returnVO.setEtc_credt_amt(resultVO9.getEtc_credt_amt());
		returnVO.setEtc_cash_receipt_amt(resultVO9.getEtc_cash_receipt_amt());
		returnVO.setEtc_direct_amt(resultVO9.getEtc_direct_amt());
		returnVO.setEtc_market_amt(resultVO9.getEtc_market_amt());
		returnVO.setEtc_pubric_transport_amt(resultVO9.getEtc_pubric_transport_amt());
		returnVO.setCompute_1(compute_1);
		returnVO.setCompute_2(compute_2);
		returnVO.setCompute_3(compute_3);
		returnVO.setCompute_4(compute_4);
		
		/*그밖의 소득공제(기타)*/
		YearendtaxVO resultVO10 = yearendtaxDao.getYearendtaxInfo10(paramMap);
		
		if (resultVO10 == null) {
			resultVO10 = new YearendtaxVO();
		}
		
		returnVO.setEtc_employ_stock_amt(resultVO10.getEtc_employ_stock_amt());
		returnVO.setEtc_chunk_money_amt(resultVO10.getEtc_chunk_money_amt());
		returnVO.setEtc_long_invest_stock_amt(resultVO10.getEtc_long_invest_stock_amt());
		
		/*그밖의 소득공제(기타)*/
		YearendtaxVO resultVO11 = yearendtaxDao.getYearendtaxInfo11(paramMap);
		
		if (resultVO11 == null) {
			resultVO11 = new YearendtaxVO();
		}
		
		returnVO.setEtc_income_tax_reduction_amt(resultVO11.getEtc_income_tax_reduction_amt());
		returnVO.setSmiymjtc_100(resultVO11.getSmiymjtc_100());
		returnVO.setSmiymjtc_50(resultVO11.getSmiymjtc_50());
		returnVO.setYt_salary(resultVO11.getYt_salary());
		returnVO.setEtc_smiymjtc_amt(resultVO11.getEtc_smiymjtc_amt());
		returnVO.setYt_earned_income_amt(resultVO11.getYt_earned_income_amt());
		returnVO.setEtc_children_amt(resultVO11.getEtc_children_amt());
		returnVO.setAnnuity_savng_amt(resultVO11.getAnnuity_savng_amt());
		returnVO.setInsurance_secrity_amt(resultVO11.getInsurance_secrity_amt());
		returnVO.setInsurance_disabled_person_amt(resultVO11.getInsurance_disabled_person_amt());
		returnVO.setMedical_person_amt(resultVO11.getMedical_person_amt());
		returnVO.setMedical_old_amt(resultVO11.getMedical_old_amt());
		returnVO.setMedical_disabled_person_amt(resultVO11.getMedical_disabled_person_amt());
		returnVO.setMedical_etc_amt(resultVO11.getMedical_etc_amt());
		returnVO.setMedical_amt_o(resultVO11.getMedical_amt_o());
		returnVO.setTotal_medical_amt(resultVO11.getTotal_medical_amt());
		returnVO.setMedical_calc(resultVO11.getMedical_calc());
		returnVO.setEducate_person_amt(resultVO11.getEducate_person_amt());
		returnVO.setEducate_kindergarten_amt(resultVO11.getEducate_kindergarten_amt());
		returnVO.setEducate_school_amt(resultVO11.getEducate_school_amt());
		returnVO.setEducate_univ_amt(resultVO11.getEducate_univ_amt());
		returnVO.setEducate_disabled_person_amt(resultVO11.getEducate_disabled_person_amt());
		returnVO.setEdcate_amt_o(resultVO11.getEdcate_amt_o());
		returnVO.setTotal_edcate_amt(resultVO11.getTotal_edcate_amt());
		returnVO.setContribue_politic_amt(resultVO11.getContribue_politic_amt());
		returnVO.setEtc_contribue_politic_amt(resultVO11.getEtc_contribue_politic_amt());
		returnVO.setContribue_law_amt(resultVO11.getContribue_law_amt());
		returnVO.setContribute_trust_amt(resultVO11.getContribute_trust_amt());
		returnVO.setContribue_employ_stock_amt(resultVO11.getContribue_employ_stock_amt());
		returnVO.setContribue_designate_amt(resultVO11.getContribue_designate_amt());
		returnVO.setContribue_religion_amt(resultVO11.getContribue_religion_amt());
		returnVO.setContribute_exception_amt(resultVO11.getContribute_exception_amt());
		returnVO.setYt_standard_deduct_amt(resultVO11.getYt_standard_deduct_amt());
		returnVO.setEtc_sework_tx_amt(resultVO11.getEtc_sework_tx_amt());
		returnVO.setEtc_house_laon_interest_amt(resultVO11.getEtc_house_laon_interest_amt());
		returnVO.setEtc_foreigner_income(resultVO11.getEtc_foreigner_income());
		returnVO.setEtc_foreigner_pay(resultVO11.getEtc_foreigner_pay());
		returnVO.setHouse_month_lent_amt(resultVO11.getHouse_month_lent_amt());
		
		return returnVO;
	}
	
	/* (non-Javadoc)
	 * @see com.hanaph.gw.yt.yearendtax.service.YearendtaxService#getSearchAddressList(java.util.Map)
	 */
	@Override
	public List<YearendtaxAddressVO> getSearchAddressList(Map<String, String> paramMap) {
		return yearendtaxDao.getSearchAddressList(paramMap);
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.yt.yearendtax.service.YearendtaxService#insertYearendtaxAddress(com.hanaph.gw.yt.yearendtax.vo.YearendtaxAddressVO)
	 */
	@Override
	public void insertYearendtaxAddress(Map<String, Object> paramMap) {
		yearendtaxDao.insertYearendtaxAddress(paramMap);

	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.yt.yearendtax.service.YearendtaxService#procYearendtaxAjax(java.util.Map)
	 */
	@Override
	public void getProcedureCall(Map<String, String> paramMap) {
		
		YearendtaxVO paramVO = new YearendtaxVO();
		
		paramVO.setYear((String)paramMap.get("year"));
		paramVO.setAdjst_div((String)paramMap.get("adjst_div"));
		paramVO.setEmp_no((String)paramMap.get("emp_no"));
		paramVO.setSearchType((String)paramMap.get("searchType"));
		
		/*프로시저 호출*/
		yearendtaxDao.getProcedureCall(paramVO);
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.yt.yearendtax.service.YearendtaxService#insertYearendtaxHouse(java.util.Map)
	 */
	@Override
	public String procYearendtaxHouse(Map<String, Object> paramMap) {
		return yearendtaxDao.procYearendtaxHouse(paramMap);
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.yt.yearendtax.service.YearendtaxService#procYearendtaxSaving(java.util.Map)
	 */
	@Override
	public String procYearendtaxSaving(Map<String, Object> paramMap) {
		return yearendtaxDao.procYearendtaxSaving(paramMap);
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.yt.yearendtax.service.YearendtaxService#procYearendtaxFamilyAjax(java.util.Map)
	 */
	@Override
	public String procYearendtaxFamily(Map<String, Object> paramMap) {
		return yearendtaxDao.procYearendtaxFamily(paramMap);
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.yt.yearendtax.service.YearendtaxService#procYearendtaxInsuranceAjax(java.util.Map)
	 */
	@Override
	public String procYearendtaxInsurance(Map<String, Object> paramMap) {
		return yearendtaxDao.procYearendtaxInsurance(paramMap);
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.yt.yearendtax.service.YearendtaxService#procYearendtaxCreditCardAjax(java.util.Map)
	 */
	@Override
	public String procYearendtaxCreditCard(Map<String, Object> paramMap) {
		return yearendtaxDao.procYearendtaxCreditCard(paramMap);
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.yt.yearendtax.service.YearendtaxService#procYearendtaxEducate(java.util.Map)
	 */
	@Override
	public String procYearendtaxEducate(Map<String, Object> paramMap) {
		return yearendtaxDao.procYearendtaxEducate(paramMap);
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.yt.yearendtax.service.YearendtaxService#getYearendtaxAddressList(java.util.Map)
	 */
	@Override
	public List<YearendtaxAddressVO> getYearendtaxAddressList(Map<String, String> paramMap) {
		return yearendtaxDao.getYearendtaxAddressList(paramMap);
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.yt.yearendtax.service.YearendtaxService#procYearendtaxMedical(java.util.Map)
	 */
	@Override
	public String procYearendtaxMedical(Map<String, Object> paramMap) {
		return yearendtaxDao.procYearendtaxMedical(paramMap);
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.yt.yearendtax.service.YearendtaxService#procYearendtaxContribute(java.util.Map)
	 */
	@Override
	public String procYearendtaxContribute(Map<String, Object> paramMap) {
		return yearendtaxDao.procYearendtaxContribute(paramMap);
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.yt.yearendtax.service.YearendtaxService#procYearendtaxPreviousWorkplace(java.util.Map)
	 */
	@Override
	public String procYearendtaxPreviousWorkplace(Map<String, Object> paramMap) {
		return yearendtaxDao.procYearendtaxPreviousWorkplace(paramMap);
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.yt.yearendtax.service.YearendtaxService#updateYearendtaxInfo(java.util.Map)
	 */
	@Override
	public void updateYearendtaxInfo(Map<String, String> paramMap) {
		
		YearendtaxVO paramVO = new YearendtaxVO();
		
		paramVO.setYear((String)paramMap.get("year"));
		paramVO.setAdjst_div((String)paramMap.get("adjst_div"));
		paramVO.setEmp_no((String)paramMap.get("emp_no"));
		paramVO.setHouse_lease_loan_amt(Integer.parseInt((String)paramMap.get("house_lease_loan_amt").replaceAll(",", "")));
		paramVO.setHouse_security_loan1_amt(Integer.parseInt((String)paramMap.get("house_security_loan1_amt").replaceAll(",", "")));
		paramVO.setHouse_security_loan2_amt(Integer.parseInt((String)paramMap.get("house_security_loan2_amt").replaceAll(",", "")));
		paramVO.setHouse_security_loan3_amt(Integer.parseInt((String)paramMap.get("house_security_loan3_amt").replaceAll(",", "")));
		paramVO.setHouse_security_loan4_amt(Integer.parseInt((String)paramMap.get("house_security_loan4_amt").replaceAll(",", "")));
		paramVO.setHouse_security_loan5_amt(Integer.parseInt((String)paramMap.get("house_security_loan5_amt").replaceAll(",", "")));
		paramVO.setEtc_employ_stock_amt(Integer.parseInt((String)paramMap.get("etc_employ_stock_amt").replaceAll(",", "")));
		paramVO.setEtc_chunk_money_amt(Integer.parseInt((String)paramMap.get("etc_chunk_money_amt").replaceAll(",", "")));
		paramVO.setEtc_income_tax_reduction_amt(Integer.parseInt((String)paramMap.get("etc_income_tax_reduction_amt").replaceAll(",", "")));
		paramVO.setEtc_contribue_politic_amt(Integer.parseInt((String)paramMap.get("etc_contribue_politic_amt").replaceAll(",", "")));
		paramVO.setEtc_sework_tx_amt(Integer.parseInt((String)paramMap.get("etc_sework_tx_amt").replaceAll(",", "")));
		paramVO.setEtc_house_laon_interest_amt(Integer.parseInt((String)paramMap.get("etc_house_laon_interest_amt").replaceAll(",", "")));
		paramVO.setEtc_foreigner_income(Integer.parseInt((String)paramMap.get("etc_foreigner_income").replaceAll(",", "")));
		paramVO.setEtc_foreigner_pay(Integer.parseInt((String)paramMap.get("etc_foreigner_pay").replaceAll(",", "")));
		
		yearendtaxDao.updateYearendtaxInfo(paramVO);
	}

}
