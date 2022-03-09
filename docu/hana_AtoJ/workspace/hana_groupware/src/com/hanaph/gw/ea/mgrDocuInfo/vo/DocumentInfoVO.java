/**
 * Hana Project
 * Copyright 2014 iRush Co.,
 *
 */
package com.hanaph.gw.ea.mgrDocuInfo.vo;

/**
 * <pre>
 * Class Name : DocumentInfoVO.java
 * 설명 : 양식정보관리 VO
 * 
 * Modification Information
 *    수정일      수정자              수정 내용
 *  ------------ -------------- --------------------------------
 *  2014. 12. 18.      CHOIILJI
 * </pre>
 * 
 * @version : 1.0
 * @author : CHOIILJI(choiilji@irush.co.kr)
 * @since : 2014. 12. 18.
 */
public class DocumentInfoVO {
	private int rnum; //리스트 번호
	private int docu_seq;// 문서seq
	private String dept_cd;// 부서코드
	private String docu_nm;// 문서명
	private String docu_cd;// 문서코드
	private String decide_yn;// 전결여부
	private String security_yn;// 대외비여부
	private String contents;// 설명
	private String create_dt;// 등록일시
	private String create_no;// 등록자
	private String modify_dt;// 수정일시
	private String modify_no;// 수정자
	private String use_yn;// 사용여부
	
	private String dept_ko_nm; // 부서명(한글)
	
	/**
	 * @return the rnum
	 */
	public int getRnum() {
		return rnum;
	}
	/**
	 * @param rnum the rnum to set
	 */
	public void setRnum(int rnum) {
		this.rnum = rnum;
	}
	/**
	 * @return the docu_seq
	 */
	public int getDocu_seq() {
		return docu_seq;
	}
	/**
	 * @param docu_seq the docu_seq to set
	 */
	public void setDocu_seq(int docu_seq) {
		this.docu_seq = docu_seq;
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
	 * @return the docu_nm
	 */
	public String getDocu_nm() {
		return docu_nm;
	}
	/**
	 * @param docu_nm the docu_nm to set
	 */
	public void setDocu_nm(String docu_nm) {
		this.docu_nm = docu_nm;
	}
	/**
	 * @return the docu_cd
	 */
	public String getDocu_cd() {
		return docu_cd;
	}
	/**
	 * @param docu_cd the docu_cd to set
	 */
	public void setDocu_cd(String docu_cd) {
		this.docu_cd = docu_cd;
	}
	/**
	 * @return the decide_yn
	 */
	public String getDecide_yn() {
		return decide_yn;
	}
	/**
	 * @param decide_yn the decide_yn to set
	 */
	public void setDecide_yn(String decide_yn) {
		this.decide_yn = decide_yn;
	}
	/**
	 * @return the security_yn
	 */
	public String getSecurity_yn() {
		return security_yn;
	}
	/**
	 * @param security_yn the security_yn to set
	 */
	public void setSecurity_yn(String security_yn) {
		this.security_yn = security_yn;
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
	 * @return the create_dt
	 */
	public String getCreate_dt() {
		return create_dt;
	}
	/**
	 * @param create_dt the create_dt to set
	 */
	public void setCreate_dt(String create_dt) {
		this.create_dt = create_dt;
	}
	/**
	 * @return the create_no
	 */
	public String getCreate_no() {
		return create_no;
	}
	/**
	 * @param create_no the create_no to set
	 */
	public void setCreate_no(String create_no) {
		this.create_no = create_no;
	}
	/**
	 * @return the modify_dt
	 */
	public String getModify_dt() {
		return modify_dt;
	}
	/**
	 * @param modify_dt the modify_dt to set
	 */
	public void setModify_dt(String modify_dt) {
		this.modify_dt = modify_dt;
	}
	/**
	 * @return the modify_no
	 */
	public String getModify_no() {
		return modify_no;
	}
	/**
	 * @param modify_no the modify_no to set
	 */
	public void setModify_no(String modify_no) {
		this.modify_no = modify_no;
	}
	/**
	 * @return the use_yn
	 */
	public String getUse_yn() {
		return use_yn;
	}
	/**
	 * @param use_yn the use_yn to set
	 */
	public void setUse_yn(String use_yn) {
		this.use_yn = use_yn;
	}
	/**
	 * @return the dept_ko_nm
	 */
	public String getDept_ko_nm() {
		return dept_ko_nm;
	}
	/**
	 * @param dept_ko_nm the dept_ko_nm to set
	 */
	public void setDept_ko_nm(String dept_ko_nm) {
		this.dept_ko_nm = dept_ko_nm;
	}

}
