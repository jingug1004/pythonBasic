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
				<div class="settlement_cont">
					<div class="settlement_box01">
						<div class="fl">
							<h3>내가올린문서</h3>
							<div class="tableB cont_table06 table_width01">
								<table>
									<colgroup>
										<col style="width:186px"/>
										<col style="width:186px"/>
									</colgroup>
									<tbody>
										<tr>
											<th class="bt_none">작성중</th>
											<td class="bt_none"><a href="">99999건</a></td>
										</tr>
										<tr>
											<th>요청</th>
											<td><a href="">9999건</a></td>
										</tr>
										<tr>
											<th>진행중</th>
											<td><a href="">0건</a></td>
										</tr>
										<tr>
											<th>완료</th>
											<td><a href="">99건</a></td>
										</tr>
										<tr>
											<th>반려</th>
											<td><a href="">999건</a></td>
										</tr>										
									</tbody>
								</table>
							</div>
							<p class="settle_txt">장기미결재&nbsp;&nbsp;&nbsp;<a href="">1</a>&nbsp;건</p>
						</div>
						
						<div class="fr">
							<h3>내가받은문서</h3>
							<div class="tableB cont_table06 table_width01">
								<table>
									<colgroup>
										<col style="width:123px"/>
										<col style="width:82px"/>
										<col style="width:82px"/>
										<col style="width:82px"/>
									</colgroup>
									<tbody>
										<tr>
											<th class="bt_none"></th>
											<th class="bt_none">결제</th>
											<th class="bt_none">협조</th>
											<th class="bt_none">참조</th>
										</tr>
										<tr>
											<th>요청</th>
											<td><a href="">9999건</a></td>
											<td><a href="">9999건</a></td>
											<td><a href="">9999건</a></td>
										</tr>
										<tr>
											<th>진행중</th>
											<td><a href="">9999건</a></td>
											<td><a href="">9999건</a></td>											
											<td><a href="">0건</a></td>
										</tr>
										<tr>
											<th>완료</th>
											<td><a href="">9999건</a></td>
											<td><a href="">9999건</a></td>											
											<td><a href="">99건</a></td>
										</tr>
										<tr>
											<th>반려</th>
											<td><a href="">9999건</a></td>
											<td><a href="">9999건</a></td>											
											<td><a href="">999건</a></td>
										</tr>										
									</tbody>
								</table>
							</div>
							<p class="settle_txt">문서수발건&nbsp;&nbsp;&nbsp;<a href="">1</a>&nbsp;건</p>							
						</div>
					</div>
					<div class="settlement_box02">
						<div class="settle_tit_box">
							<h3 class="fl">내가올린문서</h3>
							<p class="more fr"><a href="">+ 더보기</a></p>
						</div>
						<div class="tableB cont_table06 table_width02">
							<table>
								<thead>
									<tr>
										<th class="bt_none">문서번호</th>
										<th class="bt_none">문서종류</th>
										<th class="bt_none">제목</th>
										<th class="bt_none">기안자</th>
										<th class="bt_none">기안일</th>
										<th class="bt_none">문서상태</th>
										<th class="bt_none">처리자</th>
									</tr>
								</thead>
								<tbody>
									<tr>
										<td>전산-2014-0011</td>
										<td class="fc_blue">기안서</td>
										<td>2014-11-17 휴가신청합니다.</td>
										<td>김수환무거북이</td>
										<td>2014-11-15</td>
										<td class="">완료</td>
										<td>정찬근</td>
									</tr>
									<tr>
										<td>전산-2014-0011</td>
										<td class="fc_blue">근태신청서</td>
										<td>2014-11-17 휴가신청합니다.</td>
										<td>김수환무거북이</td>
										<td>2014-11-15</td>
										<td class="fc_red">반려</td>
										<td>정찬근</td>
									</tr>
									<tr>
										<td>전산-2014-0011</td>
										<td class="fc_blue">근태계</td>
										<td>2014-11-17 휴가신청합니다.</td>
										<td>김수환무거북이</td>
										<td>2014-11-15</td>
										<td class="fc_blue">진행중</td>
										<td>정찬근</td>
									</tr>									
								</tbody>
							</table>
						</div>
					</div>
					<div class="settlement_box02">
						<div class="settle_tit_box">
							<h3 class="fl">내가받은문서</h3>
							<p class="more fr"><a href="">+ 더보기</a></p>
						</div>
						<div class="tableB cont_table06 table_width02">
							<table>
								<thead>
									<tr>
										<th class="bt_none">문서번호</th>
										<th class="bt_none">문서종류</th>
										<th class="bt_none">제목</th>
										<th class="bt_none">기안자</th>
										<th class="bt_none">기안일</th>
										<th class="bt_none">문서상태</th>
										<th class="bt_none">처리자</th>
									</tr>
								</thead>
								<tbody>
									<tr>
										<td>전산-2014-0011</td>
										<td class="fc_blue">기안서</td>
										<td>2014-11-17 휴가신청합니다.</td>
										<td>김수환무거북이</td>
										<td>2014-11-15</td>
										<td class="">완료</td>
										<td>정찬근</td>
									</tr>
									<tr>
										<td>전산-2014-0011</td>
										<td class="fc_blue">근태신청서</td>
										<td>2014-11-17 휴가신청합니다.</td>
										<td>김수환무거북이</td>
										<td>2014-11-15</td>
										<td class="fc_red">반려</td>
										<td>정찬근</td>
									</tr>
									<tr>
										<td>전산-2014-0011</td>
										<td class="fc_blue">근태계</td>
										<td>2014-11-17 휴가신청합니다.</td>
										<td>김수환무거북이</td>
										<td>2014-11-15</td>
										<td class="fc_blue">진행중</td>
										<td>정찬근</td>
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