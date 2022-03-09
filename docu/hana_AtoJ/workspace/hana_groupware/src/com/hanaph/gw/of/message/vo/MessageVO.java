/**
  * Hana Project
  * Copyright 2014 iRush Co.,
  *
  */
package com.hanaph.gw.of.message.vo;

import com.hanaph.gw.co.common.vo.CommonVO;
import com.hanaph.gw.co.fileAttach.vo.FileAttachVO;
import com.hanaph.gw.pe.member.vo.MemberVO;

import java.util.List;

/**
 * <pre>
 * Class Name : MessageVO.java
 * 설명 : 쪽지 보내기 VO
 * 
 * Modification Information
 *    수정일      수정자              수정 내용
 *  ------------ -------------- --------------------------------
 *  2014. 11. 27.      Jung.Jin.Muk          
 * </pre>
 * 
 * @version : 
 * @author  : Jung.Jin.Muk(pc123pc@irush.co.kr)
 * @since   : 2014. 11. 27.
 */
public class MessageVO extends CommonVO{
	private String message_seq;		//쪽지seq
	private String contents;		//쪽지내용
	private String subject;			//제목
	private int read_cnt;			//읽은 수
	private String read_yn;			//읽은 여부
	private int attach_cnt;			//첨부파일갯수
	private String emp_no;			//사원번호
	private String emp_ko_nm;		//이름	
	private String read_dt;			//읽은 일시
	private String dept_ko_nm;		//부서
	private String type;			//받은,보낸 구분타입
	private List<MemberVO> list;	//받은이
	
	private List<FileAttachVO> attachList;	//첨부파일 리스트

	public String getMessage_seq() {
		return message_seq;
	}
	public void setMessage_seq(String message_seq) {
		this.message_seq = message_seq;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getContents() {
		return contents;
	}
	public void setContents(String contents) {
		this.contents = contents;
	}
	public int getRead_cnt() {
		return read_cnt;
	}
	public void setRead_cnt(int read_cnt) {
		this.read_cnt = read_cnt;
	}
	public String getRead_yn() {
		return read_yn;
	}
	public void setRead_yn(String read_yn) {
		this.read_yn = read_yn;
	}
	public int getAttach_cnt() {
		return attach_cnt;
	}
	public void setAttach_cnt(int attach_cnt) {
		this.attach_cnt = attach_cnt;
	}
	public String getEmp_no() {
		return emp_no;
	}
	public void setEmp_no(String emp_no) {
		this.emp_no = emp_no;
	}
	public List<MemberVO> getList() {
		return list;
	}
	public void setList(List<MemberVO> list) {
		this.list = list;
	}
	public String getEmp_ko_nm() {
		return emp_ko_nm;
	}
	public String getRead_dt() {
		return read_dt;
	}
	public void setRead_dt(String read_dt) {
		this.read_dt = read_dt;
	}
	public void setDept_ko_nm(String dept_ko_nm) {
		this.dept_ko_nm = dept_ko_nm;
	}
	public String getDept_ko_nm() {
		return dept_ko_nm;
	}
	public void setEmp_ko_nm(String emp_ko_nm) {
		this.emp_ko_nm = emp_ko_nm;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public List<FileAttachVO> getAttachList() {
		return attachList;
	}
	public void setAttachList(List<FileAttachVO> attachList) {
		this.attachList = attachList;
	}
}
