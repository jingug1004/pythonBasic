<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%--
 * @파일명 : salaryList.jsp
 * @메뉴명 : 급여조회 리스트
 * @최초작성일 : 2015/01/28            
 * @author : CHOIILJI                 
 * @수정내역 : 
--%>
<%@ page import="java.util.List" %>
<%@ page import="com.hanaph.gw.co.common.utils.StringUtil" %>
<%@ page import="com.hanaph.gw.pe.member.vo.SalaryVO" %>   
<%@ include file ="/common/path.jsp" %>
<%
	List<SalaryVO> salaryList = (List<SalaryVO>)request.getAttribute("salaryList");
	String search_start_dt = StringUtil.nvl((String)request.getAttribute("search_start_dt"));
	String search_end_dt = StringUtil.nvl((String)request.getAttribute("search_end_dt"));
	int salaryTotalCount = (Integer)request.getAttribute("salaryTotalCount");
%>
<!DOCTYPE html>
<html lang="ko">
<head>
	<%@include file="/include/head.jsp"%>
	<script type="text/javascript">
	
	/**
	 * 상세페이지 이동
	 */
	function goSalary(pay_date){
		document.getElementById("pay_date").value = pay_date;
		document.getElementById("frm").action ="<%=GROUP_ROOT%>/pe/member/salaryDetail.do";
		document.getElementById("frm").submit();
	}
	
	/**
	 * 검색
	 */
	 function goSearch(){
		if(formCheck.dateChk($("#search_start_dt").val(),$("#search_end_dt").val())<0){
			alert("시작 날짜가 종료 날짜 보다 이후 입니다.");
			return;
		}
		
		document.getElementById("currentPage").value = "1";
		document.getElementById("frm").action ="<%=GROUP_ROOT%>/pe/member/salaryList.do";
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
				<div class="wrap_pay_on_inquiry">
					<form name="frm" id="frm" method="post">
						<input type="hidden" id="pay_date" name="pay_date">
					<div class="wrap_search">
						<div class="float_l">
							<span class="txt">조회기간</span>
							<span class="wrap_carendar">
								<input type="text" id="search_start_dt" name="search_start_dt" value="<%=search_start_dt%>" readonly/>
								<button type="button" class="btn_date"><span class="blind">달력보기</span></button>
							</span>
							~
							<span class="wrap_carendar">
								<input type="text" id="search_end_dt" name="search_end_dt" value="<%=search_end_dt%>" readonly/>
								<button type="button" class="btn_date"><span class="blind">달력보기</span></button>
							</span>
						</div>
						<div class="float_r">
							<button type="button" class="btn_search" onclick="javascript:goSearch(); return false;">검색</button>
						</div>
					</div>
					</form>
					<div class="wrap_list">
						<div class="tableA">
							
							<table>
								<thead>
									<tr>
										<th>급여지급일</th>
										<th>기본급</th>
										<th>총지급액</th>
										<th>공제합계</th>
										<th>차인지급액</th>
									</tr>
								</thead>
								<tbody>
								<%
									if(salaryList.size()!=0){
										for(int i=0; salaryList.size()>i;i++){
											SalaryVO salaryVO = new SalaryVO();
											salaryVO = salaryList.get(i);
								%>
									<tr style="cursor:pointer;" class="bg_d" onclick="javascript:goSalary('<%=StringUtil.nvl(salaryVO.getPay_date())%>'); return false;">
										<td><%=StringUtil.nvl(salaryVO.getPay_date())%></td>
										<td><%=StringUtil.makeMoneyType(salaryVO.getPay_amt1())%></td>
										<td><%=StringUtil.makeMoneyType(salaryVO.getPay_amt46())%></td>
										<td><%=StringUtil.makeMoneyType(salaryVO.getPay_amt47())%></td>
										<td><%=StringUtil.makeMoneyType(salaryVO.getPay_amt48())%></td>
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
						</div>
						
						<div class="paging">
							<div class="wrap_total">
								* 총 건수 : <span class="cnt"><%=StringUtil.makeMoneyType(salaryTotalCount)%></span>건
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