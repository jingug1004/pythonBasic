/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: Apache Tomcat/7.0.55
 * Generated at: 2016-11-09 05:54:20 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.WEB_002dINF.jsps.of.message;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.util.List;
import com.hanaph.gw.of.message.vo.MessageVO;
import com.hanaph.gw.co.fileAttach.vo.FileAttachVO;
import com.hanaph.gw.pe.member.vo.MemberVO;
import javax.servlet.http.HttpSession;
import com.hanaph.gw.pe.member.vo.MemberVO;
import java.util.List;
import com.hanaph.gw.co.menu.vo.MenuVO;
import java.util.ArrayList;
import com.hanaph.gw.co.menu.vo.MenuVO;
import com.hanaph.gw.co.common.utils.StringUtil;
import java.util.List;

public final class messageWrite_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final javax.servlet.jsp.JspFactory _jspxFactory =
          javax.servlet.jsp.JspFactory.getDefaultFactory();

  private static java.util.Map<java.lang.String,java.lang.Long> _jspx_dependants;

  static {
    _jspx_dependants = new java.util.HashMap<java.lang.String,java.lang.Long>(7);
    _jspx_dependants.put("/include/head.jsp", Long.valueOf(1424677116691L));
    _jspx_dependants.put("/common/path.jsp", Long.valueOf(1429838623000L));
    _jspx_dependants.put("/include/footer.jsp", Long.valueOf(1418202584955L));
    _jspx_dependants.put("/include/lnb.jsp", Long.valueOf(1444971460463L));
    _jspx_dependants.put("/include/gnb.jsp", Long.valueOf(1444976197526L));
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
      out.write('\r');
      out.write('\n');
com.hanaph.gw.co.common.utils.Environment env = new com.hanaph.gw.co.common.utils.Environment();
	
	String HTTPS_GROUP_WEB_ROOT = env.getValue("https.web_root_dir.url");
	String HTTPS_GROUP_ROOT = env.getValue("https.root_dir.url");
	String GROUP_WEB_ROOT = env.getValue("web_root_dir.url");
	String GROUP_ROOT = env.getValue("root_dir.url");
	String JDBC_URL = env.getValue("jdbc.url");
	
	
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");

	List<FileAttachVO> attachList = (List<FileAttachVO>)request.getAttribute("attachList"); //첨부파일리스트

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
      out.write("\t<link rel=\"stylesheet\" type=\"text/css\" media=\"all\" href=\"");
      out.print(GROUP_WEB_ROOT);
      out.write("/css/styles.css\" />\r\n");
      out.write("\t<script type=\"text/javascript\" src=\"");
      out.print(GROUP_WEB_ROOT);
      out.write("/js/fileDrag.js\"></script>\r\n");
      out.write("\t<script type=\"text/javascript\" src=\"");
      out.print(GROUP_WEB_ROOT);
      out.write("/js/nicEdit/nicEdit.js\"></script>\r\n");
      out.write("\t<script type=\"text/javascript\">\r\n");
      out.write("\tvar subtmit=false;\t\t//submit flag\r\n");
      out.write("\t\r\n");
      out.write("\t/* 에디터폼 */\r\n");
      out.write("\tbkLib.onDomLoaded(function() {\r\n");
      out.write("\t\tnew nicEditor({maxHeight : 310}).panelInstance('contents');\r\n");
      out.write("\t});\r\n");
      out.write("\t\r\n");
      out.write("\t/* 파일업로드 */\r\n");
      out.write("\t$(function(){\r\n");
      out.write("\t\t/* 파일업로드 drag & drop 초기화 */ \r\n");
      out.write("\t\tif (window.File && window.FileList && window.FileReader) {\r\n");
      out.write("\t\t\tfileDragInit();\r\n");
      out.write("\t\t}\r\n");
      out.write("\t});\r\n");
      out.write("\t\r\n");
      out.write("\t/**\r\n");
      out.write("\t * 삭제한 첨부파일 정보\r\n");
      out.write("\t * @param seq\r\n");
      out.write("\t */\r\n");
      out.write("\tfunction deleteFileAttach(seq){\r\n");
      out.write("\t\tvar delFileSeq = $('#delFileSeq').val();\r\n");
      out.write("\t\r\n");
      out.write("\t\tif(delFileSeq==''){\r\n");
      out.write("\t\t\t $('#delFileSeq').val(seq);\r\n");
      out.write("\t\t}else{\r\n");
      out.write("\t\t\t$('#delFileSeq').val(delFileSeq+','+seq);\r\n");
      out.write("\t\t}\r\n");
      out.write("\t\t$('#file_'+seq).remove();\r\n");
      out.write("\t}\r\n");
      out.write("\t\r\n");
      out.write("\t/**\r\n");
      out.write("\t * 쪽지 보내기\r\n");
      out.write("\t */\r\n");
      out.write("\tfunction insertMessage(){\r\n");
      out.write("\t\t\r\n");
      out.write("\t\tnicEditors.findEditor('contents').saveContent();\r\n");
      out.write("\t\tvar editContent = nicEditors.findEditor('contents').getContent();\r\n");
      out.write("\t\tif($(\"#subject\").val() == \"\"){\r\n");
      out.write("\t\t\talert(\"제목을 입력해 주세요.\");\r\n");
      out.write("\t\t\treturn;\r\n");
      out.write("\t\t}else if(formCheck.getByteLength(document.frm.subject.value) > 100){\r\n");
      out.write("\t\t\talert(\"제목은 한글 50자 (영문 100자) 이상 입력할수 없습니다\");\r\n");
      out.write("\t\t\tdocument.frm.subject.focus();\r\n");
      out.write("\t\t\treturn;\r\n");
      out.write("\t\t}else if($.trim(editContent.replace(/&nbsp;/gi, '')) == \"\" || $.trim(editContent.replace(/&nbsp;/gi, '')) == \"<br>\"){\r\n");
      out.write("\t\t\talert(\"보내실 내용을 입력해 주세요.\");\r\n");
      out.write("\t\t\treturn;\r\n");
      out.write("\t\t}else if($(\"#target_empNos\").length == 0){\r\n");
      out.write("\t\t\talert(\"대상자를 추가해 주세요.\");\r\n");
      out.write("\t\t\tgetMember();\r\n");
      out.write("\t\t\treturn;\r\n");
      out.write("\t\t}\r\n");
      out.write("\t\t\r\n");
      out.write("\t\tif(subtmit==false){\r\n");
      out.write("\t\t\tif(confirm(\"쪽지를 보내시겠습니까?\") == true){\r\n");
      out.write("\t\t\t\tif (window.File && window.FileList && window.FileReader) {\r\n");
      out.write("\t\t\t\t\tvar fileCount = fileUploadCnt();\r\n");
      out.write("\t\t\t\t\tif(fileCount > 0) {\r\n");
      out.write("\t\t\t\t\t\tloadingBar();\r\n");
      out.write("\t\t\t\t\t\tuploadNext();\r\n");
      out.write("\t\t\t\t\t}\r\n");
      out.write("\t\t\t\t\t$('#pro').bPopup().close();\r\n");
      out.write("\t\t\t\t\t\r\n");
      out.write("\t\t\t\t\tdocument.frm.action=\"");
      out.print(GROUP_ROOT);
      out.write("/of/message/insertMessage.do\";\r\n");
      out.write("\t\t\t\t\tdocument.frm.submit();\r\n");
      out.write("\t\t\t\t} else {\r\n");
      out.write("\t\t\t\t\tif($(\"#fileselect\").val() != \"\"){\r\n");
      out.write("\t\t\t\t\t\tfileUploadByIframe('frm', '/hanagw/imgMultiFileUploadAction.do', 'fileselect[]', 'fileUploadByIframeCallback');\r\n");
      out.write("\t\t\t\t\t} else {\r\n");
      out.write("\t\t\t\t\t\tvar $frm = $('#frm');\r\n");
      out.write("\t\t\t\t\t\t$frm.attr('action','");
      out.print(GROUP_ROOT);
      out.write("/of/message/insertMessage.do');\r\n");
      out.write("\t\t\t\t\t\t$frm.removeAttr('target');\r\n");
      out.write("\t\t\t\t\t\t$frm.removeAttr('enctype');\r\n");
      out.write("\t\t\t\t\t\t$frm[0].submit();\r\n");
      out.write("\t\t\t\t\t}\r\n");
      out.write("\t\t\t\t\t\r\n");
      out.write("\t\t\t\t}\r\n");
      out.write("\t\t\t\tsubtmit=true;\r\n");
      out.write("\t\t\t}\r\n");
      out.write("\t\t}else{\r\n");
      out.write("\t\t\talert(\"보내는중 입니다.\");\r\n");
      out.write("\t\t}\r\n");
      out.write("\t}\r\n");
      out.write("\t\r\n");
      out.write("\t/**\r\n");
      out.write("\t * 익스8 파일업로드 iframe\r\n");
      out.write("\t * @param formId\r\n");
      out.write("\t * @param url\r\n");
      out.write("\t * @param fileParamName\r\n");
      out.write("\t * @param callbackFuncName\r\n");
      out.write("\t */\r\n");
      out.write("\tfunction fileUploadByIframe(formId, url, fileParamName, callbackFuncName){\r\n");
      out.write("\t\t\r\n");
      out.write("\t\tvar $iframe = $('<iframe></iframe>');\r\n");
      out.write("\t\t$iframe.attr('style', 'display:none');\r\n");
      out.write("\t\t$iframe.attr('id', 'fileUpload_Iframe');\r\n");
      out.write("\t\t$iframe.attr('name', 'fileUpload_Iframe');\r\n");
      out.write("\t\t$iframe.attr('frameBorder', '0');\r\n");
      out.write("\t\t$iframe.appendTo('body');\r\n");
      out.write("\t\t\r\n");
      out.write("\t\tvar $frm = $('#'+formId);\r\n");
      out.write("\t\t$frm.attr('action',url);\r\n");
      out.write("\t\t$frm.attr('target', 'fileUpload_Iframe');\r\n");
      out.write("\t\t$frm.attr('enctype', 'multipart/form-data');\r\n");
      out.write("\t\t$frm.append($('<input type=\"hidden\" value=\"'+$('#cd').val()+'\" name=\"cd\">'));\r\n");
      out.write("\t\t$frm.append($('<input type=\"hidden\" value=\"'+$('#filePathKind').val()+'\" name=\"filePathKind\">'));\r\n");
      out.write("\t\t$frm.append($('<input type=\"hidden\" value=\"'+fileParamName+'\" name=\"fileParamName\">'));\r\n");
      out.write("\t\t$frm.append($('<input type=\"hidden\" value=\"'+callbackFuncName+'\" name=\"callbackFuncName\">'));\r\n");
      out.write("\t\t$frm[0].submit();\r\n");
      out.write("\t}\r\n");
      out.write("\t\r\n");
      out.write("\t/**\r\n");
      out.write("\t * 익스8 파일업로드 iframe\r\n");
      out.write("\t * @param fileNum\r\n");
      out.write("\t */\r\n");
      out.write("\tfunction fileUploadByIframeCallback(fileNum){\r\n");
      out.write("\t\t$('#fileNum').val($('#fileNum').val()+fileNum+\",\");\r\n");
      out.write("\t\tvar $frm = $('#frm');\r\n");
      out.write("\t\t$frm.attr('action','");
      out.print(GROUP_ROOT);
      out.write("/of/message/insertMessage.do');\r\n");
      out.write("\t\t$frm.removeAttr('target');\r\n");
      out.write("\t\t$frm.removeAttr('enctype');\r\n");
      out.write("\t\t$frm[0].submit();\r\n");
      out.write("\t}\r\n");
      out.write("\t\r\n");
      out.write("\t/**\r\n");
      out.write("\t * 로딩바\r\n");
      out.write("\t */\r\n");
      out.write("\tfunction loadingBar(){\r\n");
      out.write("\t\t$('#pro').bPopup({\r\n");
      out.write("\t\t\tcontent:'iframe', //'ajax', 'iframe' or 'image'\r\n");
      out.write("\t\t\t//iframeAttr:'scrolling=\"no\" frameborder=\"0\" width=\"600px\" height=\"800px\"',\r\n");
      out.write("\t\t\tfollow: [true, true],\r\n");
      out.write("\t\t\tcontentContainer:'.member_content',\r\n");
      out.write("\t\t\tmodalClose: false,\r\n");
      out.write("            opacity: 0.6,\r\n");
      out.write("            positionStyle: 'fixed',\r\n");
      out.write("            modalColor : 'white'\r\n");
      out.write("\t\t});\r\n");
      out.write("\t}\r\n");
      out.write("\t\r\n");
      out.write("\t/**\r\n");
      out.write("\t * 대상가져오는 팝업\r\n");
      out.write("\t */\r\n");
      out.write("\tfunction getMember(){\r\n");
      out.write("\t\t$('#getMember').bPopup({\r\n");
      out.write("\t\t\tcontent:'iframe', //'ajax', 'iframe' or 'image'\r\n");
      out.write("\t\t\tiframeAttr:'scrolling=\"yes\" frameborder=\"0\" width=\"617px\" height=\"'+$(window).innerHeight()+'px\"',\r\n");
      out.write("\t\t\tfollow: [true, true],\r\n");
      out.write("\t\t\tcontentContainer:'.member_content',\r\n");
      out.write("\t\t\tmodalClose: true,\r\n");
      out.write("            opacity: 0.6,\r\n");
      out.write("            positionStyle: 'fixed',\r\n");
      out.write("\t\t\tloadUrl:'");
      out.print(GROUP_ROOT );
      out.write("/pe/member/memberSelectPopup.do?type=MESSAGE',\r\n");
      out.write("\t\t});\r\n");
      out.write("\t}\r\n");
      out.write("\t\r\n");
      out.write("\t/*\r\n");
      out.write("\t *기존 row 삭제 \r\n");
      out.write("\t */\r\n");
      out.write("\tfunction memberRemove(){\r\n");
      out.write(" \t\tvar html =\t'<tr><td colspan=\"3\" class=\"br_none\"><a href=\"#\" onclick=\"javascript:getMember();\"></a></td></tr>';\r\n");
      out.write(" \t\t    \r\n");
      out.write("\t \t$('#tbl tr').remove();\r\n");
      out.write("\t \t$(\"#tbl\").html(html);\r\n");
      out.write("\t}\r\n");
      out.write("\r\n");
      out.write("\t/* 반드시 addMemberRow 로 function 만들어 테이블 상황에 맞게 테이블 생성한다. */\r\n");
      out.write("\t/**\r\n");
      out.write("\t * @param obj\r\n");
      out.write("\t */\r\n");
      out.write("\tfunction addMemberRow(obj){\r\n");
      out.write("\t\tmemberRemove();\r\n");
      out.write("\t\t for(var i=0; i<obj.length; i++){\r\n");
      out.write("\t    \tif(obj[i].checked){\r\n");
      out.write("\t\t    \tvar objParam = obj[i].value;\r\n");
      out.write("\t\t\t\tvar memberListValueArr = objParam.split(\"|\");\r\n");
      out.write("\t\t\t\tvar objRow = document.getElementById('tbl'); // row 생성  \r\n");
      out.write("\r\n");
      out.write("\t\t\t   \tvar objTr1 = document.createElement('TR');\r\n");
      out.write("\t\t\t   \tobjRow.appendChild(objTr1); \r\n");
      out.write("\t\t\t   \t\r\n");
      out.write("\t\t\t   \tvar objText1 = document.createElement('TD');\r\n");
      out.write("\t\t\t   \tobjText1.setAttribute(\"width\", \"100\"); // 넓이\r\n");
      out.write("\t\t\t   \tobjText1.innerHTML=\"사번\";\r\n");
      out.write("\t\t\t   \tobjTr1.appendChild(objText1); \r\n");
      out.write("\t\t\t\t\r\n");
      out.write("\t\t\t   \tvar objInput1 = document.createElement('input');\r\n");
      out.write("\t\t\t   \tobjInput1.type = \"text\";\r\n");
      out.write("\t\t\t   \tobjInput1.name=\"target_empNos\";\r\n");
      out.write("\t\t\t   \tobjInput1.id=\"target_empNos\";\r\n");
      out.write("\t\t\t   \tobjInput1.value = memberListValueArr[0];\r\n");
      out.write("\t\t\t   \tobjTr1.appendChild(objInput1);  \r\n");
      out.write("\t\t\t\t\r\n");
      out.write("\t\t\t   \tvar objText2 = document.createElement('TD');\r\n");
      out.write("\t\t\t   \tobjText2.setAttribute(\"width\", \"100\"); // 넓이\r\n");
      out.write("\t\t\t   \tobjText2.innerHTML=\"부서\";\r\n");
      out.write("\t\t\t   \tobjTr1.appendChild(objText2); \r\n");
      out.write("\t\t\t\t\r\n");
      out.write("\t\t\t   \tvar objInput2 = document.createElement('input');\r\n");
      out.write("\t\t\t   \tobjInput2.type = \"text\";\r\n");
      out.write("\t\t\t   \tobjInput2.name=\"target_deptNm\";\r\n");
      out.write("\t\t\t   \tobjInput2.id=\"target_deptNm\";\r\n");
      out.write("\t\t\t   \tobjInput2.value = memberListValueArr[2];\r\n");
      out.write("\t\t\t   \tobjTr1.appendChild(objInput2);   \r\n");
      out.write("\t\t\t\t\r\n");
      out.write("\t\t\t   \tvar objText3 = document.createElement('TD');\r\n");
      out.write("\t\t\t   \tobjText3.setAttribute(\"width\", \"261\"); // 넓이\r\n");
      out.write("\t\t\t   \tobjText3.innerHTML=\"이름\";\r\n");
      out.write("\t\t\t   \tobjTr1.appendChild(objText3); \r\n");
      out.write("\t\t\t   \t\r\n");
      out.write("\t\t\t   \tvar objInput3 = document.createElement('input');\r\n");
      out.write("\t\t\t   \tobjInput3.type = \"text\";\r\n");
      out.write("\t\t\t   \tobjInput3.name=\"target_deptNm\";\r\n");
      out.write("\t\t\t   \tobjInput3.id=\"target_deptNm\";\r\n");
      out.write("\t\t\t   \tobjInput3.value = memberListValueArr[3];\r\n");
      out.write("\t\t\t   \tobjTr1.appendChild(objInput3);  \r\n");
      out.write("\r\n");
      out.write("\t\t\t   \tvar objTr2 = document.createElement('TR');\r\n");
      out.write("\t\t\t   \tobjRow.appendChild(objTr2); \r\n");
      out.write("\t\t\t\t\r\n");
      out.write("\t\t\t   \tvar objText4 = document.createElement('TD');\r\n");
      out.write("\t\t\t   \tobjText4.setAttribute(\"width\", \"261\"); // 넓이\r\n");
      out.write("\t\t\t   \tobjText4.innerHTML=\"휴대폰\";\r\n");
      out.write("\t\t\t   \tobjTr2.appendChild(objText4); \r\n");
      out.write("\t\t\t\t\r\n");
      out.write("\t\t\t   \tvar objInput4 = document.createElement('input');\r\n");
      out.write("\t\t\t   \tobjInput4.type = \"text\";\r\n");
      out.write("\t\t\t   \tobjInput4.name=\"target_empNos\";\r\n");
      out.write("\t\t\t   \tobjInput4.id=\"target_empNos\";\r\n");
      out.write("\t\t\t   \tobjInput4.value = \"010-222-3333\";\r\n");
      out.write("\t\t\t   \tobjTr2.appendChild(objInput4);  \r\n");
      out.write("\t\t\t\t\r\n");
      out.write("\t\t\t   \tvar objText5 = document.createElement('TD');\r\n");
      out.write("\t\t\t   \tobjText5.setAttribute(\"width\", \"261\"); // 넓이\r\n");
      out.write("\t\t\t   \tobjText5.innerHTML=\"회사전화\";\r\n");
      out.write("\t\t\t   \tobjTr2.appendChild(objText5); \r\n");
      out.write("\t\t\t\t\r\n");
      out.write("\t\t\t   \tvar objInput5 = document.createElement('input');\r\n");
      out.write("\t\t\t   \tobjInput5.type = \"text\";\r\n");
      out.write("\t\t\t   \tobjInput5.name=\"target_deptNm\";\r\n");
      out.write("\t\t\t   \tobjInput5.id=\"target_deptNm\";\r\n");
      out.write("\t\t\t   \tobjInput5.value = \"02-559-5792\";\r\n");
      out.write("\t\t\t   \tobjTr2.appendChild(objInput5);   \r\n");
      out.write("\t\t\t\t\r\n");
      out.write("\t\t\t   \tvar objText6 = document.createElement('TD');\r\n");
      out.write("\t\t\t   \tobjText6.setAttribute(\"width\", \"261\"); // 넓이\r\n");
      out.write("\t\t\t   \tobjText6.innerHTML=\"주소\";\r\n");
      out.write("\t\t\t   \tobjTr2.appendChild(objText6); \r\n");
      out.write("\t\t\t\t\r\n");
      out.write("\t\t\t   \tvar objInput6 = document.createElement('input');\r\n");
      out.write("\t\t\t   \tobjInput6.type = \"text\";\r\n");
      out.write("\t\t\t   \tobjInput6.name=\"target_deptNm\";\r\n");
      out.write("\t\t\t   \tobjInput6.id=\"target_deptNm\";\r\n");
      out.write("\t\t\t   \tobjInput6.value = \"서울시 강남구 역삼동 111\";\r\n");
      out.write("\t\t\t   \tobjTr2.appendChild(objInput6);    \r\n");
      out.write("\t\t\t\t\r\n");
      out.write("\t\t    }\r\n");
      out.write("\t    } \r\n");
      out.write("\t\t$('#getMember').bPopup().close();\r\n");
      out.write("\t\ttableScroll();\r\n");
      out.write("\t}\r\n");
      out.write("\t\r\n");
      out.write("\t/**\r\n");
      out.write("\t * 레이어 팝업 닫기\r\n");
      out.write("\t */\r\n");
      out.write("\tfunction layerClose(){ \r\n");
      out.write("\t\t$('#getMember').bPopup().close();\r\n");
      out.write("\t}\r\n");
      out.write("\t\r\n");
      out.write("\t/**\r\n");
      out.write("\t * 임직원 대상자 삭제\r\n");
      out.write("\t * @param r\r\n");
      out.write("\t */\r\n");
      out.write("\tfunction removeRow(r){ \r\n");
      out.write("\t \tvar i=r.parentNode.parentNode.rowIndex;\r\n");
      out.write("\t \tdocument.getElementById('tbl').deleteRow(i);\r\n");
      out.write("\t}\r\n");
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
      out.write("\t\t\t<div class=\"cont float_left\">\r\n");
      out.write("\t\t\t\t<h2>쪽지 쓰기</h2>\r\n");
      out.write("\t\t\t\t<div class=\"noticeboard_box\">\r\n");
      out.write("\t\t\t\t\t<div class=\"list_btn\">\r\n");
      out.write("\t\t\t\t\t\t<div class=\"list_button\">\r\n");
      out.write("\t\t\t\t\t\t\t<button type=\"button\" class=\"type_01\" onclick=\"javascript:insertMessage();\">보내기</button>\r\n");
      out.write("\t\t\t\t\t\t\t<button type=\"button\" class=\"type_01\" onclick=\"location.href='");
      out.print(GROUP_ROOT );
      out.write("/of/message/messageList.do?type=1'\">목록</button>\r\n");
      out.write("\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t<form id=\"frm\" name=\"frm\" method=\"post\">\r\n");
      out.write("\t\t\t\t\t<!--\t파일첨부기능시 이름 변경하면 안됨 -->\r\n");
      out.write("\t\t\t\t\t<input type=\"hidden\" name=\"uploadUrl\" id=\"uploadEditorUrl\" value=\"");
      out.print(GROUP_ROOT );
      out.write("/imgEditorFileUpload.do\" />\r\n");
      out.write("\t\t\t\t\t<input type=\"hidden\" id=\"cd\" name=\"cd\" value=\"O03000\" /> <!-- 쪽지 코드 변경되면 안됨 -->\r\n");
      out.write("\t\t\t\t\t<input type=\"hidden\" id=\"filePathKind\" name=\"filePathKind\" value=\"message\"/>\r\n");
      out.write("\t\t\t\t\t<!--\t//파일첨부기능시 이름 변경하면 안됨 -->\r\n");
      out.write("\t\t\t\t\t<input type=\"hidden\" id=\"emp_no\" name=\"emp_no\" />\r\n");
      out.write("\t\t\t\t\t<input type=\"hidden\" id=\"delFileSeq\" name=\"delFileSeq\" value=\"\"/>\r\n");
      out.write("\t\t\t\t\t<div class=\"cont_box cont_table06\">\r\n");
      out.write("\t\t\t\t\t\t<table>\r\n");
      out.write("\t\t\t\t\t\t\t<colgroup>\r\n");
      out.write("\t\t\t\t\t\t\t\t<col width=\"20%\" />\r\n");
      out.write("\t\t\t\t\t\t\t\t<col width=\"80%\" />\r\n");
      out.write("\t\t\t\t\t\t\t</colgroup>\r\n");
      out.write("\t\t\t\t\t\t\t\t<tbody>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t<th class=\"bt_none bc_re br_none\" colspan=\"2\">쪽지쓰기</th>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t<th class=\"\">제목</th>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t<td class=\"txt_field br_none\">\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t\t<input type=\"text\" id=\"subject\" name=\"subject\" />\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t\t<input type=\"text\" style=\"display: none;\"/>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t</td>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t<td colspan=\"2\" class=\"txt_box01 br_none\">\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t\t<textarea id=\"contents\" name=\"contents\"></textarea>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t</td>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t<th class=\"append_tit\">첨부파일</th>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t<td class=\"append_file br_none\">\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t\t<div class=\"append_search\">\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t\t\t<input type=\"file\" id=\"fileselect\" name=\"fileselect[]\" multiple=\"multiple\" />\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t\t\t<input type=\"hidden\" name=\"fileNum\" id=\"fileNum\" value=\"\"/>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t\t\t<!-- <button>찾아보기</button> -->\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t\t\t<div id=\"filedrag\">마우스로 파일을 끌어 놓으세요.</div>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t\t<div class=\"append_list\" id=\"messages\">\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t\t");

												if(attachList!=null){
													for(int i=0; i<attachList.size(); i++){
														FileAttachVO fileAttachvo = new FileAttachVO();
														fileAttachvo = attachList.get(i);
													
													
      out.write("\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t\t\t\t<p id=\"file_");
      out.print(fileAttachvo.getFile_seq());
      out.write('"');
      out.write('>');
      out.print(fileAttachvo.getOrigin_file_nm() );
      out.write(' ');
      out.write('(');
      out.print(fileAttachvo.getFile_size() );
      out.write("byte)\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t\t\t\t<button type=\"button\" class=\"type_01 ml15\" onclick=\"javascript:deleteFileAttach('");
      out.print(fileAttachvo.getFile_seq());
      out.write("');\">삭제</button>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t\t\t\t</p>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t\t\t\t\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t\t\t\t");
 
													}
												}
											
											
      out.write("\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t\t<div id=\"pro\"></div>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t</td>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t</tr>\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\r\n");
      out.write("\t\t\t\t\t\t\t\t</tbody>\r\n");
      out.write("\t\t\t\t\t\t</table>\r\n");
      out.write("\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t<div class=\"append_object_box\">\r\n");
      out.write("\t\t\t\t\t\t<button class=\"type_01\" type=\"button\" onclick=\"javascript:getMember();\">받는이</button>\r\n");
      out.write("\t\t\t\t\t\t<div class=\"cont_box cont_table07\">\r\n");
      out.write("\t\t\t\t\t\t\t<table id=\"tbl\">\r\n");
      out.write("\t\t\t\t\t\t\t\t<colgroup>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<col width=\"261px\"/>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<col width=\"\"/>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<col width=\"261px\"/>\r\n");
      out.write("\t\t\t\t\t\t\t\t</colgroup>\r\n");
      out.write("\t\t\t\t\t\t\t\t<thead>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t<th>부서</th>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t<th>직급</th>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t<th class=\"br_none\">이름</th>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t\t\t\t\t</thead>\r\n");
      out.write("\t\t\t\t\t\t\t\t<tbody>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t<td colspan=\"4\" class=\"br_none\">\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t\t<a href=\"#\" onclick=\"javascript:getMember();\">대상을 추가하세요.</a>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t</td>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t\t\t\t\t</tbody>\r\n");
      out.write("\t\t\t\t\t\t\t</table>\r\n");
      out.write("\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t</form>\r\n");
      out.write("\t\t\t\t\t<!--  popup \"note_box_staff.jsp\" 삽입 -->\r\n");
      out.write("\t\t\t\t</div>\r\n");
      out.write("\t\t\t</div>\r\n");
      out.write("\t\t</div>\r\n");
      out.write("\t</div>\r\n");
      out.write("\t<div id='pro' style='display:none; width:auto; height:auto; '>\r\n");
      out.write("\t\t<img alt='loading' src='/hanagw/asset/img/ajax-loader.gif' />\r\n");
      out.write("\t</div>\r\n");
      out.write("\t<div id='getMember' style='display:none; width:auto; height:auto; '>\r\n");
      out.write("\t\t<div class='member_content'></div> \r\n");
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
