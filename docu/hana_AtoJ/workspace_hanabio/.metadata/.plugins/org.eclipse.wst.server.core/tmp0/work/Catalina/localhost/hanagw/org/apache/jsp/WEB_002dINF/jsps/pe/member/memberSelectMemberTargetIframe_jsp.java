/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: Apache Tomcat/7.0.55
 * Generated at: 2016-11-09 04:09:34 UTC
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
import com.hanaph.gw.pe.member.vo.MemberVO;
import com.hanaph.gw.pe.member.vo.MemberVO;
import javax.servlet.http.HttpSession;

public final class memberSelectMemberTargetIframe_jsp extends org.apache.jasper.runtime.HttpJspBase
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
      out.write("\r\n");
com.hanaph.gw.co.common.utils.Environment env = new com.hanaph.gw.co.common.utils.Environment();
	
	String HTTPS_GROUP_WEB_ROOT = env.getValue("https.web_root_dir.url");
	String HTTPS_GROUP_ROOT = env.getValue("https.root_dir.url");
	String GROUP_WEB_ROOT = env.getValue("web_root_dir.url");
	String GROUP_ROOT = env.getValue("root_dir.url");
	String JDBC_URL = env.getValue("jdbc.url");
	
	
      out.write('\r');
      out.write('\n');

	List<MemberVO> memberTargetList = (List<MemberVO>)request.getAttribute("memberTargetList");
	String type = StringUtil.nvl((String)request.getAttribute("type"));
	
	MemberVO memberSessionVO = new MemberVO();
	memberSessionVO = (MemberVO) session.getAttribute("gwUser");

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
      out.write("\t<script language='javascript'>\r\n");
      out.write("\r\n");
      out.write("\t/**\r\n");
      out.write("\t * 부모창에 저장\r\n");
      out.write("\t */\r\n");
      out.write("\tfunction goTop(){\r\n");
      out.write("\t    var chkCnt = checkCount();\r\n");
      out.write("\t    if(chkCnt > 0) {\r\n");
      out.write("\t    \tparent.goMemberTarget();\r\n");
      out.write("\t \t} else {\r\n");
      out.write("\t \t\tdocument.getElementById(\"emp_no\").value=\"\";\r\n");
      out.write("\t        alert(\"임직원을 선택해주세요.\");\r\n");
      out.write("\t        return;\r\n");
      out.write("\t    }\r\n");
      out.write("\t}\r\n");
      out.write("\t\t\t\r\n");
      out.write("\t/**\r\n");
      out.write("\t * row 삭제\r\n");
      out.write("\t */\r\n");
      out.write("\tfunction delRow() {\r\n");
      out.write("\t \tvar objTable = document.getElementById(\"tbl\");\r\n");
      out.write("\t \tvar lastRow = objTable.rows.length+1;\r\n");
      out.write("\t \tvar memberListCheckBoxParam = \"\";\r\n");
      out.write("\t \tvar memberListCheckValueArr = \"\";\r\n");
      out.write("\t\r\n");
      out.write("\t \tvar chkCnt = checkCount();\r\n");
      out.write("\t \tif(chkCnt > 0) {\r\n");
      out.write("\t \t\tif( confirm(\"삭제하시겠습니까?\") ){\r\n");
      out.write("\t   \t\t\tfor(var i = lastRow; i > 1; i--) {\r\n");
      out.write("\t   \t\t\t\tmemberListCheckBoxParam = document.getElementsByTagName(\"TR\")[i].cells[0].firstChild.value;\r\n");
      out.write("\t   \t\t\t\tmemberListCheckValueArr = memberListCheckBoxParam.split(\"|\");\r\n");
      out.write("\t   \t\t\t\tif(");
      out.print(memberSessionVO.getEmp_no());
      out.write(" == memberListCheckValueArr[0] && !( \"");
      out.print(type);
      out.write("\"==\"SHARE\" || \"");
      out.print(type);
      out.write("\"==\"MESSAGE\" || \"");
      out.print(type);
      out.write("\"==\"MESSAGE_DELIVER\")){\r\n");
      out.write("\t   \t\t\t\t}else{\r\n");
      out.write("\t   \t\t\t\t\tif(document.getElementsByTagName(\"TR\")[i].cells[0].firstChild.checked) objTable.deleteRow(i-2);\r\n");
      out.write("\t   \t\t\t\t}\r\n");
      out.write("\t  \t\t\t}\r\n");
      out.write("\t  \t\t}\r\n");
      out.write("\t \t\treturn false;\r\n");
      out.write("\t \t} else {\r\n");
      out.write("\t        alert(\"삭제할 임직원을 선택해주세요.\");\r\n");
      out.write("\t        return;\r\n");
      out.write("\t    }\r\n");
      out.write("\t}\r\n");
      out.write("\t\r\n");
      out.write("\t/**\r\n");
      out.write("\t * 체크된 row count\r\n");
      out.write("\t */\r\n");
      out.write("\tfunction checkCount() {\r\n");
      out.write("\t \tvar objTable = document.getElementById(\"tbl\");\r\n");
      out.write("\t \tvar lastRow = objTable.rows.length+1;\r\n");
      out.write("\t \tvar rtnCnt = 0;\r\n");
      out.write("\r\n");
      out.write("\t \tfor(var i = lastRow; i > 1; i--){\r\n");
      out.write("\t   \t\tif(document.getElementsByTagName(\"TR\")[i].cells[0].firstChild.checked) rtnCnt++;\r\n");
      out.write("\t \t}\r\n");
      out.write("\t \treturn rtnCnt;\r\n");
      out.write("\t}\r\n");
      out.write("  \t/**\r\n");
      out.write("\t * 체크박스 전체 선택, 해제\r\n");
      out.write("\t */\r\n");
      out.write("\tfunction checkExcute(obj) {\r\n");
      out.write("\t    if(obj.checked){ \r\n");
      out.write("\t   \t\tallCheck();\r\n");
      out.write("\t    }else{\r\n");
      out.write("\t   \t\tallUnCheck();\r\n");
      out.write("\t    }\r\n");
      out.write("\t}\r\n");
      out.write("\t\r\n");
      out.write("\t/**\r\n");
      out.write("\t * 체크박스 전체 선택\r\n");
      out.write("\t */\r\n");
      out.write("\tfunction allCheck() {\r\n");
      out.write("  \t\tvar objTable = document.getElementById(\"tbl\");\r\n");
      out.write("  \t \tvar lastRow = objTable.rows.length+1;\r\n");
      out.write("\r\n");
      out.write("  \t \tfor(var i = lastRow; i > 1; i--) {\r\n");
      out.write("  \t \t\tif(document.getElementsByTagName(\"TR\")[i].cells[0].firstChild.disabled == false){\r\n");
      out.write("  \t  \t\t\tdocument.getElementsByTagName(\"TR\")[i].cells[0].firstChild.checked = true;\r\n");
      out.write("  \t \t\t}\t\r\n");
      out.write("  \t \t}\r\n");
      out.write("  \t}\r\n");
      out.write("\t\r\n");
      out.write("\t/**\r\n");
      out.write("\t * 체크박스 전체 해제\r\n");
      out.write("\t */\r\n");
      out.write("\tfunction allUnCheck() {\r\n");
      out.write("  \t \tvar objTable = document.getElementById(\"tbl\");\r\n");
      out.write("  \t \tvar lastRow = objTable.rows.length+1;\r\n");
      out.write("  \t \tfor(var i = lastRow; i > 1; i--) {\r\n");
      out.write("  \t  \t\tdocument.getElementsByTagName(\"TR\")[i].cells[0].firstChild.checked = false;\r\n");
      out.write("  \t \t}\r\n");
      out.write("  \t}\r\n");
      out.write("  \t\r\n");
      out.write("\t/**\r\n");
      out.write("\t * 임직원 추가\r\n");
      out.write("\t */\r\n");
      out.write("\tfunction addMultiRow() {\r\n");
      out.write("\t\tvar sameCnt = 0;\r\n");
      out.write("\r\n");
      out.write("\t\t//대상자 frame\r\n");
      out.write("\t\tvar checkBoxArr = document.getElementsByName(\"emp_no\"); //파일 체크박스\r\n");
      out.write("\t    var checkBoxCnt = document.getElementsByName(\"emp_no\").length;\r\n");
      out.write("\t\r\n");
      out.write("\t\t//임직원목록 frame \r\n");
      out.write("\t    var memberListCheckBoxArr = parent.memberList.document.getElementsByName(\"member_emp_no\"); //파일 체크박스\r\n");
      out.write("\t    var memberListCheckBoxCnt = parent.memberList.document.getElementsByName(\"member_emp_no\").length;\r\n");
      out.write("\t    \r\n");
      out.write("\t    var checkBoxParam = \"\";\r\n");
      out.write("\t    var memberListCheckBoxParam = \"\";\r\n");
      out.write("\t    \r\n");
      out.write("\t    for(var i=0; i<memberListCheckBoxCnt; i++){\r\n");
      out.write("\t    \tif(memberListCheckBoxArr[i].checked){\r\n");
      out.write("\t\t    \tmemberListCheckBoxParam = memberListCheckBoxArr[i].value;\r\n");
      out.write("\t\t\t\tfor(var j=0; j<checkBoxCnt; j++){\r\n");
      out.write("\t\t\t\t\tcheckBoxParam = checkBoxArr[j].value;\r\n");
      out.write("\t\t\t\t\tif(memberListCheckBoxParam == checkBoxParam){\r\n");
      out.write("\t\t\t\t\t\tif(sameCnt == 0){\r\n");
      out.write("\t\t\t\t\t\t\talert(\"동일한 직원이 존재합니다.\");\r\n");
      out.write("\t\t\t\t\t\t\tsameCnt++;\r\n");
      out.write("\t\t\t\t\t\t}\r\n");
      out.write("\t\t\t\t\t\tmemberListCheckBoxArr[i].checked = false;\r\n");
      out.write("\t\t\t\t\t}\r\n");
      out.write("\t\t\t\t}\r\n");
      out.write("\t\t    }\r\n");
      out.write("\t    } \r\n");
      out.write("\t    \r\n");
      out.write("\t    for(i=0; i<memberListCheckBoxCnt; i++){\r\n");
      out.write("\t    \tif(memberListCheckBoxArr[i].checked){\r\n");
      out.write("\t\t    \tmemberListCheckBoxParam = memberListCheckBoxArr[i].value;\r\n");
      out.write("\t\t\t\tvar memberListCheckValueArr = memberListCheckBoxParam.split(\"|\");\r\n");
      out.write("\t\t\t\tvar objRow = document.getElementById('tbl').insertRow(); // row 생성\r\n");
      out.write("\t\t\t\tvar objCel1 = document.createElement('TD');\r\n");
      out.write("\t\t\t   \tobjRow.appendChild(objCel1);\r\n");
      out.write("\t\t\t   \tobjCel1.innerHTML=\"<input type='checkbox' name='emp_no' id='emp_no' value='\"+memberListCheckBoxParam+\"' onclick='javascript:allChkStatus(this);' checked>\";\r\n");
      out.write("\t\t\t   \t\r\n");
      out.write("\t\t\t   \tobjCel1 = document.createElement('TD');\r\n");
      out.write("\t\t\t   \tobjRow.appendChild(objCel1);\r\n");
      out.write("\t\t\t   \tobjCel1.innerHTML=memberListCheckValueArr[2];\r\n");
      out.write("\t\t\t   \t\r\n");
      out.write("\t\t\t   \tobjCel1 = document.createElement('TD');\r\n");
      out.write("\t\t\t   \tobjRow.appendChild(objCel1);\r\n");
      out.write("\t\t\t   \tobjCel1.innerHTML=memberListCheckValueArr[3];\r\n");
      out.write("\t\t\t   \t\r\n");
      out.write("\t\t\t   \tobjCel1 = document.createElement('TD');\r\n");
      out.write("\t\t\t   \tobjCel1.setAttribute(\"class\", \"br_none\");\r\n");
      out.write("\t\t\t   \tobjRow.appendChild(objCel1);\r\n");
      out.write("\t\t\t   \tobjCel1.innerHTML=memberListCheckValueArr[1];\r\n");
      out.write("\t\t\t}\r\n");
      out.write("\t    } \r\n");
      out.write("\t    parent.memberList.allUnCheck();\r\n");
      out.write("\t    parent.memberList.document.getElementById(\"all_chk\").checked = false;\r\n");
      out.write("\t}\r\n");
      out.write("\r\n");
      out.write("\t/**\r\n");
      out.write("\t * 체트박스 선택\r\n");
      out.write("\t */\r\n");
      out.write("\tfunction allChkStatus(obj){\r\n");
      out.write("\t\tif(!obj.checked){\r\n");
      out.write("\t\t\tif(document.getElementById(\"all_chk\").checked){ \r\n");
      out.write("\t\t\t\tdocument.getElementById(\"all_chk\").checked = false;\r\n");
      out.write("\t\t    }\r\n");
      out.write("\t\t}\r\n");
      out.write("\t}\r\n");
      out.write("\t</script>\t\r\n");
      out.write("</head>\r\n");
      out.write("<body>\r\n");
      out.write("\t<div class=\"authority_staff_ifr\">\r\n");
      out.write("\t\t<div class=\"wrap_group\">\r\n");
      out.write("\t\t\t<h4>대상자</h4>\r\n");
      out.write("\t\t\t<div class=\"wrap_list\">\r\n");
      out.write("\t\t\t\t<table>\r\n");
      out.write("\t\t\t\t\t<colgroup>\r\n");
      out.write("\t\t\t\t\t\t<col style=\"width:30px;\" />\r\n");
      out.write("\t\t\t\t\t\t<col style=\"width:105px\" />\r\n");
      out.write("\t\t\t\t\t\t<col style=\"width:85px\" />\r\n");
      out.write("\t\t\t\t\t\t<col />\r\n");
      out.write("\t\t\t\t\t</colgroup>\r\n");
      out.write("\t\t\t\t\t<thead>\r\n");
      out.write("\t\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t\t\t<th><input type=\"checkbox\" name=\"all_chk\" id=\"all_chk\" onClick=\"checkExcute(this)\"></th>\r\n");
      out.write("\t\t\t\t\t\t\t<th>부서</th>\r\n");
      out.write("\t\t\t\t\t\t\t<th>직급</th>\r\n");
      out.write("\t\t\t\t\t\t\t<th class=\"br_none\">이름</th>\r\n");
      out.write("\t\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t\t</thead>\r\n");
      out.write("\t\t\t\t\t<tbody>\r\n");
      out.write("\t\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t\t\t<td colspan=\"4\" class=\"inner\">\r\n");
      out.write("\t\t\t\t\t\t\t\t<div>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<table id=\"tbl\">\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t");
if(type.equals("MESSAGE_DELIVER")){ 
											//메세지 전달일 경우만 기존 임직원 데이터 상위에 임직원 추가 한다. 
										
      out.write("\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t<colgroup>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t\t<col style=\"width:30px\"/>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t\t<col style=\"width:105px\" />\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t\t<col style=\"width:85px\" />\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t\t<col />\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t</colgroup>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t<tbody>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t</tbody>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t</table>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<table id=\"tbl1\">\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t");
} 
      out.write("\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t<colgroup>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t\t<col style=\"width:30px\"/>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t\t<col style=\"width:105px\" />\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t\t<col style=\"width:85px\" />\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t\t<col />\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t</colgroup>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t<tbody>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t");

										if(memberTargetList != null && memberTargetList.size() != 0){
											for(int i=0; memberTargetList.size()>i;i++){
												MemberVO memberVO = new MemberVO();
												memberVO = memberTargetList.get(i);
										
      out.write("\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t\t<td><input type='checkbox' id='emp_no' name='emp_no' value='");
      out.print(memberVO.getEmp_no() );
      out.write('|');
      out.print(StringUtil.nvl(memberVO.getEmp_ko_nm()) );
      out.write('|');
      out.print(StringUtil.nvl(memberVO.getDept_ko_nm()) );
      out.write('|');
      out.print(StringUtil.nvl(memberVO.getGrad_ko_nm()) );
      out.write("' onclick='javascript:allChkStatus(this);' ");
if(type.equals("SHARE") || type.equals("MESSAGE_DELIVER")){ 
      out.write("disabled");
}else{ 
      out.write("checked");
} 
      out.write("></td>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t\t<td>");
      out.print(StringUtil.nvl(memberVO.getDept_ko_nm()) );
      out.write("</td>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t\t<td>");
      out.print(StringUtil.nvl(memberVO.getGrad_ko_nm()) );
      out.write("</td>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t\t<td class=\"br_none\">");
      out.print(StringUtil.nvl(memberVO.getEmp_ko_nm()) );
      out.write("</td>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t");

											}
										}else{
										
      out.write("\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t\t");
if(!(type.equals("AUTH") || type.equals("SHARE") || type.equals("MESSAGE") || type.equals("MESSAGE_DELIVER"))){ 
      out.write("\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t\t\t\t<td><input type='checkbox' id='emp_no' name='emp_no' value='");
      out.print(memberSessionVO.getEmp_no() );
      out.write('|');
      out.print(StringUtil.nvl(memberSessionVO.getEmp_ko_nm()) );
      out.write('|');
      out.print(StringUtil.nvl(memberSessionVO.getDept_ko_nm()) );
      out.write('|');
      out.print(StringUtil.nvl(memberSessionVO.getGrad_ko_nm()) );
      out.write("' onclick='javascript:allChkStatus(this);' checked></td>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t\t\t\t<td>");
      out.print(memberSessionVO.getDept_ko_nm() );
      out.write("</td>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t\t\t\t<td>");
      out.print(memberSessionVO.getGrad_ko_nm() );
      out.write("</td>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t\t\t\t<td class=\"br_none\">");
      out.print(memberSessionVO.getEmp_ko_nm() );
      out.write("</td>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t\t");
} 
      out.write("\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t");
	
										} 
      out.write("\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t</tbody>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t</table>\r\n");
      out.write("\t\t\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t\t</td>\r\n");
      out.write("\t\t\t\t\t\t</tr>\t\t\t\t\t\t\t\t\t\t\r\n");
      out.write("\t\t\t\t\t</tbody>\r\n");
      out.write("\t\t\t\t</table>\r\n");
      out.write("\t\t\t</div>\r\n");
      out.write("\t\t\t<div class=\"wrap_btn\">\r\n");
      out.write("\t\t\t\t<button class=\"type_01\" type=\"button\" onClick=\"javascript:goTop(); return false;\">선택완료</button>\r\n");
      out.write("\t\t\t\t<button class=\"type_01\" type=\"button\" onClick=\"javascript:delRow(); return false;\">선택삭제</button>\r\n");
      out.write("\t\t\t</div>\t\t\t\r\n");
      out.write("\t\t</div>\r\n");
      out.write("\t</div>\r\n");
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
