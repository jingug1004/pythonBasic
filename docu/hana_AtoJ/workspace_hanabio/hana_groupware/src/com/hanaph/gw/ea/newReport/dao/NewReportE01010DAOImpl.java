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
import com.hanaph.gw.ea.newReport.vo.CertificateVO;

/**
 * <pre>
 * Class Name : NewReportE01010DAOImpl.java
 * 설명 : 증명서 발급 신청서 DAOImpl
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
@Repository("newReportE01010Dao")
public class NewReportE01010DAOImpl extends SqlSessionDaoSupport implements NewReportE01010DAO {
	
	@Autowired
	SqlSessionFactory sqlSessionFactory;

	/* (non-Javadoc)
	 * @see com.hanaph.gw.ea.newReport.dao.NewReportE01010DAO#insertCertificate(com.hanaph.gw.ea.newReport.vo.ApprovalMasterVO, com.hanaph.gw.ea.newReport.vo.CertificateVO)
	 */
	@Override
	public String insertCertificate(ApprovalMasterVO paramAmVO, CertificateVO paramCfcVO) {
		SqlSession sqlBatchSession = sqlSessionFactory.openSession(ExecutorType.BATCH);
		String approval_seq = "";
		
		try{
			sqlBatchSession.commit(false);
			sqlBatchSession.insert("newReport.insertApprovalMaster", paramAmVO);
			approval_seq = paramAmVO.getApproval_seq();
			
			List<CertificateVO> certificateList = paramCfcVO.getCertificateVO(); 
			if(certificateList.size() > 0){
				for (int i = 0; i < certificateList.size(); i++) {
					CertificateVO certificateVO = new CertificateVO();
					certificateVO = certificateList.get(i);
					certificateVO.setApproval_seq(approval_seq);
					sqlBatchSession.insert("newReportE01010.insertCertificate", certificateVO);
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
	 * @see com.hanaph.gw.ea.newReport.dao.NewReportE01010DAO#certificateDetailList(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<CertificateVO> certificateDetailList(String approval_seq) {
		return (List<CertificateVO>)getSqlSession().selectList("newReportE01010.certificateDetailList", approval_seq);

	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.ea.newReport.dao.NewReportE01010DAO#updateCertificate(com.hanaph.gw.ea.newReport.vo.ApprovalMasterVO, com.hanaph.gw.ea.newReport.vo.CertificateVO)
	 */
	@Override
	public void updateCertificate(ApprovalMasterVO paramAmVO, CertificateVO paramCfcVO) {
		
		SqlSession sqlBatchSession = sqlSessionFactory.openSession(ExecutorType.BATCH);
		String approval_seq = paramAmVO.getApproval_seq();
		
		try{
			sqlBatchSession.commit(false);
			//결재마스터 업데이트
			sqlBatchSession.update("newReport.updateApprovalMaster", paramAmVO);
			
			//삭제후 저장 한다.
			paramCfcVO.setApproval_seq(approval_seq);
			
			//삭제
			sqlBatchSession.delete("newReportE01010.deleteCertificate", paramCfcVO);
			
			List<CertificateVO> certificateList = paramCfcVO.getCertificateVO(); 
			if(certificateList.size() > 0){
				for (int i = 0; i < certificateList.size(); i++) {
					CertificateVO certificateVO = new CertificateVO();
					certificateVO = certificateList.get(i);
					certificateVO.setApproval_seq(approval_seq);
					sqlBatchSession.update("newReportE01010.insertCertificate", certificateVO);
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
