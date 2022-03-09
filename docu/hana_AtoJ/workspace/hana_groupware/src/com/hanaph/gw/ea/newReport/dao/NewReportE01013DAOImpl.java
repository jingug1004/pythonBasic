/**
  * Hana Project
  * Copyright 2014 iRush Co.,
  * CHOE @since   : 2015. 3. 26.
  */
package com.hanaph.gw.ea.newReport.dao;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hanaph.gw.ea.newReport.vo.NewMadicineVO;

/**
 * <pre>
 * Class Name : NewReportE01013DAOImpl.java
 * 설명 : 
 * 
 * Modification Information
 *    수정일      수정자              수정 내용
 *  ------------ -------------- --------------------------------
 *  2015. 3. 26.      shchoe          
 * </pre>
 * 
 * @version : 
 * @author  : shchoe
 * @since   : 2015. 3. 26.
 */
@Repository("NewReposrE01013DAO")
public class NewReportE01013DAOImpl extends SqlSessionDaoSupport implements NewReportE01013DAO {
	
	@Autowired
	SqlSessionFactory sqlSessionFactory;

	@Override
	public void insertNewMadicine(NewMadicineVO paramVrVO) {
		getSqlSession().insert("newReportE01013.insertNewMadicine", paramVrVO);
	}

	@Override
	public NewMadicineVO NewMadicineDetail(String approval_seq) {
		return (NewMadicineVO)getSqlSession().selectOne("newReportE01013.NewMadicineDetail", approval_seq);

	}

	@Override
	public void updateNewMadicine(NewMadicineVO paramVrVO) {
		getSqlSession().update("newReportE01013.updateNewMadicine", paramVrVO);
	}

}
