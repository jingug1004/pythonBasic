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
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hanaph.gw.ea.newReport.vo.ApprovalMasterVO;
import com.hanaph.gw.ea.newReport.vo.DeliveryVO;

/**
 * <pre>
 * Class Name : NewReportE01015DAOImpl.java
 * 설명 : 
 * 
 * Modification Information
 *    수정일      수정자              수정 내용
 *  ------------ -------------- --------------------------------
 *  2016. 01. 25.      원부자재 납품확인서          
 * </pre>
 * 
 * @version : 
 * @author  : 전산팀
 * @since   : 2016. 01. 25.
 */
@Repository("newReportE01016Dao")
public class NewReportE01016DAOImpl extends SqlSessionDaoSupport implements NewReportE01016DAO {

	@Autowired
	SqlSessionFactory sqlSessionFactory;
	
	/* (non-Javadoc)
	 * @see com.hanaph.gw.ea.newReport.dao.NewReportE01016DAO#insertDelivery(com.hanaph.gw.ea.newReport.vo.ApprovalMasterVO, com.hanaph.gw.ea.newReport.vo.DeliveryVO)
	 */
	@Override
	public String insertDelivery(ApprovalMasterVO paramAmVO, DeliveryVO paramDlVO) {
		SqlSession sqlBatchSession = sqlSessionFactory.openSession(ExecutorType.BATCH);
		String approval_seq = "";
		try{
			sqlBatchSession.commit(false);
			sqlBatchSession.insert("newReport.insertApprovalMaster", paramAmVO);
			approval_seq = paramAmVO.getApproval_seq();
			
			List<DeliveryVO> DeliveryList = paramDlVO.getDeliveryVO(); 
			logger.debug("원부자재 납품확인서>>>>>>>>>>>>>>>" +DeliveryList.size() );
			if(DeliveryList.size() > 0){
				for (int i = 0; i < DeliveryList.size(); i++) {
					
					DeliveryVO deliveryVO = new DeliveryVO();
					deliveryVO = DeliveryList.get(i);
					deliveryVO.setApproval_seq(approval_seq);		
					//System.out.println("--- NewReportE01016.java approval_seq : "+i+"번 "+approval_seq);
					//System.out.println("--- NewReportE01016.java deliveryVO.getYmd : "+i+"번 "+deliveryVO.getYmd());
					//System.out.println("--- NewReportE01016.java deliveryVO.getSlip_no : "+i+"번 "+deliveryVO.getSlip_no());
					//System.out.println("--- NewReportE01016.java deliveryVO.getMaterial_id : "+i+"번 "+deliveryVO.getMaterial_id());
					//System.out.println("--- NewReportE01016.java deliveryVO.getMaterial_nm : "+i+"번 "+deliveryVO.getMaterial_nm());					
					//System.out.println("--- NewReportE01016.java deliveryVO.getCust_id : "+i+"번 "+deliveryVO.getCust_id());
					//System.out.println("--- NewReportE01016.java deliveryVO.getCust_nm : "+i+"번 "+deliveryVO.getCust_nm());					
					//System.out.println("--- NewReportE01016.java deliveryVO.getStandard : "+i+"번 "+deliveryVO.getStandard());
					//System.out.println("--- NewReportE01016.java deliveryVO.getUnit : "+i+"번 "+deliveryVO.getUnit());					
					//System.out.println("--- NewReportE01016.java deliveryVO.getQty : "+i+"번 "+deliveryVO.getQty());
					//System.out.println("--- NewReportE01016.java deliveryVO.getDanga : "+i+"번 "+deliveryVO.getDanga());
					//System.out.println("--- NewReportE01016.java deliveryVO.getAmt : "+i+"번 "+deliveryVO.getAmt());
					//System.out.println("--- NewReportE01016.java deliveryVO.getVat_sum : "+i+"번 "+deliveryVO.getVat_sum());
					//System.out.println("--- NewReportE01016.java deliveryVO.getAmt_sum : "+i+"번 "+deliveryVO.getAmt_sum());
					
					sqlBatchSession.insert("newReportE01016.insertDelivery", deliveryVO);
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
	 * @see com.hanaph.gw.ea.newReport.dao.NewReportE01016DAO#deliveryDetail(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<DeliveryVO> deliveryDetail(String approval_seq) {
		return (List<DeliveryVO>)getSqlSession().selectList("newReportE01016.deliveryDetail", approval_seq);
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.ea.newReport.dao.NewReportE01016DAO#updateDelivery(com.hanaph.gw.ea.newReport.vo.ApprovalMasterVO, com.hanaph.gw.ea.newReport.vo.DeliveryVO)
	 */
	@Override
	public void updateDelivery(ApprovalMasterVO paramAmVO ,DeliveryVO paramDlVO) {
		
		SqlSession sqlBatchSession = sqlSessionFactory.openSession(ExecutorType.BATCH);
		String approval_seq = paramAmVO.getApproval_seq();
		
		try{
			sqlBatchSession.commit(false);
			//결재마스터 업데이트
			sqlBatchSession.update("newReport.updateApprovalMaster", paramAmVO);
			
			//삭제후 저장 한다.
			paramDlVO.setApproval_seq(approval_seq);
			
			//삭제
			sqlBatchSession.delete("newReportE01016.deleteDelivery", paramDlVO);
			
			List<DeliveryVO> deliveryList = paramDlVO.getDeliveryVO(); 
			logger.debug("원부자재 납품확인서>>>>>>>>>>>>>>>" +deliveryList.size() );
			if(deliveryList.size() > 0){
				for (int i = 0; i < deliveryList.size(); i++) {
					DeliveryVO deliveryVO = new DeliveryVO();
					deliveryVO = deliveryList.get(i);
					deliveryVO.setApproval_seq(approval_seq);
					/*System.out.println("--- NewReportE01016.java deliveryVO.getYmd : "+i+"번 "+approval_seq);
					System.out.println("--- NewReportE01016.java deliveryVO.getYmd : "+i+"번 "+deliveryVO.getYmd());
					System.out.println("--- NewReportE01016.java deliveryVO.getSlip_no : "+i+"번 "+deliveryVO.getSlip_no());
					System.out.println("--- NewReportE01016.java deliveryVO.getCust_id : "+i+"번 "+deliveryVO.getCust_id());
					System.out.println("--- NewReportE01016.java deliveryVO.getCust_nm : "+i+"번 "+deliveryVO.getCust_nm());
					System.out.println("--- NewReportE01016.java deliveryVO.getMaterial_id : "+i+"번 "+deliveryVO.getMaterial_id());
					System.out.println("--- NewReportE01016.java deliveryVO.getMaterial_nm : "+i+"번 "+deliveryVO.getMaterial_nm());
					System.out.println("--- NewReportE01016.java deliveryVO.getStandard : "+i+"번 "+deliveryVO.getStandard());
					System.out.println("--- NewReportE01016.java deliveryVO.getUnit : "+i+"번 "+deliveryVO.getUnit());
					System.out.println("--- NewReportE01016.java deliveryVO.getQty : "+i+"번 "+deliveryVO.getQty());
					System.out.println("--- NewReportE01016.java deliveryVO.getDanga : "+i+"번 "+deliveryVO.getDanga());
					System.out.println("--- NewReportE01016.java deliveryVO.getAmt : "+i+"번 "+deliveryVO.getAmt());
					System.out.println("--- NewReportE01016.java deliveryVO.getBalju_no : "+i+"번 "+deliveryVO.getBalju_no());
					System.out.println("--- NewReportE01016.java deliveryVO.getAmt_sum : "+i+"번 "+deliveryVO.getAmt_sum());
					System.out.println("--- NewReportE01016.java deliveryVO.getVat_sum : "+i+"번 "+deliveryVO.getVat_sum());*/
					
					sqlBatchSession.update("newReportE01016.insertDelivery", deliveryVO);
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
	 * @see com.hanaph.gw.ea.newReport.dao.NewReportE01016DAO#searchSlipNo(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<DeliveryVO> searchSlipNo(String search_ymd) {
		return (List<DeliveryVO>)getSqlSession().selectList("newReportE01016.searchSlipNo", search_ymd);
	}
	
	/* (non-Javadoc)
	 * @see com.hanaph.gw.ea.newReport.dao.NewReportE01016DAO#searchSlipData(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<DeliveryVO> searchSlipData(String search_no) {
		return (List<DeliveryVO>)getSqlSession().selectList("newReportE01016.searchSlipData", search_no);
	}
}
