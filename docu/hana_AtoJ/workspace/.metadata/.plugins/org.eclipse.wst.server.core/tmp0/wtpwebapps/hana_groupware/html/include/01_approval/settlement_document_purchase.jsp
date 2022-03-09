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
			<h3>신규문서작성<span> STEP1</span></h3>
			<button class="close"><span class="blind">닫기</span></button>
			<div>
				<div class="fr">
					<button class="type2">이전</button>
					<button class="type2">결재라인지정</button>
				</div>
			</div>
		</div>
		<div class="inner">
			<div class="cont_box cont_table06">
				<div class="pop_tit">
					<span class="tit"><em>STEP	1</em>. 문서내용작성</span>
					<p>해당 문서 내용을 작성하고 완료가 되면 결재라인 지정 버튼을 클릭하시기 바랍니다. STEP2로 이동합니다. </p>
				</div>
				<h4>
					<span class="check"><input type="checkbox" name="secret" id="secret" /><label for="secret">대외비입니다.</label></span>
					<strong>구매신청서</strong>
					<span>전결 가능합니다.</span>
				</h4>
				<table>
					<colgroup>
						<col style="width:87px">
						<col style="width:260px">
						<col style="width:86px">
						<col style="width:260px">
					</colgroup>
					<tbody>
						<tr>
							<th>문서번호</th>
							<td>전산-2014-0011</td>
							<th>작성일자</th>
							<td>2014-10-15 10:40</td>
						</tr>
						<tr>
							<th>작성부서</th>
							<td>영업부</td>
							<th>작성자</th>
							<td>이수연</td>
						</tr>
						<tr>
							<th>제목</th>
							<td colspan="3" class="subject"><input type="text" name="" id="" /></td>
						</tr>
						<tr>
							<td colspan="4" class="pdn">
								<div class="inner_box no_scroll">
									<strong class="tit_s tit_sample">
										내 역
										<button class="btn_row lhfx">+ 행추가</button> <!-- 2015-02-17 수정 -->
									</strong>
									<table class="tbl_purchase">
										<colgroup>
											<col style="width:111px">
											<col style="width:50px">
											<col style="width:71px">
											<col style="width:97px">
											<col style="">
											<col style="width:118px">
											<col style="width:46px">
										</colgroup>
										<thead>
											<th>품명</th>
											<th>규격</th>
											<th>수량</th>
											<th>납품요구일</th>
											<th>사무 및 목적</th>
											<th>비고</th>
											<th class="bdrn">삭제</th>
										</thead>
										<tbody>
											<tr>
												<td><input type="text" name="" id="" class="ipt_purchase" /></td>
												<td><input type="text" name="" id="" class="ipt_purchase" /></td>
												<td><input type="text" name="" id="" class="ipt_purchase" /></td>
												<td class="date">
													<span class="date_box">
														<input type="text" class="serch_date" />
														<button class="btn_date"><span class="blind">날짜선택</span></button>
													</span>
												</td>
												<td><input type="text" name="" id="" class="ipt_purchase" /></td>
												<td><input type="text" name="" id="" class="ipt_purchase" /></td>
												<td class="bdrn btn"><button>삭제</button></td>
											</tr>
											<tr>
												<td><input type="text" name="" id="" class="ipt_purchase" /></td>
												<td><input type="text" name="" id="" class="ipt_purchase" /></td>
												<td><input type="text" name="" id="" class="ipt_purchase" /></td>
												<td class="date">
													<span class="date_box">
														<input type="text" class="serch_date" />
														<button class="btn_date"><span class="blind">날짜선택</span></button>
													</span>
												</td>
												<td><input type="text" name="" id="" class="ipt_purchase" /></td>
												<td><input type="text" name="" id="" class="ipt_purchase" /></td>
												<td class="bdrn btn"><button>삭제</button></td>
											</tr>
											<tr>
												<td><input type="text" name="" id="" class="ipt_purchase" /></td>
												<td><input type="text" name="" id="" class="ipt_purchase" /></td>
												<td><input type="text" name="" id="" class="ipt_purchase" /></td>
												<td class="date">
													<span class="date_box">
														<input type="text" class="serch_date" />
														<button class="btn_date"><span class="blind">날짜선택</span></button>
													</span>
												</td>
												<td><input type="text" name="" id="" class="ipt_purchase" /></td>
												<td><input type="text" name="" id="" class="ipt_purchase" /></td>
												<td class="bdrn btn"><button>삭제</button></td>
											</tr>
											<tr>
												<td><input type="text" name="" id="" class="ipt_purchase" /></td>
												<td><input type="text" name="" id="" class="ipt_purchase" /></td>
												<td><input type="text" name="" id="" class="ipt_purchase" /></td>
												<td class="date">
													<span class="date_box">
														<input type="text" class="serch_date" />
														<button class="btn_date"><span class="blind">날짜선택</span></button>
													</span>
												</td>
												<td><input type="text" name="" id="" class="ipt_purchase" /></td>
												<td><input type="text" name="" id="" class="ipt_purchase" /></td>
												<td class="bdrn btn"><button>삭제</button></td>
											</tr>
											<tr>
												<td><input type="text" name="" id="" class="ipt_purchase" /></td>
												<td><input type="text" name="" id="" class="ipt_purchase" /></td>
												<td><input type="text" name="" id="" class="ipt_purchase" /></td>
												<td class="date">
													<span class="date_box">
														<input type="text" class="serch_date" />
														<button class="btn_date"><span class="blind">날짜선택</span></button>
													</span>
												</td>
												<td><input type="text" name="" id="" class="ipt_purchase" /></td>
												<td><input type="text" name="" id="" class="ipt_purchase" /></td>
												<td class="bdrn btn"><button>삭제</button></td>
											</tr>
											<tr>
												<td><input type="text" name="" id="" class="ipt_purchase" /></td>
												<td><input type="text" name="" id="" class="ipt_purchase" /></td>
												<td><input type="text" name="" id="" class="ipt_purchase" /></td>
												<td class="date">
													<span class="date_box">
														<input type="text" class="serch_date" />
														<button class="btn_date"><span class="blind">날짜선택</span></button>
													</span>
												</td>
												<td><input type="text" name="" id="" class="ipt_purchase" /></td>
												<td><input type="text" name="" id="" class="ipt_purchase" /></td>
												<td class="bdrn btn"><button>삭제</button></td>
											</tr>
										</tbody>
									</table>
								</div>						
							</td>
						</tr>
						<tr>
							<th class="append_tit">첨부파일</th>
							<td colspan="7" class="append_file br_none">
								<div class="append_search">
									<input type="text">
									<button>찾아보기</button>
								</div>
								<!-- 파일 드래그 & 드랍시 start (2014.11.17 수정 추가) -->
								<ul class="drop_file">
	
								</ul>
								<!-- 파일 드래그 & 드랍시 end-->								
								<div class="append_list">
									<span>aaa.jpg</span>
									<a href=""><img src="http://localhost:8080/hanagw/asset/img/popup_btn_close01.gif" alt="파일삭제"></a>
									<p>마우스로 파일을 끌어 넣으세요</p>
								</div>
							</td>
						</tr>							
					</tbody>
				</table>
			</div>
	
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