<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%--
 * @파일명 : basicApproval.jsp
 * @메뉴명 : 기본결재라인
 * @최초작성일 : 2015/01/28            
 * @author : CHOIILJI                 
 * @수정내역 : 
--%>
<%@ page import="java.util.List" %>
<%@ page import="com.hanaph.gw.co.common.utils.StringUtil" %>
<%@ page import="com.hanaph.gw.co.personnel.vo.DepartmentVO" %>
<%@ page import="com.hanaph.gw.ea.mgrDocuInfo.vo.DocumentInfoVO" %>
<%@ include file ="/common/path.jsp" %>
<%
	//부서트리
	List<DepartmentVO> departmentList = (List<DepartmentVO>)request.getAttribute("departmentList");
	
	//문서상세정보
	DocumentInfoVO documentInfoDetail = (DocumentInfoVO)request.getAttribute("documentInfoDetail");
	
	String search_dept_cd = StringUtil.nvl((String)request.getAttribute("search_dept_cd"));
	String search_docu_nm = StringUtil.nvl((String)request.getAttribute("search_docu_nm"));
%>
<!DOCTYPE html>
<html lang="ko">
<head>
	<%@include file="/include/head.jsp"%>	
	<script type="text/javascript">
		function goSave(){
			//결재
			var approval_emp_no = approvalMemberTarget.document.getElementsByName("approval_emp_no");
			var implDept_dept_cd = approvalDeptTarget.document.getElementsByName("implDept_dept_cd");
			
			if(approval_emp_no.length == 0){
				alert("결재자를 추가해 주세요.");
				return;
			}
			
			if(implDept_dept_cd.length == 0){
				alert("시행부서를 추가해 주세요.");
				return;
			}
			
			if(confirm("저장하시겠습니까?") == true){
				var approval;
				if(approval_emp_no.length> 0){
					approval = new Array();
					for(var i=0; approval_emp_no.length> i;i++){
						approval.push(approval_emp_no[i].value+"|"+(i+1));
					}
					document.getElementById("approval").value = approval;
				}

				//시행
				
				var implDept;
				if(implDept_dept_cd.length> 0){
					implDept = new Array();
					for(var i=0; implDept_dept_cd.length> i;i++){
						implDept.push(implDept_dept_cd[i].value+"|"+(i+1));
					}
					document.getElementById("implDept").value = implDept;
				}	
				
				document.getElementById("frm_approval").action="<%=GROUP_ROOT %>/ea/mgrDocuInfo/basicApprovalInsert.do";
				document.getElementById("frm_approval").submit();
			}
		}
	
		function goDocuInfoList(){
			document.getElementById("frm_approval").action="<%=GROUP_ROOT %>/ea/mgrDocuInfo/docuInfoList.do";
			document.getElementById("frm_approval").submit();
		}
	</script>
</head>
<body>
	<%@include file="/include/header.jsp"%>
	<%@include file="/include/gnb.jsp"%>
	<div class="wrap_con">
		<div class="content">
			<%@include file="/include/location.jsp"%>
			<%@include file="/include/lnb.jsp"%>
			<div class="cont float_left">
				<h2>문서결재라인<span class="type"> 등록</span></h2>
				<form name="frm_iframe" id="frm_iframe" method="post">
					<input type="hidden" id="dept_cd" name="dept_cd">
					<input type="hidden" id="up_dept_gbn" name="up_dept_gbn">
				</form>	
				
				<form name="frm_approval" id="frm_approval" method="post">
					<input type="hidden" id="approval" name="approval">
					<input type="hidden" id="implDept" name="implDept">
					<input type="hidden" id="docu_seq" name="docu_seq" value="<%=documentInfoDetail.getDocu_seq()%>">
					<input type="hidden" id="search_dept_cd" name="search_dept_cd" value="<%=search_dept_cd%>">
					<input type="hidden" id="search_docu_nm" name="search_docu_nm" value="<%=search_docu_nm%>">
				</form>
				<div class="list_btn last_step">
					<div class="list_button">
						<button type="button" class="type_01" onclick="javascript:goSave(); return false;">저장</button>
						<button type="button" class="type_01" onclick="javascript:goDocuInfoList(); return false;">목록</button>
					</div>
				</div>
				<div class="settlement_box">
					<ul class="group">
						<li><span class="tit">양식명</span><span><%=documentInfoDetail.getDocu_nm() %></span></li>
						<li><span class="tit">부서</span><span class="bdrn"><%=documentInfoDetail.getDept_ko_nm() %></span></li>
					</ul>
					<p class="txt">＊아래의 부서를 선택하시면 소속 사용자가 우측에 나옵니다.</p>
					<div class="settle_individ mgn">				
						<div class="fl">
							<div class="cont_box04 mgn h494 pst_rel">	
							<div class="wrap_tree_btn pst_abs" style="width:92px; margin:10px auto; left:114px;">
								<button type="button" class="type_01" onclick="javascript: d.openAll();">펼치기</button>
								<button type="button" class="type_01" onclick="javascript: d.closeAll();">접기</button>
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
									document.getElementById("frm_iframe").target= "approvalMemberList";
									document.getElementById("dept_cd").value = dept_cd;
									document.getElementById("up_dept_gbn").value = up_dept_gbn;
									document.getElementById("frm_iframe").action="<%=GROUP_ROOT %>/ea/mgrDocuInfo/basicApprovalMemberIframe.do";
									document.getElementById("frm_iframe").submit();
									goDept(dept_cd);
								}	
								
								function goDept(dept_cd){
									document.getElementById("frm_iframe").target= "approvalDeptList";
									document.getElementById("dept_cd").value = dept_cd;
									document.getElementById("frm_iframe").action="<%=GROUP_ROOT %>/ea/mgrDocuInfo/basicApprovalDeptIframe.do";
									document.getElementById("frm_iframe").submit();
								}
							</script>
						</div>		
					</div>
					<div class="fr">
						<div class="wrap_settle_cont">
							<div class="settle_cont01 fl posa">
								<iframe id="approvalMemberList" name="approvalMemberList" src="<%=GROUP_ROOT %>/ea/mgrDocuInfo/basicApprovalMemberIframe.do?dept_cd=<%=documentInfoDetail.getDept_cd() %>&up_dept_gbn=1" frameborder="0" border="0" scrolling="no" width="432px" height="265px"></iframe>
							</div>
		
							<div class="settle_cont02 fr mt52 h212">
								<iframe id="approvalMemberTarget" name="approvalMemberTarget" src="<%=GROUP_ROOT %>/ea/mgrDocuInfo/basicApprovalMemberTargetIframe.do?docu_seq=<%=documentInfoDetail.getDocu_seq() %>" frameborder="0" border="0" scrolling="no" width="152px" height="265px"></iframe>
							</div>	
						</div>
		
						<div class="wrap_settle_cont mt20">
							<div class="settle_cont01 fl">
								<iframe id="approvalDeptList" name="approvalDeptList" src="<%=GROUP_ROOT %>/ea/mgrDocuInfo/basicApprovalDeptIframe.do" frameborder="0" border="0" scrolling="no" width="280px" height="212px"></iframe>
							</div>	
							<div class="settle_cont02 fr">
								<iframe id="approvalDeptTarget" name="approvalDeptTarget" src="<%=GROUP_ROOT %>/ea/mgrDocuInfo/basicApprovalDeptTargetIframe.do?docu_seq=<%=documentInfoDetail.getDocu_seq() %>" frameborder="0" border="0" scrolling="no" width="152px" height="212px"></iframe>
							</div>	
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>			
</body>
</html>