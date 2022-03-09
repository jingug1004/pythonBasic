/**
  * Hana Project
  * Copyright 2014 iRush Co.,
  *
  */
package com.hanaph.gw.hr.personnel.service;

import java.util.List;
import java.util.Map;

import com.hanaph.gw.hr.personnel.vo.PersonnelVO;

/**
 * <pre>
 * Class Name : Personnel.java
 * 설명 : 인사현황 정보 Service interface
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
public interface PersonnelService {

	/**
	 * 
	 * <pre>
	 * 1. 개요     : 인사현황리스트
	 * 2. 처리내용 :
	 * </pre>
	 * @Method Name : getPersonnelCountList
	 * @param paramMap
	 * @return
	 */
	public List<PersonnelVO> getPersonnelCountList(Map<String, String> paramMap);
	
	/**
	 * 
	 * <pre>
	 * 1. 개요     : 발령현황리스트 
	 * 2. 처리내용 :
	 * </pre>
	 * @Method Name : getAppointmentCountList
	 * @param paramMap
	 * @return
	 */
	public List<PersonnelVO> getAppointmentCountList(Map<String, String> paramMap);
}

