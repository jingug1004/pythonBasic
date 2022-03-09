/**
  * Hana Project
  * Copyright 2014 iRush Co.,
  *
  */
package com.hanaph.gw.ea.newReport.dao;


import com.hanaph.gw.co.fileAttach.vo.FileAttachVO;
import com.hanaph.gw.ea.newReport.vo.ApprovalMasterVO;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

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

	// ml180116.ml07_T09 김진국 - NewReportDAOImpl.java에 addMustOpinion 메서드 구현 - APPROVAL_SEQ(문서번호)를 통하여 GW_EA_APPROVAL_MASTER 테이블의 MUSTOPINION 칼럼을 'Y'로 변경시키기 위해서
	@Override
	public void addMustOpinion(String approval_seq) throws Exception {
		getSqlSession().update("newReport.addMustOpinion", approval_seq);
	}

	// ml180118.ml04_T26 김진국 - NewReportDAOImpl.java에 addMustOpinionApproval 구현 메서드 추가 - '시의필' 버튼 누르면 결재 마스터(GW_EA_APPROVAL_MASTER) 뿐만 아니라 결재 테이블(GW_EA_APPROVAL)도 변경하기 위해서
	@Override
	public void addMustOpinionApproval(String approval_seq) throws Exception {
		getSqlSession().update("newReport.addMustOpinionApproval", approval_seq);
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.ea.newReport.dao.NewReportDAO#approvalDetail(java.lang.String)
	 * ml180122.ml09_T48 김진국 - newReportDAOImpl.java에 getApprovalSubject 구현 메서드 추가 - 쪽지에 결재 제목을 가져오기 위해서
	 */
	@Override
	public String getApprovalSubject(String approval_seq)throws Exception {
		return (String)getSqlSession().selectOne("newReport.getApprovalSubject", approval_seq);
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.ea.newReport.dao.NewReportDAO#getAttachList(java.util.Map)
	 * ml180124.ml04_T73 김진국 - newReportDAOImpl.java에 getApprovalerMustOpinion 추가 - 시의필 실행시 결재 작성자와 결재자에게도 쪽지 보내기 위해서!
	 */
	@Override
	public List<String> getApprovalerMustOpinion(String approval_seq) throws Exception{
		return getSqlSession().selectList("newReport.getApprovalerMustOpinion",approval_seq);
	}

	@Override
	public String isYMustOpinion(String approval_seq) throws Exception {
		return (String) getSqlSession().selectOne("newReport.isYMustOpinion", approval_seq);
	}

	/* ml180126.ml06_T86 김진국 - NewReportDAO, newReport.xml, NewReprtService, NewReportController에 addMustOpinionYtoN 추가 - 결재 등록시 '시의필' 실행했다가 취소면 update로 MustOpinion N으로 바꿈 */
	@Override
	public void addMustOpinionYtoN(String approval_seq) throws Exception {
		getSqlSession().update("newReport.addMustOpinionYtoN", approval_seq);

	}

}
