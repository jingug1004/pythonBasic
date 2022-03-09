/**
 * Hana Project
 * Copyright 2014 iRush Co.,
 *
 */
package com.hanaph.gw.of.board.controller;

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
import com.hanaph.gw.of.board.service.BoardService;
import com.hanaph.gw.of.board.vo.BoardCommentVO;
import com.hanaph.gw.of.board.vo.BoardTargetVO;
import com.hanaph.gw.of.board.vo.BoardVO;
import com.hanaph.gw.pe.member.vo.MemberVO;

/**
 * <pre>
 * Class Name : BoardController.java
 * 설명 : 게시판 Controller
 * 
 * Modification Information
 *    수정일      수정자              수정 내용
 *  ------------ -------------- --------------------------------
 *  2014. 10. 6.      재가부        게시판 클래스 신규 생성
 *  2014. 10.25.      우정아		  게시판 등록 기능 생성
 * </pre>
 * 
 * 
 * 
 * @version :
 * @author : 재가부(@irush.co.kr)
 * @since : 2014. 10. 6.
 */
@Controller
public class BoardController{

	@Autowired
	private BoardService boardService;

	@Autowired
	private CodeService codeService;

	@Autowired
	private FileAttachService fileAttachService;
	
	@Autowired
	private MenuService menuService;
	
	@Autowired
	private AuthorityService authorityService;

	private static final Logger logger = Logger.getLogger(BoardController.class);
	private Environment env = new Environment();
	

	/**
	 * <pre>
	 * 1. 개요     : 게시판 글 목록
	 * 2. 처리내용 :
	 * </pre>
	 * 
	 * @Method Name : boardList
	 * @param request
	 * @return ModelAndView List boardList
	 */
	@RequestMapping("/of/board/boardList.do")
	public ModelAndView boardList(HttpServletRequest request, HttpServletResponse response) {
		
		ModelAndView mav = new ModelAndView("of/board/boardList");
		
		HttpSession session = request.getSession();
		MemberVO memberSessionVO = (MemberVO) session.getAttribute("gwUser");
		
		/******* LNB start *******/
		final String MENU_CHILD= "0102";
		//LNB 메뉴 생성
		String menu_parents = StringUtil.substring(MENU_CHILD, 2);
		MenuUtil mu = new MenuUtil();
		List<MenuVO> lnbMenuList = mu.makeLnb(request, menu_parents , menuService);
		mav.addObject("lnbMenuList", lnbMenuList);
		mav.addObject("MENU_CHILD", MENU_CHILD);
		/******* LNB end *******/
		
		/*게시판 유형*/
		String cd = StringUtil.nvl(request.getParameter("cd"),"O02001");

		/*게시판 유형 목록을 가져오기 위한 parameter*/
		Map<String, String> codeParmaMap = new HashMap<String, String>();
		codeParmaMap.put("m_cd", "O02");
		
		List<CodeVO> codeList = codeService.getCodeList(codeParmaMap);

		/*검색어 parameter*/
		//String searchDate[] = WebUtil.dateCal(-2); //CHOE 20150903 1년 단위 조회함
		String searchDate[] = WebUtil.NewYearDate();
		String searchType = StringUtil.nvl(request.getParameter("searchType"),"subject");
		String searchKeyword = StringUtil.nvl(request.getParameter("searchKeyword"));
		String search_start_ymd = StringUtil.nvl(request.getParameter("search_start_ymd"),searchDate[0]);
		String search_end_ymd = StringUtil.nvl(request.getParameter("search_end_ymd"),searchDate[1]);
		
		/* 
		cnt : 게시글 전체 카운트 수
		plRowRange : list에 보여질 row갯수
		plPageRange : list에 보여질 page 수
		currentPage : 현재 페이지 번호
		*/
		int currentPage = Integer.parseInt(StringUtil.nvl(request.getParameter("currentPage"), "1"));
		int plRowRange = 10;
		int plPageRange = 10;
		
		Map<String, String> paramMap = new HashMap<String, String>();

		paramMap.put("searchType", searchType);
		paramMap.put("searchKeyword", searchKeyword);
		paramMap.put("search_start_ymd", search_start_ymd);
		paramMap.put("search_end_ymd", search_end_ymd);
		paramMap.put("delete_yn", "N");
		paramMap.put("page", String.valueOf(currentPage));
		paramMap.put("perPageRow", String.valueOf(plRowRange));
		paramMap.put("boardType", cd);
		paramMap.put("emp_no", memberSessionVO.getEmp_no());
		
		/*게시글 총 갯수*/
		int cnt = boardService.getBoardCount(paramMap);
		List<BoardVO> boardList = boardService.getBoardList(paramMap);
		PageUtil pu = new PageUtil();

		/*paging 처리를 위한 string set*/
		String pagingStr = pu.autoPaging(cnt, plRowRange, plPageRange, currentPage);
		
		/*관리자 권한 리스트 가져오기*/
		int [] authIdxArray = {1,2};
		Map<String, Object> paramMap1 = new HashMap<String, Object>();
		paramMap1.put("authIdxArray", authIdxArray);
		List<MemberVO> authorityMemberList = authorityService.getAuthorityMemberList(paramMap1);

		/*page string set*/
		mav.addObject("pagingStr", pagingStr);
		mav.addObject("boardList", boardList);
		mav.addObject("totalCnt", cnt);
		/*게시판 유형 목록*/
		mav.addObject("codeList", codeList);
		mav.addObject("cd", cd);
		/*검색어 parameter*/
		mav.addObject("searchType", searchType);
		mav.addObject("searchKeyword", searchKeyword);
		mav.addObject("search_start_ymd", search_start_ymd);
		mav.addObject("search_end_ymd", search_end_ymd);
		
		/*권한*/
		mav.addObject("emp_no", memberSessionVO.getEmp_no());
		mav.addObject("authorityMemberList", authorityMemberList);
		
		return mav;
	}

	/**
	 * <pre>
	 * 1. 개요     : 게시판 글 상세
	 * 2. 처리내용 :
	 * </pre>
	 * 
	 * @Method Name : boardDetail
	 * @param request
	 * @return ModelAndView
	 */
	@RequestMapping("/of/board/boardDetail.do")
	public ModelAndView boardDetail(HttpServletRequest request, HttpServletResponse response){
		
		ModelAndView mav = new ModelAndView("of/board/boardDetail");
		
		int currentPage = Integer.parseInt(StringUtil.nvl(request.getParameter("currentPage"),"1"));	// 현재 페이지 번호
		
		/******* LNB start *******/
		final String MENU_CHILD= "0102";
		String menu_parents = StringUtil.substring(MENU_CHILD, 2);
		MenuUtil mu = new MenuUtil();
		List<MenuVO> lnbMenuList = mu.makeLnb(request, menu_parents , menuService);
		mav.addObject("lnbMenuList", lnbMenuList);
		mav.addObject("MENU_CHILD", MENU_CHILD);
		/******* LNB end *******/
		
		HttpSession memberSession = request.getSession();
		MemberVO memberSessionVO = (MemberVO) memberSession.getAttribute("gwUser");
		String seq = StringUtil.nvl(request.getParameter("seq"));
		String emp_no = StringUtil.nvl(memberSessionVO.getEmp_no());
		
		/*검색 파라미터*/
		String searchType = StringUtil.nvl(request.getParameter("searchType"),"subject");
		String searchKeyword = StringUtil.nvl(request.getParameter("searchKeyword"));
		String search_start_ymd = StringUtil.nvl(request.getParameter("search_start_ymd"));
		String search_end_ymd = StringUtil.nvl(request.getParameter("search_end_ymd"));
		String cd = StringUtil.nvl(request.getParameter("cd"),"O02001");
		
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("emp_no", emp_no);
		paramMap.put("seq", seq);
		paramMap.put("cd", request.getParameter("cd"));		//첨부파일목록을 가져오기 위한 정보(글 seq, 메뉴 code)
		paramMap.put("read_yn", "Y");
		
		BoardVO boardDetail = boardService.getBoardDetail(paramMap);
		List<FileAttachVO> attachList = fileAttachService.getAttachList(paramMap);
		
		/*관리자 권한 리스트 가져오기*/
		int [] authIdxArray = {1,2};
		Map<String, Object> paramMap1 = new HashMap<String, Object>();
		paramMap1.put("authIdxArray", authIdxArray);
		List<MemberVO> authorityMemberList = authorityService.getAuthorityMemberList(paramMap1);

		/*글 상세정보*/
		mav.addObject("boardDetail", boardDetail);
		mav.addObject("attachList", attachList);
		mav.addObject("emp_no", emp_no);

		/*검색 파라미터*/
		mav.addObject("searchType", searchType);
		mav.addObject("searchKeyword", searchKeyword);
		mav.addObject("search_start_ymd", search_start_ymd);
		mav.addObject("search_end_ymd", search_end_ymd);
		mav.addObject("currentPage",currentPage);
		mav.addObject("cd", cd);
		
		/*권한*/
		mav.addObject("authorityMemberList", authorityMemberList);
		
        return mav;
		
	}
	
	
	/**
	 * <pre>
	 * 1. 개요     : 코멘트 리스트
	 * 2. 처리내용 : 코멘트 리스트를 ajax로 가져온다.
	 * </pre>
	 * @Method Name : selectComment
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/of/board/selectComment.do")
	public void selectComment(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		int currentPage = Integer.parseInt(StringUtil.nvl(request.getParameter("currentPage"),"1"));	// 현재 페이지 번호
		int plRowRange = 10;	//한페이지당 보여줄 게시물 수
		int plPageRange = 10;	//list에 보여질 row갯수
		
		String seq = StringUtil.nvl(request.getParameter("seq"), ""); // 게시판 글 seq
		
		Map<String, String> paramMap = new HashMap<String, String>();
		
		paramMap.put("page",String.valueOf(currentPage));		
		paramMap.put("perPageRow", String.valueOf(plRowRange));
		paramMap.put("seq", seq);
		
		int commentCnt = boardService.getBoardCommentCount(Integer.parseInt(seq));
		
		PageUtil pu = new PageUtil();
		String pagingStr = pu.autoPaging(commentCnt, plRowRange, plPageRange, currentPage);
		
		BoardCommentVO resultVO = new BoardCommentVO();
		resultVO.setList(boardService.getCommentLsit(paramMap));
		resultVO.setPagingStr(pagingStr);
		resultVO.setCurrentPage(currentPage);
		resultVO.setCommentCnt(commentCnt);

		MarshallerUtil.marshalling("json", response, resultVO);
	}

	/**
	 * <pre>
	 * 1. 개요     : 게시판 글 쓰기 폼
	 * 2. 처리내용 :
	 * </pre>
	 * 
	 * @Method Name : boardInsertForm
	 * @param request
	 * @return ModelAndView
	 */
	@RequestMapping("/of/board/insertBoardForm.do")
	public ModelAndView boardInsertForm(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView mav = new ModelAndView("of/board/boardInsertForm");
		
		/******* LNB start *******/
		final String MENU_CHILD= "0102";
		String menu_parents = StringUtil.substring(MENU_CHILD, 2);
		MenuUtil mu = new MenuUtil();
		List<MenuVO> lnbMenuList = mu.makeLnb(request, menu_parents , menuService);
		mav.addObject("lnbMenuList", lnbMenuList);
		mav.addObject("MENU_CHILD", MENU_CHILD);
		/******* LNB end *******/
		
		Map<String, String> paramMap = new HashMap<String, String>();
		
		String searchDate[] = WebUtil.dateCal(12);  //CHOE 20150903 12개월은 개시함		
		String start_ymd = searchDate[1];
		String end_ymd = searchDate[0];

		/*게시판 유형 목록을 가져오기 위한 parameter*/
		paramMap.put("m_cd", "O02");
		List<CodeVO> codeList = codeService.getCodeList(paramMap);
		mav.addObject("codeList", codeList);

		mav.addObject("start_ymd", start_ymd);
		mav.addObject("end_ymd", end_ymd);
		
		mav.addObject("m_cd", "O02");
		/*답글 등록일 경우 부모글 seq와 level parameter 값을 가져온다*/
		mav.addObject("reply_grp",
				StringUtil.nvl(request.getParameter("reply_grp"), ""));
		mav.addObject("reply_level",
				StringUtil.nvl(request.getParameter("reply_level"), ""));
		/*답글의 경우 게시판 유형을 수정 못하게 parameter 값을 가져간다*/
		mav.addObject("cd", StringUtil.nvl(request.getParameter("cd"), ""));

		return mav;
	}

	/**
	 * 
	 * <pre>
	 * 1. 개요     : 게시판 등록 로직
	 * 2. 처리내용 :
	 * </pre>
	 * 
	 * @Method Name : insertBoard
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws ModelAndViewDefiningException
	 */
	@RequestMapping("/of/board/insertBoard.do")
	protected ModelAndView insertBoard(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ModelAndViewDefiningException{
		
		ModelAndView mav = new ModelAndView("");
		
		String seq = "";
		
		BoardVO paramVO = new BoardVO();
		int level = 0;
		
		HttpSession memberSession = request.getSession();
		MemberVO memberSessionVO = (MemberVO) memberSession.getAttribute("gwUser");

		String reply_level = StringUtil.nvl(request.getParameter("reply_level"), "0");	// 덧글 level parameter
		String target_empNos[] = request.getParameterValues("target_empNos");			// 대상자 리스트
		String reply_grp = ""; 															// 부모 게시글 seq parameter
		String target_empNo = "";
		
		for (int i = 0; i < target_empNos.length; i++) {
			target_empNo += target_empNos[i]+"|";
		}

		/*부모 게시글이 없을 경우*/
		if (reply_level.equals("0")) {
			level = 1;
			reply_grp = "0";
			paramVO.setCd(StringUtil.nvl(request.getParameter("cd"), ""));
		} else {// 부모 게시글 있을 경우
			level = Integer.parseInt(reply_level) + 1;
			reply_grp = StringUtil.nvl(request.getParameter("reply_grp"));
			paramVO.setCd(StringUtil.nvl(request.getParameter("boardType")));
		}
		
		paramVO.setReply_step(1);
		paramVO.setReply_level(level);													// 덧글 level
		paramVO.setReply_grp(Integer.parseInt(reply_grp)); 								// 부모 게시글 seq
		paramVO.setSubject(StringUtil.nvl(request.getParameter("subject"))); 			// 제목
		paramVO.setStart_ymd(StringUtil.nvl(request.getParameter("start_ymd"), "")); 	// 게시 시작일
		paramVO.setEnd_ymd(StringUtil.nvl(request.getParameter("end_ymd"))); 			// 게시
		paramVO.setContents(StringUtil.nvl(request.getParameter("contents"))); 			// 내용
		paramVO.setCreate_no(memberSessionVO.getEmp_no()); 								// 작성자
		paramVO.setRead_cnt(0);															// 조회수
		paramVO.setDelete_yn("N");														// 삭제여부
		
		/*게시판 대상자 데이터*/
		BoardTargetVO boardTargetVO = new BoardTargetVO();
		boardTargetVO.setEmp_no(target_empNo);
		boardTargetVO.setRead_yn("N");

		seq = boardService.insertBoard(paramVO, boardTargetVO);
		String url = "window.location.href='"+env.getValue("root_dir.url")+"/of/board/boardList.do?cd="+paramVO.getCd()+"';";
		
		if(!"".equals(seq)){
			if(!"".equals(StringUtil.nvl(request.getParameter("fileNum")))){
				Map<String, String> paramMap = new HashMap<String, String>(); //파일첨부 파라미터
				paramMap.put("fileNum", StringUtil.nvl(request.getParameter("fileNum")));
				paramMap.put("seq", String.valueOf(seq));
				
				fileAttachService.updateFileAttach(paramMap);;
			}
			
			String delFileSeq = StringUtil.nvl(request.getParameter("delFileSeq"));
			
			if(!"".equals(delFileSeq)){
				fileAttachService.deleteAttach(delFileSeq);
			}
			
			logger.debug("*********** 게시판 등록 성공 ***********");
			mav = CommonUtil.getMessageView("저장 되었습니다.", url , "" );
			throw new ModelAndViewDefiningException(mav);
		}else{
			logger.debug("*********** 게시판 등록 실패!! ***********");
			mav = CommonUtil.getMessageView("등록 실패!", url , "" );
			throw new ModelAndViewDefiningException(mav);
		}
	}
	
	/**
	 * <pre>
	 * 1. 개요     : 게시판 글 수정 폼
	 * 2. 처리내용 :
	 * </pre>
	 * 
	 * @Method Name : boardUpdateForm
	 * @param request
	 * @return ModelAndView
	 */
	@RequestMapping("/of/board/boardUpdateForm.do")
	public ModelAndView updateBoardForm(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView mav = new ModelAndView("of/board/boardUpdateForm");
		
		/******* LNB start *******/
		final String MENU_CHILD= "0102";
		String menu_parents = StringUtil.substring(MENU_CHILD, 2);
		MenuUtil mu = new MenuUtil();
		List<MenuVO> lnbMenuList = mu.makeLnb(request, menu_parents , menuService);
		mav.addObject("lnbMenuList", lnbMenuList);
		mav.addObject("MENU_CHILD", MENU_CHILD);
		/******* LNB end *******/
		
		HttpSession memberSession = request.getSession();
		MemberVO memberSessionVO = (MemberVO) memberSession.getAttribute("gwUser");
		
		String seq = StringUtil.nvl(request.getParameter("seq"));
		String emp_no = StringUtil.nvl(memberSessionVO.getEmp_no());
	
		Map<String, String> paramMap = new HashMap<String, String>();
		
		paramMap.put("emp_no", emp_no);
		/*상세보기 하려는 게시글 seq*/
		paramMap.put("seq", seq);
		
		/*게시판 유형 목록을 가져오기 위한 parameter*/
		paramMap.put("m_cd", "O02");
		List<CodeVO> codeList = codeService.getCodeList(paramMap);
		mav.addObject("codeList", codeList);

		/*첨부파일목록을 가져오기 위한 정보(글 seq, 메뉴 code)*/
		paramMap.put("seq", seq);
		paramMap.put("cd", request.getParameter("cd"));

		/*board seq로 게시글 정보와 첨부파일 목록을 select*/
		BoardVO boardDetail = boardService.getBoardDetail(paramMap);
		List<FileAttachVO> attachList = fileAttachService.getAttachList(paramMap);
		
		/*대상자 가져오기*/
		paramMap.put("board_seq", seq);
		List<MemberVO> memberBoardList = boardService.getBoardMemberList(paramMap);
		
		mav.addObject("m_cd", "O02");//메뉴코드
		mav.addObject("boardDetail", boardDetail);//게시글상세정보
		mav.addObject("attachList", attachList);//첨부파일리스트
		mav.addObject("cd", StringUtil.nvl(request.getParameter("cd")));//게시판타입
		mav.addObject("memberBoardList", memberBoardList);//게시글 보여줄 대상자 리스트
		mav.addObject("search_start_ymd", request.getParameter("search_start_ymd"));//검색시작날짜
		mav.addObject("search_end_ymd", request.getParameter("search_end_ymd"));//검색마지막날짜
		mav.addObject("searchKeyword", request.getParameter("searchKeyword"));//검색키워드
		mav.addObject("searchType", StringUtil.nvl(request.getParameter("searchType"),"subject"));//검색타입

		return mav;

	}
	
	/**
	 * 
	 * <pre>
	 * 1. 개요     : 게시판 수정 로직
	 * 2. 처리내용 :
	 * </pre>
	 * 
	 * @Method Name : updateBoard
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/of/board/updateBoard.do")
	protected void updateBoard(HttpServletRequest request, HttpServletResponse response)
			throws IOException{
		
		boolean result = false;

		HttpSession memberSession = request.getSession();
		MemberVO memberSessionVO = (MemberVO) memberSession.getAttribute("gwUser");
		
		String seq = StringUtil.nvl(request.getParameter("seq"));							//게시글시퀀스
		String cd = StringUtil.nvl(request.getParameter("cd"));								//게시판타입
		String subject = StringUtil.nvl(request.getParameter("subject"));					//제목
		String contents = StringUtil.nvl(request.getParameter("contents"));					//게시내용
		String start_ymd = StringUtil.nvl(request.getParameter("start_ymd"));				//게시글시작날짜
		String end_ymd = StringUtil.nvl(request.getParameter("end_ymd"));					//게시글마지막날짜
		String emp_no = StringUtil.nvl(memberSessionVO.getEmp_no());						//임직원번호
		String currentPage = StringUtil.nvl(request.getParameter("currentPage"));			//현재페이지번호
		String search_start_ymd = StringUtil.nvl(request.getParameter("search_start_ymd"));	//검색시작날짜
		String search_end_ymd = StringUtil.nvl(request.getParameter("search_end_ymd"));		//검색마지막날짜
		String searchKeyword = StringUtil.nvl(request.getParameter("searchKeyword"));		//검색키워드
		String searchType = StringUtil.nvl(request.getParameter("searchType"),"subject"); 	//검색타입
		
		String target_empNos[] = request.getParameterValues("target_empNos");
		String target_empNo = "";
		for (int i = 0; i < target_empNos.length; i++) {
			target_empNo += target_empNos[i]+"|";
		}

		BoardVO paramVO = new BoardVO();
		paramVO.setSeq(seq);
		paramVO.setSubject(subject);
		paramVO.setStart_ymd(start_ymd);
		paramVO.setEnd_ymd(end_ymd);
		paramVO.setContents(contents); 
		paramVO.setModify_no(emp_no);
		
		//게시판 대상자 데이터
		BoardTargetVO boardTargetVO = new BoardTargetVO();
		boardTargetVO.setSeq(Integer.parseInt(seq));
		boardTargetVO.setEmp_no(target_empNo);
		boardTargetVO.setRead_yn("N");

		result = boardService.updateBoard(paramVO, boardTargetVO);
		
		Map<String, String> returnParamMap = new HashMap<String, String>();

		if(result){
			if(!"".equals(StringUtil.nvl(request.getParameter("fileNum")))){
				
				Map<String, String> paramMap = new HashMap<String, String>(); //파일첨부 파라미터
				paramMap.put("fileNum", StringUtil.nvl(request.getParameter("fileNum")));
				paramMap.put("seq", String.valueOf(seq));
				
				fileAttachService.updateFileAttach(paramMap);;
			}
			
			String delFileSeq = StringUtil.nvl(request.getParameter("delFileSeq"));
			
			if(!"".equals(delFileSeq)){
				fileAttachService.deleteAttach(delFileSeq);
			}

			returnParamMap.put("returnURL", env.getValue("root_dir.url")+"/of/board/boardDetail.do");
			returnParamMap.put("seq", seq);
			returnParamMap.put("currentPage", currentPage);
			returnParamMap.put("cd", cd);
			returnParamMap.put("search_start_ymd", search_start_ymd);
			returnParamMap.put("search_end_ymd", search_end_ymd);
			returnParamMap.put("searchKeyword", searchKeyword);
			returnParamMap.put("searchType", searchType);
			
			logger.debug("*********** 게시판 수정 성공 ***********");
			MarshallerUtil.marshalling("txt", response, CommonUtil.postMessageView("수정 되었습니다.", returnParamMap));
		}else{
			logger.debug("*********** 게시판 수정 실패!! ***********");
			MarshallerUtil.marshalling("txt", response, CommonUtil.postMessageView("수정 실패!!", returnParamMap));
		}

	}

	/**
	 * 
	 * <pre>
	 * 1. 개요     : 게시판 삭제 로직
	 * 2. 처리내용 : 테이블 DELETE_YN 컬럼값을 'Y'로 업데이트 한다.
	 * </pre>
	 * 
	 * @Method Name : deleteBoard
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/of/board/deleteBoard.do")
	protected void deleteBoard(HttpServletRequest request, HttpServletResponse response)
			throws IOException{

		boolean result = false;
		
		String seq = StringUtil.nvl(request.getParameter("seq"));								//게시글시퀀스
		String cd = StringUtil.nvl(request.getParameter("cd"));									//게시판타입
		String searchType = StringUtil.nvl(request.getParameter("searchType"),"subject");		//검색타입
		String search_start_ymd = StringUtil.nvl(request.getParameter("search_start_ymd"));		//시작날짜
		String search_end_ymd = StringUtil.nvl(request.getParameter("search_end_ymd"));			//마지막날짜
		
		int currentPage = Integer.parseInt(StringUtil.nvl(request.getParameter("currentPage"),"1"));	// 현재 페이지 번호
		
		HttpSession session = request.getSession();
		MemberVO memberSessionVO = (MemberVO) session.getAttribute("gwUser");
		
		BoardVO paramVO = new BoardVO();
		paramVO.setSeq(seq);
		paramVO.setDelete_yn("Y");
		paramVO.setCd(cd);
		paramVO.setModify_no(memberSessionVO.getEmp_no());
		
		result = boardService.deleteBoard(paramVO);
		
		Map<String, String> returnParamMap = new HashMap<String, String>();
		
		returnParamMap.put("returnURL", env.getValue("root_dir.url")+"/of/board/boardList.do");
		returnParamMap.put("seq", seq);
		returnParamMap.put("page",String.valueOf(currentPage));
		returnParamMap.put("cd", cd);
		returnParamMap.put("search_start_ymd", search_start_ymd);
		returnParamMap.put("search_end_ymd", search_end_ymd);
		returnParamMap.put("searchType", searchType);
		
		if(result){
			logger.debug("*********** 게시글 삭제 성공 ***********");
			MarshallerUtil.marshalling("txt", response, CommonUtil.postMessageView("삭제 되었습니다.", returnParamMap));
		}else{
			logger.debug("*********** 게시글 삭제 실패!! ***********");
			MarshallerUtil.marshalling("txt", response, CommonUtil.postMessageView("삭제 실패", returnParamMap));
		}
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
	@RequestMapping("/of/board/insertComment.do")
	public void insertComment(HttpServletRequest request, HttpServletResponse response )
			throws Exception{
		
		HttpSession session = request.getSession();
		MemberVO memberSessionVO = (MemberVO) session.getAttribute("gwUser");
		
		int result = 0;
		
		int seq = Integer.parseInt(StringUtil.nvl(request.getParameter("seq")));	//게시글시퀀스
		String comments = StringUtil.nvl(request.getParameter("comments"));			//댓글내용
		
		BoardCommentVO boardCmtVO = new BoardCommentVO();
		boardCmtVO.setSeq(seq);
		boardCmtVO.setComments(comments);
		boardCmtVO.setCreate_no(memberSessionVO.getEmp_no());
		
		if(boardService.insertComment(boardCmtVO)){
			result = 1;
		}
		
		boardCmtVO.setResult(result);
		
		MarshallerUtil.marshalling("json", response, boardCmtVO);

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
	@RequestMapping("/of/board/deleteComment.do")
	public void deleteComment(HttpServletRequest request, HttpServletResponse response )
			throws Exception{
		
		int result = 0;
		int comment_seq = Integer.parseInt(StringUtil.nvl(request.getParameter("comment_seq")));
		
		BoardCommentVO boardCmtVO = new BoardCommentVO();
		boardCmtVO.setComment_seq(comment_seq);
		boardCmtVO.setDelete_yn("Y");
		
		if(boardService.deleteComment(boardCmtVO)){
			result = 1;	
		}
		boardCmtVO.setResult(result);
		
		MarshallerUtil.marshalling("json", response, boardCmtVO);
	}
}
