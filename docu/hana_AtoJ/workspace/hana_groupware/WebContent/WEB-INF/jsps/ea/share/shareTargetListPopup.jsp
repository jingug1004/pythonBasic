<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%--
 * @파일명 : shareTargetListPopup.jsp
 * @메뉴명 : 공유대상 팝업
 * @최초작성일 : 2015/03/04            
 * @author : CHOIILJI   
 * @수정내역 : 
--%>
<%@ page import="java.util.List" %>
<%@ page import="com.hanaph.gw.co.common.utils.StringUtil" %>
<%@ page import="com.hanaph.gw.pe.member.vo.MemberVO" %>  
<%@ include file ="/common/path.jsp" %>
<%
	//공유대상
	List<MemberVO> shareTargetList = (List<MemberVO>)request.getAttribute("shareTargetList");
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
			<%
				String share_dt ="";
				if(shareTargetList.size()!=0){
					MemberVO memberVO = new MemberVO();
					memberVO = shareTargetList.get(0);
					share_dt = memberVO.getCreate_dt();
				}	
			%>
			<h3 class="pop_tit">공유문서 대상자 <%if(shareTargetList.size()!=0){ %>- <%=share_dt %><%} %></h3>
			<div class="cont_box03" style="width: 500px;">
				<table>
					<colgroup>
						<col width="22%" />
						<col width="15%" />
						<col width="10%" />
						<col width="23%" />
					</colgroup>
					<tbody>
						<tr>
							<th>부서명</th>
							<th>사용자</th>
							<th>열람여부</th>
							<th class="br_none">열람시간</th>
						</tr>
						<tr>
						<%
						if(shareTargetList.size()!=0){
							for(int i=0; shareTargetList.size()>i;i++){
								MemberVO memberVO = new MemberVO();
								memberVO = shareTargetList.get(i);
						%>
						<tr>
							<td><%=StringUtil.nvl(memberVO.getDept_ko_nm()) %></td>
							<td><%=StringUtil.nvl(memberVO.getEmp_ko_nm()) %></td>
							<td><%=StringUtil.nvl(memberVO.getRead_yn(), "N")%></td>
							<td class="br_none"><%=StringUtil.nvl(memberVO.getRead_dt()) %></td>
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
