/**
  * Hana Project
  * Copyright 2014 iRush Co.,
  *
  */
package com.hanaph.gw.ea.newReport.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hanaph.gw.ea.newReport.vo.ImplDeptEmpVO;
import com.hanaph.gw.ea.newReport.vo.ImplDeptVO;

/**
 * <pre>
 * Class Name : ImplDeptDAOImpl.java
 * 설명 : 시행부서 DAOImpl 
 * 
 * Modification Information
 *    수정일      수정자              수정 내용
 *  ------------ -------------- --------------------------------
 *  2015. 1. 8.      CHOIILJI         
 * </pre>
 * 
 * @version : 1.0
 * @author  : CHOIILJI(choiilji@irush.co.kr)
 * @since   : 2015. 1. 8.
 */
@Repository("implDeptDao")
public class ImplDeptDAOImpl extends SqlSessionDaoSupport implements
		ImplDeptDAO {

	@Autowired
	SqlSessionFactory sqlSessionFactory;
	
	/* (non-Javadoc)
	 * @see com.hanaph.gw.ea.newReport.dao.ImplDeptDAO#getImplDeptDetailList(java.util.Map)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ImplDeptVO> getImplDeptDetailList(Map<String, String> paramMap) {
		return (List<ImplDeptVO>)getSqlSession().selectList("newReport.getImplDeptDetailList", paramMap);
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.ea.newReport.dao.ImplDeptDAO#insertImplDept(com.hanaph.gw.ea.newReport.vo.ImplDeptVO)
	 */
	@Override
	public int insertImplDept(ImplDeptVO implDeptVO) {
		SqlSession sqlBatchSession = sqlSessionFactory.openSession(ExecutorType.BATCH);
		return (Integer)sqlBatchSession.insert("newReport.insertImplDept", implDeptVO);
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.ea.newReport.dao.ImplDeptDAO#deleteImplDept(java.util.Map)
	 */
	@Override
	public int deleteImplDept(Map<String, String> paramMap) {
		return (Integer)getSqlSession().delete("newReport.deleteImplDept", paramMap);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.hanaph.gw.ea.newReport.dao.ImplDeptDAO#insertImplDeptAndEmp(ImplDeptVO, ImplDeptEmpVO)
	 */
	@Override
	public int insertImplDeptAndEmp(ImplDeptVO implDeptVO, ImplDeptEmpVO implDeptEmpVO) {
		SqlSession sqlBatchSession = sqlSessionFactory.openSession(ExecutorType.BATCH);
		
		try{
			sqlBatchSession.commit(false);
			
			List<ImplDeptVO> implDeptVOList = implDeptVO.getImplDeptVO();
			//시행
			if(implDeptVOList.size() > 0){
				for (int i = 0; i < implDeptVOList.size(); i++) {
					ImplDeptVO implDept = new ImplDeptVO();
					implDept = implDeptVOList.get(i);
					sqlBatchSession.insert("newReport.insertImplDept", implDept);
					implDeptEmpVO.setDept_cd(implDept.getDept_cd());
					sqlBatchSession.insert("newReport.insertImplDeptEmp", implDeptEmpVO);
				}
			}
			sqlBatchSession.commit();
			return 1;
		}catch(Exception ex){
			ex.printStackTrace();
			sqlBatchSession.rollback();
            return 0;
		}finally{
			sqlBatchSession.close();	
		}
	}

}
