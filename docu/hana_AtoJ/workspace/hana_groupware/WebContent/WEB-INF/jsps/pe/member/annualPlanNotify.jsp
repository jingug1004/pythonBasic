<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%--
 * @파일명 : annualPlanNotify.jsp
 * @메뉴명 : 연차지정통보서
 * @최초작성일 : 2015/01/28            
 * @author : CHOIILJI                 
 * @수정내역 : 
--%>
<%@ page import="java.util.List" %>
<%@ page import="com.hanaph.gw.co.common.utils.StringUtil" %>
<%@ page import="com.hanaph.gw.pe.member.vo.AnnualMgrVO" %>   
<%@ include file ="/common/path.jsp" %>
<%
	AnnualMgrVO annualPlanNotify = (AnnualMgrVO)request.getAttribute("annualPlanNotify");	
	int year = (Integer)request.getAttribute("year");	
	int month = (Integer)request.getAttribute("month");	
	int day = (Integer)request.getAttribute("day");
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
				<h2>연차지정통보서</h2>
				<div class="wrap_appoint_inform" id="annual">
					<div class="wrap_btn annual">
						<button class="type_01" onclick="javascripr:Commons.previewOpen('annual', '785', '768');">미리보기</button>
					</div>
					<div class="wrap_form">
						<h3>미사용 연차유급휴가 사용시기 지정통보서</h3>
						<dl class="mt30">
							<dt>부서 : </dt>
							<dd><%=StringUtil.nvl(annualPlanNotify.getDept_ko_nm()) %></dd>
							
							<dt>성명 : </dt>
							<dd><%=StringUtil.nvl(annualPlanNotify.getEmp_ko_nm()) %></dd>
						</dl>
						
						<p class="txt">
							"<span class="year"><%= StringUtil.nvl(annualPlanNotify.getYr_year())%></span>년도 연차휴가 사용계획서" 미제출자에 대하여, 관련규정에 의거하여 미사용 연차휴가 사용일을 회사가 지정하여 통보합니다. 사용시기 지정일에 연차휴가를 사용하시기를 권장하며 사용하지 아니한 연차휴가 및 수당청구권은 소멸되오니 유념하시기 바랍니다.<br />							
						</p>
						
						<p class="txt mt30">&lt;아 래&gt;</p>
						<h4 class="mt20">1. 통지받은 내역</h4>
						<div class="tableA">
							<table>
								<thead>
									<tr>
										<th>연차휴가 발생대상 기간</th>
										<th>연차휴가 사용대상 기간</th>
										<th>발생연차</th>
										<th>사용연차</th>
										<th>미사용연차</th>
									</tr>
								</thead>
								<tbody>
									<%
									float yr_year_used_day = 0;
									if(Float.parseFloat(annualPlanNotify.getYr_year_used_day()) < 0){
										yr_year_used_day = Float.parseFloat(annualPlanNotify.getYr_year_used_day());
									}
									//CHOE 20151105 우승훈 요청 11월 12월의 남은 공동연차일은 반영하지 않도록 한다.
									float used_day = annualPlanNotify.getUsed_days() - yr_year_used_day - annualPlanNotify.getJoint_days_remain();
									float reserved_day = annualPlanNotify.getUseable_days() - annualPlanNotify.getUsed_days() + yr_year_used_day + annualPlanNotify.getJoint_days_remain();
									
									//float used_day = annualPlanNotify.getUsed_days() - yr_year_used_day ;
									//float reserved_day = annualPlanNotify.getUseable_days() - annualPlanNotify.getUsed_days() + yr_year_used_day;									
									//useable_days -  used_days  + if( number( yr_year_used_day ) < 0, number( yr_year_used_day ), 0)
									%>
									<tr>
										<td class="no_bd_l"><%=StringUtil.nvl(annualPlanNotify.getCreate_between_day()) %></td>
										<td><%=StringUtil.nvl(annualPlanNotify.getUse_between_day()) %></td>
										<td><%=annualPlanNotify.getUseable_days() %></td>
										<td><%=used_day %></td>
										<td class="no_bd_r"><%=reserved_day %></td>
									</tr>
								</tbody>
							</table>
						</div>
						
						<h4 class="mt30">2. 미사용 연차유급휴가 사용시기 지정 통보내역</h4>
						<div class="tableA">
							<table>
								<thead>
									<tr>
										<th>구분</th>
										<th>사용시기 지정일</th>
									</tr>
								</thead>
								<tbody>									
									<tr>
										<td>11월</td>
										<td><%=StringUtil.nvl(annualPlanNotify.getNovember()) %></td>
									</tr>
									<tr>
										<td>12월</td>
										<td><%=StringUtil.nvl(annualPlanNotify.getDecember()) %></td>
									</tr>
								</tbody>
							</table>
						</div>
						
						<div class="wrap_sign mt30">
							<p class="date">
								<span><%= year%></span>년 <span><%= month %></span>월 <span><%= day %></span>일
							</p>
							<!--<p class="sign">
								작성자 : <span class="name"></span>(서명 또는 인)
							</p>-->
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