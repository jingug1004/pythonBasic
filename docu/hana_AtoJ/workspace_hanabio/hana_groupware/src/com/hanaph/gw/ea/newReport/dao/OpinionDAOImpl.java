/**
  * Hana Project
  * Copyright 2014 iRush Co.,
  *
  */
package com.hanaph.gw.ea.newReport.dao;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

import com.hanaph.gw.co.fileAttach.vo.FileAttachVO;
import com.hanaph.gw.ea.newReport.vo.NewMadicineVO;
import com.hanaph.gw.ea.newReport.vo.OpinionVO;

/**
 * <pre>
 * Class Name : OpinionDAOImpl.java
 * 설명 : 의견 DAOImpl
 * 
 * Modification Information
 *    수정일      수정자              수정 내용
 *  ------------ -------------- --------------------------------
 *  2015. 1. 20.      CHOIILJI         
 * </pre>
 * 
 * @version : 1.0
 * @author  : CHOIILJI(choiilji@irush.co.kr)
 * @since   : 2015. 1. 20.
 */

@Repository("opinionDao")
public class OpinionDAOImpl extends SqlSessionDaoSupport implements OpinionDAO {

	/* (non-Javadoc)
	 * @see com.hanaph.gw.ea.newReport.dao.OpinionDAO#getOpinionList(java.util.Map)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<OpinionVO> getOpinionList(Map<String, String> paramMap) {
		return (List<OpinionVO>)getSqlSession().selectList("newReport.getOpinionList", paramMap);
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.ea.newReport.dao.OpinionDAO#insertOpinion(com.hanaph.gw.ea.newReport.vo.OpinionVO)
	 */
	@Override
	public int insertOpinion(OpinionVO opinionVO) {
		return (Integer)getSqlSession().update("newReport.insertOpinion", opinionVO);		
	}

	/*
	 * (non-Javadoc)
	 * @see com.hanaph.gw.ea.newReport.dao.OpinionDAO#deleteOpinion(java.util.Map)
	 */
	@Override
	public int deleteOpinion(Map<String, String> paramMap) {
		return (Integer)getSqlSession().update("newReport.deleteOpinion", paramMap);
	}

	/*
	 * (non-Javadoc)
	 * @see com.hanaph.gw.ea.newReport.dao.OpinionDAO#deleteOpinionAll(java.util.Map)
	 */
	@Override
	public int deleteOpinionAll(Map<String, String> paramMap) {
		return (Integer)getSqlSession().update("newReport.deleteOpinionAll", paramMap);
	}

	
	/* (non-Javadoc)
	 * @see com.hanaph.gw.ea.newReport.dao.OpinionDAO#insertFileOpinion(com.hanaph.gw.co.fileAttach.vo.FileAttachVO)
	 */
	@Override
	public int insertFileOpinion(FileAttachVO uploadVO) {		
		getSqlSession().insert("newReport.insertFileOpinion", uploadVO);
		return uploadVO.getFile_seq();
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.ea.newReport.dao.OpinionDAO#updateFileOpinion(java.util.Map)
	 */
	@Override
	public void updateFileOpinion(Map<String, String> paramMap) {
		getSqlSession().update("newReport.updateFileOpinion", paramMap);
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.ea.newReport.dao.OpinionDAO#getFileOpinionList(java.util.Map)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<FileAttachVO> getFileOpinionList(Map<String, String> paramMap) {
		return getSqlSession().selectList("newReport.getFileOpinionList",paramMap);
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.ea.newReport.dao.OpinionDAO#deleteFileOpinion(com.hanaph.gw.co.fileAttach.vo.FileAttachVO)
	 */
	@Override
	public int deleteFileOpinion(FileAttachVO fileParamVO) {
		return getSqlSession().update("newReport.deleteFileOpinion", fileParamVO);
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.ea.newReport.dao.OpinionDAO#getOriginFileOpinionNm(java.lang.String)
	 */
	@Override
	public String getOriginFileOpinionNm(String file_seq) {
		return (String) getSqlSession().selectOne("newReport.getOriginFileOpinionNm", file_seq);
	}

}
