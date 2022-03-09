<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%--
 * @파일명 : docuInfoList.jsp
 * @메뉴명 : 관리자 양식정보관리
 * @최초작성일 : 2015/01/28            
 * @author : CHOIILJI                 
 * @수정내역 : 
--%>
<%@ page import="java.util.List" %>
<%@ page import="com.hanaph.gw.co.common.utils.StringUtil" %>
<%@ page import="com.hanaph.gw.co.common.utils.RequestFilterUtil" %>
<%@ page import="com.hanaph.gw.ea.mgrDocuInfo.vo.DocumentInfoVO" %>
<%@ page import="com.hanaph.gw.co.personnel.vo.DepartmentVO" %>
<%@ include file ="/common/path.jsp" %>
<%
	List<DocumentInfoVO> documentInfoList = (List<DocumentInfoVO>)request.getAttribute("documentInfoList");
	List<DepartmentVO> deptList = (List<DepartmentVO>)request.getAttribute("deptList");
	String dept_cd = StringUtil.nvl((String)request.getAttribute("dept_cd"));
	String search_dept_cd = StringUtil.nvl((String)request.getAttribute("search_dept_cd"));
	String search_docu_nm = RequestFilterUtil.toHtmlTagReplace("", (String)request.getAttribute("search_docu_nm"));
	int documentInfoTotalCount = (Integer)request.getAttribute("documentInfoTotalCount");
%>
<!DOCTYPE html>
<html lang="ko">
<head>
	<%@include file="/include/head.jsp"%>
	<script type="text/javascript">
	function goDocuInfo(docu_seq){
		document.getElementById("docu_seq").value = docu_seq;
		document.getElementById("frm").action ="<%=GROUP_ROOT%>/ea/mgrDocuInfo/docuInfoUpdateForm.do";
		document.getElementById("frm").submit();
	}
	
	function goSearch(){
		if(formCheck.containsChars(document.getElementById("search_docu_nm").value, "%")){
			 alert("특수문자를 사용할수 없습니다");
			 return;
		}
		
		document.getElementById("search_frm").action ="<%=GROUP_ROOT%>/ea/mgrDocuInfo/docuInfoList.do";
		document.getElementById("search_frm").submit();
	}
	
	function goApproval(docu_seq){
		document.getElementById("docu_seq").value = docu_seq;
		document.getElementById("frm").action ="<%=GROUP_ROOT%>/ea/mgrDocuInfo/basicApproval.do";
		document.getElementById("frm").submit();
	}
	
	function layerClose(){ 
		$('#pop_approval').bPopup().close();
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
			
			<!-- ######## start ####### -->
			<div class="cont float_left">
				<h2>양식정보관리</h2>
				<div class="wrap_report_style">
					<div class="wrap_search">
					<form name="search_frm" id="search_frm" method="post">
						<div class="search">
							<ul class="serch_con01">
								<li>
									<span class="sc_txt">부서</span>
									<select id="search_dept_cd" name="search_dept_cd" onchange="javscript:goSearch();">
										<option value="0000">선택하세요</option>
								<%
									if(deptList.size()!=0){
										for(int i=0; deptList.size()>i;i++){
											DepartmentVO departmentVO = new DepartmentVO();
											departmentVO = deptList.get(i);
								%>
									<option value="<%=departmentVO.getDept_cd()%>" <%if(departmentVO.getDept_cd().equals(search_dept_cd)){ %>selected<%} %>><%=departmentVO.getDept_ko_nm() %></option>
								<%
										}
									}
								%>	
									</select>
								</li>
								<li>
									<span class="sc_txt">양식명</span>
									<input type="text" id="search_docu_nm" name="search_docu_nm" value="<%=search_docu_nm%>" onKeyPress="if(event.keyCode=='13'){goSearch(); return false;}"/>
								</li>
							</ul>
						</div>
						<div class="wrap_btn">
							<button type="button" class="btn_search" onclick="javascript:goSearch(); return false;">검색</button>
						</div>
					</form>	
					</div>
					<div class="wrap_list">
						<div class="tableA">
						<form id="frm" name="frm" method="post">
						<input type="hidden" name="docu_seq" id="docu_seq" >
						<input type="hidden" id="search_dept_cd" name="search_dept_cd" value="<%=search_dept_cd %>"/>
						<input type="hidden" id="search_docu_nm" name="search_docu_nm" value="<%=search_docu_nm %>"/>
						
							<table>
								<colgroup>
									<col style="width:10%;" />
									<col style="width:30%;" />
									<col style="width:30%;" />
									<col style="width:15%;" />
									<col style="width:15%;" />
								</colgroup>
								<thead>
									<tr class="b_top">
										<th>NO.</th>
										<th>양식명</th>
										<th>부서</th>
										<th>정보관리</th>
										<th>결재라인관리</th>
									</tr>
								</thead>
								<tbody>
								<%
									if(documentInfoList.size()!=0){
										for(int i=0; documentInfoList.size()>i;i++){
											DocumentInfoVO documentInfoVO = new DocumentInfoVO();
											documentInfoVO = documentInfoList.get(i);
								%>
									<tr>
										<td><%=documentInfoVO.getRnum() %></td>
										<td><%=documentInfoVO.getDocu_nm() %></td>
										<td><%=documentInfoVO.getDept_ko_nm() %></td>
										<td><button type="button" class="type_01" onclick="javascript:goDocuInfo('<%=documentInfoVO.getDocu_seq() %>');">정보수정</button></td>
										<td><button type="button" class="type_01" onclick="javascript:goApproval('<%=documentInfoVO.getDocu_seq() %>');">결재라인수정</button></td>
									</tr>
								<%
										}
									}else{
								%>	
									<tr>
										<td colspan="5">데이터가 없습니다.</td>
									</tr>
								<%
									}
								%>	
								</tbody>
							</table>
							</form>
						</div>
						
						<div class="paging area_con">
							<div class="wrap_total">
								* 총 건수 : <span class="cnt"><%=StringUtil.makeMoneyType(documentInfoTotalCount)%></span>건
							</div>
						</div>
					</div>
				</div>
			</div>
			<!-- ######## end ######### -->
			
		</div>
	</div>
	<div id='pop_approval' style='display:none; width:auto; height:auto; '>
		<div class='approval_content'></div> 
	</div>
	<%@include file="/include/footer.jsp"%>
</body>
</html>