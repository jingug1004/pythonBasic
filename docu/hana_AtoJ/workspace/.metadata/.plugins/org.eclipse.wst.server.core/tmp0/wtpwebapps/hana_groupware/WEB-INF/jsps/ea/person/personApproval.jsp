<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%--
 * @파일명 : personApproval.jsp
 * @메뉴명 : 개인결재라인
 * @최초작성일 : 2015/01/28            
 * @author : CHOIILJI                 
 * @수정내역 : 
--%>
<%@ page import="java.util.List" %>
<%@ page import="com.hanaph.gw.co.common.utils.StringUtil" %>
<%@ page import="com.hanaph.gw.co.common.utils.RequestFilterUtil" %>
<%@ page import="com.hanaph.gw.co.personnel.vo.DepartmentVO" %>
<%@ page import="com.hanaph.gw.ea.person.vo.PersonLineVO" %>
<%@ include file ="/common/path.jsp" %>
<%
	//부서트리
	List<DepartmentVO> departmentList = (List<DepartmentVO>)request.getAttribute("departmentList");
	//개인결재라인 리스트
	List<PersonLineVO> personLineList = (List<PersonLineVO>)request.getAttribute("personLineList");

	String person_seq = StringUtil.nvl((String)request.getAttribute("person_seq"));
%>
<!DOCTYPE html>
<html lang="ko">
<head>
	<%@include file="/include/head.jsp"%>	
	<script type="text/javascript">
		function goNowApprovalSave(obj){
			//결재
			var approval_emp_no = approvalMemberTarget.document.getElementsByName("approval_emp_no");
			var approval;
			if(approval_emp_no.length> 0){
				approval = new Array();
				for(var i=0; approval_emp_no.length> i;i++){
					approval.push(approval_emp_no[i].value+"|"+(i+1));
				}
				document.getElementById("approval").value = approval;
			}
			
			//시행
			var implDept_dept_cd = approvalDeptTarget.document.getElementsByName("implDept_dept_cd");
			var implDept;
			if(implDept_dept_cd.length> 0){
				implDept = new Array();
				for(var i=0; implDept_dept_cd.length> i;i++){
					implDept.push(implDept_dept_cd[i].value+"|"+(i+1));
				}
				document.getElementById("implDept").value = implDept;
			}	
		
			document.getElementById("subject").value = obj.value;
			document.getElementById("frm_approval").action="<%=GROUP_ROOT %>/ea/person/personApprovalInsert.do";
			document.getElementById("frm_approval").submit();
		}
	
		
		function openPersonLine(){
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
			
			if(confirm("현결재라인을 저장하시겠습니까?") == true){
				$('#now_approval').bPopup({
					content:'iframe', //'ajax', 'iframe' or 'image'
					iframeAttr:'scrolling="no" frameborder="0" width="330px" height="210px"',
					follow: [true, true],
					contentContainer:'.now_approval_content',
					modalClose: true,
		            opacity: 0.6,
		            positionStyle: 'fixed',
					loadUrl:'<%=GROUP_ROOT%>/ea/person/personLineForm.do',
				});
			}	
		}
		
		function layerClose(){ 
			$('#now_approval').bPopup().close();
		}
		
		function goApprovalLine(obj){
			document.getElementById("person_seq").value =obj.value;
			document.getElementById("frm_approval").action = "<%=GROUP_ROOT %>/ea/person/personApproval.do";
			document.getElementById("frm_approval").submit();
		}
		
		function goDelete(){
			if(formCheck.isNull(document.getElementById("person_seq"), "삭제할 결재 라인을 선택해 주세요.")){
				return;
			}
			
			if(confirm("삭제하시겠습니까?") == true){
				document.getElementById("frm_approval").action = "<%=GROUP_ROOT %>/ea/person/approvalDelete.do";
				document.getElementById("frm_approval").submit();
			}
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
				<h2>개인결재라인</h2>
				<form name="frm_iframe" id="frm_iframe" method="post">
					<input type="hidden" id="dept_cd" name="dept_cd">
					<input type="hidden" id="up_dept_gbn" name="up_dept_gbn">
				</form>	
				<div class="settlement_box">
					<p class="list_t">문서 내용과 결재라인을 최종 확인하시고  요청버튼을 클릭하시면 상신됩니다.</p>
					<p class="mt5">작성완료버튼을 클릭하시면 내가 올린 문서에 작성 중 상태로 등록됩니다. </p>
					<div class="settle_individ">				
						<div class="fl">
							<form name="frm_approval" id="frm_approval" method="post">
								<input type="hidden" id="approval" name="approval">
								<input type="hidden" id="implDept" name="implDept">
								<input type ="hidden" id="subject" name="subject">
							<div class="search_box01">
								<span>결재라인</span>
								<select id="person_seq" name="person_seq" onchange="javascript:goApprovalLine(this);">
									<option value="" <%if(person_seq.equals("")){ %>selected<%} %>>선택하세요</option>
									<%
									if(personLineList.size() !=0){ 
										for(int i=0; personLineList.size()>i;i++){
											PersonLineVO personLineVO = new PersonLineVO();
											personLineVO = personLineList.get(i);
									%>
									<option value="<%=personLineVO.getPerson_seq()%>" <%if(person_seq.equals(String.valueOf(personLineVO.getPerson_seq()))){ %>selected<%} %>>[개인]<%=RequestFilterUtil.toHtmlTagReplace("", personLineVO.getSubject()) %></option>
									<%	}
									}%>
								</select>
							</div>
							</form>
							<div class="cont_box04 pst_rel">		
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
									document.getElementById("frm_iframe").action="<%=GROUP_ROOT %>/ea/person/personApprovalMemberIframe.do"
									document.getElementById("frm_iframe").submit();
									goDept(dept_cd);
								}	
								
								function goDept(dept_cd){
									document.getElementById("frm_iframe").target= "approvalDeptList";
									document.getElementById("dept_cd").value = dept_cd;
									document.getElementById("frm_iframe").action="<%=GROUP_ROOT %>/ea/person/personApprovalDeptIframe.do"
									document.getElementById("frm_iframe").submit();
								}
							</script>
						</div>		
					</div>
					<div class="fr">
						<div class="wrap_settle_cont">
							<div class="settle_cont01 fl posa">
								<iframe id="approvalMemberList" name="approvalMemberList" src="<%=GROUP_ROOT %>/ea/person/personApprovalMemberIframe.do" frameborder="0" border="0" scrolling="no" width="432px" height="265px"></iframe>
							</div>
		
							<div class="settle_cont02 fr mt52 h212">
								<iframe id="approvalMemberTarget" name="approvalMemberTarget" src="<%=GROUP_ROOT %>/ea/person/personApprovalMemberTargetIframe.do?person_seq=<%=person_seq %>" frameborder="0" border="0" scrolling="no" width="152px" height="265px"></iframe>
							</div>
						</div>
		
						<div class="wrap_settle_cont mt20">
							<div class="settle_cont01 fl">
								<iframe id="approvalDeptList" name="approvalDeptList" src="<%=GROUP_ROOT %>/ea/person/personApprovalDeptIframe.do" frameborder="0" border="0" scrolling="no" width="280px" height="212px"></iframe>
							</div>	
							<div class="settle_cont02 fr">
								<iframe id="approvalDeptTarget" name="approvalDeptTarget" src="<%=GROUP_ROOT %>/ea/person/personApprovalDeptTargetIframe.do?person_seq=<%=person_seq %>" frameborder="0" border="0" scrolling="no" width="152px" height="212px"></iframe>
							</div>	
						</div>	
						<div class="list_btn last_step">
							<div class="list_button">
							<button type="button" class="type_01" onclick="javascript:openPersonLine(); return false;">현재결재라인저장</button>
							<button type="button" class="type_01" onclick="javascript:goDelete(); return false;">현재결재라인삭제</button>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>	
<!-- 현결재라인저장 iframe -->
<div id='now_approval' style='display:none; width:auto; height:auto; '>
	<div class='now_approval_content'></div> 
</div>		
</body>
</html>