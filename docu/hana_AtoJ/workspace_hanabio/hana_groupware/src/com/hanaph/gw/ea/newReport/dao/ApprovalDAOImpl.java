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
import org.apache.log4j.Logger;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hanaph.gw.ea.newReport.vo.ApprovalMasterVO;
import com.hanaph.gw.ea.newReport.vo.ApprovalVO;
import com.hanaph.gw.ea.newReport.vo.ImplDeptVO;

/**
 * <pre>
 * Class Name : ApprovalDAOImpl.java
 * 설명 : step2.결재라인지정 DAOImpl
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
@Repository("approvalDao")
public class ApprovalDAOImpl extends SqlSessionDaoSupport implements ApprovalDAO {
	@Autowired
	SqlSessionFactory sqlSessionFactory;

	private static final Logger logger = Logger.getLogger(ApprovalDAOImpl.class);
	
	/* (non-Javadoc)
	 * @see com.hanaph.gw.ea.newReport.dao.ApprovalDAO#getApprovalDetailList(java.util.Map)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ApprovalVO> getApprovalDetailList(Map<String, String> paramMap) {
		return (List<ApprovalVO>)getSqlSession().selectList("newReport.getApprovalDetailList", paramMap);
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.ea.newReport.dao.ApprovalDAO#insertApprovalAll(java.util.Map, java.util.List, java.util.List, java.util.List)
	 */
	@Override
	public int insertApprovalAll(Map<String, String> paramMap,
			List<ApprovalVO> approvalVOList,
			List<ImplDeptVO> implDeptVOList,
			ApprovalMasterVO approvalMasterVO) {
		SqlSession sqlBatchSession = sqlSessionFactory.openSession(ExecutorType.BATCH);
		
		try{
			sqlBatchSession.commit(false);
			//결재마스터 update
			sqlBatchSession.update("newReport.updateApprovalMasterStepState", approvalMasterVO);
			
			//결재
			logger.debug("결재>>>>>>>>>>>>>>>" +approvalVOList.size() );
			sqlBatchSession.delete("newReport.deleteApproval", paramMap);
			if(approvalVOList.size() > 0){
				for (int i = 0; i < approvalVOList.size(); i++) {
					ApprovalVO approvalVO = new ApprovalVO();
					approvalVO = approvalVOList.get(i);
					sqlBatchSession.insert("newReport.insertApproval", approvalVO);
				}
			}
			
			//시행
			logger.debug("시행>>>>>>>>>>>>>>>" +implDeptVOList.size() );
			sqlBatchSession.delete("newReport.deleteImplDept", paramMap);
			if(implDeptVOList.size() > 0){
				for (int i = 0; i < implDeptVOList.size(); i++) {
					ImplDeptVO implDeptVO = new ImplDeptVO();
					implDeptVO = implDeptVOList.get(i);
					sqlBatchSession.insert("newReport.insertImplDept", implDeptVO);
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

	/* (non-Javadoc)
	 * @see com.hanaph.gw.ea.newReport.dao.ApprovalDAO#insertApproval(com.hanaph.gw.ea.newReport.vo.ApprovalVO)
	 */
	@Override
	public int insertApproval(ApprovalVO approvalVO) {
		SqlSession sqlBatchSession = sqlSessionFactory.openSession(ExecutorType.BATCH);
		return (Integer)sqlBatchSession.insert("newReport.insertApproval", approvalVO);
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.ea.newReport.dao.ApprovalDAO#deleteApproval(java.util.Map)
	 */
	@Override
	public int deleteApproval(Map<String, String> paramMap) {
		return (Integer)getSqlSession().delete("newReport.deleteApproval", paramMap);
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.ea.newReport.dao.ApprovalDAO#getApprovalNowEmpNo(java.util.Map)
	 */
	public ApprovalVO getApprovalNowEmpNo(Map<String, String> paramMap) {
		return (ApprovalVO)getSqlSession().selectOne("newReport.getApprovalNowEmpNo", paramMap);
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.ea.newReport.dao.ApprovalDAO#getApprovalPrevCheckEmpNo(java.util.Map)
	 */
	@Override
	public ApprovalVO getApprovalPrevCheckEmpNo(Map<String, String> paramMap) {
		return (ApprovalVO)getSqlSession().selectOne("newReport.getApprovalPrevCheckEmpNo", paramMap);

	}

}
