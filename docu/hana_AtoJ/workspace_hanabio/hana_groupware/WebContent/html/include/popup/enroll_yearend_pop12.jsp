<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="ko">
<head>
	<%@include file="../../include/head.jsp"%>	
</head>
<body>

<!-- pop up 가로 사이즈 800px -->
<div class="wrap_pop_bg">
	<div class="wrap_statement_overtime">
		<div class="top">
			<h3>특별공제 <span>(기부금)</span></h3>
		</div>
		<div class="inner">
			<div class="cont_box cont_table06">
				<div class="pop_tit">
					<span class="tit"></span>
				</div>
				<div class="wrap_tbl_yearend">
					<table class="tbl_yearend contribute">
						<tr>
							<td class="box">
								<div>
									<button class="btn_row">+ 행추가</button>
									<table class="tbl_yearend medical">
										<tbody>
											<td class="inner">
												<div>
													<table class="tbl_yearend mgn">
														<colgroup>
															<col style="width:22px"/>
															<col style="width:72px"/>
															<col style="width:148px"/>
															<col style="width:100px"/>
															<col style="width:168px"/>
															<col style="width:100px"/>
															<col style="width:100px"/>
															<col style="width:100px"/>
															<col style="width:100px"/>
														</colgroup>
														<thead>
															<th></th>
															<th>구분</th>
															<th>주민번호</th>
															<th>성명</th>
															<th>기부금유형</th>
															<th>기부금액</th>
															<th>기부처상호</th>
															<th>기부처사업자번호</th>
															<th class="bdrn">기부처영수증번호</th>
														</thead>
														<tbody>
															<tr>
																<th>1</th>
																<td>
																	<select name="" id="">
																		<option>기타</option>
																		<option>국세청</option>
																	</select>
																</td>
																<td class="search">
																	<span class="find_box">
																		<input type="text" class="serch_date">
																		<button class="btn_find"><span class="blind">찾아보기</span></button>
																	</span>
																</td>
																<td><input type="text" name="" id="" class="ipt_disable"/></td>
																<td>
																	<select name="" id="">
																		<option>법정기부금</option>
																		<option>정치자금기부금</option>
																		<option>특례기부금(공익신탁제외)</option>
																		<option>공익신탁 기부금</option>
																		<option>지정기부금(종교신탁제외)</option>
																		<option>종교단체 기부금</option>
																		<option>우리사주조합기부금</option>
																	</select>
																</td>
																<td><input type="text" name="" id="" class="numright" /></td> <!-- 2015-02-25 수정 -->
																<td><input type="text" name="" id="" /></td>
																<td><input type="text" name="" id="" /></td>
																<td class="bdrn"><input type="text" name="" id="" /></td>
															</tr>	
														</tbody>
													</table>
	
													<table class="tbl_sum">
														<colgroup>
															<col style="width:22px"/>
															<col style="width:72px"/>
															<col style="width:148px"/>
															<col style="width:100px"/>
															<col style="width:168px"/>
															<col style="width:100px"/>
															<col style="width:100px"/>
															<col style="width:100px"/>
															<col style="width:100px"/>
														</colgroup>	
														<tbody>
															<tr>
																<td colspan="5">합계</td>
																<td><input type="text" name="" id="" value="0" /></td>
																<td><input type="text" name="" id="" value="0" /></td>
																<td><input type="text" name="" id="" value="0" /></td>
																<td><input type="text" name="" id="" value="0" /></td>
															</tr>
														</tbody>
													</table>
												</div>
											</td>
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