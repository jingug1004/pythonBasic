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
			<h3>특별 공제 <span>(의료비)</span></h3>
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
									<table class="tbl_yearend medical">
										<tbody>
											<td class="inner">
												<div>
													<table>
														<colgroup>
															<col style="width:22px"/>
															<col style="width:132px"/> <!-- 2015-02-25 수정 -->
															<col style="width:100px"/>
															<col style="width:212px"/>
															<col style="width:100px"/>
															<col style="width:215px"/>
															<col style="width:70px"/> <!-- 2015-02-25 수정 -->
															<col style="width:120px"/> <!-- 2015-02-25 수정 -->
														</colgroup>
														<thead>
															<tr>
																<th></th>
																<th>관계인주민번호</th>
																<th>이름</th>
																<th>증빙구분</th>
																<th>지급처사업자번호</th>
																<th>지급처 사업자명</th>
																<th>지급건수</th>
																<th class="bdrn">지급금액</th>
															</tr>	
	
														</thead>
														<tbody>
															<tr>
																<th>1</th>
																<td class="search">
																	<span class="find_box">
																		<input type="text" class="serch_date jm_num"> <!-- 2015-02-25 수정 -->
																		<button class="btn_find"><span class="blind">찾아보기</span></button>
																	</span>
																</td>
																<td><input type="text" name="" id="" class="ipt_disable"/></td>
																<td>
																	<select name="" id="" class="sec_cls">
																		<option>국세청장이 제공하는 의료비 자료</option>
																		<option>국민건강보험 공단의 의료비 부담</option>
																		<option>진료비 계산서, 약제비 계산서</option>
																		<option>장기요양급여비용 명세서</option>
																		<option>기타 의료비 영수증</option>
																	</select>
																</td>
																<td><input type="text" name="" id="" /></td>
																<td><input type="text" name="" id="" class="ipt_workplace"/></td>
																<td><input type="text" name="" id="" class="ipt_count orcnt"/></td> <!-- 2015-02-25 수정 -->
																<td class="bdrn"><input type="text" name="" id="" class="ipt_sum orsum"/></td> <!-- 2015-02-25 수정 -->
															</tr>	
														</tbody>
													</table>
	
													<table class="tbl_sum">
														<colgroup>
															<col style="width:22px"/>
															<col style="width:132px"/> <!-- 2015-02-25 수정 -->
															<col style="width:100px"/>
															<col style="width:212px"/>
															<col style="width:100px"/>
															<col style="width:215px"/>
															<col style="width:68px"/> <!-- 2015-02-25 수정 -->
															<col style="width:119px"/> <!-- 2015-02-25 수정 -->
														</colgroup>									
														<tr>
															<td colspan="6">합계</td>
															<td><input type="text" name="" id="" value="0" class="ipt_count"/></td>
															<td><input type="text" name="" id="" value="0" class="ipt_sum"/></td>
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