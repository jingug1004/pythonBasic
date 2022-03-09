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

import com.hanaph.gw.ea.newReport.vo.ReviewVO;

/**
 * <pre>
 * Class Name : NewReportE01014DAOImpl.java
 * 설명 : 
 * 
 * Modification Information
 *    수정일      수정자              수정 내용
 *  ------------ -------------- --------------------------------
 *  2015. 12. 10.      개발검토서          
 * </pre>
 * 
 * @version : 
 * @author  : 전산팀
 * @since   : 2015. 12. 10.
 */
@Repository("newReportE01014Dao")
public class NewReportE01014DAOImpl extends SqlSessionDaoSupport implements NewReportE01014DAO {

	@Autowired
	SqlSessionFactory sqlSessionFactory;
	
	@Override
	public void insertReview(ReviewVO paramDfVO) {
		getSqlSession().insert("newReportE01014.insertReview", paramDfVO);
	}

	@Override
	public ReviewVO reviewDetail(String approval_seq) {
		return (ReviewVO)getSqlSession().selectOne("newReportE01014.reviewDetail", approval_seq);
	}

	@Override
	public void updateReview(ReviewVO paramDfVO) {
		getSqlSession().update("newReportE01014.updateReview", paramDfVO);
	}


}
