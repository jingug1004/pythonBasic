/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: Apache Tomcat/7.0.55
 * Generated at: 2015-12-17 07:43:52 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.WEB_002dINF.jsps.ea.report;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.util.List;
import com.hanaph.gw.co.common.utils.StringUtil;
import com.hanaph.gw.co.common.utils.RequestFilterUtil;
import com.hanaph.gw.ea.report.vo.ReportVO;
import com.hanaph.gw.co.code.vo.CodeVO;
import com.hanaph.gw.pe.member.vo.MemberVO;
import javax.servlet.http.HttpSession;
import com.hanaph.gw.pe.member.vo.MemberVO;
import java.util.List;
import com.hanaph.gw.co.menu.vo.MenuVO;
import java.util.ArrayList;
import com.hanaph.gw.co.menu.vo.MenuVO;
import com.hanaph.gw.co.common.utils.StringUtil;
import java.util.List;

public final class reportList_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final javax.servlet.jsp.JspFactory _jspxFactory =
          javax.servlet.jsp.JspFactory.getDefaultFactory();

  private static java.util.Map<java.lang.String,java.lang.Long> _jspx_dependants;

  static {
    _jspx_dependants = new java.util.HashMap<java.lang.String,java.lang.Long>(8);
    _jspx_dependants.put("/include/head.jsp", Long.valueOf(1424677116691L));
    _jspx_dependants.put("/common/path.jsp", Long.valueOf(1429838623000L));
    _jspx_dependants.put("/include/footer.jsp", Long.valueOf(1418202584955L));
    _jspx_dependants.put("/include/lnb.jsp", Long.valueOf(1444971460463L));
    _jspx_dependants.put("/include/gnb.jsp", Long.valueOf(1444976197526L));
    _jspx_dependants.put("/include/header.jsp", Long.valueOf(1434416046480L));
    _jspx_dependants.put("/common/paging.jsp", Long.valueOf(1418692288909L));
    _jspx_dependants.put("/include/location.jsp", Long.valueOf(1418692302668L));
  }

  private javax.el.ExpressionFactory _el_expressionfactory;
  private org.apache.tomcat.InstanceManager _jsp_instancemanager;

  public java.util.Map<java.lang.String,java.lang.Long> getDependants() {
    return _jspx_dependants;
  }

  public void _jspInit() {
    _el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
    _jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
  }

  public void _jspDestroy() {
  }

  public void _jspService(final javax.servlet.http.HttpServletRequest request, final javax.servlet.http.HttpServletResponse response)
        throws java.io.IOException, javax.servlet.ServletException {

    final javax.servlet.jsp.PageContext pageContext;
    javax.servlet.http.HttpSession session = null;
    final javax.servlet.ServletContext application;
    final javax.servlet.ServletConfig config;
    javax.servlet.jsp.JspWriter out = null;
    final java.lang.Object page = this;
    javax.servlet.jsp.JspWriter _jspx_out = null;
    javax.servlet.jsp.PageContext _jspx_page_context = null;


    try {
      response.setContentType("text/html; charset=utf-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write('\r');
      out.write('\n');
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
com.hanaph.gw.co.common.utils.Environment env = new com.hanaph.gw.co.common.utils.Environment();
	
	String HTTPS_GROUP_WEB_ROOT = env.getValue("https.web_root_dir.url");
	String HTTPS_GROUP_ROOT = env.getValue("https.root_dir.url");
	String GROUP_WEB_ROOT = env.getValue("web_root_dir.url");
	String GROUP_ROOT = env.getValue("root_dir.url");
	String JDBC_URL = env.getValue("jdbc.url");
	
	
      out.write("\r\n");
      out.write("\r\n");

	List<ReportVO> reportList = (List<ReportVO>)request.getAttribute("reportList");	//내가올린문서 리스트
	List<CodeVO> docuKindList = (List<CodeVO>)request.getAttribute("docuKindList");	//문서종류
	List<CodeVO> docuStateList = (List<CodeVO>)request.getAttribute("docuStateList"); //문서상태
	
	String search_docu_kind = (String)request.getAttribute("search_docu_kind"); //문서 종류 검색
	String search_docu_state = (String)request.getAttribute("search_docu_state"); //문서 상태 검색
	String searchType = StringUtil.nvl((String)request.getAttribute("searchType"),"approval_seq"); //검색 타입
	String searchKeyword = (String)request.getAttribute("searchKeyword"); //검색 키워드
	String search_start_ymd = StringUtil.nvl((String)request.getAttribute("search_start_ymd")); //기안 시작 일
	String search_end_ymd = StringUtil.nvl((String)request.getAttribute("search_end_ymd")); //기안 마지막 일
	int cnt = ((Integer)request.getAttribute("cnt")).intValue(); //내가올린문서 리스트
	
	searchKeyword = StringUtil.nvl(RequestFilterUtil.toHtmlTagReplace("", searchKeyword));


      out.write("\r\n");
      out.write("<!DOCTYPE html>\r\n");
      out.write("<html lang=\"ko\">\r\n");
      out.write("<head>\r\n");
      out.write("\t");
      out.write("\r\n");
      out.write("  \r\n");
      out.write("  \r\n");
      out.write("\r\n");
      out.write("\t<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">\r\n");
      out.write("\t<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge,chrome=1\">\r\n");
      out.write("\t\r\n");
      out.write("\t<title>하나제약 그룹웨어</title>\r\n");
      out.write("\r\n");
      out.write("\t<link rel=\"stylesheet\" type=\"text/css\" href=\"");
      out.print(GROUP_WEB_ROOT);
      out.write("/css/main.css\" />\r\n");
      out.write("\t<link rel=\"stylesheet\" type=\"text/css\" href=\"");
      out.print(GROUP_WEB_ROOT);
      out.write("/css/fonts.css\" />\r\n");
      out.write("\t<link rel=\"stylesheet\" type=\"text/css\" href=\"");
      out.print(GROUP_WEB_ROOT);
      out.write("/css/dtree.css\" />\r\n");
      out.write("\t<link rel=\"stylesheet\" type=\"text/css\" href=\"");
      out.print(GROUP_WEB_ROOT);
      out.write("/css/smoothness/jquery-ui-1.10.4.custom.css\" />\r\n");
      out.write("\t<script type=\"text/javascript\" src=\"");
      out.print(GROUP_WEB_ROOT);
      out.write("/js/jquery-1.11.1.min.js\"></script>\r\n");
      out.write("\t<script type=\"text/javascript\" src=\"");
      out.print(GROUP_WEB_ROOT);
      out.write("/js/jquery-ui-1.10.4.custom.js\"></script>\r\n");
      out.write("\t<script type=\"text/javascript\" src=\"");
      out.print(GROUP_WEB_ROOT);
      out.write("/js/jquery.bpopup.min.js\"></script>\r\n");
      out.write("\t<script type=\"text/javascript\" src=\"");
      out.print(GROUP_WEB_ROOT);
      out.write("/js/default.js\"></script>\r\n");
      out.write("\t<script type=\"text/javascript\" src=\"");
      out.print(GROUP_WEB_ROOT);
      out.write("/js/common.js\"></script>\r\n");
      out.write("\t<script type=\"text/javascript\" src=\"");
      out.print(GROUP_WEB_ROOT);
      out.write("/js/dtree.js\" ></script>\r\n");
      out.write("\t<script type=\"text/javascript\" src=\"");
      out.print(GROUP_WEB_ROOT);
      out.write("/js/formCheck.js\" ></script>\r\n");
      out.write("\t\r\n");
      out.write("\r\n");
      out.write("\t<script type=\"text/javascript\">\r\n");
      out.write("\t\t$(window).load(function() {\r\n");
      out.write("\t\t\t$(\"#searchType > option[value=");
      out.print(searchType);
      out.write("]\").attr(\"selected\",\"true\");\r\n");
      out.write("\t\t\t$(\"#search_docu_state > option[value=");
      out.print(search_docu_state);
      out.write("]\").attr(\"selected\",\"true\");\r\n");
      out.write("\t\t\t$(\"#search_docu_kind > option[value=");
      out.print(search_docu_kind);
      out.write("]\").attr(\"selected\",\"true\");\r\n");
      out.write("\t\t});\r\n");
      out.write("\t\t\r\n");
      out.write("\t\t/**\r\n");
      out.write("\t\t * 검색\r\n");
      out.write("\t\t */\r\n");
      out.write("\t\tfunction goSearch(){\r\n");
      out.write("\t\t\tif(formCheck.containsChars(document.getElementById(\"searchKeyword\").value, \"%\")){\r\n");
      out.write("\t\t\t\t alert(\"특수문자를 사용할수 없습니다\");\r\n");
      out.write("\t\t\t\t return;\r\n");
      out.write("\t\t\t}\r\n");
      out.write("\t\t\t\r\n");
      out.write("\t\t\tif(formCheck.dateChk($(\"#search_start_ymd\").val(),$(\"#search_end_ymd\").val())<0){\r\n");
      out.write("\t\t\t\talert(\"작성일 시작 날짜가 작성일 종료 날짜 보다 이후 입니다.\");\r\n");
      out.write("\t\t\t\tsearch_start_ymd.focus();\r\n");
      out.write("\t\t\t\treturn\r\n");
      out.write("\t\t\t}\r\n");
      out.write("\t\t\tdocument.searchForm.action = \"");
      out.print(GROUP_ROOT);
      out.write("/ea/report/reportList.do\";\r\n");
      out.write("\t\t\tdocument.searchForm.submit();\r\n");
      out.write("\t\t}\r\n");
      out.write("\t\t\r\n");
      out.write("\t\t/**\r\n");
      out.write("\t\t * 결재 상세 팝업\r\n");
      out.write("\t\t * @param approval_seq\r\n");
      out.write("\t\t */\r\n");
      out.write("\t\tfunction goApprovalPopup(approval_seq){\r\n");
      out.write("\t\t\tvar height = 0;\r\n");
      out.write("\t\t\tif(850 < $(window).innerHeight()){\r\n");
      out.write("\t\t\t\theight = 850; \r\n");
      out.write("\t\t\t}else{\r\n");
      out.write("\t\t\t\theight = $(window).innerHeight();\r\n");
      out.write("\t\t\t}\r\n");
      out.write("\t\t\tCommons.popupOpen('appPop','");
      out.print(GROUP_ROOT);
      out.write("/ea/newReport/approvalPopup.do?approval_seq='+approval_seq+'&menu=0202','820',height);\r\n");
      out.write("\t\t}\r\n");
      out.write("\t\t\r\n");
      out.write("\t\t/**\r\n");
      out.write("\t\t * 스텝3 팝업\r\n");
      out.write("\t\t * @param approval_seq\r\n");
      out.write("\t\t * @param docu_seq\r\n");
      out.write("\t\t */\r\n");
      out.write("\t\tfunction goStep3Pop(approval_seq, docu_seq){\r\n");
      out.write("\t\t\tCommons.popupOpen('step3Pop','");
      out.print(GROUP_ROOT);
      out.write("/ea/newReport/step3approvalPopup.do?approval_seq='+approval_seq+'&menu=0202&docu_seq='+docu_seq,'820','850');\r\n");
      out.write("\t\t}\r\n");
      out.write("\t\t\r\n");
      out.write("\t\t/**\r\n");
      out.write("\t\t * 스텝2 팝업\r\n");
      out.write("\t\t * @param approval_seq\r\n");
      out.write("\t\t * @param docu_seq\r\n");
      out.write("\t\t * @param docu_cd\r\n");
      out.write("\t\t */\r\n");
      out.write("\t\tfunction goStep2Pop(approval_seq, docu_seq, docu_cd){\r\n");
      out.write("\t\t\tCommons.popupOpen('step3Pop','");
      out.print(GROUP_ROOT);
      out.write("/ea/newReport/step2approvalPopup.do?approval_seq='+approval_seq+'&docu_cd='+docu_cd+'&docu_seq='+docu_seq,'820','850');\r\n");
      out.write("\t\t}\r\n");
      out.write("\t\t\r\n");
      out.write("\t</script>\r\n");
      out.write("</head>\r\n");
      out.write("<body>\r\n");
      out.write("\t");
      out.write("\r\n");
      out.write("\r\n");

	HttpSession headerSession = request.getSession(false);	
	MemberVO headerMemberVO = (MemberVO) headerSession.getAttribute("gwUser");

      out.write('\r');
      out.write('\n');

	String user_id ="";
	String pass="";

      out.write("\r\n");
      out.write("<script type=\"text/javascript\">\r\n");
      out.write("\tfunction logOut(){\r\n");
      out.write("\t\tif(confirm(\"로그아웃 하시겠습니까?\")){\r\n");
      out.write("\t\t\t location.href = \"");
      out.print(GROUP_ROOT);
      out.write("/co/login/logOut.do\";  // 이동할주소\r\n");
      out.write("\r\n");
      out.write("\t\t}\r\n");
      out.write("\t}\r\n");
      out.write("</script>\r\n");
      out.write("\r\n");
      out.write("<div class=\"wrap_header\">\r\n");
      out.write("\t<div class=\"header\">\r\n");
      out.write("\t\t<h1><a href=\"#\" onclick=\"location.href='");
      out.print(GROUP_ROOT);
      out.write("/main/main.do'\"><img src=\"");
      out.print(GROUP_WEB_ROOT);
      out.write("/img/header_logo.jpg\" alt=\"하나제약 그룹웨어\" /></a></h1>\r\n");
      out.write("\t\t<div class=\"wrap_menu\">\r\n");
      out.write("\t\t\t<span class=\"connect\">");
      out.print(headerMemberVO.getEmp_ko_nm() );
      out.write("</span>\r\n");
      out.write("\t\t\t<ul class=\"menu\">\r\n");
      out.write("\t\t\t\t<li class=\"first\"><a href=\"#\" onclick=\"location.href='");
      out.print(GROUP_ROOT);
      out.write("/pe/member/memberDetail.do'\">내정보</a></li>\r\n");
      out.write("\t\t\t\t<li><a href=\"#\" onclick=\"javascript:Commons.popupOpen('passChg', '");
      out.print(GROUP_ROOT);
      out.write("/co/login/passwordChangeForm.do', '290', '239'); return false;\">비밀번호변경</a></li>\r\n");
      out.write("\t\t\t\t<li><a href=\"#\" onclick=\"javascript:logOut(); return false;\">로그아웃</a></li>\r\n");
      out.write("\t\t\t\t<li><a href=\"#\" onclick=\"location.href='");
      out.print(GROUP_ROOT);
      out.write("/of/notice/noticeList.do?search_start_ymd=2015-01-01&search_read_yn=N'\">공지 ");
      out.print(headerMemberVO.getNoticeCountNoread() );
      out.write("</a></li>\r\n");
      out.write("\t\t\t\t<li><a href=\"#\" onclick=\"location.href='");
      out.print(GROUP_ROOT);
      out.write("/of/message/messageList.do?start_ymd=2015-01-01&type=1&search_read_yn=N'\">받은쪽지 ");
      out.print(headerMemberVO.getMessageReceiveCount() );
      out.write("</a></li>\r\n");
      out.write("\t\t\t\t<li><a href=\"#\" onclick=\"location.href='");
      out.print(GROUP_ROOT);
      out.write("/ea/receive/receiveList.do?search_start_ymd=2015-01-01&search_appr_state=E03001'\">문서결재 ");
      out.print(headerMemberVO.getReceiveCount() );
      out.write("</a></li>\r\n");
      out.write("\t\t\t\t<li><a href=\"#\" onclick=\"location.href='");
      out.print(GROUP_ROOT);
      out.write("/ea/implement/implementList.do?search_start_dt=2015-01-01&approval_type=E03001&search_read_yn=N'\">시행결재중 ");
      out.print(headerMemberVO.getImplementOngoingCount() );
      out.write("</a></li>\r\n");
      out.write("\t\t\t\t<li><a href=\"#\" onclick=\"location.href='");
      out.print(GROUP_ROOT);
      out.write("/ea/implement/implementList.do?search_start_dt=2015-01-01&approval_type=E03002&search_read_yn=N'\">시행완료 ");
      out.print(headerMemberVO.getImplementCompleteCount() );
      out.write("</a></li>\r\n");
      out.write("\t\t\t</ul>\r\n");
      out.write("\t\t\t<!-- 20150609 웹메일 클릭 시 로그인 되지 않도록 수정 -->\r\n");
      out.write("\t\t\t<!-- 원복 시 해당 주석 삭제 -->\r\n");
      out.write("\t\t\t");
      out.write("\r\n");
      out.write("\t\t\t<a href=\"http://gwmail.hanaph.co.kr/index.php?_user=");
      out.print(headerMemberVO.getE_mail() );
      out.write("\" target=\"_blank\" class=\"webmail\" >WEB MAIL</a>\r\n");
      out.write("\t\t</div>\r\n");
      out.write("\t</div>\r\n");
      out.write("</div>");
      out.write('\r');
      out.write('\n');
      out.write('	');
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");

	List<MenuVO> gnbMenuList = (List)headerSession.getAttribute("gwGNB"); 

      out.write("\r\n");
      out.write("<div class=\"wrap_gnb\">\r\n");
      out.write("\t<ul>\r\n");
      out.write("\t\t");

		if(gnbMenuList.size() > 0){
			for(int i=0; gnbMenuList.size()>i;i++){
				MenuVO menuVO = new MenuVO();
				menuVO = gnbMenuList.get(i);
		
      out.write("\r\n");
      out.write("\t\t<li ");
if(i == 0){ 
      out.write("class=\"first\"");
} 
      out.write("><a href=\"#\" onclick=\"location.href='");
      out.print(GROUP_ROOT );
      out.print(menuVO.getUrl());
      out.write('\'');
      out.write('"');
      out.write('>');
      out.print(menuVO.getMenu_nm() );
 if("jdbc:oracle:thin:@58.229.234.12:1521:ORCL".equals(JDBC_URL)){ 
      out.write('(');
      out.write('테');
      out.write(')');
} 
      out.write("</a></li>\r\n");
      out.write("\t\t");
	}
		} 
      out.write(" \r\n");
      out.write("\t</ul>\r\n");
      out.write("\t\r\n");
      out.write("</div>");
      out.write("\r\n");
      out.write("\t\r\n");
      out.write("\t<div class=\"wrap_con\">\r\n");
      out.write("\t\t<div class=\"content\">\r\n");
      out.write("\t\t\t");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");

	List<MenuVO> lnbMentList = (List<MenuVO>)request.getAttribute("lnbMenuList");
	if(lnbMentList == null){
		lnbMentList = new ArrayList();
	}
	String MENU_CHILD = com.hanaph.gw.co.common.utils.StringUtil.nvl((String)request.getAttribute("MENU_CHILD"), "01");

      out.write("\r\n");
      out.write("\r\n");
      out.write("<div class=\"location\">\r\n");
      out.write("\t<ul>\r\n");
      out.write("\t\t<li><a href=\"#\" onclick=\"location.href='");
      out.print(GROUP_ROOT);
      out.write("/main/main.do'\"><img src=\"");
      out.print(GROUP_WEB_ROOT);
      out.write("/img/location_icon_home.gif\" alt=\"home\" /></a> > </li>\r\n");
      out.write("\t\t");

		if(lnbMentList.size()!=0){
			
			for(int i=0; lnbMentList.size()>i;i++){
				MenuVO menuVO = new MenuVO();
				menuVO = lnbMentList.get(i);
				if(menuVO.getMenu_child().equals(MENU_CHILD)){
		
      out.write("\r\n");
      out.write("\t\t<li><a href=\"#\">");
      out.print(menuVO.getParents_menu_nm() );
      out.write("</a> > </li>\r\n");
      out.write("\t\t<li><a href=\"");
      out.print(GROUP_ROOT );
      out.print(menuVO.getUrl() );
      out.write("\" class=\"active\">");
      out.print(menuVO.getMenu_nm() );
      out.write("</a></li>\r\n");
      out.write("\t\t");

				}
			}
		}
		
      out.write("\r\n");
      out.write("\t\t\r\n");
      out.write("\t</ul>\r\n");
      out.write("</div>");
      out.write("\r\n");
      out.write("\t\t\t");
      out.write("\r\n");
      out.write("<div class=\"lnb\">\r\n");
      out.write("\t");

		if(lnbMentList.size()!=0){
			MenuVO menuVO = new MenuVO();
			menuVO = lnbMentList.get(0);
	
      out.write("\r\n");
      out.write("\t<p class=\"tit\">");
      out.print(StringUtil.nvl(menuVO.getParents_menu_nm()) );
      out.write("</p>\r\n");
      out.write("\t");

		}
	
      out.write("\r\n");
      out.write("\t<ul>\r\n");
      out.write("\t\t");

		if(lnbMentList.size()!=0){
			for(int i=0; lnbMentList.size()>i;i++){
				MenuVO menuVO = new MenuVO();
				menuVO = lnbMentList.get(i);
		
      out.write("\r\n");
      out.write("\t\t<li ");
if(i==lnbMentList.size()-1){ 
      out.write(" class=\"last\"");
} 
      out.write(">\r\n");
      out.write("\t\t\t<a href=\"");
      out.print(GROUP_ROOT );
      out.print(menuVO.getUrl() );
      out.write("\" >\r\n");
      out.write("\t\t\t\t");
if(menuVO.getMenu_child().length() == 6) {
      out.write("&nbsp;&nbsp;&nbsp;&nbsp;ㄴ");
} 
      out.write("\r\n");
      out.write("\t\t\t\t");
      out.print(StringUtil.nvl(menuVO.getMenu_nm()));
      out.write("\t\r\n");
      out.write("\t\t\t\t<span class=\"none_check\">&nbsp;");
      out.print(StringUtil.nvl(menuVO.getAdd_menu_nm()));
      out.write("</span>\t\t\t\r\n");
      out.write("\t\t\t</a>\r\n");
      out.write("\t\t</li>\r\n");
      out.write("\t\t");

			}
		}
		
      out.write("\r\n");
      out.write("\t</ul>\r\n");
      out.write("</div>");
      out.write("\r\n");
      out.write("\t\t\t\r\n");
      out.write("\t\t\t<!-- ######## start ####### -->\r\n");
      out.write("<div class=\"cont float_left\">\r\n");
      out.write("\t<div class=\"wrap_send_document\">\r\n");
      out.write("\t\t<h2>내가올린문서</h2>\r\n");
      out.write("\t\t<div class=\"noticeboard_box\">\r\n");
      out.write("\t\t<div class=\"serch_box\">\r\n");
      out.write("\t\t<form id=\"searchForm\" name=\"searchForm\" method=\"post\">\r\n");
      out.write("\t\t\t<ul class=\"serch_con01\">\r\n");
      out.write("\t\t\t\t<li>\r\n");
      out.write("\t\t\t\t\t<span class=\"sc_txt02\">작성일</span>\r\n");
      out.write("\t\t\t\t\t<span class=\"serch_date_box\">\r\n");
      out.write("\t\t\t\t\t\t<input class=\"serch_date\" type=\"text\" id=\"search_start_ymd\" name=\"search_start_ymd\" value=\"");
      out.print(search_start_ymd);
      out.write("\" readonly=\"readonly\">\r\n");
      out.write("\t\t\t\t\t\t<button type=\"button\" class=\"btn_date\"><span class=\"blind\">날짜선택</span></button>\r\n");
      out.write("\t\t\t\t\t</span> ~ \r\n");
      out.write("\t\t\t\t\t<span class=\"serch_date_box\">\r\n");
      out.write("\t\t\t\t\t\t<input class=\"serch_date\" type=\"text\" id=\"search_end_ymd\" name=\"search_end_ymd\" value=\"");
      out.print(search_end_ymd);
      out.write("\" readonly=\"readonly\">\r\n");
      out.write("\t\t\t\t\t\t<button type=\"button\" class=\"btn_date\"><span class=\"blind\">날짜선택</span></button>\r\n");
      out.write("\t\t\t\t\t</span>\r\n");
      out.write("\t\t\t\t\t<span class=\"sc_txt02\">문서종류</span>\r\n");
      out.write("\t\t\t\t\t<select class=\"serch_select02 w96\" id=\"search_docu_kind\" name=\"search_docu_kind\">\r\n");
      out.write("\t\t\t\t\t\t<option value=\"all\">전체</option>\r\n");
      out.write("\t\t\t\t\t\t");

						if(docuKindList.size()!=0){
							for(int i=0;i<docuKindList.size();i++){
								CodeVO codeVO = new CodeVO();
								codeVO = docuKindList.get(i);
						
      out.write("\r\n");
      out.write("\t\t\t\t\t\t\t\t<option value=\"");
      out.print(codeVO.getCd());
      out.write('"');
      out.write('>');
      out.print(codeVO.getCd_nm());
      out.write("</option>\r\n");
      out.write("\t\t\t\t\t\t");

							}
					   	}
						
      out.write("\r\n");
      out.write("\t\t\t\t\t</select>\r\n");
      out.write("\t\t\t\t\t<span class=\"sc_txt02\">문서상태</span>\r\n");
      out.write("\t\t\t\t\t<select class=\"serch_select02 w96\" id=\"search_docu_state\" name=\"search_docu_state\">\r\n");
      out.write("\t\t\t\t\t\t\t<option value=\"all\">전체</option>\r\n");
      out.write("\t\t\t\t\t\t");

						if(docuStateList.size()!=0){
							for(int i=0;i<docuStateList.size();i++){
								CodeVO codeVO = new CodeVO();
								codeVO = docuStateList.get(i);
						
      out.write("\r\n");
      out.write("\t\t\t\t\t\t\t\t<option value=\"");
      out.print(codeVO.getCd());
      out.write('"');
      out.write('>');
      out.print(codeVO.getCd_nm());
      out.write("</option>\r\n");
      out.write("\t\t\t\t\t\t");

							}
					   	}
						
      out.write("\r\n");
      out.write("\t\t\t\t\t</select>\r\n");
      out.write("\t\t\t\t</li>\r\n");
      out.write("\t\t\t\t<li class=\"cont02\">\r\n");
      out.write("\t\t\t\t\t<span class=\"sc_txt\">\r\n");
      out.write("\t\t\t\t\t\t<select class=\"search_select02 w116\" id=\"searchType\" name=\"searchType\">\r\n");
      out.write("\t\t\t\t\t\t\t<option value=\"approval_seq\">문서번호</option>\r\n");
      out.write("\t\t\t\t\t\t\t<option value=\"subject\">제목</option>\r\n");
      out.write("\t\t\t\t\t\t</select>\r\n");
      out.write("\t\t\t\t\t</span>\r\n");
      out.write("\t\t\t\t\t<input class=\"serch_txt03 w486\" type=\"text\" id=\"searchKeyword\" name=\"searchKeyword\" value=\"");
      out.print(searchKeyword);
      out.write("\" maxlength=\"32\" onKeyPress=\"if(event.keyCode=='13'){goSearch(); return false;}\" >\r\n");
      out.write("\t\t\t\t</li>\r\n");
      out.write("\t\t\t</ul>\r\n");
      out.write("\t\t\t<span class=\"serch_btn\"><button type=\"button\" onclick=\"javascript:goSearch();\">검색</button></span>\r\n");
      out.write("\t\t\t</form>\r\n");
      out.write("\t\t</div>\r\n");
      out.write("\t\t\r\n");
      out.write("\t\t\r\n");
      out.write("\t\t<div class=\"cont_box cont_table05\">\r\n");
      out.write("\t\t\t<form id=\"frm\" name=\"frm\" method=\"post\">\r\n");
      out.write("\t\t\t<input type=\"hidden\" id=\"search_docu_state\" name=\"search_docu_state\" value=\"");
      out.print(search_docu_state );
      out.write("\" />\r\n");
      out.write("\t\t\t<input type=\"hidden\" id=\"search_docu_kind\" name=\"search_docu_kind\" value=\"");
      out.print(search_docu_kind );
      out.write("\" />\r\n");
      out.write("\t\t\t<input type=\"hidden\" id=\"searchType\" name=\"searchType\" value=\"");
      out.print(searchType );
      out.write("\" />\r\n");
      out.write("\t\t\t<input type=\"hidden\" id=\"searchKeyword\" name=\"searchKeyword\" value=\"");
      out.print(searchKeyword );
      out.write("\" />\r\n");
      out.write("\t\t\t<input type=\"hidden\" id=\"search_start_ymd\" name=\"search_start_ymd\" value=\"");
      out.print(search_start_ymd );
      out.write("\" />\r\n");
      out.write("\t\t\t<input type=\"hidden\" id=\"search_end_ymd\" name=\"search_end_ymd\" value=\"");
      out.print(search_end_ymd );
      out.write("\" />\r\n");
      out.write("\t\t\t\t<table>\r\n");
      out.write("\t\t\t\t<colgroup>\r\n");
      out.write("\t\t\t\t\t<col width=\"15%\">\r\n");
      out.write("\t\t\t\t\t<col width=\"12%\">\r\n");
      out.write("\t\t\t\t\t<col width=\"40%\">\r\n");
      out.write("\t\t\t\t\t<col width=\"13%\">\r\n");
      out.write("\t\t\t\t\t<col width=\"10%\">\r\n");
      out.write("\t\t\t\t</colgroup>\r\n");
      out.write("\t\t\t\t\t<thead>\r\n");
      out.write("\t\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t\t\t<th>문서번호</th>\r\n");
      out.write("\t\t\t\t\t\t\t<th>문서종류</th>\r\n");
      out.write("\t\t\t\t\t\t\t<th>제목</th>\r\n");
      out.write("\t\t\t\t\t\t\t<th>작성일</th>\r\n");
      out.write("\t\t\t\t\t\t\t<th>문서상태</th>\r\n");
      out.write("\t\t\t\t\t\t\t<th class=\"br_none\">처리자</th>\t\t\t\t\t\t\t\t\t\t\r\n");
      out.write("\t\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t\t</thead>\r\n");
      out.write("\t\t\t\t\t<tbody>\r\n");
      out.write("\t\t\t\t\t\t");

						if(reportList.size()!=0){
							for(int i=0; reportList.size()>i; i++){
								ReportVO reportVO = new ReportVO();
								reportVO = reportList.get(i);
								String[] last_line_nm = new String[2]; 
								if(!"".equals(StringUtil.nvl(reportVO.getLast_line_nm()))){
									last_line_nm = reportVO.getLast_line_nm().split("\\^");
								}
						
      out.write("\r\n");
      out.write("\t\t\t\t\t\t");
if("1".equals(reportVO.getStep_state())){ 
      out.write("\r\n");
      out.write("\t\t\t\t\t\t<tr onclick=\"javascript:goStep2Pop('");
      out.print(reportVO.getApproval_seq());
      out.write('\'');
      out.write(',');
      out.write('\'');
      out.print(reportVO.getDocu_seq());
      out.write('\'');
      out.write(',');
      out.write('\'');
      out.print(reportVO.getDocu_cd());
      out.write("'); return false;\"\r\n");
      out.write("\t\t\t\t\t\t\t");
if("E02003".equals(reportVO.getState())){
      out.write("class=\"ing\"");
}else if("E02005".equals(reportVO.getState())){
      out.write("class=\"no\"");
}else{
}
      out.write(">\r\n");
      out.write("\t\t\t\t\t\t");
}else if("E02001".equals(reportVO.getState())){ 
      out.write("\r\n");
      out.write("\t\t\t\t\t\t<tr onclick=\"javascript:goStep3Pop('");
      out.print(reportVO.getApproval_seq());
      out.write('\'');
      out.write(',');
      out.write('\'');
      out.print(reportVO.getDocu_seq());
      out.write("'); return false;\"\r\n");
      out.write("\t\t\t\t\t\t\t");
if("E02003".equals(reportVO.getState())){
      out.write("class=\"ing\"");
}else if("E02005".equals(reportVO.getState())){
      out.write("class=\"no\"");
}else{
}
      out.write(">\r\n");
      out.write("\t\t\t\t\t\t");
}else{ 
      out.write("\r\n");
      out.write("\t\t\t\t\t\t<tr onclick=\"javascript:goApprovalPopup('");
      out.print(reportVO.getApproval_seq() );
      out.write("'); return false;\"\r\n");
      out.write("\t\t\t\t\t\t\t");
if("E02003".equals(reportVO.getState())){
      out.write("class=\"ing\"");
}else if("E02005".equals(reportVO.getState())){
      out.write("class=\"no\"");
}else{
}
      out.write(">\r\n");
      out.write("\t\t\t\t\t\t");
} 
      out.write("\r\n");
      out.write("\t\t\t\t\t\t\t<td>");
      out.print(StringUtil.nvl(reportVO.getApproval_seq()) );
      out.write("</td>\r\n");
      out.write("\t\t\t\t\t\t\t<td>");
      out.print(StringUtil.nvl(reportVO.getDocu_nm()) );
      out.write("</td>\r\n");
      out.write("\t\t\t\t\t\t\t<td>");
      out.print(StringUtil.nvl(RequestFilterUtil.toHtmlTagReplace("", reportVO.getSubject())));
      out.write("</td>\r\n");
      out.write("\t\t\t\t\t\t\t<td>");
      out.print(StringUtil.nvl(reportVO.getMake_dt()) );
      out.write("</td>\r\n");
      out.write("\t\t\t\t\t\t\t<td> \r\n");
      out.write("\t\t\t\t\t\t\t    ");
      out.print(StringUtil.nvl(reportVO.getState_nm()) );
      out.write("</td>\r\n");
      out.write("\t\t\t\t\t\t\t<td class=\"br_none\">");
      out.print(StringUtil.nvl(last_line_nm[1]) );
      out.write("</td>\r\n");
      out.write("\t\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t\t\t");

							}
						}else{
						
      out.write("\t\r\n");
      out.write("\t\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t\t\t<td colspan=\"6\" class=\"br_none\">데이터가 없습니다.</td>\r\n");
      out.write("\t\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t\t\t");

						}
						
      out.write("\r\n");
      out.write("\t\t\t\t\t</tbody>\r\n");
      out.write("\t\t\t\t</table>\r\n");
      out.write("\t\t\t</form>\r\n");
      out.write("\t\t</div>\r\n");
      out.write("\t\t<div class=\"paging\">\r\n");
      out.write("\t\t\t<div class=\"wrap_total\">\r\n");
      out.write("\t\t\t\t* 총 건수 : <span class=\"cnt\">");
      out.print(StringUtil.makeMoneyType(cnt));
      out.write("</span>건\r\n");
      out.write("\t\t\t</div>\r\n");
      out.write("\t\t\t<div class=\"wrap_paging\">\r\n");
      out.write("\t\t\t\t");
      out.write('\r');
      out.write('\n');

	String pagingStr = com.hanaph.gw.co.common.utils.StringUtil.nvl((String)request.getAttribute("pagingStr"),"");
	String currentPage = com.hanaph.gw.co.common.utils.StringUtil.nvl(request.getParameter("currentPage"),"1");

      out.write("\r\n");
      out.write("<script type=\"text/javascript\">\r\n");
      out.write("\t\r\n");
      out.write("\tvar $form = $(\"#frm\");\r\n");
      out.write("\tif($form.length > 0) {\r\n");
      out.write("\t//\t\t \t/* 정보 세팅 */\r\n");
      out.write("\t\t$(\"<input></input>\").attr({type:\"hidden\", name:\"currentPage\",id:\"currentPage\", value:$.trim('");
      out.print(currentPage);
      out.write("')}).appendTo($form);\r\n");
      out.write("\t}\r\n");
      out.write("\r\n");
      out.write("\tfunction goPage(v_curr_page) \r\n");
      out.write("\t{\r\n");
      out.write("\t    $(\"#currentPage\").val(v_curr_page);\r\n");
      out.write("\t    $form.submit();\r\n");
      out.write("\t}\r\n");
      out.write("</script>\r\n");
if(!"".equals(pagingStr) && !"".equals(currentPage)){ 
      out.write('\r');
      out.write('\n');
      out.write('	');
      out.print(pagingStr);
      out.write('\r');
      out.write('\n');
}
      out.write("\r\n");
      out.write("\t\t\t</div>\r\n");
      out.write("\t\t</div>\t\t\t\t\r\n");
      out.write("\t</div>\r\n");
      out.write("\t</div>\r\n");
      out.write("</div>\r\n");
      out.write("\t\t\t<!-- ######## end ######### -->\r\n");
      out.write("\t\t\t\r\n");
      out.write("\t\t</div>\r\n");
      out.write("\t</div>\r\n");
      out.write("\t\r\n");
      out.write("\t");
      out.write("\r\n");
      out.write("\r\n");
      out.write("<div class=\"wrap_foot\">\r\n");
      out.write("\t<div class=\"footer\">\r\n");
      out.write("\t\tCopyright ⓒ 2014 <span class=\"copy_fc\">HANA PHARM CO.</span>, LTD. All Right Reserved.\r\n");
      out.write("\t</div>\r\n");
      out.write("</div>");
      out.write("\r\n");
      out.write("</body>\r\n");
      out.write("</html>");
    } catch (java.lang.Throwable t) {
      if (!(t instanceof javax.servlet.jsp.SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          try {
            if (response.isCommitted()) {
              out.flush();
            } else {
              out.clearBuffer();
            }
          } catch (java.io.IOException e) {}
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else throw new ServletException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}
