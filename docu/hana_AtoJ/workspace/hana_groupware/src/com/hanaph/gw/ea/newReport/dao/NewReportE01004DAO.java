/**
  * Hana Project
  * Copyright 2014 iRush Co.,
  *
  */
package com.hanaph.gw.ea.newReport.dao;

import com.hanaph.gw.ea.newReport.vo.VaporizeVO;


/**
 * <pre>
 * Class Name : NewReportE01004DAO.java
 * 설명 : 기화기기안서
 * 
 * Modification Information
 *    수정일      수정자              수정 내용
 *  ------------ -------------- --------------------------------
 *  2015. 1. 16.      Jung.Jin.Muk          
 * </pre>
 * 
 * @version : 
 * @author  : Jung.Jin.Muk(pc123pc@irush.co.kr)
 * @since   : 2015. 1. 16.
 */
public interface NewReportE01004DAO {
	
	/**
	 * <pre>
	 * 1. 개요     : 기화기기안서를 등록 한다.
	 * 2. 처리내용 : 기화기기안서를 등록 한다.
	 * </pre>
	 * @Method Name : insertVaporize
	 * @param paramAmVO
	 * @param paramVrVO
	 * @return
	 */
	void insertVaporize(VaporizeVO paramVrVO);

	/**
	 * <pre>
	 * 1. 개요     : 기화기기안서 상세정보를 가져온다.
	 * 2. 처리내용 : 기화기기안서 상세정보를 가져온다.
	 * </pre>
	 * @Method Name : vaporizeDetail
	 * @param approval_seq
	 * @return
	 */
	VaporizeVO vaporizeDetail(String approval_seq);

	/**
	 * <pre>
	 * 1. 개요     : 기화기기안서를 수정 한다.
	 * 2. 처리내용 : 기화기기안서를 수정 한다.
	 * </pre>
	 * @Method Name : updateVaporize
	 * @param paramAmVO
	 * @param paramVrVO
	 */
	void updateVaporize(VaporizeVO paramVrVO);

}
