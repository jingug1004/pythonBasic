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
	<h2>연차지정통보서</h2>
	<div class="wrap_appoint_inform">
		<div class="wrap_btn">
			<button class="type_01">미리보기</button>
		</div>
		<div class="wrap_form">
			<h3>미사용연차유급휴가지정통보서</h3>
			<dl class="mt30">
				<dt>부서 : </dt>
				<dd>서울로컬1호점</dd>
				
				<dt>성명 : </dt>
				<dd>박세호</dd>
			</dl>
			
			<p class="txt">
				본 통보서는 관련규정에 의해 기 제출한 "<span class="year">2014</span>년도 연차휴가 사용계획서"에 의거하여, 미사용 연차휴가를 회사가 지정하여<br />
				통보합니다. 사용시기 지정일에 연차휴가를 사용하시기를 권장하며 사용하지 아니한 연차유급휴가일수는 소멸하오니 이점<br />
				유념하시기 바랍니다.
			</p>
			
			<p class="txt mt30">&lt;아 래&gt;</p>
			<h4>1. 통지받은 내역</h4>
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
						<tr>
							<td>2012.01.01~2012.12.31</td>
							<td>2012.01.01~2012.12.31</td>
							<td>17</td>
							<td>12</td>
							<td>5</td>
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
							<td></td>
						</tr>
						<tr>
							<td>12월</td>
							<td></td>
						</tr>
					</tbody>
				</table>
			</div>
			
			<div class="wrap_sign mt30">
				<p class="date">
					<span>2014</span>년 <span>9</span>월 <span>24</span>일
				</p>
				<p class="sign">
					작성자 : <span class="name"></span>(서명 또는 인)
				</p>
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