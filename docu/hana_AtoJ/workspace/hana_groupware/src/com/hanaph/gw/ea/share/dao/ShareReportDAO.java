/**
  * Hana Project
  * Copyright 2014 iRush Co.,
  *
  */
package com.hanaph.gw.ea.share.dao;

import java.util.List;
import java.util.Map;

import com.hanaph.gw.ea.share.vo.ShareReportVO;
import com.hanaph.gw.pe.member.vo.MemberVO;

/**
 * <pre>
 * Class Name : ShareReportDAO.java
 * 설명 : 공유문서 조회 DAO
 * 
 * Modification Information
 *    수정일      수정자              수정 내용
 *  ------------ -------------- --------------------------------
 *  2015. 1. 21.      CHOIILJI         
 * </pre>
 * 
 * @version : 1.0
 * @author  : CHOIILJI(choiilji@irush.co.kr)
 * @since   : 2015. 1. 21.
 */
public interface ShareReportDAO {

	/**
	 * 
	 * <pre>
	 * 1. 개요     : 공유문서 리스트
	 * 2. 처리내용 : 공유문서 리스트 가져온다.
	 * </pre>
	 * @Method Name : getSharetList
	 * @param paramMap
	 * @return
	 */
	public List<ShareReportVO> getShareList(Map<String, String> paramMap);
	
	/**
	 * 
	 * <pre>
	 * 1. 개요     : 공유문서 리스트 전체 카운트
	 * 2. 처리내용 : 공유문서 리스트 전체 카운트 가져온다.
	 * </pre>
	 * @Method Name : getShareCount
	 * @param paramMap
	 * @return
	 */
	public int getShareCount (Map<String, String> paramMap);
	
	/**
	 * 
	 * <pre>
	 * 1. 개요     : 공유문서 대상자 목록 리스트
	 * 2. 처리내용 : 공유문서 대상자 목록 리스트 가져온다.
	 * </pre>
	 * @Method Name : getShareTargetList
	 * @param paramMap
	 * @return
	 */
	public List<MemberVO> getShareTargetList(Map<String, String> paramMap);
	
	/**
	 * 
	 * <pre>
	 * 1. 개요     : 공유문서 대상자 저장한다.
	 * 2. 처리내용 : 공유문서 대상자 저장한다.
	 * </pre>
	 * @Method Name : insertShareTarget
	 * @param shareVO
	 * @return
	 */
	public int insertShareTarget(ShareReportVO shareVO);
	
	/**
	 * 
	 * <pre>
	 * 1. 개요     : 공유문서 대상자 삭제한다.
	 * 2. 처리내용 : 공유문서 대상자 삭제한다.
	 * </pre>
	 * @Method Name : deleteShareTarget
	 * @param paramMap
	 * @return
	 */
	public boolean deleteShareTarget(Map<String, String> paramMap);
	
	
	/**
	 * 
	 * <pre>
	 * 1. 개요     : 문서별 공유문서 타켓 상제정보 가져온다.
	 * 2. 처리내용 : 문서별 공유문서 타켓 상제정보 가져온다.
	 * </pre>
	 * @Method Name : getShareTargetDetail
	 * @param paramMap
	 * @return
	 */
	public ShareReportVO getShareTargetDetail(Map<String, String> paramMap);
	
	/**
	 * 
	 * <pre>
	 * 1. 개요     : 공유문서 타켓 열람여부 업데이트 
	 * 2. 처리내용 : 공유문서 타켓 열람여부 업데이트를 한다.
	 * </pre>
	 * @Method Name : updateShareTargetReadYn
	 * @param shareVO
	 * @return
	 */
	public boolean updateShareTargetReadYn(ShareReportVO shareReportVO);
}
