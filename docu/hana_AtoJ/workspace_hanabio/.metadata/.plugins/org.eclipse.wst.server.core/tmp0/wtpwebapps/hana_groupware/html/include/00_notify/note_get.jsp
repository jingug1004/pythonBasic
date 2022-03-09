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
	<h2>받은 쪽지함</h2>
	<div class="noticeboard_box">
	
<div class="serch_box">
			<ul class="serch_con01">
				<li>
					<span class="sc_txt">조회기간</span>
					<span class="serch_date_box">
						<input type="text" class="serch_date" />
						<button class="btn_date"><span class="blind">날짜선택</span></button>
					</span> ~ 
					<span class="serch_date_box">
						<input type="text" class="serch_date" />
						<button class="btn_date"><span class="blind">날짜선택</span></button>
					</span>
				</li>
				<li class="cont02">
					<span class="sc_txt">게시물 검색</span>
					<select class="serch_select02">
						<option value="">제목</option>
						<option value="">작성자</option>
					</select>
					<input type="text" class="serch_txt" />
				</li>
			</ul>
			<span class="serch_btn"><button>검색</button></span>
		</div>	
		<div class="list_btn">
			<div class="list_button">
				<button class="type_01">삭제</button>
				<button class="type_01">인쇄</button>
			</div>
			<span class="list_t">* 총 건수:<span class="none_check">99</span>건</span>
		</div>
		<div class="cont_box cont_table05">
			<form>
				<table class="over">
				<colgroup>
					<col width="31px" />
					<col width="72px" />
					<col width="338px" />
					<col width="154px" />
					<col width="81px" />
				</colgroup>
					<thead>
						<tr>
							<th><input type="checkbox" /></th>
							<th>보낸이</th>							
							<th>내용</th>
							<th>보낸일시</th>
							<th>읽은 수</th>
							<th class="br_none">첨부</th>										
						</tr>
					</thead>	
					<tbody>	
						<tr class="in_table">
							<td colspan="6">
							
								<div class="cont_table05 ofy_scroll">
									<table>
									<colgroup>
										<col width="31px" />
										<col width="72px" />
										<col width="338px" />
										<col width="154px" />
										<col width="81px" />
									</colgroup>
										<tbody>	
											<tr>
												<td class="bt_none"><input type="checkbox" /></td>
												<td class="bt_none">이정민</td>
												<td class="bt_none list_txt"><a href="">전체/제품리스트 신청합니다.<span>(3)</span></a></td>
												<td class="bt_none"><span>2014-05-05</span>&nbsp;<span>20:20:20</span></td>
												<td class="bt_none">15</td>
												<td class="bt_none br_none"><a href=""><img src="<%=GROUP_ROOT%>/asset/img/img_file.png" alt="파일첨부" /></a></td>										
											</tr>
											<tr>
												<td><input type="checkbox" /></td>
												<td>이정민</td>
												<td class="list_txt none_check"><a href="">명함신청합니다.명함신청합니다.명함신청합니다.명함신청합니다.명함신청합니다.명함신청합니다.<span>(3)</span></a></td>
												<td><span>2014-05-05</span>&nbsp;<span>20:20:20</span></td>
												<td>9999</td>
												<td class="br_none"><a href=""><img src="<%=GROUP_ROOT%>/asset/img/img_file.png" alt="파일첨부" /></a></td>										
											</tr>
											<tr>
												<td><input type="checkbox" /></td>
												<td>이정민</td>
												<td class="list_txt"><a href="">전체/제품리스트 신청합니다.<span>(3)</span></a></td>
												<td><span>2014-05-05</span>&nbsp;<span>20:20:20</span></td>
												<td>9999</td>
												<td class="br_none"><a href=""><img src="<%=GROUP_ROOT%>/asset/img/img_file.png" alt="파일첨부" /></a></td>										
											</tr>
											<tr>
												<td><input type="checkbox" /></td>
												<td>이정민</td>
												<td class="list_txt"><a href="">전체/제품리스트 신청합니다.<span>(3)</span></a></td>
												<td><span>2014-05-05</span>&nbsp;<span>20:20:20</span></td>
												<td>9999</td>
												<td class="br_none"><a href=""><img src="<%=GROUP_ROOT%>/asset/img/img_file.png" alt="파일첨부" /></a></td>										
											</tr>
											<tr>
												<td><input type="checkbox" /></td>
												<td>이정민</td>
												<td class="list_txt"><a href="">전체/제품리스트 신청합니다.<span>(3)</span></a></td>
												<td><span>2014-05-05</span>&nbsp;<span>20:20:20</span></td>
												<td>9999</td>
												<td class="br_none"><a href=""><img src="<%=GROUP_ROOT%>/asset/img/img_file.png" alt="파일첨부" /></a></td>										
											</tr>
											<tr>
												<td><input type="checkbox" /></td>
												<td>이정민</td>
												<td class="list_txt"><a href="">전체/제품리스트 신청합니다.<span>(3)</span></a></td>
												<td><span>2014-05-05</span>&nbsp;<span>20:20:20</span></td>
												<td>9999</td>
												<td class="br_none"><a href=""><img src="<%=GROUP_ROOT%>/asset/img/img_file.png" alt="파일첨부" /></a></td>										
											</tr>
											<tr>
												<td><input type="checkbox" /></td>
												<td>이정민</td>
												<td class="list_txt"><a href="">전체/제품리스트 신청합니다.<span>(3)</span></a></td>
												<td><span>2014-05-05</span>&nbsp;<span>20:20:20</span></td>
												<td>9999</td>
												<td class="br_none"><a href=""><img src="<%=GROUP_ROOT%>/asset/img/img_file.png" alt="파일첨부" /></a></td>										
											</tr>
											<tr>
												<td><input type="checkbox" /></td>
												<td>이정민</td>
												<td class="list_txt"><a href="">전체/제품리스트 신청합니다.<span>(3)</span></a></td>
												<td><span>2014-05-05</span>&nbsp;<span>20:20:20</span></td>
												<td>9999</td>
												<td class="br_none"><a href=""><img src="<%=GROUP_ROOT%>/asset/img/img_file.png" alt="파일첨부" /></a></td>										
											</tr>						
										</tbody>
									</table>
								</div>
							</td>
						</tr>
						<tr class="ts_reset">
							<th colspan="2">보낸이</th>
							<td colspan="4">이정민</td>
						</tr>
						<!-- 2014.12.08 class="max_height" 추가, name <div></div> 추가 start-->
						<tr class="ts_reset">
							<th colspan="2">받는이</th>
							<td colspan="4" class="max_height">
							<div>
							강웅, 최일지<br />
							강웅, 최일지<br />
							강웅, 최일지<br />
							강웅, 최일지<br />
							강웅, 최일지<br />
							강웅, 최일지<br />
							강웅, 최일지<br />
							강웅, 최일지<br />
							강웅, 최일지<br />
							강웅, 최일지<br />
							강웅, 최일지<br />
							강웅, 최일지<br />
							강웅, 최일지<br />
							강웅, 최일지<br />
							강웅, 최일지<br />
							강웅, 최일지<br />
							강웅, 최일지<br />
							</div></td>
						</tr>
						<!-- 2014.12.08 class="max_height" 추가, name <div></div> 추가 end-->
						<tr class="txt_box_re">
							<td colspan="6">
								<div>
									test<br />
									test<br />
									test<br />
									test<br />
									test<br />
									test<br />
									test<br />
									test<br />
									test<br />
									test
								</div>
							</td>
						</tr>
						<tr class="ts_reset">
							<th class="bb_none" colspan="2">첨부파일</th>
							<td colspan="4" class="append_file01">
								<div class="append_list">
									<span>
										<a href="">aaa.jpg</a>,
										<a href="">BB.dock</a>,
										<a href="">ccc.gif</a>
									</span>
								</div>
							</td>
						</tr>						
					</tbody>
				</table>
			</form>
		</div>		
	</div>
</div>


<!-- ######## end ######### -->
			
		</div>
	</div>
	
	<%@include file="../../include/footer.jsp"%>
</body>
</html>