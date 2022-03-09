<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%--
 * @파일명 : approvalHistoryList.jsp
 * @메뉴명 : 결재상태이력
 * @최초작성일 : 2015/02/10            
 * @author : Jung.Jin.Muk(pc123pc@irush.co.kr)                 
 * @수정내역 : 
--%>
<%@ page import="java.util.List" %>
<%@ page import="com.hanaph.gw.co.common.utils.StringUtil" %>
<%@ page import="com.hanaph.gw.ea.newReport.vo.ApprovalMasterVO" %>
<%@ page import="com.hanaph.gw.ea.newReport.vo.ApprovalVO" %>
<%@ include file ="/common/path.jsp" %>
<%
	/* 결재 */
	List<ApprovalVO> approvalDetailList = (List<ApprovalVO>)request.getAttribute("approvalDetailList");
	
	/* 결재마스터 */
	ApprovalMasterVO approvalMasterVO = (ApprovalMasterVO)request.getAttribute("approvalMasterVO");
%>
<!DOCTYPE html>
<html lang="ko">
<head>
	<%@include file="/include/head.jsp"%>	
</head>
<body>
<div class="wrap_pop_bg">
	<div class="wrap_statement_overtime">
		<div class="top">
			<h3><%=approvalMasterVO.getReq_dt() %><span> <%=approvalMasterVO.getSubject() %></span></h3>
		</div>
		<div class="inner">
			<div class="cont_box cont_table06">
				<div class="pop_tit">
					<span class="tit">결재상태이력</span>
					<p>현재 진행된 결재상태 이력이 나옵니다.</p>
				</div>
				<div class="wrap_tbl_record">
					<table class="tbl_record">
						<colgroup>
							<col style="" class="section">
							<col style="" class="team">
							<col style="" class="grade">
							<col style="" class="person">
							<col style="" class="approve">
							<col style="" class="read">
							<col style="">
						</colgroup>
						<thead>
							<tr>
								<th>순서</th>
								<th>부서</th>
								<th>직급</th>
								<th>기안자</th>
								<th>결재</th>
								<th>열람시간</th>
								<th>결재/반려시간</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td colspan="7" class="box_scroll">
									<div>
										<table class="tbl_record">
											<colgroup>
												<col style="" class="section2">
												<col style="" class="team">
												<col style="" class="grade">
												<col style="" class="person">
												<col style="" class="approve">
												<col style="" class="read">
												<col style="">
											</colgroup>
											<tbody>
											<%
											if(approvalDetailList.size() > 0){
												for(int i=0; approvalDetailList.size()>i;i++){
													ApprovalVO approvalVO = new ApprovalVO();
													approvalVO = approvalDetailList.get(i);
											%>
												<tr>
													<td><%=i+1%></td>
													<td><%=StringUtil.nvl(approvalVO.getDept_nm()) %></td>
													<td><%=StringUtil.nvl(approvalVO.getGrad_ko_nm()) %></td>
													<td><%=StringUtil.nvl(approvalVO.getEmp_ko_nm()) %></td>
													<td><%=StringUtil.nvl(approvalVO.getState_nm(),"미결") %></td>
													<td><%=StringUtil.nvl(approvalVO.getRead_dt()) %></td>
													<td class="bdrn"><%=StringUtil.nvl(approvalVO.getApproval_dt()) %></td>
												</tr>
											<%
												}
											}else{
											%>
												<tr>
													<td colspan="7">결재 이력이 없습니다.</td>
												</tr>
											<%
											}
											%>
											</tbody>
										</table>
									</div>
								</td>
							</tr>							
						</tbody>
					</table>
				</div>
			</div>
		</div>
		<div class="box_close"><button type="button" onclick="parent.layerClose()">닫기</button></div>
		<button type="button" class="close" onclick="parent.layerClose()"><span class="blind">닫기</span></button>
	</div>
</div>	
</body>
</html>