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
import com.hanaph.gw.ea.newReport.vo.RequisitionVO;

/**
 * <pre>
 * Class Name : NewReportE01015DAOImpl.java
 * 설명 : 
 * 
 * Modification Information
 *    수정일      수정자              수정 내용
 *  ------------ -------------- --------------------------------
 *  2015. 12. 11.      물품 구입 청구서          
 * </pre>
 * 
 * @version : 
 * @author  : 전산팀
 * @since   : 2015. 12. 11.
 */
@Repository("newReportE01015Dao")
public class NewReportE01015DAOImpl extends SqlSessionDaoSupport implements NewReportE01015DAO {

	@Autowired
	SqlSessionFactory sqlSessionFactory;
	
	/* (non-Javadoc)
	 * @see com.hanaph.gw.ea.newReport.dao.NewReportE01015DAO#insertRequisition(com.hanaph.gw.ea.newReport.vo.ApprovalMasterVO, com.hanaph.gw.ea.newReport.vo.RequisitionVO)
	 */
	@Override
	public String insertRequisition(ApprovalMasterVO paramAmVO, RequisitionVO paramRqVO) {
		SqlSession sqlBatchSession = sqlSessionFactory.openSession(ExecutorType.BATCH);
		String approval_seq = "";
		try{
			sqlBatchSession.commit(false);
			sqlBatchSession.insert("newReport.insertApprovalMaster", paramAmVO);
			approval_seq = paramAmVO.getApproval_seq();
			
			List<RequisitionVO> RequisitionList = paramRqVO.getRequisitionVO(); 
			logger.debug("물품 구입 청구서>>>>>>>>>>>>>>>" +RequisitionList.size() );
			if(RequisitionList.size() > 0){
				for (int i = 0; i < RequisitionList.size(); i++) {
					
					RequisitionVO requisitionVO = new RequisitionVO();
					requisitionVO = RequisitionList.get(i);
					requisitionVO.setApproval_seq(approval_seq);
					//System.out.println("--- NewReportE01015.java requisitionVO.getReq_ymd : "+i+"번 "+requisitionVO.getReq_ymd());
					//System.out.println("--- NewReportE01015.java requisitionVO.getReq_no : "+i+"번 "+requisitionVO.getReq_no());
					//System.out.println("--- NewReportE01015.java requisitionVO.getMaterial_id : "+i+"번 "+requisitionVO.getMaterial_id());
					//System.out.println("--- NewReportE01015.java requisitionVO.getMaterial_nm : "+i+"번 "+requisitionVO.getMaterial_nm());
					
					//System.out.println("--- NewReportE01015.java requisitionVO.getCust_id : "+i+"번 "+requisitionVO.getCust_id());
					//System.out.println("--- NewReportE01015.java requisitionVO.getCust_nm : "+i+"번 "+requisitionVO.getCust_nm());
					
					//System.out.println("--- NewReportE01015.java requisitionVO.getStandard : "+i+"번 "+requisitionVO.getStandard());
					//System.out.println("--- NewReportE01015.java requisitionVO.getUnit : "+i+"번 "+requisitionVO.getUnit());
					
					//System.out.println("--- NewReportE01015.java requisitionVO.getQty : "+i+"번 "+requisitionVO.getQty());
					//System.out.println("--- NewReportE01015.java requisitionVO.getDanga : "+i+"번 "+requisitionVO.getDanga());
					//System.out.println("--- NewReportE01015.java requisitionVO.getAmt : "+i+"번 "+requisitionVO.getAmt());
					
					//System.out.println("--- NewReportE01015.java requisitionVO.getImport_yn : "+i+"번 "+requisitionVO.getImport_yn());
					//System.out.println("--- NewReportE01015.java requisitionVO.getHyunjaego_qty : "+i+"번 "+requisitionVO.getHyunjaego_qty());
					
					//System.out.println("--- NewReportE01015.java requisitionVO.getUsage : "+i+"번 "+requisitionVO.getUsage());
					//System.out.println("--- NewReportE01015.java requisitionVO.getIpgo_ymd : "+i+"번 "+requisitionVO.getIpgo_ymd());
					//System.out.println("--- NewReportE01015.java requisitionVO.getBigo : "+i+"번 "+requisitionVO.getBigo());
					
					sqlBatchSession.insert("newReportE01015.insertRequisition", requisitionVO);
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
	 * @see com.hanaph.gw.ea.newReport.dao.NewReportE01015DAO#requisitionDetail(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<RequisitionVO> requisitionDetail(String approval_seq) {
		return (List<RequisitionVO>)getSqlSession().selectList("newReportE01015.requisitionDetail", approval_seq);
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.ea.newReport.dao.NewReportE01015DAO#updateRequisition(com.hanaph.gw.ea.newReport.vo.ApprovalMasterVO, com.hanaph.gw.ea.newReport.vo.RequisitionVO)
	 */
	@Override
	public void updateRequisition(ApprovalMasterVO paramAmVO ,RequisitionVO paramRqVO) {
		
		SqlSession sqlBatchSession = sqlSessionFactory.openSession(ExecutorType.BATCH);
		String approval_seq = paramAmVO.getApproval_seq();
		
		try{
			sqlBatchSession.commit(false);
			//결재마스터 업데이트
			sqlBatchSession.update("newReport.updateApprovalMaster", paramAmVO);
			
			//삭제후 저장 한다.
			paramRqVO.setApproval_seq(approval_seq);
			
			//삭제
			sqlBatchSession.delete("newReportE01015.deleteRequisition", paramRqVO);
			
			List<RequisitionVO> requisitionList = paramRqVO.getRequisitionVO(); 
			logger.debug("물품 구입 청구서>>>>>>>>>>>>>>>" +requisitionList.size() );
			if(requisitionList.size() > 0){
				for (int i = 0; i < requisitionList.size(); i++) {
					RequisitionVO requisitionVO = new RequisitionVO();
					requisitionVO = requisitionList.get(i);
					requisitionVO.setApproval_seq(approval_seq);
					/*System.out.println("--- NewReportE01015.java requisitionVO.getReq_ymd : "+i+"번 "+requisitionVO.getReq_ymd());
					System.out.println("--- NewReportE01015.java requisitionVO.getReq_no : "+i+"번 "+requisitionVO.getReq_no());
					System.out.println("--- NewReportE01015.java requisitionVO.getMaterial_id : "+i+"번 "+requisitionVO.getMaterial_id());
					System.out.println("--- NewReportE01015.java requisitionVO.getMaterial_nm : "+i+"번 "+requisitionVO.getMaterial_nm());
					
					System.out.println("--- NewReportE01015.java requisitionVO.getCust_id : "+i+"번 "+requisitionVO.getCust_id());
					System.out.println("--- NewReportE01015.java requisitionVO.getCust_nm : "+i+"번 "+requisitionVO.getCust_nm());
					
					System.out.println("--- NewReportE01015.java requisitionVO.getStandard : "+i+"번 "+requisitionVO.getStandard());
					System.out.println("--- NewReportE01015.java requisitionVO.getUnit : "+i+"번 "+requisitionVO.getUnit());
					
					System.out.println("--- NewReportE01015.java requisitionVO.getQty : "+i+"번 "+requisitionVO.getQty());
					System.out.println("--- NewReportE01015.java requisitionVO.getDanga : "+i+"번 "+requisitionVO.getDanga());
					System.out.println("--- NewReportE01015.java requisitionVO.getAmt : "+i+"번 "+requisitionVO.getAmt());
					
					System.out.println("--- NewReportE01015.java requisitionVO.getImport_yn : "+i+"번 "+requisitionVO.getImport_yn());
					System.out.println("--- NewReportE01015.java requisitionVO.getHyunjaego_qty : "+i+"번 "+requisitionVO.getHyunjaego_qty());
					
					System.out.println("--- NewReportE01015.java requisitionVO.getUsage : "+i+"번 "+requisitionVO.getUsage());
					System.out.println("--- NewReportE01015.java requisitionVO.getIpgo_ymd : "+i+"번 "+requisitionVO.getIpgo_ymd());
					System.out.println("--- NewReportE01015.java requisitionVO.getBigo : "+i+"번 "+requisitionVO.getBigo());*/
					sqlBatchSession.update("newReportE01015.insertRequisition", requisitionVO);
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
	
	/* (non-Javadoc)
	 * @see com.hanaph.gw.ea.newReport.dao.NewReportE01015DAO#searchReqNo(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<RequisitionVO> searchReqNo(String search_req_ymd) {
		return (List<RequisitionVO>)getSqlSession().selectList("newReportE01015.searchReqNo", search_req_ymd);
	}
	
	/* (non-Javadoc)
	 * @see com.hanaph.gw.ea.newReport.dao.NewReportE01015DAO#searchReqData(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<RequisitionVO> searchReqData(String search_req_no) {
		return (List<RequisitionVO>)getSqlSession().selectList("newReportE01015.searchReqData", search_req_no);
	}
}
