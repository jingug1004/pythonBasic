<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%--
 * @파일명 : appliBusi.jsp
 * @메뉴명 : 명함신청
 * @최초작성일 : 2016/11/04            
 * @author : CHOE                
 * @수정내역 : 
--%>
<%@ page import="java.util.List" %>
<%@ page import="com.hanaph.gw.co.code.vo.CodeVO" %>
<%@ page import="com.hanaph.gw.co.common.utils.StringUtil" %>
<%@ page import="com.hanaph.gw.co.common.utils.RequestFilterUtil" %>
<%@ page import="com.hanaph.gw.of.appli.vo.AppliBusiVO" %>
<%@ include file ="/common/path.jsp" %>
<%
	//List<MemberVO> memberBoardList = (List<MemberVO>)request.getAttribute("memberBoardList"); //등록 되어 있는 대상자 리스트
	
	AppliBusiVO applibusiVO = (AppliBusiVO)request.getAttribute("applibusiVO");
	if(applibusiVO == null){
		applibusiVO = new AppliBusiVO();
	}
	
	//String v_busi_emp_nm = "";
	
%>
<!DOCTYPE html>
<html lang="ko">
<head>
	<%@include file="/include/head.jsp"%>
	<link rel="stylesheet" type="text/css" media="all" href="<%=GROUP_WEB_ROOT%>/css/main.css" />		
	<script type="text/javascript">
	var subtmit=false;		//submit flag
	/**
	 * 명함 신청
	 */
	function insertAppliBusi(){
		
		if($("#busi_dept_nm").val() == ""){
			alert("부서를 입력해 주세요.");
			document.frm.busi_dept_nm.focus();
			return;
		}else if($("#busi_grade_nm").val() == ""){
			alert("직급을 입력해 주세요.");
			document.frm.busi_grade_nm.focus();
			return;	
		}else if($("#busi_emp_nm").val() == ""){
			alert("이름을 입력해 주세요.");
			document.frm.busi_emp_nm.focus();
			return;	
		}else if($("#busi_emp_no").val() == ""){
			alert("사원번호를 입력해 주세요.");
			document.frm.busi_emp_no.focus();
			return;	
		/*}else if(formCheck.getByteLength(document.frm.addr1.value) > 100){
			alert("주소1는 한글 50자 (영문 100자) 이상 입력할수 없습니다");
			document.frm.addr1.focus();
			return;
		}else if(formCheck.getByteLength(document.frm.tel1.value) > 20){
			alert("전화1는 숫자 20자 이상 입력할수 없습니다");
			document.frm.tel1.focus();
			return;
		}else if(formCheck.getByteLength(document.frm.fax1.value) > 20){
			alert("팩스1는 숫자 20자 이상 입력할수 없습니다");
			document.frm.fax1.focus();
			return;
		}else if(formCheck.getByteLength(document.frm.addr2.value) > 100){
			alert("주소2은 한글 50자 (영문 100자) 이상 입력할수 없습니다");
			document.frm.addr2.focus();
			return;
		}else if(formCheck.getByteLength(document.frm.tel2.value) > 20){
			alert("전화2는 숫자 20자 이상 입력할수 없습니다");
			document.frm.tel2.focus();
			return;
		}else if(formCheck.getByteLength(document.frm.fax2.value) > 20){
			alert("팩스2는 숫자 20자 이상 입력할수 없습니다");
			document.frm.fax2.focus();
			return;
		}else if(formCheck.getByteLength(document.frm.addr3.value) > 100){
			alert("주소3는 한글 50자 (영문 100자) 이상 입력할수 없습니다");
			document.frm.addr3.focus();
			return;
		}else if(formCheck.getByteLength(document.frm.tel3.value) > 20){
			alert("전화3는 숫자 20자 이상 입력할수 없습니다");
			document.frm.tel3.focus();
			return;
		}else if(formCheck.getByteLength(document.frm.fax3.value) > 20){
			alert("팩스3는 숫자 20자 이상 입력할수 없습니다");
			document.frm.fax3.focus();
			return;
		}else if(formCheck.getByteLength(document.frm.addr1_en.value) > 100){
			alert("영문 주소1은 영문 100자 이상 입력할수 없습니다");
			document.frm.addr1_en.focus();
			return;
		}else if(formCheck.getByteLength(document.frm.tel1_en.value) > 20){
			alert("영문 전화1는 숫자 20자 이상 입력할수 없습니다");
			document.frm.tel1_en.focus();
			return;
		}else if(formCheck.getByteLength(document.frm.fax1_en.value) > 20){
			alert("영문 팩스1는 숫자 20자 이상 입력할수 없습니다");
			document.frm.fax1_en.focus();
			return;
		}else if(formCheck.getByteLength(document.frm.addr2_en.value) > 100){
			alert("영문 주소2은 영문 100자 이상 입력할수 없습니다");
			document.frm.addr2_en.focus();
			return;
		}else if(formCheck.getByteLength(document.frm.tel2_en.value) > 20){
			alert("영문 전화2는 숫자 20자 이상 입력할수 없습니다");
			document.frm.tel2_en.focus();
			return;
		}else if(formCheck.getByteLength(document.frm.fax2_en.value) > 20){
			alert("영문 팩스2는 숫자 20자 이상 입력할수 없습니다");
			document.frm.fax2_en.focus();
			return;
		}else if(formCheck.getByteLength(document.frm.addr3_en.value) > 100){
			alert("영문 주소3은 영문 100자 이상 입력할수 없습니다");
			document.frm.addr3_en.focus();
			return;
		}else if(formCheck.getByteLength(document.frm.tel3_en.value) > 20){
			alert("영문 전화3는 숫자 20자 이상 입력할수 없습니다");
			document.frm.tel3_en.focus();
			return;
		}else if(formCheck.getByteLength(document.frm.fax3_en.value) > 20){
			alert("영문 팩스3는 숫자 20자 이상 입력할수 없습니다");
			document.frm.fax3_en.focus();
			return;*/
		}
		//alert("확인 끝 저장 시작");
		if(subtmit==false){
			if(confirm("명함 신청을 하시겠습니까?") == true){
				document.frm.action="<%=GROUP_ROOT%>/of/appli/insertAppliBusi.do";				
				document.frm.submit();				
				subtmit=true;
			}
		}else{
			alert("신청하는 중 입니다.");
		}
	}
	
	/**
	 * 신청자 선택 팝업
	 */
	function getMember(){		
		$('#getMember').bPopup({
			content:'iframe', //'ajax', 'iframe' or 'image'
			//iframeAttr:'scrolling="no" frameborder="0" width="600px" height="800px"',
			iframeAttr:'scrolling="yes" frameborder="0" width="617px" height="'+$(window).innerHeight()+'px"',
			follow: [true, true],
			contentContainer:'.member_content',
			modalClose: true,
            opacity: 0.6,
            positionStyle: 'fixed',
			loadUrl:'<%=GROUP_ROOT%>/pe/member/memberSelectPopup.do?type=BUSI',
		});		
	}
	
	/*
	 *기존 row 삭제 
	 */
	function memberRemove(){
		//alert("memberRemove");
 		var html =	'<colgroup><col style="width:12%;"><col style="width:21%;"><col style="width:2%;"><col style="width:21%;"><col style="width:1%;"><col style="width:21%;"><col style="width:1%;"><col style="width:21%;"></colgroup>';
 		 //   html += '<tr><td colspan = "7">&nbsp</td></tr><tr><td></td><td>부서 </td><td></td><td>직급  </td><td>이름 </td><td></td></tr>'; 		   
 		 		
	 	$('#tbl tr').remove();
	 	$("#tbl").html(html);
	}
	
	
	/* 반드시 addMemberRow 로 function 만들어 테이블 상황에 맞게 테이블 생성한다. */
	/**
	 * @param obj
	 */
	function addMemberRow(obj){
		memberRemove();
		for(var i=0; i<obj.length; i++){
			if(obj[i].checked){
				if (obj.length == 1){
				    var objParam = obj[i].value;
				    //alert("addMemberRow objParam " + objParam);
				    
					var memberListValueArr = objParam.split("|");
					var objTable = document.getElementById("tbl");
					//var objRow = document.getElementById('tbl').insertRow(1); // row 생성
					var objRow = document.getElementById('tbl')
					
					//0번째 줄 시작					
					var objTr_0 = document.createElement("TR");
					objRow.appendChild(objTr_0);
					var objTd_0 = document.createElement('TD');
					objTd_0.setAttribute("width", "600"); // 넓이
					objTd_0.innerHTML="&nbsp";
					objTr_0.appendChild(objTd_0);
					
					
					//1번째 줄 시작
					var objTr_1 = document.createElement("TR");
					objRow.appendChild(objTr_1);			
					
					var objCel_1 = document.createElement('TD');
					objCel_1.setAttribute("width", "72"); // 넓이
					objCel_1.innerHTML="";
			   		objTr_1.appendChild(objCel_1);
			   		
			   		var objCel_2 = document.createElement('TD');
			   		objCel_2.setAttribute("width", "127"); // 넓이
			   		objCel_2.innerHTML="부서";
			   		objTr_1.appendChild(objCel_2);			   		
			   
			   		var objCel_3 = document.createElement('TD');
			   		objCel_3.setAttribute("width", "13"); // 넓이
			   		objCel_3.innerHTML="";
			   		objTr_1.appendChild(objCel_3);
			   		
			   		var objCel_4 = document.createElement('TD');
			   		objCel_4.setAttribute("width", "127"); // 넓이
			   		objCel_4.innerHTML="직급";
			   		objTr_1.appendChild(objCel_4);
			   		
			   		var objCel_5 = document.createElement('TD');
			   		objCel_5.setAttribute("width", "1"); // 넓이
			   		objCel_5.innerHTML="";
			   		objTr_1.appendChild(objCel_5);
			   		
			   		var objCel_6 = document.createElement('TD');
			   		objCel_6.setAttribute("width", "127"); // 넓이
			   		objCel_6.innerHTML="이름";
			   		objTr_1.appendChild(objCel_6);
			   		
			   		var objCel_7 = document.createElement('TD');
			   		objCel_7.setAttribute("width", "1"); // 넓이
			   		objCel_7.innerHTML="";
			   		objTr_1.appendChild(objCel_7);
			   		
			   		var objCel_8 = document.createElement('TD');
			   		objCel_8.setAttribute("width", "127"); // 넓이
			   		objCel_8.innerHTML="사원번호";
			   		objTr_1.appendChild(objCel_8);
			   		
			   		var objCel_9 = document.createElement('TD');
			   		objCel_9.setAttribute("width", "1"); // 넓이
			   		objCel_9.innerHTML="";
			   		objTr_1.appendChild(objCel_9);
			   		
			   	 	
			   		//2번째 줄 시작					
					var objTr_2 = document.createElement("TR");
					objRow.appendChild(objTr_2);
					
					var objCel_20 = document.createElement('TD');
					objCel_20.setAttribute("width", "72"); // 넓이
					objCel_20.innerHTML="";
					objTr_2.appendChild(objCel_20);
					
					//부서 정보 입력					
					var objInput_1 = document.createElement('input');
					objInput_1.type = "text";
					objInput_1.name = "busi_dept_nm";
					objInput_1.id = "busi_dept_nm";
					objInput_1.value = memberListValueArr[2];
				   	objTr_2.appendChild(objInput_1);
				   	
				   	//3번 빗줄 처리
				   	var objCel_21 = document.createElement('TD');
				   	objCel_21.setAttribute("width", "13"); // 넓이
				   	objCel_21.innerHTML="/";
				   	objTr_2.appendChild(objCel_21);
				   	
					//직급 정보 입력					
				   	var objInput_2 = document.createElement('input');
				   	objInput_2.type = "text";
				   	objInput_2.name = "busi_grade_nm";
				   	objInput_2.id = "busi_grade_nm";
				   	objInput_2.value = memberListValueArr[3];
				   	objTr_2.appendChild(objInput_2);
				   	
				  	//이름 정보 입력	
				  	var objCel_22 = document.createElement('TD');
				  	objCel_22.setAttribute("width", "1"); // 넓이
				  	objCel_22.innerHTML="";
					objTr_2.appendChild(objCel_22);
				   	var objInput_3 = document.createElement('input');
				   	objInput_3.type = "text";
				   	objInput_3.name = "busi_emp_nm";
				   	objInput_3.id = "busi_emp_nm";
				   	objInput_3.value = memberListValueArr[1];
				   	objTr_2.appendChild(objInput_3);			   	
				   		    
				  	//사원번호
				  	var objCel_23 = document.createElement('TD');
				  	objCel_23.setAttribute("width", "1"); // 넓이
				  	objCel_23.innerHTML="";
					objTr_2.appendChild(objCel_23);
				  	var objCel_24 = document.createElement('TD');
				  	objCel_24.setAttribute("width", "127"); // 넓이				  	
				  	objCel_24.innerHTML=memberListValueArr[0];
					objTr_2.appendChild(objCel_24);
					/*var objCel_23 = document.createElement('TD');
					objCel_23.setAttribute("width", "1"); // 넓이
					objCel_23.innerHTML="";
					objTr_2.appendChild(objCel_23);*/
					var objinput_4 = document.createElement('input');
					objinput_4.type = "hidden";
					//objinput_4.type = "text";
					objinput_4.name = "busi_emp_no";
					objinput_4.id = "busi_emp_no";
					objinput_4.value = memberListValueArr[0];					
					objTr_2.appendChild(objinput_4);
				   						
					//3번째 줄 시작					
					var objTr_3 = document.createElement("TR");
					objRow.appendChild(objTr_3);
					var objTd_30 = document.createElement('TD');
					objTd_30.setAttribute("width", "600"); // 넓이
					objTd_30.innerHTML="&nbsp";
					objTr_3.appendChild(objTd_30);
					
					//4번째 줄 시작					
					var objTr_4 = document.createElement("TR");
					objRow.appendChild(objTr_4);
					var objTd_40 = document.createElement('TD');
					objTd_40.setAttribute("width", "600"); // 넓이
					objTd_40.innerHTML="&nbsp";
					objTr_4.appendChild(objTd_40);
					
					//applibusiVO.setBusi_emp_nm() = //잘라서 붙임
					/*var memberListValueArr = objParam.split("|");
					var objHidden = document.createElement('input');
					
					//신청 사번 넣은
					alert("addMemberRow v_busi_emp_no" + v_busi_emp_no);
					var v_busi_emp_no = memberListValueArr[0];
					//applibusiVO.setBusi_emp_no(v_busi_emp_no);
					//("#busi_dept_nm").val() = v_busi_emp_no;
					
					//신청 이름 넣은
					alert("addMemberRow v_busi_emp_nm " + v_busi_emp_nm);
					//applibusiVO.setBusi_emp_nm(v_busi_emp_nm);
					//$("#busi_emp_nm").val() = v_busi_emp_no;
					//$("#busi_emp_nm").push = v_busi_emp_no;
					//busi_emp_nm.push = v_busi_emp_nm;
					v_busi_emp_nm = memberListValueArr[1];
					
					//신청 부서 넣은
					alert("addMemberRow v_busi_dept_nm " + v_busi_dept_nm);
					//applibusiVO.setBusi_dept_nm(v_busi_dept_nm);
					var v_busi_dept_nm = memberListValueArr[2];
					
					//신청 직급 넣은
					alert("addMemberRow v_busi_grade_nm " + v_busi_grade_nm);
					//applibusiVO.setBusi_grade_nm(v_busi_grade_nm);   	
					var v_busi_grade_nm = memberListValueArr[3];	*/				
					
					$('#getMember').bPopup().close();
					tableScroll();
		    	}	    	
				else{
					alert("신청대상자는 한명 만 선택 바랍니다.");
				}
			}
		}
	}
	/**
	 * 레이어 팝업 닫기
	 */
	function layerClose(){ 
		$('#getMember').bPopup().close();
	}
	</script>
</head>
<body>
	<%@include file="/include/header.jsp"%>
	<%@include file="/include/gnb.jsp"%>
	
	<div class="wrap_con">
		<div class="content">
			<%@include file="/include/location.jsp"%>
			<%@include file="/include/lnb.jsp"%>
			<form id="frm" name="frm" method="post">
				<div class="cont float_left">		
				<h2>명함 신청</h2>	
				<h4>&nbsp</h4>						
				<h4>한글 명함  (주소2 전화2 팩스2/주소3 전화3 팩스3은 필요한 경우 적으시면 됩니다. ex] 본사,공장,연구소 표기를 원하는 경우)</h4>					
				<h4>&nbsp</h4>
				<h4>"신청대상자" 버튼을 이용하여 한명을 선택하시면, 사원번호 정보가 등록 됩니다.</h4>					
				<h4>&nbsp</h4>
				<button class="type_01" type="button" onclick="javascript:getMember();">신청대상자</button>	
				<button class="type_01" type="button" onclick="javascript:insertAppliBusi();">신청 접수</button>
				<h4>&nbsp</h4>	
				<div class="applibusi_box">	
					<table cellspacing=0 cellpadding=0 width="77%" style="border:solid 1px #EAEAEA;" align="center">
  					<tr><td> 											
						<table>
							<colgroup>
								<col style="width:2%;">
								<col style="width:65%;">
								<col style="width:31%;">
								<col style="width:2%;">																	
							</colgroup>
							<tr>
								<td></td>
								<td><img src="<%=GROUP_ROOT%>/asset/img/applibusi.gif"></td>
								<td></td>
								<td></td>														
							</tr>							
						</table>
						<table id = 'tbl'>
							<colgroup>								
								<col style="width:12%;">
								<col style="width:21%;">
								<col style="width:2%;">
								<col style="width:21%;">
								<col style="width:21%;">
								<col style="width:21%;">																	
							</colgroup>
							<tr>
								<td colspan = "7">&nbsp</td>	
							</tr>		
							<tr>								
								<td></td>
								<td>부서 </td>
								<td></td>
								<td>직급  </td>
								<td>이름 </td>							
								<td>사원번호</td>							
							</tr>						
							<tr>								
								<td></td>							
								<td><input type="text" id="busi_dept_nm" name="busi_dept_nm" value=""/></td>
								<td>/</td>
								<td><input type="text" id="busi_grade_nm" name="busi_grade_nm" value=""/></td>
								<td><input type="text" id="busi_emp_nm" name="busi_emp_nm" value=""></td>
								<td><input type="hidden" id="busi_emp_no" name="busi_emp_no" value=""></td>
							</tr>						
							<tr>
								<td colspan = "7">&nbsp</td>	
							</tr>	
							<tr>
								<td colspan = "7">&nbsp</td>	
							</tr>														
						</table>
						<table>
							<colgroup>
								<col style="width:2%;">
								<col style="width:23%;">
								<col style="width:23%;">
								<col style="width:2%;">														
								<col style="width:23%;">
								<col style="width:2%;">	
								<col style="width:23%;">
								<col style="width:2%;">																
							</colgroup>
							<tr>
								<td></td>
								<td colspan = "2">주소1</td>
								<td></td>
								<td>전화1</td>
								<td></td>
								<td>팩스1</td>
								<td></td>
							</tr>								
							<tr>
								<td></td>	
								<td colspan = "2"><input class = "ipt_middle" type="text" id="addr1" name="addr1" value=""/></td>
								<td></td>							
								<td><input class = "ipt_small" type="text" id="tel1" name="tel1" value=""/></td>
								<td></td>
								<td><input class = "ipt_small" type="text" id="fax1" name="fax1" value=""/></td>
								<td></td>								
							</tr>
							<tr>
								<td></td>
								<td>휴대전화</td>
								<td></td>
								<td></td>
								<td colspan = "3">이메일</td>								
								<td></td>	
							</tr>	
							<tr>
								<td></td>							
								<td><input class = "ipt_small" type="text" id="mobile" name="mobile" value=""/></td>
								<td></td>
								<td></td>								
								<td colspan = "3"><input class = "ipt_small" type="text" id="email" name="email" value=""/></td>
								<td></td>											
							</tr>
							<tr>
								<td></td>
								<td colspan = "2">주소2</td>
								<td></td>
								<td>전화2</td>
								<td></td>
								<td>팩스2</td>
								<td></td>
							</tr>								
							<tr>
								<td></td>	
								<td colspan = "2"><input class = "ipt_middle" type="text" id="addr2" name="addr2" value=""/></td>	
								<td></td>						
								<td><input class = "ipt_small" type="text" id="tel2" name="tel2" value=""/></td>
								<td></td>
								<td><input class = "ipt_small" type="text" id="fax2" name="fax2" value=""/></td>
								<td></td>								
							</tr>
							<tr>
								<td></td>
								<td colspan = "2">주소3</td>
								<td></td>
								<td>전화3</td>
								<td></td>
								<td>팩스3</td>
								<td></td>
							</tr>	
							<tr>
								<td></td>	
								<td colspan = "2"><input class = "ipt_middle" type="text" id="addr3" name="addr3" value=""/></td>
								<td></td>							
								<td><input class = "ipt_small" type="text" id="tel3" name="tel3" value=""/></td>
								<td></td>
								<td><input class = "ipt_small" type="text" id="fax3" name="fax3" value=""/></td>
								<td></td>								
							</tr>																	
						</table>
					</td></tr> 
					</table>											
				</div>
				<ul><img src="<%=GROUP_ROOT%>/asset/img/popup_line_bg.gif"></ul>	
				
				<h4>&nbsp</h4>
				<h4>Name Card (Please write in English. International number +82-10-1234-5678.)</h4>	
				<h4>&nbsp</h4>			
				
				<div class="applibusi_box">	
					<table cellspacing=0 cellpadding=0 width="77%" style="border:solid 1px #EAEAEA;" align="center">
  					<tr><td> 											
						<table>
							<colgroup>
								<col style="width:2%;">
								<col style="width:65%;">
								<col style="width:31%;">
								<col style="width:2%;">																	
							</colgroup>
							<tr>
								<td></td>
								<td><img src="<%=GROUP_ROOT%>/asset/img/applibusi_en.gif"></td>
								<td></td>
								<td></td>														
							</tr>							
						</table>
						<table class="over">
							<colgroup>
								<col style="width:12%;">
								<col style="width:21%;">
								<col style="width:2%;">
								<col style="width:21%;">
								<col style="width:21%;">
								<col style="width:21%;">																		
							</colgroup>
							<tr>
								<td colspan = "6">&nbsp</td>	
							</tr>		
							<tr>
								<td></td>
								<td>Department </td>
								<td></td>
								<td>Grade  </td>
								<td>Name </td>							
								<td></td>							
							</tr>					
							<tr>
								<td></td>							
								<td><input class = "ipt_small" type="text" id="busi_dept_nm_en" name="busi_dept_nm_en" value=""/></td>
								<td>/</td>
								<td><input class = "ipt_small" type="text" id="busi_grade_nm_en" name="busi_grade_nm_en" value=""/></td>
								<td><input class = "ipt_small" type="text" id="busi_emp_nm_en" name="busi_emp_nm_en" value=""/></td>
								<td></td>														
							</tr>
							<tr>
								<td colspan = "6">&nbsp</td>	
							</tr>	
							<tr>
								<td colspan = "6">&nbsp</td>	
							</tr>														
						</table>
						<table>
							<colgroup>
								<col style="width:2%;">
								<col style="width:23%;">
								<col style="width:23%;">
								<col style="width:2%;">														
								<col style="width:23%;">
								<col style="width:2%;">	
								<col style="width:23%;">
								<col style="width:2%;">																
							</colgroup>
							<tr>
								<td></td>
								<td colspan = "2">Address1</td>
								<td></td>
								<td>Tel1</td>
								<td></td>
								<td>Fax1</td>
								<td></td>
							</tr>								
							<tr>
								<td></td>	
								<td colspan = "2"><input class = "ipt_middle" type="text" id="addr1_en" name="addr1_en" value=""/></td>
								<td></td>							
								<td><input class = "ipt_small" type="text" id="tel1_en" name="tel1_en" value=""/></td>
								<td></td>
								<td><input class = "ipt_small" type="text" id="fax1_en" name="fax1_en" value=""/></td>
								<td></td>								
							</tr>
							<tr>
								<td></td>
								<td>Mobile</td>
								<td></td>
								<td></td>
								<td colspan = "3">Email</td>								
								<td></td>	
							</tr>	
							<tr>
								<td></td>							
								<td><input class = "ipt_small" type="text" id="mobile_en" name="mobile_en" value=""/></td>
								<td></td>
								<td></td>								
								<td colspan = "3"><input class = "ipt_small" type="text" id="email_en" name="email_en" value=""/></td>
								<td></td>											
							</tr>
							<tr>
								<td></td>
								<td colspan = "2">Address2</td>
								<td></td>
								<td>Tel2</td>
								<td></td>
								<td>Fax2</td>
								<td></td>
							</tr>								
							<tr>
								<td></td>	
								<td colspan = "2"><input class = "ipt_middle" type="text" id="addr2_en" name="addr2_en" value=""/></td>	
								<td></td>						
								<td><input class = "ipt_small" type="text" id="tel2_en" name="tel2_en" value=""/></td>
								<td></td>
								<td><input class = "ipt_small" type="text" id="fax2_en" name="fax2_en" value=""/></td>
								<td></td>								
							</tr>
							<tr>
								<td></td>
								<td colspan = "2">Address3</td>
								<td></td>
								<td>Tel3</td>
								<td></td>
								<td>Fax3</td>
								<td></td>
							</tr>	
							<tr>
								<td></td>	
								<td colspan = "2"><input class = "ipt_middle" type="text" id="addr3_en" name="addr3_en" value=""/></td>
								<td></td>							
								<td><input class = "ipt_small" type="text" id="tel3_en" name="tel3_en" value=""/></td>
								<td></td>
								<td><input class = "ipt_small" type="text" id="fax3_en" name="fax3_en" value=""/></td>
								<td></td>								
							</tr>																	
						</table>
					</td></tr> 
					</table>											
				</div>
				<ul><img src="<%=GROUP_ROOT%>/asset/img/popup_line_bg.gif"></ul>						
			</div>
			</form>		
		</div>
	</div>
	<div id='pro' style='display:none; width:auto; height:auto; '>
		<img alt='loading' src='/hanagw/asset/img/ajax-loader.gif' />
	</div>
	<div id='getMember' style='display:none; width:auto; height:auto; '>
		<div class='member_content'></div> 
	</div>
	<%@include file="/include/footer.jsp"%>
</body>
</html>