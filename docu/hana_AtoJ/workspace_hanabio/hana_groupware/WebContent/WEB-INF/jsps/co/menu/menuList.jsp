<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%--
 * @파일명 : menuList.jsp
 * @메뉴명 : 메뉴리스트
 * @최초작성일 : 2015/02/10            
 * @author : Jung.Jin.Muk(pc123pc@irush.co.kr)                 
 * @수정내역 : 
--%>
<%@ page import="java.util.List" %>
<%@ page import="com.hanaph.gw.co.menu.vo.MenuVO" %>   
<%@ include file ="/common/path.jsp" %>
<%
	List<MenuVO> menuList = (List<MenuVO>)request.getAttribute("menuList"); //메뉴리스트
%>
<!DOCTYPE html>
<html lang="ko">
<head>
	<%@include file="/include/head.jsp"%>
</head>
<body>
	<%@include file="/include/header.jsp"%>
	<%@include file="/include/gnb.jsp"%>
	<div class="wrap_con">
		<div class="content">
			<%@include file="/include/location.jsp"%>
			<%@include file="/include/lnb.jsp"%>
			<div class="cont float_left">
				<h2>메뉴관리</h2>
				<div class="management_box">
					<form name="frm" id="frm" method="post">
						<input type ="hidden" id="menu_parents" name="menu_parents">
						<input type ="hidden" id="menu_child" name="menu_child">
					</form>
					<div class="cont_box fl menu_lft pst_rel">
						<div class="list_button pst_abs" style="width:92px; margin:10px auto">
							<button class="type_01" onclick="javascript: d.openAll();">펼치기</button>
							<button class="type_01" onclick="javascript: d.closeAll();">접기</button>
						</div>
						<div class="mt10" style="margin-top:40px;"></div>
						<table style="width:200px;">
							<colgroup>
								<col style="width:40%;">
								<col style="width:60%;">
							</colgroup>
							<tbody>
							<tr>
								<td valign="top">
								<script type="text/javascript">
									d = new dTree('d');
									<%
									if(menuList.size()!=0){
										for(int i=0; menuList.size()>i;i++){
											MenuVO menuVO = new MenuVO();
											menuVO = menuList.get(i);
									%>
									d.add(<%=menuVO.getMenu_child()%>,<%=menuVO.getMenu_parents()%>,"<%=menuVO.getMenu_nm()%>","javascript:goLocation('<%=menuVO.getMenu_parents()%>','<%=menuVO.getMenu_child()%>');","<%=menuVO.getDescr()%>");			
									<%
										}
									}
									%>
									document.write(d);
									/**
									 * 하위메뉴리스트
									 * @param menu_parents
									 * @param menu_child
									 */
									function goLocation(menu_parents,menu_child){
										document.frm.menu_parents.value = menu_parents;
										document.frm.menu_child.value = menu_child;
										document.frm.target="menuDetail";
										document.frm.action="<%=GROUP_ROOT %>/co/menu/menuDetailSubListIframe.do";
										document.frm.submit();
									}
								</script>
								</td>
							</tr>
							</tbody>
						</table>
					</div>
					<div class="menu_cont fr">
		 				<iframe src="<%=GROUP_ROOT %>/co/menu/menuDetailSubListIframe.do" id="menuDetail" name="menuDetail" frameBorder="0" border="0" width="518px" height="545px" style="overflow:hidden"></iframe>
					</div>
				</div>
			</div>
		</div>
	</div>
	<%@include file="/include/footer.jsp"%>
</body>
</html>