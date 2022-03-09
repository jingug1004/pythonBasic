<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%--
 * @파일명 : basicApprovalMemberTargetIframe.jsp
 * @메뉴명 : 기본결재라인 대상자 iframe
 * @최초작성일 : 2015/01/28            
 * @author : CHOIILJI                 
 * @수정내역 : 
--%>
<%@ page import="java.util.List" %>
<%@ page import="com.hanaph.gw.co.common.utils.StringUtil" %>
<%@ page import="com.hanaph.gw.pe.member.vo.MemberVO" %>   
<%@ page import="com.hanaph.gw.ea.mgrDocuInfo.vo.BasicApprovalVO" %>
<%@ include file ="/common/path.jsp" %>
<%
	//결재
	List<BasicApprovalVO> basicApprovalDetailList = (List<BasicApprovalVO>)request.getAttribute("basicApprovalDetailList");
%>
<!DOCTYPE html>
<html lang="ko">
<head>
	<%@include file="/include/head.jsp"%>	
	<script type="text/javascript">
	//결재
	function addApprovalMultiRow(){
		var sameCnt = 0;
		var approvalCheckBoxArr = document.getElementsByName("approval_emp_no");
		var approvalCheckBoxCnt = document.getElementsByName("approval_emp_no").length;
		
	    var approvalListCheckBoxArr = parent.approvalMemberList.document.getElementsByName("emp_no");
	    var approvalListCheckBoxCnt = parent.approvalMemberList.document.getElementsByName("emp_no").length;
	    
	    var checkBoxParam = "";
	    var memberListCheckBoxParam = "";

	    for(var i=0; i<approvalListCheckBoxCnt; i++){
	    	if(approvalListCheckBoxArr[i].checked){
		    	memberListCheckBoxParam = approvalListCheckBoxArr[i].value;
		    	var memberListCheckValueArr = memberListCheckBoxParam.split("|");
				for(var j=0; j<approvalCheckBoxCnt; j++){
					checkBoxParam = approvalCheckBoxArr[j].value;
					if(memberListCheckValueArr[0] == checkBoxParam){
						if(sameCnt == 0){
							alert("동일한 직원이 존재합니다.");
							sameCnt++;
						}
						approvalListCheckBoxArr[i].checked = false;
					}
				}
		    }
	    } 
	    
	    for(i=0; i<approvalListCheckBoxCnt; i++){
	    	if(approvalListCheckBoxArr[i].checked){
	    		if($( "li" ).length > 6 ){
			    	alert("결재는 최대 7명 입니다.");
			    	parent.approvalMemberList.allUnCheck();
			    	return;
			    }
		    	memberListCheckBoxParam = approvalListCheckBoxArr[i].value;
		    	var memberListCheckValueArr = memberListCheckBoxParam.split("|");
	    		var ul = document.getElementById("approval_ul");
				var li = document.createElement("li");
		    	li.setAttribute("data-role", "ui-droplist-item");
		    	ul.appendChild(li);
		    	li.innerHTML = memberListCheckValueArr[1]+"<input type='hidden' name='approval_emp_no' value='"+memberListCheckValueArr[0]+"'>";	
		    }
	    } 
	    parent.approvalMemberList.allUnCheck();
	}
	</script>
</head>
<body>
	<div class="settle_men fl" data-role="ui-droplist" data-type="basic">
		<h4>결재자</h4>
		<ul id="approval_ul" class="select_list fl" data-role="ui-droplist-list">
			<%
			if(basicApprovalDetailList.size() > 0){
				for(int i=0; basicApprovalDetailList.size()>i;i++){
					BasicApprovalVO basicApprovalVO = new BasicApprovalVO();
					basicApprovalVO = basicApprovalDetailList.get(i);
			%>
			<li data-role="ui-droplist-item"><%=basicApprovalVO.getEmp_ko_nm() %><input type="hidden" name="approval_emp_no" value="<%=basicApprovalVO.getEmp_no()%>"></li>
			<%
				}
			}
			%>		
		</ul>
		<div class="btn_list fr">
			<a href="#" class="btn_top" data-role="ui-droplist-btn-up"><img src="<%=GROUP_WEB_ROOT%>/img/popup_btn_top.gif" alt="위로"></a>
			<a href="#" class="btn_bottom" data-role="ui-droplist-btn-down"><img src="<%=GROUP_WEB_ROOT%>/img/popup_btn_bottom.gif" alt="아래로"></a>
			<a href="#" class="btn_close" data-role="ui-droplist-btn-remove"><img src="<%=GROUP_WEB_ROOT%>/img/popup_btn_close02.gif" alt="삭제"></a>
		</div>
	</div>
<script src="<%=GROUP_WEB_ROOT%>/js/droplist.js"></script>
<script type="text/javascript">
$('*[data-role=ui-droplist]').each(function () {
    var droplist = new Droplist($(this), -1);
});
</script>
</body>
</html>