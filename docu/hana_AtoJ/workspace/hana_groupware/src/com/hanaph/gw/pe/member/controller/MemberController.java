/**
  * Hana Project
  * Copyright 2014 iRush Co.,
  *
  */
package com.hanaph.gw.pe.member.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialException;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.hanaph.gw.co.authority.service.AuthorityService;
import com.hanaph.gw.co.common.utils.MenuUtil;
import com.hanaph.gw.co.common.utils.StringUtil;
import com.hanaph.gw.co.menu.service.MenuService;
import com.hanaph.gw.co.menu.vo.MenuVO;
import com.hanaph.gw.co.personnel.service.DepartmentService;
import com.hanaph.gw.co.personnel.vo.DepartmentVO;
import com.hanaph.gw.ea.share.service.ShareReportService;
import com.hanaph.gw.of.board.service.BoardService;
import com.hanaph.gw.of.message.service.MessageService;
import com.hanaph.gw.of.notice.service.NoticeService;
import com.hanaph.gw.of.appli.service.AppliBusiService;
import com.hanaph.gw.pe.member.service.MemberService;
import com.hanaph.gw.pe.member.vo.MemberVO;

/**
 * <pre>
 * Class Name : MyPageController.java
 * 설명 : 그룹웨어는 로그인관리 하는 테이블과 회원 정보의 테이블이 분리 되어 있어 두개의 class 로 관리 한다. 회원정보 Controller
 * 
 * Modification Information
 *    수정일      수정자              수정 내용
 *  ------------ -------------- --------------------------------
 *  2014. 10. 16.      CHOIILJI         
 * </pre>
 * 
 * @version : 
 * @author  : CHOIILJI(choiilji@irush.co.kr)
 * @since   : 2014. 10. 16.
 */
@Controller
public class MemberController {

	@Autowired
	private MemberService  memberService;
	
	@Autowired
	private MenuService menuService;
	
	@Autowired
	private DepartmentService departmentService;
	
	@Autowired
	private AuthorityService authorityService;
	
	@Autowired
	private NoticeService noticeService;
	
	@Autowired
	private BoardService boardService;
	
	@Autowired
	private MessageService messageService;
	
	@Autowired
	private AppliBusiService applibusiService;
	
	@Autowired
	private ShareReportService shareReportService;
	/**
	 * 
	 * <pre>
	 * 1. 개요     : 회원정보 팝업 
	 * 2. 처리내용 : 회원 간단정보 팝업가져온다.
	 * </pre>
	 * @Method Name : getMemberPopup
	 * @param request
	 * @return
	 */
	@RequestMapping("/pe/member/memberPopup.do")
	public ModelAndView getMemberPopup(HttpServletRequest request){
		
		ModelAndView mav = new ModelAndView("pe/member/memberPopup");
		Map<String, String> paramMap = new HashMap<String, String>(); 
		
		String emp_no = StringUtil.nvl(request.getParameter("emp_no"));
		
		paramMap.put("emp_no", emp_no);
		MemberVO memberDetail = memberService.getMemberDetail(paramMap);
		if(memberDetail == null){
			memberDetail = new MemberVO();
		}
		mav.addObject("memberDetail", memberDetail);
        return mav;
	}
	
	/**
	 * 
	 * <pre>
	 * 1. 개요     : 임직원 선택 팝업.
	 * 2. 처리내용 : 임직원 선택 팝업.
	 * </pre>
	 * @Method Name : getMemberSelectPopup
	 * @param request
	 * @return
	 */
	@RequestMapping("/pe/member/memberSelectPopup.do")
	public ModelAndView getMemberSelectPopup(HttpServletRequest request){
		
		ModelAndView mav = new ModelAndView("pe/member/memberSelectPopup");
		Map<String, String> paramMap = new HashMap<String, String>();  
		
		String seq = StringUtil.nvl(request.getParameter("seq"));
		String type = StringUtil.nvl(request.getParameter("type"));
		
		List<DepartmentVO> departmentList = departmentService.getDepartmentList(paramMap);
		
		mav.addObject("seq", seq);
		mav.addObject("type", type);
		mav.addObject("departmentList", departmentList);
		
        return mav;
	}
	
	/**
	 * 
	 * <pre>
	 * 1. 개요     : 회원정보 리스트 
	 * 2. 처리내용 : 부서코드별 회원정보 리스트 가져온다.
	 * </pre>
	 * @Method Name : getMemberSelectMember
	 * @param request
	 * @return
	 */
	@RequestMapping("/pe/member/memberSelectMemberIframe.do")
	public ModelAndView getMemberSelectMember(HttpServletRequest request){
		
		ModelAndView mav = new ModelAndView("pe/member/memberSelectMemberIframe");
		
		HttpSession session = request.getSession(false);
		MemberVO memberVO = (MemberVO) session.getAttribute("gwUser");
		
		Map<String, String> paramMap = new HashMap<String, String>();
		
		String dept_cd = StringUtil.nvl(request.getParameter("dept_cd"), "00000");
		String up_dept_gbn = StringUtil.nvl(request.getParameter("up_dept_gbn"));
		String emp_ko_nm = StringUtil.nvl(request.getParameter("emp_ko_nm"));
		//대상타겟 구분 - AUTH, NOTICE, BOARD, MESSAGE
		String type = StringUtil.nvl(request.getParameter("type"));
		String seq = StringUtil.nvl(request.getParameter("seq"));
		//성명 검색시 적용 안함
		if(emp_ko_nm.equals("") || emp_ko_nm == null){
			//하위 조직이 없는 조직은 넘어온 dept_cd 로만 사용자를 검색 하고 하위 조직이 있는 조직은 하위의 조직까지 검색 한다. 
			if(up_dept_gbn.equals("1")){
				paramMap.put("up_dept_cd", "");
				paramMap.put("dept_cd", dept_cd);
			}else{
				paramMap.put("up_dept_cd", dept_cd);
				paramMap.put("dept_cd", "");
			}
		}else{
			paramMap.put("emp_ko_nm", emp_ko_nm);
		}
		
		paramMap.put("type", type);
		paramMap.put("seq", seq);
		paramMap.put("login_emp_no", memberVO.getEmp_no());
		
		List<MemberVO> memberList = memberService.getMemberList(paramMap);
		mav.addObject("memberList", memberList);
		mav.addObject("type", type);
		mav.addObject("seq", seq);
		mav.addObject("emp_ko_nm", emp_ko_nm);
		
		
        return mav;
	}
	
	/**
	 * 
	 * <pre>
	 * 1. 개요     : 선택한 회원정보를 담을 form
	 * 2. 처리내용 :
	 * </pre>
	 * @Method Name : getMemberSelectTarget
	 * @param request
	 * @return
	 */
	@RequestMapping("/pe/member/memberSelectMemberTargetIframe.do")
	public ModelAndView getMemberSelectTarget(HttpServletRequest request){
		
		ModelAndView mav = new ModelAndView("pe/member/memberSelectMemberTargetIframe");
		
		Map<String, String> paramMap = new HashMap<String, String>();  
		Map<String, Object> paramMap1 = new HashMap<String, Object>();  

		//대상타겟 구분 - AUTH, NOTICE, BOARD, MESSAGE
		String type = StringUtil.nvl(request.getParameter("type"));
		String seq = StringUtil.nvl(request.getParameter("seq"));
		//System.out.println("--- MemberController memberSelectMemberTargetIframe TYPE :" + type +" SEQ :"+seq);  //정상값 나옴
		
		List<MemberVO> memberTargetList = null;
		if(type.equals("AUTH")){
			int [] authIdxArray = {Integer.parseInt(seq)};
			paramMap1.put("authIdxArray", authIdxArray);
			memberTargetList = authorityService.getAuthorityMemberList(paramMap1);
		}else if(type.equals("NOTICE")){
			paramMap.put("notice_seq", seq);
			memberTargetList = noticeService.getNoticeMemberList(paramMap);
		}else if(type.equals("BOARD")){
			paramMap.put("board_seq", seq);
			memberTargetList = boardService.getBoardMemberList(paramMap);
		}else if(type.equals("MESSAGE") || type.equals("MESSAGE_DELIVER")){
			paramMap.put("message_seq", seq);
			memberTargetList = messageService.getReceiveEmpNO(paramMap);
		//CHOE 20160510 쪽지 답장 버튼 누르면 원본 보낸 사람을 찾아서 쪽지 대상자로 넣어 준다. 
		}else if(type.equals("MESSAGE_REPLY")){			
			paramMap.put("message_seq", seq);
			memberTargetList = messageService.getReplyEmpNO(paramMap);
		}else if(type.equals("SHARE")){
			paramMap.put("approval_seq", seq);
			memberTargetList = shareReportService.getShareTargetList(paramMap);
		//CHOE 20161108 명함신청서 로그인 한 사람의 EMP_NO를 넣는다.	
		}else if(type.equals("BUSI")){
			//세션에서 회원정보 가져 온다.
			//HttpSession session = request.getSession(false);
			//MemberVO memberVO = (MemberVO) session.getAttribute("gwUser");
			
			//paramMap.put("busi_emp_nm", StringUtil.nvl(memberVO.getEmp_no()));			
			//memberTargetList = "";			
		}
		
		mav.addObject("type", type);
		mav.addObject("memberTargetList", memberTargetList);
		
        return mav;
	}
	
	/**
	 * <pre>
	 * 1. 개요     :	대상자 리스트 
	 * 2. 처리내용 : 공지사항,게시판,쪽지 대상자 목록을 가져온다.
	 * </pre>
	 * @Method Name : getMemberListPopup
	 * @param request
	 * @return
	 */
	@RequestMapping("/pe/member/memberTargetListPopup.do")
	public ModelAndView getMemberListPopup(HttpServletRequest request){
		
		ModelAndView mav = new ModelAndView("pe/member/memberTargetListPopup");
		
		Map<String, String> paramMap = new HashMap<String, String>();  
		
		//대상타겟 구분 - AUTH, NOTICE, BOARD, MESSAGE
		String type = StringUtil.nvl(request.getParameter("type"));
		String seq = StringUtil.nvl(request.getParameter("seq"));
		
		List<MemberVO> memberTargetList = null;
		if(type.equals("NOTICE")){
			paramMap.put("notice_seq", seq);
			memberTargetList = noticeService.getNoticeMemberList(paramMap);
		}else if(type.equals("BOARD")){
			paramMap.put("board_seq", seq);
			memberTargetList = boardService.getBoardMemberList(paramMap);
		}else if(type.equals("MESSAGE") || type.equals("MESSAGE_DELIVER")){
			paramMap.put("message_seq", seq);
			memberTargetList = messageService.getReceiveEmpNO(paramMap);
		//CHOE 20161108 명함신청 정보 전달
		}else if(type.equals("BUSI")){
			//세션에서 회원정보 가져 온다.
			HttpSession session = request.getSession(false);
			MemberVO memberVO = (MemberVO) session.getAttribute("gwUser");
			
			paramMap.put("busi_emp_nm", StringUtil.nvl(memberVO.getEmp_no()));
			
		}
				
		mav.addObject("memberTargetList", memberTargetList);
		
        return mav;
		
	}
	
	/**
	 * 
	 * <pre>
	 * 1. 개요     : 회원정보 리스트 
	 * 2. 처리내용 : 전체 회원정보 리스트 가져온다.
	 * </pre>
	 * @Method Name : getDeptMemberListPopup
	 * @param request
	 * @return
	 */
	@RequestMapping("/pe/member/memberListPopup.do")
	public ModelAndView getDeptMemberListPopup(HttpServletRequest request){
		
		ModelAndView mav = new ModelAndView("pe/member/memberListPopup");
		Map<String, String> paramMap = new HashMap<String, String>();  
		String dept_cd = "0000";
		String emp_ko_nm = StringUtil.nvl(request.getParameter("emp_ko_nm"));
		String target = StringUtil.nvl(request.getParameter("target"));
		
		//성명 검색시 적용 안함
		if(emp_ko_nm.equals("") || emp_ko_nm == null){
			paramMap.put("up_dept_cd", dept_cd);
		}else{
			paramMap.put("emp_ko_nm", emp_ko_nm);
		}
		
		List<MemberVO> memberList = memberService.getMemberList(paramMap);
		mav.addObject("memberList", memberList);
		mav.addObject("emp_ko_nm", emp_ko_nm);
		mav.addObject("target", target);
        return mav;
	}
	
	/**
	 * 
	 * <pre>
	 * 1. 개요     : 회원정보 리스트 
	 * 2. 처리내용 : 회원상세정보 가져온다.
	 * </pre>
	 * @Method Name : getMemberDeatil
	 * @param request
	 * @return
	 */
	@RequestMapping("/pe/member/memberDetail.do")
	public ModelAndView getMemberDeatil(HttpServletRequest request){
		
		final String MENU_CHILD= "0401";
		
		ModelAndView mav = new ModelAndView("pe/member/memberDetail");
		Map<String, String> paramMap = new HashMap<String, String>(); 
		//세션에서 회원정보 가져 온다.
		HttpSession session = request.getSession(false);
		MemberVO memberVO = (MemberVO) session.getAttribute("gwUser");
		
		paramMap.put("emp_no", StringUtil.nvl(memberVO.getEmp_no()));
		MemberVO memberDetail = memberService.getMemberDetail(paramMap);
		if(memberDetail == null){
			memberDetail = new MemberVO();
		}
		mav.addObject("memberDetail", memberDetail);
		
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
	 * 
	 * <pre>
	 * 1. 개요     : Blob 사진정보 가졍온다.
	 * 2. 처리내용 :
	 * </pre>
	 * @Method Name : memberPhoto
	 * @param emp_no
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws SerialException
	 * @throws SQLException
	 */
	@RequestMapping(value ="/pe/member/memberPhoto/{emp_no}.do", method = RequestMethod.GET)
	public void memberPhoto(@PathVariable("emp_no") String emp_no, HttpServletRequest request, HttpServletResponse response) throws IOException, SerialException, SQLException {
		

		Map<String, String> paramMap = new HashMap<String, String>(); 
		
		OutputStream outputStream;
		
		paramMap.put("emp_no", emp_no);
		MemberVO memberVO = memberService.getMemberPhoto(paramMap);

		if(memberVO != null && memberVO.getPhoto1_nm() != null){
		
			response.setHeader("Content-Disposition", "inline;filename=\"" + memberVO.getPhoto1_nm() + "\""); 
			outputStream = response.getOutputStream(); 
			
			response.setContentType("image/jpeg"); 
	
			SerialBlob blob = new SerialBlob(memberVO.getPhoto1()); 
	
			IOUtils.copy(blob.getBinaryStream(), outputStream); 
	
			outputStream.flush(); 
			outputStream.close();
		}
	}

	/**
	 * ml180125.ml02_T79 김진국 - MemberController.java에 전 쪽지 수보다 현 쪽지수가 더 많으면 팝업 알림 beforeMessageReceiveCount 메서드 추가 - '시의필' 실행하면 실시간으로 쪽지수가 증가되어 확인 바라는 알림창 띄우기 위해서!
	 * @param httpServletRequest header.jsp에서 HttpServletRequest.getParameter함
	 * @throws Exception
	 */
	@RequestMapping(value="/pe/member/beforeMessageReceiveCount.do")
	public void beforeMessageReceiveCount (HttpServletRequest httpServletRequest) throws Exception {

		String count = StringUtil.nvl(httpServletRequest.getParameter("checkMessageCnt"));

		HttpSession session = httpServletRequest.getSession();
		MemberVO memberVO = (MemberVO) session.getAttribute("gwUser");

		memberVO.setBeforeMessageReceiveCount(Integer.parseInt(count));
	}

}
