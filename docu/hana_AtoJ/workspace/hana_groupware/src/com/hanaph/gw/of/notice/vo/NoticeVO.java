/**
  * Hana Project
  * Copyright 2014 iRush Co.,
  *
  */
package com.hanaph.gw.of.notice.vo;

import com.hanaph.gw.co.common.vo.CommonVO;

/**
 * <pre>
 * Class Name : NoticeVO.java
 * 설명 : 공지사항 정보
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
public class NoticeVO extends CommonVO{

	private String seq;			//공지사항seq
	private String subject;		//제목
	private String noti_kind;	//공지사항 구분
	private String contents;	//내용
	private int read_cnt;		//읽은 수
	private String read_yn;		//열람여부
	private int comment_cnt;	//댓글 수
	private String start_ymd;	//공지사항 기간
	private String end_ymd;		//공지사항 기간
	private String delete_yn;	//삭제여부
	private int attach_cnt;		//파일 갯수
	private String emp_ko_nm;	//사원이름
	
	/**
	 * @return the seq
	 */
	public String getSeq() {
		return seq;
	}
	/**
	 * @param seq the seq to set
	 */
	public void setSeq(String seq) {
		this.seq = seq;
	}
	/**
	 * @return the subject
	 */
	public String getSubject() {
		return subject;
	}
	/**
	 * @param subject the subject to set
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}
	/**
	 * @return the noti_kind
	 */
	public String getNoti_kind() {
		return noti_kind;
	}
	/**
	 * @param noti_kind the noti_kind to set
	 */
	public void setNoti_kind(String noti_kind) {
		this.noti_kind = noti_kind;
	}
	/**
	 * @return the contents
	 */
	public String getContents() {
		return contents;
	}
	/**
	 * @param contents the contents to set
	 */
	public void setContents(String contents) {
		this.contents = contents;
	}
	/**
	 * @return the read_cnt
	 */
	public int getRead_cnt() {
		return read_cnt;
	}
	/**
	 * @param read_cnt the read_cnt to set
	 */
	public void setRead_cnt(int read_cnt) {
		this.read_cnt = read_cnt;
	}
	/**
	 * @return the read_yn
	 */
	public String getRead_yn() {
		return read_yn;
	}
	/**
	 * @param read_yn the read_yn to set
	 */
	public void setRead_yn(String read_yn) {
		this.read_yn = read_yn;
	}
	/**
	 * @return the comment_cnt
	 */
	public int getComment_cnt() {
		return comment_cnt;
	}
	/**
	 * @param comment_cnt the comment_cnt to set
	 */
	public void setComment_cnt(int comment_cnt) {
		this.comment_cnt = comment_cnt;
	}
	/**
	 * @return the start_ymd
	 */
	public String getStart_ymd() {
		return start_ymd;
	}
	/**
	 * @param start_ymd the start_ymd to set
	 */
	public void setStart_ymd(String start_ymd) {
		this.start_ymd = start_ymd;
	}
	/**
	 * @return the end_ymd
	 */
	public String getEnd_ymd() {
		return end_ymd;
	}
	/**
	 * @param end_ymd the end_ymd to set
	 */
	public void setEnd_ymd(String end_ymd) {
		this.end_ymd = end_ymd;
	}
	/**
	 * @return the delete_yn
	 */
	public String getDelete_yn() {
		return delete_yn;
	}
	/**
	 * @param delete_yn the delete_yn to set
	 */
	public void setDelete_yn(String delete_yn) {
		this.delete_yn = delete_yn;
	}
	/**
	 * @return the attach_cnt
	 */
	public int getAttach_cnt() {
		return attach_cnt;
	}
	/**
	 * @param attach_cnt the attach_cnt to set
	 */
	public void setAttach_cnt(int attach_cnt) {
		this.attach_cnt = attach_cnt;
	}
	/**
	 * @return the emp_ko_nm
	 */
	public String getEmp_ko_nm() {
		return emp_ko_nm;
	}
	/**
	 * @param emp_ko_nm the emp_ko_nm to set
	 */
	public void setEmp_ko_nm(String emp_ko_nm) {
		this.emp_ko_nm = emp_ko_nm;
	}
	
}
