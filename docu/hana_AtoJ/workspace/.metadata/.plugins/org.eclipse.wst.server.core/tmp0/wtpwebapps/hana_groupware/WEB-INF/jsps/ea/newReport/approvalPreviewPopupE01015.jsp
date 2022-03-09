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

<div class="wrap_pop_bg2 no_bg">
	<div class="wrap_receive_document_h wrap_document_print_h" id="appPrint">
		<div class="top appPrint">
			<h3>인쇄<span> <%=RequestFilterUtil.toHtmlTagReplace("", approvalMasterVO.getSubject()) %></span></h3>
			<button class="close" onclick="javascript:window.close();"><span class="blind">닫기</span></button>
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
				    <col style="width:12%;" />
				    <col style="width:13%;" />
				    <col style="width:12%;" />
				    <col style="width:13%;" />
				    <col style="width:12%;" />
				    <col style="width:13%;" />
				    <col style="width:12%;" />
				    <col style="width:13%;" />
			      </colgroup>
				  <tbody>
				    <tr>
				      <th>문서번호</th>
				      <td>123456789</td>
				      <th style="border-bottom: none;">작성일자</th>
				      <td>2015. 01. 15</td>
				      <th>작성부서</th>
				      <td>구매부</td>
				      <th>작성자</th>
				      <td class="bdr2">홍길동</td>
			        </tr>
				    <tr>
				      <th>청구결재</th>
				      <td colspan="3" class="confirm_box">
				      	<table>
						  <colgroup>
						    <col style="width:20%;" />
						    <col style="width:20%;" />
						    <col style="width:20%;" />
						    <col style="width:20%;" />
						    <col style="width:20%;" />
					      </colgroup>
						  <tbody>
						    <tr>
						      <th>test</th>
						      <th></th>
						      <th></th>
						      <th></th>
						      <th></th>
					        </tr>
						    <tr>
						      <td>test</td>
						      <td></td>
						      <td></td>
						      <td></td>
						      <td></td>
					        </tr>
					      </tbody>
						</table>
				      </td>
				      <th>확인결재</th>
				      <td colspan="3" class="confirm_box bdr2">
				      	<table>
						  <colgroup>
						    <col style="width:20%;" />
						    <col style="width:20%;" />
						    <col style="width:20%;" />
						    <col style="width:20%;" />
						    <col style="width:20%;" />
					      </colgroup>
						  <tbody>
						    <tr>
						      <th>test</th>
						      <th></th>
						      <th></th>
						      <th></th>
						      <th></th>
					        </tr>
						    <tr>
						      <td>test</td>
						      <td></td>
						      <td></td>
						      <td></td>
						      <td></td>
					        </tr>
					      </tbody>
						</table>
				      </td>
			        </tr>
				    <tr>
				      <th>시행부서</th>
				      <td colspan="7" class="bdr2 text_l">구매부</td>
			        </tr>
				    <tr>
				      <th>제        목</th>
				      <td colspan="7" class="bdr2 text_l"><%=RequestFilterUtil.toHtmlTagReplace("", approvalMasterVO.getSubject()) %></td>
			        </tr>
			      </tbody>
				</table>
			<jsp:include page="<%=includePage%>"/>
		</div>
	</div>
</div>
</body>
</html>