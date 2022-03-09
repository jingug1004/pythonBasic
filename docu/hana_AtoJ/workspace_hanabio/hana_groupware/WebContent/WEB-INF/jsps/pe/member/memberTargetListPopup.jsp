<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%--
 * @파일명 : memberTargetListPopup.jsp
 * @메뉴명 : 대상 임직원 리스트
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
			<h3 class="pop_tit">대상자</h3>
			<div class="cont_box03">
				<table>
					<colgroup>
						<col width="38%" />
						<col width="30%" />
						<col width="22%" />
					</colgroup>
					<tbody>
						<tr>
							<th>부서</th>
							<th>직급</th>
							<th class="br_none">이름</th>
						</tr>
						<tr>
						<%
						if(memberTargetList.size()!=0){
							for(int i=0; memberTargetList.size()>i;i++){
								MemberVO memberVO = new MemberVO();
								memberVO = memberTargetList.get(i);
						%>
						<tr>
							<td><%=StringUtil.nvl(memberVO.getDept_ko_nm()) %></td>
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
		</div>
	</div>
</body>
</html>
