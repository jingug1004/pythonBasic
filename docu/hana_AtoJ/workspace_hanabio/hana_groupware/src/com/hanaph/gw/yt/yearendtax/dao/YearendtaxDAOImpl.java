/**
  * Hana Project
  * Copyright 2014 iRush Co.,
  *
  */
package com.hanaph.gw.yt.yearendtax.dao;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hanaph.gw.co.common.utils.StringUtil;
import com.hanaph.gw.yt.yearendtax.vo.YearendtaxAddressVO;
import com.hanaph.gw.yt.yearendtax.vo.YearendtaxVO;

/**
 * <pre>
 * Class Name : YearendtaxDAOImpl.java
 * 설명 : 연말정산 DAO 구현한 class
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
@Repository("yearendtaxDao")
public class YearendtaxDAOImpl extends SqlSessionDaoSupport implements YearendtaxDAO {
	
	@Autowired
	SqlSessionFactory sqlSessionFactory;

	/* (non-Javadoc)
	 * @see com.hanaph.gw.yearendtax.dao.YearendtaxDAO#getYearendtaxList(java.util.Map)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<YearendtaxVO> getYearendtaxList(Map<String, String> paramMap) {
		return (List<YearendtaxVO>)getSqlSession().selectList("yearendtax.getYearendtaxList", paramMap);
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.yearendtax.dao.YearendtaxDAO#getYearendtaxCount(java.util.Map)
	 */
	@Override
	public int getYearendtaxCount(Map<String, String> paramMap) {
		Integer cnt = (Integer)getSqlSession().selectOne("yearendtax.getYearendtaxCount", paramMap);
		return cnt;
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.yearendtax.dao.YearendtaxDAO#getYearendtaxDetail(java.util.Map)
	 */
	@Override
	public YearendtaxVO getYearendtaxDetail(Map<String, String> paramMap) {
		return (YearendtaxVO)getSqlSession().selectOne("yearendtax.getYearendtaxDetail", paramMap);
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.yt.yearendtax.dao.YearendtaxDAO#getYearendtaxPreviousWorkplaceList(java.util.Map)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<YearendtaxVO> getYearendtaxPreviousWorkplaceList(Map<String, String> paramMap) {
		return (List<YearendtaxVO>)getSqlSession().selectList("yearendtax.getYearendtaxPreviousWorkplaceList", paramMap);
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.yt.yearendtax.dao.YearendtaxDAO#getYearendtaxDependentsDetail(java.util.Map)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<YearendtaxVO> getYearendtaxFamilyList(Map<String, String> paramMap) {
		return (List<YearendtaxVO>)getSqlSession().selectList("yearendtax.getYearendtaxFamilyList", paramMap);
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.yt.yearendtax.dao.YearendtaxDAO#getYearendtaxHouseList(java.util.Map)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<YearendtaxVO> getYearendtaxHouseList(Map<String, String> paramMap) {
		return (List<YearendtaxVO>)getSqlSession().selectList("yearendtax.getYearendtaxHouseList", paramMap);
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.yt.yearendtax.dao.YearendtaxDAO#getYearendtaxCreditCardList(java.util.Map)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<YearendtaxVO> getYearendtaxCreditCardList(Map<String, String> paramMap) {
		return (List<YearendtaxVO>)getSqlSession().selectList("yearendtax.getYearendtaxCreditCardList", paramMap);
	}

	@Override
	public YearendtaxVO getYearendtaxDetail2(Map<String, String> paramMap) {
		return (YearendtaxVO)getSqlSession().selectOne("yearendtax.getYearendtaxDetail2", paramMap);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<YearendtaxVO> getYearendtaxDetail3(Map<String, String> paramMap) {
		return (List<YearendtaxVO>)getSqlSession().selectList("yearendtax.getYearendtaxDetail3", paramMap);
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.yt.yearendtax.dao.YearendtaxDAO#getYearendtaxInsuranceList(java.util.Map)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<YearendtaxVO> getYearendtaxInsuranceList(Map<String, String> paramMap) {
		return (List<YearendtaxVO>)getSqlSession().selectList("yearendtax.getYearendtaxInsuranceList", paramMap);
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.yt.yearendtax.dao.YearendtaxDAO#getYearendtaxMedicalList(java.util.Map)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<YearendtaxVO> getYearendtaxMedicalList(Map<String, String> paramMap) {
		return (List<YearendtaxVO>)getSqlSession().selectList("yearendtax.getYearendtaxMedicalList", paramMap);
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.yt.yearendtax.dao.YearendtaxDAO#getYearendtaxEducateList(java.util.Map)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<YearendtaxVO> getYearendtaxEducateList(Map<String, String> paramMap) {
		return (List<YearendtaxVO>)getSqlSession().selectList("yearendtax.getYearendtaxEducateList", paramMap);
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.yt.yearendtax.dao.YearendtaxDAO#getYearendtaxContributeList(java.util.Map)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<YearendtaxVO> getYearendtaxContributeList(Map<String, String> paramMap) {
		return (List<YearendtaxVO>)getSqlSession().selectList("yearendtax.getYearendtaxContributeList", paramMap);
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.yt.yearendtax.dao.YearendtaxDAO#getYearendtaxSavingList(java.util.Map)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<YearendtaxVO> getYearendtaxSavingList(Map<String, String> paramMap) {
		return (List<YearendtaxVO>)getSqlSession().selectList("yearendtax.getYearendtaxSavingList", paramMap);
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.yt.yearendtax.dao.YearendtaxDAO#getYearendtaxFinancialList(java.util.Map)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<YearendtaxVO> getYearendtaxFinancialList(Map<String, String> paramMap) {
		return (List<YearendtaxVO>)getSqlSession().selectList("yearendtax.getYearendtaxFinancialList", paramMap);
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.yt.yearendtax.dao.YearendtaxDAO#getYearendtaxDependentsList(java.util.Map)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<YearendtaxVO> getYearendtaxDependentsList(Map<String, String> paramMap) {
		return (List<YearendtaxVO>)getSqlSession().selectList("yearendtax.getYearendtaxDependentsList", paramMap);
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.yt.yearendtax.dao.YearendtaxDAO#getYearendtaxInfo1(java.util.Map)
	 */
	@Override
	public YearendtaxVO getYearendtaxInfo1(Map<String, String> paramMap) {
		return (YearendtaxVO)getSqlSession().selectOne("yearendtax.getYearendtaxInfo1", paramMap);
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.yt.yearendtax.dao.YearendtaxDAO#getYearendtaxInfo2(java.util.Map)
	 */
	@Override
	public YearendtaxVO getYearendtaxInfo2(Map<String, String> paramMap) {
		return (YearendtaxVO)getSqlSession().selectOne("yearendtax.getYearendtaxInfo2", paramMap);
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.yt.yearendtax.dao.YearendtaxDAO#getYearendtaxInfo3(java.util.Map)
	 */
	@Override
	public YearendtaxVO getYearendtaxInfo3(Map<String, String> paramMap) {
		return (YearendtaxVO)getSqlSession().selectOne("yearendtax.getYearendtaxInfo3", paramMap);
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.yt.yearendtax.dao.YearendtaxDAO#getYearendtaxInfo4(java.util.Map)
	 */
	@Override
	public YearendtaxVO getYearendtaxInfo4(Map<String, String> paramMap) {
		return (YearendtaxVO)getSqlSession().selectOne("yearendtax.getYearendtaxInfo4", paramMap);
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.yt.yearendtax.dao.YearendtaxDAO#getYearendtaxInfo5(java.util.Map)
	 */
	@Override
	public YearendtaxVO getYearendtaxInfo5(Map<String, String> paramMap) {
		return (YearendtaxVO)getSqlSession().selectOne("yearendtax.getYearendtaxInfo5", paramMap);
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.yt.yearendtax.dao.YearendtaxDAO#getYearendtaxInfo6(java.util.Map)
	 */
	@Override
	public YearendtaxVO getYearendtaxInfo6(Map<String, String> paramMap) {
		return (YearendtaxVO)getSqlSession().selectOne("yearendtax.getYearendtaxInfo6", paramMap);
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.yt.yearendtax.dao.YearendtaxDAO#getYearendtaxInfo7(java.util.Map)
	 */
	@Override
	public YearendtaxVO getYearendtaxInfo7(Map<String, String> paramMap) {
		return (YearendtaxVO)getSqlSession().selectOne("yearendtax.getYearendtaxInfo7", paramMap);
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.yt.yearendtax.dao.YearendtaxDAO#getYearendtaxInfo8(java.util.Map)
	 */
	@Override
	public YearendtaxVO getYearendtaxInfo8(Map<String, String> paramMap) {
		return (YearendtaxVO)getSqlSession().selectOne("yearendtax.getYearendtaxInfo8", paramMap);
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.yt.yearendtax.dao.YearendtaxDAO#getYearendtaxInfo9(java.util.Map)
	 */
	@Override
	public YearendtaxVO getYearendtaxInfo9(Map<String, String> paramMap) {
		return (YearendtaxVO)getSqlSession().selectOne("yearendtax.getYearendtaxInfo9", paramMap);
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.yt.yearendtax.dao.YearendtaxDAO#getYearendtaxInfo10(java.util.Map)
	 */
	@Override
	public YearendtaxVO getYearendtaxInfo10(Map<String, String> paramMap) {
		return (YearendtaxVO)getSqlSession().selectOne("yearendtax.getYearendtaxInfo10", paramMap);
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.yt.yearendtax.dao.YearendtaxDAO#getSearchAddressList(java.util.Map)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<YearendtaxAddressVO> getSearchAddressList(Map<String, String> paramMap) {
		return (List<YearendtaxAddressVO>)getSqlSession().selectList("yearendtax.getSearchAddressList", paramMap);

	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.yt.yearendtax.dao.YearendtaxDAO#getYearendtaxInfo11(java.util.Map)
	 */
	@Override
	public YearendtaxVO getYearendtaxInfo11(Map<String, String> paramMap) {
		return (YearendtaxVO)getSqlSession().selectOne("yearendtax.getYearendtaxInfo11", paramMap);
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.yt.yearendtax.dao.YearendtaxDAO#insertYearendtaxAddress(com.hanaph.gw.yt.yearendtax.vo.YearendtaxAddressVO)
	 */
	@Override
	public void insertYearendtaxAddress(Map<String, Object> paramMap) {
		
		SqlSession sqlBatchSession = sqlSessionFactory.openSession(ExecutorType.BATCH);
		
		String[] emp_no = (String[]) paramMap.get("emp_no"); //사원번호
		String[] tel_no = (String[]) paramMap.get("tel_no"); //휴대전화번호
		String[] zip_cd = (String[]) paramMap.get("zip_cd"); //우편번호
		String[] address1 = (String[]) paramMap.get("address1"); //주소1
		String[] address2 = (String[]) paramMap.get("address2"); //주소2
		
		try{
			
			sqlBatchSession.commit(false);
			
			sqlBatchSession.insert("yearendtax.deleteYearendtaxAddress", emp_no[0]);
			
			if(zip_cd != null ){
				for (int i = 0; i < zip_cd.length; i++) {
					YearendtaxAddressVO addressVO = new YearendtaxAddressVO();
					
					addressVO.setEmp_no(emp_no[i]);
					addressVO.setTel_no(tel_no[i]);
					addressVO.setZip_cd(zip_cd[i].replace("-",""));
					addressVO.setAddress1(address1[i]);
					addressVO.setAddress2(address2[i]);
					
					sqlBatchSession.insert("yearendtax.insertYearendtaxAddress", addressVO);
				}
			}
			
			sqlBatchSession.commit();
			
		}catch(Exception ex){
			ex.printStackTrace();
			sqlBatchSession.rollback();
		}finally{
			sqlBatchSession.close();	
		}
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.yt.yearendtax.dao.YearendtaxDAO#getProcedureCall(com.hanaph.gw.yt.yearendtax.vo.YearendtaxVO)
	 */
	@Override
	public void getProcedureCall(YearendtaxVO yearendtaxVO) {
		Map<String, String> paramMap = new HashMap<String, String>();
		
		String searchType = yearendtaxVO.getSearchType();
		
		if ("all".equals(searchType) || "family".equals(searchType)) {
			paramMap.put("year", yearendtaxVO.getYear());
			paramMap.put("adjst_div", yearendtaxVO.getAdjst_div());
			paramMap.put("emp_no", yearendtaxVO.getEmp_no());
			getSqlSession().selectOne("yearendtax.getFamilyProcedureCall", paramMap);
		}
		
		if ("all".equals(searchType) || "pay".equals(searchType)) {
			paramMap.put("year", yearendtaxVO.getYear());
			paramMap.put("adjst_div", yearendtaxVO.getAdjst_div());
			getSqlSession().selectOne("yearendtax.getPayProcedureCall", paramMap);
		}
		
		
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.yt.yearendtax.dao.YearendtaxDAO#insertYearendtaxHouse(java.util.Map)
	 */
	@Override
	public String procYearendtaxHouse(Map<String, Object> paramMap) {
		
		SqlSession sqlBatchSession = sqlSessionFactory.openSession(ExecutorType.BATCH, false);
		
		try {
			sqlBatchSession.getConnection().setAutoCommit(false);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		String result = "success"; // 결과
		
		try{
			String emp_no = (String) paramMap.get("emp_no");
			String year = (String) paramMap.get("year");
			String adjst_div = (String) paramMap.get("adjst_div");
			String searchType = (String) paramMap.get("searchType");
			String[] house_gb = (String[]) paramMap.get("house_gb"); // 입력선택
			String[] house_nm = (String[]) paramMap.get("house_nm"); // 성명(상호)
			String[] house_jumin = (String[]) paramMap.get("house_jumin"); // 주민번호 or 사업자번호
			String[] house_start_dt = (String[]) paramMap.get("house_start_dt"); // 계약기간 시작
			String[] house_end_dt = (String[]) paramMap.get("house_end_dt"); // 계약기간 종료
			String[] house_amt1 = (String[]) paramMap.get("house_amt1"); // 월세액 합계 
			String[] house_amt2 = (String[]) paramMap.get("house_amt2"); // 공제금액
			String[] house_addr = (String[]) paramMap.get("house_addr"); // 임대차 계약서상 주소지
			
			YearendtaxVO deleteVO = new YearendtaxVO();
			
			deleteVO.setEmp_no(emp_no);
			deleteVO.setYear(year);
			deleteVO.setAdjst_div(adjst_div);
			deleteVO.setSearchType(searchType);
			
			sqlBatchSession.delete("yearendtax.deleteYearendtaxHouse", deleteVO);
			
			if (house_gb != null) {
				for (int i = 0; i < house_gb.length; i++) {
					YearendtaxVO insertVO = new YearendtaxVO();
					
					insertVO.setEmp_no(emp_no);
					insertVO.setYear(year);
					insertVO.setAdjst_div(adjst_div);
					insertVO.setHouse_gb(house_gb[i]);
					insertVO.setHouse_nm(house_nm[i]);
					insertVO.setHouse_jumin(house_jumin[i].replaceAll("-", ""));
					insertVO.setHouse_start_dt(house_start_dt[i].replaceAll("-", ""));
					insertVO.setHouse_end_dt(house_end_dt[i].replaceAll("-", ""));
					insertVO.setHouse_amt1(Integer.parseInt(house_amt1[i].replaceAll(",", "")));
					insertVO.setHouse_amt2(Integer.parseInt(house_amt2[i].replaceAll(",", "")));
					insertVO.setHouse_addr(house_addr[i]);
					
					sqlBatchSession.insert("yearendtax.insertYearendtaxHouse", insertVO);
				}
			}
			
			sqlBatchSession.flushStatements();
			sqlBatchSession.commit();
			
		}catch(RuntimeException ex){
			ex.printStackTrace();
			sqlBatchSession.rollback();
			result = ex.toString();
			
		}finally{
			sqlBatchSession.close();	
			
		}
		
		return result;
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.yt.yearendtax.dao.YearendtaxDAO#procYearendtaxSaving(java.util.Map)
	 */
	@Override
	public String procYearendtaxSaving(Map<String, Object> paramMap) {

		SqlSession sqlBatchSession = sqlSessionFactory.openSession(ExecutorType.BATCH, false);
		
		try {
			sqlBatchSession.getConnection().setAutoCommit(false);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		String result = "success"; // 결과
		
		try{
			sqlBatchSession.commit(false);
			
			String emp_no = (String) paramMap.get("emp_no");
			String year = (String) paramMap.get("year");
			String adjst_div = (String) paramMap.get("adjst_div");
			String searchType = (String) paramMap.get("searchType");
			String[] gongje_gb = (String[]) paramMap.get("gongje_gb"); // 소득공제구분
			String[] bank_nm = (String[]) paramMap.get("bank_nm"); // 금융기관명
			String[] bank_cd = (String[]) paramMap.get("bank_cd"); // 금융기관코드
			String[] account_no = (String[]) paramMap.get("account_no"); // 계좌번호
			String[] in_amt = (String[]) paramMap.get("in_amt"); // 불입금액
			
			YearendtaxVO deleteVO = new YearendtaxVO();
			
			deleteVO.setEmp_no(emp_no);
			deleteVO.setYear(year);
			deleteVO.setAdjst_div(adjst_div);
			deleteVO.setSearchType(searchType);
			
			sqlBatchSession.delete("yearendtax.deleteYearendtaxSaving", deleteVO);
			
			if (gongje_gb != null) {
				for (int i = 0; i < gongje_gb.length; i++) {
					YearendtaxVO insertVO = new YearendtaxVO();
					
					insertVO.setEmp_no(emp_no);
					insertVO.setYear(year);
					insertVO.setAdjst_div(adjst_div);
					insertVO.setSeq(String.valueOf(i+1));
					insertVO.setGongje_gb(gongje_gb[i]);
					insertVO.setBank_nm(bank_nm[i]);
					insertVO.setBank_cd(bank_cd[i]);
					insertVO.setAccount_no(account_no[i]);
					insertVO.setIn_amt(Integer.parseInt(in_amt[i].replaceAll(",", "")));
					
					sqlBatchSession.insert("yearendtax.insertYearendtaxSaving", insertVO);
					
				}
			}
			
			sqlBatchSession.flushStatements();
			sqlBatchSession.commit();
			
		}catch(RuntimeException ex){
			ex.printStackTrace();
			sqlBatchSession.rollback();
			result = ex.toString();
		}finally{
			sqlBatchSession.close();	
		}
		
		return result;
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.yt.yearendtax.dao.YearendtaxDAO#procYearendtaxFamilyAjax(java.util.Map)
	 */
	@Override
	public String procYearendtaxFamily(Map<String, Object> paramMap) {
		
		SqlSession sqlBatchSession = sqlSessionFactory.openSession(ExecutorType.BATCH, false);
		
		try {
			sqlBatchSession.getConnection().setAutoCommit(false);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		String result = "success"; // 결과
		
		try{
			sqlBatchSession.commit(false);
			
			String emp_no = (String) paramMap.get("emp_no");
			String year = (String) paramMap.get("year");
			String adjst_div = (String) paramMap.get("adjst_div");
			String[] foreign_cd = (String[]) paramMap.get("foreign_cd"); // 내외국인
			String[] rel_nm = (String[]) paramMap.get("rel_nm"); // 성명
			String[] rel_cd = (String[]) paramMap.get("rel_cd"); // 관계코드
			String[] rel_jumin_no = (String[]) paramMap.get("rel_jumin_no"); // 관계인주민번호(수정)
			String[] org_rel_jumin_no = (String[]) paramMap.get("org_rel_jumin_no"); // 관계인주민번호(original)
			String[] choose_yn = (String[]) paramMap.get("choose_yn"); // 기본
			String[] pensioner_yn = (String[]) paramMap.get("pensioner_yn"); // 수급자
			String[] foster_child_yn = (String[]) paramMap.get("foster_child_yn"); // 위탁아동
			String[] respect_aged_yn = (String[]) paramMap.get("respect_aged_yn"); // 경로우대
			String[] disabled_person_yn = (String[]) paramMap.get("disabled_person_yn"); // 장애인
			String[] woman_yn = (String[]) paramMap.get("woman_yn"); // 부녀자
			String[] single_parents_yn = (String[]) paramMap.get("single_parents_yn"); // 한부모
			String[] insulance_yn = (String[]) paramMap.get("insulance_yn"); // 보험료
			String[] medical_yn = (String[]) paramMap.get("medical_yn"); // 의료비
			String[] education_yn = (String[]) paramMap.get("education_yn"); // 교육비
			String[] card_yn = (String[]) paramMap.get("card_yn"); // 신용카드
			String[] contribution_yn = (String[]) paramMap.get("contribution_yn"); // 기부금
			String[] delete_yn = (String[]) paramMap.get("delete_yn"); // 삭제여부
			
			
			for (int i = 0; i < rel_jumin_no.length; i++) {
				
				String rel_jumin_no_value = StringUtil.nvl(rel_jumin_no[i]).replaceAll("-", "");
				String org_rel_jumin_no_value = StringUtil.nvl(org_rel_jumin_no[i]).replaceAll("-", "");
				String delete_yn_value = StringUtil.nvl(delete_yn[i]);
				
				YearendtaxVO paramVO = new YearendtaxVO();
				
				paramVO.setEmp_no(emp_no);
				paramVO.setYear(year);
				paramVO.setAdjst_div(adjst_div);
				paramVO.setForeign_cd(Integer.parseInt(foreign_cd[i]));
				paramVO.setRel_nm(rel_nm[i]);
				paramVO.setRel_cd(rel_cd[i]);
				paramVO.setRel_jumin_no(rel_jumin_no_value);
				paramVO.setOrg_rel_jumin_no(org_rel_jumin_no_value);
				paramVO.setChoose_yn(StringUtil.nvl(choose_yn[i], "N"));
				paramVO.setPensioner_yn(StringUtil.nvl(pensioner_yn[i], "N"));
				paramVO.setFoster_child_yn(StringUtil.nvl(foster_child_yn[i], "N"));
				paramVO.setRespect_aged_yn(StringUtil.nvl(respect_aged_yn[i], "N"));
				paramVO.setDisabled_person_yn(StringUtil.nvl(disabled_person_yn[i], "N"));
				paramVO.setWoman_yn(StringUtil.nvl(woman_yn[i], "N"));
				paramVO.setSingle_parents_yn(StringUtil.nvl(single_parents_yn[i], "N"));
				paramVO.setInsulance_yn(StringUtil.nvl(insulance_yn[i], "N"));
				paramVO.setMedical_yn(StringUtil.nvl(medical_yn[i], "N"));
				paramVO.setEducation_yn(StringUtil.nvl(education_yn[i], "N"));
				paramVO.setCard_yn(StringUtil.nvl(card_yn[i], "N"));
				paramVO.setContribution_yn(StringUtil.nvl(contribution_yn[i], "N"));
				
				if ("Y".equals(delete_yn_value)) { // delete - delete flag 존재
					/*삭제*/
					sqlBatchSession.delete("yearendtax.deleteYearendtaxFamily", paramVO);
				} else {
					if ("".equals(org_rel_jumin_no_value)) { // insert - 기존 주민번호가 없음(최초 행 추가)
						/*등록*/
						sqlBatchSession.insert("yearendtax.insertYearendtaxFamily", paramVO);
					} else { // update - 기존 주민번호가 존재
						/*수정*/
						sqlBatchSession.update("yearendtax.updateYearendtaxFamily", paramVO);
					}
				}
			}
			
			sqlBatchSession.flushStatements();
			sqlBatchSession.commit();
			
		}catch(RuntimeException ex){
			ex.printStackTrace();
			sqlBatchSession.rollback();
			result = ex.toString();
		}finally{
			sqlBatchSession.close();	
		}
		
		return result;
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.yt.yearendtax.dao.YearendtaxDAO#procYearendtaxInsuranceAjax(java.util.Map)
	 */
	@Override
	public String procYearendtaxInsurance(Map<String, Object> paramMap) {
		
		SqlSession sqlBatchSession = sqlSessionFactory.openSession(ExecutorType.BATCH, false);
		
		try {
			sqlBatchSession.getConnection().setAutoCommit(false);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		String result = "success"; // 결과
		
		try{
			sqlBatchSession.commit(false);
			
			String emp_no = (String) paramMap.get("emp_no");
			String year = (String) paramMap.get("year");
			String adjst_div = (String) paramMap.get("adjst_div");
			String[] rel_jumin_no = (String[]) paramMap.get("rel_jumin_no"); // 관계인주민번호
			String[] insurance_person_1 = (String[]) paramMap.get("insurance_person_1"); // 보장성보험-국세청
			String[] insurance_person_2 = (String[]) paramMap.get("insurance_person_2"); // 보장성보험-그밖의자료
			String[] insurance_disabled_person_1 = (String[]) paramMap.get("insurance_disabled_person_1"); // 장애인보장성보험-국세청
			String[] insurance_disabled_person_2 = (String[]) paramMap.get("insurance_disabled_person_2"); // 장애인보장성보험-그밖의자료
			
			
			for (int i = 0; i < rel_jumin_no.length; i++) {
				
				YearendtaxVO paramVO = new YearendtaxVO();
				
				paramVO.setEmp_no(emp_no);
				paramVO.setYear(year);
				paramVO.setAdjst_div(adjst_div);
				paramVO.setRel_jumin_no(StringUtil.nvl(rel_jumin_no[i]).replaceAll("-", ""));
				paramVO.setInsurance_person_1(Integer.parseInt(insurance_person_1[i].replaceAll(",", "")));
				paramVO.setInsurance_person_2(Integer.parseInt(insurance_person_2[i].replaceAll(",", "")));
				paramVO.setInsurance_disabled_person_1(Integer.parseInt(insurance_disabled_person_1[i].replaceAll(",", "")));
				paramVO.setInsurance_disabled_person_2(Integer.parseInt(insurance_disabled_person_2[i].replaceAll(",", "")));
				
				sqlBatchSession.update("yearendtax.updateYearendtaxInsurance", paramVO);
			}
			
			sqlBatchSession.flushStatements();
			sqlBatchSession.commit();
			
		}catch(RuntimeException ex){
			ex.printStackTrace();
			sqlBatchSession.rollback();
			result = ex.toString();
		}finally{
			sqlBatchSession.close();	
		}
		
		return result;
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.yt.yearendtax.dao.YearendtaxDAO#procYearendtaxCreditCardAjax(java.util.Map)
	 */
	@Override
	public String procYearendtaxCreditCard(Map<String, Object> paramMap) {
		
		SqlSession sqlBatchSession = sqlSessionFactory.openSession(ExecutorType.BATCH, false);
		
		try {
			sqlBatchSession.getConnection().setAutoCommit(false);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		String result = "success"; // 결과
		
		try{
			sqlBatchSession.commit(false);
			
			String emp_no = (String) paramMap.get("emp_no");
			String year = (String) paramMap.get("year");
			String adjst_div = (String) paramMap.get("adjst_div");
			String[] rel_jumin_no = (String[]) paramMap.get("rel_jumin_no"); // 관계인주민번호
			
			/*2014 상반기*/
			String[] credit_1 = (String[]) paramMap.get("credit_1"); // 신용카드 - 국세청
			String[] credit_2 = (String[]) paramMap.get("credit_2"); // 신용카드 - 그 밖의 금액
			String[] cash_1 = (String[]) paramMap.get("cash_1"); // 현금영수증 - 국세청
			String[] cash_2 = (String[]) paramMap.get("cash_2"); // 현금영수증 - 그 밖의 금액
			String[] direct_1 = (String[]) paramMap.get("direct_1"); // 직불카드 - 국세청
			String[] direct_2 = (String[]) paramMap.get("direct_2"); // 직불카드 - 그 밖의 금액
			String[] market_1 = (String[]) paramMap.get("market_1"); // 전통시장 - 국세청
			String[] market_2 = (String[]) paramMap.get("market_2"); // 전통시장 - 그 밖의 금액
			String[] pubric_transport_1 = (String[]) paramMap.get("pubric_transport_1"); // 대중교통 - 국세청
			String[] pubric_transport_2 = (String[]) paramMap.get("pubric_transport_2"); // 대중교통 - 그 밖의 금액
			
			/*2014 하반기*/
			String[] credit_1_sh = (String[]) paramMap.get("credit_1_sh"); // 신용카드 - 국세청
			String[] credit_2_sh = (String[]) paramMap.get("credit_2_sh"); // 신용카드 - 그 밖의 금액
			String[] cash_1_sh = (String[]) paramMap.get("cash_1_sh"); // 현금영수증 - 국세청
			String[] cash_2_sh = (String[]) paramMap.get("cash_2_sh"); // 현금영수증 - 그 밖의 금액
			String[] direct_1_sh = (String[]) paramMap.get("direct_1_sh"); // 직불카드 - 국세청
			String[] direct_2_sh = (String[]) paramMap.get("direct_2_sh"); // 직불카드 - 그 밖의 금액
			String[] market_1_sh = (String[]) paramMap.get("market_1_sh"); // 전통시장 - 국세청
			String[] market_2_sh = (String[]) paramMap.get("market_2_sh"); // 전통시장 - 그 밖의 금액
			String[] pubric_transport_1_sh = (String[]) paramMap.get("pubric_transport_1_sh"); // 대중교통 - 국세청
			String[] pubric_transport_2_sh = (String[]) paramMap.get("pubric_transport_2_sh"); // 대중교통 - 그 밖의 금액
			
			/*2013*/
			String[] credit_1_ly = (String[]) paramMap.get("credit_1_ly"); // 신용카드 - 국세청
			String[] credit_2_ly = (String[]) paramMap.get("credit_2_ly"); // 신용카드 - 그 밖의 금액
			String[] cash_1_ly = (String[]) paramMap.get("cash_1_ly"); // 현금영수증 - 국세청
			String[] cash_2_ly = (String[]) paramMap.get("cash_2_ly"); // 현금영수증 - 그 밖의 금액
			String[] direct_1_ly = (String[]) paramMap.get("direct_1_ly"); // 직불카드 - 국세청
			String[] direct_2_ly = (String[]) paramMap.get("direct_2_ly"); // 직불카드 - 그 밖의 금액
			String[] market_1_ly = (String[]) paramMap.get("market_1_ly"); // 전통시장 - 국세청
			String[] market_2_ly = (String[]) paramMap.get("market_2_ly"); // 전통시장 - 그 밖의 금액
			String[] pubric_transport_1_ly = (String[]) paramMap.get("pubric_transport_1_ly"); // 대중교통 - 국세청
			String[] pubric_transport_2_ly = (String[]) paramMap.get("pubric_transport_2_ly"); // 대중교통 - 그 밖의 금액
			
			
			for (int i = 0; i < rel_jumin_no.length; i++) {
				
				YearendtaxVO paramVO = new YearendtaxVO();
				
				paramVO.setEmp_no(emp_no);
				paramVO.setYear(year);
				paramVO.setAdjst_div(adjst_div);
				paramVO.setRel_jumin_no(StringUtil.nvl(rel_jumin_no[i]).replaceAll("-", ""));
				paramVO.setCredit_1(Integer.parseInt(credit_1[i].replaceAll(",", "")));
				paramVO.setCredit_2(Integer.parseInt(credit_2[i].replaceAll(",", "")));
				paramVO.setCash_1(Integer.parseInt(cash_1[i].replaceAll(",", "")));
				paramVO.setCash_2(Integer.parseInt(cash_2[i].replaceAll(",", "")));
				paramVO.setDirect_1(Integer.parseInt(direct_1[i].replaceAll(",", "")));
				paramVO.setDirect_2(Integer.parseInt(direct_2[i].replaceAll(",", "")));
				paramVO.setMarket_1(Integer.parseInt(market_1[i].replaceAll(",", "")));
				paramVO.setMarket_2(Integer.parseInt(market_2[i].replaceAll(",", "")));
				paramVO.setPubric_transport_1(Integer.parseInt(pubric_transport_1[i].replaceAll(",", "")));
				paramVO.setPubric_transport_2(Integer.parseInt(pubric_transport_2[i].replaceAll(",", "")));
				paramVO.setCredit_1_sh(Integer.parseInt(credit_1_sh[i].replaceAll(",", "")));
				paramVO.setCredit_2_sh(Integer.parseInt(credit_2_sh[i].replaceAll(",", "")));
				paramVO.setCash_1_sh(Integer.parseInt(cash_1_sh[i].replaceAll(",", "")));
				paramVO.setCash_2_sh(Integer.parseInt(cash_2_sh[i].replaceAll(",", "")));
				paramVO.setDirect_1_sh(Integer.parseInt(direct_1_sh[i].replaceAll(",", "")));
				paramVO.setDirect_2_sh(Integer.parseInt(direct_2_sh[i].replaceAll(",", "")));
				paramVO.setMarket_1_sh(Integer.parseInt(market_1_sh[i].replaceAll(",", "")));
				paramVO.setMarket_2_sh(Integer.parseInt(market_2_sh[i].replaceAll(",", "")));
				paramVO.setPubric_transport_1_sh(Integer.parseInt(pubric_transport_1_sh[i].replaceAll(",", "")));
				paramVO.setPubric_transport_2_sh(Integer.parseInt(pubric_transport_2_sh[i].replaceAll(",", "")));
				paramVO.setCredit_1_ly(Integer.parseInt(credit_1_ly[i].replaceAll(",", "")));
				paramVO.setCredit_2_ly(Integer.parseInt(credit_2_ly[i].replaceAll(",", "")));
				paramVO.setCash_1_ly(Integer.parseInt(cash_1_ly[i].replaceAll(",", "")));
				paramVO.setCash_2_ly(Integer.parseInt(cash_2_ly[i].replaceAll(",", "")));
				paramVO.setDirect_1_ly(Integer.parseInt(direct_1_ly[i].replaceAll(",", "")));
				paramVO.setDirect_2_ly(Integer.parseInt(direct_2_ly[i].replaceAll(",", "")));
				paramVO.setMarket_1_ly(Integer.parseInt(market_1_ly[i].replaceAll(",", "")));
				paramVO.setMarket_2_ly(Integer.parseInt(market_2_ly[i].replaceAll(",", "")));
				paramVO.setPubric_transport_1_ly(Integer.parseInt(pubric_transport_1_ly[i].replaceAll(",", "")));
				paramVO.setPubric_transport_2_ly(Integer.parseInt(pubric_transport_2_ly[i].replaceAll(",", "")));
				
				sqlBatchSession.update("yearendtax.updateYearendtaxCreditCard", paramVO);
			}
			
			sqlBatchSession.flushStatements();
			sqlBatchSession.commit();
			
		}catch(RuntimeException ex){
			ex.printStackTrace();
			sqlBatchSession.rollback();
			result = ex.toString();
		}finally{
			sqlBatchSession.close();	
		}
		
		return result;
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.yt.yearendtax.dao.YearendtaxDAO#procYearendtaxCreditCardAjax(java.util.Map)
	 */
	@Override
	public String procYearendtaxEducate(Map<String, Object> paramMap) {
		
		SqlSession sqlBatchSession = sqlSessionFactory.openSession(ExecutorType.BATCH, false);
		
		try {
			sqlBatchSession.getConnection().setAutoCommit(false);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		String result = "success"; // 결과
		
		try{
			sqlBatchSession.commit(false);
			
			String emp_no = (String) paramMap.get("emp_no");
			String year = (String) paramMap.get("year");
			String adjst_div = (String) paramMap.get("adjst_div");
			String[] rel_jumin_no = (String[]) paramMap.get("rel_jumin_no"); // 주민번호
			String[] edu_org = (String[]) paramMap.get("edu_org"); // 교육기관
			String[] public_tx_amt = (String[]) paramMap.get("public_tx_amt"); // 공과금
			
			YearendtaxVO deleteVO = new YearendtaxVO();
			
			deleteVO.setEmp_no(emp_no);
			deleteVO.setYear(year);
			deleteVO.setAdjst_div(adjst_div);
			
			sqlBatchSession.delete("yearendtax.deleteYearendtaxEducate", deleteVO);
			
			if (rel_jumin_no != null) {
				for (int i = 0; i < rel_jumin_no.length; i++) {
					YearendtaxVO insertVO = new YearendtaxVO();
					
					insertVO.setEmp_no(emp_no);
					insertVO.setYear(year);
					insertVO.setAdjst_div(adjst_div);
					insertVO.setSeq(String.valueOf(i+1));
					insertVO.setRel_jumin_no(rel_jumin_no[i].replaceAll("-", ""));
					insertVO.setEdu_org(edu_org[i]);
					insertVO.setPublic_tx_amt(Integer.parseInt(public_tx_amt[i].replaceAll(",", "")));
					
					sqlBatchSession.insert("yearendtax.insertYearendtaxEducate", insertVO);
					
				}
			}
			
			sqlBatchSession.flushStatements();
			sqlBatchSession.commit();
			
		}catch(RuntimeException ex){
			ex.printStackTrace();
			sqlBatchSession.rollback();
			result = ex.toString();
		}finally{
			sqlBatchSession.close();	
		}
		
		return result;
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.yt.yearendtax.dao.YearendtaxDAO#getYearendtaxAddressList(java.util.Map)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<YearendtaxAddressVO> getYearendtaxAddressList(Map<String, String> paramMap) {
		return (List<YearendtaxAddressVO>)getSqlSession().selectList("yearendtax.getYearendtaxAddressList", paramMap);
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.yt.yearendtax.dao.YearendtaxDAO#procYearendtaxMedical(java.util.Map)
	 */
	@Override
	public String procYearendtaxMedical(Map<String, Object> paramMap) {
		
		SqlSession sqlBatchSession = sqlSessionFactory.openSession(ExecutorType.BATCH, false);
		
		try {
			sqlBatchSession.getConnection().setAutoCommit(false);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		String result = "success"; // 결과
		
		try{
			sqlBatchSession.commit(false);
			
			String emp_no = (String) paramMap.get("emp_no");
			String year = (String) paramMap.get("year");
			String adjst_div = (String) paramMap.get("adjst_div");
			String[] rel_jumin_no = (String[]) paramMap.get("rel_jumin_no"); // 주민번호
			String[] medi_gb = (String[]) paramMap.get("medi_gb"); // 증빙구분
			String[] vendor_no = (String[]) paramMap.get("vendor_no"); // 지급처사업자번호
			String[] vendor_nm = (String[]) paramMap.get("vendor_nm"); // 지급처 사업자명
			String[] card_cnt = (String[]) paramMap.get("card_cnt"); // 지급건수
			String[] card_amt = (String[]) paramMap.get("card_amt"); // 지급금액
			
			YearendtaxVO deleteVO = new YearendtaxVO();
			
			deleteVO.setEmp_no(emp_no);
			deleteVO.setYear(year);
			deleteVO.setAdjst_div(adjst_div);
			
			sqlBatchSession.delete("yearendtax.deleteYearendtaxMedical", deleteVO);
			
			if (rel_jumin_no != null) {
				for (int i = 0; i < rel_jumin_no.length; i++) {
					YearendtaxVO insertVO = new YearendtaxVO();
					
					insertVO.setEmp_no(emp_no);
					insertVO.setYear(year);
					insertVO.setAdjst_div(adjst_div);
					insertVO.setSeq(String.valueOf(i+1));
					insertVO.setRel_jumin_no(rel_jumin_no[i].replaceAll("-", ""));
					insertVO.setMedi_gb(medi_gb[i]);
					insertVO.setVendor_no(vendor_no[i]);
					insertVO.setVendor_nm(vendor_nm[i]);
					insertVO.setCard_cnt(card_cnt[i]);
					insertVO.setCard_amt(Integer.parseInt(card_amt[i].replaceAll(",", "")));
					
					sqlBatchSession.insert("yearendtax.insertYearendtaxMedical", insertVO);
					
				}
			}
			
			sqlBatchSession.flushStatements();
			sqlBatchSession.commit();
			
		}catch(RuntimeException ex){
			ex.printStackTrace();
			sqlBatchSession.rollback();
			result = ex.toString();
		}finally{
			sqlBatchSession.close();	
		}
		
		return result;
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.yt.yearendtax.dao.YearendtaxDAO#procYearendtaxContribute(java.util.Map)
	 */
	@Override
	public String procYearendtaxContribute(Map<String, Object> paramMap) {
		
		SqlSession sqlBatchSession = sqlSessionFactory.openSession(ExecutorType.BATCH, false);
		
		try {
			sqlBatchSession.getConnection().setAutoCommit(false);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		String result = "success"; // 결과
		
		try{
			sqlBatchSession.commit(false);
			
			String emp_no = (String) paramMap.get("emp_no");
			String year = (String) paramMap.get("year");
			String adjst_div = (String) paramMap.get("adjst_div");
			String[] rel_jumin_no = (String[]) paramMap.get("rel_jumin_no"); // 주민번호
			String[] gubun = (String[]) paramMap.get("gubun"); // 구분
			String[] yr_don_div = (String[]) paramMap.get("yr_don_div"); // 기부금유형
			String[] don_amt = (String[]) paramMap.get("don_amt"); // 기부금액
			String[] don_nm = (String[]) paramMap.get("don_nm"); // 기부처상호
			String[] don_no = (String[]) paramMap.get("don_no"); // 기부처사업자번호
			String[] don_receipt_no = (String[]) paramMap.get("don_receipt_no"); // 기부처영수증번호
			
			YearendtaxVO deleteVO = new YearendtaxVO();
			
			deleteVO.setEmp_no(emp_no);
			deleteVO.setYear(year);
			deleteVO.setAdjst_div(adjst_div);
			
			sqlBatchSession.delete("yearendtax.deleteYearendtaxContribute", deleteVO);
			
			if (rel_jumin_no != null) {
				for (int i = 0; i < rel_jumin_no.length; i++) {
					YearendtaxVO insertVO = new YearendtaxVO();
					
					insertVO.setEmp_no(emp_no);
					insertVO.setYear(year);
					insertVO.setAdjst_div(adjst_div);
					insertVO.setSeq(String.valueOf(i+1));
					insertVO.setRel_jumin_no(rel_jumin_no[i].replaceAll("-", ""));
					insertVO.setGubun(gubun[i]);
					insertVO.setYr_don_div(yr_don_div[i]);
					insertVO.setDon_amt(Integer.parseInt(don_amt[i].replaceAll(",", "")));
					insertVO.setDon_nm(don_nm[i]);
					insertVO.setDon_no(don_no[i]);
					insertVO.setDon_receipt_no(don_receipt_no[i]);
					
					sqlBatchSession.insert("yearendtax.insertYearendtaxContribute", insertVO);
					
				}
			}
			
			sqlBatchSession.flushStatements();
			sqlBatchSession.commit();
			
		}catch(RuntimeException ex){
			ex.printStackTrace();
			sqlBatchSession.rollback();
			result = ex.toString();
		}finally{
			sqlBatchSession.close();	
		}
		
		return result;
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.yt.yearendtax.dao.YearendtaxDAO#procYearendtaxPreviousWorkplace(java.util.Map)
	 */
	@Override
	public String procYearendtaxPreviousWorkplace(Map<String, Object> paramMap) {
		
		SqlSession sqlBatchSession = sqlSessionFactory.openSession(ExecutorType.BATCH, false);
		
		try {
			sqlBatchSession.getConnection().setAutoCommit(false);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		String result = "success"; // 결과
		
		try{
			sqlBatchSession.commit(false);
			
			String emp_no = (String) paramMap.get("emp_no");
			String year = (String) paramMap.get("year");
			String adjst_div = (String) paramMap.get("adjst_div");
			String[] seq = (String[]) paramMap.get("seq"); // 일련번호
			String[] corporate_nm = (String[]) paramMap.get("corporate_nm"); // 회사명
			String[] president = (String[]) paramMap.get("president"); // 대표자명
			String[] corporate_no = (String[]) paramMap.get("corporate_no"); // 사업자번호
			String[] start_work_dt = (String[]) paramMap.get("start_work_dt"); // 근무기간 fr
			String[] end_work_dt = (String[]) paramMap.get("end_work_dt"); // 근무기간 to
			String[] start_reduce_dt = (String[]) paramMap.get("start_reduce_dt"); // 감면기간 fr
			String[] end_reduce_dt = (String[]) paramMap.get("end_reduce_dt"); // 감면기간 to
			String[] salary_dt = (String[]) paramMap.get("salary_dt"); // 지급일자
			String[] salary_amt = (String[]) paramMap.get("salary_amt"); // 급여총액
			String[] bonus_amt = (String[]) paramMap.get("bonus_amt"); // 상여총액
			String[] constructive_bonus_amt = (String[]) paramMap.get("constructive_bonus_amt"); // 인정상여
			String[] health_amt = (String[]) paramMap.get("health_amt"); // 건강보험
			String[] employ_amt = (String[]) paramMap.get("employ_amt"); // 고용보험
			String[] kuk_yeon_amt = (String[]) paramMap.get("kuk_yeon_amt"); // 국민연금
			String[] annuity_amt = (String[]) paramMap.get("annuity_amt"); // 개인연금
			String[] income_amt = (String[]) paramMap.get("income_amt"); // 소득세
			String[] jumin_amt = (String[]) paramMap.get("jumin_amt"); // 지방소득세
			String[] nong_amt = (String[]) paramMap.get("nong_amt"); // 농특세
			String[] total_salary = (String[]) paramMap.get("total_salary"); // 근무처별소득명세합계
			String[] stock_option_amt = (String[]) paramMap.get("stock_option_amt"); // 주식매수선택권 행사이익
			String[] employ_stock_amt = (String[]) paramMap.get("employ_stock_amt"); // 우리사주조합인출금
			String[] derector_retirement_amt = (String[]) paramMap.get("derector_retirement_amt"); // 임원퇴직소득금액한도초과액
			String[] total_free = (String[]) paramMap.get("total_free"); // 비과세소득계
			String[] reduction_amt = (String[]) paramMap.get("reduction_amt"); // 감면소득계
			String[] foreign_work_amt = (String[]) paramMap.get("foreign_work_amt"); // 국외근로비과세
			String[] oevrtime_amt = (String[]) paramMap.get("oevrtime_amt"); // 연장근무(야간수당) 비과세
			String[] meternity_amt = (String[]) paramMap.get("meternity_amt"); // 출산보육수당
			String[] research_amt = (String[]) paramMap.get("research_amt"); // 연구보조비
			String[] school_expenses_amt = (String[]) paramMap.get("school_expenses_amt"); // 학자금
			String[] collect_amt = (String[]) paramMap.get("collect_amt"); // 취재수당
			String[] remote_rural_area_amt = (String[]) paramMap.get("remote_rural_area_amt"); // 벽지수당
			String[] natural_disaster_amt = (String[]) paramMap.get("natural_disaster_amt"); // 천재지변 재해로 받는 수당
			String[] stock_purchase_amt = (String[]) paramMap.get("stock_purchase_amt"); // 주식매수선택권
			String[] foreigner_amt = (String[]) paramMap.get("foreigner_amt"); // 외국인기술자소득세면제
			String[] employ_stock_amt1 = (String[]) paramMap.get("employ_stock_amt1"); // 우리사주조합인출금50%
			String[] employ_stock_amt2 = (String[]) paramMap.get("employ_stock_amt2"); // 우리사주조합인출금75%
			String[] guard_embark_amt = (String[]) paramMap.get("guard_embark_amt"); // 경호수당, 승선수당
			String[] smiymjtc_amt = (String[]) paramMap.get("smiymjtc_amt"); // 중소기업청년취업소득세감면
			String[] major_amt = (String[]) paramMap.get("major_amt"); // 전공의수련보조수당
			String[] submarine_mineral_amt = (String[]) paramMap.get("submarine_mineral_amt"); // 해저광물자원개발을위한과세특례
			String[] scholarship_amt = (String[]) paramMap.get("scholarship_amt"); // 교육기본법제28조제1항에따라받는장학금
			String[] organization_amt = (String[]) paramMap.get("organization_amt"); // 외국정부또는국제기관에근무하는사람에대한비과세
			String[] kindergarten_teacher_amt = (String[]) paramMap.get("kindergarten_teacher_amt"); // 시립유치원수석교사의인건비유아교육법시행령
			String[] childcare_amt = (String[]) paramMap.get("childcare_amt"); // 보육교사인건비영유아보육법시행령
			String[] teache_clause_amt = (String[]) paramMap.get("teache_clause_amt"); // 조세조약상교직자조항의소득세감면
			String[] move_amt = (String[]) paramMap.get("move_amt"); // 정부공공기관중지방이전기관종사자이수수당
			String[] legislation_amt = (String[]) paramMap.get("legislation_amt"); // 법령조례에의한보수를받지않는의원수당
			String[] operation_amt = (String[]) paramMap.get("operation_amt"); // 작전임무수행을위해외국에주둔하는군인받는급여
			String[] smiymjtc_rate = (String[]) paramMap.get("smiymjtc_rate"); // 중소기업청년취업소득세감면 금액이 있는 경우 감면 비율
			
			YearendtaxVO deleteVO = new YearendtaxVO();
			
			deleteVO.setEmp_no(emp_no);
			deleteVO.setYear(year);
			deleteVO.setAdjst_div(adjst_div);
			
			sqlBatchSession.delete("yearendtax.deleteYearendtaxPreviousWorkplace", deleteVO);
			
			if (seq != null) {
				for (int i = 0; i < seq.length; i++) {
					YearendtaxVO insertVO = new YearendtaxVO();
					
					insertVO.setEmp_no(emp_no);
					insertVO.setYear(year);
					insertVO.setAdjst_div(adjst_div);
					insertVO.setSeq(seq[i]);
					insertVO.setCorporate_nm(corporate_nm[i]);
					insertVO.setPresident(president[i]);
					insertVO.setCorporate_no(corporate_no[i].replaceAll("-", ""));
					insertVO.setStart_work_dt(start_work_dt[i].replaceAll("-", ""));
					insertVO.setEnd_work_dt(end_work_dt[i].replaceAll("-", ""));
					insertVO.setStart_reduce_dt(start_reduce_dt[i].replaceAll("-", ""));
					insertVO.setEnd_reduce_dt(end_reduce_dt[i].replaceAll("-", ""));
					insertVO.setSalary_dt(salary_dt[i].replaceAll("-", ""));
					insertVO.setSalary_amt(Integer.parseInt(salary_amt[i].replaceAll(",", "")));
					insertVO.setBonus_amt(Integer.parseInt(bonus_amt[i].replaceAll(",", "")));
					insertVO.setConstructive_bonus_amt(Integer.parseInt(constructive_bonus_amt[i].replaceAll(",", "")));
					insertVO.setHealth_amt(Integer.parseInt(health_amt[i].replaceAll(",", "")));
					insertVO.setEmploy_amt(Integer.parseInt(employ_amt[i].replaceAll(",", "")));
					insertVO.setKuk_yeon_amt(Integer.parseInt(kuk_yeon_amt[i].replaceAll(",", "")));
					insertVO.setAnnuity_amt(Integer.parseInt(annuity_amt[i].replaceAll(",", "")));
					insertVO.setIncome_amt(Integer.parseInt(income_amt[i].replaceAll(",", "")));
					insertVO.setJumin_amt(Integer.parseInt(jumin_amt[i].replaceAll(",", "")));
					insertVO.setNong_amt(Integer.parseInt(nong_amt[i].replaceAll(",", "")));
					insertVO.setTotal_salary(Integer.parseInt(total_salary[i].replaceAll(",", "")));
					insertVO.setStock_option_amt(Integer.parseInt(stock_option_amt[i].replaceAll(",", "")));
					insertVO.setEmploy_stock_amt(Integer.parseInt(employ_stock_amt[i].replaceAll(",", "")));
					insertVO.setDerector_retirement_amt(Integer.parseInt(derector_retirement_amt[i].replaceAll(",", "")));
					insertVO.setTotal_free(Integer.parseInt(total_free[i].replaceAll(",", "")));
					insertVO.setReduction_amt(Integer.parseInt(reduction_amt[i].replaceAll(",", "")));
					insertVO.setForeign_work_amt(Integer.parseInt(foreign_work_amt[i].replaceAll(",", "")));
					insertVO.setOevrtime_amt(Integer.parseInt(oevrtime_amt[i].replaceAll(",", "")));
					insertVO.setMeternity_amt(Integer.parseInt(meternity_amt[i].replaceAll(",", "")));
					insertVO.setResearch_amt(Integer.parseInt(research_amt[i].replaceAll(",", "")));
					insertVO.setSchool_expenses_amt(Integer.parseInt(school_expenses_amt[i].replaceAll(",", "")));
					insertVO.setCollect_amt(Integer.parseInt(collect_amt[i].replaceAll(",", "")));
					insertVO.setRemote_rural_area_amt(Integer.parseInt(remote_rural_area_amt[i].replaceAll(",", "")));
					insertVO.setNatural_disaster_amt(Integer.parseInt(natural_disaster_amt[i].replaceAll(",", "")));
					insertVO.setStock_purchase_amt(Integer.parseInt(stock_purchase_amt[i].replaceAll(",", "")));
					insertVO.setForeigner_amt(Integer.parseInt(foreigner_amt[i].replaceAll(",", "")));
					insertVO.setEmploy_stock_amt1(Integer.parseInt(employ_stock_amt1[i].replaceAll(",", "")));
					insertVO.setEmploy_stock_amt2(Integer.parseInt(employ_stock_amt2[i].replaceAll(",", "")));
					insertVO.setGuard_embark_amt(Integer.parseInt(guard_embark_amt[i].replaceAll(",", "")));
					insertVO.setSmiymjtc_amt(Integer.parseInt(smiymjtc_amt[i].replaceAll(",", "")));
					insertVO.setMajor_amt(Integer.parseInt(major_amt[i].replaceAll(",", "")));
					insertVO.setSubmarine_mineral_amt(Integer.parseInt(submarine_mineral_amt[i].replaceAll(",", "")));
					insertVO.setScholarship_amt(Integer.parseInt(scholarship_amt[i].replaceAll(",", "")));
					insertVO.setOrganization_amt(Integer.parseInt(organization_amt[i].replaceAll(",", "")));
					insertVO.setKindergarten_teacher_amt(Integer.parseInt(kindergarten_teacher_amt[i].replaceAll(",", "")));
					insertVO.setChildcare_amt(Integer.parseInt(childcare_amt[i].replaceAll(",", "")));
					insertVO.setTeache_clause_amt(Integer.parseInt(teache_clause_amt[i].replaceAll(",", "")));
					insertVO.setMove_amt(Integer.parseInt(move_amt[i].replaceAll(",", "")));
					insertVO.setLegislation_amt(Integer.parseInt(legislation_amt[i].replaceAll(",", "")));
					insertVO.setOperation_amt(Integer.parseInt(operation_amt[i].replaceAll(",", "")));
					insertVO.setSmiymjtc_rate(smiymjtc_rate[i]);
					
					sqlBatchSession.insert("yearendtax.insertYearendtaxPreviousWorkplace", insertVO);
					
				}
			}
			
			sqlBatchSession.flushStatements();
			sqlBatchSession.commit();
			
		}catch(RuntimeException ex){
			ex.printStackTrace();
			sqlBatchSession.rollback();
			result = ex.toString();
		}finally{
			sqlBatchSession.close();	
		}
		
		return result;
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.yt.yearendtax.dao.YearendtaxDAO#updateYearendtaxInfo(com.hanaph.gw.yt.yearendtax.vo.YearendtaxVO)
	 */
	@Override
	public void updateYearendtaxInfo(YearendtaxVO yearendtaxVO) {
		getSqlSession().update("yearendtax.updateYearendtaxInfo", yearendtaxVO);
	}
}
