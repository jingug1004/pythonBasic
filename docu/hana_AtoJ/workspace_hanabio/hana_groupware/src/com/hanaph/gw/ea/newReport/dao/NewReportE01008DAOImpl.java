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
import com.hanaph.gw.ea.newReport.vo.PurchaseVO;

/**
 * <pre>
 * Class Name : NewReportE01008DAOImpl.java
 * 설명 : 구매신청서 DAOImpl
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
@Repository("newReportE01008Dao")
public class NewReportE01008DAOImpl extends SqlSessionDaoSupport implements NewReportE01008DAO {

	@Autowired
	SqlSessionFactory sqlSessionFactory;
	
	/* (non-Javadoc)
	 * @see com.hanaph.gw.ea.newReport.dao.NewReportE01008DAO#insertPurchase(com.hanaph.gw.ea.newReport.vo.ApprovalMasterVO, com.hanaph.gw.ea.newReport.vo.PurchaseVO)
	 */
	@Override
	public String insertPurchase(ApprovalMasterVO paramAmVO, PurchaseVO paramPcVO) {
		SqlSession sqlBatchSession = sqlSessionFactory.openSession(ExecutorType.BATCH);
		String approval_seq = "";

		try{
			sqlBatchSession.commit(false);
			sqlBatchSession.insert("newReport.insertApprovalMaster", paramAmVO);
			approval_seq = paramAmVO.getApproval_seq();
			
			List<PurchaseVO> purchaseList = paramPcVO.getPurchaseVO(); 
			logger.debug("구매신청서>>>>>>>>>>>>>>>" +purchaseList.size() );
			if(purchaseList.size() > 0){
				for (int i = 0; i < purchaseList.size(); i++) {
					PurchaseVO purchaseVO = new PurchaseVO();
					purchaseVO = purchaseList.get(i);
					purchaseVO.setApproval_seq(approval_seq);
					sqlBatchSession.insert("newReportE01008.insertPurchase", purchaseVO);
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
	 * @see com.hanaph.gw.ea.newReport.dao.NewReportE01008DAO#purchaseDetailList(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PurchaseVO> purchaseDetailList(String approval_seq) {
		return (List<PurchaseVO>)getSqlSession().selectList("newReportE01008.purchaseDetailList", approval_seq);

	}

	/*
	 * (non-Javadoc)
	 * @see com.hanaph.gw.ea.newReport.dao.NewReportE01008DAO#updatePurchase(com.hanaph.gw.ea.newReport.vo.ApprovalMasterVO, com.hanaph.gw.ea.newReport.vo.PurchaseVO)
	 */
	@Override
	public void updatePurchase(ApprovalMasterVO paramAmVO, PurchaseVO paramPcVO) {
		SqlSession sqlBatchSession = sqlSessionFactory.openSession(ExecutorType.BATCH);
		String approval_seq = paramAmVO.getApproval_seq();

		try{
			sqlBatchSession.commit(false);
			//결재마스터 업데이트
			sqlBatchSession.update("newReport.updateApprovalMaster", paramAmVO);
			
			//삭제후 저장 한다.
			paramPcVO.setApproval_seq(approval_seq);
			
			//삭제
			sqlBatchSession.delete("newReportE01008.deletePurchase", paramPcVO);
			
			List<PurchaseVO> purchaseList = paramPcVO.getPurchaseVO(); 
			logger.debug("구매신청서>>>>>>>>>>>>>>>" +purchaseList.size() );
			if(purchaseList.size() > 0){
				for (int i = 0; i < purchaseList.size(); i++) {
					PurchaseVO purchaseVO = new PurchaseVO();
					purchaseVO = purchaseList.get(i);
					purchaseVO.setApproval_seq(approval_seq);
					sqlBatchSession.insert("newReportE01008.insertPurchase", purchaseVO);
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
