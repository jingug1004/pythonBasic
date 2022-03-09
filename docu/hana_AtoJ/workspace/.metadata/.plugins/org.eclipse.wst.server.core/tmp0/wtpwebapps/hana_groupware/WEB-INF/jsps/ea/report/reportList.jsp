<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%--
 * @파일명 : reportList.jsp
 * @메뉴명 : 내가올린문서
 * @최초작성일 : 2015/02/10            
 * @author : Jung.Jin.Muk(pc123pc@irush.co.kr)                 
 * @수정내역 : 
--%>
<%@ page import="java.util.List" %>
<%@ page import="com.hanaph.gw.co.common.utils.StringUtil" %>
<%@ page import="com.hanaph.gw.co.common.utils.RequestFilterUtil" %>
<%@ page import="com.hanaph.gw.ea.report.vo.ReportVO" %>
<%@ page import="com.hanaph.gw.co.code.vo.CodeVO" %>
<%@ include file ="/common/path.jsp" %>

<%
	List<ReportVO> reportList = (List<ReportVO>)request.getAttribute("reportList");	//내가올린문서 리스트
	List<CodeVO> docuKindList = (List<CodeVO>)request.getAttribute("docuKindList");	//문서종류
	List<CodeVO> docuStateList = (List<CodeVO>)request.getAttribute("docuStateList"); //문서상태
	
	String search_docu_kind = (String)request.getAttribute("search_docu_kind"); //문서 종류 검색
	String search_docu_state = (String)request.getAttribute("search_docu_state"); //문서 상태 검색
	String searchType = StringUtil.nvl((String)request.getAttribute("searchType"),"approval_seq"); //검색 타입
	String searchKeyword = (String)request.getAttribute("searchKeyword"); //검색 키워드
	String search_start_ymd = StringUtil.nvl((String)request.getAttribute("search_start_ymd")); //기안 시작 일
	String search_end_ymd = StringUtil.nvl((String)request.getAttribute("search_end_ymd")); //기안 마지막 일
	int cnt = ((Integer)request.getAttribute("cnt")).intValue(); //내가올린문서 리스트

	String checkMustOpinion = StringUtil.nvl((String)request.getAttribute("checkMustOpinion")); //시행부서 의견 필수


	searchKeyword = StringUtil.nvl(RequestFilterUtil.toHtmlTagReplace("", searchKeyword));

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
				alert("작성일 시작 날짜가 작성일 종료 날짜 보다 이후 입니다.");
				search_start_ymd.focus();
				return
			}
			document.searchForm.action = "<%=GROUP_ROOT%>/ea/report/reportList.do";
			document.searchForm.submit();
		}
		
		/**
		 * 결재 상세 팝업
		 * @param approval_seq
		 */
		function goApprovalPopup(approval_seq){
			var height = 0;
			if(850 < $(window).innerHeight()){
				height = 850; 
			}else{
				height = $(window).innerHeight();
			}
			Commons.popupOpen('appPop','<%=GROUP_ROOT%>/ea/newReport/approvalPopup.do?approval_seq='+approval_seq+'&menu=0202','820',height);
		}
		
		/**
		 * 스텝3 팝업
		 * @param approval_seq
		 * @param docu_seq
		 */
		function goStep3Pop(approval_seq, docu_seq){
			Commons.popupOpen('step3Pop','<%=GROUP_ROOT%>/ea/newReport/step3approvalPopup.do?approval_seq='+approval_seq+'&menu=0202&docu_seq='+docu_seq,'820','850');
		}
		
		/**
		 * 스텝2 팝업
		 * @param approval_seq
		 * @param docu_seq
		 * @param docu_cd
		 */
		function goStep2Pop(approval_seq, docu_seq, docu_cd){
			Commons.popupOpen('step3Pop','<%=GROUP_ROOT%>/ea/newReport/step2approvalPopup.do?approval_seq='+approval_seq+'&docu_cd='+docu_cd+'&docu_seq='+docu_seq,'820','850');
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
	<div class="wrap_send_document">
		<h2>내가올린문서</h2>
		<div class="noticeboard_box">
		<div class="serch_box">
		<form id="searchForm" name="searchForm" method="post">
			<ul class="serch_con01">
				<li>
					<span class="sc_txt02">작성일</span>
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
						%>
								<option value="<%=codeVO.getCd()%>"><%=codeVO.getCd_nm()%></option>
						<%
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
						</select>
					</span>
					<%--<input class="serch_txt03 w486" type="text" id="searchKeyword" name="searchKeyword" value="<%=searchKeyword%>" maxlength="32" onKeyPress="if(event.keyCode=='13'){goSearch(); return false;}" >--%>
					<input class="serch_txt03 w305" type="text" id="searchKeyword" name="searchKeyword" value="<%=searchKeyword%>" maxlength="32" onKeyPress="if(event.keyCode=='13'){goSearch(); return false;}" >
					<%--<span class="sc_txt03">시행부서 의견 필수</span>--%>
					<%-- ml180122.ml22_T61 김진국 - reportList.jsp에 체크박스 '시의필' 만듬 - 체크하면 시의필만 따로 분류해서 조회해서 보여주기 위해서 --%>
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
		
		
		<div class="cont_box cont_table05">
			<form id="frm" name="frm" method="post">
			<input type="hidden" id="search_docu_state" name="search_docu_state" value="<%=search_docu_state %>" />
			<input type="hidden" id="search_docu_kind" name="search_docu_kind" value="<%=search_docu_kind %>" />
			<input type="hidden" id="searchType" name="searchType" value="<%=searchType %>" />
			<input type="hidden" id="searchKeyword" name="searchKeyword" value="<%=searchKeyword %>" />
			<input type="hidden" id="search_start_ymd" name="search_start_ymd" value="<%=search_start_ymd %>" />
			<input type="hidden" id="search_end_ymd" name="search_end_ymd" value="<%=search_end_ymd %>" />
			<input type="hidden" id="checkMustOpinion" name="checkMustOpinion" value="<%=checkMustOpinion %>" />
				<table>
				<colgroup>
					<col width="15%">
					<col width="12%">
					<col width="40%">
					<col width="13%">
					<col width="10%">
				</colgroup>
					<thead>
						<tr>
							<th>문서번호</th>
							<th>문서종류</th>
							<th>제목</th>
							<th>작성일</th>
							<th>문서상태</th>
							<th class="br_none">처리자</th>										
						</tr>
					</thead>
					<tbody>
						<%
						if(reportList.size()!=0){
							for(int i=0; reportList.size()>i; i++){
								ReportVO reportVO = new ReportVO();
								reportVO = reportList.get(i);
								String[] last_line_nm = new String[2]; 
								if(!"".equals(StringUtil.nvl(reportVO.getLast_line_nm()))){
									last_line_nm = reportVO.getLast_line_nm().split("\\^");
								}
						%>
						<%if("1".equals(reportVO.getStep_state())){ %>
						<tr onclick="javascript:goStep2Pop('<%=reportVO.getApproval_seq()%>','<%=reportVO.getDocu_seq()%>','<%=reportVO.getDocu_cd()%>'); return false;"
							<%if("E02003".equals(reportVO.getState())){%>class="ing"<%}else if("E02005".equals(reportVO.getState())){%>class="no"<%}else{%><%}%>>
						<%}else if("E02001".equals(reportVO.getState())){ %>
						<tr onclick="javascript:goStep3Pop('<%=reportVO.getApproval_seq()%>','<%=reportVO.getDocu_seq()%>'); return false;"
							<%if("E02003".equals(reportVO.getState())){%>class="ing"<%}else if("E02005".equals(reportVO.getState())){%>class="no"<%}else{%><%}%>>
						<%}else{ %>
						<tr onclick="javascript:goApprovalPopup('<%=reportVO.getApproval_seq() %>'); return false;"
							<%if("E02003".equals(reportVO.getState())){%>class="ing"<%}else if("E02005".equals(reportVO.getState())){%>class="no"<%}else{%><%}%>>
						<%} %>
							<td><%=StringUtil.nvl(reportVO.getApproval_seq()) %></td>
							<td><%=StringUtil.nvl(reportVO.getDocu_nm()) %></td>
							<td><%=StringUtil.nvl(RequestFilterUtil.toHtmlTagReplace("", reportVO.getSubject()))%></td>
							<td><%=StringUtil.nvl(reportVO.getMake_dt()) %></td>
							<td> 
							    <%=StringUtil.nvl(reportVO.getState_nm()) %></td>
							<td class="br_none"><%=StringUtil.nvl(last_line_nm[1]) %></td>
						</tr>
						<%
							}
						}else{
						%>	
						<tr>
							<td colspan="6" class="br_none">데이터가 없습니다.</td>
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
	
	<%@include file="/include/footer.jsp"%>
</body>
</html>