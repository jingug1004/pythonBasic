<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%--
 * @파일명 : salaryDetail.jsp
 * @메뉴명 : 급여조회 상세
 * @최초작성일 : 2015/01/28            
 * @author : CHOIILJI                 
 * @수정내역 : 
--%>
<%@ page import="java.util.List" %>
<%@ page import="com.hanaph.gw.co.common.utils.StringUtil" %>
<%@ page import="com.hanaph.gw.pe.member.vo.SalaryVO" %>   
<%@ include file ="/common/path.jsp" %>
<%
	SalaryVO salaryDetail = (SalaryVO)request.getAttribute("salaryDetail");
	String currentPage = StringUtil.nvl((String)request.getAttribute("currentPage"));
	String search_start_dt = StringUtil.nvl((String)request.getAttribute("search_start_dt"));
	String search_end_dt = StringUtil.nvl((String)request.getAttribute("search_end_dt"));
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
				<h2>급여조회</h2>
				<div class="wrap_pay_on_inquiry" id="salary">
					<div class="wrap_btn salary">
						<button type="button" class="type_01" onclick="javascripr:Commons.previewOpen('salary', '785', '768');">미리보기</button>
						<button type="button" class="type_01" onclick="javascript:goList(); return false;">목록</button>
					</div>
					<div class="wrap_list mt10">
						<div class="tableA">
							<form name="frm" id="frm" method="post" action="<%=GROUP_ROOT %>/pe/member/salaryList.do">
								<input type="hidden" id="pay_date" name="pay_date">
								<input type="hidden" id="currentPage" name="currentPage" value="<%=currentPage%>">
								<input type="hidden" id="search_start_dt" name="search_start_dt" value="<%=search_start_dt%>">
								<input type="hidden" id="search_end_dt" name="search_end_dt" value="<%=search_end_dt%>">
							</form>		
							<table>
								<colgroup>
									<col style="width:25%;" />
									<col style="width:25%;" />
									<col style="width:25%;" />
									<col style="width:25%;" />
								</colgroup>
								<tr>
									<td colspan="4"><h3><%=salaryDetail.getPay_date() %> 정기급여 (하나제약)<h3></td>
								</tr>
								<tr>
									<th>부서</th>
									<th>직위</th>
									<th>사번</th>
									<th>성명</th>
								</tr>
								<tr>
									<td><%=StringUtil.nvl(salaryDetail.getDept_name()) %></td>
									<td><%=StringUtil.nvl(salaryDetail.getJik_name()) %></td>
									<td><%=StringUtil.nvl(salaryDetail.getEmp_no()) %></td>
									<td><%=StringUtil.nvl(salaryDetail.getEmp_name()) %></td>
								</tr>
							</table>
						</div>
						<div class="tableA">
							<table>
								<colgroup>
									<col style="width:20%;" />
									<col style="width:20%;" />
									<col style="width:20%;" />
									<col style="width:20%;" />
									<col style="width:20%;" />
								</colgroup>
								<tr>
									<th>기본급</th>
									<th>상여금</th>
									<th>직책수당</th>
									<th>가족수당</th>
									<th>연장근로수당</th>
								</tr>
								<tr>
									<td><%=StringUtil.makeMoneyType(salaryDetail.getPay_amt1()) %></td>
									<td><%=StringUtil.makeMoneyType(salaryDetail.getPay_amt18()) %></td>
									<td><%=StringUtil.makeMoneyType(salaryDetail.getPay_amt9()) %></td>
									<td><%=StringUtil.makeMoneyType(salaryDetail.getPay_amt11()) %></td>
									<td><%=StringUtil.makeMoneyType(salaryDetail.getPay_amt20()) %></td>
								</tr>
								<tr>
									<th>기타지급</th>
									<th>상여근태감액</th>
									<th>특수직무수당</th>
									<th>업무수당</th>
									<th>휴일근로수당</th>
								</tr>
								<tr>
									<td><%=StringUtil.makeMoneyType(salaryDetail.getPay_amt15()) %></td>
									<td><%=StringUtil.makeMoneyType(salaryDetail.getPay_amt32()) %></td>
									<td><%=StringUtil.makeMoneyType(salaryDetail.getPay_amt23()) %></td>
									<td><%=StringUtil.makeMoneyType(salaryDetail.getPay_amt6()) %></td>
									<td><%=StringUtil.makeMoneyType(salaryDetail.getPay_amt22()) %></td>
								</tr>
								<tr>
									<th>기타수당</th>
									<th>식대보조비</th>
									<th>식대보조</th>
									<th>생산비과세</th>
									<th>인센티브</th>
								</tr>
								<tr>
									<td><%=StringUtil.makeMoneyType(salaryDetail.getPay_amt14()) %></td>
									<td><%=StringUtil.makeMoneyType(salaryDetail.getPay_amt7()) %></td>
									<td><%=StringUtil.makeMoneyType(salaryDetail.getPay_amt8()) %></td>
									<td><%=StringUtil.makeMoneyType(salaryDetail.getPay_amt5()) %></td>
									<td><%=StringUtil.makeMoneyType(salaryDetail.getPay_amt19()) %></td>
								</tr>
								<tr>
									<th>자격수당</th>
									<th>출납수당</th>
									<th>야간근로수당</th>
									<th>누락분</th>
									<th rowspan="2" class="bg_w"></th>
								</tr>
								<tr>
									<td><%=StringUtil.makeMoneyType(salaryDetail.getPay_amt17()) %></td>
									<td><%=StringUtil.makeMoneyType(salaryDetail.getPay_amt3()) %></td>
									<td><%=StringUtil.makeMoneyType(salaryDetail.getPay_amt21()) %></td>
									<td><%=StringUtil.makeMoneyType(salaryDetail.getPay_amt12()) %></td>									
								</tr>
								<tr>
									<th colspan="3">급여총액</th>
									<td colspan="2" class="txt_r"><%=StringUtil.makeMoneyType(salaryDetail.getPay_amt46()) %></td>
								</tr>
								<tr>
									<th>소득세</th>
									<th>주민세</th>
									<th>국민연금</th>
									<th>건강보험</th>
									<th>고용보험</th>
								</tr>
								<tr>
									<td><%=StringUtil.makeMoneyType(salaryDetail.getPay_amt27()) %></td>
									<td><%=StringUtil.makeMoneyType(salaryDetail.getPay_amt28()) %></td>
									<td><%=StringUtil.makeMoneyType(salaryDetail.getPay_amt29()) %></td>
									<td><%=StringUtil.makeMoneyType(salaryDetail.getPay_amt30()) %></td>
									<td><%=StringUtil.makeMoneyType(salaryDetail.getPay_amt31()) %></td>
								</tr>
								<tr>
									<th>가지급금</th>
									<th>사우회비</th>
									<th>사우회비상환</th>
									<th>기타공제</th>
									<th>소득세(연말)</th>
								</tr>
								<tr>
									<td><%=StringUtil.makeMoneyType(salaryDetail.getPay_amt37()) %></td>
									<td><%=StringUtil.makeMoneyType(salaryDetail.getPay_amt40()) %></td>
									<td><%=StringUtil.makeMoneyType(salaryDetail.getPay_amt41()) %></td>
									<td><%=StringUtil.makeMoneyType(salaryDetail.getPay_amt38()) %></td>
									<td><%=StringUtil.makeMoneyType(salaryDetail.getPay_amt24()) %></td>
								</tr>
								<tr>
									<th>주민세(연말)</th>
									<th>농특세(연말)</th>
									<th>보증보험</th>									
									<%
									if( salaryDetail.getPay_amt10() != 0 ){
										%>
										<th>건강보험환급금</th>										
									<%}
									else {%>
										<th rowspan="2" class="bg_w"></th>
									<%}
									%>
									<th rowspan="2" class="bg_w"></th>
								</tr>
								<tr>
									<td><%=StringUtil.makeMoneyType(salaryDetail.getPay_amt25()) %></td>
									<td><%=StringUtil.makeMoneyType(salaryDetail.getPay_amt26()) %></td>
									<td><%=StringUtil.makeMoneyType(salaryDetail.getPay_amt45()) %></td>
									<%
									if( salaryDetail.getPay_amt10() != 0 ){
										%>
										<td><%=StringUtil.makeMoneyType(salaryDetail.getPay_amt10()) %></td>
									<%}
									%>
								</tr>
								<tr>
									<th colspan="3">공제계</th>
									<td colspan="2" class="txt_r"><%=StringUtil.makeMoneyType(salaryDetail.getPay_amt47()) %></td>
								</tr>
								<tr>
									<th colspan="3">순지급액</th>
									<td colspan="2" class="txt_r"><%=StringUtil.makeMoneyType(salaryDetail.getPay_amt48()) %></td>
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