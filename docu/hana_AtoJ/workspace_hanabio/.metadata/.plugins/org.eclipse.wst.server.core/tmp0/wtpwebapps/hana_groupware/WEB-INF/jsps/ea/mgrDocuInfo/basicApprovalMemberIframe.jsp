<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%--
 * @파일명 : basicApprovalMemberIframe.jsp
 * @메뉴명 : 기본결재라인 회원정보 iframe
 * @최초작성일 : 2015/01/28            
 * @author : CHOIILJI                 
 * @수정내역 : 
--%>
<%@ page import="java.util.List" %>
<%@ page import="com.hanaph.gw.co.common.utils.StringUtil" %>
<%@ page import="com.hanaph.gw.co.common.utils.RequestFilterUtil" %>
<%@ page import="com.hanaph.gw.pe.member.vo.MemberVO" %>   
<%@ include file ="/common/path.jsp" %>
<%
	//소속사용자
	List<MemberVO> memberList = (List<MemberVO>)request.getAttribute("memberList");
	String emp_ko_nm = RequestFilterUtil.toHtmlTagReplace("", (String)request.getAttribute("emp_ko_nm"));
%>
<!DOCTYPE html>
<html lang="ko">
<head>
	<%@include file="/include/head.jsp"%>	
	<script type="text/javascript">

		function goSearch(){
			if(formCheck.isNull(document.getElementById("emp_ko_nm"), "성명을 입력하여 주십시오.")){
				return;
			}
			if(formCheck.containsChars(document.getElementById("emp_ko_nm").value, "%")){
				 alert("특수문자를 사용할수 없습니다");
				 return;
			}
			document.getElementById("frm").submit();
		}
		
		//직원 checkbox 선택
		function goApprovalTarget() {
		 	   
		    var chkCnt = checkCount();
		    if(chkCnt > 0) {
		    	parent.approvalMemberTarget.addApprovalMultiRow();
		 	} else {
		        alert("임직원을 선택해주세요.");
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
	<div class="search_box02">
		<form name="frm" id="frm" method="post" action="<%=GROUP_ROOT %>/ea/mgrDocuInfo/basicApprovalMemberIframe.do"> 
		<span>사원명</span>
		<input type="text" name="emp_ko_nm" id="emp_ko_nm" value="<%=emp_ko_nm%>" onKeyPress="if(event.keyCode=='13'){goSearch(); return false;}"/>
		<button type="button" onclick="javascript:goSearch(); return false;">검색</button>
		</form>
	</div>
	<div class="settle_table01">
		<table>
		<colgroup>
			<col style="width:28px" />
			<col style="width:85px" />
			<col style="width:60px" />
			<col style="width:90px" />
		</colgroup>
			<thead>
				<tr>
					<th></th>
					<th>부서</th>
					<th>직급</th>
					<th class="br_none">사원명</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td colspan="5" class="inner">
						<div>
							<table id="tbl">
								<colgroup>
									<col style="width:28px"/>
									<col style="width:85px"/>
									<col style="width:60px"/>
									<col style=""/>
								</colgroup>
								<tbody>
								<%
									if(memberList.size()!=0){
										for(int i=0; memberList.size()>i;i++){
										MemberVO memberVO = new MemberVO();
										memberVO = memberList.get(i);
									%>
									<tr>
										<td><input type="checkbox" id="emp_no" name="emp_no" value="<%=memberVO.getEmp_no() %>|<%=StringUtil.nvl(memberVO.getEmp_ko_nm()) %>" ></td>
										<td class="team"><%=StringUtil.nvl(memberVO.getDept_ko_nm()) %></td>
										<td><%=StringUtil.nvl(memberVO.getGrad_ko_nm()) %></td>
										<td class="br_none"><%=StringUtil.nvl(memberVO.getEmp_ko_nm()) %></td>
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
	<div class="list_btn w265">
		<div class="list_button mt5">
			<button type="button" class="type_01 btn_appr" onclick="javascript:goApprovalTarget(); return false;">결재자 추가</button>
		</div>
	</div>
</body>
</html>