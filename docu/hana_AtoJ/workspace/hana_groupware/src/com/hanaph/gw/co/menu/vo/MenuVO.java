/**
  * Hana Project
  * Copyright 2014 iRush Co.,
  *
  */
package com.hanaph.gw.co.menu.vo;

import com.hanaph.gw.co.common.vo.CommonVO;

/**
 * <pre>
 * Class Name : MenuVO.java
 * 설명 : 메뉴 VO
 * 
 * Modification Information
 *    수정일      수정자              수정 내용
 *  ------------ -------------- --------------------------------
 *  2014. 10. 28.      Jung.Jin.Muk          
 * </pre>
 * 
 * @version : 
 * @author  : Jung.Jin.Muk(pc123pc@irush.co.kr)
 * @since   : 2014. 10. 28.
 */
public class MenuVO extends CommonVO{
	
	private String menu_parents;	//매뉴번호
	private String menu_child;		//아래매뉴번호
	private String menu_nm;			//매뉴이름
	private String descr;			//매뉴설명
	private String ordering;		//순서
	private String url;				//url
	private String status;			//저장,수정 구분값
	private int cnt;				//카운트
	private String parents_menu_nm;	//매뉴이름
	private String add_menu_nm;	    //추가되는 매뉴이름
	
	/**
	 * @return the menu_parents
	 */
	public String getMenu_parents() {
		return menu_parents;
	}
	/**
	 * @param menu_parents the menu_parents to set
	 */
	public void setMenu_parents(String menu_parents) {
		this.menu_parents = menu_parents;
	}
	/**
	 * @return the menu_child
	 */
	public String getMenu_child() {
		return menu_child;
	}
	/**
	 * @param menu_child the menu_child to set
	 */
	public void setMenu_child(String menu_child) {
		this.menu_child = menu_child;
	}
	/**
	 * @return the menu_nm
	 */
	public String getMenu_nm() {
		return menu_nm;
	}
	/**
	 * @param menu_nm the menu_nm to set
	 */
	public void setMenu_nm(String menu_nm) {
		this.menu_nm = menu_nm;
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
	 * @return the ordering
	 */
	public String getOrdering() {
		return ordering;
	}
	/**
	 * @param ordering the ordering to set
	 */
	public void setOrdering(String ordering) {
		this.ordering = ordering;
	}
	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}
	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
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
	 * @return the cnt
	 */
	public int getCnt() {
		return cnt;
	}
	/**
	 * @param cnt the cnt to set
	 */
	public void setCnt(int cnt) {
		this.cnt = cnt;
	}
	/**
	 * @return the parents_menu_nm
	 */
	public String getParents_menu_nm() {
		return parents_menu_nm;
	}
	/**
	 * @param parents_menu_nm the parents_menu_nm to set
	 */
	public void setParents_menu_nm(String parents_menu_nm) {
		this.parents_menu_nm = parents_menu_nm;
	}
	
	public String getAdd_menu_nm() {
		return add_menu_nm;
	}
	
	public void setAdd_menu_nm(String add_menu_nm) {
		this.add_menu_nm = add_menu_nm;
	}
	
	
}
