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
			<h3>기본공제 <span>(가족사항)</span></h3>
		</div>
		<div class="inner">
			<div class="cont_box cont_table06">
				<div class="pop_tit">
					<span class="tit"></span>
				</div>
				<div class="wrap_tbl_yearend">
					<table class="tbl_yearend family">
						<tr>
							<td class="box">
								<div>
									<button class="btn_row">+ 행추가</button>
									<table class="tbl_yearend mgn">
										<colgroup>
											<col style="width:22px"/>
											<col style="width:70px"/>
											<col style=""/>
											<col style="width:140px"/>
											<col style=""/>
											<col style="width:22px"/>
											<col style="width:22px"/>
											<col style="width:22px"/>
											<col style="width:22px"/>
											<col style="width:22px"/>
											<col style="width:22px"/>
											<col style="width:22px"/>
											<col style="width:22px"/>
											<col style="width:22px"/>
											<col style="width:22px"/>
											<col style="width:22px"/>
											<col style="width:22px"/>
										</colgroup>
										<thead>
											<th></th>
											<th class="type01">내외국인</th>
											<th class="type02">성명</th>
											<th class="type01">관계코드</th>
											<th class="type01">관계인주민번호</th>
											<th class="type02">기본</th>
											<th class="type02">수급자</th>
											<th class="type02">위탁아동</th>
											<th class="type03">경로우대</th>
											<th class="type03">장애인</th>
											<th class="type03">부녀자</th>
											<th class="type03">한부모</th>
											<th class="type04">보험료</th>
											<th class="type04">의료비</th>
											<th class="type04">교육비</th>
											<th class="type04">신용카드</th>
											<th class="type04">기부금</th>
										</thead>
										<tbody>
											<tr>
												<th>1</th>
												<td>
													<select name="" id="">
														<option>내국인</option>
														<option>외국인</option>
													</select>
												</td>
												<td><input type="text" name="" id="" value="장수민"/></td>
												<td>
													<select name="" id="" class="sec_code">
														<option>소득자본인</option>
														<option>소득자의직계존속</option>
														<option>배우자의직계존속</option>
														<option>배우자</option>
														<option>직계비속(자녀,입양자)</option>
														<option>직계비속(위 항목 제외)</option>
														<option>형제자매</option>
														<option>수급자</option>
														<option>위탁아동</option>
													</select>
												</td>
												<td><input type="text" name="" id="" value="970204-2163719"/></td>
												<td><input type="checkbox" name="" id="" checked="checked"/></td>
												<td><input type="checkbox" name="" id="" /></td>
												<td><input type="checkbox" name="" id="" /></td>
												<td><input type="checkbox" name="" id="" /></td>
												<td><input type="checkbox" name="" id="" /></td>
												<td><input type="checkbox" name="" id="" /></td>
												<td><input type="checkbox" name="" id="" /></td>
												<td><input type="checkbox" name="" id="" checked="checked"/></td>
												<td><input type="checkbox" name="" id="" checked="checked"/></td>
												<td><input type="checkbox" name="" id="" checked="checked"/></td>
												<td><input type="checkbox" name="" id="" checked="checked"/></td>
												<td><input type="checkbox" name="" id="" checked="checked"/></td>
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