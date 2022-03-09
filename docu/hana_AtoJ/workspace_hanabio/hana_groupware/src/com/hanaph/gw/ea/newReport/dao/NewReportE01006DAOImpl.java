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

import com.hanaph.gw.ea.newReport.vo.ParticipationVO;

/**
 * <pre>
 * Class Name : NewReportE01006DAOImpl.java
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
@Repository("newReportE01006Dao")
public class NewReportE01006DAOImpl extends SqlSessionDaoSupport implements NewReportE01006DAO {

	@Autowired
	SqlSessionFactory sqlSessionFactory;

	@Override
	public void insertParticipation(ParticipationVO paramPpVO) {
		getSqlSession().insert("newReportE01006.insertParticipation", paramPpVO);
	}

	@Override
	public ParticipationVO participationDetail(String approval_seq) {
		return (ParticipationVO)getSqlSession().selectOne("newReportE01006.participationDetail", approval_seq);
	}

	@Override
	public void updateParticipation(ParticipationVO paramPpVO) {
		getSqlSession().update("newReportE01006.updateParticipation", paramPpVO);
	}


	

}
