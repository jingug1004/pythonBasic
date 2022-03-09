<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%--
 * @파일명 : annualList.jsp
 * @메뉴명 : 연차사용내역
 * @최초작성일 : 2015/01/28            
 * @author : CHOIILJI                 
 * @수정내역 : 
--%>
<%@ page import="java.util.List" %>
<%@ page import="com.hanaph.gw.co.common.utils.StringUtil" %>
<%@ page import="com.hanaph.gw.pe.member.vo.AnnualVO" %>   
<%@ include file ="/common/path.jsp" %>
<%
	List<AnnualVO> annualList = (List<AnnualVO>)request.getAttribute("annualList");
	String search_start_dt = StringUtil.nvl((String)request.getAttribute("search_start_dt"), "");
	String search_end_dt = StringUtil.nvl((String)request.getAttribute("search_end_dt"), "");
	int annualTotalCount = (Integer)request.getAttribute("annualTotalCount");
	float annualUsedCount = (Float)request.getAttribute("annualUsedCount");
%>
<!DOCTYPE html>
<html lang="ko">
<head>
	<%@include file="/include/head.jsp"%>
	<script type="text/javascript">
	
	/**
	 * 검색
	 */
	function goSearch(){
		if(formCheck.dateChk($("#search_start_dt").val(),$("#search_end_dt").val())<0){
			alert("시작 날짜가 종료 날짜 보다 이후 입니다.");
			return;
		}
		
		document.getElementById("currentPage").value = "1";
		document.getElementById("frm").action ="<%=GROUP_ROOT%>/pe/member/annualList.do";
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
	<h2>연차사용내역</h2>
	<div class="wrap_pay_on_inquiry">
		<form name="frm" id="frm" method="post">
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
				<button class="btn_search" onclick="javascript:goSearch(); return false;">검색</button>
			</div>
		</div>
		</form>
		<div class="wrap_list">
			<div class="tableA">
				
				<input type="hidden" id="pay_date" name="pay_date">
				<table>
					<thead>
						<tr>
							<th>구분</th>
							<th>시작일자</th>
							<th>종료일자</th>
							<th>연차일수</th>
							<th>비고</th>										
						</tr>
					</thead>
					<tbody>
					<%
						if(annualList.size()!=0){
									for(int i=0; annualList.size()>i;i++){
										AnnualVO annualVO = new AnnualVO();
										annualVO = annualList.get(i);
					%>
					<tr>
						<td><%=StringUtil.nvl(annualVO.getRq_vacat_nm())%></td>
						<td><%=StringUtil.nvl(annualVO.getRq_fr_dt())%></td>
						<td><%=StringUtil.nvl(annualVO.getRq_to_dt())%></td>
						<td<%="42020".equals(annualVO.getRq_vacat_cd())?" class='fc_b'": " class='fc_r'"%>><%=annualVO.getRq_wk_day()%></td>
						<td><%=StringUtil.nvl(annualVO.getRq_remark())%></td>
					</tr>
					<%
						}
					%>	
					<tr>
						<td></td>
						<td colspan="2">총 연차사용일수</td>
						<td><%=annualUsedCount%></td>
						<td></td>
					</tr>	
					<%
							} else{
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
					* 총 건수 : <span class="cnt"><%=StringUtil.makeMoneyType(annualTotalCount)%></span>건
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