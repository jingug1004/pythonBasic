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
			<li><a href="#">PAGE 1</a></li>
			<li class="on"><a href="#">PAGE 2</a></li>
			<li><a href="#">PAGE 3</a></li>
		</ul>
		<div class="wrap_receipt">
			<div class="main page2">
				<table class="tbl_receipt">
					<colgroup>
						<col style="width:28px"/>
						<col style="width:28px"/>
						<col style="width:28px"/>
						<col style="width:50px"/>
						<col style="width:42px"/>
						<col style="width:52px"/>
						<col style="width:74px"/>
						<col style=""/>
						<col style="width:28px"/>
						<col style="width:28px"/>
						<col style="width:28px"/>
						<col style="width:50px"/>
						<col style="width:48px"/>
						<col style="width:80px"/>
						<col style=""/>
					</colgroup>
					<tbody>
						<tr class="tit_row">
							<th rowspan="41"><span>Ⅳ<br />정<br />산<br />명<br />세</span></th>
							<th colspan="6">(21) 총급여((16), 다만 외국인단일세율 적용시에는 연간근로소득)</th>
							<td><input type="text" name="" id="" value="7,129,350"/></td>
							<th colspan="6">(49) 특별공제 종합한도 초과액</th>
							<td class="bdrn"><input type="text" name="" id="" /></td>
						</tr>
						<tr>
							<th colspan="6">(22) 근로소득공제</th>
							<td><input type="text" name="" id="" value="4,351,740"/></td>
							<th colspan="6">(50) 종합소득 과세표준</th>
							<td class="bdrn"><input type="text" name="" id="" value="634,250"/></td>
						</tr>
						<tr>
							<th colspan="6">(23) 근로소득금액</th>
							<td><input type="text" name="" id="" value="2,777,610"/></td>
							<th colspan="6">(51) 산출세액</th>
							<td class="bdrn"><input type="text" name="" id="" value="38,050"/></td>
						</tr>
						<tr>
							<th rowspan="24" class="tac"><span class="tit_row_s">종<br />합<br />소<br />득<br />공<br />제</span></th>
							<th rowspan="3" class="tac">기본공제</th>
							<th colspan="4" class="tac">(24) 본인</th>
							<td><input type="text" name="" id="" /></td>
							<th rowspan="5" class="tac"><span class="tit_row_s">세<br />액<br />감<br />면</span></th>
							<th colspan="5">(52) 「소득세법」</th>
							<td class="bdrn"><input type="text" name="" id="" /></td>
						</tr>
						<tr>
							<th colspan="4">(25) 배우자</th>
							<td><input type="text" name="" id="" /></td>
							<th colspan="5">(53) 「조세특례제한법」 (53)-1 제외</th>
							<td class="bdrn"><input type="text" name="" id="" /></td>
						</tr>
						<tr>
							<th colspan="4" class="person">(26) 부양가족<span>(<em>0</em>명)</span></th>
							<td><input type="text" name="" id="" /></td>
							<th colspan="5">(54) 「조세특례제한법」 제30조</th>
							<td class="bdrn"><input type="text" name="" id="" value="19,030"/></td>
						</tr>		
						<tr>
							<th rowspan="4" class="tac">추가공제</th>
							<th colspan="4" class="person">(27) 경로우대<span>(<em>0</em>명)</span></th>
							<td><input type="text" name="" id="" /></td>
							<th colspan="5">(55) 조세조약</th>
							<td class="bdrn"><input type="text" name="" id="" value="19,030"/></td>
						</tr>								
						<tr>
							<th colspan="4" class="person">(28) 장애인<span>(<em>0</em>명)</span></th>
							<td><input type="text" name="" id="" /></td>
							<th colspan="5">(56) 세액감면계</th>
							<td class="bdrn"><input type="text" name="" id="" value="19,030"/></td>
						</tr>	
						<tr>
							<th colspan="4">(29) 부녀자</th>
							<td><input type="text" name="" id="" /></td>
							<th rowspan="27" class="tac"><span class="tit_row_s">세<br />액<br />공<br />제</span></th>
							<th colspan="5">(57) 근로소득</th>
							<td class="bdrn"><input type="text" name="" id="" value="18,980"/></td>
						</tr>	
						<tr>
							<th colspan="4">(30) 한부모가족</th>
							<td><input type="text" name="" id="" /></td>
							<th colspan="5">(58) 자녀</th>
							<td class="bdrn"><input type="text" name="" id=""/></td>
						</tr>	
						<tr>
							<th rowspan="5" class="tac">연금보험료공제</th>
							<th colspan="4">(31) 국민연금보험료</th>
							<td><input type="text" name="" id="" /></td>
							<th rowspan="6" class="tac">연금계좌</th>
							<th rowspan="2" colspan="3">(59) 과학기술인공제</th>
							<th>공제대상금액</th>
							<td class="bdrn"><input type="text" name="" id=""/></td>
						</tr>	
						<tr>
							<th rowspan="4">(32) 공적연금보험료공제</th>
							<th colspan="3">(가) 공무원연금</th>
							<td><input type="text" name="" id="" /></td>
							<th>세액공제액</th>
							<td class="bdrn"><input type="text" name="" id=""/></td>
						</tr>	
						<tr>
							<th colspan="3">(나) 군인연금</th>
							<td><input type="text" name="" id="" /></td>
							<th rowspan="2" colspan="3">(60) 「근로자퇴직급여보장법」에 따른 퇴직연금</th>
							<th>공제대상금액</th>
							<td class="bdrn"><input type="text" name="" id=""/></td>
						</tr>	
						<tr>
							<th colspan="3">(다) 사립학교교직원연금</th>
							<td><input type="text" name="" id="" /></td>
							<th>세액공제액</th>
							<td class="bdrn"><input type="text" name="" id=""/></td>
						</tr>	
						<tr>
							<th colspan="3">(라) 별정우체국연금</th>
							<td><input type="text" name="" id="" /></td>
							<th rowspan="2" colspan="3">(61) 연금저축</th>
							<th>공제대상금액</th>
							<td class="bdrn"><input type="text" name="" id=""/></td>
						</tr>	
						<tr>
							<th rowspan="12" class="tac">특별공제</th>
							<th rowspan="2">(33) 보험료</th>
							<th colspan="3">(가) 건강보험료(노인장기요양보험료포함)</th>
							<td><input type="text" name="" id="" value="221,750"/></td>
							<th>세액공제액</th>
							<td class="bdrn"><input type="text" name="" id=""/></td>
						</tr>	
						<tr>
							<th colspan="3">(나) 고용보험료</th>
							<td><input type="text" name="" id="" value="46,310"/></td>
							<th rowspan="14" class="tac">특별세액공제</th>
							<th rowspan="2" colspan="3">(62) 보장성보험</th>
							<th>공제대상금액</th>
							<td class="bdrn"><input type="text" name="" id=""/></td>
						</tr>	
						<tr>
							<th rowspan="8">(34) 주택자금</th>
							<th rowspan="2" colspan="2">(가) 주택임차차입금원리금상환액</th>
							<th>대출기관</th>
							<td><input type="text" name="" id="" /></td>
							<th>세액공제액</th>
							<td class="bdrn"><input type="text" name="" id=""/></td>
						</tr>	
						<tr>
							<th>거주자</th>
							<td><input type="text" name="" id="" /></td>
							<th rowspan="2" colspan="3">(63) 의료비</th>
							<th>공제대상금액</th>
							<td class="bdrn"><input type="text" name="" id=""/></td>
						</tr>	
						<tr>
							<th colspan="3">(나)</th>
							<td><input type="text" name="" id="" /></td>
							<th>세액공제액</th>
							<td class="bdrn"><input type="text" name="" id=""/></td>
						</tr>	
						<tr>
							<th rowspan="5">(다) 장기주택저당차입금이자상환액</th>
							<th rowspan="3">2011 이전 차입분</th>
							<th>15년 미만</th>
							<td><input type="text" name="" id="" /></td>
							<th rowspan="2" colspan="3">(64) 교육비</th>
							<th>공제대상금액</th>
							<td class="bdrn"><input type="text" name="" id=""/></td>
						</tr>	
						<tr>
							<th>15년~29년</th>
							<td><input type="text" name="" id="" /></td>
							<th>세액공제액</th>
							<td class="bdrn"><input type="text" name="" id=""/></td>
						</tr>	
						<tr>
							<th>30년 이상</th>
							<td><input type="text" name="" id="" /></td>
							<th rowspan="6">(65) 기부금</th>
							<th rowspan="4">(가) 정치자금기부금</th>
							<th rowspan="2">10만원이하</th>
							<th>공제대상금액</th>
							<td class="bdrn"><input type="text" name="" id=""/></td>
						</tr>	
						<tr>
							<th rowspan="2">2012 이후 차입분(15년이상)</th>
							<th>고정금리 · 비거치 상환대출</th>
							<td><input type="text" name="" id="" /></td>
							<th>세액공제액</th>
							<td class="bdrn"><input type="text" name="" id=""/></td>
						</tr>	
						<tr>
							<th>그 밖의 대출</th>
							<td><input type="text" name="" id="" /></td>
							<th rowspan="2">10만원초과</th>
							<th>공제대상금액</th>
							<td class="bdrn"><input type="text" name="" id=""/></td>
						</tr>	
						<tr>
							<th colspan="4">(35) 기부금(이월분)</th>
							<td><input type="text" name="" id="" /></td>
							<th>세액공제액</th>
							<td class="bdrn"><input type="text" name="" id=""/></td>
						</tr>	
						<tr>
							<th colspan="4">(36) 계</th>
							<td><input type="text" name="" id="" value="268,060"/></td>
							<th colspan="3">(나) 법정기부금</th>
							<td class="bdrn"><input type="text" name="" id=""/></td>
						</tr>	
						<tr>
							<th colspan="6">(37) 차감소득금액</th>
							<td><input type="text" name="" id="" value="634,250"/></td>
							<th colspan="3">(다) 지정기부금</th>
							<td class="bdrn"><input type="text" name="" id=""/></td>
						</tr>	
						<tr>
							<th rowspan="13" class="tac"><span class="tit_row_s">그<br />밖<br />의<br />소<br />득<br />공<br />제</span></th>
							<th colspan="5">(38) 개인연금저축</th>
							<td><input type="text" name="" id="" /></td>
							<th colspan="4">(66) 계</th>
							<td class="bdrn"><input type="text" name="" id=""/></td>
						</tr>	
						<tr>
							<th colspan="5">(39) 소기업 · 소상공인 공제부금</th>
							<td><input type="text" name="" id="" /></td>
							<th colspan="4">(67) 표준공제</th>
							<td class="bdrn"><input type="text" name="" id=""/></td>
						</tr>	
						<tr>
							<th rowspan="3" colspan="2">(40) 주택마련저축소득공제</th>
							<th colspan="3">(가) 청약저축</th>
							<td><input type="text" name="" id="" /></td>	
							<th colspan="5">(68) 납세조합공제</th>
							<td class="bdrn"><input type="text" name="" id=""/></td>
						</tr>	
						<tr>
							<th colspan="3">(나) 주택청약종합저축</th>
							<td><input type="text" name="" id="" /></td>	
							<th colspan="5">(69) 주택차입금</th>
							<td class="bdrn"><input type="text" name="" id=""/></td>
						</tr>	
						<tr>
							<th colspan="3">(다) 근로자주택마련저축</th>
							<td><input type="text" name="" id="" /></td>	
							<th colspan="5">(70) 외국납부</th>
							<td class="bdrn"><input type="text" name="" id=""/></td>
						</tr>	
						<tr>
							<th colspan="5">(41) 투자조합출자등 소득공제</th>
							<td><input type="text" name="" id="" /></td>	
							<th colspan="5">(71) 월세액</th>
							<td class="bdrn"><input type="text" name="" id=""/></td>
						</tr>	
						<tr>
							<th colspan="5">(42) 신용카드 등 사용액</th>
							<td><input type="text" name="" id="" /></td>	
							<th colspan="5">(72) 세액공제계</th>
							<td class="bdrn"><input type="text" name="" id="" value="38,010"/></td>
						</tr>	
						<tr>
							<th colspan="5">(43) 우리사주조합 출연금</th>
							<td><input type="text" name="" id="" /></td>	
							<th colspan="6">결정세액 (51)-(56)-(72)</th>
							<td class="bdrn"><input type="text" name="" id="" value="50"/></td>
						</tr>	
						<tr>
							<th colspan="5">(44) 우리사주조합 기부금</th>
							<td><input type="text" name="" id="" /></td>
							<td colspan="7" class="bdrn"></td>
						</tr>	
						<tr>
							<th colspan="5">(45) 고용유지 중소기업 근로자</th>
							<td><input type="text" name="" id="" /></td>
							<td colspan="7" class="bdrn"></td>
						</tr>	
						<tr>
							<th colspan="5">(46) 묵돈 만드는 전세 이자상환액</th>
							<td><input type="text" name="" id="" /></td>
							<td colspan="7" class="bdrn"></td>
						</tr>	
						<tr>
							<th colspan="5">(47) 장기집합투자증권저축</th>
							<td><input type="text" name="" id="" /></td>
							<td colspan="7" class="bdrn"></td>
						</tr>	
						<tr>
							<th colspan="5">(48) 그 밖의 소득공제 계</th>
							<td><input type="text" name="" id="" /></td>
							<td colspan="7" class="bdrn"></td>
						</tr>	
					</tbody>
				</table>
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