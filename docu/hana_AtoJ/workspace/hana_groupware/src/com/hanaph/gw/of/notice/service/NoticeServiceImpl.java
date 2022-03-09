/**
  * Hana Project
  * Copyright 2014 iRush Co.,
  *
  */
package com.hanaph.gw.of.notice.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hanaph.gw.co.common.utils.StringUtil;
import com.hanaph.gw.co.fileAttach.service.FileAttachService;
import com.hanaph.gw.co.fileAttach.vo.FileAttachVO;
import com.hanaph.gw.of.common.vo.CommonTargetVO;
import com.hanaph.gw.of.notice.dao.NoticeDAO;
import com.hanaph.gw.of.notice.vo.NoticeCommentVO;
import com.hanaph.gw.of.notice.vo.NoticeReadVO;
import com.hanaph.gw.of.notice.vo.NoticeVO;
import com.hanaph.gw.pe.member.vo.MemberVO;

/**
 * <pre>
 * Class Name : NoticeServiceImpl.java
 * 설명 : 공지사항 ServiceImpl
 * 
 * Modification Information
 *    수정일      수정자              수정 내용
 *  ------------ -------------- --------------------------------
 *  2014. 11. 21.      Jung.Jin.Muk          
 * </pre>
 * 
 * @version : 
 * @author  : Jung.Jin.Muk(pc123pc@irush.co.kr)
 * @since   : 2014. 11. 21.
 */
@Service(value="NoticeService")
public class NoticeServiceImpl implements NoticeService{
	
	private static final Logger logger = Logger.getLogger(NoticeServiceImpl.class);

	@Autowired
	private NoticeDAO noticeDAO;
	
	@Autowired
	private FileAttachService fileAttachService;
	
	@Autowired
	SqlSessionFactory sqlSessionFactory;

	/* (non-Javadoc)
	 * @see com.hanaph.gw.of.notice.service.NoticeService#getNoticeList(java.util.Map)
	 */
	public List<NoticeVO> getNoticeList(Map<String, String> paramMap) {
		return noticeDAO.getNoticeList(paramMap);
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.of.notice.service.NoticeService#getNoticeCount(java.util.Map)
	 */
	public int getNoticeCount(Map<String, String> paramMap) {
		return noticeDAO.getNoticeCount(paramMap);
	}
	
	/* (non-Javadoc)
	 * @see com.hanaph.gw.of.notice.service.NoticeService#getNoticeSeq()
	 */
	public int getNoticeSeq() {
		return noticeDAO.getNoticeSeq();
	}
	
	/* (non-Javadoc)
	 * @see com.hanaph.gw.of.notice.service.NoticeService#insertNotice(com.hanaph.gw.of.notice.vo.NoticeVO, com.hanaph.gw.of.notice.vo.NoticeReadVO)
	 */
	public String insertNotice(NoticeVO noticeVO, NoticeReadVO noticeReadVO) {
		return noticeDAO.insertNotice(noticeVO, noticeReadVO);
	}
	
	/* (non-Javadoc)
	 * @see com.hanaph.gw.of.notice.service.NoticeService#updateNotice(com.hanaph.gw.of.notice.vo.NoticeVO, com.hanaph.gw.of.notice.vo.NoticeReadVO)
	 */
	public boolean updateNotice(NoticeVO noticeVO, NoticeReadVO noticeReadVO) {
		return noticeDAO.updateNotice(noticeVO, noticeReadVO);
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.of.notice.service.NoticeService#noticeDetail(com.hanaph.gw.of.notice.vo.NoticeReadVO)
	 */
	@Transactional
	public NoticeVO noticeDetail(NoticeReadVO noticeReadVO) {
		
		NoticeReadVO readData =  noticeDAO.noticeReadData(noticeReadVO);
		
		//2015.04.10 kta 공지사항 대상자 없이 등록		
		//if("N".equals(readData.getRead_yn())){
		if (null == readData) {
			noticeReadVO.setRead_yn("Y");
			noticeDAO.updateNoticeRead(noticeReadVO);		//조회데이터 저장
			noticeDAO.updateNoticeReadAdd(noticeReadVO);	//조회수 증가(업데이트)
		}
		return noticeDAO.noticeDetail(noticeReadVO);
	}
	
	/* (non-Javadoc)
	 * @see com.hanaph.gw.of.notice.service.NoticeService#getNoticeCommentList(java.util.Map)
	 */
	public List<NoticeCommentVO> getNoticeCommentList(Map<String, String> paramMap) {
		return noticeDAO.getNoticeCommentList(paramMap);
	}
	
	/* (non-Javadoc)
	 * @see com.hanaph.gw.of.notice.service.NoticeService#getNoticeCommentCount(int)
	 */
	public int getNoticeCommentCount(int seq) {
		return noticeDAO.getNoticeCommentCount(seq);
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.of.notice.service.NoticeService#deleteNotice(com.hanaph.gw.of.notice.vo.NoticeVO)
	 */
	public boolean deleteNotice(NoticeVO noticeVO) {
		boolean result = false;
		
		/*첨부파일 삭제처리 파라미터 셋팅*/
		FileAttachVO fileAttachVO = new FileAttachVO();
		fileAttachVO.setCd("O01000"); //공지사항 코드
		fileAttachVO.setSeq(noticeVO.getSeq());	//공지사항 시퀀스
		fileAttachVO.setDelete_yn("Y"); //첨부파일 삭제여부
		
		if(noticeVO.getSeq().indexOf("|") > -1){
			String seq = noticeVO.getSeq();
			String[] seqs = seq.split("\\|");
			
			for(int i= 0; i<seqs.length; i++){
				noticeVO.setSeq(seqs[i]);
				fileAttachVO.setSeq(seqs[i]);	//공지사항 시퀀스
				fileAttachService.deleteAttachAll(fileAttachVO);
				result = noticeDAO.deleteNotice(noticeVO);
			}
		}else{
			fileAttachService.deleteAttachAll(fileAttachVO);
			result = noticeDAO.deleteNotice(noticeVO);
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.of.notice.service.NoticeService#insertComment(com.hanaph.gw.of.notice.vo.NoticeCommentVO)
	 */
	public boolean insertComment(NoticeCommentVO noticeCmtVO) {
		return noticeDAO.insertComment(noticeCmtVO);
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.of.notice.service.NoticeService#deleteComment(com.hanaph.gw.of.notice.vo.NoticeCommentVO)
	 */
	public boolean deleteComment(NoticeCommentVO noticeCmtVO) {
		return noticeDAO.deleteComment(noticeCmtVO);
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.of.notice.service.NoticeService#getNoticeMemberList(java.util.Map)
	 */
	public List<MemberVO> getNoticeMemberList(Map<String, String> paramMap) {
		return noticeDAO.getNoticeMemberList(paramMap);
	}

	/* (non-Javadoc)
	 * @see com.hanaph.gw.of.notice.service.NoticeService#getNoticeReadDataList(java.util.Map)
	 */
	public List<CommonTargetVO> getNoticeReadDataList(Map<String, String> paramMap) {
		return noticeDAO.getNoticeReadDataList(paramMap);
	}

	@Override
	public int getNoticeCountNoread(Map<String, String> paramMap) {
		return noticeDAO.getNoticeCountNoread(paramMap); 
	}
	
	
}
