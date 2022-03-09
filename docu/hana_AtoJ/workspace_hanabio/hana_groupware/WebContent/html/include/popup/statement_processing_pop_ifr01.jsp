<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="ko">
<head>
	<%@include file="../../include/head.jsp"%>	
</head>
<body>
	<div class="search_box02 w488">
		<span>부서명</span>
		<input type="text" class="w210"/>
		<button>검색</button>
	</div>
	<div class="settle_table02 w314">
		<table>
		<colgroup>
			<col style="width:28px" />
			<col style="" />
		</colgroup>
			<thead>
				<tr>
					<th><input type="checkbox" /></th>
					<th class="br_none">부서</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td colspan="2" class="inner">
						<div>
							<table>
								<colgroup>
									<col style="width:28px"/>
									<col />
								</colgroup>
								<tbody>
									<tr>
										<td><input type="checkbox" name="checkName" /></td>
										<td class="br_none check_name">경영기획팀</td>
									</tr>
									<tr>
										<td><input type="checkbox" name="checkName" /></td>
										<td class="br_none check_name">경영기획팀</td>
									</tr>
									<tr>
										<td><input type="checkbox" name="checkName" /></td>
										<td class="br_none check_name">경영기획팀</td>
									</tr>
									<tr>
										<td><input type="checkbox" name="checkName" /></td>
										<td class="br_none check_name">경영기획팀</td>
									</tr>
									<tr>
										<td><input type="checkbox" name="checkName" /></td>
										<td class="br_none check_name">경영기획팀</td>
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
		<div class="list_button mt5 w314">
			<!-- <button type="button" class="type_01 btn_coop">협조</button> -->
			<button type="button" class="type_01 btn_exe">시행부서 추가</button>
		</div>
	</div>

<script src="http://localhost:8080/hanagw/asset/js/jquery-1.11.1.min.js"></script>
<script src="http://localhost:8080/hanagw/asset/js/droplist.js"></script>
</body>
</html>