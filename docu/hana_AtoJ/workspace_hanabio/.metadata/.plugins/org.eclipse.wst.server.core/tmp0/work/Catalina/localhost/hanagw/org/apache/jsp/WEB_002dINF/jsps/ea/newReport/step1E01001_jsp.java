/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: Apache Tomcat/7.0.55
 * Generated at: 2015-12-04 08:51:08 UTC
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
import com.hanaph.gw.co.code.vo.CodeVO;
import com.hanaph.gw.co.common.utils.StringUtil;
import com.hanaph.gw.co.common.utils.RequestFilterUtil;
import java.util.List;

public final class step1E01001_jsp extends org.apache.jasper.runtime.HttpJspBase
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
      out.write(" \r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");

	String emp_ko_nm = StringUtil.nvl((String)request.getAttribute("emp_ko_nm"));//임직원이름
	AnnualMgrVO annualPlan = (AnnualMgrVO)request.getAttribute("annualPlan");//연차정보
	VacationVO vacationVO = (VacationVO)request.getAttribute("vacationVO");//휴가정보
	List<CodeVO> codeList = (List<CodeVO>)request.getAttribute("codeList"); //게시판종류
	
	int annualcommonDtCnt = 0; //공동연차사용일수
	annualcommonDtCnt = ((Integer)request.getAttribute("annualcommonDtCnt")).intValue(); 

	if(vacationVO == null){
		vacationVO = new VacationVO();
	}

	float yr_year_used_day = 0;
	if(Float.parseFloat(annualPlan.getYr_year_used_day()) < 0){
		yr_year_used_day = Float.parseFloat(annualPlan.getYr_year_used_day());
	}
	
	float reserved_day = annualPlan.getUseable_days() - annualPlan.getUsed_days() + yr_year_used_day;
	

      out.write("\r\n");
      out.write("<script type=\"text/javascript\">\r\n");
      out.write("\t\r\n");
      out.write("\t$(window).load(function() {\r\n");
      out.write("\t\tvar alertMessage = \"");
      out.print(emp_ko_nm );
      out.write(" 님의 남은 연차 일은 ");
      out.print(reserved_day);
      out.write("일 입니다.\";\r\n");
      out.write("\t\talertMessage += \"\\n\\n남은 연차일 (");
      out.print(reserved_day);
      out.write("일)= 연차사용 가능일(");
      out.print(annualPlan.getUseable_days());
      out.write(")일- 올해\";\r\n");
      out.write("\t\talertMessage += \"\\n연차 사용일 (");
      out.print(annualPlan.getUsed_days());
      out.write("일)- 지난해 연차 사용일 (");
      out.print(yr_year_used_day);
      out.write("일)\";\r\n");
      out.write("\t\talertMessage += \"\\n\\n위와 같은 메시지를 작성자에게 안내 하였습니다.\";\r\n");
      out.write("\t\t\r\n");
      out.write("\t\talert(alertMessage);\r\n");
      out.write("\t});\r\n");
      out.write("\r\n");
      out.write("\t/**\r\n");
      out.write("\t * 벨리데이션 체크\r\n");
      out.write("\t * @returns {Boolean}\r\n");
      out.write("\t */\r\n");
      out.write("\tfunction saveStep1(){\r\n");
      out.write("\t\tif(formCheck.isNull(document.frm.subject, \"제목을 입력해 주세요.\")){\r\n");
      out.write("\t\t\treturn;\r\n");
      out.write("\t\t}else if(formCheck.getByteLength(document.frm.subject.value) > 100){\r\n");
      out.write("\t\t\talert(\"제목은 한글 50자 (영문 100자) 이상 입력할수 없습니다\");\r\n");
      out.write("\t\t\tdocument.frm.subject.focus();\r\n");
      out.write("\t\t\treturn;\r\n");
      out.write("\t\t}else if(formCheck.isNull(document.frm.reason, \"휴가사유를 입력해 주세요.\")){\r\n");
      out.write("\t\t\t\treturn;\r\n");
      out.write("\t\t}else if(formCheck.getByteLength(document.frm.reason) > 100){\r\n");
      out.write("\t\t\talert(\"휴가 사유는 1000자 (영문 2000자) 이상 입력할수 없습니다\");\r\n");
      out.write("\t\t\tdocument.frm.subject.focus();\r\n");
      out.write("\t\t\treturn;\r\n");
      out.write("\t\t}else if(formCheck.isNumDash(document.frm.contact_number.value)){\r\n");
      out.write("\t\t\talert(\"전화번호 형식이 아닙니다.\\n하이픈(-)만 사용할 수 있습니다.\");\r\n");
      out.write("\t\t\tdocument.frm.contact_number.focus();\r\n");
      out.write("\t\t\treturn;\r\n");
      out.write("\t\t}else if(formCheck.dateChk($(\"#start_ymd\").val(),$(\"#end_ymd\").val())<0){\r\n");
      out.write("\t\t\talert(\"휴가기간 시작 날짜가 휴가기간 종료 날짜 보다 이후 입니다.\");\r\n");
      out.write("\t\t\treturn\r\n");
      out.write("\t\t}\r\n");
      out.write("\t\treturn true;\r\n");
      out.write("\t}\r\n");
      out.write("\t\r\n");
      out.write("\t /* 날짜셋팅 */\r\n");
      out.write("\t function ymd_setting(){\r\n");
      out.write("\t\t $(\"#end_ymd\").val($(\"#start_ymd\").val());\r\n");
      out.write("\t }\r\n");
      out.write("\t \r\n");
      out.write("\t function readonlyCheck(){\r\n");
      out.write("\t\t /*반차일경우는 날짜를 하루만 선택할수 있다.*/\r\n");
      out.write("\t\t if($(\"#vacation_kind\").val() == 'E06220'){\r\n");
      out.write("\t\t\t$('#end_ymd').attr(\"disabled\", \"disabled\");\r\n");
      out.write("\t\t }else{\r\n");
      out.write("\t\t\t $('#end_ymd').removeAttr(\"disabled\"); \r\n");
      out.write("\t\t }\r\n");
      out.write("\t }\r\n");
      out.write("\t \r\n");
      out.write("</script>\r\n");
      out.write("<div class=\"inner_box no_scroll\">\r\n");
      out.write("\t<strong class=\"tit_s\">내역</strong>\r\n");
      out.write("\t<table>\r\n");
      out.write("\t\t<colgroup>\r\n");
      out.write("\t\t\t<col class=\"cb_w86\">\r\n");
      out.write("\t\t\t<col style=\"width:229px\">\r\n");
      out.write("\t\t\t<col style=\"width:86px\">\r\n");
      out.write("\t\t\t<col style=\"width:90px\">\r\n");
      out.write("\t\t\t<col style=\"width:100px\">\r\n");
      out.write("\t\t\t<col style=\"\">\r\n");
      out.write("\t\t</colgroup>\r\n");
      out.write("\t\t<tr>\r\n");
      out.write("\t\t\t<th>휴가종류</th>\r\n");
      out.write("\t\t\t<td class=\"holliday\">\r\n");
      out.write("\t\t\t\t<select class=\"serch_select01\" id=\"vacation_kind\" name=\"vacation_kind\" onchange=\"readonlyCheck();\">\r\n");
      out.write("\t\t\t\t");

				if(codeList.size()!=0){
					for(int i=0;i<codeList.size();i++){
						CodeVO codeVO = new CodeVO();
						codeVO = codeList.get(i);
						if("Y".equals(codeVO.getUse_yn())){
				
      out.write("\r\n");
      out.write("\t\t\t\t\t\t\t\t<option value=\"");
      out.print(codeVO.getCd());
      out.write('"');
      out.write(' ');
      out.print(StringUtil.nvl(vacationVO.getCd()).equals(codeVO.getCd())? "selected": "");
      out.write(' ');
      out.write(' ');
      out.write('>');
      out.print(codeVO.getCd_nm());
      out.write("</option>\r\n");
      out.write("\t\t\t\t");

						}
					}
				}
				
      out.write("\r\n");
      out.write("\t\t\t\t</select>\r\n");
      out.write("\t\t\t</td>\r\n");
      out.write("\t\t\t<th>남은 연차일</th>\r\n");
      out.write("\t\t\t<td class=\"annual\">");
      out.print(reserved_day );
      out.write("</td>\r\n");
      out.write("\t\t\t<th>연차사용가능일</th>\r\n");
      out.write("\t\t\t<td class=\"bdrn annual\">");
      out.print(annualPlan.getUseable_days() );
      out.write("</td>\r\n");
      out.write("\t\t</tr>\r\n");
      out.write("\t\t<tr>\r\n");
      out.write("\t\t\t<th>휴가기간</th>\r\n");
      out.write("\t\t\t<td>\r\n");
      out.write("\t\t\t\t<span class=\"date_box\">\r\n");
      out.write("\t\t\t\t\t<input type=\"text\" class=\"serch_date\" name=\"start_ymd\" id=\"start_ymd\" onchange=\"javascript:ymd_setting();\" value=\"");
      out.print(StringUtil.nvl(vacationVO.getStart_ymd()));
      out.write("\" readonly=\"readonly\"/>\r\n");
      out.write("\t\t\t\t\t<button type=\"button\" class=\"btn_date\"><span class=\"blind\">날짜선택</span></button>\r\n");
      out.write("\t\t\t\t</span> ~ \r\n");
      out.write("\t\t\t\t<span class=\"date_box\">\r\n");
      out.write("\t\t\t\t\t<input type=\"text\" class=\"serch_date\" name=\"end_ymd\" id=\"end_ymd\" value=\"");
      out.print(StringUtil.nvl(vacationVO.getEnd_ymd()));
      out.write("\" readonly=\"readonly\"/>\r\n");
      out.write("\t\t\t\t\t<button type=\"button\" class=\"btn_date\"><span class=\"blind\">날짜선택</span></button>\r\n");
      out.write("\t\t\t\t</span>\r\n");
      out.write("\t\t\t</td>\r\n");
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
      out.write("\t\t</tr>\r\n");
      out.write("\t\t<tr>\r\n");
      out.write("\t\t\t<th>휴가사유</th>\r\n");
      out.write("\t\t\t<td colspan=\"5\" class=\"bdrn cause\"><textarea name=\"reason\" id=\"reason\">");
      out.print(RequestFilterUtil.toHtmlTagReplace("", vacationVO.getReason()));
      out.write("</textarea></td>\r\n");
      out.write("\t\t</tr>\r\n");
      out.write("\t\t<tr>\r\n");
      out.write("\t\t\t<th>비상연락처</th>\r\n");
      out.write("\t\t\t<td colspan=\"5\" class=\"bdrn\"><input type=\"text\" name=\"contact_number\" id=\"contact_number\" value=\"");
      out.print(StringUtil.nvl(vacationVO.getContact_number()));
      out.write("\" maxlength=\"20\"/></td>\r\n");
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
