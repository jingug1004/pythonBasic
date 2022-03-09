/**
  * Hana Project
  * Copyright 2014 iRush Co.,
  *
  */
package com.hanaph.gw.of.notice.dao;

import java.util.List;
import java.util.Map;

import com.hanaph.gw.of.common.vo.CommonTargetVO;
import com.hanaph.gw.of.notice.vo.NoticeCommentVO;
import com.hanaph.gw.of.notice.vo.NoticeReadVO;
import com.hanaph.gw.of.notice.vo.NoticeVO;
import com.hanaph.gw.pe.member.vo.MemberVO;

/**
 * <pre>
 * Class Name : NoticeDAO.java
 * 설명 : 공지사항 DAO
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
public interface NoticeDAO {

	/**
	 * <pre>
	 * 1. 개요     : 공지사항 리스트 
	 * 2. 처리내용 :	공지사항 리스트를 가져온다.
	 * </pre>
	 * @Method Name : getNoticeList
	 * @param paramMap
	 * @return
	 */
	List<NoticeVO> getNoticeList(Map<String, String> paramMap);
	
	/**
	 * <pre>
	 * 1. 개요     : 공지사항 카운트 
	 * 2. 처리내용 : 공지사항 카운트 수 를가져온다.
	 * </pre>
	 * @Method Name : getNoticeCount
	 * @param paramMap
	 * @return
	 */
	int getNoticeCount(Map<String, String> paramMap);
	
	/**
	 * <pre>
	 * 1. 개요     : 공지사항 seq 
	 * 2. 처리내용 : 공지사항 마지막 seq를 가져온다.
	 * </pre>
	 * @Method Name : getNoticeSeq
	 * @return
	 */
	int getNoticeSeq();
	
	/**
	 * <pre>
	 * 1. 개요     : 공지사항 저장  
	 * 2. 처리내용 : 공지사항을 저장 한다.
	 * </pre>
	 * @Method Name : insertNotice
	 * @param noticeVO
	 * @param noticeReadVO
	 * @return
	 */
	String insertNotice(NoticeVO noticeVO, NoticeReadVO noticeReadVO);

	/**
	 * <pre>
	 * 1. 개요     : 공지사항 대상자 저장
	 * 2. 처리내용 : 공지사항 대상자 임직원을 저장 한다.
	 * </pre>
	 * @Method Name : insertNoticeEmpNo
	 * @param noticeReadVO
	 * @return
	 */
	boolean insertNoticeEmpNo(NoticeReadVO noticeReadVO);
	
	/**
	 * <pre>
	 * 1. 개요     : 권한 수정
	 * 2. 처리내용 : 권한 수정을 한다.
	 * </pre>
	 * @Method Name : updateNotice
	 * @param noticeVO
	 * @return
	 */
	boolean updateNotice(NoticeVO noticeVO, NoticeReadVO noticeReadVO);
	
	/**
	 * <pre>
	 * 1. 개요     : 공지사항 상세 정보를 가져 온다. 
	 * 2. 처리내용 :
	 * </pre>
	 * @Method Name : noticeDetail
	 * @param noticeReadVO
	 * @return
	 */
	NoticeVO noticeDetail(NoticeReadVO noticeReadVO);
	
	/**
	 * <pre>
	 * 1. 개요     :	공지사항 열람 
	 * 2. 처리내용 : 공지사항 열람 할 시에 yn 체크 한다.
	 * </pre>
	 * @Method Name : updateNoticeRead
	 * @param noticeReadVO
	 */
	void updateNoticeRead(NoticeReadVO noticeReadVO);
	
	/**
	 * <pre>
	 * 1. 개요     : 공지사항 열람 정보 
	 * 2. 처리내용 : 공지사항 열람 정보를 가져온다.
	 * </pre>
	 * @Method Name : noticeReadData
	 * @param noticeReadVO
	 * @return
	 */
	NoticeReadVO noticeReadData(NoticeReadVO noticeReadVO);
	
	/**
	 * <pre>
	 * 1. 개요     : 공지사항 열람 횟수
	 * 2. 처리내용 : 공지사항 열람 회수(조회수) +1증가 한다
	 * </pre>
	 * @Method Name : updateNoticeReadAdd
	 * @param noticeReadVO
	 */
	void updateNoticeReadAdd(NoticeReadVO noticeReadVO);

	/**
	 * <pre>
	 * 1. 개요     : 공지사항 댓글 리스트
	 * 2. 처리내용 : 공지사항 댓글 리스트 가져온다.
	 * </pre>
	 * @Method Name : getNoticeCommentList
	 * @param paramMap
	 * @return
	 */
	List<NoticeCommentVO> getNoticeCommentList(Map<String, String> paramMap);
	
	/**
	 * <pre>
	 * 1. 개요     : 공지사항 댓글 카운트 
	 * 2. 처리내용 : 공지사항 댓글 카운트 가져온다.
	 * </pre>
	 * @Method Name : getNoticeCommentCount
	 * @param seq
	 * @return
	 */
	int getNoticeCommentCount(int seq);
	
	/**
	 * <pre>
	 * 1. 개요     : 공지사항 삭제
	 * 2. 처리내용 : 공지사항 삭제를 한다.
	 * </pre>
	 * @Method Name : deleteNotice
	 * @param noticeVO
	 * @return
	 */
	boolean deleteNotice(NoticeVO noticeVO);

	/**
	 * <pre>
	 * 1. 개요     : 댓글 저장
	 * 2. 처리내용 : 댓글 저장을 한다.
	 * </pre>
	 * @Method Name : insertComment
	 * @param noticeCmtVO
	 * @return
	 */
	boolean insertComment(NoticeCommentVO noticeCmtVO);

	/**
	 * <pre>
	 * 1. 개요     : 댓글 삭제 
	 * 2. 처리내용 : 댓글 삭제를 한다.
	 * </pre>
	 * @Method Name : deleteComment
	 * @param noticeCmtVO
	 * @return
	 */
	boolean deleteComment(NoticeCommentVO noticeCmtVO);

	/**
	 * <pre>
	 * 1. 개요     : 공지사항 대상자
	 * 2. 처리내용 : 공지사항 대상자를 가져온다.
	 * </pre>
	 * @Method Name : getNoticeMemberList
	 * @param paramMap
	 * @return
	 */
	List<MemberVO> getNoticeMemberList(Map<String, String> paramMap);

	/**
	 * <pre>
	 * 1. 개요     : 공지사항 읽은 여부
	 * 2. 처리내용 : 공지사항 읽은 여부 상세 데이터를 가져온다.
	 * </pre>
	 * @Method Name : getNoticeReadDataList
	 * @param paramMap
	 * @return
	 */
	List<CommonTargetVO> getNoticeReadDataList (Map<String, String> paramMap);

	/**
	 * <pre>
	 * 1. 개요     : 읽지 않은공지수 가져오기
	 * 2. 처리내용 : 읽지 않은공지수 가져오기
	 * </pre>
	 * @Method Name : getNoticeCountNoread
	 * @param paramMap
	 * @return
	 */		
	int getNoticeCountNoread(Map<String, String> paramMap);
}
