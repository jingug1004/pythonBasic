<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%--
 * @파일명 : addApprovalImplDeptIframe.jsp
 * @메뉴명 : 시행부서 추가 - 시행부서 목록 iframe
 * @최초작성일 : 2015/01/28            
 * @author : CHOIILJI                 
 * @수정내역 : 
--%>
<%@ page import="java.util.List" %>
<%@ page import="com.hanaph.gw.co.common.utils.StringUtil" %>
<%@ page import="com.hanaph.gw.co.personnel.vo.DepartmentVO" %>
<%@ include file ="/common/path.jsp" %>
<%
	//하위조직
	List<DepartmentVO> deptTerminalList = (List<DepartmentVO>)request.getAttribute("deptTerminalList");
	String dept_ko_nm = (String)request.getAttribute("dept_ko_nm");
%>
<!DOCTYPE html>
<html lang="ko">
<head>
	<%@include file="/include/head.jsp"%>
	<script type="text/javascript">
	
	/**
	 *  부서명 검색
	 */
	function goDeptSearch(){
		if(formCheck.containsChars(document.getElementById("dept_ko_nm").value, "%")){
			 alert("특수문자를 사용할수 없습니다");
			 return;
		}
		document.getElementById("frm").submit();
	}
	
	//checkbox 선택
	function goImplDeptTarget(){
		
		var chkCnt = checkCount();
	    if(chkCnt > 0) {
	    	parent.approvalDeptTarget.addImplDeptMultiRow();
	 	} else {
	        alert("시행부서를 선택해주세요.");
	        return;
	    }
	}
	
	function checkExcute(obj) {
  	    if(obj.checked){ 
  	   		allCheck();
  	    }else{
  	   		allUnCheck();
  	    }
  	}
  	
	function allCheck() {
  		var objTable = document.getElementById("tbl");
  	 	var lastRow = objTable.rows.length+1;
  	 	for(var i = lastRow; i > 1; i--) {
  	  		document.getElementsByTagName("TR")[i].cells[0].firstChild.checked = true;
  	 	}
  	}
	
	function allUnCheck() {
  	 	var objTable = document.getElementById("tbl");
  	 	var lastRow = objTable.rows.length+1;
  	 	for(var i = lastRow; i > 1; i--) {
  	  		document.getElementsByTagName("TR")[i].cells[0].firstChild.checked = false;
  	 	}
  	}
  	
  	function checkCount() {
	 	var objTable = document.getElementById("tbl");
	 	var lastRow = objTable.rows.length+1;
	 	var rtnCnt = 0;

	 	for(var i = lastRow; i > 1; i--){
	   		if(document.getElementsByTagName("TR")[i].cells[0].firstChild.checked) rtnCnt++;
	 	}
	 	return rtnCnt;
	}
	</script>
</head>
<body>
<!-- ######## start ####### -->
	
<!--  popup start -->
		
	<form name="frm" id="frm" method="post" action="<%=GROUP_ROOT %>/ea/newReport/addApprovalImplDeptIframe.do"> 
	<div class="search_box02 w488">
		<span>부서명</span>
		<input id="dept_ko_nm" name="dept_ko_nm" type="text" value="<%=dept_ko_nm%>" class="w210"/>
		<button type="button" onclick="javascript:goDeptSearch(); return false;">검색</button>
	</div>
	</form>
	<div class="settle_table02 w314">
		<table>
			<colgroup>
				<col style="width:28px" />
				<col style="" />
			</colgroup>
			<thead>
				<tr>
					<th></th>
					<th class="br_none">부서</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td colspan="2" class="inner">
						<div>
							<table id="tbl">
								<colgroup>
									<col style="width:28px"/>
									<col />
								</colgroup>
								<tbody>
								<%
									if(deptTerminalList.size()!=0){
										for(int i=0; deptTerminalList.size()>i;i++){
											DepartmentVO departmentVO = new DepartmentVO();
											departmentVO = deptTerminalList.get(i);
									%>
									<tr>
										<td><input type="checkbox" id="dept_cd" name="dept_cd" value="<%=departmentVO.getDept_cd() %>|<%=departmentVO.getDept_ko_nm() %>" ></td>
										<td class="br_none"><%=StringUtil.nvl(departmentVO.getDept_ko_nm()) %></td>
									</tr>
								<%
									}
								}
								%>
								</tbody>
							</table>
						</div>
					</td>
				</tr>	
			</tbody>
		</table>
	</div>
	<div class="list_btn">
		<div class="list_button mt5 w314">
		<button type="button" class="type_01 btn_exe2" onclick="javascript:goImplDeptTarget(); return false;">시행부서 추가</button>
		</div>
	</div>
</body>
</html>	
				