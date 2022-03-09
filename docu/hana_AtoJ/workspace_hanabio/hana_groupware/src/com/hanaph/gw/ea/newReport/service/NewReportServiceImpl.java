/**
  * Hana Project
  * Copyright 2014 iRush Co.,
  *
  */
package com.hanaph.gw.ea.newReport.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hanaph.gw.co.fileAttach.vo.FileAttachVO;
import com.hanaph.gw.ea.newReport.dao.ImplDeptEmpDAO;
import com.hanaph.gw.ea.newReport.dao.NewReportDAO;
import com.hanaph.gw.ea.newReport.dao.OpinionDAO;
import com.hanaph.gw.ea.newReport.vo.AccidentVO;
import com.hanaph.gw.ea.newReport.vo.ApprovalMasterVO;
import com.hanaph.gw.ea.newReport.vo.CommuteVO;
import com.hanaph.gw.ea.newReport.vo.DraftVO;
import com.hanaph.gw.ea.newReport.vo.ImplDeptEmpVO;
import com.hanaph.gw.ea.newReport.vo.IncompanyVO;
import com.hanaph.gw.ea.newReport.vo.ParticipationVO;
import com.hanaph.gw.ea.newReport.vo.VacationVO;
import com.hanaph.gw.ea.newReport.vo.VaporizeVO;
import com.hanaph.gw.ea.share.dao.ShareReportDAO;

/**
 * <pre>
 * Class Name : NewReportServiceImpl.java
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
@Service(value="newReportService")
public class NewReportServiceImpl implements NewReportService {
	
	@Autowired 
	private NewReportDAO newReportDAO;
	
	@Autowired
	private ImplDeptEmpDAO implDeptEmpDAO;

	@Autowired
	private OpinionDAO opinionDao;
	
	@Autowired
	private ShareReportDAO shareDao;
	
	/*
	 * (non-Javadoc)
	 * @see com.hanaph.gw.ea.newReport.service.NewReportService#updateApprovalMaster(com.hanaph.gw.ea.newReport.vo.ApprovalMasterVO)
	 */
	@Override
	public boolean updateApprovalMaster(ApprovalMasterVO paramAmVO) {
		return newReportDAO.updateApprovalMaster(paramAmVO);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.hanaph.gw.ea.newReport.service.NewReportService#updateApprovalMasterStepState(com.hanaph.gw.ea.newReport.vo.ApprovalMasterVO)
	 */
	@Override
	public boolean updateApprovalMasterStepState(ApprovalMasterVO paramAmVO) {
		return newReportDAO.updateApprovalMasterStepState(paramAmVO);
	}

	/*
	 * (non-Javadoc)
	 * @see com.hanaph.gw.ea.newReport.service.NewReportService#updateApprovalMasterStepAndState(com.hanaph.gw.ea.newReport.vo.ApprovalMasterVO)
	 */
	@Override
	@Transactional
	public boolean updateApprovalMasterComplete(ApprovalMasterVO paramAmVO, ImplDeptEmpVO implDeptEmpVO) {
		try{
			//결재 마스터 업데이트
			newReportDAO.updateApprovalMasterComplete(paramAmVO);
			//시행부서 임직원 인서트
			implDeptEmpDAO.insertImplDeptEmp(implDeptEmpVO);
			return true;
		}catch (Exception ex){
			ex.printStackTrace();
			return false;
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.hanaph.gw.ea.newReport.service.NewReportService#updateApprovalMasterState(com.hanaph.gw.ea.newReport.vo.ApprovalMasterVO)
	 */
	@Override
	@Transactional
	public boolean updateApprovalMasterState(ApprovalMasterVO paramAmVO) {
		boolean chkStatus = true;
		boolean bResult  = false;
		
		//요청취소 	
		if(paramAmVO.getProcess_state().equals("REQUEST")){
			ApprovalMasterVO approvalMasterVO = newReportDAO.approvalDetail(paramAmVO.getApproval_seq());
			if(!approvalMasterVO.getState().equals("E02002")){
				chkStatus = false;
			}else{
				Map<String, String> paramMap = new HashMap<String, String>();
				//시행부서 직원 삭제
				ImplDeptEmpVO implDeptEmpVO = new ImplDeptEmpVO();
				implDeptEmpVO.setApproval_seq(paramAmVO.getApproval_seq());
				implDeptEmpDAO.deleteImplDeptEmp(implDeptEmpVO);
				
				//의견 삭제
				paramMap.put("approval_seq", paramAmVO.getApproval_seq());
				opinionDao.deleteOpinionAll(paramMap);
				
				//공유문서 직원 삭제
				shareDao.deleteShareTarget(paramMap);
			}
		}
		
		if(chkStatus){
			bResult = newReportDAO.updateApprovalMasterState(paramAmVO);
		}
		return bResult;
	}

	/*
	 * (non-Javadoc)
	 * @see com.hanaph.gw.ea.newReport.service.NewReportService#updateApprovalMasterRejection(com.hanaph.gw.ea.newReport.vo.ApprovalMasterVO)
	 */
	@Override
	public boolean updateApprovalMasterRejection(ApprovalMasterVO paramAmVO) {
		return newReportDAO.updateApprovalMasterRejection(paramAmVO);
	}

	/*
	 * (non-Javadoc)
	 * @see com.hanaph.gw.ea.newReport.service.NewReportService#updateApprovalMasterDelete(com.hanaph.gw.ea.newReport.vo.ApprovalMasterVO)
	 */
	@Override
	public boolean updateApprovalMasterDelete(ApprovalMasterVO paramAmVO) {
		return newReportDAO.updateApprovalMasterDelete(paramAmVO);
	}
	
	/* (non-Javadoc)
	 * @see com.hanaph.gw.ea.newReport.service.NewReportService#approvalDetail(java.lang.String)
	 */
	@Override
	public ApprovalMasterVO approvalDetail(String approval_seq) {
		return newReportDAO.approvalDetail(approval_seq);
	}
	
	/* (non-Javadoc)
	 * @see com.hanaph.gw.ea.newReport.service.NewReportService#insertFileAttach(com.hanaph.gw.co.fileAttach.vo.FileAttachVO)
	 */
	@Override
	public int insertFileAttach(FileAttachVO uploadVO) {
		return newReportDAO.insertFileAttach(uploadVO);
	}
	
	/* (non-Javadoc)
	 * @see com.hanaph.gw.ea.newReport.service.NewReportService#updateFileAttach(java.util.Map)
	 */
	@Override
	public void updateFileAttach(Map<String, String> paramMap) {
		
		if(paramMap.get("fileNum").indexOf(",") > -1){
			String fileNum = paramMap.get("fileNum");
			String[] fileNums = fileNum.split("\\,");
			
			for(int i= 0; i<fileNums.length; i++){
				paramMap.put("file_seq",fileNums[i]);
				newReportDAO.updateFileAttach(paramMap);
			}
		}else{
			newReportDAO.updateFileAttach(paramMap);
		}
	}
	
	/* (non-Javadoc)
	 * @see com.hanaph.gw.ea.newReport.service.NewReportService#getAttachList(java.util.Map)
	 */
	@Override
	public List<FileAttachVO> getAttachList(Map<String, String> paramMap) {
		return newReportDAO.getAttachList(paramMap);
	}
	
	/* (non-Javadoc)
	 * @see com.hanaph.gw.ea.newReport.service.NewReportService#deleteAttach(java.lang.String)
	 */
	@Override
	public void deleteAttach(String delFileSeq) {
		if (!delFileSeq.equals("")) {
			String arrSeq[] = delFileSeq.split(",");
			for (int i = 0; i < arrSeq.length; i++) {
				FileAttachVO fileParamVO = new FileAttachVO();
				fileParamVO.setFile_seq(Integer.parseInt(arrSeq[i]));
				fileParamVO.setDelete_yn("Y");
				newReportDAO.deleteAttach(fileParamVO);
			}
		}
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.ea.newReport.service.NewReportService#getOriginFileNm(java.lang.String)
	 */
	@Override
	public String getOriginFileNm(String file_seq) {
		return newReportDAO.getOriginFileNm(file_seq);

	}
}
