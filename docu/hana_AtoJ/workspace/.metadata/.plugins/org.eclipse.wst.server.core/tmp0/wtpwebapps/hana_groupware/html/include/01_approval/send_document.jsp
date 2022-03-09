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
	<div class="wrap_send_document">
		<h2>내가올린문서</h2>
		<div class="noticeboard_box">
		<div class="serch_box">
			<ul class="serch_con01">
				<li>
					<span class="sc_txt w48">기안일</span>
					<span class="serch_date_box">
						<input class="serch_date" type="text">
						<button class="btn_date"><span class="blind">날짜선택</span></button>
					</span> ~ 
					<span class="serch_date_box">
						<input class="serch_date" type="text">
						<button class="btn_date"><span class="blind">날짜선택</span></button>
					</span>
					<span class="sc_txt w48 ml45">문서상태</span>
					<select class="serch_select01">
						<option value="">진행중</option>
					</select>
				</li>
				<li class="cont02">
					<span class="sc_txt w48">문서종류</span>
					<select class="serch_select02 w116">
						<option value="">근태신청서</option>
					</select>
					<span class="sc_txt">
						<select class="search_select02 w116">
							<option value="">문서번호</option>
						</select>
					</span>
					<input class="serch_txt02 w275" type="text">
				</li>
			</ul>
			<span class="serch_btn"><button>검색</button></span>
		</div>
		
		<!-- <div class="list_btn">
			<div class="list_button">
				<button class="type_01">일괄결재</button>
			</div>
		</div> -->
		
		<div class="cont_box cont_table05">
			<form>
				<table>
				<colgroup>
					<col width="15%">
					<col width="12%">
					<col width="40%">
					<col width="13%">
					<col width="10%">
				</colgroup>
					<thead>
						<tr>
							<th>문서번호</th>
							<th>문서종류</th>
							<th>제목</th>
							<th>기안일</th>
							<th>문서상태</th>
							<th class="br_none">처리자</th>										
						</tr>
					</thead>
					<tbody>
						<tr>
							<td>전산-2014-0011</td>
							<td class="type">기안서</td>
							<td>기안합니다</td>
							<td>2014-05-05</td>
							<td>요청</td>
							<td class="br_none">최일지</td>										
						</tr>
						<tr>
							<td>전산-2014-0011</td>
							<td class="type">근태신청서</td>
							<td>2014-05-08 휴가신청</td>
							<td>2014-05-05</td>
							<td>진행중</td>
							<td class="br_none">최일지</td>										
						</tr>
						<tr>
							<td>전산-2014-0011</td>
							<td class="type">근태계</td>
							<td>2014-05-05 조퇴신청서</td>
							<td>2014-05-05</td>
							<td>요청</td>
							<td class="br_none">최일지</td>										
						</tr>
						<tr>
							<td>전산-2014-0011</td>
							<td class="type">기안서</td>
							<td>기안합니다</td>
							<td>2014-05-05</td>
							<td>요청</td>
							<td class="br_none">최일지</td>										
						</tr>
						<tr>
							<td>전산-2014-0011</td>
							<td class="type">근태신청서</td>
							<td>2014-05-08 휴가신청</td>
							<td>2014-05-05</td>
							<td>진행중</td>
							<td class="br_none">최일지</td>										
						</tr>
						<tr>
							<td>전산-2014-0011</td>
							<td class="type">근태계</td>
							<td>2014-05-05 조퇴신청서</td>
							<td>2014-05-05</td>
							<td>요청</td>
							<td class="br_none">최일지</td>										
						</tr>
						<tr>
							<td>전산-2014-0011</td>
							<td class="type">기안서</td>
							<td>기안합니다</td>
							<td>2014-05-05</td>
							<td>요청</td>
							<td class="br_none">최일지</td>										
						</tr>
						<tr>
							<td>전산-2014-0011</td>
							<td class="type">근태신청서</td>
							<td>2014-05-08 휴가신청</td>
							<td>2014-05-05</td>
							<td>진행중</td>
							<td class="br_none">최일지</td>										
						</tr>
						<tr>
							<td>전산-2014-0011</td>
							<td class="type">근태계</td>
							<td>2014-05-05 조퇴신청서</td>
							<td>2014-05-05</td>
							<td>요청</td>
							<td class="br_none">최일지</td>										
						</tr>
						<tr>
							<td>전산-2014-0011</td>
							<td class="type">근태계</td>
							<td>2014-05-05 조퇴신청서</td>
							<td>2014-05-05</td>
							<td>요청</td>
							<td class="br_none">최일지</td>										
						</tr>
					</tbody>
				</table>
			</form>
		</div>
		<div class="paging">
			<div class="wrap_total">
				* 총 건수 : <span class="cnt">99</span>건
			</div>
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