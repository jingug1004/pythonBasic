/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: Apache Tomcat/7.0.55
 * Generated at: 2015-12-09 06:36:32 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.WEB_002dINF.jsps.ea.newReport;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import com.hanaph.gw.pe.member.vo.AnnualMgrVO;
import com.hanaph.gw.ea.newReport.vo.VacationVO;
import com.hanaph.gw.co.common.utils.StringUtil;
import com.hanaph.gw.co.common.utils.RequestFilterUtil;
import com.hanaph.gw.ea.newReport.vo.ApprovalMasterVO;

public final class step3E01001_jsp extends org.apache.jasper.runtime.HttpJspBase
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
      out.write("\r\n");
      out.write("\r\n");


	/*휴가 신청서*/
	VacationVO vacationVO = (VacationVO)request.getAttribute("vacationVO");
	String emp_ko_nm = StringUtil.nvl((String)request.getAttribute("emp_ko_nm"));
	AnnualMgrVO annualPlan = (AnnualMgrVO)request.getAttribute("annualPlan");
	
	int annualcommonDtCnt = 0; //공동연차사용일수
	annualcommonDtCnt = ((Integer)request.getAttribute("annualcommonDtCnt")).intValue();
	
	/* 결재마스터 */
	ApprovalMasterVO approvalMasterVO = (ApprovalMasterVO)request.getAttribute("approvalMasterVO");
	if(approvalMasterVO == null){
		approvalMasterVO = new ApprovalMasterVO();
	}
	
	String state = StringUtil.nvl(approvalMasterVO.getState());
	
	if(vacationVO == null){
		vacationVO = new VacationVO();
	}

	float yr_year_used_day = 0;
	if(Float.parseFloat(annualPlan.getYr_year_used_day()) < 0){
		yr_year_used_day = Float.parseFloat(annualPlan.getYr_year_used_day());
	}
	
	float reserved_day = annualPlan.getUseable_days() - annualPlan.getUsed_days() + yr_year_used_day;

      out.write("\r\n");
      out.write("<div class=\"inner_box no_scroll non_ipt\">\r\n");
      out.write("\t<strong class=\"tit_s\">내역</strong>\r\n");
      out.write("\t<table>\r\n");
      out.write("\t\t<colgroup>\r\n");
      out.write("\t\t");
if(!"E02004".equals(state)){ 
      out.write("\r\n");
      out.write("\t\t\t<col class=\"cb_w85\">\r\n");
      out.write("\t\t\t<col style=\"width:229px\">\r\n");
      out.write("\t\t\t<col style=\"width:86px\">\r\n");
      out.write("\t\t\t<col style=\"width:90px\">\r\n");
      out.write("\t\t\t<col style=\"width:100px\">\r\n");
      out.write("\t\t\t<col style=\"\">\t\t\t\r\n");
      out.write("\t\t");
}else{ 
      out.write("\r\n");
      out.write("\t\t\t<col class=\"cb_w85\">\r\n");
      out.write("\t\t\t<col style=\"\">\r\n");
      out.write("\t\t");
}
      out.write("\r\n");
      out.write("\t\t</colgroup>\r\n");
      out.write("\t\t<tr>\r\n");
      out.write("\t\t\t<th>휴가종류</th>\r\n");
      out.write("\t\t\t<td ");
if("E02004".equals(state)){
      out.write("colspan=\"5\" class=\"holliday bdrn\"");
}else{
      out.write("class=\"holliday\"");
}
      out.write('>');
      out.print(StringUtil.nvl(vacationVO.getVacation_kind()));
      out.write("</td>\r\n");
      out.write("\t\t");
if(!"E02004".equals(state)){ 
      out.write("\r\n");
      out.write("\t\t\t<th>남은 연차일</th>\r\n");
      out.write("\t\t\t<td class=\"annual\">");
      out.print(reserved_day );
      out.write("</td>\r\n");
      out.write("\t\t\t<th>연차사용가능일</th>\r\n");
      out.write("\t\t\t<td class=\"bdrn annual\">");
      out.print(annualPlan.getUseable_days() );
      out.write("</td>\r\n");
      out.write("\t\t");
}
      out.write("\r\n");
      out.write("\t\t</tr>\r\n");
      out.write("\t\t<tr>\r\n");
      out.write("\t\t\t<th>휴가기간</th>\r\n");
      out.write("\t\t\t<td ");
if("E02004".equals(state)){
      out.write("colspan=\"5\" class=\"bdrn\"");
}
      out.write(">\r\n");
      out.write("\t\t\t\t");
      out.print(StringUtil.nvl(vacationVO.getStart_ymd()));
      out.write(' ');
      out.write('~');
      out.write(' ');
      out.print(StringUtil.nvl(vacationVO.getEnd_ymd()));
      out.write("\r\n");
      out.write("\t\t\t</td>\r\n");
      out.write("\t\t");
if(!"E02004".equals(state)){ 
      out.write("\r\n");
      out.write("\t\t\t<th>올해 사용일</th>\r\n");
      out.write("\t\t\t<td class=\"annual\">");
      out.print(annualPlan.getUsed_days() );
      out.write('(');
      out.print(annualcommonDtCnt);
      out.write(")</td>\r\n");
      out.write("\t\t\t<th>지난해 사용일</th>\r\n");
      out.write("\t\t\t<td class=\"bdrn annual\">");
      out.print(yr_year_used_day );
      out.write("</td>\r\n");
      out.write("\t\t");
}
      out.write("\r\n");
      out.write("\t\t</tr>\r\n");
      out.write("\t\t<tr>\r\n");
      out.write("\t\t\t<th>휴가사유</th>\r\n");
      out.write("\t\t\t<td colspan=\"5\" class=\"bdrn cause\">");
      out.print(StringUtil.nl2br(RequestFilterUtil.toHtmlTagReplace("", vacationVO.getReason())));
      out.write("</td>\r\n");
      out.write("\t\t</tr>\r\n");
      out.write("\t\t<tr>\r\n");
      out.write("\t\t\t<th>비상연락처</th>\r\n");
      out.write("\t\t\t<td colspan=\"5\" class=\"bdrn\">");
      out.print(StringUtil.nvl(vacationVO.getContact_number()));
      out.write("</td>\r\n");
      out.write("\t\t</tr>\r\n");
      out.write("\t</table>\r\n");
      out.write("</div>\t\r\n");
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
