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

import com.hanaph.gw.ea.newReport.vo.IncompanyVO;

/**
 * <pre>
 * Class Name : NewReportE01003DAOImpl.java
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
@Repository("newReportE01003Dao")
public class NewReportE01003DAOImpl extends SqlSessionDaoSupport implements NewReportE01003DAO {

	@Autowired
	SqlSessionFactory sqlSessionFactory;

	@Override
	public void insertIncompany(IncompanyVO paramIcVO) {
		getSqlSession().insert("newReportE01003.insertIncompany", paramIcVO);
	}

	@Override
	public IncompanyVO incompanyDetail(String approval_seq) {
		return (IncompanyVO)getSqlSession().selectOne("newReportE01003.incompanyDetail", approval_seq);

	}

	@Override
	public void updateIncompany(IncompanyVO paramIcVO) {
		getSqlSession().update("newReportE01003.updateIncompany", paramIcVO);
	}


}
