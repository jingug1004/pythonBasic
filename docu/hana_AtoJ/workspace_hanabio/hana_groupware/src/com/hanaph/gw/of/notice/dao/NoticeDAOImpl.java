/**
  * Hana Project
  * Copyright 2014 iRush Co.,
  *
  */
package com.hanaph.gw.of.notice.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hanaph.gw.co.common.utils.StringUtil;
import com.hanaph.gw.of.common.vo.CommonTargetVO;
import com.hanaph.gw.of.notice.vo.NoticeCommentVO;
import com.hanaph.gw.of.notice.vo.NoticeReadVO;
import com.hanaph.gw.of.notice.vo.NoticeVO;
import com.hanaph.gw.pe.member.vo.MemberVO;

/**
 * <pre>
 * Class Name : NoticeDAOImpl.java
 * 설명 : 공지사항 DAOImpl
 * 
 * Modification Information
 *    수정일      수정자              수정 내용
 *  ------------ -------------- --------------------------------
 *  2014. 11. 21.      Jung.Jin.Muk          
 * </pre>
 * 
 * @version : 
 * @author  : Jung.Jin.Muk(pc123pc@irush.co.kr)
 * @since   : 2014. 11. 21.
 */
@Repository("noticeDAO")
public class NoticeDAOImpl extends SqlSessionDaoSupport implements NoticeDAO{

	@Autowired
	SqlSessionFactory sqlSessionFactory;
	
	/* (non-Javadoc)
	 * @see com.hanaph.gw.of.notice.dao.NoticeDAO#getNoticeList(java.util.Map)
	 */
	@SuppressWarnings("unchecked")
	public List<NoticeVO> getNoticeList(Map<String, String> paramMap) {
		return (List<NoticeVO>)getSqlSession().selectList("notice.getNoticeList", paramMap);
	}
	
	/* (non-Javadoc)
	 * @see com.hanaph.gw.of.notice.dao.NoticeDAO#getNoticeCount(java.util.Map)
	 */
	public int getNoticeCount(Map<String, String> paramMap) {
		Integer count = (Integer)getSqlSession().selectOne("notice.getNoticeCount", paramMap);
		return count;
	}
	
	/* (non-Javadoc)
	 * @see com.hanaph.gw.of.notice.dao.NoticeDAO#getNoticeSeq()
	 */
	public int getNoticeSeq() {
		return (Integer) getSqlSession().selectOne("notice.getNoticeSeq");
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.of.notice.dao.NoticeDAO#insertNotice(com.hanaph.gw.of.notice.vo.NoticeVO)
	 */
	public String insertNotice(NoticeVO noticeVO, NoticeReadVO noticeReadVO) {
		String seq = "";
		
		SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH);
		sqlSession.commit(false);
		
		try{
			sqlSession.insert("notice.insertNotice", noticeVO);
			noticeReadVO.setSeq(Integer.parseInt(noticeVO.getSeq()));
			seq = StringUtil.nvl(noticeVO.getSeq());
			
			/*2015.04.10 kta 공지사항 저장시에는 대상자가 등록되지 않도록 수정.
			 if(noticeReadVO.getEmp_no().indexOf("|") > -1){
				String emp_no = noticeReadVO.getEmp_no();
				String[] emp_nos = emp_no.split("\\|");
				
				for(int i= 0; i<emp_nos.length; i++){
					noticeReadVO.setEmp_no(emp_nos[i]);
					sqlSession.insert("notice.insertNoticeEmpNo", noticeReadVO);
				}
			}else{
				sqlSession.insert("notice.insertNoticeEmpNo", noticeReadVO);
			}*/
			
			sqlSession.commit();
			
		}catch(RuntimeException e){
			seq = "";
			logger.debug("공지사항 등록 실패 :: " , e);
			sqlSession.rollback();
		}finally{
			sqlSession.close();	
		}
		return seq;
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.of.notice.dao.NoticeDAO#insertNoticeEmpNo(com.hanaph.gw.of.notice.vo.NoticeReadVO)
	 */
	public boolean insertNoticeEmpNo(NoticeReadVO noticeReadVO) {
		Integer count = getSqlSession().insert("notice.insertNoticeEmpNo", noticeReadVO);
		if(count != 0){
			return true;
		}else{
			return false;
		}
	}
	
	/* (non-Javadoc)
	 * @see com.hanaph.gw.of.notice.dao.NoticeDAO#updateNotice(com.hanaph.gw.of.notice.vo.NoticeVO)
	 */
	public boolean updateNotice(NoticeVO noticeVO, NoticeReadVO noticeReadVO) {
		boolean result = true;
		
		SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH);
		sqlSession.commit(false);
		
		try{
			sqlSession.delete("notice.deleteNoticeTarget", noticeReadVO);
			sqlSession.update("notice.updateNotice", noticeVO);
			sqlSession.update("notice.updateNoticeSetReadCnt", noticeReadVO);
			
			noticeReadVO.setSeq(Integer.parseInt(noticeVO.getSeq()));
			
			/*2015.04.10 kta 공지사항 저장시에는 대상자가 등록되지 않도록 수정.
			if(noticeReadVO.getEmp_no().indexOf("|") > -1){
				String emp_no = noticeReadVO.getEmp_no();
				String[] emp_nos = emp_no.split("\\|");
				
				for(int i= 0; i<emp_nos.length; i++){
					noticeReadVO.setEmp_no(emp_nos[i]);
					sqlSession.insert("notice.insertNoticeEmpNo", noticeReadVO);
				}
			}else{
				sqlSession.insert("notice.insertNoticeEmpNo", noticeReadVO);
			}*/
			
			sqlSession.commit();

		}catch(RuntimeException e){
			result = false;
			logger.debug("공지사항 수정 실패 :: " , e);
			sqlSession.rollback();
		}finally{
			sqlSession.close();	
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.of.notice.dao.NoticeDAO#noticeDetail(com.hanaph.gw.of.notice.vo.NoticeReadVO)
	 */
	public NoticeVO noticeDetail(NoticeReadVO noticeReadVO) {
		return (NoticeVO)getSqlSession().selectOne("notice.noticeDetail", noticeReadVO);
	}
	
	/* (non-Javadoc)
	 * @see com.hanaph.gw.of.notice.dao.NoticeDAO#updateNoticeRead(com.hanaph.gw.of.notice.vo.NoticeReadVO)
	 */
	public void updateNoticeRead(NoticeReadVO noticeReadVO) {
        //2015.04.10 kta 공지사항 대상자 없이 등록
		//getSqlSession().selectOne("notice.updateNoticeRead", noticeReadVO);
		getSqlSession().selectOne("notice.insertNoticeRead", noticeReadVO);
	}
	
	/* (non-Javadoc)
	 * @see com.hanaph.gw.of.notice.dao.NoticeDAO#noticeReadData(com.hanaph.gw.of.notice.vo.NoticeReadVO)
	 */
	public NoticeReadVO noticeReadData(NoticeReadVO noticeReadVO) {
		NoticeReadVO readData = (NoticeReadVO) getSqlSession().selectOne("notice.noticeReadData", noticeReadVO);
		return readData;
	}
	
	/* (non-Javadoc)
	 * @see com.hanaph.gw.of.notice.dao.NoticeDAO#updateNoticeReadAdd(com.hanaph.gw.of.notice.vo.NoticeReadVO)
	 */
	public void updateNoticeReadAdd(NoticeReadVO noticeReadVO) {
		getSqlSession().update("notice.updateNoticeReadAdd", noticeReadVO);
	}
	
	/* (non-Javadoc)
	 * @see com.hanaph.gw.of.notice.dao.NoticeDAO#getNoticeCommentList(java.util.Map)
	 */
	@SuppressWarnings("unchecked")
	public List<NoticeCommentVO> getNoticeCommentList(Map<String, String> paramMap) {
		return (List<NoticeCommentVO>)getSqlSession().selectList("notice.getNoticeCommentList", paramMap);
	}
	
	/* (non-Javadoc)
	 * @see com.hanaph.gw.of.notice.dao.NoticeDAO#getNoticeCommentCount(int)
	 */
	public int getNoticeCommentCount(int seq) {
		Integer count = (Integer)getSqlSession().selectOne("notice.getNoticeCommentCount", seq);
		return count;
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.of.notice.dao.NoticeDAO#deleteNotice(com.hanaph.gw.of.notice.vo.NoticeVO)
	 */
	public boolean deleteNotice(NoticeVO noticeVO) {
		Integer count = getSqlSession().update("notice.deleteNotice", noticeVO);
		if(count != 0){
			return true;
		}else{
			return false;
		}
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.of.notice.dao.NoticeDAO#insertComment(com.hanaph.gw.of.notice.vo.NoticeCommentVO)
	 */
	public boolean insertComment(NoticeCommentVO noticeCmtVO) {
		Integer count = getSqlSession().insert("notice.insertComment", noticeCmtVO);
		if(count != 0){
			return true;
		}else{
			return false;
		}
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.of.notice.dao.NoticeDAO#deleteComment(com.hanaph.gw.of.notice.vo.NoticeCommentVO)
	 */
	public boolean deleteComment(NoticeCommentVO noticeCmtVO) {
		Integer count = getSqlSession().update("notice.deleteComment", noticeCmtVO);
		if(count != 0){
			return true;
		}else{
			return false;
		}
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.of.notice.dao.NoticeDAO#getNoticeMemberList(java.util.Map)
	 */
	@SuppressWarnings("unchecked")
	public List<MemberVO> getNoticeMemberList(Map<String, String> paramMap) {
		return (List<MemberVO>)getSqlSession().selectList("notice.getNoticeMemberList", paramMap);
	}
	
	/* (non-Javadoc)
	 * @see com.hanaph.gw.of.notice.dao.NoticeDAO#getNoticeReadDataList(java.util.Map)
	 */
	@SuppressWarnings("unchecked")
	public List<CommonTargetVO> getNoticeReadDataList(Map<String, String> paramMap) {
		return (List<CommonTargetVO>)getSqlSession().selectList("notice.getNoticeReadDataList", paramMap);
	}

	@Override
	public int getNoticeCountNoread(Map<String, String> paramMap) {
		Integer count = (Integer)getSqlSession().selectOne("notice.getNoticeCountNoread", paramMap);
		return count;
	}
	
}
