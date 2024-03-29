/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: Apache Tomcat/7.0.55
 * Generated at: 2018-02-08 02:30:02 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.WEB_002dINF.jsps.order;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import com.hanaph.saleon.member.vo.LoginUserVO;
import com.hanaph.saleon.common.utils.StringUtil;
import com.hanaph.saleon.common.utils.WebUtil;

public final class ledgerList_jsp extends org.apache.jasper.runtime.HttpJspBase
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
      out.write("\t<script type=\"text/javascript\">\r\n");
      out.write("\r\n");
      out.write("\tvar saleActionFlag = false;\t//기능(엑셀, 인쇄) 제어를 위한 전역변수\r\n");
      out.write("\t\r\n");
      out.write("\t/* 화면의 Dom 객체가 모두 준비되었을 때 */\r\n");
      out.write("\t$(document).ready(function(){\r\n");
      out.write("\t\t\r\n");
      out.write("\t\t/* jqGrid의 jsonReader 옵션 전역 설정. row 반복 형태의 json 처리. */\r\n");
      out.write("\t    jsonReader : {\r\n");
      out.write("            repeatitems: false;\r\n");
      out.write("    \t}\r\n");
      out.write("\t\t\r\n");
      out.write("\t\t/* jqGrid 생성 및 options 설정. */\r\n");
      out.write("\t\t$(\"#grid_list\").jqGrid({\r\n");
      out.write("\t\t\tmtype:\"post\",\r\n");
      out.write("\t\t\tdatatype:\"json\",\r\n");
      out.write("\t\t\tcolNames:[\"일자\",\"납품처\",\"품목\",\"단위\",\"수량\",\"단가\",\"공급가액\",\"세액\",\"합계금액\",\"수금\",\"잔액\"],\r\n");
      out.write("\t\t\tcolModel:[\r\n");
      out.write("\t\t\t\t\t\t{name:\"ymd\",\t\tindex:\"ymd\",\t\talign:\"center\",\twidth:100 },\t\t\t\t\t\t//일자\r\n");
      out.write("\t\t\t\t\t\t{name:\"rcust_nm\",\tindex:\"rcust_nm \",\talign:\"left\",\twidth:200 },\t\t\t\t\t\t//납품처\r\n");
      out.write("\t\t\t\t\t\t{name:\"item_nm\",\tindex:\"item_nm\", \talign:\"left\",\twidth:300},\t\t\t\t\t\t\t//품목\r\n");
      out.write("\t\t\t\t\t\t{name:\"standard\",\tindex:\"standard\", \talign:\"center\",\twidth:55,},\t\t\t\t\t\t\t//단위\r\n");
      out.write("\t\t\t\t\t\t{name:\"qty\",\t\tindex:\"qty\", \t\talign:\"center\", width:55, formatter:Commons.integerFmt},\t\t//수량\r\n");
      out.write("\t\t\t\t\t\t{name:\"danga\",\t\tindex:\"danga\", \t\talign:\"right\", \twidth:55, formatter:Commons.integerFmt},\t\t//단가\r\n");
      out.write("\t\t\t\t\t\t{name:\"amt\",\t\tindex:\"amt\", \t\talign:\"right\",  width:100, formatter:Commons.integerFmt},\t//공급가액\r\n");
      out.write("\t\t\t\t\t\t{name:\"vat\",\t\tindex:\"vat\", \t\talign:\"right\",\twidth:100, formatter:Commons.integerFmt},\t//세액\r\n");
      out.write("\t\t\t\t\t\t{name:\"tot\",\t\tindex:\"tot\",\t\talign:\"right\",\twidth:100, formatter:Commons.integerFmt},\t//합계금액\r\n");
      out.write("\t\t\t\t\t\t{name:\"sukum\",\t\tindex:\"sukum\", \t\talign:\"right\",\twidth:100, formatter:Commons.integerFmt},\t//수금\r\n");
      out.write("\t\t\t\t\t\t{name:\"rem_amt\",\tindex:\"rem_amt\",\talign:\"right\",\twidth:100, formatter:Commons.integerFmt}\t\t//잔액\r\n");
      out.write("\t\t\t\t\t\t\r\n");
      out.write("\t\t\t\t\t],\r\n");
      out.write("\t\t\theight:461,\r\n");
      out.write("\t\t\tpage: 1,\r\n");
      out.write("\t\t\trowNum: 20,\r\n");
      out.write("\t\t\trownumbers: true, \r\n");
      out.write("\t\t\trowList: [20,30,40],\r\n");
      out.write("\t\t\trownumbers: false,\r\n");
      out.write("\t\t\tautowidth:true,\r\n");
      out.write("\t\t\tloadComplete: function(data){\t//조회 완료시 호출되는 function\r\n");
      out.write("\t\t\t\tif(data.records > 0){\t\t//조회 결과가 있을 경우\r\n");
      out.write("\t\t\t\t\tsaleActionFlag = true;\r\n");
      out.write("\t\t\t\t\t$(\"#amt\").val(Commons.addComma(data.sumLedger.amt));\r\n");
      out.write("\t\t\t\t\t$(\"#vat\").val(Commons.addComma(data.sumLedger.vat));\r\n");
      out.write("\t\t\t\t\t$(\"#tot\").val(Commons.addComma(data.sumLedger.tot));\r\n");
      out.write("\t\t\t\t\t$(\"#sukum\").val(Commons.addComma(data.sumLedger.sukum));\r\n");
      out.write("\t\t\t\t\t/*\r\n");
      out.write("\t\t\t\t\tif(data.page==1){\r\n");
      out.write("\t\t\t\t\t\tvar datarow = {ymd:\"\",rcust_nm:\"\",item_nm:\"<b>[ 이월잔액 ]</b>\",standard:\"\",qty:\"\",danga:\"\",amt:\"\",vat:\"\",tot:\"\",sukum:\"\",rem_amt:'<b>'+data.rows[0].before_amt+'</b>'};\r\n");
      out.write("\t\t\t\t\t\t$(\"#grid_list\").addRowData(0,datarow,\"first\");\r\n");
      out.write("\t\t\t\t\t\t$(\"#grid_list\").setSelection('0');\r\n");
      out.write("\t\t\t\t\t}\r\n");
      out.write("\t\t\t\t\t*/\r\n");
      out.write("\t\t\t\t}else{\r\n");
      out.write("\t\t\t\t\tsaleActionFlag = false;\r\n");
      out.write("\t\t\t\t\talert(\"해당 원장자료가 없습니다.\");\r\n");
      out.write("\t\t\t\t}\r\n");
      out.write("\t\t\t},\r\n");
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
      out.write("\t\t\tgridPrint();\r\n");
      out.write("\t\t});\r\n");
      out.write("\t\t\r\n");
      out.write("\t\t/* 엑셀 버튼 클릭 */\r\n");
      out.write("\t\t$(\"#p_excel\").on(\"click\",function(){\r\n");
      out.write("\t\t\tCommons.extraAction(saleActionFlag, 'excel', '");
      out.print(ONLINE_ROOT);
      out.write("/order/ledgerGridListExcelDown.do', '');\r\n");
      out.write("\t\t});\r\n");
      out.write("\t\t\r\n");
      out.write("\t\t/* 닫기 버튼 클릭 */\r\n");
      out.write("\t\t$(\"#p_close\").on(\"click\",function(){\r\n");
      out.write("\t\t\tparent.Commons.closeTab('원장조회');\r\n");
      out.write("\t\t});\r\n");
      out.write("\t\t\r\n");
      out.write("\t\t/* 조회일자 셋팅 */\r\n");
      out.write("\t\tCommons.setDate('adt_from','adt_to');\r\n");
      out.write("\t\t\r\n");
      out.write("\t});\r\n");
      out.write("\t\r\n");
      out.write("\t/* 브라우저 창의 사이즈가 변경될 때 */\r\n");
      out.write("    $(window).resize(function(){\r\n");
      out.write("\t\t$(\"#grid_list\").setGridWidth($('.wrap_result_group').width(), true);\t//grid 영역의 넓이가 동적으로 조절 \r\n");
      out.write("\t});\r\n");
      out.write("    \r\n");
      out.write("    /* 화면의 Dom 객체가 모두 준비되었을 때 */\r\n");
      out.write("\t$(window).load(function(){\r\n");
      out.write("\t\t/*\r\n");
      out.write("\t\t*\t엔터키를 눌렀을 때 조회버튼이 비활성화되어 있고, 엔터키가 눌러진 객체의 name속성이 grid_count 아닐 경우 조회\r\n");
      out.write("\t\t*/\r\n");
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
      out.write("\t/**\r\n");
      out.write("\t*\t원장 조회\r\n");
      out.write("\t*/\r\n");
      out.write("\tfunction getGridList(){\r\n");
      out.write("\t\t\r\n");
      out.write("\t\t//validation check//\r\n");
      out.write("\t\tvar start_date = $('#adt_from');\r\n");
      out.write("\t\tvar end_date = $('#adt_to');\r\n");
      out.write("\t\t\r\n");
      out.write("\t\tif (!formCheck.isDateFormat(start_date.val())){\r\n");
      out.write("\t\t\talert(\"조회 기간을 확인하세요.\");\r\n");
      out.write("\t\t\tstart_date.focus();\r\n");
      out.write("\t\t\treturn\r\n");
      out.write("\t    }\r\n");
      out.write("\r\n");
      out.write("\t\tif (!formCheck.isDateFormat(end_date.val())){\r\n");
      out.write("\t\t\talert(\"조회 기간을 확인하세요.\");\r\n");
      out.write("\t\t\tend_date.focus();\r\n");
      out.write("\t\t\treturn\r\n");
      out.write("\t\t}\r\n");
      out.write("\t\t\r\n");
      out.write("\t\tif(formCheck.dateChk(start_date.val(),end_date.val())<0){\r\n");
      out.write("\t\t\talert(\"조회 시작일이 종료일보다 이후 입니다.\");\r\n");
      out.write("\t\t\tstart_date.focus();\r\n");
      out.write("\t\t\treturn\r\n");
      out.write("\t\t}\r\n");
      out.write("\t\t\t\t\r\n");
      out.write("\t\t\t\t\r\n");
      out.write("\t\tvar adt_from = $(\"#adt_from\").val();\r\n");
      out.write("\t\tvar adt_to = $(\"#adt_to\").val();\r\n");
      out.write("\t\t\r\n");
      out.write("\t\tvar param = \"adt_from=\" + adt_from + \"&adt_to=\" + adt_to ;\t// 파라미터 셋팅\r\n");
      out.write("\t\t\r\n");
      out.write("\t\t\r\n");
      out.write("\t\t$(\"#grid_list\").jqGrid('setGridParam',{url:\"");
      out.print(ONLINE_ROOT);
      out.write("/order/ledgerGridList.do?\" + param}).trigger(\"reloadGrid\", [{page:1}]);\t// 호출\r\n");
      out.write("\t}\r\n");
      out.write("\t\r\n");
      out.write("\r\n");
      out.write("\t/**\r\n");
      out.write("\t*\t인쇄 버튼 클릭시 인쇄 전용 jqGrid를 생성한다.\r\n");
      out.write("\t*/\r\n");
      out.write("\tfunction gridPrint(){\r\n");
      out.write("\t\t\r\n");
      out.write("\t\tif(!saleActionFlag){\r\n");
      out.write("\t\t\talert(\"조회결과가 없습니다.\");\r\n");
      out.write("\t\t\treturn;\r\n");
      out.write("\t\t}\r\n");
      out.write("\t\t\r\n");
      out.write("\t\tvar grid = $(\"#grid_list\");\r\n");
      out.write("\t\tvar adt_from = $(\"#adt_from\").val();\r\n");
      out.write("\t\tvar adt_to = $(\"#adt_to\").val();\r\n");
      out.write("\t\tvar page = $('.ui-pg-input').val();\r\n");
      out.write("\t\tvar rows=0;\r\n");
      out.write("\t\tif(page==1){\r\n");
      out.write("\t\t\trows = grid.getCol('ymd',true,'count')-1;\r\n");
      out.write("\t\t}else{\r\n");
      out.write("\t\t\trows = grid.getCol('ymd',true,'count');\r\n");
      out.write("\t\t}\r\n");
      out.write("\t\t\r\n");
      out.write("\t\t$('#ledger').html('<table id=\\\"grid_list_clone\\\"></table>');\r\n");
      out.write("\t\t\r\n");
      out.write("\t\t\r\n");
      out.write("\t\tvar param = \"adt_from=\" + adt_from + \"&adt_to=\" + adt_to;\t// 파라미터 셋팅\r\n");
      out.write("\t\t\r\n");
      out.write("\t\t/* 프린트를 하기위해 grid 재생성 */\r\n");
      out.write("\t\t$(\"#grid_list_clone\").jqGrid({\r\n");
      out.write("\t\t\turl:\"");
      out.print(ONLINE_ROOT);
      out.write("/order/ledgerGridList.do?\" + param,\r\n");
      out.write("\t\t\tmtype:\"post\",\r\n");
      out.write("\t\t\tdatatype:\"json\",\r\n");
      out.write("\t\t\tcolNames:[\"일자\",\"납품처\",\"품목\",\"단위\",\"수량\",\"단가\",\"공급가액\",\"세액\",\"합계금액\",\"수금\",\"잔액\"],\r\n");
      out.write("\t\t\tcolModel:[\r\n");
      out.write("\t\t\t\t\t\t{name:\"ymd\",\t\tindex:\"ymd\",\t\talign:\"center\",\twidth:100 },\t\t\t\t\t\t//일자\r\n");
      out.write("\t\t\t\t\t\t{name:\"rcust_nm\",\tindex:\"rcust_nm \",\talign:\"left\",\twidth:150 },\t\t\t\t\t\t//납품처\r\n");
      out.write("\t\t\t\t\t\t{name:\"item_nm\",\tindex:\"item_nm\", \talign:\"left\",\twidth:300},\t\t\t\t\t\t\t//품목\r\n");
      out.write("\t\t\t\t\t\t{name:\"standard\",\tindex:\"standard\", \talign:\"center\",\twidth:55,},\t\t\t\t\t\t\t//단위\r\n");
      out.write("\t\t\t\t\t\t{name:\"qty\",\t\tindex:\"qty\", \t\talign:\"center\", width:55, formatter:Commons.integerFmt},\t\t//수량\r\n");
      out.write("\t\t\t\t\t\t{name:\"danga\",\t\tindex:\"danga\", \t\talign:\"right\", \twidth:55, formatter:Commons.integerFmt},\t\t//단가\r\n");
      out.write("\t\t\t\t\t\t{name:\"amt\",\t\tindex:\"amt\", \t\talign:\"right\",  width:100, formatter:Commons.integerFmt},\t//공급가액\r\n");
      out.write("\t\t\t\t\t\t{name:\"vat\",\t\tindex:\"vat\", \t\talign:\"right\",\twidth:100, formatter:Commons.integerFmt},\t//세액\r\n");
      out.write("\t\t\t\t\t\t{name:\"tot\",\t\tindex:\"tot\",\t\talign:\"right\",\twidth:100, formatter:Commons.integerFmt},\t//합계금액\r\n");
      out.write("\t\t\t\t\t\t{name:\"sukum\",\t\tindex:\"sukum\", \t\talign:\"right\",\twidth:100, formatter:Commons.integerFmt},\t//수금\r\n");
      out.write("\t\t\t\t\t\t{name:\"rem_amt\",\tindex:\"rem_amt\",\talign:\"right\",\twidth:100, formatter:Commons.integerFmt}\t\t//잔액\r\n");
      out.write("\t\t\t\t\t],\r\n");
      out.write("\t\t\tpage: page,\r\n");
      out.write("\t\t\trowNum: rows,\r\n");
      out.write("\t\t\trownumbers: true, \r\n");
      out.write("\t\t\tloadComplete: function(data){\r\n");
      out.write("\t\t\t\tCommons.extraAction(saleActionFlag, 'print', '");
      out.print(ONLINE_ROOT);
      out.write("/common/commonPrint.do', 'ledger');\r\n");
      out.write("\t\t\t}\r\n");
      out.write("   \t\t});\r\n");
      out.write("\t\t\r\n");
      out.write("\t\t\r\n");
      out.write("\t}\r\n");
      out.write("\t</script>\r\n");
      out.write("\t\r\n");
      out.write("</head>\r\n");
      out.write("<body>\r\n");
      out.write("\t<form name=\"frm\" id=\"frm\">\r\n");
      out.write("\t\t\t\r\n");
      out.write("\t\t<!-- ##### content start ##### -->\r\n");
      out.write("\t\t<div class=\"wrap_search\">\r\n");
      out.write("\t\t\t<div class=\"search\">\r\n");
      out.write("\t\t\t\t<label>조회기간</label>\r\n");
      out.write("\t\t\t\t<p class=\"wrap_date\">\r\n");
      out.write("\t\t\t\t\t<input type=\"text\" id=\"adt_from\" name=\"adt_from\" maxlength=\"10\"/>\r\n");
      out.write("\t\t\t\t\t<button type=\"button\" class=\"btn_date\"><span class=\"blind\">달력보기</span></button>\r\n");
      out.write("\t\t\t\t</p>\r\n");
      out.write("\t\t\t\t~\r\n");
      out.write("\t\t\t\t<p class=\"wrap_date\">\r\n");
      out.write("\t\t\t\t\t<input type=\"text\" id=\"adt_to\" name=\"adt_to\" maxlength=\"10\"/>\r\n");
      out.write("\t\t\t\t\t<button type=\"button\" class=\"btn_date\"><span class=\"blind\">달력보기</span></button>\r\n");
      out.write("\t\t\t\t</p>\r\n");
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
      out.write("\t\t\t<div class=\"wrap_result_group\">\r\n");
      out.write("\t\t\t\t<table id=\"grid_list\"></table>\r\n");
      out.write("\t\t\t\t<div id=\"grid_Pager\"></div>\r\n");
      out.write("\t\t\t</div>\r\n");
      out.write("\t\t\t<div class=\"wrap_result_group\">\r\n");
      out.write("\t\t\t\t<div class=\"result_group\">\r\n");
      out.write("\t\t\t\t\t<div class=\"float_r\">\r\n");
      out.write("\t\t\t\t\t\t<label class=\"point\">공급가액</label>\r\n");
      out.write("\t\t\t\t\t\t<input type=\"text\" id=\"amt\" readonly class=\"w100 ta_right ipt_disable\" />\r\n");
      out.write("\t\t\t\t\t\t<label class=\"point ml10\">세액</label>\r\n");
      out.write("\t\t\t\t\t\t<input type=\"text\" id=\"vat\" readonly class=\"w100 ta_right ipt_disable\" />\r\n");
      out.write("\t\t\t\t\t\t<label class=\"point ml10\">공급총액</label>\r\n");
      out.write("\t\t\t\t\t\t<input type=\"text\" id=\"tot\" readonly class=\"w100 ta_right ipt_disable\" />\r\n");
      out.write("\t\t\t\t\t\t<label class=\"point ml10\">수금</label>\r\n");
      out.write("\t\t\t\t\t\t<input type=\"text\" id=\"sukum\" readonly class=\"w100 ta_right ipt_disable\" />\r\n");
      out.write("\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t</div>\r\n");
      out.write("\t\t\t</div>\r\n");
      out.write("\t\t</div>\r\n");
      out.write("\t\t<!-- ##### content end ##### -->\r\n");
      out.write("\t\t\r\n");
      out.write("\t\t<div id=\"ledger\" style=\"display:none;\"><table id=\"grid_list_clone\"></table></div>\r\n");
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
