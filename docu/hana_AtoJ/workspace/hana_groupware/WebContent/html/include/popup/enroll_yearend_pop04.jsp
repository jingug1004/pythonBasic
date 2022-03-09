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
			<h3>개인연금저축</h3>
		</div>
		<div class="inner">
			<div class="cont_box cont_table06">
				<div class="pop_tit">
					<span class="tit"></span>
				</div>
				<div class="wrap_tbl_yearend">
					<table class="tbl_yearend saving">
						<tr>
							<td class="box">
								<div>
									<button class="btn_row">+ 행추가</button>
									<table class="tbl_yearend mgn">
										<colgroup>
											<col style="width:22px"/>
											<col style="width:132px"/>
											<col style="width:152px"/>
											<col style=""/>
											<col style=""/>
											<col style=""/>
										</colgroup>
										<thead>
											<th></th>
											<th>소득공제구분</th>
											<th>금융기관명</th>
											<th>금융기관코드</th>
											<th>계좌번호</th>
											<th>불입금액</th>
										</thead>
										<tbody>
											<tr>
												<th>1</th>
												<td>
													<select name="" id="" class="sec_cls">
														<option>개인연금저축</option>
													</select>
												</td>
												<td class="search">
													<span class="find_box">
														<input type="text" class="serch_date">
														<button class="btn_find"><span class="blind">찾아보기</span></button>
													</span>
												</td>
												<td><input type="text" name="" id="" class="ipt_disable"/></td>
												<td><input type="text" name="" id="" /></td>
												<td><input type="text" name="" id="" class="numright" /></td> <!-- 2015-02-25 수정 -->
											</tr>	
										</tbody>
									</table>
	
									<table class="tbl_sum">
										<colgroup>
											<col style="width:22px"/>
											<col style="width:132px"/>
											<col style="width:152px"/>
											<col style=""/>
											<col style=""/>
											<col style=""/>
										</colgroup>	
										<tbody>
											<tr>
												<td colspan="5"></td>
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