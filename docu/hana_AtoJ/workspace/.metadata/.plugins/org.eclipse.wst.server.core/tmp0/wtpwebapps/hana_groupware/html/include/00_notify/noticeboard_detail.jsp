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
		<div class="list_btn">
			<div class="list_button">
				<button class="type_01">삭제</button>
				<button class="type_01">목록</button>
				<button class="type_01 layer_pop" url="<%=GROUP_ROOT%>/html/include/popup/noticeboard_pop01.jsp" size="350">게시대상보기</button>
			</div>
		</div>
		<div class="cont_box cont_table06">
			<table>
				<colgroup>
					<col width="20%" />
					<col width="30%" />
					<col width="20%" />
					<col width="30%" />
				</colgroup>
					<tbody>
						<tr>
							<th class="bt_none">하나제약 게시판</th>
							<td colspan="3" class="bt_none">제품리스트 신청합니다.</td>
						</tr>
						<tr>
							<th>게시자</th>
							<td>황경호</td>
							<th>조회수</th>
							<td>15</td>							
						</tr>
						<tr>
							<th>게시일</th>
							<td>2014-05-05 <span class="time_color">20:20:20</span></td>
							<th>게시기간</th>
							<td><span>2014-05-05</span>~<span>2014-05-05</span></td>							
						</tr>
						<tr>
							<td colspan="4" class="txt_box">게시판 텍스트</td>
						</tr>
						<tr>
							<th>첨부파일</th>
							<td colspan="3" class="append_file"><a href="">aaa.jpg</a></td>
						</tr>																								
					</tbody>
			</table>
		</div>
		<div class="coment_box">
			<textarea class="coment"></textarea>
			<button class="btn_coment">댓글입력</button>
		</div>
		<div class="coment_list_box">
			<table>
				<colgroup>
					<col width="10%" />
					<col width="20%" />
					<col width="65%" />
					<col width="5%" />
				</colgroup>
				<tr class="first_list">
					<th>이정민</th>
					<td class="date_txt">2014-05-05 <span>20:20:20</span></td>
					<td class="tit_txt">저도 신청합니다.</td>
					<td><a href=""><img src="<%=GROUP_ROOT%>/asset/img/popup_btn_close01.gif" alt="삭제" /></a></td>
				</tr>
				<tr>
					<th>이정민</th>
					<td class="date_txt">2014-05-05 <span>20:20:20</span></td>
					<td class="tit_txt">저도 신청합니다.</td>
					<td><a href=""><img src="<%=GROUP_ROOT%>/asset/img/popup_btn_close01.gif" alt="삭제" /></a></td>
				</tr>
				<tr>
					<th>이정민</th>
					<td class="date_txt">2014-05-05 <span>20:20:20</span></td>
					<td class="tit_txt">저도 신청합니다.</td>
					<td><a href=""><img src="<%=GROUP_ROOT%>/asset/img/popup_btn_close01.gif" alt="삭제" /></a></td>
				</tr>								
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