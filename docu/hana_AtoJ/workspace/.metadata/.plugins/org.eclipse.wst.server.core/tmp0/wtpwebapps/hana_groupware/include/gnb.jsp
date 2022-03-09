<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.hanaph.gw.co.menu.vo.MenuVO" %>
<%
	List<MenuVO> gnbMenuList = (List)headerSession.getAttribute("gwGNB"); 
%>
<div class="wrap_gnb">
	<ul>
		<%
		if(gnbMenuList.size() > 0){
			for(int i=0; gnbMenuList.size()>i;i++){
				MenuVO menuVO = new MenuVO();
				menuVO = gnbMenuList.get(i);
		%>
		<li <%if(i == 0){ %>class="first"<%} %>><a href="#" onclick="location.href='<%=GROUP_ROOT %><%=menuVO.getUrl()%>'"><%=menuVO.getMenu_nm() %><% if("jdbc:oracle:thin:@58.229.234.12:1521:ORCL".equals(JDBC_URL)){ %>(테)<%} %></a></li>
		<%	}
		} %> 
	</ul>
	
</div>