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
			<h3>야근내역서<span> STEP3</span></h3>
			<button class="close"><span class="blind">닫기</span></button>
			<div>
				<button class="type2">삭제</button>
				<div class="fr">
					<button class="type2">이전</button>
					<button class="type2">작성완료</button>
					<button class="type2">요청</button>
				</div>
			</div>
		</div>
		<div class="inner">
			<div class="cont_box cont_table06">
				<div class="pop_tit">
					<span class="tit"><em>STEP	3</em>. 최종확인</span>
					<p>문서 내용과 결재라인을 최종 확인하시고 요청버튼을 클릭하시면 상신됩니다. 작성완료버튼을 클릭하시면 내가 올린 문서에 작성 중 상태로 등록됩니다. </p>
				</div>
				<h4>
					<span>대외비입니다.</span>
					<strong>야근내역서</strong>
					<span>전결 가능합니다.</span>
				</h4>
				<table>
					<colgroup>
						<col style="width:12%">
						<col style="width:12%">
						<col style="width:12%">
						<col style="width:12%">
						<col style="width:12%">
						<col style="width:12%">
						<col style="width:12%">
						<col style="width:12%">
					</colgroup>
					<tbody>
						<tr>
							<th>문서번호</th>
							<td colspan="3">전산-2014-0011</td>
							<th>작성일자</th>
							<td colspan="3">2014-10-15 10:40</td>
						</tr>
						<tr>
							<th>작성부서</th>
							<td colspan="3">영업부</td>
							<th>작성자</th>
							<td colspan="3">이수연</td>
						</tr>
						<tr class="appr">
							<th rowspan="2">결재</th>
							<td>팀장</td>
							<td>부서장</td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
						</tr>
						<tr class="appr">
							<td>1<br /><br />아무개</td>
							<td>2<br /><br />홍길동</td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
						</tr>
						<tr>
							<th>시행부서</th>
							<td colspan="7" class="implement">
								<div>총무팀 총무팀 총무팀 총무팀 총무팀 총무팀 총무팀 총무팀 총무팀
								</div>
							</td>
						</tr>
						<tr>
							<th>제목</th>
							<td colspan="7"></td>
						</tr>
						<tr>
							<td colspan="8" class="breakdown">
								<div class="inner_box">
									<strong class="tit_s">내 역</strong>
									<table>
										<colgroup>
											<col style="width:86px">
											<col style="">
											<col style="width:86px">
											<col style="">
										</colgroup>
										<tr>
											<th>요청일자</th>
											<td class="date">
												<span class="date_box">
													<input type="text" class="serch_date">
													<button class="btn_date"><span class="blind">날짜선택</span></button>
												</span>
											</td>
											<th>오픈요청일</th>
											<td class="date bdrn">
												<span class="date_box">
													<input type="text" class="serch_date">
													<button class="btn_date"><span class="blind">날짜선택</span></button>
												</span>
											</td>
										</tr>
										<tr>
											<th>긴급구분</th>
											<td>
												<input type="checkbox" name="urgency" id="urgency" /><label for="u1">상</label>
												<input type="checkbox" name="urgency" id="urgency" /><label for="u2">중</label>
												<input type="checkbox" name="urgency" id="urgency" /><label for="u3">하</label>
											</td>
											<th>선택구분</th>
											<td class="bdrn">
												<input type="checkbox" name="field" id="field" /><label for="f1">인사급여</label>
												<input type="checkbox" name="field" id="field" /><label for="f2">영업관리</label>
												<input type="checkbox" name="field" id="field" /><label for="f3">회계관리</label>
											</td>
										</tr>
										<tr>
											<th>제목</th>
											<td colspan="3" class="bdrn"></td>
										</tr>
										<tr>
											<th>제안배경</th>
											<td colspan="3" class="bdrn"></td>
										</tr>
										<tr>
											<th>상세내용</th>
											<td colspan="3" class="bdrn"></td>
										</tr>
										<tr>
											<th>실현방법</th>
											<td colspan="3" class="bdrn"></td>
										</tr>
										<tr>
											<th>특기사항</th>
											<td colspan="3" class="bdrn"></td>
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