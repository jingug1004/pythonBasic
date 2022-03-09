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
			<li><a href="#">PAGE 2</a></li>
			<li class="on"><a href="#">PAGE 3</a></li>
		</ul>
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
							<td><input type="text" name="" id="" /></td>
							<td><input type="text" name="" id="" /></td>
							<td><input type="text" name="" id="" /></td>
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
							<td class="bdrn"><input type="text" name="" id="" /></td>
						</tr>
						<tr>
							<td><input type="text" name="" id="" /></td>
							<td><input type="text" name="" id="" /></td>
							<td><input type="text" name="" id="" /></td>
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
							<td colspan="2"><input type="text" name="" id="" class="tac" value="O"/></td>
							<td><input type="text" name="" id="" /></td>
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
							<td colspan="2"><input type="text" name="" id=""/></td>
							<td><input type="text" name="" id="" /></td>
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

<!-- ######## end ######### -->
			
		</div>
	</div>
	
	<%@include file="../../include/footer.jsp"%>
</body>
</html>