/**
  * Hana Project
  * Copyright 2014 iRush Co.,
  *
  */
package com.hanaph.gw.ea.implement.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hanaph.gw.ea.implement.dao.ImplementReportDAO;
import com.hanaph.gw.ea.implement.vo.ImplementReportVO;

/**
 * <pre>
 * Class Name : ImplementReportServiceImpl.java
 * 설명 : 시행문서조회 Service Impl
 * 
 * Modification Information
 *    수정일      수정자              수정 내용
 *  ------------ -------------- --------------------------------
 *  2015. 1. 16.      CHOIILJI         
 * </pre>
 * 
 * @version : 1.0
 * @author  : CHOIILJI(choiilji@irush.co.kr)
 * @since   : 2015. 1. 16.
 */
@Service("implementReportService")
public class ImplementReportServiceImpl implements ImplementReportService {

	@Autowired
	private ImplementReportDAO implementReportDao;
	
	/* (non-Javadoc)
	 * @see com.hanaph.gw.ea.implement.service.ImplementService#getImplementList(java.util.Map)
	 */
	@Override
	public List<ImplementReportVO> getImplementList(Map<String, String> paramMap) {
		return implementReportDao.getImplementList(paramMap);
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.ea.implement.service.ImplementService#getImplementCount(java.util.Map)
	 */
	@Override
	public int getImplementCount(Map<String, String> paramMap) {
		return implementReportDao.getImplementCount(paramMap);
	}

}
