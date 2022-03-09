/**
  * Hana Project
  * Copyright 2014 iRush Co.,
  *
  */
package com.hanaph.gw.of.message.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hanaph.gw.of.common.vo.CommonTargetVO;
import com.hanaph.gw.of.message.vo.MessageVO;
import com.hanaph.gw.pe.member.vo.MemberVO;

/**
 * <pre>
 * Class Name : MessageDAOImpl.java
 * 설명 : 쪽지 보내기 DAOImpl
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
@Repository("messageDAO")
public class MessageDAOImpl extends SqlSessionDaoSupport implements MessageDAO{
	
	@Autowired
	SqlSessionFactory sqlSessionFactory;
	
	/* (non-Javadoc)
	 * @see com.hanaph.gw.of.message.dao.MessageDAO#insertMessageTarget(com.hanaph.gw.of.message.vo.MessageVO)
	 */
	public boolean insertMessageTarget(MessageVO messageVO) {
		Integer count = getSqlSession().insert("message.insertMessageTarget", messageVO);
		if(count != 0){
			return true;
		}else{
			return false;
		}	
	}
	
	/* (non-Javadoc)
	 * @see com.hanaph.gw.of.message.dao.MessageDAO#insertMessage(com.hanaph.gw.of.message.vo.MessageVO)
	 */
	public String insertMessage(MessageVO messageVO) {
		
		String seq = "";
		
		SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH);
		sqlSession.commit(false);
		
		try{
			sqlSession.insert("message.insertMessage", messageVO);
			seq = messageVO.getMessage_seq();
			if(messageVO.getEmp_no().indexOf("|") > -1){
				String emp_no = messageVO.getEmp_no();
				String[] emp_nos = emp_no.split("\\|");
				
				for(int i= 0; i<emp_nos.length; i++){
					messageVO.setEmp_no(emp_nos[i]);
					sqlSession.insert("message.insertMessageTarget", messageVO);
				}
			}else{
				sqlSession.insert("message.insertMessageTarget", messageVO);
			}
			
			sqlSession.commit();
			
		}catch(RuntimeException e){
			seq = "";
			logger.debug("쪽지 전송 실패 :: " , e);
			sqlSession.rollback();
		}finally{
			sqlSession.close();	
		}
		return seq;
		
	}
	
	/* (non-Javadoc)
	 * @see com.hanaph.gw.of.message.dao.MessageDAO#getMessageSeq()
	 */
	public int getMessageSeq() {
		return (Integer) getSqlSession().selectOne("message.getMessageSeq");
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.of.message.dao.MessageDAO#getSendMessageList(java.util.Map)
	 */
	@SuppressWarnings("unchecked")
	public List<MessageVO> getSendMessageList(Map<String, String> paramMap) {
		return (List<MessageVO>)getSqlSession().selectList("message.getSendMessageList", paramMap);
	}
	
	/* (non-Javadoc)
	 * @see com.hanaph.gw.of.message.dao.MessageDAO#getReceiveMessageList(java.util.Map)
	 */
	@SuppressWarnings("unchecked")
	public List<MessageVO> getReceiveMessageList(Map<String, String> paramMap) {
		return (List<MessageVO>)getSqlSession().selectList("message.getReceiveMessageList", paramMap);
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.of.message.dao.MessageDAO#getMessageReceiveCount(java.util.Map)
	 */
	public int getMessageReceiveCount(Map<String, String> paramMap) {
		Integer count = (Integer)getSqlSession().selectOne("message.getMessageReceiveCount", paramMap);
		return count;
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.of.message.dao.MessageDAO#getMessageDetail(java.util.Map)
	 */
	public MessageVO getMessageDetail(Map<String, String> paramMap) {
		return (MessageVO)getSqlSession().selectOne("message.getMessageDetail", paramMap);
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.of.message.dao.MessageDAO#getReceiveEmpNO(java.util.Map)
	 */
	@SuppressWarnings("unchecked")
	public List<MemberVO> getReceiveEmpNO(Map<String, String> paramMap) {		
		return (List<MemberVO>)getSqlSession().selectList("message.getReceiveEmpNO", paramMap);
	}
	
	/* (non-Javadoc)
	 * @see com.hanaph.gw.of.message.dao.MessageDAO#getReplyEmpNO(java.util.Map)
	 */
	@SuppressWarnings("unchecked")
	public List<MemberVO> getReplyEmpNO(Map<String, String> paramMap) {
		//System.out.println("--- MessageDAOImpl getReplyEmpNO message_seq :" + paramMap);
		return (List<MemberVO>)getSqlSession().selectList("message.getReplyEmpNO", paramMap);
	}
	
	/* (non-Javadoc)
	 * @see com.hanaph.gw.of.message.dao.MessageDAO#deleteMessageTarget(com.hanaph.gw.of.message.vo.MessageVO)
	 */
	public boolean deleteMessageTarget(MessageVO messageVO) {
		Integer count = getSqlSession().update("message.deleteMessageTarget", messageVO);
		if(count != 0){
			return true;
		}else{
			return false;
		}	
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.of.message.dao.MessageDAO#updateMessageReadYn(com.hanaph.gw.of.message.vo.MessageVO)
	 */
	public void updateMessageReadYn(MessageVO paramMessageVO) {
		getSqlSession().update("message.updateMessageReadYn", paramMessageVO);
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.of.message.dao.MessageDAO#deleteMessageDB(com.hanaph.gw.of.message.vo.MessageVO)
	 */
	public boolean deleteMessageDB(MessageVO messageVO) {
		Integer count = getSqlSession().delete("message.deleteMessageDB", messageVO);
		if(count != 0){
			return true;
		}else{
			return false;
		}	
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.of.message.dao.MessageDAO#getMessageReadDataList(java.util.Map)
	 */
	@SuppressWarnings("unchecked")
	public List<CommonTargetVO> getMessageReadDataList(Map<String, String> paramMap) {
		return (List<CommonTargetVO>)getSqlSession().selectList("message.getMessageReadDataList", paramMap);
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.of.message.dao.MessageDAO#deleteMessageTargetDB(com.hanaph.gw.of.message.vo.MessageVO)
	 */
	public void deleteMessageTargetDB(MessageVO messageVO) {
		getSqlSession().delete("message.deleteMessageTargetDB", messageVO);
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.of.message.dao.MessageDAO#deleteMessage(com.hanaph.gw.of.message.vo.MessageVO)
	 */
	@Override
	public boolean deleteMessage(MessageVO messageVO) {
		Integer count = getSqlSession().update("message.deleteMessage", messageVO);
		if(count != 0){
			return true;
		}else{
			return false;
		}	
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.of.message.dao.MessageDAO#insertMessageTargetToss(com.hanaph.gw.of.message.vo.MessageVO)
	 */
	@Override
	public void insertMessageTargetToss(MessageVO messageVO) {
		SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH);
		sqlSession.commit(false);
		
		try{
			if(messageVO.getEmp_no().indexOf("|") > -1){
				String emp_no = messageVO.getEmp_no();
				String[] emp_nos = emp_no.split("\\|");
				
				for(int i= 0; i<emp_nos.length; i++){
					messageVO.setEmp_no(emp_nos[i]);
					sqlSession.insert("message.insertMessageTargetToss", messageVO);
				}
			}else{
				sqlSession.insert("message.insertMessageTargetToss", messageVO);
			}
			
			sqlSession.commit();
			
		}catch(RuntimeException e){
			logger.debug("쪽지 전달 실패 :: " , e);
			sqlSession.rollback();
		}finally{
			sqlSession.close();	
		}
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.of.message.dao.MessageDAO#getMessageSender(java.lang.String)
	 */
	@Override
	public MemberVO getMessageSender(Map<String, String> paramMap) {
		return (MemberVO)getSqlSession().selectOne("message.getMessageSender", paramMap);
	}

}
