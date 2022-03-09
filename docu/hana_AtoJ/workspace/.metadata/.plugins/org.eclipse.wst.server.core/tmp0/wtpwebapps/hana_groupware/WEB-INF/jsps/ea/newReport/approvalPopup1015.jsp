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
	
	Integer winWidth = 0;  //CHOE 20160114 물품구입청구확인서 리사이즈 용 변수
	Integer winResize = 0;
	
	/*시행문서 이고 결재 완료 이고 시간외 근무 내역서일 경우 START*/
	boolean overTimeChk = (Boolean)request.getAttribute("overTimeChk");
	String overTimeDetailDecu_seq = StringUtil.nvl((String)request.getAttribute("overTimeDetailDecu_seq"));	//메뉴코드
	/*시행문서 이고 결재 완료 이고 시간외 근무 내역서일 경우 END*/
	
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
	
	winWidth = $(window).width();
	winResize = 1446 - winWidth;  //CHOE 20160113 화면이 1120보다 커지지 않는다.
	//alert("winResize : "+winResize);
	<%if("E01015".equals(docu_cd)|| "E01016".equals(docu_cd)) {%>		
		$(document).ready(function() {
			window.resizeBy(winResize, 0);			
		});		
	<%
	}%>
	
	$(document).ready(function () {
		<%if("020701".equals(menu) || "020702".equals(menu) || "0206".equals(menu)){%>
		window.opener.location.reload();  //CHOE 팝업창을 띄운 부모창을 다시 불러올때
		<%}%>
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
			document.getElementById("state").value = "E03001";			//미결
			document.getElementById("frm").action = "<%=GROUP_ROOT%>/ea/receive/updateApproval.do?gubun="+gubun;
			document.getElementById("frm").submit();
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

<div class="wrap_pop_bg2">
	<div class="wrap_confirm">
		<div class="top">
			<h3><%=approvalMasterVO.getReq_dt() %><span> <%=RequestFilterUtil.toHtmlTagReplace("",approvalMasterVO.getSubject()) %></span></h3>
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
					<%if(("020702".equals(menu) && "E02004".equals(state)) || (adminAuth && "020501".equals(menu) && "E02004".equals(state)) || (menu.equals("0202") && approvalMasterVO.getCreate_no().equals(memberSessionVO.getEmp_no()) && "E02004".equals(state))){ //((시행문서 결재완료&& 결재완료) || (관리자 && 문서목록 && 결재완료)||(내가올린문서 && 작성자 && 결재완료))%>
					<button type="button" class="type2" onclick="javascript:addShare(); return false;">문서공유</button>
					<%} %>	
					<%if(("020701".equals(menu) && !"E03003".equals(docu_state)) || ("020702".equals(menu) && !"E03003".equals(docu_state)) || ("0203".equals(menu)  && !"E03003".equals(docu_state)) || (adminAuth && "020501".equals(menu) && !"E02005".equals(state))){ //(시행문서 결재중 || 시행문서 결재완료 || 내가받은문서 || (관리자 && 문서목록 && !반려))%>	
					<button type="button" class="type2" onclick="javascript:addImplDept(); return false;">시행부서 추가</button>
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
						<button type="button" class="type2" onclick="javascript:approvalPopup('5'); return false;">승인취소</button>	
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
				<div class="confirm">
					<table class="over">
					<colgroup>
						<col style="width:12%">
						<col style="width:12%">
						<col style="width:12%">
						<col style="width:12%">
						<col style="width:12%">
						<col style="width:12%">
						<col style="width:12%">
						<col style="width:12%">
					</colgroup>
					<tbody>
						<tr>
							<th>문서번호</th>
							<td><%=approval_seq %></td>
							<th style="border-bottom: none;">작성일자</th>
							<td><%=StringUtil.nvl(approvalMasterVO.getMake_dt())%></td>						
							<th>작성부서</th>
							<td><%=StringUtil.nvl(approvalMasterVO.getDept_ko_nm())%></td>
							<th>작성자</th>
							<td><%=StringUtil.nvl(approvalMasterVO.getEmp_ko_nm())%></td>
						</tr>
						<tr>
							<%if("E01015".equals(docu_cd)){ %>
							<th>청구결재</th>
							<td colspan="3" class="confirm_box">
						      	<table>
							  		<colgroup>
								    	<col style="width:20%;" />
								    	<col style="width:20%;" />
								    	<col style="width:20%;" />
								    	<col style="width:20%;" />
								    	<col style="width:20%;" />
								    	<col style="width:20%;" />
								    	<col style="width:20%;" />
							      	</colgroup>
								  	<tbody>
									  	<tr>
										  	<% 
											if(approvalDetailList.size() > 0){
												for(int i=0; approvalDetailList.size()>i;i++){
													ApprovalVO approvalVO = new ApprovalVO();
													approvalVO = approvalDetailList.get(i);													
													if (i < 7) {
														if ("1".equals(approvalVO.getGroup_div())){
											%>			
											<th><%=StringUtil.nvl(approvalVO.getGrad_ko_nm()) %></th>
											<%			} else { %>
											<th></th>							
											<%			}
													}
												}
												/*CHOE 20160121 하드코딩으로 인한 추가 코드 절대 삭제 금지*/
												for(int j=7; approvalDetailList.size() < j;j--) {%>
											<th></th>	
												<%}
											}%>
										</tr>
										<tr>
											<%
											if(approvalDetailList.size() > 0){
												for(int i=0; approvalDetailList.size()>i;i++){
													ApprovalVO approvalVO = new ApprovalVO();
													approvalVO = approvalDetailList.get(i);											
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
												/*CHOE 20160121 하드코딩으로 인한 추가 코드 절대 삭제 금지*/
												for(int j=7; approvalDetailList.size() < j;j--) {%>
											<td></td>
												<%}
											}%>		
										</tr>
									</tbody>
								</table>							
							</td>
							<%}else{ /*원부자재 납품확인서*/%>
								<th>결재</th>
								<td colspan="3" class="confirm_box">
						      	<table>
								  <colgroup>
								  	<col style="width:14%;" />
							    	<col style="width:14%;" />
							    	<col style="width:14%;" />
							    	<col style="width:14%;" />
							    	<col style="width:14%;" />
							    	<col style="width:14%;" />
							    	<col style="width:14%;" />
							      </colgroup>
								  <tbody>
									<tr>
									  	<% 
										if(approvalDetailList.size() > 0){
											for(int i=0; approvalDetailList.size()>i;i++){
												ApprovalVO approvalVO = new ApprovalVO();
												approvalVO = approvalDetailList.get(i);													
												if (i < 7) {													
										%>			
										<th><%=StringUtil.nvl(approvalVO.getGrad_ko_nm()) %></th>
										<%			} else { %>
										<th></th>						
										<%			}
											}
											/*CHOE 20160121 하드코딩으로 인한 추가 코드 절대 삭제 금지*/
											for(int j=7; approvalDetailList.size() < j;j--) {%>
										<th></th>	
											<%}
										}%>
									</tr>
									<tr>
										<%
										if(approvalDetailList.size() > 0){
											for(int i=0; approvalDetailList.size()>i;i++){
												ApprovalVO approvalVO = new ApprovalVO();
												approvalVO = approvalDetailList.get(i);
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
										<%		} else { %>
										<td></td>							
										<%		}
											}
											/*CHOE 20160121 하드코딩으로 인한 추가 코드 절대 삭제 금지*/
											for(int j=7; approvalDetailList.size() < j;j--) {%>
										<td></td>
											<%}
										}%>		
										</tr>
								  </tbody>
								</table>							  
						      	</td>
						     <%} %>	
							<%if("E01015".equals(docu_cd)){ %>
							<th>확인결재</th>
							<td colspan="3" class="confirm_box">
								<table>
									<colgroup>
								    	<col style="width:20%;" />
								    	<col style="width:20%;" />
								    	<col style="width:20%;" />
								    	<col style="width:20%;" />
								    	<col style="width:20%;" />
								    	<col style="width:20%;" />
								    	<col style="width:20%;" />
							      	</colgroup>
								  	<tbody>
										<tr>
											<% 
											if(approvalDetailList.size() > 0){
												for(int i=0; approvalDetailList.size()>i;i++){
													ApprovalVO approvalVO = new ApprovalVO();
													approvalVO = approvalDetailList.get(i);													
													if (i < 10) {
														if ("2".equals(approvalVO.getGroup_div())){
											%>			
											<th><%=StringUtil.nvl(approvalVO.getGrad_ko_nm()) %></th>
											<%			} 
													}
												}											
											}%>
											<th></th><th></th><th></th><th></th>
										</tr>
										<tr>
											<%
											if(approvalDetailList.size() > 0){
												int k = 1;
												for(int i=0; approvalDetailList.size()>i;i++){
													ApprovalVO approvalVO = new ApprovalVO();
													approvalVO = approvalDetailList.get(i);												
													if (i < 14) {
														if ("2".equals(approvalVO.getGroup_div())){
												%>				
												<td <%if("E03002".equals(approvalVO.getState()) || "E03004".equals(approvalVO.getState())){%>
														class="on"
													<%}else if("E03003".equals(approvalVO.getState())){%>
														class="no"
													<%}%>
												>
														<%=k %><br /><br />														
														<%=StringUtil.nvl(approvalVO.getEmp_ko_nm())%><br />
														<%if("E03002".equals(approvalVO.getState())){%><span class="sp_btn">기결</span>
														<%}else if("E03003".equals(approvalVO.getState())){%><span class="sp_btn">반려</span>
														<%}else if("E03004".equals(approvalVO.getState())){%><span class="sp_btn">전결</span>
													<%}%>
												</td>
												<%			k += 1;
															} 
														}
													}											
											}%>		
											<td></td><td></td><td></td><td></td>
										</tr>
							      	</tbody>
								</table>
							</td>
							<%}else{/*원부자재 납품확인서*/ %>
							<th></th>
							<td colspan="3" class="confirm_box">								
							</td>
							<%} %>		
						</tr>										
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
					</tbody>
					</table>
				</div>
						
				<jsp:include page="<%=includePage%>"/>
				
				<table class="over" style="margin-top:3px;">
					<colgroup>
						<col style="width:12%">
						<col style="width:12%">
						<col style="width:12%">
						<col style="width:12%">
						<col style="width:12%">
						<col style="width:12%">
						<col style="width:12%">
						<col style="width:12%">
					</colgroup>
					<tbody>
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