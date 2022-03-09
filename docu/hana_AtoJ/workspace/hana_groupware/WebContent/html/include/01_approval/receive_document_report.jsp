<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="ko">
<head>
	<%@include file="../../include/head.jsp"%>	
</head>
<body>

<div class="wrap_pop_bg no_bg">
	<div class="wrap_receive_document wrap_document_print">
		<div class="top">
			<h3>인쇄<span> (지각/조퇴/결근)계</span></h3>
			<button class="close"><span class="blind">닫기</span></button>
			<div>
				<div class="fr">
					<button class="type2">인쇄</button>
				</div>
			</div>
		</div>
		<div class="inner">
			<div class="cont_box cont_table06">
				<h4><strong>(지각/조퇴/결근)계</strong></h4>
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
							<th>결재선</th>
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
							<th>문서번호</th>
							<td>전산-2014-0011</td>
							<th>작성일자</th>
							<td class="bdr2">2014-10-15 10:40</td>
						</tr>
						<tr>
							<th>작성부서</th>
							<td>영업부</td>
							<th>작성자</th>
							<td class="bdr2">이수연</td>
						</tr>
						<tr>
							<th>제목</th>
							<td colspan="3" class="bdr2"></td>
						</tr>
					</tbody>
				</table>
	
				<table class="tbl_report">
					<colgroup>
						<col style="width:99px">
						<col style="">
						<col style="width:99px">
						<col style="">
					</colgroup>
					<tbody>
						<tr>
							<th>내용</th>
							<td></td>
							<th>사전보고유무</th>
							<td class="bdr2"></td>
						</tr>
						<tr>
							<th>사전보고수령자</th>
							<td></td>
							<th>근태계 날짜</th>
							<td class="bdr2"></td>
						</tr>
						<tr>
							<th>미보고사유</th>
							<td colspan="3" class="bdr2"></td>
						</tr>
						<tr>
							<th>사유</th>
							<td colspan="3" class="bdrn inner">
								<table>
									<colgroup>
										<col style="width:102px"/>
										<col style=""/>
									</colgroup>
									<tbody>
										<tr>
											<th class="bdtn">지각(출근시각)</th>
											<td class="bdtn">10:00</td>
										</tr>
										<tr>
											<th>조퇴(발생시각)</th>
											<td></td>										
										</tr>
										<tr>
											<th>결근(결근기간)</th>
											<td>2014.10.15 ~ 2014.10.16</td>										
										</tr>
										<tr>
											<th>내용</th>
											<td class="ta"><div></div></td>										
										</tr>
									</tbody>
								</table>
							</td>
						</tr>
						<tr>
							<th>비고</th>
							<td colspan="3" class="etc bdr2">
								-해당 사유 내용은 구체적으로 기입<br />
								-예정된 지각, 조퇴, 결근의 경우 해당 날짜 및 시간 명시<br />
								-지각, 조퇴, 결근은 선보고를 원칙으로 하며, 미보고시 별도의 미보고 사유를 기입
							</td>
						</tr>
					</tbody>
				</table>
			</div>
	
		</div>
	</div>
</div>

</body>
</html>