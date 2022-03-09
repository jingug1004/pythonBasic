/**
  * Hana Project
  * Copyright 2014 iRush Co.,
  *
  */
package com.hanaph.gw.ea.newReport.dao;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hanaph.gw.ea.newReport.vo.DraftVO;

/**
 * <pre>
 * Class Name : NewReportE01002DAOImpl.java
 * 설명 : 
 * 
 * Modification Information
 *    수정일      수정자              수정 내용
 *  ------------ -------------- --------------------------------
 *  2015. 1. 16.      Jung.Jin.Muk          
 * </pre>
 * 
 * @version : 
 * @author  : Jung.Jin.Muk(pc123pc@irush.co.kr)
 * @since   : 2015. 1. 16.
 */
@Repository("newReportE01002Dao")
public class NewReportE01002DAOImpl extends SqlSessionDaoSupport implements NewReportE01002DAO {

	@Autowired
	SqlSessionFactory sqlSessionFactory;
	
	@Override
	public void insertDraft(DraftVO paramDfVO) {
		getSqlSession().insert("newReportE01002.insertDraft", paramDfVO);
	}

	@Override
	public DraftVO draftDetail(String approval_seq) {
		return (DraftVO)getSqlSession().selectOne("newReportE01002.draftDetail", approval_seq);
	}

	@Override
	public void updateDraft(DraftVO paramDfVO) {
		getSqlSession().update("newReportE01002.updateDraft", paramDfVO);
	}


}
