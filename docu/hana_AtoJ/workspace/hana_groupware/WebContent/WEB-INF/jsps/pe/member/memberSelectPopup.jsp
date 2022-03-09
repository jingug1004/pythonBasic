<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%--
 * @파일명 : memberSelectPopup.jsp
 * @메뉴명 : 임직원선택 팝업
 * @최초작성일 : 2015/01/28            
 * @author : CHOIILJI                 
 * @수정내역 : 
--%>
<%@ page import="java.util.List" %>
<%@ page import="com.hanaph.gw.co.common.utils.StringUtil" %>
<%@ page import="com.hanaph.gw.co.personnel.vo.DepartmentVO" %>
<%@ include file ="/common/path.jsp" %>
<%
	List<DepartmentVO> departmentList = (List<DepartmentVO>)request.getAttribute("departmentList");
	String type = StringUtil.nvl((String)request.getAttribute("type"));
	String seq = StringUtil.nvl((String)request.getAttribute("seq"));
%>
<!DOCTYPE html>
<html lang="ko">
<head>
	<%@include file="/include/head.jsp"%>	
	<script type="text/javascript">
	/**
	 * 부모창에 값 전달
	 */
	function goMemberTarget(){
		parent.addMemberRow(memberTargetList.document.getElementsByName("emp_no"));
		window.close();
	}
	</script>
</head>
<body>

<div class="wrap_authority_staff">
	<div class="authority_staff">
		<h3>임직원선택</h3>
		<form name="frm" id="frm" method="post" action="<%=GROUP_ROOT %>/pe/member/memberSelectMemberIframe.do">
			<input type ="hidden" id="dept_cd" name="dept_cd">
			<input type ="hidden" id="up_dept_gbn" name="up_dept_gbn">
			<input type ="hidden" id="type" name="type" value="<%=type%>">
			<input type ="hidden" id="seq" name="seq" value="<%=seq%>">
		</form>	
		
		<div class="wrap_tree pst_rel">
			<div class="wrap_tree_btn pst_abs" style="width:92px; margin:10px auto">
				<button class="type_01" onclick="javascript: d.openAll();">펼치기</button>
				<button class="type_01" onclick="javascript: d.closeAll();">접기</button>
			</div>
			<div class="mt10" style="margin-top:40px;"></div>
			<script type="text/javascript">

				d = new dTree('d');
				<%
				if(departmentList.size()!=0){
					for(int i=0; departmentList.size()>i;i++){
						DepartmentVO departmentVO = new DepartmentVO();
						departmentVO = departmentList.get(i);
						if(departmentVO.getUp_dept_cd().equals("9999")){
							departmentVO.setUp_dept_cd("-1");
						}
				%>
				
				d.add("<%=StringUtil.nvl(departmentVO.getDept_cd())%>","<%=StringUtil.nvl(departmentVO.getUp_dept_cd()) %>","<%=StringUtil.nvl(departmentVO.getDept_ko_nm()) %>","javascript:goLocation('<%=StringUtil.nvl(departmentVO.getDept_cd())%>','<%=StringUtil.nvl(departmentVO.getUp_dept_gbn())%>');","<%=StringUtil.nvl(departmentVO.getDept_ko_nm()) %>");
				<%
					}
				}
				%>
				document.write(d);
			
				function goLocation( dept_cd, up_dept_gbn){
					document.getElementById("frm").target= "memberList";
					document.getElementById("dept_cd").value = dept_cd;
					document.getElementById("up_dept_gbn").value = up_dept_gbn;
					document.getElementById("frm").submit();
				}	
				
			</script>
		</div>
		
		<div class="wrap_group">
			<iframe name="memberList" src="<%=GROUP_ROOT %>/pe/member/memberSelectMemberIframe.do?seq=<%=seq %>&type=<%=type %>" width="303px;" height="260px" frameBorder="0" border="0"></iframe>
			<iframe name="memberTargetList" src="<%=GROUP_ROOT %>/pe/member/memberSelectMemberTargetIframe.do?seq=<%=seq %>&type=<%=type %>" width="303px;" height="345px" frameBorder="0" border="0"></iframe>			
		</div>

		<button class="close" onclick="window.parent.layerClose();"><span class="blind">닫기</span></button>
	</div>
</div>

</body>
</html>