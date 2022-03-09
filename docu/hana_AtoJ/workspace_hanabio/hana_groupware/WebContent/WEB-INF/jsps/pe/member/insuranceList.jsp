<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%--
 * @파일명 : insuranceList.jsp
 * @메뉴명 : 건강보험료 리스트
 * @최초작성일 : 2015/01/28            
 * @author : CHOIILJI                 
 * @수정내역 : 
--%>
<%@ page import="java.util.List" %>
<%@ page import="com.hanaph.gw.co.common.utils.StringUtil" %>
<%@ page import="com.hanaph.gw.pe.member.vo.InsuranceVO" %>   
<%@ include file ="/common/path.jsp" %>
<%
	List<InsuranceVO> insuranceList = (List<InsuranceVO>)request.getAttribute("insuranceList");
	int insuranceTotalCount = (Integer)request.getAttribute("insuranceTotalCount");
%>
<!DOCTYPE html>
<html lang="ko">
<head>
	<%@include file="/include/head.jsp"%>
	<script type="text/javascript">
	/**
	 * 상세페이지 이동
	 */
	function goInsurance(import_year){
		document.getElementById("import_year").value = import_year;
		document.getElementById("frm").action ="<%=GROUP_ROOT%>/pe/member/insuranceDetail.do";
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
			
			<!-- ######## start ####### -->
			
			<div class="cont float_left">
				<h2>건강보험료</h2>
				<div class="wrap_pay_on_inquiry">
					<div class="wrap_list mt20">
						<div class="tableA">
							<form name="frm" id="frm" method="post">
								<input type="hidden" id="import_year" name="import_year">
							<table>
								<thead>
									<tr>
										<th>대상기간</th>
										<th>총 급여</th>
										<th>납부한 총 보험료</th>
										<th>확정보험료</th>
										<th>가입자부담금</th>
										<th>월납입보험료</th>										
									</tr>
								</thead>
								<tbody>
									<%
										if(insuranceList.size()!=0){
																	for(int i=0; insuranceList.size()>i;i++){
																		InsuranceVO insuranceVO = new InsuranceVO();
																		insuranceVO = insuranceList.get(i);
									%>
									<tr style="cursor:pointer;" class="bg_d" onclick="javascript:goInsurance('<%=StringUtil.cleanup(StringUtil.nvl(insuranceVO.getImport_year()))%>'); return false;">
										<td><%=StringUtil.cleanup(StringUtil.nvl(insuranceVO.getImport_year()))%></td>
										<td><%=StringUtil.cleanup(StringUtil.nvl(insuranceVO.getT_pay()))%></td>
										<td><%=StringUtil.cleanup(StringUtil.nvl(insuranceVO.getPayment_1()))%></td>
										<td><%=StringUtil.cleanup(StringUtil.nvl(insuranceVO.getDecide_1()))%></td>
										<td><%=StringUtil.cleanup(StringUtil.nvl(insuranceVO.getBurden_1()))%></td>
										<td><%=StringUtil.cleanup(StringUtil.nvl(insuranceVO.getTemp_3()))%></td>
									</tr>
									<%
										} 
																}else{
									%>
									<tr>
										<td colspan="6">데이터가 없습니다.</td>
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
								* 총 건수 : <span class="cnt"><%=StringUtil.makeMoneyType(insuranceTotalCount)%></span>건
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