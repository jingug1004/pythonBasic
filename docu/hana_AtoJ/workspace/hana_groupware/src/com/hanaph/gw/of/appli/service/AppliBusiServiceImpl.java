/**
  * Hana Project
  * Copyright 2014 iRush Co.,
  *
  */
package com.hanaph.gw.of.appli.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hanaph.gw.of.appli.dao.AppliBusiDAO;
import com.hanaph.gw.of.appli.vo.AppliBusiVO;

/**
 * <pre>
 * Class Name : AppliBusiServiceImpl.java
 * 설명 : 명함신청서 Service 구현한 class
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
@Service(value="applibusiService")
public class AppliBusiServiceImpl implements AppliBusiService {	

	@Autowired
	private AppliBusiDAO applibusiDao;
	
	@Autowired
	SqlSessionFactory sqlSessionFactory;
	

	
	/* (non-Javadoc)
	 * @see com.hanaph.gw.of.appli.service.AppliBusiService#insertAppliBusi(AppliBusiVO paramVO)
	 */
	@Override
	public String insertAppliBusi(AppliBusiVO paramVO) {
		String seq = applibusiDao.insertAppliBusi(paramVO);
		return seq;		
	}
	
}
