/**
  * Hana Project
  * Copyright 2014 iRush Co.,
  *
  */
package com.hanaph.gw.hr.personnel.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hanaph.gw.hr.personnel.dao.PersonnelDAO;
import com.hanaph.gw.hr.personnel.vo.PersonnelVO;

/**
 * <pre>
 * Class Name : PersonnelServiceImpl.java
 * 설명 : 인사현황 정보 Service 구현한 class
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
@Service(value="personnelService")
public class PersonnelServiceImpl implements PersonnelService {
	@Autowired
	private PersonnelDAO personnelDao;

	/*
	 * (non-Javadoc)
	 * @see com.hanaph.gw.personnel.service.PersonnelService#getPersonnelCountList(java.util.Map)
	 */
	@Override
	public List<PersonnelVO> getPersonnelCountList(Map<String, String> paramMap) {
		return personnelDao.getPersonnelCountList(paramMap);
	}

	/*
	 * (non-Javadoc)
	 * @see com.hanaph.gw.personnel.service.PersonnelService#getAppointmentCountList(java.util.Map)
	 */
	@Override
	public List<PersonnelVO> getAppointmentCountList(
			Map<String, String> paramMap) {
		return personnelDao.getAppointmentCountList(paramMap);
	}
}
