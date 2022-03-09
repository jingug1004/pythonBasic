<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
	<%@include file="../../include/head.jsp"%>
</head>
<body>
	<%@include file="../../include/header.jsp"%>
	<%@include file="../../include/gnb.jsp"%>
	
	<div class="wrap_con">
		<div class="content">
			<%@include file="../../include/location.jsp"%>
			<%@include file="../../include/lnb.jsp"%>
			
			<!-- ######## start ####### -->
			
<div class="cont float_left">
	<h2>원천징수영수증</h2>
	<div class="list_btn last_step">
		<div class="list_button tar">
			<button>계산시작</button>
			<button>검색</button>
		</div>
	</div>
	<div class="wrap_withholding_receipt">
		<ul class="tab">
			<!-- 활성화 탭 버튼 : li에 on 클래스 추가-->
			<li class="on" rel="tab1">PAGE 1</li>
			<li rel="tab2">PAGE 2</li>
			<li rel="tab3">PAGE 3</li>
		</ul>
		<div class="wrap_receipt_container">
			<div id="tab1" class="wrap_receipt_midbox">
				<div class="page_info">(1 쪽)</div>
				<div class="wrap_receipt">
					<div class="top">
						<div class="tit">
							<h3>[<span class="space"></span>]근로소득 원천징수영수증<br />[<span class="space"></span>]근로소득 <span>지급명세서</span></h3>
							<p>([<span>v</span>] 소득자 보관용&nbsp;&nbsp;[<span></span>] 발행자 보관용&nbsp;&nbsp;[<span></span>] 발행자 보고용)</p>
						</div>
						<div class="number">
							<strong>관리<br />번호</strong>
							<span></span>
						</div>
						<div class="tell">
							<table>
								<colgroup>
									<col style="width:80px"/>
									<col style="width:28px"/>
									<col style="width:91px"/>
									<col style=""/>
								</colgroup>
								<tbody>
									<tr>
										<th colspan="2">거주구분</th>
										<!-- 체크 표시된 영역 span에 on 또는 on2 클래스 추가-->
										<td colspan="2" class="bdrn"><span class="on">거주자1<em style="display:none"></em></span>/<span class="on2">비거주자2<em style="display:none"></em></span></td> <!-- 2015-02-26 수정 -->
									</tr>
									<tr>
										<th>거주지국</th>
										<td></td>
										<th>거주지국코드</th>
										<td class="bdrn"></td>
									</tr>
									<tr>
										<th colspan="2">내 · 외국인</th>
										<td colspan="2" class="bdrn"><span class="on">내국인1<em style="display:none"></em></span>, <span><em style="display:none"></em>외국인9</span></td>
									</tr>
									<tr>
										<th colspan="2">외국인단일세용적용</th>
										<td colspan="2" class="bdrn">여1 / 부2</td> <!-- 2015-02-25 수정 -->
									</tr>
									<tr>
										<th>국적</th>
										<td></td>
										<th>국적코드</th>
										<td class="bdrn"></td>
									</tr>
									<tr>
										<th colspan="2">세대주여부</th>
										<td colspan="2" class="bdrn">세대주1, 세대원2</td> <!-- 2015-02-25 수정 -->
									</tr>
									<tr>
										<th colspan="2">연말정산구분</th>
										<td colspan="2" class="bdrn">계속근로1, 중도퇴사2</td> <!-- 2015-02-25 수정 -->
									</tr>
								</tbody>
							</table>
						</div>
					</div>
					<div class="main">
						<table class="tbl_receipt">
							<!-- start 2015-02-25 수정 -->
							<colgroup>
								<col style="width:100px;" />
								<col style="width:130px;" />
								<col />
								<col style="width:130px;" />
								<col style="width:180px;" />
							</colgroup>
							<!-- end 2015-02-25 수정 -->
							<tbody>
								<tr>
									<th rowspan="3" class="role">징&nbsp;&nbsp;수<br />의무자</th>
									<th class="bdrn">(1) 법인명(상호)</th>
									<td><input type="text" name="" id="" value="하나제약주식회사" class="txtcnt" /></td>
									<th class="bdrn">(2) 대표자(성명)</th>
									<td class="bdrn"><input type="text" name="" id="" value="정재운" class="txtcnt" /></td>
								</tr>
								<tr>
									<th class="bdrn">(3) 사업자등록번호</th>
									<td><input type="text" name="" id="" value="124-51-87882" class="txtcnt" /></td>
									<th class="bdrn">(4) 주민등록번호</th>
									<td class="bdrn"><input type="text" name="" id="" value="181111-0002409" class="txtcnt" /></td>
								</tr>
								<tr>
									<th class="bdrn">(5) 소재지(주소)</th>
									<td colspan="3" class="bdrn"><input type="text" name="" id="" value="경기도 화성시 향남읍 상신 907-8"/></td>
								</tr>
								<tr>
									<th rowspan="2" class="role">소득자</th>
									<th class="bdrn">(6) 성명</th>
									<td><input type="text" name="" id="" value="박세호" class="txtcnt" /></td>
									<th class="bdrn">(7) 주민등록번호</th>
									<td class="bdrn"><input type="text" name="" id="" value="520721-1508429" class="txtcnt" /></td>
								</tr>
								<tr>
									<th class="bdrn">(8) 주소</th>
									<td colspan="3" class="bdrn"><input type="text" name="" id="" value="서울 서초구 잠원동-25-5 401호"/></td>
								</tr>
								<tr>
									<td colspan="5" class="inner">
										<div>
											<table class="tbl_receipt thd_pd"> <!-- 2015-02-25 수정 -->
												<!-- start 2015-02-25 수정 -->
												<colgroup>
													<col style="width:28px"/>
													<col style="width:208px"/>
													<col style="width:22px"/>
													<col style="width:120px"/>
													<col style="width:120px"/>
													<col style="width:120px"/>
													<col />
													<col style="width:90px"/>
												</colgroup>
												<!-- end 2015-02-25 수정 -->
												<tbody>
													<tr class="tit_row">
														<th rowspan="13"><span>Ⅰ<br />근<br />무<br />처<br />별<br />소<br />득<br />명<br />세</span></th>
														<th colspan="2" class="tac">구분</th>
														<th class="tac">주(현)</th>
														<th class="tac">종(전)</th>
														<th class="tac">종(전)</th>
														<th class="tac pdzero">(16)-1납세조항</th> <!-- 2015-02-25 수정 -->
														<th class="bdrn tac">합계</th>
													</tr>
													<tr>
														<th colspan="2">(9) 근무처별</th>
														<td><input type="text" name="" id="" class="ipt_disable"/></td>
														<td><input type="text" name="" id="" /></td>
														<td><input type="text" name="" id="" /></td>
														<td><input type="text" name="" id="" /></td>
														<td class="bdrn"><input type="text" name="" id="" /></td>
													</tr>
													<tr>
														<th colspan="2">(10) 사업자등록번호</th>
														<td><input type="text" name="" id="" class="ipt_disable"/></td>
														<td><input type="text" name="" id="" /></td>
														<td><input type="text" name="" id="" /></td>
														<td><input type="text" name="" id="" /></td>
														<td class="bdrn"><input type="text" name="" id="" /></td>
													</tr>
													<!-- start 2015-02-25 수정 -->
													<tr> 
														<th colspan="2">(11) 근무기간</th>
														<td class="pdzero">2018.01.01~2018.01.01</td>
														<td class="pdzero">2018.01.01~2018.01.01</td>
														<td class="pdzero">2018.01.01~2018.01.01</td>
														<td></td>
														<td class="bdrn"></td>
													</tr>
													<tr>
														<th colspan="2">(12) 감면기간</th>
														<td class="pdzero">2018.01.01~2018.01.01</td>
														<td class="pdzero">2018.01.01~2018.01.01</td>
														<td class="pdzero">2018.01.01~2018.01.01</td>
														<td></td>
														<td class="bdrn"></td>
													</tr>
													<!-- end 2015-02-25 수정 -->
													<tr>
														<th colspan="2">(13) 급여</th>
														<td><input type="text" name="" id="" value="88,525,680"/></td>
														<td><input type="text" name="" id="" /></td>
														<td><input type="text" name="" id="" /></td>
														<td><input type="text" name="" id="" /></td>
														<td class="bdrn"><input type="text" name="" id="" value="38,525,530"/></td>
													</tr>
													<tr>
														<th colspan="2">(14) 삽여</th>
														<td><input type="text" name="" id="" value="18,998,500"/></td>
														<td><input type="text" name="" id="" /></td>
														<td><input type="text" name="" id="" /></td>
														<td><input type="text" name="" id="" /></td>
														<td class="bdrn"><input type="text" name="" id="" value="13,995,500"/></td>
													</tr>
													<tr>
														<th colspan="2">(15) 인접삽여</th>
														<td><input type="text" name="" id="" /></td>
														<td><input type="text" name="" id="" /></td>
														<td><input type="text" name="" id="" /></td>
														<td><input type="text" name="" id="" /></td>
														<td class="bdrn"><input type="text" name="" id="" /></td>
													</tr>
													<tr>
														<th colspan="2">(15)-1 주식매수선택권 행사이익</th>
														<td><input type="text" name="" id="" /></td>
														<td><input type="text" name="" id="" /></td>
														<td><input type="text" name="" id="" /></td>
														<td><input type="text" name="" id="" /></td>
														<td class="bdrn"><input type="text" name="" id="" /></td>
													</tr>
													<tr>
														<th colspan="2">(15)-2 우리사주조합인출금</th>
														<td><input type="text" name="" id="" /></td>
														<td><input type="text" name="" id="" /></td>
														<td><input type="text" name="" id="" /></td>
														<td><input type="text" name="" id="" /></td>
														<td class="bdrn"><input type="text" name="" id="" /></td>
													</tr>
													<tr>
														<th colspan="2">(15)-3 임원퇴직소득금한도초과액</th>
														<td><input type="text" name="" id="" /></td>
														<td><input type="text" name="" id="" /></td>
														<td><input type="text" name="" id="" /></td>
														<td><input type="text" name="" id="" /></td>
														<td class="bdrn"><input type="text" name="" id="" /></td>
													</tr>
													<tr>
														<th colspan="2">(15)-4</th>
														<td><input type="text" name="" id="" value="52,522,030"/></td>
														<td><input type="text" name="" id="" /></td>
														<td><input type="text" name="" id="" /></td>
														<td><input type="text" name="" id="" /></td>
														<td class="bdrn"><input type="text" name="" id="" value="52,522,030"/></td>
													</tr>
													<tr>
														<th colspan="2">(16) 합계</th>
														<td><input type="text" name="" id="" /></td>
														<td><input type="text" name="" id="" /></td>
														<td><input type="text" name="" id="" /></td>
														<td><input type="text" name="" id="" /></td>
														<td class="bdrn"><input type="text" name="" id="" /></td>
													</tr>
													<tr class="tit_row">
														<th rowspan="30"><span>Ⅱ<br />비<br />과<br />세<br />및<br />감<br />면<br />소<br />득<br />명<br />세</span></th>
														<th class="tal">(18) 국외근로</th>
														<td><input type="text" name="" id="" /></td>
														<td><input type="text" name="" id="" /></td>
														<td><input type="text" name="" id="" /></td>
														<td><input type="text" name="" id="" /></td>
														<td><input type="text" name="" id="" /></td>
														<td class="bdrn"><input type="text" name="" id="" /></td>
													</tr>
													<tr>
														<th>(18)-1 야간근로수당</th>
														<td><input type="text" name="" id="" /></td>
														<td><input type="text" name="" id="" /></td>
														<td><input type="text" name="" id="" /></td>
														<td><input type="text" name="" id="" /></td>
														<td><input type="text" name="" id="" /></td>
														<td class="bdrn"><input type="text" name="" id="" /></td>
													</tr>
													<tr>
														<th>(18)-2 출산보육수당</th>
														<td><input type="text" name="" id="" /></td>
														<td><input type="text" name="" id="" /></td>
														<td><input type="text" name="" id="" /></td>
														<td><input type="text" name="" id="" /></td>
														<td><input type="text" name="" id="" /></td>
														<td class="bdrn"><input type="text" name="" id="" /></td>
													</tr>
													<tr>
														<th>(18)-3</th>
														<td><input type="text" name="" id="" /></td>
														<td><input type="text" name="" id="" /></td>
														<td><input type="text" name="" id="" /></td>
														<td><input type="text" name="" id="" /></td>
														<td><input type="text" name="" id="" /></td>
														<td class="bdrn"><input type="text" name="" id="" /></td>
													</tr>
													<tr>
														<th>(18)-4 연구보조비</th>
														<td><input type="text" name="" id="" /></td>
														<td><input type="text" name="" id="" /></td>
														<td><input type="text" name="" id="" /></td>
														<td><input type="text" name="" id="" /></td>
														<td><input type="text" name="" id="" /></td>
														<td class="bdrn"><input type="text" name="" id="" /></td>
													</tr>
													<tr>
														<th>(18)-5 비과세학자금</th>
														<td><input type="text" name="" id="" /></td>
														<td><input type="text" name="" id="" /></td>
														<td><input type="text" name="" id="" /></td>
														<td><input type="text" name="" id="" /></td>
														<td><input type="text" name="" id="" /></td>
														<td class="bdrn"><input type="text" name="" id="" /></td>
													</tr>
													<tr>
														<th>(18)-6 취재수당</th>
														<td><input type="text" name="" id="" /></td>
														<td><input type="text" name="" id="" /></td>
														<td><input type="text" name="" id="" /></td>
														<td><input type="text" name="" id="" /></td>
														<td><input type="text" name="" id="" /></td>
														<td class="bdrn"><input type="text" name="" id="" /></td>
													</tr>
													<tr>
														<th>(18)-7 벽지수당</th>
														<td><input type="text" name="" id="" /></td>
														<td><input type="text" name="" id="" /></td>
														<td><input type="text" name="" id="" /></td>
														<td><input type="text" name="" id="" /></td>
														<td><input type="text" name="" id="" /></td>
														<td class="bdrn"><input type="text" name="" id="" /></td>
													</tr>
													<tr>
														<th>(18)-8 천재지변등재해로받는급여</th>
														<td><input type="text" name="" id="" /></td>
														<td><input type="text" name="" id="" /></td>
														<td><input type="text" name="" id="" /></td>
														<td><input type="text" name="" id="" /></td>
														<td><input type="text" name="" id="" /></td>
														<td class="bdrn"><input type="text" name="" id="" /></td>
													</tr>
													<tr>
														<th>(18)-9 법령조례의한미보수위원수당</th>
														<td><input type="text" name="" id="" /></td>
														<td><input type="text" name="" id="" /></td>
														<td><input type="text" name="" id="" /></td>
														<td><input type="text" name="" id="" /></td>
														<td><input type="text" name="" id="" /></td>
														<td class="bdrn"><input type="text" name="" id="" /></td>
													</tr>
													<tr>
														<th>(18)-10 작전임무외국주둔군인급여</th>
														<td><input type="text" name="" id="" /></td>
														<td><input type="text" name="" id="" /></td>
														<td><input type="text" name="" id="" /></td>
														<td><input type="text" name="" id="" /></td>
														<td><input type="text" name="" id="" /></td>
														<td class="bdrn"><input type="text" name="" id="" /></td>
													</tr>
													<tr>
														<th>(18)-11 주식매수선택권</th>
														<td><input type="text" name="" id="" /></td>
														<td><input type="text" name="" id="" /></td>
														<td><input type="text" name="" id="" /></td>
														<td><input type="text" name="" id="" /></td>
														<td><input type="text" name="" id="" /></td>
														<td class="bdrn"><input type="text" name="" id="" /></td>
													</tr>
													<tr>
														<th>(18)-12 외국인기술자소득세면제</th>
														<td><input type="text" name="" id="" /></td>
														<td><input type="text" name="" id="" /></td>
														<td><input type="text" name="" id="" /></td>
														<td><input type="text" name="" id="" /></td>
														<td><input type="text" name="" id="" /></td>
														<td class="bdrn"><input type="text" name="" id="" /></td>
													</tr>
													<tr>
														<th>(18)-13</th>
														<td><input type="text" name="" id="" /></td>
														<td><input type="text" name="" id="" /></td>
														<td><input type="text" name="" id="" /></td>
														<td><input type="text" name="" id="" /></td>
														<td><input type="text" name="" id="" /></td>
														<td class="bdrn"><input type="text" name="" id="" /></td>
													</tr>
													<tr>
														<th>(18)-14 우리사주조합인출금50</th>
														<td><input type="text" name="" id="" /></td>
														<td><input type="text" name="" id="" /></td>
														<td><input type="text" name="" id="" /></td>
														<td><input type="text" name="" id="" /></td>
														<td><input type="text" name="" id="" /></td>
														<td class="bdrn"><input type="text" name="" id="" /></td>
													</tr>
													<tr>
														<th>(18)-15 우리사주조합인출금75</th>
														<td><input type="text" name="" id="" /></td>
														<td><input type="text" name="" id="" /></td>
														<td><input type="text" name="" id="" /></td>
														<td><input type="text" name="" id="" /></td>
														<td><input type="text" name="" id="" /></td>
														<td class="bdrn"><input type="text" name="" id="" /></td>
													</tr>
													<tr>
														<th>(18)-16</th>
														<td><input type="text" name="" id="" /></td>
														<td><input type="text" name="" id="" /></td>
														<td><input type="text" name="" id="" /></td>
														<td><input type="text" name="" id="" /></td>
														<td><input type="text" name="" id="" /></td>
														<td class="bdrn"><input type="text" name="" id="" /></td>
													</tr>
													<tr>
														<th>(18)-17 해저광물자원개발과세특례</th>
														<td><input type="text" name="" id="" /></td>
														<td><input type="text" name="" id="" /></td>
														<td><input type="text" name="" id="" /></td>
														<td><input type="text" name="" id="" /></td>
														<td><input type="text" name="" id="" /></td>
														<td class="bdrn"><input type="text" name="" id="" /></td>
													</tr>
													<tr>
														<th>(18)-18 경호수당/승선수당 등</th>
														<td><input type="text" name="" id="" /></td>
														<td><input type="text" name="" id="" /></td>
														<td><input type="text" name="" id="" /></td>
														<td><input type="text" name="" id="" /></td>
														<td><input type="text" name="" id="" /></td>
														<td class="bdrn"><input type="text" name="" id="" /></td>
													</tr>
													<tr>
														<th>(18)-19 외국정부국제기관근무자</th>
														<td><input type="text" name="" id="" /></td>
														<td><input type="text" name="" id="" /></td>
														<td><input type="text" name="" id="" /></td>
														<td><input type="text" name="" id="" /></td>
														<td><input type="text" name="" id="" /></td>
														<td class="bdrn"><input type="text" name="" id="" /></td>
													</tr>
													<tr>
														<th>(18)-20</th>
														<td><input type="text" name="" id="" /></td>
														<td><input type="text" name="" id="" /></td>
														<td><input type="text" name="" id="" /></td>
														<td><input type="text" name="" id="" /></td>
														<td><input type="text" name="" id="" /></td>
														<td class="bdrn"><input type="text" name="" id="" /></td>
													</tr>
													<tr>
														<th>(18)-21 교육기본법제28조1항장학금</th>
														<td><input type="text" name="" id="" /></td>
														<td><input type="text" name="" id="" /></td>
														<td><input type="text" name="" id="" /></td>
														<td><input type="text" name="" id="" /></td>
														<td><input type="text" name="" id="" /></td>
														<td class="bdrn"><input type="text" name="" id="" /></td>
													</tr>
													<tr>
														<th>(18)-22 보육교사인건비</th>
														<td><input type="text" name="" id="" /></td>
														<td><input type="text" name="" id="" /></td>
														<td><input type="text" name="" id="" /></td>
														<td><input type="text" name="" id="" /></td>
														<td><input type="text" name="" id="" /></td>
														<td class="bdrn"><input type="text" name="" id="" /></td>
													</tr>
													<tr>
														<th>(18)-23 사립유치원수석교사의인건비</th>
														<td><input type="text" name="" id="" /></td>
														<td><input type="text" name="" id="" /></td>
														<td><input type="text" name="" id="" /></td>
														<td><input type="text" name="" id="" /></td>
														<td><input type="text" name="" id="" /></td>
														<td class="bdrn"><input type="text" name="" id="" /></td>
													</tr>
													<tr>
														<th>(18)-24 중소기업청년취업소득세감면</th>
														<td><input type="text" name="" id="" /></td>
														<td><input type="text" name="" id="" /></td>
														<td><input type="text" name="" id="" /></td>
														<td><input type="text" name="" id="" /></td>
														<td><input type="text" name="" id="" /></td>
														<td class="bdrn"><input type="text" name="" id="" /></td>
													</tr>
													<tr>
														<th>(18)-25 조세조약상교직자조합의소득세감면</th>
														<td><input type="text" name="" id="" /></td>
														<td><input type="text" name="" id="" /></td>
														<td><input type="text" name="" id="" /></td>
														<td><input type="text" name="" id="" /></td>
														<td><input type="text" name="" id="" /></td>
														<td class="bdrn"><input type="text" name="" id="" /></td>
													</tr>
													<tr>
														<th>(18)-26 정부지방이전기관중사자이주수당</th>
														<td><input type="text" name="" id="" /></td>
														<td><input type="text" name="" id="" /></td>
														<td><input type="text" name="" id="" /></td>
														<td><input type="text" name="" id="" /></td>
														<td><input type="text" name="" id="" /></td>
														<td class="bdrn"><input type="text" name="" id="" /></td>
													</tr>
													<tr>
														<th>(19) 수련보조수당</th>
														<td><input type="text" name="" id="" /></td>
														<td><input type="text" name="" id="" /></td>
														<td><input type="text" name="" id="" /></td>
														<td><input type="text" name="" id="" /></td>
														<td><input type="text" name="" id="" /></td>
														<td class="bdrn"><input type="text" name="" id="" /></td>
													</tr>
													<tr>
														<th>(20) 비과세소득계</th>
														<td><input type="text" name="" id="" /></td>
														<td><input type="text" name="" id="" /></td>
														<td><input type="text" name="" id="" /></td>
														<td><input type="text" name="" id="" /></td>
														<td><input type="text" name="" id="" /></td>
														<td class="bdrn"><input type="text" name="" id="" /></td>
													</tr>
													<tr>
														<th>(20)-1 감면소득계</th>
														<td><input type="text" name="" id="" /></td>
														<td><input type="text" name="" id="" /></td>
														<td><input type="text" name="" id="" /></td>
														<td><input type="text" name="" id="" /></td>
														<td><input type="text" name="" id="" /></td>
														<td class="bdrn"><input type="text" name="" id="" /></td>
													</tr>
													<tr>
														<td colspan="8" class="inner">
															<table class="tbl_receipt">
																<colgroup>
																	<col style="width:28px"/>
																	<col style="width:80px;" /> <!-- 2015-02-25 수정 -->
																	<col style="width:112px;" /> <!-- 2015-02-25 수정 -->
																	<col style="width:96px;" /> <!-- 2015-02-25 수정 -->
																	<col /> <!-- 2015-02-25 수정 -->
																	<col style="width:101px"/>
																	<col style="width:101px"/>
																	<col style="width:101px"/>
																</colgroup>
																<tbody>
																	<tr class="tit_row">
																		<th rowspan="8"><span>Ⅲ<br />세<br />액<br />명<br />세</span></th>
																		<th colspan="4" class="txtcnt">구 &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;분</th> <!-- 2015-02-25 수정 -->
																		<th class="txtcnt">(80) 소득세</th> <!-- 2015-02-25 수정 -->
																		<th class="txtcnt">(81) 지방소득세</th> <!-- 2015-02-25 수정 -->
																		<th class="bdrn txtcnt">(82) 농어촌특별세</th> <!-- 2015-02-25 수정 -->
																	</tr>
																	<tr>
																		<th colspan="4">(74) 결정세액</th>
																		<td><input type="text" name="" id="" value="50"/></td>
																		<td><input type="text" name="" id="" /></td>
																		<td class="bdrn"><input type="text" name="" id="" /></td>
																	</tr>
																	<tr>
																		<th rowspan="4" class="txtcnt">기납부<br>세 &nbsp; 액</th> <!-- 2015-02-25 수정 -->
																		<th rowspan="3">(75) 종(전)근무지<br />(결정세액란의<br />세액 기재)</th>
																		<th rowspan="3">사업자등록번호</th>
																		<td></td>
																		<td><input type="text" name="" id="" value="0" /></td>
																		<td><input type="text" name="" id="" value="0" /></td>
																		<td class="bdrn"><input type="text" name="" id="" value="0" /></td>
																	</tr>
																	<tr>
																		<td></td>
																		<td><input type="text" name="" id="" value="0" /></td>
																		<td><input type="text" name="" id="" value="0" /></td>
																		<td class="bdrn"><input type="text" name="" id="" value="0" /></td>
																	</tr>
																	<tr>
																		<td></td>
																		<td><input type="text" name="" id="" value="0" /></td>
																		<td><input type="text" name="" id="" value="0" /></td>
																		<td class="bdrn"><input type="text" name="" id="" value="0" /></td>
																	</tr>
																	<tr>
																		<th colspan="3">(76) 주(현)근무지</th>
																		<td><input type="text" name="" id="" value="27,650"/></td>
																		<td><input type="text" name="" id="" value="2,700"/></td>
																		<td class="bdrn"><input type="text" name="" id="" /></td>
																	</tr>
																	<tr>
																		<th colspan="4">(77) 납부특례세액</th>
																		<td><input type="text" name="" id="" /></td>
																		<td><input type="text" name="" id="" /></td>
																		<td class="bdrn"><input type="text" name="" id="" /></td>
																	</tr>
																	<tr>
																		<th colspan="4">(78) 차감징수세액 (74) - (75) - (76) - (77)</th>
																		<td><input type="text" name="" id="" value="-27,650"/></td>
																		<td><input type="text" name="" id="" value="-2,700"/></td>
																		<td class="bdrn"><input type="text" name="" id="" value="0" /></td>
																	</tr>
																</tbody>
															</table>
														</td>
													</tr>
												</tbody>
											</table>
										</div>
									</td>
								</tr>
							</tbody>
						</table>
					</div>	
					<div class="summary">
						<ul>
							<li>국민연금 : <span>375,300</span></li>
							<li>의료보험 : <span>221,750</span></li>
							<li>고용보험 : <span>46,310</span></li>
						</ul>
						<div>
							<p>위의 원천징수액(근로소득)을 영수(지급)합니다. <span><em>2014</em>년 <em>12</em>월 <em>31</em>일</span></p>
							<div>
								<span class="sir">세 무 서 장 <em></em>귀하</span>
								<span class="obligor ">징수(보고)의무자 <em></em></span>
								<span class="sign">하나제약주식회사 <em></em>(서명 또는 인)</span>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div id="tab2" class="wrap_receipt_midbox">
				<div class="page_info">(2 쪽)</div>			
				<div class="wrap_receipt">
					<div class="main page2">
						<!-- start 2015-02-25 수정 -->
						<table class="tbl_receipt lheight"> 
							<colgroup>
								<col style="width:28px"/>
								<col style="width:28px"/>
								<col style="width:28px"/>
								<col style="width:58px"/>
								<col style="width:58px"/> 
								<col style="width:58px"/> 
								<col style="width:78px"/> 
								<col style=""/>
								<col style="width:28px"/>
								<col style="width:28px"/>
								<col style="width:28px"/>
								<col style="width:58px"/>
								<col style="width:58px"/>
								<col style="width:78px"/>
								<col style=""/>
						<!-- end 2015-02-25 수정 -->
							</colgroup>
							<tbody>
								<tr class="tit_row bdtn">
									<th rowspan="41" class="bdb2"><span>Ⅳ<br />정<br />산<br />명<br />세</span></th>
									<th colspan="6">(21) 총급여((16), 다만 외국인단일세율 적용시에는 연간근로소득)</th>
									<td class="bdr2"><input type="text" name="" id="" value="7,129,350"/></td>
									<th colspan="6">(49) 특별공제 종합한도 초과액</th>
									<td class="bdrn"><input type="text" name="" id="" /></td>
								</tr>
								<tr>
									<th colspan="6">(22) 근로소득공제</th>
									<td class="bdr2"><input type="text" name="" id="" value="4,351,740"/></td>
									<th colspan="6">(50) 종합소득 과세표준</th>
									<td class="bdrn"><input type="text" name="" id="" value="634,250"/></td>
								</tr>
								<tr>
									<th colspan="6">(23) 근로소득금액</th>
									<td class="bdr2"><input type="text" name="" id="" value="2,777,610"/></td>
									<th colspan="6">(51) 산출세액</th>
									<td class="bdrn"><input type="text" name="" id="" value="38,050"/></td>
								</tr>
								<tr>
									<th rowspan="24" class="tac"><span class="tit_row_s">종<br />합<br />소<br />득<br />공<br />제</span></th>
									<th rowspan="3" class="tac">기본공제</th>
									<th colspan="4">(24) 본인</th> <!-- 2015-02-25 수정 -->
									<td class="bdr2"><input type="text" name="" id="" /></td>
									<th rowspan="5" class="tac"><span class="tit_row_s">세<br />액<br />감<br />면</span></th>
									<th colspan="5">(52) 「소득세법」</th>
									<td class="bdrn"><input type="text" name="" id="" /></td>
								</tr>
								<tr>
									<th colspan="4">(25) 배우자</th>
									<td class="bdr2"><input type="text" name="" id="" /></td>
									<th colspan="5">(53) 「조세특례제한법」 (53)-1 제외</th>
									<td class="bdrn"><input type="text" name="" id="" /></td>
								</tr>
								<tr>
									<th colspan="4" class="person">(26) 부양가족<span>(<em>0</em>명)</span></th>
									<td class="bdr2"><input type="text" name="" id="" /></td>
									<th colspan="5">(54) 「조세특례제한법」 제30조</th>
									<td class="bdrn"><input type="text" name="" id="" value="19,030"/></td>
								</tr>		
								<tr>
									<th rowspan="4" class="tac">추가공제</th>
									<th colspan="4" class="person">(27) 경로우대<span>(<em>0</em>명)</span></th>
									<td class="bdr2"><input type="text" name="" id="" /></td>
									<th colspan="5">(55) 조세조약</th>
									<td class="bdrn"><input type="text" name="" id="" value="19,030"/></td>
								</tr>								
								<tr>
									<th colspan="4" class="person">(28) 장애인<span>(<em>0</em>명)</span></th>
									<td class="bdr2"><input type="text" name="" id="" /></td>
									<th colspan="5">(56) 세액감면계</th>
									<td class="bdrn"><input type="text" name="" id="" value="19,030"/></td>
								</tr>	
								<tr>
									<th colspan="4">(29) 부녀자</th>
									<td class="bdr2"><input type="text" name="" id="" /></td>
									<th rowspan="27" class="tac"><span class="tit_row_s">세<br />액<br />공<br />제</span></th>
									<th colspan="5">(57) 근로소득</th>
									<td class="bdrn"><input type="text" name="" id="" value="18,980"/></td>
								</tr>	
								<tr>
									<th colspan="4">(30) 한부모가족</th>
									<td class="bdr2"><input type="text" name="" id="" /></td>
									<th colspan="5">(58) 자녀</th>
									<td class="bdrn"><input type="text" name="" id=""/></td>
								</tr>	
								<tr>
									<th rowspan="5" class="tac">연금보험료공제</th>
									<th colspan="4">(31) 국민연금보험료</th>
									<td class="bdr2"><input type="text" name="" id="" value="375,300"/></td>
									<th rowspan="6" class="tac">연금계좌</th>
									<th rowspan="2" colspan="3">(59) 과학기술인공제</th>
									<th>공제대상금액</th>
									<td class="bdrn"><input type="text" name="" id=""/></td>
								</tr>	
								<tr>
									<th rowspan="4">(32) 공적연금보험료공제</th>
									<th colspan="3">(가) 공무원연금</th>
									<td class="bdr2"><input type="text" name="" id="" /></td>
									<th>세액공제액</th>
									<td class="bdrn"></td> <!-- 2015-02-25 수정 -->
								</tr>	
								<tr>
									<th colspan="3">(나) 군인연금</th>
									<td class="bdr2"><input type="text" name="" id="" /></td>
									<th rowspan="2" colspan="3">(60) 「근로자퇴직급여보장법」에 따른 퇴직연금</th>
									<th>공제대상금액</th>
									<td class="bdrn"><input type="text" name="" id=""/></td>
								</tr>	
								<tr>
									<th colspan="3">(다) 사립학교교직원연금</th>
									<td class="bdr2"><input type="text" name="" id="" /></td>
									<th>세액공제액</th>
									<td class="bdrn"><input type="text" name="" id=""/></td>
								</tr>	
								<tr>
									<th colspan="3">(라) 별정우체국연금</th>
									<td class="bdr2"><input type="text" name="" id="" /></td>
									<th rowspan="2" colspan="3">(61) 연금저축</th>
									<th>공제대상금액</th>
									<td class="bdrn"><input type="text" name="" id="" value="0" /></td>
								</tr>	
								<tr>
									<th rowspan="12" class="tac">특별공제</th>
									<th rowspan="2" class="pdzero">(33) 보험료</th> <!-- 2015-02-25 수정 -->
									<th colspan="3" class="pdzero">(가) 건강보험료(노인장기요양보험료포함)</th> <!-- 2015-02-25 수정 -->
									<td class="bdr2"><input type="text" name="" id="" value="221,750"/></td>
									<th>세액공제액</th>
									<td class="bdrn"><input type="text" name="" id="" value="0" /></td> <!-- 2015-02-25 수정 -->
								</tr>	
								<tr>
									<th colspan="3">(나) 고용보험료</th>
									<td class="bdr2"><input type="text" name="" id="" value="46,310"/></td>
									<th rowspan="14" class="tac">특별세액공제</th>
									<th rowspan="2" colspan="3">(62) 보장성보험</th>
									<th>공제대상금액</th>
									<td class="bdrn"><input type="text" name="" id=""/></td>
								</tr>	
								<tr>
									<th rowspan="8">(34) 주택자금</th>
									<th rowspan="2" colspan="2">(가) 주택임차차입금원리금상환액</th>
									<th>대출기관</th>
									<td class="bdr2"><input type="text" name="" id="" /></td>
									<th>세액공제액</th>
									<td class="bdrn"><input type="text" name="" id=""/></td>
								</tr>	
								<tr>
									<th>거주자</th>
									<td class="bdr2"><input type="text" name="" id="" /></td>
									<th rowspan="2" colspan="3">(63) 의료비</th>
									<th>공제대상금액</th>
									<td class="bdrn"><input type="text" name="" id=""/></td>
								</tr>	
								<tr>
									<th colspan="3">(나)</th>
									<td class="bdr2"><input type="text" name="" id="" /></td>
									<th>세액공제액</th>
									<td class="bdrn"><input type="text" name="" id=""/></td>
								</tr>	
								<tr>
									<th rowspan="5">(다) 장기주택저당차입금이자상환액</th>
									<th rowspan="3">2011 이전 차입분</th>
									<th>15년 미만</th>
									<td class="bdr2"><input type="text" name="" id="" /></td>
									<th rowspan="2" colspan="3">(64) 교육비</th>
									<th>공제대상금액</th>
									<td class="bdrn"><input type="text" name="" id=""/></td>
								</tr>	
								<tr>
									<th>15년~29년</th>
									<td class="bdr2"><input type="text" name="" id="" /></td>
									<th>세액공제액</th>
									<td class="bdrn"><input type="text" name="" id=""/></td>
								</tr>	
								<tr>
									<th>30년 이상</th>
									<td class="bdr2"><input type="text" name="" id="" /></td>
									<th rowspan="6">(65) 기부금</th>
									<th rowspan="4">(가) 정치자금기부금</th>
									<th rowspan="2">10만원이하</th>
									<th>공제대상금액</th>
									<td class="bdrn"><input type="text" name="" id=""/></td>
								</tr>	
								<tr>
									<th rowspan="2">2012 이후 차입분(15년이상)</th>
									<th>고정금리 · 비거치 상환대출</th>
									<td class="bdr2"><input type="text" name="" id="" /></td>
									<th>세액공제액</th>
									<td class="bdrn"><input type="text" name="" id="" value="0" /></td> <!-- 2015-02-25 수정 -->
								</tr>	
								<tr>
									<th>그 밖의 대출</th>
									<td class="bdr2"><input type="text" name="" id="" /></td>
									<th rowspan="2">10만원초과</th>
									<th>공제대상금액</th>
									<td class="bdrn"><input type="text" name="" id=""/></td>
								</tr>	
								<tr>
									<th colspan="4">(35) 기부금(이월분)</th>
									<td class="bdr2"><input type="text" name="" id="" /></td>
									<th>세액공제액</th>
									<td class="bdrn"><input type="text" name="" id=""/></td>
								</tr>	
								<tr>
									<th colspan="4">(36) 계</th>
									<td class="bdr2"><input type="text" name="" id="" value="268,060"/></td>
									<th colspan="3">(나) 법정기부금</th>
									<td class="bdrn"><input type="text" name="" id=""/></td>
								</tr>	
								<tr>
									<th colspan="6">(37) 차감소득금액</th>
									<td class="bdr2"><input type="text" name="" id="" value="634,250"/></td>
									<th colspan="3">(다) 지정기부금</th>
									<td class="bdrn"><input type="text" name="" id=""/></td>
								</tr>	
								<tr>
									<th rowspan="13" class="tac bdb2"><span class="tit_row_s">그<br />밖<br />의<br />소<br />득<br />공<br />제</span></th>
									<th colspan="5">(38) 개인연금저축</th>
									<td class="bdr2"><input type="text" name="" id="" /></td>
									<th colspan="4">(66) 계</th>
									<td class="bdrn"><input type="text" name="" id=""/></td>
								</tr>	
								<tr>
									<th colspan="5">(39) 소기업 · 소상공인 공제부금</th>
									<td class="bdr2"><input type="text" name="" id="" /></td>
									<th colspan="4">(67) 표준공제</th>
									<td class="bdrn"><input type="text" name="" id=""/></td>
								</tr>	
								<tr>
									<th rowspan="3" colspan="2">(40) 주택마련저축소득공제</th>
									<th colspan="3">(가) 청약저축</th>
									<td class="bdr2"><input type="text" name="" id="" /></td>	
									<th colspan="5">(68) 납세조합공제</th>
									<td class="bdrn"><input type="text" name="" id=""/></td>
								</tr>	
								<tr>
									<th colspan="3">(나) 주택청약종합저축</th>
									<td class="bdr2"><input type="text" name="" id="" /></td>	
									<th colspan="5">(69) 주택차입금</th>
									<td class="bdrn"><input type="text" name="" id=""/></td>
								</tr>	
								<tr>
									<th colspan="3">(다) 근로자주택마련저축</th>
									<td class="bdr2"><input type="text" name="" id="" /></td>	
									<th colspan="5">(70) 외국납부</th>
									<td class="bdrn"><input type="text" name="" id=""/></td>
								</tr>	
								<tr>
									<th colspan="5">(41) 투자조합출자등 소득공제</th>
									<td class="bdr2"><input type="text" name="" id="" /></td>	
									<th colspan="5">(71) 월세액</th>
									<td class="bdrn"><input type="text" name="" id=""/></td>
								</tr>	
								<tr>
									<th colspan="5">(42) 신용카드 등 사용액</th>
									<td class="bdr2"><input type="text" name="" id="" /></td>	
									<th colspan="5">(72) 세액공제계</th>
									<td class="bdrn"><input type="text" name="" id="" value="38,010"/></td>
								</tr>	
								<tr>
									<th colspan="5">(43) 우리사주조합 출연금</th>
									<td class="bdr2"><input type="text" name="" id="" /></td>	
									<th colspan="6"><span class="tdbold">결정세액 (51)-(56)-(72)</span></th> <!-- 2015-02-25 수정 -->
									<td class="bdrn"><input type="text" name="" id="" value="245" /></td> <!-- 2015-02-25 수정 -->
								</tr>	
								<tr>
									<th colspan="5">(44) 우리사주조합 기부금</th>
									<td class="bdr2"><input type="text" name="" id="" /></td>
									<td colspan="7" class="bdrn"></td>
								</tr>	
								<tr>
									<th colspan="5">(45) 고용유지 중소기업 근로자</th>
									<td class="bdr2"><input type="text" name="" id="" /></td>
									<td colspan="7" class="bdrn"></td>
								</tr>	
								<tr>
									<th colspan="5">(46) 묵돈 만드는 전세 이자상환액</th>
									<td class="bdr2"><input type="text" name="" id="" /></td>
									<td colspan="7" class="bdrn"></td>
								</tr>	
								<tr>
									<th colspan="5">(47) 장기집합투자증권저축</th>
									<td class="bdr2"><input type="text" name="" id="" /></td>
									<td colspan="7" class="bdrn"></td>
								</tr>	
								<tr class="bdb2">
									<th colspan="5">(48) 그 밖의 소득공제 계</th>
									<td class="bdr2"><input type="text" name="" id="" /></td>
									<td colspan="7" class="bdrn"></td>
								</tr>	
							</tbody>
						</table>
					</div>	
				</div>
			</div>
			<div id="tab3" class="wrap_receipt_midbox">
				<div class="page_info">(3 쪽)</div>			
				<div class="wrap_receipt">
					<div class="main page3">
						<table class="tbl_receipt">
							<colgroup>
								<col style="width:32px"/>
								<col style="width:95px"/>
								<col style="width:22px"/>
								<col style="width:22px"/>
								<col style="width:40px"/>
								<col style="width:44px"/>
								<col style=""/>
								<col style=""/>
								<col style=""/>
								<col style=""/>
								<col style=""/>
								<col style=""/>
								<col style=""/>
								<col style=""/>
								<col style=""/>
								<col style=""/>
							</colgroup>
							<tbody>
								<tr>
									<th colspan="16" class="bdrn">(79) 소득공제 명세(인적공제항목은 해당란에 "O"표시 (장애인 해당시 해당코드를 기재)를 하며, 각종 소득공제에 항목은 공제를 위하여 실제 지출한 금액을 기입합니다)</th>
								</tr>
								<tr>
									<th colspan="5">인적공제 항목</th>
									<th colspan="11" class="bdrn">각종 소득공제 항목</th>
								</tr>
								<tr>
									<th>관계<br />코드</th>
									<th>성명</th>
									<th colspan="2">기본<br />공제</th>
									<th>경로<br />우대</th>
									<th rowspan="2">자료<br />구분</th>
									<th colspan="2">보험료</th>
									<th rowspan="2">의료비</th>
									<th rowspan="2">교육비</th>
									<th colspan="5">신용카드 등 사용액공제</th>
									<th rowspan="2" class="bdrn">기부금</th>
								</tr>
								<tr>
									<th>내 · 외국인</th>
									<th>주민등록번호</th>
									<th>부녀자</th>
									<th>학부모</th>
									<th>장애인</th>
									<th>건강 · 고용 등</th>
									<th>보장성</th>
									<th>신용카드 (전통시장,대중교통비제외)</th>
									<th>직불카드 (전통시장,대중교통비제외)</th>
									<th>현금영수증 (전통시장,대중교통비제외)</th>
									<th>전통시장 사용액</th>
									<th>대중교통 이용액</th>
								</tr>
								<tr>
									<th rowspan="2" colspan="2">인적공제항목에 해당하는 인원수를 기재<br />(자녀: <span>0</span>명)</th>
									<td><input type="text" name="" id="" value="O" /></td>
									<td><input type="text" name="" id="" value="O" /></td>
									<td><input type="text" name="" id="" value="O" class="tac" /></td> <!-- 2015-02-17 수정 -->
									<th>국세청</th>
									<td><input type="text" name="" id="" /></td>
									<td><input type="text" name="" id="" value="288,080"/></td>
									<td><input type="text" name="" id="" /></td>
									<td><input type="text" name="" id="" /></td>
									<td><input type="text" name="" id="" /></td>
									<td><input type="text" name="" id="" /></td>
									<td><input type="text" name="" id="" /></td>
									<td><input type="text" name="" id="" /></td>
									<td><input type="text" name="" id="" /></td>
									<td class="bdrn txtright"><input type="text" name="" id="" value="0" /></td>
								</tr>
								<tr>
									<td><input type="text" name="" id="" value="O" /></td>
									<td><input type="text" name="" id="" value="O" /></td>
									<td><input type="text" name="" id="" value="O" class="tac" /></td> <!-- 2015-02-17 수정 -->
									<th>기타</th>
									<td><input type="text" name="" id="" /></td>
									<td><input type="text" name="" id="" /></td>
									<td><input type="text" name="" id="" /></td>
									<td><input type="text" name="" id="" /></td>
									<td><input type="text" name="" id="" /></td>
									<td><input type="text" name="" id="" /></td>
									<td><input type="text" name="" id="" /></td>
									<td><input type="text" name="" id="" /></td>
									<td><input type="text" name="" id="" /></td>
									<td class="bdrn"><input type="text" name="" id="" /></td>
								</tr>
								<tr>
									<th>0</th>
									<td><input type="text" name="" id="" class="tac" value="장수민"/></td>
									<td colspan="2"><input type="text" name="" id="" class="tac" value="O" /></td>
									<td><input type="text" name="" id="" value="O" class="tac" /></td> <!-- 2015-02-17 수정 -->
									<th>국세청</th>
									<td><input type="text" name="" id="" /></td>
									<td><input type="text" name="" id="" value="268,060"/></td>
									<td><input type="text" name="" id="" /></td>
									<td><input type="text" name="" id="" /></td>
									<td><input type="text" name="" id="" /></td>
									<td><input type="text" name="" id="" /></td>
									<td><input type="text" name="" id="" /></td>
									<td><input type="text" name="" id="" /></td>
									<td><input type="text" name="" id="" /></td>
									<td class="bdrn"><input type="text" name="" id="" /></td>
								</tr>
								<tr>
									<th>1</th>
									<td><input type="text" name="" id="" class="tac" value="970204-2163719"/></td>
									<td colspan="2"><input type="text" name="" id="" value="O" class="tac" /></td> <!-- 2015-02-17 수정 -->
									<td><input type="text" name="" id="" value="O" class="tac" /></td> <!-- 2015-02-17 수정 -->
									<th>기타</th>
									<td><input type="text" name="" id="" /></td>
									<td><input type="text" name="" id="" /></td>
									<td><input type="text" name="" id="" /></td>
									<td><input type="text" name="" id="" /></td>
									<td><input type="text" name="" id="" /></td>
									<td><input type="text" name="" id="" /></td>
									<td><input type="text" name="" id="" /></td>
									<td><input type="text" name="" id="" /></td>
									<td><input type="text" name="" id="" /></td>
									<td class="bdrn"><input type="text" name="" id="" /></td>
								</tr>
							</tbody>
						</table>
					</div>	
				</div>
			</div>
		</div>
	</div>
</div>

<!-- ######## end ######### -->
			
		</div>
	</div>
	
	<%@include file="../../include/footer.jsp"%>
</body>
</html>