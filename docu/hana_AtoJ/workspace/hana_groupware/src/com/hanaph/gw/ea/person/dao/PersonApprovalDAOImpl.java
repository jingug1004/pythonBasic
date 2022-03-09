/**
  * Hana Project
  * Copyright 2014 iRush Co.,
  *
  */
package com.hanaph.gw.ea.person.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hanaph.gw.ea.person.vo.PersonApprovalVO;
import com.hanaph.gw.ea.person.vo.PersonImplDeptVO;
import com.hanaph.gw.ea.person.vo.PersonLineVO;

/**
 * <pre>
 * Class Name : PersonApprovalDAOImpl.java
 * 설명 : 개인결재라인 DAOImpl
 * 
 * Modification Information
 *    수정일      수정자              수정 내용
 *  ------------ -------------- --------------------------------
 *  2014. 12. 30.      CHOIILJI         
 * </pre>
 * 
 * @version : 1.0
 * @author  : CHOIILJI(choiilji@irush.co.kr)
 * @since   : 2014. 12. 30.
 */
@Repository("personApprovalDao")
public class PersonApprovalDAOImpl extends SqlSessionDaoSupport implements PersonApprovalDAO {
	
	@Autowired
	SqlSessionFactory sqlSessionFactory;
	
	/* (non-Javadoc)
	 * @see com.hanaph.gw.ea.person.dao.PersonApprovalDAO#getPersonApprovalDetailList(java.util.Map)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PersonApprovalVO> getPersonApprovalDetailList(
			Map<String, String> paramMap) {
		return (List<PersonApprovalVO>)getSqlSession().selectList("person.getPersonApprovalDetailList", paramMap);
	}

	/*
	 * (non-Javadoc)
	 * @see com.hanaph.gw.ea.person.dao.PersonApprovalDAO#insertPersonApprovalAll(java.util.Map, com.hanaph.gw.ea.person.vo.PersonLineVO, com.hanaph.gw.ea.person.vo.PersonApprovalVO, com.hanaph.gw.ea.person.vo.PersonImplDeptVO)
	 */
	@Override
	public int insertPersonApprovalAll(Map<String, String> paramMap, PersonLineVO personLineVO,
			PersonApprovalVO personApprovalVO,
			PersonImplDeptVO personImplDeptVO) {
		
		SqlSession sqlBatchSession = sqlSessionFactory.openSession(ExecutorType.BATCH);
		
		try{
			sqlBatchSession.commit(false);
			
			//결재라인 시퀀스 가져온다.
			int person_seq = (Integer)sqlBatchSession.selectOne("person.getPersonSeq");
			
			personLineVO.setPerson_seq(person_seq);
			sqlBatchSession.insert("person.insertPersonLine", personLineVO);
			
			//결재
			if(personApprovalVO != null){
				List<PersonApprovalVO> personApprovalVOList = personApprovalVO.getPersonApprovalVO();
				for (int i = 0; i < personApprovalVOList.size(); i++) {
					PersonApprovalVO personApproval = new PersonApprovalVO();
					personApproval = personApprovalVOList.get(i);
					personApproval.setPerson_seq(person_seq);
					sqlBatchSession.insert("person.insertPersonApproval", personApproval);
				}
			}
			
			//시행
			if(personImplDeptVO != null){
				List<PersonImplDeptVO> personImplDeptVOList = personImplDeptVO.getPersonImplDeptVO();
				for (int i = 0; i < personImplDeptVOList.size(); i++) {
					PersonImplDeptVO personImplDept = new PersonImplDeptVO();
					personImplDept = personImplDeptVOList.get(i);
					personImplDept.setPerson_seq(person_seq);
					sqlBatchSession.insert("person.insertPersonImplDept", personImplDept);
				}
			}
			
			sqlBatchSession.commit();
			return person_seq;
		}catch(Exception ex){
			ex.printStackTrace();
			sqlBatchSession.rollback();
			return 0;
		}finally{
			sqlBatchSession.close();	
		}
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.ea.person.dao.PersonApprovalDAO#insertPersonApproval(com.hanaph.gw.ea.person.vo.PersonApprovalVO)
	 */
	@Override
	public int insertPersonApproval(PersonApprovalVO personApprovalVO) {
		SqlSession sqlBatchSession = sqlSessionFactory.openSession(ExecutorType.BATCH);
		return (Integer)sqlBatchSession.insert("person.insertApproval", personApprovalVO);
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.ea.person.dao.PersonApprovalDAO#deletePersonApproval(java.util.Map)
	 */
	@Override
	public int deletePersonApproval(Map<String, String> paramMap) {
		return (Integer)getSqlSession().delete("person.deletePersonApproval", paramMap);
	}

}
