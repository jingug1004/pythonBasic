<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%--
 * @파일명 : insuranceDetail.jsp
 * @메뉴명 : 건강보험료 상세
 * @최초작성일 : 2015/01/28            
 * @author : CHOIILJI                 
 * @수정내역 : 
--%>
<%@ page import="java.util.List" %>
<%@ page import="com.hanaph.gw.co.common.utils.StringUtil" %>
<%@ page import="com.hanaph.gw.pe.member.vo.InsuranceVO" %>   
<%@ include file ="/common/path.jsp" %>
<%
	InsuranceVO insuranceDetail = (InsuranceVO)request.getAttribute("insuranceDetail");
	String currentPage = StringUtil.nvl((String)request.getAttribute("currentPage"));
	String import_year = (String)request.getAttribute("import_year");
%>
<!DOCTYPE html>
<html lang="ko">
<head>
	<%@include file="/include/head.jsp"%>
	<script type="text/javascript">
	/**
	 * 목록이동 
	 */
	function goList(){
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
				<div class="wrap_health_insurance" id="insurance">
					<div class="wrap_btn insurance">
						<button class="type_01" onclick="javascripr:Commons.previewOpen('insurance', '785', '768');">미리보기</button>
						<button class="type_01" onclick="javascript:goList(); return false;">목록</button>
					</div>
					<div class="wrap_list mt10">
						<div class="tableA">
							<form name="frm" id="frm" method="post" action ="<%=GROUP_ROOT %>/pe/member/insuranceList.do">
								<input type="hidden" id="currentPage" name="currentPage" value="<%=currentPage%>">
							</form>	
							<table>
								<colgroup>
									<col style="width:25%;" />
									<col style="width:25%;" />
									<col style="width:25%;" />
									<col style="width:25%;" />
								</colgroup>
								<tr>
									<td colspan="4"><h3><%=import_year %>년 건강보험 연말정산 환급/징수 금</h3></td>
								</tr>
								<tr>
									<th rowspan="2">총 급여</th>
									<td rowspan="2" class="txt_r"><%=StringUtil.cleanup(StringUtil.nvl(insuranceDetail.getT_pay())) %></td>
									<th>근무개월</th>
									<td class="txt_r"><%=StringUtil.cleanup(StringUtil.nvl(insuranceDetail.getMonth())) %>개월</td>
								</tr>
								<tr>
									<th>보수월액</th>
									<td class="txt_r"><%=StringUtil.cleanup(StringUtil.nvl(insuranceDetail.getMonth_pay())) %></td>
								</tr> 
								<tr>
									<th rowspan="2">납부한 총 보험료</th>
									<td rowspan="2" class="txt_r"><%=StringUtil.cleanup(StringUtil.nvl(insuranceDetail.getPayment_1())) %></td>
									<th>납부한 총 보험료(건강)</th>
									<td class="txt_r"><%=StringUtil.cleanup(StringUtil.nvl(insuranceDetail.getPayment_2())) %></td>
								</tr>
								<tr>
									<th>납부한 총 보험료(장기요양)</th>
									<td class="txt_r"><%=StringUtil.cleanup(StringUtil.nvl(insuranceDetail.getPayment_3())) %></td>
								</tr> 
								<tr>
									<th rowspan="2">확정보험료</th>
									<td rowspan="2" class="txt_r"><%=StringUtil.cleanup(StringUtil.nvl(insuranceDetail.getDecide_1())) %></td>
									<th>확정보험료(건강)</th>
									<td class="txt_r"><%=StringUtil.cleanup(StringUtil.nvl(insuranceDetail.getDecide_2())) %></td>
								</tr>
								<tr>
									<th>확정보험료(장기요양)</th>
									<td class="txt_r"><%=StringUtil.cleanup(StringUtil.nvl(insuranceDetail.getDecide_3())) %></td>
								</tr> 
								<tr>
									<th rowspan="2">가입자부담금</th>
									<td rowspan="2" class="txt_r"><%=StringUtil.cleanup(StringUtil.nvl(insuranceDetail.getBurden_1())) %></td>
									<th>가입자부담금(건강)</th>
									<td class="txt_r"><%=StringUtil.cleanup(StringUtil.nvl(insuranceDetail.getBurden_2())) %></td>
								</tr>
								<tr>
									<th>가입자부담금(장기요양)</th>
									<td class="txt_r"><%=StringUtil.cleanup(StringUtil.nvl(insuranceDetail.getBurden_3())) %></td>
								</tr> 
								<tr>
									<td colspan="4"><span class="fc_b">※ 가입자 부담금은 4월 급여에 공제 (+공제, -환급)</span></td>
								</tr>
								<tr>
									<th>월납입보험료</th>
									<td colspan="3" class="txt_r"><%=StringUtil.cleanup(StringUtil.nvl(insuranceDetail.getTemp_3())) %></td>
								</tr>
								<tr>
									<td colspan="4"><span class="fc_b">※ <%= Integer.parseInt(StringUtil.nvl(insuranceDetail.getImport_year()))%>년 04월 ~ <%= Integer.parseInt(StringUtil.nvl(insuranceDetail.getImport_year()))+1%>년 03월 까지 건강 보험료 적용</span></td>
								</tr>
							</table>
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