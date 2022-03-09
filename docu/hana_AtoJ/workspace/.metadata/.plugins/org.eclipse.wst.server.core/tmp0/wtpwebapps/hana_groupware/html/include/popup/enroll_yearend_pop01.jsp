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
			<h3>기본사항 <span>(종전 근무지)</span></h3>
		</div>
		<div class="inner">
			<div class="cont_box cont_table06">
				<div class="before_com">
					<button class="btn_row">+ 행추가</button>
				</div>
				<div class="wrap_tbl_yearend">
					<table class="tbl_yearend">
						<tr>
							<td class="box">
								<div class="device">
									<div class="pop_tit txtleft">
										<span class="tit">1</span>
									</div>
									<button class="btn_row">- 행삭제</button>
									<table class="tbl_yearend mgn">
										<colgroup>
											<col style="width:71px"/>
											<col style="">
											<col style="width:71px">
											<col style="">
											<col style="width:71px">
											<col style="">
											<col style="width:71px">
											<col style="">
										</colgroup>
										<tbody>
											<tr>
												<th>회사명</th>
												<td colspan="3"><input type="text" class="numright" /></td>
												<th>대표자명</th>
												<td><input type="text" class="numright w89" /></td>
												<th>사업자번호</th>
												<td><input type="text" class="numright w89" /></td>
											</tr>	
											<tr>
												<th>근무기간</th>
												<td colspan="3" class="date">
													<span class="date_box">
														<input type="text" class="serch_date">
														<button class="btn_date"><span class="blind">날짜선택</span></button>
													</span>
													부터
													<span class="date_box">
														<input type="text" class="serch_date">
														<button class="btn_date"><span class="blind">날짜선택</span></button>
													</span>
													까지
												</td>
												<th>감면기간</th>
												<td colspan="3" class="date">
													<span class="date_box">
														<input type="text" class="serch_date">
														<button class="btn_date"><span class="blind">날짜선택</span></button>
													</span>
													부터
													<span class="date_box">
														<input type="text" class="serch_date">
														<button class="btn_date"><span class="blind">날짜선택</span></button>
													</span>
													까지
												</td>
											</tr>
											<tr>
												<th>지급일자</th>
												<td class="date">
													<span class="date_box">
														<input type="text" class="serch_date">
														<button class="btn_date"><span class="blind">날짜선택</span></button>
													</span>
												</td>
												<th class="type01">급여총액</th>
												<td class="numright2"><input type="text" class="numright w89" /></td> <!-- 2015-02-25 수정 -->
												<th class="type01">상여총액</th>
												<td class="numright2"><input type="text" class="numright w89" /></td> <!-- 2015-02-25 수정 -->
												<th class="type01">인정상여</th>
												<td class="numright2"><input type="text" class="numright w89" /></td> <!-- 2015-02-25 수정 -->
											</tr>	
											<tr>
												<th>건강보험</th>
												<td class="numright2"><input type="text" class="numright w89" /></td> <!-- 2015-02-25 수정 -->
												<th>고용보험</th>
												<td class="numright2"><input type="text" class="numright w89" /></td> <!-- 2015-02-25 수정 -->
												<th>국민연금</th>
												<td class="numright2"><input type="text" class="numright w89" /></td> <!-- 2015-02-25 수정 -->
												<th>개인연금</th>
												<td class="numright2"><input type="text" class="numright w89" /></td> <!-- 2015-02-25 수정 -->
											</tr>
											<tr>
												<th>소득세</th>
												<td class="numright2"><input type="text" class="numright w89" /></td> <!-- 2015-02-25 수정 -->
												<th>지방소득세</th>
												<td class="numright2"><input type="text" class="numright w89" /></td> <!-- 2015-02-25 수정 -->
												<th>농특세</th>
												<td class="numright2"><input type="text" class="numright w89" /></td> <!-- 2015-02-25 수정 -->
												<td colspan="2">*소득세는 결정세액 입력하세요</td>
											</tr>
										</tbody>
									</table>
	
									<table class="tbl_yearend">
										<colgroup>
											<col style="width:123px"/>
											<col style="">
											<col style="width:123px">
											<col style="">
											<col style="width:123px">
											<col style="">
										</colgroup>
										<tbody>
											<tr>
												<th class="type01">근무처별소득명세합계</th>
												<td colspan="5"><input type="text" name="" id="" class="ipt_disable numright"/></td> <!-- 2015-02-25 수정 -->
											</tr>	
											<tr>
												<th class="type01">주식매수선택권 행사이익</th>
												<td class="numright2"><input type="text" class="numright w94" /></td> <!-- 2015-02-25 수정 -->
												<th class="type01">우리사주조합인출금</th>
												<td class="numright2"><input type="text" class="numright w94" /></td> <!-- 2015-02-25 수정 -->
												<th class="type01">임원퇴직소득금액한도초과액</th>
												<td class="numright2"><input type="text" class="numright w94" /></td> <!-- 2015-02-25 수정 -->
											</tr>
										</tbody>
									</table>
	
									<table class="tbl_yearend">
										<colgroup>
											<col style="width:91px"/>
											<col style="">
											<col style="">
											<col style="">
											<col style="">
											<col style="">
										</colgroup>
										<tbody>
											<tr>
												<th class="type02">비과세소득계</th>
												<td colspan="3"><input type="text" name="" id="" class="ipt_disable numright"/></td> <!-- 2015-02-25 수정 -->
												<th class="type03">감면소득계</th>
												<td><input type="text" name="" id="" class="ipt_disable numright"/></td> <!-- 2015-02-25 수정 -->
											</tr>	
											<tr>
												<th class="type02">국외근로비과세</th>
												<td class="numright2"><input type="text" class="numright" /></td> <!-- 2015-02-25 수정 -->
												<th class="type02">연장근무(야간수당) 비과세</th>
												<td class="numright2"><input type="text" class="numright" /></td> <!-- 2015-02-25 수정 -->
												<th class="type02">출산보육수당</th>
												<td class="numright2"><input type="text" class="numright" /></td> <!-- 2015-02-25 수정 -->
											</tr>
											<tr>
												<th class="type02">연구보조비</th>
												<td class="numright2"><input type="text" class="numright" /></td> <!-- 2015-02-25 수정 -->
												<th class="type02">학자금</th>
												<td class="numright2"><input type="text" class="numright" /></td> <!-- 2015-02-25 수정 -->
												<th class="type02">취재수당</th>
												<td class="numright2"><input type="text" class="numright" /></td> <!-- 2015-02-25 수정 -->
											</tr>
											<tr>
												<th class="type02">벽지수당</th>
												<td class="numright2"><input type="text" class="numright" /></td> <!-- 2015-02-25 수정 -->
												<th class="type02">천재지변 재해로 받는 수당</th>
												<td class="numright2"><input type="text" class="numright" /></td> <!-- 2015-02-25 수정 -->
												<th class="type02">주식매수선택권</th>
												<td class="numright2"><input type="text" class="numright" /></td> <!-- 2015-02-25 수정 -->
											</tr>
											<tr>
												<th class="type03">외국인기술자소득세면제</th>
												<td class="numright2"><input type="text" class="numright" /></td> <!-- 2015-02-25 수정 -->
												<th class="type02">우리사주조합인출금50%</th>
												<td class="numright2"><input type="text" class="numright" /></td> <!-- 2015-02-25 수정 -->
												<th class="type02">우리사주조합인출금75%</th>
												<td class="numright2"><input type="text" class="numright" /></td> <!-- 2015-02-25 수정 -->
											</tr>
											<tr>
												<th class="type02">경호수당, 승선수당</th>
												<td class="numright2"><input type="text" class="numright" /></td> <!-- 2015-02-25 수정 -->
												<th class="type03">중소기업청년취업소득세감면</th>
												<td class="numright2"><input type="text" class="numright" /></td> <!-- 2015-02-25 수정 -->
												<th class="type02">전공의수련보조수당</th>
												<td class="numright2"><input type="text" class="numright" /></td> <!-- 2015-02-25 수정 -->
											</tr>
											<tr>
												<th colspan="2" class="type03">해저광물자원개발을위한과세특례</th>
												<td class="numright2"><input type="text" class="numright" /></td> <!-- 2015-02-25 수정 -->
												<th colspan="2" class="type02">교육기본법제28조제1항에따라받는장학금</th>
												<td class="numright2"><input type="text" class="numright" /></td> <!-- 2015-02-25 수정 -->
											</tr>
											<tr>
												<th colspan="2" class="type02">외국정부또는국제기관에근무하는사람에대한비과세</th>
												<td class="numright2"><input type="text" class="numright" /></td> <!-- 2015-02-25 수정 -->
												<th colspan="2" class="type02">시립유치원수석교사의인건비유아교육법시행령</th>
												<td class="numright2"><input type="text" class="numright" /></td> <!-- 2015-02-25 수정 -->
											</tr>
											<tr>
												<th colspan="2" class="type02">보육교사인건비영유아보육법시행령</th>
												<td class="numright2"><input type="text" class="numright" /></td> <!-- 2015-02-25 수정 -->
												<th colspan="2" class="type03">조세조약상교직자조항의소득세감면</th>
												<td class="numright2"><input type="text" class="numright" /></td> <!-- 2015-02-25 수정 -->
											</tr>
											<tr>
												<th colspan="2" class="type02">정부공공기관중지방이전기관종사자이수수당</th>
												<td class="numright2"><input type="text" class="numright" /></td> <!-- 2015-02-25 수정 -->
												<th colspan="2" class="type02">법령조례에의한보수를받지않는의원수당</th>
												<td class="numright2"><input type="text" class="numright" /></td> <!-- 2015-02-25 수정 -->
											</tr>
											<tr>
												<th colspan="2" class="type02">작전임무수행을위해외국에주둔하는군인받는급여</th>
												<td class="numright2"><input type="text" class="numright" /></td> <!-- 2015-02-25 수정 -->
												<th colspan="2" class="type02"></th>
												<td></td>
											</tr>
										</tbody>
									</table>
									<p class="refer">
										중소기업청년취업소득세감면 금액이 있는 경우 감면 비율
										<input type="text" name="" id="" class="numright" /> % <!-- 2015-02-25 수정 -->
									</p>								
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