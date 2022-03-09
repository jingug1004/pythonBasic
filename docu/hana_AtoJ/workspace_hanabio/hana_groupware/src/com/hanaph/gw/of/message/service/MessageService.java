/**
  * Hana Project
  * Copyright 2014 iRush Co.,
  *
  */
package com.hanaph.gw.of.message.service;

import java.util.List;
import java.util.Map;

import com.hanaph.gw.of.common.vo.CommonTargetVO;
import com.hanaph.gw.of.message.vo.MessageVO;
import com.hanaph.gw.pe.member.vo.MemberVO;

/**
 * <pre>
 * Class Name : MessageService.java
 * 설명 : 쪽지 보내기 Service
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
public interface MessageService {
	
	/**
	 * <pre>
	 * 1. 개요     : 쪽지 보내기
	 * 2. 처리내용 : 쪽지를 보낸다.
	 * </pre>
	 * @Method Name : insertMessage
	 * @param messageVO
	 * @return
	 */
	String insertMessage(MessageVO messageVO);
	
	/**
	 * <pre>
	 * 1. 개요     : 쪽지 seq 
	 * 2. 처리내용 : 마지막 쪽지 seq를 가져온다.
	 * </pre>
	 * @Method Name : getMessageSeq
	 * @return
	 */
	int getMessageSeq();
	
	/**
	 * <pre>
	 * 1. 개요     : 보낸 쪽지
	 * 2. 처리내용 : 보낸 쪽지 리스트를 가져온다.
	 * </pre>
	 * @Method Name : getSendMessageList
	 * @param paramMap
	 * @return
	 */
	List<MessageVO> getMessageList(Map<String, String> paramMap);
	
	/**
	 * <pre>
	 * 1. 개요     : 받은 쪽지
	 * 2. 처리내용 : 받은 쪽지 리스트를 가져온다.
	 * </pre>
	 * @Method Name : getReceiveMessageList
	 * @param paramMap
	 * @return
	 */
	int getMessageReceiveCount(Map<String, String> paramMap);

	/**
	 * <pre>
	 * 1. 개요     : 쪽지 상세 정보 
	 * 2. 처리내용 : 쪽지 상세 정보를 가져온다.
	 * </pre>
	 * @Method Name : getMessageDetail
	 * @param messageVO
	 * @return
	 */
	MessageVO getMessageDetail(Map<String, String> paramMap);

	/**
	 * <pre>
	 * 1. 개요     : 보낸 쪽지 직원
	 * 2. 처리내용 : 보낸 쪽지 직원 리스트를 가져온다.
	 * </pre>
	 * @Method Name : getReceiveEmpNO
	 * @param messageVO
	 * @return
	 */
	List<MemberVO> getReceiveEmpNO(Map<String, String> paramMap);
	
	/**
	 * <pre>
	 * 1. 개요     : 보낸 쪽지 작성자
	 * 2. 처리내용 : 보낸 쪽지 작성자 정보를 가져온다.
	 * </pre>
	 * @Method Name : getReplyEmpNO
	 * @param messageVO
	 * @return
	 */
	List<MemberVO> getReplyEmpNO(Map<String, String> paramMap);
	
	/**
	 * <pre>
	 * 1. 개요     : 쪽지 삭제
	 * 2. 처리내용 : 쪽지 삭제를 한다.
	 * </pre>
	 * @Method Name : deleteMessage
	 * @param messageVO
	 * @return
	 */
	boolean deleteMessage(MessageVO messageVO);

	/**
	 * <pre>
	 * 1. 개요     : 쪽지 열람 여부
	 * 2. 처리내용 : 쪽지 열람시에 열람 여부 체크를 한다.
	 * </pre>
	 * @Method Name : updateMessageReadYn
	 * @param paramMessageVO
	 */
	void updateMessageReadYn(MessageVO paramMessageVO);

	/**
	 * <pre>
	 * 1. 개요     : 쪽지 삭제
	 * 2. 처리내용 : 쪽지를 db에서 데이터를 삭제 한다.
	 * </pre>
	 * @Method Name : deleteMessageDB
	 * @param messageVO
	 * @return
	 */
	boolean deleteMessageDB(MessageVO messageVO);

	/**
	 * <pre>
	 * 1. 개요     : 메세지 확인 여부
	 * 2. 처리내용 : 메세지 확인 상세 데이터를 불러온다.
	 * </pre>
	 * @Method Name : getMessageReadDataList
	 * @param paramMap
	 * @return
	 */
	List<CommonTargetVO> getMessageReadDataList(Map<String, String> paramMap);

	/**
	 * <pre>
	 * 1. 개요     : 쪽지 전달
	 * 2. 처리내용 : 쪽지 대상자를 추가한다.
	 * </pre>
	 * @Method Name : insertMessageTarget
	 * @param messageVO
	 */
	void insertMessageTargetToss(MessageVO messageVO);

	/**
	 * <pre>
	 * 1. 개요     : 발신인 정보 가져오기
	 * 2. 처리내용 : 발신인 정보를 가져온다.
	 * </pre>
	 * @Method Name : getMessageSender
	 * @param create_no
	 * @return
	 */		
	MemberVO getMessageSender(Map<String, String> paramMap);
	
}
