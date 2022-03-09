/**
  * Hana Project
  * Copyright 2014 iRush Co.,
  *
  */
package com.hanaph.gw.ea.newReport.service;

import java.util.List;
import java.util.Map;

import com.hanaph.gw.co.fileAttach.vo.FileAttachVO;
import com.hanaph.gw.ea.newReport.vo.AccidentVO;
import com.hanaph.gw.ea.newReport.vo.ApprovalMasterVO;
import com.hanaph.gw.ea.newReport.vo.CommuteVO;
import com.hanaph.gw.ea.newReport.vo.DraftVO;
import com.hanaph.gw.ea.newReport.vo.ImplDeptEmpVO;
import com.hanaph.gw.ea.newReport.vo.IncompanyVO;
import com.hanaph.gw.ea.newReport.vo.ParticipationVO;
import com.hanaph.gw.ea.newReport.vo.VacationVO;
import com.hanaph.gw.ea.newReport.vo.VaporizeVO;


/**
 * <pre>
 * Class Name : NewReportService.java
 * 설명 : 신규문서
 * 
 * Modification Information
 *    수정일      수정자              수정 내용
 *  ------------ -------------- --------------------------------
 *  2014. 12. 29.      Jung.Jin.Muk          
 * </pre>
 * 
 * @version : 
 * @author  : Jung.Jin.Muk(pc123pc@irush.co.kr)
 * @since   : 2014. 12. 29.
 */
public interface NewReportService {
	
	/**
	 * 
	 * <pre>
	 * 1. 개요     : 결재Master 수정한다.
	 * 2. 처리내용 : 스텝상태만 업데이트 한다.
	 * </pre>
	 * @Method Name : updateApprovalMasterStepState
	 * @param paramAmVO
	 * @return
	 */
	boolean updateApprovalMasterStepState(ApprovalMasterVO paramAmVO);
	
	/**
	 * 
	 * <pre>
	 * 1. 개요     : 결재Master 수정한다.
	 * 2. 처리내용 : 스텝상태, 상태 업데이트, 시행부서 직원 등록 한다.
	 * </pre>
	 * @Method Name : updateApprovalMasterComplete
	 * @param paramAmVO
	 * @param implDeptEmpVO
	 * @return
	 */
	boolean updateApprovalMasterComplete(ApprovalMasterVO paramAmVO, ImplDeptEmpVO implDeptEmpVO);
	
	/**
	 * 
	 * <pre>
	 * 1. 개요     : 결재Master 수정한다.
	 * 2. 처리내용 : 결재Master 수정한다.
	 * </pre>
	 * @Method Name : updateApprovalMaster
	 * @param paramAmVO
	 * @return
	 */
	boolean updateApprovalMaster(ApprovalMasterVO paramAmVO);
	
	/**
	 * 
	 * <pre>
	 * 1. 개요     : 결재Master 수정한다. 
	 * 2. 처리내용 : 상태 업데이트 한다.
	 * </pre>
	 * @Method Name : updateApprovalMasterState
	 * @param paramAmVO
	 * @return
	 */
	boolean updateApprovalMasterState(ApprovalMasterVO paramAmVO);
	
	/**
	 * 
	 * <pre>
	 * 1. 개요     : 결재Master 수정한다. 
	 * 2. 처리내용 : 반려사유 업데이트 한다.
	 * </pre>
	 * @Method Name : updateApprovalMasterRejection
	 * @param paramAmVO
	 * @return
	 */
	boolean updateApprovalMasterRejection(ApprovalMasterVO paramAmVO);
	
	/**
	 * 
	 * <pre>
	 * 1. 개요     : 결재Master 수정한다. 
	 * 2. 처리내용 : 삭제상태 업데이트 한다.
	 * </pre>
	 * @Method Name : updateApprovalMasterDelete
	 * @param paramAmVO
	 * @return
	 */
	boolean updateApprovalMasterDelete(ApprovalMasterVO paramAmVO);
	
	/**
	 * <pre>
	 * 1. 개요     : 신규문서작성 상세정보
	 * 2. 처리내용 : 신규문서작성 상세정보
	 * </pre>
	 * @Method Name : ApprovalDetail
	 * @param approval_seq
	 * @return
	 */
	ApprovalMasterVO approvalDetail(String approval_seq);

	/**
	 * <pre>
	 * 1. 개요     : 전자결재 파일첨부
	 * 2. 처리내용 : 전자결재 파일첨부
	 * </pre>
	 * @Method Name : insertFileAttach
	 * @param uploadvo
	 * @return
	 */
	int insertFileAttach(FileAttachVO uploadvo);
	
	/**
	 * <pre>
	 * 1. 개요     : 전자결재 첨부파일 수정
	 * 2. 처리내용 : 전자결재 첨부파일 수정
	 * </pre>
	 * @Method Name : updateFileAttach
	 * @param paramMap
	 */
	public void updateFileAttach(Map<String, String> paramMap);

	/**
	 * <pre>
	 * 1. 개요     : 전자결재 첨부파일 리스트
	 * 2. 처리내용 : 전자결재 첨부파일 리스트
	 * </pre>
	 * @Method Name : getAttachList
	 * @param paramMap
	 * @return
	 */
	public List<FileAttachVO> getAttachList(Map<String, String> paramMap);

	/**
	 * <pre>
	 * 1. 개요     : 전자결재 첨부파일 삭제
	 * 2. 처리내용 : 전자결재 첨부파일 삭제
	 * </pre>
	 * @Method Name : deleteAttach
	 * @param fileParamVO
	 * @return
	 */
	public void deleteAttach(String delFileSeq);

	/**
	 * <pre>
	 * 1. 개요     : 원래파일명을 가져온다.
	 * 2. 처리내용 : 원래파일명을 가져온다.
	 * </pre>
	 * @Method Name : getOriginFileNm
	 * @param file_seq
	 * @return
	 */
	String getOriginFileNm(String file_seq);

}
