/**
  * Hana Project
  * Copyright 2014 iRush Co.,
  *
  */
package com.hanaph.gw.of.common.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.hanaph.gw.co.common.utils.StringUtil;
import com.hanaph.gw.of.board.service.BoardService;
import com.hanaph.gw.of.common.vo.CommonTargetVO;
import com.hanaph.gw.of.message.service.MessageService;
import com.hanaph.gw.of.notice.service.NoticeService;

/**
 * <pre>
 * Class Name : CommonTagetController.java
 * 설명 : 게시글,공지글,메세지 공통 Controller
 * 
 * Modification Information
 *    수정일      수정자              수정 내용
 *  ------------ -------------- --------------------------------
 *  2014. 12. 16.      CHOIILJI         
 * </pre>
 * 
 * @version : 
 * @author  : CHOIILJI(choiilji@irush.co.kr)
 * @since   : 2014. 12. 16.
 */
@Controller
public class CommonTagetController {
	
	@Autowired
	private NoticeService noticeService;
	
	@Autowired
	private BoardService boardService;
	
	@Autowired
	private MessageService messageService;
	
	/**
	 * <pre>
	 * 1. 개요     : 확인 여부
	 * 2. 처리내용 : 게시글,공지글,메세지 확인 상세 데이터를 불러온다.
	 * </pre>
	 * @Method Name : messageReadData
	 * @param request
	 * @return
	 */
	@RequestMapping("/of/common/commonReadData.do")
	public ModelAndView commonReadData(HttpServletRequest request){
		
		ModelAndView mav = new ModelAndView("of/common/commonReadDataPopup");
		
		Map<String, String> paramMap = new HashMap<String, String>();  
		
		//대상타겟 구분 - AUTH, NOTICE, BOARD, MESSAGE
		String type = StringUtil.nvl(request.getParameter("type"));
		String seq = StringUtil.nvl(request.getParameter("seq"));
		
		List<CommonTargetVO> memberReadDataList = null;
		if(type.equals("NOTICE")){
			paramMap.put("notice_seq", seq);
			memberReadDataList = noticeService.getNoticeReadDataList(paramMap);
		}else if(type.equals("BOARD")){
			paramMap.put("board_seq", seq);
			memberReadDataList = boardService.getBoardReadDataList(paramMap);
		}else if(type.equals("MESSAGE")){
			paramMap.put("message_seq", seq);
			memberReadDataList = messageService.getMessageReadDataList(paramMap);
		}
				
		mav.addObject("memberReadDataList", memberReadDataList);
		
        return mav;
	}
}
