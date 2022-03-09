<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="ko">
<head>
	<%@include file="../../include/head.jsp"%>	
</head>
<body>

<div class="wrap_pop_bg">
	<div class="wrap_statement_overtime">
		<div class="top">
			<h3>특별공제 <span>(교육비)</span></h3>
		</div>
		<div class="inner">
			<div class="cont_box cont_table06">
				<div class="pop_tit">
					<span class="tit"></span>
				</div>
				<div class="wrap_tbl_yearend">
					<table class="tbl_yearend educate">
						<tr>
							<td class="box">
								<div>
									<button class="btn_row">+ 행추가</button>
									<table class="tbl_yearend mgn">
										<colgroup>
											<col style="width:22px"/>
											<col style="width:148px"/>
											<col style=""/>
											<col style=""/>
											<col style=""/>
										</colgroup>
										<thead>
											<th></th>
											<th>주민번호</th>
											<th>성명</th>
											<th>교육기관</th>
											<th>공과금</th>
										</thead>
										<tbody>
											<tr>
												<th>1</th>
												<td class="search">
													<span class="find_box">
														<input type="text" class="serch_date">
														<button class="btn_find"><span class="blind">찾아보기</span></button>
													</span>
												</td>
												<td><input type="text" name="" id="" class="ipt_disable"/></td>
												<td>
													<select name="" id="">
														<option>취학전아동</option>
														<option>초.중.고 학생</option>
														<option>대학교 교육비</option>
													</select>
												</td>
												<td><input type="text" name="" id="" class="numright" /></td> <!-- 2015-02-25 수정 -->
											</tr>	
										</tbody>
									</table>
	
									<table class="tbl_sum">
										<colgroup>
											<col style="width:22px"/>
											<col style="width:132px"/>
											<col style=""/>
											<col style=""/>
											<col style=""/>
										</colgroup>	
										<tbody>
											<tr>
												<td colspan="4"></td>
												<td>합계 <input type="text" name="" id="" value="0" /></td>
											</tr>
										</tbody>
									</table>
								</div>
							</td>
						</tr>
					</table>
				</div>
			</div>
		</div>
		<button class="close"><span class="blind">닫기</span></button>
		<div class="box_btn">
			<button>저장</button><button>닫기</button>
		</div>
	</div>
</div>

<script type="text/javascript" src="http://localhost:8080/hanagw/asset/js/jquery-1.11.1.min.js"></script>
<script type="text/javascript" src="http://localhost:8080/hanagw/asset/js/jquery-ui-1.10.4.custom.js"></script>
<script type="text/javascript" src="http://localhost:8080/hanagw/asset/js/jquery.easyui.min.js"></script>
<script type="text/javascript" src="http://localhost:8080/hanagw/asset/js/polyfill.js"></script>
<script type="text/javascript" src="http://localhost:8080/hanagw/asset/js/custombox.min.js"></script>
<script type="text/javascript" src="http://localhost:8080/hanagw/asset/js/default.js"></script>

</body>
</html>