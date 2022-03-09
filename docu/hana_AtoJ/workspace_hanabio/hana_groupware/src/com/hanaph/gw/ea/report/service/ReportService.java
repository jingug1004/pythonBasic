/**
  * Hana Project
  * Copyright 2014 iRush Co.,
  *
  */
package com.hanaph.gw.ea.report.service;

import java.util.List;
import java.util.Map;

import com.hanaph.gw.ea.newReport.vo.ApprovalMasterVO;
import com.hanaph.gw.ea.report.vo.ReportVO;

/**
 * <pre>
 * Class Name : ReportService.java
 * 설명 : 
 * 
 * Modification Information
 *    수정일      수정자              수정 내용
 *  ------------ -------------- --------------------------------
 *  2015. 1. 19.      Jung.Jin.Muk          
 * </pre>
 * 
 * @version : 
 * @author  : Jung.Jin.Muk(pc123pc@irush.co.kr)
 * @since   : 2015. 1. 19.
 */
public interface ReportService {

	/**
	 * <pre>
	 * 1. 개요     : 내가올린문서 카운트 
	 * 2. 처리내용 : 내가올린문서 총 갯수를 가져온다.
	 * </pre>
	 * @Method Name : getReportCount
	 * @param paramMap
	 * @return
	 */
	public int getReportCount(Map<String, String> paramMap);

	/**
	 * <pre>
	 * 1. 개요     : 내가올린문서 리스트
	 * 2. 처리내용 : 내가올린문서 리스트를 가져온다.
	 * </pre>
	 * @Method Name : getReportList
	 * @param paramMap
	 * @return
	 */
	public List<ReportVO> getReportList(Map<String, String> paramMap);

	/**
	 * <pre>
	 * 1. 개요     : 내가올린문서 메인 카운트
	 * 2. 처리내용 : 내가올린문서 메인 카운트
	 * </pre>
	 * @Method Name : getMainReportCnt
	 * @param paramMap
	 * @return
	 */
	public List<ApprovalMasterVO> getMainReportCnt(Map<String, String> paramMap);

}
