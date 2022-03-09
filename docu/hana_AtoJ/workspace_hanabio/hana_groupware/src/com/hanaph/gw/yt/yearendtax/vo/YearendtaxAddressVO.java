/**
  * Hana Project
  * Copyright 2014 iRush Co.,
  *
  */
package com.hanaph.gw.yt.yearendtax.vo;


/**
 * <pre>
 * Class Name : YearendtaxAddressVO.java
 * 설명 : 연말정산 주소 VO
 * 
 * Modification Information
 *    수정일      수정자              수정 내용
 *  ------------ -------------- --------------------------------
 *  2015. 2. 24.      jung jin muk          
 * </pre>
 * 
 * @version : 
 * @author  : jung jin muk(pc123pc@irush.co.kr)
 * @since   : 2015. 2. 24.
 */
public class YearendtaxAddressVO {
	
	private String emp_no; //사원번호
	private String tel_no; //휴대전화번호
	private String zip_cd;	//우편번호
	private String sido; //시도
	private String gungu; //군
	private String law_dong_nm; //동
	private String doro_nm; //도로명
	private String bldg_no1; //빌딩이름1
	private String bldg_no2; //빌딩이름2
	private String address1; //주소1
	private String address2; //주소2
	private int addressCnt; //주소 저장후 카운트
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
	 * @return the tel_no
	 */
	public String getTel_no() {
		return tel_no;
	}
	/**
	 * @param tel_no the tel_no to set
	 */
	public void setTel_no(String tel_no) {
		this.tel_no = tel_no;
	}
	/**
	 * @return the zip_cd
	 */
	public String getZip_cd() {
		return zip_cd;
	}
	/**
	 * @param zip_cd the zip_cd to set
	 */
	public void setZip_cd(String zip_cd) {
		this.zip_cd = zip_cd;
	}
	/**
	 * @return the sido
	 */
	public String getSido() {
		return sido;
	}
	/**
	 * @param sido the sido to set
	 */
	public void setSido(String sido) {
		this.sido = sido;
	}
	/**
	 * @return the gungu
	 */
	public String getGungu() {
		return gungu;
	}
	/**
	 * @param gungu the gungu to set
	 */
	public void setGungu(String gungu) {
		this.gungu = gungu;
	}
	/**
	 * @return the law_dong_nm
	 */
	public String getLaw_dong_nm() {
		return law_dong_nm;
	}
	/**
	 * @param law_dong_nm the law_dong_nm to set
	 */
	public void setLaw_dong_nm(String law_dong_nm) {
		this.law_dong_nm = law_dong_nm;
	}
	/**
	 * @return the doro_nm
	 */
	public String getDoro_nm() {
		return doro_nm;
	}
	/**
	 * @param doro_nm the doro_nm to set
	 */
	public void setDoro_nm(String doro_nm) {
		this.doro_nm = doro_nm;
	}
	/**
	 * @return the bldg_no1
	 */
	public String getBldg_no1() {
		return bldg_no1;
	}
	/**
	 * @param bldg_no1 the bldg_no1 to set
	 */
	public void setBldg_no1(String bldg_no1) {
		this.bldg_no1 = bldg_no1;
	}
	/**
	 * @return the bldg_no2
	 */
	public String getBldg_no2() {
		return bldg_no2;
	}
	/**
	 * @param bldg_no2 the bldg_no2 to set
	 */
	public void setBldg_no2(String bldg_no2) {
		this.bldg_no2 = bldg_no2;
	}
	/**
	 * @return the address1
	 */
	public String getAddress1() {
		return address1;
	}
	/**
	 * @param address1 the address1 to set
	 */
	public void setAddress1(String address1) {
		this.address1 = address1;
	}
	/**
	 * @return the address2
	 */
	public String getAddress2() {
		return address2;
	}
	/**
	 * @param address2 the address2 to set
	 */
	public void setAddress2(String address2) {
		this.address2 = address2;
	}
	/**
	 * @return the addressCnt
	 */
	public int getAddressCnt() {
		return addressCnt;
	}
	/**
	 * @param addressCnt the addressCnt to set
	 */
	public void setAddressCnt(int addressCnt) {
		this.addressCnt = addressCnt;
	}
	
}
