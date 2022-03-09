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
					<strong>사고보고서</strong>
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
							<td colspan="3" class="subject"><input type="text" name="" id="" value="[거래처명] 세보프란전용 기화기 대여 요청의 건"/></td>
						</tr>
						<tr>
							<td colspan="4" class="pdn">
								<div class="inner_box no_scroll ie8sn">
									<strong class="tit_s tit_sample">
										내 역
										<span class="refer">* 첨부서류 : 거래처카드</span>
									</strong>
									<table class="tbl_accident">
										<colgroup>
											<col style="width:52px">
											<col style="width:60px">
											<col style="width:72px">
											<col style="width:72px">
											<col style="width:88px">
											<col style="width:72px">
										</colgroup>
										<tbody>
											<tr>
												<th>거래처명</th>
												<td colspan="2"><input type="text" name="" id="" /></td>
												<th>거래처코드</th>
												<td colspan="2" class="bdrn"><input type="text" name="" id="" /></td>
											</tr>
											<tr>
												<th>지점</th>
												<td colspan="2"><input type="text" name="" id="" /></td>
												<th>담당자</th>
												<td colspan="2" class="bdrn"><input type="text" name="" id="" /></td>
											</tr>
											<tr>
												<th>사고구분</th>
												<td colspan="2"><input type="text" name="" id="" /></td>
												<th>총매출 / 총수금</th>
												<td colspan="2" class="account bdrn"><input type="text" name="" id="" /> <span class="swung">/</span> <input type="text" name="" id="" /></td>
											</tr>
											<tr>
												<th>사고발생일</th>
												<td colspan="2" class="date">
													<span class="date_box">
														<input type="text" class="serch_date" />
														<button class="btn_date"><span class="blind">날짜선택</span></button>
													</span>
												</td>
												<th>최종거래일</th>
												<td colspan="2" class="bdrn date">
													<span class="date_box">
														<input type="text" class="serch_date" />
														<button class="btn_date"><span class="blind">날짜선택</span></button>
													</span>
												</td>
											</tr>
											<tr>
												<th rowspan="5">거래처<br />상세내역</th>
												<th>구분</th>
												<th>이름/상호</th>
												<th>주민/사업자번호</th>
												<th colspan="2" class="bdrn">주소/소재지</th>
											</tr>
											<tr>
												<th>거래처</th>
												<td><input type="text" name="" id="" /></td>
												<td><input type="text" name="" id="" /></td>
												<td colspan="2" class="bdrn"><input type="text" name="" id="" /></td>				
											</tr>
											<tr>
												<th>대표자</th>
												<td><input type="text" name="" id="" /></td>
												<td><input type="text" name="" id="" /></td>
												<td colspan="2" class="bdrn"><input type="text" name="" id="" /></td>				
											</tr>
											<tr>
												<th>연대보증인1</th>
												<td><input type="text" name="" id="" /></td>
												<td><input type="text" name="" id="" /></td>
												<td colspan="2" class="bdrn"><input type="text" name="" id="" /></td>				
											</tr>
											<tr>
												<th>연대보증인2</th>
												<td><input type="text" name="" id="" /></td>
												<td><input type="text" name="" id="" /></td>
												<td colspan="2" class="bdrn"><input type="text" name="" id="" /></td>				
											</tr>
											<tr>
												<th rowspan="2">채권내역</th>
												<th>외상물품대금</th>
												<td><input type="text" name="" id="" /></td>
												<th>미도래어음</th>
												<td colspan="2" class="bdrn"><input type="text" name="" id="" /></td>
											</tr>
											<tr>
												<th>부도채권총액</th>
												<td colspan="4" class="amount bdrn"><input type="text" name="" id="" /></td>
											</tr>
											<tr>
												<th rowspan="6">담보현황</th>
												<th></th>
												<th>금액</th>
												<th>만기일</th>
												<th>부동산소재지/발행인</th>
												<th class="bdrn">배서인</th>
											</tr>
											<tr>
												<th>부동산</th>
												<td><input type="text" name="" id="" /></td>
												<td><input type="text" name="" id="" /></td>
												<td><input type="text" name="" id="" /></td>
												<td class="bdrn"><input type="text" name="" id="" /></td>
											</tr>
											<tr>
												<th>어음/수표</th>
												<td><input type="text" name="" id="" /></td>
												<td><input type="text" name="" id="" /></td>
												<td><input type="text" name="" id="" /></td>
												<td class="bdrn"><input type="text" name="" id="" /></td>
											</tr>
											<tr>
												<th>보증보험</th>
												<td><input type="text" name="" id="" /></td>
												<td><input type="text" name="" id="" /></td>
												<td><input type="text" name="" id="" /></td>
												<td class="bdrn"><input type="text" name="" id="" /></td>
											</tr>
											<tr>
												<th>지급보증</th>
												<td><input type="text" name="" id="" /></td>
												<td><input type="text" name="" id="" /></td>
												<td><input type="text" name="" id="" /></td>
												<td class="bdrn"><input type="text" name="" id="" /></td>
											</tr>
											<tr>
												<th>기타</th>
												<td><input type="text" name="" id="" /></td>
												<td><input type="text" name="" id="" /></td>
												<td><input type="text" name="" id="" /></td>
												<td class="bdrn"><input type="text" name="" id="" /></td>
											</tr>
											<tr>
												<th>회수내용</th>
												<th>반품회수액</th>
												<td colspan="2"><input type="text" name="" id="" /></td>
												<th>금전회수액</th>
												<td class="bdrn"><input type="text" name="" id="" /></td>
											</tr>
											<tr>
												<th>보고내용<br />및<br />진행사항</th>
												<td colspan="5" class="ta bdrn">
													<textarea name="" id=""></textarea>
												</td>
											</tr>
										</tbody>
									</table>
								</div>						
							</td>
						</tr>
						<tr>
							<th class="append_tit">
								첨부파일
								<span class="ref">※ 첨부서류 :<br />&nbsp;&nbsp;&nbsp;&nbsp;거래처카드</span>
							</th>
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
				<div class="box_accident_report">
					<input type="text" name="" id="" class="ipt_year" />년 <input type="text" name="" id="" class="ipt_month" />월 <input type="text" name="" id="" class="ipt_day" />일
					<p>상기와 같이 보고합니다.</p>
				</div>
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