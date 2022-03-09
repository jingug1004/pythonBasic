<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="com.hanaph.gw.co.menu.vo.MenuVO" %>
<%@ page import="com.hanaph.gw.co.common.utils.StringUtil" %>
<%@ page import="java.util.List" %>
<%
	List<MenuVO> lnbMentList = (List<MenuVO>)request.getAttribute("lnbMenuList");
	if(lnbMentList == null){
		lnbMentList = new ArrayList();
	}
	String MENU_CHILD = com.hanaph.gw.co.common.utils.StringUtil.nvl((String)request.getAttribute("MENU_CHILD"), "01");
%>

<div class="location">
	<ul>
		<li><a href="#" onclick="location.href='<%=GROUP_ROOT%>/main/main.do'"><img src="<%=GROUP_WEB_ROOT%>/img/location_icon_home.gif" alt="home" /></a> > </li>
		<%
		if(lnbMentList.size()!=0){
			
			for(int i=0; lnbMentList.size()>i;i++){
				MenuVO menuVO = new MenuVO();
				menuVO = lnbMentList.get(i);
				if(menuVO.getMenu_child().equals(MENU_CHILD)){
		%>
		<li><a href="#"><%=menuVO.getParents_menu_nm() %></a> > </li>
		<li><a href="<%=GROUP_ROOT %><%=menuVO.getUrl() %>" class="active"><%=menuVO.getMenu_nm() %></a></li>
		<%
				}
			}
		}
		%>
		
	</ul>
</div>