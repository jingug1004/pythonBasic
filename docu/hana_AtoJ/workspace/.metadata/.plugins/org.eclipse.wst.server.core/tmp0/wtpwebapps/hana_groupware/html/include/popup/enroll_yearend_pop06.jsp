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
			<h3>그밖의 소득공제 <span>(신용카드 등 사용금액)</span></h3>
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
									<table class="tbl_yearend credit">
										<tbody>
											<td class="inner">
												<div>
													<table>
														<colgroup>
															<col style="width:22px"/>
															<col style="width:72px"/>
															<col style="width:100px"/>
															<col style="width:140px"/> <!-- 2015-02-25 수정 -->
															<col style="width:100px"/>
															<col style="width:100px"/>
															<col style="width:100px"/>
															<col style="width:100px"/>
															<col style="width:100px"/>
															<col style="width:100px"/>
															<col style="width:100px"/>
															<col style="width:100px"/>
															<col style="width:100px"/>
															<col style="width:100px"/>
															<col style="width:100px"/>
															<col style="width:100px"/>
															<col style="width:100px"/>
															<col style="width:100px"/>
															<col style="width:100px"/>
															<col style="width:100px"/>
														</colgroup>
														<thead>
															<tr>
																<th colspan="5"></th>
																<th colspan="5">2014 상반기</th>
																<th colspan="5">2014 하반기</th>
																<th colspan="5" class="bdrn type03">2013년 신용카드 등 본인 사용분 입력란</th>
															</tr>
															<tr>
																<th></th>
																<th>내외국인</th>
																<th class="type02">성명</th>
																<th>관계코드</th>
																<th>관계인주민번호</th>
																<th>신용카드-국세청</th>
																<th>현금영수증-국세청</th>
																<th>직불카드-국세청</th>
																<th>전통시장-국세청</th>
																<th>대중교통-국세청</th>
																<th>신용카드-국세청</th>
																<th>현금영수증-국세청</th>
																<th>직불카드-국세청</th>
																<th>전통시장-국세청</th>
																<th>대중교통-국세청</th>
																<th class="type03">신용카드-국세청</th>
																<th class="type03">현금영수증-국세청</th>
																<th class="type03">직불카드-국세청</th>
																<th class="type03">전통시장-국세청</th>
																<th class="bdrn type03">대중교통-국세청</th>
															</tr>	
															<tr>
																<th colspan="5"></th>
																<th>그밖의 금액</th>
																<th>그밖의 금액</th>
																<th>그밖의 금액</th>
																<th>그밖의 금액</th>
																<th>그밖의 금액</th>
																<th>그밖의 금액</th>
																<th>그밖의 금액</th>
																<th>그밖의 금액</th>
																<th>그밖의 금액</th>
																<th>그밖의 금액</th>
																<th class="type03">그밖의 금액</th>
																<th class="type03">그밖의 금액</th>
																<th class="type03">그밖의 금액</th>
																<th class="type03">그밖의 금액</th>
																<th class="bdrn type03">그밖의 금액</th>
															</tr>
														</thead>
														<tbody>
															<tr>
																<th>1</th>
																<td>
																	<select name="" id="" class="ipt_disable">
																		<option>내국인</option>
																	</select>
																</td>
																<td><input type="text" name="" id="" class="ipt_disable" value="장수민"/></td>
																<td>
																	<select name="" id="" class="ipt_disable selsize"> <!-- 2015-02-25 수정 -->
																		<option>소득자의직계존속</option>
																		<option>소득자본인</option>
																		<option>소득자본인</option>
																	</select>
																</td>
																<td><input type="text" name="" id="" class="ipt_disable" value="970204-2163719"></td>
																<td><input type="text" name="" id="" class="numright" /></td> <!-- 2015-02-25 수정 -->
																<td><input type="text" name="" id="" class="numright" /></td> <!-- 2015-02-25 수정 -->
																<td><input type="text" name="" id="" class="numright" /></td> <!-- 2015-02-25 수정 -->
																<td><input type="text" name="" id="" class="numright" /></td> <!-- 2015-02-25 수정 -->
																<td><input type="text" name="" id="" class="numright" /></td> <!-- 2015-02-25 수정 -->
																<td><input type="text" name="" id="" class="numright" /></td> <!-- 2015-02-25 수정 -->
																<td><input type="text" name="" id="" class="numright" /></td> <!-- 2015-02-25 수정 -->
																<td><input type="text" name="" id="" class="numright" /></td> <!-- 2015-02-25 수정 -->
																<td><input type="text" name="" id="" class="numright" /></td> <!-- 2015-02-25 수정 -->
																<td><input type="text" name="" id="" class="numright" /></td> <!-- 2015-02-25 수정 -->
																<td><input type="text" name="" id="" class="numright" /></td> <!-- 2015-02-25 수정 -->
																<td><input type="text" name="" id="" class="numright" /></td> <!-- 2015-02-25 수정 -->
																<td><input type="text" name="" id="" class="numright" /></td> <!-- 2015-02-25 수정 -->
																<td><input type="text" name="" id="" class="numright" /></td> <!-- 2015-02-25 수정 -->
																<td class="bdrn"><input type="text" name="" id="" class="numright" /></td> <!-- 2015-02-25 수정 -->
															</tr>	
															<tr>
																<td colspan="5"></td>
																<td><input type="text" name="" id="" class="numright" /></td> <!-- 2015-02-25 수정 -->
																<td><input type="text" name="" id="" class="numright" /></td> <!-- 2015-02-25 수정 -->
																<td><input type="text" name="" id="" class="numright" /></td> <!-- 2015-02-25 수정 -->
																<td><input type="text" name="" id="" class="numright" /></td> <!-- 2015-02-25 수정 -->
																<td><input type="text" name="" id="" class="numright" /></td> <!-- 2015-02-25 수정 -->
																<td><input type="text" name="" id="" class="numright" /></td> <!-- 2015-02-25 수정 -->
																<td><input type="text" name="" id="" class="numright" /></td> <!-- 2015-02-25 수정 -->
																<td><input type="text" name="" id="" class="numright" /></td> <!-- 2015-02-25 수정 -->
																<td><input type="text" name="" id="" class="numright" /></td> <!-- 2015-02-25 수정 -->
																<td><input type="text" name="" id="" class="numright" /></td> <!-- 2015-02-25 수정 -->
																<td><input type="text" name="" id="" class="numright" /></td> <!-- 2015-02-25 수정 -->
																<td><input type="text" name="" id="" class="numright" /></td> <!-- 2015-02-25 수정 -->
																<td><input type="text" name="" id="" class="numright" /></td> <!-- 2015-02-25 수정 -->
																<td><input type="text" name="" id="" class="numright" /></td> <!-- 2015-02-25 수정 -->
																<td class="bdrn"><input type="text" name="" id="" class="numright" /></td> <!-- 2015-02-25 수정 -->
															</tr>	
														</tbody>
													</table>
	
													<table class="tbl_sum">
														<colgroup>
															<col style="width:22px"/>
															<col style="width:72px"/>
															<col style="width:100px"/>
															<col style="width:140px"/> <!-- 2015-02-25 수정 -->
															<col style="width:100px"/>
															<col style="width:100px"/>
															<col style="width:100px"/>
															<col style="width:100px"/>
															<col style="width:100px"/>
															<col style="width:100px"/>
															<col style="width:100px"/>
															<col style="width:100px"/>
															<col style="width:100px"/>
															<col style="width:100px"/>
															<col style="width:100px"/>
															<col style="width:100px"/>
															<col style="width:100px"/>
															<col style="width:100px"/>
															<col style="width:100px"/>
															<col style="width:100px"/>
														</colgroup>									
														<tr>
															<td colspan="4"></td>
															<td>합계</td>
															<td><input type="text" name="" id="" value="0"></td>
															<td><input type="text" name="" id="" value="0"></td>
															<td><input type="text" name="" id="" value="0"></td>
															<td><input type="text" name="" id="" value="0"></td>
															<td><input type="text" name="" id="" value="0"></td>
															<td><input type="text" name="" id="" value="0"></td>
															<td><input type="text" name="" id="" value="0"></td>
															<td><input type="text" name="" id="" value="0"></td>
															<td><input type="text" name="" id="" value="0"></td>
															<td><input type="text" name="" id="" value="0"></td>
															<td><input type="text" name="" id="" value="0"></td>
															<td><input type="text" name="" id="" value="0"></td>
															<td><input type="text" name="" id="" value="0"></td>
															<td><input type="text" name="" id="" value="0"></td>
															<td><input type="text" name="" id="" value="0"></td>
														</tr>
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