<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%--
 * @파일명 : personnelList.jsp
 * @메뉴명 : 인사현황조회  
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
			
			<!-- ######## start ####### -->
			<div class="cont float_left">
				<h2>인사현황조회</h2>
				<div class="wrap_personnel_situation">
					<form name="frm" id="frm" method="post" action="<%=GROUP_ROOT %>/hr/personnel/personnelDetailIframe.do">
						<input type ="hidden" id="dept_cd" name="dept_cd">
						<input type ="hidden" id="up_dept_gbn" name="up_dept_gbn">
					</form>
					<div class="wrap_tree">
						<p class="explain">아래의 부서를 선택하시면 소속 사용자가 나옵니다.</p>
						<div class="tree pst_rel" >
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
									document.getElementById("frm").target= "personnelDetail";
									document.getElementById("dept_cd").value = dept_cd;
									document.getElementById("up_dept_gbn").value = up_dept_gbn;
									document.getElementById("frm").submit();
								}		
							</script>
						</div>
					</div>
					
					<div class="wrap_status ml14">
						<iframe name="personnelDetail"  src="<%=GROUP_ROOT %>/hr/personnel/personnelDetailIframe.do" frameBorder="0" border="0" width="490px" height="540px"></iframe>
					</div>
				</div>
			</div>
			
			<!-- ######## end ######### -->
			
		</div>
	</div>
	
	<%@include file="/include/footer.jsp"%>
</body>
</html>