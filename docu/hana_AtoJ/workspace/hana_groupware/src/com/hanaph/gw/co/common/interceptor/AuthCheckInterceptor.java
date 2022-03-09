
package com.hanaph.gw.co.common.interceptor;

import com.hanaph.gw.co.common.utils.CommonUtil;
import com.hanaph.gw.co.common.utils.StringUtil;
import com.hanaph.gw.co.common.utils.WebUtil;
import com.hanaph.gw.ea.implement.service.ImplementReportService;
import com.hanaph.gw.ea.receive.service.ReceiveService;
import com.hanaph.gw.of.message.service.MessageService;
import com.hanaph.gw.of.notice.service.NoticeService;
import com.hanaph.gw.pe.member.vo.MemberVO;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.ModelAndViewDefiningException;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * <pre>
 * Class Name : AuthCheckInterceptor.java
 * 설명 : 모든 페이지 체크하는 Interceptor
 * 
 * Modification Information
 *    수정일      수정자              수정 내용
 *  ------------ -------------- --------------------------------
 *  2014. 10. 6.      재가부          
 * </pre>
 * 
 * @version : 
 * @author  : 재가부(@irush.co.kr)
 * @since   : 2014. 10. 6.
 */
public class AuthCheckInterceptor extends HandlerInterceptorAdapter {
	
	private static final Logger logger = Logger.getLogger(AuthCheckInterceptor.class);
	
	@Autowired
	private MessageService messageService;
	
	@Autowired
	private ImplementReportService implementReportService;
	
	@Autowired
	private ReceiveService receiveService;
	
	@Autowired
	private NoticeService noticeService;
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.web.servlet.handler.HandlerInterceptorAdapter#preHandle
	 * (javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse, java.lang.Object)
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		 
		ModelAndView mav = new ModelAndView();
			
		/*로그인 필요 여부 확인 하는 class*/
		NoLoginCheck usingAuth = ((HandlerMethod) handler).getMethodAnnotation(NoLoginCheck.class);
		
		/*NoCheckLogin 어노테이션이 없음으로 무조건 로그인 체크*/
		if (usingAuth == null) {
			/*Session 체크*/
			HttpSession session = request.getSession();
			MemberVO memberSessionVO = (MemberVO) session.getAttribute("gwUser");
			if (session == null || memberSessionVO == null || memberSessionVO.getEmp_no() == null) {
				logger.debug("login fail!");
				if(CommonUtil.isAjaxRequest(request)) {
					response.sendError(HttpServletResponse.SC_FORBIDDEN);
					return false;
				} else {
	        		mav = CommonUtil.getMessageView("로그인 정보가 없습니다.", "Commons.sessionOut();", null);
					throw new ModelAndViewDefiningException(mav);
				}
			} else {
				logger.debug("login sucsess!");
				
				/*헤더 받은쪽지, 문서결재, 참조 문서함 카운트*/
				Map<String, String> paramMap = new HashMap<String, String>();
				paramMap.put("emp_no", memberSessionVO.getEmp_no());
				/*받은쪽지*/
				memberSessionVO.setMessageReceiveCount(messageService.getMessageReceiveCount(paramMap));
				/*시행문서 - 결재중 && 읽지않은문서*/
				paramMap.put("approval_type", "E03001");
				paramMap.put("search_read_yn", "N");
				memberSessionVO.setImplementOngoingCount(implementReportService.getImplementCount(paramMap));

				/*시행문서 - 결재완료 && 읽지않은문서*/
				/* ml180129.ml01_T87 김진국 - AuthCheckInterceptor.java에서 header 부분의 시행완료 카운트를 전자결재-시행문서조회-결재완료 카운트와 동일하게 맞춤 - 헤더와 결재완료 카운트와 맞지 않아서 Start */
				String searchDate[] = WebUtil.dateCal(-2);

				String search_start_dt = StringUtil.nvl((String)request.getParameter("search_start_dt"), searchDate[0]);//기안시작일
				String search_end_dt = StringUtil.nvl((String)request.getParameter("search_end_dt"), searchDate[1]);//기안종료일

				paramMap.put("search_start_dt", search_start_dt.replaceAll("-", ""));
				paramMap.put("search_end_dt", search_end_dt.replaceAll("-", ""));
				/* ml180129.ml01_T87 김진국 - AuthCheckInterceptor.java에서 header 부분의 시행완료 카운트를 전자결재-시행문서조회-결재완료 카운트와 동일하게 맞춤 - 헤더와 결재완료 카운트와 맞지 않아서 End */

				paramMap.put("approval_type", "E03002");
				memberSessionVO.setImplementCompleteCount(implementReportService.getImplementCount(paramMap));
				/*문서결재 카운트*/
				memberSessionVO.setReceiveCount(receiveService.getLongTermReceiveCount(paramMap));
				/*공지*/ 
				paramMap.put("search_noti_kind", "all");
				paramMap.put("search_read_yn", "N");
				memberSessionVO.setNoticeCountNoread(noticeService.getNoticeCountNoread(paramMap));
				
				session.setAttribute("gwUser", memberSessionVO);
				
				/*url 다이렉트 입력 방지*/
				if(request.getHeader("Referer") == null ) { 
					/*mav = CommonUtil.getMessageView("입력을 허용하지 않습니다. 메인 페이지로 이동합니다.", "window.location.href='"+env.getValue("root_dir.url")+"/main/main.do'", null);
					throw new ModelAndViewDefiningException(mav);*/     
				}
			}
		} else {
			logger.debug("not login page!");
		}
		return true;
	}
}
