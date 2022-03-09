/**
  * Hana Project
  * Copyright 2014 iRush Co.,
  *
  */
package com.hanaph.gw.hr.personnel.dao;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

import com.hanaph.gw.hr.personnel.vo.PersonnelVO;


/**
 * <pre>
 * Class Name : PersonnelDAOImpl.java
 * 설명 : 인사현황 정보 DAO 구현한 class
 * 
 * Modification Information
 *    수정일      수정자              수정 내용
 *  ------------ -------------- --------------------------------
 *  2014. 10. 23.      CHOIILJI         
 * </pre>
 * 
 * @version : 1.0
 * @author  : CHOIILJI(choiilji@irush.co.kr)
 * @since   : 2014. 10. 23.
 */
@Repository("personnel")
public class PersonnelDAOImpl extends SqlSessionDaoSupport implements
		PersonnelDAO {

	/*
	 * (non-Javadoc)
	 * @see com.hanaph.gw.personnel.dao.PersonnelDAO#getPersonnelCountList(java.util.Map)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PersonnelVO> getPersonnelCountList(Map<String, String> paramMap) {
		return (List<PersonnelVO>)getSqlSession().selectList("personnel.getPersonnelCountList", paramMap);
	}

	/*
	 * (non-Javadoc)
	 * @see com.hanaph.gw.personnel.dao.PersonnelDAO#getAppointmentCountList(java.util.Map)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PersonnelVO> getAppointmentCountList(
			Map<String, String> paramMap) {
		return (List<PersonnelVO>)getSqlSession().selectList("personnel.getAppointmentCountList", paramMap);
	}

}
