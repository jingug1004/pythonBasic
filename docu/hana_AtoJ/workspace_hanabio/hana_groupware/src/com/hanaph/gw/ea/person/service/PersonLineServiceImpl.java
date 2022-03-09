/**
  * Hana Project
  * Copyright 2014 iRush Co.,
  *
  */
package com.hanaph.gw.ea.person.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hanaph.gw.ea.person.dao.PersonLineDAO;
import com.hanaph.gw.ea.person.vo.PersonLineVO;

/**
 * <pre>
 * Class Name : PersonLineServiceImpl.java
 * 설명 : 개인결재라인 Master ServiceImpl
 * 
 * Modification Information
 *    수정일      수정자              수정 내용
 *  ------------ -------------- --------------------------------
 *  2014. 12. 30.      CHOIILJI         
 * </pre>
 * 
 * @version : 1.0
 * @author  : CHOIILJI(choiilji@irush.co.kr)
 * @since   : 2014. 12. 30.
 */
@Service(value="personLineService")
public class PersonLineServiceImpl implements PersonLineService {

	@Autowired
	private PersonLineDAO personLineDao;

	/*
	 * (non-Javadoc)
	 * @see com.hanaph.gw.ea.person.service.PersonLineService#getPersonLineList(java.util.Map)
	 */
	@Override
	public List<PersonLineVO> getPersonLineList(Map<String, String> paramMap) {
		return personLineDao.getPersonLineList(paramMap);
	}

	/*
	 * (non-Javadoc)
	 * @see com.hanaph.gw.ea.person.service.PersonLineService#deletePersonLine(java.util.Map)
	 */
	@Override
	public int deletePersonLine(Map<String, String> paramMap) {
		return personLineDao.deletePersonLine(paramMap);
	}

}
