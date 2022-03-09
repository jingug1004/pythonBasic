<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%--
 * @파일명 : memberSelectMemberIframe.jsp
 * @메뉴명 : 임직원 리스트 iframe
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
	List<MemberVO> memberList = (List<MemberVO>)request.getAttribute("memberList");
	String type = StringUtil.nvl((String)request.getAttribute("type"));
	String seq = StringUtil.nvl((String)request.getAttribute("seq"));
	String emp_ko_nm = RequestFilterUtil.toHtmlTagReplace("", (String)request.getAttribute("emp_ko_nm"));
	
%>
<!DOCTYPE html>
<html lang="ko">
<head>
	<%@include file="/include/head.jsp"%>	
	<script type="text/javascript">

	/**
	 * 검색
	 */
	function goSearch(){
		var emp_ko_nm = document.frm.emp_ko_nm.value;
		var getNm = "";
		if(formCheck.isNull(document.getElementById("emp_ko_nm"), "성명을 입력하여 주십시오.")){
			return;
		}
		if(formCheck.containsChars(document.getElementById("emp_ko_nm").value, "%")){
			 alert("특수문자를 사용할수 없습니다");
			 return;
		}
		if("AUTH" == "<%=type%>"){
			$.ajax({
				type: "POST",
				data: {emp_ko_nm:emp_ko_nm},
				url: "<%=GROUP_ROOT%>/co/authority/searchAuthMember.do",
				async : false,
				success: function(data){
					
					for(var i=0; i<data.emp_no_list.length;i++){
						getNm += data.emp_no_list[i].emp_ko_nm + "(" + data.emp_no_list[i].auth_nm + ")";
						if(data.emp_no_list.length-1 != i){
							getNm += ", ";
						}
					}
					
					if(data.emp_no_list.length>0){
						alert("맵핑된 직원 : " + getNm);
					}
				}
			});
		}
		document.getElementById("frm").submit();
	}
	
	/**
	 * 직원선택
	 */
	function goAllTarget() {
	 	   
	    var chkCnt = checkCount();
	    if(chkCnt > 0) {
	    	parent.memberTargetList.addMultiRow();
	 	} else {
	        alert("임직원을 선택해주세요.");
	        return;
	    }
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
  	  		document.getElementsByTagName("TR")[i].cells[0].firstChild.checked = true;
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
	</script>
</head>
<body>
	<div class="authority_staff_ifr">
		<div class="wrap_group">
			<h4>임직원 목록</h4>
			<div class="wrap_sch">
			<form name="frm" id="frm" method="post" onsubmit="return false;" action="<%=GROUP_ROOT %>/pe/member/memberSelectMemberIframe.do"> 
				<input type ="hidden" id="seq" name="seq" value="<%=seq%>">
				<input type ="hidden" id="type" name="type" value="<%=type%>">
				<label>사용자명</label>
				<input type="text" name="emp_ko_nm" id="emp_ko_nm" value="<%=emp_ko_nm%>" onKeyPress="if(event.keyCode=='13'){goSearch(); return false;}"/>
				<button class="search" type="button" onclick="javascript:goSearch(); return false;">검색</button>
			</form>	
			</div>
			<div class="wrap_staff">
				<table>
					<colgroup>
						<col style="width:30px;" />
						<col style="width:105px" />
						<col style="width:85px"/>
						<col />
					</colgroup>
					<thead>
						<tr>
							<th><input type="checkbox" id="all_chk" name="all_chk"  onClick="checkExcute(this)"></th>
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
										<colgroup>
											<col style="width:30px"/>
											<col style="width:105px" />
											<col style="width:85px" />
											<col />
										</colgroup>
										<tbody>
										<%
										if(memberList.size()!=0){
											for(int i=0; memberList.size()>i;i++){
											MemberVO memberVO = new MemberVO();
											memberVO = memberList.get(i);
										%>
												<tr>
													<td<%if(i == 0){ %> class="bdtn"<%} %>><input type="checkbox" id="member_emp_no" name="member_emp_no" value="<%=memberVO.getEmp_no() %>|<%=StringUtil.nvl(memberVO.getEmp_ko_nm()) %>|<%=StringUtil.nvl(memberVO.getDept_ko_nm()) %>|<%=StringUtil.nvl(memberVO.getGrad_ko_nm()) %>" ></td>
													<td<%if(i == 0){ %> class="bdtn"<%} %>><%=StringUtil.nvl(memberVO.getDept_ko_nm()) %></td>
													<td<%if(i == 0){ %> class="bdtn"<%} %>><%=StringUtil.nvl(memberVO.getGrad_ko_nm()) %></td>
													<td class="br_none<%if(i == 0){ %> bdtn<%} %>"><%=StringUtil.nvl(memberVO.getEmp_ko_nm()) %></td>
												</tr>
										<%
											}
										}else{
										%>
											<tr>
												<td colspan="4" class="br_none">데이터가 없습니다.</td>
											</tr>
										
										<%} %>
										</tbody>
									</table>
								</div>
							</td>	
						</tr>	
					</tbody>
				</table>
			</div>
			<div class="wrap_result">
				<span><%=memberList.size() %>명</span>
				<button class="type_01" type="button" onclick="javascript:goAllTarget(); return false;">선택적용</button>
			</div>
		</div>
	</div>
</body>
</html>