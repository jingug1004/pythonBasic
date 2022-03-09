/**
  * Hana Project
  * Copyright 2014 iRush Co.,
  *
  */
package com.hanaph.gw.co.code.vo;

import com.hanaph.gw.co.common.vo.CommonVO;

/**
 * <pre>
 * Class Name : CodeVO.java
 * 설명 : 코드 관리 VO
 * 
 * Modification Information
 *    수정일      수정자              수정 내용
 *  ------------ -------------- --------------------------------
 *  2014. 10. 14.      Jung.Jin.Muk          
 * </pre>
 * 
 * @version : 
 * @author  : Jung.Jin.Muk(pc123pc@irush.co.kr)
 * @since   : 2014. 10. 14.
 */
public class CodeVO extends CommonVO{
	
	private String cd;			//코드번호
	private String m_cd;		//대분류
	private String s_cd;		//소분류
	private String cd_nm;		//코드명
	private String descr;		//설명
	private String ordering;	//정렬순서	
	private String use_yn;		//사용여부
	private boolean result;		//업뎃성공여부
	private String status;		//저장,수정 구분값
	
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
	 * @return the m_cd
	 */
	public String getM_cd() {
		return m_cd;
	}
	/**
	 * @param m_cd the m_cd to set
	 */
	public void setM_cd(String m_cd) {
		this.m_cd = m_cd;
	}
	/**
	 * @return the s_cd
	 */
	public String getS_cd() {
		return s_cd;
	}
	/**
	 * @param s_cd the s_cd to set
	 */
	public void setS_cd(String s_cd) {
		this.s_cd = s_cd;
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
	 * @return the result
	 */
	public boolean isResult() {
		return result;
	}
	/**
	 * @param result the result to set
	 */
	public void setResult(boolean result) {
		this.result = result;
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
}
