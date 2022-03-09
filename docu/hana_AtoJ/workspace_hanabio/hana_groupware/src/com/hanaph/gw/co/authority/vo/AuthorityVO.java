/**
  * Hana Project
  * Copyright 2014 iRush Co.,
  *
  */
package com.hanaph.gw.co.authority.vo;

import java.util.List;

import com.hanaph.gw.co.common.vo.CommonVO;

/**
 * <pre>
 * Class Name : AuthorityVO.java
 * 설명 : 권한 관리 VO
 * 
 * Modification Information
 *    수정일      수정자              수정 내용
 *  ------------ -------------- --------------------------------
 *  2014. 10. 27.      Jung.Jin.Muk          
 * </pre>
 * 
 * @version : 
 * @author  : Jung.Jin.Muk(pc123pc@irush.co.kr)
 * @since   : 2014. 10. 27.
 */
public class AuthorityVO extends CommonVO{
	private int rnum;			//권한리스트 번호
	private String auth_seq;	//권한 시퀀스
	private String auth_nm;		//권한 이름
	private String descr;		//권한 설명
	private String menu_cd;		//메뉴번호
	private String emp_no;		//사번
	private String emp_ko_nm;	//이름
	private String status;		//메뉴등록,직원등록 구분상태
	private List<AuthorityVO> emp_no_list;	//맵핑된 권한 직원 목록
	
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
	 * @return the auth_seq
	 */
	public String getAuth_seq() {
		return auth_seq;
	}
	/**
	 * @param auth_seq the auth_seq to set
	 */
	public void setAuth_seq(String auth_seq) {
		this.auth_seq = auth_seq;
	}
	/**
	 * @return the auth_nm
	 */
	public String getAuth_nm() {
		return auth_nm;
	}
	/**
	 * @param auth_nm the auth_nm to set
	 */
	public void setAuth_nm(String auth_nm) {
		this.auth_nm = auth_nm;
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
	 * @return the menu_cd
	 */
	public String getMenu_cd() {
		return menu_cd;
	}
	/**
	 * @param menu_cd the menu_cd to set
	 */
	public void setMenu_cd(String menu_cd) {
		this.menu_cd = menu_cd;
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
	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * @return the emp_no_list
	 */
	public List<AuthorityVO> getEmp_no_list() {
		return emp_no_list;
	}
	/**
	 * @param emp_no_list the emp_no_list to set
	 */
	public void setEmp_no_list(List<AuthorityVO> emp_no_list) {
		this.emp_no_list = emp_no_list;
	}
	
}
