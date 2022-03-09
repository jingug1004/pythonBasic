package com.hanaph.gw.of.board.dao;

import java.util.List;
import java.util.Map;

import com.hanaph.gw.of.board.vo.BoardCommentVO;
import com.hanaph.gw.of.board.vo.BoardTargetVO;
import com.hanaph.gw.of.board.vo.BoardVO;
import com.hanaph.gw.of.common.vo.CommonTargetVO;
import com.hanaph.gw.pe.member.vo.MemberVO;

/**
 * <pre>
 * Class Name : BoardDAO.java
 * 설명 : 게시판 DAO
 * 
 * Modification Information
 *    수정일      수정자              수정 내용
 *  ------------ -------------- --------------------------------
 *  2014. 10. 6.      재가부
 * </pre>
 * 
 * @version : 
 * @author  : 재가부(@irush.co.kr)
 * @since   : 2014. 10. 6.
 */
public interface BoardDAO{
	
	/**
	 * <pre>
	 * 1. 개요     : 카운트 
	 * 2. 처리내용 : 게시글 총 카운트 수 를가져온다.
	 * </pre>
	 * @Method Name : getBoardCount
	 * @param paramMap
	 * @return
	 */
	public int getBoardCount(Map<String, String> paramMap);
	
	/**
	 * <pre>
	 * 1. 개요     : 게시글 등록
	 * 2. 처리내용 : 게시글 등록
	 * </pre>
	 * @Method Name : insertBoard
	 * @param paramMap
	 */
	public String insertBoard(BoardVO paramVO, BoardTargetVO boardTargetVO);
	
	/**
	 * <pre>
	 * 1. 개요     : 게시글 수정
	 * 2. 처리내용 : 게시글 수정
	 * </pre>
	 * @Method Name : updateBoard
	 * @param paramVO
	 * @param boardTargetVO
	 * @return
	 */
	public boolean updateBoard(BoardVO paramVO, BoardTargetVO boardTargetVO);
	
	/**
	 * <pre>
	 * 1. 개요     : 리스트 
	 * 2. 처리내용 : 게시판 리스트를 가져온다.
	 * </pre>
	 * @Method Name : getBoardList
	 * @param paramMap
	 * @return
	 */
	public List<BoardVO> getBoardList(Map<String, String> paramMap); 
	
	/**
	 * <pre>
	 * 1. 개요     : 삭제 
	 * 2. 처리내용 : 게시글을 삭제 한다.
	 * </pre>
	 * @Method Name : deleteBoard
	 * @param paramVO
	 * @return
	 */
	public boolean deleteBoard(BoardVO paramVO);

	/**
	 * <pre>
	 * 1. 개요     : 게시판 상세 
	 * 2. 처리내용 : 게시판 상세 정보를 가져온다.
	 * </pre>
	 * @Method Name : getBoardDetail
	 * @param paramMap
	 * @return
	 */
	public BoardVO getBoardDetail(Map<String, String> paramMap);

	/**
	 * <pre>
	 * 1. 개요     : 게시판 시퀀스 
	 * 2. 처리내용 : 게시판 마지막 시퀀스 글번호를 가져온다.
	 * </pre>
	 * @Method Name : getBoardSeq
	 * @return
	 */
	public int getBoardSeq();

	/**
	 * <pre>
	 * 1. 개요     : 등록
	 * 2. 처리내용 : 댓글을 등록한다.
	 * </pre>
	 * @Method Name : insertComment
	 * @param paramVO
	 * @return
	 */
	public boolean insertComment(BoardCommentVO paramVO);

	/**
	 * <pre>
	 * 1. 개요     : 수정 
	 * 2. 처리내용 : 댓글을 수정 한다.
	 * </pre>
	 * @Method Name : updateComment
	 * @param paramVO
	 * @return
	 */
	public int updateComment(BoardCommentVO paramVO);

	/**
	 * <pre>
	 * 1. 개요     : 삭제
	 * 2. 처리내용 : 댓글을 삭제한다.
	 * </pre>
	 * @Method Name : deleteComment
	 * @param paramVO
	 * @return
	 */
	public boolean deleteComment(BoardCommentVO paramVO);

	/**
	 * <pre>
	 * 1. 개요     : 리스트 
	 * 2. 처리내용 : 댓글 리스트를 가져온다.
	 * </pre>
	 * @Method Name : getCommentLsit
	 * @param paramMap
	 * @return
	 */
	public List<BoardCommentVO> getCommentLsit(Map<String, String> paramMap);

	/**
	 * <pre>
	 * 1. 개요     : 대상자 목록 
	 * 2. 처리내용 : 대상자 목록을 가져온다.
	 * </pre>
	 * @Method Name : getBoardMemberList
	 * @param paramMap
	 * @return
	 */
	public List<MemberVO> getBoardMemberList(Map<String, String> paramMap);

	/**
	 * <pre>
	 * 1. 개요     : 조회
	 * 2. 처리내용 : 게시글 조회수, 열람 여부 데이터를 가져온다.
	 * </pre>
	 * @Method Name : boardReadData
	 * @param paramMap
	 * @return
	 */
	public BoardTargetVO getBoardReadData(Map<String, String> paramMap);

	/**
	 * <pre>
	 * 1. 개요     : 조회
	 * 2. 처리내용 : 게시글 읽은 일시 업데이트 한다.
	 * </pre>
	 * @Method Name : updateBoardRead
	 * @param paramMap
	 */
	public void updateBoardRead(Map<String, String> paramMap);

	/**
	 * <pre>
	 * 1. 개요     : 조회 
	 * 2. 처리내용 : 조회수 증가 시킨다
	 * </pre>
	 * @Method Name : updateBoardReadAdd
	 * @param paramMap
	 */
	public void updateBoardReadAdd(Map<String, String> paramMap);

	/**
	 * <pre>
	 * 1. 개요     : 카운트
	 * 2. 처리내용 : 댓글 카운트를 가져온다.
	 * </pre>
	 * @Method Name : getBoardCommentCount
	 * @param seq
	 * @return
	 */
	public int getBoardCommentCount(int seq);

	
	/**
	 * <pre>
	 * 1. 개요     : 게시글 확인 여부
	 * 2. 처리내용 : 게시글 확인 여부 상세데이터를 불러온다.
	 * </pre>
	 * @Method Name : getBoardReadDataList
	 * @param paramMap
	 * @return
	 */
	public List<CommonTargetVO> getBoardReadDataList(Map<String, String> paramMap);
	
	
}
