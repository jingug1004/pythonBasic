/**
  * Hana Project
  * Copyright 2014 iRush Co.,
  *
  */
package com.hanaph.gw.of.board.vo;

/**
 * <pre>
 * Class Name : BoardTargetVO.java
 * 설명 : 조회수 정보
 * 
 * Modification Information
 *    수정일      수정자              수정 내용
 *  ------------ -------------- --------------------------------
 *  2014. 12. 02.      wung.kang          
 * </pre>
 * 
 * @version : 
 * @author  : wung.kang
 * @since   : 2014. 12. 02.
 */
public class BoardTargetVO {
	
	private String emp_no;		//사원번호
	private int seq;			//시퀀스
	private String read_yn;		//열람여부
	private String read_dt;		//열람시간
	
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
	 * @return the read_dt
	 */
	public String getRead_dt() {
		return read_dt;
	}
	/**
	 * @param read_dt the read_dt to set
	 */
	public void setRead_dt(String read_dt) {
		this.read_dt = read_dt;
	}
	
}
