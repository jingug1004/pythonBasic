<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%--
 * @파일명 : addApprovalImplDeptTargetIframe.jsp
 * @메뉴명 : 시행부서 추가 - 시행부서 대상 iframe
 * @최초작성일 : 2015/01/28            
 * @author : CHOIILJI                 
 * @수정내역 : 
--%>
<%@ page import="java.util.List" %>
<%@ page import="com.hanaph.gw.co.common.utils.StringUtil" %>
<%@ page import="com.hanaph.gw.ea.mgrDocuInfo.vo.BasicImplDeptVO" %>
<%@ page import="com.hanaph.gw.ea.newReport.vo.ImplDeptVO" %>
<%@ page import="com.hanaph.gw.ea.person.vo.PersonImplDeptVO" %>
<%@ include file ="/common/path.jsp" %>
<%
	String approval_seq = (String)request.getAttribute("approval_seq");
	String docu_seq = (String)request.getAttribute("docu_seq");

	//시행
	List<ImplDeptVO> implDeptDetailList = (List<ImplDeptVO>)request.getAttribute("implDeptDetailList");
	
	int listSize = 0;
	if(implDeptDetailList!=null){
		listSize = implDeptDetailList.size();
	}
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
		
		    var approvalListCheckBoxArr = parent.approvalDeptList.document.getElementsByName("dept_cd");
		    var approvalListCheckBoxCnt = parent.approvalDeptList.document.getElementsByName("dept_cd").length;
		    
		    var checkBoxParam = "";
		    var memberListCheckBoxParam = "";
		    
		    for(var i=0; i<approvalListCheckBoxCnt; i++){
		    	if(approvalListCheckBoxArr[i].checked){
			    	memberListCheckBoxParam = approvalListCheckBoxArr[i].value;
			    	var memberListCheckValueArr = memberListCheckBoxParam.split("|");
					for(var j=0; j<checkBoxCnt; j++){
						checkBoxParam = checkBoxArr[j].value;
						var checkBoxCheckValueArr = checkBoxParam.split("|");
						if(memberListCheckValueArr[0] == checkBoxCheckValueArr[0]){
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
			    	li.innerHTML=memberListCheckValueArr[1]+"<input type='hidden' name='implDept_dept_cd' value='"+memberListCheckValueArr[0]+"|INSERT'>";
			    }
		    } 
		    parent.approvalDeptList.allUnCheck();
		}
		
	</script>
</head>
<body>
	
	<div class="settle_men fr w182" data-role="ui-droplist" data-type="basic">
		<h4>시행부서</h4>
		<ul id="implDept_ul" class="select_list01 fl exeList w141" data-role="ui-droplist-list">
		<input type ="hidden" id="implDept_dept_cd_cnt" name="implDept_dept_cd_cnt" value="<%=implDeptDetailList.size()%>">
		<%
			if(implDeptDetailList!=null){
				for(int i=0; implDeptDetailList.size()>i;i++){
					ImplDeptVO implDeptVO = new ImplDeptVO();
					implDeptVO = implDeptDetailList.get(i);
		%>	
			<li><%=implDeptVO.getDept_ko_nm() %><input type="hidden" name="implDept_dept_cd" value="<%=implDeptVO.getDept_cd()%>|SAVA"></li>
		<%
				}
			}
		%>
		</ul>
		<div class="btn_list01 fr">
			<a href="#" class="btn_top" data-role="ui-droplist-btn-up"><img src="<%=GROUP_WEB_ROOT%>/img/popup_btn_top.gif" alt="위로" /></a>
			<a href="#" class="btn_bottom" data-role="ui-droplist-btn-down"><img src="<%=GROUP_WEB_ROOT%>/img/popup_btn_bottom.gif" alt="아래로" /></a>
			<a href="#" class="btn_close" data-role="ui-droplist-btn-remove"><img src="<%=GROUP_WEB_ROOT%>/img/popup_btn_close02.gif" alt="삭제" /></a>
		</div>
		<div class="list_btn fr">
			<div class="list_button mt5 w265">
				<button class="type_01 btn_coop" onclick="parent.parent.layerClose();">취소</button>
				<button class="type_01 btn_exe" onclick="javascript:parent.goApprovalImplDept(); return false;">시행부서 적용</button>
			</div>	
		</div>
	</div>

<script src="<%=GROUP_WEB_ROOT%>/js/droplist.js"></script>
<script type="text/javascript">
/**
 * 기존 데이터는 수정이 안되야함. droplist.js 를 변형해서 사용함. 
 *
 */
$('*[data-role=ui-droplist]').each(function () {
    var droplist = new Droplist($(this), <%=listSize+1%>);
});
</script>
</body>
</html>