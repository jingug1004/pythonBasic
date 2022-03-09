<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%--
 * @파일명 : implementCompletePopup.jsp
 * @메뉴명 : 시행완료대상 팝업
 * @최초작성일 : 2015/03/04            
 * @author : CHOIILJI   
 * @수정내역 : 
--%>
<%@ page import="java.util.List" %>
<%@ page import="com.hanaph.gw.co.common.utils.StringUtil" %>
<%@ page import="com.hanaph.gw.ea.newReport.vo.ImplDeptEmpVO" %>  
<%@ include file ="/common/path.jsp" %>
<%
	//공유대상
	List<ImplDeptEmpVO> implCompleteList = (List<ImplDeptEmpVO>)request.getAttribute("implCompleteList");
%>
<!DOCTYPE html>
<html lang="ko">
<head>
	<%@include file="/include/head.jsp"%>	
</head>
<body>
	<div class="noti_pop">
		<div class="pop_box">
			<p class="btn_del"><button onclick="window.parent.layerClose();"><img src="<%=GROUP_WEB_ROOT%>/img/popup_btn_close01.gif" alt="닫기" /></button></p>
			<h3 class="pop_tit">시행완료 대상자</h3>
			<div class="cont_box03" style="width: 500px;">
				<table>
					<colgroup>
						<col width="17%" />
						<col width="13%" />
						<col width="25%" />
						<col width="47" />
					</colgroup>
					<tbody>
						<tr>
							<th>부서명</th>
							<th>사용자</th>
							<th>완료시간</th>
							<th class="br_none">내용</th>
						</tr>
						<tr>
						<%
						if(implCompleteList.size()!=0){
							for(int i=0; implCompleteList.size()>i;i++){
								ImplDeptEmpVO implDeptEmpVO = new ImplDeptEmpVO();
								implDeptEmpVO = implCompleteList.get(i);
						%>
						<tr>
							<td><%=StringUtil.nvl(implDeptEmpVO.getDept_ko_nm()) %></td>
							<td><%=StringUtil.nvl(implDeptEmpVO.getEmp_ko_nm()) %></td>
							<td><%=StringUtil.nvl(implDeptEmpVO.getComplete_dt())%></td>
							<td class="br_none"><%=StringUtil.nvl(implDeptEmpVO.getComplete_note()) %></td>
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
		</div>
	</div>
</body>
</html>
