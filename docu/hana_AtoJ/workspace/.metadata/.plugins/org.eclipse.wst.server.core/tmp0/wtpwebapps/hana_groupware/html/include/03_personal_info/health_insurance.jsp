<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
	<%@include file="../../include/head.jsp"%>
</head>
<body>
	<%@include file="../../include/header.jsp"%>
	<%@include file="../../include/gnb.jsp"%>
	
	<div class="wrap_con">
		<div class="content">
			<%@include file="../../include/location.jsp"%>
			<%@include file="../../include/lnb.jsp"%>
			
			<!-- ######## start ####### -->
			
<div class="cont float_left">
	<h2>건강보험료</h2>
	<div class="wrap_health_insurance">
		<div class="wrap_btn">
			<button class="type_01">목록</button><!-- 11.17 추가 -->
			<button class="type_01">미리보기</button>
		</div>
		<div class="wrap_list mt10">
			<div class="tableA">
				<table>
					<colgroup>
						<col style="width:25%;" />
						<col style="width:25%;" />
						<col style="width:25%;" />
						<col style="width:25%;" />
					</colgroup>
					<tr>
						<td colspan="4"><h3>2013년 건강보험 연말정산 환급/징수 금</h3></td>
					</tr>
					<tr>
						<th rowspan="2">총 급여</th>
						<td rowspan="2">52,522,030</td>
						<th>근무개월</th>
						<td>12개월</td>
					</tr>
					<tr>
						<th>보수월액</th>
						<td>4,376,835</td>
					</tr>
					<tr>
						<th rowspan="2">납부한 총 보험</th>
						<td rowspan="2">1,434,990</td>
						<th>납부한 총 보험료(건강)</th>
						<td>1,346,820</td>
					</tr>
					<tr>
						<th>납부한 총 보험료(장기요양)</th>
						<td>88,,170</td>
					</tr>
					<tr>
						<th rowspan="2">확정보험료</th>
						<td rowspan="2">1,647,960</td>
						<th>확정보험료(건강)</th>
						<td>1,546,680</td>
					</tr>
					<tr>
						<th>확정보험료(장기요양)</th>
						<td>101,280</td>
					</tr>
					<tr>
						<th rowspan="2">가입자부담금</th>
						<td rowspan="2">212,970</td>
						<th>가입자부담금(건강)</th>
						<td>199,860</td>
					</tr>
					<tr>
						<th>가입자부담금(장기요양)</th>
						<td>13,110</td>
					</tr>
					<tr>
						<td colspan="4"><span class="fc_b">※ 가입자 부담금은 4월에 급여에 공제(+공제, -환급)</span></td>
					</tr>
					<tr>
						<th>월납입보험료</th>
						<td colspan="3">139,660</td>
					</tr>
					<tr>
						<td colspan="4"><span class="fc_b">※ 2014년 04월 ~ 2015년 03월까지 건강보험료 적용</span></td>
					</tr>
				</table>
			</div>
		</div>
	</div>
</div>

<!-- ######## end ######### -->
			
		</div>
	</div>
	
	<%@include file="../../include/footer.jsp"%>
</body>
</html>