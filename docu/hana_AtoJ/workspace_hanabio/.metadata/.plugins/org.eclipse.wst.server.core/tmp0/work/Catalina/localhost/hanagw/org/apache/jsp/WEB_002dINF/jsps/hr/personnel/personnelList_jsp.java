/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: Apache Tomcat/7.0.55
 * Generated at: 2015-06-16 01:46:38 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.WEB_002dINF.jsps.hr.personnel;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.util.List;
import com.hanaph.gw.co.common.utils.StringUtil;
import com.hanaph.gw.co.personnel.vo.DepartmentVO;
import com.hanaph.gw.pe.member.vo.MemberVO;
import javax.servlet.http.HttpSession;
import com.hanaph.gw.pe.member.vo.MemberVO;
import java.util.List;
import com.hanaph.gw.co.menu.vo.MenuVO;
import java.util.ArrayList;
import com.hanaph.gw.co.menu.vo.MenuVO;
import com.hanaph.gw.co.common.utils.StringUtil;
import java.util.List;

public final class personnelList_jsp extends org.apache.jasper.runtime.HttpJspBase
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
      out.write("\r\n");
com.hanaph.gw.co.common.utils.Environment env = new com.hanaph.gw.co.common.utils.Environment();
	
	String HTTPS_GROUP_WEB_ROOT = env.getValue("https.web_root_dir.url");
	String HTTPS_GROUP_ROOT = env.getValue("https.root_dir.url");
	String GROUP_WEB_ROOT = env.getValue("web_root_dir.url");
	String GROUP_ROOT = env.getValue("root_dir.url");
	String JDBC_URL = env.getValue("jdbc.url");
	
	
      out.write('\r');
      out.write('\n');

	List<DepartmentVO> departmentList = (List<DepartmentVO>)request.getAttribute("departmentList");

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
      out.write("\t\t\t<div class=\"cont float_left\">\r\n");
      out.write("\t\t\t\t<h2>인사현황조회</h2>\r\n");
      out.write("\t\t\t\t<div class=\"wrap_personnel_situation\">\r\n");
      out.write("\t\t\t\t\t<form name=\"frm\" id=\"frm\" method=\"post\" action=\"");
      out.print(GROUP_ROOT );
      out.write("/hr/personnel/personnelDetailIframe.do\">\r\n");
      out.write("\t\t\t\t\t\t<input type =\"hidden\" id=\"dept_cd\" name=\"dept_cd\">\r\n");
      out.write("\t\t\t\t\t\t<input type =\"hidden\" id=\"up_dept_gbn\" name=\"up_dept_gbn\">\r\n");
      out.write("\t\t\t\t\t</form>\r\n");
      out.write("\t\t\t\t\t<div class=\"wrap_tree\">\r\n");
      out.write("\t\t\t\t\t\t<p class=\"explain\">아래의 부서를 선택하시면 소속 사용자가 나옵니다.</p>\r\n");
      out.write("\t\t\t\t\t\t<div class=\"tree pst_rel\" >\r\n");
      out.write("\t\t\t\t\t\t\t<div class=\"wrap_tree_btn pst_abs\" style=\"width:92px; margin:10px auto\">\r\n");
      out.write("\t\t\t\t\t\t\t\t<button class=\"type_01\" onclick=\"javascript: d.openAll();\">펼치기</button>\r\n");
      out.write("\t\t\t\t\t\t\t\t<button class=\"type_01\" onclick=\"javascript: d.closeAll();\">접기</button>\r\n");
      out.write("\t\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t\t<div class=\"mt10\" style=\"margin-top:40px;\"></div>\r\n");
      out.write("\t\t\t\t\t\t\t<script type=\"text/javascript\">\r\n");
      out.write("\t\t\t\t\t\t\t\td = new dTree('d');\r\n");
      out.write("\t\t\t\t\t\t\t\t");

								if(departmentList.size()!=0){
									for(int i=0; departmentList.size()>i;i++){
										DepartmentVO departmentVO = new DepartmentVO();
										departmentVO = departmentList.get(i);
										if(departmentVO.getUp_dept_cd().equals("9999")){
											departmentVO.setUp_dept_cd("-1");
										}
								
      out.write("\r\n");
      out.write("\t\t\t\t\t\t\t\td.add(\"");
      out.print(StringUtil.nvl(departmentVO.getDept_cd()));
      out.write('"');
      out.write(',');
      out.write('"');
      out.print(StringUtil.nvl(departmentVO.getUp_dept_cd()) );
      out.write('"');
      out.write(',');
      out.write('"');
      out.print(StringUtil.nvl(departmentVO.getDept_ko_nm()) );
      out.write("\",\"javascript:goLocation('");
      out.print(StringUtil.nvl(departmentVO.getDept_cd()));
      out.write('\'');
      out.write(',');
      out.write('\'');
      out.print(StringUtil.nvl(departmentVO.getUp_dept_gbn()));
      out.write("');\",\"");
      out.print(StringUtil.nvl(departmentVO.getDept_ko_nm()) );
      out.write("\");\r\n");
      out.write("\t\t\t\t\t\t\t\t");

									}
								}
								
      out.write("\r\n");
      out.write("\t\t\t\t\t\t\t\tdocument.write(d);\r\n");
      out.write("\t\t\t\t\t\t\t\r\n");
      out.write("\t\t\t\t\t\t\t\tfunction goLocation( dept_cd, up_dept_gbn){\r\n");
      out.write("\t\t\t\t\t\t\t\t\tdocument.getElementById(\"frm\").target= \"personnelDetail\";\r\n");
      out.write("\t\t\t\t\t\t\t\t\tdocument.getElementById(\"dept_cd\").value = dept_cd;\r\n");
      out.write("\t\t\t\t\t\t\t\t\tdocument.getElementById(\"up_dept_gbn\").value = up_dept_gbn;\r\n");
      out.write("\t\t\t\t\t\t\t\t\tdocument.getElementById(\"frm\").submit();\r\n");
      out.write("\t\t\t\t\t\t\t\t}\t\t\r\n");
      out.write("\t\t\t\t\t\t\t</script>\r\n");
      out.write("\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\r\n");
      out.write("\t\t\t\t\t<div class=\"wrap_status ml14\">\r\n");
      out.write("\t\t\t\t\t\t<iframe name=\"personnelDetail\"  src=\"");
      out.print(GROUP_ROOT );
      out.write("/hr/personnel/personnelDetailIframe.do\" frameBorder=\"0\" border=\"0\" width=\"490px\" height=\"540px\"></iframe>\r\n");
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
