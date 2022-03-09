<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%--
 * @파일명 : memberSelectMemberTargetIframe.jsp
 * @메뉴명 : 임직원 대상 iframe
 * @최초작성일 : 2015/01/28            
 * @author : CHOIILJI                 
 * @수정내역 : 
--%>
<%@ page import="java.util.List" %>
<%@ page import="com.hanaph.gw.co.common.utils.StringUtil" %>
<%@ page import="com.hanaph.gw.pe.member.vo.MemberVO" %>
<%@ include file ="/common/path.jsp" %>
<%
	List<MemberVO> memberTargetList = (List<MemberVO>)request.getAttribute("memberTargetList");
	String type = StringUtil.nvl((String)request.getAttribute("type"));
	
	MemberVO memberSessionVO = new MemberVO();
	memberSessionVO = (MemberVO) session.getAttribute("gwUser");
%>
<!DOCTYPE html>
<html lang="ko">
<head>
	<%@include file="/include/head.jsp"%>
	<script language='javascript'>

	/**
	 * 부모창에 저장
	 */
	function goTop(){
	    var chkCnt = checkCount();
	    if(chkCnt > 0) {
	    	parent.goMemberTarget();
	 	} else {
	 		document.getElementById("emp_no").value="";
	        alert("임직원을 선택해주세요.");
	        return;
	    }
	}
			
	/**
	 * row 삭제
	 */
	function delRow() {
	 	var objTable = document.getElementById("tbl");
	 	var lastRow = objTable.rows.length+1;
	 	var memberListCheckBoxParam = "";
	 	var memberListCheckValueArr = "";
	
	 	var chkCnt = checkCount();
	 	if(chkCnt > 0) {
	 		if( confirm("삭제하시겠습니까?") ){
	   			for(var i = lastRow; i > 1; i--) {
	   				memberListCheckBoxParam = document.getElementsByTagName("TR")[i].cells[0].firstChild.value;
	   				memberListCheckValueArr = memberListCheckBoxParam.split("|");
	   				if(<%=memberSessionVO.getEmp_no()%> == memberListCheckValueArr[0] && !( "<%=type%>"=="SHARE" || "<%=type%>"=="MESSAGE" || "<%=type%>"=="MESSAGE_DELIVER")){
	   				}else{
	   					if(document.getElementsByTagName("TR")[i].cells[0].firstChild.checked) objTable.deleteRow(i-2);
	   				}
	  			}
	  		}
	 		return false;
	 	} else {
	        alert("삭제할 임직원을 선택해주세요.");
	        return;
	    }
	}
	
	/**
	 * 체크된 row count
	 */
	function checkCount() {
	 	var objTable = document.getElementById("tbl");
	 	var lastRow = objTable.rows.length+1;
	 	var rtnCnt = 0;

	 	for(var i = lastRow; i > 1; i--){
	   		if(document.getElementsByTagName("TR")[i].cells[0].firstChild.checked) rtnCnt++;
	 	}
	 	return rtnCnt;
	}
  	/**
	 * 체크박스 전체 선택, 해제
	 */
	function checkExcute(obj) {
	    if(obj.checked){ 
	   		allCheck();
	    }else{
	   		allUnCheck();
	    }
	}
	
	/**
	 * 체크박스 전체 선택
	 */
	function allCheck() {
  		var objTable = document.getElementById("tbl");
  	 	var lastRow = objTable.rows.length+1;

  	 	for(var i = lastRow; i > 1; i--) {
  	 		if(document.getElementsByTagName("TR")[i].cells[0].firstChild.disabled == false){
  	  			document.getElementsByTagName("TR")[i].cells[0].firstChild.checked = true;
  	 		}	
  	 	}
  	}
	
	/**
	 * 체크박스 전체 해제
	 */
	function allUnCheck() {
  	 	var objTable = document.getElementById("tbl");
  	 	var lastRow = objTable.rows.length+1;
  	 	for(var i = lastRow; i > 1; i--) {
  	  		document.getElementsByTagName("TR")[i].cells[0].firstChild.checked = false;
  	 	}
  	}
  	
	/**
	 * 임직원 추가
	 */
	function addMultiRow() {
		var sameCnt = 0;

		//대상자 frame
		var checkBoxArr = document.getElementsByName("emp_no"); //파일 체크박스
	    var checkBoxCnt = document.getElementsByName("emp_no").length;
	
		//임직원목록 frame 
	    var memberListCheckBoxArr = parent.memberList.document.getElementsByName("member_emp_no"); //파일 체크박스
	    var memberListCheckBoxCnt = parent.memberList.document.getElementsByName("member_emp_no").length;
	    
	    var checkBoxParam = "";
	    var memberListCheckBoxParam = "";
	    
	    for(var i=0; i<memberListCheckBoxCnt; i++){
	    	if(memberListCheckBoxArr[i].checked){
		    	memberListCheckBoxParam = memberListCheckBoxArr[i].value;
				for(var j=0; j<checkBoxCnt; j++){
					checkBoxParam = checkBoxArr[j].value;
					if(memberListCheckBoxParam == checkBoxParam){
						if(sameCnt == 0){
							alert("동일한 직원이 존재합니다.");
							sameCnt++;
						}
						memberListCheckBoxArr[i].checked = false;
					}
				}
		    }
	    } 
	    
	    for(i=0; i<memberListCheckBoxCnt; i++){
	    	if(memberListCheckBoxArr[i].checked){
		    	memberListCheckBoxParam = memberListCheckBoxArr[i].value;
				var memberListCheckValueArr = memberListCheckBoxParam.split("|");
				var objRow = document.getElementById('tbl').insertRow(); // row 생성
				var objCel1 = document.createElement('TD');
			   	objRow.appendChild(objCel1);
			   	objCel1.innerHTML="<input type='checkbox' name='emp_no' id='emp_no' value='"+memberListCheckBoxParam+"' onclick='javascript:allChkStatus(this);' checked>";
			   	
			   	objCel1 = document.createElement('TD');
			   	objRow.appendChild(objCel1);
			   	objCel1.innerHTML=memberListCheckValueArr[2];
			   	
			   	objCel1 = document.createElement('TD');
			   	objRow.appendChild(objCel1);
			   	objCel1.innerHTML=memberListCheckValueArr[3];
			   	
			   	objCel1 = document.createElement('TD');
			   	objCel1.setAttribute("class", "br_none");
			   	objRow.appendChild(objCel1);
			   	objCel1.innerHTML=memberListCheckValueArr[1];
			}
	    } 
	    parent.memberList.allUnCheck();
	    parent.memberList.document.getElementById("all_chk").checked = false;
	}

	/**
	 * 체트박스 선택
	 */
	function allChkStatus(obj){
		if(!obj.checked){
			if(document.getElementById("all_chk").checked){ 
				document.getElementById("all_chk").checked = false;
		    }
		}
	}
	</script>	
</head>
<body>
	<div class="authority_staff_ifr">
		<div class="wrap_group">
			<h4>대상자</h4>
			<div class="wrap_list">
				<table>
					<colgroup>
						<col style="width:30px;" />
						<col style="width:105px" />
						<col style="width:85px" />
						<col />
					</colgroup>
					<thead>
						<tr>
							<th><input type="checkbox" name="all_chk" id="all_chk" onClick="checkExcute(this)"></th>
							<th>부서</th>
							<th>직급</th>
							<th class="br_none">이름</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td colspan="4" class="inner">
								<div>
									<table id="tbl">
									
										<%if(type.equals("MESSAGE_DELIVER")){ 
											//메세지 전달일 경우만 기존 임직원 데이터 상위에 임직원 추가 한다. 
										%>
										<colgroup>
											<col style="width:30px"/>
											<col style="width:105px" />
											<col style="width:85px" />
											<col />
										</colgroup>
										<tbody>
										</tbody>
									</table>
									
									<table id="tbl1">
										<%} %>
										<colgroup>
											<col style="width:30px"/>
											<col style="width:105px" />
											<col style="width:85px" />
											<col />
										</colgroup>
										<tbody>
										<%
										if(memberTargetList != null && memberTargetList.size() != 0){
											for(int i=0; memberTargetList.size()>i;i++){
												MemberVO memberVO = new MemberVO();
												memberVO = memberTargetList.get(i);
										%>
										<tr>
											<td><input type='checkbox' id='emp_no' name='emp_no' value='<%=memberVO.getEmp_no() %>|<%=StringUtil.nvl(memberVO.getEmp_ko_nm()) %>|<%=StringUtil.nvl(memberVO.getDept_ko_nm()) %>|<%=StringUtil.nvl(memberVO.getGrad_ko_nm()) %>' onclick='javascript:allChkStatus(this);' <%if(type.equals("SHARE") || type.equals("MESSAGE_DELIVER")){ %>disabled<%}else{ %>checked<%} %>></td>
											<td><%=StringUtil.nvl(memberVO.getDept_ko_nm()) %></td>
											<td><%=StringUtil.nvl(memberVO.getGrad_ko_nm()) %></td>
											<td class="br_none"><%=StringUtil.nvl(memberVO.getEmp_ko_nm()) %></td>
										</tr>
										<%
											}
										}else{
										%>
											<%if(!(type.equals("AUTH") || type.equals("SHARE") || type.equals("MESSAGE") || type.equals("MESSAGE_DELIVER")|| type.equals("BUSI"))){ %>
												<tr>
													<td><input type='checkbox' id='emp_no' name='emp_no' value='<%=memberSessionVO.getEmp_no() %>|<%=StringUtil.nvl(memberSessionVO.getEmp_ko_nm()) %>|<%=StringUtil.nvl(memberSessionVO.getDept_ko_nm()) %>|<%=StringUtil.nvl(memberSessionVO.getGrad_ko_nm()) %>' onclick='javascript:allChkStatus(this);' checked></td>
													<td><%=memberSessionVO.getDept_ko_nm() %></td>
													<td><%=memberSessionVO.getGrad_ko_nm() %></td>
													<td class="br_none"><%=memberSessionVO.getEmp_ko_nm() %></td>
												</tr>
											<%} %>
										<%	
										} %>
										</tbody>
									</table>
								</div>
							</td>
						</tr>										
					</tbody>
				</table>
			</div>
			<div class="wrap_btn">
				<button class="type_01" type="button" onClick="javascript:goTop(); return false;">선택완료</button>
				<button class="type_01" type="button" onClick="javascript:delRow(); return false;">선택삭제</button>
			</div>			
		</div>
	</div>
</body>
</html>