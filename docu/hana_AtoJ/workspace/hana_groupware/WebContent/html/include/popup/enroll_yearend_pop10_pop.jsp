<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
	<%@include file="../../include/head.jsp"%>
</head>
<body>
<!-- ######## start ####### -->
	
<!--  popup start --><!-- pop up 가로 사이즈 330px -->
		<div class="individ_pop">
			<div class="popup_box">
				<h3>관계인선택</h3>
				<div class="pop_con_all pop_register pop_yearend rel">
					<div class="search_box03">
						<div class="float_l">
							<p>
								<span>주민등록번호</span>
								<input type="text" name="" id="" />
							</p>
							<p>
								<span>가족이름</span>
								<input type="text" name="" id="" />
							</p>
						</div>
						<button class="search02">검색</button>
					</div>
					<div class="box_scroll">
						<table>
							<colgroup>
								<col style="width:22px"/>
								<col style="width:95px"/>
								<col style="width:95px"/>
								<col style=""/>
							</colgroup>
							<thead>
								<tr>
									<th>√</th>
									<th>주민번호</th>
									<th>관계</th>
									<th class="bdrn">성명</th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<td colspan="4" class="inner">
										<div>
											<table>
												<colgroup>
													<col style="width:22px"/>
													<col style="width:95px"/>
													<col style="width:95px"/>
													<col style=""/>												
												</colgroup>
												<tbody>
													<tr>
														<td><input type="checkbox" name="number" id="" /></td>
														<td>970204-216371</td>
														<td>소득자본인</td>
														<td class="bdrn">장수민</td>
													</tr>
												</tbody>
											</table>
										</div>
									</td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>
				<div class="btn_pop">
					<button class="type_01">확인</button>
					<button class="type_01">닫기</button>
				</div>
				<button class="close"><span class="blind">닫기</span></button>
			</div>
		</div>
		<!--  popup end -->		

<!-- ######## end ######### -->
</body>
</html>