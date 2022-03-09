/**
  * Hana Project
  * Copyright 2014 iRush Co.,
  *
  */
package com.hanaph.gw.ea.newReport.dao;

import com.hanaph.gw.ea.newReport.vo.AccidentVO;


/**
 * <pre>
 * Class Name : NewReportE01011DAO.java
 * 설명 : 사고보고서
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
public interface NewReportE01011DAO {

	/**
	 * <pre>
	 * 1. 개요     : 사고보고서 등록 한다.
	 * 2. 처리내용 : 사고보고서 등록 한다.
	 * </pre>
	 * @Method Name : insertAccident
	 * @param paramAmVO
	 * @param paramAdVO
	 * @return
	 */
	void insertAccident(AccidentVO paramAdVO);

	/**
	 * <pre>
	 * 1. 개요     : 사고보고서 상세정보를 가져온다. 
	 * 2. 처리내용 : 사고보고서 상세정보를 가져온다.
	 * </pre>
	 * @Method Name : accidentDetail
	 * @param approval_seq
	 * @return
	 */
	AccidentVO accidentDetail(String approval_seq);

	/**
	 * <pre>
	 * 1. 개요     : 사고보고서 수정을 한다.
	 * 2. 처리내용 : 사고보고서 수정을 한다.
	 * </pre>
	 * @Method Name : updateAccident
	 * @param paramAmVO
	 * @param paramAdVO
	 */
	void updateAccident(AccidentVO paramAdVO);

}
