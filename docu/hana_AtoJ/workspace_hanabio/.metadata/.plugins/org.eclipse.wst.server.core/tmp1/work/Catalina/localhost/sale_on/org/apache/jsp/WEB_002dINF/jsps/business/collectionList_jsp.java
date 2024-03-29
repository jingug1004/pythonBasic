/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: Apache Tomcat/7.0.55
 * Generated at: 2015-04-28 07:04:27 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.WEB_002dINF.jsps.business;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import com.hanaph.saleon.member.vo.LoginUserVO;
import com.hanaph.saleon.common.utils.StringUtil;
import com.hanaph.saleon.common.utils.WebUtil;
import com.hanaph.saleon.business.vo.BusinessVO;

public final class collectionList_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final javax.servlet.jsp.JspFactory _jspxFactory =
          javax.servlet.jsp.JspFactory.getDefaultFactory();

  private static java.util.Map<java.lang.String,java.lang.Long> _jspx_dependants;

  static {
    _jspx_dependants = new java.util.HashMap<java.lang.String,java.lang.Long>(4);
    _jspx_dependants.put("/include/head.jsp", Long.valueOf(1421734197738L));
    _jspx_dependants.put("/common/path.jsp", Long.valueOf(1430124365934L));
    _jspx_dependants.put("/include/footer.jsp", Long.valueOf(1421734197738L));
    _jspx_dependants.put("/common/sessionCheck.jsp", Long.valueOf(1430193978042L));
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
      out.write("\r\n");
      out.write("\r\n");

	/* 사용자 정보 셋팅(부서코드, pda 권한) */
	BusinessVO commonEmpInfo = new BusinessVO();
	commonEmpInfo = (BusinessVO)request.getAttribute("commonEmpInfo");
	
	boolean isNull = true; // null flag
	String dept_cd = ""; // 부서코드
	String pda_auth = ""; // pda 권한
	
	if(null != commonEmpInfo) { // null이 아닐 경우
		dept_cd = commonEmpInfo.getDept_cd();
		pda_auth = commonEmpInfo.getPda_auth();
		isNull = false;
	}

      out.write("\r\n");
      out.write("<!DOCTYPE html>\r\n");
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
      out.print(ONLINE_ROOT );
      out.write("\";\t\t/*환경변수*/\r\n");
      out.write("</script>\r\n");
      out.write("<script type=\"text/javascript\" src=\"");
      out.print(ONLINE_WEB_ROOT);
      out.write("/js/jquery-1.11.1.min.js\"></script>\r\n");
      out.write("<script type=\"text/javascript\" src=\"");
      out.print(ONLINE_WEB_ROOT);
      out.write("/js/jquery-ui-1.10.4.custom.js\"></script>\r\n");
      out.write("<script type=\"text/javascript\" src=\"");
      out.print(ONLINE_WEB_ROOT);
      out.write("/js/jquery.easyui.min.js\"></script>\r\n");
      out.write("<script type=\"text/javascript\" src=\"");
      out.print(ONLINE_WEB_ROOT);
      out.write("/js/common.js\" charset=\"utf-8\"></script>\r\n");
      out.write("<script type=\"text/javascript\" src=\"");
      out.print(ONLINE_WEB_ROOT);
      out.write("/js/default.js\"></script>\r\n");
      out.write("<script type=\"text/javascript\" src=\"");
      out.print(ONLINE_WEB_ROOT);
      out.write("/js/placeholders.js\"></script>\r\n");
      out.write("<link rel=\"stylesheet\" type=\"text/css\" href=\"");
      out.print(ONLINE_WEB_ROOT);
      out.write("/css/easyui.css\" />\r\n");
      out.write("<link rel=\"stylesheet\" type=\"text/css\" href=\"");
      out.print(ONLINE_WEB_ROOT);
      out.write("/css/main.css\" />\r\n");
      out.write("<link rel=\"stylesheet\" type=\"text/css\" href=\"");
      out.print(ONLINE_WEB_ROOT);
      out.write("/css/jquery-ui.css\" />\r\n");
      out.write("<link rel=\"stylesheet\" type=\"text/css\" href=\"");
      out.print(ONLINE_WEB_ROOT);
      out.write("/css/smoothness/jquery-ui.theme.css\" />\r\n");
      out.write("<link rel=\"stylesheet\" type=\"text/css\" href=\"");
      out.print(ONLINE_WEB_ROOT);
      out.write("/css/fonts.css\" />\r\n");
      out.write("<link rel=\"stylesheet\" type=\"text/css\" href=\"");
      out.print(ONLINE_WEB_ROOT);
      out.write("/css/gw_sample.css\">");
      out.write("\r\n");
      out.write("\t<link rel=\"stylesheet\" type=\"text/css\" href=\"");
      out.print(ONLINE_WEB_ROOT);
      out.write("/css/ui.jqgrid.css\">\r\n");
      out.write("\t<script type=\"text/javascript\" src=\"");
      out.print(ONLINE_WEB_ROOT);
      out.write("/js/grid.locale-kr.js\"></script>\r\n");
      out.write("\t<script type=\"text/javascript\" src=\"");
      out.print(ONLINE_WEB_ROOT);
      out.write("/js/jquery.jqGrid.min.js\"></script>\r\n");
      out.write("\t<script type=\"text/javascript\" src=\"");
      out.print(ONLINE_WEB_ROOT);
      out.write("/js/formCheck.js\"></script>\r\n");
      out.write("\t<script type=\"text/javascript\" src=\"");
      out.print(ONLINE_WEB_ROOT);
      out.write("/js/business.js\"></script>\r\n");
      out.write("\t<script type=\"text/javascript\">\r\n");
      out.write("\t\r\n");
      out.write("\t/* 전역 변수 */\r\n");
      out.write("\tvar collectionActionFlag = false; // 판매현황 부가기능(인쇄, 엑셀다운로드) 사용 가능 여부 flag\r\n");
      out.write("\t\r\n");
      out.write("\t$(document).ready(function(){\r\n");
      out.write("\t\t\r\n");
      out.write("\t\t/* 사원 정보 존재 여부 */\r\n");
      out.write("\t\tif (");
      out.print(isNull);
      out.write(") {\r\n");
      out.write("\t\t\talert(\"사원정보 자료가 없습니다.\");\r\n");
      out.write("\t\t}\r\n");
      out.write("\t\t\r\n");
      out.write("\t\t/* jqGrid의 jsonReader 옵션 전역 설정. row 반복 형태의 json 처리.  */\r\n");
      out.write("\t    jsonReader : {\r\n");
      out.write("            repeatitems: false;\r\n");
      out.write("    \t}\r\n");
      out.write("\t\r\n");
      out.write("\t\t/* 수금현황 grid */\r\n");
      out.write("\t\t$(\"#grid_list\").jqGrid({\r\n");
      out.write("\t\t\tmtype:\"get\",\r\n");
      out.write("\t\t\tdatatype:\"json\",\r\n");
      out.write("\t\t\tcolNames:[\"거래일자\",\"거래처코드\",\"거래처명\",\"납품처코드\",\"납품처명\",\"구분\",\"수금액\",\"만기일\",\"어음번호\",\"발행처\",\"사원코드\",\"담당사원\"],\r\n");
      out.write("\t\t\tcolModel:[\r\n");
      out.write("\t\t\t\t\t\t{name:\"ymd\",\t\tindex:\"ymd\",\t\talign:\"center\", width:80},\r\n");
      out.write("\t\t\t\t\t\t{name:\"cust_id\",\tindex:\"cust_id\", \talign:\"center\", width:60},\r\n");
      out.write("\t\t\t\t\t\t{name:\"cust_nm\",\tindex:\"cust_nm\", \talign:\"left\", width:150},\r\n");
      out.write("\t\t\t\t\t\t{name:\"rcust_id\",\tindex:\"rcust_id\", \talign:\"center\", width:60},\r\n");
      out.write("\t\t\t\t\t\t{name:\"rcust_nm\",\tindex:\"rcust_nm\", \talign:\"left\", width:150},\r\n");
      out.write("\t\t\t\t\t\t{name:\"item_id\",\tindex:\"item_id\", \talign:\"center\", width:80},\r\n");
      out.write("\t\t\t\t\t\t{name:\"sukum\",\t\tindex:\"sukum\", \t\talign:\"right\", width:100,\tformatter:\"integer\"},\r\n");
      out.write("\t\t\t\t\t\t{name:\"end_ymd\",\tindex:\"end_ymd\", \talign:\"center\", width:80},\r\n");
      out.write("\t\t\t\t\t\t{name:\"bill_no\",\tindex:\"bill_no\",\talign:\"center\", width:150},\r\n");
      out.write("\t\t\t\t\t\t{name:\"balhang\",\tindex:\"balhang\",\talign:\"left\", width:150},\r\n");
      out.write("\t\t\t\t\t\t{name:\"sawon_id\",\tindex:\"sawon_id\",\talign:\"center\", width:50},\r\n");
      out.write("\t\t\t\t\t\t{name:\"sawon_nm\",\tindex:\"sawon_nm\",\talign:\"left\", width:50}\r\n");
      out.write("\t\t\t\t\t],\r\n");
      out.write("\t\t\tformatter : {\r\n");
      out.write("\t\t\t\tinteger : {thousandsSeparator: \",\", defaultValue: '0'} // 천단위 마다 콤마\r\n");
      out.write("\t        },\r\n");
      out.write("\t\t\theight:484,\r\n");
      out.write("\t\t\trownumWidth : 30,\r\n");
      out.write("\t\t\tpage: 1,\r\n");
      out.write("\t\t\trowNum: 20,\r\n");
      out.write("\t\t\trownumbers: true, \r\n");
      out.write("\t\t\trowList: [20,30,40],\r\n");
      out.write("\r\n");
      out.write("\t\t\t/* 조회 완료 시 호출 */\r\n");
      out.write("\t\t\tloadComplete: function(data){\r\n");
      out.write("\t\t\t\tif (data.records > 0) {\r\n");
      out.write("\t\t\t\t\tvar sukum = data.totalCountInfo.total_sukum;\r\n");
      out.write("\t\t\t\t\t\r\n");
      out.write("\t\t\t\t\t$(\"#sukum\").val(Commons.addComma(sukum)); // 수금액\r\n");
      out.write("\t\t\t\t\t\r\n");
      out.write("\t\t\t\t\tcollectionActionFlag = true; // 인쇄, 엑셀 사용 가능하도록\r\n");
      out.write("\t\t\t\t\t\r\n");
      out.write("\t\t\t\t} else {\r\n");
      out.write("\t\t\t\t\talert(\"해당 수금자료가 없습니다.\");\r\n");
      out.write("\t\t\t\t\tcollectionActionFlag = false;\r\n");
      out.write("\t\t\t\t}\r\n");
      out.write("\t\t\t},\r\n");
      out.write("\t\t\t\r\n");
      out.write("\t\t\tpager: \"#grid_Pager\"\r\n");
      out.write("   \t\t});\r\n");
      out.write("\t\t\r\n");
      out.write("\t\t/* 조회 버튼 클릭 */\r\n");
      out.write("\t\t$(\"#ct_retrieve_btn_type01\").on(\"click\",function(){\r\n");
      out.write("\t\t\tgetGridList();\r\n");
      out.write("\t\t});\r\n");
      out.write("\t\t\r\n");
      out.write("\t\t/* 인쇄 버튼 클릭 */\r\n");
      out.write("\t\t$(\"#p_print\").on(\"click\",function(){\r\n");
      out.write("\t\t\tCommons.extraAction(collectionActionFlag, 'print', '");
      out.print(ONLINE_ROOT);
      out.write("/common/commonPrint.do', 'collection');\r\n");
      out.write("\t\t});\r\n");
      out.write("\t\t\r\n");
      out.write("\t\t/* 엑셀 버튼 클릭 */\r\n");
      out.write("\t\t$(\"#p_excel\").on(\"click\",function(){\r\n");
      out.write("\t\t\tCommons.extraAction(collectionActionFlag, 'excel', '");
      out.print(ONLINE_ROOT);
      out.write("/business/collectionGridListExcelDown.do', '')\r\n");
      out.write("\t\t});\r\n");
      out.write("\t\t\r\n");
      out.write("\t\t/* 닫기 버튼 클릭 */\r\n");
      out.write("\t\t$(\"#p_close\").on(\"click\",function(){\r\n");
      out.write("\t\t\tparent.Commons.closeTab('수금현황');\r\n");
      out.write("\t\t});\r\n");
      out.write("\t\t\r\n");
      out.write("\t\t/* 조회일자 셋팅(이번 달 1일 ~ 오늘) */\r\n");
      out.write("\t\tCommons.setDate(\"ad_fr_date\", \"ad_to_date\");\r\n");
      out.write("\t\t\r\n");
      out.write("\t\t/* grid 영역 넓이 셋팅 */\r\n");
      out.write("\t\t$(\"#grid_list\").setGridWidth($(\"#collection\").width() , false);\r\n");
      out.write("\t\t\r\n");
      out.write("\t\t/* 엔터키 눌렸을 경우 조회되도록. */\r\n");
      out.write("\t\t$(\"body\").on(\"keydown\", function(event){\r\n");
      out.write("\t\t\tif($(\"#ct_retrieve_btn_type01\").prop('disabled') == false){\r\n");
      out.write("\t\t\t\tif(event.keyCode==13 && event.target.name!=\"grid_count\"){\r\n");
      out.write("\t\t\t\t\tgetGridList();\r\n");
      out.write("\t\t\t\treturn false;\r\n");
      out.write("\t\t\t\t}\r\n");
      out.write("\t\t\t}\r\n");
      out.write("\t\t});\r\n");
      out.write("\t});\r\n");
      out.write("\t\r\n");
      out.write("\t/* 브라우저 창의 사이즈가 변경될 때 */\r\n");
      out.write("\t$(window).resize(function(){\r\n");
      out.write("\t\t$(\"#grid_list\").setGridWidth($(\"#collection\").width() , false);\r\n");
      out.write("\t});\r\n");
      out.write("\t\r\n");
      out.write("\t/* 조회 */\r\n");
      out.write("\tfunction getGridList(){\r\n");
      out.write("\t\tvar ad_fr_date = $(\"#ad_fr_date\").val();\r\n");
      out.write("\t\tvar ad_to_date = $(\"#ad_to_date\").val();\r\n");
      out.write("\t\tvar as_fr_cust = $(\"#as_fr_cust\").val();\r\n");
      out.write("\t\tvar as_to_cust = $(\"#as_to_cust\").val();\r\n");
      out.write("\t\tvar as_dept_cd = $(\"#as_dept_cd\").val();\r\n");
      out.write("\t\tvar as_pda_auth = $(\"#as_pda_auth\").val();\r\n");
      out.write("\t\t\r\n");
      out.write("\t\t/* 유효성 체크 */\r\n");
      out.write("\t\tif (!formCheck.isDateFormat(ad_fr_date)) {\r\n");
      out.write("\t\t\talert(\"올바른 조회 기간을 입력해주세요.\");\r\n");
      out.write("\t\t\t$(\"#ad_fr_date\").focus();\r\n");
      out.write("\t\t\treturn false;\r\n");
      out.write("\t\t}\r\n");
      out.write("\t\t\r\n");
      out.write("\t\tif (!formCheck.isDateFormat(ad_to_date)) {\r\n");
      out.write("\t\t\talert(\"올바른 조회 기간을 입력해주세요.\");\r\n");
      out.write("\t\t\t$(\"#ad_to_date\").focus();\r\n");
      out.write("\t\t\treturn false;\r\n");
      out.write("\t\t}\r\n");
      out.write("\t\t\r\n");
      out.write("\t\tif (formCheck.dateChk(ad_fr_date, ad_to_date) < 0) {\r\n");
      out.write("\t\t\talert(\"조회 기간을 확인하세요!\");\r\n");
      out.write("\t\t\treturn false;\r\n");
      out.write("\t\t}\r\n");
      out.write("\t\t\r\n");
      out.write("\t\t/* 파라미터 셋팅 */\r\n");
      out.write("\t\tvar params = {\r\n");
      out.write("\t\t\tad_fr_date : ad_fr_date,\r\n");
      out.write("\t\t\tad_to_date : ad_to_date,\r\n");
      out.write("\t\t\tas_fr_cust : as_fr_cust,\r\n");
      out.write("\t\t\tas_to_cust : as_to_cust,\r\n");
      out.write("\t\t\tas_dept_cd : as_dept_cd,\r\n");
      out.write("\t\t\tas_pda_auth : as_pda_auth\r\n");
      out.write("\t\t};\r\n");
      out.write("\t\t\r\n");
      out.write("\t\tvar paramStr = $.param(params);\r\n");
      out.write("\t\t\r\n");
      out.write("\t\t/* 호출 */\r\n");
      out.write("\t\t$(\"#grid_list\").jqGrid('setGridParam',{url:\"");
      out.print(ONLINE_ROOT);
      out.write("/business/collectionGridList.do?\" + paramStr}).trigger(\"reloadGrid\", [{page:1}]);\r\n");
      out.write("\t}\r\n");
      out.write("\t\r\n");
      out.write("\t</script>\r\n");
      out.write("</head>\r\n");
      out.write("\r\n");
      out.write("<body>\r\n");
      out.write("\t<form name=\"frm\" id=\"frm\">\r\n");
      out.write("\t\t<input type=\"hidden\" name=\"as_dept_cd\" id=\"as_dept_cd\" value=\"");
      out.print(dept_cd );
      out.write("\" />\r\n");
      out.write("\t\t<input type=\"hidden\" name=\"as_pda_auth\" id=\"as_pda_auth\" value=\"");
      out.print(pda_auth );
      out.write("\" />\r\n");
      out.write("\t\t\r\n");
      out.write("\t\t<div class=\"wrap_search\">\r\n");
      out.write("\t\t\t<div class=\"search\">\r\n");
      out.write("\t\t\t\t<label>조회기간</label>\r\n");
      out.write("\t\t\t\t<p class=\"wrap_date\">\r\n");
      out.write("\t\t\t\t\t<input type=\"text\" maxlength=\"10\" id=\"ad_fr_date\" name=\"ad_fr_date\" />\r\n");
      out.write("\t\t\t\t\t<button type=\"button\" class=\"btn_date\"><span class=\"blind\">달력보기</span></button>\r\n");
      out.write("\t\t\t\t</p>\r\n");
      out.write("\t\t\t\t~\r\n");
      out.write("\t\t\t\t<p class=\"wrap_date\">\r\n");
      out.write("\t\t\t\t\t<input type=\"text\" maxlength=\"10\" id=\"ad_to_date\" name=\"ad_to_date\" />\r\n");
      out.write("\t\t\t\t\t<button type=\"button\" class=\"btn_date\"><span class=\"blind\">달력보기</span></button>\r\n");
      out.write("\t\t\t\t</p>\r\n");
      out.write("\t\t\t\t\r\n");
      out.write("\t\t\t\t<label>거래처</label>\r\n");
      out.write("\t\t\t\t<input type=\"text\" class=\"w120\" maxlength=\"10\" id=\"as_fr_cust\" name=\"as_fr_cust\" onblur=\"javascript:Business.getCustomerName(this.id);\"/>\r\n");
      out.write("\t\t\t\t<button type=\"button\" class=\"btn_search\" onclick=\"javascript:Commons.popupOpen('as_fr_cust', '");
      out.print(ONLINE_ROOT);
      out.write("/business/common/customerList.do', '600', '655');\" ><span class=\"blind\">찾기</span></button>\r\n");
      out.write("\t\t\t\t<input type=\"text\" class=\"w140 ipt_disable\" id=\"as_fr_cust_name\" name=\"as_fr_cust_name\" readonly />\r\n");
      out.write("\t\t\t\t\r\n");
      out.write("\t\t\t\t<div class=\"wrap_search_btn\">\r\n");
      out.write("\t\t\t\t\t");
      out.print(WebUtil.createButtonArea(currPgmNoByUri, "ct_"));
      out.write("\r\n");
      out.write("\t\t\t\t</div>\r\n");
      out.write("\t\t\t</div>\r\n");
      out.write("\t\t</div>\r\n");
      out.write("\t\t\r\n");
      out.write("\t\t<div class=\"wrap_btn_group\">\r\n");
      out.write("\t\t\t<div class=\"btn_group ta_r\">\r\n");
      out.write("\t\t\t\t");
      out.print(WebUtil.createButtonArea(currPgmNoByUri, "p_"));
      out.write("\r\n");
      out.write("\t\t\t</div>\r\n");
      out.write("\t\t</div>\r\n");
      out.write("\t\t\r\n");
      out.write("\t\t<div class=\"inner_cont2\">\r\n");
      out.write("\t\t\t<div class=\"wrap_result_group\" id=\"collection\">\r\n");
      out.write("\t\t\t\t<table id=\"grid_list\"></table>\r\n");
      out.write("\t\t\t\t<div id=\"grid_Pager\" class=\"collection\"></div>\r\n");
      out.write("\t\t\t</div>\r\n");
      out.write("\t\t\t\r\n");
      out.write("\t\t\t<div class=\"wrap_result_group\">\r\n");
      out.write("\t\t\t\t<div class=\"result_group\">\r\n");
      out.write("\t\t\t\t\t<div class=\"float_r\">\r\n");
      out.write("\t\t\t\t\t\t<label class=\"point\">수금액</label>\r\n");
      out.write("\t\t\t\t\t\t<input type=\"text\" class=\"w100 ta_right ipt_disable\" id=\"sukum\" readonly />\r\n");
      out.write("\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t</div>\r\n");
      out.write("\t\t\t</div>\r\n");
      out.write("\t\t</div>\r\n");
      out.write("\t</form>\r\n");
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
