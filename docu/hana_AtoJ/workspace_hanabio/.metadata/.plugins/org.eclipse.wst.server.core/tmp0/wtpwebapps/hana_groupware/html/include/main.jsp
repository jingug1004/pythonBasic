<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
	<%@include file="../include/head.jsp"%>
</head>
<body>
	<%@include file="../include/header.jsp"%>
	<%@include file="../include/gnb.jsp"%>
	
	<div class="wrap_con">
		<div class="content">
			
			<!-- ######## start ####### -->
			<div class="cont">
				<div class="wrap_main">
				
					<!-- <div class="wrap_tb">
						<div class="float_l mBox">
							<h2>인사현황</h2>
							<div class="tableB mt10">
								<table>
									<colgroup>
										<col style="width:45%" />
										<col style="width:55%" />
									</colgroup>
									<tr class="no_bd_t">
										<th>서울사무소</th>
										<td>15</td>
									</tr>
									<tr>
										<th>영업부</th>
										<td>255</td>
									</tr>
									<tr>
										<th>상신공장</th>
										<td>17</td>
									</tr>
									<tr>
										<th>하길공장</th>
										<td>120</td>
									</tr>
									<tr>
										<th>총인원</th>
										<td>428</td>
									</tr>
								</table>
							</div>
							<p class="read_more"><a href="#">+ 더보기</a></p>
						</div>
						<div class="float_l mBox ml30">
							<h2>발령현황</h2>
							<div class="tableB mt10">
								<table>
									<thead>
										<tr>
											<th>구분</th>
											<th>오늘</th>
											<th>이달</th>
											<th>올해</th>
											<th>지난해</th>
										</tr>
									</thead>
									<tbody>
										<tr>
											<td>입사(신입)</td>
											<td>0</td>
											<td>0</td>
											<td>38</td>
											<td>49</td>
										</tr>
										<tr>
											<td>입사(경력)</td>
											<td>0</td>
											<td>5</td>
											<td>68</td>
											<td>77</td>
										</tr>
										<tr>
											<td>입사(기타)</td>
											<td>0</td>
											<td>0</td>
											<td>0</td>
											<td>0</td>
										</tr>
										<tr>
											<td>퇴사</td>
											<td>0</td>
											<td>0</td>
											<td>76</td>
											<td>84</td>
										</tr>
									</tbody>
								</table>
							</div>
							<p class="read_more"><a href="#">+ 더보기</a></p>
						</div>
					</div> --> <!-- 20150107 주석 처리 -->
					
					<div class="wrap_list">
						<div class="float_l mBox">
							<h2>공지사항</h2>
							
							<ul>
								<li class="on"><a href="#"><span class="tit">SFA 카드수금 : 단말기 사용 공지</span><span class="date">2014-05-05</span></a></li>
								<li><a href="#"><span class="tit">&lt;수금 단말기 관련 공지&gt;</span><span class="date">2014-05-05</span></a></li>
								<li><a href="#"><span class="tit">PDA 저조자교육 공지의 건</span><span class="date">2014-05-05</span></a></li>
								<li><a href="#"><span class="tit">하나제약 프로그램 담당 부서</span><span class="date">2014-05-05</span></a></li>
							</ul>
							
							<p class="read_more"><a href="#">+ 더보기</a></p>
						</div>
						<div class="float_l mBox ml30">
							<h2>쪽지</h2>
							
							<ul>
								<li><a href="#"><span class="tit">SFA 카드수금 : 단말기 사용 공지</span><span class="date">2014-05-05</span></a></li>
								<li><a href="#"><span class="tit">&lt;수금 단말기 관련 공지&gt;</span><span class="date">2014-05-05</span></a></li>
								<li><a href="#"><span class="tit">PDA 저조자교육 공지의 건</span><span class="date">2014-05-05</span></a></li>
								<li><a href="#"><span class="tit">하나제약 프로그램 담당 부서</span><span class="date">2014-05-05</span></a></li>
							</ul>
							
							<p class="read_more"><a href="#">+ 더보기</a></p>
						</div>
					</div>

					<div class="settlement_cont mt40">
						<div class="settlement_box01">
							<div class="fl">
								<h2>내가올린문서</h2>
								<div class="tableB cont_table06 table_width03">
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
								<div class="case">
									<h2>장기미결재</h2>
									<p class="settle_txt"><a href="">1</a>&nbsp;건</p>
								</div>
							</div>
							
							<div class="fr">
								<h2>내가받은문서</h2>
								<div class="tableB cont_table06 table_width03">
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
								<div class="case">
									<h2>문서수발건</h2>
									<p class="settle_txt"><a href="">1</a>&nbsp;건</p>
								</div>					
							</div>
						</div>
						<div class="settlement_box02">
							<div class="settle_tit_box">
								<h2 class="fl">내가올린문서</h2>
								<p class="more fr"><a href="">+ 더보기</a></p>
							</div>
							<div class="tableB cont_table06 table_width02 rpx">
								<table class="tr_over">
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
											<td>근태계</td>
											<td>2014-11-17 휴가신청합니다.</td>
											<td>김수환무거북이</td>
											<td>2014-11-15</td>
											<td>진행중</td>
											<td>정찬근</td>
										</tr>
										<tr class="fc_blue"> <!-- 파란색 font color 2015-02-09 추가 -->
											<td>전산-2014-0011</td>
											<td>기안서</td>
											<td>2014-11-17 휴가신청합니다.</td>
											<td>김수환무거북이</td>
											<td>2014-11-15</td>
											<td>완료</td>
											<td>정찬근</td>
										</tr>
										<tr>
											<td>전산-2014-0011</td>
											<td>근태계</td>
											<td>2014-11-17 휴가신청합니다.</td>
											<td>김수환무거북이</td>
											<td>2014-11-15</td>
											<td>진행중</td>
											<td>정찬근</td>
										</tr>
										<tr class="fc_red"> <!-- 빨간색 font color 2015-02-09 추가 -->
											<td>전산-2014-0011</td>
											<td>근태신청서</td>
											<td>2014-11-17 휴가신청합니다.</td>
											<td>김수환무거북이</td>
											<td>2014-11-15</td>
											<td>반려</td>
											<td>정찬근</td>
										</tr>
										<tr>
											<td>전산-2014-0011</td>
											<td>근태계</td>
											<td>2014-11-17 휴가신청합니다.</td>
											<td>김수환무거북이</td>
											<td>2014-11-15</td>
											<td>진행중</td>
											<td>정찬근</td>
										</tr>									
									</tbody>
								</table>
							</div>
						</div>
						<div class="settlement_box02">
							<div class="settle_tit_box">
								<h2 class="fl">내가받은문서</h2>
								<p class="more fr"><a href="">+ 더보기</a></p>
							</div>
							<div class="tableB cont_table06 table_width02 rpx">
								<table class="tr_over">
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
											<td>기안서</td>
											<td>2014-11-17 휴가신청합니다.</td>
											<td>김수환무거북이</td>
											<td>2014-11-15</td>
											<td>완료</td>
											<td>정찬근</td>
										</tr>
										<tr class="fc_red"> <!-- 빨간색 font color 2015-02-09 추가 -->
											<td>전산-2014-0011</td>
											<td>근태신청서</td>
											<td>2014-11-17 휴가신청합니다.</td>
											<td>김수환무거북이</td>
											<td>2014-11-15</td>
											<td>반려</td>
											<td>정찬근</td>
										</tr>
										<tr>
											<td>전산-2014-0011</td>
											<td>근태계</td>
											<td>2014-11-17 휴가신청합니다.</td>
											<td>김수환무거북이</td>
											<td>2014-11-15</td>
											<td>진행중</td>
											<td>정찬근</td>
										</tr>
										<tr>
											<td>전산-2014-0011</td>
											<td>근태계</td>
											<td>2014-11-17 휴가신청합니다.</td>
											<td>김수환무거북이</td>
											<td>2014-11-15</td>
											<td>진행중</td>
											<td>정찬근</td>
										</tr>
										<tr class="fc_blue"> <!-- 파란색 font color 2015-02-09 추가 -->
											<td>전산-2014-0011</td>
											<td>근태계</td>
											<td>2014-11-17 휴가신청합니다.</td>
											<td>김수환무거북이</td>
											<td>2014-11-15</td>
											<td>진행중</td>
											<td>정찬근</td>
										</tr>									
									</tbody>
								</table>
							</div>
						</div>
					</div>
				
				</div>
			</div>
			<!-- ######## end ######### -->
			
		</div>
	</div>
	
	<%@include file="../include/footer.jsp"%>

<!-- 2015-02-06 추가 -->
<script type="text/javascript"> 
$(document).ready(function(){

	$('table.tr_over tbody tr').mouseover(function(){
		$(this).addClass('hightlight');
	});

	$('table.tr_over tbody tr').mouseout(function(){
		$(this).removeClass('hightlight');
	});
});
</script>
<!-- //2015-02-06 추가 -->
	
</body>
</html>