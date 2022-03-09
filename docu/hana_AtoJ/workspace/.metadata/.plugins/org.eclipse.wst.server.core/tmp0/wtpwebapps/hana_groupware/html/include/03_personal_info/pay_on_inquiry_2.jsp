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
	<h2>급여조회</h2>
	<div class="wrap_pay_on_inquiry">
		<div class="wrap_btn">
			<button class="type_01">미리보기</button>
		</div>
		<div class="wrap_list mt10">
			<div class="tableA">
				<table>
					<colgroup>
						<col style="width:25%;" />
						<col style="width:25%;" />
						<col style="width:25%;" />
						<col style="width:25%;" />
					</colgroup>
					<tr>
						<td colspan="4"><h3>20140831 정기급여(하나제약)<h3></td>
					</tr>
					<tr>
						<th>부서</th>
						<th>직위</th>
						<th>사번</th>
						<th>성명</th>
					</tr>
					<tr>
						<td>서울로컬1호점</td>
						<td>과장대리</td>
						<td>2008172</td>
						<td>박세호</td>
					</tr>
				</table>
			</div>
			<div class="tableA">
				<table>
					<colgroup>
						<col style="width:20%;" />
						<col style="width:20%;" />
						<col style="width:20%;" />
						<col style="width:20%;" />
						<col style="width:20%;" />
					</colgroup>
					<tr>
						<th>기본급</th>
						<th>상여금</th>
						<th>직책수당</th>
						<th>가족수당</th>
						<th>연장근로수당</th>
					</tr>
					<tr>
						<td>2,390,000</td>
						<td>1,195,000</td>
						<td></td>
						<td></td>
						<td></td>
					</tr>
					<tr>
						<th>기타지급</th>
						<th>상여근태감액</th>
						<th>특수직무수당</th>
						<th>업무수당</th>
						<th>휴일근로수당</th>
					</tr>
					<tr>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
					</tr>
					<tr>
						<th>기타수당</th>
						<th>식대보조비</th>
						<th>식대보조</th>
						<th>생산비과세</th>
						<th>인센티브</th>
					</tr>
					<tr>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td>398,330</td>
					</tr>
					<tr>
						<th>자격수당</th>
						<th>출납수당</th>
						<th>야간근로수당</th>
						<th>누락분</th>
						<th rowspan="2" class="bg_w"></th>
					</tr>
					<tr>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
					</tr>
					<tr>
						<th colspan="3">급여총액</th>
						<td colspan="2" class="txt_r">3,983,330</td>
					</tr>
					<tr>
						<th>소득세</th>
						<th>주민세</th>
						<th>국민연금</th>
						<th>건강보험</th>
						<th>고용보험</th>
					</tr>
					<tr>
						<td>199,110</td>
						<td>19,910</td>
						<td>183,600</td>
						<td>139,660</td>
						<td>25,890</td>
					</tr>
					<tr>
						<th>가지급금</th>
						<th>사우회비</th>
						<th>사우회비상환</th>
						<th>기타공제</th>
						<th>소득세(연말)</th>
					</tr>
					<tr>
						<td></td>
						<td></td>
						<td></td>
						<td colspan="2" rowspan="3"></td>
					</tr>
					<tr>
						<th>주민세(연말)</th>
						<th>농특세(연말)</th>
						<th>보증보험</th>
					</tr>
					<tr>
						<td></td>
						<td></td>
						<td></td>
					</tr>
					<tr>
						<th colspan="3">공제계</th>
						<td colspan="2" class="txt_r">580,120</td>
					</tr>
					<tr>
						<th colspan="3">순지급액</th>
						<td colspan="2" class="txt_r">3,403,210</td>
					</tr>
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