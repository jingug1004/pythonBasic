<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<div class="lnb">
	<%
		if(lnbMentList.size()!=0){
			MenuVO menuVO = new MenuVO();
			menuVO = lnbMentList.get(0);
	%>
	<p class="tit"><%=StringUtil.nvl(menuVO.getParents_menu_nm()) %></p>
	<%
		}
	%>
	<ul>
		<%
		if(lnbMentList.size()!=0){
			for(int i=0; lnbMentList.size()>i;i++){
				MenuVO menuVO = new MenuVO();
				menuVO = lnbMentList.get(i);
		%>
		<li <%if(i==lnbMentList.size()-1){ %> class="last"<%} %>>
			<a href="<%=GROUP_ROOT %><%=menuVO.getUrl() %>" >
				<%if(menuVO.getMenu_child().length() == 6) {%>&nbsp;&nbsp;&nbsp;&nbsp;ㄴ<%} %>
				<%=StringUtil.nvl(menuVO.getMenu_nm())%>	
				<span class="none_check">&nbsp;<%=StringUtil.nvl(menuVO.getAdd_menu_nm())%></span>			
			</a>
		</li>
		<%
			}
		}
		%>
	</ul>
</div>