/**
  * Hana Project
  * Copyright 2014 iRush Co.,
  *
  */
package com.hanaph.gw.ea.share.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hanaph.gw.ea.share.vo.ShareReportVO;
import com.hanaph.gw.pe.member.vo.MemberVO;

/**
 * <pre>
 * Class Name : ShareReportDAOImpl.java
 * 설명 : 공유문서 조회 DAOImpl
 * 
 * Modification Information
 *    수정일      수정자              수정 내용
 *  ------------ -------------- --------------------------------
 *  2015. 1. 21.      CHOIILJI         
 * </pre>
 * 
 * @version : 1.0
 * @author  : CHOIILJI(choiilji@irush.co.kr)
 * @since   : 2015. 1. 21.
 */
@Repository("shareDao")
public class ShareReportDAOImpl extends SqlSessionDaoSupport implements ShareReportDAO {

	@Autowired
	SqlSessionFactory sqlSessionFactory;
	
	/* (non-Javadoc)
	 * @see com.hanaph.gw.ea.share.dao.ShareDAO#getShareList(java.util.Map)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ShareReportVO> getShareList(Map<String, String> paramMap) {
		return (List<ShareReportVO>)getSqlSession().selectList("share.getShareList", paramMap);
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.ea.share.dao.ShareDAO#getShareCount(java.util.Map)
	 */
	@Override
	public int getShareCount(Map<String, String> paramMap) {
		return (Integer)getSqlSession().selectOne("share.getShareCount", paramMap);
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.ea.share.dao.ShareDAO#getShareTargetList(java.util.Map)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<MemberVO> getShareTargetList(Map<String, String> paramMap) {
		return (List<MemberVO>)getSqlSession().selectList("share.getShareTargetList", paramMap);
	}

	/*
	 * (non-Javadoc)
	 * @see com.hanaph.gw.ea.share.dao.ShareDAO#insertShare(com.hanaph.gw.ea.share.vo.ShareVO)
	 */
	@Override
	public int insertShareTarget(ShareReportVO shareVO) {
		SqlSession sqlBatchSession = sqlSessionFactory.openSession(ExecutorType.BATCH);
		try{
			sqlBatchSession.commit(false);
			
			List<ShareReportVO> shareVOList = shareVO.getShareVO();
			//결재
			logger.debug("공유 대상 임직원>>>>>>>>>>>>>>>" +shareVOList.size() );
			if(shareVOList.size() > 0){
				for (int i = 0; i < shareVOList.size(); i++) {
					ShareReportVO share = new ShareReportVO();
					share = shareVOList.get(i);
					sqlBatchSession.insert("share.insertShareTarget", share);
				}
			}
			
			sqlBatchSession.commit();
			return 1;
		}catch(Exception ex){
			ex.printStackTrace();
			sqlBatchSession.rollback();
            return 0;
		}finally{
			sqlBatchSession.close();	
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.hanaph.gw.ea.share.dao.ShareReportDAO#deleteShareTarget(java.util.Map)
	 */
	@Override
	public boolean deleteShareTarget(Map<String, String> paramMap) {
		boolean bResult  = false;
		int iResult = (Integer)getSqlSession().delete("share.deleteShareTarget", paramMap);
		if(iResult > 0){
			bResult = true;
		}
		return bResult;
	}

	/*
	 * (non-Javadoc)
	 * @see com.hanaph.gw.ea.share.dao.ShareReportDAO#getShareTargetDetail(java.util.Map)
	 */
	@Override
	public ShareReportVO getShareTargetDetail(Map<String, String> paramMap) {
		return (ShareReportVO)getSqlSession().selectOne("share.getShareTargetDetail", paramMap);
	}

	/*
	 * (non-Javadoc)
	 * @see com.hanaph.gw.ea.share.dao.ShareReportDAO#updateShareTargetReadYn(com.hanaph.gw.ea.share.vo.ShareReportVO)
	 */
	@Override
	public boolean updateShareTargetReadYn(ShareReportVO shareReportVO) {
		boolean bResult  = false;
		int iResult = (Integer)getSqlSession().update("share.updateShareTargetReadYn", shareReportVO);
		if(iResult > 0){
			bResult = true;
		}
		return bResult;
	}
}
