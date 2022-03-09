/**
  * Hana Project
  * Copyright 2014 iRush Co.,
  *
  */
package com.hanaph.gw.ea.newReport.dao;

import com.hanaph.gw.ea.newReport.vo.IncompanyVO;


/**
 * <pre>
 * Class Name : NewReportE01003DAO.java
 * 설명 : 사내통신
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
public interface NewReportE01003DAO {

	/**
	 * <pre>
	 * 1. 개요     : 사내통신을 등록 한다.
	 * 2. 처리내용 : 사내통신을 등록 한다.
	 * </pre>
	 * @Method Name : insertIncompany
	 * @param paramAmVO
	 * @param paramIcVO
	 * @return
	 */
	void insertIncompany(IncompanyVO paramIcVO);

	/**
	 * <pre>
	 * 1. 개요     : 사내통신 상세정보를 가져온다.
	 * 2. 처리내용 : 사내통신 상세정보를 가져온다.
	 * </pre>
	 * @Method Name : incompanyDetail
	 * @param approval_seq
	 * @return
	 */
	IncompanyVO incompanyDetail(String approval_seq);

	/**
	 * <pre>
	 * 1. 개요     : 사내통신 수정을 한다.
	 * 2. 처리내용 : 사내통신 수정을 한다.
	 * </pre>
	 * @Method Name : updateIncompany
	 * @param paramAmVO
	 * @param paramIcVO
	 */
	void updateIncompany(IncompanyVO paramIcVO);

}
