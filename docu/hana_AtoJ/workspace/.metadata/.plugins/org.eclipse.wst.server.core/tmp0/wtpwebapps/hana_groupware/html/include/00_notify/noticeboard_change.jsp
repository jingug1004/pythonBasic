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
				<button class="type_01">저장</button>
				<button class="type_01">목록</button>
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
							<th class="bt_none bc_re br_none" colspan="4">하나제약 게시판</th>
						</tr>
						<tr>
							<th>제목</th>
							<td colspan="3" class="txt_field br_none"><input type="text" /></td>
						</tr>
						<tr>
							<th>게시기간</th>
							<td colspan="3" class="date_search br_none">
								<div class="date_position">
									<input type="text" />
									<button class="btn_date">날짜찾기</button>
								</div>
								~
								<div class="date_position">
									<input type="text" />
									<button class="btn_date">날짜찾기</button>	
								</div>							
							</td>
						</tr>
						<tr>
							<td colspan="4" class="txt_box01 br_none">
								<textarea rows="" cols=""></textarea>
							</td>
						</tr>
						<tr>
							<th class="append_tit">첨부파일</th>
							<td colspan="3" class="append_file br_none">
								<div class="append_search">
									<input type="text" />
									<button>찾아보기</button>
								</div>
								<!-- 파일 드래그 & 드랍시 start (2014.11.17 수정 추가) -->
								<ul class="drop_file">

								</ul>
								<!-- 파일 드래그 & 드랍시 end-->								
								<div class="append_list">
									<span>aaa.jpg</span>
									<a href=""><img src="<%=GROUP_ROOT%>/asset/img/popup_btn_close01.gif" alt="파일삭제" /></a>
								</div>
							</td>
						</tr>																								
					</tbody>
			</table>
		</div>
		<div class="append_object_box">
			<button class="type_01">게시 대상 추가</button>
			<div class="cont_box cont_table07">
				<table>
					<colgroup>
						<col width="20px" />
						<col width="14%"/>
						<col width="14%"/>
						<col width="14%"/>
						<col width="14%"/>
						<col width="14%"/>
						<col width="14%"/>
						<col width="14%"/>
					</colgroup>
					<thead>
						<tr>
							<th><input type="checkbox" /></th>
							<th>대상종류</th>
							<th>대상자(조직)</th>
							<th>대상이름</th>
							<th>····</th>
							<th>····</th>
							<th>····</th>
							<th class="br_none">····</th>
						</tr>
					</thead>
					<tbody>
						<tr class="">
							<td colspan="8" class="br_none">대상을 추가하세요.</td>
						</tr>
						<!-- 대상 추가시 활성화 
						<tr>
							<td><input type="checkbox" /></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td class="br_none"></td>
						</tr>
						-->
					</tbody>
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