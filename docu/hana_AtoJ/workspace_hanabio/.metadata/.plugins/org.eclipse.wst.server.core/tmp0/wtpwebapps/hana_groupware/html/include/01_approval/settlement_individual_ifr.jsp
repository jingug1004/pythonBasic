<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="ko">
<head>
	<%@include file="../../include/head.jsp"%>	
</head>
<body>


	<div class="authority_staff_ifr">
		
			<div class="fr">
				<div class="settle_cont01">
					<div class="search_box02">
						<span>사원명</span>
						<input type="text" />
						<button>검색</button>
					</div>
					<div class="settle_table01">
						<table>
						<colgroup>
							<col style="width:28px" />
							<col style="width:62px" />
							<col style="width:131px" />
							<col style="width:75px" />
							<col style="width:103px" />
						</colgroup>
							<thead>
								<tr>
									<th><input type="checkbox" /></th>
									<th>조직</th>
									<th>부서</th>
									<th>직급</th>
									<th class="br_none">사원명</th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<td><input type="checkbox" /></td>
									<td>조직</td>
									<td>경영기획팀</td>
									<td>부장</td>
									<td class="br_none">이박사</td>
								</tr>
								<tr>
									<td><input type="checkbox" /></td>
									<td></td>
									<td></td>
									<td></td>
									<td class="br_none"></td>
								</tr>
								<tr>
									<td><input type="checkbox" /></td>
									<td></td>
									<td></td>
									<td></td>
									<td class="br_none"></td>
								</tr>
								<tr>
									<td><input type="checkbox" /></td>
									<td></td>
									<td></td>
									<td></td>
									<td class="br_none"></td>
								</tr>								
							</tbody>
						</table>
					</div>
					<div class="list_btn">
						<div class="list_button mt5">
							<button class="type_01">결재</button>
							<button class="type_01">참조</button>
						</div>
					</div>
				</div>
				<div class="settle_cont02">
					<div class="settle_men fl" data-role="ui-droplist" data-type="radio">
						<h4>결재자</h4>
						<ul class="select_list fl" data-role="ui-droplist-list">
							<li data-role="ui-droplist-item"><input type="radio" name="appr" id="a01" /><label for="a01">아무개</label></li>
							<li data-role="ui-droplist-item"><input type="radio" name="appr" id="a02" /><label for="a02">홍길동</label></li>
							<li data-role="ui-droplist-item"><input type="radio" name="appr" id="a03" /><label for="a03">정임원</label></li>
						</ul>
						<div class="btn_list fr">
							<a href="#" class="btn_top" data-role="ui-droplist-btn-up"><img src="../../../asset/img/popup_btn_top.gif" alt="위로"></a>
							<a href="#" class="btn_bottom" data-role="ui-droplist-btn-down"><img src="../../../asset/img/popup_btn_bottom.gif" alt="아래로"></a>
							<a href="#" class="btn_close" data-role="ui-droplist-btn-remove"><img src="../../../asset/img/popup_btn_close02.gif" alt="삭제"></a>
						</div>
						<p class="list_txt">※ 결재자 지정 시 기안부서확인자를 필수로 체크 바랍니다.</p>
					</div>
					<div class="settle_men fr" data-role="ui-droplist" data-type="basic">
						<h4>참조자</h4>
						<ul class="select_list fl" data-role="ui-droplist-list">
							<li data-role="ui-droplist-item"><a href="#">최일지</a></li>														
						</ul>
						<div class="btn_list fr">
							<a href="#" class="btn_top" data-role="ui-droplist-btn-up"><img src="../../../asset/img/popup_btn_top.gif" alt="위로"></a>
							<a href="#" class="btn_bottom" data-role="ui-droplist-btn-down"><img src="../../../asset/img/popup_btn_bottom.gif" alt="아래로"></a>
							<a href="#" class="btn_close" data-role="ui-droplist-btn-remove"><img src="../../../asset/img/popup_btn_close02.gif" alt="삭제"></a>
						</div>
					</div>
				</div>
				<div class="settle_cont02">
					<div class="settle_men fl">
						<div class="settle_table02">
							<table>
							<colgroup>
								<col style="width:28px" />
								<col style="width:167px" />
							</colgroup>
								<thead>
									<tr>
										<th><input type="checkbox" /></th>
										<th>부서</th>
									</tr>
								</thead>
								<tbody>
									<tr>
										<td><input type="checkbox" /></td>
										<td>경영기획팀</td>
									</tr>
									<tr>
										<td><input type="checkbox" /></td>
										<td></td>
									</tr>
									<tr>
										<td><input type="checkbox" /></td>
										<td></td>
									</tr>
								</tbody>
							</table>
						</div>
						<div class="list_btn">
							<div class="list_button">
								<button class="type_01">협조</button>
							</div>
						</div>
					</div>
					<div class="settle_men fr" data-role="ui-droplist" data-type="basic">
						<h4>협조부서</h4>
						<ul class="select_list01 fl" data-role="ui-droplist-list">
							<li data-role="ui-droplist-item"><a href="#">영업기획팀</a></li>
							<li data-role="ui-droplist-item"><a href="#">전산팀</a></li>
						</ul>
						<div class="btn_list01 fr">
							<a href="#" class="btn_top" data-role="ui-droplist-btn-up"><img src="../../../asset/img/popup_btn_top.gif" alt="위로" /></a>
							<a href="#" class="btn_bottom" data-role="ui-droplist-btn-down"><img src="../../../asset/img/popup_btn_bottom.gif" alt="아래로" /></a>
							<a href="#" class="btn_close" data-role="ui-droplist-btn-remove"><img src="../../../asset/img/popup_btn_close02.gif" alt="삭제" /></a>
						</div>
						<div class="list_btn">
							<div class="list_button">
								<button class="type_01">현재결제라인저장</button>
							</div>
						</div>
					</div>
				</div>	
			</div>
		
		
	</div>

<script src="http://localhost:8080/hanagw/asset/js/jquery-1.11.1.min.js"></script>
<script src="http://localhost:8080/hanagw/asset/js/default.js"></script>

</body>
</html>