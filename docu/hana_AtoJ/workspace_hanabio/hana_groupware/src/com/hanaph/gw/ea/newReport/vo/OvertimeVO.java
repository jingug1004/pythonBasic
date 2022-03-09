/**
  * Hana Project
  * Copyright 2014 iRush Co.,
  *
  */
package com.hanaph.gw.ea.newReport.vo;

import java.util.List;

/**
 * <pre>
 * Class Name : OvertimeVO.java
 * 설명 : 시간외근무신청서, 시간외근무내역서 VO
 * 
 * Modification Information
 *    수정일      수정자              수정 내용
 *  ------------ -------------- --------------------------------
 *  2014. 12. 29.      Jung.Jin.Muk          
 * </pre>
 * 
 * @version : 1.0
 * @author  : Jung.Jin.Muk(pc123pc@irush.co.kr)
 * @since   : 2014. 12. 29.
 */
public class OvertimeVO {
	private int seq; 				//시간외근무신청서 시퀀스
	private String approval_seq; 	//문서번호	
	private String dept_cd; 		//부서코드
	private String dept_nm; 		//부서명
	private String emp_no; 			//사원번호
	private String emp_nm; 			//사원명
	private String biz_content; 	//업무내용
	private String work_due_ymd; 	//근무예정일
	private String work_start_hh; 	//근무시작시
	private String work_start_mi;	//근무시작분
	private String work_end_hh;		//근무종료시
	private String work_end_mi;		//근무종료분
	private String work_due_hh; 	//근무예정시
	private String work_due_mi; 	//근무예정분
	private String work_real_hh;	//실근무시
	private String work_real_mi;	//실근무분
	private String bigo;		 	//비고
	private String bigo_nm;		 	//비고
	private List<OvertimeVO> overtimeVO; //OvertimeVO List
	
	private String approval_seq_old;//시간외근무신청서 문서번호 시간외 근무 내역서에 등록
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
	 * @return the dept_cd
	 */
	public String getDept_cd() {
		return dept_cd;
	}
	/**
	 * @param dept_cd the dept_cd to set
	 */
	public void setDept_cd(String dept_cd) {
		this.dept_cd = dept_cd;
	}
	/**
	 * @return the dept_nm
	 */
	public String getDept_nm() {
		return dept_nm;
	}
	/**
	 * @param dept_nm the dept_nm to set
	 */
	public void setDept_nm(String dept_nm) {
		this.dept_nm = dept_nm;
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
	 * @return the emp_nm
	 */
	public String getEmp_nm() {
		return emp_nm;
	}
	/**
	 * @param emp_nm the emp_nm to set
	 */
	public void setEmp_nm(String emp_nm) {
		this.emp_nm = emp_nm;
	}
	/**
	 * @return the biz_content
	 */
	public String getBiz_content() {
		return biz_content;
	}
	/**
	 * @param biz_content the biz_content to set
	 */
	public void setBiz_content(String biz_content) {
		this.biz_content = biz_content;
	}
	/**
	 * @return the work_due_ymd
	 */
	public String getWork_due_ymd() {
		return work_due_ymd;
	}
	/**
	 * @param work_due_ymd the work_due_ymd to set
	 */
	public void setWork_due_ymd(String work_due_ymd) {
		this.work_due_ymd = work_due_ymd;
	}
	/**
	 * @return the work_start_hh
	 */
	public String getWork_start_hh() {
		return work_start_hh;
	}
	/**
	 * @param work_start_hh the work_start_hh to set
	 */
	public void setWork_start_hh(String work_start_hh) {
		this.work_start_hh = work_start_hh;
	}
	/**
	 * @return the work_start_mi
	 */
	public String getWork_start_mi() {
		return work_start_mi;
	}
	/**
	 * @param work_start_mi the work_start_mi to set
	 */
	public void setWork_start_mi(String work_start_mi) {
		this.work_start_mi = work_start_mi;
	}
	/**
	 * @return the work_end_hh
	 */
	public String getWork_end_hh() {
		return work_end_hh;
	}
	/**
	 * @param work_end_hh the work_end_hh to set
	 */
	public void setWork_end_hh(String work_end_hh) {
		this.work_end_hh = work_end_hh;
	}
	/**
	 * @return the work_end_mi
	 */
	public String getWork_end_mi() {
		return work_end_mi;
	}
	/**
	 * @param work_end_mi the work_end_mi to set
	 */
	public void setWork_end_mi(String work_end_mi) {
		this.work_end_mi = work_end_mi;
	}
	/**
	 * @return the work_due_hh
	 */
	public String getWork_due_hh() {
		return work_due_hh;
	}
	/**
	 * @param work_due_hh the work_due_hh to set
	 */
	public void setWork_due_hh(String work_due_hh) {
		this.work_due_hh = work_due_hh;
	}
	/**
	 * @return the work_due_mi
	 */
	public String getWork_due_mi() {
		return work_due_mi;
	}
	/**
	 * @param work_due_mi the work_due_mi to set
	 */
	public void setWork_due_mi(String work_due_mi) {
		this.work_due_mi = work_due_mi;
	}
	/**
	 * @return the work_real_hh
	 */
	public String getWork_real_hh() {
		return work_real_hh;
	}
	/**
	 * @param work_real_hh the work_real_hh to set
	 */
	public void setWork_real_hh(String work_real_hh) {
		this.work_real_hh = work_real_hh;
	}
	/**
	 * @return the work_real_mi
	 */
	public String getWork_real_mi() {
		return work_real_mi;
	}
	/**
	 * @param work_real_mi the work_real_mi to set
	 */
	public void setWork_real_mi(String work_real_mi) {
		this.work_real_mi = work_real_mi;
	}
	/**
	 * @return the bigo
	 */
	public String getBigo() {
		return bigo;
	}
	/**
	 * @param bigo the bigo to set
	 */
	public void setBigo(String bigo) {
		this.bigo = bigo;
	}
	/**
	 * @return the overtimeVO
	 */
	public List<OvertimeVO> getOvertimeVO() {
		return overtimeVO;
	}
	/**
	 * @param overtimeVO the overtimeVO to set
	 */
	public void setOvertimeVO(List<OvertimeVO> overtimeVO) {
		this.overtimeVO = overtimeVO;
	}
	/**
	 * @return the approval_seq_old
	 */
	public String getApproval_seq_old() {
		return approval_seq_old;
	}
	/**
	 * @param approval_seq_old the approval_seq_old to set
	 */
	public void setApproval_seq_old(String approval_seq_old) {
		this.approval_seq_old = approval_seq_old;
	}
	/**
	 * @return the bigo_nm
	 */
	public String getBigo_nm() {
		return bigo_nm;
	}
	/**
	 * @param bigo_nm the bigo_nm to set
	 */
	public void setBigo_nm(String bigo_nm) {
		this.bigo_nm = bigo_nm;
	}
	
	
}
