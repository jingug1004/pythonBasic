<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="ko">
<head>
	<%@include file="../../include/head.jsp"%>	
</head>
<body>
	<div class="search_box02">
		<span>사원명</span>
		<input type="text" class="w210"/>
		<button>검색</button>
	</div>
	<div class="settle_table01">
		<table>
		<colgroup>
			<col style="width:28px" />
			<col style="width:85px" />
			<col style="width:60px" />
			<col style="width:90px" />
		</colgroup>
			<thead>
				<tr>
					<th><input type="checkbox" /></th>
					<th>부서</th>
					<th>직급</th>
					<th class="br_none">사원명</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td colspan="5" class="inner">
						<div>
							<table>
								<colgroup>
									<col style="width:28px"/>
									<col style="width:85px"/>
									<col style="width:60px"/>
									<col style=""/>
								</colgroup>
								<tbody><tr>
										<td><input type="checkbox" name="checkName" /></td>
										<td>경영기획팀</td>
										<td>부장</td>
										<td class="br_none check_name">이박사</td>
									</tr>
									<tr>
										<td><input type="checkbox" name="checkName" /></td>
										<td></td>
										<td></td>
										<td class="br_none check_name"></td>
									</tr>
									<tr>
										<td><input type="checkbox" name="checkName" /></td>
										<td></td>
										<td></td>
										<td class="br_none check_name"></td>
									</tr>
									<tr>
										<td><input type="checkbox" name="checkName" /></td>
										<td></td>
										<td></td>
										<td class="br_none check_name"></td>
									</tr>	
									<tr>
										<td><input type="checkbox" name="checkName" /></td>
										<td></td>
										<td></td>
										<td class="br_none check_name"></td>
									</tr>	
								</tbody>
							</table>
						</div>
					</td>
				</tr>
			</tbody>
		</table>
	</div>
	<div class="list_btn">
		<div class="list_button mt5 w265">
			<button type="button" class="type_01 btn_appr">결재자 추가</button>
			<!-- <button type="button" class="type_01 btn_refe">참조</button> -->
		</div>
	</div>

<script src="http://localhost:8080/hanagw/asset/js/jquery-1.11.1.min.js"></script>
<script src="http://localhost:8080/hanagw/asset/js/droplist.js"></script>
</body>
</html>