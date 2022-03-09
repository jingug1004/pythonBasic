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
	<div class="wrap_search_yearend">
		<h2>연말정산조회</h2>
		<div class="noticeboard_box">
		<div class="serch_box pd10">
			<ul class="serch_con01">
				<li>
					<span class="sc_txt w48">게시기간</span>
					<span class="serch_date_box">
						<input class="serch_date" type="text">
						<button class="btn_date"><span class="blind">날짜선택</span></button>
					</span> ~ 
					<span class="serch_date_box">
						<input class="serch_date" type="text">
						<button class="btn_date"><span class="blind">날짜선택</span></button>
					</span>
				</li>
			</ul>
			<span class="serch_btn"><button class="sm">검색</button></span>
		</div>
		
		
		<div class="cont_box cont_table05">
			<form>
				<table>
				<colgroup>
					<col width="34%">
					<col width="33%">
					<col width="33%">
				</colgroup>
					<thead>
						<tr>
							<th>연도</th>
							<th>등록</th>
							<th>미리보기</th>										
						</tr>
					</thead>
					<tbody>
						<tr>
							<td>2014</td>
							<td><button class="type_01">등록</button></td>
							<td><button class="type_01">미리보기</button></td>
						</tr>
						<tr>
							<td>2013</td>
							<td><button class="type_01">등록</button></td>
							<td><button class="type_01">미리보기</button></td>
						</tr>
						<tr>
							<td>2012</td>
							<td><button class="type_01">등록</button></td>
							<td><button class="type_01">미리보기</button></td>
						</tr>
						<tr>
							<td>2011</td>
							<td><button class="type_01">등록</button></td>
							<td><button class="type_01">미리보기</button></td>
						</tr>
						<tr>
							<td>2010</td>
							<td><button class="type_01">등록</button></td>
							<td><button class="type_01">미리보기</button></td>
						</tr>
						<tr>
							<td>2009</td>
							<td><button class="type_01">등록</button></td>
							<td><button class="type_01">미리보기</button></td>
						</tr>
						<tr>
							<td>2008</td>
							<td><button class="type_01">등록</button></td>
							<td><button class="type_01">미리보기</button></td>
						</tr>
						<tr>
							<td>2007</td>
							<td><button class="type_01">등록</button></td>
							<td><button class="type_01">미리보기</button></td>
						</tr>
						<tr>
							<td>2006</td>
							<td><button class="type_01">등록</button></td>
							<td><button class="type_01">미리보기</button></td>
						</tr>
						<tr>
							<td>2005</td>
							<td><button class="type_01">등록</button></td>
							<td><button class="type_01">미리보기</button></td>
						</tr>
					</tbody>
				</table>
			</form>
		</div>
		<div class="paging">
			
			<div class="wrap_paging">
				<button class="prev01"><span class="blind">이전 10페이지</span></button>
				<button class="prev02"><span class="blind">이전 페이지</span></button>
				
				<ul>
					<li><a href="#" class="active">1</a></li>
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