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
import com.hanaph.gw.ea.newReport.vo.OvertimeVO;

/**
 * <pre>
 * Class Name : NewReportE01009DAOImpl.java
 * 설명 : 시간외근무신청서 DAOImpl
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
@Repository("newReportE01009Dao")
public class NewReportE01009DAOImpl extends SqlSessionDaoSupport implements NewReportE01009DAO {

	@Autowired
	SqlSessionFactory sqlSessionFactory;
	
	/* (non-Javadoc)
	 * @see com.hanaph.gw.ea.newReport.dao.NewReportE01009DAO#insertOvertime(com.hanaph.gw.ea.newReport.vo.ApprovalMasterVO, com.hanaph.gw.ea.newReport.vo.OvertimeVO)
	 */
	@Override
	public String insertOvertime(ApprovalMasterVO paramAmVO, OvertimeVO paramOtVO) {
		
		SqlSession sqlBatchSession = sqlSessionFactory.openSession(ExecutorType.BATCH);
		String approval_seq = "";
		try{
			sqlBatchSession.commit(false);
			sqlBatchSession.insert("newReport.insertApprovalMaster", paramAmVO);
			approval_seq = paramAmVO.getApproval_seq();
			
			List<OvertimeVO> overtimeList = paramOtVO.getOvertimeVO(); 
			logger.debug("시간외근무신청서>>>>>>>>>>>>>>>" +overtimeList.size() );
			if(overtimeList.size() > 0){
				for (int i = 0; i < overtimeList.size(); i++) {
					OvertimeVO overtimeVO = new OvertimeVO();
					overtimeVO = overtimeList.get(i);
					overtimeVO.setApproval_seq(approval_seq);
					sqlBatchSession.insert("newReportE01009.insertOvertime", overtimeVO);
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
	 * @see com.hanaph.gw.ea.newReport.dao.NewReportE01009DAO#overtimeDetailList(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<OvertimeVO> overtimeDetailList(String approval_seq) {
		return (List<OvertimeVO>)getSqlSession().selectList("newReportE01009.overtimeDetailList", approval_seq);

	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.ea.newReport.dao.NewReportE01009DAO#updateOvertime(com.hanaph.gw.ea.newReport.vo.ApprovalMasterVO, com.hanaph.gw.ea.newReport.vo.OvertimeVO)
	 */
	@Override
	public void updateOvertime(ApprovalMasterVO paramAmVO, OvertimeVO paramOtVO) {
		
		SqlSession sqlBatchSession = sqlSessionFactory.openSession(ExecutorType.BATCH);
		String approval_seq = paramAmVO.getApproval_seq();
		
		try{
			sqlBatchSession.commit(false);
			//결재마스터 업데이트
			sqlBatchSession.update("newReport.updateApprovalMaster", paramAmVO);
			
			//삭제후 저장 한다.
			paramOtVO.setApproval_seq(approval_seq);
			
			//삭제
			sqlBatchSession.delete("newReportE01009.deleteOvertime", paramOtVO);
			
			List<OvertimeVO> overtimeList = paramOtVO.getOvertimeVO(); 
			logger.debug("시간외근무신청서>>>>>>>>>>>>>>>" +overtimeList.size() );
			if(overtimeList.size() > 0){
				for (int i = 0; i < overtimeList.size(); i++) {
					OvertimeVO overtimeVO = new OvertimeVO();
					overtimeVO = overtimeList.get(i);
					overtimeVO.setApproval_seq(approval_seq);
					sqlBatchSession.update("newReportE01009.insertOvertime", overtimeVO);
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
