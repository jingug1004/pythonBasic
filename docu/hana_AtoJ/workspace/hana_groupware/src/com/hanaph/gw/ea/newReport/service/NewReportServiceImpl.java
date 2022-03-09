/**
  * Hana Project
  * Copyright 2014 iRush Co.,
  *
  */
package com.hanaph.gw.ea.newReport.service;

import com.hanaph.gw.co.fileAttach.vo.FileAttachVO;
import com.hanaph.gw.ea.newReport.dao.ImplDeptEmpDAO;
import com.hanaph.gw.ea.newReport.dao.NewReportDAO;
import com.hanaph.gw.ea.newReport.dao.OpinionDAO;
import com.hanaph.gw.ea.newReport.vo.ApprovalMasterVO;
import com.hanaph.gw.ea.newReport.vo.ImplDeptEmpVO;
import com.hanaph.gw.ea.share.dao.ShareReportDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

	// ml180116.ml09_T11 김진국 - NewReportServiceImpl.java에 addMustOpinion 인터페이스 구현 - APPROVAL_SEQ(문서번호)를 통하여 GW_EA_APPROVAL_MASTER 테이블의 MUSTOPINION 칼럼을 'Y'로 변경시키기 위해서
	@Override
	public void addMustOpinion(String approval_seq) throws Exception {
		newReportDAO.addMustOpinion(approval_seq);
	}

	// ml180118.ml06_T28 김진국 - NewReportServiceImpl.java에 addMustOpinionApproval 구현 메서드 추가 - '시의필' 버튼 누르면 결재 마스터(GW_EA_APPROVAL_MASTER) 뿐만 아니라 결재 테이블(GW_EA_APPROVAL)도 변경하기 위해서
	@Override
	public void addMustOpinionApproval(String approval_seq) throws Exception {
		newReportDAO.addMustOpinionApproval(approval_seq);
	}

	// ml180122.ml11_T50 김진국 - newReportServiceImpl.java에 getApprovalSubject 구현 메서드 추가 - 쪽지에 결재 제목을 가져오기 위해서
	@Override
	public String getApprovalSubject(String approval_seq) throws Exception {
		return newReportDAO.getApprovalSubject(approval_seq);
	}

	/* (non-Javadoc)
 	 * @see com.hanaph.gw.ea.newReport.service.NewReportService#getAttachList(java.util.Map)
 	 * ml180124.ml06_T75 김진국 - newReportServiceImpl.java getApprovalerMustOpinion 추가 - 시의필 실행시 결재 작성자와 결재자에게도 쪽지 보내기 위해서!
 	 */
	@Override
	public List<String> getApprovalerMustOpinion(String approval_seq) throws Exception {
		return newReportDAO.getApprovalerMustOpinion(approval_seq);
	}

	/* (non-Javadoc)
	 * ml180126.ml05_T85 김진국 - NewReportDAO, newReport.xml, NewReprtService, NewReportController에 isYMustOpinion 추가 - 결재 등록시 '시의필' 실행했다가 취소하려고
	 */
	@Override
	public String isYMustOpinion(String approval_seq) throws Exception  {
		return newReportDAO.isYMustOpinion(approval_seq);
	}

	/* (non-Javadoc)
	 * ml180126.ml06_T86 김진국 - NewReportDAO, newReport.xml, NewReprtService, NewReportController에 addMustOpinionYtoN 추가 - 결재 등록시 '시의필' 실행했다가 취소면 update로 MustOpinion N으로 바꿈
	 */
	@Override
	public void addMustOpinionYtoN(String approval_seq)throws Exception  {
		newReportDAO.addMustOpinionYtoN(approval_seq);
	}

}
