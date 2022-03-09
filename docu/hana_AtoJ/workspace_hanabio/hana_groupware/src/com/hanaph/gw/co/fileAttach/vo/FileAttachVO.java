package com.hanaph.gw.co.fileAttach.vo;

import com.hanaph.gw.co.common.vo.CommonVO;

/**
 * <pre>
 * Class Name : FileAttachVO.java
 * 설명 : 첨부파일 VO
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
public class FileAttachVO extends CommonVO {

	private int file_seq;			//파일 번호
	private String seq;				//게시물 번호
	private String cd;				//코드
	private String descr;			//설명
	private String origin_file_nm;	//등록한 파일명
	private String file_nm;			//사용여부서버에 저장되는 파일명
	private String file_path;		//파일경로
	private long file_size;			//파일 사이즈
	private String file_ext;		//파일 확장자
	private String approval_seq;	//결재 시퀀스
	private int opinion_seq;	//의견 시퀀스
	
	/**
	 * @return the file_seq
	 */
	public int getFile_seq() {
		return file_seq;
	}
	/**
	 * @param file_seq the file_seq to set
	 */
	public void setFile_seq(int file_seq) {
		this.file_seq = file_seq;
	}
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
	 * @return the descr
	 */
	public String getDescr() {
		return descr;
	}
	/**
	 * @param descr the descr to set
	 */
	public void setDescr(String descr) {
		this.descr = descr;
	}
	/**
	 * @return the origin_file_nm
	 */
	public String getOrigin_file_nm() {
		return origin_file_nm;
	}
	/**
	 * @param origin_file_nm the origin_file_nm to set
	 */
	public void setOrigin_file_nm(String origin_file_nm) {
		this.origin_file_nm = origin_file_nm;
	}
	/**
	 * @return the file_nm
	 */
	public String getFile_nm() {
		return file_nm;
	}
	/**
	 * @param file_nm the file_nm to set
	 */
	public void setFile_nm(String file_nm) {
		this.file_nm = file_nm;
	}
	/**
	 * @return the file_path
	 */
	public String getFile_path() {
		return file_path;
	}
	/**
	 * @param file_path the file_path to set
	 */
	public void setFile_path(String file_path) {
		this.file_path = file_path;
	}
	/**
	 * @return the file_size
	 */
	public long getFile_size() {
		return file_size;
	}
	/**
	 * @param file_size the file_size to set
	 */
	public void setFile_size(long file_size) {
		this.file_size = file_size;
	}
	/**
	 * @return the file_ext
	 */
	public String getFile_ext() {
		return file_ext;
	}
	/**
	 * @param file_ext the file_ext to set
	 */
	public void setFile_ext(String file_ext) {
		this.file_ext = file_ext;
	}
	/**
	 * @return the approval_seq
	 */
	public String getApproval_seq() {
		return approval_seq;
	}
	/**
	 * @param approval_seq the approval_seq to set
	 */
	public void setApproval_seq(String approval_seq) {
		this.approval_seq = approval_seq;
	}
	/**
	 * @param getOpinion_seq to set
	 */
	public int getOpinion_seq() {
		return opinion_seq;
	}
	/**
	 * @param getOpinion_seq to get
	 */
	public void setOpinion_seq(int opinion_seq) {
		this.opinion_seq = opinion_seq;
	}
	
	
}
