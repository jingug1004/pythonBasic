<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%--
 * @파일명 : customerBalanceList.jsp
 * @메뉴명 : 영업관리 > 담보/잔고현황  
 * @최초작성일 : 2015/02/02            
 * @author : 김재갑                 
 * @수정내역 : 
--%>
<%@ include file ="/common/path.jsp" %>
<!DOCTYPE html>
<html lang="ko">
<head>
	<%@ include file ="/include/head.jsp" %>
	<link rel="stylesheet" type="text/css" href="<%=ONLINE_WEB_ROOT%>/css/ui.jqgrid.css">
	<script type="text/javascript" src="<%=ONLINE_WEB_ROOT%>/js/grid.locale-kr.js"></script>
	<script type="text/javascript" src="<%=ONLINE_WEB_ROOT%>/js/jquery.jqGrid.min.js"></script>
	
	<script type="text/javascript">
	
	var saleActionFlag = false;		//기능(엑셀, 인쇄) 제어를 위한 전역변수
	var selectedCustomerId = "";
	
	/* 화면의 Dom 객체가 모두 준비되었을 때 */
	$(document).ready(function(){
		
		/* 잔고/담보현황 jqGrid 생성 및 options 설정. */
		$("#customer_detail").jqGrid({
			
			mtype:"post",		// 요청방식
			datatype:"json",		// 결과물 받을 데이터 타입
			colNames:["담보종류","매출담보금액","만기일","어음번호","발행처","지급처","발행일","출고일"],		// 컬럼명
			colModel:[
						{name:"bill_gb",		index:"bill_gb",		align:"center",	width:100 },	//담보종류
						{name:"sale_dambo_amt",	index:"sale_dambo_amt ",align:"right",	width:120 ,formatter:Commons.integerFmt},//매출담보금액
						{name:"end_ymd",		index:"end_ymd", 		align:"center",	width:120},		//만기일
						{name:"bill_no",		index:"bill_no", 		align:"center",	width:150,},	//어음번호
						{name:"balhang",		index:"balhang", 		align:"left", 	width:150, },	//발행처
						{name:"jigeub",			index:"jigeub", 		align:"left", 	width:200, },	//지급처
						{name:"start_ymd",		index:"start_ymd", 		align:"center", width:120, },	//발행일
						{name:"chulgo_ymd",		index:"chulgo_ymd", 	align:"center",	width:120, },	//출고일
					],
			
			height:325,			// 높이
			page: 1,			// 기본 페이지 번호
			rowNum: 20,			// row갯수
			rownumbers: true, 	// 행 번호
			rowList: [20,30,40],// 갯수 옵션
			autowidth:true,		// 자동 가로 사이즈
			
			loadComplete: function(data){	//조회 완료시 호출되는 function
				/*
				*	잔고/담보현황 조회 결과가 있을 경우 상단 정보 셋팅
				*/
				var jasu_amt = 0;
				var cur_amt =0;
				var sum_cur_jasu = 0;
				var rate_dmbo = 0;
				
				if(data.records > 0){		//조회 결과가 있을 경우
					jasu_amt = data.sumBalance.jasu_amt;
					cur_amt = data.sumBalance.cur_amt;
					sum_cur_jasu = jasu_amt + cur_amt;		
					sale_dambo_amt = data.sumBalance.sale_dambo_amt;
					rate_dmbo = Math.round( (sale_dambo_amt/sum_cur_jasu) * 100, 0 );	
					
					$("#before_amt").val(Commons.integerFmt(data.sumBalance.before_amt));	//전월잔고
					$("#jasu_amt").val(Commons.integerFmt(jasu_amt));						//미도래(자수)
					$("#cur_amt").val(Commons.integerFmt(cur_amt));							//현잔고
					$("#sum_cur_jasu").val(Commons.integerFmt(sum_cur_jasu));				//총여신
					
					$("#sale_amt").val(Commons.integerFmt(data.sumBalance.sale_amt));		//금월판매
					$("#tasu_amt").val(Commons.integerFmt(data.sumBalance.tasu_amt));		//미도래(타수)
					$("#sale_dambo_amt").val(Commons.integerFmt(sale_dambo_amt));			//담보확보액
					
					if(rate_dmbo==Infinity || isNaN(rate_dmbo)){
						$("#rate_dmbo_cur").val("");										//담보확보율
					}else{
						$("#rate_dmbo_cur").val(rate_dmbo+"%");								//담보확보율	
					}
					saleActionFlag = true;	
				}else{
					saleActionFlag = false;
					alert("해당 잔고/담보 자료가 없습니다.");
				}
				
			},
			pager: "#customer_detail_pager"
   		});
		
		/* 거래처 코드,거래처 명 grid 생성. */
		$("#customer_grid_list").jqGrid({
			
			mtype:"post",		// 요청방식
			datatype:"json",						// 결과물 받을 데이터 타입
			colNames:["거래처코드","거래처명"],		// 컬럼명
			colModel:[
						{name:"cust_id",		index:"cust_id",		align:"left",	width:100, key:true},	// 거래처코드
						{name:"cust_nm",		index:"cust_nm ",		align:"left",	width:120}, 			//거래처명
					],
			height:436,
			rowNum: -1,
			autowidth:true,
			
			/* 조회 완료 시 호출 */
			onSelectRow: function(id){
				selectedCustomerId = id;
				getCustomerBalanceGridDetail(id);
			},
			
			loadComplete: function(data){	//조회 완료시 호출되는 function
				
				if(data.records == 0){		//조회 결과가 있을 경우
					alert("해당 거래처가 없습니다.");	
				}else{
					selectedCustomerId = data.rows[0].cust_id;
					$("#customer_grid_list").jqGrid('setSelection', selectedCustomerId);
					getCustomerBalanceGridDetail(selectedCustomerId);
				}
				
			},
		});
		
		/* 조회 버튼 클릭 */
		$("#ct_retrieve_btn_type01").on("click",function(){
			getCustomerBalanceGridList();
		});
		
		/**
		 * 인쇄 버튼 클릭
		 */
		$("#p_print").on("click",function(){
			gridPrint();
		});
		
		/**
		 * 엑셀 버튼 클릭
		 */
		$("#p_excel").on("click",function(){
			Commons.extraAction(saleActionFlag, 'excel', '<%=ONLINE_ROOT%>/business/customerBalanceDetailExcel.do?customerId='+selectedCustomerId, '');
		});
		
		/* 닫기 버튼 클릭 */
		$("#p_close").on("click",function(){
			parent.Commons.closeTab('잔고|담보현황');
		});
		
		/* 거래구분 담보거래처 기본 셋팅 */
		$(":radio[name='gubun']:radio[value='0']").attr('checked', true);
		
	});
	
	/* 브라우저 창의 사이즈가 변경될 때 */
    $(window).resize(function(){
		$("#customer_detail").setGridWidth($('.box_type03 h372 mt15').width(), true);	//grid 영역의 넓이가 동적으로 조절 
	});
    
    /* 윈도우의 모든 객체/파일이 로드 완료되었을 때 */
	$(window).load(function(){
		$("body").on("keydown", function(event){
			if($("#ct_retrieve_btn_type01").prop('disabled') == false){
				if(event.keyCode==13 && event.target.name!="grid_count"){
					getCustomerBalanceGridList();
				return false;
				}
			}
		});
	});
	
	/**
	 * grid 조회
	 */
	function getCustomerBalanceGridList(){
		var customerId = $("#as_cust_id").val();
		var gubun = $(":radio[name='gubun']:checked").val();
		
		if(customerId == ""){
			$("#as_cust_id_name").val("");
		}
		
		$("#customer_grid_list").jqGrid('setGridParam',{url:"<%=ONLINE_ROOT%>/business/customerBalanceGridList.do?customerId="+customerId+"&gubun="+gubun}).trigger("reloadGrid", [{page:1}]);	// 호출
	}
	
	/**
	 * grid 조회
	 */
	function getCustomerBalanceGridDetail(id){
		var customerId = id;
		$("#customer_detail").jqGrid('setGridParam',{url:"<%=ONLINE_ROOT%>/business/customerBalanceGridDetail.do?customerId="+customerId}).trigger("reloadGrid", [{page:1}]);	// 호출
	}
	
	/**
	 * 프린트를 하기위해 grid 재생성 
	 */
	function gridPrint(){
		
		var customerId = selectedCustomerId;
		var grid = $("#customer_detail");
		var page = $('.ui-pg-input').val();
		var rows=grid.getCol('bill_gb',true,'count');
		
		$('#customerBalance').html('<table id=\"grid_list_clone\"></table>');
		
		$("#grid_list_clone").jqGrid({
			url:"<%=ONLINE_ROOT%>/business/customerBalanceGridDetail.do?customerId="+customerId,
			// 요청방식
			mtype:"post",
			// 결과물 받을 데이터 타입
			datatype:"json",
			// 컬럼명
			colNames:["담보종류","매출담보금액","만기일","어음번호","발행처","지급처","발행일","출고일"],		// 컬럼명
			// 컬럼 데이터(추가, 삭제, 수정이 가능하게 하려면 autoincrement컬럼을 제외한 모든 컬럼을 editable:true로 지정)
			// edittyped은 text, password, ... input type명을 사용 
			colModel:[
						{name:"bill_gb",		index:"bill_gb",		align:"center",	width:100 },//담보종류
						{name:"sale_dambo_amt",	index:"sale_dambo_amt ",align:"right",	width:120 ,formatter:Commons.integerFmt},//매출담보금액
						{name:"end_ymd",		index:"end_ymd", 		align:"center",	width:120},		//만기일
						{name:"bill_no",		index:"bill_no", 		align:"center",	width:150,},	//어음번호
						{name:"balhang",		index:"balhang", 		align:"left", 	width:150, },	//발행처
						{name:"jigeub",			index:"jigeub", 		align:"left", 	width:200, },	//지급처
						{name:"start_ymd",		index:"start_ymd", 		align:"center", width:120, },	//발행일
						{name:"chulgo_ymd",		index:"chulgo_ymd", 	align:"center",	width:120, },	//출고일
					],
			// 그리드(페이지)마다 보여줄 행의 수 -> 매개변수이름은 "rows"로 요청된다.
			page: page,
			rowNum: rows,
			loadComplete: function(data){
				Commons.extraAction(saleActionFlag, 'print', '<%=ONLINE_ROOT%>/common/commonPrint.do', 'customerBalance');
			}
   		});
		
		
	}
	
	</script>
	
</head>
<body>
	<form name="frm" id="frm">
		<!-- ##### content start ##### -->
		<div class="wrap_search mbn">
				<div class="search">
				<label>거래처</label>
				<input name="as_cust_id" id="as_cust_id" class="w140" type="text" />
				<button type="button" class="btn_search" onclick="javascript:Commons.popupOpen('as_cust_id', '<%=ONLINE_ROOT%>/business/common/customerList.do', '600', '655');" ><span class="blind">찾기</span></button>
				<input name="as_cust_id_name" id="as_cust_id_name" type="text" class="w140 ipt_disable" />
				
				<label class="ml10">거래구분</label>
				<input type="radio" name="gubun" id="gubun" value= "0" /><label for="cle1">담보거래처</label>
				<input type="radio" name="gubun" id="gubun" value= "1" /><label for="cle2">전체거래처</label>
				
				<div class="wrap_search_btn">
					<%=WebUtil.createButtonArea(currPgmNoByUri, "ct_")%>
				</div>
			</div>
		</div>	
		<div class="inner_cont sb_cont">		
			<div class="wrap_btn_group mb10">
				<div class="btn_group ta_r">
					<%=WebUtil.createButtonArea(currPgmNoByUri, "p_")%>
				</div>
			</div>		
			<div class="wrap_customer01">
				<div class="wrap_search">
					<div class="box_type01 float_l">
						<table id="customer_grid_list"></table>
					</div>
					<div class="float_r">
						<div class="search">
							<div class="search_type02">
								<label class="w46 ta_r">전월잔고</label>
								<input type="text" class="w140 ta_right ipt_disable" id="before_amt" readonly>
								
								<label class="w70 ta_r">미도래(자수)</label>
								<input type="text" class="w140 ta_right ipt_disable" id="jasu_amt" readonly>
								
								<label class="w70 ta_r">현잔고</label>
								<input type="text" class="w140 ta_right ipt_disable" id="cur_amt" readonly>
								
								<label class="w70 ta_r">총여신</label>
								<input type="text" class="w140 ta_right ipt_disable" id="sum_cur_jasu" readonly><br>
								
								<label class="w46 ta_r">금월판매</label>
								<input type="text" class="w140 ta_right ipt_disable" id="sale_amt" readonly>
								
								<label class="w70 ta_r">미도래(타수)</label>
								<input type="text" class="w140 ta_right ipt_disable" id="tasu_amt" readonly>
								
								<label class="w70 ta_r">담보확보액</label>
								<input type="text" class="w140 ta_right ipt_disable" id="sale_dambo_amt" readonly>
								
								<label class="w70 ta_r">담보확보율</label>
								<input type="text" class="w140 ta_right ipt_disable" id="rate_dmbo_cur" readonly>
							</div>	
						</div>
						<div class="box_type03 h372 mt15">
							<table id="customer_detail"></table>
							<div id="customer_detail_pager"></div>				
						</div>
					</div>
				</div>
			</div>
		</div>
		<!-- ##### content end ##### -->
	</form>	
	<div id="customerBalance" style="display:none;"><table id="grid_list_clone"></table></div>
	<%@include file="/include/footer.jsp"%>
</body>
</html>