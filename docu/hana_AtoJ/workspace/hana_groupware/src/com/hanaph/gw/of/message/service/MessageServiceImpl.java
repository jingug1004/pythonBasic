/**
  * Hana Project
  * Copyright 2014 iRush Co.,
  *
  */
package com.hanaph.gw.of.message.service;

import com.hanaph.gw.co.common.utils.Environment;
import com.hanaph.gw.co.fileAttach.service.FileAttachService;
import com.hanaph.gw.co.fileAttach.vo.FileAttachVO;
import com.hanaph.gw.of.common.vo.CommonTargetVO;
import com.hanaph.gw.of.message.dao.MessageDAO;
import com.hanaph.gw.of.message.vo.MessageVO;
import com.hanaph.gw.pe.member.vo.MemberVO;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <pre>
 * Class Name : MessageServiceImpl.java
 * 설명 : 쪽지보내기 ServiceImpl
 * 
 * Modification Information
 *    수정일      수정자              수정 내용
 *  ------------ -------------- --------------------------------
 *  2014. 11. 27.      Jung.Jin.Muk          
 * </pre>
 * 
 * @version : 
 * @author  : Jung.Jin.Muk(pc123pc@irush.co.kr)
 * @since   : 2014. 11. 27.
 */
@Service(value="MessageService")
public class MessageServiceImpl implements MessageService {
	
	@Autowired
	private MessageDAO messageDAO;
	
	@Autowired
	SqlSessionFactory sqlSessionFactory;
	
	@Autowired
	private FileAttachService fileAttachService;
	
	private static final Logger logger = Logger.getLogger(MessageServiceImpl.class);
	private Environment env = new Environment();
	
	/* (non-Javadoc)
	 * @see com.hanaph.gw.of.message.service.MessageService#insertMessage(com.hanaph.gw.of.message.vo.MessageVO)
	 */
	public String insertMessage(MessageVO messageVO) {
		return messageDAO.insertMessage(messageVO);
	}
	
	/* (non-Javadoc)
	 * @see com.hanaph.gw.of.message.service.MessageService#getMessageSeq()
	 */
	public int getMessageSeq() {
		return messageDAO.getMessageSeq();
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.of.message.service.MessageService#getMessageList(java.util.Map)
	 */
	public List<MessageVO> getMessageList(Map<String, String> paramMap) {
		
		if("1".equals(paramMap.get("type"))){	//1.받은쪽지 2.보낸쪽지
			return messageDAO.getReceiveMessageList(paramMap);
		}else{
			return messageDAO.getSendMessageList(paramMap);
		}
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.of.message.service.MessageService#getMessageReceiveCount(java.util.Map)
	 */
	public int getMessageReceiveCount(Map<String, String> paramMap) {
		return messageDAO.getMessageReceiveCount(paramMap);
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.of.message.service.MessageService#getMessageDetail(java.util.Map)
	 */
	public MessageVO getMessageDetail(Map<String, String> paramMap) {
		return messageDAO.getMessageDetail(paramMap);
	}
	
	/* (non-Javadoc)
	 * @see com.hanaph.gw.of.message.service.MessageService#getReceiveEmpNO(java.util.Map)
	 */
	public List<MemberVO> getReceiveEmpNO(Map<String, String> paramMap) {
		return messageDAO.getReceiveEmpNO(paramMap);

	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.of.message.service.MessageService#getReplyEmpNO(java.util.Map)
	 */
	public List<MemberVO> getReplyEmpNO(Map<String, String> paramMap) {
		return messageDAO.getReplyEmpNO(paramMap);

	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.of.message.service.MessageService#deleteMessage(com.hanaph.gw.of.message.vo.MessageVO)
	 */
	@Transactional
	public boolean deleteMessage(MessageVO messageVO) {
		boolean result = false;
		
		/*첨부파일 삭제처리 파라미터 셋팅*/
		FileAttachVO fileAttachVO = new FileAttachVO();
		fileAttachVO.setCd("O03000"); //쪽지함 코드
		fileAttachVO.setSeq(messageVO.getMessage_seq()); //쪽지 시퀀스
		fileAttachVO.setDelete_yn("Y"); //첨부파일 삭제여부
		
		if(messageVO.getMessage_seq().indexOf("|") > -1){
			String message_seq = messageVO.getMessage_seq();
			String[] message_seqs = message_seq.split("\\|");
			
			for(int i= 0; i<message_seqs.length; i++){
				messageVO.setMessage_seq(message_seqs[i]);
				if("2".equals(messageVO.getType())){	
					fileAttachVO.setSeq(message_seqs[i]); //쪽지 시퀀스
					fileAttachService.deleteAttachAll(fileAttachVO);
					/*보낸쪽지 삭제*/
					result = messageDAO.deleteMessage(messageVO);
				}else{
					/*받은쪽지 삭제*/
					result = messageDAO.deleteMessageTarget(messageVO);
				}
			}
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.of.message.service.MessageService#updateMessageReadYn(com.hanaph.gw.of.message.vo.MessageVO)
	 */
	public void updateMessageReadYn(MessageVO paramMessageVO) {
		messageDAO.updateMessageReadYn(paramMessageVO);
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.of.message.service.MessageService#deleteMessageDB(com.hanaph.gw.of.message.vo.MessageVO)
	 */
	public boolean deleteMessageDB(MessageVO messageVO) {
		boolean result = false;
		String s = "";
		List<FileAttachVO> attachList = null;
		Map<String, String> paramMap = new HashMap<String, String>(); //파일첨부 파라미터
		paramMap.put("cd","O03000");
		
		if(messageVO.getMessage_seq().indexOf("|") > -1){
			String message_seq = messageVO.getMessage_seq();
			String[] message_seqs = message_seq.split("\\|");
			
			for(int i= 0; i<message_seqs.length; i++){
				messageVO.setMessage_seq(message_seqs[i]);
				messageDAO.deleteMessageTargetDB(messageVO);
				
				paramMap.put("seq",message_seqs[i]);
				attachList = fileAttachService.getAttachList(paramMap);
				
				if(attachList != null){
					for (int j = 0; j < attachList.size(); j++) {
						FileAttachVO fileAttachvo = new FileAttachVO();
						fileAttachvo = attachList.get(j);
						s = env.getValue("file_dir.url") + "/" + fileAttachvo.getFile_path() +  "/" + fileAttachvo.getFile_nm();
						File f = new File(s);
						f.delete();
					}
				}
				
				result = messageDAO.deleteMessageDB(messageVO);
			}
		}else{
			messageDAO.deleteMessageTargetDB(messageVO);
			paramMap.put("seq", messageVO.getMessage_seq());
			attachList = fileAttachService.getAttachList(paramMap);
			
			if(attachList != null){
				for (int j = 0; j < attachList.size(); j++) {
					FileAttachVO fileAttachvo = new FileAttachVO();
					fileAttachvo = attachList.get(j);
					s = env.getValue("file_dir.url") + "/" + fileAttachvo.getFile_path() +  "/" + fileAttachvo.getFile_nm();
					File f = new File(s);
					f.delete();
				}
			}
			
			result = messageDAO.deleteMessageDB(messageVO);
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.of.message.service.MessageService#getMessageReadDataList(java.util.Map)
	 */
	public List<CommonTargetVO> getMessageReadDataList(Map<String, String> paramMap) {
		return messageDAO.getMessageReadDataList(paramMap);
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.of.message.service.MessageService#insertMessageTarget(com.hanaph.gw.of.message.vo.MessageVO)
	 */
	public void insertMessageTargetToss(MessageVO messageVO) {
		messageDAO.insertMessageTargetToss(messageVO);
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.of.message.service.MessageService#insertMessageTarget(com.hanaph.gw.of.message.vo.MessageVO)
	 * ml180122.ml19_T58 김진국 - messageServiceImpl.java에 insertMessageTarget 구현 메서드 추가 - 시행부서 의견 필수 시 쪽지 보낼 리스트 사원 추가
	 */
	public void insertMessageTargetMustOpinion(MessageVO messageVO) {
		messageDAO.insertMessageTargetMustOpinion(messageVO);
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.of.message.service.MessageService#getMessageSender(java.lang.String)
	 */
	@Override
	public MemberVO getMessageSender(Map<String, String> paramMap) {
		return messageDAO.getMessageSender(paramMap);
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.of.message.service.MessageService#getMessageSender(java.lang.String)
	 * ml180122.ml14_T53 김진국 - messageServiceImpl.java에 messageService.insertMessageMustOpinion(messageVO) 구현 메서드 추가 - 시행부서 의견 필수 시 쪽지 추가
	 */
	@Override
	public void insertMessageMustOpinion(MessageVO messageVO) {
		messageDAO.insertMessageMustOpinion(messageVO);
	}

}
