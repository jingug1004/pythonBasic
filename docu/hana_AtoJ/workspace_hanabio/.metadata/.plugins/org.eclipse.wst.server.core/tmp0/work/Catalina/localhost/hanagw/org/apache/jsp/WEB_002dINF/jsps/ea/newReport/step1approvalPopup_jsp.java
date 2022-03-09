/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: Apache Tomcat/7.0.55
 * Generated at: 2015-06-16 00:57:28 UTC
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
import com.hanaph.gw.co.common.utils.RequestFilterUtil;
import com.hanaph.gw.co.fileAttach.vo.FileAttachVO;
import com.hanaph.gw.ea.mgrDocuInfo.vo.DocumentInfoVO;
import com.hanaph.gw.ea.newReport.vo.ApprovalMasterVO;
import com.hanaph.gw.ea.newReport.vo.AccidentVO;
import com.hanaph.gw.pe.member.vo.MemberVO;
import javax.servlet.http.HttpSession;

public final class step1approvalPopup_jsp extends org.apache.jasper.runtime.HttpJspBase
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
	
	
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");

	/* 첨부파일 */
	List<FileAttachVO> attachList = (List<FileAttachVO>)request.getAttribute("attachList");
	/* 문서 기본정보 */
	DocumentInfoVO documentInfoDetail = (DocumentInfoVO)request.getAttribute("documentInfoDetail");
	/* 결재마스터 */
	ApprovalMasterVO approvalMasterVO = (ApprovalMasterVO)request.getAttribute("approvalMasterVO");
	
	String make_dt = StringUtil.nvl((String)request.getAttribute("make_dt")); //작성일시
	String dept_ko_nm = StringUtil.nvl((String)request.getAttribute("dept_ko_nm")); //작성부서
	String emp_ko_nm = StringUtil.nvl((String)request.getAttribute("emp_ko_nm")); //작성자
	String docu_cd = StringUtil.nvl((String)request.getAttribute("docu_cd")); //문서코드
	String docu_nm = StringUtil.nvl((String)request.getAttribute("docu_nm")); //문서이름
	String docu_seq = StringUtil.nvl((String)request.getAttribute("docu_seq")); //문서번호
	String approval_seq = StringUtil.nvl((String)request.getAttribute("approval_seq")); //결재번호
	String security_yn = StringUtil.nvl(documentInfoDetail.getSecurity_yn(),"N"); //대외비여부
	String decide_yn = StringUtil.nvl(documentInfoDetail.getDecide_yn(),"N"); //전결가능
	String includePage = "step1"+docu_cd+".jsp";
	
	if(approvalMasterVO == null){
		approvalMasterVO = new ApprovalMasterVO();
	}
	String str = "";
	if("".equals(approval_seq)){
		str = "저장";
	}else{
		str = "수정";
	}
	
	/*사고보고서  보고 날짜 셋팅*/
	AccidentVO accidentVO = (AccidentVO)request.getAttribute("accidentVO");
	if(accidentVO == null){
		accidentVO = new AccidentVO();
	}
	String bogo_day = StringUtil.nvl(accidentVO.getBogo_day());		//보고일
	String bogo_month = StringUtil.nvl(accidentVO.getBogo_month()); //보고월
	String bogo_year = StringUtil.nvl(accidentVO.getBogo_year());	//보고년
	

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
      out.write("\t<script type=\"text/javascript\">\r\n");
      out.write("\t\r\n");
      out.write("\tvar subtmit=false;\t\t//submit flag\r\n");
      out.write("\t\r\n");
      out.write("\t/* 파일업로드 */\r\n");
      out.write("\t$(function(){\r\n");
      out.write("\t\t// 파일업로드 drag & drop 초기화 \r\n");
      out.write("\t\tif (window.File && window.FileList && window.FileReader) {\r\n");
      out.write("\t\t\tfileDragInit();\r\n");
      out.write("\t\t}\r\n");
      out.write("\t});\r\n");
      out.write("\t\r\n");
      out.write("\t/**\r\n");
      out.write("\t * 삭제한 첨부파일 정보//삭제한 첨부파일 정보\r\n");
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
      out.write("\t * ex8파일업로드 iframe\r\n");
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
      out.write("\t * ex8파입업로드 콜백 함수\r\n");
      out.write("\t * @param fileNum\r\n");
      out.write("\t */\r\n");
      out.write("\tfunction fileUploadByIframeCallback(fileNum){\r\n");
      out.write("\t\t$('#fileNum').val($('#fileNum').val()+fileNum+\",\");\r\n");
      out.write("\t\tvar $frm = $('#frm');\r\n");
      out.write("\t\t$frm.attr('action','");
      out.print(GROUP_ROOT);
      out.write("/ea/newReport/insertDocument.do');\r\n");
      out.write("\t\t$frm.removeAttr('target');\r\n");
      out.write("\t\t$frm.removeAttr('enctype');\r\n");
      out.write("\t\t$frm[0].submit();\r\n");
      out.write("\t}\r\n");
      out.write("\t\r\n");
      out.write("\t/**\r\n");
      out.write("\t * step1 저장후 step2로 이동 \r\n");
      out.write("\t */\r\n");
      out.write("\tfunction goStep2(){\r\n");
      out.write("\t\tif(saveStep1() && subtmit==false){\r\n");
      out.write("\t\t\tif(confirm(\"");
      out.print(str);
      out.write(" 하시겠습니까? STEP2로 넘어갑니다.\") == true){\r\n");
      out.write("\t\t\t\tif (window.File && window.FileList && window.FileReader) {\r\n");
      out.write("\t\t\t\t\tvar fileCount = fileUploadCnt();\r\n");
      out.write("\t\t\t\t\tif(fileCount > 0) {\r\n");
      out.write("\t\t\t\t\t\tloadingBar();\r\n");
      out.write("\t\t\t\t\t\tuploadNext();\r\n");
      out.write("\t\t\t\t\t}\r\n");
      out.write("\t\t\t\t\t$('#pro').bPopup().close();\r\n");
      out.write("\t\t\t\t\tdocument.frm.action='");
      out.print(GROUP_ROOT);
      out.write("/ea/newReport/insertDocument.do';\r\n");
      out.write("\t\t\t\t\tdocument.frm.submit();\r\n");
      out.write("\t\t\t\t}else{\r\n");
      out.write("\t\t\t\t\tif($(\"#fileselect\").val() != \"\"){\r\n");
      out.write("\t\t\t\t\t\tfileUploadByIframe('frm', '/hanagw/approvalMultiFileUploadAction.do', 'fileselect[]', 'fileUploadByIframeCallback');\r\n");
      out.write("\t\t\t\t\t} else {\r\n");
      out.write("\t\t\t\t\t\tvar $frm = $('#frm');\r\n");
      out.write("\t\t\t\t\t\t$frm.attr('action','");
      out.print(GROUP_ROOT);
      out.write("/ea/newReport/insertDocument.do');\r\n");
      out.write("\t\t\t\t\t\t$frm.removeAttr('target');\r\n");
      out.write("\t\t\t\t\t\t$frm.removeAttr('enctype');\r\n");
      out.write("\t\t\t\t\t\t$frm[0].submit();\r\n");
      out.write("\t\t\t\t\t}\r\n");
      out.write("\t\t\t\t}\r\n");
      out.write("\t\t\t\t\r\n");
      out.write("\t\t\t\tsubtmit=true;\r\n");
      out.write("\t\t\t\t\r\n");
      out.write("\t\t\t}\r\n");
      out.write("\t\t}\r\n");
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
      out.write("\t * 레이어 팝업 닫기\r\n");
      out.write("\t * @param id\r\n");
      out.write("\t */\r\n");
      out.write("\tfunction layerClose(id){ \r\n");
      out.write("\t\tvar layerID = \"#\"+id;\r\n");
      out.write("\t\t$(layerID).bPopup().close();\r\n");
      out.write("\t}\r\n");
      out.write("\t\r\n");
      out.write("\t/**\r\n");
      out.write("\t * 파일업로드\r\n");
      out.write("\t */\r\n");
      out.write("\tfunction uploadNext() {\r\n");
      out.write("\t\tfor (var int = 0; int < file_list.length; int++) {\r\n");
      out.write("\t\t\tvar nextFile = file_list[int];\r\n");
      out.write("\t\t\tmultiUploadFile(nextFile);\t//ajax 파일업로드\r\n");
      out.write("\t\t}\r\n");
      out.write("\t}\r\n");
      out.write("\t\r\n");
      out.write("\t/**\r\n");
      out.write("\t * 파일업로드\r\n");
      out.write("\t * @param file\r\n");
      out.write("\t */\r\n");
      out.write("\tfunction multiUploadFile(file) {\r\n");
      out.write("\t\t\r\n");
      out.write("\t\tvar fd = new FormData(); // https://hacks.mozilla.org/2011/01/how-to-develop-a-html5-image-uploader/\r\n");
      out.write("\t\tfd.append(\"image\", file);\r\n");
      out.write("\t\tfd.append(\"approval_seq\", $('#approval_seq').val());\r\n");
      out.write("\t\tfd.append(\"filePathKind\", $('#filePathKind').val());\r\n");
      out.write("\t\t\r\n");
      out.write("\t\tvar xhr = new XMLHttpRequest();\r\n");
      out.write("\t\txhr.open(\"POST\", \"/hanagw/approvalMultiFileUploadAction.do\", false);\r\n");
      out.write("\t\txhr.onload = function() {\r\n");
      out.write("\t\t\ttry {\r\n");
      out.write("\t\t\t\tvar res = JSON.parse(xhr.responseText);\t\t\t// 업로드한 파일과 게시글 매핑시 사용함.\r\n");
      out.write("\t\t\t\tvar fileNum = res.file_seq;\r\n");
      out.write("\t\t\t} catch (e) {\r\n");
      out.write("\t\t\t\tconsole.log(e);\r\n");
      out.write("\t\t\t\treturn this.onError();\r\n");
      out.write("\t\t\t}\r\n");
      out.write("\t\t\t$('#fileNum').val($('#fileNum').val()+fileNum+\",\");\r\n");
      out.write("\t\t};\r\n");
      out.write("\t\txhr.onerror = function() {};\r\n");
      out.write("\t\txhr.upload.onprogress = function(e) {\r\n");
      out.write("\t\t\t//this.setProgress(e.loaded / e.total);\r\n");
      out.write("\t\t};\r\n");
      out.write("\t\txhr.send(fd);\r\n");
      out.write("\t}\r\n");
      out.write("\t\r\n");
      out.write("\t</script>\r\n");
      out.write("</head>\r\n");
      out.write("<body>\r\n");
      out.write("<div class=\"wrap_pop_bg\">\r\n");
      out.write("\t<div class=\"wrap_statement_overtime\">\r\n");
      out.write("\t\t<div class=\"top\">\r\n");
      out.write("\t\t\t<h3>신규문서작성<span> STEP1</span></h3>\r\n");
      out.write("\t\t\t<button type=\"button\" onclick=\"window.close();\" class=\"close\"><span class=\"blind\">닫기</span></button>\r\n");
      out.write("\t\t\t<div>\r\n");
      out.write("\t\t\t\t<div class=\"fr\">\r\n");
      out.write("\t\t\t\t\t<button class=\"type2\" type=\"button\" onclick=\"javascript:goStep2();\">결재라인지정</button>\r\n");
      out.write("\t\t\t\t</div>\r\n");
      out.write("\t\t\t</div>\r\n");
      out.write("\t\t</div>\r\n");
      out.write("\t\t<div class=\"inner\">\r\n");
      out.write("\t\t\t<div class=\"cont_box cont_table06\">\r\n");
      out.write("\t\t\t\t<div class=\"pop_tit\">\r\n");
      out.write("\t\t\t\t\t<span class=\"tit\"><em>STEP 1</em>. 문서내용작성</span>\r\n");
      out.write("\t\t\t\t\t<p>해당 문서 내용을 작성하고 완료가 되면 결재라인 지정 버튼을 클릭하시기 바랍니다. STEP2로 이동합니다. </p>\r\n");
      out.write("\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t<form id=\"frm\" name=\"frm\" method=\"post\">\r\n");
      out.write("\t\t\t\t<!--\t파일첨부기능시 이름 변경하면 안됨 -->\r\n");
      out.write("\t\t\t\t<input type=\"hidden\" id=\"delFileSeq\" name=\"delFileSeq\" />\r\n");
      out.write("\t\t\t\t<input type=\"hidden\" id=\"filePathKind\" name=\"filePathKind\" value=\"");
      out.print(docu_cd );
      out.write("\"/>\r\n");
      out.write("\t\t\t\t<!--\t//파일첨부기능시 이름 변경하면 안됨 -->\r\n");
      out.write("\t\t\t\t<input type=\"hidden\" id=\"docu_cd\" name=\"docu_cd\" value=\"");
      out.print(docu_cd );
      out.write("\" />\r\n");
      out.write("\t\t\t\t<input type=\"hidden\" id=\"docu_seq\" name=\"docu_seq\" value=\"");
      out.print(docu_seq );
      out.write("\" />\r\n");
      out.write("\t\t\t\t<input type=\"hidden\" id=\"decide_yn\" name=\"decide_yn\" value=\"");
      out.print(decide_yn );
      out.write("\" />\r\n");
      out.write("\t\t\t\t<input type=\"hidden\" id=\"make_dt\" name=\"make_dt\" value=\"");
      out.print(make_dt );
      out.write("\" />\r\n");
      out.write("\t\t\t\t<input type=\"hidden\" id=\"approval_seq\" name=\"approval_seq\" value=\"");
      out.print(approval_seq );
      out.write("\" />\r\n");
      out.write("\t\t\t\t\r\n");
      out.write("\t\t\t\t<h4>\r\n");
      out.write("\t\t\t\t\t");
if("Y".equals(security_yn)){ 
      out.write("\r\n");
      out.write("\t\t\t\t\t<span class=\"check\">\r\n");
      out.write("\t\t\t\t\t\t<input type=\"checkbox\" name=\"security_yn\" id=\"security_yn\" ");
if("Y".equals(approvalMasterVO.getSecurity_yn())){
      out.write("checked");
}
      out.write(" />\r\n");
      out.write("\t\t\t\t\t\t<label for=\"security_yn\">대외비입니다.</label>\r\n");
      out.write("\t\t\t\t\t</span>\r\n");
      out.write("\t\t\t\t\t");
} 
      out.write("\r\n");
      out.write("\t\t\t\t\t<strong>");
      out.print(documentInfoDetail.getDocu_nm() );
      out.write("</strong>\r\n");
      out.write("\t\t\t\t\t");
if("Y".equals(decide_yn)){ 
      out.write("\r\n");
      out.write("\t\t\t\t\t<span>전결 가능합니다.</span>\r\n");
      out.write("\t\t\t\t\t");
} 
      out.write("\r\n");
      out.write("\t\t\t\t</h4>\r\n");
      out.write("\t\t\t\t<table>\r\n");
      out.write("\t\t\t\t\t<colgroup>\r\n");
      out.write("\t\t\t\t\t\t<col class=\"cb_col1\">\r\n");
      out.write("\t\t\t\t\t\t<col style=\"width:260px\">\r\n");
      out.write("\t\t\t\t\t\t<col style=\"width:86px\">\r\n");
      out.write("\t\t\t\t\t\t<col style=\"width:260px\">\r\n");
      out.write("\t\t\t\t\t</colgroup>\r\n");
      out.write("\t\t\t\t\t<tbody>\r\n");
      out.write("\t\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t\t\t<th>문서번호</th>\r\n");
      out.write("\t\t\t\t\t\t\t<td>");
if(!"".equals(approval_seq)){
      out.print(approval_seq);
}else{
      out.print(docu_cd );
      out.print(make_dt.replace("-","").substring(0,8));
      out.write('X');
      out.write('X');
      out.write('X');
}
      out.write("</td>\r\n");
      out.write("\t\t\t\t\t\t\t\r\n");
      out.write("\t\t\t\t\t\t\t<th>작성일자</th>\r\n");
      out.write("\t\t\t\t\t\t\t<td>");
      out.print(make_dt.substring(0, 16) );
      out.write("</td>\r\n");
      out.write("\t\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t\t\t<th>작성부서</th>\r\n");
      out.write("\t\t\t\t\t\t\t<td>");
      out.print(dept_ko_nm );
      out.write("</td>\r\n");
      out.write("\t\t\t\t\t\t\t<th>작성자</th>\r\n");
      out.write("\t\t\t\t\t\t\t<td>");
      out.print(emp_ko_nm );
      out.write("</td>\r\n");
      out.write("\t\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t\t\t<th>제목</th>\r\n");
      out.write("\t\t\t\t\t\t\t<td colspan=\"3\" class=\"subject\"><input type=\"text\" name=\"subject\" id=\"subject\" value=\"");
      out.print(RequestFilterUtil.toHtmlTagReplace("", approvalMasterVO.getSubject()) );
      out.write("\" \r\n");
      out.write("\t\t\t\t\t\t\t\t");
if("E01004".equals(docu_cd)){
      out.write("readonly=\"readonly\"");
}
      out.write("/>\r\n");
      out.write("\t\t\t\t\t\t\t</td>\r\n");
      out.write("\t\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t\t\t<td colspan=\"4\" class=\"pdn\">\r\n");
      out.write("\t\t\t\t\t\t\t\t");
      org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, includePage, out, false);
      out.write("\r\n");
      out.write("\t\t\t\t\t\t\t</td>\r\n");
      out.write("\t\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t\t\t<th class=\"append_tit\">\r\n");
      out.write("\t\t\t\t\t\t\t\t첨부파일\r\n");
      out.write("\t\t\t\t\t\t\t\t");
if("E01011".equals(docu_cd)){ 
      out.write("\r\n");
      out.write("\t\t\t\t\t\t\t\t<span class=\"ref\">※ 첨부서류 :<br />&nbsp;&nbsp;&nbsp;거래처카드</span>\r\n");
      out.write("\t\t\t\t\t\t\t\t");
} 
      out.write("\r\n");
      out.write("\t\t\t\t\t\t\t</th>\r\n");
      out.write("\t\t\t\t\t\t\t<td colspan=\"7\" class=\"append_file br_none\">\r\n");
      out.write("\t\t\t\t\t\t\t\t<div class=\"append_search\">\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<input type=\"file\" id=\"fileselect\" name=\"fileselect[]\" multiple=\"multiple\" />\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<input type=\"hidden\" name=\"fileNum\" id=\"fileNum\" />\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<div id=\"filedrag\">마우스로 파일을 끌어 놓으세요.</div>\r\n");
      out.write("\t\t\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t\t\t<div id=\"messages\">\r\n");
      out.write("\t\t\t\t\t\t\t\t");

									if(attachList!=null){
										for(int i=0; i<attachList.size(); i++){
											FileAttachVO fileAttachvo = new FileAttachVO();
											fileAttachvo = attachList.get(i);
										
										
      out.write("\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t<p id=\"file_");
      out.print(fileAttachvo.getFile_seq());
      out.write('"');
      out.write('>');
      out.print(fileAttachvo.getOrigin_file_nm() );
      out.write(' ');
      out.write('(');
      out.print(fileAttachvo.getFile_size() );
      out.write("byte)\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t<button type=\"button\" class=\"type_01 ml15\" onclick=\"javascript:deleteFileAttach('");
      out.print(fileAttachvo.getFile_seq());
      out.write("');\">삭제</button>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t</p>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t");
 
										}
									}
								
								
      out.write("\r\n");
      out.write("\t\t\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t\t\t<div id=\"pro\"></div>\r\n");
      out.write("\t\t\t\t\t\t\t</td>\r\n");
      out.write("\t\t\t\t\t\t</tr>\t\t\t\t\t\t\t\t\r\n");
      out.write("\t\t\t\t\t</tbody>\r\n");
      out.write("\t\t\t\t</table>\r\n");
      out.write("\t\t\t\t");
if("E01011".equals(docu_cd)){ 
      out.write("\r\n");
      out.write("\t\t\t\t<div class=\"box_accident_report\">\r\n");
      out.write("\t\t\t\t\t<input type=\"text\" name=\"bogo_year\" id=\"bogo_year\" class=\"ipt_year\" value=\"");
      out.print(bogo_year);
      out.write("\" />년 <input type=\"text\" name=\"bogo_month\" id=\"bogo_month\" class=\"ipt_month\" value=\"");
      out.print(bogo_month);
      out.write("\" />월 <input type=\"text\" name=\"bogo_day\" id=\"bogo_day\" class=\"ipt_day\" value=\"");
      out.print(bogo_day);
      out.write("\" />일\r\n");
      out.write("\t\t\t\t\t<p>상기와 같이 보고합니다.</p>\r\n");
      out.write("\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t");
} 
      out.write("\r\n");
      out.write("\t\t\t\t</form>\r\n");
      out.write("\t\t\t</div>\r\n");
      out.write("\t\t</div>\r\n");
      out.write("\t\t<div id='pro' style='display:none; width:auto; height:auto; '>\r\n");
      out.write("\t\t\t<img alt='loading' src='/hanagw/asset/img/ajax-loader.gif' />\r\n");
      out.write("\t\t</div>\r\n");
      out.write("\t\t<div id='getMember' style='display:none; width:auto; height:auto; '>\r\n");
      out.write("\t\t\t<div class='member_content'></div> \r\n");
      out.write("\t\t</div>\r\n");
      out.write("\t\t<div id='pop_memberSearch' style='display:none; width:auto; height:auto; '>\r\n");
      out.write("\t\t\t<div class='member_content'></div> \r\n");
      out.write("\t\t</div>\r\n");
      out.write("\t</div>\t\r\n");
      out.write("</div>\r\n");
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
