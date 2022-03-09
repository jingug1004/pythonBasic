package com.hanaph.gw.of.board.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hanaph.gw.co.common.utils.StringUtil;
import com.hanaph.gw.of.board.vo.BoardCommentVO;
import com.hanaph.gw.of.board.vo.BoardTargetVO;
import com.hanaph.gw.of.board.vo.BoardVO;
import com.hanaph.gw.of.common.vo.CommonTargetVO;
import com.hanaph.gw.pe.member.vo.MemberVO;

/**
 * <pre>
 * Class Name : BoardDAOImpl.java
 * 설명 : 게시판 DAOImpl
 * 
 * Modification Information
 *    수정일      수정자              수정 내용
 *  ------------ -------------- --------------------------------
 *  2015. 3. 4.      jung jin muk         
 * </pre>
 * 
 * @version : 
 * @author  : jung jin muk(pc123pc@irush.co.kr)
 * @since   : 2015. 3. 4.
 */
@Repository("boardDao")
public class BoardDAOImpl extends SqlSessionDaoSupport implements BoardDAO {
			
	@Autowired
	SqlSessionFactory sqlSessionFactory;
	
	/* (non-Javadoc)
	 * @see com.hanaph.gw.of.board.dao.BoardDAO#getBoardCount(java.util.Map)
	 */
	@Override
    public int getBoardCount(Map<String, String> paramMap){
		Integer count = (Integer)getSqlSession().selectOne("board.getBoardCount", paramMap);
		return count;
    }
	
	/* (non-Javadoc)
	 * @see com.hanaph.gw.of.board.dao.BoardDAO#insertBoard(java.util.Map)
	 */
	public String insertBoard(BoardVO paramVO, BoardTargetVO boardTargetVO) {
		
		String seq = "";
		
		SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH);
		sqlSession.commit(false);
		
		try{
			sqlSession.insert("board.insertBoard", paramVO);
			boardTargetVO.setSeq(Integer.parseInt(paramVO.getSeq()));
			seq = StringUtil.nvl(paramVO.getSeq());
			
			if(boardTargetVO.getEmp_no().indexOf("|") > -1){
				String emp_no = boardTargetVO.getEmp_no();
				String[] emp_nos = emp_no.split("\\|");
				
				for(int i= 0; i<emp_nos.length; i++){
					boardTargetVO.setEmp_no(emp_nos[i]);
					sqlSession.insert("board.insertBoardEmpNo", boardTargetVO);
				}
			}else{
				sqlSession.insert("board.insertBoardEmpNo", boardTargetVO);
			}
			
			sqlSession.commit();
			
		}catch(RuntimeException e){
			seq = "";
			logger.debug("게시판 등록 실패 :: " , e);
			sqlSession.rollback();
		}finally{
			sqlSession.close();	
		}
		return seq;
		
	}
	
	/* (non-Javadoc)
	 * @see com.hanaph.gw.of.board.dao.BoardDAO#updateBoard(com.hanaph.gw.of.board.vo.BoardVO, com.hanaph.gw.of.board.vo.BoardTargetVO)
	 */
	public boolean updateBoard(BoardVO paramVO, BoardTargetVO boardTargetVO){
		
		boolean result = true;
		
		SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH);
		sqlSession.commit(false);
		
		try{
			sqlSession.delete("board.deleteBoardTarget", boardTargetVO);
			sqlSession.update("board.updateBoard", paramVO);
			sqlSession.update("board.updateBoardSetReadCnt", boardTargetVO);
			
				
			if(boardTargetVO.getEmp_no().indexOf("|") > -1){
				String emp_no = boardTargetVO.getEmp_no();
				String[] emp_nos = emp_no.split("\\|");
				
				for(int i= 0; i<emp_nos.length; i++){
					boardTargetVO.setEmp_no(emp_nos[i]);
					sqlSession.insert("board.insertBoardEmpNo", boardTargetVO);
				}
			}else{
				sqlSession.insert("board.insertBoardEmpNo", boardTargetVO);
			}
			
			sqlSession.commit();
			
		}catch(RuntimeException e){
			result = false;
			logger.debug("게시판 수정 실패 :: " , e);
			sqlSession.rollback();
		}finally{
			sqlSession.close();	
		}
		return result;
	}
	
	/* (non-Javadoc)
	 * @see com.hanaph.gw.of.board.dao.BoardDAO#getBoardList(java.util.Map)
	 */
	@Override
    @SuppressWarnings("unchecked")
    public List<BoardVO> getBoardList(Map<String, String> paramMap){
        return (List<BoardVO>)getSqlSession().selectList("board.getBoardList", paramMap);
    }

	/* (non-Javadoc)
	 * @see com.hanaph.gw.of.board.dao.BoardDAO#deleteBoard(com.hanaph.gw.of.board.vo.BoardVO)
	 */
	@Override
	public boolean deleteBoard(BoardVO paramVO) {
		Integer count = getSqlSession().update("board.deleteBoard", paramVO);
		if(count != 0){
			return true;
		}else{
			return false;
		}
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.of.board.dao.BoardDAO#getBoardDetail(java.util.Map)
	 */
	@Override
	public BoardVO getBoardDetail(Map<String, String> paramMap) {
		return (BoardVO)getSqlSession().selectOne("board.getBoardDetail", paramMap);
	}
	
	/* (non-Javadoc)
	 * @see com.hanaph.gw.of.board.dao.BoardDAO#getBoardSeq()
	 */
	@Override
	public int getBoardSeq() {
		return (Integer) getSqlSession().selectOne("board.getBoardSeq");
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.of.board.dao.BoardDAO#insertComment(com.hanaph.gw.of.board.vo.BoardCommentVO)
	 */
	@Override
	public boolean insertComment(BoardCommentVO paramVO) {
		Integer count = getSqlSession().insert("board.insertComment", paramVO);
		if(count != 0){
			return true;
		}else{
			return false;
		}
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.of.board.dao.BoardDAO#updateComment(com.hanaph.gw.of.board.vo.BoardCommentVO)
	 */
	@Override
	public int updateComment(BoardCommentVO paramVO) {
		return getSqlSession().update("board.updateComment", paramVO);
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.of.board.dao.BoardDAO#deleteComment(com.hanaph.gw.of.board.vo.BoardCommentVO)
	 */
	@Override
	public boolean deleteComment(BoardCommentVO paramVO) {
		Integer count = getSqlSession().insert("board.deleteComment", paramVO);
		if(count != 0){
			return true;
		}else{
			return false;
		}
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.of.board.dao.BoardDAO#getCommentLsit(java.util.Map)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<BoardCommentVO> getCommentLsit(Map<String, String> paramMap) {
		return (List<BoardCommentVO>)getSqlSession().selectList("board.getCommentLsit", paramMap);
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.of.board.dao.BoardDAO#getBoardMemberList(java.util.Map)
	 */
	@SuppressWarnings("unchecked")
	public List<MemberVO> getBoardMemberList(Map<String, String> paramMap) {
		return (List<MemberVO>)getSqlSession().selectList("board.getBoardMemberList", paramMap);
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.of.board.dao.BoardDAO#getBoardReadData(java.util.Map)
	 */
	@Override
	public BoardTargetVO getBoardReadData(Map<String, String> paramMap) {
		BoardTargetVO readData = (BoardTargetVO) getSqlSession().selectOne("board.getBoardReadData", paramMap);
		return readData;
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.of.board.dao.BoardDAO#updateBoardRead(java.util.Map)
	 */
	@Override
	public void updateBoardRead(Map<String, String> paramMap) {
		getSqlSession().update("board.updateBoardRead", paramMap);
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.of.board.dao.BoardDAO#updateBoardReadAdd(java.util.Map)
	 */
	@Override
	public void updateBoardReadAdd(Map<String, String> paramMap) {
 		getSqlSession().update("board.updateBoardReadAdd", paramMap);
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.of.board.dao.BoardDAO#getBoardCommentCount(int)
	 */
	@Override
	public int getBoardCommentCount(int seq) {
		return (Integer) getSqlSession().selectOne("board.getBoardCommentCount", seq);
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.of.board.dao.BoardDAO#getBoardReadDataList(java.util.Map)
	 */
	@SuppressWarnings("unchecked")
	public List<CommonTargetVO> getBoardReadDataList(Map<String, String> paramMap) {
		return (List<CommonTargetVO>)getSqlSession().selectList("board.getBoardReadDataList", paramMap);
	}
	
}
