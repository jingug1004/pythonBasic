<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%--
 * @파일명 : step2approvalDeptTargetIframe.jsp
 * @메뉴명 : 결재라인 시행부서 대상 iframe    
 * @최초작성일 : 2015/01/28            
 * @author : CHOIILJI                 
 * @수정내역 : 
--%>
<%@ page import="java.util.List" %>
<%@ page import="com.hanaph.gw.co.common.utils.StringUtil" %>
<%@ page import="com.hanaph.gw.pe.member.vo.MemberVO" %>
<%@ page import="com.hanaph.gw.ea.mgrDocuInfo.vo.BasicImplDeptVO" %>
<%@ page import="com.hanaph.gw.ea.newReport.vo.ImplDeptVO" %>
<%@ page import="com.hanaph.gw.ea.person.vo.PersonImplDeptVO" %>
<%@ include file ="/common/path.jsp" %>
<%
	String approval_seq = (String)request.getAttribute("approval_seq");
	String docu_seq = (String)request.getAttribute("docu_seq");
	String person_seq = (String)request.getAttribute("person_seq");
	String approval_gbn = (String)request.getAttribute("approval_gbn");

	//기본시행
	List<BasicImplDeptVO> basicImplDeptDetailList = (List<BasicImplDeptVO>)request.getAttribute("implDeptDetailList");
	//개인시행
	List<PersonImplDeptVO> personImplDeptDetailList = (List<PersonImplDeptVO>)request.getAttribute("implDeptDetailList");
	//저장시행
	List<ImplDeptVO> implDeptDetailList = (List<ImplDeptVO>)request.getAttribute("implDeptDetailList");
%>
<!DOCTYPE html>
<html lang="ko">
<head>
	<%@include file="/include/head.jsp"%>	
	<script type="text/javascript">
		function addImplDeptMultiRow(){
			var sameCnt = 0;
			
			var checkBoxArr = document.getElementsByName("implDept_dept_cd"); //파일 체크박스
		    var checkBoxCnt = document.getElementsByName("implDept_dept_cd").length;
		
		    var approvalListCheckBoxArr = parent.approvalDeptList.document.getElementsByName("dept_cd"); //파일 체크박스
		    var approvalListCheckBoxCnt = parent.approvalDeptList.document.getElementsByName("dept_cd").length;
		    
		    var checkBoxParam = "";	
		    var memberListCheckBoxParam = ""; 
		    
		   for(var i=0; i<approvalListCheckBoxCnt; i++){
				if(approvalListCheckBoxArr[i].checked){
			    	memberListCheckBoxParam = approvalListCheckBoxArr[i].value;
			    	var memberListCheckValueArr = memberListCheckBoxParam.split("|");
					for(var j=0; j<checkBoxCnt; j++){
						checkBoxParam = checkBoxArr[j].value;
						if(memberListCheckValueArr[0] == checkBoxParam){
							if(sameCnt == 0){
								alert("동일한 부서가 존재합니다.");
								sameCnt++;
							}
							approvalListCheckBoxArr[i].checked = false;
						}
					}
			    }
		    } 
		    
		    for(i=0; i<approvalListCheckBoxCnt; i++){
		    	if(approvalListCheckBoxArr[i].checked){
			    	memberListCheckBoxParam = approvalListCheckBoxArr[i].value;
			    	var memberListCheckValueArr = memberListCheckBoxParam.split("|");
					var ul = document.getElementById("implDept_ul");
					var li = document.createElement("li");
			    	li.setAttribute("data-role", "ui-droplist-item");
			    	ul.appendChild(li);
			    	li.innerHTML=memberListCheckValueArr[1]+"<input type='hidden' name='implDept_dept_cd' value='"+memberListCheckValueArr[0]+"'>";
			    }
		    } 
		    parent.approvalDeptList.allUnCheck();
		    parent.approvalDeptList.document.getElementById("all_chk").checked = false;
		}
		
		function addApprovalImplDeptMultiRow(){
			
			/*CHOE 20150706 왼쪽 정보 (대상자 선택화면)*/
			var approvalCheckBoxArr = parent.approvalMemberList.document.getElementsByName("emp_no");
		    var approvalCheckBoxCnt = parent.approvalMemberList.document.getElementsByName("emp_no").length;			
			
			/*CHOE 20150706 오른쪽 정보(LIST 화면)*/
			var DeptListBoxArr = document.getElementsByName("implDept_dept_cd");
			var DeptListBoxCnt = document.getElementsByName("implDept_dept_cd").length;			
			
			var approvalCheckBoxParam = "";
		    var DeptListBoxParam = "";
		    
		    for(var i=0; i<approvalCheckBoxCnt; i++){  
		    	if(approvalCheckBoxArr[i].checked){
		    		approvalCheckBoxParam = approvalCheckBoxArr[i].value;
			    	var approvalCheckBoxValueArr = approvalCheckBoxParam.split("|");
			    	
			    	for(var j=0; j<DeptListBoxCnt; j++){
			    		DeptListBoxParam = DeptListBoxArr[j].value;
						var DeptLIstValueArr = DeptListBoxParam.split("|");
						
						if(approvalCheckBoxValueArr[0] == DeptLIstValueArr[0]){
							/*시행부서와 비교 하였지만 이함수는 직원을 포함하므로 동일한 직원이 존재한다고 알려준다.*/
							alert("시행부서(시행검토자)에 동일한 직원이 존재합니다.");
							approvalCheckBoxArr[i].checked = false;
						}						
			    	}			    	
		    	}
		    }
		    
		    for(i=0; i<approvalCheckBoxCnt; i++){		    	
		    	if(approvalCheckBoxArr[i].checked){		    	
		    		approvalCheckBoxParam = approvalCheckBoxArr[i].value;
			    	var approvalCheckBoxValueArr = approvalCheckBoxParam.split("|");
			    	
		    		var ul = document.getElementById("implDept_ul");
					var li = document.createElement("li");
			    	li.setAttribute("data-role", "ui-droplist-item");
			    	ul.appendChild(li);
			    	li.innerHTML = approvalCheckBoxValueArr[1]+"<input type='hidden' name='implDept_dept_cd' value='"+approvalCheckBoxValueArr[0]+"'>";
			    	
			    }
		    } 
		    
		    /*CHOE 20150706 선택한 사원 정보를 선택 취소로 만든다.*/
		    parent.approvalMemberList.allUnCheck();
		    
		}
	</script>
</head>
<body>
	<div class="settle_men fr" data-role="ui-droplist" data-type="basic">
		<h4>시행부서(시행검토자)</h4>
		<ul id="implDept_ul" class="select_list01 fl" data-role="ui-droplist-list">
		<%
		if(approval_gbn.equals("") || approval_gbn == null){
			if(implDeptDetailList.size()!=0){
				for(int i=0; implDeptDetailList.size()>i;i++){
					ImplDeptVO implDeptVO = new ImplDeptVO();
					implDeptVO = implDeptDetailList.get(i);
		%>	
			<li data-role="ui-droplist-item"><%=implDeptVO.getDept_ko_nm() %><input type="hidden" name="implDept_dept_cd" value="<%=implDeptVO.getDept_cd()%>"></li>
		<%
				}
			}
		}else{
			if(approval_gbn.equals("BASIC")){
				if(basicImplDeptDetailList.size()!=0){
					for(int i=0; basicImplDeptDetailList.size()>i;i++){
						BasicImplDeptVO basicImplDeptVO = new BasicImplDeptVO();
						basicImplDeptVO = basicImplDeptDetailList.get(i);
		%>
			<li data-role="ui-droplist-item"><%=basicImplDeptVO.getDept_ko_nm() %><input type="hidden" name="implDept_dept_cd" value="<%=basicImplDeptVO.getDept_cd()%>"></li>
		<%
				}
			}
			}else if(approval_gbn.equals("PERSON")){
				if(personImplDeptDetailList.size()!=0){
					for(int i=0; personImplDeptDetailList.size()>i;i++){
						PersonImplDeptVO personImplDeptVO = new PersonImplDeptVO();
						personImplDeptVO = personImplDeptDetailList.get(i);
		%>
			<li data-role="ui-droplist-item"><%=personImplDeptVO.getDept_ko_nm() %><input type="hidden" name="implDept_dept_cd" value="<%=personImplDeptVO.getDept_cd()%>"></li>
		<%
					}
				}
			}	
		}	
		%>
		
		</ul>
		<div class="btn_list01 fr">
			<a href="#" class="btn_top" data-role="ui-droplist-btn-up"><img src="<%=GROUP_WEB_ROOT%>/img/popup_btn_top.gif" alt="위로" /></a>
			<a href="#" class="btn_bottom" data-role="ui-droplist-btn-down"><img src="<%=GROUP_WEB_ROOT%>/img/popup_btn_bottom.gif" alt="아래로" /></a>
			<a href="#" class="btn_close" data-role="ui-droplist-btn-remove"><img src="<%=GROUP_WEB_ROOT%>/img/popup_btn_close02.gif" alt="삭제" /></a>
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