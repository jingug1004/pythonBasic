/**
  * Hana Project
  * Copyright 2014 iRush Co.,
  *
  */
package com.hanaph.gw.ea.newReport.dao;

import java.util.Map;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hanaph.gw.ea.newReport.vo.CommuteVO;

/**
 * <pre>
 * Class Name : NewReportE01005DAOImpl.java
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
@Repository("newReportE01005Dao")
public class NewReportE01005DAOImpl extends SqlSessionDaoSupport implements NewReportE01005DAO {

	@Autowired
	SqlSessionFactory sqlSessionFactory;

	@Override
	public void insertCommute(CommuteVO paramCmVO) {
		getSqlSession().insert("newReportE01005.insertCommute", paramCmVO);
	}

	@Override
	public CommuteVO commuteDetail(String approval_seq) {
		return (CommuteVO)getSqlSession().selectOne("newReportE01005.commuteDetail", approval_seq);
	}
	
	@Override
	public CommuteVO commuteTardy(Map<String, String> paramMap) {
		return (CommuteVO)getSqlSession().selectOne("newReportE01005.commuteTardy", paramMap);
	}

	@Override
	public void updateCommute(CommuteVO paramCmVO) {
		getSqlSession().update("newReportE01005.updateCommute", paramCmVO);
	}


	

}
