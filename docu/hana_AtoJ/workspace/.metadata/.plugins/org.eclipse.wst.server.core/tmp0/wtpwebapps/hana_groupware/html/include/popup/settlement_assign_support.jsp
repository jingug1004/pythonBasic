<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
	<%@include file="../../include/head.jsp"%>
</head>
<body>
<!-- ######## start ####### -->
	
<!--  popup start -->
		<div class="settle_pop">
			<div class="popup_box">
				<h3>협조결재라인 지정</h3>
				<div class="wrap_settle_pop">
					<div class="box_sign_overtime">
						<span class="tit">협조부서 결재라인 지정</span>				
					</div>
					<div class="pop_con_all pop_register">
						<div class="float_l">
							<div class="search_box03 tac">
								<span>사원명</span>
								<input type="text">
								<button class="search2">검색</button>
							</div>
							<div class="box_scroll">
								<table>
									<colgroup>
										<col style="width:28px"/>
										<col style="width:112px"/>
										<col style="width:90px"/>
										<col style=""/>
									</colgroup>
									<thead>
										<tr>
											<th></th>
											<th>부서</th>
											<th>직급</th>
											<th class="bdrn">사원명</th>
										</tr>
									</thead>
									<tbody>
										<tr>
											<td><input type="radio" name="checkName" id="" /></td>
											<td>경영기획팀</td>
											<td>부장</td>
											<td class="bdrn check_name">이수연</td>
										</tr>
										<tr>
											<td><input type="radio" name="checkName" id="" /></td>
											<td>경영기획팀</td>
											<td>과장</td>
											<td class="bdrn check_name">강웅</td>
										</tr>
										<tr>
											<td><input type="radio" name="checkName" id="" /></td>
											<td>경영기획팀</td>
											<td>부장</td>
											<td class="bdrn check_name">최일지</td>
										</tr>
										<tr>
											<td><input type="radio" name="checkName" id="" /></td>
											<td>경영기획팀</td>
											<td>부장</td>
											<td class="bdrn check_name">이정민</td>
										</tr>
									</tbody>
								</table>
							</div>
							<div class="btn_pop">
								<button class="type_01 btn_appr2">결재</button>
							</div>
						</div>
						<div class="float_r">
							<div class="settle_men fl" data-role="ui-droplist" data-type="radio">
								<h4>결재자</h4>
								<ul class="select_list fl apprList2" data-role="ui-droplist-list">
									<li data-role="ui-droplist-item"><input type="radio" name="appr" id="a01"><label for="a01">아무개</label></li>
									<li data-role="ui-droplist-item"><input type="radio" name="appr" id="a02"><label for="a02">홍길동</label></li>
									<li data-role="ui-droplist-item"><input type="radio" name="appr" id="a03"><label for="a03">정임원</label></li>
									<li data-role="ui-droplist-item"><input type="radio" name="appr" id="a04"><label for="a04">아무개</label></li>
									<li data-role="ui-droplist-item"><input type="radio" name="appr" id="a05"><label for="a05">홍길동</label></li>
									<li data-role="ui-droplist-item"><input type="radio" name="appr" id="a06"><label for="a06">정임원</label></li>
								</ul>
								<div class="btn_list fr">
									<a href="#" class="btn_top" data-role="ui-droplist-btn-up"><img src="../../../asset/img/popup_btn_top.gif" alt="위로"></a>
									<a href="#" class="btn_bottom" data-role="ui-droplist-btn-down"><img src="../../../asset/img/popup_btn_bottom.gif" alt="아래로"></a>
									<a href="#" class="btn_close" data-role="ui-droplist-btn-remove"><img src="../../../asset/img/popup_btn_close02.gif" alt="삭제"></a>
								</div>
							</div>
							<div class="btn_pop">
								<button class="type_01">취소</button>
								<button class="type_01">결재라인 적용</button>
							</div>
						</div>
					</div>
				</div>
				<button class="close"><span class="blind">닫기</span></button>
			</div>
		</div>
		<!--  popup end -->		

<!-- ######## end ######### -->

<script src="http://localhost:8080/hanagw/asset/js/jquery-1.11.1.min.js"></script>
<script src="http://localhost:8080/hanagw/asset/js/droplist.js"></script>
</body>
</html>