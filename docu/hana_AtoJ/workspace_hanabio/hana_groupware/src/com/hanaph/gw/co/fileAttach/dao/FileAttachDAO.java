package com.hanaph.gw.co.fileAttach.dao;

import java.util.List;
import java.util.Map;

import com.hanaph.gw.co.code.vo.CodeVO;
import com.hanaph.gw.co.fileAttach.vo.FileAttachVO;
import com.hanaph.gw.of.board.vo.BoardVO;

/**
 * <pre>
 * Class Name : FileAttachDAO.java
 * 설명 : 첨부파일 DAO
 * 
 * Modification Information
 *    수정일      수정자              수정 내용
 *  ------------ -------------- --------------------------------
 *  2014. 10. 15.      우정아          
 * </pre>
 * 
 * @version : 
 * @author  : 우정아(@irush.co.kr)
 * @since   : 2014. 10. 21.
 */
public interface FileAttachDAO{
	
	/**
	 * <pre>
	 * 1. 개요     : 첨부파일 등록
	 * 2. 처리내용 : 첨부파일 등록
	 * </pre>
	 * @Method Name : insertFileAttach
	 * @param paramVO
	 * @return
	 */
	public int insertFileAttach(FileAttachVO paramVO);

	/**
	 * <pre>
	 * 1. 개요     : 첨부파일 수정
	 * 2. 처리내용 : 첨부파일 등록 후 seq파라미터값을 받아 업데이트 한다. 
	 * </pre>
	 * @Method Name : updateFileAttach
	 * @param paramMap
	 */
	public void updateFileAttach(Map<String, String> paramMap);
	
	/**
	 * <pre>
	 * 1. 개요     : 첨부파일 리스트
	 * 2. 처리내용 : 첨부파일 리스트
	 * </pre>
	 * @Method Name : getAttachList
	 * @param paramMap
	 * @return
	 */
	public List<FileAttachVO> getAttachList(Map<String, String> paramMap);

	/**
	 * <pre>
	 * 1. 개요     : 첨부파일 삭제
	 * 2. 처리내용 : 첨부파일 삭제
	 * </pre>
	 * @Method Name : deleteAttach
	 * @param fileParamVO
	 * @return
	 */
	public int deleteAttach(FileAttachVO fileParamVO);

	/**
	 * <pre>
	 * 1. 개요     : 원래파일명을 가져온다.
	 * 2. 처리내용 : 원래파일명을 가져온다.
	 * </pre>
	 * @Method Name : getOriginFileNm
	 * @param file_seq
	 * @return
	 */
	public String getOriginFileNm(String file_seq);

	/**
	 * <pre>
	 * 1. 개요     : 첨부파일 삭제
	 * 2. 처리내용 : 해당 하는 글에 대한 첨부파일을 삭제 한다.
	 * </pre>
	 * @Method Name : deleteAttachAll
	 * @param fileAttachVO
	 */
	public void deleteAttachAll(FileAttachVO fileAttachVO);
	
}
