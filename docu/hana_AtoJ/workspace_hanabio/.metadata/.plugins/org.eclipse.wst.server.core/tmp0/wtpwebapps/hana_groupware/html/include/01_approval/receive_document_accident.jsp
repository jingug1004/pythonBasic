<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="ko">
<head>
	<%@include file="../../include/head.jsp"%>	
	<!--[if IE]>
		<style type="text/css">
			.wrap_receive_document .cont_box .tbl_accident {border-collapse:separate}
		</style>
	<![endif]-->
</head>
<body>

<div class="wrap_pop_bg no_bg">
	<div class="wrap_receive_document wrap_document_print">
		<div class="top">
			<h3>인쇄<span> 사고보고서</span></h3>
			<button class="close"><span class="blind">닫기</span></button>
			<div>
				<div class="fr">
					<button class="type2">인쇄</button>
				</div>
			</div>
		</div>
		<div class="inner">
			<div class="cont_box cont_table06">
				<h4><strong>사고보고서</strong></h4>
				<div class="refer">
					<ul>
						<li>전결 가능합니다.</li>
						<li>대외비입니다.</li>
					</ul>
				</div>
				<table class="tbl_register">
					<colgroup>
						<col style="width:14%">
						<col style="width:14%">
						<col style="width:14%">
						<col style="width:14%">
						<col style="width:14%">
						<col style="width:14%">
						<col style="width:14%">
					</colgroup>
					<tbody>
						<tr>
							<th class="">결재선</th>
							<td>우정아<br />2014-12-05<br />10:40</td>
							<td>강웅<br />2014-12-05<br />10:40</td>
							<td></td>
							<td></td>
							<td></td>
							<td class="bdr2"></td>
						</tr>				
					</tbody>
				</table>
	
				<table class="tbl_info">
					<colgroup>
						<col style="width:99px">
						<col style="">
						<col style="width:99px">
						<col style="">
					</colgroup>
					<tbody>
						<tr>
							<th class="">문서번호</th>
							<td>전산-2014-0011</td>
							<th>작성일자</th>
							<td class="bdr2">2014-10-15 10:40</td>
						</tr>
						<tr>
							<th class="">작성부서</th>
							<td>영업부</td>
							<th>작성자</th>
							<td class="bdr2">이수연</td>
						</tr>
						<tr>
							<th class="">제목</th>
							<td colspan="3" class="bdr2"></td>
						</tr>
					</tbody>
				</table>
	
				<table class="tbl_accident mt20">
					<colgroup>
						<col style="width:99px">
						<col style="">
						<col style="width:99px">
						<col style="">
					</colgroup>
					<tbody>
						<tr class="bdts">
							<th class="">거래처명</th>
							<td class="bdr"></td>
							<th class="bdr">거래처코드</th>
							<td class="bdr2"></td>
						</tr>				
						<tr>
							<th class="">지점</th>
							<td class="bdr"></td>
							<th class="bdr">담당자</th>
							<td class="bdr2"></td>
						</tr>	
						<tr>
							<td colspan="4" class="divide"></td>
						</tr>
						<tr class="bdts">
							<th class="">사고구분</th>
							<td class="bdr"></td>
							<th class="bdr">총매출/총수금</th>
							<td class="bdr2"></td>
						</tr>				
						<tr>
							<th class="">사고발생일</th>
							<td class="bdr"></td>
							<th class="bdr">최종거래일</th>
							<td class="bdr2"></td>
						</tr>	
						<tr>
							<td colspan="4" class="divide bdbn"></td>
						</tr>
					</tbody>
				</table>
	
				<table class="tbl_accident">
					<colgroup>
						<col style="width:99px"/>
						<col style="width:112px"/>
						<col style=""/>
						<col style=""/>
						<col style=""/>
					</colgroup>
					<tbody>
						<tr class="bdts">
							<th rowspan="5" class="bd_show">거래처<br />상세내역</th>
							<th>구분</th>
							<th>이름/상호</th>
							<th>주민/사업자번호</th>
							<th class="bdr2">주소/소재지</th>
						</tr>				
						<tr>
							<th>거래처</th>
							<td></td>
							<td></td>
							<td class="bdr2"></td>
						</tr>
						<tr>
							<th>대표자</th>
							<td></td>
							<td></td>
							<td class="bdr2"></td>
						</tr>
						<tr>
							<th>연대보증인1</th>
							<td></td>
							<td></td>
							<td class="bdr2"></td>
						</tr>
						<tr>
							<th>연대보증인2</th>
							<td></td>
							<td></td>
							<td class="bdr2"></td>
						</tr>
						<tr>
							<td colspan="5" class="divide bdbn"></td>
						</tr>
					</tbody>
				</table>
	
				<table class="tbl_accident">
					<colgroup>
						<col style="width:99px">
						<col style="width:99px">
						<col style="">
						<col style="width:99px">
						<col style="">
					</colgroup>
					<tbody>
						<tr class="bdts">
							<th rowspan="2" class="bd_show">채권내역</th>
							<th>외상물품대금</th>
							<td></td>
							<th>미도래어음</th>
							<td class="bdr2"></td>
						</tr>				
						<tr>
							<th>부도채권총액</th>
							<td colspan="3" class="bdr2"></td>
						</tr>
						<tr>	
							<td colspan="5" class="divide bdbn"></td>
						</tr>
					</tbody>
				</table>
				<table class="tbl_accident">
					<colgroup>
						<col style="width:99px"/>
						<col style="width:96px"/>
						<col style=""/>
						<col style=""/>
						<col style="width:184px"/>
						<col style=""/>
					</colgroup>
					<tbody>
						<tr class="bdts">
							<th rowspan="6" class="bd_show">담보현황</th>
							<th></th>
							<th>금액</th>
							<th>만기일</th>
							<th>부동산소재지/발행인</th>
							<th class="bdr2">배서인</th>
						</tr>				
						<tr>
							<th>부동산</th>
							<td></td>
							<td></td>
							<td></td>
							<td class="bdr2"></td>
						</tr>
						<tr>
							<th>어음/수표</th>
							<td></td>
							<td></td>
							<td></td>
							<td class="bdr2"></td>
						</tr>
						<tr>
							<th>보증보험</th>
							<td></td>
							<td></td>
							<td></td>
							<td class="bdr2"></td>
						</tr>
						<tr>
							<th>지급보증</th>
							<td></td>
							<td></td>
							<td></td>
							<td class="bdr2"></td>
						</tr>
						<tr>
							<th>기타</th>
							<td></td>
							<td></td>
							<td></td>
							<td class="bdr2"></td>
						</tr>
						<tr>
							<td colspan="6" class="divide bdbn"></td>
						</tr>
					</tbody>
				</table>
				<table class="tbl_accident bdbs">
					<colgroup>
						<col style="width:99px"/>
						<col style="width:99px"/>
						<col style=""/>
						<col style="width:99px"/>
						<col style=""/>
					</colgroup>
					<tbody>
						<tr class="bdts">
							<th>회수내용</th>
							<th>반품회수액</th>
							<td></td>
							<th>금전회수액</th>
							<td class="bdr2"></td>
						</tr>
						<tr>
							<td colspan="5" class="divide"></td>
						</tr>
						<tr class="bdts">
							<th>보고내용<br />및<br />진행사항</th>
							<td colspan="4" class="ta bdr2"><div></div></td>
						</tr>
					</tbody>
				</table>
				<p class="ref_accident">※첨부서류 : 거래처카드</p>
				<div class="box_accident_report">
					<span>2014</span>.<span>11</span>.<span>12</span>
					<p>상기와 같이 보고합니다.</p>
				</div>
			</div>
	
		</div>
	</div>
</div>

</body>
</html>