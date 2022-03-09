<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%--
 * @파일명 : approvalPopup.jsp
 * @메뉴명 : 문서 상세조회 팝업
 * @최초작성일 : 2015/01/28            
 * @author : CHOIILJI                 
 * @수정내역 : 
--%>
<%@ page import="java.util.List" %>
<%@ page import="com.hanaph.gw.co.common.utils.StringUtil" %>
<%@ page import="com.hanaph.gw.co.common.utils.RequestFilterUtil" %>
<%@ include file ="/common/path.jsp" %>
<%@ page import="com.hanaph.gw.co.fileAttach.vo.FileAttachVO" %>
<%@ page import="com.hanaph.gw.ea.mgrDocuInfo.vo.DocumentInfoVO" %>
<%@ page import="com.hanaph.gw.ea.newReport.vo.ApprovalMasterVO" %>
<%@ page import="com.hanaph.gw.ea.newReport.vo.ApprovalVO" %>
<%@ page import="com.hanaph.gw.ea.newReport.vo.AccidentVO" %>
<%@ page import="com.hanaph.gw.ea.newReport.vo.ImplDeptVO" %>
<%@ page import="com.hanaph.gw.ea.mgrDocuInfo.vo.DocumentInfoVO" %>
<%@ page import="com.hanaph.gw.ea.newReport.vo.OpinionVO" %>

<%
	/* 세션정보 */
	MemberVO memberSessionVO = new MemberVO();
	memberSessionVO = (MemberVO) session.getAttribute("gwUser");

	List<FileAttachVO> attachList = (List<FileAttachVO>)request.getAttribute("attachList");
	
	/*내 결재 차례*/
	ApprovalVO approvalNowEmpNo = (ApprovalVO)request.getAttribute("approvalNowEmpNo");
 	if(approvalNowEmpNo == null){
 		approvalNowEmpNo = new ApprovalVO();
	}
	
	/* 결재마스터 */
	ApprovalMasterVO approvalMasterVO = (ApprovalMasterVO)request.getAttribute("approvalMasterVO");
	if(approvalMasterVO == null){
		approvalMasterVO = new ApprovalMasterVO();
	}
	
	/* 의견리스트 */
	List<OpinionVO> opinionList = (List<OpinionVO>)request.getAttribute("opinionList");
	
	/* 결재 */
	List<ApprovalVO> approvalDetailList = (List<ApprovalVO>)request.getAttribute("approvalDetailList");
	/* 시행부서 */
	List<ImplDeptVO> implDeptDetailList = (List<ImplDeptVO>)request.getAttribute("implDeptDetailList");
	/* 문서기본정보 */
	DocumentInfoVO documentInfoDetail = (DocumentInfoVO)request.getAttribute("documentInfoDetail");
	/*수정,삭제 권한을 가진 임직원*/
	List<MemberVO> authorityMemberList = (List<MemberVO>)request.getAttribute("authorityMemberList");
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
	String state = StringUtil.nvl(approvalMasterVO.getState());
	String docu_cd = StringUtil.nvl((String)request.getAttribute("docu_cd"));	//문서코드
	String approval_seq = StringUtil.nvl((String)request.getAttribute("approval_seq"));	//결재 문서번호	
	String includePage = "step3"+docu_cd+".jsp";	//신규문서 파일명
	String menu = StringUtil.nvl((String)request.getAttribute("menu"));	//메뉴코드
	String docu_seq = StringUtil.nvl((String)request.getAttribute("docu_seq"));	//문서번호
	String docu_state = StringUtil.nvl((String)request.getAttribute("docu_state")); //결재문서상태(기결,미결,반려)
	String readYnCnt = StringUtil.nvl((String)request.getAttribute("readYnCnt")); //의견등록,삭제 성공값

	/*시행문서 이고 결재 완료 이고 시간외 근무 내역서일 경우 START*/
	boolean overTimeChk = (Boolean)request.getAttribute("overTimeChk");
	String overTimeDetailDecu_seq = StringUtil.nvl((String)request.getAttribute("overTimeDetailDecu_seq"));	//메뉴코드
	/*시행문서 이고 결재 완료 이고 시간외 근무 내역서일 경우 END*/

	/*ml180126.ml01_T81 김진국 - approvalPopup.jsp에 	String stateMustOpinion = StringUtil.nvl(approvalMasterVO.getMustopinion()); 추가 - '시의필' 상태에 따라 결재 제목 옆에 '시의필' 문서인지 보여주기 위해서! */
	String stateMustOpinion = StringUtil.nvl(approvalMasterVO.getMustopinion());

	/*사고보고서  보고 날짜 셋팅*/
	AccidentVO accidentVO = (AccidentVO)request.getAttribute("accidentVO");
	if(accidentVO == null){
		accidentVO = new AccidentVO();
	}
	
	String bogo_day = StringUtil.nvl(accidentVO.getBogo_day());		//보고일
	String bogo_month = StringUtil.nvl(accidentVO.getBogo_month()); //보고월
	String bogo_year = StringUtil.nvl(accidentVO.getBogo_year());	//보고년
	
	String[] last_line_nm = new String[2]; //마지막결재자 직원이름^직원번호
	if(!"".equals(StringUtil.nvl(approvalMasterVO.getLast_line_nm()))){
		last_line_nm = approvalMasterVO.getLast_line_nm().split("\\^");
	}
	
	//시행완료 여부
	boolean implementtCompleteChk = (Boolean)request.getAttribute("implementtCompleteChk");
%>
<!DOCTYPE html>
<html lang="ko">
<head>
	<%@include file="/include/head.jsp"%>
	<script type="text/javascript">
	
	$(document).ready(function () {
		<%if("020701".equals(menu) || "020702".equals(menu) || "0206".equals(menu)){%>
		window.opener.location.reload();
		<%}%>
        window.opener.document.location.reload();
    });
	
	readYnCnt = <%=readYnCnt%>; //의견 읽은 여부
	
	/**
	 * 결재문서 수정
	 */
	function updateApprpval(){
		document.getElementById("frm").action = "<%=GROUP_ROOT%>/ea/newReport/step1approvalPopup.do";
		document.getElementById("frm").submit();
	}
	/**
	 * 결재문서 삭제
	 */
	function deleteApprpval(){
		if(confirm("삭제하시겠습니까?") == true){
			document.getElementById("frm").action = "<%=GROUP_ROOT%>/ea/newReport/deleteApprovalDocument.do";
			document.getElementById("frm").submit();
		}
	}
	
	/**
	 * 관리자 결재라인만 수정
	 */
	function updateApprpvalStep2(){
		if(confirm("수정하시겠습니까?") == true){
			document.getElementById("frm").action = "<%=GROUP_ROOT%>/ea/newReport/step2approvalPopup.do";
			document.getElementById("frm").submit();
		}
	}
	
	/**
	 * 결재문서 미리보기
	 */
	function previewApprpval(){
		<% if(docu_cd.equals("E01015")){ %>
		Commons.popupOpen('previewPop','<%=GROUP_ROOT%>/ea/newReport/approvalPreviewPopup.do?approval_seq=<%=approval_seq%>','1200','820');
		<% }else{ %>
		Commons.popupOpen('previewPop','<%=GROUP_ROOT%>/ea/newReport/approvalPreviewPopup.do?approval_seq=<%=approval_seq%>','820','850');
		<% }%>
	}
	
	/**
	 * 공유대상선택
	 */
	function addShare(){
		//document.frm.auth_seq.value = auth_seq;
		$('#layerOpen').bPopup({
			content:'iframe', //'ajax', 'iframe' or 'image'
			iframeAttr:'scrolling="no" frameborder="0" width="600px" height="707px"',
			follow: [true, true],
			contentContainer:'.layer_content',
			modalClose: true,
            opacity: 0.6,
            positionStyle: 'fixed',
			loadUrl:'<%=GROUP_ROOT%>/pe/member/memberSelectPopup.do?seq=<%=approval_seq%>&type=SHARE',
		});
	}
	
	/**
	 * 공유대상 저장
	 * @param obj
	 */
	function addMemberRow(obj){
		var emp_no = new Array();
		 for(var i=0; i<obj.length; i++){
	    	if(obj[i].checked){
		    	var objParam = obj[i].value;
				var memberListValueArr = objParam.split("|");
				emp_no.push(memberListValueArr[0]);
		    }
	    } 
		document.getElementById("emp_no").value = emp_no;
		document.getElementById("frm").action = "<%=GROUP_ROOT %>/ea/share/insertShareTarget.do";
		document.getElementById("frm").submit();
	}
	/**
	 * 시행부서 의견 필수 추가 ml180116.ml01 김진국 - approvalPopup.jsp 에 addMustOpinion 펑션 추가 - '시행부서 의견 필수' 버튼 눌렀을 때 펑션 실행하기 위해서
     */
	function addMustOpinion(){
		$.ajax({
			type: "POST",
			url: "<%=GROUP_ROOT%>/ea/newReport/addMustOpinion.do?approval_seq=<%=approval_seq%>",
			async: false,
			success: function () {
                alert("시행부서 의견 필수로 전환되었습니다.\n\n시행부서의 의견들이 필수적으로 작성되어야 다음 결재가 가능합니다.\n\n바로 전 결재자의 결재승인(기결)은 취소됩니다.");
                updateApprovalMustOpinion(5); /* ml180117.ml01_T12 김진국 - approvalPopup.jsp에 updateApproval(gubun)승인취소 펑션을 호출 - 승인한 본인의 결재를 취소하기 위해서 - 바로 전 결재자도 취소해야 한다. */
                window.opener.document.location.reload();
            },
			error : function () {
				alert("error");
            }

		});
    }
	/**
	 * 시행부서 추가
	 */
	function addImplDept(){
		//Commons.popupOpen('implDeptPop','<%=GROUP_ROOT%>/ea/newReport/addApprovalImplDept.do?approval_seq=<%=approval_seq%>','570','375');
		$('#layerOpen').bPopup({
			content:'iframe', //'ajax', 'iframe' or 'image'
			iframeAttr:'scrolling="no" frameborder="0" width="570px" height="375px"',
			follow: [true, true],
			contentContainer:'.layer_content',
			modalClose: true,
            opacity: 0.6,
            positionStyle: 'fixed',
			loadUrl:'<%=GROUP_ROOT%>/ea/newReport/addApprovalImplDept.do?approval_seq=<%=approval_seq%>'
		});
	}

	/**
	 * 시간외 근무 내역서 등록
	 */
	function addOverTimeDetail(){
		//Commons.popupOpen('','<%=GROUP_ROOT%>/ea/newReport/step1approvalPopup.do?approval_seq_old=<%=approval_seq%>&docu_seq=<%=overTimeDetailDecu_seq%>','820','850');
		location.href = "<%=GROUP_ROOT%>/ea/newReport/step1approvalPopup.do?approval_seq_old=<%=approval_seq%>&docu_seq=<%=overTimeDetailDecu_seq%>";
	}
	
	/**
	 * 의견 추가
	 */
	function addOpinionPopup(){
		readYnCnt = 1;
		$('#layerOpen').bPopup({
			content:'iframe', //'ajax', 'iframe' or 'image'
			/*CHOE 20151204 사이즈 변경*/
			/*iframeAttr:'scrolling="no" frameborder="0" width="800px" height="514px"',*/
			iframeAttr:'scrolling="no" frameborder="0" width="800px" height="714px"',
			follow: [true, true],
			contentContainer:'.layer_content',
			modalClose: false,
            opacity: 0.6,
            positionStyle: 'fixed',
			loadUrl:'<%=GROUP_ROOT%>/ea/newReport/opinionList.do?approval_seq=<%=approval_seq%>'
		});
	}
	
	/**
	 * 레이어팝업 닫기	
	 */
	function layerClose(){
		if(opinionReload()){
			window.location.href = "<%=GROUP_ROOT%>/ea/newReport/approvalPopup.do?docu_state=<%=docu_state%>&approval_seq=<%=approval_seq%>&menu=<%=menu%>&readYnCnt=1";
		}
		
		$('#layerOpen').bPopup().close();
	}
	
	/**
	 * 의견이 등록,삭제 되면 true를 반환 한다.
	 */
	function opinionReload(){
		return true;
	}

	/**
	 * 결재이력 팝업
	 */
	function approvalHistoryPopup(){
		$('#layerOpen').bPopup({
			content:'iframe', //'ajax', 'iframe' or 'image'
			iframeAttr:'scrolling="no" frameborder="0" width="800px" height="504px"',
			follow: [true, true],
			contentContainer:'.layer_content',
			modalClose: true,
            opacity: 0.6,
            positionStyle: 'fixed',
			loadUrl:'<%=GROUP_ROOT%>/ea/newReport/approvalHistoryList.do?approval_seq=<%=approval_seq%>'
		});
	}
	
	/**
	 * 승인, 반려, 전결, 취소 레이어 팝업
	 */
	function approvalPopup(gubun){
		if(gubun == 1 && 0 != <%=opinionList.size()%>){	//승인시
			if(readYnCnt == 0){ //의견창 띄움 체크
				alert("의견이 있습니다. 의견을 확인하십시요.");
				return;
			}
		}
		if(gubun == 2){
			$('#layerOpen').bPopup({
				content:'iframe', //'ajax', 'iframe' or 'image'
				iframeAttr:'scrolling="no" frameborder="0" width="330px" height="400px"',
				follow: [true, true],
				contentContainer:'.layer_content',
				modalClose: true,
	            opacity: 0.6,
	            positionStyle: 'fixed',
				loadUrl:'<%=GROUP_ROOT%>/ea/receive/approvalPopup.do?gubun='+gubun
			});
		}else{
			$('#layerOpen').bPopup({
				content:'iframe', //'ajax', 'iframe' or 'image'
				iframeAttr:'scrolling="no" frameborder="0" width="330px" height="220px"',
				follow: [true, true],
				contentContainer:'.layer_content',
				modalClose: true,
	            opacity: 0.6,
	            positionStyle: 'fixed',
				loadUrl:'<%=GROUP_ROOT%>/ea/receive/approvalPopup.do?gubun='+gubun
			});
		}
	}
	
	
	/**
	 * 승인, 반려, 전결, 취소
	 */
	function updateApproval(gubun){
		
		var result = "true";
		
		/*결재 승인,결재 반려 할때 이미 결재,반려된 상태인지 체크*/
		if("1" == gubun || "2" == gubun){
			$.ajax({
				type:"POST",
				url:"<%=GROUP_ROOT%>/ea/receive/approvalNextCheckEmpNo.do",
				async : false,
				dataType:"json",
				data:{
					approval_seq:"<%=approval_seq%>"
					},
				success:function(data){
					if(data.result!=1){
						alert("이미 이전 결재자가 승인 취소하여 결재 또는 반려 할 수 없습니다.");
						result = "false";
						layerClose();
						window.opener.document.location.reload();
						window.self.close();
					}
				},
				error : function(xhr, status, error) {
					alert("결재 체크 ERROR");
					console.log(error);
				}
			});
		/*승인 취소,반려 취소 할때 이미 취소된 상태인지 체크*/
		}else if("4" == gubun || "5" == gubun){
			$.ajax({
				type:"POST",
				url:"<%=GROUP_ROOT%>/ea/receive/approvalPrevCheckEmpNo.do",
				async : false,
				dataType:"json",
				data:{
					approval_seq:"<%=approval_seq%>"
					},
				success:function(data){
					if(data.result!=1){
						alert("이미 이전 결재자가 승인 하여 승인취소 또는 반려취소 할 수 없습니다.");
						result = "false";
						layerClose();
						window.opener.document.location.reload();
						window.self.close();
					}
				},
				error : function(xhr, status, error) {
					alert("결재 체크 ERROR");
					console.log(error);
				}
			});
		}
		
		/*승인*/
		if("1" == gubun && "true" == result){
			<%
			if(approvalDetailList.size() > 0){
				for(int i=0; approvalDetailList.size()>i;i++){
					ApprovalVO checkApprovalVO = new ApprovalVO();
					checkApprovalVO = approvalDetailList.get(i);
					if(checkApprovalVO.getEmp_no().equals(memberSessionVO.getEmp_no())){
						if(approvalDetailList.size()==checkApprovalVO.getOrdering()){	//마지막 결재자인 경우
						%>
							document.getElementById("masterState").value = "E02004";	//완료
						<%
						}else{
						%>
							document.getElementById("masterState").value = "E02003";	//진행중
						<%
						}
					}
				}
			}
			%>
			document.getElementById("state").value = "E03002";			//기결
			document.getElementById("frm").action = "<%=GROUP_ROOT%>/ea/receive/updateApproval.do?gubun="+gubun;
			document.getElementById("frm").submit();
			
		/*반려*/
		}else if("2" == gubun && "true" == result){
			document.getElementById("masterState").value = "E02005";	//반려
			document.getElementById("state").value = "E03003";			//반려
			document.getElementById("frm").action = "<%=GROUP_ROOT%>/ea/receive/updateApproval.do?gubun="+gubun;
			document.getElementById("frm").submit();
		/*전결*/
		}else if("3" == gubun){
			document.getElementById("masterState").value = "E02004";	//완료
			document.getElementById("state").value = "E03004";			//전결
			document.getElementById("frm").action = "<%=GROUP_ROOT%>/ea/receive/updateApproval.do?gubun="+gubun;
			document.getElementById("frm").submit();
		/*반려, 승인, 전결 취소*/
		}else if(("4" == gubun &&  "true" == result) || ("5" == gubun && "true" == result) || "6" == gubun){
			<%
			if(approvalDetailList.size() > 0){
				for(int i=0; approvalDetailList.size()>i;i++){
					ApprovalVO checkApprovalVO = new ApprovalVO();
					checkApprovalVO = approvalDetailList.get(i);
					if(checkApprovalVO.getEmp_no().equals(memberSessionVO.getEmp_no())){
						if(memberSessionVO.getEmp_no().equals(last_line_nm[0]) && 1 == checkApprovalVO.getOrdering()){	//마지막결재자가 결재순서 처음인 경우
						%>
							document.getElementById("masterState").value = "E02002";	//요청중
				            window.opener.document.location.reload();
						<%
						}else{
						%>
							document.getElementById("masterState").value = "E02003";	//진행중
				            window.opener.document.location.reload();
						<%
					  }
				  }
			  }
			}
			%>
			document.getElementById("state").value = "E03001";			//미결
			document.getElementById("frm").action = "<%=GROUP_ROOT%>/ea/receive/updateApproval.do?gubun="+gubun;
			document.getElementById("frm").submit();
		}
	}

	//	ml180117.ml02_T13 김진국 - approvalPopup.jsp에 function updateApprovalMustOpinion(gubun)승인취소 펑션을 function updateApproval(gubun) 복사 및 수정 - 승인한 본인의 결재를 취소하기 위해서 - 바로 전 결재자도 취소해야 한다.
    function updateApprovalMustOpinion(gubun){

		var result = "true";

		/*승인*/
		if("1" == gubun && "true" == result){
			<%
			if(approvalDetailList.size() > 0){
				for(int i=0; approvalDetailList.size()>i;i++){
					ApprovalVO checkApprovalVO = new ApprovalVO();
					checkApprovalVO = approvalDetailList.get(i);
					if(checkApprovalVO.getEmp_no().equals(memberSessionVO.getEmp_no())){
						if(approvalDetailList.size()==checkApprovalVO.getOrdering()){	//마지막 결재자인 경우
						%>
							document.getElementById("masterState").value = "E02004";	//완료
						<%
						}else{
						%>
							document.getElementById("masterState").value = "E02003";	//진행중
						<%
						}
					}
				}
			}
			%>
			document.getElementById("state").value = "E03002";			//기결
			document.getElementById("frm").action = "<%=GROUP_ROOT%>/ea/receive/updateApproval.do?gubun="+gubun;
			document.getElementById("frm").submit();

		/*반려*/
		}else if("2" == gubun && "true" == result){
			document.getElementById("masterState").value = "E02005";	//반려
			document.getElementById("state").value = "E03003";			//반려
			document.getElementById("frm").action = "<%=GROUP_ROOT%>/ea/receive/updateApproval.do?gubun="+gubun;
			document.getElementById("frm").submit();
		/*전결*/
		}else if("3" == gubun){
			document.getElementById("masterState").value = "E02004";	//완료
			document.getElementById("state").value = "E03004";			//전결
			document.getElementById("frm").action = "<%=GROUP_ROOT%>/ea/receive/updateApproval.do?gubun="+gubun;
			document.getElementById("frm").submit();
		/*반려, 승인, 전결 취소*/
		}else if(("4" == gubun &&  "true" == result) || ("5" == gubun && "true" == result) || "6" == gubun){
			<%
			if(approvalDetailList.size() > 0){
				for(int i=0; approvalDetailList.size()>i;i++){
					ApprovalVO checkApprovalVO = new ApprovalVO();
					checkApprovalVO = approvalDetailList.get(i);
					if(checkApprovalVO.getEmp_no().equals(memberSessionVO.getEmp_no())){
						if(memberSessionVO.getEmp_no().equals(last_line_nm[0]) && 1 == checkApprovalVO.getOrdering()){	//마지막결재자가 결재순서 처음인 경우
						%>
						document.getElementById("masterState").value = "E02002";	//요청중
			            window.opener.document.location.reload();
						<%
						}else{
						%>
						document.getElementById("masterState").value = "E02003";	//진행중

						// ml180117.ml03_T14 김진국 - approvalPopup.jsp에 function updateApprovalMustOpinion(gubun)승인취소 펑션에 바로 전결재자까지 결재 승인 취소 - 결재 승인한 사람이든 결재 승인 안 한 사람이든 본인과 전 결재승인자가 있으면 모두 승인취소 해야한다.
			            // 마지막결재자가 결재순서 처음이 아닌 경우
          				document.getElementById("state").value = "E03001";			//미결
						document.getElementById("frm").action = "<%=GROUP_ROOT%>/ea/receive/updateApprovalMustOpinion.do?gubun="+gubun;
						document.getElementById("frm").submit();
						window.opener.document.location.reload();
			            // 마지막결재자가 결재순서 처음이 아닌 경우
						<%
						}
					}
				}
			}
			%>
		}
	}

	/**
	 * 요청취소
	 */
	function approvalCompleteReject(){
		document.getElementById("frm").action = "<%=GROUP_ROOT%>/ea/newReport/approvalCompleteReject.do";
		document.getElementById("frm").submit();
	}
	
	
	/**
	 * 시행완료 등록 폼
	 */
	function addImplementtComplete(){
		$('#layerOpen').bPopup({
			content:'iframe', //'ajax', 'iframe' or 'image'
			iframeAttr:'scrolling="no" frameborder="0" width="330px" height="210px"',
			follow: [true, true],
			contentContainer:'.layer_content',
			modalClose: true,
            opacity: 0.6,
            positionStyle: 'fixed',
			loadUrl:'<%=GROUP_ROOT%>/ea/newReport/addImplCompleteFormPopup.do?approval_seq=<%=approval_seq%>'
		});
	}
	</script>
</head>
<body>

<div class="wrap_pop_bg">
	<div class="wrap_statement_overtime">
		<div class="top">
			<%--<h3><%=approvalMasterVO.getReq_dt() %><span> <%=RequestFilterUtil.toHtmlTagReplace("",approvalMasterVO.getSubject()) %></span></h3>--%>
			<h3><%=approvalMasterVO.getReq_dt() %>
				<span> <%=RequestFilterUtil.toHtmlTagReplace("",approvalMasterVO.getSubject()) %></span>
				<% if (stateMustOpinion.equals("Y"))  { %>
				<span style="font-size: 16px; font-family: 'NanumGothic'; color: #cd0a0a; "> '시행부서 의견 필수' 문서  </span>
				<% } %>
			</h3>
			<button type="button" class="close" onclick="javascript:window.close();"><span class="blind">닫기</span></button>
			<div>
				<%if(!docu_cd.equals("E01010") && adminAuth && "020501".equals(menu)){//(!증명서 발급 신청서 && 관리자 && 문서목록)증명서 발급 신청서는 인쇄 페이지가 없음%>
				<button type="button" class="type2" onclick="javascript:previewApprpval('<%=approval_seq %>'); return false;">미리보기</button>
				<%} %>
				<%if(menu.equals("0202") && state.equals("E02001") && (memberSessionVO.getEmp_no().equals(approvalMasterVO.getCreate_no()))){ //(내가올린문서 && 작성중 && 본인) 수정 삭제 권한 있음%>
				<button type="button" class="type2" onclick="javascript:updateApprpval(); return false;">수정</button>
				<button type="button" class="type2" onclick="javascript:deleteApprpval(); return false;">삭제</button>
				<%} %>
				<%if((adminAuth && "020501".equals(menu) && state.equals("E02003"))){ //관리자는 결재라인만 수정이 가능하다.%>
				<button type="button" class="type2" onclick="javascript:updateApprpvalStep2(); return false;">수정</button>
				<%} %>
				<div class="fr">

					<%-- ml180112.ml01 김진국 - approvalPopup.jsp(결재 상세 팝업)에 시행부서 추가와 같은 프로퍼티의 버튼을 만들었음 - 웹단에 보여야 하니깐. --%>
					<%if(/*("020701".equals(menu) && !"E03002".equals(docu_state)) ||
							("020702".equals(menu) && !"E03002".equals(docu_state)) ||*/
							("0203".equals(menu)  && !"E03002".equals(docu_state)) ||
							(adminAuth && "020501".equals(menu) && !"E03002".equals(state))){ //(시행문서 결재중(주석) || 시행문서 결재완료(주석) || 내가받은문서 || (관리자 && 문서목록 && !반려) || 결재 작성)%>
					<button type="button" class="type2" <%-- ml180119.ml04_T37 김진국 - approval_seq.jsp의 시행부서 추가 버튼 태그에 title="클릭하면 바로 전 결재자의 승인(기결)이 취소되며,&#10;&#13;해당 시행부서의 의견이 모두 달려야 결재가 진행됩니다." - 친절히 설명 --%>
							title="바로 전 결재자의 결재승인(기결)이 있다면 승인 취소되며,&#10;&#13;해당 시행부서의 의견이 모두 달려야 결재가 진행됩니다.&#10;&#13;결재는 본인의 바로 전 결재자부터 시작됩니다."
							onclick="javascript:addMustOpinion(); return false;">시행부서 의견 필수</button>
					<%} %>
					<%-- ml180112.ml01 김진국 - approvalPopup.jsp(결재 상세 팝업)에 시행부서 추가와 같은 프로퍼티의 버튼을 만들었음 - 웹단에 보여야 하니깐. --%>

					<%if(("020702".equals(menu) && "E02004".equals(state)) || (adminAuth && "020501".equals(menu) && "E02004".equals(state)) || (menu.equals("0202") && approvalMasterVO.getCreate_no().equals(memberSessionVO.getEmp_no()) && "E02004".equals(state))){ //((시행문서 결재완료&& 결재완료) || (관리자 && 문서목록 && 결재완료)||(내가올린문서 && 작성자 && 결재완료)) ml180119.ml06_T39 김진국 - approval_seq.jsp의 시행부서 추가 버튼 태그에 결재 완료에서는 삭제(주석처리) - 결재 완료된 것에 대해서는 시행부서 추가 및 시행부서 의견 필수 실행되지 않게 한다.
					%>
					<button type="button" class="type2" onclick="javascript:addShare(); return false;">문서공유</button>
					<%} %>	
					<%if( ("020701".equals(menu) && !"E03003".equals(docu_state)) || /* ml180124.ml01_T70 김진국 - approvalPopup.jsp에 020701 주석해제 - 시행문서조회 - 결재중에도 보이게! */
						/*	("020702".equals(menu) && !"E03003".equals(docu_state)) || */
							("0203".equals(menu)  && !"E03003".equals(docu_state)) || (adminAuth && "020501".equals(menu) && !"E02005".equals(state))){ //(시행문서 결재중 || 시행문서 결재완료(주석 처리) || 내가받은문서 || (관리자 && 문서목록 && !반려))%>
					<button type="button" class="type2"
							title="결재중에 시행부서 의견을 더 추가해야 한다면 클릭하세요.&#10;&#13;시행부서 추가 후 좌측 '시행부서 의견 필수' 클릭하세요.&#10;&#13;(시행부서 의견 필수는 결재승인(기결) 전 상태 또는 본인이 결재승인 했다면 결재 '승인취소'가 이뤄져야 가능합니다.)" <%-- ml180119.ml03_T36 김진국 - approval_seq.jsp의 시행부서 추가 버튼 태그에 title="결재중에 시행부서 의견을 더 추가해야 한다면 클릭하세요." - 친절히 설명 --%>
							onclick="javascript:addImplDept(); return false;">시행부서 추가</button>
					<%} %>	
					<%if("020702".equals(menu) && "E02004".equals(state) && "E01009".equals(docu_cd) && overTimeChk ){ //(시행문서결재완료 && 결제완료 && 시간외근무신청서 && 시간외근무내역서내역없음)%>
					<button type="button" class="type2" onclick="javascript:addOverTimeDetail(); return false;">시간 외 근무 내역서</button>
					<%} %>
					<%if(("0202".equals(menu) && "E02002".equals(state)) || (adminAuth && "020501".equals(menu) && "E02002".equals(state))){ //내가올린문서 &&  요청중  || (관리자 && 문서목록 && 요청중)%>
					<button type="button" class="type2" onclick="javascript:approvalPopup('7'); return false;">요청취소</button>
					<%}%>
					<%if("020702".equals(menu) && "E02004".equals(state) && !implementtCompleteChk ){ //(시행문서결재완료 && 결제완료 && 시행완료내역없음)%>
					<button type="button" class="type2" onclick="javascript:addImplementtComplete(); return false;">시행완료 등록</button>
					<%} %>
					<button type="button" class="type2" onclick="javascript:addOpinionPopup(); return false;">의견<font size="1">(<%=opinionList.size()%>)</font></button>
					<%if("0203".equals(menu)){ //내가받은문서%>
						<%if("E03004".equals(docu_state) && memberSessionVO.getEmp_no().equals(last_line_nm[0])){ //((전결) && 마지막결재자가 나 일때))%>
						<button type="button" class="type2" onclick="javascript:approvalPopup('6'); return false;">전결취소</button>
						<%}else if(("E03002".equals(docu_state) || "E02004".equals(state)) && memberSessionVO.getEmp_no().equals(last_line_nm[0])){ //((기결 || 완료) && 마지막결재자가 나 일때 && 기결))%>
						<button type="button" class="type2" <%-- ml180119.ml05_T38 김진국 - approval_seq.jsp의 시행부서 추가 버튼 태그에 title="결재를 승인취소하면 시행부서 의견 필수를 실행할 수 있습니다." - 친절히 설명 --%>
								title="결재를 승인취소하면 시행부서 의견 필수를 실행할 수 있습니다.&#10;&#13;본인의 결재를 승인취소하면 시행부서 의견 필수 버튼이 보입니다.&#10;&#13;시행부서 의견 필수를 실행하면 추가한 시행부서의 의견이 모두 있어야 다음 결재가 진행 가능합니다."
								onclick="javascript:approvalPopup('5'); return false;">승인취소</button>
						<%}else if("E03003".equals(docu_state) && memberSessionVO.getEmp_no().equals(last_line_nm[0])){//반려 && 마지막결재자(나)%>
						<button type="button" class="type2" onclick="javascript:approvalPopup('4'); return false;">반려취소</button>
						<%} %>
						<%if(("E02003".equals(state) || "E02002".equals(state)) && "Y".equals(approvalMasterVO.getDecide_yn()) 
								&& "Y".equals(documentInfoDetail.getDecide_yn()) && memberSessionVO.getEmp_no().equals(approvalNowEmpNo.getEmp_no())){%>
						<button type="button" class="type2" onclick="javascript:approvalPopup('3'); return false;">전결</button>
						<%} %>
						<%if(("E02002".equals(state) || "E02003".equals(state)) && memberSessionVO.getEmp_no().equals(approvalNowEmpNo.getEmp_no())){ //((진행중 || 요청중) && 결재차례가 나 일때)%>
						<button type="button" class="type2" onclick="javascript:approvalPopup('1'); return false;">승인</button>
						<button type="button" class="type2" onclick="javascript:approvalPopup('2'); return false;">반려</button>
						<%} %>
					<%}%>
					<%if(("E02003".equals(state) || "E02005".equals(state) || "E02004".equals(state)) && (!"0206".equals(menu))){ %>
					<button type="button" class="type2" onclick="javascript:approvalHistoryPopup(); return false;">결재이력</button>
					<%}%>
				</div>
			</div>
		</div>
		<div class="inner">
			<div class="cont_box cont_table06">
				<form id="frm" name="frm" method="post">
				<input type="hidden" id="approval_seq" name="approval_seq" value="<%=approval_seq %>" />
				<input type="hidden" id="menu" name="menu" value="<%=menu%>" />
				<input type="hidden" id="emp_no" name="emp_no" value="" />
				<input type="hidden" id="docu_seq" name="docu_seq" value="<%=docu_seq%>" />
				<input type="hidden" id="docu_cd" name="docu_cd" value="<%=docu_cd%>" />
				<input type="hidden" id="rejection_reason" name="rejection_reason" />
				<input type="hidden" id="masterState" name="masterState" />
				<input type="hidden" id="state" name="state" />
				<h4 class="type2">
					<%if("Y".equals(approvalMasterVO.getSecurity_yn()) && "Y".equals(documentInfoDetail.getSecurity_yn())){ %><span>대외비입니다.</span><%} %>
					<strong><%=approvalMasterVO.getDocu_nm() %> <%if("E02005".equals(state)){%><font color="#ff0000">(반려)</font><%}%></strong>
					<%if("Y".equals(approvalMasterVO.getDecide_yn())){ %><span>전결 가능합니다.</span><%} %>
				</h4>
				<table>
					<colgroup>
						<col style="width:12.5%">
						<col style="width:12.5%">
						<col style="width:12.5%">
						<col style="width:12.5%">
						<col style="width:12.5%">
						<col style="width:12.5%">
						<col style="width:12.5%">
						<col style="width:12.5%">
					</colgroup>
					<tbody>
						<tr>
							<th>문서번호</th>
							<td colspan="3"><%=approval_seq %></td>
							<th>작성일자</th>
							<td colspan="3"><%=StringUtil.nvl(approvalMasterVO.getMake_dt())%></td>
						</tr>
						<tr>
							<th>작성부서</th>
							<td colspan="3"><%=StringUtil.nvl(approvalMasterVO.getDept_ko_nm())%></td>
							<th>작성자</th>
							<td colspan="3"><%=StringUtil.nvl(approvalMasterVO.getEmp_ko_nm())%></td>
						</tr>
						<%if("E01015".equals(docu_cd)){ %>						
							<tr class="appr">
								<th rowspan="2">청구결재</th>
								<%
								if(approvalDetailList.size() > 0){
									for(int i=0; approvalDetailList.size()>i;i++){
										ApprovalVO approvalVO = new ApprovalVO();
										approvalVO = approvalDetailList.get(i);
										/*CHOE 201151230 청구결재는 7칸만 사용 한다.
										getGroup_div == "1"인 경우는 청구결재에 넣고 2인 경우는 넣지 않는다.
										넣지 않는 빈칸는 <td>로 채운다.
										*/
										if (i < 7) {
											if ("1".equals(approvalVO.getGroup_div())){
								%>				
								<td><%=StringUtil.nvl(approvalVO.getGrad_ko_nm()) %></td>
								<%			} else { %>
								<td></td>							
								<%			}
										}
									} 
								}%>	
							</tr>
							<tr class="appr">
								<%
								if(approvalDetailList.size() > 0){
									for(int i=0; approvalDetailList.size()>i;i++){
										ApprovalVO approvalVO = new ApprovalVO();
										approvalVO = approvalDetailList.get(i);
										/*CHOE 201151230 청구결재는 7칸만 사용 한다.
										getGroup_div == "1"인 경우는 청구결재에 넣고 2인 경우는 넣지 않는다.
										넣지 않는 빈칸는 <td>로 채운다.
										*/
										if (i < 7) {
											if ("1".equals(approvalVO.getGroup_div())){
								%>				
								<td <%if("E03002".equals(approvalVO.getState()) || "E03004".equals(approvalVO.getState())){%>
										class="on"
									<%}else if("E03003".equals(approvalVO.getState())){%>
										class="no"
									<%}%>
								>
										<%=i+1 %><br /><br />
										<%=StringUtil.nvl(approvalVO.getEmp_ko_nm())%><br />
										<%if("E03002".equals(approvalVO.getState())){%><span class="sp_btn">기결</span>
										<%}else if("E03003".equals(approvalVO.getState())){%><span class="sp_btn">반려</span>
										<%}else if("E03004".equals(approvalVO.getState())){%><span class="sp_btn">전결</span>
									<%}%>
								</td>
								<%			} else { %>
								<td></td>							
								<%			}
										}
									} 
								}%>									
							</tr>
							<tr class="appr">
								<th rowspan="2">확인결재</th>
								<%
								if(approvalDetailList.size() > 0){
									for(int i=0; approvalDetailList.size()>i;i++){
										ApprovalVO approvalVO = new ApprovalVO();
										approvalVO = approvalDetailList.get(i);
										/*CHOE 201151230 확인결재는 7칸만 사용 한다.
										getGroup_div == "2"인 경우는 확인결재에 넣고 1인 경우는 넣지 않는다.
												"2"인 경우 정보를 미리 채우고 남은(2칸 :확인결재5명) 빈칸는 <td>로 채운다.
										*/
										if (i < 14) {
											if ("2".equals(approvalVO.getGroup_div())){
								%>		
								<td><%=StringUtil.nvl(approvalVO.getGrad_ko_nm()) %></td>
								<%			} 
										}
									}%>
								<td></td>
								<td></td>
								<% } %>		
							</tr>
							<tr class="appr">
								<%
								if(approvalDetailList.size() > 0){
									for(int i=0; approvalDetailList.size()>i;i++){
										ApprovalVO approvalVO = new ApprovalVO();
										approvalVO = approvalDetailList.get(i);
										/*CHOE 201151230 확인결재는 7칸만 사용 한다.
										getGroup_div == "2"인 경우는 확인결재에 넣고 1인 경우는 넣지 않는다.
										"2"인 경우 정보를 미리 채우고 남은(2칸 :확인결재5명) 빈칸는 <td>로 채운다.
										*/
										if (i < 14) {
											if ("2".equals(approvalVO.getGroup_div())){
								%>				
								<td <%if("E03002".equals(approvalVO.getState()) || "E03004".equals(approvalVO.getState())){%>
										class="on"
									<%}else if("E03003".equals(approvalVO.getState())){%>
										class="no"
									<%}%>
								>
										<%=i+1 %><br /><br />
										<%=StringUtil.nvl(approvalVO.getEmp_ko_nm())%><br />
										<%if("E03002".equals(approvalVO.getState())){%><span class="sp_btn">기결</span>
										<%}else if("E03003".equals(approvalVO.getState())){%><span class="sp_btn">반려</span>
										<%}else if("E03004".equals(approvalVO.getState())){%><span class="sp_btn">전결</span>
									<%}%>
								</td>
								<%			}
										}
									} %>								
								<td></td>
								<td></td>
								<% } %>								
							</tr>
						<% } else { %>						
							<tr class="appr">
								<th rowspan="2">결재</th>
								<%
								if(approvalDetailList.size() > 0){
									for(int i=0; approvalDetailList.size()>i;i++){
										ApprovalVO approvalVO = new ApprovalVO();
										approvalVO = approvalDetailList.get(i);
								%>				
								<td><%=StringUtil.nvl(approvalVO.getGrad_ko_nm()) %></td>
								<%	}
								} %>
								<%if(approvalDetailList.size() < 7){ 
									for(int i=0; 7-approvalDetailList.size()>i;i++){
								%>	
								<td></td>		
								<%	} 
								}%>
							</tr>
							<tr class="appr">
								<%
								if(approvalDetailList.size() > 0){
									for(int i=0; approvalDetailList.size()>i;i++){
										ApprovalVO approvalVO = new ApprovalVO();
										approvalVO = approvalDetailList.get(i);
								%>
				
								<td <%if("E03002".equals(approvalVO.getState()) || "E03004".equals(approvalVO.getState())){%>
										class="on"
									<%}else if("E03003".equals(approvalVO.getState())){%>
										class="no"
									<%}%>
								>
										<%=i+1 %><br /><br />
										<%=StringUtil.nvl(approvalVO.getEmp_ko_nm())%><br />
										<%if("E03002".equals(approvalVO.getState())){%><span class="sp_btn">기결</span>
										<%}else if("E03003".equals(approvalVO.getState())){%><span class="sp_btn">반려</span>
										<%}else if("E03004".equals(approvalVO.getState())){%><span class="sp_btn">전결</span>
									<%}%>
								</td>
								<%	}
								} %>
								<%if(approvalDetailList.size() < 7){ 
									for(int i=0; 7-approvalDetailList.size()>i;i++){
								%>	
								<td></td>		
								<%	} 
								}%>
								
							</tr>
						<% } %>						
						<tr>
							<th>시행부서</th>
							<td colspan="7" class="implement">
								<div>
								<%
								if(implDeptDetailList != null){
									for(int i=0; implDeptDetailList.size()>i;i++){
										ImplDeptVO implDeptVO = new ImplDeptVO();
										implDeptVO = implDeptDetailList.get(i);
								%>
								<%=StringUtil.nvl(implDeptVO.getDept_ko_nm())%><%if(i!=implDeptDetailList.size()-1){ %>,<%}%>
								<%	} 
								}%>	
								</div>
							</td>
						</tr>
						<tr>
							<th>제목</th>
							<td colspan="7"><%=RequestFilterUtil.toHtmlTagReplace("", approvalMasterVO.getSubject()) %></td>
						</tr>
						<tr>
							<td colspan="8" class="breakdown">
								<jsp:include page="<%=includePage%>"/>
							</td>
						</tr>
						<tr>
							<th class="append_tit">
								첨부파일
								<%if("E01011".equals(docu_cd)){ %>
								<span class="ref">※ 첨부서류 :<br />&nbsp;&nbsp;&nbsp;거래처카드</span>
								<%} %>
							</th>
							<td colspan="7" class="append_file br_none">
								<div class="append_search">
									<input type="hidden" name="fileNum" id="fileNum" />
								</div>
								<div id="messages" class="attachList">
								<%
									if(attachList!=null){
										for(int i=0; i<attachList.size(); i++){
											FileAttachVO fileAttachvo = new FileAttachVO();
											fileAttachvo = attachList.get(i);
										
										%>
										<p>
											<a href="<%=GROUP_ROOT%>/fileDownload.do?file_seq=<%=fileAttachvo.getFile_seq()%>&filename=<%=fileAttachvo.getFile_nm()%>&filePath=<%=fileAttachvo.getFile_path()%>&type=appr">
												<%=fileAttachvo.getOrigin_file_nm()%>
											</a>
										</p>
										
										<%
										}
									}

									if(attachList.size() > 1) {
								%>
									<p><button type="button" onclick="javascript:Commons.fileDownloadAll(0);">전체 다운로드</button></p>
								<%
									}
								%>
								</div>
								<div id="pro"></div>
							</td>
						</tr>						
					</tbody>
				</table>
				<%if("E01011".equals(docu_cd)){ %>
				<div class="box_accident_report">
					<%=bogo_year%>년 <%=bogo_month%>월 <%=bogo_day%>일
					<p>상기와 같이 보고합니다.</p>
				</div>
				<%} %>
				<%if("E02005".equals(state)){ %>
				<table class="tbl_return">
					<colgroup>
						<col style="width:96px"/>
						<col style=""/>
						<col style="width:138px"/>
					</colgroup>
					<thead>
						<tr>
							<th>반려자</th>
							<th>반려사유</th>
							<th>반려일자</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td><%=StringUtil.nvl(last_line_nm[1]) %></td>
							<td style="text-align:left;"><%=StringUtil.nvl(RequestFilterUtil.toHtmlTagReplace("",approvalMasterVO.getRejection_reason())) %></td>
							<td><%=StringUtil.nvl(approvalMasterVO.getModify_dt()) %></td>
						</tr>
					</tbody>
				</table>
				<%} %>
			</form>
			</div>
		</div>
	</div>
</div>	
<div id='layerOpen' style='display:none; width:auto; height:auto; '>
	<div class='layer_content'></div> 
</div>
</body>
</html>