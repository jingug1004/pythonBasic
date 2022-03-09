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
	<h2>하나제약 게시판</h2>
	<div class="noticeboard_box">
		<div class="serch_box">
			<ul class="serch_con01">
				<li>
					<span class="sc_txt">게시기간</span>
					<span class="serch_date_box">
						<input type="text" class="serch_date" />
						<button class="btn_date"><span class="blind">날짜선택</span></button>
					</span> ~ 
					<span class="serch_date_box">
						<input type="text" class="serch_date" />
						<button class="btn_date"><span class="blind">날짜선택</span></button>
					</span>
					<span class="sc_txt">게시판종류</span>
					<select class="serch_select01">
						<option value="">하나제약 게시판</option>
						<option value="">하나제약 게시판01</option>
					</select>
				</li>
				<li class="cont02">
					<span class="sc_txt">게시물 검색</span>
					<select class="serch_select02">
						<option value="">제목</option>
						<option value="">작성자</option>
					</select>
					<input type="text" class="serch_txt" />
				</li>
			</ul>
			<span class="serch_btn"><button>검색</button></span>
		</div>
		<div class="list_btn">
			<div class="list_button">
				<button class="type_01">신규</button>
				<button class="type_01">삭제</button>
			</div>
			<span class="list_t">* 안 읽은 글은 제목이 <span class="none_check">파란색</span>으로 표시됩니다.</span>
		</div>
		
		<div class="cont_box cont_table05">
			<form>
				<table>
				<colgroup>
					<col width="40px" />
					<col width="*" />
					<col width="109px" />
					<col width="78px" />
					<col width="68px" />
					<col width="68px" />
				</colgroup>
					<thead>
						<tr>
							<th><input type="checkbox" /></th>
							<th>말머리/제목(댓글수)</th>
							<th>게시일</th>
							<th>게시자</th>
							<th>읽은수</th>
							<th class="br_none">첨부</th>										
						</tr>
					</thead>
					<tbody>
						<tr>
							<td><input type="checkbox" /></td>
							<td class="list_txt none_check"><a href="">전체/제품리스트 신청합니다.</a><span class="count">(3)</span></td><!-- 20141218 수정 -->
							<td><span>2014-05-05</span>&nbsp;<span>20:20:20</span></td>
							<td>이정민</td>
							<td>15</td>
							<td class="br_none"><a href=""><img src="<%=GROUP_ROOT%>/asset/img/img_file.png" alt="파일첨부" /></a></td>										
						</tr>
						<tr>
							<td><input type="checkbox" /></td>
							<td class="list_txt none_check"><a href="">전체/제품리스트 신청합니다.전체/제품리스트 신청합니다.전체/제품리스트 신청합니다.</a><span class="count">(3)</span></td><!-- 20141218 수정 -->
							<td><span>2014-05-05</span>&nbsp;<span>20:20:20</span></td>
							<td>이정민</td>
							<td>9999</td>
							<td class="br_none"><a href=""><img src="<%=GROUP_ROOT%>/asset/img/img_file.png" alt="파일첨부" /></a></td>										
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

<!-- ######## end ######### -->
			
		</div>
	</div>
	
	<%@include file="../../include/footer.jsp"%>
</body>
</html>