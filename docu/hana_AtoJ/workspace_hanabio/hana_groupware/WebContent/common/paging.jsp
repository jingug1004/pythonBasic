<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%
	String pagingStr = com.hanaph.gw.co.common.utils.StringUtil.nvl((String)request.getAttribute("pagingStr"),"");
	String currentPage = com.hanaph.gw.co.common.utils.StringUtil.nvl(request.getParameter("currentPage"),"1");
%>
<script type="text/javascript">
	
	var $form = $("#frm");
	if($form.length > 0) {
	//		 	/* 정보 세팅 */
		$("<input></input>").attr({type:"hidden", name:"currentPage",id:"currentPage", value:$.trim('<%=currentPage%>')}).appendTo($form);
	}

	function goPage(v_curr_page) 
	{
	    $("#currentPage").val(v_curr_page);
	    $form.submit();
	}
</script>
<%if(!"".equals(pagingStr) && !"".equals(currentPage)){ %>
	<%=pagingStr%>
<%}%>