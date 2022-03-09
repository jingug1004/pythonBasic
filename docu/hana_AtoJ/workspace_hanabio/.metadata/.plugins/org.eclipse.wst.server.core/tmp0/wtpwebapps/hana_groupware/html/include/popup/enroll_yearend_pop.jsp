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
			<h3>전화번호와 주소정보 입력 (미입력시 연말정산 등록화면으로 넘어가지 않습니다.)</h3>
		</div>
		<div class="inner">
			<div class="cont_box cont_table06">
				<div class="pop_tit">
					<span class="tit"></span>
				</div>
				<div class="wrap_tbl_yearend">
					<table class="tbl_yearend zipcode">
						<tr>
							<td class="box">
								<div>
									<button class="btn_row">+ 행추가</button>
									<table class="tbl_yearend mgn">
										<colgroup>
											<col style="width:22px"/>
											<col style="width:65px"/>
											<col style="width:98px"/>
											<col style="width:82px"/>
											<col style=""/>
											<col style=""/>
										</colgroup>
										<thead>
											<th></th>
											<th>사원번호</th>
											<th>휴대전화번호</th>
											<th>우편번호</th>
											<th>주소1</th>
											<th>상세주소</th>
										</thead>
										<tbody>
											<tr>
												<th>1</th>
												<td><input type="text" name="" id="" class="ipt_num" value="2014166" /></td>
												<td><input type="text" name="" id="" class="ipt_tel" value="010-4386-8613"/></td>
												<td class="search">
													<span class="find_box">
														<input type="text" class="serch_date" value="445992" />
														<button class="btn_find"><span class="blind">찾아보기</span></button>
													</span>
												</td>
												<td><input type="text" name="" id="" class="ipt_adrs" value="경기도 화성시 효행로265번길 우림그린빌리지 17 0" /></td>
												<td><input type="text" name="" id="" class="ipt_adrs2" value="107/704" /></td>
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