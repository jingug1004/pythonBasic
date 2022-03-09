/**
  * Hana Project
  * Copyright 2014 iRush Co.,
  *
  */
package com.hanaph.gw.of.notice.vo;

import java.util.List;

import com.hanaph.gw.co.common.vo.CommonVO;

/**
 * <pre>
 * Class Name : NoticeCommentVO.java
 * 설명 : 공지사항 코멘트 정보
 * 
 * Modification Information
 *    수정일      수정자              수정 내용
 *  ------------ -------------- --------------------------------
 *  2014. 11. 25.      Jung.Jin.Muk          
 * </pre>
 * 
 * @version : 
 * @author  : Jung.Jin.Muk(pc123pc@irush.co.kr)
 * @since   : 2014. 11. 25.
 */
public class NoticeCommentVO extends CommonVO{
	private int comment_seq;				//코멘트 seq
	private int seq;						//공지사항 seq
	private String comments;				//코멘트 내용
	private String emp_no;					//사원번호
	private List<NoticeCommentVO> list;		//코멘트 리스트
	private String pagingStr;				//코멘트 페이징
	private int currentPage;				//현재페이지
	private int commentCnt;					//코멘트 카운트
	
	/**
	 * @return the comment_seq
	 */
	public int getComment_seq() {
		return comment_seq;
	}
	/**
	 * @param comment_seq the comment_seq to set
	 */
	public void setComment_seq(int comment_seq) {
		this.comment_seq = comment_seq;
	}
	/**
	 * @return the seq
	 */
	public int getSeq() {
		return seq;
	}
	/**
	 * @param seq the seq to set
	 */
	public void setSeq(int seq) {
		this.seq = seq;
	}
	/**
	 * @return the comments
	 */
	public String getComments() {
		return comments;
	}
	/**
	 * @param comments the comments to set
	 */
	public void setComments(String comments) {
		this.comments = comments;
	}
	/**
	 * @return the emp_no
	 */
	public String getEmp_no() {
		return emp_no;
	}
	/**
	 * @param emp_no the emp_no to set
	 */
	public void setEmp_no(String emp_no) {
		this.emp_no = emp_no;
	}
	/**
	 * @return the list
	 */
	public List<NoticeCommentVO> getList() {
		return list;
	}
	/**
	 * @param list the list to set
	 */
	public void setList(List<NoticeCommentVO> list) {
		this.list = list;
	}
	/**
	 * @return the pagingStr
	 */
	public String getPagingStr() {
		return pagingStr;
	}
	/**
	 * @param pagingStr the pagingStr to set
	 */
	public void setPagingStr(String pagingStr) {
		this.pagingStr = pagingStr;
	}
	/**
	 * @return the currentPage
	 */
	public int getCurrentPage() {
		return currentPage;
	}
	/**
	 * @param currentPage the currentPage to set
	 */
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	/**
	 * @return the commentCnt
	 */
	public int getCommentCnt() {
		return commentCnt;
	}
	/**
	 * @param commentCnt the commentCnt to set
	 */
	public void setCommentCnt(int commentCnt) {
		this.commentCnt = commentCnt;
	}
}
