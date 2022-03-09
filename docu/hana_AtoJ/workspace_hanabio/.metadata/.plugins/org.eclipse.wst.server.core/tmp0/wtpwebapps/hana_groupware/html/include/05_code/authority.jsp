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
				<h2>권한관리</h2>
				<div class="wrap_authority">
					<div class="wrap_search mb7">
						<div class="search">
							<select>
								<option>권한이름</option>
							</select>
							<input type="text" />
						</div>
						<div class="wrap_btn">
							<button class="btn_search">검색</button>
						</div>
					</div>
					
					<div class="wrap_list">
						<div class="tableA">
							<table>
								<thead>
									<tr>
										<th>NO.</th>
										<th>권한이름</th>
										<th>권한설명</th>
										<th>메뉴선택</th>
										<th>임직원선택</th>
									</tr>
								</thead>
								<tbody>
									<tr>
										<td></td>
										<td></td>
										<td></td>
										<td><button class="type_01 layer_pop" url="<%=GROUP_ROOT%>/html/include/popup/authority_menu.jsp" size="450">메뉴선택</button></td>
										<td><button class="type_01 layer_pop" url="<%=GROUP_ROOT%>/html/include/popup/authority_staff.jsp" size="600">임직원선택</button></td>
									</tr>
									<tr>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
									</tr>
									<tr>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
									</tr>
									<tr>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
									</tr>
									<tr>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
									</tr>
									<tr>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
									</tr>
									<tr>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
									</tr>
									<tr>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
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
							<div class="wrap_btn">
								<button class="type_01">등록</button>
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