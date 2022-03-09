/**
  * Hana Project
  * Copyright 2014 iRush Co.,
  *
  */
package com.hanaph.gw.ea.newReport.dao;


import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.hanaph.gw.co.fileAttach.vo.FileAttachVO;
import com.hanaph.gw.ea.newReport.vo.AccidentVO;
import com.hanaph.gw.ea.newReport.vo.ApprovalMasterVO;
import com.hanaph.gw.ea.newReport.vo.CommuteVO;
import com.hanaph.gw.ea.newReport.vo.DraftVO;
import com.hanaph.gw.ea.newReport.vo.IncompanyVO;
import com.hanaph.gw.ea.newReport.vo.ParticipationVO;
import com.hanaph.gw.ea.newReport.vo.VacationVO;
import com.hanaph.gw.ea.newReport.vo.VaporizeVO;

/**
 * <pre>
 * Class Name : NewReportDAOImpl.java
 * 설명 : 
 * 
 * Modification Information
 *    수정일      수정자              수정 내용
 *  ------------ -------------- --------------------------------
 *  2014. 12. 29.      Jung.Jin.Muk          
 * </pre>
 * 
 * @version : 
 * @author  : Jung.Jin.Muk(pc123pc@irush.co.kr)
 * @since   : 2014. 12. 29.
 */
@Repository("newReportDao")
public class NewReportDAOImpl extends SqlSessionDaoSupport implements NewReportDAO{

	@Autowired
	SqlSessionFactory sqlSessionFactory;
	
	/*
	 * (non-Javadoc)
	 * @see com.hanaph.gw.ea.newReport.dao.NewReportDAO#insertApprovalMaster(com.hanaph.gw.ea.newReport.vo.ApprovalMasterVO)
	 */
	@Override
	public String insertApprovalMaster(ApprovalMasterVO paramAmVO) {
		getSqlSession().insert("newReport.insertApprovalMaster", paramAmVO);
		return paramAmVO.getApproval_seq();

	}

	/*
	 * (non-Javadoc)
	 * @see com.hanaph.gw.ea.newReport.dao.NewReportDAO#updateApprovalMasterState(com.hanaph.gw.ea.newReport.vo.ApprovalMasterVO)
	 */
	@Override
	public boolean updateApprovalMasterState(ApprovalMasterVO paramAmVO) {
		Integer count = getSqlSession().insert("newReport.updateApprovalMasterState", paramAmVO);
		if(count != 0){
			return true;
		}else{
			return false;
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.hanaph.gw.ea.newReport.dao.NewReportDAO#updateApprovalMasterStepState(com.hanaph.gw.ea.newReport.vo.ApprovalMasterVO)
	 */
	@Override
	public boolean updateApprovalMasterStepState(ApprovalMasterVO paramAmVO) {
		Integer count = getSqlSession().insert("newReport.updateApprovalMasterStepState", paramAmVO);
		if(count != 0){
			return true;
		}else{
			return false;
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.hanaph.gw.ea.newReport.dao.NewReportDAO#updateApprovalMasterComplete(com.hanaph.gw.ea.newReport.vo.ApprovalMasterVO)
	 */
	@Override
	public boolean updateApprovalMasterComplete(ApprovalMasterVO paramAmVO) {
		Integer count = getSqlSession().insert("newReport.updateApprovalMasterComplete", paramAmVO);
		if(count != 0){
			return true;
		}else{
			return false;
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.hanaph.gw.ea.newReport.dao.NewReportDAO#updateApprovalMasterRejection(com.hanaph.gw.ea.newReport.vo.ApprovalMasterVO)
	 */
	@Override
	public boolean updateApprovalMasterRejection(ApprovalMasterVO paramAmVO) {
		Integer count = getSqlSession().insert("newReport.updateApprovalMasterRejection", paramAmVO);
		if(count != 0){
			return true;
		}else{
			return false;
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.hanaph.gw.ea.newReport.dao.NewReportDAO#updateApprovalMasterDelete(com.hanaph.gw.ea.newReport.vo.ApprovalMasterVO)
	 */
	@Override
	public boolean updateApprovalMasterDelete(ApprovalMasterVO paramAmVO) {
		getSqlSession().update("newReport.deleteAttachAll", paramAmVO);
		Integer count = getSqlSession().update("newReport.updateApprovalMasterDelete", paramAmVO);
		if(count != 0){
			return true;
		}else{
			return false;
		}
	}
	
	
	/*
	 * (non-Javadoc)
	 * @see com.hanaph.gw.ea.newReport.dao.NewReportDAO#updateApprovalMaster(com.hanaph.gw.ea.newReport.vo.ApprovalMasterVO)
	 */
	@Override
	public boolean updateApprovalMaster(ApprovalMasterVO paramAmVO) {
		Integer count = getSqlSession().insert("newReport.updateApprovalMaster", paramAmVO);
		if(count != 0){
			return true;
		}else{
			return false;
		}
	}
	
	/* (non-Javadoc)
	 * @see com.hanaph.gw.ea.newReport.dao.NewReportDAO#approvalDetail(java.lang.String)
	 */
	@Override
	public ApprovalMasterVO approvalDetail(String approval_seq) {
		return (ApprovalMasterVO)getSqlSession().selectOne("newReport.approvalDetail", approval_seq);
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.ea.newReport.dao.NewReportDAO#insertFileAttach(com.hanaph.gw.co.fileAttach.vo.FileAttachVO)
	 */
	@Override
	public int insertFileAttach(FileAttachVO uploadVO) {
		getSqlSession().insert("newReport.insertFileAttach", uploadVO);
		return uploadVO.getFile_seq();
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.ea.newReport.dao.NewReportDAO#updateFileAttach(java.util.Map)
	 */
	@Override
	public void updateFileAttach(Map<String, String> paramMap) {
		getSqlSession().update("newReport.updateFileAttach", paramMap);
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.ea.newReport.dao.NewReportDAO#getAttachList(java.util.Map)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<FileAttachVO> getAttachList(Map<String, String> paramMap) {
		return getSqlSession().selectList("newReport.getAttachList",paramMap);
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.ea.newReport.dao.NewReportDAO#deleteAttach(com.hanaph.gw.co.fileAttach.vo.FileAttachVO)
	 */
	@Override
	public int deleteAttach(FileAttachVO fileParamVO) {
		return getSqlSession().update("newReport.deleteAttach", fileParamVO);
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.ea.newReport.dao.NewReportDAO#getOriginFileNm(java.lang.String)
	 */
	@Override
	public String getOriginFileNm(String file_seq) {
		return (String) getSqlSession().selectOne("newReport.getOriginFileNm", file_seq);
	}


}
