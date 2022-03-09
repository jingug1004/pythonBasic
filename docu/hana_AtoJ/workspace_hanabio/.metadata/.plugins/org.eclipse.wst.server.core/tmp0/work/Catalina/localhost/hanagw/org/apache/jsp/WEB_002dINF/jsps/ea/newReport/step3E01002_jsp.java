/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: Apache Tomcat/7.0.55
 * Generated at: 2015-06-16 04:37:50 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.WEB_002dINF.jsps.ea.newReport;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import com.hanaph.gw.co.common.utils.StringUtil;
import com.hanaph.gw.co.common.utils.RequestFilterUtil;
import com.hanaph.gw.ea.newReport.vo.DraftVO;

public final class step3E01002_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final javax.servlet.jsp.JspFactory _jspxFactory =
          javax.servlet.jsp.JspFactory.getDefaultFactory();

  private static java.util.Map<java.lang.String,java.lang.Long> _jspx_dependants;

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

	/* 기안서 */
	DraftVO draftVO = (DraftVO)request.getAttribute("draftVO");
	if(draftVO == null){
		draftVO = new DraftVO();
	}

      out.write("\r\n");
      out.write("<div class=\"inner_box no_scroll non_ipt\">\r\n");
      out.write("\t<strong class=\"tit_s tit_sample\">내 역</strong>\r\n");
      out.write("\t<table class=\"tbl_draft\">\r\n");
      out.write("\t\t<colgroup>\r\n");
      out.write("\t\t\t<col class=\"cb_w85\">\r\n");
      out.write("\t\t\t<col style=\"\">\r\n");
      out.write("\t\t\t<col style=\"width:87px\">\r\n");
      out.write("\t\t\t<col style=\"\">\r\n");
      out.write("\t\t</colgroup>\r\n");
      out.write("\t\t<tbody>\r\n");
      out.write("\t\t\t<tr>\r\n");
      out.write("\t\t\t\t<th>시행일자</th>\r\n");
      out.write("\t\t\t\t<td colspan=\"3\" class=\"date bdrn\">\r\n");
      out.write("\t\t\t\t\t");
      out.print(StringUtil.nvl(draftVO.getImposition_ymd()) );
      out.write("\r\n");
      out.write("\t\t\t\t</td>\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t\t<tr>\r\n");
      out.write("\t\t\t\t<th>통제</th>\r\n");
      out.write("\t\t\t\t<td>");
      out.print(RequestFilterUtil.toHtmlTagReplace("", draftVO.getControll()) );
      out.write("</td>\r\n");
      out.write("\t\t\t\t<th>협조</th>\r\n");
      out.write("\t\t\t\t<td class=\"bdrn\">");
      out.print(RequestFilterUtil.toHtmlTagReplace("", draftVO.getCooperation()) );
      out.write("</td>\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t\t<tr>\r\n");
      out.write("\t\t\t\t<th>내용</th>\r\n");
      out.write("\t\t\t\t<td colspan=\"3\" class=\"inner bdrn\">\r\n");
      out.write("\t\t\t\t\t<table>\r\n");
      out.write("\t\t\t\t\t\t<colgroup>\r\n");
      out.write("\t\t\t\t\t\t\t<col style=\"width:134px\"/>\r\n");
      out.write("\t\t\t\t\t\t\t<col style=\"\"/>\r\n");
      out.write("\t\t\t\t\t\t</colgroup>\r\n");
      out.write("\t\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t\t\t<th>기안목적</th>\r\n");
      out.write("\t\t\t\t\t\t\t<td class=\"ta\">");
      out.print(StringUtil.nl2br(RequestFilterUtil.toHtmlTagReplace("", draftVO.getPurpose())));
      out.write("</td>\r\n");
      out.write("\t\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t\t\t<th>기안내용</th>\r\n");
      out.write("\t\t\t\t\t\t\t<td class=\"ta\">");
      out.print(StringUtil.nl2br(RequestFilterUtil.toHtmlTagReplace("", draftVO.getContent())));
      out.write("</td>\r\n");
      out.write("\t\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t\t\t<th>기타</th>\r\n");
      out.write("\t\t\t\t\t\t\t<td class=\"ta\">");
      out.print(StringUtil.nl2br(RequestFilterUtil.toHtmlTagReplace("", draftVO.getEtc())));
      out.write("</td>\r\n");
      out.write("\t\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t\t</table>\r\n");
      out.write("\t\t\t\t</td>\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t</tbody>\r\n");
      out.write("\t</table>\r\n");
      out.write("</div>\t");
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
