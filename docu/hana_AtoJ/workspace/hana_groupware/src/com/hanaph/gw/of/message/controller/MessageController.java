/**
  * Hana Project
  * Copyright 2014 iRush Co.,
  *
  */
package com.hanaph.gw.of.message.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.ModelAndViewDefiningException;

import com.hanaph.gw.co.authority.service.AuthorityService;
import com.hanaph.gw.co.common.utils.CommonUtil;
import com.hanaph.gw.co.common.utils.Environment;
import com.hanaph.gw.co.common.utils.MarshallerUtil;
import com.hanaph.gw.co.common.utils.MenuUtil;
import com.hanaph.gw.co.common.utils.RequestFilterUtil;
import com.hanaph.gw.co.common.utils.StringUtil;
import com.hanaph.gw.co.common.utils.WebUtil;
import com.hanaph.gw.co.fileAttach.service.FileAttachService;
import com.hanaph.gw.co.fileAttach.vo.FileAttachVO;
import com.hanaph.gw.co.menu.service.MenuService;
import com.hanaph.gw.co.menu.vo.MenuVO;
import com.hanaph.gw.of.message.service.MessageService;
import com.hanaph.gw.of.message.vo.MessageVO;
import com.hanaph.gw.pe.member.vo.MemberVO;

/**
 * <pre>
 * Class Name : MessageController.java
 * 설명 : 쪽지보내기 Controller
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
@Controller
public class MessageController {
	
	@Autowired
	private MessageService messageService;
	
	@Autowired
	private MenuService menuService;
	
	@Autowired
	private FileAttachService fileAttachService;
	
	@Autowired
	private AuthorityService authorityService;
	
	private static final Logger logger = Logger.getLogger(MessageController.class);
	private Environment env = new Environment();
	
	
	/**
	 * <pre>
	 * 1. 개요     :	쪽지 보내기
	 * 2. 처리내용 : 쪽지 보내기 페이지로 이동한다.
	 * </pre>
	 * @Method Name : messageWrite
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/of/message/messageWrite.do")
	public ModelAndView messageWrite(HttpServletRequest request, HttpServletResponse response ){
		
		ModelAndView mav = new ModelAndView("of/message/messageWrite");
		
		final String MENU_CHILD= "010301";

		//LNB 메뉴 생성 START
		String menu_parents = StringUtil.substring(MENU_CHILD, 2);
		MenuUtil mu = new MenuUtil();
		List<MenuVO> lnbMenuList = mu.makeLnb(request, menu_parents , menuService);
		mav.addObject("lnbMenuList", lnbMenuList);
		mav.addObject("MENU_CHILD", MENU_CHILD);
		//LNB 메뉴 생성 END
		
		return mav;
	}
	
	/**
	 * <pre>
	 * 1. 개요     :	쪽지 보내기 
	 * 2. 처리내용 :	db에 쪽지 데이터를 저장 한다.
	 * </pre>
	 * @Method Name : InsertMessage
	 * @param request
	 * @return
	 */
	@RequestMapping("/of/message/insertMessage.do")
	public ModelAndView insertMessage(HttpServletRequest request, HttpServletResponse response )
			throws IOException, ModelAndViewDefiningException{
		
		ModelAndView mav = new ModelAndView("");
		
		HttpSession session = request.getSession();
		MemberVO memberSessionVO = (MemberVO) session.getAttribute("gwUser");
		
		String subject = StringUtil.nvl(request.getParameter("subject"));		//쪽지제목
		String contents = StringUtil.nvl(request.getParameter("contents"));		//쪽지내용
		String target_empNos[] = request.getParameterValues(("target_empNos"));	//받는직원리스트
		String target_empNo = "";
		String type = StringUtil.nvl(request.getParameter("type"));		//쪽지구분 1.받은2.보낸3.전달
		String message_seq = StringUtil.nvl(request.getParameter("message_seq"));		//쪽지번호(시퀀스)
		contents = RequestFilterUtil.convertToSearchParameter(contents);
		subject = RequestFilterUtil.convertToSearchParameter(subject);
		
		if(target_empNos != null){
			for (int i = 0; i < target_empNos.length; i++) {
				target_empNo += target_empNos[i]+"|";
			}
		}
		
		MessageVO messageVO = new MessageVO();
		messageVO.setSubject(subject);
		messageVO.setContents(contents);
		messageVO.setEmp_no(target_empNo);
		messageVO.setCreate_no(memberSessionVO.getEmp_no());
		
		String seq = "";
		
		String url = "window.location.href='"+env.getValue("root_dir.url")+"/of/message/messageList.do?type=2';";
		
		if("3".equals(type)){	//전달 구분
			messageVO.setEmp_no(StringUtil.nvl(request.getParameter("target_empNo")));//전달직원리스트
			messageVO.setMessage_seq(message_seq);
			messageService.insertMessageTargetToss(messageVO);
			
			//CHOE 20160509 쪽지를 전달할때 첨부된 파일이 있다면 같이 보낸다. 추가 보완 기능
			if(!"".equals(StringUtil.nvl(request.getParameter("fileNum")))){
				Map<String, String> paramMap = new HashMap<String, String>(); //파일첨부 파라미터
				paramMap.put("fileNum", StringUtil.nvl(request.getParameter("fileNum")));
				paramMap.put("seq", String.valueOf(seq));
				
				fileAttachService.updateFileAttach(paramMap);
			}
			//--------------------------------------------------
			
			mav = CommonUtil.getMessageView("쪽지가 전달 되었습니다.", url , "" );
			throw new ModelAndViewDefiningException(mav);
			
		}else{
			seq = messageService.insertMessage(messageVO);
			
			if(!"".equals(seq)){
				if(!"".equals(StringUtil.nvl(request.getParameter("fileNum")))){
					Map<String, String> paramMap = new HashMap<String, String>(); //파일첨부 파라미터
					paramMap.put("fileNum", StringUtil.nvl(request.getParameter("fileNum")));
					paramMap.put("seq", String.valueOf(seq));
					
					fileAttachService.updateFileAttach(paramMap);
				}
				
				String delFileSeq = StringUtil.nvl(request.getParameter("delFileSeq"));
				
				if(!"".equals(delFileSeq)){
					fileAttachService.deleteAttach(delFileSeq);
				}
				
				logger.debug("*********** 쪽지 보내기 성공 ***********");
				mav = CommonUtil.getMessageView("쪽지가 전송 되었습니다.", url , "" );
				throw new ModelAndViewDefiningException(mav);
			}else{
				logger.debug("*********** 쪽지 보내기 실패!! ***********");
				mav = CommonUtil.getMessageView("쪽지 전송 실패!.", url , "" );
				throw new ModelAndViewDefiningException(mav);
			}
		}
		
	}
	
	/**
	 * <pre>
	 * 1. 개요     : 받은 쪽지함
	 * 2. 처리내용 : 받은 쪽지함 리스트를 보여준다.
	 * </pre>
	 * @Method Name : messageList
	 * @param request
	 * @return
	 */
	@RequestMapping("/of/message/messageList.do")
	public ModelAndView messageList(HttpServletRequest request){
		ModelAndView mav = new ModelAndView("of/message/messageList");
		
		String searchDate[] = WebUtil.dateCal(-2);											//현재달에서-2달한날짜
		String searchType = StringUtil.nvl(request.getParameter("searchType"));				//검색타입
		String searchKeyword = StringUtil.nvl(request.getParameter("searchKeyword"));		//검색키워드
		String search_read_yn = StringUtil.nvl(request.getParameter("search_read_yn"),"all"); //열람여부
		String start_ymd = StringUtil.nvl(request.getParameter("start_ymd"),searchDate[0]);	//검색시작날짜
		String end_ymd = StringUtil.nvl(request.getParameter("end_ymd"),searchDate[1]);		//검색마지막날짜
		String type = StringUtil.nvl(request.getParameter("type"));							//받은.보낸쪽지 타입 1.받은 2.보낸
		String seq = StringUtil.nvl(request.getParameter("seq"));							//메세지 시퀀스
		
		String MENU_CHILD = "";
		if("1".equals(type)){		//받은쪽지함
			MENU_CHILD = "010302";
		}else if("2".equals(type)){	//보낸쪽지함
			MENU_CHILD = "010303";
		}
		
		HttpSession session = request.getSession();
		MemberVO memberSessionVO = (MemberVO) session.getAttribute("gwUser");
		
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("searchKeyword", searchKeyword);
		paramMap.put("searchType", searchType);
		paramMap.put("search_start_ymd", start_ymd);
		paramMap.put("search_end_ymd", end_ymd);
		paramMap.put("search_read_yn", search_read_yn);
		
		paramMap.put("type", type);
		paramMap.put("emp_no", memberSessionVO.getEmp_no());
		
		List<MessageVO> messageList = messageService.getMessageList(paramMap);
		
		/*관리자 권한 리스트 가져오기*/
		int [] authIdxArray = {1,2};
		Map<String, Object> paramMap1 = new HashMap<String, Object>();
		paramMap1.put("authIdxArray", authIdxArray);
		List<MemberVO> authorityMemberList = authorityService.getAuthorityMemberList(paramMap1);

		
		/*LNB 메뉴 생성 START*/
		String menu_parents = StringUtil.substring(MENU_CHILD, 2);
		MenuUtil mu = new MenuUtil();
		List<MenuVO> lnbMenuList = mu.makeLnb(request, menu_parents , menuService);
		mav.addObject("lnbMenuList", lnbMenuList);
		mav.addObject("MENU_CHILD", MENU_CHILD);
		/*LNB 메뉴 생성 END*/
		
		/*검색어 parameter*/
		mav.addObject("search_read_yn", search_read_yn);
		mav.addObject("searchType", searchType);
		mav.addObject("searchKeyword", searchKeyword);
		mav.addObject("start_ymd", start_ymd);
		mav.addObject("end_ymd", end_ymd);
		
		/*쪽지함 parameter*/
		mav.addObject("messageList", messageList);
		mav.addObject("type", type);
		
		/*권한*/
		mav.addObject("emp_no", memberSessionVO.getEmp_no());
		mav.addObject("authorityMemberList", authorityMemberList);
		
		/*메세지 시퀀스*/
		mav.addObject("seq", seq);
		
		return mav;
	}
	
	/**
	 * <pre>
	 * 1. 개요     :	쪽지 상세정보 
	 * 2. 처리내용 :	쪽지 상세정보를 보여준다
	 * </pre>
	 * @Method Name : messageDetail
	 * @param request
	 * @return
	 */
	@RequestMapping("/of/message/messageDetail.do")
	public void messageDetail(HttpServletRequest request, HttpServletResponse response) throws IOException{
		
		HttpSession session = request.getSession();
		MemberVO memberSessionVO = (MemberVO) session.getAttribute("gwUser");

		String message_seq = StringUtil.nvl(request.getParameter("message_seq"));	//메세지시퀀스
		
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("message_seq", message_seq);
		paramMap.put("delete_yn", "N");
		
		MessageVO messageDetail = messageService.getMessageDetail(paramMap);
		
		List<MemberVO> receiveEmpNO = messageService.getReceiveEmpNO(paramMap);
		
		paramMap.put("seq",message_seq);
		paramMap.put("cd", "O03000");	//쪽지함 구분코드 바뀌면 안됨!	
		List<FileAttachVO> attachList = fileAttachService.getAttachList(paramMap);

		MessageVO paramMessageVO = new MessageVO();
		paramMessageVO.setMessage_seq(message_seq);
		paramMessageVO.setDelete_yn("N");
		paramMessageVO.setRead_yn("Y");
		paramMessageVO.setEmp_no(memberSessionVO.getEmp_no());
		
		messageService.updateMessageReadYn(paramMessageVO);
		
		MessageVO returnMessageVO = new MessageVO();
		returnMessageVO.setList(receiveEmpNO);
		returnMessageVO.setAttachList(attachList);
		returnMessageVO.setEmp_ko_nm(messageDetail.getEmp_ko_nm());
		returnMessageVO.setContents(messageDetail.getContents());
		returnMessageVO.setCreate_dt(messageDetail.getCreate_dt());
		
		MarshallerUtil.marshalling("json", response, returnMessageVO);
	}
	
	/**
	 * <pre>
	 * 1. 개요     :	쪽지 삭제 
	 * 2. 처리내용 : 쪽지를 삭제한다.
	 * </pre>
	 * @Method Name : deleteMessage
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws ModelAndViewDefiningException
	 */
	@RequestMapping("/of/message/deleteMessage.do")
	public ModelAndView deleteMessage(HttpServletRequest request, HttpServletResponse response )
			throws IOException, ModelAndViewDefiningException{
		
		ModelAndView mav = new ModelAndView("");
		
		HttpSession session = request.getSession();
		MemberVO memberSessionVO = (MemberVO) session.getAttribute("gwUser");
		
		String message_seq = StringUtil.nvl(request.getParameter("message_seq"));	//메시지시퀀스
		String type = StringUtil.nvl(request.getParameter("type"));					//받은보낸쪽지 타입
		
		MessageVO messageVO = new MessageVO();
		messageVO.setMessage_seq(message_seq);
		messageVO.setEmp_no(memberSessionVO.getEmp_no());
		messageVO.setType(type);
		messageVO.setModify_no(memberSessionVO.getEmp_no());
		messageVO.setDelete_yn("Y");		
		
		boolean result = false;
		
		String url = "window.location.href='"+env.getValue("root_dir.url")+"/of/message/messageList.do?type=" + type + "';";

		result = messageService.deleteMessage(messageVO);

		if(result){
			logger.debug("*********** 쪽지 삭제 성공 ***********");
			mav = CommonUtil.getMessageView("삭제 되었습니다.", url , "" );
			throw new ModelAndViewDefiningException(mav);
		}else{
			logger.debug("*********** 쪽지 삭제 실패!! ***********");
			mav = CommonUtil.getMessageView("삭제 실패!.", url , "" );
			throw new ModelAndViewDefiningException(mav);
		}
	}
	
	/**
	 * <pre>
	 * 1. 개요     :	쪽지 데이터 DB삭제 
	 * 2. 처리내용 : 쪽지 데이터를 DB에서 삭제 한다.
	 * </pre>
	 * @Method Name : deleteMessageDB
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws ModelAndViewDefiningException
	 */
	@RequestMapping("/of/message/deleteMessageDB.do")
	public ModelAndView deleteMessageDB(HttpServletRequest request, HttpServletResponse response )
			throws IOException, ModelAndViewDefiningException{
		
		ModelAndView mav = new ModelAndView("");
		
		HttpSession session = request.getSession();
		MemberVO memberSessionVO = (MemberVO) session.getAttribute("gwUser");
		
		String message_seq = request.getParameter("message_seq");	//메세지시퀀스
		String type = StringUtil.nvl(request.getParameter("type"));	//메세지타입
		
		MessageVO messageVO = new MessageVO();
		messageVO.setMessage_seq(message_seq);
		messageVO.setEmp_no(memberSessionVO.getEmp_no());
		messageVO.setDelete_yn("Y");		
		
		boolean result = false;
		
		String url = "window.location.href='"+env.getValue("root_dir.url")+"/of/message/messageList.do?type=" + type + "';";

		result = messageService.deleteMessageDB(messageVO);

		if(result){
			logger.debug("*********** 데이터 삭제 성공 ***********");
			mav = CommonUtil.getMessageView("데이터가 삭제 되었습니다.", url , "" );
			throw new ModelAndViewDefiningException(mav);
		}else{
			logger.debug("*********** 데이터 삭제 실패!! ***********");
			mav = CommonUtil.getMessageView("데이터 삭제 실패!.", url , "" );
			throw new ModelAndViewDefiningException(mav);
		}
	}
	
	/**
	 * <pre>
	 * 1. 개요     : 답장 쓰기 화면으로 이동
	 * 2. 처리내용 : 답장 쓰기 화면으로 이동한다.
	 * </pre>
	 * @Method Name : messageReplyWrite
	 * @param request
	 * @param response
	 * @return
	 */		
	@RequestMapping("/of/message/messageReplyWrite.do")
	public ModelAndView messageReplyWrite(HttpServletRequest request, HttpServletResponse response ){
		
		ModelAndView mav = new ModelAndView("of/message/messageReplyWrite");
		
		final String MENU_CHILD= "010301";

		//LNB 메뉴 생성 START
		String menu_parents = StringUtil.substring(MENU_CHILD, 2);
		MenuUtil mu = new MenuUtil();
		List<MenuVO> lnbMenuList = mu.makeLnb(request, menu_parents , menuService);
		mav.addObject("lnbMenuList", lnbMenuList);
		mav.addObject("MENU_CHILD", MENU_CHILD);
		//LNB 메뉴 생성 END
		
		String message_seq = StringUtil.nvl(request.getParameter("message_seq"));	//메세지시퀀스
		
		//System.out.println("MessageController messageReplyWrite message_seq : " + message_seq);
		
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("message_seq", message_seq);
		paramMap.put("delete_yn", "N");
		
		// 원본 쪽지 내용
		MessageVO messageDetail = messageService.getMessageDetail(paramMap);
		
		//System.out.println(" MessageController messageReplyWrite messageDetail.getCreate_no" + messageDetail.getCreate_no());
		
		paramMap.put("sender_emp_no", messageDetail.getCreate_no());
		
		// 원본 쪽지 발신인 정보 
		MemberVO senderDetail = messageService.getMessageSender(paramMap);
		
		System.out.println("=============================================");
		System.out.println("원본 정보");
		
		System.out.println("제목 : " + messageDetail.getSubject());
		System.out.println("내용 : " + messageDetail.getContents());
		
		System.out.println("원본 발신인 부서 : " + senderDetail.getDept_ko_nm());
		System.out.println("원본 발신인 직급 : " + senderDetail.getEmp_ko_nm());
		System.out.println("원본 발신인 이름 : " + senderDetail.getGrad_ko_nm());
		
		System.out.println("=============================================");
		
		mav.addObject("messageDetail", messageDetail);
		mav.addObject("senderDetail", senderDetail);
		
		return mav;
	}
}
