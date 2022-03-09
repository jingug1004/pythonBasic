/**
  * Hana Project
  * Copyright 2014 iRush Co.,
  *
  */
package com.hanaph.gw.ea.receive.dao;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

import com.hanaph.gw.ea.newReport.vo.ApprovalMasterVO;
import com.hanaph.gw.ea.newReport.vo.ApprovalVO;
import com.hanaph.gw.ea.receive.vo.ReceiveVO;


/**
 * <pre>
 * Class Name : ReceiveDAOImpl.java
 * 설명 : 
 * 
 * Modification Information
 *    수정일      수정자              수정 내용
 *  ------------ -------------- --------------------------------
 *  2015. 1. 20.      Jung.Jin.Muk          
 * </pre>
 * 
 * @version : 
 * @author  : Jung.Jin.Muk(pc123pc@irush.co.kr)
 * @since   : 2015. 1. 20.
 */
@Repository("receiveDAO")
public class ReceiveDAOImpl extends SqlSessionDaoSupport implements ReceiveDAO{

	/* (non-Javadoc)
	 * @see com.hanaph.gw.ea.receive.dao.ReceiveDAO#getReceiveCount(java.util.Map)
	 */
	@Override
	public int getReceiveCount(Map<String, String> paramMap) {
		Integer count = (Integer)getSqlSession().selectOne("receive.getReceiveCount", paramMap);
		return count;
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.ea.receive.dao.ReceiveDAO#getReceiveList(java.util.Map)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ReceiveVO> getReceiveList(Map<String, String> paramMap) {
		return (List<ReceiveVO>)getSqlSession().selectList("receive.getReceiveList", paramMap);

	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.ea.receive.dao.ReceiveDAO#updateApproval(com.hanaph.gw.ea.newReport.vo.ApprovalMasterVO)
	 */
	@Override
	public void updateApproval(ReceiveVO receiveVO) {
		getSqlSession().update("receive.updateApproval", receiveVO);
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.ea.receive.dao.ReceiveDAO#updateApproval(com.hanaph.gw.ea.newReport.vo.ApprovalMasterVO)
	 * ml180117.ml09_T20 김진국 - ReceiveDAOImpl.java에 updateApprovalMustOpinion DAO 구현 메서드를 만든다 - 결재 승인한 사람이든 결재 승인 안 한 사람이든 본인과 전 결재승인자가 있으면 모두 승인취소 해야한다.
	 */
	@Override
	public void updateApprovalMustOpinion(ReceiveVO receiveVO) {
		getSqlSession().update("receive.updateApprovalMustOpinion", receiveVO);
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.ea.receive.dao.ReceiveDAO#getReceiveReadYnDetail(java.util.Map)
	 */
	@Override
	public ApprovalVO getReceiveReadYnDetail(Map<String, String> paramMap) {
		return (ApprovalVO) getSqlSession().selectOne("receive.getReceiveReadYnDetail", paramMap);
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.ea.receive.dao.ReceiveDAO#updateReceiveReadYn(com.hanaph.gw.ea.receive.vo.ReceiveVO)
	 */
	@Override
	public void updateReceiveReadYn(ApprovalVO approvalVO) {
		getSqlSession().update("receive.updateReceiveReadYn", approvalVO);
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.ea.receive.dao.ReceiveDAO#getApprovalOrdering(java.lang.String)
	 */
	@Override
	public ReceiveVO getApprovalOrdering(ReceiveVO receiveVO) {
		return (ReceiveVO) getSqlSession().selectOne("receive.getApprovalOrdering", receiveVO);
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.ea.receive.dao.ReceiveDAO#getMainReceiveCnt(java.util.Map)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ApprovalMasterVO> getMainReceiveCnt(Map<String, String> paramMap) {
		return (List<ApprovalMasterVO>)getSqlSession().selectList("receive.getMainReceiveCnt", paramMap);

	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.ea.receive.dao.ReceiveDAO#getLongTermReceiveCount(java.util.Map)
	 */
	@Override
	public int getLongTermReceiveCount(Map<String, String> paramMap) {
		Integer count = (Integer)getSqlSession().selectOne("receive.getLongTermReceiveCount", paramMap);
		return count;
	}

}
