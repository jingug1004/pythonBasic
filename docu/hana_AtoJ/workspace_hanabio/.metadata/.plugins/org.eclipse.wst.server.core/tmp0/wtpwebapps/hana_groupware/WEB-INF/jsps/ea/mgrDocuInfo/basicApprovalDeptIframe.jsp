<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%--
 * @파일명 : basicApprovalDeptIframe.jsp
 * @메뉴명 : 기본결재라인 부서 목록 iframe
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
%>
<!DOCTYPE html>
<html lang="ko">
<head>
	<%@include file="/include/head.jsp"%>	
	<script type="text/javascript">
		//직원 checkbox 선택
		function goImplDeptTarget() {
		 	   
		    var chkCnt = checkCount();
		    if(chkCnt > 0) {
		    	parent.approvalDeptTarget.addImplDeptMultiRow();
		 	} else {
		        alert("시행 부서를 선택해주세요.");
		        return;
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
	<div class="settle_table02">
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
		<div class="list_button mt5 w265">
			<button type="button" class="type_01 btn_exe" onclick="javascript:goImplDeptTarget(); return false;">시행부서 추가</button>
		</div>
	</div>
	
</body>
</html>