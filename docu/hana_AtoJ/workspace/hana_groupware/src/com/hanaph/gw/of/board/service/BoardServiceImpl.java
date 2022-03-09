package com.hanaph.gw.of.board.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hanaph.gw.co.fileAttach.service.FileAttachService;
import com.hanaph.gw.co.fileAttach.vo.FileAttachVO;
import com.hanaph.gw.of.board.dao.BoardDAO;
import com.hanaph.gw.of.board.vo.BoardCommentVO;
import com.hanaph.gw.of.board.vo.BoardTargetVO;
import com.hanaph.gw.of.board.vo.BoardVO;
import com.hanaph.gw.of.common.vo.CommonTargetVO;
import com.hanaph.gw.pe.member.vo.MemberVO;

/**
 * <pre>
 * Class Name : BoardServiceImpl.java
 * 설명 : 게시판 ServiceImpl
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
@Service(value="boardService")
public class BoardServiceImpl implements BoardService {
	
	private static final Logger logger = Logger.getLogger(BoardServiceImpl.class);

	@Autowired
	private BoardDAO boardDao;
	
	@Autowired
	FileAttachService fileAttachService;
	
	@Autowired
	SqlSessionFactory sqlSessionFactory;
	
	/* (non-Javadoc)
	 * @see com.hanaph.gw.of.board.service.BoardService#getBoardCount(java.util.Map)
	 */
	public int getBoardCount(Map<String, String> paramMap){
		return boardDao.getBoardCount(paramMap);
	}
	
	/* (non-Javadoc)
	 * @see com.hanaph.gw.of.board.service.BoardService#getBoardList(java.util.Map)
	 */
	@Override
	public List<BoardVO> getBoardList(Map<String, String> paramMap){
		return boardDao.getBoardList(paramMap);
	}
	
	/* (non-Javadoc)
	 * @see com.hanaph.gw.of.board.service.BoardService#insertBoard(com.hanaph.gw.of.board.vo.BoardVO, com.hanaph.gw.of.board.vo.BoardTargetVO)
	 */
	@Override
	public String insertBoard(BoardVO paramVO, BoardTargetVO boardTargetVO){
		String seq = boardDao.insertBoard(paramVO, boardTargetVO);
		return seq;
	}
	
	/* (non-Javadoc)
	 * @see com.hanaph.gw.of.board.service.BoardService#updateBoard(com.hanaph.gw.of.board.vo.BoardVO, com.hanaph.gw.of.board.vo.BoardTargetVO)
	 */
	@Override
	public boolean updateBoard(BoardVO paramVO, BoardTargetVO boardTargetVO){
		return boardDao.updateBoard(paramVO, boardTargetVO);
	}
	
	/* (non-Javadoc)
	 * @see com.hanaph.gw.of.board.service.BoardService#deleteBoard(com.hanaph.gw.of.board.vo.BoardVO)
	 */
	@Override
	public boolean deleteBoard(BoardVO paramVO){
		
		boolean result = false;
		
		/*첨부파일 삭제처리 파라미터 셋팅*/
		FileAttachVO fileAttachVO = new FileAttachVO();
		fileAttachVO.setCd(paramVO.getCd()); //게시판 구분
		fileAttachVO.setSeq(paramVO.getSeq()); //게시판 시퀀스
		fileAttachVO.setDelete_yn("Y"); //첨부파일 삭제여부
		
		if(paramVO.getSeq().indexOf("|") > -1){
			String seq = paramVO.getSeq();
			String[] seqs = seq.split("\\|");
			
			for(int i= 0; i<seqs.length; i++){
				paramVO.setSeq(seqs[i]);
				fileAttachVO.setSeq(seqs[i]); //쪽지 시퀀스
				fileAttachService.deleteAttachAll(fileAttachVO);
				result = boardDao.deleteBoard(paramVO);
			}
		}else{
			fileAttachService.deleteAttachAll(fileAttachVO);
			result = boardDao.deleteBoard(paramVO);
		}
		
		return result;
	}
	
	/* (non-Javadoc)
	 * @see com.hanaph.gw.of.board.service.BoardService#getBoardDetail(java.util.Map)
	 */
	@Override
	public BoardVO getBoardDetail(Map<String, String> paramMap) {
		
		BoardTargetVO readData =  boardDao.getBoardReadData(paramMap);
		if("N".equals(readData.getRead_yn())){
			readData.setRead_yn("Y");
			boardDao.updateBoardRead(paramMap);		//조회데이터 저장
			boardDao.updateBoardReadAdd(paramMap);	//조회수 증가(업데이트)
		}
		return boardDao.getBoardDetail(paramMap); 
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.of.board.service.BoardService#getBoardSeq()
	 */
	@Override
	public int getBoardSeq() {
		return boardDao.getBoardSeq(); 
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.of.board.service.BoardService#insertComment(com.hanaph.gw.of.board.vo.BoardCommentVO)
	 */
	@Override
	public boolean insertComment(BoardCommentVO paramVO) {
		return boardDao.insertComment(paramVO); 
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.of.board.service.BoardService#updateComment(com.hanaph.gw.of.board.vo.BoardCommentVO)
	 */
	@Override
	public int updateComment(BoardCommentVO paramVO) {
		return boardDao.updateComment(paramVO); 
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.of.board.service.BoardService#deleteComment(com.hanaph.gw.of.board.vo.BoardCommentVO)
	 */
	@Override
	public boolean deleteComment(BoardCommentVO paramVO) {
		return boardDao.deleteComment(paramVO); 
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.of.board.service.BoardService#getCommentLsit(java.util.Map)
	 */
	@Override
	public List<BoardCommentVO> getCommentLsit(Map<String, String> paramMap) {
		return boardDao.getCommentLsit(paramMap);
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.of.board.service.BoardService#getBoardMemberList(java.util.Map)
	 */
	@Override
	public List<MemberVO> getBoardMemberList(Map<String, String> paramMap) {
		return boardDao.getBoardMemberList(paramMap);
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.of.board.service.BoardService#getBoardCommentCount(int)
	 */
	@Override
	public int getBoardCommentCount(int seq) {
		return boardDao.getBoardCommentCount(seq);
	}
	
	/* (non-Javadoc)
	 * @see com.hanaph.gw.of.board.service.BoardService#getBoardReadDataList(java.util.Map)
	 */
	@Override
	public List<CommonTargetVO> getBoardReadDataList(Map<String, String> paramMap) {
		return boardDao.getBoardReadDataList(paramMap);
	}
	
}
