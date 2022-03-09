<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%--
 * @파일명 : step2approvalMemberTargetIframe.jsp
 * @메뉴명 : 결재라인 회원 대상 iframe 
 * @최초작성일 : 2015/01/28            
 * @author : CHOIILJI                 
 * @수정내역 : 
--%>
<%@ page import="java.util.List" %>
<%@ page import="com.hanaph.gw.co.common.utils.StringUtil" %>
<%@ page import="com.hanaph.gw.pe.member.vo.MemberVO" %>   
<%@ page import="com.hanaph.gw.ea.newReport.vo.ApprovalVO" %>
<%@ page import="com.hanaph.gw.ea.mgrDocuInfo.vo.BasicApprovalVO" %>
<%@ page import="com.hanaph.gw.ea.person.vo.PersonApprovalVO" %>
<%@ page import="com.hanaph.gw.ea.newReport.vo.ApprovalMasterVO" %>
<%@ include file ="/common/path.jsp" %>
<%
	String approval_seq = (String)request.getAttribute("approval_seq");
	String docu_seq = (String)request.getAttribute("docu_seq");
	String person_seq = (String)request.getAttribute("person_seq");
	String approval_gbn = (String)request.getAttribute("approval_gbn");
	
	//세션 회원정보
	MemberVO memberSessionVO = new MemberVO();
	memberSessionVO = (MemberVO) session.getAttribute("gwUser");
	
	//기본결재
	List<BasicApprovalVO> basicApprovalDetailList = (List<BasicApprovalVO>)request.getAttribute("approvalDetailList");
	//개인결재
	List<PersonApprovalVO> personApprovalDetailList = (List<PersonApprovalVO>)request.getAttribute("approvalDetailList");
	//저장결재
	List<ApprovalVO> approvalDetailList = (List<ApprovalVO>)request.getAttribute("approvalDetailList");
	//관리자 권한을 가진 임직원
	List<MemberVO> authorityMemberList = (List<MemberVO>)request.getAttribute("authorityMemberList");
	/* 결재마스터 */
	ApprovalMasterVO approvalMasterVO = (ApprovalMasterVO)request.getAttribute("approvalMasterVO");
	
	boolean adminAuth = false;
	if(authorityMemberList != null){
		for(int i=0; authorityMemberList.size()>i;i++){
			MemberVO memberVO = new MemberVO();
			memberVO = authorityMemberList.get(i);
			if(memberSessionVO.getEmp_no().equals(memberVO.getEmp_no())){
				adminAuth = true;
			}
		}
	}	
	
	int listSize = 0;	
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
						var checkBoxCheckValueArr = checkBoxParam.split("|");
						if(memberListCheckValueArr[0] == checkBoxCheckValueArr[0]){
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
			    	li.innerHTML = memberListCheckValueArr[1]+"<input type='hidden' name='approval_emp_no' value='"+memberListCheckValueArr[0]+"|INSERT'>";	
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
		if(approval_gbn.equals("") || approval_gbn == null){	
			if(approvalDetailList.size() > 0){
				for(int i=0; approvalDetailList.size()>i;i++){
					ApprovalVO approvalVO = new ApprovalVO();
					approvalVO = approvalDetailList.get(i);
					if(!approvalVO.getEmp_no().equals(memberSessionVO.getEmp_no()) || adminAuth){	// 본인은 결재 라인에 포함 하지 않는다.(관리자는 예외)
						if ("1".equals(approvalVO.getGroup_div())){//CHOE 201230 getGroup_div = 1인 경우만 표시 한다.
							if(!approvalVO.getState().equals("E03001")){//결재가 진행중인 라인은 수정이 안된다.
							
							listSize++;
		%>
		<li><input type="hidden" name="approval_emp_no" value="<%=approvalVO.getEmp_no()%>|SAVA"><%=approvalVO.getEmp_ko_nm() %></li>
		<%					}else{%>
		<li data-role="ui-droplist-item"><input type="hidden" name="approval_emp_no" value="<%=approvalVO.getEmp_no()%>|INSERT"><%=approvalVO.getEmp_ko_nm() %></li>
		<%					}
						}
					}else{
						listSize--;
					}
				}
			}		
		}else{
			if(approval_gbn.equals("BASIC")){
				if(basicApprovalDetailList.size() > 0){
					for(int i=0; basicApprovalDetailList.size()>i;i++){
						BasicApprovalVO basicApprovalVO = new BasicApprovalVO();
						basicApprovalVO = basicApprovalDetailList.get(i);
						if(!basicApprovalVO.getEmp_no().equals(memberSessionVO.getEmp_no()) || adminAuth){
			%>
			<li data-role="ui-droplist-item"><input type="hidden" name="approval_emp_no" value="<%=basicApprovalVO.getEmp_no()%>|INSERT" ><%=basicApprovalVO.getEmp_ko_nm() %></li>
			<%
						}
					}
				}
			}else if(approval_gbn.equals("PERSON")){	
				if(personApprovalDetailList.size() > 0){
					for(int i=0; personApprovalDetailList.size()>i;i++){
						PersonApprovalVO personApprovalVO = new PersonApprovalVO();
						personApprovalVO = personApprovalDetailList.get(i);
						if(!personApprovalVO.getEmp_no().equals(memberSessionVO.getEmp_no()) || adminAuth){
			%>
			<li data-role="ui-droplist-item"><input type="hidden" name="approval_emp_no" value="<%=personApprovalVO.getEmp_no()%>|INSERT"><%=personApprovalVO.getEmp_ko_nm() %></li>
			<%
						}
					}
				}
			}
		} %>	
		</ul>
		<div class="btn_list fr">
			<a href="#" class="btn_top" data-role="ui-droplist-btn-up"><img src="<%=GROUP_WEB_ROOT%>/img/popup_btn_top.gif" alt="위로"></a>
			<a href="#" class="btn_bottom" data-role="ui-droplist-btn-down"><img src="<%=GROUP_WEB_ROOT%>/img/popup_btn_bottom.gif" alt="아래로"></a>
			<a href="#" class="btn_close" data-role="ui-droplist-btn-remove"><img src="<%=GROUP_WEB_ROOT%>/img/popup_btn_close02.gif" alt="삭제"></a>
		</div>
	</div>
<script src="<%=GROUP_WEB_ROOT%>/js/droplist.js"></script>	
<script type="text/javascript">
<%if(approval_gbn.equals("") || approval_gbn == null){%>	

$('*[data-role=ui-droplist]').each(function () {
    var droplist = new Droplist($(this), <%=listSize+1%>);
});
	
<% 	}else{%>		

$('*[data-role=ui-droplist]').each(function () {
    var droplist = new Droplist($(this), -1);
});

<%	} %>
</script>
</body>
</html>