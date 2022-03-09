<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%--
 * @파일명 : addApprovalImplDept.jsp
 * @메뉴명 : 시행부서 추가
 * @최초작성일 : 2015/01/28            
 * @author : CHOIILJI                 
 * @수정내역 : 
--%>
<%@ page import="java.util.List" %>
<%@ page import="com.hanaph.gw.co.common.utils.StringUtil" %>
<%@ page import="com.hanaph.gw.co.personnel.vo.DepartmentVO" %>
<%@ include file ="/common/path.jsp" %>
<%
	String approval_seq = StringUtil.nvl((String)request.getAttribute("approval_seq"));
%>
<!DOCTYPE html>
<html lang="ko">
<head>
	<%@include file="/include/head.jsp"%>
	<script type="text/javascript">
	
	function goApprovalImplDept(){
		
		var implDept_dept_cd = approvalDeptTarget.document.getElementsByName("implDept_dept_cd");
		var implDept_dept_cd_cnt = approvalDeptTarget.document.getElementById("implDept_dept_cd_cnt").value;
		
		if(parseInt(implDept_dept_cd.length-implDept_dept_cd_cnt) == 0){
			alert("시행부서를 추가해 주세요.");
			return;
		}
		
		if(confirm("시행부서를 추가하시겠습니까?") == true){
			//시행
			var implDept;
			if(implDept_dept_cd.length> 0){
				implDept = new Array();
				for(var i=0; implDept_dept_cd.length> i;i++){
					var checkValueArr = implDept_dept_cd[i].value.split("|");
					if(checkValueArr[1] == "INSERT"){
						implDept.push(checkValueArr[0]+"|"+(i+1));
					}	
				}
				document.getElementById("implDept").value = implDept;
			}	
			
			document.getElementById("frm_approval").action = "<%=GROUP_ROOT %>/ea/newReport/addApprovalImplDeptInsert.do";
			document.getElementById("frm_approval").submit();
		}	
	}

	</script>
</head>
<body>
		<div class="settle_pop">
		<form name="frm_approval" id="frm_approval" method="post">
			<input type ="hidden" id="implDept" name="implDept">
			<input type ="hidden" id="approval_seq" name="approval_seq" value="<%=approval_seq%>">
		</form>
			<div class="popup_box">
				<h3>시행부서 추가</h3>
				<div class="wrap_settle_pop">
					<div class="box_sign_overtime">
						<span class="tit">시행부서 추가</span>				
					</div>
					<div class="pop_con_all pop_register mt15">
						<div class="float_l w308 posa">
							<iframe id="approvalDeptList" name="approvalDeptList" src="<%=GROUP_ROOT %>/ea/newReport/addApprovalImplDeptIframe.do" frameborder="0" border="0" scrolling="no" width="520px" height="265px"></iframe>
						</div>
						<div class="float_r h193 mt52">
							<iframe id="approvalDeptTarget" name="approvalDeptTarget" src="<%=GROUP_ROOT %>/ea/newReport/addApprovalImplDeptTargetIframe.do?approval_seq=<%=approval_seq %>" frameborder="0" border="0" scrolling="no" width="188px" height="214px"></iframe>
						</div>
					</div>
				</div>
				<button type="button" class="close" onclick="parent.layerClose();"><span class="blind">닫기</span></button>
			</div>
		</div>
		<!--  popup end -->		
</body>
</html>