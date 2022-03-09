<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%--
 * @파일명 : approvalPreviewPopup.jsp
 * @메뉴명 : 결재문서 미리보기
 * @최초작성일 : 2015/01/28            
 * @author : CHOIILJI                 
 * @수정내역 : 
--%>
<%@ page import="java.util.List" %>
<%@ page import="com.hanaph.gw.co.common.utils.StringUtil" %>
<%@ page import="com.hanaph.gw.co.common.utils.RequestFilterUtil" %>
<%@ page import="com.hanaph.gw.co.fileAttach.vo.FileAttachVO" %>
<%@ page import="com.hanaph.gw.ea.mgrDocuInfo.vo.DocumentInfoVO" %>
<%@ page import="com.hanaph.gw.ea.newReport.vo.ApprovalMasterVO" %>
<%@ page import="com.hanaph.gw.ea.newReport.vo.ApprovalVO" %>
<%@ page import="com.hanaph.gw.ea.newReport.vo.AccidentVO" %>
<%@ page import="com.hanaph.gw.ea.newReport.vo.ImplDeptVO" %>
<%@ include file ="/common/path.jsp" %>

<%
	/* 첨부파일 */
	List<FileAttachVO> attachList = (List<FileAttachVO>)request.getAttribute("attachList");
	/* 결재마스터 */
	ApprovalMasterVO approvalMasterVO = (ApprovalMasterVO)request.getAttribute("approvalMasterVO");
	/* 결재 */
	List<ApprovalVO> approvalDetailList = (List<ApprovalVO>)request.getAttribute("approvalDetailList");
		
	String state = StringUtil.nvl((String)request.getAttribute("state"));
	String docu_cd = StringUtil.nvl((String)request.getAttribute("docu_cd"));	//문서코드
	String approval_seq = StringUtil.nvl((String)request.getAttribute("approval_seq"));	//결재 문서번호	
	String includePage = "preview"+docu_cd+".jsp";	//신규문서 파일명
	
	if(approvalMasterVO == null){
		approvalMasterVO = new ApprovalMasterVO();
	}
%>
<!DOCTYPE html>
<html lang="ko">
<head>
	<%@include file="/include/head.jsp"%>
	<link rel="stylesheet" type="text/css" media="all" href="<%=GROUP_WEB_ROOT%>/css/styles.css" />
	<script type="text/javascript" src="<%=GROUP_WEB_ROOT%>/js/fileDrag.js"></script>
	<script type="text/javascript">
	
	
	</script>
</head>
<body>
<div class="wrap_receive_document wrap_document_print" id="appPrint">
	<div class="top appPrint">
		<h3>인쇄<span> <%=RequestFilterUtil.toHtmlTagReplace("", approvalMasterVO.getSubject()) %></span></h3>
		<button type="button" class="close" onclick="javascript:window.close();"><span class="blind">닫기</span></button>
		<div>
			<div class="fr">
				<button type="button" class="type2" onclick="javascripr:Commons.directPreviewOpen('appPrint', '785', '768');">인쇄</button>
			</div>
		</div>
	</div>
	<div class="inner">
		<div class="cont_box cont_table06">
			<h4><strong><%=approvalMasterVO.getDocu_nm() %></strong></h4>
			<div class="refer">
				<ul>
					<li><%if("Y".equals(approvalMasterVO.getDecide_yn())){ %>전결 가능합니다.<%} %></li>
					<li><%if("Y".equals(approvalMasterVO.getSecurity_yn())){ %>대외비입니다.<%} %></li>
				</ul>
			</div>
			<table class="tbl_register">
				<colgroup>
					<col style="width:16%">
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
						<th>결재선</th>
						<%
						if(approvalDetailList.size() > 0){
							for(int i=0; approvalDetailList.size()>i;i++){
								ApprovalVO approvalVO = new ApprovalVO();
								approvalVO = approvalDetailList.get(i);
						%>
		
						<td <%if(i == 6){ %>class="bdr2"<%} %>><%=StringUtil.nvl(approvalVO.getEmp_ko_nm()) %>
						<br /><%=StringUtil.nvl(approvalVO.getApproval_dt())%><br /></td>
						<%	}
						} %>
						<%if(approvalDetailList.size() < 7){ 
							for(int i=0; 7-approvalDetailList.size()>i;i++){
						%>	
						<td <%if(i == 7-approvalDetailList.size()-1){ %>class="bdr2"<%} %>></td>		
						<%	} 
						}%>
					</tr>
				</tbody>
			</table>
			<table class="tbl_info">
				<colgroup>
					<col style="width:99px">
					<col style="">
					<col style="width:99px">
					<col style="">
				</colgroup>
				<tbody>	
					<tr>
						<th>문서번호</th>
						<td><%=approval_seq %></td>
						<th>작성일자</th>
						<td class="bdr2"><%=StringUtil.nvl(approvalMasterVO.getMake_dt())%></td>
					</tr>
					<tr>
						<th>작성부서</th>
						<td><%=StringUtil.nvl(approvalMasterVO.getDept_ko_nm())%></td>
						<th>작성자</th>
						<td class="bdr2"><%=StringUtil.nvl(approvalMasterVO.getEmp_ko_nm())%></td>
					</tr>
					<tr>
						<th>제목</th>
						<td colspan="3" class="bdr2"><%=RequestFilterUtil.toHtmlTagReplace("", approvalMasterVO.getSubject()) %></td>
					</tr>
				</tbody>
			</table>
			<jsp:include page="<%=includePage%>"/>
		</div>
	</div>
</div>
</body>
</html>