/**
  * Hana Project
  * Copyright 2014 iRush Co.,
  *
  */
package com.hanaph.gw.ea.receive.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hanaph.gw.ea.newReport.dao.ImplDeptEmpDAO;
import com.hanaph.gw.ea.newReport.dao.NewReportDAO;
import com.hanaph.gw.ea.newReport.vo.ApprovalMasterVO;
import com.hanaph.gw.ea.newReport.vo.ApprovalVO;
import com.hanaph.gw.ea.newReport.vo.ImplDeptEmpVO;
import com.hanaph.gw.ea.receive.dao.ReceiveDAO;
import com.hanaph.gw.ea.receive.vo.ReceiveVO;
import com.hanaph.gw.pe.member.service.AnnualService;
import com.hanaph.gw.pe.member.vo.AnnualVO;

/**
 * <pre>
 * Class Name : ReceiveServiceImpl.java
 * 설명 : 
 * 
 * Modification Information
 *    수정일      수정자              수정 내용
 *  ------------ -------------- --------------------------------
 *  2015. 1. 20.      Jung.Jin.Muk          
 * </pre>
 * 
 * @version : 
 * @author  : Jung.Jin.Muk(pc123pc@irush.co.kr)
 * @since   : 2015. 1. 20.
 */
@Service(value="ReceiveService")
public class ReceiveServiceImpl implements ReceiveService {
	
	@Autowired
	private ReceiveDAO receiveDAO;
	
	@Autowired
	private NewReportDAO newReportDAO;
	
	@Autowired
	private ImplDeptEmpDAO implDeptEmpDao;
	
	@Autowired
	private AnnualService annualService;
	
	/* (non-Javadoc)
	 * @see com.hanaph.gw.ea.receive.service.ReceiveService#getReceiveCount(java.util.Map)
	 */
	@Override
	public int getReceiveCount(Map<String, String> paramMap) {
		return receiveDAO.getReceiveCount(paramMap);
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.ea.receive.service.ReceiveService#getReceiveList(java.util.Map)
	 */
	@Override
	public List<ReceiveVO> getReceiveList(Map<String, String> paramMap) {
		return receiveDAO.getReceiveList(paramMap);
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.ea.receive.service.ReceiveService#updateApproval(java.util.Map)
	 */
	@Override
	@Transactional
	public void updateApproval(ApprovalMasterVO approvalMasterVO, ReceiveVO receiveVO, ImplDeptEmpVO implDeptEmpVO, AnnualVO annualVO) {
		if(approvalMasterVO.getApproval_seq().indexOf("|") > -1){
			String approval_seq = approvalMasterVO.getApproval_seq();
			String[] approval_seqs = approval_seq.split("\\|");
			
			for(int i= 0; i<approval_seqs.length; i++){
				approvalMasterVO.setApproval_seq(approval_seqs[i]);
				receiveVO.setApproval_seq(approval_seqs[i]);
				/*일괄결재*/
				if("8".equals(receiveVO.getGubun())){
					ReceiveVO appOdrVO = new ReceiveVO(); 
					appOdrVO = receiveDAO.getApprovalOrdering(receiveVO);
					/*마지막결재자 체크*/
					if(appOdrVO.getOrdering()==appOdrVO.getMaxorder()){
						approvalMasterVO.setState("E02004");
						/*결재 완료시에 휴가신청서 연차를 저장 한다*/
						if("E01001".equals(approvalMasterVO.getDocu_cd())){
							annualService.insertAnnual(annualVO);
						}
					}else{
						approvalMasterVO.setState("E02003");
					}
				}
				newReportDAO.updateApprovalMasterState(approvalMasterVO);
				receiveDAO.updateApproval(receiveVO);
			}
		}else{
			/*승인*/
			if("1".equals(receiveVO.getGubun())){
				ReceiveVO appOdrVO = new ReceiveVO(); 
				appOdrVO = receiveDAO.getApprovalOrdering(receiveVO);
				
				/*마지막결재자 체크*/
				if(appOdrVO.getOrdering()==appOdrVO.getMaxorder()){
					approvalMasterVO.setState("E02004");
					
					/*결재 완료시에 휴가신청서 연차를 저장 한다*/
					if("E01001".equals(approvalMasterVO.getDocu_cd())){
						annualService.insertAnnual(annualVO);
					}
				}
			/*전결*/
			}else if("3".equals(receiveVO.getGubun())){
				
				/*결재 완료시에 휴가신청서 연차를 저장 한다*/
				if("E01001".equals(approvalMasterVO.getDocu_cd())){
					annualService.insertAnnual(annualVO);
				}
			/*반려취소, 승인취소, 전결취소*/	
			}else if("4".equals(receiveVO.getGubun()) || "5".equals(receiveVO.getGubun()) || "6".equals(receiveVO.getGubun())){
				receiveVO.setState("E03001");
				/*승인취소, 전결취소*/
				if("5".equals(receiveVO.getGubun()) || "6".equals(receiveVO.getGubun())){
					/*승인취소, 전결취소 시 시행완료 삭제한다.*/
					implDeptEmpDao.deleteImplDeptEmpComplete(implDeptEmpVO);
					
					/*승인취소, 전결취소 시 휴가신청서 연차를 삭제한다*/
					if("E01001".equals(approvalMasterVO.getDocu_cd())){
						annualService.deleteAnnual(annualVO);
					}
				}
			/*결재반려*/
			}else if("2".equals(receiveVO.getGubun())){
				newReportDAO.updateApprovalMasterRejection(approvalMasterVO);
			}
			newReportDAO.updateApprovalMasterState(approvalMasterVO);
			receiveDAO.updateApproval(receiveVO);
		}
		
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.ea.receive.service.ReceiveService#updateAllApproval(java.util.Map)
	 */
	@Override
	@Transactional
	public void updateAllApproval(ApprovalMasterVO approvalMasterVO, ReceiveVO receiveVO) {
		
		if(approvalMasterVO.getApproval_seq().indexOf("|") > -1){
			String approval_seq = approvalMasterVO.getApproval_seq();
			String[] approval_seqs = approval_seq.split("\\|");
			
			for(int i= 0; i<approval_seqs.length; i++){
				approvalMasterVO.setApproval_seq(approval_seqs[i]);
				approvalMasterVO.setState("E03002");	//기결로 상태 업뎃
				approvalMasterVO.setState("E02003");	//진행중으로 상태 업뎃
				newReportDAO.updateApprovalMasterState(approvalMasterVO);
			}
		}else{
			approvalMasterVO.setState("E02003");	//진행중으로 상태 업뎃
			newReportDAO.updateApprovalMasterState(approvalMasterVO);
		}
		
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.ea.receive.service.ReceiveService#getReceiveReadYnDetail(java.util.Map)
	 */
	@Override
	public ApprovalVO getReceiveReadYnDetail(Map<String, String> paramMap) {
		return receiveDAO.getReceiveReadYnDetail(paramMap);
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.ea.receive.service.ReceiveService#updateReceiveReadYn(com.hanaph.gw.ea.receive.vo.ReceiveVO)
	 */
	@Override
	public void updateReceiveReadYn(ApprovalVO approvalVO) {
		receiveDAO.updateReceiveReadYn(approvalVO);
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.ea.receive.service.ReceiveService#getMainReceiveCnt(java.util.Map)
	 */
	@Override
	public List<ApprovalMasterVO> getMainReceiveCnt(Map<String, String> paramMap) {
		return receiveDAO.getMainReceiveCnt(paramMap);
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.ea.receive.service.ReceiveService#getLongTermReceiveCount(java.util.Map)
	 */
	@Override
	public int getLongTermReceiveCount(Map<String, String> paramMap) {
		return receiveDAO.getLongTermReceiveCount(paramMap);
	}

}
