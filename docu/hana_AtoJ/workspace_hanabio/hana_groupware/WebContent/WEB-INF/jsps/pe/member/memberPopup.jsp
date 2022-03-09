<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%--
 * @파일명 : memberPopup.jsp
 * @메뉴명 : 회원정보 팝업
 * @최초작성일 : 2015/01/28            
 * @author : CHOIILJI                 
 * @수정내역 : 
--%>
<%@ page import="java.util.List" %>
<%@ page import="com.hanaph.gw.co.common.utils.StringUtil" %>
<%@ page import="com.hanaph.gw.pe.member.vo.MemberVO" %>   
<%@ include file ="/common/path.jsp" %>
<%
	MemberVO memberDetailVO = (MemberVO)request.getAttribute("memberDetail");
%>
<!DOCTYPE html>
<html lang="ko">
<head>
<%@ include file ="/include/head.jsp" %>
</head>
<body>
<div class="wrap_user_data">
	<div class="data_menu">
		<h3>사용자정보</h3>
		<div class="data_box">
			<table class="pop_table01 text_left">
				<colgroup>
					<col style="width:113px" />
					<col style="width:166px" />
					<col style="width:110px" />
				</colgroup>
				<tbody>
					<tr>
						<th>이름</th>
						<td><%=StringUtil.nvl(memberDetailVO.getEmp_ko_nm()) %></td>
						<td rowspan="5" class="img_box nopadding"><img src="<%=GROUP_ROOT %>/pe/member/memberPhoto/<%=StringUtil.nvl(memberDetailVO.getEmp_no()) %>.do" onerror="this.src='<%=GROUP_WEB_ROOT%>/img/character.gif'" alt="사진" alt="사진"  /></td>
					</tr>
					<tr>
						<th>부서</th>
						<td><%=StringUtil.nvl(memberDetailVO.getDept_ko_nm())%></td>
					</tr>
					<tr>
						<th>직급</th>
						<td><%=StringUtil.nvl(memberDetailVO.getGrad_ko_nm())%></td>
					</tr>
					<tr>
						<th>내선번호</th>
						<td><%=StringUtil.nvl(memberDetailVO.getIn_tel())%></td>
					</tr>
					<tr>
						<th>모바일전화</th>
						<td><%=StringUtil.nvl(memberDetailVO.getPager_no())%></td>
					</tr>
				</tbody>
			</table>
		</div>
	
		<button class="close" type="button" onclick="javascript:self.close();"><span class="blind">닫기</span></button>
	</div>
</div>
</body>
</html>