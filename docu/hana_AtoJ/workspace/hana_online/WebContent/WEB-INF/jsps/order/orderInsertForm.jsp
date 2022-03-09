<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%--
 * @파일명 : orderInsertForm.jsp
 * @메뉴명 : 온라인발주 > 주문등록       
 * @최초작성일 : 2014/10/29            
 * @author : 우정아                  
 * @수정내역 : 
--%>
<%@ include file ="/common/path.jsp" %>
<%@ page import="javax.servlet.http.HttpSession" %>
<%@ page import="com.hanaph.saleon.member.vo.LoginUserVO" %>
<%@ page import="com.hanaph.saleon.order.vo.OrderVO" %>
<%
	/*
	*	주문 등록에 필요한 거래처 정보 셋팅
	*/
	OrderVO orderInit = new OrderVO();
	orderInit = (OrderVO)request.getAttribute("orderInit");
	
	/*
	*	도매담당자 정보
	*/
	String ls_sawon_info = StringUtil.nvl(orderInit.getLs_sawon_info(),"");
	
%>
<!DOCTYPE html>
<html lang="ko">
<head>
	<%@ include file ="/include/head.jsp" %>
	<link rel="stylesheet" type="text/css" href="<%=ONLINE_WEB_ROOT%>/css/ui.jqgrid.css">
	<script type='text/javascript' src='<%=ONLINE_WEB_ROOT%>/js/jquery.bpopup.min.js'></script>
	<script type="text/javascript" src="<%=ONLINE_WEB_ROOT%>/js/grid.locale-kr.js"></script>
	<script type="text/javascript" src="<%=ONLINE_WEB_ROOT%>/js/jquery.jqGrid.min.js"></script>
	<script type="text/javascript" src="<%=ONLINE_WEB_ROOT%>/js/formCheck.js"></script>
	<script type="text/javascript" src="<%=ONLINE_WEB_ROOT%>/js/order.js"></script>
	
	<script type="text/javascript">
	var lastSelection=0;			//마지막에 선택한 ROW의 ID (제품코드)
	var itemIdArray = '';			//수량 입력한 제품 코드명을 구분자(,)를 함께 배열로 넣는다.
	var saleActionFlag = false;		//기능(엑셀, 인쇄) 제어를 위한 전역변수
	var inserYn=0;					//저장버튼 flag
	
	var excelUploadFlag = false;			//엑셀 등록 여부.

	/* 화면의 Dom 객체가 모두 준비되었을 때 */
	$(document).ready(function(){
		
		Commons.setDate('','req_date');		//주문요청일 오늘날짜로 set
		
		$('#tot_dambo').val(Commons.addComma(<%=(long)orderInit.getLd_tot_dambo()%>));	//총 담보액 set
		$('#rem_dambo').val(Commons.addComma(<%=(long)orderInit.getRem_dambo()%>));		//주문 가능한 set
		
		$('#control_rate_day').val(<%=orderInit.getControl_rate_day()%>); // 주문제어회전일
		$('#rate_day').val(<%=orderInit.getRate_day()%>); // 회전일
		
		<%-- 2017-12-16 FNSLORDCHECK 펑션을 이용하여 체크하는 방식으로 로직 변경
			/*
			*	파워빌더 wf_credit_chk()
			*	여신한도액 초과여부, 여신규정 자료 유무, 거래처/담당자 정보 유무 체크
			*/
			if(orderInit.isResult()==false){		//여신한도액 초과
				out.println("alert('여신한도액을 초과하였습니다. 담당자에게 문의하세요.');");		//javascript alert
			
			}else{
				if(orderInit.getLd_dambo_rate() == 0 && 
					orderInit.getLd_credit_amt() == 0 && 
						orderInit.getLd_credit_dambo() == 0){	//여신규정 자료 유무 체크
					out.println("alert('여신규정 자료가 없습니다.');");
				}
		
				if(StringUtil.nvl(orderInit.getLs_sawon_id(),"").equals("") &&
					StringUtil.nvl(orderInit.getLs_yeondae(),"").equals("") &&
						StringUtil.nvl(orderInit.getIs_yeondae3(),"").equals(""))	{	//거래처 자료 유무 체크
					out.println("alert('거래처코드: "+userEmpCode+"로 거래처 자료가 없습니다.');");			//javascript alert
				}else{
					if(StringUtil.nvl(orderInit.getLs_sawon_info(),"").equals("")){		//담당자 자료 유무 체크
						out.println("alert('사원코드:"+orderInit.getLs_sawon_id()+"로 담당자 자료가 없습니다.');");			//javascript alert
					}
				}
				if(orderInit.getLl_count() > 1){		//거래처 사업번호 체크
					out.println("alert('이 거래처에 사업자번호가 1개 이상 존재합니다. 관련부서로 문의 바랍니다.');");			//javascript alert
				}
				
				if(StringUtil.nvl(orderInit.getLs_sawon_info(),"").equals("")){		//담당자 자료 유무 체크
					out.println("alert('사원코드: "+userEmpCode+"로 담당자 자료가 없습니다.');");			//javascript alert
				}
			}
			/*
			*	파워빌더 wf_budong_chk()
			*	출하중지처 여부 체크 및 거래처 이메일 유무 체크
			*/
			if(!StringUtil.nvl2(orderInit.getLs_budong_yn(),"").equals("")){		
				if(StringUtil.nvl2(orderInit.getLs_budong_yn(),"").equals("Y")){	
					out.println("alert('주문이 제한 되었습니다.\\n담당자에게 문의하시기 바랍니다.');");			//javascript alert
				}
			
				if (StringUtil.nvl2(orderInit.getLs_email(),"").equals("")){	//E-mail체크
					out.println("alert('해당거래처의 E-mail주소 정보가 없습니다.\\n담당자에게 문의하시기 바랍니다.');");			//javascript alert
				}
				
			}else{
				out.println("alert('거래처코드: "+userEmpCode+"로 거래처 자료가 없습니다.');");			//javascript alert
			}
		--%>
		<%
		/*
		2017.12
		FNSLORDCHECK 펑션을 이용하여 거래처 상태를 체크 한다.

		키 : SAGOCK, -- 사고처 체크 
		키 : CUSTDIVCHK, 값 : * -- 직거래처 여부
		키 : SECURITYLMTCK, 값 : N -- 담보가치한도체크
		키 : NOCOLLMTCK, 값 : N -- 무수금한도체크
		키 : ORDERCTRLYN, 값 : N -- 주문제한처여부
		키 : LOANLMTCK, 값 : * -- 여신한도체크
		키 : MEDICALCODECHK, 값 : * --요양기관번호 존재여부 
		키 : BALANCELMTCK, 값 : * -- 잔고한도체크
		키 : EMAILCHK, 값 : N -- 이메일존재여부
		키 : CREDITCK, 값 : N --신용한도체크
		키 : STOPDIV, 값 : N --중지거래처 체크
		키 : TURNLMTCK, 값 : N --회전일한도 체크
		*/

		if("*".equals(orderInit.getBeforeChk().get("ORDERCTRLYN").toString())){
			out.println("alert('주문이 제한 되었습니다.\\n담당자에게 문의하시기 바랍니다.');");			//javascript alert
		}
		
		if("*".equals(orderInit.getBeforeChk().get("LOANLMTCK").toString())){
			out.println("alert('여신한도액을 초과하였습니다. 담당자에게 문의하세요.');");		//javascript alert
		}

		if("*".equals(orderInit.getBeforeChk().get("EMAILCHK").toString())){
			out.println("alert('해당거래처의 E-mail주소 정보가 없습니다.\\n담당자에게 문의하시기 바랍니다.');");			//javascript alert
		}
		
		
		if(orderInit.getLl_count() > 1){		//거래처 사업번호 체크
			out.println("alert('이 거래처에 사업자번호가 1개 이상 존재합니다. 관련부서로 문의 바랍니다.');");			//javascript alert
		}

		if(StringUtil.nvl(orderInit.getLs_sawon_info(),"").equals("")){		//담당자 자료 유무 체크
			out.println("alert('"+ls_sawon_info+"에 대한 거래처(담당자) 자료가 없습니다.');");			//javascript alert
		}

		%>
		
		
		/* jqGrid의 jsonReader 옵션 전역 설정. row 반복 형태의 json 처리. */
	    jsonReader : {
            repeatitems: false;
    	}
		
		/* 엑셀업로드 후 판매처 jqGrid 생성 및 options 설정. */
		$("#grid_list2").jqGrid({
			editurl:"<%=ONLINE_ROOT%>/order/excelStoreGridList.do",
			mtype:"post",
			datatype:"json",
			colNames:["판매처코드","판매처명","배송지코드","배송지주소"],
			colModel:[
				{name:"rcust_id",	index:"rcust_id",	align:"center",		width:60},			//판매처코드
				{name:"rcust_nm",	index:"rcust_nm ",	align:"center",		width:80},			//판매처명
				{name:"addrseq",	index:"addrseq", 	align:"center",		width:30},			//배송지코드
				{name:"addrname",	index:"addrname", 	align:"left", 	    width:200},			//배송지주소
				
			],
			rowNum: -1,
			onSelectRow: detailOrderGrid,		//row를 클릭등으로 selection할 때 detailOrderGrid 함수를 실행함
			height:100,
			autowidth:true,
			rownumbers: true, 
			loadComplete: function(data){	//조회 완료시 호출되는 function
				
				if(data.records == 0){		//조회결과가 없을 경우
					alert("판매처 자료가 없습니다. 엑셀 데이터를 확인해주세요.");
					$("#grid_list2").jqGrid('clearGridData');
					$("#grid_list").jqGrid('clearGridData');
				}else{						//조회결과가 있을 경우
						
				}
			}			
   		});
		
		/* jqGrid 생성 및 options 설정. */
		/* 수량 위반 항목 체크를 위한 threeavgck 추가*/
		$("#grid_list").jqGrid({
			editurl: "<%=ONLINE_ROOT%>/order/itemGridList.do",
			mtype:"post",
			datatype:"json",
			colNames:["제품코드","제품명","규격","수량","단가","공급가액","세액","합계금액","bas_amt","dc_danga","dc_amt","percent","saupjang_cd","psb_qty","m_qty","threeavgck"],
			colModel:[
						{name:"item_id",	index:"item_id",	align:"center",	width:120,	key:true},							//제품코드
						{name:"item_nm",	index:"item_nm ",	align:"left",	width:300,	formatter:Order.saupjangFormatter},	//제품명
						{name:"standard",	index:"standard", 	align:"center",	width:120},										//규격
						{name:"qty",		index:"qty", 		align:"center", width:100,	editable: true, editype : "text", formatter:"textbox",
							editoptions : 	Commons.jqgridEditOptions('grid_list', 'N', jqgridEditOptions_callback)	
						},																										//수량
						{name:"bal_amt",	index:"bal_amt", 	align:"right", 	width:100,	formatter:Order.amountFormatter},	//단가
						{name:"supply_net",	index:"supply_net", align:"right",  width:120,	formatter:Order.amountFormatter},	//공급가액
						{name:"supply_vat",	index:"supply_vat", align:"right",	width:120,	formatter:Order.amountFormatter},	//세액
						{name:"tot",		index:"tot",		align:"right",	width:150,	formatter:Order.amountFormatter},	//합계금액
						{name:"bas_amt",	index:"bas_amt", 	hidden:true},	//bas_amt
						{name:"dc_danga",	index:"dc_danga", 	hidden:true},	//dc_danga
						{name:"dc_amt",		index:"dc_amt", 	hidden:true},	//dc_amt
						{name:"percent",	index:"percent", 	hidden:true},	//percent
						{name:"saupjang_cd",index:"saupjang_cd",hidden:true},	//제품 구분 코드(일반,마약,향정)
						{name:"psb_qty",	index:"psb_qty",	hidden:true},	//주문한도
						{name:"m_qty",		index:"m_qty",		hidden:true},	//m_qty
						{name:"threeavgck",	index:"threeavgck",	hidden:true}
					],
			rowNum: -1,
			onSelectRow: editRow,		//row를 클릭등으로 selection할 때 editRow 함수를 실행함
			height:461,
			autowidth:true,
			loadComplete: function(data){	//조회 완료시 호출되는 function
				
				if(data.records == 0){		//조회 결과가 없을 경우
					alert("해당 제품단가 자료가 없습니다.");
					saleActionFlag=false;
				}else{						//조회 결과가 있을 경우
					saleActionFlag=true;
					$("#grid_list").jqGrid('setSelection', data.rows[0].item_id);	//첫번째 로우 편집 모드로 변경
				}
			}
   		});
		
		/* 입력 버튼 클릭 */
		$("#p_insert").on("click",function(){
			searchStore();
		});
		
		/* 저장 버튼 클릭 */
		$("#p_save").on("click",function(){
			loadingBar();
			saveRow();
		});
		
		/* 인쇄 버튼 클릭 */
		$("#p_print").on("click",function(){
			gridPrint();
		});
		
		/* 엑셀 버튼 클릭 */
		$("#p_excel").on("click",function(){
			Commons.extraAction(saleActionFlag, 'excel', '<%=ONLINE_ROOT%>/order/onderInsertGridListExcelDown.do', '');
		});
		
		/* 닫기 버튼 클릭 */
		$("#p_close").on("click",function(){
			parent.Commons.closeTab('주문등록');
		});
		
		
		/* 엑셀 주문 클릭 */
		$("#cm_excelOrder").on("click",function() {
			openExcelUploadPopup();
		});
		
	});
	
	/* 브라우저 창의 사이즈가 변경될 때 */
    $(window).resize(function(){
    	$("#grid_list2").setGridWidth($('#divItemGrid').width()-2, true);		//grid 영역의 넓이가 동적으로 조절
		$("#grid_list").setGridWidth($('#divItemGrid').width()-2, true);	//grid 영역의 넓이가 동적으로 조절
	});
    
    /* 화면의 모든 객체가 모두 로드되었을 때 */
	$(window).load(function(){
		/* 엔터키를 눌렀을 경우 조회되도록 */
		$("body").on("keydown", function(event){
			if($("#p_insert").prop('disabled') == false){
				if(event.keyCode==13 && event.target.name!="grid_count"){
					searchStore();
					return false;	
				}
			}
		});
	});

    //2018-01-11 loading bar...
	function loadingBar(){
		$('#pro').bPopup({
			content:'iframe', //'ajax', 'iframe' or 'image'
			//iframeAttr:'scrolling="no" frameborder="0" width="600px" height="800px"',
			follow: [true, true],
			contentContainer:'.member_content',
			modalClose: false,
            opacity: 0.6,
            positionStyle: 'fixed',
            modalColor : 'white'
		});
	}


	/**
	 * 판매처 input에서 포커스 아웃되면 실행.
	 * 판매처 정보를 조회해서 판매처에 대한 체크를 진행해서 이상이 없을 경우 제품 목록 조회한다.
	 * @param rcust_id	판매처 코드
	 */
	function storeChkAjax(rcust_id, excelOrderFlag, excelAddrseq){
		
		//1
		<%if("*".equals(orderInit.getBeforeChk().get("LOANLMTCK").toString())){%>
			alert('여신 한도가 초과되었습니다.\n담당자에게 문의하시기 바랍니다.');
			return;
		<%}%>

		
		// 제품 jqgrid 초기화
		$("#grid_list").jqGrid('clearGridData');
		
		//CHOE 2017.02.22 관리부 이준 요청 : 선입금 처가 아닌 경우에만 회전일 관리를 확인한다. (선입금처 회전일 무시하고 주문)
		//체크사항 : 기존 로직은 true 신규로직은 false
		if('<%=orderInit.getPre_deposit()%>' == 'N'){
            //2017.12 FNSLORDCHECK 펑션 이용하여 회전일 체크
            <%if("*".equals(orderInit.getBeforeChk().get("TURNLMTCK").toString()  )  )   { %>
				alert('회전일이 초과되었습니다.\n담당자에게 문의하시기 바랍니다.');
				$("#rcust_id_name").val("");
				$("#rsawon_info").val("");
				$("#rsawon_id").val("");
				return;
			<%}%>

			
/*			
			if (("" != $("#control_rate_day").val() && 0 < Number($("#control_rate_day").val())) && Number($("#rate_day").val()) > Number($("#control_rate_day").val())) {
				alert("회전일이 기준 회전일(" + $("#control_rate_day").val() + "일) 보다 초과되었습니다.");
				$("#rcust_id_name").val("");
				$("#rsawon_info").val("");
				$("#rsawon_id").val("");
				return;
			}
*/
		}
		
		if(rcust_id != '' ){
			$.ajax({
				type:"POST",
				url:"<%=ONLINE_ROOT%>/order/common/storeChkAjax.do",
				data:{ rcust_id : rcust_id },
				dataType:"json",
				//async: false,
				success:function(data){
					if("*" == data.beforeChk['STOPDIV'] ){
						alert("출하중지처 입니다.");
						$("#rcust_id").val("");
						$("#rcust_id_name").val("");
						return;
					}
					/*
					if(data.ls_budong_yn=='y'){
						alert("출하중지처 입니다.");
						$("#rcust_id").val("");
						$("#rcust_id_name").val("");
						return;
					}
					*/
					if(data.ls_cust_nm=='' && data.ls_sawon_id==''){
						alert("등록된 판매처코드가 아닙니다.");
						$("#rcust_id_name").val("");
						$("#rsawon_info").val("");
						return;
					}else{
						$("#rcust_id_name").val(data.ls_cust_nm);
						if(data.ls_sawon_info==''){
							alert("사원코드: "+data.ls_sawon_id+"로 담당자 자료가 없습니다.");
						}else{
							$("#rsawon_info").val(Commons.trim(data.ls_sawon_info));
							$("#rsawon_id").val(data.ls_sawon_id);
						}
						
						// 파라미터 셋팅 - 엑셀업로드 기능추가로 제품 jqgrid 호출 부분 공통메서드로 변경 loadComplete 세팅위해
						//var param = "rcust_id=" + $('#rcust_id').val() ;
						// 제품 jqgrid 호출
						//$("#grid_list").jqGrid('setGridParam',{url:"<%=ONLINE_ROOT%>/order/itemGridList.do?" + param}).trigger("reloadGrid");
						
						// 제품 jqgrid 호출
						if(excelOrderFlag == "Y"){
							excelGridList(rcust_id, excelAddrseq);
						}else{
							gridListReload();
						}
						
						lastSelection=0;
						itemIdArray='';
						$("#ls_limit_yn").val('');
						$("#ls_pro_date").val('');
						$("#ls_pro_bigo").val('');
					}
				},error:function(err){
					alert("storeChkAjax 데이터베이스 관련 오류 발생 "+err.status+'error');
				}
			});
		} else {
			$("#rcust_id_name").val("");
			$("#rsawon_info").val("");
			$("#rsawon_id").val("");
		}
	}
	
    /**
     *	jqGrid에서 제품을 선택했을 시 하단 평균수량, 주문한도 정보등을 DB에서 조회한 후 화면에 셋팅한다.
     *	현재 선택한 제품 코드가 lastSelection변수에 저장된 값과 다를 경우 rowItemChk 함수를 호출해서 이전에 입력한 제품의 수량의 유효성을 체크한다.
     * @param id	선택한 제품 코드
     */
    function editRow(id) {
		var grid = $("#grid_list");
		//하단 평균수량, 주문한도 수량 parameter
		var ls_item_id = grid.getRowData(id).item_id;
       	var saupjang_cd = grid.getRowData(id).saupjang_cd;
       	var req_date = $('#req_date').val();
       	
       	if(!formCheck.isDateFormat(req_date)){
			alert('주문요청일 형식(YYYY-MM-DD)을 확인해주세요.');
			return;
		}
       	var ls_ymd = req_date.replace(/-/gi,"");
       	
       	var lsYn = '';											//허가증 여부
       	//하단 평균수량, 주문한도 수량 ajax
  		$.ajax({
			type:"POST",
			url:"<%=ONLINE_ROOT%>/order/common/itemGbYnAjax.do",
			data:{ ls_item_id : ls_item_id, rcust_id : $('#rcust_id').val(), ls_ymd:ls_ymd, saupjang_cd : saupjang_cd},
		    

			dataType:"json",
			async: false ,
			success:function(data){
				lsYn = data.ls_yn;
				try{
					
					/* -- fnSLordItemCheck 펑션을 통하여 향 정신성인지 체크 하는 로직으로 변경
					if(lsYn == 'N'){ 
						grid.restoreRow(id);
						alert('허가증이 필요합니다.(해당 주문 내역은 저장되지 않습니다.)');
						return;
					}
					*/
					
					try{
						if(data.itemChk['8'] != null && data.itemChk['8'] != ''){
							grid.restoreRow(id);
							alert('허가증이 필요합니다.(해당 주문 내역은 저장되지 않습니다.)');
							return;
						}
					}catch(e){}
					
					
				
					if(data.ll_mavg_qty != 0){
						$("#mavg_qty").val(data.ll_mavg_qty);
					}else{
						$("#mavg_qty").val("0");
					}
					if(data.ll_mqty != 0){
						$("#m_qty").val(data.ll_mqty);
						grid.setCell(id,"m_qty", data.ll_mqty);
					}else{
						$("#m_qty").val("0");
					}
					if(data.ll_psb_qty != 0){
						$("#psb_qty").val(data.ll_psb_qty);
						grid.setCell(id,"psb_qty", data.ll_psb_qty);
					}else{
						$("#psb_qty").val("0");
					}
				}catch(e){
					alert(e['message']);
				}
			},error:function(err){
				alert("itemGbYnAjax 데이터베이스 관련 오류 발생 "+err.status+'error');
			}
      	});	
  		//선택한 row를 수정폼으로 변경하고, 그전에 수량을 입력한 row가 있다면 그 수량에 대한 validation check를 한다.
  		if (id && id != lastSelection) {
  			if(lsYn != 'N'){
  				rowItemChk('row', id);
  			} else {
  				if(lastSelection != 0){
  					grid.setSelection(lastSelection);
  	  				rowItemChk('row', lastSelection);
  				}
  			}
  			
        } 
    }
	
	/**
	 * tab키나 enter키를 입력했을 경우 현재 로우가 마지막 로우일 경우 현재 로우에서 입력된 수량으로  validation check를 한다.
	 * @param rowId	현재 row id
	 * @param nextRowId	다음 row id
	 */
    function jqgridEditOptions_callback(rowId, nextRowId){
		if(rowId && rowId != null && !(nextRowId && nextRowId != null)){
			rowItemChk('row', rowId);
		}
    }
	
	/**
	 * 	저장버튼 click시 거래처에 대한 유효성을 판단한 후 rowItemChk 함수를 호출해서 다음 저장 프로세스 진행한다.
	 *	함수 호출 흐름을 본다면 saveRow() -> rowItemChk() -> damboCheck() -> insertOrderAjax()
	 */
	function saveRow(){
			
		if('<%=orderInit.getPre_deposit()%>' == 'Y'){
				alert("주문금액만큼의 선입금이 필요합니다.");
		}
		//더블클릭 방지
		if(inserYn==1){
			return;
		}
		<%if(!StringUtil.nvl2(orderInit.getLs_budong_yn(),"").equals("")){%>	
		
		//출하중지처체크
		
		
		
		
			<%if("*".equals(StringUtil.nvl2(orderInit.getBeforeChk().get("ORDERCTRLYN"),""))){%>
				alert("주문이 제한 되었습니다.\n담당자에게 문의하시기 바랍니다.");
				return;
			<%}%>
			//E-mail체크
			
			
			<%if("*".equals(StringUtil.nvl2(orderInit.getBeforeChk().get("EMAILCHK"),""))){%>
				alert("해당거래처의 E-mail주소 정보가 없습니다.\n담당자에게 문의하시기 바랍니다.");
				return;
			<%}%>
		<%}%>
		if($("#rcust_id").val()==''){
			$('#pro').bPopup().close();
			alert("판매처를 먼저 선택해주세요.");
			return;
		}
		var req_date = $('#req_date');
       	if(!formCheck.isDateFormat(req_date.val())){
       		$('#pro').bPopup().close();
			alert('주문요청일 형식(YYYY-MM-DD)을 확인해주세요.');
			req_date.focus();
			return;
		}
       	
       	//배송지 등록체크
       	var addrseq = $("#addrseq").val();
		if(addrseq == ""){
			$('#pro').bPopup().close();
			alert("배송지를 등록해주세요.");
			return;
		}
       	
		// DB 80byte 길이 제한
		if (formCheck.getByteLength($('#bigo').val())>75){
			$('#pro').bPopup().close();
			alert('요청사항의 내용은 한글 37자, 영문 80자로 입력해주세요(75byte)');
			return;
		}
		inserYn=1;
		rowItemChk('save',0);
	}
	
	
	/**
	 * 선택한 row를 수정폼으로 변경하고, 그전에 수량을 입력한 row가 있다면 그 수량에 대한 validation check를 한다.
	 *	함수 호출 흐름을 본다면 saveRow() -> rowItemChk() -> damboCheck() -> insertOrderAjax()
	 * @param proc	saveRow에서 호출한건지, editRow함수에서 호출한건지 구분
	 * @param id	현재 선택한 제품 코드
	 */
	function rowItemChk(proc,id){
		//변수 생성
		var grid = $("#grid_list");
		var ld_req_qty = 0;
		var ld_bas_danga = 0;
		var ld_bal_danga = 0;
		var ld_dc_danga = 0;
		var supply_net = 0;
		var supply_vat = 0;
		var amt=0;
		var vat=0;
		var tot_amt=0;
		
		/* 제품 선택 여부*/
		if(!grid.getGridParam("selrow")){
			$('#pro').bPopup().close();
			alert("제품을 선택한 뒤 수량을 입력해 주세요.");
			inserYn=0;
			return;
		}
		/* saveRow에서 호출했을 경우 lastSelection id가 현재 select row id여야 한다. */
		if(id==0){
			lastSelection=grid.getGridParam("selrow");	
		}
		
		/*
		*	lastSelection가 0인 경우 제품선택을 한번도 하지 않았으므로 선택한 제품의 수량 변경할 수 있도록 해주고,
		*	0이 아닐 경우 lastSelection에 저장된 제품코드와 입력된 수량으로 가격 계산을 하고, 제품의 상태(재고,출하중지 등)를 DB에서 조회한 후 체크한다. 			
		*/
		if(lastSelection == 0){		//lastSelection가 0인 경우
			grid.editRow(id);
			lastSelection = id;
			
		}else{						//lastSelection가 0이 아닌 경우 						
			
			var row = grid.getRowData(lastSelection);
			ld_req_qty = $('#'+row.item_id+'_qty').val();
			
			if(ld_req_qty == "" || typeof ld_req_qty == "undefined"){
				ld_req_qty = row.qty;	//editing이 아닐 경우
			}
			
			if(isNaN(ld_req_qty)){
				$('#pro').bPopup().close();
				alert("숫자만 입력가능합니다.");
				grid.setSelection(lastSelection);
				$('#'+row.item_id+'_qty').val("");
				inserYn=0;
				return;
			}	
			
			
			ld_bas_danga = row.bas_amt;
			ld_bal_danga = (row.bal_amt).replace(/,/gi,"");
			ld_dc_danga = row.dc_danga;
			

			//3,4번대거래처는 기준가로 계산한다.
			var ls_rcust_id = $('#rcust_id').val();
			
			/*
			{name:"qty" //수량
			{name:"bal_amt", //단가
			{name:"supply_net", //공급가액
			{name:"supply_vat",	//세액
			{name:"tot",		//합계금액
			*/
			
			var total_amt = 0;//합계금액
			
			//합계금액 : 단가 * 수량
			// total_amt = ld_bal_danga * ld_req_qty;
			total_amt = ld_bal_danga * ld_req_qty * 1.1; // 180104-01 수정 김진국 단가 * 수량 * 1.1 ex) 20 * 22,020 * 1.1 = 484,440원(합계금액)

			
			
			var use_input_qty = parseInt($('#'+row.item_id+'_qty').val());		//사용자가 입력한 수량
			if('' != $('#'+row.item_id+'_qty').val()){
				total_amt = use_input_qty * Number((row.bal_amt).replace(/,/gi,""));	//합계금액
				
				//세액
				if (ls_rcust_id.charAt(0) == '3' || ls_rcust_id.charAt(0) == '4' ){
					supply_vat = (ld_bas_danga * ld_req_qty) - (ld_bal_danga * ld_req_qty);		
				}else{
					// supply_vat = Math.floor(total_amt * 0.1);
					supply_vat = Math.floor(total_amt / 1.1 / 10); // 180104-02 수정 김진국 합계금액에서 0.1 곱해서 공급세액 된 것을 부가세액에서 0.1로 바꿈.
					// 180104-02 합계금액 / (공급가액 + 공급세액)율 / 10
				}
				
				supply_net = total_amt - supply_vat;
				
				var ll_dc_amt = ld_dc_danga * ld_req_qty;
				
				//grid cell에 값 셋팅
				grid.setCell(lastSelection,"supply_vat", supply_vat);
				grid.setCell(lastSelection,"supply_net", supply_net);
				grid.setCell(lastSelection,"dc_amt",ll_dc_amt);
				
				//공급가액+세액 = 합계금액
				grid.setCell(lastSelection,"tot",total_amt);
			}
			
			
			
			
			
			//grid 하단 총 공급가액, 세액, 공급총액 구하는 방식
			var colSupply_net = grid.getCol('supply_net', true);
			var colSupply_vat = grid.getCol('supply_vat', true);
			var colTot = grid.getCol('tot', true);
			
			for(var colInt=0; colInt < colSupply_net.length;colInt++){
				if(colSupply_net[colInt].value!=''){
					var str = (colSupply_net[colInt].value).replace(/,/gi,"");
					amt += parseInt(str);
				}
				if(colSupply_net[colInt].value!=''){
					var str = (colSupply_vat[colInt].value).replace(/,/gi,"");
					vat += parseInt(str);
				}
				if(colSupply_net[colInt].value!=''){
					var str = (colTot[colInt].value).replace(/,/gi,"");
					tot_amt += parseInt(str);
				}
			}
			
			var ld_rem_dambo = parseInt($('#ld_rem_dambo').val()); 
			 
			//grid 하단 공급가액, 세액, 공급총액, 주문 가능액 value set
			$('#amt').val(Commons.addComma(amt));
			$('#vat').val(Commons.addComma(vat));
			$('#tot_amt').val(Commons.addComma(tot_amt));
			$('#rem_dambo').val(Commons.addComma(parseInt(ld_rem_dambo) - tot_amt));
			
			var ll_supply_net = parseInt(supply_net);
			if( ll_supply_net > 0 && ll_dc_amt > 0 ){
				if( ll_supply_net <= ll_dc_amt ){
					$('#pro').bPopup().close();
					alert('에러가 발생 되었습니다.\n화면을 닫고 다시열어어서 주문을 시도하여도 계속발생시\n하나제약 본사로 연락바랍니다.\n'+
							'error code:'+ll_supply_net+':'+ll_dc_amt+':'+ld_req_qty);
					grid.setSelection(lastSelection);
					grid.editRow(lastSelection,true);
					inserYn=0;
					return;
				}
			}
			/* 신용담보금액은 기존로직대로 구하고, 주문가능액은 김태안팀장님이 요청한대로 수정함.2015-01-22 
			 * 주문가능금액 은 수량을 입력할때마다 변경되어야 하는데...계산식은 다음과 같다.   수정요청=> 
	      		주문가능금액 = 여신한도액 - 총여신 - 현재접수상태의주문금액- 현재주문액 합계.    
	      		이때, 주문가능금액이 마이너스가 되는 순간 여신금액위반이라고 떠야 함. 
	        		> 총여신     : 메인화면의 여신현황의 총여신 가져오는 로직과 동일
	        		> 여신한도액 : sale.sale0003 에 credit_limit_amt (여신한도액)
	      		ld_rem_dambo => 주문가능액(여신한도액 - 총여신)
			 * */
			/*
			선입금 거래처 체크로직이 현재 운영에는 없지만 추가되어야 함
        	sale0003.pre_deposit  가 'Y' 이면 "주문금액만큼의 선입금이 필요합니다." 라고 메세지만 보여주고
        	확인하면 주문은 되도록. 여신한도액 체크 안 함.
			*/
			
			if('<%=orderInit.getPre_deposit()%>' == 'N'){
				var v_ld_credit_limit_amt = $('#ld_credit_limit_amt').val().replace(/,/gi,"");
				var v_rem_dambo = $('#rem_dambo').val().replace(/,/gi,"");
				if(v_ld_credit_limit_amt>0 && parseInt(tot_amt)>0){
					if(v_rem_dambo < 0){
						$('#pro').bPopup().close();
						alert('주문금액이 여신 한도액을 초과하였습니다. 담당자에게 문의 바랍니다.');
						grid.setSelection(lastSelection);
						grid.editRow(lastSelection,true);
						inserYn=0;
						return;
					}
				}
			}
			
		
			var ls_rcust_id = $('#rcust_id').val();							//판매처 코드
			var ld_req_qty = parseInt($('#'+row.item_id+'_qty').val());		//사용자가 입력한 수량
			
			
			if(ld_req_qty == 0 || $('#'+row.item_id+'_qty').val()==''){		//수량이 0 또는 아무것도 입력하지 않았을 경우
				
				$('#'+row.item_id+'_qty').val("");
				var arry = itemIdArray.split(',');			//주문 등록할 상품 id의 배열값		
				var itemIdArray2 = "";
				for(var i=0; i < arry.length; i++ ){
					if(arry[i] != row.item_id ){
						if("" == itemIdArray2){
							itemIdArray2=arry[i];	
						}else{
							itemIdArray2 += ','+arry[i];
						}
					}
				}
				itemIdArray = itemIdArray2;
				grid.saveRow(lastSelection);
				lastSelection = id;
				
				if(proc=='save'){			//저장버튼을 클릭했을 경우 damboCheck() 호출해서 다음 저장 프로세스를 진행한다.
					damboCheck();
				}else{						
					grid.editRow(id);
					$('#'+id+'_qty').focus();
				}
			}else{			
				/*
				*	수량이 입력되엇을 경우 제품의 상태(재고,출하중지 등)를 DB에서 조회한 후 체크한다.
				*/
				//태그 제거 정규식
				var pattern = /<[^>]+>/g;
							
				//제품 체크
				var ls_item_cd = row.item_id;
				var ls_item_nm = row.item_nm.replace(pattern, '');
				var ls_standard = row.standard;
				var ldt_jaego = $('#req_date').val().replace(/-/gi,"");
				
				
				
				/**
				*	2017-12-17 체크로직 변경 ==> fnSLordItemCheck 함수 사용 
				*/
				//함수 전달용 파라메터 추가 설정
				var ls_ymd = ldt_jaego;//주문일자
				//var gs_empCode = ''; //session 에서 가져온다.
				
				var ls_p_orderqty = ld_req_qty; // 주문수량
				var ls_p_salprc = row.bal_amt.replace(/,/gi,""); //주문단가
				
				$.ajax({
					type:"POST",
					url:"<%=ONLINE_ROOT%>/order/itemChkRegAjax.do",
					data:{ls_item_cd:ls_item_cd, ldt_jaego:ldt_jaego, ls_rcust_id:ls_rcust_id, ls_ymd:ls_ymd, ls_p_orderqty:ls_p_orderqty, ls_p_salprc:ls_p_salprc},
					dataType:"json",
					async: false,
					success:function(data){
						if("*" == data.productdiv){
							$('#pro').bPopup().close();
							alert('단종된 제품 입니다.');
							grid.setSelection(lastSelection);
							$('#'+row.item_id+'_qty').val("");
							grid.editRow(lastSelection,true);
							inserYn=0;
							return;
						}
						if("*" == data.pieceyn){
							$('#pro').bPopup().close();
							alert("낱알로 등록할 수 없는 제품입니다.");
							grid.setSelection(lastSelection);
							$('#'+row.item_id+'_qty').val("");
							grid.editRow(lastSelection,true);
							inserYn=0;
							return;
						}
						
						
						
						/*
						2017.12 fnSLordItemCheck 및 FNSLORDITEMCHECKT 펑션 이용하여 아이템 체크
						체크 해야 할 사항들
						
						등록된 제품코드가 아닙니다 -- return;  -- x
						단종된 제품입니다 -- return;   -- 3
						출하중지된 제품입니다 -- return; 2
						재고가 없습니다. -- return; -- 12
						주문수량이 가용 재고 수량을 초과하였습니다 -- return; -- x
						주문수량이 창고 재고 수량을 초과하였습니다 -- return; -- x
						40개 단위로 주문되어야 합니다 -- return; -- 5
						
						'제품코드:  는(은) 주문한도 수량 보다 초과되었습니다.이달총주문수량
						n승인이 보류 또는 반려될 수 있으니 담당자와 상의바랍니다.'); -- pass
						
						*/
						
						//체크할 항목 중 return 시켜야 할 항목들의 번호를 아래에 나열한다.
						var chkReturnNums = ['1','2','3','6','9','10'];
 						if (data.ls_chul_yn == '' && data.ls_use_yn == ''){
 							$('#pro').bPopup().close();
 							alert('제품코드 : '+ (ls_item_cd) +'\n' + (ls_item_nm) + ' ' + (ls_standard) + '는 등록된 제품코드가 아닙니다.!');
							grid.setSelection(lastSelection);
							$('#'+row.item_id+'_qty').val("");
							grid.editRow(lastSelection,true);
							inserYn=0;
							return;
						}
						
						for(var test = 0 ; test < chkReturnNums.length ; test++){
							try{
								if(data.itemChk[chkReturnNums[test]] != '' && data.itemChk[chkReturnNums[test]] != null){
									$('#pro').bPopup().close();
									alert(data.itemChk[chkReturnNums[test]]);
									grid.setSelection(lastSelection);
									$('#'+row.item_id+'_qty').val("");
									
									grid.editRow(lastSelection,true);
									inserYn=0;
									return;
								}
							}catch(e){alert(e['message']);continue;}
						}
						
						// 체크 항목중 통과 시켜야 할 항목 나열
						// 2018-01-09 아래의 항목에 위반 되었을때 저장은 되지만  DD,DM 테이블의 threeavgck 컬럼에 * 아닐경우 N 을 보낸다.
						var chkContinueNums = ['20-1','20-2','20-2','20-4','20-5','20-6'];
						grid.setCell(lastSelection,"threeavgck", "N");
						for(var test = 0 ; test < chkContinueNums.length ; test++){
							try{
								if(data.itemChk[chkContinueNums[test]] != '' && data.itemChk[chkContinueNums[test]] != null){
									alert(data.itemChk[chkContinueNums[test]]);
									grid.setCell(lastSelection,"threeavgck", "*");
								}
							}catch(e){continue;}
						}


                        // 2017.12 fnSLordItemCheck 및 FNSLORDITEMCHECKT 펑션 이용하여 아이템 체크  기존 체크 로직 블럭 처리
<%-- 						if (data.ls_chul_yn == '' && data.ls_use_yn == ''){
						alert('제품코드 : '+ (ls_item_cd) +'\n' + (ls_item_nm) + ' ' + (ls_standard) + '는 등록된 제품코드가 아닙니다.!');
						grid.setSelection(lastSelection);
						$('#'+row.item_id+'_qty').val("");
						grid.editRow(lastSelection,true);
						inserYn=0;
						return;
					}else{
						
						if (data.ls_use_yn == 'N'){
							alert('제품코드 : '+ (ls_item_cd)+'\n' + (ls_item_nm) + ' ' + (ls_standard) + '는 단종된 제품입니다.\n담당자에게 문의 하시기 바랍니다.');
							grid.setSelection(lastSelection);
							$('#'+row.item_id+'_qty').val("");
							grid.editRow(lastSelection,true);
							inserYn=0;
							return;
						}
						if (data.ls_chul_yn == 'Y'){
							alert('제품코드 : '+ (ls_item_cd) +'\n' + (ls_item_nm) + ' ' + (ls_standard) + '는 출하중지된 제품입니다.\n담당자에게 문의 하시기 바랍니다.');
							grid.setSelection(lastSelection);
							$('#'+row.item_id+'_qty').val("");
							grid.editRow(lastSelection,true);
							inserYn=0;
							return;
						}
					}
	
					if (data.ld_invjaego_qty ==''){
						alert('제품코드: '+ (ls_item_cd) +'\n' + (ls_item_nm) + ' ' + (ls_standard) + '는 재고가 없습니다.');
						grid.setSelection(lastSelection);
						$('#'+row.item_id+'_qty').val("");
						grid.editRow(lastSelection,true);
						inserYn=0;
						return;
					}else{
						if( parseInt(data.ld_invjaego_qty) >= parseInt(data.ld_jaego_qty) ){				
							//가용재고만큼만
							if ( parseInt(ld_req_qty) > parseInt(data.ld_jaego_qty) ){
								alert('주문수량이 가용 재고 수량을 초과하였습니다.\n제품코드: '+ (ls_item_cd) +'\n' + (ls_item_nm) +' '+(ls_standard)+'는 현재고: '+ Commons.addComma(data.ld_jaego_qty)+ ' 입니다.');
								grid.setSelection(lastSelection);
								$('#'+row.item_id+'_qty').val("");
								grid.editRow(lastSelection,true);
								inserYn=0;
								return;
							}
						} else {
							//창고재고만큼만
							if( parseInt(ld_req_qty) > parseInt(data.ld_invjaego_qty) ){
								alert('주문수량이 창고 재고 수량을 초과하였습니다.\n제품코드: '+ (ls_item_cd) +'\n' + (ls_item_nm) +' '+(ls_standard)+'는 현재고: '+ Commons.addComma(data.ld_invjaego_qty)+ ' 입니다.');
								grid.setSelection(lastSelection);
								$('#'+row.item_id+'_qty').val("");
								grid.editRow(lastSelection,true);
								inserYn=0;
								return;
							}
						}
						
					} 
					//kta 2016.05.25
					//아시크라듀오시럽50m 1병  30003 은 40개 단위로만 주문가능하도록 제어
					if (ls_item_cd == '30003'){
						if ( (parseInt(ld_req_qty) % 40) != 0) {
							alert('제품코드: '+ (ls_item_cd) +'\n' + (ls_item_nm) + ' ' +(ls_standard)+ '는 40개 단위로 주문되어야 합니다.\n ');
							return;								
						}
					}
					
					//제품별 주문한도
					var psb_qty = row.psb_qty;		//주문한도
					var m_qty = row.m_qty;			//이달 합계수량
					
					
					var tot_m_qty = parseInt(ld_req_qty)  + parseInt(m_qty); //사용자가 입력한 수량 + 이달 합계수량
					
					
					if( data.li_cnt == 0 ){		//개시후 3개월 초과거래처 => (이달합계수량 + 현재입력수량) > 30 이면 alert
						if( tot_m_qty > parseInt(psb_qty) ){	
							alert('제품코드: '+ (ls_item_cd) +'\n'+(ls_item_nm) + ' ' +(ls_standard)+ ' 는(은) 주문한도 수량(' + parseInt(psb_qty) + ')보다 초과되었습니다.이달총주문수량(' + tot_m_qty + ')\n승인이 보류 또는 반려될 수 있으니 담당자와 상의바랍니다.1');
						}	
					} else {	//개시후3개월 이내거래처 => (이달합계수량 + 현재입력수량) > 주문한도 이면 alert
						if( tot_m_qty > 30 ){
							if (ls_item_cd != '30003'){  //아시크라듀오시럽50m 1병 은 40병 단위로 구매해야 하므로 제외한다
								alert('제품코드: '+ (ls_item_cd) +'\n' + (ls_item_nm) + ' ' +(ls_standard)+ ' 는(은) 주문한도 수량(' + parseInt(psb_qty) + ')보다 초과되었습니다.이달총주문수량(' + tot_m_qty + ')\n승인이 보류 또는 반려될 수 있으니 담당자와 상의바랍니다.2');
							}
						}
					}
--%>

						
						
						
						
						//저장할 제품ID , 배열로 넣기
						if(itemIdArray ==''){
							itemIdArray = row.item_id;
						}else{
							var yn='N';								
							var arry = itemIdArray.split(',');		
							for(var i=0; i < arry.length; i++ ){
								if(arry[i] == row.item_id ){
									yn='Y';
								}
							}
							if(yn=='N'){
								itemIdArray += ','+row.item_id;
							}
						}
						//$('#'+row.item_id+'_qty').val(Number($('#'+row.item_id+'_qty').val()));
						grid.saveRow(lastSelection);
						
						if(proc=='save'){				//저장버튼을 클릭했을 경우 damboCheck() 호출해서 다음 저장 프로세스를 진행한다.
							damboCheck();
						}else{
							grid.editRow(id);
							lastSelection = id;
						}
					},error:function(err){
						alert("itemChkAjax 데이터베이스 관련 오류 발생 "+err.status+'error');
					}
				});
			}
		}
	}
	
	/**
	 * 판매처 검색
	 */
	function searchStore(){
		//1
		
		<%if("*".equals(orderInit.getBeforeChk().get("LOANLMTCK").toString())){%>
			alert('여신 한도가 초과되었습니다.\n담당자에게 문의하시기 바랍니다.');
			return;
		<%}%>

		
		//CHOE 2017.02.22 관리부 이준 요청 : 선입금 처가 아닌 경우에만 회전일 관리를 확인한다. (선입금처 회전일 무시하고 주문)
		if('<%=orderInit.getPre_deposit()%>' == 'N'){
			
/*
			if (("" != $("#control_rate_day").val() && 0 < Number($("#control_rate_day").val())) && Number($("#rate_day").val()) > Number($("#control_rate_day").val())) {
				alert("회전일이 기준 회전일(" + $("#control_rate_day").val() + "일) 보다 초과되었습니다.");
				return false;
			}
*/
		}
		
		if($('#rcust_id').val()==''){
			alert("판매처를 먼저 입력하세요.");
			return;
		}else{
			lastSelection=0;
			itemIdArray='';
			storeChkAjax($('#rcust_id').val());
			$("#ls_limit_yn").val('');
			$("#ls_pro_date").val('');
			$("#ls_pro_bigo").val('');
		}
	}
	
	/**
	 * 선입금 거래처 체크, 담보체크
	 * 담보예의가 아니면서 공급가합계 > 주문가능금액 일 경우 담보제공 약속 등록 팝업
	 *	함수 호출 흐름을 본다면 saveRow() -> rowItemChk() -> damboCheck() -> insertOrderAjax()
	 */
	function damboCheck(){
		if(itemIdArray==''){
			alert("수량 입력된 제품이 없습니다.");
			var grid = $("#grid_list");
			//grid.editRow(grid.getGridParam("selrow"),true);
			inserYn=0;
			return;
		}else{
			
			var ld_supply_tot_sum = $('#tot_amt').val().replace(/,/gi,"");

			//2018-01-08 저장 전 판매처와 최종금액으로 한번 더 체크 한다.
			
			$.ajax({
				type:"POST",
				url:"<%=ONLINE_ROOT%>/order/common/storeChkAjax.do",
				data:{ rcust_id : $('#rcust_id').val() ,tot_amt : ld_supply_tot_sum},
				dataType:"json",
				//async: false,
				success:function(data){
					if("*" == data.beforeChk['STOPDIV'] ){
						alert("출하중지처 입니다.");
						$("#rcust_id").val("");
						$("#rcust_id_name").val("");
						return;
					}
					if(data.ls_cust_nm=='' && data.ls_sawon_id==''){
						alert("등록된 판매처코드가 아닙니다.");
						$("#rcust_id_name").val("");
						$("#rsawon_info").val("");
						return;
					}
					
					
					
					gotoLastCheck(0);
					
					
				},error:function(err){
					alert("storeChkAjax 데이터베이스 관련 오류 발생 "+err.status+'error');
				}
			});
			
			
		}
	}
	 
	/**
	 * 2018-01-11
	 * 저장 전 grid 한번 더 체크
	 */
	function gotoLastCheck(idx){
		var grid = $("#grid_list");
		
		var gridROwCount = $("#grid_list").getGridParam("reccount");

		if(idx == gridROwCount){
			doInsert();
		}
		
		var row = grid.getRowData(idx);

		if(row.qty == '' || row.qty == null){
			gotoLastCheck(idx + 1);
		}
		
		var ls_item_cd = row.item_id;
		var ls_item_nm = row.item_nm;
		var ldt_jaego = $('#req_date').val().replace(/-/gi,"");
		var ls_rcust_id = $('#rcust_id').val();
		var ls_ymd = req_date = $('#req_date').val().replace(/-/gi,"");
		var ls_p_orderqty = row.qty.replace(/,/gi,"");
		var ls_p_salprc = row.bal_amt.replace(/,/gi,"");
		var ls_standard = row.standard;
		
		
		
		$.ajax({
			type:"POST",
			url:"<%=ONLINE_ROOT%>/order/itemChkRegAjax.do",
			data:{ls_item_cd:ls_item_cd, ldt_jaego:ldt_jaego, ls_rcust_id:ls_rcust_id, ls_ymd:ls_ymd, ls_p_orderqty:ls_p_orderqty, ls_p_salprc:ls_p_salprc},
			dataType:"json",
			async: false,
			success:function(data){
				if("*" == data.productdiv){
					$('#pro').bPopup().close();
					alert('단종된 제품 입니다.');
					grid.setSelection(idx);
					grid.setCell(idx,"qty","");
					grid.editRow(idx,true);
					inserYn=0;
					return;
				}
				if("*" == data.pieceyn){
					$('#pro').bPopup().close();
					alert("낱알로 등록할 수 없는 제품입니다.");
					grid.setSelection(idx);
					grid.setCell(idx,"qty","");
					grid.editRow(idx,true);
					inserYn=0;
					return;
				}
				
				
				
				//체크할 항목 중 return 시켜야 할 항목들의 번호를 아래에 나열한다.
				var chkReturnNums = ['1','2','3','6','9','10'];
				if (data.ls_chul_yn == '' && data.ls_use_yn == ''){
					$('#pro').bPopup().close();
					alert('제품코드 : '+ (ls_item_cd) +'\n' + (ls_item_nm) + ' ' + (ls_standard) + '는 등록된 제품코드가 아닙니다.!');
					grid.setSelection(idx);
					grid.setCell(idx,"qty","");
					grid.editRow(idx,true);
					inserYn=0;
					return;
				}
				
				for(var test = 0 ; test < chkReturnNums.length ; test++){
					try{
						if(data.itemChk[chkReturnNums[test]] != '' && data.itemChk[chkReturnNums[test]] != null){
							$('#pro').bPopup().close();
							alert(data.itemChk[chkReturnNums[test]]);
							grid.setSelection(idx);
							grid.setCell(idx,"qty","");
							grid.editRow(idx,true);
							inserYn=0;
							return;
						}
					}catch(e){alert(e['message']);continue;}
				}

				gotoLastCheck(idx + 1);
			}});
	}
	

	/**
	*2018-01-11 저장 전 체크를 위하여 펑션 분리
	*/
	function doInsert(){
		// 선주문 거래처가 아니거나 담보예의가 아니면서 공급가합계 > 주문가능금액 일 경우 담보제공 약속 등록 팝업 -- 2018-01-09 담보예외처 추가
		var ibCredit_Over = false;

		if ('<%=StringUtil.nvl(orderInit.getIs_yeondae3(),"")%>' == 'Y'){		//담보예외
			$("#ls_limit_yn").val('E');
		}else{
			
			var ld_supply_tot_sum = $('#tot_amt').val().replace(/,/gi,"");
			
			if (parseInt(ld_supply_tot_sum) > parseInt(<%=orderInit.getCredit_dambo()%>)){	//공급가합계 > 주문가능금액 
				ibCredit_Over = true;
				$("#ls_limit_yn").val('Y');		//여신초과
			}else{
				$("#ls_limit_yn").val('N');		//여신이내
			}
		}
		
		if(ibCredit_Over && '<%=orderInit.getSecurityexyn()%>' != 'Y' && '<%=orderInit.getPre_deposit()%>' != 'Y'){
			Commons.popupOpen('damboCheck', '<%=ONLINE_ROOT%>/order/common/guaranteePopup.do', '480', '391');	//팝업 호출
		}else{
			insertOrderAjax();
		}

	}
	/**
	 * 담보제공 약속 등록 팝업에서 기일과 약속사항을 입력하고 닫았을 경우 주문 등록 진행
	 * @param ls_pro_date	기일
	 * @param ls_pro_bigo	약속사항
	 */
	function damboValue(ls_pro_date, ls_pro_bigo){
		$('#ls_pro_date').val(ls_pro_date);
		$('#ls_pro_bigo').val(ls_pro_bigo);
	
		insertOrderAjax();
	}
		
	/**
	 * 주문 등록.
	 *	함수 호출 흐름을 본다면 saveRow() -> rowItemChk() -> damboCheck() -> insertOrderAjax()
	 */
	function insertOrderAjax(){
		var grid = $("#grid_list");
		var arry = itemIdArray.split(',');
		var itemFrm = $('#itemFrm');
		
		$('#itemFrm').empty();		// input tag 생성전에 초기화
		
		$("<input></input>").attr({type:"hidden", name:"ldt_req_date",	value: $('#req_date').val().replace(/-/gi,"")}).appendTo(itemFrm);
		$("<input></input>").attr({type:"hidden", name:"ls_sawon_id",	value:'<%=StringUtil.nvl(orderInit.getLs_sawon_id(),"")%>'}).appendTo(itemFrm);
		$("<input></input>").attr({type:"hidden", name:"ls_rsawon_id",	value:$("#rsawon_id").val()}).appendTo(itemFrm);
		$("<input></input>").attr({type:"hidden", name:"ls_rcust_cd",	value:$("#rcust_id").val()}).appendTo(itemFrm);
		$("<input></input>").attr({type:"hidden", name:"ls_limit_yn",	value:$("#ls_limit_yn").val()}).appendTo(itemFrm);
		$("<input></input>").attr({type:"hidden", name:"ls_pro_date",	value:$("#ls_pro_date").val()}).appendTo(itemFrm);
		$("<input></input>").attr({type:"hidden", name:"ls_pro_bigo",	value:$("#ls_pro_bigo").val()}).appendTo(itemFrm);
		$("<input></input>").attr({type:"hidden", name:"bigo",			value:$("#bigo").val()}).appendTo(itemFrm);
		$("<input></input>").attr({type:"hidden", name:"addrseq",		value:$("#addrseq").val()}).appendTo(itemFrm);
		
		var chkQtyCnt = 0;
		var firstItemId = "";
		for(var i=0; i < arry.length; i++ ){
			var row = grid.getRowData(arry[i]);
			
			$("<input></input>").attr({type:"hidden", name:"item_id", 		value:row.item_id}).appendTo(itemFrm);
			$("<input></input>").attr({type:"hidden", name:"qty", 			value:row.qty}).appendTo(itemFrm);
			$("<input></input>").attr({type:"hidden", name:"bal_amt", 		value:(row.bal_amt).replace(/,/gi,"")}).appendTo(itemFrm);
			$("<input></input>").attr({type:"hidden", name:"supply_net", 	value:(row.supply_net).replace(/,/gi,"")}).appendTo(itemFrm);
			$("<input></input>").attr({type:"hidden", name:"supply_vat", 	value:(row.supply_vat).replace(/,/gi,"")}).appendTo(itemFrm);
			$("<input></input>").attr({type:"hidden", name:"percent", 		value:row.percent}).appendTo(itemFrm);
			$("<input></input>").attr({type:"hidden", name:"dc_amt", 		value:row.dc_amt}).appendTo(itemFrm);
			$("<input></input>").attr({type:"hidden", name:"dc_danga", 		value:row.dc_danga}).appendTo(itemFrm);
			$("<input></input>").attr({type:"hidden", name:"saupjang_cd", 	value:row.saupjang_cd}).appendTo(itemFrm);
			$("<input></input>").attr({type:"hidden", name:"threeavgck", 	value:row.threeavgck}).appendTo(itemFrm);
			
			var ld_req_qty = row.qty;
			if($.trim(ld_req_qty) == "" || isNaN(ld_req_qty)){
				chkQtyCnt++;
				if(firstItemId == ""){
					firstItemId = row.item_id;
				}
			}	
		}
		
		// 수량이 null 들어가는 케이스가 있어서 저장전에 수량 체크한다.
		if(chkQtyCnt > 0){
			
			if(firstItemId != ""){
				$("#grid_list").jqGrid('setSelection', firstItemId);
			}
			
			alert("수량 정보가 제대로 입력되지 않은 항목이 있습니다.");
			$('#itemFrm').empty();
			inserYn=0;
			return;
		}

		$.ajax({
			type:"POST",
			data: itemFrm.serialize(),
			url:"<%=ONLINE_ROOT%>/order/insertOrderAjax.do",
			dataType:"json",
			async: false ,
			success:function(data){
				
				if(data.result=='Y'){
					alert("주문요청 자료를 등록 하였습니다.");
					var rem_dambo = $('#rem_dambo').val().replace(/,/gi,"");
					$('#ld_rem_dambo').val(rem_dambo);
					
					$('#pro').bPopup().close();
				}else{
					alert("주문요청 자료 등록이 실패하였습니다.");
				}
				
			},error:function(err){
				alert("주문등록 데이터베이스 관련 오류 발생 "+err.status+'error');
			},complete:function(){
				
				//저장후 제품 테이블 다시 reload
				// 파라미터 셋팅 - 엑셀업로드 기능추가로 제품 jqgrid 호출 부분 공통메서드로 변경 loadComplete 세팅위해
				//var param = "rcust_id=" + $('#rcust_id').val() ;
				// 제품 jqgrid 호출
				//$("#grid_list").jqGrid('setGridParam',{url:"<%=ONLINE_ROOT%>/order/itemGridList.do?" + param}).trigger("reloadGrid");
				
				if(excelUploadFlag){	//엑셀주문후 저장일 경우
					//등록된 판매처 삭제
					var grid2 = $("#grid_list2");
					grid2.jqGrid('delRowData', grid2.getGridParam("selrow"));

					// 판매처에 해당하는 제품 jqgrid 초기화
					$("#grid_list").jqGrid('clearGridData');
				}else{
					// 제품 jqgrid 호출
					gridListReload();
				}
				
				lastSelection=0;
				itemIdArray='';
				$("#ls_limit_yn").val('');
				$("#ls_pro_date").val('');
				$("#ls_pro_bigo").val('');
				$('#itemFrm').empty();
				$("#mavg_qty").val('');
				$("#psb_qty").val('');
				$("#amt").val('');
				$("#vat").val('');
				$("#tot_amt").val('');
				inserYn=0;
			}
		});
		
	}

	/**
	 * 	인쇄를 하기위해 grid 재생성 	
	 */
	function gridPrint(){
		$('#orderInsert').html('<table id=\"grid_list_clone\"></table>');
		
		var param = "rcust_id=" + $('#rcust_id').val() ;
		$("#grid_list_clone").jqGrid({
			url: "<%=ONLINE_ROOT%>/order/itemGridList.do?" + param,
			// 요청방식
			mtype:"post",
			// 결과물 받을 데이터 타입
			datatype:"json",
			// 컬럼명
			colNames:["제품코드","제품명","규격","수량","단가","공급가액","세액","합계금액"],
			// 컬럼 데이터(추가, 삭제, 수정이 가능하게 하려면 autoincrement컬럼을 제외한 모든 컬럼을 editable:true로 지정)
			// edittyped은 text, password, ... input type명을 사용 
			colModel:[
						{name:"item_id",	index:"item_id",	align:"center",	width:120,	key:true},							//제품코드
						{name:"item_nm",	index:"item_nm ",	align:"left",	width:300,	formatter:Order.saupjangFormatter},	//제품명
						{name:"standard",	index:"standard", 	align:"center",	width:120},										//규격
						{name:"qty",		index:"qty", 		align:"center", width:100,	editable: true, editype : "text", formatter:"textbox"},			//수량
						{name:"bal_amt",	index:"bal_amt", 	align:"right", 	width:100,	formatter:Order.amountFormatter},	//단가
						{name:"supply_net",	index:"supply_net", align:"right",  width:120,	formatter:Order.amountFormatter},	//공급가액
						{name:"supply_vat",	index:"supply_vat", align:"right",	width:120,	formatter:Order.amountFormatter},	//세액
						{name:"tot",		index:"tot",		align:"right",	width:150,	formatter:Order.amountFormatter}	//합계금액
					],
			//-1로 하면 전체 갯수 
			rowNum: -1,
			//수정할수 있는 폼으로 바뀜
			onSelectRow: editRow,
			height:580,
			loadComplete: function(data){
				Commons.extraAction(saleActionFlag, 'print', '<%=ONLINE_ROOT%>/common/commonPrint.do', 'orderInsert');
			}
   		});		
	}
	
	function initdamboCheck(){
		var grid = $("#grid_list");
		grid.editRow(grid.getGridParam("selrow"),true);
		inserYn=0;
		return;
	}
	
	/* 회전일, 주문제어 회전일 비교 */
	/* function rateDayCheck(){
		var control_rate_day = $("#control_rate_day").val();
		var rate_day = $("#rate_day").val();
		
		rate_day = "150";
		var result = "Y";
		if (Number(rate_day) > Number(control_rate_day)) {
			alert("회전일이 기준 회전일(" + control_rate_day + "일) 보다 초과되었습니다.");
			result = "N";
		}
		
		return result;
	} */
	
	/* 거래처 팝업 열기 */
	function openCustomerPopup() {		
		//CHOE 2017.02.22 관리부 이준 요청 : 선입금 처가 아닌 경우에만 회전일 관리를 확인한다. (선입금처 회전일 무시하고 주문)
		
		if('<%=orderInit.getPre_deposit()%>' == 'N'){
            //2017.12 oragmp.FNSLCUSTTURN 펑션으로 회전일 체크
            <%if("*".equals(orderInit.getBeforeChk().get("TURNLMTCK").toString())){%>
				alert('회전일이 초과되었습니다.\n담당자에게 문의하시기 바랍니다.');
				$("#rcust_id_name").val("");
				$("#rsawon_info").val("");
				$("#rsawon_id").val("");
				return;
			<%}%>

			
			/* if (("" != $("#control_rate_day").val() && 0 < Number($("#control_rate_day").val())) && Number($("#rate_day").val()) > Number($("#control_rate_day").val())) {
				alert("회전일이 기준 회전일(" + $("#control_rate_day").val() + "일) 보다 초과되었습니다. pop");
				return false;
			}
			 */
		}
		Commons.popupOpen('rcust_id', '<%=ONLINE_ROOT%>/order/common/storeListPopup.do', '600', '655');
	}
	
	/* 배송지 팝업 열기 */
	function openBaesongjiPopup() {
		Commons.popupOpen('baesongji', '<%=ONLINE_ROOT%>/order/common/baesongjiListPopup.do', '1000', '655');
	}
	
	/* 엑셀주문서 작성방법 안내 팝업 열기 */
	function openExcelOrderWriteInfoPopup() {
		Commons.popupOpen('excelOrder', '<%=ONLINE_ROOT%>/order/common/excelOrderWriteInfoPopup.do', '600', '765');
	}
	
	/* 엑셀 업로드 팝업 열기 */
	function openExcelUploadPopup() {
		Commons.popupOpen('excelUpload', '<%=ONLINE_ROOT%>/order/common/excelUploadPopup.do', '600', '200');
	}
	
	/* 일반적인경우 제품 테이블 다시 reload */
	function gridListReload() {
		// 파라미터 셋팅
		var param = "rcust_id=" + $('#rcust_id').val();
		
		// 제품 jqgrid 호출
		$("#grid_list").jqGrid('setGridParam',{
					url:"<%=ONLINE_ROOT%>/order/itemGridList.do?" + param
					,loadComplete: function(data){
						if(data.records == 0){		//조회 결과가 없을 경우
							alert("해당 제품단가 자료가 없습니다.");
							saleActionFlag=false;
						}else{						//조회 결과가 있을 경우
							saleActionFlag=true;
							$("#grid_list").jqGrid('setSelection', data.rows[0].item_id);	//첫번째 로우 편집 모드로 변경
						}
					}
				}).trigger("reloadGrid");
		
	}
	
	/* 엑셀업로드후 판매처 목록 조회 */
	function excelStoreGridList() {
		//판매처정보초기화
		$("#rcust_id").val("");
		$("#rcust_id_name").val("");
		$("#rsawon_info").val("");
		$("#rsawon_id").val("");
		//배송지세팅
		$("#addrseq").val("");
		$("#baesongji_name").val("");
		
		//판매처 jqgrid 호출
		$("#grid_list2").jqGrid('setGridParam',{url:"<%=ONLINE_ROOT%>/order/excelStoreGridList.do"}).trigger("reloadGrid");
		
		// 제품 jqgrid 초기화
		$("#grid_list").jqGrid('clearGridData');
	}
	
	/* 엑셀업로드후 제품 테이블 다시 reload */
	function excelGridList(rcust_id, addrseq) {

		var grid = $("#grid_list");
		var param = "rcust_id=" + rcust_id + "&addrseq=" + addrseq + "&excelUpload=Y";
		
		// 제품 jqgrid 호출
		$("#grid_list").jqGrid('setGridParam',{
					url:"<%=ONLINE_ROOT%>/order/itemGridList.do?" + param
					,loadComplete: function(data){
						if(data.records == 0){		//조회 결과가 없을 경우
							alert("해당 제품단가 자료가 없습니다.");
						}else{						//조회 결과가 있을 경우
							$("#grid_list").jqGrid('setSelection', data.rows[0].item_id);	//첫번째 로우 편집 모드로 변경
							
							for (var i = 0; i < data.rows.length; i++) {
								//console.log(i+"="+data.rows[i].item_id);
								
								var itemId = data.rows[i].item_id;
					       		if(i > 0 && itemId != ""){
					       			editRow(itemId);
					       			if(i == data.rows.length-1){	//마지막 제품코드일경우 선택값 변경
					       				grid.setSelection(lastSelection);
					       			}
					       		}
							}
						}
					}
				}).trigger("reloadGrid");
		
		lastSelection=0;
		itemIdArray='';
		$("#ls_limit_yn").val('');
		$("#ls_pro_date").val('');
		$("#ls_pro_bigo").val('');
		$('#itemFrm').empty();
		$("#mavg_qty").val('');
		$("#psb_qty").val('');
		$("#amt").val('');
		$("#vat").val('');
		$("#tot_amt").val('');
	}
	
	/**
	*	판매처 row 선택했을 때 선택한 판매처코드를 기준으로 주문 가능 제품을 조회해서 그리드에 뿌려준다.
	*	@param id	주문 master 그리드에서 선택한 구매번호
	*/
	function detailOrderGrid(id){
		excelUploadFlag = true;
		
		var grid = $("#grid_list2");
		var grid_rcust_id = grid.getRowData(id).rcust_id;
		var grid_addrseq = grid.getRowData(id).addrseq;
		var grid_addrname = grid.getRowData(id).addrname;
		
		//판매처 세팅 및 정보조회
		$("#rcust_id").val(grid_rcust_id);
		storeChkAjax(grid_rcust_id, 'Y', grid_addrseq);
		
		//배송지세팅
		if(Commons.trim(grid_addrname) == ""){	//배송지코드 없을경우
			$("#addrseq").val("");
			$("#baesongji_name").val("");			
		}else{
			$("#addrseq").val(grid_addrseq-1);
			$("#baesongji_name").val(grid_addrname);	
		}
	}
	
	/* 엑셀주문서업로드 버튼노출 */
	function chkExcelUpload(){
		if ($("#excelOrderUpload").is(":checked")) {
			$("#spanExcelUpload").show();
			$("#divStoreGrid").show();
			
			$("#grid_list").setGridWidth($('.wrap_result_group').width()-2, true);		//grid 영역의 넓이가 동적으로 조절
			$("#grid_list2").setGridWidth($('.wrap_result_group').width()-2, true);		//grid 영역의 넓이가 동적으로 조절 
		}else{
			$("#spanExcelUpload").hide();
			$("#divStoreGrid").hide();
		}
	}
	
	</script>
	
</head>
<body>
	<form id="frm" name="frm">
	
		<!-- temp01 -->
		<input type="hidden" id="ld_credit_limit_amt" name="ld_credit_limit_amt" value="<%=(long)orderInit.getLd_credit_limit_amt()%>" />
		<!-- temp02 -->
		<input type="hidden" id="ld_sale_tot_credit" name="ld_sale_tot_credit" value="<%=(long)orderInit.getLd_sale_tot_credit()%>" />
		<input type="hidden" id="m_qty" name="m_qty" value="" />
		<input type="hidden" id="item_seq" name="item_seq" value="" /> 
		<input type="hidden" id="credit_dambo" name="credit_dambo" value="<%=(long)orderInit.getCredit_dambo()%>" />
		<input type="hidden" id="ld_rem_dambo" name="ld_rem_dambo" value="<%=(long)orderInit.getLd_rem_dambo()%>" />
		<input type="hidden" id="rsawon_id" name="rsawon_id" value="" />
		<input type="hidden" id="ls_pro_date" name="ls_pro_date" value="" />
		<input type="hidden" id="ls_pro_bigo" name="ls_pro_bigo" value="" />
		<input type="hidden" id="ls_limit_yn" name="ls_limit_yn" value="" />
		
		<input type="hidden" id="control_rate_day" name="control_rate_day" value="" />
		<input type="hidden" id="rate_day" name="rate_day" value="" />
	
		<!-- ##### content start ##### -->	
		<div class="wrap_search">
			<!-- 엑셀 업로드 관련 주석 처리 : display 여부로 컨트롤 -->
			<div class="search" style="display:none;padding-bottom: 0px;">
				<input type="checkbox" name="excelOrderUpload" id="excelOrderUpload" value="Y" onclick="chkExcelUpload();" />
				<label class="w140" for="excelOrderUpload">엑셀업로드 주문하기</label>
				
				<span class="wrap_btn_group" id="spanExcelUpload" style="display: none;">
					<span class="btn_group">
						<button type="button" onclick="openExcelOrderWriteInfoPopup();">엑셀주문서작성법</button>
						<button type="button" id="cm_excelOrder">엑셀주문서업로드</button> 
						
						&nbsp;&nbsp;
						엑셀주문서를 업로드 후 판매처코드별로 배송지 확인 및 요청사항 입력 후 저장 하십시요.
					</span>
				</span>
			</div>
			
			<div class="search">
				<div class="float_l">
					<label class="w70">주문요청일</label>
					<p class="wrap_date">
						<input type="text" id="req_date" name="req_date" maxlength="10" onchange="javascript:Commons.setDate('','req_date');"  readonly class="ipt_disable" />
						<button type="button" class="btn_date"><span class="blind">달력보기</span></button>
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<label class="w50">배송지</label> 
						<button type="button" class="btn_search" onclick="openBaesongjiPopup();"><span class="blind">찾기</span></button>
						<input type="text" class="w200 ipt_disable" id="baesongji_name" name="baesongji_name" readonly="readonly" value="<%=StringUtil.nvl( orderInit.getAddr1() )%>"/><br />
						<input type="hidden" id="addrseq" name="addrseq"  value="<%= StringUtil.nvl( orderInit.getSeq() )%>" />
					</p><br />
					
					<!-- 
					<label class="w50">배송지</label> 
					<button type="button" class="btn_search" onclick="javascript:openBaesongjiPopup();"><span class="blind">찾기</span></button>
					<input type="text" class="w200 ipt_disable" id="baesongji_name" name="baesongji_name" readonly/><br />
					 -->
					
					<label class="w70">판매처</label>
					<input type="text" class="w140" id="rcust_id" name="rcust_id"  onchange="javascript:storeChkAjax(this.value);"/>
					<button type="button" class="btn_search" onclick="javascript:openCustomerPopup();"><span class="blind">찾기</span></button>
					<input type="text" class="w265 ipt_disable" id="rcust_id_name" name="rcust_id_name" readonly/><br />
					
					<label class="w70">요청사항</label>
					<input type="text" class="w435" id="bigo" name="bigo" value=""/>
				</div>
				<div class="float_r">
					<label class="w70">도매담당자</label>
					<input type="text" class="w350 ipt_disable" id="sawon_info" name="sawon_info" value="<%=ls_sawon_info.trim() %>" readonly/><br />
					<label class="w70">병원담당자</label>
					<input type="text" class="w350 ipt_disable" id="rsawon_info" name="rsawon_info" value="" readonly/><br />
					<label class="w70">총담보</label>
					<input type="text" class="w120 ta_right ipt_disable" id="tot_dambo" name="tot_dambo" value="" readonly/>
					<label class="w70 ml10">주문가능액</label>
					<input type="text" class="w140 ta_right ipt_disable" id="rem_dambo" name="rem_dambo" value="" readonly/>
				</div>
			</div>
		</div>
		
		<div class="wrap_btn_group">
			<div class="btn_group">
				
				<div class="float_l">
					[설명] (평균수량=직전3개월평균 * <%=(long)orderInit.getLs_jumun_limit() %>%) (주문한도=평균수량-해당월)
				</div>

				<div class="float_r ta_r">
					<%=WebUtil.createButtonArea(currPgmNoByUri, "p_")%>
				</div>
			</div>
		</div>
		<div class="inner_cont2">
			<div class="wrap_result_group" id="divStoreGrid" style="display: none;">
				<table id="grid_list2"></table>
			</div>
			<div class="wrap_result_group" id="divItemGrid">
				<table id="grid_list"></table>
			</div>
			<div class="wrap_result_group">
				<div class="result_group">
					<div class="float_l">
						<label class="point">평균수량</label>
						<input type="text" class="w100 ta_right ipt_disable" id="mavg_qty" readonly/>
						<label class="point ml10">주문한도</label>
						<input type="text" class="w100 ta_right ipt_disable" id="psb_qty" readonly/>
					</div>
					<div class="float_r">
						<label class="point">공급가액</label>
						<input type="text" class="w100 ta_right ipt_disable" id="amt" readonly/>
						<label class="point ml10">세액</label>
						<input type="text" class="w100 ta_right ipt_disable" id="vat" readonly/>
						<label class="point ml10">공급총액</label>
						<input type="text" class="w100 ta_right ipt_disable" id="tot_amt" readonly/>
					</div>
				</div>
			</div>
		</div>
		<!-- ##### content end ##### -->
				
	</form>
	<div id="orderInsert" style="display:none;"><table id="grid_list_clone"></table></div>
	<div id='pro' style='display:none; width:auto; height:auto; '>
		<img alt='loading' src='/hanagw/asset/img/ajax-loader.gif' />
	</div>
	
	<form id="itemFrm" name="itemFrm">
	</form>
	<%@include file="/include/footer.jsp"%>
</body>
</html>