/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: Apache Tomcat/7.0.55
 * Generated at: 2015-06-16 04:37:35 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.WEB_002dINF.jsps.pe.member;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.util.List;
import com.hanaph.gw.co.common.utils.StringUtil;
import com.hanaph.gw.pe.member.vo.AnnualMgrVO;
import com.hanaph.gw.pe.member.vo.MemberVO;
import javax.servlet.http.HttpSession;
import com.hanaph.gw.pe.member.vo.MemberVO;
import java.util.List;
import com.hanaph.gw.co.menu.vo.MenuVO;
import java.util.ArrayList;
import com.hanaph.gw.co.menu.vo.MenuVO;
import com.hanaph.gw.co.common.utils.StringUtil;
import java.util.List;

public final class annualPlanNotify_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final javax.servlet.jsp.JspFactory _jspxFactory =
          javax.servlet.jsp.JspFactory.getDefaultFactory();

  private static java.util.Map<java.lang.String,java.lang.Long> _jspx_dependants;

  static {
    _jspx_dependants = new java.util.HashMap<java.lang.String,java.lang.Long>(7);
    _jspx_dependants.put("/include/head.jsp", Long.valueOf(1424677116691L));
    _jspx_dependants.put("/common/path.jsp", Long.valueOf(1429838623000L));
    _jspx_dependants.put("/include/footer.jsp", Long.valueOf(1418202584955L));
    _jspx_dependants.put("/include/lnb.jsp", Long.valueOf(1425539967377L));
    _jspx_dependants.put("/include/gnb.jsp", Long.valueOf(1429253452094L));
    _jspx_dependants.put("/include/header.jsp", Long.valueOf(1434416046480L));
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
      out.write("   \r\n");
com.hanaph.gw.co.common.utils.Environment env = new com.hanaph.gw.co.common.utils.Environment();
	
	String HTTPS_GROUP_WEB_ROOT = env.getValue("https.web_root_dir.url");
	String HTTPS_GROUP_ROOT = env.getValue("https.root_dir.url");
	String GROUP_WEB_ROOT = env.getValue("web_root_dir.url");
	String GROUP_ROOT = env.getValue("root_dir.url");
	String JDBC_URL = env.getValue("jdbc.url");
	
	
      out.write('\r');
      out.write('\n');

	AnnualMgrVO annualPlanNotify = (AnnualMgrVO)request.getAttribute("annualPlanNotify");	
	int year = (Integer)request.getAttribute("year");	
	int month = (Integer)request.getAttribute("month");	
	int day = (Integer)request.getAttribute("day");

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
      out.write("\t/**\r\n");
      out.write("\t * 검색 \r\n");
      out.write("\t */\r\n");
      out.write("\tfunction goSearch(){\r\n");
      out.write("\t\tdocument.getElementById(\"frm\").action =\"");
      out.print(GROUP_ROOT);
      out.write("/pe/member/annualList.do\";\r\n");
      out.write("\t\tdocument.getElementById(\"frm\").submit();\r\n");
      out.write("\t}\r\n");
      out.write("\t</script>\t\r\n");
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
 if("jdbc:oracle:thin:@59.150.42.10:1521:ORCL".equals(JDBC_URL)){ 
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
      out.write("><a href=\"");
      out.print(GROUP_ROOT );
      out.print(menuVO.getUrl() );
      out.write('"');
      out.write(' ');
      out.write('>');
if(menuVO.getMenu_child().length() == 6) {
      out.write("&nbsp;&nbsp;&nbsp;&nbsp;ㄴ");
} 
      out.print(StringUtil.nvl(menuVO.getMenu_nm()));
      out.write("</a></li>\r\n");
      out.write("\t\t");

			}
		}
		
      out.write("\r\n");
      out.write("\t</ul>\r\n");
      out.write("</div>");
      out.write("\r\n");
      out.write("\t\t\t\r\n");
      out.write("\t\t\t<!-- ######## start ####### -->\r\n");
      out.write("\t\t\t\r\n");
      out.write("\t\t\t<div class=\"cont float_left\">\r\n");
      out.write("\t\t\t\t<h2>연차지정통보서</h2>\r\n");
      out.write("\t\t\t\t<div class=\"wrap_appoint_inform\" id=\"annual\">\r\n");
      out.write("\t\t\t\t\t<div class=\"wrap_btn annual\">\r\n");
      out.write("\t\t\t\t\t\t<button class=\"type_01\" onclick=\"javascripr:Commons.previewOpen('annual', '785', '768');\">미리보기</button>\r\n");
      out.write("\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t<div class=\"wrap_form\">\r\n");
      out.write("\t\t\t\t\t\t<h3>미사용 연차유급휴가 사용시기 지정통보서</h3>\r\n");
      out.write("\t\t\t\t\t\t<dl class=\"mt30\">\r\n");
      out.write("\t\t\t\t\t\t\t<dt>부서 : </dt>\r\n");
      out.write("\t\t\t\t\t\t\t<dd>");
      out.print(StringUtil.nvl(annualPlanNotify.getDept_ko_nm()) );
      out.write("</dd>\r\n");
      out.write("\t\t\t\t\t\t\t\r\n");
      out.write("\t\t\t\t\t\t\t<dt>성명 : </dt>\r\n");
      out.write("\t\t\t\t\t\t\t<dd>");
      out.print(StringUtil.nvl(annualPlanNotify.getEmp_ko_nm()) );
      out.write("</dd>\r\n");
      out.write("\t\t\t\t\t\t</dl>\r\n");
      out.write("\t\t\t\t\t\t\r\n");
      out.write("\t\t\t\t\t\t<p class=\"txt\">\r\n");
      out.write("\t\t\t\t\t\t\t본 통보서는 관련규정에 의해 기 제출한 \"<span class=\"year\">");
      out.print( StringUtil.nvl(annualPlanNotify.getYr_year()));
      out.write("</span>년도 연차휴가 사용계획서\"에 의거하여, 미사용 연차휴가를 회사가 지정하여<br />\r\n");
      out.write("\t\t\t\t\t\t\t통보합니다. 사용시기 지정일에 연차휴가를 사용하시기를 권장하며 사용하지 아니한 연차유급휴가일수는 소멸하오니 이점<br />\r\n");
      out.write("\t\t\t\t\t\t\t유념하시기 바랍니다.\r\n");
      out.write("\t\t\t\t\t\t</p>\r\n");
      out.write("\t\t\t\t\t\t\r\n");
      out.write("\t\t\t\t\t\t<p class=\"txt mt30\">&lt;아 래&gt;</p>\r\n");
      out.write("\t\t\t\t\t\t<h4 class=\"mt20\">1. 통지받은 내역</h4>\r\n");
      out.write("\t\t\t\t\t\t<div class=\"tableA\">\r\n");
      out.write("\t\t\t\t\t\t\t<table>\r\n");
      out.write("\t\t\t\t\t\t\t\t<thead>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t<th>연차휴가 발생대상 기간</th>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t<th>연차휴가 사용대상 기간</th>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t<th>발생연차</th>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t<th>사용연차</th>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t<th>미사용연차</th>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t\t\t\t\t</thead>\r\n");
      out.write("\t\t\t\t\t\t\t\t<tbody>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t");

									float yr_year_used_day = 0;
									if(Float.parseFloat(annualPlanNotify.getYr_year_used_day()) < 0){
										yr_year_used_day = Float.parseFloat(annualPlanNotify.getYr_year_used_day());
									}
									float used_day = annualPlanNotify.getUsed_days() - yr_year_used_day;
									float reserved_day = annualPlanNotify.getUseable_days() - annualPlanNotify.getUsed_days() + yr_year_used_day;
									//useable_days -  used_days  + if( number( yr_year_used_day ) < 0, number( yr_year_used_day ), 0)
									
      out.write("\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t<td class=\"no_bd_l\">");
      out.print(StringUtil.nvl(annualPlanNotify.getCreate_between_day()) );
      out.write("</td>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t<td>");
      out.print(StringUtil.nvl(annualPlanNotify.getUse_between_day()) );
      out.write("</td>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t<td>");
      out.print(annualPlanNotify.getUseable_days() );
      out.write("</td>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t<td>");
      out.print(used_day );
      out.write("</td>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t<td class=\"no_bd_r\">");
      out.print(reserved_day );
      out.write("</td>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t\t\t\t\t</tbody>\r\n");
      out.write("\t\t\t\t\t\t\t</table>\r\n");
      out.write("\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t\r\n");
      out.write("\t\t\t\t\t\t<h4 class=\"mt30\">2. 미사용 연차유급휴가 사용시기 지정 통보내역</h4>\r\n");
      out.write("\t\t\t\t\t\t<div class=\"tableA\">\r\n");
      out.write("\t\t\t\t\t\t\t<table>\r\n");
      out.write("\t\t\t\t\t\t\t\t<thead>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t<th>구분</th>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t<th>사용시기 지정일</th>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t\t\t\t\t</thead>\r\n");
      out.write("\t\t\t\t\t\t\t\t<tbody>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t<td>11월</td>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t<td>");
      out.print(StringUtil.nvl(annualPlanNotify.getDecember()) );
      out.write("</td>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t<td>12월</td>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t<td>");
      out.print(StringUtil.nvl(annualPlanNotify.getDecember()) );
      out.write("</td>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t\t\t\t\t</tbody>\r\n");
      out.write("\t\t\t\t\t\t\t</table>\r\n");
      out.write("\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t\r\n");
      out.write("\t\t\t\t\t\t<div class=\"wrap_sign mt30\">\r\n");
      out.write("\t\t\t\t\t\t\t<p class=\"date\">\r\n");
      out.write("\t\t\t\t\t\t\t\t<span>");
      out.print( year);
      out.write("</span>년 <span>");
      out.print( month );
      out.write("</span>월 <span>");
      out.print( day );
      out.write("</span>일\r\n");
      out.write("\t\t\t\t\t\t\t</p>\r\n");
      out.write("\t\t\t\t\t\t\t<p class=\"sign\">\r\n");
      out.write("\t\t\t\t\t\t\t\t작성자 : <span class=\"name\"></span>(서명 또는 인)\r\n");
      out.write("\t\t\t\t\t\t\t</p>\r\n");
      out.write("\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t</div>\r\n");
      out.write("\t\t\t</div>\r\n");
      out.write("\t\t\t\r\n");
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
