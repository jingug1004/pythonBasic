/**
  * Hana Project
  * Copyright 2016 CHOE
  *
  */
package com.hanaph.gw.of.appli.service;

import java.util.List;
import java.util.Map;

import com.hanaph.gw.of.appli.vo.AppliBusiVO;

/**
 * <pre>
 * Class Name : AppliBuisService.java
 * 설명 : 명함신청서 Service interface
 * 
 * Modification Information
 *    수정일      수정자              수정 내용
 *  ------------ -------------- --------------------------------
 *  2016. 11. 07.      CHOE         
 * </pre>
 * 
 * @version : 
 * @author  : CHOE
 * @since   : 2016. 11. 07.
 */
public interface AppliBusiService {
	
	/**
	 * 
	 * <pre>
	 * 1. 개요     : 명함신청 정보를 저장한다.
	 * 2. 처리내용 :
	 * </pre>
	 * @Method Name : insertAppliBusi
	 * @param paramMap
	 * @return
	 */
	public String insertAppliBusi(AppliBusiVO paramVO);
}
