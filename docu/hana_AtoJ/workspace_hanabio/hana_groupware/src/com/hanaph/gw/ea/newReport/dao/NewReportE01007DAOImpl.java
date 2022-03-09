/**
  * Hana Project
  * Copyright 2014 iRush Co.,
  *
  */
package com.hanaph.gw.ea.newReport.dao;

import java.util.List;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hanaph.gw.ea.newReport.vo.ApprovalMasterVO;
import com.hanaph.gw.ea.newReport.vo.SampleVO;

/**
 * <pre>
 * Class Name : NewReportE01007DAOImpl.java
 * 설명 : 샘플신청서 DAOImpl
 * 
 * Modification Information
 *    수정일      수정자              수정 내용
 *  ------------ -------------- --------------------------------
 *  2015. 1. 16.      CHOIILJI         
 * </pre>
 * 
 * @version : 1.0
 * @author  : CHOIILJI(choiilji@irush.co.kr)
 * @since   : 2015. 1. 16.
 */
@Repository("newReportE01007Dao")
public class NewReportE01007DAOImpl extends SqlSessionDaoSupport implements NewReportE01007DAO {

	@Autowired
	SqlSessionFactory sqlSessionFactory;
	
	/* (non-Javadoc)
	 * @see com.hanaph.gw.ea.newReport.dao.NewReportE01007DAO#insertSample(com.hanaph.gw.ea.newReport.vo.ApprovalMasterVO, com.hanaph.gw.ea.newReport.vo.SampleVO)
	 */
	@Override
	public String insertSample(ApprovalMasterVO paramAmVO, SampleVO paramSpVO) {
		SqlSession sqlBatchSession = sqlSessionFactory.openSession(ExecutorType.BATCH);
		String approval_seq = "";
		try{
			sqlBatchSession.commit(false);
			sqlBatchSession.insert("newReport.insertApprovalMaster", paramAmVO);
			approval_seq = paramAmVO.getApproval_seq();
			
			List<SampleVO> sampleList = paramSpVO.getSampleVO(); 
			if(sampleList.size() > 0){
				for (int i = 0; i < sampleList.size(); i++) {
					SampleVO sampleVO = new SampleVO();
					sampleVO = sampleList.get(i);
					sampleVO.setApproval_seq(approval_seq);
					sqlBatchSession.insert("newReportE01007.insertSample", sampleVO);
				}
			}
			
			sqlBatchSession.commit();
			return approval_seq;
		}catch(Exception ex){
			ex.printStackTrace();
			sqlBatchSession.rollback();
            return "";
		}finally{
			sqlBatchSession.close();	
		}
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.ea.newReport.dao.NewReportE01007DAO#sampleDetailList(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<SampleVO> sampleDetailList(String approval_seq) {
		return (List<SampleVO>)getSqlSession().selectList("newReportE01007.sampleDetailList", approval_seq);

	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.ea.newReport.dao.NewReportE01007DAO#updateSample(com.hanaph.gw.ea.newReport.vo.ApprovalMasterVO, com.hanaph.gw.ea.newReport.vo.SampleVO)
	 */
	@Override
	public void updateSample(ApprovalMasterVO paramAmVO, SampleVO paramSpVO) {
		SqlSession sqlBatchSession = sqlSessionFactory.openSession(ExecutorType.BATCH);
		String approval_seq = paramAmVO.getApproval_seq();
		try{
			sqlBatchSession.commit(false);
			//결재마스터 업데이트
			sqlBatchSession.update("newReport.updateApprovalMaster", paramAmVO);
			
			//삭제후 저장 한다.
			paramSpVO.setApproval_seq(approval_seq);
			sqlBatchSession.delete("newReportE01007.deleteSample", paramSpVO);
			
			List<SampleVO> sampleList = paramSpVO.getSampleVO(); 
			if(sampleList.size() > 0){
				for (int i = 0; i < sampleList.size(); i++) {
					SampleVO sampleVO = new SampleVO();
					sampleVO = sampleList.get(i);
					sampleVO.setApproval_seq(approval_seq);
					sqlBatchSession.insert("newReportE01007.insertSample", sampleVO);
				}
			}
			sqlBatchSession.commit();
			
		}catch(Exception ex){
			ex.printStackTrace();
			sqlBatchSession.rollback();
		}finally{
			sqlBatchSession.close();	
		}
		
	}

}
