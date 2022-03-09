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

import com.hanaph.gw.ea.newReport.vo.VaporizeVO;

/**
 * <pre>
 * Class Name : NewReportE01004DAOImpl.java
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
@Repository("newReportE01004Dao")
public class NewReportE01004DAOImpl extends SqlSessionDaoSupport implements NewReportE01004DAO {

	@Autowired
	SqlSessionFactory sqlSessionFactory;

	@Override
	public void insertVaporize(VaporizeVO paramVrVO) {
		getSqlSession().insert("newReportE01004.insertVaporize", paramVrVO);
	}

	@Override
	public VaporizeVO vaporizeDetail(String approval_seq) {
		return (VaporizeVO)getSqlSession().selectOne("newReportE01004.vaporizeDetail", approval_seq);

	}

	@Override
	public void updateVaporize(VaporizeVO paramVrVO) {
		getSqlSession().update("newReportE01004.updateVaporize", paramVrVO);
	}


	

}
