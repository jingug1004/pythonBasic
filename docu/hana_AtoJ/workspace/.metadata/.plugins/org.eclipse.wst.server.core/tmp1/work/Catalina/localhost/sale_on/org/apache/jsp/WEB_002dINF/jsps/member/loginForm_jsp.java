/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: Apache Tomcat/7.0.55
 * Generated at: 2018-02-06 07:33:27 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.WEB_002dINF.jsps.member;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import com.hanaph.saleon.member.vo.LoginUserVO;
import com.hanaph.saleon.common.utils.StringUtil;
import com.hanaph.saleon.common.utils.WebUtil;

public final class loginForm_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final javax.servlet.jsp.JspFactory _jspxFactory =
          javax.servlet.jsp.JspFactory.getDefaultFactory();

  private static java.util.Map<java.lang.String,java.lang.Long> _jspx_dependants;

  static {
    _jspx_dependants = new java.util.HashMap<java.lang.String,java.lang.Long>(4);
    _jspx_dependants.put("/common/path.jsp", Long.valueOf(1430124365934L));
    _jspx_dependants.put("/include/footer.jsp", Long.valueOf(1421734197738L));
    _jspx_dependants.put("/common/sessionCheck.jsp", Long.valueOf(1430193978042L));
    _jspx_dependants.put("/include/headLoginForm.jsp", Long.valueOf(1429838584176L));
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
      out.write('\r');
      out.write('\n');
      out.write('\r');
      out.write('\n');
      out.write('\r');
      out.write('\n');
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");

	LoginUserVO loginUserVO = (LoginUserVO) session.getAttribute("onlineUser");	//세션생성 세션에서 emp_code를 가져온다.
	
	String userEmpCode  = "";		// 사원 코드
	String userEmpName  = "";		// 사원 이름
	String userEmpGb    = "";		// 사원 구분 
	String userDeptCode = "";		// 부서코드
	String userDeptName = "";		// 부서명
	String userGradeName = "";		// 사원 Grade Name
	String userDeptCd = "";			//hanahr 부서정보
	String userAssgnCd = "";			//직책 코드
	String userAssgnName = "";			//직책 코드명
	/*
	 * 로그인 정보가 있는경우
	 */
	if(loginUserVO != null){
		userEmpCode = StringUtil.nvl(loginUserVO.getEmp_code());    	// 사용자코드
		userEmpName = StringUtil.nvl(loginUserVO.getEmp_name());		// 사용자명
		userEmpGb = StringUtil.nvl(loginUserVO.getEmp_gb());			// 사용자 구분 코드
		userDeptCode = StringUtil.nvl(loginUserVO.getDept_code());  	// 부서 코드
		userDeptName = StringUtil.nvl(loginUserVO.getDept_name());		// 부서 명
		userGradeName = StringUtil.nvl(loginUserVO.getGrade_name());	// 사용자 부서명
		userDeptCd = StringUtil.nvl(loginUserVO.getDept_cd());  	    // 부서 코드
		userAssgnCd = StringUtil.nvl(loginUserVO.getAssgn_cd());  	    // 직책 코드
		userAssgnName = StringUtil.nvl(loginUserVO.getAssgn_name());  	// 직책 코드명
	}
	
	/* 현재 URI에 해당하는 프로그램 No(pgm_no) */
	String currPgmNoByUri = request.getAttribute("pgm_no") == null ? "" : (String) request.getAttribute("pgm_no");

      out.write('\r');
      out.write('\n');
      out.write('\r');
      out.write('\n');

	com.hanaph.saleon.common.utils.Environment env = new com.hanaph.saleon.common.utils.Environment();	//환경 변수
	
	String HTTPS_ONLINE_WEB_ROOT = env.getValue("https.web_root_dir.url");
	String HTTPS_ONLINE_ROOT = env.getValue("https.root_dir.url");
	String ONLINE_WEB_ROOT = env.getValue("web_root_dir.url");		//URL 프로토콜, 도메인, 포트, 서브컨텍스트명, web디렉토리
	String ONLINE_ROOT = env.getValue("root_dir.url");				//URL 프로토콜, 도메인, 포트, 서브컨텍스트명
	String JDBC_URL = env.getValue("jdbc.url");
	

      out.write('\r');
      out.write('\n');
      out.write('\r');
      out.write('\n');

	//http:// 로 들어왔을 경우 https://로 redirect
	String url = request.getRequestURL().toString();

	//http:// 로 시작하고, 운영 url일 경우에만 redirect
	if( url.startsWith("http://") && ( url.indexOf("saleon.") > 1) ) {
		response.sendRedirect(HTTPS_ONLINE_ROOT + "/member/login.do");
	}

      out.write("\r\n");
      out.write("<html lang=\"ko\">\r\n");
      out.write("<head>\r\n");
      out.write("\t");
      out.write('\r');
      out.write('\n');
      out.write("\r\n");
      out.write("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />\r\n");
      out.write("<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge,chrome=1\">\r\n");
      out.write("<script type=\"text/javascript\">\r\n");
      out.write("\tvar ONLINE_ROOT = \"");
      out.print(HTTPS_ONLINE_ROOT );
      out.write("\";\t\t/*환경변수*/\r\n");
      out.write("</script>\r\n");
      out.write("<script type=\"text/javascript\" src=\"");
      out.print(HTTPS_ONLINE_WEB_ROOT);
      out.write("/js/jquery-1.11.1.min.js\"></script>\r\n");
      out.write("<script type=\"text/javascript\" src=\"");
      out.print(HTTPS_ONLINE_WEB_ROOT);
      out.write("/js/jquery-ui-1.10.4.custom.js\"></script>\r\n");
      out.write("<script type=\"text/javascript\" src=\"");
      out.print(HTTPS_ONLINE_WEB_ROOT);
      out.write("/js/jquery.easyui.min.js\"></script>\r\n");
      out.write("<script type=\"text/javascript\" src=\"");
      out.print(HTTPS_ONLINE_WEB_ROOT);
      out.write("/js/common.js\" charset=\"utf-8\"></script>\r\n");
      out.write("<script type=\"text/javascript\" src=\"");
      out.print(HTTPS_ONLINE_WEB_ROOT);
      out.write("/js/default.js\"></script>\r\n");
      out.write("<script type=\"text/javascript\" src=\"");
      out.print(HTTPS_ONLINE_WEB_ROOT);
      out.write("/js/placeholders.js\"></script>\r\n");
      out.write("<link rel=\"stylesheet\" type=\"text/css\" href=\"");
      out.print(HTTPS_ONLINE_WEB_ROOT);
      out.write("/css/easyui.css\" />\r\n");
      out.write("<link rel=\"stylesheet\" type=\"text/css\" href=\"");
      out.print(HTTPS_ONLINE_WEB_ROOT);
      out.write("/css/main.css\" />\r\n");
      out.write("<link rel=\"stylesheet\" type=\"text/css\" href=\"");
      out.print(HTTPS_ONLINE_WEB_ROOT);
      out.write("/css/jquery-ui.css\" />\r\n");
      out.write("<link rel=\"stylesheet\" type=\"text/css\" href=\"");
      out.print(HTTPS_ONLINE_WEB_ROOT);
      out.write("/css/smoothness/jquery-ui.theme.css\" />\r\n");
      out.write("<link rel=\"stylesheet\" type=\"text/css\" href=\"");
      out.print(HTTPS_ONLINE_WEB_ROOT);
      out.write("/css/fonts.css\" />\r\n");
      out.write("<link rel=\"stylesheet\" type=\"text/css\" href=\"");
      out.print(HTTPS_ONLINE_WEB_ROOT);
      out.write("/css/gw_sample.css\">");
      out.write("\r\n");
      out.write("<title>하나제약 온라인주문</title>\r\n");
      out.write("<script type=\"text/javascript\">\r\n");
      out.write("\t/**\r\n");
      out.write("\t*\t로그인 \r\n");
      out.write("\t*/\r\n");
      out.write("\tfunction goLogin(){\r\n");
      out.write("\t\t\r\n");
      out.write("\t\tvar empCode = $(\"#empCode\").val();\r\n");
      out.write("\t\tvar password = $(\"#password\").val();\r\n");
      out.write("\t\t\r\n");
      out.write("\t\tif(empCode == \"\"){\r\n");
      out.write("\t\t\talert(\"아이디를 입력 하세요.\");\r\n");
      out.write("\t\t\t$(\"#empCode\").focus();\r\n");
      out.write("\t\t\treturn;\r\n");
      out.write("\t\t}\r\n");
      out.write("\t\t\r\n");
      out.write("\t\tif(password == \"\"){\r\n");
      out.write("\t\t\talert(\"비밀번호를 입력 하세요.\");\r\n");
      out.write("\t\t\t$(\"#password\").focus();\r\n");
      out.write("\t\t\treturn;\r\n");
      out.write("\t\t}\r\n");
      out.write("\t\t\r\n");
      out.write("\t\tsaveid();\r\n");
      out.write("\t\t\r\n");
      out.write("\t\t$.ajax({\r\n");
      out.write("\t           type:\"POST\",\r\n");
      out.write("\t           //data : \"empCode=\"+empCode+\"&password=\"+password,\r\n");
      out.write("\t           data : {empCode : empCode, password : password},\r\n");
      out.write("\t           url:\"");
      out.print(HTTPS_ONLINE_ROOT);
      out.write("/member/getLogin.do\",\r\n");
      out.write("\t           dataType:\"json\",\r\n");
      out.write("\t           \r\n");
      out.write("\t           success : function(data) {\r\n");
      out.write("\t        \t   \t//console.log(data.resultcode);\r\n");
      out.write("\t        \t   \tif(data.resultcode != 1){\r\n");
      out.write("\t        \t\t\tif(data.resultcode == 2){\r\n");
      out.write("\t        \t\t\t\talert(\"온라인 주문 회원 데이터가 존재하지 않습니다.\\n영업관리부에 문의 하세요.\");\r\n");
      out.write("\t        \t   \t\t}else if(data.resultcode == 3){\r\n");
      out.write("\t\t        \t\t\talert(\"비밀번호가 틀립니다.\"); \r\n");
      out.write("\t\t        \t\t\t$(\"#password\").val(\"\");\r\n");
      out.write("\t\t        \t\t\t$(\"#password\").focus();\r\n");
      out.write("\t        \t   \t\t}else if(data.resultcode == 4){\r\n");
      out.write("\t\t        \t\t\talert(\"중지거래처 입니다.\\n영업관리부에 문의 하세요.\");  \r\n");
      out.write("\t\t       \t\t\t}else{\r\n");
      out.write("\t        \t\t\t\talert(\"ID 또는 Password를 잘못 입력하셨습니다.\");\r\n");
      out.write("\t\t        \t\t\t$(\"#empCode\").val(\"\");\r\n");
      out.write("\t\t        \t\t\t$(\"#password\").val(\"\");\r\n");
      out.write("\t\t        \t\t\t$(\"#empCode\").focus();\r\n");
      out.write("\t        \t\t  \t}\r\n");
      out.write("\t        \t   }else{\r\n");
      out.write("\t        \t\t   /*alert(\"내부 점검 중입니다. 14시 이후에 주문 등록 해 주시기 바랍니다.\");\r\n");
      out.write("\t        \t\t   location.href = \"");
      out.print(ONLINE_ROOT );
      out.write("/member/logOut.do\";*/\r\n");
      out.write("\t        \t\t   location.href = \"");
      out.print(ONLINE_ROOT);
      out.write("/main.do\";\r\n");
      out.write("\t        \t   }\r\n");
      out.write("\t           },\r\n");
      out.write("\t           error : function(xhr, status, error) {\r\n");
      out.write("                   if (error == \"SyntaxError: Unexpected token < in JSON at position 0\" ||\r\n");
      out.write("                       error == \"SyntaxError: 유효하지 않은 문자입니다.\") {\r\n");
      out.write("                       alert(\"이미 로그인 사용자가 있습니다. 접속이 불가합니다.\");\r\n");
      out.write("                   } else {\r\n");
      out.write("                       alert(\"장애가 발생하였습니다.\\n잠시 후 다시 시도해주세요.\" + error);\r\n");
      out.write("                   }\r\n");
      out.write("\t           }\r\n");
      out.write("\t     });\r\n");
      out.write("\r\n");
      out.write("\t}\r\n");
      out.write("\t\r\n");
      out.write("\t/**\r\n");
      out.write("\t*\tcookie에 로그인 아이디 저장하기\r\n");
      out.write("\t*/\r\n");
      out.write("\tfunction saveid() {\r\n");
      out.write("\t\tif ($(\"input:checkbox[id='checksaveid']\").is(\":checked\")) {\t//쿠키 저장\r\n");
      out.write("\t\t\tCommons.setCookie(\"saveid\", $(\"#empCode\").val(), 1);\t// 기본적으로 1일동안 기억하게 함. 일수를 조절하려면 * 1에서 숫자를 조절하면 됨\r\n");
      out.write("\t\t} else {\r\n");
      out.write("\t\t\tCommons.setCookie(\"saveid\", $(\"#empCode\").val(), -1);\t//쿠키 삭제\r\n");
      out.write("\t\t}\r\n");
      out.write("\t}\r\n");
      out.write("\t\r\n");
      out.write("\t/* 화면의 모든 객체가 로드 되었을 때 */\r\n");
      out.write("\t$(window).load(function() { \r\n");
      out.write("\t\t\r\n");
      out.write("\t\tvar idCookie = Commons.getCookie(\"saveid\");\r\n");
      out.write("\t\t\r\n");
      out.write("\t\tif($.trim(idCookie) != \"\"){\r\n");
      out.write("\t\t\t$(\"#empCode\").val(idCookie);\r\n");
      out.write("\t\t\t$(\"#checksaveid\").attr('checked',true);\r\n");
      out.write("\t\t}\r\n");
      out.write("\t\t\r\n");
      out.write("\t});\r\n");
      out.write("\t\r\n");
      out.write("</script>\r\n");
      out.write("</head>\r\n");
      out.write("<body onkeydown=\"javascript:if(event.keyCode==13){goLogin(); return false;}\">\r\n");
      out.write("\t<div class=\"wrap_header\">\r\n");
      out.write("\t\t<div class=\"header\">\r\n");
      out.write("\t\t\t<h1><a href=\"#\"><img src=\"");
      out.print(HTTPS_ONLINE_ROOT);
      out.write("/asset/img/logo.jpg\" alt=\"\" /></a></h1>\r\n");
      out.write("\t\t</div>\r\n");
      out.write("\t</div>\r\n");
      out.write("\t<div class=\"wrap_con\">\r\n");
      out.write("\t\t<div class=\"wrap_login login_bg\">\r\n");
      out.write("\t\t\t<div class=\"login\">\r\n");
      out.write("\t\t\t\t<p class=\"slogan\">\r\n");
      out.write("\t\t\t\t\tFor the Best Medicine, Better Life\r\n");
      out.write("\t\t\t\t</p>\r\n");
      out.write("\t\t\t\t<h1>\r\n");
      out.write("\t\t\t\t\t하나제약 주식회사 <span>온라인주문</span>\r\n");
      out.write("\t\t\t\t</h1>   \r\n");
      out.write("\t\t\t\t<p class=\"txt\">\r\n");
      out.write("\t\t\t\t\t하나제약 주식회사 온라인주문입니다.<br />\r\n");
      out.write("\t\t\t\t\t온라인주문 시스템을 이용하시려면 로그인을 하시기 바랍니다.\r\n");
      out.write("\t\t\t\t</p>\r\n");
      out.write("\t\t\t\t<div class=\"wrap_log\">\r\n");
      out.write("\t\t\t\t\t<div class=\"wrap_input\">\r\n");
      out.write("\t\t\t\t\t\t<label>아이디</label> <input type=\"text\" name=\"empCode\" id=\"empCode\" /> <br />\r\n");
      out.write("\t\t\t\t\t\t<label>비밀번호</label> <input type=\"password\" name=\"password\" id=\"password\" /> <br />\r\n");
      out.write("\t\t\t\t\t\t<input type=\"checkbox\" class=\"save_id_chk\" name=\"checksaveid\" id=\"checksaveid\" onClick=\"javascript:saveid();\" /> <label for=\"checksaveid\" class=\"save_id\">아이디 저장</label>\r\n");
      out.write("\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t<div class=\"wrap_btn\">\r\n");
      out.write("\t\t\t\t\t\t<button type=\"button\" class=\"btn_login\" onClick=\"javascript:goLogin();\">LOGIN</button>\r\n");
      out.write("\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t<font color=\"red\">사용가능 브라우저<br />Internet Explorer8이상,Chrome,Firefox (Chrome 사용권장)</font>\r\n");
      out.write("\t\t\t</div>\r\n");
      out.write("\t\t</div>\r\n");
      out.write("\t</div>\r\n");
      out.write("\t");
      out.write('\r');
      out.write('\n');
      out.write("\r\n");
      out.write("<div class=\"wrap_foot\">\r\n");
      out.write("\t<div class=\"footer\">\r\n");
      out.write("\t\tCopyright ⓒ 2014 <span class=\"copy_fc\">HANA PHARM CO.</span>, LTD. All Right Reserved.\r\n");
      out.write("\t</div>\r\n");
      out.write("</div>\r\n");
      out.write("<!-- 엑셀 다운로드용 iframe -->\r\n");
      out.write("<iframe name=\"excelDownFrame\" frameBorder=\"0\" width=\"0\" height=\"0\" class=\"blind\"></iframe>\r\n");
      out.write("\r\n");
      out.write("</body>\r\n");
      out.write("</html>\r\n");
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
