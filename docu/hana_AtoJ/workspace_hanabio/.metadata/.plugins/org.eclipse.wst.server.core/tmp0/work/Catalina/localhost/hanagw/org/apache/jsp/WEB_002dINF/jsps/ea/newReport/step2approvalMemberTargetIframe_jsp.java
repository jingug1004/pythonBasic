/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: Apache Tomcat/7.0.55
 * Generated at: 2015-06-16 01:06:35 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.WEB_002dINF.jsps.ea.newReport;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.util.List;
import com.hanaph.gw.co.common.utils.StringUtil;
import com.hanaph.gw.pe.member.vo.MemberVO;
import com.hanaph.gw.ea.newReport.vo.ApprovalVO;
import com.hanaph.gw.ea.mgrDocuInfo.vo.BasicApprovalVO;
import com.hanaph.gw.ea.person.vo.PersonApprovalVO;
import com.hanaph.gw.ea.newReport.vo.ApprovalMasterVO;
import com.hanaph.gw.pe.member.vo.MemberVO;
import javax.servlet.http.HttpSession;

public final class step2approvalMemberTargetIframe_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final javax.servlet.jsp.JspFactory _jspxFactory =
          javax.servlet.jsp.JspFactory.getDefaultFactory();

  private static java.util.Map<java.lang.String,java.lang.Long> _jspx_dependants;

  static {
    _jspx_dependants = new java.util.HashMap<java.lang.String,java.lang.Long>(2);
    _jspx_dependants.put("/include/head.jsp", Long.valueOf(1424677116691L));
    _jspx_dependants.put("/common/path.jsp", Long.valueOf(1429838623000L));
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

	String approval_seq = (String)request.getAttribute("approval_seq");
	String docu_seq = (String)request.getAttribute("docu_seq");
	String person_seq = (String)request.getAttribute("person_seq");
	String approval_gbn = (String)request.getAttribute("approval_gbn");
	
	//세션 회원정보
	MemberVO memberSessionVO = new MemberVO();
	memberSessionVO = (MemberVO) session.getAttribute("gwUser");
	
	//기본결재
	List<BasicApprovalVO> basicApprovalDetailList = (List<BasicApprovalVO>)request.getAttribute("approvalDetailList");
	//개인결재
	List<PersonApprovalVO> personApprovalDetailList = (List<PersonApprovalVO>)request.getAttribute("approvalDetailList");
	//저장결재
	List<ApprovalVO> approvalDetailList = (List<ApprovalVO>)request.getAttribute("approvalDetailList");
	//관리자 권한을 가진 임직원
	List<MemberVO> authorityMemberList = (List<MemberVO>)request.getAttribute("authorityMemberList");
	/* 결재마스터 */
	ApprovalMasterVO approvalMasterVO = (ApprovalMasterVO)request.getAttribute("approvalMasterVO");
	
	boolean adminAuth = false;
	if(authorityMemberList != null){
		for(int i=0; authorityMemberList.size()>i;i++){
			MemberVO memberVO = new MemberVO();
			memberVO = authorityMemberList.get(i);
			if(memberSessionVO.getEmp_no().equals(memberVO.getEmp_no())){
				adminAuth = true;
			}
		}
	}	
	
	int listSize = 0;	

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
      out.write("\t//결재\r\n");
      out.write("\t\tfunction addApprovalMultiRow(){\r\n");
      out.write("\t\t\tvar sameCnt = 0;\r\n");
      out.write("\t\t\tvar approvalCheckBoxArr = document.getElementsByName(\"approval_emp_no\");\r\n");
      out.write("\t\t\tvar approvalCheckBoxCnt = document.getElementsByName(\"approval_emp_no\").length;\r\n");
      out.write("\t\t\t\r\n");
      out.write("\t\t    var approvalListCheckBoxArr = parent.approvalMemberList.document.getElementsByName(\"emp_no\");\r\n");
      out.write("\t\t    var approvalListCheckBoxCnt = parent.approvalMemberList.document.getElementsByName(\"emp_no\").length;\r\n");
      out.write("\t\t    \r\n");
      out.write("\t\t    var checkBoxParam = \"\";\r\n");
      out.write("\t\t    var memberListCheckBoxParam = \"\";\r\n");
      out.write("\r\n");
      out.write("\t\t    for(var i=0; i<approvalListCheckBoxCnt; i++){\r\n");
      out.write("\t\t    \tif(approvalListCheckBoxArr[i].checked){\r\n");
      out.write("\t\t\t    \tmemberListCheckBoxParam = approvalListCheckBoxArr[i].value;\r\n");
      out.write("\t\t\t    \tvar memberListCheckValueArr = memberListCheckBoxParam.split(\"|\");\r\n");
      out.write("\t\t\t\t\tfor(var j=0; j<approvalCheckBoxCnt; j++){\r\n");
      out.write("\t\t\t\t\t\tcheckBoxParam = approvalCheckBoxArr[j].value;\r\n");
      out.write("\t\t\t\t\t\tvar checkBoxCheckValueArr = checkBoxParam.split(\"|\");\r\n");
      out.write("\t\t\t\t\t\tif(memberListCheckValueArr[0] == checkBoxCheckValueArr[0]){\r\n");
      out.write("\t\t\t\t\t\t\tif(sameCnt == 0){\r\n");
      out.write("\t\t\t\t\t\t\t\talert(\"동일한 직원이 존재합니다.\");\r\n");
      out.write("\t\t\t\t\t\t\t\tsameCnt++;\r\n");
      out.write("\t\t\t\t\t\t\t}\r\n");
      out.write("\t\t\t\t\t\t\tapprovalListCheckBoxArr[i].checked = false;\r\n");
      out.write("\t\t\t\t\t\t}\r\n");
      out.write("\t\t\t\t\t}\r\n");
      out.write("\t\t\t\t\tif( (!");
      out.print(adminAuth);
      out.write(" && memberListCheckValueArr[0] == ");
      out.print(memberSessionVO.getEmp_no());
      out.write(") || memberListCheckValueArr[0] == ");
      out.print(approvalMasterVO.getCreate_no());
      out.write("){\r\n");
      out.write("\t\t\t    \t\talert(\"작성자는 결재라인에 포함 할 수 없습니다.\");\r\n");
      out.write("\t\t\t    \t\tapprovalListCheckBoxArr[i].checked = false;\r\n");
      out.write("\t\t\t    \t}\r\n");
      out.write("\t\t\t    }\r\n");
      out.write("\t\t    } \r\n");
      out.write("\t\t    \r\n");
      out.write("\t\t    for(i=0; i<approvalListCheckBoxCnt; i++){\r\n");
      out.write("\t\t    \tif(approvalListCheckBoxArr[i].checked){\r\n");
      out.write("\t\t    \t\tif($( \"li\" ).length > 6 ){\r\n");
      out.write("\t\t\t\t    \talert(\"결재는 최대 7명 입니다.\");\r\n");
      out.write("\t\t\t\t    \tparent.approvalMemberList.allUnCheck();\r\n");
      out.write("\t\t\t\t    \treturn;\r\n");
      out.write("\t\t\t\t    }\r\n");
      out.write("\t\t\t    \tmemberListCheckBoxParam = approvalListCheckBoxArr[i].value;\r\n");
      out.write("\t\t\t    \tvar memberListCheckValueArr = memberListCheckBoxParam.split(\"|\");\r\n");
      out.write("\t\t    \t\tvar ul = document.getElementById(\"approval_ul\");\r\n");
      out.write("\t\t\t\t\tvar li = document.createElement(\"li\");\r\n");
      out.write("\t\t\t    \tli.setAttribute(\"data-role\", \"ui-droplist-item\");\r\n");
      out.write("\t\t\t    \tul.appendChild(li);\r\n");
      out.write("\t\t\t    \tli.innerHTML = memberListCheckValueArr[1]+\"<input type='hidden' name='approval_emp_no' value='\"+memberListCheckValueArr[0]+\"|INSERT'>\";\t\r\n");
      out.write("\t\t\t    }\r\n");
      out.write("\t\t    } \r\n");
      out.write("\t\t    parent.approvalMemberList.allUnCheck();\r\n");
      out.write("\t\t    \r\n");
      out.write("\t\t}\r\n");
      out.write("\t</script>\t\r\n");
      out.write("</head>\r\n");
      out.write("<body>\r\n");
      out.write("\t<div class=\"settle_men fl\" data-role=\"ui-droplist\" data-type=\"basic\">\r\n");
      out.write("\t\t<h4>결재자</h4>\r\n");
      out.write("\t\t<ul id=\"approval_ul\" class=\"select_list fl\" data-role=\"ui-droplist-list\">\r\n");
      out.write("\t\t");

		if(approval_gbn.equals("") || approval_gbn == null){	
			if(approvalDetailList.size() > 0){
				for(int i=0; approvalDetailList.size()>i;i++){
					ApprovalVO approvalVO = new ApprovalVO();
					approvalVO = approvalDetailList.get(i);
					if(!approvalVO.getEmp_no().equals(memberSessionVO.getEmp_no()) || adminAuth){	// 본인은 결재 라인에 포함 하지 않는다.(관리자는 예외)
						if(!approvalVO.getState().equals("E03001")){//결재가 진행중인 라인은 수정이 안된다.
							listSize++;
		
      out.write("\r\n");
      out.write("\t\t<li><input type=\"hidden\" name=\"approval_emp_no\" value=\"");
      out.print(approvalVO.getEmp_no());
      out.write("|SAVA\">");
      out.print(approvalVO.getEmp_ko_nm() );
      out.write("</li>\r\n");
      out.write("\t\t");
				}else{ 
      out.write("\r\n");
      out.write("\t\t<li data-role=\"ui-droplist-item\"><input type=\"hidden\" name=\"approval_emp_no\" value=\"");
      out.print(approvalVO.getEmp_no());
      out.write("|INSERT\">");
      out.print(approvalVO.getEmp_ko_nm() );
      out.write("</li>\r\n");
      out.write("\t\t");
			
						}
					}else{
						listSize--;
					}
				}
			}		
		}else{
			if(approval_gbn.equals("BASIC")){
				if(basicApprovalDetailList.size() > 0){
					for(int i=0; basicApprovalDetailList.size()>i;i++){
						BasicApprovalVO basicApprovalVO = new BasicApprovalVO();
						basicApprovalVO = basicApprovalDetailList.get(i);
						if(!basicApprovalVO.getEmp_no().equals(memberSessionVO.getEmp_no()) || adminAuth){
			
      out.write("\r\n");
      out.write("\t\t\t<li data-role=\"ui-droplist-item\"><input type=\"hidden\" name=\"approval_emp_no\" value=\"");
      out.print(basicApprovalVO.getEmp_no());
      out.write("|INSERT\" >");
      out.print(basicApprovalVO.getEmp_ko_nm() );
      out.write("</li>\r\n");
      out.write("\t\t\t");

						}
					}
				}
			}else if(approval_gbn.equals("PERSON")){	
				if(personApprovalDetailList.size() > 0){
					for(int i=0; personApprovalDetailList.size()>i;i++){
						PersonApprovalVO personApprovalVO = new PersonApprovalVO();
						personApprovalVO = personApprovalDetailList.get(i);
						if(!personApprovalVO.getEmp_no().equals(memberSessionVO.getEmp_no()) || adminAuth){
			
      out.write("\r\n");
      out.write("\t\t\t<li data-role=\"ui-droplist-item\"><input type=\"hidden\" name=\"approval_emp_no\" value=\"");
      out.print(personApprovalVO.getEmp_no());
      out.write("|INSERT\">");
      out.print(personApprovalVO.getEmp_ko_nm() );
      out.write("</li>\r\n");
      out.write("\t\t\t");

						}
					}
				}
			}
		} 
      out.write("\t\r\n");
      out.write("\t\t</ul>\r\n");
      out.write("\t\t<div class=\"btn_list fr\">\r\n");
      out.write("\t\t\t<a href=\"#\" class=\"btn_top\" data-role=\"ui-droplist-btn-up\"><img src=\"");
      out.print(GROUP_WEB_ROOT);
      out.write("/img/popup_btn_top.gif\" alt=\"위로\"></a>\r\n");
      out.write("\t\t\t<a href=\"#\" class=\"btn_bottom\" data-role=\"ui-droplist-btn-down\"><img src=\"");
      out.print(GROUP_WEB_ROOT);
      out.write("/img/popup_btn_bottom.gif\" alt=\"아래로\"></a>\r\n");
      out.write("\t\t\t<a href=\"#\" class=\"btn_close\" data-role=\"ui-droplist-btn-remove\"><img src=\"");
      out.print(GROUP_WEB_ROOT);
      out.write("/img/popup_btn_close02.gif\" alt=\"삭제\"></a>\r\n");
      out.write("\t\t</div>\r\n");
      out.write("\t</div>\r\n");
      out.write("<script src=\"");
      out.print(GROUP_WEB_ROOT);
      out.write("/js/droplist.js\"></script>\t\r\n");
      out.write("<script type=\"text/javascript\">\r\n");
if(approval_gbn.equals("") || approval_gbn == null){
      out.write("\t\r\n");
      out.write("\r\n");
      out.write("$('*[data-role=ui-droplist]').each(function () {\r\n");
      out.write("    var droplist = new Droplist($(this), ");
      out.print(listSize+1);
      out.write(");\r\n");
      out.write("});\r\n");
      out.write("\t\r\n");
 	}else{
      out.write("\t\t\r\n");
      out.write("\r\n");
      out.write("$('*[data-role=ui-droplist]').each(function () {\r\n");
      out.write("    var droplist = new Droplist($(this), -1);\r\n");
      out.write("});\r\n");
      out.write("\r\n");
	} 
      out.write("\r\n");
      out.write("</script>\r\n");
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
