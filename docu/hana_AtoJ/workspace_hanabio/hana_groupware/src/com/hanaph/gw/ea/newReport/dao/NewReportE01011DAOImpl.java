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

import com.hanaph.gw.ea.newReport.vo.AccidentVO;

/**
 * <pre>
 * Class Name : NewReportE01011DAOImpl.java
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
@Repository("newReportE01011Dao")
public class NewReportE01011DAOImpl extends SqlSessionDaoSupport implements NewReportE01011DAO {

	@Autowired
	SqlSessionFactory sqlSessionFactory;

	@Override
	public void insertAccident(AccidentVO paramAdVO) {
		getSqlSession().insert("newReportE01011.insertAccident", paramAdVO);
	}

	@Override
	public AccidentVO accidentDetail(String approval_seq) {
		return (AccidentVO)getSqlSession().selectOne("newReportE01011.accidentDetail", approval_seq);

	}

	@Override
	public void updateAccident(AccidentVO paramAdVO) {
		getSqlSession().update("newReportE01011.updateAccident", paramAdVO);
	}

	

}
