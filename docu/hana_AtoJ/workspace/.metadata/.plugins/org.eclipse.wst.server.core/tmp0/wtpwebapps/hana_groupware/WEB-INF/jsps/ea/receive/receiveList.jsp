<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%--
 * @파일명 : receiveList.jsp
 * @메뉴명 : 내가받은문서
 * @최초작성일 : 2015/02/10            
 * @author : Jung.Jin.Muk(pc123pc@irush.co.kr)                 
 * @수정내역 : 
--%>
<%@ page import="java.util.List" %>
<%@ page import="com.hanaph.gw.co.common.utils.StringUtil" %>
<%@ page import="com.hanaph.gw.co.common.utils.RequestFilterUtil" %>
<%@ page import="com.hanaph.gw.ea.receive.vo.ReceiveVO" %>
<%@ page import="com.hanaph.gw.co.code.vo.CodeVO" %>
<%@ include file ="/common/path.jsp" %>
<%
	List<ReceiveVO> receiveList = (List<ReceiveVO>)request.getAttribute("receiveList");	//내가받은문서 리스트
	List<CodeVO> docuKindList = (List<CodeVO>)request.getAttribute("docuKindList");	//문서종류
	List<CodeVO> docuStateList = (List<CodeVO>)request.getAttribute("docuStateList"); //문서상태
	List<CodeVO> apprState = (List<CodeVO>)request.getAttribute("apprState"); //결재상태
	List<MemberVO> authorityMemberList = (List<MemberVO>)request.getAttribute("authorityMemberList"); //일괄결재 권한을 가진 임직원
	
	String search_docu_kind = (String)request.getAttribute("search_docu_kind"); //문서 종류 검색
	String search_docu_state = (String)request.getAttribute("search_docu_state"); //문서 상태 검색
	String search_appr_state = (String)request.getAttribute("search_appr_state"); //결재 상태 검색
	String searchType = StringUtil.nvl((String)request.getAttribute("searchType"),"approval_seq"); //검색 타입
	String searchKeyword = (String)request.getAttribute("searchKeyword"); //검색 키워드
	String search_start_ymd = StringUtil.nvl((String)request.getAttribute("search_start_ymd")); //기안 시작 일
	String search_end_ymd = StringUtil.nvl((String)request.getAttribute("search_end_ymd")); //기안 마지막 일
	String emp_ko_nm = StringUtil.nvl((String)request.getAttribute("emp_ko_nm")); //로그인한 이름
	String emp_no = StringUtil.nvl((String)request.getAttribute("emp_no")); //로그인한 임직원 번호
	int cnt = ((Integer)request.getAttribute("cnt")).intValue(); //내가받은문서 리스트
	
	searchKeyword = StringUtil.nvl(RequestFilterUtil.toHtmlTagReplace("", searchKeyword));

	String checkMustOpinion = StringUtil.nvl((String)request.getAttribute("checkMustOpinion")); //시행부서 의견 필수



%>
<!DOCTYPE html>
<html lang="ko">
<head>
	<%@include file="/include/head.jsp"%>
	<script type="text/javascript">
	$(window).load(function() {
		$("#searchType > option[value=<%=searchType%>]").attr("selected","true");
		$("#search_docu_state > option[value=<%=search_docu_state%>]").attr("selected","true");
		$("#search_docu_kind > option[value=<%=search_docu_kind%>]").attr("selected","true");
		$("#search_appr_state > option[value=<%=search_appr_state%>]").attr("selected","true");
        $("#checkMustOpinion > option[value=<%=checkMustOpinion%>]").attr("selected","true");
    });
	
	/**
	 * 검색
	 */
	function goSearch(){
		if(formCheck.containsChars(document.getElementById("searchKeyword").value, "%")){
			 alert("특수문자를 사용할수 없습니다");
			 return;
		}
		
		if(formCheck.dateChk($("#search_start_ymd").val(),$("#search_end_ymd").val())<0){
			alert("기안일 시작 날짜가 기안일 종료 날짜 보다 이후 입니다.");
			search_start_ymd.focus();
			return
		}
		document.searchForm.action = "<%=GROUP_ROOT%>/ea/receive/receiveList.do";
		document.searchForm.submit();
	}
	
	/**
	 * @param approval_seq
	 * @param docu_state
	 */
	function goApprovalPopup(approval_seq, docu_state){
		var height = 0;
		if(850 < $(window).innerHeight()){
			height = 850; 
		}else{
			height = $(window).innerHeight();
		}
		Commons.popupOpen('appPop','<%=GROUP_ROOT%>/ea/newReport/approvalPopup.do?docu_state='+docu_state+'&approval_seq='+approval_seq+'&menu=0203','820',height);
	}
	
	/**
	 * 체크박스 전체 선택
	 */
	function allCheck2(){
		$("input[name=allCheck]:checkbox").each(function() {
    		if($(this).is(':checked')) {
    			$("input[disabled!='disabled']").prop("checked", true);
            } else {
            	$("input[disabled!='disabled']").prop("checked", false);
            }
		});
	}
	
	/**
	 * 일괄결재 레이어 팝업
	 */
	function approvalPopup(){
		if($("input[name='check']:checkbox:checked").length < 1){
			alert("일괄 결재할 문서를 선택해 주세요.");
			return;
		}
		$('#layerOpen').bPopup({
			content:'iframe', //'ajax', 'iframe' or 'image'
			iframeAttr:'scrolling="no" frameborder="0" width="800px" height="564px"',
			follow: [true, true],
			contentContainer:'.layer_content',
			modalClose: true,
            opacity: 0.6,
            positionStyle: 'fixed',
			loadUrl:'<%=GROUP_ROOT%>/ea/receive/approvalPopup.do?gubun=8'
		});
	}
	
	/**
	 * 일괄결재
	 */
	function updateApproval(){
		var approval_seq = "";
		result = true;
		$("input[name='check']:checkbox:checked").each(function(){
			if('E01001' == $(this).val().substring(0,6)){
				alert("휴가신청서는 일괄결재 할 수 없습니다.");
				result =false;
				return false;
			}
			approval_seq += $(this).val()+"|";
		});
		if(result){
			document.frm.approval_seq.value = approval_seq;
			document.getElementById("state").value = "E03002";			//기결
			document.getElementById("frm").action = "<%=GROUP_ROOT%>/ea/receive/updateApproval.do?gubun=8";
			document.getElementById("frm").submit();
		}else{
			layerClose();			
		}
	}
	
	/**
	 * 레이어팝업 닫기	
	 */
	function layerClose(){
		$('#layerOpen').bPopup().close();
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
			
			<!-- ######## start ####### -->
<div class="cont float_left">
	<div class="wrap_receive_document_all">
		<h2>내가받은문서</h2>
		<div class="noticeboard_box">
		<div class="serch_box">
		<form id="searchForm" name="searchForm" method="post">
			<ul class="serch_con01">
				<li>
					<span class="sc_txt02">기안일</span>
					<span class="serch_date_box">
						<input class="serch_date" type="text" id="search_start_ymd" name="search_start_ymd" value="<%=search_start_ymd%>" readonly="readonly">
						<button type="button" class="btn_date"><span class="blind">날짜선택</span></button>
					</span> ~ 
					<span class="serch_date_box">
						<input class="serch_date" type="text" id="search_end_ymd" name="search_end_ymd" value="<%=search_end_ymd%>" readonly="readonly">
						<button type="button" class="btn_date"><span class="blind">날짜선택</span></button>
					</span>
					<span class="sc_txt02">문서종류</span>
					<select class="serch_select02 w96" id="search_docu_kind" name="search_docu_kind">
						<option value="all">전체</option>
						<%
						if(docuKindList.size()!=0){
							for(int i=0;i<docuKindList.size();i++){
								CodeVO codeVO = new CodeVO();
								codeVO = docuKindList.get(i);
						%>
								<option value="<%=codeVO.getCd()%>"><%=codeVO.getCd_nm()%></option>
						<%
							}
					   	}
						%>
					</select>
					<span class="sc_txt02">문서상태</span>
					<select class="serch_select02 w96" id="search_docu_state" name="search_docu_state">
						<option value="all">전체</option>
						<%
						if(docuStateList.size()!=0){
							for(int i=0;i<docuStateList.size();i++){
								CodeVO codeVO = new CodeVO();
								codeVO = docuStateList.get(i);
								if(!"E02001".equals(codeVO.getCd())){
						%>
								<option value="<%=codeVO.getCd()%>"><%=codeVO.getCd_nm()%></option>
						<%
								}
							}
					   	}
						%>
					</select>
				</li>
				<li class="cont02">
					<span class="sc_txt">
						<select class="search_select02 w116" id="searchType" name="searchType">
							<option value="approval_seq">문서번호</option>
							<option value="subject">제목</option>
							<option value="emp_ko_nm">기안자</option>
						</select>
					</span>
					<input class="serch_txt04 w127" type="text" id="searchKeyword" name="searchKeyword" maxlength="32" value="<%=searchKeyword%>" onKeyPress="if(event.keyCode=='13'){goSearch(); return false;}" >
					<span class="sc_txt02">결재상태</span>
					<select class="serch_select02 w96" id="search_appr_state" name="search_appr_state">
						<option value="all">전체</option>
						<%
						if(apprState.size()!=0){
							for(int i=0;i<apprState.size();i++){
								CodeVO codeVO = new CodeVO();
								codeVO = apprState.get(i);
						%>
								<option value="<%=codeVO.getCd()%>"><%=codeVO.getCd_nm()%></option>
						<%
							}
					   	}
						%>
					</select>

					<span class="sc_txt">
						<select class="search_select02 w148" id="checkMustOpinion" name="checkMustOpinion">
							<option value="">전체</option>
							<option value="N">일반 문서</option>
							<option value="Y">시행부서 의견 필수</option>
						</select>
					</span>

				</li>
			</ul>
			<span class="serch_btn"><button type="button" onclick="javascript:goSearch();">검색</button></span>
		</form>
		</div>
		<!-- 관리자만 일괄결재 버튼 노출 -->
		<div class="list_btn">
			<div class="list_button">
			<%
		 	if(authorityMemberList != null){
				for(int i=0; authorityMemberList.size()>i;i++){
					MemberVO memberVO = new MemberVO();
					memberVO = authorityMemberList.get(i);
					if(emp_no.equals(memberVO.getEmp_no())){
			%>
				<button type="button" class="type_01" onclick="javascript:approvalPopup();">일괄 결재</button>
			<% 
			 		}
				}
			}
			%>
			</div>
			<div>
				<span style="float:right;">검정색 : 완료 | 빨간색 : 반려 | 파란색 : 요청, 진행중</span>
			</div>
		</div>
		
		<div class="cont_box cont_table05">
			<form id="frm" name="frm" method="post">
			<input type="hidden" id="approval_seq" name="approval_seq">
			<input type="hidden" id="masterState" name="masterState">
			<input type="hidden" id="state" name="state" />
			<input type="hidden" id="search_appr_state" name="search_appr_state" value="<%=search_appr_state %>" />
			<input type="hidden" id="search_docu_state" name="search_docu_state" value="<%=search_docu_state %>" />
			<input type="hidden" id="search_docu_kind" name="search_docu_kind" value="<%=search_docu_kind %>" />
			<input type="hidden" id="searchType" name="searchType" value="<%=searchType %>" />
			<input type="hidden" id="searchKeyword" name="searchKeyword" value="<%=searchKeyword %>" />
			<input type="hidden" id="search_start_ymd" name="search_start_ymd" value="<%=search_start_ymd %>" />
			<input type="hidden" id="search_end_ymd" name="search_end_ymd" value="<%=search_end_ymd %>" />

				<input type="hidden" id="checkMustOpinion" name="checkMustOpinion" value="<%=checkMustOpinion %>" />

				<table>
				<colgroup>
					<col width="5%">
					<col width="14%">
					<col width="11%">
					<col width="">
					<col width="8%">
					<col width="13%">
					<col width="10%">
					<col width="7%">
					<col width="8%">
				</colgroup>
					<thead>
						<tr>
							<th><input type="checkbox" id="allCheck" name="allCheck" onclick="javascript:allCheck2();" /></th>
							<th>문서번호</th>
							<th>문서종류</th>
							<th>제목</th>
							<th>기안자</th>
							<th>기안일</th>
							<th>문서상태</th>
							<th>결재</th>
							<th class="br_none">처리자</th>										
						</tr>
					</thead>
					<%
					if(receiveList.size()!=0){
						for(int i=0; receiveList.size()>i; i++){
							ReceiveVO receiveVO = new ReceiveVO();
							receiveVO = receiveList.get(i);
							String[] last_line_nm = new String[2]; 
							if(!"".equals(StringUtil.nvl(receiveVO.getLast_line_nm()))){
								last_line_nm = receiveVO.getLast_line_nm().split("\\^");
							}
					%>
						<tbody>
							<tr <%if("E02005".equals(receiveVO.getDocu_cd())){%>class="no"<%}else if("E02003".equals(receiveVO.getDocu_cd()) || "E02002".equals(receiveVO.getDocu_cd())){%>class="ing"<%}%>>
								<td><input type="checkbox" id="check" name="check" value="<%=receiveVO.getApproval_seq()%>" 
								<%if("E02004".equals(receiveVO.getDocu_cd()) || "E02005".equals(receiveVO.getDocu_cd()) || !"E03001".equals(receiveVO.getState())){%> disabled="disabled" <%}%>/></td>
								<td onclick="javascript:goApprovalPopup('<%=receiveVO.getApproval_seq()%>','<%=StringUtil.nvl(receiveVO.getState(),"E03001")%>'); return false;"><%=receiveVO.getApproval_seq()%></td>
								<td onclick="javascript:goApprovalPopup('<%=receiveVO.getApproval_seq()%>','<%=StringUtil.nvl(receiveVO.getState(),"E03001")%>'); return false;"><%=StringUtil.nvl(receiveVO.getDocu_nm())%></td>
								<td onclick="javascript:goApprovalPopup('<%=receiveVO.getApproval_seq()%>','<%=StringUtil.nvl(receiveVO.getState(),"E03001")%>'); return false;"><%=StringUtil.nvl(RequestFilterUtil.toHtmlTagReplace("", receiveVO.getSubject()))%></td>
								<td onclick="javascript:goApprovalPopup('<%=receiveVO.getApproval_seq()%>','<%=StringUtil.nvl(receiveVO.getState(),"E03001")%>'); return false;"><%=StringUtil.nvl(receiveVO.getReq_nm())%></td>
								<td onclick="javascript:goApprovalPopup('<%=receiveVO.getApproval_seq()%>','<%=StringUtil.nvl(receiveVO.getState(),"E03001")%>'); return false;"><%=StringUtil.nvl(receiveVO.getReq_dt())%></td>
								<td onclick="javascript:goApprovalPopup('<%=receiveVO.getApproval_seq()%>','<%=StringUtil.nvl(receiveVO.getState(),"E03001")%>'); return false;"><%=StringUtil.nvl(receiveVO.getDocu_state())%></td>
								<td onclick="javascript:goApprovalPopup('<%=receiveVO.getApproval_seq()%>','<%=StringUtil.nvl(receiveVO.getState(),"E03001")%>'); return false;"><%=StringUtil.nvl(receiveVO.getApproval_state(),"미결")%></td>
								<td onclick="javascript:goApprovalPopup('<%=receiveVO.getApproval_seq()%>','<%=StringUtil.nvl(receiveVO.getState(),"E03001")%>'); return false;" class="br_none"><%=StringUtil.nvl(last_line_nm[1])%></td>
							</tr>
						<%
						}
					}else{
						%>
							<tr>
								<td colspan="9" class="br_none">데이터가 없습니다.</td>
							</tr>
						<%
						} 
						%>
						</tbody>
				</table>
			</form>
		</div>
		<div class="paging">
			<div class="wrap_total">
				* 총 건수 : <span class="cnt"><%=StringUtil.makeMoneyType(cnt)%></span>건
			</div>
			<div class="wrap_paging">
				<%@ include file ="/common/paging.jsp" %>
			</div>
		</div>				
	</div>
	</div>
</div>
			<!-- ######## end ######### -->
			
		</div>
	</div>
	<div id='layerOpen' style='display:none; width:auto; height:auto; '>
		<div class='layer_content'></div> 
	</div>
	<%@include file="/include/footer.jsp"%>
</body>
</html>