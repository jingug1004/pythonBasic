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
					<strong>기화기기안서</strong>
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
							<td colspan="3" class="subject"><input type="text" name="" id="" value="[거래처명] 세보프란전용 기화기 대여 요청의 건"/></td>
						</tr>
						<tr>
							<td colspan="4" class="pdn">
								<div class="inner_box no_scroll">
									<strong class="tit_s tit_sample">내 역</strong>
									<table class="tbl_vaporizer">
										<colgroup>
											<col class="cb_col3"> <!-- 2015-02-13 수정 -->
											<col style="">
											<col style="width:86px">
											<col style="">
										</colgroup>
										<tbody>
											<tr>
												<th>시행일자</th>
												<td colspan="3" class="date bdrn">
													<span class="date_box">
														<input type="text" class="serch_date" />
														<button class="btn_date"><span class="blind">날짜선택</span></button>
													</span>
												</td>
											</tr>
											<tr>
												<th>종류</th>
												<td colspan="3" class="bdrn">
													<ul>
														<li><input type="radio" name="type" id="t1" /><label for="t1">대여</label></li>	
														<li><input type="radio" name="type" id="t2" /><label for="t2">심폐마취기설치</label></li>	
														<li><input type="radio" name="type" id="t3" /><label for="t3">점검(CALIBRATION)</label></li>	
														<li><input type="radio" name="type" id="t4" /><label for="t4">업그레이드키트설치</label></li>	
														<li><input type="radio" name="type" id="t5" /><label for="t5">주입구교체</label></li>	
														<li><input type="radio" name="type" id="t6" /><label for="t6">어뎁터 구매(파손)</label></li>	
														<li class="mbn"><input type="radio" name="type" id="t7" /><label for="t7">마운트교체</label></li>	
														<li class="mbn"><input type="radio" name="type" id="t8" /><label for="t8">수리</label></li>	
													</ul>
												</td>
											</tr>
											<tr>
												<th>1. 거래처명</th>
												<td><input type="text" name="" id="" class="ipt_code" /></td>
												<th>2. 거래처코드</th>
												<td class="bdrn"><input type="text" name="" id="" class="ipt_code" /></td>
											</tr>
											<tr>
												<th>3. 대표자명</th>
												<td colspan="3" class="bdrn"><input type="text" name="" id="" /></td>
											</tr>
											<tr>
												<th>4. 기종 및 수량</th>
												<td><input type="text" name="" id="" class="ipt_code" /></td>
												<th>5. 월사용수량</th>
												<td class="bdrn"><input type="text" name="" id="" class="ipt_code" /></td>
											</tr>
											<tr>
												<th>6. 내용</th>
												<td colspan="3" class="bdrn con"><input type="text" name="" id="" class="ipt_content" value="[거래처명]세보프란전용 기화기000000000000000 요청하오니 검토 후 재가 바랍니다. "/></td>
											</tr>
											<tr>
												<th>7. 특이사항</th>
												<td colspan="3" class="ta bdrn">
													<textarea name="" id="" class="ta_vaporizer"></textarea>
												</td>
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