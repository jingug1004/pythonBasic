<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%--
 * @파일명 : excelOrderWriteInfoPopup.jsp
 * @메뉴명 : 엑셀주문서 작성방법 안내
 * @최초작성일 : 2017/12/20           
 * @author :          
 * @수정내역 : 
--%>
<%@ include file ="/common/path.jsp" %>
<!DOCTYPE html>
<html>
<head>
	<title>엑셀주문서 작성방법 안내</title>
	<%@ include file ="/include/head.jsp" %>
	<!--[if lte IE 9]><script src="asset/js/IE9.js"></script><![endif]-->
	<!--[if lte IE 9]><script src="asset/js/html5.js"></script><![endif]-->
	<link rel="stylesheet" type="text/css" href="<%=ONLINE_WEB_ROOT%>/css/ui.jqgrid.css">
	<script type="text/javascript" src="<%=ONLINE_WEB_ROOT%>/js/grid.locale-kr.js"></script>
	<script type="text/javascript" src="<%=ONLINE_WEB_ROOT%>/js/jquery.jqGrid.min.js"></script>
	<script type="text/javascript" src="<%=ONLINE_WEB_ROOT%>/js/order.js"></script>
	<script type="text/javascript">
	
	/* 엑셀Sample 다운로드 */
	function openExcelDownload() {
		Commons.goExcelDownload('<%=ONLINE_ROOT%>/asset/download/sale_sample.xls');
	}
	
	</script>
</head>

<body>
	<div class="popup">
		
		<!-- ##### content start ##### -->
		<!-- window size : 600 * 765 -->
			<h1 class="tit">엑셀주문서 작성방법 안내</h1>
			
			<form id="frm" name="frm">
				<div class="wrap_pop_search">
					<label>1. 판매자코드 엑셀저장 하여 판매처코드 정보를 확보합니다.</label>
					<button type="button" onclick="Commons.goExcelDownload('<%=ONLINE_ROOT%>/order/common/storeGridListExcelDown.do');">판매처코드 엑셀저장</button>
					<br /><br />
					
					<label>2. 배송지코드 엑셀저장 하여 배송지코드 정보를 확보합니다.</label>
					<button type="button" onclick="Commons.goExcelDownload('<%=ONLINE_ROOT%>/order/common/baesongjiGridListExcelDown.do');">배송지코드 엑셀저장</button>
					 
					<br /><br />
					
					<label>3. 아래 엑셀양식과 동일하게 엑셀파일을 생성합니다.</label>
					<button type="button" onclick="openExcelDownload();">엑셀샘플양식 다운로드</button>
					<div style="color: red; font-size: 11px;">
						* 엑셀샘플양식을 다운로드하여 이용하시거나 파일형식을 Excel 97 - 2003 통합 문서 (*.xls)로 이용해 주세요.
					</div>
					<br />
					<img src="<%=ONLINE_WEB_ROOT %>/img/excel_order_sample.jpg">
				</div>
			</form>
			
			<button type="button" class="close"><span class="blind">닫기</span></button>
		
		<!-- ##### content end ##### -->
	
	</div>
	<%@ include file ="/include/footer_pop.jsp" %>
	<!-- 엑셀 다운로드용 iframe -->
	<iframe name="excelDownFrame" frameBorder="0" width="0" height="0" class="blind"></iframe>
</body>

</html>
