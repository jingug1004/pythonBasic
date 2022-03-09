<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%--
 * @파일명 : collectionList.jsp
 * @메뉴명 : 영업관리 > 수금현황
 * @최초작성일 : 2014/10/28
 * @author : 윤범진                  
 * @수정내역 : 
--%>
<%@ include file ="/common/path.jsp" %>
<%@ page import="com.hanaph.saleon.business.vo.BusinessVO" %>
<%
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
%>
<!DOCTYPE html>
<html lang="ko">
<head>
	<%@ include file ="/include/head.jsp" %>
	<link rel="stylesheet" type="text/css" href="<%=ONLINE_WEB_ROOT%>/css/ui.jqgrid.css">
	<script type="text/javascript" src="<%=ONLINE_WEB_ROOT%>/js/grid.locale-kr.js"></script>
	<script type="text/javascript" src="<%=ONLINE_WEB_ROOT%>/js/jquery.jqGrid.min.js"></script>
	<script type="text/javascript" src="<%=ONLINE_WEB_ROOT%>/js/formCheck.js"></script>
	<script type="text/javascript" src="<%=ONLINE_WEB_ROOT%>/js/business.js"></script>
	<script type="text/javascript">
	
	/* 전역 변수 */
	var collectionActionFlag = false; // 판매현황 부가기능(인쇄, 엑셀다운로드) 사용 가능 여부 flag
	
	$(document).ready(function(){
		
		/* 사원 정보 존재 여부 */
		if (<%=isNull%>) {
			alert("사원정보 자료가 없습니다.");
		}
		
		/* jqGrid의 jsonReader 옵션 전역 설정. row 반복 형태의 json 처리.  */
	    jsonReader : {
            repeatitems: false;
    	}
	
		/* 수금현황 grid */
		$("#grid_list").jqGrid({
			mtype:"get",
			datatype:"json",
			colNames:["거래일자","거래처코드","거래처명","납품처코드","납품처명","구분","수금액","만기일","어음번호","발행처","사원코드","담당사원"],
			colModel:[
						{name:"ymd",		index:"ymd",		align:"center", width:80},
						{name:"cust_id",	index:"cust_id", 	align:"center", width:60},
						{name:"cust_nm",	index:"cust_nm", 	align:"left", width:150},
						{name:"rcust_id",	index:"rcust_id", 	align:"center", width:60},
						{name:"rcust_nm",	index:"rcust_nm", 	align:"left", width:150},
						{name:"item_id",	index:"item_id", 	align:"center", width:80},
						{name:"sukum",		index:"sukum", 		align:"right", width:100,	formatter:"integer"},
						{name:"end_ymd",	index:"end_ymd", 	align:"center", width:80},
						{name:"bill_no",	index:"bill_no",	align:"center", width:150},
						{name:"balhang",	index:"balhang",	align:"left", width:150},
						{name:"sawon_id",	index:"sawon_id",	align:"center", width:50},
						{name:"sawon_nm",	index:"sawon_nm",	align:"left", width:50}
					],
			formatter : {
				integer : {thousandsSeparator: ",", defaultValue: '0'} // 천단위 마다 콤마
	        },
			height:484,
			rownumWidth : 30,
			page: 1,
			rowNum: 20,
			rownumbers: true, 
			rowList: [20,30,40],

			/* 조회 완료 시 호출 */
			loadComplete: function(data){
				if (data.records > 0) {
					var sukum = data.totalCountInfo.total_sukum;
					
					$("#sukum").val(Commons.addComma(sukum)); // 수금액
					
					collectionActionFlag = true; // 인쇄, 엑셀 사용 가능하도록
					
				} else {
					alert("해당 수금자료가 없습니다.");
					collectionActionFlag = false;
				}
			},
			
			pager: "#grid_Pager"
   		});
		
		/* 조회 버튼 클릭 */
		$("#ct_retrieve_btn_type01").on("click",function(){
			getGridList();
		});
		
		/* 인쇄 버튼 클릭 */
		$("#p_print").on("click",function(){
			Commons.extraAction(collectionActionFlag, 'print', '<%=ONLINE_ROOT%>/common/commonPrint.do', 'collection');
		});
		
		/* 엑셀 버튼 클릭 */
		$("#p_excel").on("click",function(){
			Commons.extraAction(collectionActionFlag, 'excel', '<%=ONLINE_ROOT%>/business/collectionGridListExcelDown.do', '')
		});
		
		/* 닫기 버튼 클릭 */
		$("#p_close").on("click",function(){
			parent.Commons.closeTab('수금현황');
		});
		
		/* 조회일자 셋팅(이번 달 1일 ~ 오늘) */
		Commons.setDate("ad_fr_date", "ad_to_date");
		
		/* grid 영역 넓이 셋팅 */
		$("#grid_list").setGridWidth($("#collection").width() , false);
		
		/* 엔터키 눌렸을 경우 조회되도록. */
		$("body").on("keydown", function(event){
			if($("#ct_retrieve_btn_type01").prop('disabled') == false){
				if(event.keyCode==13 && event.target.name!="grid_count"){
					getGridList();
				return false;
				}
			}
		});
	});
	
	/* 브라우저 창의 사이즈가 변경될 때 */
	$(window).resize(function(){
		$("#grid_list").setGridWidth($("#collection").width() , false);
	});
	
	/* 조회 */
	function getGridList(){
		var ad_fr_date = $("#ad_fr_date").val();
		var ad_to_date = $("#ad_to_date").val();
		var as_fr_cust = $("#as_fr_cust").val();
		var as_to_cust = $("#as_to_cust").val();
		var as_dept_cd = $("#as_dept_cd").val();
		var as_pda_auth = $("#as_pda_auth").val();
		
		/* 유효성 체크 */
		if (!formCheck.isDateFormat(ad_fr_date)) {
			alert("올바른 조회 기간을 입력해주세요.");
			$("#ad_fr_date").focus();
			return false;
		}
		
		if (!formCheck.isDateFormat(ad_to_date)) {
			alert("올바른 조회 기간을 입력해주세요.");
			$("#ad_to_date").focus();
			return false;
		}
		
		if (formCheck.dateChk(ad_fr_date, ad_to_date) < 0) {
			alert("조회 기간을 확인하세요!");
			return false;
		}
		
		/* 파라미터 셋팅 */
		var params = {
			ad_fr_date : ad_fr_date,
			ad_to_date : ad_to_date,
			as_fr_cust : as_fr_cust,
			as_to_cust : as_to_cust,
			as_dept_cd : as_dept_cd,
			as_pda_auth : as_pda_auth
		};
		
		var paramStr = $.param(params);
		
		/* 호출 */
		$("#grid_list").jqGrid('setGridParam',{url:"<%=ONLINE_ROOT%>/business/collectionGridList.do?" + paramStr}).trigger("reloadGrid", [{page:1}]);
	}
	
	</script>
</head>

<body>
	<form name="frm" id="frm">
		<input type="hidden" name="as_dept_cd" id="as_dept_cd" value="<%=dept_cd %>" />
		<input type="hidden" name="as_pda_auth" id="as_pda_auth" value="<%=pda_auth %>" />
		
		<div class="wrap_search">
			<div class="search">
				<label>조회기간</label>
				<p class="wrap_date">
					<input type="text" maxlength="10" id="ad_fr_date" name="ad_fr_date" />
					<button type="button" class="btn_date"><span class="blind">달력보기</span></button>
				</p>
				~
				<p class="wrap_date">
					<input type="text" maxlength="10" id="ad_to_date" name="ad_to_date" />
					<button type="button" class="btn_date"><span class="blind">달력보기</span></button>
				</p>
				
				<label>거래처</label>
				<input type="text" class="w120" maxlength="10" id="as_fr_cust" name="as_fr_cust" onblur="javascript:Business.getCustomerName(this.id);"/>
				<button type="button" class="btn_search" onclick="javascript:Commons.popupOpen('as_fr_cust', '<%=ONLINE_ROOT%>/business/common/customerList.do', '600', '655');" ><span class="blind">찾기</span></button>
				<input type="text" class="w140 ipt_disable" id="as_fr_cust_name" name="as_fr_cust_name" readonly />
				
				<div class="wrap_search_btn">
					<%=WebUtil.createButtonArea(currPgmNoByUri, "ct_")%>
				</div>
			</div>
		</div>
		
		<div class="wrap_btn_group">
			<div class="btn_group ta_r">
				<%=WebUtil.createButtonArea(currPgmNoByUri, "p_")%>
			</div>
		</div>
		
		<div class="inner_cont2">
			<div class="wrap_result_group" id="collection">
				<table id="grid_list"></table>
				<div id="grid_Pager" class="collection"></div>
			</div>
			
			<div class="wrap_result_group">
				<div class="result_group">
					<div class="float_r">
						<label class="point">수금액</label>
						<input type="text" class="w100 ta_right ipt_disable" id="sukum" readonly />
					</div>
				</div>
			</div>
		</div>
	</form>
	<%@include file="/include/footer.jsp"%>
</body>
</html>