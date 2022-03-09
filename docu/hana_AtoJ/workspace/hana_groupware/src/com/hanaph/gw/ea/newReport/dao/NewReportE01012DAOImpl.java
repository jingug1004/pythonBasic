/**
  * Hana Project
  * Copyright 2014 iRush Co.,
  *
  */
package com.hanaph.gw.ea.newReport.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hanaph.gw.ea.newReport.vo.ApprovalMasterVO;
import com.hanaph.gw.ea.newReport.vo.ApprovalVO;
import com.hanaph.gw.ea.newReport.vo.ImplDeptVO;
import com.hanaph.gw.ea.newReport.vo.OvertimeVO;

/**
 * <pre>
 * Class Name : NewReportE01012DAOImpl.java
 * 설명 : 시간외근무내역서 DAOImpl
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
@Repository("newReportE01012Dao")
public class NewReportE01012DAOImpl extends SqlSessionDaoSupport implements NewReportE01012DAO {

	@Autowired
	SqlSessionFactory sqlSessionFactory;
	
	@Autowired
	private ApprovalDAO approvalDao; 
	
	@Autowired
	private ImplDeptDAO implDeptDao;
	
	/* (non-Javadoc)
	 * @see com.hanaph.gw.ea.newReport.dao.NewReportE01012DAO#insertOvertimeDetail(com.hanaph.gw.ea.newReport.vo.ApprovalMasterVO, com.hanaph.gw.ea.newReport.vo.OvertimeVO)
	 */
	@Override
	public String insertOvertimeDetail(ApprovalMasterVO paramAmVO,
			OvertimeVO paramOtVO) {
		SqlSession sqlBatchSession = sqlSessionFactory.openSession(ExecutorType.BATCH);
		String approval_seq = "";
		try{
			sqlBatchSession.commit(false);
			//저장전에 다른 데이터가 입력이 되 었는지 다시 한번 체크 한다.
			int overTimeChk = (Integer)sqlBatchSession.selectOne("newReportE01012.overtimeExist", paramOtVO.getApproval_seq_old());
			if(overTimeChk <= 0){
				sqlBatchSession.insert("newReport.insertApprovalMaster", paramAmVO);
				approval_seq = paramAmVO.getApproval_seq();
				
				List<OvertimeVO> overtimeList = paramOtVO.getOvertimeVO(); 
				logger.debug("시간외근무내역서>>>>>>>>>>>>>>>" +overtimeList.size() );
				if(overtimeList.size() > 0){
					for (int i = 0; i < overtimeList.size(); i++) {
						OvertimeVO overtimeVO = new OvertimeVO();
						overtimeVO = overtimeList.get(i);
						overtimeVO.setApproval_seq(approval_seq);
						sqlBatchSession.insert("newReportE01012.insertOvertimeDetail", overtimeVO);
					}
				}
				
				//시간외 근무 내역서는 저장시 시간외 근무 신청서에 결재 라인을 저장한다.
				Map<String, String> paramMap = new HashMap<String, String>();
				paramMap.put("approval_seq", paramOtVO.getApproval_seq_old());
				
				//시간외 근무 신청서 결재 라인
				List<ApprovalVO> approvalVOList = approvalDao.getApprovalDetailList(paramMap);
				logger.debug("시간외근무내역서 결재>>>>>>>>>>>>>>>" +approvalVOList.size() );
				if(approvalVOList.size() > 0){
					for (int i = 0; i < approvalVOList.size(); i++) {
						ApprovalVO approvalVO = new ApprovalVO();
						approvalVO = approvalVOList.get(i);
						//결재라인에 본인은 제외한다.
						if(!approvalVO.getEmp_no().equals(paramOtVO.getEmp_no())){
							approvalVO.setApproval_seq(approval_seq);
							approvalVO.setState("E03001");
							approvalVO.setCreate_no(paramOtVO.getEmp_no());
							sqlBatchSession.insert("newReport.insertApproval", approvalVO);
						}
					}
				}
				
				//시간외 근무 신청서 시행 라인			
				List<ImplDeptVO> implDeptVOList = implDeptDao.getImplDeptDetailList(paramMap);
				logger.debug("시간외근무내역서 시행>>>>>>>>>>>>>>>" +implDeptVOList.size() );
				if(implDeptVOList.size() > 0){
					for (int i = 0; i < implDeptVOList.size(); i++) {
						ImplDeptVO implDeptVO = new ImplDeptVO();
						implDeptVO = implDeptVOList.get(i);
						implDeptVO.setApproval_seq(approval_seq);
						implDeptVO.setCreate_no(paramOtVO.getEmp_no());
						sqlBatchSession.insert("newReport.insertImplDept", implDeptVO);
					}
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
	 * @see com.hanaph.gw.ea.newReport.dao.NewReportE01012DAO#overtimeDetailDetailList(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<OvertimeVO> overtimeDetailDetailList(String approval_seq) {
		return (List<OvertimeVO>)getSqlSession().selectList("newReportE01012.overtimeDetailDetailList", approval_seq);

	}
	
	/*
	 * (non-Javadoc)
	 * @see com.hanaph.gw.ea.newReport.dao.NewReportE01012DAO#overtimeExist(java.lang.String)
	 */
	@Override
	public boolean overtimeExist(String approval_seq_old) {
		boolean bResult  = false;
		int iResult = (Integer)getSqlSession().selectOne("newReportE01012.overtimeExist", approval_seq_old);
		if(iResult > 0){
			bResult = true;
		}
		return bResult;
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.ea.newReport.dao.NewReportE01012DAO#updateOvertimeDetail(com.hanaph.gw.ea.newReport.vo.ApprovalMasterVO, com.hanaph.gw.ea.newReport.vo.OvertimeVO)
	 */
	@Override
	public void updateOvertimeDetail(ApprovalMasterVO paramAmVO, OvertimeVO paramOtVO) {
		
		SqlSession sqlBatchSession = sqlSessionFactory.openSession(ExecutorType.BATCH);
		String approval_seq = paramAmVO.getApproval_seq();
		
		try{
			sqlBatchSession.commit(false);
			//결재마스터 업데이트
			sqlBatchSession.update("newReport.updateApprovalMaster", paramAmVO);
			
			//삭제후 저장 한다.
			paramOtVO.setApproval_seq(approval_seq);
			
			//삭제
			sqlBatchSession.delete("newReportE01012.deleteOvertimeDetail", paramOtVO);
			
			List<OvertimeVO> overtimeList = paramOtVO.getOvertimeVO(); 
			logger.debug("시간외근무내역서>>>>>>>>>>>>>>>" +overtimeList.size() );
			if(overtimeList.size() > 0){
				for (int i = 0; i < overtimeList.size(); i++) {
					OvertimeVO overtimeVO = new OvertimeVO();
					overtimeVO = overtimeList.get(i);
					overtimeVO.setApproval_seq(approval_seq);
					sqlBatchSession.update("newReportE01012.insertOvertimeDetail", overtimeVO);
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
