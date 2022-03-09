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
					<strong>휴가신청서</strong>
					<span>전결 가능합니다.</span>
				</h4>
				<table>
					<colgroup>
						<col class="cb_col1"> <!-- 2015-02-13 수정 -->
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
									<strong class="tit_s">내 역</strong>
									<table>
										<colgroup>
											<col class="cb_col2"> <!-- 2015-02-13 수정 -->
											<col style="width:229px">
											<col style="width:86px">
											<col style="width:90px">
											<col style="width:100px">
											<col style="">
										</colgroup>
										<tr>
											<th>휴가종류</th>
											<td class="holliday"><input type="text" name="" id="" /></td>
											<th>남은 연차일</th>
											<td class="annual">2.5</td>
											<th>연차사용가능일</th>
											<td class="bdrn annual">15.0</td>
										</tr>
										<tr>
											<th>휴가기간</th>
											<td>
												<span class="date_box">
													<input type="text" class="serch_date" />
													<button class="btn_date"><span class="blind">날짜선택</span></button>
												</span> ~ 
												<span class="date_box">
													<input type="text" class="serch_date" />
													<button class="btn_date"><span class="blind">날짜선택</span></button>
												</span>
											</td>
											<th>올해 사용일</th>
											<td class="annual">12.5</td>
											<th>지난해 사용일</th>
											<td class="bdrn annual">0.0</td>
										</tr>
										<tr>
											<th>휴가사유</th>
											<td colspan="5" class="bdrn cause"><textarea name="" id=""></textarea></td>
										</tr>
										<tr>
											<th>비상연락처</th>
											<td colspan="5" class="bdrn"><input type="text" name="" id="" /></td>
										</tr>
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