/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: Apache Tomcat/7.0.55
 * Generated at: 2015-06-16 00:54:49 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.WEB_002dINF.jsps.co.common;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import com.hanaph.gw.pe.member.vo.MemberVO;
import javax.servlet.http.HttpSession;

public final class error_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final javax.servlet.jsp.JspFactory _jspxFactory =
          javax.servlet.jsp.JspFactory.getDefaultFactory();

  private static java.util.Map<java.lang.String,java.lang.Long> _jspx_dependants;

  static {
    _jspx_dependants = new java.util.HashMap<java.lang.String,java.lang.Long>(3);
    _jspx_dependants.put("/include/head.jsp", Long.valueOf(1424677116691L));
    _jspx_dependants.put("/common/path.jsp", Long.valueOf(1429838623000L));
    _jspx_dependants.put("/include/footer.jsp", Long.valueOf(1418202584955L));
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
    java.lang.Throwable exception = org.apache.jasper.runtime.JspRuntimeLibrary.getThrowable(request);
    if (exception != null) {
      response.setStatus(javax.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    }
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
com.hanaph.gw.co.common.utils.Environment env = new com.hanaph.gw.co.common.utils.Environment();
	
	String HTTPS_GROUP_WEB_ROOT = env.getValue("https.web_root_dir.url");
	String HTTPS_GROUP_ROOT = env.getValue("https.root_dir.url");
	String GROUP_WEB_ROOT = env.getValue("web_root_dir.url");
	String GROUP_ROOT = env.getValue("root_dir.url");
	String JDBC_URL = env.getValue("jdbc.url");
	
	
      out.write('\r');
      out.write('\n');

	response.setStatus(HttpServletResponse.SC_OK);

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
      out.write("\t<div class=\"wrap_header\">\r\n");
      out.write("\t\t<div class=\"header\">\r\n");
      out.write("\t\t\t<h1><a href=\"#\" onclick=\"location.href='");
      out.print(GROUP_ROOT);
      out.write("/main/main.do'\"><img src=\"");
      out.print(GROUP_ROOT);
      out.write("/asset/img/header_logo.jpg\" alt=\"하나제약 그룹웨어\" /></a></h1>\r\n");
      out.write("\t\t</div>\r\n");
      out.write("\t</div>\r\n");
      out.write("\t\r\n");
      out.write("\t<div class=\"wrap_con error_bg\">\r\n");
      out.write("\t\t<div class=\"wrap_error\">\r\n");
      out.write("\t\t\t<div class=\"error\">\r\n");
      out.write("\t\t\t\t<p>요청 처리 과정에서 <span>에러</span>가 발생하였습니다.</p>\r\n");
      out.write("\t\t\t\t<p>잠시 후 다시 시도하시거나 전산팀에 문의 바랍니다.</p>\r\n");
      out.write("\t\t\t\t\r\n");
      out.write("\t\t\t</div>\r\n");
      out.write("\t\t</div>\r\n");
      out.write("\t</div>\r\n");
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
