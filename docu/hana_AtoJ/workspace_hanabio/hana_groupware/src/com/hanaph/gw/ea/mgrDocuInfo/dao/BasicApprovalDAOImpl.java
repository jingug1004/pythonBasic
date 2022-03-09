/**
  * Hana Project
  * Copyright 2014 iRush Co.,
  *
  */
package com.hanaph.gw.ea.mgrDocuInfo.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hanaph.gw.ea.mgrDocuInfo.vo.BasicApprovalVO;
import com.hanaph.gw.ea.mgrDocuInfo.vo.BasicImplDeptVO;

/**
 * <pre>
 * Class Name : BasicApprovalDAOImpl.java
 * 설명 : 문서별 기본결재라인 DAOImpl 
 * 
 * Modification Information
 *    수정일      수정자              수정 내용
 *  ------------ -------------- --------------------------------
 *  2014. 12. 22.      CHOIILJI         
 * </pre>
 * 
 * @version : 1.0
 * @author  : CHOIILJI(choiilji@irush.co.kr)
 * @since   : 2014. 12. 22.
 */
@Repository("basicApprovalDao")
public class BasicApprovalDAOImpl extends SqlSessionDaoSupport implements
		BasicApprovalDAO {
	@Autowired
	SqlSessionFactory sqlSessionFactory;
	
	/*(non-Javadoc)
	 * @see com.hanaph.gw.ea.mgrDocuInfo.dao.BasicApprovalDAO#getBasicApprovalDetailList(java.util.Map)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<BasicApprovalVO> getBasicApprovalDetailList(Map<String, String> paramMap) {
		return (List<BasicApprovalVO>)getSqlSession().selectList("mgrDocuInfo.getBasicApprovalDetailList", paramMap);
	}

	@Override
	public int insertBasicApprovalAll(Map<String, String> paramMap, 
			BasicApprovalVO basicApprovalVO, 
			BasicImplDeptVO basicImplDeptVO) {
		SqlSession sqlBatchSession = sqlSessionFactory.openSession(ExecutorType.BATCH);
		
		try{
			sqlBatchSession.commit(false);
			
			//결재
			sqlBatchSession.delete("mgrDocuInfo.deleteBasicApproval", paramMap);
			if(basicApprovalVO !=null){
				List<BasicApprovalVO> basicApprovalVOList = basicApprovalVO.getBasicApprovalVO();				
				for (int i = 0; i < basicApprovalVOList.size(); i++) {
					BasicApprovalVO basicApproval = new BasicApprovalVO();
					basicApproval = basicApprovalVOList.get(i);
					sqlBatchSession.insert("mgrDocuInfo.insertBasicApproval", basicApproval);
				}
			}
			
			//시행
			sqlBatchSession.delete("mgrDocuInfo.deleteBasicImplDept", paramMap);
			List<BasicImplDeptVO> basicImplDeptVOList = basicImplDeptVO.getBasicImplDeptVO();
			if(basicImplDeptVOList.size() > 0){
				for (int i = 0; i < basicImplDeptVOList.size(); i++) {
				BasicImplDeptVO basicImplDept = new BasicImplDeptVO();
				basicImplDept = basicImplDeptVOList.get(i);
				sqlBatchSession.insert("mgrDocuInfo.insertBasicImplDept", basicImplDept);
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
	
	/*
	 * (non-Javadoc)
	 * @see com.hanaph.gw.ea.mgrDocuInfo.dao.BasicApprovalDAO#insertBasicApproval(com.hanaph.gw.ea.mgrDocuInfo.vo.BasicApprovalVO)
	 */
	@Override
	public int insertBasicApproval(BasicApprovalVO basicApprovalVO) {
		SqlSession sqlBatchSession = sqlSessionFactory.openSession(ExecutorType.BATCH);
		return (Integer)sqlBatchSession.insert("mgrDocuInfo.insertBasicApproval", basicApprovalVO);
	}

	/*
	 * (non-Javadoc)
	 * @see com.hanaph.gw.ea.mgrDocuInfo.dao.BasicApprovalDAO#deleteBasicApproval(java.util.Map)
	 */
	@Override
	public int deleteBasicApproval(Map<String, String> paramMap) {
		return (Integer)getSqlSession().delete("mgrDocuInfo.deleteBasicApproval", paramMap);
	}

}
