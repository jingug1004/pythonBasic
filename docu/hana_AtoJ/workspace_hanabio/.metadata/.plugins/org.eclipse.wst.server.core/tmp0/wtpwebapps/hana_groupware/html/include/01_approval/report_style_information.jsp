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
				<h2>양식정보관리</h2>
				<div class="wrap_report_style mt20">
					<div class="list_button">
						<button class="type_01">저장</button>
						<button class="type_01">목록</button>
					</div>
					
					<div class="wrap_list bdt_2 mt7">
						<div class="">
							<table class="tbl_report_style">
								<colgroup>
									<col style="width:160px;" />
									<col style="" />
								</colgroup>
								<thead>
									<tr class="b_top">
										<th colspan="2" class="bgn">양식정보관리</th>
									</tr>
								</thead>
								<tbody>
									<tr>
										<th>양식명</th>
										<td>기안서</td>
									</tr>
									<tr>
										<th>부서</th>
										<td>영업부</td>
									</tr>
									<tr>
										<th>등록일</th>
										<td>2014-10-05</td>
									</tr>
									<tr>
										<th>옵션</th>
										<td>
											<span class="list_opt"><input type="checkbox" name="opt" id="opt1" /><label for="opt1">전결</label></span>
											<span class="list_opt"><input type="checkbox" name="opt" id="opt2" /><label for="opt2">대외비</label></span>
										</td>
									</tr>
									<tr>
										<th>설명</th>
										<td class="explain">
											<textarea name="" id=""></textarea>
										</td>
									</tr>
								</tbody>
							</table>
						</div>
						
					</div>
					<div class="list_button mt7">
						<button class="type_01">저장</button>
						<button class="type_01">목록</button>
					</div>
				</div>
			</div>
			<!-- ######## end ######### -->
			
		</div>
	</div>
	
	<%@include file="../../include/footer.jsp"%>
</body>
</html>