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

import com.hanaph.gw.ea.newReport.vo.VacationVO;

/**
 * <pre>
 * Class Name : NewReportE01001DAOImpl.java
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
@Repository("newReportE01001Dao")
public class NewReportE01001DAOImpl extends SqlSessionDaoSupport implements NewReportE01001DAO {

	@Autowired
	SqlSessionFactory sqlSessionFactory;

	@Override
	public void insertVacation(VacationVO paramVcVO) {
		getSqlSession().insert("newReportE01001.insertVacation", paramVcVO);
	}

	@Override
	public VacationVO vacationDetail(String approval_seq) {
		return (VacationVO)getSqlSession().selectOne("newReportE01001.vacationDetail", approval_seq);
	}

	@Override
	public void updateVacation(VacationVO paramVcVO) {
		getSqlSession().update("newReportE01001.updateVacation", paramVcVO);
	}


	

}
