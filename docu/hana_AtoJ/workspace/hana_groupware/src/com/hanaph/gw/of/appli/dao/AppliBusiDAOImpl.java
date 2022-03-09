/**
  * Hana Project
  * Copyright 2014 iRush Co.,
  *
  */
package com.hanaph.gw.of.appli.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hanaph.gw.of.appli.vo.AppliBusiVO;
import com.hanaph.gw.of.board.vo.BoardTargetVO;
import com.hanaph.gw.of.board.vo.BoardVO;

/**
 * <pre>
 * Class Name : AppliBusiDAOImpl.java
 * 설명 : 회원정보 DAO 구현한 class
 * 
 * Modification Information
 *    수정일      수정자              수정 내용
 *  ------------ -------------- --------------------------------
 *  2016. 11. 07.      CHOE         
 * </pre>
 * 
 * @version : 
 * @author  : CHOE
 * @since   : 2016. 11. 07.
 */
@Repository("applibusiDao")
public class AppliBusiDAOImpl extends SqlSessionDaoSupport implements AppliBusiDAO {
	
	@Autowired
	SqlSessionFactory sqlSessionFactory;

	/* (non-Javadoc)
	 * @see com.hanaph.gw.of.appli.dao.AppliBusiDAO#insertAppliBusi(AppliBusiVO paramVO)
	 */
	@Override	
	public String insertAppliBusi(AppliBusiVO paramVO) {
		String seq = "";
		
		SqlSession sqlBatchSession = sqlSessionFactory.openSession(ExecutorType.BATCH);

		try{
			sqlBatchSession.insert("appli.insertAppliBusi", paramVO);
			sqlBatchSession.commit();
			seq ="등록하였습니다.";
		}catch(Exception ex){
			ex.printStackTrace();
			sqlBatchSession.rollback();
			seq ="저장에 실패 하였습니다.";
		}finally{
			sqlBatchSession.close();			
		}	
		return seq;
	}	
	
}
