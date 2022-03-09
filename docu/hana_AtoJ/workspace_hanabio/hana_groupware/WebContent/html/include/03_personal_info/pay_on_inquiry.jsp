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
	<h2>급여조회</h2>
	<div class="wrap_pay_on_inquiry">
		<div class="wrap_search">
			<div class="float_l">
				<span class="txt">게시기간</span>
				<span class="wrap_carendar">
					<input type="text" />
					<button class="btn_date"><span class="blind">달력보기</span></button>
				</span>
				~
				<span class="wrap_carendar">
					<input type="text" />
					<button class="btn_date"><span class="blind">달력보기</span></button>
				</span>
			</div>
			<div class="float_r">
				<button class="btn_search">검색</button>
			</div>
		</div>
		<div class="wrap_list">
			<div class="tableA">
				<table class="tbl_list">
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
						<tr>
							<td>2014.01.31</td>
							<td>2,000,000</td>
							<td>3,000,000</td>
							<td>500,000</td>
							<td>2,500,000</td>
						</tr>
						<tr>
							<td>2014.01.31</td>
							<td>2,000,000</td>
							<td>3,000,000</td>
							<td>500,000</td>
							<td>2,500,000</td>
						</tr>
						<tr>
							<td>2014.01.31</td>
							<td>2,000,000</td>
							<td>3,000,000</td>
							<td>500,000</td>
							<td>2,500,000</td>
						</tr>
						<tr>
							<td>2014.01.31</td>
							<td>2,000,000</td>
							<td>3,000,000</td>
							<td>500,000</td>
							<td>2,500,000</td>
						</tr>
						<tr>
							<td>2014.01.31</td>
							<td>2,000,000</td>
							<td>3,000,000</td>
							<td>500,000</td>
							<td>2,500,000</td>
						</tr>
						<tr>
							<td>2014.01.31</td>
							<td>2,000,000</td>
							<td>3,000,000</td>
							<td>500,000</td>
							<td>2,500,000</td>
						</tr>
						<tr>
							<td>2014.01.31</td>
							<td>2,000,000</td>
							<td>3,000,000</td>
							<td>500,000</td>
							<td>2,500,000</td>
						</tr>
						<tr>
							<td>2014.01.31</td>
							<td>2,000,000</td>
							<td>3,000,000</td>
							<td>500,000</td>
							<td>2,500,000</td>
						</tr>
						<tr>
							<td>2014.01.31</td>
							<td>2,000,000</td>
							<td>3,000,000</td>
							<td>500,000</td>
							<td>2,500,000</td>
						</tr>
						<tr>
							<td>2014.01.31</td>
							<td>2,000,000</td>
							<td>3,000,000</td>
							<td>500,000</td>
							<td>2,500,000</td>
						</tr>
					</tbody>
				</table>
			</div>
			
			<div class="paging">
				<div class="wrap_total">
					* 총 건수 : <span class="cnt">99</span>건
				</div>
				<div class="wrap_paging">
					<button class="prev01"><span class="blind">이전 10페이지</span></button>
					<button class="prev02"><span class="blind">이전 페이지</span></button>
					
					<ul>
						<li><a class="active" href="#">1</a></li>
						<li><a href="#">2</a></li>
						<li><a href="#">3</a></li>
						<li><a href="#">4</a></li>
						<li><a href="#">5</a></li>
						<li><a href="#">6</a></li>
						<li><a href="#">7</a></li>
						<li><a href="#">8</a></li>
						<li><a href="#">9</a></li>
						<li><a href="#">10</a></li>
					</ul>
					
					<button class="next02"><span class="blind">다음 페이지</span></button>
					<button class="next01"><span class="blind">다음 10페이지</span></button>
				</div>
				
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