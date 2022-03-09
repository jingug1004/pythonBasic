/**
  * Hana Project
  * Copyright 2014 iRush Co.,
  *
  */
package com.hanaph.gw.ea.mgrDocuInfo.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hanaph.gw.ea.mgrDocuInfo.dao.DocumentInfoDAO;
import com.hanaph.gw.ea.mgrDocuInfo.vo.DocumentInfoVO;

/**
 * <pre>
 * Class Name : DocumentInfoServiceImpl.java
 * 설명 : 양식정보관리 ServiceImpl 
 * 
 * Modification Information
 *    수정일      수정자              수정 내용
 *  ------------ -------------- --------------------------------
 *  2014. 12. 18.      CHOIILJI         
 * </pre>
 * 
 * @version : 1.0
 * @author  : CHOIILJI(choiilji@irush.co.kr)
 * @since   : 2014. 12. 18.
 */

@Service(value="documentInfoService")
public class DocumentInfoServiceImpl implements DocumentInfoService {

	@Autowired
	private DocumentInfoDAO documentInfoDao;  
	
	/* (non-Javadoc)
	 * @see com.hanaph.gw.ea.mgrDocuInfo.service.DocumentInfoService#getDocumentInfoList(java.util.Map)
	 */
	@Override
	public List<DocumentInfoVO> getDocumentInfoList(Map<String, String> paramMap) {
		return documentInfoDao.getDocumentInfoList(paramMap);
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.ea.mgrDocuInfo.service.DocumentInfoService#getDocumentInfoCount(java.util.Map)
	 */
	@Override
	public int getDocumentInfoCount(Map<String, String> paramMap) {
		return documentInfoDao.getDocumentInfoCount(paramMap);
	}

	/*
	 * (non-Javadoc)
	 * @see com.hanaph.gw.ea.mgrDocuInfo.service.DocumentInfoService#getDocumentInfoDetail(java.util.Map)
	 */
	@Override
	public DocumentInfoVO getDocumentInfoDetail(Map<String, String> paramMap) {
		return documentInfoDao.getDocumentInfoDetail(paramMap);
	}

	/*
	 * (non-Javadoc)
	 * @see com.hanaph.gw.ea.mgrDocuInfo.service.DocumentInfoService#updateDocumentInfo(com.hanaph.gw.ea.mgrDocuInfo.vo.DocumentInfoVO)
	 */
	@Override
	public int updateDocumentInfo(DocumentInfoVO documentInfoVO) {
		return documentInfoDao.updateDocumentInfo(documentInfoVO);
	}

	/*
	 * (non-Javadoc)
	 * @see com.hanaph.gw.ea.mgrDocuInfo.service.DocumentInfoService#selectDocuSeq(java.util.Map)
	 */
	@Override
	public String selectDocuSeq(Map<String, String> paramMap) {
		return documentInfoDao.selectDocuSeq(paramMap);
	}

}
