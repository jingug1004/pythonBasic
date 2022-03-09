<%@page import="com.hanaph.saleon.business.vo.ReturnVO"%>
<%@page import="java.util.HashMap"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="com.hanaph.saleon.business.vo.ReturnVO" %>
<%@ page import="com.hanaph.saleon.common.utils.StringUtil" %>
<%@ page import="java.util.List" %>
<%@ include file ="/common/path.jsp" %>
<%
	@SuppressWarnings("unchecked")
	HashMap header = new HashMap();	


	/* 조회기간 */
	String ad_fr_date = StringUtil.nvl((String)header.get("ad_fr_date"));
	String ad_to_date = StringUtil.nvl((String)header.get("ad_to_date"));
	
	try{
	ad_fr_date = ad_fr_date.split("-")[0] + "년 " + ad_fr_date.split("-")[1] + "월 " + ad_fr_date.split("-")[2] + "일";
	ad_to_date = ad_to_date.split("-")[0] + "년 " + ad_to_date.split("-")[1] + "월 " + ad_to_date.split("-")[2] + "일";
	}catch(Exception e){}
	
	/* 원장 목록 */
	@SuppressWarnings("unchecked")
	List<ReturnVO> ledgerList = (List<ReturnVO>)request.getAttribute("returnList");
	
	
	@SuppressWarnings("unchecked")
	ReturnVO totalVO = (ReturnVO)request.getAttribute("returnTotalVO");
	
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="ko" lang="ko">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="imagetoolbar" content="no" />
<style type="text/css">
/* html, body {height:100%;} */
body {margin:0; padding:0; font-family:Dotum, '돋움', verdana, sans-serif; color:#000; font-size:11px; /* background:url('logo.gif') no-repeat center 50%; */}
form, div, p, h1, h2, h3, h4, h5, h6, dl, dt, dd, ul, ol, li, blockquote, th, td {margin:0; padding:0; border:0 none;}
img {border:0 none;}
table {border-collapse:collapse; table-layout:fixed; margin:0; padding:0; border:0;}

table.header {clear:both; width:100%;border:solid 1px #000000;}
table.header td {padding:4px 5px 2px;border:solid 1px #000000;}
table.header td.subject {font-weight:bold; font-size:40px; text-align:center;}
table.header td.center {text-align:center;}
table.header td.right {text-align:right;}
table.header td.bold {font-weight:bold;}

table.list {clear:both; margin:15px 0 30px; width:100%; border:1px solid #000;}
table.list th {padding:5px 5px 4px; border:1px solid #000; background-color:#eee;}
table.list td {text-align:right; border:1px solid #000; padding:4px 5px 2px; line-height:1.4; white-space:nowrap; /*word-wrap:break-word; word-break: break-all;*/ overflow:hidden; text-overflow:clip;}
table.list td span {display:inline-block; margin-right:8px;}
table.list td.center {text-align:center;}
table.list td.left {text-align:left;}
table.list td.bold {font-weight:bold;}
table.list th.last, 
p.bg_logo {position:fixed; z-index:-1; text-align:center;}
p.bg_logo img {width:1020px; height:717px; text-align:center; margin:0 auto;}
.wrap_btn_group {position:absolute; top:15px; right:10px;}
.wrap_btn_group .btn_group button {border:1px solid #818181; background-color:#878787; color:#fff; font-size:11px;}
button {background-color:#FBFBFB; border-top:1px solid #E9E9E9; border-left:1px solid #E9E9E9; border-right:1px solid #C4C4C4; border-bottom:1px solid #C4C4C4; cursor:pointer; padding:0px 5px 0; height:20px; font-family:"NanumGothic"; vertical-align:middle;}
#wrap {margin-top:10px;}

@media print {
	@page { size: landscape; } 
	body {font-size:9px; font-family:Gulim, '굴림', verdana, sans-serif; margin:10px 0;}
	#wrap {page:a4sheet; /* page-break-after:always; */ margin-top:0;}
	table.header {clear:both; width:100%;border:solid 1px #000000;}
	table.header td {padding:4px 5px 2px;border:solid 1px #000000;}
	table.header td.subject {font-weight:bold; font-size:40px; text-align:center;}
	table.header td.center {text-align:center;}
	table.header td.right {text-align:right;}
	table.header td.bold {font-weight:bold;}

	table.list {margin:15px 0 0;}
	table.list th {font-size:10px; background-color:#eee;}
	.wrap_btn_group {display:none;}
	p.bg_logo img {width:976px; height:630px; text-align:center; margin:0 auto;}

}
</style>
<!-- script type="text/javascript" src="<%=ONLINE_WEB_ROOT%>/js/common.js" charset="utf-8"></script -->
</head>
<body>
<div id="wrap">
	<p class="bg_logo"><img src="<%=ONLINE_WEB_ROOT %>/img/bg_logo.gif" alt="하나제약주식회사" /></p>
	<!-- start header -->
	<table class="header">
	<colgroup>
		<col style="width:2%" />
		<col style="width:2%" />
		<col style="width:6%" />
		<col style="width:6%" />
		<col style="width:6%" />
		<col style="width:2%" />
		<col style="width:5%" />
		<col />
		<col style="width:21%" />
		<col style="width:2%" />
		<col style="width:5%" />
		<col style="width:5%" />
		<col style="width:5%" />
	</colgroup>
	<tr>
		<td class="subject" colspan="9" rowspan="3">반 품 명 세 서</td>
		<td class="center" rowspan="3"><font size="1">결<br/>제</font></td>
		<td class="center"><font size="1">담당</font></td>
		<td class="center"><font size="1">팀장</font></td>
		<td class="center"><font size="1">지점장</font></td>
	</tr>
	<tr>
		<td class="center" height="30px;"></td>
		<td class="center"></td>
		<td class="center"></td></tr>
	
	<tr>
		<td class="center"><font size="1">/</font></td>
		<td class="center"><font size="1">/</font></td>
		<td class="center"><font size="1">/</font></td>
	</tr>
	
	
		<tr>
			<td rowspan="2">결<br/><br/>재</td>
			<td rowspan="2">배<br/>송<br/>과</td>
			<td height="15"></td>
			<td></td>
			<td></td>
			<td rowspan="2">비<br/><br/>고</td>
			<td colspan="7" rowspan="2" class="left"></td>
		</tr>
		<tr>
			<td height="30"></td>
			<td></td>
			<td class="left"></td>
		</tr>
	
	</table>
	<!-- end header -->

	<!-- start 일자별 목록 테이블 -->
	<table class="list">
	<colgroup>
		<col style="width:2%;" />
		<col style="width:3%;" />
		<col style="width:7%;" />
		<col style="width:5%;" />
		<col style="width:7%;" />
		<col style="width:2%;" />
		<col />
		<col style="width:5%;" />
		<col style="width:4%;" />
		<col style="width:5%;" />
		<col style="width:7%;" />
		<col style="width:4%;" />
		<col style="width:8%;" />
		<col style="width:8%;" />
		<col style="width:8%;" />
		<col style="width:8%;" />
	</colgroup>
	<thead>
	<tr>
		<th rowspan="2" colspan="2">거래처코드</th>
		<th rowspan="2">거래처명</th>
		<th rowspan="2">간납처코드</th>
		<th rowspan="2">간납처명</th>
		<th rowspan="2" colspan="2">제품명</th>
		<th rowspan="2">제품<br/>코드</th>
		<th rowspan="2">규격</th>
		<th>제조</th>
		<th>사용기한</th>
		<th rowspan="2">수량</th>
		<th rowspan="2">금액</th>
		<th rowspan="2">담당자</th>
		<th rowspan="2">단가<br/>(VAT 별도)</th>
		<th rowspan="2">반품<br/>사유</th>
	</tr>
	<tr>
		<th>번호</th>
		<th>년 월 일</th>
	</tr>
	</thead>
	<tbody>
	<%
		for(int i = 0 ; i < ledgerList.size() ; i++){
			ReturnVO vo = (ReturnVO)ledgerList.get(i);
	%>
		<tr>
			<td class="center" colspan="2"><%=vo.getCust_id() %></td>
			<td class="left"><%=vo.getCust_nm() %></td>
			<td class="center"><%=vo.getRcust_id() %></td>
			<td class="left"><%=vo.getRcust_nm() %></td>
			<td class="left" colspan="2"><%=vo.getItem_nm() %></td>
			<td class="center"><%=vo.getItem_id() %></td>
			<td><%=vo.getStandard() %></td>
			<td><%=vo.getProd_no() %></td>
			<td><%=vo.getUse_ymd_to() %></td>
			<td><%=StringUtil.makeMoneyTypeInt(vo.getBanpum_qty() ) %></td>
			<td><%=StringUtil.makeMoneyTypeInt(vo.getAmt()) %></td>
			<td><%=vo.getCust_sawon_nm() %></td>
			<td><%=StringUtil.makeMoneyTypeInt(vo.getDanga()) %></td>
			<td><%=vo.getBanpum_reason_nm() %></td>
		</tr>
	<%} %>
	<%
	if( 0 < ledgerList.size()){
	%>
		<tr>
			<td class="center" colspan="2"></td>
			<td class="left"></td>
			<td class="center"></td>
			<td class="left"></td>
			<td class="left" colspan="2">합&nbsp;계</td>
			<td class="center"></td>
			<td></td>
			<td></td>
			<td></td>
			<td><%=StringUtil.makeMoneyTypeInt(totalVO.getTotal_b_qty() ) %></td>
			<td><%=StringUtil.makeMoneyTypeInt(totalVO.getTotal_amt()) %></td>
			<td></td>
			<td></td>
			<td></td>
		</tr>
	<%
	}
	%>
	
	<!-- end 합 계 -->
	</tbody>
	</table>
	<!-- end 일자별 목록 테이블 -->
</div>

<!-- 임시 인쇄하기 버튼 -->
<div id="buttonArea" class="wrap_btn_group">
	<div class="btn_group ta_r">
		<button type="button" onclick="javascript:window.print();" >출력</button>&nbsp;
		<button type="button" onclick="javascript:self.close();" >닫기</button>
	</div>
</div>
</body>
</html>