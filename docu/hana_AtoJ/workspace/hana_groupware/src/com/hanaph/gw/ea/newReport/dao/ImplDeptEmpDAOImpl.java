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

/**
 * <pre>
 * Class Name : ImplDeptEmpDAOImpl.java
 * 설명 : 문서별 시행부서 직원 DAO impl
 * 
 * Modification Information
 *    수정일      수정자              수정 내용
 *  ------------ -------------- --------------------------------
 *  2015. 1. 16.      CHOIILJI         
 * </pre>
 * 
 * @version : 
 * @author  : CHOIILJI(choiilji@irush.co.kr)
 * @since   : 2015. 1. 16.
 */
@Repository("implDeptEmpDao")
public class ImplDeptEmpDAOImpl extends SqlSessionDaoSupport implements ImplDeptEmpDAO {

	@Autowired
	SqlSessionFactory sqlSessionFactory;
	
	/* (non-Javadoc)
	 * @see com.hanaph.gw.ea.newReport.dao.ImplDeptEmpDAO#insertImplDeptEmp(com.hanaph.gw.ea.newReport.vo.ImplDeptEmpVO)
	 */
	@Override
	public boolean insertImplDeptEmp(ImplDeptEmpVO implDeptEmpVO) {
		boolean bResult = false;
		int iResult = (Integer)getSqlSession().update("newReport.insertImplDeptEmp", implDeptEmpVO);
		if(iResult>0){
			bResult = true;
		}
		return bResult;
	}

	/*
	 * (non-Javadoc)
	 * @see com.hanaph.gw.ea.newReport.dao.ImplDeptEmpDAO#updateImplDeptEmp(com.hanaph.gw.ea.newReport.vo.ImplDeptEmpVO)
	 */
	@Override
	public boolean deleteImplDeptEmp(ImplDeptEmpVO implDeptEmpVO) {
		boolean bResult = false;
		int iResult = (Integer)getSqlSession().update("newReport.deleteImplDeptEmp", implDeptEmpVO);
		if(iResult>0){
			bResult = true;
		}
		return bResult;
	}

	/*
	 * (non-Javadoc)
	 * @see com.hanaph.gw.ea.newReport.dao.ImplDeptEmpDAO#updateImplDeptEmpReadYN(com.hanaph.gw.ea.newReport.vo.ImplDeptEmpVO)
	 */
	@Override
	public boolean updateImplDeptEmpReadYN(ImplDeptEmpVO implDeptEmpVO) {
		boolean bResult = false;
		int iResult = (Integer)getSqlSession().update("newReport.updateImplDeptEmpReadYN", implDeptEmpVO);
		if(iResult>0){
			bResult = true;
		}
		return bResult;
	}

	/*
	 * (non-Javadoc)
	 * @see com.hanaph.gw.ea.newReport.dao.ImplDeptEmpDAO#getImplDeptEmpDetail(com.hanaph.gw.ea.newReport.vo.ImplDeptEmpVO)
	 */
	@Override
	public ImplDeptEmpVO getImplDeptEmpDetail(Map<String, String> paramMap) {
		return (ImplDeptEmpVO)getSqlSession().selectOne("newReport.getImplDeptEmpDetail", paramMap);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.hanaph.gw.ea.newReport.dao.ImplDeptEmpDAO#getImplDeptEmpList(java.util.Map)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ImplDeptEmpVO> getImplDeptEmpList(Map<String, String> paramMap) {
		return (List<ImplDeptEmpVO>)getSqlSession().selectList("newReport.getImplDeptEmpList", paramMap);
	}

	/*
	 * (non-Javadoc)
	 * @see com.hanaph.gw.ea.newReport.dao.ImplDeptEmpDAO#updateImplDeptEmpComplete(com.hanaph.gw.ea.newReport.vo.ImplDeptEmpVO)
	 */
	@Override
	public boolean updateImplDeptEmpComplete(ImplDeptEmpVO implDeptEmpVO) {
		boolean bResult = false;
		int iResult = (Integer)getSqlSession().update("newReport.updateImplDeptEmpComplete", implDeptEmpVO);
		if(iResult>0){
			bResult = true;
		}
		return bResult;
	}

	/*
	 * (non-Javadoc)
	 * @see com.hanaph.gw.ea.newReport.dao.ImplDeptEmpDAO#deleteImplDeptEmpComplete(com.hanaph.gw.ea.newReport.vo.ImplDeptEmpVO)
	 */
	@Override
	public boolean deleteImplDeptEmpComplete(ImplDeptEmpVO implDeptEmpVO) {
		boolean bResult = false;
		int iResult = (Integer)getSqlSession().update("newReport.deleteImplDeptEmpComplete", implDeptEmpVO);
		if(iResult>0){
			bResult = true;
		}
		return bResult;
	}

	/*
	 * (non-Javadoc)
	 * @see com.hanaph.gw.ea.newReport.dao.ImplDeptEmpDAO#insertImplDeptEmpInterestYN(com.hanaph.gw.ea.newReport.vo.ImplDeptEmpVO)
	 */
	@Override
	public boolean insertImplDeptEmpInterestYN(ImplDeptEmpVO implDeptEmpVO) {
		
		SqlSession sqlBatchSession = sqlSessionFactory.openSession(ExecutorType.BATCH);
		try{
			sqlBatchSession.commit(false);

			if(implDeptEmpVO.getImplDeptEmpVO().size() > 0){
				for (int i = 0; i < implDeptEmpVO.getImplDeptEmpVO().size(); i++) {
					ImplDeptEmpVO approvalVO = new ImplDeptEmpVO();
					approvalVO = implDeptEmpVO.getImplDeptEmpVO().get(i);
					sqlBatchSession.insert("newReport.insertImplDeptEmpInterestYN", approvalVO);
				}
			}

			sqlBatchSession.commit();
			return true;
		}catch(Exception ex){
			ex.printStackTrace();
			sqlBatchSession.rollback();
            return false;
		}finally{
			sqlBatchSession.close();	
		}
	}
}
