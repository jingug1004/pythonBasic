<%@page import="com.sun.xml.internal.ws.api.pipe.Tube"%>
<%--
 * @파일명 : step2approvalPopup.jsp
 * @메뉴명 : 결재라인 팝업
 * @최초작성일 : 2015/01/28            
 * @author : CHOIILJI                 
 * @수정내역 : 
--%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.hanaph.gw.co.common.utils.StringUtil" %>
<%@ page import="com.hanaph.gw.co.common.utils.RequestFilterUtil" %>
<%@ page import="com.hanaph.gw.ea.person.vo.PersonLineVO" %>
<%@ page import="com.hanaph.gw.co.personnel.vo.DepartmentVO" %>
<%@ page import="com.hanaph.gw.ea.newReport.vo.ApprovalVO" %>
<%@ page import="com.hanaph.gw.ea.newReport.vo.ApprovalMasterVO" %>
<%@ include file ="/common/path.jsp" %>
<%
	List<PersonLineVO> personLineList = (List<PersonLineVO>)request.getAttribute("personLineList");
	List<DepartmentVO> departmentList = (List<DepartmentVO>)request.getAttribute("departmentList");	

	//----------------------------------------------------------------------------------------------
	//CHOE 20151112 시행부서 중에 총무부-'0012' ,구매부-'0024'가 있는 경우 총괄팀장부서-'0302'를 넣어 준다. 하드코딩 전산팀장님 지시	
	ApprovalMasterVO approvalMasterVO = (ApprovalMasterVO)request.getAttribute("approvalMasterVO");
	String docu_cd = StringUtil.nvl((String)request.getAttribute("docu_cd")); //문서코드
	
	//CheckDocu_cd = "Y" 인경우 총괄팀장 부서를 넣는다. 기화기 기안서와 샘플신청서는 넣지 않는다. CheckDocu_cd = "N"
	String CheckDocu_cd = "Y";
	String CheckReq = "N";  //물품 구입 청구확인서 여부
	if ("E01004".equals(docu_cd)){	//기화기 기안서 제외
		CheckDocu_cd = "N";
	}else if("E01007".equals(docu_cd)){  //샘플신청서 제외
		CheckDocu_cd = "N";
	}else if ("E01015".equals(docu_cd)){
		CheckReq = "Y";
	}
	//----------------------------------------------------------------------------------------------
	
	String approval_seq = StringUtil.nvl((String)request.getAttribute("approval_seq"));
	String approval_seq_old = StringUtil.nvl((String)request.getAttribute("approval_seq_old"));
	String docu_seq = StringUtil.nvl((String)request.getAttribute("docu_seq"));
	String person_seq = StringUtil.nvl((String)request.getAttribute("person_seq"));
	String approval_gbn = StringUtil.nvl((String)request.getAttribute("approval_gbn"));
	String menu = StringUtil.nvl((String)request.getAttribute("menu"));	


	boolean approval_line = true;
	//결재중에는 저장된 결재 라인 이외에 결재라인을 선택 할 수 없다.
	List<ApprovalVO> approvalDetailList = (List<ApprovalVO>)request.getAttribute("approvalDetailList");
	if(approvalDetailList.size() > 0){
		for(int i=0; approvalDetailList.size()>i;i++){
			ApprovalVO approvalVO = new ApprovalVO();
			approvalVO = approvalDetailList.get(i);
			if(!approvalVO.getState().equals("E03001")){//결재가 진행중인 라인은 수정이 안된다.
				approval_line = false;
				i = approvalDetailList.size();
			}
		}
	}	
%>
<!DOCTYPE html>
<html lang="ko">
<head>
	<%@include file="/include/head.jsp"%>
	<script type="text/javascript">
	
	var subtmit = false;		//submit flag
	/**
	 * 결재라인저장하고 step3 으로 넘어간다.
	 */
	function goStep3(){
		
		var approval_emp_no = approvalMemberTarget.document.getElementsByName("approval_emp_no");
		var implDept_dept_cd = approvalDeptTarget.document.getElementsByName("implDept_dept_cd");		
		
		var approval_emp_no_count = 0;
		if(approval_emp_no.length> 0){
			for(var i=0; approval_emp_no.length> i;i++){
				var checkValueArr = approval_emp_no[i].value.split("|");
				if(checkValueArr[1] == "INSERT"){
					approval_emp_no_count++;					
				}
			}
		}
		
		if(approval_emp_no_count == 0){
			alert("결재자를 추가해 주세요.");
			return;
		}
		
		if(implDept_dept_cd.length == 0){
			alert("시행부서를 추가해 주세요.");
			return;
		}
		if(subtmit == false){
			if(confirm("저장하시겠습니까?") == true){
				//결재
				var approval;
				var addDeptFool = "N";
				if(approval_emp_no.length> 0){
					approval = new Array();
					
					for(var i=0; approval_emp_no.length> i;i++){
						var checkValueArr = approval_emp_no[i].value.split("|");
						if(checkValueArr[1] == "INSERT"){//기존에 승인중인 라인은 그대로 두고 신규 결재자만 저장한다.
							//-----------------------------------------------------------------------------------------
							//20161005 전산팀장님과 윤홍주부장 합작 하드코드 추가 조동훈 부사장이 결재에 포함되는 경우 시행부로 추가한다.
							if (checkValueArr[0] == '2006178') {
								alert("결재라인지정이 불가합니다. 시행부서로 들어갑니다.");
								addDeptFool = 'Y';								
							}else{						
							//-----------------------------------------------------------------------------------------
								if (addDeptFool == 'Y') {  //앞에 if 문에서 제외되는 경우 뒤 쪽 ordering을 수정해야 한다.
									approval.push(checkValueArr[0]+"|"+(i));
								}else{
									approval.push(checkValueArr[0]+"|"+(i+1));  //원본
								}
							}
						}					
						//----------------------------------------------------------------------------------------------						
						//CHOE 20151229 조원형2011020 안상윤2004074 윤홍주2002252 조동훈2006178 최동재2015190 5명 추가 - E01015 넘기면 ApprovlaController처리
						//물품 구입 청구확인서이면서
						if("<%=CheckReq%>" == "Y"){
							//alert("setp2approvalPopup.jsp approval_emp_no.length : "+approval_emp_no.length+" i : "+i);
							if (approval_emp_no.length == i+1){		
								//i의 값이 0부터 시작하여 +1하고 ordering 값으로 채워진다. 여기서 부서는 +2부터 채워 나간다.
								//물품 구입 청구확인서는 결재자가 7명이 넘을수 있다 -> step2approvalPopup.jsp 에서 처리한다. 
								approval.push("E010152011020"+"|"+(i+2));								
								approval.push("E010152002252"+"|"+(i+3));
								approval.push("E010152015190"+"|"+(i+4));
								//CHOE 20170111 안상윤 차장 제거
								//approval.push("E010152004074"+"|"+(i+3));								
								//CHOE 20161101 조동훈부사장님 제거
								//approval.push("E010152006178"+"|"+(i+5));
								//approval.push("E010152015190"+"|"+(i+6));								
							}
						}
						//----------------------------------------------------------------------------------------------
					}
					
					document.getElementById("approval").value = approval;
				}
				
				//시행
				var implDept;
				//CHOE 20151112 시행부서 중에 총무부-'0012' ,구매부-'0024'가 있는 경우 총괄팀장부서-'0302'를 넣어 준다. 하드코딩 전산팀장님 지시
				//addDept 는 Y-총괄팀장부서 추가, N-추가하지 않음  ,P-pass 총무부구매부 있는지 확인 하지 않음 
				var addDept = "N";
				if(implDept_dept_cd.length> 0){
					implDept = new Array();
					for(var i=0; implDept_dept_cd.length> i;i++){
						implDept.push(implDept_dept_cd[i].value+"|"+(i+1));
						
						//----------------------------------------------------------------------------------------------
						//CHOE 20151112 시행부서 중에 총무부-'0012' ,구매부-'0024'가 있는 경우 총괄팀장부서-'0302'를 넣어 준다. 하드코딩 전산팀장님 지시
						//CheckDocu_cd = "Y" 인경우 총괄팀장 부서를 넣는다. 기화기 기안서와 샘플신청서는 넣지 않는다. CheckDocu_cd = "N"
						if("<%=CheckDocu_cd%>" == "Y"){							
							//case1.총괄팀장 부서가 앞에 있는 경우 1-2를 무시하고 지나간다.
							//case2.총괄팀장 부서가 중간에 있는 경우 addDept = "P" 로 바뀐다.
							//case3.총괄팀장 부서가 끝에 있는 경우 addDept = "P" 로 바뀐다.						
							//1-1.이미 총괄팀장 부서가 있는 경우 예외처리
							if (implDept_dept_cd[i].value == "0302"){  
								addDept = "P";  								
							}
							
							if (implDept_dept_cd[i].value == "0367"){  
								addDeptFool = 'N';   //CHOE 20161005 중복입력 배제
							}
							//1-2.총무부 구매부를 동시에 넣은 경우 한번만 실행 한다.							
							if (addDept == "N"){
								if (implDept_dept_cd[i].value == "0012"){ 
									addDept = "Y";
								}else if (implDept_dept_cd[i].value == "0024"){
									addDept = "Y";
								} 
							}
						}
						//----------------------------------------------------------------------------------------------
					}
					//----------------------------------------------------------------------------------------------
					//CHOE 20151112 시행부서 중에 총무부-'0012' ,구매부-'0024'가 있는 경우 총괄팀장부서-'0302'를 넣어 준다. 하드코딩 전산팀장님 지시
					//CheckDocu_cd = "Y" 인경우 총괄팀장 부서를 넣는다. 기화기 기안서와 샘플신청서는 넣지 않는다. CheckDocu_cd = "N"					
					if (addDept == "Y"){ 
						alert("총무부 구매부를 시행부서로 넣는 경우 총괄팀장을 꼭 시행부서로 추가해야 합니다.");
						implDept.push('0302'+"|"+(i+1));
					}
					//----------------------------------------------------------------------------------------------
					//20161005 전산팀장님과 윤홍주부장 합작 하드코드 추가 조동훈 부사장이 결재에 포함되는 경우 시행부로 추가한다.
					if (addDeptFool == "Y"){
						implDept.push('0367'+"|"+(i+1));
					}
					//----------------------------------------------------------------------------------------------
					document.getElementById("implDept").value = implDept;				
				}	
				
				document.getElementById("frm_approval").action = "<%=GROUP_ROOT %>/ea/newReport/step2approvalInsert.do";
				document.getElementById("frm_approval").submit();
				subtmit=true;
			}
		}
	}
	
	/**
	 * 현결재라인 저장시 결제라인명 저장 레이어팝업
	 */
	 
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
		
		if(confirm("현재결재라인을 저장하시겠습니까?") == true){
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
	
	/**
	 * 레이어 팝업 닫는다.
	 */
	function layerClose(){ 
		$('#now_approval').bPopup().close();
	}
	
	/**
	 * 현재 결재라인 저장한다.
	 */
	function goNowApprovalSave(obj){
		//결재
		var approval_emp_no = approvalMemberTarget.document.getElementsByName("approval_emp_no");
		var approval;
		if(approval_emp_no.length> 0){
			approval = new Array();
			for(var i=0; approval_emp_no.length> i;i++){
				var checkValueArr = approval_emp_no[i].value.split("|");
				approval.push(checkValueArr[0]+"|"+(i+1));
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
		document.getElementById("frm_approval").action = "<%=GROUP_ROOT %>/ea/person/personApprovalInsert.do";
		document.getElementById("frm_approval").submit();
	}

	/**
	 * 저장된 결제라인중 선택된 라인 보여준다.
	 */
	function goApprovalLine(obj){
		var selectValue = obj.value;
		if(selectValue !=""){
			var selectValueArr = selectValue.split("|");
			document.getElementById("approval_gbn").value = selectValueArr[0];
			if(selectValueArr[0] == "PERSON"){
				document.getElementById("person_seq").value = selectValueArr[1];
			}
		}
		document.getElementById("frm_approval").action = "<%=GROUP_ROOT %>/ea/newReport/step2approvalPopup.do";
		document.getElementById("frm_approval").submit();
	}
	
	/**
	 * Step1 으로 이동한다.
	 */
	function goStep1(){
		document.getElementById("frm_approval").action = "<%=GROUP_ROOT%>/ea/newReport/step1approvalPopup.do";
		document.getElementById("frm_approval").submit();
	}
	
	/**
	 * 결재문서 삭제 한다.
	 */
	function deleteApprpval(){
		if(confirm("삭제하시겠습니까?") == true){
			document.getElementById("frm_approval").action = "<%=GROUP_ROOT%>/ea/newReport/deleteApprovalDocument.do";
			document.getElementById("frm_approval").submit();
		}
	}
	</script>	
</head>
<body>

<div class="wrap_popup_menu">
	<div class="popup_menu">
		<h3>결재라인지정<span> STEP2</span></h3>
		<form name="frm_iframe" id="frm_iframe" method="post" >
			<input type ="hidden" id="dept_cd" name="dept_cd">
			<input type ="hidden" id="up_dept_gbn" name="up_dept_gbn">
		</form>	
		
		<div class="top_btn_area">
			<%if(!"020501".equals(menu)){ //관리자는 결재 라인만 수정이 가능하다. 삭제 할 수 없다.%>
			<button class="type2" onclick="javascript:deleteApprpval(); return false;">삭제</button>
			<%} %>
			<div class="fr">
				<%if(!"020501".equals(menu)){ //관리자는 결재 라인만 수정이 가능하다. 이전 으로 갈 수 없다.%>
				<button class="type2" onclick="javascript:goStep1(); return false;">이전</button>
				<%} %>
				<button class="type2" onclick="javascript:goStep3(); return false;">결재라인적용</button>
			</div>
		</div>
		<div class="popup_list">
			<div class="settl_txt">
				<p class="tit"><em>STEP 2</em>. 결재라인지정 </p>
				<p>해당 문서의 결재라인을 지정하고 완료가 되면 결재라인 적용 버튼을 클릭하시기 바랍니다.  </p>
				<p>STEP3으로 이동합니다.</p>
			</div>
			<div class="fl">
				<form name="frm_approval" id="frm_approval" method="post">
					<input type ="hidden" id="approval" name="approval">
					<input type ="hidden" id="implDept" name="implDept">
					<input type ="hidden" id="docu_seq" name="docu_seq" value="<%=docu_seq%>">
					<input type ="hidden" id="approval_seq" name="approval_seq" value="<%=approval_seq%>">
					<input type ="hidden" id="approval_seq_old" name="approval_seq_old" value="<%=approval_seq_old%>">
					<input type ="hidden" id="person_seq" name="person_seq">
					<input type ="hidden" id="approval_gbn" name="approval_gbn">
					<input type ="hidden" id="subject" name="subject">
					<input type ="hidden" id="menu" name="menu" value="<%=menu %>">
				<div class="search_box01">
					<span>결재라인</span>
					<select id="approval_line" onchange="javascript:goApprovalLine(this);">
						<option value="" <%if(approval_gbn.equals("")){ %>selected<%} %>>선택하세요</option>
						<%if(approval_line){ %>
						<option value="BASIC|<%=docu_seq%>" <%if((approval_gbn+"|"+docu_seq).equals("BASIC|"+docu_seq)){ %>selected<%} %>>[기본]기본결재라인</option>
						<%
							if(personLineList.size() !=0){ 
								for(int i=0; personLineList.size()>i;i++){
									PersonLineVO personLineVO = new PersonLineVO();
									personLineVO = personLineList.get(i);
							%>
						<option value="PERSON|<%=personLineVO.getPerson_seq()%>" <%if((approval_gbn+"|"+personLineVO.getPerson_seq()).equals("PERSON|"+person_seq)){ %>selected<%} %>>[개인]<%=RequestFilterUtil.toHtmlTagReplace("", personLineVO.getSubject()) %></option>
						<%		}
							}%>
						<%} %>
					</select>
				</div>
				</form>
				<div class="cont_box04 pst_rel">
					<div class="wrap_tree_btn pst_abs" style="width:92px; margin:10px auto">
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
							document.getElementById("frm_iframe").action="<%=GROUP_ROOT %>/ea/newReport/step2approvalMemberIframe.do";
							document.getElementById("frm_iframe").submit();
							goDept(dept_cd);
						}	
						
						function goDept(dept_cd){
							document.getElementById("frm_iframe").target= "approvalDeptList";
							document.getElementById("dept_cd").value = dept_cd;
							document.getElementById("frm_iframe").action="<%=GROUP_ROOT %>/ea/newReport/step2approvalDeptIframe.do";
							document.getElementById("frm_iframe").submit();
						}
					</script>
				</div>		
			</div>
			<div class="fr">
				<div class="wrap_settle_cont">
					<div class="settle_cont01 fl posa">
						<iframe id="approvalMemberList" name="approvalMemberList" src="<%=GROUP_ROOT %>/ea/newReport/step2approvalMemberIframe.do" frameborder="0" border="0" scrolling="no" width="432px" height="265px"></iframe>
					</div>	
					<div class="settle_cont02 fr mt52 h212">
						<iframe id="approvalMemberTarget" name="approvalMemberTarget" src="<%=GROUP_ROOT %>/ea/newReport/step2approvalMemberTargetIframe.do?docu_seq=<%=docu_seq%>&approval_gbn=<%=approval_gbn %>&person_seq=<%=person_seq %>&approval_seq=<%=approval_seq %>&approval_seq_old=<%=approval_seq_old %>" frameborder="0" border="0" scrolling="no" width="152px" height="265px"></iframe>
					</div>	
				</div>

				<div class="wrap_settle_cont mt20">
					<div class="settle_cont01 fl">
						<iframe id="approvalDeptList" name="approvalDeptList" src="<%=GROUP_ROOT %>/ea/newReport/step2approvalDeptIframe.do" frameborder="0" border="0" scrolling="no" width="280px" height="212px"></iframe>
					</div>
					<div class="settle_cont02 fr">
						<iframe id="approvalDeptTarget" name="approvalDeptTarget" src="<%=GROUP_ROOT %>/ea/newReport/step2approvalDeptTargetIframe.do?docu_seq=<%=docu_seq%>&approval_gbn=<%=approval_gbn %>&person_seq=<%=person_seq %>&approval_seq=<%=approval_seq %>&approval_seq_old=<%=approval_seq_old %>" frameborder="0" border="0" scrolling="no" width="152px" height="212px"></iframe>
					</div>
				</div>	
				<%if(!"020501".equals(menu)){ //관리자는 결재 라인만 수정이 가능하다. 이전 으로 갈 수 없다.%>
				<div class="list_btn last_step">
					<div class="list_button">
						<button type="button" class="type_01" onclick="javascript:openPersonLine(); return false;">현재결재라인저장</button>
					</div>
				</div>
				<%} %>
			</div>
		</div>
		<button class="close" type="button" onclick="javascript:window.close();"><span class="blind">닫기</span></button>
	</div>
</div>
<!-- 현결재라인저장 iframe -->
<div id='now_approval' style='display:none; width:auto; height:auto; '>
	<div class='now_approval_content'></div> 
</div>
</body>
</html>