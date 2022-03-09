<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%--
 * @파일명 : contractList.jsp
 * @메뉴명 : 연봉계약서 리스트
 * @최초작성일 : 2015/01/28            
 * @author : CHOIILJI                 
 * @수정내역 : 
--%>
<%@ page import="java.util.List" %>
<%@ page import="com.hanaph.gw.co.common.utils.StringUtil" %>
<%@ page import="com.hanaph.gw.pe.member.vo.ContractVO" %>   
<%@ include file ="/common/path.jsp" %>
<%
	List<ContractVO> contractList = (List<ContractVO>)request.getAttribute("contractList");
	int contractTotalCount = (Integer)request.getAttribute("contractTotalCount");
%>
<!DOCTYPE html>
<html lang="ko">
<head>
	<%@include file="/include/head.jsp"%>
	<script type="text/javascript">
	/**
	 * 상세페이지 이동
	 */
	function goContract(ymd_start, ymd_end){
		document.getElementById("ymd_start").value = ymd_start;
		document.getElementById("ymd_end").value = ymd_end;
		document.getElementById("frm").action ="<%=GROUP_ROOT%>/pe/member/contractDetail.do";
		document.getElementById("frm").submit();
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
			<div class="cont float_left">
			<h2>연봉계약서</h2>
			<div class="wrap_pay_on_inquiry">
				<div class="wrap_list mt20">
					<div class="tableA">
					<form name="frm" id="frm" method="post">
						<input type="hidden" id="ymd_start" name="ymd_start">
						<input type="hidden" id="ymd_end" name="ymd_end">
						<table>
							<thead>
								<tr>
									<th>계약기간</th>
									<th>연봉지급액</th>
									<th>해당기간퇴직금</th>
									<th>근무개월</th>
									<th class="br_none">연봉계약서출력일</th>										
								</tr>
							</thead>
							<tbody>	
								<%
										if(contractList.size()!=0){
																for(int i=0; contractList.size()>i;i++){
																	ContractVO contractVO = new ContractVO();
																	contractVO = contractList.get(i);
									%>
								<tr style="cursor:pointer;" class="bg_d" onclick="javascript:goContract('<%=StringUtil.nvl(contractVO.getYmd_start())%>','<%=StringUtil.nvl(contractVO.getYmd_end())%>'); return false;">
									<td><%=StringUtil.nvl(contractVO.getYmd_start_ko())%> ~ <%=StringUtil.nvl(contractVO.getYmd_end_ko())%></td>
									<td><%=StringUtil.makeMoneyType(contractVO.getAnnual_salary())%> 원</td>
									<td><%=StringUtil.makeMoneyType(contractVO.getSeverance_pay())%> 원</td>
									<td><%=StringUtil.makeMoneyType(contractVO.getWork_months())%> 개월</td>
									<td class="br_none"><%=StringUtil.nvl(contractVO.getYmd_output_ko())%></td>
								</tr>
								<%
									} 
														}else{
								%>
								<tr>
									<td colspan="5">데이터가 없습니다.</td>
								</tr>
								<%
									}
								%>
							</tbody>
						</table>
					</form>
					</div>
				</div>
				<div class="paging">
					<div class="wrap_total">
						* 총 건수 : <span class="cnt"><%=StringUtil.makeMoneyType(contractTotalCount)%></span>건
					</div>
					<%@ include file ="/common/paging.jsp" %>
				</div>				
			</div>
		</div>
		</div>
	</div>
	<%@include file="/include/footer.jsp"%>
</body>
</html>