/**
  * Hana Project
  * Copyright 2014 iRush Co.,
  *
  */
package com.hanaph.gw.ea.mgrDocuInfo.dao;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

import com.hanaph.gw.ea.mgrDocuInfo.vo.DocumentInfoVO;


/**
 * <pre>
 * Class Name : DocumentInfoDAOImpl.java
 * 설명 : 양식정보관리 DAOImpl
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
@Repository("documentInfoDao")
public class DocumentInfoDAOImpl extends SqlSessionDaoSupport implements
		DocumentInfoDAO {

	/* (non-Javadoc)
	 * @see com.hanaph.gw.ea.mgrDocuInfo.dao.DocumentInfoDAO#getSalaryList(java.util.Map)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<DocumentInfoVO> getDocumentInfoList(Map<String, String> paramMap) {
		return (List<DocumentInfoVO>)getSqlSession().selectList("mgrDocuInfo.getDocumentInfoList", paramMap);
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.ea.mgrDocuInfo.dao.DocumentInfoDAO#getDocumentInfoCount(java.util.Map)
	 */
	@Override
	public int getDocumentInfoCount(Map<String, String> paramMap) {
		return (Integer)getSqlSession().selectOne("mgrDocuInfo.getDocumentInfoCount", paramMap);
	}

	/*
	 * (non-Javadoc)
	 * @see com.hanaph.gw.ea.mgrDocuInfo.dao.DocumentInfoDAO#getDocumentInfoDetail(java.util.Map)
	 */
	@Override
	public DocumentInfoVO getDocumentInfoDetail(Map<String, String> paramMap) {
		return (DocumentInfoVO)getSqlSession().selectOne("mgrDocuInfo.getDocumentInfoDetail", paramMap);
	}

	/*
	 * (non-Javadoc)
	 * @see com.hanaph.gw.ea.mgrDocuInfo.dao.DocumentInfoDAO#updateDocumentInfo(com.hanaph.gw.ea.mgrDocuInfo.vo.DocumentInfoVO)
	 */
	@Override
	public int updateDocumentInfo(DocumentInfoVO documentInfoVO) {
		return (Integer)getSqlSession().update("mgrDocuInfo.updateDocumentInfo", documentInfoVO);
	}

	/*
	 * (non-Javadoc)
	 * @see com.hanaph.gw.ea.mgrDocuInfo.dao.DocumentInfoDAO#selectDocuSeq(java.util.Map)
	 */
	@Override
	public String selectDocuSeq(Map<String, String> paramMap) {
		return (String)getSqlSession().selectOne("mgrDocuInfo.selectDocuSeq", paramMap);
	}
}
