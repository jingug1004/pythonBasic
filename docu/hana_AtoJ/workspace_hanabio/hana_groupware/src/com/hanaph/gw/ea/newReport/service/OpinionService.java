/**
  * Hana Project
  * Copyright 2014 iRush Co.,
  *
  */
package com.hanaph.gw.ea.newReport.service;

import java.util.List;
import java.util.Map;

import com.hanaph.gw.co.fileAttach.vo.FileAttachVO;
import com.hanaph.gw.ea.newReport.vo.OpinionVO;

/**
 * <pre>
 * Class Name : OpinionService.java
 * 설명 : 의견 Service
 * 
 * Modification Information
 *    수정일      수정자              수정 내용
 *  ------------ -------------- --------------------------------
 *  2015. 1. 20.      CHOIILJI         
 * </pre>
 * 
 * @version : 1.0
 * @author  : CHOIILJI(choiilji@irush.co.kr)
 * @since   : 2015. 1. 20.
 */
public interface OpinionService {

	/**
	 * 
	 * <pre>
	 * 1. 개요     : 시행부서 의견 리스트
	 * 2. 처리내용 : 시행부서 의견 리스트 가져온다.
	 * </pre>
	 * @Method Name : getOpinionList
	 * @param paramMap
	 * @return
	 */
	public List<OpinionVO> getOpinionList(Map<String, String> paramMap);
	
	/**
	 * 
	 * <pre>
	 * 1. 개요     : 시행부서 의견 저장
	 * 2. 처리내용 : 시행부서 의견 저장
	 * </pre>
	 * @Method Name : insertOpinion
	 * @param opinionVO
	 * @return
	 */
	public int insertOpinion(OpinionVO opinionVO);
	
	/**
	 * 
	 * <pre>
	 * 1. 개요     : 시행부서 개인의견 삭제
	 * 2. 처리내용 : 시행부서 개인의견 삭제
	 * </pre>
	 * @Method Name : deleteOpinion
	 * @param paramMap
	 * @return
	 */
	public int deleteOpinion(Map<String, String> paramMap);
	
	/**
	 * 
	 * <pre>
	 * 1. 개요     : 시행부서 문서별 전체의견 삭제
	 * 2. 처리내용 : 시행부서 문서별 전체의견 삭제
	 * </pre>
	 * @Method Name : deleteOpinion
	 * @param paramMap
	 * @return
	 */
	public int deleteOpinionAll(Map<String, String> paramMap);
	
	
	
	/**
	 * <pre>
	 * 1. 개요     : 의견 파일첨부
	 * 2. 처리내용 : 의견 파일첨부
	 * </pre>
	 * @Method Name : insertFileOpinion
	 * @param uploadvo
	 * @return
	 */
	int insertFileOpinion(FileAttachVO uploadvo);
	
	/**
	 * <pre>
	 * 1. 개요     : 의견 첨부파일 수정
	 * 2. 처리내용 : 의견 첨부파일 수정
	 * </pre>
	 * @Method Name : updateFileOpinion
	 * @param paramMap
	 */
	public void updateFileOpinion(Map<String, String> paramMap);

	/**
	 * <pre>
	 * 1. 개요     : 의견 첨부파일 리스트
	 * 2. 처리내용 : 의견 첨부파일 리스트
	 * </pre>
	 * @Method Name : getFileOpinionList
	 * @param paramMap
	 * @return
	 */
	public List<FileAttachVO> getFileOpinionList(Map<String, String> paramMap);

	/**
	 * <pre>
	 * 1. 개요     : 의견 첨부파일 삭제
	 * 2. 처리내용 : 의견 첨부파일 삭제
	 * </pre>
	 * @Method Name : deleteFileOpinion
	 * @param fileParamVO
	 * @return
	 */
	public void deleteFileOpinion(String delFileSeq);

	/**
	 * <pre>
	 * 1. 개요     : 원래파일명을 가져온다.
	 * 2. 처리내용 : 원래파일명을 가져온다.
	 * </pre>
	 * @Method Name : getOriginFileOpinionNm
	 * @param file_seq
	 * @return
	 */
	String getOriginFileOpinionNm(String file_seq);
}
