<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%--
 * @파일명 : shareList.jsp
 * @메뉴명 : 공유문서 리스트
 * @최초작성일 : 2015/01/28            
 * @author : CHOIILJI                 
 * @수정내역 : 
--%>
<%@ page import="java.util.List" %>
<%@ page import="com.hanaph.gw.co.common.utils.StringUtil" %>
<%@ page import="com.hanaph.gw.co.common.utils.RequestFilterUtil" %>
<%@ page import="com.hanaph.gw.ea.share.vo.ShareReportVO" %>
<%@ page import="com.hanaph.gw.co.code.vo.CodeVO" %>   
<%@ include file ="/common/path.jsp" %>
<%
	//공유문서 목록 리스트
	List<ShareReportVO> shareList = (List<ShareReportVO>)request.getAttribute("shareList");
	int shareTotalCount = (Integer)request.getAttribute("shareTotalCount");
	//문서정보 검색 리스트
	List<CodeVO> sCodeList = (List<CodeVO>)request.getAttribute("sCodeList");
	
	String approval_type = StringUtil.nvl((String)request.getAttribute("approval_type"));
	String search_start_dt = StringUtil.nvl((String)request.getAttribute("search_start_dt"));
	String search_end_dt = StringUtil.nvl((String)request.getAttribute("search_end_dt"));
	String search_docu_cd = StringUtil.nvl((String)request.getAttribute("search_docu_cd"));
	String search_condition = StringUtil.nvl((String)request.getAttribute("search_condition"));
	String search_text = RequestFilterUtil.toHtmlTagReplace("", (String)request.getAttribute("search_text"));
	String search_read_yn = StringUtil.nvl((String)request.getAttribute("search_read_yn"));
	String menu = RequestFilterUtil.toHtmlTagReplace("", (String)request.getAttribute("menu"));
%>
<!DOCTYPE html>
<html lang="ko">
<head>
	<%@include file="/include/head.jsp"%>
	<script type="text/javascript">
	function goSearch(){
		if(formCheck.dateChk($("#search_start_dt").val(),$("#search_end_dt").val())<0){
			alert("시작 날짜가 종료 날짜 보다 이후 입니다.");
			return;
		}
		
		if(formCheck.containsChars(document.getElementById("search_text").value, "%")){
			 alert("특수문자를 사용할수 없습니다");
			 return;
		}
		
		document.getElementById("search_frm").action ="<%=GROUP_ROOT%>/ea/share/shareList.do";
		document.getElementById("search_frm").submit();
	}
	
	function goApprovalPopup(approval_seq){
		var height = 0;
		if(850 < $(window).innerHeight()){
			height = 850; 
		}else{
			height = $(window).innerHeight();
		}
		Commons.popupOpen('appPop','<%=GROUP_ROOT%>/ea/newReport/approvalPopup.do?menu=<%=menu%>&approval_seq='+approval_seq,'820',height);
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
	<div class="wrap_receive_document_collabo">
		<h2>공유문서조회</h2>
		<div class="noticeboard_box">
		<form name="search_frm" id="search_frm" method="post">
		<input type="hidden" name="approval_type" id="approval_type" value="<%=approval_type%>">
		<input type="hidden" name="menu" id="menu" value="<%=menu%>">
		<div class="serch_box">
			<ul class="serch_con01">
				<li>
					<span class="sc_txt02">기안일</span>
					<span class="serch_date_box">
						<input id="search_start_dt" name="search_start_dt" class="search_date" type="text" value="<%=search_start_dt%>">
						<button type="button" class="btn_date"><span class="blind">날짜선택</span></button>
					</span> ~ 
					<span class="serch_date_box">
						<input id="search_end_dt" name="search_end_dt" class="search_date" type="text" value="<%=search_end_dt%>">
						<button type="button" class="btn_date"><span class="blind">날짜선택</span></button>
					</span>
					<span class="sc_txt02">문서종류</span>
					<select id="search_docu_cd" name="search_docu_cd" class="serch_select02 w96">
						<option value="">전체</option>
						<%
							if(sCodeList.size()!=0){
								for(int i=0; sCodeList.size()>i;i++){
									CodeVO codeVO = new CodeVO();
									codeVO = sCodeList.get(i);
						%>
						<option value="<%=codeVO.getCd()%>" <%if(codeVO.getCd().equals(search_docu_cd)){%>selected<%}%>><%=codeVO.getCd_nm()%></option>
						<%
							}
										}
						%>
					</select>
					<span class="sc_txt02">열람여부</span>
					<select id="search_read_yn" name="search_read_yn" class="serch_select02 w96">
						<option value="">전체</option>
						<option value="Y" <%if("Y".equals(search_read_yn)){%>selected<%}%>>Y</option>						
						<option value="N" <%if("N".equals(search_read_yn)){%>selected<%}%>>N</option>
					</select>
				</li>
				<li class="cont02">
					<span class="sc_txt">
						<select id="search_condition" name="search_condition" class="search_select02 w116">
							<option value="SEQ" <%if(search_condition.equals("SEQ")){%>selected<%}%>>문서번호</option>
							<option value="SBJECT" <%if(search_condition.equals("SBJECT")){%>selected<%}%>>제목</option>
							<option value="NAME" <%if(search_condition.equals("NAME")){%>selected<%}%>>기안자</option>
						</select>
					</span>
					<input id="search_text" name="search_text" class="serch_txt03 w486" type="text" value="<%=search_text%>" onKeyPress="if(event.keyCode=='13'){goSearch(); return false;}">
				</li>
			</ul>
			<span class="serch_btn"><button type="button" onclick="javascript:goSearch(); return false;">검색</button></span>
		</div>
		</form>
		<div class="cont_box cont_table05">
			<form id="frm" name="frm" method="post">
				<input type="hidden" name="approval_type" id="approval_type" value="<%=approval_type%>"/>
				<input type="hidden" id="search_start_dt" name="search_start_dt" value="<%=search_start_dt%>"/>
				<input type="hidden" id="search_end_dt" name="search_end_dt" value="<%=search_end_dt%>"/>
				<input type="hidden" id="search_docu_cd" name="search_docu_cd" value="<%=search_docu_cd%>"/>
				<input type="hidden" id="search_condition" name="search_condition" value="<%=search_condition%>"/>
				<input type="hidden" id="search_text" name="search_text" value="<%=search_text%>"/>
				<input type="hidden" id="search_read_yn" name="search_read_yn" value="<%=search_read_yn%>"/>
				<table>
				<colgroup>
					<col width="7%">
					<col width="18%">
					<col width="11%">
					<col width="">
					<col width="8%">
					<col width="12%">
					<col width="10%">
					<col width="8%">
				</colgroup>
					<thead>
						<tr>
							<th>열람</th>
							<th>문서번호</th>
							<th>종류</th>
							<th>제목</th>
							<th>기안자</th>
							<th>기안일</th>
							<th>문서상태</th>
							<th class="br_none">처리자</th>										
						</tr>
					</thead>
					<tbody>
					<%
						if(shareList.size()!=0){
							for(int i=0; shareList.size()>i;i++){
								ShareReportVO shareVO = new ShareReportVO();
								shareVO = shareList.get(i);
								String last_line_nm = "";
								if(!"".equals(StringUtil.nvl(shareVO.getLast_line_nm()))){
									last_line_nm = shareVO.getLast_line_nm().split("\\^")[1];
								}
					%>
						<tr onclick="javascript:goApprovalPopup('<%=shareVO.getApproval_seq() %>'); return false;">
							<td><%=StringUtil.nvl(shareVO.getRead_yn()) %></td>
							<td><%=StringUtil.nvl(shareVO.getApproval_seq()) %></td>
							<td><%=StringUtil.nvl(shareVO.getDocu_nm()) %></td>
							<td><%=RequestFilterUtil.toHtmlTagReplace("", shareVO.getSubject()) %></td>
							<td><%=StringUtil.nvl(shareVO.getEmp_ko_nm()) %></td>
							<td><%=StringUtil.nvl(shareVO.getReq_dt()) %></td>
							<td><%=StringUtil.nvl(shareVO.getState_nm()) %></td>
							<td class="br_none"><%=last_line_nm %></td>										
						</tr>
						
					<%
							}
						}else{
					%>	
						<tr>
							<td colspan="8" class="br_none">데이터가 없습니다.</td>
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
				* 총 건수 : <span class="cnt"><%=StringUtil.makeMoneyType(shareTotalCount)%></span>건
			</div>
			<%@ include file ="/common/paging.jsp" %>
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