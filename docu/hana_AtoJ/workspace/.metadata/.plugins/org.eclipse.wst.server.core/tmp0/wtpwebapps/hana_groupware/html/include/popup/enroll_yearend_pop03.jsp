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
			<h3>특별공제 <span>(주택자금)</span></h3>
		</div>
		<div class="inner">
			<div class="cont_box cont_table06">
				<div class="pop_tit">
					<span class="tit"></span>
				</div>
				<div class="wrap_tbl_yearend">
					<table class="tbl_yearend">
						<tr>
							<td class="box">
								<div>
									<button class="btn_row">+ 행추가</button>
									<table class="tbl_yearend house">
										<tbody>
											<td class="inner">
												<div>
													<table>
														<colgroup>
															<col style="width:22px"/>
															<col style="width:172px"/>
															<col style="width:101px"/>
															<col style="width:101px"/>
															<col style="width:98px"/>
															<col style="width:98px"/>
															<col style="width:101px"/>
															<col style="width:101px"/>
															<col style="width:98px"/>
															<col style="width:98px"/>
															<col style="width:98px"/>
															<col style="width:230px"/>
														</colgroup>
														<thead>
															<tr>
																<th></th>
																<th>입력선택</th>
																<th>성명 (상호)</th>
																<th>주민번호 or<br />사업자번호</th>
																<th>계약기간 시작</th>
																<th>계약기간 종료</th>
																<th>차입금 이자율<br />전세 보증금</th>
																<th>원리금 상환액 계</th>
																<th>원금</th>
																<th>이자</th>
																<th>공제금액</th>
																<th class="bdrn">임대차 계약서상 주소지</th>
															</tr>									
														</thead>
														<tbody>
															<tr>
																<th>1</th>
																<td>
																	<select name="" id="">
																		<option>주택임차차입금 금전소비대</option>
																		<option>주택임차차입금 임대차</option>
																	</select>
																</td>
																<td><input type="text" name="" id="" /></td>
																<td><input type="text" name="" id="" /></td>
																<td class="date">
																	<span class="date_box">
																		<input type="text" class="serch_date">
																		<button class="btn_date"><span class="blind">날짜선택</span></button>
																	</span>															
																</td>
																<td class="date">
																	<span class="date_box">
																		<input type="text" class="serch_date">
																		<button class="btn_date"><span class="blind">날짜선택</span></button>
																	</span>															
																</td>
																<td><input type="text" name="" id="" class="numright" /></td> <!-- 2015-02-25 수정 -->
																<td><input type="text" name="" id="" class="numright" /></td> <!-- 2015-02-25 수정 -->
																<td></td>
																<td></td>
																<td></td>
																<td class="bdrn"><input type="text" name="" id=""/></td>
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