/**
  * Hana Project
  * Copyright 2014 iRush Co.,
  *
  */
package com.hanaph.gw.of.notice.controller;

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

import com.hanaph.gw.co.authority.service.AuthorityService;
import com.hanaph.gw.co.code.service.CodeService;
import com.hanaph.gw.co.code.vo.CodeVO;
import com.hanaph.gw.co.common.utils.CommonUtil;
import com.hanaph.gw.co.common.utils.Environment;
import com.hanaph.gw.co.common.utils.MarshallerUtil;
import com.hanaph.gw.co.common.utils.MenuUtil;
import com.hanaph.gw.co.common.utils.PageUtil;
import com.hanaph.gw.co.common.utils.StringUtil;
import com.hanaph.gw.co.common.utils.WebUtil;
import com.hanaph.gw.co.fileAttach.service.FileAttachService;
import com.hanaph.gw.co.fileAttach.vo.FileAttachVO;
import com.hanaph.gw.co.menu.service.MenuService;
import com.hanaph.gw.co.menu.vo.MenuVO;
import com.hanaph.gw.of.notice.service.NoticeService;
import com.hanaph.gw.of.notice.vo.NoticeCommentVO;
import com.hanaph.gw.of.notice.vo.NoticeReadVO;
import com.hanaph.gw.of.notice.vo.NoticeVO;
import com.hanaph.gw.pe.member.vo.MemberVO;

/**
 * <pre>
 * Class Name : NoticeController.java
 * 설명 : 공지사항 Controller
 * 
 * Modification Information
 *    수정일      수정자              수정 내용
 *  ------------ -------------- --------------------------------
 *  2014. 11. 21.      Jung.Jin.Muk          
 *  2015. 04. 04.      KTA - 공지대상자 없이 등록하고 읽음표시 처리
 * </pre>
 * 
 * @version : 1.0
 * @author  : Jung.Jin.Muk(pc123pc@irush.co.kr)
 * @since   : 2014. 11. 21.
 */

@Controller
public class NoticeController {
	
	@Autowired
	private NoticeService noticeService;
	
	@Autowired
	private MenuService menuService;
	
	@Autowired
	private CodeService codeService;
	
	@Autowired
	private FileAttachService fileAttachService;
	
	@Autowired
	private AuthorityService authorityService;
	
	private static final Logger logger = Logger.getLogger(NoticeController.class);
	private Environment env = new Environment();
	
	/**
	 * <pre>
	 * 1. 개요     : 공지사항 리스트 
	 * 2. 처리내용 :	공지사항 리스트를 가져온다.
	 * </pre>
	 * @Method Name : noticeList
	 * @param request
	 * @return
	 */
	@RequestMapping("/of/notice/noticeList.do")
	public ModelAndView noticeList(HttpServletRequest request){
		
		ModelAndView mav = new ModelAndView("of/notice/noticeList");
		
		final String MENU_CHILD= "0101";
		
		int currentPage = Integer.parseInt(StringUtil.nvl(request.getParameter("currentPage"),"1"));	// 현재 페이지 번호
		int plRowRange = 10;	
		int plPageRange = 10;
		
		//String searchDate[] = WebUtil.dateCal(-2); //CHOE 20150903 1년 단위 조회함
		String searchDate[] = WebUtil.NewYearDate();
		
		String searchType = StringUtil.nvl(request.getParameter("searchType"),"subject");						//검색타입
		String searchKeyword = StringUtil.nvl(request.getParameter("searchKeyword"));							//검색어
		String search_noti_kind = StringUtil.nvl(request.getParameter("search_noti_kind"),"all");				//공지사항구분
		String search_start_ymd = StringUtil.nvl(request.getParameter("search_start_ymd"),searchDate[0]);		//시작날짜
		String search_end_ymd = StringUtil.nvl(request.getParameter("search_end_ymd"),searchDate[1]);			//마지막날짜
		String search_read_yn = StringUtil.nvl(request.getParameter("search_read_yn"),"all");					//열람여부	
		
		HttpSession session = request.getSession();
		MemberVO memberSessionVO = (MemberVO) session.getAttribute("gwUser");
		
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("noti_kind", search_noti_kind);
		paramMap.put("search_noti_kind", search_noti_kind);
		paramMap.put("searchKeyword", searchKeyword);
		paramMap.put("searchType", searchType);
		paramMap.put("search_start_ymd", search_start_ymd);
		paramMap.put("search_end_ymd", search_end_ymd);
		paramMap.put("page",String.valueOf(currentPage));		
		paramMap.put("perPageRow", String.valueOf(plRowRange));
		paramMap.put("emp_no", memberSessionVO.getEmp_no());
		paramMap.put("search_read_yn", search_read_yn);
		
		List<NoticeVO> noticeList = noticeService.getNoticeList(paramMap);	//공지사항 리스트
		int cnt = noticeService.getNoticeCount(paramMap);					//코멘트 카운트
		
		/*공지사항 코드 데이터*/
		Map<String, String> codeParmaMap = new HashMap<String, String>();
		codeParmaMap.put("m_cd", "O01");
		List<CodeVO> codeList = codeService.getCodeList(codeParmaMap);
		
		/*관리자 권한 리스트 가져오기*/
		int [] authIdxArray = {1,2};
		Map<String, Object> paramMap1 = new HashMap<String, Object>();
		paramMap1.put("authIdxArray", authIdxArray);
		List<MemberVO> authorityMemberList = authorityService.getAuthorityMemberList(paramMap1);
		
		PageUtil pu = new PageUtil();
		String pagingStr = pu.autoPaging(cnt, plRowRange, plPageRange, currentPage);
		
		/*LNB 메뉴 생성 START*/
		String menu_parents = StringUtil.substring(MENU_CHILD, 2);
		MenuUtil mu = new MenuUtil();
		List<MenuVO> lnbMenuList = mu.makeLnb(request, menu_parents , menuService);
		mav.addObject("lnbMenuList", lnbMenuList);
		mav.addObject("MENU_CHILD", MENU_CHILD);
		/*LNB 메뉴 생성 END*/
		
		/*검색 파라미터*/
		mav.addObject("search_noti_kind", search_noti_kind);
		mav.addObject("searchType", searchType);
		mav.addObject("searchKeyword", searchKeyword);
		mav.addObject("search_start_ymd", search_start_ymd);
		mav.addObject("search_end_ymd", search_end_ymd);
		mav.addObject("search_read_yn", search_read_yn);
		
		/*페이징 파라미터*/
		mav.addObject("pagingStr", pagingStr);
		mav.addObject("cnt", cnt);
		
		/*공지사항*/
		mav.addObject("noticeList", noticeList);
		
		/*공지사항 구분코드 데이터*/
		mav.addObject("codeList", codeList);			
		
		/*권한*/
		mav.addObject("emp_no", memberSessionVO.getEmp_no());
		mav.addObject("authorityMemberList", authorityMemberList);
		
		return mav;
	}
	
	/**
	 * <pre>
	 * 1. 개요     : 공지사항 등록 
	 * 2. 처리내용 :	공지사항 등록 view
	 * </pre>
	 * @Method Name : insertNoticeForm
	 * @param request
	 * @return
	 */
	@RequestMapping("/of/notice/insertNoticeForm")
	public ModelAndView insertNoticeForm(HttpServletRequest request) {
		
		ModelAndView mav = new ModelAndView("of/notice/noticeInsertForm");
		
		final String MENU_CHILD= "0102";
		final String cd = "O01000";
		
		String seq = StringUtil.nvl(request.getParameter("seq"));							//공지사항시퀀스
		String update = StringUtil.nvl(request.getParameter("update"));						//업데이트구분자
		String search_start_ymd = StringUtil.nvl(request.getParameter("search_start_ymd"));	//검색시작날짜
		String search_end_ymd = StringUtil.nvl(request.getParameter("search_end_ymd"));		//검색마지막날짜
		String search_noti_kind = StringUtil.nvl(request.getParameter("search_noti_kind"));	//공지사항구분(긴급,일반)
		String searchKeyword = StringUtil.nvl(request.getParameter("searchKeyword"));		//검색키워드
		String searchType = StringUtil.nvl(request.getParameter("searchType"),"subject");	//검색타입
		
		HttpSession session = request.getSession();
		MemberVO memberSessionVO = (MemberVO) session.getAttribute("gwUser");
		
		Map<String, String> paramMap = new HashMap<String, String>();
		
		String searchDate[] = WebUtil.dateCal(12);  //CHOE 20150903 12개월은 개시함

		NoticeVO noticeVO = null;
		if("update".equals(update)){	//update파라미터값이 있으면 수정모드
			NoticeReadVO noticeReadVO = new NoticeReadVO();
			noticeReadVO.setSeq(Integer.parseInt(seq));
			noticeReadVO.setRead_yn("Y");
			noticeReadVO.setEmp_no(memberSessionVO.getEmp_no());
			noticeVO = noticeService.noticeDetail(noticeReadVO);	//공지사항 상세정보 가져온다.
			
			/*파일정보*/
			paramMap.put("seq", seq);
			paramMap.put("cd", cd);
			List<FileAttachVO> attachList = fileAttachService.getAttachList(paramMap);	//파일리스트를 가져온다.
			mav.addObject("attachList", attachList);
		}
		
		/*공지사항 코드 데이터*/
		Map<String, String> codeParmaMap = new HashMap<String, String>();
		codeParmaMap.put("m_cd", "O01");
		List<CodeVO> codeList = codeService.getCodeList(codeParmaMap);	//코드리스트를 가져온다.
		
		/*대상자 목록*/
		paramMap.put("notice_seq", seq);
		List<MemberVO> memberNoticeList = noticeService.getNoticeMemberList(paramMap);
		
		/*LNB 메뉴 생성 START*/
		String menu_parents = StringUtil.substring(MENU_CHILD, 2);
		MenuUtil mu = new MenuUtil();
		List<MenuVO> lnbMenuList = mu.makeLnb(request, menu_parents , menuService);
		mav.addObject("lnbMenuList", lnbMenuList);
		mav.addObject("MENU_CHILD", MENU_CHILD);
		/*LNB 메뉴 생성 END*/
		
		mav.addObject("noticeVO", noticeVO);					//공지사항상세정보
		mav.addObject("codeList", codeList);					//코드리스트
		mav.addObject("start_ymd", searchDate[1]);				//공지사항시작날짜
		mav.addObject("end_ymd", searchDate[0]);				//공지사항마지막날짜
		mav.addObject("search_start_ymd", search_start_ymd);	//검색시작날짜
		mav.addObject("search_end_ymd", search_end_ymd);		//검색마지막날짜
		mav.addObject("search_noti_kind",search_noti_kind);		//검색공지사항구분(긴급,일반)
		mav.addObject("searchKeyword",searchKeyword);			//검색키워드
		mav.addObject("searchType",searchType);					//검색타입
		mav.addObject("memberNoticeList", memberNoticeList);	//공지사항 대상자 직원리스트
		
		return mav;
	}
	
	/**
	 * <pre>
	 * 1. 개요     : 공지사항 등록 
	 * 2. 처리내용 : 공지사항 등록을 한다
	 * </pre>
	 * @Method Name : insertNotice
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/of/notice/insertNotice.do")
	public void insertNotice(HttpServletRequest request, HttpServletResponse response) throws IOException{
		
		HttpSession session = request.getSession();
		MemberVO memberSessionVO = (MemberVO) session.getAttribute("gwUser");
		
		String subject = StringUtil.nvl(request.getParameter("subject"));			//제목
		String noti_kind = StringUtil.nvl(request.getParameter("noti_kind"));		//공지사항 구분
		String contents = StringUtil.nvl(request.getParameter("contents"));			//공지사항 내용
		String start_ymd = StringUtil.nvl(request.getParameter("start_ymd"));		//공지사항 시작 날짜
		String end_ymd = StringUtil.nvl(request.getParameter("end_ymd"));			//공지사항 마지막 날짜
		//2015.04.10 kta 공지사항 대상자 없이 등록
		//String target_empNos[] = request.getParameterValues("target_empNos");		//공지사항 대상자 직원리스트
		String update = StringUtil.nvl(request.getParameter("update"));				//수정구분자
		String currentPage = StringUtil.nvl(request.getParameter("currentPage"));	//현재페이지번호
		
		/*검색 parameter*/
		String search_start_ymd = StringUtil.nvl(request.getParameter("search_start_ymd"));
		String search_end_ymd = StringUtil.nvl(request.getParameter("search_end_ymd"));
		String search_noti_kind = StringUtil.nvl(request.getParameter("search_noti_kind"));
		String searchKeyword = StringUtil.nvl(request.getParameter("searchKeyword"));
		String searchType = StringUtil.nvl(request.getParameter("searchType"),"subject");

		String target_empNo = "";

		//2015.04.10 kta 공지사항 대상자 없이 등록
        /*
        for (int i = 0; i < target_empNos.length; i++) {
			target_empNo += target_empNos[i]+"|";
		}*/
		
		/*공지사항 저장 데이터*/
		NoticeVO noticeVO = new NoticeVO();
		noticeVO.setSeq(StringUtil.nvl(request.getParameter("seq"),"0"));
		noticeVO.setSubject(subject);
		noticeVO.setNoti_kind(noti_kind);
		noticeVO.setContents(contents);
		noticeVO.setStart_ymd(start_ymd);
		noticeVO.setEnd_ymd(end_ymd);
		noticeVO.setCreate_no(memberSessionVO.getEmp_no());
		noticeVO.setModify_no(memberSessionVO.getEmp_no());
		
		boolean result = false;
		String seq = "";
		
		/*공지사항 대상자 데이터*/
		NoticeReadVO noticeReadVO = new NoticeReadVO();
		noticeReadVO.setSeq(Integer.parseInt(StringUtil.nvl(request.getParameter("seq"),"0")));
		noticeReadVO.setEmp_no(target_empNo);
		noticeReadVO.setRead_yn("N");
		
		Map<String, String> returnParamMap = new HashMap<String, String>();
		
		if("update".equals(update)){	//update파라미터값이 있으면 수정모드
			noticeReadVO.setRead_yn("N");
			result = noticeService.updateNotice(noticeVO, noticeReadVO);
			
			returnParamMap.put("returnURL", env.getValue("root_dir.url")+"/of/notice/noticeDetail.do");
			returnParamMap.put("seq", StringUtil.nvl(request.getParameter("seq"),"0"));
			returnParamMap.put("currentPage", currentPage);
			returnParamMap.put("search_noti_kind", search_noti_kind);
			returnParamMap.put("search_start_ymd", search_start_ymd);
			returnParamMap.put("search_end_ymd", search_end_ymd);
			returnParamMap.put("searchKeyword", searchKeyword);
			returnParamMap.put("searchType", searchType);
			
			
		}else{
			returnParamMap.put("returnURL", env.getValue("root_dir.url")+"/of/notice/noticeList.do");
			seq = noticeService.insertNotice(noticeVO, noticeReadVO);
		}
		
		if(result || !"".equals(seq)){
			if(!"".equals(StringUtil.nvl(request.getParameter("fileNum")))){
				Map<String, String> paramMap = new HashMap<String, String>(); //파일첨부 파라미터
				paramMap.put("fileNum", StringUtil.nvl(request.getParameter("fileNum")));
				if("update".equals(update)){
					paramMap.put("seq", StringUtil.nvl(request.getParameter("seq"),"0"));
				}else{
					paramMap.put("seq", seq);
				}
				fileAttachService.updateFileAttach(paramMap);;
			}
			
			String delFileSeq = StringUtil.nvl(request.getParameter("delFileSeq"));
			
			if(!"".equals(delFileSeq)){
				fileAttachService.deleteAttach(delFileSeq);
			}
			
			if("update".equals(update)){
				MarshallerUtil.marshalling("txt", response, CommonUtil.postMessageView("수정 되었습니다.", returnParamMap));
			}else{
				MarshallerUtil.marshalling("txt", response, CommonUtil.postMessageView("등록 되었습니다.", returnParamMap));
			}
		}
	}
	
	/**
	 * <pre>
	 * 1. 개요     :	공지사항 상세 
	 * 2. 처리내용 : 공지사항 상세 정보를 보여준다.
	 * </pre>
	 * @Method Name : noticeDetail
	 * @param request
	 * @return
	 */
	@RequestMapping("/of/notice/noticeDetail.do")
	public ModelAndView noticeDetail(HttpServletRequest request){
		
		ModelAndView mav = new ModelAndView("of/notice/noticeDetail");
		
		int currentPage = Integer.parseInt(StringUtil.nvl(request.getParameter("currentPage"),"1"));	// 현재 페이지 번호

		final String MENU_CHILD= "0102";
		final String cd = "O01000";
		
		String seq = StringUtil.nvl(request.getParameter("seq"));
		
		/*검색 파라미터*/
		String searchType = StringUtil.nvl(request.getParameter("searchType"),"subject");
		String searchKeyword = StringUtil.nvl(request.getParameter("searchKeyword"));
		String search_noti_kind = StringUtil.nvl(request.getParameter("search_noti_kind"));
		String search_start_ymd = StringUtil.nvl(request.getParameter("search_start_ymd"));
		String search_end_ymd = StringUtil.nvl(request.getParameter("search_end_ymd"));
		String search_read_yn = StringUtil.nvl(request.getParameter("search_read_yn"),"all");//열람여부
		
		HttpSession session = request.getSession();
		MemberVO memberSessionVO = (MemberVO) session.getAttribute("gwUser");
		
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("seq",seq);
		
		NoticeReadVO noticeReadVO = new NoticeReadVO();
		noticeReadVO.setSeq(Integer.parseInt(seq));
		noticeReadVO.setRead_yn("Y");
		noticeReadVO.setEmp_no(memberSessionVO.getEmp_no());

		NoticeVO noticeVO = noticeService.noticeDetail(noticeReadVO);
		
		/*첨부파일목록을 가져오기 위한 정보(글 seq, 메뉴 code)*/
		paramMap.put("cd", cd);		
		List<FileAttachVO> attachList = fileAttachService.getAttachList(paramMap);
		
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
		
		mav.addObject("emp_no", memberSessionVO.getEmp_no());	//세션ID
		mav.addObject("noticeVO", noticeVO);					//상세데이터
		mav.addObject("attachList", attachList);				//파일 리스트
		
		/*검색 파라미터*/
		mav.addObject("search_noti_kind", search_noti_kind);	//공지구분
		mav.addObject("searchType", searchType);				//검색타입
		mav.addObject("searchKeyword", searchKeyword);			//검색어
		mav.addObject("search_start_ymd", search_start_ymd);	//날짜
		mav.addObject("search_end_ymd", search_end_ymd);		//날짜
		mav.addObject("currentPage",currentPage);				//현재페이지번호
		mav.addObject("search_read_yn",search_read_yn);		
		
		/*권한*/
		mav.addObject("authorityMemberList", authorityMemberList);
		
		return mav;
	}
	
	/**
	 * <pre>
	 * 1. 개요     : 공지사항 삭제
	 * 2. 처리내용 : 공지사항 삭제를 한다.
	 * </pre>
	 * @Method Name : deleteNotice
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/of/notice/deleteNotice.do")
	public void deleteNotice(HttpServletRequest request, HttpServletResponse response )
			throws IOException{
		
		String searchType = StringUtil.nvl(request.getParameter("searchType"),"subject");		//검색타입
		String search_noti_kind = StringUtil.nvl(request.getParameter("search_noti_kind"));		//공지사항구분
		String search_start_ymd = StringUtil.nvl(request.getParameter("search_start_ymd"));		//시작날짜
		String search_end_ymd = StringUtil.nvl(request.getParameter("search_end_ymd"));			//마지막날짜
		
		int currentPage = Integer.parseInt(StringUtil.nvl(request.getParameter("currentPage"),"1"));	// 현재 페이지 번호
		HttpSession session = request.getSession();
		MemberVO memberSessionVO = (MemberVO) session.getAttribute("gwUser");
		
		boolean result = false;
		
		String seq = request.getParameter("seq");
		
		NoticeVO noticeVO = new NoticeVO();
		noticeVO.setSeq(seq);
		noticeVO.setDelete_yn("Y");
		noticeVO.setModify_no(memberSessionVO.getEmp_no());
		
		result = noticeService.deleteNotice(noticeVO);
		
		Map<String, String> returnParamMap = new HashMap<String, String>();
		
		returnParamMap.put("returnURL", env.getValue("root_dir.url")+"/of/notice/noticeList.do");
		returnParamMap.put("seq", seq);
		returnParamMap.put("page",String.valueOf(currentPage));
		returnParamMap.put("search_noti_kind", search_noti_kind);
		returnParamMap.put("search_start_ymd", search_start_ymd);
		returnParamMap.put("search_end_ymd", search_end_ymd);
		returnParamMap.put("searchType", searchType);
		
		if(result){
			logger.debug("*********** 공지사항 삭제 성공 ***********");
			MarshallerUtil.marshalling("txt", response, CommonUtil.postMessageView("삭제 되었습니다.", returnParamMap));
		}else{
			MarshallerUtil.marshalling("txt", response, CommonUtil.postMessageView("삭제 실패!", returnParamMap));

			logger.debug("*********** 공지사항 삭제 실패!! ***********");
		}
	}
	
	/**
	 * <pre>
	 * 1. 개요     :	코멘트 리스트 
	 * 2. 처리내용 : 코멘트 리스트를 가져온다.
	 * </pre>
	 * @Method Name : selectComment
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/of/notice/selectComment.do")
	public void selectComment(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		int currentPage = Integer.parseInt(StringUtil.nvl(request.getParameter("currentPage"),"1"));	// 현재 페이지 번호
		int plRowRange = 10;	
		int plPageRange = 10;
		
		String seq = StringUtil.nvl(request.getParameter("seq")); // 게시판 글 seq
		
		Map<String, String> paramMap = new HashMap<String, String>();
		
		paramMap.put("page",String.valueOf(currentPage));		
		paramMap.put("perPageRow", String.valueOf(plRowRange));
		paramMap.put("seq", seq);
		
		int commentCnt = noticeService.getNoticeCommentCount(Integer.parseInt(seq));
		
		List<NoticeCommentVO> noticeCommentList = noticeService.getNoticeCommentList(paramMap);
		
		PageUtil pu = new PageUtil();
		String pagingStr = pu.autoPaging(commentCnt, plRowRange, plPageRange, currentPage);
		
		NoticeCommentVO resultVO = new NoticeCommentVO();
		resultVO.setList(noticeCommentList);
		resultVO.setPagingStr(pagingStr);
		resultVO.setCurrentPage(currentPage);
		resultVO.setCommentCnt(commentCnt);

		MarshallerUtil.marshalling("json", response, resultVO);
	}
	
	/**
	 * <pre>
	 * 1. 개요     :	공지사항 코멘트 등록 
	 * 2. 처리내용 : 공지사항 코멘트 등록을 한다.
	 * </pre>
	 * @Method Name : insertComment
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/of/notice/insertComment.do")
	public void insertComment(HttpServletRequest request, HttpServletResponse response ) 
			throws Exception{
		
		HttpSession session = request.getSession();
		MemberVO memberSessionVO = (MemberVO) session.getAttribute("gwUser");
		
		int result = 0;
		
		int seq = Integer.parseInt(StringUtil.nvl(request.getParameter("seq")));	//공지사항시퀀스
		String comments = StringUtil.nvl(request.getParameter("comments"));			//댓글내용
		
		NoticeCommentVO noticeCmtVO = new NoticeCommentVO();
		noticeCmtVO.setSeq(seq);
		noticeCmtVO.setComments(comments);
		noticeCmtVO.setCreate_no(memberSessionVO.getEmp_no());
		
		if(noticeService.insertComment(noticeCmtVO)){
			result = 1;
		}
		
		noticeCmtVO.setResult(result);
		
		MarshallerUtil.marshalling("json", response, noticeCmtVO);
	}
	
	/**
	 * <pre>
	 * 1. 개요     :	공지사항 코멘드 삭제 
	 * 2. 처리내용 : 공지사항 코멘드를 삭제한다.
	 * </pre>
	 * @Method Name : deleteComment
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/of/notice/deleteComment.do")
	public void deleteComment(HttpServletRequest request, HttpServletResponse response )
			throws Exception{
		
		int result = 0;
		
		int comment_seq = Integer.parseInt(StringUtil.nvl(request.getParameter("comment_seq")));
		
		NoticeCommentVO noticeCmtVO = new NoticeCommentVO();
		noticeCmtVO.setComment_seq(comment_seq);
		noticeCmtVO.setDelete_yn("Y");
		
		if(noticeService.deleteComment(noticeCmtVO)){
			result = 1;
		}
		
		noticeCmtVO.setResult(result);
		
		MarshallerUtil.marshalling("json", response, noticeCmtVO);
	}
}


