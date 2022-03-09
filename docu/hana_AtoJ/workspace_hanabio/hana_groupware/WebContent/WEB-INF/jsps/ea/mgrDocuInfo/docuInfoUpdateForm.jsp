<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%--
 * @파일명 : docuInfoUpdateForm.jsp
 * @메뉴명 : 관리자 양식정보관리 수정폼
 * @최초작성일 : 2015/01/28            
 * @author : CHOIILJI                 
 * @수정내역 : 
--%>
<%@ page import="java.util.List" %>
<%@ page import="com.hanaph.gw.co.common.utils.StringUtil" %>
<%@ page import="com.hanaph.gw.ea.mgrDocuInfo.vo.DocumentInfoVO" %>
<%@ page import="com.hanaph.gw.co.personnel.vo.DepartmentVO" %>
<%@ include file ="/common/path.jsp" %>
<%
	DocumentInfoVO documentInfoDetail = (DocumentInfoVO)request.getAttribute("documentInfoDetail");
	String search_dept_cd = StringUtil.nvl((String)request.getAttribute("search_dept_cd"));
	String search_docu_nm = StringUtil.nvl((String)request.getAttribute("search_docu_nm"));
%>
<!DOCTYPE html>
<html lang="ko">
<head>
	<%@include file="/include/head.jsp"%>
	<script type="text/javascript">
		function goList(){
			document.getElementById("frm").action="<%=GROUP_ROOT %>/ea/mgrDocuInfo/docuInfoList.do";
			document.getElementById("frm").submit();
		}
	
		function goUpdate(){
			if(confirm("수정하시겠습니까?") == true){
				document.getElementById("frm").action="<%=GROUP_ROOT %>/ea/mgrDocuInfo/docuInfoUpdate.do";
				document.getElementById("frm").submit();
			}	
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
				<div class="wrap_report_style mt20">
					<div class="list_button">
						<button type="button" class="type_01" onclick="javascript:goUpdate(); return false;">저장</button>
							<button type="button" class="type_01" onclick="javascript:goList(); return false;">목록</button>
					</div>
					
					<div class="wrap_list bdt_2 mt7">
						<div class="">
						<form name="frm" id="frm" method="post">
						<input type="hidden" id="docu_seq" name="docu_seq" value="<%=documentInfoDetail.getDocu_seq()%>">
						<input type="hidden" id="search_dept_cd" name="search_dept_cd" value="<%=search_dept_cd%>">
						<input type="hidden" id="search_docu_nm" name="search_docu_nm" value="<%=search_docu_nm%>">
							<table class="tbl_report_style">
								<colgroup>
									<col style="width:160px;" />
									<col style="" />
								</colgroup>
								<tbody>
									<tr>
										<th>양식명</th>
										<td><%=StringUtil.nvl(documentInfoDetail.getDocu_nm()) %></td>
									</tr>
									<tr>
										<th>부서</th>
										<td><%=StringUtil.nvl(documentInfoDetail.getDept_ko_nm()) %></td>
									</tr>
									<tr>
										<th>등록일</th>
										<td><%=StringUtil.nvl(documentInfoDetail.getCreate_dt()) %></td>
									</tr>
									<tr>
										<th>사용여부</th>
										<td>
											<input type="radio" id="use_yn1" name="use_yn" value="Y" <%if(documentInfoDetail.getUse_yn().equals("Y")){ %>checked<%} %>/>사용
											<input type="radio" id="use_yn2" name="use_yn" value="N" <%if(documentInfoDetail.getUse_yn().equals("N")){ %>checked<%} %>/>미사용
										</td>
									</tr>
									<tr>
										<th>옵션</th>
										<td>
											<input type="checkbox" id="decide_yn" name="decide_yn" value="Y" <%if(documentInfoDetail.getDecide_yn().equals("Y")){ %>checked<%} %>/> 전결
											<input type="checkbox" id="security_yn" name="security_yn" value="Y" <%if(documentInfoDetail.getSecurity_yn().equals("Y")){ %>checked<%} %>/>대외비
										</td>
									</tr>
									<tr>
										<th>설명</th>
										<td class="explain">
											<textarea id="contents" name="contents" ><%=StringUtil.nvl(documentInfoDetail.getContents()) %></textarea>
										</td>
									</tr>
								</tbody>
							</table>
						</form>	
						</div>
					</div>
				</div>
			</div>
			<!-- ######## end ######### -->
			
		</div>
	</div>
	
	<%@include file="/include/footer.jsp"%>
</body>
</html>