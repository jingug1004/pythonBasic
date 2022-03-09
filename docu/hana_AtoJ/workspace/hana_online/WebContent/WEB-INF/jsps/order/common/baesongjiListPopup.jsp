<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%--
 * @파일명 : baesongjiListPopup.jsp
 * @메뉴명 : 배송지 검색 팝업    
 * @최초작성일 : 2017/11/09            
 * @author :          
 * @수정내역 : 
--%>
<%@ include file ="/common/path.jsp" %>
<!DOCTYPE html>
<html>
<head>
	<title>배송지 검색</title>
	<%@ include file ="/include/head.jsp" %>
	<!--[if lte IE 9]><script src="asset/js/IE9.js"></script><![endif]-->
	<!--[if lte IE 9]><script src="asset/js/html5.js"></script><![endif]-->
	<link rel="stylesheet" type="text/css" href="<%=ONLINE_WEB_ROOT%>/css/ui.jqgrid.css">
	<script type="text/javascript" src="<%=ONLINE_WEB_ROOT%>/js/grid.locale-kr.js"></script>
	<script type="text/javascript" src="<%=ONLINE_WEB_ROOT%>/js/jquery.jqGrid.min.js"></script>
	<script type="text/javascript" src="<%=ONLINE_WEB_ROOT%>/js/order.js"></script>
	<script type="text/javascript">
	/* 화면의 Dom 객체가 모두 준비되었을 때 */
	$(document).ready(function(){
		
		/* jqGrid의 jsonReader 옵션 전역 설정. row 반복 형태의 json 처리. */
	    jsonReader : {
            repeatitems: false;
    	}
	
		/* 배송지 조회 */
		$("#grid_list").jqGrid({
			url: "<%=ONLINE_ROOT%>/order/common/baesongjiGridList.do",
			mtype:"get",		// 요청방식
			datatype:"json",	// 결과물 받을 데이터 타입
			colNames:["배송지코드", "배송지명","수취인","배송지주소","전화번호"],		// 컬럼명
			colModel:[
                        {name:"seq",	    index:"seq",     	width:80,	align:"center"},
						{name:"addrname",	index:"addrname", 	width:100,	align:"left"},
						{name:"rcvrname",   index:"rcvrname",   width:100,  align:"left"},
						{name:"addr1",		index:"addr1",   	width:240,	align:"left"},
						{name:"telno",	    index:"telno", 	    width:140,	align:"center"},
					],
			rowNum: -1,
			height:415,
			autowidth:true,
			beforeProcessing: function(status){
				if (status.records < 1) {
					alert("해당 배송지가 없습니다.");
				}
			},
			ondblClickRow: function(){
				Order.baesongjiValue();   // 배송지 선택시 처리는 order.js
			},
   		});
	});
	
	/* 브라우저 창의 사이즈가 변경될 때 */
    $(window).resize(function(){
		$("#grid_list").setGridWidth($('.pop_grid').width(), true);		//grid 영역의 넓이가 동적으로 조절 
	});
    
	/**
	 * 조회 버튼 클릭시 
	 */
	function getGridList(){
		
		var searchKeyword = $("#searchKeyword").val();		//검색어
		
		var param = "searchKeyword=" + encodeURI(encodeURIComponent(searchKeyword));	// 파라미터 셋팅
		
		$("#grid_list").jqGrid('setGridParam',{url:"<%=ONLINE_ROOT%>/order/common/baesongjiGridList.do?" + param}).trigger("reloadGrid");		// 호출
	}
	
	</script>
</head>


<body onKeyPress="if(event.keyCode=='13'){getGridList(); return false;}">
	<div class="popup">
		
		<!-- ##### content start ##### -->
		<!-- window size : 1000 * 655 -->
			<h1 class="tit">배송지 검색</h1>
			
			<div class="wrap_pop_search">
				<label>배송지명</label>
				<input type="text" class="w330" type="text" id="searchKeyword" name="searchKeyword"/>
				<button type="button" onclick="getGridList();">조회</button>
				<button type="button" onclick="Order.baesongjiValue();" >선택</button>
			</div>
			
			<div class="pop_grid box_type01 h440">
				<table id="grid_list"></table>
			</div>
			
			<button type="button" class="close"><span class="blind">닫기</span></button>
		
		<!-- ##### content end ##### -->
	
	</div>
	<%@ include file ="/include/footer_pop.jsp" %>
</body>

</html>
