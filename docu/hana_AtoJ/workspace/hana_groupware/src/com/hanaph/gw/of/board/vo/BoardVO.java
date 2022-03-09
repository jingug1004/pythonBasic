package com.hanaph.gw.of.board.vo;

import com.hanaph.gw.co.common.vo.CommonVO;

/**
 * <pre>
 * Class Name : BoardVO.java
 * 설명 : 게시판 VO
 * 
 * Modification Information
 *    수정일      수정자              수정 내용
 *  ------------ -------------- --------------------------------
 *  2015. 1. 8.      Jung.Jin.Muk          
 * </pre>
 * 
 * @version : 
 * @author  : Jung.Jin.Muk(pc123pc@irush.co.kr)
 * @since   : 2015. 1. 8.
 */
public class BoardVO extends CommonVO {

	private String seq;				//게시글 번호
	private int reply_grp;			//연관된 글을 묶어주는 컬럼
	private int reply_level;		//글의 정렬 순서를 지정해 주는 컬럼
	private int reply_step;			//몇 번째 단계의 답변인지 나타내는 컬럼
	private String cd;				//게시판 구분 코드
	private String cd_nm;			//게시판 구분 코드명
	private String subject;			//제목
	private String contents;		//내용
	private int read_cnt;			//조회수
	private String read_yn;			//열람 여부
	private String start_ymd;		//게시 시작일
	private String end_ymd;			//게시 종료일
	private String comment_cnt;		//댓글 갯수
	private int attach_cnt;			//파일첨부 갯수
	private String emp_ko_nm;		//사원이름
	
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
	 * @return the reply_grp
	 */
	public int getReply_grp() {
		return reply_grp;
	}
	/**
	 * @param reply_grp the reply_grp to set
	 */
	public void setReply_grp(int reply_grp) {
		this.reply_grp = reply_grp;
	}
	/**
	 * @return the reply_level
	 */
	public int getReply_level() {
		return reply_level;
	}
	/**
	 * @param reply_level the reply_level to set
	 */
	public void setReply_level(int reply_level) {
		this.reply_level = reply_level;
	}
	/**
	 * @return the reply_step
	 */
	public int getReply_step() {
		return reply_step;
	}
	/**
	 * @param reply_step the reply_step to set
	 */
	public void setReply_step(int reply_step) {
		this.reply_step = reply_step;
	}
	/**
	 * @return the cd
	 */
	public String getCd() {
		return cd;
	}
	/**
	 * @param cd the cd to set
	 */
	public void setCd(String cd) {
		this.cd = cd;
	}
	/**
	 * @return the cd_nm
	 */
	public String getCd_nm() {
		return cd_nm;
	}
	/**
	 * @param cd_nm the cd_nm to set
	 */
	public void setCd_nm(String cd_nm) {
		this.cd_nm = cd_nm;
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
	 * @return the comment_cnt
	 */
	public String getComment_cnt() {
		return comment_cnt;
	}
	/**
	 * @param comment_cnt the comment_cnt to set
	 */
	public void setComment_cnt(String comment_cnt) {
		this.comment_cnt = comment_cnt;
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
